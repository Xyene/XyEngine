#version 130

varying vec3 v_surfaceNormal;
varying vec3 v_vertexPosition;
varying vec2 v_UV;
varying mat4 v_modelViewMatrix;

layout(location = 0) uniform struct Material {
    // Map for per-texel ambient terms
    sampler2D ambientMap;
    bool hasAmbient;
    // Map for per-texel diffuse terms
    sampler2D diffuseMap;
    bool hasDiffuse;
    // Map for per-texel specular terms
    sampler2D specularMap;
    bool hasSpecular;
    // Map for per-texel normals
    sampler2D normalMap;
    bool hasNormal;
    // Map for transparency
    sampler2D alphaMap;
    bool hasAlpha;
    // Ambient term
    vec3 ambient;
    // Diffuse term
    vec3 diffuse;
    // Specular term
    vec3 specular;
    // Shininess
    // The cosine of the vertex and refracted ray is raised to this power
    float shininess;
    // The alpha component of this material
    float transparency;
} u_material;

layout(location = 15) uniform bool u_hasFog;
layout(location = 16) uniform struct Fog {
    vec3 fogColor;
    float fogNear;
    float fogFar;
} u_fog;

layout(location = 19) uniform int u_pointLightCount;
layout(location = 20) uniform int u_spotLightCount;

#define MAX_LIGHTS 64

layout(location = 21) uniform struct PointLight {
    // Location of the light source
    vec3 position;
    // Brightness modifier
    float intensity;
    // Ambient term
    vec3 ambient;
    // Diffuse term
    vec3 diffuse;
    // Specular term
    vec3 specular;
} u_pointLights[MAX_LIGHTS];

// 341 = MAX_LIGHTS * 5 (# of fields in u_pointLights) + 15 (offset of u_pointLights)
layout(location = 341) uniform struct SpotLight {
    // Location of the light source
    vec3 position;
    // Direction
    vec3 direction;
    // The cosine of the cutoff of the spotlight
    float cutoff;
    // Brightness modifier
    float intensity;
    // Ambient term
    vec3 ambient;
    // Diffuse term
    vec3 diffuse;
    // Specular term
    vec3 specular;
} u_spotLights[MAX_LIGHTS];

varying mat3 v_TBN;

vec3 normal_mapped_normal()
{
    vec3 normal = texture2D(u_material.normalMap, v_UV).rgb * 2.0 - 1.0;
    return normalize(v_TBN * normal);
}

void main(void)
{
    vec3 fragment = vec3(0);
    vec3 texture = texture2D(u_material.diffuseMap, v_UV).rgb;
    float specMap = u_material.hasSpecular ? dot(texture2D(u_material.specularMap, v_UV).rgb, vec3(1)) : 1;
    //vec3 normalMap = texture2D(u_material.normalMap, v_UV.xy).rgb;
    //normalMap = normalMap * 2.0 - 1.0;
    //normalMap = normalize(gl_NormalMatrix * normalMap);
    //normalMap = gl_NormalMatrix * normalMap;
    //normalMap = v_surfaceNormal;
    vec3 normalMap = u_material.hasNormal ? normal_mapped_normal() : v_surfaceNormal;

    for(int idx = 0; idx < u_pointLightCount; idx++) {
        PointLight lightSource = u_pointLights[idx];

        // Incoming light ray is a normalized direction from the light source to the current vertex position
        // We must multiply the source location by the modelview matrix to convert from
        // camera space to world space
        vec3 incidentRay = normalize(/*(v_modelViewMatrix * vec4(lightSource.position, 1)).xyz*/ vec3(10) - v_vertexPosition);
        // The refracted ray is the incident ray reflected on the surface normal
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        // Ambient term is the u_material ambience * the light source ambience
        vec3 ambient = u_material.ambient * lightSource.ambient;
        // Diffuse term is the u_material diffuse * the light source diffuse * the dot product (cosine) between the
        // incident and normal, the smaller the angle, the brighter the diffuse term will be
        vec3 diffuse = u_material.diffuse * lightSource.diffuse * max(dot(normalMap, incidentRay), 0);
        // The specular term is the u_material specularity * the light source specularity * the
        // dot product between the vertex position and the refracted ray, to the power of the u_material shininess
        vec3 specular = u_material.specular * lightSource.specular *
            pow(max(dot(normalize(-v_vertexPosition), refractedRay), 0), u_material.shininess * specMap);

        // Add to final fragment color
        // Diffuse must be multiplied by the texture since they represent the same term
        fragment += (ambient + (diffuse * texture) + specular) * lightSource.intensity; // (Ambient + (Diffuse) + Specular) * INTENSITY;
    }
    for(int idx = 0; idx < u_spotLightCount; idx++) {
        SpotLight lightSource = u_spotLights[idx];
        vec3 incidentRay = normalize((v_modelViewMatrix * vec4(lightSource.position, 1)).xyz - v_vertexPosition);
        vec3 spotDirection = normalize((v_modelViewMatrix * vec4(lightSource.direction, 0)).xyz);
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        // The cosine between the spot light direction vector and the incident vector
        float cosTheta = dot(-incidentRay, spotDirection);
        float falloffArea = cos(radians(30));
        float shadedArea = lightSource.cutoff - falloffArea;

        // Is the fragment located in the cone?
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
            fragment += falloff * (ambient + (diffuse * texture) + specular) * lightSource.intensity;
       }
    }

    gl_FragColor = vec4(fragment, u_material.transparency);

    if(u_hasFog) {
        float depth = gl_FragCoord.z / gl_FragCoord.w;
        float fogFactor = smoothstep(u_fog.fogNear, u_fog.fogFar, depth);
        gl_FragColor = mix(gl_FragColor, vec4(u_fog.fogColor, gl_FragColor.w), fogFactor);
    }
}