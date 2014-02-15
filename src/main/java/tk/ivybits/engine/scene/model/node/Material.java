package tk.ivybits.engine.scene.model.node;

import tk.ivybits.engine.scene.texture.ITexture;

import java.awt.*;

public class Material {
    public ITexture ambientTexture;
    public ITexture diffuseTexture;
    public ITexture specularTexture;
    public ITexture alphaTexture;
    public ITexture bumpMap;
    public Color ambientColor = Color.BLACK;
    public Color diffuseColor = Color.WHITE;
    public Color specularColor = Color.WHITE;
    public float shininess;
    public float transparency = 1;
    public String name;

    public Material(String name) {
        this.name = name;
    }

    public Material() {
    }

    @Override
    public String toString() {
        return "Material{" +
                "ambientTexture=" + ambientTexture +
                ", diffuseTexture=" + diffuseTexture +
                ", specularTexture=" + specularTexture +
                ", alphaTexture=" + alphaTexture +
                ", ambientColor=" + ambientColor +
                ", diffuseColor=" + diffuseColor +
                ", specularColor=" + specularColor +
                ", shininess=" + shininess +
                ", transparency=" + transparency +
                ", name='" + name + '\'' +
                '}';
    }
}
