package tk.ivybits.engine.gl.texture;

import java.awt.image.BufferedImage;

import static tk.ivybits.engine.gl.GL.*;

public class CubeTexture extends Texture {
    private Texture[] faces;

    private static Texture get(int target, BufferedImage image) {
        return new Texture(target, image)
                .setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST)
                .setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    }

    public CubeTexture(BufferedImage xpos, BufferedImage xneg, BufferedImage ypos, BufferedImage yneg, BufferedImage zpos, BufferedImage zneg) {
        super(GL_TEXTURE_CUBE_MAP);

        bind();

        faces = new Texture[]{
                get(GL_TEXTURE_CUBE_MAP_POSITIVE_X, xpos),
                get(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, xneg),
                get(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, ypos),
                get(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, yneg),
                get(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, zpos),
                get(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, zneg)
        };

        setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
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
