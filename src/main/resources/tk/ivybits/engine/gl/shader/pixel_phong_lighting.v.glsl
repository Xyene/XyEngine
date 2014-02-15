varying vec3 v_surfaceNormal;
varying vec3 v_vertexPosition;
varying vec2 v_UV;
varying mat4 v_modelViewMatrix;
varying mat3 v_TBN;

attribute vec3 a_Vertex;
attribute vec3 a_Normal;
attribute vec3 a_Tangent;
attribute vec2 a_UV;

void main(void)  
{
    vec4 hVertex = vec4(a_Vertex, 1.0);
    gl_Position = gl_ModelViewProjectionMatrix * hVertex;

    v_surfaceNormal = normalize(gl_NormalMatrix * a_Normal);
    v_vertexPosition = (gl_ModelViewMatrix * hVertex).xyz;

    v_UV = a_UV;
    v_modelViewMatrix = gl_ModelViewMatrix;

    vec3 Tangent = normalize((gl_ModelViewMatrix * vec4(a_Tangent, 0.0)).xyz);
    Tangent = normalize(Tangent - dot(Tangent, v_surfaceNormal) * v_surfaceNormal);
    vec3 Bitangent = cross(Tangent, v_surfaceNormal);
    v_TBN = mat3(Tangent, Bitangent, v_surfaceNormal);
}
          