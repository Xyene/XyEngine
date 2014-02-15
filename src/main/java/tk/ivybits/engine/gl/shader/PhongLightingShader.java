package tk.ivybits.engine.gl.shader;

import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.light.AtmosphereModel;
import tk.ivybits.engine.scene.light.PointLight;
import tk.ivybits.engine.scene.light.SpotLight;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.model.node.Material;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public class PhongLightingShader extends AbstractShader implements IGeometryShader {
    private final IScene scene;

    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_phong_lighting.v.glsl";
    private static int
            MATERIAL_BASE_UNIFORM_LOCATION = 0,
            FOG_BASE_UNIFORM_LOCATION = 15,
            POINT_LIGHT_COUNT_UNIFORM_LOCATION = 18,
            SPOTLIGHT_COUNT_UNIFORM_LOCATION = 19,
            POINT_LIGHT_BASE_UNIFORM_LOCATION = 20,
            SPOTLIGHT_BASE_UNIFORM_LOCATION = 340;
    private int handle;
    private Material material;

    public PhongLightingShader(IScene scene) {
        this.scene = scene;
    }

    @Override
    public void attach() {
        super.attach();
        AtmosphereModel atmo = scene.getAtmosphere();
        List<PointLight> pointLights = atmo.getPointLights();
        for (int i = 0; i < pointLights.size(); i++) {
            PointLight light = pointLights.get(i);

            int base = (POINT_LIGHT_BASE_UNIFORM_LOCATION + (i * 5));

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
        glUniform1i(POINT_LIGHT_COUNT_UNIFORM_LOCATION, atmo.getPointLights().size());
        List<SpotLight> spotLights = atmo.getSpotLights();
        for (int i = 0; i < spotLights.size(); i++) {
            SpotLight light = spotLights.get(i);

            int base = (SPOTLIGHT_BASE_UNIFORM_LOCATION + (i * 7));

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
        glUniform1i(SPOTLIGHT_COUNT_UNIFORM_LOCATION, atmo.getSpotLights().size());
    }

    @Override
    public void useMaterial(Material material) {
        this.material = material;

        if (material.diffuseTexture != null) {
            glActiveTexture(GL_TEXTURE0);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 2, 0);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 3, 1);
            material.diffuseTexture.bind();
        } else {
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 3, 0);
        }
        if (material.specularTexture != null) {
            glActiveTexture(GL_TEXTURE1);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 4, 1);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 5, 1);
            material.specularTexture.bind();
        } else {
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 5, 0);
        }
        if (material.bumpMap != null) {
            glActiveTexture(GL_TEXTURE2);
            glEnable(GL_TEXTURE_2D);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 6, 2);
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 7, 1);
            material.bumpMap.bind();
        } else {
            glUniform1i(MATERIAL_BASE_UNIFORM_LOCATION + 7, 0);
        }

        Color ambient = material.ambientColor;
        glUniform3f(MATERIAL_BASE_UNIFORM_LOCATION + 10,
                ambient.getRed() / 255F,
                ambient.getGreen() / 255F,
                ambient.getBlue() / 255F);
        Color diffuse = material.diffuseColor;
        glUniform3f(MATERIAL_BASE_UNIFORM_LOCATION + 11,
                diffuse.getRed() / 255F,
                diffuse.getGreen() / 255F,
                diffuse.getBlue() / 255F);
        Color specular = material.specularColor;
        glUniform3f(MATERIAL_BASE_UNIFORM_LOCATION + 12,
                specular.getRed() / 255F,
                specular.getGreen() / 255F,
                specular.getBlue() / 255F);
        glUniform1f(MATERIAL_BASE_UNIFORM_LOCATION + 13, 128 - material.shininess + 1);
        glUniform1f(MATERIAL_BASE_UNIFORM_LOCATION + 14, material.transparency);
    }

    @Override
    public int getShaderHandle() {
        try {
            return handle == 0 ? handle = ShaderBinder.loadShaderPair(
                    getSystemResourceAsStream(VERTEX_SHADER_LOCATION),
                    getSystemResourceAsStream(FRAGMENT_SHADER_LOCATION)) : handle;
        } catch (IOException e) {
            return handle = -1;
        }
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        String name;
        switch (attribute) {
            case VERTEX:
                name = "a_Vertex";
                break;
            case NORMAL:
                name = "a_Normal";
                break;
            case UV:
                name = "a_UV";
                break;
            case TANGENT:
                name = "a_Tangent";
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return glGetAttribLocation(getShaderHandle(), name);
    }
}
