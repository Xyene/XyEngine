package tk.ivybits.engine.scene;

import tk.ivybits.engine.gl.shader.IGeometryShader;
import tk.ivybits.engine.gl.shader.IShader;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.light.AtmosphereModel;
import tk.ivybits.engine.scene.model.IGeometry;

public interface IScene {
    IActor createActor(IGeometry model);

    void destroyActor(IActor actor);

    void revalidateActor(IActor actor);

    AtmosphereModel getAtmosphere();

    IDrawContext getDrawContext();

    ICamera getCamera();

    void setDefaultShader(IGeometryShader shader);

    void setRenderedShader(IShader shader);

    void setViewportSize(int width, int height);

    void draw();

    IShader getRenderedShader();

    IGeometryShader getDefaultShader();
}
