
#define MAX_LIGHTS 8

varying vec3 v_normal;
varying vec3 v_vertex;
varying vec2 v_uv;
#ifdef NORMAL_MAPPING
varying mat3 v_tangentMatrix;
#endif
#ifdef OBJECT_SHADOWS
varying vec4 v_lightSpacePos[MAX_LIGHTS];
#endif

attribute vec3 a_vertex;
attribute vec3 a_normal;
#ifdef NORMAL_MAPPING
attribute vec3 a_tangent;
#endif
attribute vec2 a_uv;

#ifdef OBJECT_SHADOWS
uniform int u_lightMatrixCount;
uniform mat4 u_lightViewMatrix[MAX_LIGHTS];
#endif

uniform mat4 u_modelMatrix;
uniform mat3 u_normalMatrix;
uniform mat4 u_mvpMatrix;

uniform vec3 u_eye;

varying vec3 tangent_eyeVec;

void main(void)  
{
    vec4 hVertex = vec4(a_vertex, 1.0);

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

    v_normal = normalize(u_normalMatrix * a_normal);
    v_vertex = vec3(u_modelMatrix * hVertex);

    v_uv = a_uv;

    #ifdef NORMAL_MAPPING
    vec3 tangent = normalize(u_normalMatrix * a_tangent);
    v_tangentMatrix = mat3(tangent, cross(tangent, v_normal), v_normal);

    tangent_eyeVec = u_eye - hVertex.xyz;
    tangent_eyeVec *= v_tangentMatrix;
    #endif
}
          