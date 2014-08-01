package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ProgramBuilder;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.node.*;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private HashMap<FrameBuffer, Matrix4f> shadowMapFBO;

    private Matrix4f modelMatrix = new Matrix4f(), viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    private boolean needsScenePush = true;
    private HashMap<List<Boolean>, Program> shaders = new HashMap<>();

    private Program shader;
    private Matrix4f bias = new Matrix4f();

    private static final String[] DEFINE_LOOKUP = {
            "NORMAL_MAPPING",
            "SPECULAR_MAPPING",
            "OBJECT_SHADOWS",
            "FOG"
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
    private final HashMap<BufferedImage, Texture> textureCache = new HashMap<BufferedImage, Texture>() {
        @Override
        public Texture get(Object obj) {
            Texture tex = super.get(obj);
            BufferedImage image = (BufferedImage)obj;
            if (tex == null) {
                tex = new Texture(image)
                        .setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                        .setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                        .setParameter(GL_GENERATE_MIPMAP, GL_TRUE);
                if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
                    tex.setParameter(GL_TEXTURE_MAX_ANISOTROPY_EXT, glGetInteger(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
                put(image, tex);
            }
            return tex;
        }
    };

    private void setupHandles() {
        if (!needsScenePush) {
            return;
        }
        needsScenePush = false;

        // This is in case we rebuilt the shader
        updateLights();
        fogUpdated(scene.getSceneGraph().getAtmosphere().getFog());
        setProjection();
    }

    public BaseShader(Map<ProgramType, List<String>> sources, IScene scene, HashMap<FrameBuffer, Matrix4f> shadowMapFBO) {
        this.sources = sources;
        this.scene = scene;
        this.shadowMapFBO = shadowMapFBO;
    }

    public void useMaterial(Material material) {
        getProgram().attach();
        setupHandles();

        int texture = 0;
        if (material.diffuseTexture != null) {
            shader.setUniform("u_material.hasDiffuse", 1);
            if (shader.hasUniform("u_material.diffuseMap")) {
                glActiveTexture(GL_TEXTURE0 + texture);
                glEnable(GL_TEXTURE_2D);
                shader.setUniform("u_material.diffuseMap", (texture++));
                textureCache.get(material.diffuseTexture).bind();
            }
        } else {
            shader.setUniform("u_material.hasDiffuse", 0);
        }
        if (scene.getDrawContext().isEnabled(SPECULAR_MAPS))
            if (material.specularTexture != null) {
                shader.setUniform("u_material.hasSpecular", 1);
                if (shader.hasUniform("u_material.specularMap")) {
                    glActiveTexture(GL_TEXTURE0 + texture);
                    glEnable(GL_TEXTURE_2D);
                    shader.setUniform("u_material.specularMap", (texture++));
                    textureCache.get(material.specularTexture).bind();
                }
            } else {
                shader.setUniform("u_material.hasSpecular", 0);
            }
        if (scene.getDrawContext().isEnabled(NORMAL_MAPS))
            if (material.bumpMap != null) {
                shader.setUniform("u_material.hasNormal", 1);
                if (shader.hasUniform("u_material.normalMap")) {
                    glActiveTexture(GL_TEXTURE0 + texture);
                    glEnable(GL_TEXTURE_2D);
                    shader.setUniform("u_material.normalMap", texture);
                }
                textureCache.get(material.bumpMap).bind();
            } else {
                shader.setUniform("u_material.hasNormal", 0);
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
        shader.setUniform("u_material.transparency", material.transparency);
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
                for (FrameBuffer map : shadowMapFBO.keySet()) {
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
                scene.getDrawContext().isEnabled(NORMAL_MAPS),
                scene.getDrawContext().isEnabled(SPECULAR_MAPS),
                scene.getDrawContext().isEnabled(OBJECT_SHADOWS),
                scene.getDrawContext().isEnabled(FOG)
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
}
