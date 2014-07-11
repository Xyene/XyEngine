package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.scene.gl20.shadow.ShadowMapFBO;
import tk.ivybits.engine.gl.shader.AbstractShader;
import tk.ivybits.engine.gl.shader.ISceneShader;
import tk.ivybits.engine.gl.shader.Program;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.node.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.shader.Program.ShaderType.FRAGMENT;
import static tk.ivybits.engine.gl.shader.Program.ShaderType.VERTEX;

public class PhongLightingShader extends AbstractShader implements ISceneShader, ISceneChangeListener {
    private final IScene scene;
    private List<ShadowMapFBO> shadowMapFBO;

    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.v.glsl";
    private boolean uniformsFetched = false;
    private int
            MATERIAL_BASE,
            HAS_FOG,
            FOG_BASE,
            POINT_LIGHT_COUNT,
            SPOTLIGHT_COUNT,
            POINT_LIGHT_BASE,
            SPOTLIGHT_BASE,
            SHADOW_MAP_BASE,
            LIGHT_MATRIX_COUNT,
            LIGHT_MATRIX_BASE,
            PROJECTION_MATRIX,
            MODEL_MATRIX,
            VIEW_MATRIX,
            NORMAL_MATRIX;
    private int[] ATTRIBUTES;
    private Program shader;
    private Projection proj;
    private Matrix4f bias = new Matrix4f();

    {
//        bias.load(FloatBuffer.wrap(new float[]{
//                0.5f, 0.0f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.0f, 0.0f,
//                0.0f, 0.0f, 0.5f, 0.0f,
//                0.5f, 0.5f, 0.5f, 1.0f}));
        bias.m00 = .5f;
        bias.m10 = .0f;
        bias.m20 = .0f;
        bias.m30 = .0f;
        bias.m01 = .0f;
        bias.m11 = .5f;
        bias.m21 = .0f;
        bias.m31 = .0f;
        bias.m02 = .0f;
        bias.m12 = .0f;
        bias.m22 = .5f;
        bias.m32 = .0f;
        bias.m03 = .5f;
        bias.m13 = .5f;
        bias.m23 = .5f;
        bias.m33 = 1f;
    }

    private List<ISpotLight> spotLights = new ArrayList<>();
    private List<IPointLight> pointLights = new ArrayList<>();

    private void setupHandles() {
        if (uniformsFetched) {
            return;
        }
        uniformsFetched = true;
        MATERIAL_BASE = 0; // shader.getUniformLocation("u_material"); reports -1
        HAS_FOG = shader.getUniformLocation("u_hasFog");
        FOG_BASE = shader.getUniformLocation("u_fog.fogColor");
        POINT_LIGHT_COUNT = shader.getUniformLocation("u_pointLightCount");
        SPOTLIGHT_COUNT = shader.getUniformLocation("u_spotLightCount");
        POINT_LIGHT_BASE = shader.getUniformLocation("u_pointLights[0].position");
        SPOTLIGHT_BASE = shader.getUniformLocation("u_spotLights[0].position");
        SHADOW_MAP_BASE = shader.getUniformLocation("u_shadowMap[0]");
        LIGHT_MATRIX_COUNT = shader.getUniformLocation("u_lightMatrixCount");
        LIGHT_MATRIX_BASE = shader.getUniformLocation("u_lightViewMatrix[0]");
        PROJECTION_MATRIX = shader.getUniformLocation("u_projectionMatrix");
        MODEL_MATRIX = shader.getUniformLocation("u_modelMatrix");
        VIEW_MATRIX = shader.getUniformLocation("u_viewMatrix");
        NORMAL_MATRIX = shader.getUniformLocation("u_normalMatrix");

        ATTRIBUTES = new int[]{
                shader.getAttributeLocation("a_Vertex"),
                shader.getAttributeLocation("a_Normal"),
                shader.getAttributeLocation("a_UV"),
                shader.getAttributeLocation("a_Tangent")
        };
    }

    public PhongLightingShader(IScene scene, List<ShadowMapFBO> shadowMapFBO) {
        this.scene = scene;
        this.shadowMapFBO = shadowMapFBO;
    }

    @Override
    public void attach() {
        super.attach();
        setupHandles();

        shader.setUniform(LIGHT_MATRIX_COUNT, shadowMapFBO.size());
        for (int n = 0; n < shadowMapFBO.size(); n++) {
            ShadowMapFBO fbo = shadowMapFBO.get(n);

            Matrix4f depthBiasMVP = new Matrix4f();
            Matrix4f.mul(proj.getProjectionMatrix(), fbo.projection, depthBiasMVP);
            Matrix4f.mul(depthBiasMVP, proj.getModelMatrix(), depthBiasMVP);
            Matrix4f.mul(bias, depthBiasMVP, depthBiasMVP);

            glActiveTexture(GL_TEXTURE3 + n);
            glEnable(GL_TEXTURE_2D);
            shader.setUniform(SHADOW_MAP_BASE + n, 3 + n);
            fbo.bind();
            shader.setUniform(LIGHT_MATRIX_BASE + n, fbo.projection);
        }
    }

    @Override
    public void useMaterial(Material material) {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        if (material.diffuseTexture != null) {
            glActiveTexture(GL_TEXTURE0);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE, 1);
            glUniform1i(MATERIAL_BASE + 1, 0);
            material.diffuseTexture.bind();
        } else {
            glUniform1i(MATERIAL_BASE, 0);
        }
        if (material.specularTexture != null) {
            glActiveTexture(GL_TEXTURE1);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE + 2, 1);
            glUniform1i(MATERIAL_BASE + 3, 1);
            material.specularTexture.bind();
        } else {
            glUniform1i(MATERIAL_BASE + 2, 0);
        }
        if (material.bumpMap != null) {
            glActiveTexture(GL_TEXTURE2);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE + 4, 1);
            glUniform1i(MATERIAL_BASE + 5, 2);
            material.bumpMap.bind();
        } else {
            glUniform1i(MATERIAL_BASE + 4, 0);
        }

        Color ambient = material.ambientColor;
        glUniform3f(MATERIAL_BASE + 6,
                ambient.getRed() / 255F,
                ambient.getGreen() / 255F,
                ambient.getBlue() / 255F);
        Color diffuse = material.diffuseColor;
        glUniform3f(MATERIAL_BASE + 7,
                diffuse.getRed() / 255F,
                diffuse.getGreen() / 255F,
                diffuse.getBlue() / 255F);
        Color specular = material.specularColor;
        glUniform3f(MATERIAL_BASE + 8,
                specular.getRed() / 255F,
                specular.getGreen() / 255F,
                specular.getBlue() / 255F);
        glUniform1f(MATERIAL_BASE + 9, 128 - material.shininess + 1);
        glUniform1f(MATERIAL_BASE + 10, material.transparency);
        if (!attached) super.detach();
    }

    @Override
    public int getShaderHandle() {
        return (shader == null ? shader = Program.builder()
                .loadSystemShader(VERTEX, VERTEX_SHADER_LOCATION)
                .loadSystemShader(FRAGMENT, FRAGMENT_SHADER_LOCATION)
                .build() : shader).getId();
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        return ATTRIBUTES[attribute.ordinal()];
    }

    @Override
    public void setProjection(Projection proj) {
        this.proj = proj;
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        shader.setUniform(PROJECTION_MATRIX, proj.getProjectionMatrix());
        Matrix4f model = proj.getModelMatrix();
        shader.setUniform(MODEL_MATRIX, model);
        shader.setUniform(VIEW_MATRIX, proj.getViewMatrix());
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
        shader.setUniform(NORMAL_MATRIX, normals);
        if (!attached) super.detach();
    }

    void updateLights() {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        for (int i = 0; i < pointLights.size(); i++) {
            IPointLight light = pointLights.get(i);

            int base = (POINT_LIGHT_BASE + (i * 5));

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
        glUniform1i(POINT_LIGHT_COUNT, pointLights.size());

        for (int i = 0; i < spotLights.size(); i++) {
            ISpotLight light = spotLights.get(i);

            int base = (SPOTLIGHT_BASE + (i * 7));

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
        glUniform1i(SPOTLIGHT_COUNT, spotLights.size());

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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void fogUpdated(IFog fog) {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        if (fog.isEnabled()) {
            glUniform1i(HAS_FOG, 1);
            int base = FOG_BASE;
            Color fogColor = fog.getFogColor();
            glUniform3f(base,
                    fogColor.getRed() / 255F,
                    fogColor.getGreen() / 255F,
                    fogColor.getBlue() / 255F);
            glUniform1f(base + 1, fog.getFogNear());
            glUniform1f(base + 2, fog.getFogFar());
        } else {
            glUniform1i(HAS_FOG, 0);
        }
        if (!attached) super.detach();
    }

    @Override
    public void actorAdded(IActor actor) {

    }

    @Override
    public void actorRemoved(IActor actor) {

    }
}
