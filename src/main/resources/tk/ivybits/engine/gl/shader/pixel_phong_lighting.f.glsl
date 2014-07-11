#version 130

struct SpotLight {
    vec3 position;
    vec3 direction;
    float cutoff;
    float intensity;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight {
    vec3 position;
    float intensity;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct Material {
    bool hasDiffuse;
    sampler2D diffuseMap;
    bool hasSpecular;
    sampler2D specularMap;
    bool hasNormal;
    sampler2D normalMap;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    float transparency;
};

struct Fog {
    vec3 fogColor;
    float fogNear;
    float fogFar;
};

#define MAX_LIGHTS 8

in vec3 v_surfaceNormal;
in vec3 v_vertexPosition;
in vec2 v_UV;
in vec4 v_lightSpacePos[MAX_LIGHTS];
in mat3 v_TBN;

// Set location to 0 - glGetUniformLocation reports -1
layout(location = 0) uniform Material u_material;
uniform bool u_hasFog;
uniform Fog u_fog;
uniform int u_pointLightCount;
uniform int u_spotLightCount;

uniform int u_lightMatrixCount;
uniform PointLight u_pointLights[MAX_LIGHTS];
uniform SpotLight u_spotLights[MAX_LIGHTS];

uniform sampler2D u_shadowMap[MAX_LIGHTS];

float spotlight_shadow_factor()
{
        float visibility = 1.0;
        float bias = 0.005;
        for(int idx = 0; idx < u_lightMatrixCount; idx++) {
            vec4 ShadowCoord = v_lightSpacePos[idx];
            if(ShadowCoord.w > 0) {
                if (textureProj(u_shadowMap[idx], ShadowCoord.xyw).z < (ShadowCoord.z - bias) / ShadowCoord.w){
                    visibility /= 3.0;
                }
            }
        }
        return visibility;
}

vec3 normal_mapped_normal()
{
    return normalize(v_TBN * (texture2D(u_material.normalMap, v_UV).rgb * 2.0 - 1.0));
}

void main(void)
{
    vec3 fragment = vec3(0);
    vec3 texture = texture2D(u_material.diffuseMap, v_UV).rgb;
    float specMap = u_material.hasSpecular ? dot(texture2D(u_material.specularMap, v_UV).rgb, vec3(1)) : 1;

    vec3 normalMap = u_material.hasNormal ? normal_mapped_normal() : v_surfaceNormal;

    for(int idx = 0; idx < u_pointLightCount; idx++) {
        PointLight lightSource = u_pointLights[idx];

        vec3 incidentRay = normalize(lightSource.position - v_vertexPosition);
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        vec3 ambient = u_material.ambient * lightSource.ambient;
        vec3 diffuse = u_material.diffuse * lightSource.diffuse * max(dot(normalMap, incidentRay), 0);
        vec3 specular = u_material.specular * lightSource.specular *
            pow(max(dot(normalize(-v_vertexPosition), refractedRay), 0), u_material.shininess * specMap);

        fragment += (ambient + (diffuse * texture) + specular) * lightSource.intensity;
    }

    for(int idx = 0; idx < u_spotLightCount; idx++) {
        SpotLight lightSource = u_spotLights[idx];
        vec3 incidentRay = normalize(lightSource.position - v_vertexPosition);
        vec3 spotDirection = normalize(lightSource.direction);
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        float cosTheta = dot(-incidentRay, spotDirection);
        float falloffArea = cos(radians(30));
        float shadedArea = lightSource.cutoff - falloffArea;

        vec3 color = vec3(0);
        if(cosTheta > shadedArea) {
            float falloff = max((cosTheta - falloffArea) / shadedArea, 0);
            vec3 ambient = u_material.ambient *
                lightSource.ambient;
            vec3 diffuse = u_material.diffuse *
                max(dot(normalMap, incidentRay), 0) *
                lightSource.diffuse;
            vec3 specular = u_material.specular *
                pow(max(dot(refractedRay, normalize(-v_vertexPosition)), 0), u_material.shininess * specMap) *
                lightSource.specular;
            color += (falloff * (ambient + ((diffuse * texture) + specular) * lightSource.intensity));
       }
       fragment += color * spotlight_shadow_factor();
    }

    gl_FragColor = vec4(fragment, u_material.transparency);

    if(u_hasFog) {
        float depth = gl_FragCoord.z / gl_FragCoord.w;
        float fogFactor = smoothstep(u_fog.fogNear, u_fog.fogFar, depth);
        gl_FragColor = mix(gl_FragColor, vec4(u_fog.fogColor, gl_FragColor.w), fogFactor);
    }
}