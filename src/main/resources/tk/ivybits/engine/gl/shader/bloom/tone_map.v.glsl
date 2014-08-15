
varying vec2 v_uv;

void main() {
    gl_Position = ftransform();
    // scene and bloom are of the same size: we can use the same texcoord unit
    v_uv = gl_MultiTexCoord0.xy;
}