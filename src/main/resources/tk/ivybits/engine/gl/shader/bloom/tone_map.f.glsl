
varying vec2 v_uv;

uniform sampler2D u_scene;
uniform sampler2D u_bloom;

uniform float u_bloomFactor;
uniform float u_exposure;
uniform float u_maxBrightness;

void main() {
    gl_FragColor = (texture2D(u_scene, v_uv) + texture2D(u_bloom, v_uv) * u_bloomFactor) * (u_exposure * (u_exposure / u_maxBrightness + 1.0) / (u_exposure + 1.0));
}