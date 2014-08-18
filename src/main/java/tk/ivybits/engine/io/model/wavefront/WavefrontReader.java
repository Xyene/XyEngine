/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.io.model.wavefront;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.io.model.IModelReader;
import tk.ivybits.engine.io.res.IResource;
import tk.ivybits.engine.io.res.IResourceFinder;
import tk.ivybits.engine.scene.model.*;
import tk.ivybits.engine.util.TangentSpace;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class WavefrontReader implements IModelReader {
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

    public Model load(String in, IResourceFinder finder) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(finder.find(in).openStream()));

        ArrayList<Vector3f> V = new ArrayList<>();
        ArrayList<Vector3f> VN = new ArrayList<>();
        ArrayList<Vector2f> VT = new ArrayList<>();
        Model model = new Model(in);
        Material currentMaterial;
        HashMap<String, Material> materials = new HashMap<>();
        boolean tangent = false;

        Mesh currentMesh = new Mesh(null);

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

                    Face face = new Face(vertexCount);
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
                    currentMesh.getFaces().add(face);
                    if (tangent) {
                        TangentSpace.calculateTangents(face);
                    }
                    break;
                case NEW_GROUP:
                case NEW_OBJECT:
                    break;
                case USE_MATERIAL:
                    currentMaterial = materials.get(concatTokens(tokens));
                    tangent = currentMaterial.normalMap != null;
                    if(currentMesh.getFaces().size() > 0)
                        model.getMeshes().add(currentMesh);
                    currentMesh = new Mesh(currentMaterial);
                    break;
                case MATERIAL_FILE:
                    materials = loadMaterials(finder.getParentOf(in) + concatTokens(tokens), finder);
                    break;
            }
        }
        if(currentMesh.getFaces().size() > 0)
            model.getMeshes().add(currentMesh);
        return model;
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

    private static HashMap<String, Material> loadMaterials(String in, IResourceFinder finder) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(finder.find(in).openStream()));
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
                    if (currentMaterial != null)
                        currentMaterial.ambientColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_DIFFUSE:
                    if (currentMaterial != null)
                        currentMaterial.diffuseColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_SPECULAR:
                    if (currentMaterial != null)
                        currentMaterial.specularColor = new Color(parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()), parseFloat(tokens.nextToken()));
                    break;
                case COLOR_SHININESS:
                    if (currentMaterial != null)
                        currentMaterial.shininess = 128 * (parseFloat(tokens.nextToken()) / 1000);
                    break;
                case COLOR_TRANSPARENCY_1:
                case COLOR_TRANSPARENCY_2:
                    if (currentMaterial != null) currentMaterial.opaqueness = parseFloat(tokens.nextToken());
                    break;
                case TEXTURE_AMBIENT:
                case TEXTURE_DIFFUSE:
                case TEXTURE_SPECULAR:
                case TEXTURE_TRANSPARENCY:
                case TEXTURE_BUMP_MAP:
                    String spath = concatTokens(tokens);
                    if (spath.trim().isEmpty())
                        continue;
                    IResource img = finder.find(finder.getParentOf(in) + spath);

                    switch (type) {
                        case TEXTURE_AMBIENT:
                            if (currentMaterial != null) currentMaterial.ambientMap = img;
                            break;
                        case TEXTURE_DIFFUSE:
                            if (currentMaterial != null) currentMaterial.diffuseMap = img;
                            break;
                        case TEXTURE_SPECULAR:
                            if (currentMaterial != null) currentMaterial.glossMap = img;
                            break;
                        case TEXTURE_TRANSPARENCY:
                            if (currentMaterial != null) currentMaterial.alphaTexture = img;
                            break;
                        case TEXTURE_BUMP_MAP:
                            if (currentMaterial != null) currentMaterial.normalMap = img;
                            break;
                    }
                    break;
            }
        }
        return materials;
    }
}
