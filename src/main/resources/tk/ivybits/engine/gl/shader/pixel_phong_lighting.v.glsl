#version 130

#define MAX_LIGHTS 8

out vec3 v_surfaceNormal;
out vec3 v_vertexPosition;
out vec2 v_UV;
out mat3 v_TBN;
out vec4 v_lightSpacePos[MAX_LIGHTS];

in vec3 a_Vertex;
in vec3 a_Normal;
in vec3 a_Tangent;
in vec2 a_UV;

uniform int u_lightMatrixCount;
uniform mat4 u_lightViewMatrix[MAX_LIGHTS];

uniform mat4 u_projectionMatrix;
uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat3 u_normalMatrix;

void main(void)  
{
    vec4 hVertex = vec4(a_Vertex, 1.0);

    gl_Position = u_projectionMatrix * u_viewMatrix * u_modelMatrix * hVertex;
    for(int idx = 0; idx < u_lightMatrixCount; idx++) {
        // Map [-1,1] to [0,1]
        // Performs
        // x = x * 0.5 + 0.5
        // y = y * 0.5 + 0.5
        // z = z * 0.5 + 0.5
        // on all components
        v_lightSpacePos[idx] = mat4(
                               .5, .0, .0, .0,
                               .0, .5, .0, .0,
                               .0, .0, .5, .0,
                               .5, .5, .5, 1.) * (u_projectionMatrix * u_lightViewMatrix[idx] * u_modelMatrix) * hVertex;

    }
    v_surfaceNormal = normalize(u_normalMatrix * a_Normal);
    v_vertexPosition = vec3(u_modelMatrix * hVertex);

    v_UV = a_UV;

    vec3 Tangent = normalize((u_normalMatrix * a_Tangent).xyz);
    Tangent = normalize(Tangent - dot(Tangent, v_surfaceNormal) * v_surfaceNormal);
    vec3 Bitangent = cross(Tangent, v_surfaceNormal);
    v_TBN = mat3(Tangent, Bitangent, v_surfaceNormal);
}
          