package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.scene.BoundingBox;

public class Frustum {
    public float[][] frustum = new float[6][4];

    public void setTransform(Matrix4f projection, Matrix4f view) {
        if (true) return; // FIXME
        float v0 = (view.m00 * projection.m00 + view.m01 * projection.m10 + view.m02 * projection.m20 + view.m03 * projection.m30);
        float v1 = (view.m00 * projection.m01 + view.m01 * projection.m11 + view.m02 * projection.m21 + view.m03 * projection.m31);
        float v2 = (view.m00 * projection.m02 + view.m01 * projection.m12 + view.m02 * projection.m22 + view.m03 * projection.m32);
        float v3 = (view.m00 * projection.m03 + view.m01 * projection.m13 + view.m02 * projection.m23 + view.m03 * projection.m33);

        float v4 = (view.m10 * projection.m00 + view.m11 * projection.m10 + view.m12 * projection.m20 + view.m13 * projection.m30);
        float v5 = (view.m10 * projection.m01 + view.m11 * projection.m11 + view.m12 * projection.m21 + view.m13 * projection.m31);
        float v6 = (view.m10 * projection.m02 + view.m11 * projection.m12 + view.m12 * projection.m22 + view.m13 * projection.m32);
        float v7 = (view.m10 * projection.m03 + view.m11 * projection.m13 + view.m12 * projection.m23 + view.m13 * projection.m33);

        float v8 = (view.m20 * projection.m00 + view.m21 * projection.m10 + view.m22 * projection.m20 + view.m23 * projection.m30);
        float v9 = (view.m20 * projection.m01 + view.m21 * projection.m11 + view.m22 * projection.m21 + view.m23 * projection.m31);
        float v10 = (view.m20 * projection.m02 + view.m21 * projection.m12 + view.m22 * projection.m22 + view.m23 * projection.m32);
        float v11 = (view.m20 * projection.m03 + view.m21 * projection.m13 + view.m22 * projection.m23 + view.m23 * projection.m33);

        float v12 = (view.m30 * projection.m00 + view.m31 * projection.m10 + view.m32 * projection.m20 + view.m33 * projection.m30);
        float v13 = (view.m30 * projection.m01 + view.m31 * projection.m11 + view.m32 * projection.m21 + view.m33 * projection.m31);
        float v14 = (view.m30 * projection.m02 + view.m31 * projection.m12 + view.m32 * projection.m22 + view.m33 * projection.m32);
        float v15 = (view.m30 * projection.m03 + view.m31 * projection.m13 + view.m32 * projection.m23 + view.m33 * projection.m33);

        frustum[0][0] = (v3 - v0);
        frustum[0][1] = (v7 - v4);
        frustum[0][2] = (v11 - v8);
        frustum[0][3] = (v15 - v12);

        frustum[1][0] = (v3 + v0);
        frustum[1][1] = (v7 + v4);
        frustum[1][2] = (v11 + v8);
        frustum[1][3] = (v15 + v12);

        frustum[2][0] = (v3 + v1);
        frustum[2][1] = (v7 + v5);
        frustum[2][2] = (v11 + v9);
        frustum[2][3] = (v15 + v1);

        frustum[3][0] = (v3 - v1);
        frustum[3][1] = (v7 - v5);
        frustum[3][2] = (v11 - v9);
        frustum[3][3] = (v15 - v13);

        frustum[4][0] = (v3 - v2);
        frustum[4][1] = (v7 - v6);
        frustum[4][2] = (v11 - v10);
        frustum[4][3] = (v15 - v14);

        frustum[5][0] = (v3 + v2);
        frustum[5][1] = (v7 + v6);
        frustum[5][2] = (v11 + v10);
        frustum[5][3] = (v15 + v14);

        for (int side = 0; side < 6; side++) {
            float magnitude = (float) Math.sqrt(frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]);

            frustum[side][0] /= magnitude;
            frustum[side][1] /= magnitude;
            frustum[side][2] /= magnitude;
            frustum[side][3] /= magnitude;
        }
    }

    public boolean pointInFrustum(float x, float y, float z) {
        for (int side = 0; side < 6; side++) {
            if (frustum[side][0] * x + frustum[side][1] * y + frustum[side][2] * z + frustum[side][3] <= 0.0F) {
                return false;
            }
        }

        return true;
    }

    public boolean sphereInFrustum(float x, float y, float z, float radius) {
        for (int side = 0; side < 6; side++) {
            if (frustum[side][0] * x + frustum[side][1] * y + frustum[side][2] * z + frustum[side][3] <= -radius) {
                return false;
            }
        }

        return true;
    }

    public boolean cubeFullyInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int side = 0; side < 6; side++) {
            if (frustum[side][0] * x1 + frustum[side][1] * y1 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x2 + frustum[side][1] * y1 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x1 + frustum[side][1] * y2 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x2 + frustum[side][1] * y2 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x1 + frustum[side][1] * y1 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x2 + frustum[side][1] * y1 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x1 + frustum[side][1] * y2 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F)
                return false;
            if (frustum[side][0] * x2 + frustum[side][1] * y2 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F)
                return false;
        }

        return true;
    }

    // Assumes a model transformation of 0,0,0 and no rotation
    public boolean bbInFrustum(float x, float y, float z, BoundingBox bb) {
        if(true) return true; // FIXME
        float length = bb.getLength() / 2;
        float width = bb.getWidth() / 2;
        float height = bb.getHeight();

        return cubeInFrustum(x - length, y + height, z - width, x + length, y, z + width);
    }

    public boolean cubeInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int side = 0; side < 6; side++) {
            if ((frustum[side][0] * x1 + frustum[side][1] * y1 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x2 + frustum[side][1] * y1 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x1 + frustum[side][1] * y2 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x2 + frustum[side][1] * y2 + frustum[side][2] * z1 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x1 + frustum[side][1] * y1 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x2 + frustum[side][1] * y1 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x1 + frustum[side][1] * y2 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F) && (frustum[side][0] * x2 + frustum[side][1] * y2 + frustum[side][2] * z2 + frustum[side][3] <= 0.0F)) {
                return false;
            }
        }
        return true;
    }
}