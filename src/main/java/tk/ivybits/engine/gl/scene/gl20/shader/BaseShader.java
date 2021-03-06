/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.gl.ProgramBuilder;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.texture.CubeTexture;
import tk.ivybits.engine.gl.texture.Framebuffer;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.io.res.IResource;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.event.ISceneChangeListener;
import tk.ivybits.engine.scene.model.Material;
import tk.ivybits.engine.scene.node.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.*;
import java.util.List;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.scene.IDrawContext.Capability.*;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.*;

/**
 * Defines a common base for all shaders extending scene rendering.
 */
public class BaseShader implements ISceneChangeListener {
    private final Map<ProgramType, List<String>> sources;
    private final IScene scene;
    private HashMap<Framebuffer, Matrix4f> shadowMapFBO;

    private Matrix4f modelMatrix = new Matrix4f(), viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    private boolean needsScenePush = true;
    private HashMap<List<Boolean>, Program> shaders = new HashMap<>();

    private Program shader;
    private Matrix4f bias = new Matrix4f();

    private static final String[] DEFINE_LOOKUP = {
            "NORMAL_MAPPING",
            "PARALLAX_MAPPING",
            "SPECULAR_MAPPING",
            "OBJECT_SHADOWS",
            "FOG",
            "REFLECTIONS"
    };

    {
        bias.load(FloatBuffer.wrap(new float[]{
                0.5f, 0.0f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.5f, 0.0f,
                0.5f, 0.5f, 0.5f, 1.0f}));
    }

    private List<ISpotLight> spotLights = new ArrayList<>();
    private List<IPointLight> pointLights = new ArrayList<>();
    private List<IDirectionalLight> dirLights = new ArrayList<>();
    private final WeakHashMap<IResource, Texture> textureCache = new WeakHashMap<IResource, Texture>() {
        @Override
        public Texture get(Object obj) {
            Texture tex = super.get(obj);
            if (tex == null) {
                IResource res = (IResource) obj;
                BufferedImage image = null;
                try {
                    image = ImageIO.read(res.openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tex = new Texture(GL_TEXTURE_2D, image, false)
                        .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                        .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                        .setParameter(GL_GENERATE_MIPMAP, GL_TRUE)
                        .setParameter(GL_TEXTURE_BASE_LEVEL, 0)
                        .setParameter(GL_TEXTURE_MAX_LEVEL, 8);
                if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
                    tex.setParameter(GL_TEXTURE_MAX_ANISOTROPY_EXT, glGetInteger(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                put(res, tex);
            }
            return tex;
        }
    };
    private CubeTexture environmentMap;
    private Vector3f eyePosition;

    private void setupHandles() {
        if (!needsScenePush) {
            return;
        }
        needsScenePush = false;

        // This is in case we rebuilt the shader
        updateLights();
        fogUpdated(scene.getSceneGraph().getAtmosphere().getFog());
        setProjection();
        if (eyePosition != null) setEyePosition(eyePosition);
        if (environmentMap != null) setEnvironmentMap(environmentMap);
    }

    public BaseShader(Map<ProgramType, List<String>> sources, IScene scene, HashMap<Framebuffer, Matrix4f> shadowMapFBO) {
        this.sources = sources;
        this.scene = scene;
        this.shadowMapFBO = shadowMapFBO;
    }

    public void useMaterial(Material material) {
        getProgram().attach();
        setupHandles();

        int texture = 0;
        if (material.diffuseMap != null) {
            shader.setUniform("u_material.hasDiffuse", true);
            if (shader.hasUniform("u_material.diffuseMap")) {
                shader.setUniform("u_material.diffuseMap", texture);
                textureCache.get(material.diffuseMap).bind(texture++);
            }
        } else {
            shader.setUniform("u_material.hasDiffuse", false);
        }
        if (scene.getDrawContext().isEnabled(SPECULAR_MAPPING))
            if (material.glossMap != null) {
                shader.setUniform("u_material.hasSpecular", true);
                if (shader.hasUniform("u_material.specularMap")) {
                    shader.setUniform("u_material.specularMap", texture);
                    textureCache.get(material.glossMap).bind(texture++);
                }
            } else {
                shader.setUniform("u_material.hasSpecular", false);
            }
        if (scene.getDrawContext().isEnabled(NORMAL_MAPPING))
            if (material.normalMap != null) {
                shader.setUniform("u_material.hasNormal", true);
                if (shader.hasUniform("u_material.normalMap")) {
                    shader.setUniform("u_material.normalMap", texture);
                    textureCache.get(material.normalMap).bind(texture++);
                }
            } else {
                shader.setUniform("u_material.hasNormal", false);
            }
        if (scene.getDrawContext().isEnabled(PARALLAX_MAPPING))
            if (material.heightMap != null) {
                shader.setUniform("u_material.hasHeight", true);
                if (shader.hasUniform("u_material.heightMap")) {
                    shader.setUniform("u_material.heightMap", texture);
                    textureCache.get(material.heightMap).bind(texture++);
                }
            } else {
                shader.setUniform("u_material.hasHeight", false);
            }

        Color ambient = material.ambientColor;
        shader.setUniform("u_material.ambient",
                ambient.getRed() / 255F,
                ambient.getGreen() / 255F,
                ambient.getBlue() / 255F);
        Color diffuse = material.diffuseColor;
        shader.setUniform("u_material.diffuse",
                diffuse.getRed() / 255F,
                diffuse.getGreen() / 255F,
                diffuse.getBlue() / 255F);
        Color specular = material.specularColor;
        shader.setUniform("u_material.specular",
                specular.getRed() / 255F,
                specular.getGreen() / 255F,
                specular.getBlue() / 255F);
        shader.setUniform("u_material.shininess", 128 - material.shininess + 1);
        shader.setUniform("u_material.opaqueness", material.opaqueness);

        if (scene.getDrawContext().isEnabled(REFLECTIONS)) {
            shader.setUniform("u_material.reflectivity", material.reflectivity);
            shader.setUniform("u_material.refractionIndex", material.refractionIndex);
        }

        shader.detach();
    }

    private void setProjection() {
        getProgram().attach();
        shader.setUniform("u_modelMatrix", modelMatrix);

        if (shader.hasUniform("u_normalMatrix")) {
            Matrix3f normals = new Matrix3f();
            normals.m00 = modelMatrix.m00;
            normals.m01 = modelMatrix.m01;
            normals.m02 = modelMatrix.m02;
            normals.m10 = modelMatrix.m10;
            normals.m11 = modelMatrix.m11;
            normals.m12 = modelMatrix.m12;
            normals.m20 = modelMatrix.m20;
            normals.m21 = modelMatrix.m21;
            normals.m22 = modelMatrix.m22;
            Matrix3f.transpose(normals, normals);
            Matrix3f.invert(normals, normals);
            shader.setUniform("u_normalMatrix", normals);
        }

        if (shader.hasUniform("u_mvpMatrix")) {
            Matrix4f mvp = new Matrix4f();
            Matrix4f.mul(projectionMatrix, viewMatrix, mvp);
            Matrix4f.mul(mvp, modelMatrix, mvp);
            shader.setUniform("u_mvpMatrix", mvp);
        }

        if (scene.getDrawContext().isEnabled(OBJECT_SHADOWS)) {
            if (shader.hasUniform("u_lightViewMatrix[0]")) {
                int n = 0;
                for (Matrix4f proj : shadowMapFBO.values()) {
                    Matrix4f depthBiasMVP = new Matrix4f();
                    Matrix4f.mul(projectionMatrix, proj, depthBiasMVP);
                    Matrix4f.mul(depthBiasMVP, modelMatrix, depthBiasMVP);
                    Matrix4f.mul(bias, depthBiasMVP, depthBiasMVP);

                    shader.setUniform("u_lightViewMatrix[" + n + "]", depthBiasMVP);
                    n++;
                }
            }
        }

        if (scene.getDrawContext().isEnabled(OBJECT_SHADOWS)) {
            shader.setUniform("u_lightMatrixCount", shadowMapFBO.size());
            if (shader.hasUniform("u_shadowMap[0]")) {
                int n = 0;
                for (Framebuffer map : shadowMapFBO.keySet()) {
                    glActiveTexture(GL_TEXTURE3 + n);
                    glEnable(GL_TEXTURE_2D);
                    shader.setUniform("u_shadowMap[" + n + "]", 3 + n);
                    map.bind();
                    n++;
                }
            }
        }

        shader.detach();
    }

    public Program getProgram() {
        List<Boolean> identifier = Arrays.asList(
                scene.getDrawContext().isEnabled(NORMAL_MAPPING),
                scene.getDrawContext().isEnabled(PARALLAX_MAPPING),
                scene.getDrawContext().isEnabled(SPECULAR_MAPPING),
                scene.getDrawContext().isEnabled(OBJECT_SHADOWS),
                scene.getDrawContext().isEnabled(FOG),
                scene.getDrawContext().isEnabled(REFLECTIONS)
        );
        shader = shaders.get(identifier);
        if (shader == null) {
            ProgramBuilder builder = Program.builder().add(sources);
            for (int i = 0; i != identifier.size(); i++) {
                if (identifier.get(i)) {
                    builder.define(DEFINE_LOOKUP[i]);
                }
            }
            shader = builder.build();
            shaders.put(identifier, shader);
            needsScenePush = true;
        }
        return shader;
    }

    public void setModelTransform(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
        setProjection();
    }

    public void setViewTransform(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
        setProjection();
    }

    public void setProjectionTransform(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        setProjection();
    }

    void updateLights() {
        getProgram().attach();
        setupHandles();
        if (shader.hasUniform("u_dirLights[0].direction")) {
            for (int i = 0; i < dirLights.size(); i++) {
                IDirectionalLight light = dirLights.get(i);

                String base = "u_dirLights[" + i + "]";

                shader.setUniform(base + ".direction", light.dx(), light.dy(), light.dz());
                shader.setUniform(base + ".intensity", light.getIntensity());
                Color ambient = light.getAmbientColor();
                shader.setUniform(base + ".ambient",
                        ambient.getRed() / 255F,
                        ambient.getGreen() / 255F,
                        ambient.getBlue() / 255F);
                Color diffuse = light.getDiffuseColor();
                shader.setUniform(base + ".diffuse",
                        diffuse.getRed() / 255F,
                        diffuse.getGreen() / 255F,
                        diffuse.getBlue() / 255F);
                Color specular = light.getSpecularColor();
                shader.setUniform(base + ".specular",
                        specular.getRed() / 255F,
                        specular.getGreen() / 255F,
                        specular.getBlue() / 255F);
            }
            shader.setUniform("u_dirLightCount", dirLights.size());
        }

        if (shader.hasUniform("u_pointLights[0].position")) {
            for (int i = 0; i < pointLights.size(); i++) {
                IPointLight light = pointLights.get(i);

                String base = "u_pointLights[" + i + "]";

                shader.setUniform(base + ".position", light.x(), light.y(), light.z());
                shader.setUniform(base + ".intensity", light.getIntensity());
                Color ambient = light.getAmbientColor();
                shader.setUniform(base + ".ambient",
                        ambient.getRed() / 255F,
                        ambient.getGreen() / 255F,
                        ambient.getBlue() / 255F);
                Color diffuse = light.getDiffuseColor();
                shader.setUniform(base + ".diffuse",
                        diffuse.getRed() / 255F,
                        diffuse.getGreen() / 255F,
                        diffuse.getBlue() / 255F);
                Color specular = light.getSpecularColor();
                shader.setUniform(base + ".specular",
                        specular.getRed() / 255F,
                        specular.getGreen() / 255F,
                        specular.getBlue() / 255F);
                shader.setUniform(base + ".attenuation", light.getAttenuation());
            }
            shader.setUniform("u_pointLightCount", pointLights.size());
        }

        if (shader.hasUniform("u_spotLights[0].position")) {
            for (int i = 0; i < spotLights.size(); i++) {
                ISpotLight light = spotLights.get(i);

                String base = "u_spotLights[" + i + "]";

                shader.setUniform(base + ".position", light.x(), light.y(), light.z());
                shader.setUniform(base + ".direction", light.dx(), light.dy(), light.dz());
                shader.setUniform(base + ".cutoff", (float) Math.cos(Math.toRadians(light.getCutoff())));
                shader.setUniform(base + ".intensity", light.getIntensity());
                Color ambient = light.getAmbientColor();
                shader.setUniform(base + ".ambient",
                        ambient.getRed() / 255F,
                        ambient.getGreen() / 255F,
                        ambient.getBlue() / 255F);
                Color diffuse = light.getDiffuseColor();
                shader.setUniform(base + ".diffuse",
                        diffuse.getRed() / 255F,
                        diffuse.getGreen() / 255F,
                        diffuse.getBlue() / 255F);
                Color specular = light.getSpecularColor();
                shader.setUniform(base + ".specular",
                        specular.getRed() / 255F,
                        specular.getGreen() / 255F,
                        specular.getBlue() / 255F);
                shader.setUniform(base + ".attenuation", light.getAttenuation());
            }
            shader.setUniform("u_spotLightCount", spotLights.size());
        }

        shader.detach();
    }

    @Override
    public void spotLightCreated(ISpotLight light) {
        spotLights.add(light);
        updateLights();
    }

    @Override
    public void pointLightCreated(IPointLight light) {
        pointLights.add(light);
        updateLights();
    }

    @Override
    public void directionalLightCreated(IDirectionalLight light) {
        dirLights.add(light);
        updateLights();
    }

    @Override
    public void spotLightUpdated(ISpotLight light) {
        updateLights();
    }

    @Override
    public void pointLightUpdated(IPointLight light) {
        updateLights();
    }

    @Override
    public void directionalLightUpdated(IDirectionalLight light) {
        updateLights();
    }

    @Override
    public void spotLightDestroyed(ISpotLight light) {
        spotLights.remove(light);
        updateLights();
    }

    @Override
    public void pointLightDestroyed(IPointLight light) {
        pointLights.remove(light);
        updateLights();
    }

    @Override
    public void directionalLightDestroyed(IDirectionalLight light) {
        dirLights.remove(light);
        updateLights();
    }

    @Override
    public void fogUpdated(IFog fog) {
        if (scene.getDrawContext().isEnabled(FOG)) {
            getProgram().attach();
            if (shader.hasUniform("u_fog.fogColor")) {
                Color fogColor = fog.getFogColor();
                shader.setUniform("u_fog.fogColor",
                        fogColor.getRed() / 255F,
                        fogColor.getGreen() / 255F,
                        fogColor.getBlue() / 255F);
                shader.setUniform("u_fog.fogNear", fog.getFogNear());
                shader.setUniform("u_fog.fogFar", fog.getFogFar());
            }
            shader.detach();
        }
    }

    @Override
    public void actorAdded(IActor actor) {

    }

    @Override
    public void actorRemoved(IActor actor) {

    }

    public void setEnvironmentMap(CubeTexture environmentMap) {
        this.environmentMap = environmentMap;
        getProgram().attach();
        setupHandles();
        if (shader.hasUniform("u_envMap")) {
            environmentMap.bind(4);
            shader.setUniform("u_envMap", 4);
        }
        shader.detach();
    }

    public void setEyePosition(Vector3f eyePosition) {
        this.eyePosition = eyePosition;
        getProgram().attach();
        setupHandles();
        shader.setUniform("u_eye", eyePosition);
        shader.detach();
    }
}
