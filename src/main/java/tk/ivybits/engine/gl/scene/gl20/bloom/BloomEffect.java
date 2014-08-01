package tk.ivybits.engine.gl.scene.gl20.bloom;

import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.gl.texture.RenderBuffer;
import tk.ivybits.engine.gl.texture.Texture;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.ProgramType.*;

public class BloomEffect {
    private FrameBuffer input, output;
    public Program thresholdShader, vblur, hblur;

    private FrameBuffer[] swap;
    private FrameBuffer[] blur;
    private int width;
    private int height;
    private final int lod;
    private final float reductionFactor;

    private FrameBuffer createBuffer(int filter, int width, int height) {
        FrameBuffer bloom = new FrameBuffer(width, height);
        bloom.attach(new Texture(GL_TEXTURE_2D, GL_RGB, width, height)
                .setParameter(GL_TEXTURE_MAG_FILTER, filter)
                .setParameter(GL_TEXTURE_MIN_FILTER, filter)
                .setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
                .setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        );
        return bloom;
    }

    public BloomEffect(int width, int height, int lod, float reductionFactor) {
        this.width = width;
        this.height = height;
        this.lod = lod;
        this.reductionFactor = reductionFactor;

        swap = new FrameBuffer[lod];
        blur = new FrameBuffer[lod];

        create();

        thresholdShader = Program.builder()
                .loadPackagedShader(FRAGMENT_SHADER, "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.f.glsl")
                .loadPackagedShader(VERTEX_SHADER, "tk/ivybits/engine/gl/shader/bloom/bloom_threshold.v.glsl")
                .build();

        vblur = Program.builder()
                .loadPackagedShader(FRAGMENT_SHADER, "tk/ivybits/engine/gl/shader/bloom/blur.f.glsl")
                .loadPackagedShader(VERTEX_SHADER, "tk/ivybits/engine/gl/shader/bloom/blur.v.glsl")
                .define("VERTICAL")
                .build();
        hblur = Program.builder()
                .loadPackagedShader(FRAGMENT_SHADER, "tk/ivybits/engine/gl/shader/bloom/blur.f.glsl")
                .loadPackagedShader(VERTEX_SHADER, "tk/ivybits/engine/gl/shader/bloom/blur.v.glsl")
                .define("HORIZONTAL")
                .build();
    }

    /**
     * Creates the framebuffers needed for this bloom effect. Only the input buffer has a depth attachment.
     */
    private void create() {
        output = createBuffer(GL_NEAREST, width, height);
        input = createBuffer(GL_NEAREST, width, height).attach(RenderBuffer.newDepthBuffer(width, height));

        int cwidth = width, cheight = height;
        for (int i = 0; i < lod; i++) {
            cwidth *= reductionFactor;
            cheight *= reductionFactor;
            blur[i] = createBuffer(GL_LINEAR, cwidth, cheight);
            swap[i] = createBuffer(GL_LINEAR, cwidth, cheight);
        }
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        // FIXME: resize approach doesn't work
//        output.resize(width, height);
//        input.resize(width, height);
//        int cwidth = width, cheight = height;
//        for (int i = 0; i < LOD; i++) {
//            cwidth *= REDUCTION_FACTOR;
//            cheight *= REDUCTION_FACTOR;
//            blur[i].resize(cwidth, cheight);
//            swap[i].resize(cwidth, cheight);
//        }
        output.destroy();
        input.destroy();
        for (int i = 0; i < lod; i++) {
            blur[i].destroy();
            swap[i].destroy();
        }
        create();
    }

    public void destroy() {
        output.destroy();
        input.destroy();
        for (int i = 0; i < lod; i++) {
            blur[i].destroy();
            swap[i].destroy();
        }
        thresholdShader.destroy();
        vblur.destroy();
        hblur.destroy();
    }

    public FrameBuffer getInputBuffer() {
        return input;
    }

    public Texture getOutputBuffer() {
        return output.getTexture();
    }

    private void drawDeviceQuad(FrameBuffer fbo) {
        int viewWidth = fbo.width();
        int viewHeight = fbo.height();

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
        blur[0].bind();
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);

        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_TEXTURE_2D);
        thresholdShader.attach();
        input.getTexture().bind();
        drawDeviceQuad(blur[0]);
        input.getTexture().unbind();
        thresholdShader.detach();

        blur[0].unbind();

        blur[0].getTexture().bind();
        for (int i = 1; i < lod; i++) {
            blur[i].bind();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);
            drawDeviceQuad(blur[i]);
            blur[i].bind();
        }
        blur[0].getTexture().unbind();

        for (int i = 0; i < lod; i++) {
            swap[i].bind();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);

            vblur.attach();

            blur[i].getTexture().bind();
            drawDeviceQuad(swap[i]);
            blur[i].getTexture().unbind();
            swap[i].bind();

            vblur.detach();

            blur[i].bind();
            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);

            hblur.attach();

            swap[i].getTexture().bind();
            drawDeviceQuad(blur[i]);
            swap[i].getTexture().unbind();
            blur[i].bind();

            hblur.detach();
        }

        output.bind();
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT);

        glEnable(GL_BLEND);
        // Enable additive blending to blend downscaled bloom buffers
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        input.getTexture().bind();
        drawDeviceQuad(output);
        input.getTexture().bind();

        for (int i = 0; i < lod; i++) {
            blur[i].getTexture().bind();
            drawDeviceQuad(output);
            blur[i].getTexture().bind();
        }

        glDisable(GL_BLEND);
        output.unbind();
    }
}
