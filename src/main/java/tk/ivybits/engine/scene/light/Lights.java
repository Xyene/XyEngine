package tk.ivybits.engine.scene.light;

import tk.ivybits.engine.util.IBuilder;

import java.awt.*;

public class Lights {
    public static SpotLightBuilder newSpotLight() {
        return new SpotLightBuilder();
    }

    public static DirectionalLightBuilder newDirectionalLight() {
        return new DirectionalLightBuilder();
    }

    public static class SpotLightBuilder implements IBuilder<SpotLight> {
        private Color ambientColor = Color.BLACK, diffuseColor = Color.WHITE, specularColor = Color.WHITE;
        private float x, y, z;
        private float dx, dy, dz;
        private float cutoff;
        private float intensity = 1;

        @Override
        public SpotLight build() {
            SpotLight spotLight = new SpotLight();
            spotLight.setAmbientColor(ambientColor);
            spotLight.setDiffuseColor(diffuseColor);
            spotLight.setSpecularColor(specularColor);
            spotLight.setPosition(x, y, z);
            spotLight.setDirection(dx, dy, dz);
            spotLight.setCutoff(cutoff);
            spotLight.setIntensity(intensity);
            return spotLight;
        }

        public SpotLightBuilder setPosition(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public SpotLightBuilder setDirection(float dx, float dy, float dz) {
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            return this;
        }

        public SpotLightBuilder directAt(float dx, float dy, float dz) {
            return setDirection(x - dx, y - dy, z - dz);
        }

        public SpotLightBuilder setAmbientColor(Color ambientColor) {
            this.ambientColor = ambientColor;
            return this;
        }

        public SpotLightBuilder setDiffuseColor(Color diffuseColor) {
            this.diffuseColor = diffuseColor;
            return this;
        }

        public SpotLightBuilder setSpecularColor(Color specularColor) {
            this.specularColor = specularColor;
            return this;
        }

        public SpotLightBuilder setCutoff(float cutoff) {
            this.cutoff = cutoff;
            return this;
        }

        public SpotLightBuilder setIntensity(float intensity) {
            this.intensity = intensity;
            return this;
        }
    }

    public static class DirectionalLightBuilder implements IBuilder<PointLight> {
        private Color ambientColor = Color.BLACK, diffuseColor = Color.WHITE, specularColor = Color.WHITE;
        private float x, y, z;
        private float intensity = 1;

        @Override
        public PointLight build() {
            PointLight light = new PointLight();
            light.setAmbientColor(ambientColor);
            light.setDiffuseColor(diffuseColor);
            light.setSpecularColor(specularColor);
            light.setPosition(x, y, z);
            light.setIntensity(intensity);
            return light;
        }

        public DirectionalLightBuilder setPosition(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public DirectionalLightBuilder setAmbientColor(Color ambientColor) {
            this.ambientColor = ambientColor;
            return this;
        }

        public DirectionalLightBuilder setDiffuseColor(Color diffuseColor) {
            this.diffuseColor = diffuseColor;
            return this;
        }

        public DirectionalLightBuilder setSpecularColor(Color specularColor) {
            this.specularColor = specularColor;
            return this;
        }

        public DirectionalLightBuilder setIntensity(float intensity) {
            this.intensity = intensity;
            return this;
        }
    }
}
