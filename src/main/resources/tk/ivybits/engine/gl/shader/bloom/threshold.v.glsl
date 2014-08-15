#version 110

varying vec2 v_UV;

void main() {
    gl_Position = ftransform();
    v_UV = gl_MultiTexCoord0.xy;
}