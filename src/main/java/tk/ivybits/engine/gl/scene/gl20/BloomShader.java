package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.gl.shader.AbstractShader;
import tk.ivybits.engine.gl.shader.Program;
import tk.ivybits.engine.scene.model.node.Material;

import static tk.ivybits.engine.gl.GL.*;

public class BloomShader {
/*    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.v.glsl";
    private static final int INTENSITY_UNIFORM_LOCATION = 0;
    private static final int SAMPLE_UNIFORM_LOCATION = 1;
    private Program shader;
    private float intensity;
    private int sampleCount;

    public BloomShader() {
        setBloomIntensity(0.15f);
        setSampleCount(4);
    }

    public BloomShader setBloomIntensity(float intensity) {
        this.intensity = intensity;
        glUseProgramObjectARB(getShaderHandle());
        glUniform1fARB(INTENSITY_UNIFORM_LOCATION, intensity);
        glUseProgramObjectARB(0);
        return this;
    }

    public BloomShader setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
        glUseProgramObjectARB(getShaderHandle());
        glUniform1iARB(SAMPLE_UNIFORM_LOCATION, sampleCount);
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
    }*/
}
