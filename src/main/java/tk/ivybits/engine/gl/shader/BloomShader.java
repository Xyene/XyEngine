package tk.ivybits.engine.gl.shader;

import tk.ivybits.engine.scene.model.node.Material;

import java.io.IOException;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.lwjgl.opengl.ARBShaderObjects.*;

public class BloomShader extends AbstractShader {
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/pixel_bloom.v.glsl";
    private static final int INTENSITY_UNIFORM_LOCATION = 0;
    private static final int SAMPLE_UNIFORM_LOCATION = 1;
    private int handle;
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
            return handle == 0 ? handle = ShaderBinder.loadShaderPair(
                    getSystemResourceAsStream(VERTEX_SHADER_LOCATION),
                    getSystemResourceAsStream(FRAGMENT_SHADER_LOCATION)) : handle;
        } catch (IOException e) {
            return handle = -1;
        }
    }

    @Override
    public void useMaterial(Material material) {
        throw new UnsupportedOperationException("cannot apply a post-render material");
    }
}
