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

package tk.ivybits.engine.gl.scene.gl20.aa;

import tk.ivybits.engine.gl.texture.Framebuffer;

import static tk.ivybits.engine.gl.GL.*;

public class MSAAFBO {
    private int samples;
    private int fbo;
    private int colorBuffer;
    private int depthBuffer;
    private int width, height;

    public MSAAFBO(int width, int height, int samples) {
        this.width = width;
        this.height = height;
        this.samples = samples;

        create();
    }

    private void create() {
        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);

        createAttachments();
        bindAttachments();

        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void blit(int id) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);

        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    public void blit() {
        blit(0);
    }

    public void blit(Framebuffer where) {
        blit(where.id());
    }

    private void bindAttachments() {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, colorBuffer, 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
    }

    private void createAttachments() {
        colorBuffer = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, colorBuffer);
        glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, samples, GL_RGBA8, width, height, false);

        depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, GL_DEPTH_COMPONENT16, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    private void updateAttachments() {
        glDeleteTextures(colorBuffer);
        glDeleteRenderbuffers(depthBuffer);
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        createAttachments();
        bindAttachments();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void destroy() {
        glDeleteTextures(colorBuffer);
        glDeleteRenderbuffers(depthBuffer);
        glDeleteFramebuffers(fbo);
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
        System.out.println(samples);
        destroy();
        create();
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        updateAttachments();
    }

    public void bindFramebuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
    }

    public void unbindFramebuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getFramebufferId() {
        return colorBuffer;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
