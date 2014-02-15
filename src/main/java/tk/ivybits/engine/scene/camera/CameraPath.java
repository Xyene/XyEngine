package tk.ivybits.engine.scene.camera;

import tk.ivybits.engine.util.IBuilder;

import java.util.LinkedList;

public class CameraPath extends LinkedList<CameraPath.CameraNode> {
    public static class CameraNode {
        private final float x, y, z, pitch, yaw, roll;

        public CameraNode(float x, float y, float z, float pitch, float yaw, float roll) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.pitch = pitch;
            this.yaw = yaw;
            this.roll = roll;
        }

        public float x() {
            return x;
        }

        public float y() {
            return y;
        }

        public float z() {
            return z;
        }

        public float pitch() {
            return pitch;
        }

        public float yaw() {
            return yaw;
        }

        public float roll() {
            return roll;
        }

        public static class Builder implements IBuilder<CameraNode> {
            private float x, y, z, pitch, yaw, roll;

            public Builder at(float x, float y, float z) {
                this.x = x;
                this.y = y;
                this.z = z;
                return this;
            }

            public Builder lookAt(float x, float y, float z) {
                float dx = this.x - x;
                float dy = this.y - y;
                float dz = this.z - z;
                return this;
            }

            public Builder rotate(float pitch, float yaw, float roll) {
                this.pitch = pitch;
                this.yaw = yaw;
                this.roll = roll;
                return this;
            }

            public CameraNode build() {
                return new CameraNode(x, y, z, pitch, yaw, roll);
            }
        }
    }
}
