package tk.ivybits.engine.gl.texture;

import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.camera.ICamera;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class CubeMapTexture {
//    private int cubemap;
//    private int framebuffer, depthbuffer;
//    private int width, height;
//
//    public CubeMapTexture(int width, int height, int filter) {
//        this.width = width;
//        this.height = height;
//
//        cubemap = glGenTextures();
//        glBindTexture(GL_TEXTURE_CUBE_MAP, cubemap);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, filter);
//        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, filter);
//
//        // set textures
//        for (int i = 0; i < 6; ++i)
//            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
//
//        // create the fbo
//        framebuffer = glGenFramebuffers();
//        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
//
//        // create the uniform depth buffer
//        depthbuffer = glGenRenderbuffers();
//        glBindRenderbuffer(GL_RENDERBUFFER, depthbuffer);
//        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
//        glBindRenderbuffer(GL_RENDERBUFFER, 0);
//
//        // attach it
//        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, framebuffer);
//
//
//        // attach only the +X cubemap texture (for completeness)
//        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_CUBE_MAP_POSITIVE_X, cubemap, 0);
//
//        // this function just checks for framebuffer completeness and throws and exception if it’s not happy
//        int status;
//        if ((status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT)) != GL_FRAMEBUFFER_COMPLETE_EXT) {
//            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
//        }
//
//        // disable
//        glBindFramebuffer(GL_FRAMEBUFFER, 0);
//        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
//    }
//
//    public void update(IScene scene, float x, float y, float z) {
//        for (int face = GL_TEXTURE_CUBE_MAP_POSITIVE_X; face < GL_TEXTURE_CUBE_MAP_POSITIVE_X + 6; face++) {
//            // attach new texture and renderbuffer to fbo
//            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, face, cubemap, 0);
//            int status;
//            if ((status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT)) != GL_FRAMEBUFFER_COMPLETE_EXT) {
//                throw new IllegalStateException(String.format("failed to verify framebuffer: 0x%04X\n", status));
//            }
//
//            // clear
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//
//            glMatrixMode(GL_PROJECTION);
//            glLoadIdentity();
//            // play around with the near and far values
//            ICamera sceneCamera = scene.getCamera();
//            gluPerspective(sceneCamera.getFieldOfView(), sceneCamera.getAspectRatio(), sceneCamera.getZNear(), sceneCamera.getZFar());
//
//            glMatrixMode(GL_MODELVIEW);
//            glLoadIdentity();
//
//            // setup lookat depending on current face
//            switch (face) {
//                case GL_TEXTURE_CUBE_MAP_POSITIVE_X:
//                    gluLookAt(0, 0, 0, 1, 0, 0, 0, 1, 0);
//                    break;
//
//                case GL_TEXTURE_CUBE_MAP_NEGATIVE_X:
//                    gluLookAt(0, 0, 0, -1, 0, 0, 0, 1, 0);
//                    break;
//
//                case GL_TEXTURE_CUBE_MAP_POSITIVE_Y:
//                    gluLookAt(0, 0, 0, 0, 10, 0, 1, 0, 0);
//                    break;
//
//                case GL_TEXTURE_CUBE_MAP_NEGATIVE_Y:
//                    gluLookAt(0, 0, 0, 0, -10, 0, 1, 0, 0);
//                    break;
//
//                case GL_TEXTURE_CUBE_MAP_POSITIVE_Z:
//                    gluLookAt(0, 0, 0, 0, 0, 10, 0, 1, 0);
//                    break;
//
//                case GL_TEXTURE_CUBE_MAP_NEGATIVE_Z:
//                    gluLookAt(0, 0, 0, 0, 0, -10, 0, 1, 0);
//                    break;
//
//                default:
//                    break;
//            }
//
//            glTranslatef(-x, -y, -z);
//            scene.draw();
//        }
//    }
//
//    @Override
//    public void bind() {
//        glBindTexture(GL_TEXTURE_CUBE_MAP, id());
//    }
//
//    @Override
//    public void unbind() {
//        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
//    }
//
//    @Override
//    public int id() {
//        return cubemap;
//    }
//
//    @Override
//    public int width() {
//        return width;
//    }
//
//    @Override
//    public int height() {
//        return height;
//    }
}
