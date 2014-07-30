package tk.ivybits.engine.scene.model.node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Material {
    public BufferedImage ambientTexture;
    public BufferedImage diffuseTexture;
    public BufferedImage specularTexture;
    public BufferedImage alphaTexture;
    public BufferedImage bumpMap;
    public Color ambientColor = Color.BLACK;
    public Color diffuseColor = Color.WHITE;
    public Color specularColor = Color.WHITE;
    public float shininess;
    public float transparency = 1;
    public String name;

    public Material(String name) {
        this.name = name;
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
