#version 110

varying vec2 v_UV;

uniform sampler2D u_sampler0;
uniform sampler2D u_sampler1;
uniform sampler2D u_sampler2;
uniform sampler2D u_sampler3;
uniform sampler2D u_sampler4;

void main() {
    vec3 texel0 = texture2D(u_sampler0, v_UV).rgb;
    vec3 texel1 = texture2D(u_sampler1, v_UV).rgb;
    vec3 texel2 = texture2D(u_sampler2, v_UV).rgb;
    vec3 texel3 = texture2D(u_sampler3, v_UV).rgb;
    vec3 texel4 = texture2D(u_sampler4, v_UV).rgb;
    gl_FragColor = vec4(texel2, 1);
}