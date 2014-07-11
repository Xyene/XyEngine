struct SpotLight {
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
};

struct PointLight {
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
};

struct Material {
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
};

struct Fog {
    vec3 fogColor;
    float fogNear;
    float fogFar;
};