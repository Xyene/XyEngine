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

package tk.ivybits.engine.gl.texture;

import static tk.ivybits.engine.gl.GL.*;

public class RenderBuffer {
    private final int target;
    private int internalTarget;
    private int width;
    private int height;
    private final int samples;
    private int buffer;
    private boolean bound = false;

    public RenderBuffer(int target, int internalTarget, int width, int height, int samples) {
        this.target = target;
        this.internalTarget = internalTarget;
        this.width = width;
        this.height = height;
        this.samples = samples;
        buffer = glGenRenderbuffers();
        resize(width, height);
    }

    public int target() {
        return target;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void bind() {
        bound = true;
        glBindRenderbuffer(GL_RENDERBUFFER, buffer);
    }

    public void unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        bound = false;
    }

    public int id() {
        return buffer;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        boolean b = bound;
        if (!b) bind();
        if (samples > 0)
            glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, internalTarget, width, height);
        else
            glRenderbufferStorage(GL_RENDERBUFFER, internalTarget, width, height);
        if (!b) unbind();
    }

    public void destroy() {
        glDeleteRenderbuffers(buffer);
    }

    public boolean isBound() {
        return bound;
    }

    public static RenderBuffer newDepthBuffer(int width, int height) {
        return new RenderBuffer(GL_DEPTH_ATTACHMENT, GL_DEPTH_COMPONENT, width, height, 0);
    }

    public static RenderBuffer newMultisampledDepthBuffer(int width, int height, int samples) {
        return new RenderBuffer(GL_DEPTH_ATTACHMENT, GL_DEPTH_COMPONENT, width, height, samples);
    }
}
