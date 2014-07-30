#version 110

varying vec2 v_UV;

uniform sampler2D u_sampler;
uniform float u_threshold;

void main() {
    float brightPassThreshold = 0.9;
    vec3 luminanceVector = vec3(0.2125, 0.7154, 0.0721);
    vec4 sample = texture2D(u_sampler, v_UV);

    float luminance = dot(luminanceVector, sample.rgb);
    luminance = max(0.0, luminance - brightPassThreshold);
    sample.rgba *= sign(luminance);
    //sample.a = 0.5;
    gl_FragColor = sample;
}