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

package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.io.IO;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RawRenderShader extends BaseShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private static final String VERTEX_SHADER_SOURCE;

    static {
        try {
            VERTEX_SHADER_SOURCE = IO.readString(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
        } catch (IOException e) {
            throw new OpenGLException(e);
        }
    }

    public RawRenderShader(IScene scene, HashMap<FrameBuffer, Matrix4f> shadowMapFBO) {
        super(new HashMap<ProgramType, List<String>>() {{
            put(ProgramType.VERTEX_SHADER, Arrays.asList(VERTEX_SHADER_SOURCE));
        }}, scene, shadowMapFBO);
    }
}
