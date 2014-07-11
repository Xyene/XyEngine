#version 130

uniform sampler2D u_shadowMap;

varying vec2 v_UV;

void main(void)
{
    float depth = texture(u_shadowMap, v_UV).x;
    depth = 1.0 - (1.0 - depth) * 50.0;
    gl_FragColor = vec4(depth, depth, depth, 1.0);
}
