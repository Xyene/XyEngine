package tk.ivybits.engine.scene.node;

import java.awt.*;

public interface IFog {
    Color getFogColor();

    IFog setFogColor(Color fogColor);

    float getFogNear();

    IFog setFogNear(float fogNear);

    float getFogFar();

    IFog setFogFar(float fogFar);

    IFog setEnabled(boolean flag);

    boolean isEnabled();
}
