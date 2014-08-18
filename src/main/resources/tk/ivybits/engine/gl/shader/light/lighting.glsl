struct SpotLight {
    vec3 position;
    vec3 direction;
    float cutoff; // cosd(light cutoff)
    float intensity;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float attenuation;
};

struct PointLight {
    vec3 position;
    float intensity;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float attenuation;
};

struct DirectionalLight {
    vec3 direction;
    float intensity;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct Material {
    bool hasDiffuse;
    sampler2D diffuseMap;
#ifdef SPECULAR_MAPPING
    bool hasSpecular;
    sampler2D specularMap;
#endif
#ifdef NORMAL_MAPPING
    bool hasNormal;
    sampler2D normalMap;
#endif
#ifdef PARALLAX_MAPPING
    bool hasHeight;
    sampler2D heightMap;
#endif
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    float shininess;
    float opaqueness;
#ifdef REFLECTIONS
    float reflectivity;
    float refractionIndex;
#endif
};

#ifdef FOG
struct Fog {
    vec3 fogColor;
    float fogNear;
    float fogFar;
};
#endif
