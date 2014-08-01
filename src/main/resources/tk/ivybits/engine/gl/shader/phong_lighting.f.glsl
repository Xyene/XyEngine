#version 110

#if  __VERSION__ < 130
#define out varying
#define in varying
#define texture texture2D
#define textureProj texture2DProj
#endif

#define MAX_LIGHTS 8

in vec3 v_surfaceNormal;
in vec3 v_vertexPosition;
in vec2 v_UV;
#ifdef OBJECT_SHADOWS
in vec4 v_lightSpacePos[MAX_LIGHTS];
#endif
#ifdef NORMAL_MAPPING
in mat3 v_TBN;
#endif

uniform Material u_material;
#ifdef FOG
uniform Fog u_fog;
#endif
uniform int u_pointLightCount;
uniform int u_spotLightCount;
uniform int u_dirLightCount;

uniform PointLight u_pointLights[MAX_LIGHTS];
uniform SpotLight u_spotLights[MAX_LIGHTS];
uniform DirectionalLight u_dirLights[MAX_LIGHTS];

#ifdef OBJECT_SHADOWS
uniform int u_lightMatrixCount;
uniform sampler2D u_shadowMap[MAX_LIGHTS];
#endif

#ifdef OBJECT_SHADOWS
float spotlight_shadow_factor(int idx)
{
    float visibility = 1.0;
    /*float bias = 0.005;
    vec4 shadowCoord = v_lightSpacePos[idx];
    if(shadowCoord.w > 0) {
        if (textureProj(u_shadowMap[idx], shadowCoord.xyw).z < (shadowCoord.z - bias) / shadowCoord.w){
            visibility = 0;
        }
    }*/
    return visibility;
}
#endif

void main(void)
{
    vec3 fragment = vec3(0);
    vec3 texture = u_material.hasDiffuse ? texture2D(u_material.diffuseMap, v_UV).rgb : vec3(0);

    vec3 specularTerm = u_material.specular;

    #ifdef SPECULAR_MAPPING
    specularTerm *= texture2D(u_material.specularMap, v_UV).rgb;
    #endif

    #ifdef NORMAL_MAPPING
    vec3 normalMap = u_material.hasNormal ? normalize(v_TBN * (texture2D(u_material.normalMap, v_UV).rgb * 2.0 - 1.0)) : v_surfaceNormal;
    #else
    vec3 normalMap = v_surfaceNormal;
    #endif

    for(int idx = 0; idx < u_dirLightCount; idx++) {
        DirectionalLight lightSource = u_dirLights[idx];

        vec3 incidentRay = normalize(lightSource.direction);
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        vec3 ambient = u_material.ambient * lightSource.ambient;
        vec3 diffuse = u_material.diffuse * lightSource.diffuse * max(dot(normalMap, incidentRay), 0);
        vec3 specular = specularTerm * lightSource.specular *
            pow(max(dot(normalize(-v_vertexPosition), refractedRay), 0), u_material.shininess);

        fragment += (ambient + (diffuse * texture) + specular) * lightSource.intensity;
    }

    for(int idx = 0; idx < u_pointLightCount; idx++) {
        PointLight lightSource = u_pointLights[idx];

        vec3 incidentRay = normalize(lightSource.position - v_vertexPosition);
        vec3 refractedRay = normalize(-reflect(incidentRay, normalMap));

        vec3 ambient = u_material.ambient * lightSource.ambient;
        vec3 diffuse = u_material.diffuse * lightSource.diffuse * max(dot(normalMap, incidentRay), 0);
        vec3 specular = specularTerm * lightSource.specular *
            pow(max(dot(normalize(-v_vertexPosition), refractedRay), 0), u_material.shininess);

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
            vec3 specular = specularTerm *
                pow(max(dot(refractedRay, normalize(-v_vertexPosition)), 0), u_material.shininess) *
                lightSource.specular;
            color += (falloff * (ambient + ((diffuse * texture) + specular) * lightSource.intensity));
       }
        #ifdef OBJECT_SHADOWS
        fragment += color * spotlight_shadow_factor(idx);
        #else
        fragment += color;
        #endif
    }

    gl_FragColor = vec4(fragment, u_material.transparency);

    #ifdef FOG
    float depth = gl_FragCoord.z / gl_FragCoord.w;
    float fogFactor = smoothstep(u_fog.fogNear, u_fog.fogFar, depth);
    gl_FragColor = mix(gl_FragColor, vec4(u_fog.fogColor, gl_FragColor.w), fogFactor);
    #endif
}