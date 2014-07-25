package tk.ivybits.engine.gl.scene.gl11;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.node.ISceneGraph;
import tk.ivybits.engine.scene.node.SceneChangeAdapter;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.PriorityQueue;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.scene.IDrawContext.ALPHA_TESTING;

public class GL20Scene implements IScene {
    GL11DrawContext drawContext = new GL11DrawContext(this);
    PriorityQueue<PriorityComparableDrawable> tracker = new PriorityQueue<>(1, PriorityComparableDrawable.COMPARATOR);

    private int viewWidth;
    private int viewHeight;

    private ISceneGraph sceneGraph;
    private Matrix4f viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    public GL20Scene(int viewWidth, int viewHeight, ISceneGraph sceneGraph) {
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
                setModelView(entity.wrapped.getTransform());
                entity.draw.draw(this);
            }
            glCullFace(GL_BACK);
            for (PriorityComparableDrawable entity : tracker) {
                if (!entity.draw.isTransparent()) continue;
                setModelView(entity.wrapped.getTransform());
                entity.draw.draw(this);
            }
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : tracker) {
                if (entity.draw.isTransparent()) continue;
                setModelView(entity.wrapped.getTransform());
                entity.draw.draw(this);
            }
        } else {
            for (PriorityComparableDrawable entity : tracker) {
                setModelView(entity.wrapped.getTransform());
                entity.draw.draw(this);
            }
        }
    }

    @Override
    public void setViewTransform(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    @Override
    public void setProjectionTransform(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        glMatrixMode(GL_PROJECTION);
        buffer.clear();
        projectionMatrix.store(buffer);
        buffer.flip();
        glLoadMatrix(buffer);
    }

    @Override
    public Matrix4f getProjectionTransform() {
        return projectionMatrix;
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

    private void setModelView(Matrix4f modelMatrix) {
        setProjectionTransform(projectionMatrix);
        glMatrixMode(GL_MODELVIEW);
        buffer.clear();
        Matrix4f modelview = Matrix4f.mul(viewMatrix, modelMatrix, null);
        modelview.store(buffer);
        buffer.flip();
        glLoadMatrix(buffer);
    }
}
