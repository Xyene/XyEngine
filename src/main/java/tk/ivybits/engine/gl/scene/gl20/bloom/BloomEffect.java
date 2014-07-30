package tk.ivybits.engine.gl.scene.gl20.bloom;

import org.lwjgl.input.Keyboard;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.ProgramType;

import static tk.ivybits.engine.gl.GL.*;

public class BloomEffect {
    private static final int LOD = 3;
    private static final float REDUCTION_FACTOR = 0.6f;

    private BloomFBO input, output;
    public Program thresholdShader, vblur, hblur;

    private BloomFBO[] swap = new BloomFBO[LOD];
    private BloomFBO[] blur = new BloomFBO[LOD];

    public BloomEffect(int width, int height) {
        output = new BloomFBO(width, height, GL_NEAREST, false);
        input = new BloomFBO(width, height, GL_NEAREST, true);

        int cwidth = width, cheight = height;
        for (int i = 0; i < LOD; i++) {
            cwidth *= REDUCTION_FACTOR;
            cheight *= REDUCTION_FACTOR;
            blur[i] = new BloomFBO(cwidth, cheight, GL_LINEAR, false);
            swap[i] = new BloomFBO(cwidth, cheight, GL_LINEAR, false);
        }

        thresholdShader = Program.builder()
                .loadSystemShader(ProgramType.FRAGMENT, "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.f.glsl")
                .loadSystemShader(ProgramType.VERTEX, "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.v.glsl")
                .build();

        vblur = Program.builder()
                .loadSystemShader(ProgramType.FRAGMENT, "tk/ivybits/engine/gl/shader/bloom/blur.f.glsl")
                .loadSystemShader(ProgramType.VERTEX, "tk/ivybits/engine/gl/shader/bloom/blur.v.glsl")
                .define("VERTICAL")
                .build();
        hblur = Program.builder()
                .loadSystemShader(ProgramType.FRAGMENT, "tk/ivybits/engine/gl/shader/bloom/blur.f.glsl")
                .loadSystemShader(ProgramType.VERTEX, "tk/ivybits/engine/gl/shader/bloom/blur.v.glsl")
                .define("HORIZONTAL")
                .build();
    }

    public void resize(int width, int height) {
        output.resize(width, height);
        input.resize(width, height);
        int cwidth = width, cheight = height;
        for (int i = 0; i < LOD; i++) {
            cwidth *= REDUCTION_FACTOR;
            cheight *= REDUCTION_FACTOR;
            blur[i].resize(cwidth, cheight);
            swap[i].resize(cwidth, cheight);
        }
    }

    public void destroy() {
        output.destroy();
        input.destroy();
        for (int i = 0; i < LOD; i++) {
            blur[i].destroy();
            swap[i].destroy();
        }
        thresholdShader.destroy();
    }

    public BloomFBO getInputBuffer() {
        return input;
    }

    public BloomFBO getOutputBuffer() {
        return output;
    }

    private void drawDeviceQuad(BloomFBO fbo) {
        int viewWidth = fbo.width();
        int viewHeight = fbo.height();
        glLoadIdentity();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, viewWidth, viewHeight);
        glOrtho(0, viewWidth, 0, viewHeight, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(0, 1);
        glVertex2f(0, viewHeight);
        glTexCoord2f(1, 1);
        glVertex2f(viewWidth, viewHeight);
        glTexCoord2f(1, 0);
        glVertex2f(viewWidth, 0);
        glEnd();
    }

    public void process() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);


        blur[0].bindFramebuffer();
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_TEXTURE_2D);
        thresholdShader.attach();
        input.bindTexture();
        drawDeviceQuad(blur[0]);
        input.unbindTexture();
        thresholdShader.detach();
        blur[0].unbindFramebuffer();

        blur[0].bindTexture();
        for (int i = 1; i < LOD; i++) {
            blur[i].bindFramebuffer();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            drawDeviceQuad(blur[i]);
            blur[i].unbindFramebuffer();
        }
        blur[0].unbindTexture();

        for (int i = 0; i < LOD; i++) {
            swap[i].bindFramebuffer();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            vblur.attach();

            glActiveTexture(GL_TEXTURE0);
            glEnable(GL_TEXTURE_2D);
            blur[i].bindTexture();
            drawDeviceQuad(swap[i]);
            blur[i].unbindTexture();
            swap[i].unbindFramebuffer();

            vblur.detach();

            blur[i].bindFramebuffer();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            hblur.attach();

            glActiveTexture(GL_TEXTURE0);
            glEnable(GL_TEXTURE_2D);
            swap[i].bindTexture();
            drawDeviceQuad(blur[i]);
            swap[i].unbindTexture();
            blur[i].unbindFramebuffer();

            hblur.detach();
        }

        output.bindFramebuffer();
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        input.bindTexture();
        drawDeviceQuad(output);
        input.bindTexture();

        for (int i = 0; i < LOD; i++) {
            blur[i].bindTexture();
            drawDeviceQuad(output);
            blur[i].bindTexture();
        }

        glDisable(GL_BLEND);
        output.unbindFramebuffer();

        glPopAttrib();
    }
}
