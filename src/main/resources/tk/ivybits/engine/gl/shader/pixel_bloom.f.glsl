varying vec2 v_UV;

layout(location = 0) uniform sampler2D sampler;

layout(location = 1) uniform float u_intensity;
layout(location = 2) uniform int u_samples;

void main() {
    vec4 sum = vec4(0);

    for (int x = -u_samples; x < u_samples; x++) {
        for (int y = -u_samples; y < u_samples; y++) {
            sum += texture2D(sampler, v_UV + vec2(x, y) * 0.004) * u_intensity;
        }
    }

    vec4 sample = texture2D(sampler, v_UV);

    float modifier = sample.g * 0.015; // / 150

    gl_FragColor = sum * sum * modifier + sample;
}