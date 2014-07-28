package tk.ivybits.engine.gl.scene.gl20.bloom;

import tk.ivybits.engine.gl.Program;

import static tk.ivybits.engine.gl.GL.*;

public class BloomEffect {
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.v.glsl";
    private BloomFBO input, output;
    public Program thresholdShader;

    private BloomFBO[] pass0 = new BloomFBO[4];
    private BloomFBO[] pass1 = new BloomFBO[4];

    public BloomEffect(int width, int height) {
        output = new BloomFBO(width, height, GL_NEAREST, false);
        input = new BloomFBO(width, height, GL_NEAREST, true);

        output.resize(width, height);
        input.resize(width, height);
        for (int i = 0; i < 4; i++) {
            pass0[i] = new BloomFBO(width, height, GL_LINEAR, false);
            width >>= 1;
            height >>= 1;
            pass1[i] = new BloomFBO(width, height, GL_LINEAR, false);
        }

        thresholdShader = Program.builder()
                .loadSystemShader(Program.ShaderType.FRAGMENT, FRAGMENT_SHADER_LOCATION)
                .loadSystemShader(Program.ShaderType.VERTEX, VERTEX_SHADER_LOCATION)
                .build();
        thresholdShader.setUniform("u_sampler", 0);
        thresholdShader.setUniform("u_threshold", 0.5f);
    }

    public void resize(int width, int height) {
        System.out.println("Resize");
        output.resize(width, height);
        input.resize(width, height);
        for (int i = 0; i < 4; i++) {
            pass0[i].resize(width, height);
            width >>= 1;
            height >>= 1;
            pass1[i].resize(width, height);
        }
    }

    public void destroy() {
        output.destroy();
        input.destroy();
        thresholdShader.destroy();
        for (int i = 0; i < 4; i++) {
            pass0[i].destroy();
            pass1[i].destroy();
        }
    }

    public BloomFBO getInputBuffer() {
        return input;
    }

    public BloomFBO getOutputBuffer() {
        return output;
    }

    private void drawDeviceQuad() {
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(-1, -1);
        glTexCoord2f(1, 0);
        glVertex2f(1, -1);
        glTexCoord2f(1, 1);
        glVertex2i(1, 1);
        glTexCoord2f(0, 1);
        glVertex2f(-1, 1);
        glEnd();
    }

    public void process() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);


        pass0[0].bindFramebuffer();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_TEXTURE_2D);
        thresholdShader.attach();
        input.bindTexture();
        drawDeviceQuad();
        input.unbindTexture();
        thresholdShader.detach();
        
        pass0[0].unbindFramebuffer();

       // input.blit(output);


        output.bindFramebuffer();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        input.bindTexture();
        drawDeviceQuad();
        input.unbindTexture();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);


        pass0[0].bindTexture();
        drawDeviceQuad();
        pass0[0].unbindTexture();

        output.unbindFramebuffer();



        glPopAttrib();

//        output.unbindFramebuffer();

//        pass0[0].unbindFramebuffer();
//
//        pass0[0].bindTexture();
//
//        for (int p = 1; p < 4; p++) {
//            pass0[p].bindFramebuffer();
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//            glLoadIdentity();
//
//            glBegin(GL_QUADS);
//            glTexCoord2f(0, 0);
//            glVertex2f(-1, -1);
//            glTexCoord2f(1, 0);
//            glVertex2f(1, -1);
//            glTexCoord2f(1, 1);
//            glVertex2i(1, 1);
//            glTexCoord2f(0, 1);
//            glVertex2f(-1, 1);
//            glEnd();
//            pass0[p].unbindFramebuffer();
//        }
//
//        pass0[0].unbindTexture();
//
//        output.bindFramebuffer();
//        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        glLoadIdentity();
//
//        for (int p = 0; p < 1; p++) {
//            input.bindTexture();
//            glBegin(GL_QUADS);
//            glTexCoord2f(0, 0);
//            glVertex2f(-1, -1);
//            glTexCoord2f(1, 0);
//            glVertex2f(1, -1);
//            glTexCoord2f(1, 1);
//            glVertex2i(1, 1);
//            glTexCoord2f(0, 1);
//            glVertex2f(-1, 1);
//            input.unbindTexture();
//        }
//
//        output.unbindFramebuffer();
    }
}
