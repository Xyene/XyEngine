#version 130
#define MAX_LIGHTS 8

out vec3 v_surfaceNormal;
out vec3 v_vertexPosition;
out vec2 v_UV;
#ifdef NORMAL_MAPPING
out mat3 v_TBN;
#endif
#ifdef OBJECT_SHADOWS
out vec4 v_lightSpacePos[MAX_LIGHTS];
#endif

in vec3 a_Vertex;
in vec3 a_Normal;
#ifdef NORMAL_MAPPING
in vec3 a_Tangent;
#endif
in vec2 a_UV;

#ifdef OBJECT_SHADOWS
uniform int u_lightMatrixCount;
uniform mat4 u_lightViewMatrix[MAX_LIGHTS];
#endif

uniform mat4 u_modelMatrix;
uniform mat3 u_normalMatrix;
uniform mat4 u_mvpMatrix;

void main(void)  
{
    vec4 hVertex = vec4(a_Vertex, 1.0);

    gl_Position = u_mvpMatrix * hVertex;
    #ifdef OBJECT_SHADOWS
    for(int idx = 0; idx < u_lightMatrixCount; idx++) {
        // Map [-1,1] to [0,1]
        // Performs
        // x = x * 0.5 + 0.5
        // y = y * 0.5 + 0.5
        // z = z * 0.5 + 0.5
        // on all components
        v_lightSpacePos[idx] = u_lightViewMatrix[idx] * hVertex;
    }
    #endif

    v_surfaceNormal = normalize(u_normalMatrix * a_Normal);
    v_vertexPosition = vec3(u_modelMatrix * hVertex);
    v_UV = a_UV;

    #ifdef NORMAL_MAPPING
    vec3 tangent = normalize((u_normalMatrix * a_Tangent).xyz);
    tangent = normalize(tangent - dot(tangent, v_surfaceNormal) * v_surfaceNormal);
    vec3 bitangent = cross(tangent, v_surfaceNormal);
    v_TBN = mat3(tangent, bitangent, v_surfaceNormal);
    #endif
}
          