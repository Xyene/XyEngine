package tk.ivybits.engine.scene.light;

import tk.ivybits.engine.scene.Fog;

import java.util.ArrayList;
import java.util.List;

public class AtmosphereModel {
    private List<PointLight> pointLights = new ArrayList<>();
    private List<SpotLight> spotLights = new ArrayList<>();
    private Fog fog;

    public List<PointLight> getPointLights() {
        return pointLights;
    }

    public List<SpotLight> getSpotLights() {
        return spotLights;
    }

    public Fog getFog() {
        return fog;
    }

    public void setFog(Fog fog) {
        this.fog = fog;
    }
}
