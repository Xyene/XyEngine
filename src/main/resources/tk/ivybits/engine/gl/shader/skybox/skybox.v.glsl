
attribute vec3 a_Vertex;

uniform mat4 u_mvpMatrix;

varying vec3 v_Tex;

void main() {
    gl_Position = (u_mvpMatrix * vec4(a_Vertex, 1.0)).xyzw;
    v_Tex = a_Vertex;
}