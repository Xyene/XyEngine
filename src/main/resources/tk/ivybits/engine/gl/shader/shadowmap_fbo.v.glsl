#version 130

varying vec2 v_UV;

void main(void)
{
    gl_Position = ftransform();

    v_UV = gl_MultiTexCoord0.xy;
}
