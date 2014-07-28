package tk.ivybits.engine.loader.model.wavefront;

import tk.ivybits.engine.gl.texture.BufferedTexture;
import tk.ivybits.engine.scene.model.IModelReader;
import tk.ivybits.engine.scene.model.TangentSpace;
import tk.ivybits.engine.scene.model.node.Face;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.model.node.Mesh;
import tk.ivybits.engine.scene.model.node.Vertex;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class OBJReader implements IModelReader {
    protected static final String
            VERTEX = "v",
            NORMAL = "vn",
            TEXTURE_COORDINATE = "vt",
            FACE = "f",
            MATERIAL_FILE = "mtllib",
            USE_MATERIAL = "usemtl",
            NEW_GROUP = "g",
            NEW_OBJECT = "o",
            NEW_MATERIAL = "newmtl",
            COLOR_AMBIENT = "ka",
            COLOR_DIFFUSE = "kd",
            COLOR_SPECULAR = "ks",
            COLOR_SHININESS = "ns",
            COLOR_TRANSPARENCY_1 = "d",
            COLOR_TRANSPARENCY_2 = "tr",
            TEXTURE_AMBIENT = "map_ka",
            TEXTURE_DIFFUSE = "map_kd",
            TEXTURE_SPECULAR = "map_ks",
            TEXTURE_TRANSPARENCY = "map_d",
            TEXTURE_BUMP_MAP = "map_bump";

    public Mesh load(File in) throws IOException {
        return load(in.getAbsolutePath(), RESOURCE_FINDER_FILE);
    }

    public Mesh loadSystem(String in) throws IOException {
        return load(in, RESOURCE_FINDER_SYSTEM_RESOURCE);
    }

    public Mesh load(String in, ResourceFinder finder) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(finder.open(in)));

        ArrayList<Vector3f> V = new ArrayList<>();
        ArrayList<Vector3f> VN = new ArrayList<>();
        ArrayList<Vector2f> VT = new ArrayList<>();
        Mesh mesh = new Mesh();
        List<Face> faces = mesh.getFaces();
        Material currentMaterial = null;
        HashMap<String, Material> materials = new HashMap<>();
        boolean tangent = false;

        for (String line; (line = reader.readLine()) != null; ) {
            if ((line = line.trim()).isEmpty())
                continue;
            StringTokenizer tokens = new StringTokenizer(line);
            switch (tokens.nextToken().toLowerCase()) {
                case VERTEX:
                    V.add(new Vector3f(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken())));
                    break;
                case NORMAL:
                    VN.add(new Vector3f(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken())));
                    break;
                case TEXTURE_COORDINATE:
                    int len = tokens.countTokens();
                    VT.add(new Vector2f(
                            len >= 1 ? parseFloat(tokens.nextToken()) : 0,
                            len >= 2 ? 1 - parseFloat(tokens.nextToken()) : 0));
                    break;
                case FACE:
                    int vertexCount = tokens.countTokens();
                    if (vertexCount > 4)
                        throw new IllegalArgumentException("unsupported number of vertices/face: " + vertexCount);

                    Face face = new Face(vertexCount, currentMaterial);
                    Vertex[] vertices = face.getVertices();

                    for (int i = 0; i < vertexCount; i++) {
                        Vertex v = new Vertex();
                        String[] raw = tokens.nextToken().split("/");
                        v.pos = V.get(parseInt(raw[0]) - 1);

                        if (raw.length > 1)
                            if (!raw[1].isEmpty())
                                v.uv = VT.get(parseInt(raw[1]) - 1);

                        if (raw.length > 2)
                            if (!raw[2].isEmpty())
                                v.normal = VN.get(parseInt(raw[2]) - 1);
                        vertices[i] = v;
                    }
                    faces.add(face);
                    if (tangent) {
                        TangentSpace.calculateTangents(face);
                    }
                    break;
                case NEW_GROUP:
                case NEW_OBJECT:
                    break;
                case USE_MATERIAL:
                    currentMaterial = materials.get(concatTokens(tokens));
                    tangent = currentMaterial.bumpMap != null;
                    break;
                case MATERIAL_FILE:
                    materials = loadMaterials(finder.parent(in) + concatTokens(tokens), finder);
                    break;
            }
        }
        return mesh;
    }

    private static String concatTokens(StringTokenizer tokenizer) {
        StringBuilder temp = new StringBuilder();
        while (tokenizer.hasMoreTokens()) temp.append(" ").append(tokenizer.nextToken());
        return temp.toString().trim();
    }

    @Override
    public String getFileType() {
        return "OBJ";
    }

    private static HashMap<String, Material> loadMaterials(String in, ResourceFinder finder) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(finder.open(in)));
        HashMap<String, Material> materials = new HashMap<>();
        Material currentMaterial = null;

        for (String line; (line = reader.readLine()) != null; ) {
            if ((line = line.trim()).isEmpty())
                continue;
            StringTokenizer tokens = new StringTokenizer(line);
            String type = tokens.nextToken().toLowerCase();

            switch (type) {
                case NEW_MATERIAL:
                    String materialName = tokens.nextToken();
                    materials.put(materialName, currentMaterial = new Material(materialName));
                    break;
                case COLOR_AMBIENT:
                    currentMaterial.ambientColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_DIFFUSE:
                    currentMaterial.diffuseColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_SPECULAR:
                    currentMaterial.specularColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_SHININESS:
                    currentMaterial.shininess = 128 * (parseFloat(tokens.nextToken()) / 1000);
                    break;
                case COLOR_TRANSPARENCY_1:
                case COLOR_TRANSPARENCY_2:
                    currentMaterial.transparency = parseFloat(tokens.nextToken());
                    break;
                case TEXTURE_AMBIENT:
                case TEXTURE_DIFFUSE:
                case TEXTURE_SPECULAR:
                case TEXTURE_TRANSPARENCY:
                case TEXTURE_BUMP_MAP:
                    String spath = concatTokens(tokens);
                    if (spath.trim().isEmpty())
                        continue;
                    InputStream imgFile = finder.open(finder.parent(in) + spath);
                    BufferedTexture img;
                    try {
                        img = new BufferedTexture(GL_TEXTURE_2D, ImageIO.read(imgFile));
                    } catch (IIOException e) {
                        throw new IOException("failed to read material image " + imgFile.toString());
                    }
                    switch (type) {
                        case TEXTURE_AMBIENT:
                            currentMaterial.ambientTexture = img;
                            break;
                        case TEXTURE_DIFFUSE:
                            currentMaterial.diffuseTexture = img;
                            break;
                        case TEXTURE_SPECULAR:
                            currentMaterial.specularTexture = img;
                            break;
                        case TEXTURE_TRANSPARENCY:
                            currentMaterial.alphaTexture = img;
                            break;
                        case TEXTURE_BUMP_MAP:
                            currentMaterial.bumpMap = img;
                            break;
                    }
                    break;
            }
        }
        return materials;
    }
}
