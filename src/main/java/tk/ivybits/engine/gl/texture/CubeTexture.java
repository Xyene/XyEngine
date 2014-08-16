package tk.ivybits.engine.gl.texture;

import java.awt.image.BufferedImage;

import static tk.ivybits.engine.gl.GL.*;

public class CubeTexture extends Texture {
    private Texture[] faces;

    public CubeTexture(BufferedImage xpos, BufferedImage xneg, BufferedImage ypos, BufferedImage yneg, BufferedImage zpos, BufferedImage zneg) {
        super(GL_TEXTURE_CUBE_MAP);

        bind();

        faces = new Texture[]{
                new Texture(GL_TEXTURE_CUBE_MAP_POSITIVE_X, xpos),
                new Texture(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, xneg),
                new Texture(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, ypos),
                new Texture(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, yneg),
                new Texture(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, zpos),
                new Texture(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, zneg)
        };

        setParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        setParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        setParameter(GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        unbind();
    }

    @Override
    public void destroy() {
        for(Texture face : faces)
            face.destroy();
        super.destroy();
    }
}
