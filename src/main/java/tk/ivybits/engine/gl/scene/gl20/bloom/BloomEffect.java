/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.gl.scene.gl20.bloom;

import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.texture.Framebuffer;
import tk.ivybits.engine.gl.texture.RenderBuffer;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.IScene;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.ProgramType.*;
import static tk.ivybits.engine.scene.IDrawContext.Capability.Key.*;

public class BloomEffect {
    private final Program toneMap;
    private Framebuffer input, output;
    public Program thresholdShader, vblur, hblur;

    private Framebuffer[] swap;
    private Framebuffer[] blur;
    private final IScene scene;
    private int width;
    private int height;
    private final int lod;
    private final float reductionFactor;
//    private IDrawable quad;

    private Framebuffer createBuffer(int filter, int width, int height) {
        Framebuffer bloom = new Framebuffer(width, height);
        bloom.attach(new Texture(GL_TEXTURE_2D, GL_RGB, width, height)
                        .setParameter(GL_TEXTURE_MAG_FILTER, filter)
                        .setParameter(GL_TEXTURE_MIN_FILTER, filter)
                        .setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
                        .setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
        );
        return bloom;
    }

    public BloomEffect(IScene scene, int width, int height, int lod, float reductionFactor) {
        this.scene = scene;
        this.width = width;
        this.height = height;
        this.lod = lod;
        this.reductionFactor = reductionFactor;

        swap = new Framebuffer[lod];
        blur = new Framebuffer[lod];

        create();

//        ITesselator tess = scene.getDrawContext().createTesselator(ITesselator.UV_BUFFER, GL_TRIANGLE_FAN);
//        tess.texture(0, 0);
//        tess.vertex(0, 0,0);
//        tess.texture(0, 1);
//        tess.vertex(0, 0, 1);
//        tess.texture(1, 1);
//        tess.vertex(1, 0,1);
//        tess.texture(1, 0);
//        tess.vertex(1, 0,0);
//
//        quad = tess.create();

        thresholdShader = Program.builder()
                .loadPackagedShader(FRAGMENT_SHADER, "tk/ivybits/engine/gl/shader/bloom/threshold.f.glsl")
                .loadPackagedShader(VERTEX_SHADER, "tk/ivybits/engine/gl/shader/bloom/threshold.v.glsl")
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
        toneMap = Program.builder()
                .loadPackagedShader(FRAGMENT_SHADER, "tk/ivybits/engine/gl/shader/bloom/tone_map.f.glsl")
                .loadPackagedShader(VERTEX_SHADER, "tk/ivybits/engine/gl/shader/bloom/tone_map.v.glsl")
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
            blur[i] = createBuffer(GL_LINEAR, cwidth, cheight);
            swap[i] = createBuffer(GL_LINEAR, cwidth, cheight);
            cwidth *= reductionFactor;
            cheight *= reductionFactor;
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

    public Framebuffer getInputBuffer() {
        return input;
    }

    public Texture getOutputBuffer() {
        return output.getTexture();
    }

    private void drawDeviceQuad(Framebuffer fbo) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, fbo.width(), fbo.height());
        glOrtho(0, 1, 0, 1, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

//        quad.draw(scene);
        glBegin(GL_TRIANGLE_FAN);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(0, 1);
        glVertex2f(0, 1);
        glTexCoord2f(1, 1);
        glVertex2f(1, 1);
        glTexCoord2f(1, 0);
        glVertex2f(1, 0);
        glEnd();
    }

    public void process() {
        glClearColor(0, 0, 0, 0);
        blur[0].bind().clear(GL_COLOR_BUFFER_BIT);

        thresholdShader.attach();
        input.getTexture().bind(0);
        drawDeviceQuad(blur[0]);
        input.getTexture().unbind();
        thresholdShader.detach();

        blur[0].unbind();

        blur[0].getTexture().bind(0);
        for (int i = 1; i < lod; i++) {
            blur[i].bind();
            drawDeviceQuad(blur[i]);
            blur[i].unbind();
        }
        blur[0].getTexture().unbind();

        for (int i = 0; i < lod; i++) {
            swap[i].bind().clear(GL_COLOR_BUFFER_BIT);

            vblur.attach();

            blur[i].getTexture().bind(0);
            drawDeviceQuad(swap[i]);
            blur[i].getTexture().unbind();
            swap[i].unbind();

            vblur.detach();

            blur[i].bind().clear(GL_COLOR_BUFFER_BIT);

            hblur.attach();

            swap[i].getTexture().bind(0);
            drawDeviceQuad(blur[i]);
            swap[i].getTexture().unbind();
            blur[i].unbind();

            hblur.detach();
        }

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
        blur[0].bind();

        for (int i = 1; i < lod; i++) {
            blur[i].getTexture().bind(0);
            drawDeviceQuad(blur[0]);
            blur[i].getTexture().unbind();
        }

        blur[0].unbind();
        glDisable(GL_BLEND);

        toneMap.attach();
        output.bind().clear(GL_COLOR_BUFFER_BIT);

        toneMap.setUniform("u_exposure", scene.getDrawContext().getOption(HDR_BLOOM_EXPOSURE));
        toneMap.setUniform("u_maxBrightness", scene.getDrawContext().getOption(HDR_BLOOM_CAP));
        toneMap.setUniform("u_bloomFactor", scene.getDrawContext().getOption(HDR_BLOOM_FACTOR));

        toneMap.setUniform("u_scene", 0);
        toneMap.setUniform("u_bloom", 1);

        input.getTexture().bind(0);
        blur[0].getTexture().bind(1);
        drawDeviceQuad(output);

        input.getTexture().unbind();
        blur[0].getTexture().unbind();

        output.unbind();
        toneMap.detach();
    }
}
