package tk.ivybits.engine.scene.light.flare;

import org.lwjgl.BufferUtils;
import tk.ivybits.engine.gl.texture.TextureLoader;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.model.IGeometry;

import javax.imageio.ImageIO;
import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class LensFlare extends AbstractActor implements IDrawable {
    protected static final String FLARE_MEDIA_BASE = "tk/ivybits/engine/gl/media/flare/";
    protected int bigGlowId, streaksId, glowId, haloId;

    protected class Flare {
        float r, g, b, a;
        float scale;
        float distance;
        int texture;

        public Flare color(float r, float g, float b, float a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
            return this;
        }

        public Flare scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Flare distance(float distance) {
            this.distance = distance;
            return this;
        }

        public Flare texture(int texture) {
            this.texture = texture;
            return this;
        }
    }

    Flare[] flares;

    public LensFlare() {
        try {
            bigGlowId = TextureLoader.getSystemTexture(FLARE_MEDIA_BASE + "default_big_glow.bmp", GL_TEXTURE_2D, GL_LINEAR, false);
            streaksId = TextureLoader.getSystemTexture(FLARE_MEDIA_BASE + "default_streaks.bmp", GL_TEXTURE_2D, GL_LINEAR, false);
            glowId = TextureLoader.getSystemTexture(FLARE_MEDIA_BASE + "default_glow.bmp", GL_TEXTURE_2D, GL_LINEAR, false);
            haloId = TextureLoader.getSystemTexture(FLARE_MEDIA_BASE + "default_halo.bmp", GL_TEXTURE_2D, GL_LINEAR, false);
            flares = new Flare[]{
                    new Flare().texture(glowId).color(.9f, .6f, .4f, .5f).scale(.6f).distance(.1f), // 20%
                    new Flare().texture(haloId).color(0.8f, 0.5f, 0.6f, 0.5f).scale(1.7f).distance(0.15f), // 30%
                    new Flare().texture(haloId).color(0.9f, 0.2f, 0.1f, 0.5f).scale(0.83f).distance(0.175f), // 35%
                    new Flare().texture(haloId).color(0.7f, 0.7f, 0.4f, 0.5f).scale(1.6f).distance(0.285f), // 57%
                    new Flare().texture(glowId).color(0.9f, 0.9f, 0.2f, 0.5f).scale(0.8f).distance(0.2755f), // 55.1%
                    new Flare().texture(glowId).color(0.93f, 0.82f, 0.73f, 0.5f).scale(1.0f).distance(0.4775f), // 95.5%
                    new Flare().texture(glowId).color(0.7f, 0.6f, 0.5f, 0.5f).scale(1.4f).distance(0.49f), // 98%
                    new Flare().texture(glowId).color(0.7f, 0.8f, 0.3f, 0.5f).scale(1.8f).distance(0.65f), // 98%
                    new Flare().texture(glowId).color(0.4f, 0.3f, 0.2f, 0.5f).scale(1.4f).distance(0.63f), // 126%
                    new Flare().texture(haloId).color(0.7f, 0.5f, 0.5f, 0.5f).scale(1.4f).distance(0.8f), // 160%
                    new Flare().texture(glowId).color(0.8f, 0.5f, 0.1f, 0.5f).scale(0.6f).distance(0.7825f), // 156.5%
                    new Flare().texture(haloId).color(0.5f, 0.5f, 0.7f, 0.5f).scale(1.7f).distance(1f), // 200%
                    new Flare().texture(glowId).color(0.4f, 0.1f, 0.9f, 0.5f).scale(2.0f).distance(0.975f), // 200%
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void drawFlare(ICamera camera, int texture, float r, float g, float b, float a, Vector3f pt, float scale) {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glPushMatrix();
        glTranslatef(pt.x, pt.y, pt.z);
        glRotatef(-camera.yaw(), 0.0f, 1.0f, 0.0f);
        glRotatef(-camera.pitch(), 1.0f, 0.0f, 0.0f);
        glBindTexture(GL_TEXTURE_2D, texture);
        glColor4f(r, g, b, a);

        glBegin(GL_TRIANGLE_STRIP);
        glTexCoord2f(0.0f, 0.0f);
        glVertex2f((pt.x - scale), (pt.z - scale));
        glTexCoord2f(0.0f, 1.0f);
        glVertex2f((pt.x - scale), (pt.z + scale));
        glTexCoord2f(1.0f, 0.0f);
        glVertex2f((pt.x + scale), (pt.z - scale));
        glTexCoord2f(1.0f, 1.0f);
        glVertex2f((pt.x + scale), (pt.z + scale));
        glEnd();
        glPopMatrix();
        glPopAttrib();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public IDrawable createDrawable(IDrawContext context) {
        return this;
    }

    @Override
    public void draw(IScene scene) {
        ICamera camera = scene.getCamera();
        Vector3f lightPosition = position();
        Vector3f cameraPosition = camera.position();

        if (camera.isSphereInFrustum(position(), 1.0f)) {
            Vector3f vLightSourceToCamera = new Vector3f(cameraPosition);
            vLightSourceToCamera.sub(lightPosition);

            float magnitude = vLightSourceToCamera.length();

            Vector3f ptIntersect = camera.direction();

            ptIntersect.set(ptIntersect.x * magnitude, ptIntersect.y * magnitude, ptIntersect.z * magnitude);
            ptIntersect.add(cameraPosition);

            Vector3f vLightSourceToIntersect = new Vector3f(ptIntersect);
            vLightSourceToIntersect.sub(lightPosition);

            magnitude = vLightSourceToIntersect.length();
            vLightSourceToIntersect.normalize();

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);

            if (!camera.isOccluded(lightPosition)) {
                drawFlare(camera, bigGlowId, 0.60f, 0.60f, 0.8f, 1.0f, lightPosition, 16 * 5);
                drawFlare(camera, streaksId, 0.60f, 0.60f, 0.8f, 1.0f, lightPosition, 8 * 5);
                drawFlare(camera, glowId, 0.8f, 0.8f, 1.0f, 0.5f, lightPosition, 3.5f * 5);

                for (Flare flare : flares) {
                    Vector3f pt = new Vector3f(vLightSourceToIntersect);
                    float d = magnitude * flare.distance;
                    pt.set(pt.x * d, pt.y * d, pt.z * d);
                    pt.add(lightPosition);
                    drawFlare(camera, flare.texture, flare.r, flare.g, flare.b, flare.a, pt, flare.scale * 15);
                }
            }
            glDisable(GL_BLEND);
            glEnable(GL_DEPTH_TEST);
            glDisable(GL_TEXTURE_2D);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }
}
