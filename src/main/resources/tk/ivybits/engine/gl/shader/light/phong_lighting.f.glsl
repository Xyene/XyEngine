#define MAX_LIGHTS 8

varying vec3 v_normal;
varying vec3 v_vertex;
varying vec2 v_uv;
#ifdef OBJECT_SHADOWS
varying vec4 v_lightSpacePos[MAX_LIGHTS];
#endif
#if defined(NORMAL_MAPPING) || defined(PARALLAX_MAPPING)
varying mat3 v_tangentMatrix;
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

#ifdef REFLECTIONS
uniform samplerCube u_envMap;
uniform vec3 u_eye;
#endif

#ifdef PARALLAX_MAPPING
varying vec3 v_ray;
#endif

void main(void)
{
    vec3 fragment = vec3(0.0);

    vec2 uv = v_uv;

    #ifdef PARALLAX_MAPPING
    if(u_material.hasHeight)
    {
        float height = texture2D(u_material.heightMap, uv).r * 0.04 - 0.03;
        uv += normalize(v_ray).xy * height;
    }
    #endif

    vec4 diffuse = u_material.hasDiffuse ? texture2D(u_material.diffuseMap, uv) : vec4(0.0, 0.0, 0.0, 1.0);
    vec3 texture = diffuse.rgb;

    vec3 specularTerm = u_material.specular;

    #ifdef SPECULAR_MAPPING
    if(u_material.hasSpecular) specularTerm *= texture2D(u_material.specularMap, uv).rgb;
    #endif

    #ifdef NORMAL_MAPPING
    vec3 N = u_material.hasNormal ? normalize(v_tangentMatrix * (texture2D(u_material.normalMap, uv).rgb * 2.0 - 1.0)) : v_normal;
    #else
    vec3 N = v_normal;
    #endif

    for(int idx = 0; idx < u_dirLightCount; idx++)
    {
        DirectionalLight light = u_dirLights[idx];

        vec3 I = normalize(light.direction);
        vec3 R = normalize(-reflect(I, N));

        vec3 ambient = u_material.ambient * light.ambient;
        vec3 diffuse = u_material.diffuse * light.diffuse * max(dot(N, I), 0.0);
        vec3 specular = specularTerm * light.specular * pow(max(dot(normalize(-v_vertex), R), 0.0), u_material.shininess);

        fragment += (ambient + (diffuse * texture) + specular) * light.intensity;
    }

    for(int idx = 0; idx < u_pointLightCount; idx++)
    {
        PointLight light = u_pointLights[idx];

        vec3 I = normalize(light.position - v_vertex);
        vec3 R = normalize(-reflect(I, N));

        vec3 ambient = u_material.ambient * light.ambient;
        vec3 diffuse = u_material.diffuse * light.diffuse * max(dot(N, I), 0.0);
        vec3 specular = specularTerm * light.specular * pow(max(dot(normalize(-v_vertex), R), 0.0), u_material.shininess);

        float attenuation = 1.0 / (1.0 + light.attenuation * pow(length(light.position - v_vertex), 2.0));

        fragment += (ambient + attenuation * (diffuse * texture + specular)) * light.intensity;
    }

    for(int idx = 0; idx < u_spotLightCount; idx++) {
        SpotLight light = u_spotLights[idx];
        vec3 I = normalize(light.position - v_vertex);
        vec3 D = normalize(light.direction);
        vec3 R = normalize(-reflect(I, N));

        float theta = dot(-I, D);
        float falloffArea = cos(radians(30)); // cosd(30)
        float shadedArea = light.cutoff - falloffArea;

        if(theta <= shadedArea) continue;

        float falloff = max((theta - falloffArea) / shadedArea, 0.0);
        vec3 ambient = u_material.ambient * light.ambient;
        vec3 diffuse = u_material.diffuse * max(dot(N, I), 0.0) * light.diffuse;
        vec3 specular = specularTerm  * light.specular * pow(max(dot(normalize(-v_vertex), R), 0.0), u_material.shininess);

        float attenuation = 1.0 / (1.0 + light.attenuation * pow(length(light.position - v_vertex), 2.0));

        vec3 color = falloff * (ambient + attenuation * (diffuse * texture + specular) * light.intensity);

        #ifdef OBJECT_SHADOWS
        // color *= 1.0; // TODO
        #endif

        fragment += color;
    }

    gl_FragColor = vec4(fragment, u_material.opaqueness * diffuse.a);

    #ifdef REFLECTIONS
    if(u_material.reflectivity > 0.0)
    {
        vec3 I = normalize(v_vertex - u_eye);

        vec3 refractedColor = vec3(
			// Chromatic dispersion
			textureCube(u_envMap, refract(I, N, u_material.refractionIndex + 0.000)).r,
			textureCube(u_envMap, refract(I, N, u_material.refractionIndex + 0.008)).g,
			textureCube(u_envMap, refract(I, N, u_material.refractionIndex + 0.016)).b
		);
        vec3 reflectedColor = textureCube(u_envMap, reflect(I, N)).rgb;

        float fresnel = 1.0 * pow(1.0 + dot(I, N), 4.0);
        gl_FragColor.rgb += mix(refractedColor, reflectedColor, 1.0 - fresnel * (1.0 - u_material.opaqueness)) * u_material.reflectivity;
    }
    #endif

    #ifdef FOG
    float depth = gl_FragCoord.z / gl_FragCoord.w;
    float fogFactor = smoothstep(u_fog.fogNear, u_fog.fogFar, depth);
    gl_FragColor = mix(gl_FragColor, vec4(u_fog.fogColor, gl_FragColor.w), fogFactor);
    #endif
}