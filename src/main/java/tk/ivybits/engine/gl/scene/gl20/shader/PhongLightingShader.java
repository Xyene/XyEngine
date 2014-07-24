package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.scene.gl20.shadow.ShadowMapFBO;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.node.*;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.Program.ShaderType.FRAGMENT;
import static tk.ivybits.engine.gl.Program.ShaderType.VERTEX;

public class PhongLightingShader extends AbstractShader implements ISceneShader, ISceneChangeListener {
    private final IScene scene;
    private List<ShadowMapFBO> shadowMapFBO;

    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.v.glsl";
    private static final String VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE;

    static {
        VERTEX_SHADER_SOURCE = Program.ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
        FRAGMENT_SHADER_SOURCE = Program.ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(FRAGMENT_SHADER_LOCATION));
    }

    private boolean uniformsFetched = false;
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

    private void setupHandles() {
        if (uniformsFetched) {
            return;
        }
        uniformsFetched = true;

        // This is in case we rebuilt the shader
        updateLights();
        fogUpdated(scene.getSceneGraph().getAtmosphere().getFog());
    }

    public PhongLightingShader(IScene scene, List<ShadowMapFBO> shadowMapFBO) {
        this.scene = scene;
        this.shadowMapFBO = shadowMapFBO;
    }

    @Override
    public void attach() {
        super.attach();
        setupHandles();

        if (scene.getDrawContext().isEnabled(IDrawContext.OBJECT_SHADOWS)) {
            shader.setUniform(shader.getUniformLocation("u_lightMatrixCount"), shadowMapFBO.size());
            for (int n = 0; n < shadowMapFBO.size(); n++) {
                glActiveTexture(GL_TEXTURE3 + n);
                glEnable(GL_TEXTURE_2D);
                shader.setUniform(shader.getUniformLocation("u_shadowMap[0]") + n, 3 + n);
                shadowMapFBO.get(n).bind();
            }
        }
    }

    @Override
    public void useMaterial(Material material) {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        int texture = 0;
        if (material.diffuseTexture != null) {
            glActiveTexture(GL_TEXTURE0 + texture);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(shader.getUniformLocation("u_material.hasDiffuse"), 1);
            glUniform1i(shader.getUniformLocation("u_material.diffuseMap"), (texture++));
            material.diffuseTexture.bind();
        } else {
            glUniform1i(shader.getUniformLocation("u_material.hasDiffuse"), 0);
        }
        if (scene.getDrawContext().isEnabled(IDrawContext.SPECULAR_MAPS))
            if (material.specularTexture != null) {
                glActiveTexture(GL_TEXTURE0 + texture);
                glEnable(GL_TEXTURE_2D);
                glUniform1i(shader.getUniformLocation("u_material.hasSpecular"), 1);
                glUniform1i(shader.getUniformLocation("u_material.specularMap"), (texture++));
                material.specularTexture.bind();
            } else {
                glUniform1i(shader.getUniformLocation("u_material.hasSpecular"), 0);
            }
        if (scene.getDrawContext().isEnabled(IDrawContext.NORMAL_MAPS))
            if (material.bumpMap != null) {
                glActiveTexture(GL_TEXTURE0 + texture);
                glEnable(GL_TEXTURE_2D);
                glUniform1i(shader.getUniformLocation("u_material.hasNormal"), 1);
                glUniform1i(shader.getUniformLocation("u_material.normalMap"), (texture++));
                material.bumpMap.bind();
            } else {
                glUniform1i(shader.getUniformLocation("u_material.hasNormal"), 0);
            }

        Color ambient = material.ambientColor;
        glUniform3f(shader.getUniformLocation("u_material.ambient"),
                ambient.getRed() / 255F,
                ambient.getGreen() / 255F,
                ambient.getBlue() / 255F);
        Color diffuse = material.diffuseColor;
        glUniform3f(shader.getUniformLocation("u_material.diffuse"),
                diffuse.getRed() / 255F,
                diffuse.getGreen() / 255F,
                diffuse.getBlue() / 255F);
        Color specular = material.specularColor;
        glUniform3f(shader.getUniformLocation("u_material.specular"),
                specular.getRed() / 255F,
                specular.getGreen() / 255F,
                specular.getBlue() / 255F);
        glUniform1f(shader.getUniformLocation("u_material.shininess"), 128 - material.shininess + 1);
        glUniform1f(shader.getUniformLocation("u_material.transparency"), material.transparency);
        if (!attached) super.detach();
    }

    @Override
    protected int getShaderHandle() {
        List<Boolean> identifier = Arrays.asList(
                scene.getDrawContext().isEnabled(IDrawContext.NORMAL_MAPS),
                scene.getDrawContext().isEnabled(IDrawContext.SPECULAR_MAPS),
                scene.getDrawContext().isEnabled(IDrawContext.OBJECT_SHADOWS),
                scene.getDrawContext().isEnabled(IDrawContext.FOG)
        );
        shader = shaders.get(identifier);
        if (shader == null) {
            Program.ProgramBuilder builder = Program.builder()
                    .addShader(VERTEX, VERTEX_SHADER_SOURCE)
                    .addShader(FRAGMENT, FRAGMENT_SHADER_SOURCE);
            for (int i = 0; i != identifier.size(); i++) {
                if (identifier.get(i)) {
                    builder.define(DEFINE_LOOKUP[i]);
                }
            }
            shader = builder.build();
            shaders.put(identifier, shader);
            uniformsFetched = false;
        }
        return shader.getId();
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        if (!uniformsFetched) {
            boolean attached = isAttached;
            if (!attached) super.attach();
            setupHandles();
            if (!attached) super.detach();
        }
        return new int[]{
                shader.getAttributeLocation("a_Vertex"),
                shader.getAttributeLocation("a_Normal"),
                shader.getAttributeLocation("a_UV"),
                shader.getAttributeLocation("a_Tangent")
        }[attribute.ordinal()];
    }

    @Override
    public void setProjection(Projection proj) {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        Matrix4f model = proj.getModelMatrix();
        shader.setUniform(shader.getUniformLocation("u_modelMatrix"), model);
        Matrix3f normals = new Matrix3f();
        normals.m00 = model.m00;
        normals.m01 = model.m01;
        normals.m02 = model.m02;
        normals.m10 = model.m10;
        normals.m11 = model.m11;
        normals.m12 = model.m12;
        normals.m20 = model.m20;
        normals.m21 = model.m21;
        normals.m22 = model.m22;
        Matrix3f.transpose(normals, normals);
        Matrix3f.invert(normals, normals);
        shader.setUniform(shader.getUniformLocation("u_normalMatrix"), normals);

        Matrix4f mvp = new Matrix4f();
        Matrix4f.mul(proj.getProjectionMatrix(), proj.getViewMatrix(), mvp);
        Matrix4f.mul(mvp, proj.getModelMatrix(), mvp);
        shader.setUniform(shader.getUniformLocation("u_mvpMatrix"), mvp);

        if (scene.getDrawContext().isEnabled(IDrawContext.OBJECT_SHADOWS)) {
            for (int n = 0; n < shadowMapFBO.size(); n++) {
                Matrix4f depthBiasMVP = new Matrix4f();
                Matrix4f.mul(proj.getProjectionMatrix(), shadowMapFBO.get(n).projection, depthBiasMVP);
                Matrix4f.mul(depthBiasMVP, proj.getModelMatrix(), depthBiasMVP);
                Matrix4f.mul(bias, depthBiasMVP, depthBiasMVP);

                shader.setUniform(shader.getUniformLocation("u_lightViewMatrix[0]") + n, depthBiasMVP);
            }
        }

        if (!attached) super.detach();
    }

    @Override
    public Program getProgram() {
        return shader;
    }

    void updateLights() {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        // TODO: this may not be safe
        for (int i = 0; i < dirLights.size(); i++) {
            IDirectionalLight light = dirLights.get(i);

            int base = (shader.getUniformLocation("u_dirLights[0].direction") + (i * 5));

            glUniform3f(base, light.dx(), light.dy(), light.dz());
            glUniform1f(base + 1, light.getIntensity());
            Color ambient = light.getAmbientColor();
            glUniform3f(base + 2,
                    ambient.getRed() / 255F,
                    ambient.getGreen() / 255F,
                    ambient.getBlue() / 255F);
            Color diffuse = light.getDiffuseColor();
            glUniform3f(base + 3,
                    diffuse.getRed() / 255F,
                    diffuse.getGreen() / 255F,
                    diffuse.getBlue() / 255F);
            Color specular = light.getSpecularColor();
            glUniform3f(base + 4,
                    specular.getRed() / 255F,
                    specular.getGreen() / 255F,
                    specular.getBlue() / 255F);
        }
        glUniform1i(shader.getUniformLocation("u_dirLightCount"), dirLights.size());

        for (int i = 0; i < pointLights.size(); i++) {
            IPointLight light = pointLights.get(i);

            int base = (shader.getUniformLocation("u_pointLights[0].position") + (i * 5));

            glUniform3f(base, light.x(), light.y(), light.z());
            glUniform1f(base + 1, light.getIntensity());
            Color ambient = light.getAmbientColor();
            glUniform3f(base + 2,
                    ambient.getRed() / 255F,
                    ambient.getGreen() / 255F,
                    ambient.getBlue() / 255F);
            Color diffuse = light.getDiffuseColor();
            glUniform3f(base + 3,
                    diffuse.getRed() / 255F,
                    diffuse.getGreen() / 255F,
                    diffuse.getBlue() / 255F);
            Color specular = light.getSpecularColor();
            glUniform3f(base + 4,
                    specular.getRed() / 255F,
                    specular.getGreen() / 255F,
                    specular.getBlue() / 255F);
        }
        glUniform1i(shader.getUniformLocation("u_pointLightCount"), pointLights.size());

        for (int i = 0; i < spotLights.size(); i++) {
            ISpotLight light = spotLights.get(i);

            int base = (shader.getUniformLocation("u_spotLights[0].position") + (i * 7));

            glUniform3f(base, light.x(), light.y(), light.z());

            glUniform3f(base + 1, light.dx(), light.dy(), light.dz());
            glUniform1f(base + 2, (float) Math.cos(Math.toRadians(light.getCutoff())));
            glUniform1f(base + 3, light.getIntensity());
            Color ambient = light.getAmbientColor();
            glUniform3f(base + 4,
                    ambient.getRed() / 255F,
                    ambient.getGreen() / 255F,
                    ambient.getBlue() / 255F);
            Color diffuse = light.getDiffuseColor();
            glUniform3f(base + 5,
                    diffuse.getRed() / 255F,
                    diffuse.getGreen() / 255F,
                    diffuse.getBlue() / 255F);
            Color specular = light.getSpecularColor();
            glUniform3f(base + 6,
                    specular.getRed() / 255F,
                    specular.getGreen() / 255F,
                    specular.getBlue() / 255F);
        }
        glUniform1i(shader.getUniformLocation("u_spotLightCount"), spotLights.size());

        if (!attached) super.detach();
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
        // TODO: recompile shader for these settings
        if (scene.getDrawContext().isEnabled(IDrawContext.FOG)) {
            boolean attached = isAttached;
            if (!attached) super.attach();
            setupHandles();
            Color fogColor = fog.getFogColor();
            glUniform3f(shader.getUniformLocation("u_fog.fogColor"),
                    fogColor.getRed() / 255F,
                    fogColor.getGreen() / 255F,
                    fogColor.getBlue() / 255F);
            glUniform1f(shader.getUniformLocation("u_fog.fogNear") + 1, fog.getFogNear());
            glUniform1f(shader.getUniformLocation("u_fog.fogFar") + 2, fog.getFogFar());

            if (!attached) super.detach();
        }
    }

    @Override
    public void actorAdded(IActor actor) {

    }

    @Override
    public void actorRemoved(IActor actor) {

    }
}
