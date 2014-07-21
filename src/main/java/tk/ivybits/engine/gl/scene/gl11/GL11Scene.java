package tk.ivybits.engine.gl.scene.gl11;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.camera.SimpleCamera;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.node.ISceneGraph;
import tk.ivybits.engine.scene.node.SceneChangeAdapter;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.PriorityQueue;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.scene.IDrawContext.ALPHA_TESTING;

public class GL11Scene implements IScene {
    GL11DrawContext drawContext = new GL11DrawContext(this);
    PriorityQueue<PriorityComparableDrawable> tracker = new PriorityQueue<>(1, PriorityComparableDrawable.COMPARATOR);
    private Projection proj = new Projection();
    private ICamera camera = new SimpleCamera(proj);

    private int viewWidth;
    private int viewHeight;

    private ISceneGraph sceneGraph;

    public GL11Scene(int viewWidth, int viewHeight, ISceneGraph sceneGraph) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.sceneGraph = sceneGraph;

        sceneGraph.addSceneChangeListener(new SceneChangeAdapter() {
            @Override
            public void actorAdded(IActor actor) {
                IDrawable draw = actor.createDrawable(drawContext);
                tracker.add(new PriorityComparableDrawable(actor, draw, draw.priority()));
            }

            @Override
            public void actorRemoved(IActor actor) {
                Iterator<PriorityComparableDrawable> i = tracker.iterator();
                while (i.hasNext()) {
                    PriorityComparableDrawable pcb = i.next();
                    if (pcb == actor) {
                        i.remove();
                        break;
                    }
                }
            }
        }, true);
    }

    @Override
    public ISceneGraph getSceneGraph() {
        return sceneGraph;
    }

    @Override
    public IDrawContext getDrawContext() {
        return drawContext;
    }

    @Override
    public ICamera getCamera() {
        return camera;
    }

    @Override
    public void setViewportSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_LIGHT1);

        loadBuffer(.5f, .5f, 1, 0);
        glLight(GL_LIGHT0, GL_POSITION, buffer);
        loadBuffer(.5f, .5f, 1, 1);
        glLight(GL_LIGHT0, GL_SPECULAR, buffer);
        loadBuffer(1, 1, 1, 1);
        glLight(GL_LIGHT0, GL_DIFFUSE, buffer);
        loadBuffer(1, 0, .5f, 0);
        glLight(GL_LIGHT1, GL_POSITION, buffer);
        loadBuffer(.5f, .5f, .5f, 1);
        glLight(GL_LIGHT1, GL_DIFFUSE, buffer);
        loadBuffer(1, 1, 1, 1);
        glLight(GL_LIGHT1, GL_SPECULAR, buffer);

        if (drawContext.isEnabled(ALPHA_TESTING)) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_FRONT);
            for (PriorityComparableDrawable entity : tracker) {
                if (!entity.draw.isTransparent()) continue;
                setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
            glCullFace(GL_BACK);
            for (PriorityComparableDrawable entity : tracker) {
                if (!entity.draw.isTransparent()) continue;
                setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : tracker) {
                if (entity.draw.isTransparent()) continue;
                setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
        } else {
            for (PriorityComparableDrawable entity : tracker) {
                setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
        }
    }

    private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    private void loadBuffer(float... data) {
        buffer.rewind();
        buffer.put(data);
        buffer.flip();
    }

    public void useMaterial(Material material) {
        glEnable(GL_TEXTURE_2D);
        if (material.diffuseTexture != null) material.diffuseTexture.bind();

        glColor4f(1, 1, 1, material.transparency);

        Color diffuse = material.diffuseColor;
        loadBuffer(diffuse.getRed() / 255f, diffuse.getGreen() / 255f, diffuse.getBlue() / 255f, 1);
        glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, buffer);

        Color ambient = material.ambientColor;
        loadBuffer(ambient.getRed() / 255f, ambient.getGreen() / 255f, ambient.getBlue() / 255f, 1);
        glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, buffer);

        Color specular = material.specularColor;
        loadBuffer(specular.getRed() / 255f, specular.getGreen() / 255f, specular.getBlue() / 255f, 1);
        glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, buffer);
        glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, material.shininess);
    }

    public void setProjection(Projection proj) {
        Matrix4f model = proj.getModelMatrix();
        Matrix4f view = proj.getViewMatrix();
        Matrix4f projection = proj.getProjectionMatrix();

        glMatrixMode(GL_PROJECTION);
        buffer.clear();
        projection.store(buffer);
        buffer.flip();
        glLoadMatrix(buffer);

        glMatrixMode(GL_MODELVIEW);
        buffer.clear();
        Matrix4f modelview = Matrix4f.mul(view, model, null);
        modelview.store(buffer);
        buffer.flip();
        glLoadMatrix(buffer);
    }
}
