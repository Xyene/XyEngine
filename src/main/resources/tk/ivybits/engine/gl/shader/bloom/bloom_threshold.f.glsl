varying vec2 v_UV;

uniform sampler2D u_sampler;
uniform float u_threshold;

void main() {
  //  gl_FragColor = vec4(1, 0, 0, 0);
    vec4 texel = texture2D(u_sampler, v_UV);
    float brightness = (texel.r + texel.g + texel.b) / 3.0;
    if(brightness > 0.5) {
        gl_FragColor = vec4(texel.rgb / 5.0, 1);
    } else {
        gl_FragColor = vec4(0, 0, 0, 1);
    }
}