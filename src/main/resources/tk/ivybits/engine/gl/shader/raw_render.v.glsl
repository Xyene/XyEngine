#version 130

in vec3 a_Vertex;

uniform mat4 u_projectionMatrix;
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;

void main(void)
{
    vec4 hVertex = vec4(a_Vertex, 1.0);
    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * hVertex;
}