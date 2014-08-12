
varying vec3 v_Tex;

uniform samplerCube u_skybox;

void main() {
    gl_FragColor = vec4(textureCube(u_skybox, v_Tex).rgb, 1);
}