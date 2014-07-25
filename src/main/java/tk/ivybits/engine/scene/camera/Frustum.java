package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.scene.BoundingBox;

//https://www.youtube.com/watch?v=MIOr8PFuGYo
public class Frustum {
    public float[][] m_Frustum = new float[6][4];
    private final float[] clip = new float[16];

    private void normalizePlane(float[][] frustum, int side) {
        float magnitude = (float) Math.sqrt(frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]);

        frustum[side][0] /= magnitude;
        frustum[side][1] /= magnitude;
        frustum[side][2] /= magnitude;
        frustum[side][3] /= magnitude;
    }

    public void calculateFrustum(Matrix4f projection, Matrix4f view) {
        this.clip[0] = (view.m00 * projection.m00 + view.m01 * projection.m10 + view.m02 * projection.m20 + view.m03 * projection.m30);
        this.clip[1] = (view.m00 * projection.m01 + view.m01 * projection.m11 + view.m02 * projection.m21 + view.m03 * projection.m31);
        this.clip[2] = (view.m00 * projection.m02 + view.m01 * projection.m12 + view.m02 * projection.m22 + view.m03 * projection.m32);
        this.clip[3] = (view.m00 * projection.m03 + view.m01 * projection.m13 + view.m02 * projection.m23 + view.m03 * projection.m33);

        this.clip[4] = (view.m10 * projection.m00 + view.m11 * projection.m10 + view.m12 * projection.m20 + view.m13 * projection.m30);
        this.clip[5] = (view.m10 * projection.m01 + view.m11 * projection.m11 + view.m12 * projection.m21 + view.m13 * projection.m31);
        this.clip[6] = (view.m10 * projection.m02 + view.m11 * projection.m12 + view.m12 * projection.m22 + view.m13 * projection.m32);
        this.clip[7] = (view.m10 * projection.m03 + view.m11 * projection.m13 + view.m12 * projection.m23 + view.m13 * projection.m33);

        this.clip[8] = (view.m20 * projection.m00 + view.m21 * projection.m10 + view.m22 * projection.m20 + view.m23 * projection.m30);
        this.clip[9] = (view.m20 * projection.m01 + view.m21 * projection.m11 + view.m22 * projection.m21 + view.m23 * projection.m31);
        this.clip[10] = (view.m20 * projection.m02 + view.m21 * projection.m12 + view.m22 * projection.m22 + view.m23 * projection.m32);
        this.clip[11] = (view.m20 * projection.m03 + view.m21 * projection.m13 + view.m22 * projection.m23 + view.m23 * projection.m33);

        this.clip[12] = (view.m30 * projection.m00 + view.m31 * projection.m10 + view.m32 * projection.m20 + view.m33 * projection.m30);
        this.clip[13] = (view.m30 * projection.m01 + view.m31 * projection.m11 + view.m32 * projection.m21 + view.m33 * projection.m31);
        this.clip[14] = (view.m30 * projection.m02 + view.m31 * projection.m12 + view.m32 * projection.m22 + view.m33 * projection.m32);
        this.clip[15] = (view.m30 * projection.m03 + view.m31 * projection.m13 + view.m32 * projection.m23 + view.m33 * projection.m33);

        this.m_Frustum[0][0] = (this.clip[3] - this.clip[0]);
        this.m_Frustum[0][1] = (this.clip[7] - this.clip[4]);
        this.m_Frustum[0][2] = (this.clip[11] - this.clip[8]);
        this.m_Frustum[0][3] = (this.clip[15] - this.clip[12]);

        normalizePlane(this.m_Frustum, 0);

        this.m_Frustum[1][0] = (this.clip[3] + this.clip[0]);
        this.m_Frustum[1][1] = (this.clip[7] + this.clip[4]);
        this.m_Frustum[1][2] = (this.clip[11] + this.clip[8]);
        this.m_Frustum[1][3] = (this.clip[15] + this.clip[12]);

        normalizePlane(this.m_Frustum, 1);

        this.m_Frustum[2][0] = (this.clip[3] + this.clip[1]);
        this.m_Frustum[2][1] = (this.clip[7] + this.clip[5]);
        this.m_Frustum[2][2] = (this.clip[11] + this.clip[9]);
        this.m_Frustum[2][3] = (this.clip[15] + this.clip[13]);

        normalizePlane(this.m_Frustum, 2);

        this.m_Frustum[3][0] = (this.clip[3] - this.clip[1]);
        this.m_Frustum[3][1] = (this.clip[7] - this.clip[5]);
        this.m_Frustum[3][2] = (this.clip[11] - this.clip[9]);
        this.m_Frustum[3][3] = (this.clip[15] - this.clip[13]);

        normalizePlane(this.m_Frustum, 3);

        this.m_Frustum[4][0] = (this.clip[3] - this.clip[2]);
        this.m_Frustum[4][1] = (this.clip[7] - this.clip[6]);
        this.m_Frustum[4][2] = (this.clip[11] - this.clip[10]);
        this.m_Frustum[4][3] = (this.clip[15] - this.clip[14]);

        normalizePlane(this.m_Frustum, 4);

        this.m_Frustum[5][0] = (this.clip[3] + this.clip[2]);
        this.m_Frustum[5][1] = (this.clip[7] + this.clip[6]);
        this.m_Frustum[5][2] = (this.clip[11] + this.clip[10]);
        this.m_Frustum[5][3] = (this.clip[15] + this.clip[14]);

        normalizePlane(this.m_Frustum, 5);
    }

    public boolean pointInFrustum(float x, float y, float z) {
        for (int i = 0; i < 6; i++) {
            if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= 0.0F) {
                return false;
            }
        }

        return true;
    }

    public boolean sphereInFrustum(float x, float y, float z, float radius) {
        for (int i = 0; i < 6; i++) {
            if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= -radius) {
                return false;
            }
        }

        return true;
    }

    public boolean cubeFullyInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int i = 0; i < 6; i++) {
            if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
                return false;
            if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)
                return false;
        }

        return true;
    }

    // Assumes a model transformation of 0,0 and no rotations
    public boolean bbInFrustum(float x, float y, float z, BoundingBox bb) {
        float length = bb.getLength() / 2;
        float width = bb.getWidth() / 2;
        float height = bb.getHeight();


        return cubeInFrustum(x - length, y + height, z - width, x + length, y, z + width);
    }

    public boolean cubeInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
        for (int i = 0; i < 6; i++) {
            if ((this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) && (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F)) {
                return false;
            }
        }
        return true;
    }
}