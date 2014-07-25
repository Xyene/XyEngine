package tk.ivybits.engine.gl.scene.gl20.bloom;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.scene.gl20.shader.AbstractShader;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.model.node.Material;

import static org.lwjgl.opengl.ARBShaderObjects.*;

public class BloomShader extends AbstractShader {
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.v.glsl";
    private static final int SAMPLER_UNIFORM_LOCATION = 0;
    private static final int INTENSITY_UNIFORM_LOCATION = 1;
    private static final int SAMPLE_UNIFORM_LOCATION = 2;
    private Program shader;
    private float intensity;
    private int sampleCount;

    public BloomShader() {
        setBloomIntensity(0.15f);
        setSampleCount(4);
        glUseProgramObjectARB(getShaderHandle());
        glUniform1fARB(shader.getUniformLocation("sampler"), 0);
        glUseProgramObjectARB(0);
    }

    public BloomShader setBloomIntensity(float intensity) {
        this.intensity = intensity;
        glUseProgramObjectARB(getShaderHandle());
        glUniform1fARB(shader.getUniformLocation("u_intensity"), intensity);
        glUseProgramObjectARB(0);
        return this;
    }

    public BloomShader setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
        glUseProgramObjectARB(getShaderHandle());
        glUniform1iARB(shader.getUniformLocation("u_samples"), sampleCount);
        glUseProgramObjectARB(0);
        return this;
    }

    public float getIntensity() {
        return intensity;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    @Override
    public int getShaderHandle() {
        try {
            return (shader == null ? shader = Program.builder()
                    .loadSystemShader(Program.ShaderType.FRAGMENT, FRAGMENT_SHADER_LOCATION)
                    .loadSystemShader(Program.ShaderType.VERTEX, VERTEX_SHADER_LOCATION)
                    .build() : shader).getId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void useMaterial(Material material) {
        throw new UnsupportedOperationException("cannot apply a post-render material");
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        return 0;
    }

    @Override
    public Program getProgram() {
        return shader;
    }

    @Override
    public void setModelTransform(Matrix4f modelMatrix) {

    }

    @Override
    public void setViewTransform(Matrix4f viewMatrix) {

    }

    @Override
    public void setProjectionTransform(Matrix4f projectionMatrix) {

    }
}
