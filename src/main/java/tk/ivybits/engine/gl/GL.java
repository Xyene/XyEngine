package tk.ivybits.engine.gl;

import org.lwjgl.PointerBuffer;
import org.lwjgl.PointerWrapper;
import org.lwjgl.opengl.*;

import java.nio.*;

public class GL extends GLDirectAccess implements GLConstant {
    // Shadow texture state for faster GLDirectAccess
    private static int activeTexture = 0;
    // We have to use the GL11 GetInteger because we shadow GL_MAX_TEXTURE_UNITS
    private static final int[] textureUnits1d = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnits2d = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnits3d = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnits1dArr = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnits2dArr = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsRect = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsCube = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsCubeArr = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsBuf = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsSampled = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];
    private static final int[] textureUnitsSampledArr = new int[GL11.glGetInteger(GL_MAX_TEXTURE_UNITS)];

    public static void glPixelTransferi(int a, int b) {
        GL11.glPixelTransferi(a, b);
    }

    public static void glPolygonStipple(long a) {
        GL11.glPolygonStipple(a);
    }

    public static void glPolygonStipple(ByteBuffer a) {
        GL11.glPolygonStipple(a);
    }

    public static void glPixelTransferf(int a, float b) {
        GL11.glPixelTransferf(a, b);
    }

    public static int glGetTexParameteri(int a, int b) {
        return GL11.glGetTexParameteri(a, b);
    }

    public static void glGetPolygonStipple(long a) {
        GL11.glGetPolygonStipple(a);
    }

    public static void glGetPolygonStipple(ByteBuffer a) {
        GL11.glGetPolygonStipple(a);
    }

    public static void glInterleavedArrays(int a, int b, ShortBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, IntBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, ByteBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, FloatBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, DoubleBuffer c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static void glInterleavedArrays(int a, int b, long c) {
        GL11.glInterleavedArrays(a, b, c);
    }

    public static int glGetTexLevelParameteri(int a, int b, int c) {
        return GL11.glGetTexLevelParameteri(a, b, c);
    }

    public static void glGetClipPlane(int a, DoubleBuffer b) {
        GL11.glGetClipPlane(a, b);
    }

    public static void glGetTexLevelParameter(int a, int b, int c, IntBuffer d) {
        GL11.glGetTexLevelParameter(a, b, c, d);
    }

    public static void glGetTexLevelParameter(int a, int b, int c, FloatBuffer d) {
        GL11.glGetTexLevelParameter(a, b, c, d);
    }

    public static void glGetTexParameter(int a, int b, FloatBuffer c) {
        GL11.glGetTexParameter(a, b, c);
    }

    public static void glGetTexParameter(int a, int b, IntBuffer c) {
        GL11.glGetTexParameter(a, b, c);
    }

    public static void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void glPolygonOffset(float a, float b) {
        GL11.glPolygonOffset(a, b);
    }

    public static float glGetTexParameterf(int a, int b) {
        return GL11.glGetTexParameterf(a, b);
    }

    public static float glGetTexLevelParameterf(int a, int b, int c) {
        return GL11.glGetTexLevelParameterf(a, b, c);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, DoubleBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ByteBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, FloatBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, IntBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ShortBuffer i) {
        GL11.glTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, ShortBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, FloatBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, DoubleBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, IntBuffer g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexSubImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL11.glTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glTexParameteri(int a, int b, int c) {
        GL11.glTexParameteri(a, b, c);
    }

    public static void glNormalPointer(int a, DoubleBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, int b, long c) {
        GL11.glNormalPointer(a, b, c);
    }

    public static void glNormalPointer(int a, FloatBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, int b, ByteBuffer c) {
        GL11.glNormalPointer(a, b, c);
    }

    public static void glNormalPointer(int a, IntBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glNormalPointer(int a, ByteBuffer b) {
        GL11.glNormalPointer(a, b);
    }

    public static void glPushClientAttrib(int a) {
        GL11.glPushClientAttrib(a);
    }

    public static void glPopClientAttrib() {
        GL11.glPopClientAttrib();
    }

    public static void glTexParameter(int a, int b, IntBuffer c) {
        GL11.glTexParameter(a, b, c);
    }

    public static void glTexParameter(int a, int b, FloatBuffer c) {
        GL11.glTexParameter(a, b, c);
    }

    public static void glVertexPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glVertexPointer(a, b, c, d);
    }

    public static void glVertexPointer(int a, int b, int c, long d) {
        GL11.glVertexPointer(a, b, c, d);
    }

    public static void glVertexPointer(int a, int b, ShortBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, DoubleBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, FloatBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glVertexPointer(int a, int b, IntBuffer c) {
        GL11.glVertexPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, DoubleBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, IntBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, FloatBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, int c, long d) {
        GL11.glTexCoordPointer(a, b, c, d);
    }

    public static void glTexCoordPointer(int a, int b, ShortBuffer c) {
        GL11.glTexCoordPointer(a, b, c);
    }

    public static void glTexCoordPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glTexCoordPointer(a, b, c, d);
    }

    public static void glTexParameterf(int a, int b, float c) {
        GL11.glTexParameterf(a, b, c);
    }

    public static void glSelectBuffer(IntBuffer a) {
        GL11.glSelectBuffer(a);
    }

    public static void glGetPixelMapusv(int a, long b) {
        GL11.glGetPixelMapusv(a, b);
    }

    public static void glGetPixelMapuiv(int a, long b) {
        GL11.glGetPixelMapuiv(a, b);
    }

    public static void glDrawElements(int a, int b, int c, long d) {
        GL11.glDrawElements(a, b, c, d);
    }

    public static void glDrawElements(int a, IntBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, ByteBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, ShortBuffer b) {
        GL11.glDrawElements(a, b);
    }

    public static void glDrawElements(int a, int b, int c, ByteBuffer d) {
        GL11.glDrawElements(a, b, c, d);
    }

    public static void glEnableClientState(int a) {
        GL11.glEnableClientState(a);
    }

    public static void glClearStencil(int a) {
        GL11.glClearStencil(a);
    }

    public static void glDisableClientState(int a) {
        GL11.glDisableClientState(a);
    }

    public static void glEdgeFlagPointer(int a, ByteBuffer b) {
        GL11.glEdgeFlagPointer(a, b);
    }

    public static void glEdgeFlagPointer(int a, long b) {
        GL11.glEdgeFlagPointer(a, b);
    }

    public static void glGetPixelMapfv(int a, long b) {
        GL11.glGetPixelMapfv(a, b);
    }

    public static void glGetPixelMapu(int a, ShortBuffer b) {
        GL11.glGetPixelMapu(a, b);
    }

    public static void glGetPixelMapu(int a, IntBuffer b) {
        GL11.glGetPixelMapu(a, b);
    }

    public static void glFeedbackBuffer(int a, FloatBuffer b) {
        GL11.glFeedbackBuffer(a, b);
    }

    public static void glClearAccum(float a, float b, float c, float d) {
        GL11.glClearAccum(a, b, c, d);
    }

    public static void glClear(int a) {
        GL11.glClear(a);
    }

    public static void glCallList(int a) {
        GL11.glCallList(a);
    }

    public static void glCallLists(ShortBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glCallLists(IntBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glCallLists(ByteBuffer a) {
        GL11.glCallLists(a);
    }

    public static void glAccum(int a, float b) {
        GL11.glAccum(a, b);
    }

    public static void glClearColor(float a, float b, float c, float d) {
        GL11.glClearColor(a, b, c, d);
    }

    public static void glAlphaFunc(int a, float b) {
        GL11.glAlphaFunc(a, b);
    }

    public static void glDeleteLists(int a, int b) {
        GL11.glDeleteLists(a, b);
    }

    public static void glCullFace(int a) {
        GL11.glCullFace(a);
    }

    public static void glColorMask(boolean a, boolean b, boolean c, boolean d) {
        GL11.glColorMask(a, b, c, d);
    }

    public static void glBitmap(int a, int b, float c, float d, float e, float f, ByteBuffer g) {
        GL11.glBitmap(a, b, c, d, e, f, g);
    }

    public static void glBitmap(int a, int b, float c, float d, float e, float f, long g) {
        GL11.glBitmap(a, b, c, d, e, f, g);
    }

    public static void glBlendFunc(int a, int b) {
        GL11.glBlendFunc(a, b);
    }

    public static void glClearDepth(double a) {
        GL11.glClearDepth(a);
    }

    public static void glCopyPixels(int a, int b, int c, int d, int e) {
        GL11.glCopyPixels(a, b, c, d, e);
    }

    public static void glBegin(int a) {
        GL11.glBegin(a);
    }

    public static void glBindTexture(int a, int b) {
        GL11.glBindTexture(a, b);
        switch (a) {
            case GL_TEXTURE_1D:
                textureUnits1d[activeTexture] = b;
                break;
            case GL_TEXTURE_2D:
                textureUnits2d[activeTexture] = b;
                break;
            case GL_TEXTURE_3D:
                textureUnits3d[activeTexture] = b;
                break;
            case GL_TEXTURE_1D_ARRAY:
                textureUnits1dArr[activeTexture] = b;
                break;
            case GL_TEXTURE_2D_ARRAY:
                textureUnits2dArr[activeTexture] = b;
                break;
            case GL_TEXTURE_RECTANGLE:
                textureUnitsRect[activeTexture] = b;
                break;
            case GL_TEXTURE_CUBE_MAP:
                textureUnitsCube[activeTexture] = b;
                break;
            case GL_TEXTURE_CUBE_MAP_ARRAY:
                textureUnitsCubeArr[activeTexture] = b;
                break;
            case GL_TEXTURE_BUFFER:
                textureUnitsBuf[activeTexture] = b;
                break;
            case GL_TEXTURE_2D_MULTISAMPLE:
                textureUnitsSampled[activeTexture] = b;
                break;
            case GL_TEXTURE_2D_MULTISAMPLE_ARRAY:
                textureUnitsSampledArr[activeTexture] = b;
                break;
        }
    }

    public static void glEnd() {
        GL11.glEnd();
    }

    public static void glPrioritizeTextures(IntBuffer a, FloatBuffer b) {
        GL11.glPrioritizeTextures(a, b);
    }

    public static void glArrayElement(int a) {
        GL11.glArrayElement(a);
    }

    public static void glDeleteTextures(IntBuffer a) {
        GL11.glDeleteTextures(a);
    }

    public static void glDeleteTextures(int a) {
        GL11.glDeleteTextures(a);
    }

    public static void glCopyTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL11.glCopyTexSubImage2D(a, b, c, d, e, f, g, h);
    }

    public static boolean glAreTexturesResident(IntBuffer a, ByteBuffer b) {
        return GL11.glAreTexturesResident(a, b);
    }

    public static void glCopyTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL11.glCopyTexImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCopyTexSubImage1D(int a, int b, int c, int d, int e, int f) {
        GL11.glCopyTexSubImage1D(a, b, c, d, e, f);
    }

    public static void glCopyTexImage1D(int a, int b, int c, int d, int e, int f, int g) {
        GL11.glCopyTexImage1D(a, b, c, d, e, f, g);
    }

    public static void glColorPointer(int a, int b, DoubleBuffer c) {
        GL11.glColorPointer(a, b, c);
    }

    public static void glColorPointer(int a, int b, int c, long d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorPointer(int a, boolean b, int c, ByteBuffer d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorPointer(int a, int b, FloatBuffer c) {
        GL11.glColorPointer(a, b, c);
    }

    public static void glColorPointer(int a, int b, int c, ByteBuffer d) {
        GL11.glColorPointer(a, b, c, d);
    }

    public static void glColorMaterial(int a, int b) {
        GL11.glColorMaterial(a, b);
    }

    public static int glGetTexGeni(int a, int b) {
        return GL11.glGetTexGeni(a, b);
    }

    public static void glMap2f(int a, float b, float c, int d, int e, float f, float g, int h, int i, FloatBuffer j) {
        GL11.glMap2f(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glGetTexEnv(int a, int b, IntBuffer c) {
        GL11.glGetTexEnv(a, b, c);
    }

    public static void glGetTexEnv(int a, int b, FloatBuffer c) {
        GL11.glGetTexEnv(a, b, c);
    }

    public static float glGetTexEnvf(int a, int b) {
        return GL11.glGetTexEnvf(a, b);
    }

    public static void glMapGrid1d(int a, double b, double c) {
        GL11.glMapGrid1d(a, b, c);
    }

    public static void glColor3b(byte a, byte b, byte c) {
        GL11.glColor3b(a, b, c);
    }

    public static void glHint(int a, int b) {
        GL11.glHint(a, b);
    }

    public static void glMaterial(int a, int b, IntBuffer c) {
        GL11.glMaterial(a, b, c);
    }

    public static void glMaterial(int a, int b, FloatBuffer c) {
        GL11.glMaterial(a, b, c);
    }

    public static int glGetTexEnvi(int a, int b) {
        return GL11.glGetTexEnvi(a, b);
    }

    public static void glMapGrid1f(int a, float b, float c) {
        GL11.glMapGrid1f(a, b, c);
    }

    public static void glMapGrid2d(int a, double b, double c, int d, double e, double f) {
        GL11.glMapGrid2d(a, b, c, d, e, f);
    }

    public static void glInitNames() {
        GL11.glInitNames();
    }

    public static ByteBuffer glGetPointer(int a, long b) {
        return GL11.glGetPointer(a, b);
    }

    public static void glMaterialf(int a, int b, float c) {
        GL11.glMaterialf(a, b, c);
    }

    public static double glGetTexGend(int a, int b) {
        return GL11.glGetTexGend(a, b);
    }

    public static boolean glIsEnabled(int a) {
        return GL11.glIsEnabled(a);
    }

    public static void glGetTexImage(int a, int b, int c, int d, ByteBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, FloatBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, DoubleBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, IntBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, ShortBuffer e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexImage(int a, int b, int c, int d, long e) {
        GL11.glGetTexImage(a, b, c, d, e);
    }

    public static void glGetTexGen(int a, int b, DoubleBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static void glGetTexGen(int a, int b, FloatBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static void glGetTexGen(int a, int b, IntBuffer c) {
        GL11.glGetTexGen(a, b, c);
    }

    public static float glGetTexGenf(int a, int b) {
        return GL11.glGetTexGenf(a, b);
    }

    public static boolean glIsList(int a) {
        return GL11.glIsList(a);
    }

    public static String glGetString(int a) {
        return GL11.glGetString(a);
    }

    public static void glMapGrid2f(int a, float b, float c, int d, float e, float f) {
        GL11.glMapGrid2f(a, b, c, d, e, f);
    }

    public static void glMateriali(int a, int b, int c) {
        GL11.glMateriali(a, b, c);
    }

    public static void glListBase(int a) {
        GL11.glListBase(a);
    }

    public static void glLineWidth(float a) {
        GL11.glLineWidth(a);
    }

    public static void glMap1d(int a, double b, double c, int d, int e, DoubleBuffer f) {
        GL11.glMap1d(a, b, c, d, e, f);
    }

    public static void glLightModeli(int a, int b) {
        GL11.glLightModeli(a, b);
    }

    public static void glLightf(int a, int b, float c) {
        GL11.glLightf(a, b, c);
    }

    public static void glLighti(int a, int b, int c) {
        GL11.glLighti(a, b, c);
    }

    public static void glMap1f(int a, float b, float c, int d, int e, FloatBuffer f) {
        GL11.glMap1f(a, b, c, d, e, f);
    }

    public static void glPointSize(float a) {
        GL11.glPointSize(a);
    }

    public static void glMatrixMode(int a) {
        GL11.glMatrixMode(a);
    }

    public static void glPixelZoom(float a, float b) {
        GL11.glPixelZoom(a, b);
    }

    public static void glPixelStoref(int a, float b) {
        GL11.glPixelStoref(a, b);
    }

    public static void glPixelStorei(int a, int b) {
        GL11.glPixelStorei(a, b);
    }

    public static void glPixelMap(int a, FloatBuffer b) {
        GL11.glPixelMap(a, b);
    }

    public static void glPixelMapu(int a, IntBuffer b) {
        GL11.glPixelMapu(a, b);
    }

    public static void glPixelMapu(int a, ShortBuffer b) {
        GL11.glPixelMapu(a, b);
    }

    public static void glPixelMapuiv(int a, int b, long c) {
        GL11.glPixelMapuiv(a, b, c);
    }

    public static void glPolygonMode(int a, int b) {
        GL11.glPolygonMode(a, b);
    }

    public static void glPixelMapusv(int a, int b, long c) {
        GL11.glPixelMapusv(a, b, c);
    }

    public static void glPassThrough(float a) {
        GL11.glPassThrough(a);
    }

    public static void glOrtho(double a, double b, double c, double d, double e, double f) {
        GL11.glOrtho(a, b, c, d, e, f);
    }

    public static void glLight(int a, int b, IntBuffer c) {
        GL11.glLight(a, b, c);
    }

    public static void glLight(int a, int b, FloatBuffer c) {
        GL11.glLight(a, b, c);
    }

    public static void glLoadName(int a) {
        GL11.glLoadName(a);
    }

    public static void glLogicOp(int a) {
        GL11.glLogicOp(a);
    }

    public static boolean glIsTexture(int a) {
        return GL11.glIsTexture(a);
    }

    public static void glNormal3b(byte a, byte b, byte c) {
        GL11.glNormal3b(a, b, c);
    }

    public static void glLineStipple(int a, short b) {
        GL11.glLineStipple(a, b);
    }

    public static void glMap2d(int a, double b, double c, int d, int e, double f, double g, int h, int i, DoubleBuffer j) {
        GL11.glMap2d(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glLightModel(int a, FloatBuffer b) {
        GL11.glLightModel(a, b);
    }

    public static void glLightModel(int a, IntBuffer b) {
        GL11.glLightModel(a, b);
    }

    public static void glLoadMatrix(FloatBuffer a) {
        GL11.glLoadMatrix(a);
    }

    public static void glLoadMatrix(DoubleBuffer a) {
        GL11.glLoadMatrix(a);
    }

    public static void glPixelMapfv(int a, int b, long c) {
        GL11.glPixelMapfv(a, b, c);
    }

    public static void glLightModelf(int a, float b) {
        GL11.glLightModelf(a, b);
    }

    public static void glEvalCoord2d(double a, double b) {
        GL11.glEvalCoord2d(a, b);
    }

    public static void glEnable(int a) {
        GL11.glEnable(a);
//        if(!enabled.add(a)) {
//            try {
//                throw new OpenGLException(String.format("tried enabling 0x%04X when already enabled", a));
//            } catch (OpenGLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void glColor3d(double a, double b, double c) {
        GL11.glColor3d(a, b, c);
    }

    public static void glEvalCoord1f(float a) {
        GL11.glEvalCoord1f(a);
    }

    public static void glDisable(int a) {
        GL11.glDisable(a);
//        if(!enabled.remove(a)) {
//            try {
//                throw new OpenGLException(String.format("tried disabling 0x%04X when already disabled", a));
//            } catch (OpenGLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void glEvalCoord1d(double a) {
        GL11.glEvalCoord1d(a);
    }

    public static void glEdgeFlag(boolean a) {
        GL11.glEdgeFlag(a);
    }

    public static void glEvalCoord2f(float a, float b) {
        GL11.glEvalCoord2f(a, b);
    }

    public static void glDrawPixels(int a, int b, int c, int d, ByteBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, IntBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, ShortBuffer e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glDrawPixels(int a, int b, int c, int d, long e) {
        GL11.glDrawPixels(a, b, c, d, e);
    }

    public static void glColor3f(float a, float b, float c) {
        GL11.glColor3f(a, b, c);
    }

    public static void glColor4ub(byte a, byte b, byte c, byte d) {
        GL11.glColor4ub(a, b, c, d);
    }

    public static void glEvalMesh2(int a, int b, int c, int d, int e) {
        GL11.glEvalMesh2(a, b, c, d, e);
    }

    public static void glColor4f(float a, float b, float c, float d) {
        GL11.glColor4f(a, b, c, d);
    }

    public static void glEvalPoint1(int a) {
        GL11.glEvalPoint1(a);
    }

    public static void glEvalMesh1(int a, int b, int c) {
        GL11.glEvalMesh1(a, b, c);
    }

    public static void glColor4d(double a, double b, double c, double d) {
        GL11.glColor4d(a, b, c, d);
    }

    public static void glColor3ub(byte a, byte b, byte c) {
        GL11.glColor3ub(a, b, c);
    }

    public static void glEvalPoint2(int a, int b) {
        GL11.glEvalPoint2(a, b);
    }

    public static void glColor4b(byte a, byte b, byte c, byte d) {
        GL11.glColor4b(a, b, c, d);
    }

    public static void glClipPlane(int a, DoubleBuffer b) {
        GL11.glClipPlane(a, b);
    }

    public static void glDepthMask(boolean a) {
        GL11.glDepthMask(a);
    }

    public static void glGenTextures(IntBuffer a) {
        GL11.glGenTextures(a);
    }

    public static int glGenTextures() {
        return GL11.glGenTextures();
    }

    public static void glFrontFace(int a) {
        GL11.glFrontFace(a);
    }

    public static void glFogi(int a, int b) {
        GL11.glFogi(a, b);
    }

    public static int glGenLists(int a) {
        return GL11.glGenLists(a);
    }

    public static void glFrustum(double a, double b, double c, double d, double e, double f) {
        GL11.glFrustum(a, b, c, d, e, f);
    }

    public static void glFog(int a, FloatBuffer b) {
        GL11.glFog(a, b);
    }

    public static void glFog(int a, IntBuffer b) {
        GL11.glFog(a, b);
    }

    public static void glFlush() {
        GL11.glFlush();
    }

    public static void glFinish() {
        GL11.glFinish();
    }

    public static void glFogf(int a, float b) {
        GL11.glFogf(a, b);
    }

    public static int glGetInteger(int a) {
        switch (a) {
            case GL_MAX_TEXTURE_UNITS:
                return textureUnits1d.length;
            case GL_TEXTURE_BINDING_1D:
                return textureUnits1d[activeTexture];
            case GL_TEXTURE_BINDING_2D:
                return textureUnits2d[activeTexture];
            case GL_TEXTURE_BINDING_3D:
                return textureUnits3d[activeTexture];
            case GL_TEXTURE_BINDING_1D_ARRAY:
                return textureUnits1dArr[activeTexture];
            case GL_TEXTURE_BINDING_2D_ARRAY:
                return textureUnits2dArr[activeTexture];
            case GL_TEXTURE_BINDING_RECTANGLE:
                return textureUnitsRect[activeTexture];
            case GL_TEXTURE_BINDING_BUFFER:
                return textureUnitsBuf[activeTexture];
            case GL_TEXTURE_BINDING_CUBE_MAP:
                return textureUnitsCube[activeTexture];
            case GL_TEXTURE_BINDING_CUBE_MAP_ARRAY:
                return textureUnitsCubeArr[activeTexture];
            case GL_TEXTURE_BINDING_2D_MULTISAMPLE:
                return textureUnitsSampled[activeTexture];
            case GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY:
                return textureUnitsSampledArr[activeTexture];
            case GL_ACTIVE_TEXTURE:
                return activeTexture + GL_TEXTURE0;
            default:
                return GL11.glGetInteger(a);
        }
    }

    public static void glGetInteger(int a, IntBuffer b) {
        GL11.glGetInteger(a, b);
    }

    public static void glDepthFunc(int a) {
        GL11.glDepthFunc(a);
    }

    public static void glGetFloat(int a, FloatBuffer b) {
        GL11.glGetFloat(a, b);
    }

    public static float glGetFloat(int a) {
        return GL11.glGetFloat(a);
    }

    public static void glGetMaterial(int a, int b, IntBuffer c) {
        GL11.glGetMaterial(a, b, c);
    }

    public static void glGetMaterial(int a, int b, FloatBuffer c) {
        GL11.glGetMaterial(a, b, c);
    }

    public static void glDrawBuffer(int a) {
        GL11.glDrawBuffer(a);
    }

    public static void glGetPixelMap(int a, FloatBuffer b) {
        GL11.glGetPixelMap(a, b);
    }

    public static void glGetMap(int a, int b, FloatBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetMap(int a, int b, DoubleBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetMap(int a, int b, IntBuffer c) {
        GL11.glGetMap(a, b, c);
    }

    public static void glGetLight(int a, int b, IntBuffer c) {
        GL11.glGetLight(a, b, c);
    }

    public static void glGetLight(int a, int b, FloatBuffer c) {
        GL11.glGetLight(a, b, c);
    }

    public static int glGetError() {
        return GL11.glGetError();
    }

    public static void glGetBoolean(int a, ByteBuffer b) {
        GL11.glGetBoolean(a, b);
    }

    public static boolean glGetBoolean(int a) {
        return GL11.glGetBoolean(a);
    }

    public static void glGetDouble(int a, DoubleBuffer b) {
        GL11.glGetDouble(a, b);
    }

    public static double glGetDouble(int a) {
        return GL11.glGetDouble(a);
    }

    public static void glDepthRange(double a, double b) {
        GL11.glDepthRange(a, b);
    }

    public static void glDrawArrays(int a, int b, int c) {
        GL11.glDrawArrays(a, b, c);
    }

    public static void glTexCoord4d(double a, double b, double c, double d) {
        GL11.glTexCoord4d(a, b, c, d);
    }

    public static void glTexCoord4f(float a, float b, float c, float d) {
        GL11.glTexCoord4f(a, b, c, d);
    }

    public static void glStencilMask(int a) {
        GL11.glStencilMask(a);
    }

    public static void glStencilOp(int a, int b, int c) {
        GL11.glStencilOp(a, b, c);
    }

    public static void glViewport(int a, int b, int c, int d) {
        GL11.glViewport(a, b, c, d);
    }

    public static void glTexCoord3d(double a, double b, double c) {
        GL11.glTexCoord3d(a, b, c);
    }

    public static void glNormal3f(float a, float b, float c) {
        GL11.glNormal3f(a, b, c);
    }

    public static void glRasterPos4f(float a, float b, float c, float d) {
        GL11.glRasterPos4f(a, b, c, d);
    }

    public static void glRasterPos4i(int a, int b, int c, int d) {
        GL11.glRasterPos4i(a, b, c, d);
    }

    public static void glPushName(int a) {
        GL11.glPushName(a);
    }

    public static void glRasterPos2i(int a, int b) {
        GL11.glRasterPos2i(a, b);
    }

    public static void glRectd(double a, double b, double c, double d) {
        GL11.glRectd(a, b, c, d);
    }

    public static void glRectf(float a, float b, float c, float d) {
        GL11.glRectf(a, b, c, d);
    }

    public static void glPopName() {
        GL11.glPopName();
    }

    public static void glNewList(int a, int b) {
        GL11.glNewList(a, b);
    }

    public static void glRasterPos4d(double a, double b, double c, double d) {
        GL11.glRasterPos4d(a, b, c, d);
    }

    public static void glPushMatrix() {
        GL11.glPushMatrix();
    }

    public static void glScaled(double a, double b, double c) {
        GL11.glScaled(a, b, c);
    }

    public static void glPopMatrix() {
        GL11.glPopMatrix();
    }

    public static void glPushAttrib(int a) {
        GL11.glPushAttrib(a);
    }

    public static void glRotated(double a, double b, double c, double d) {
        GL11.glRotated(a, b, c, d);
    }

    public static void glNormal3d(double a, double b, double c) {
        GL11.glNormal3d(a, b, c);
    }

    public static void glScalef(float a, float b, float c) {
        GL11.glScalef(a, b, c);
    }

    public static void glReadBuffer(int a) {
        GL11.glReadBuffer(a);
    }

    public static void glRasterPos2d(double a, double b) {
        GL11.glRasterPos2d(a, b);
    }

    public static void glShadeModel(int a) {
        GL11.glShadeModel(a);
    }

    public static void glRasterPos3d(double a, double b, double c) {
        GL11.glRasterPos3d(a, b, c);
    }

    public static void glScissor(int a, int b, int c, int d) {
        GL11.glScissor(a, b, c, d);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, DoubleBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, long g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, ShortBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, IntBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glReadPixels(int a, int b, int c, int d, int e, int f, FloatBuffer g) {
        GL11.glReadPixels(a, b, c, d, e, f, g);
    }

    public static void glRotatef(float a, float b, float c, float d) {
        GL11.glRotatef(a, b, c, d);
    }

    public static void glNormal3i(int a, int b, int c) {
        GL11.glNormal3i(a, b, c);
    }

    public static void glRecti(int a, int b, int c, int d) {
        GL11.glRecti(a, b, c, d);
    }

    public static void glRasterPos2f(float a, float b) {
        GL11.glRasterPos2f(a, b);
    }

    public static void glRasterPos3f(float a, float b, float c) {
        GL11.glRasterPos3f(a, b, c);
    }

    public static void glRasterPos3i(int a, int b, int c) {
        GL11.glRasterPos3i(a, b, c);
    }

    public static void glMultMatrix(FloatBuffer a) {
        GL11.glMultMatrix(a);
    }

    public static void glMultMatrix(DoubleBuffer a) {
        GL11.glMultMatrix(a);
    }

    public static int glRenderMode(int a) {
        return GL11.glRenderMode(a);
    }

    public static void glEndList() {
        GL11.glEndList();
    }

    public static void glVertex2f(float a, float b) {
        GL11.glVertex2f(a, b);
    }

    public static void glTexCoord1d(double a) {
        GL11.glTexCoord1d(a);
    }

    public static void glTexCoord2f(float a, float b) {
        GL11.glTexCoord2f(a, b);
    }

    public static void glVertex3f(float a, float b, float c) {
        GL11.glVertex3f(a, b, c);
    }

    public static void glPopAttrib() {
        GL11.glPopAttrib();
    }

    public static void glVertex3i(int a, int b, int c) {
        GL11.glVertex3i(a, b, c);
    }

    public static void glVertex4i(int a, int b, int c, int d) {
        GL11.glVertex4i(a, b, c, d);
    }

    public static void glTexEnvi(int a, int b, int c) {
        GL11.glTexEnvi(a, b, c);
    }

    public static void glVertex4f(float a, float b, float c, float d) {
        GL11.glVertex4f(a, b, c, d);
    }

    public static void glVertex3d(double a, double b, double c) {
        GL11.glVertex3d(a, b, c);
    }

    public static void glVertex4d(double a, double b, double c, double d) {
        GL11.glVertex4d(a, b, c, d);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, IntBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, FloatBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, DoubleBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ByteBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexImage2D(int a, int b, int c, int d, int e, int f, int g, int h, ShortBuffer i) {
        GL11.glTexImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glTexGend(int a, int b, double c) {
        GL11.glTexGend(a, b, c);
    }

    public static void glTexGeni(int a, int b, int c) {
        GL11.glTexGeni(a, b, c);
    }

    public static void glTexEnvf(int a, int b, float c) {
        GL11.glTexEnvf(a, b, c);
    }

    public static void glTranslated(double a, double b, double c) {
        GL11.glTranslated(a, b, c);
    }

    public static void glVertex2i(int a, int b) {
        GL11.glVertex2i(a, b);
    }

    public static void glTexGenf(int a, int b, float c) {
        GL11.glTexGenf(a, b, c);
    }

    public static void glTexGen(int a, int b, IntBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexGen(int a, int b, FloatBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexGen(int a, int b, DoubleBuffer c) {
        GL11.glTexGen(a, b, c);
    }

    public static void glTexCoord2d(double a, double b) {
        GL11.glTexCoord2d(a, b);
    }

    public static void glStencilFunc(int a, int b, int c) {
        GL11.glStencilFunc(a, b, c);
    }

    public static void glTexCoord3f(float a, float b, float c) {
        GL11.glTexCoord3f(a, b, c);
    }

    public static void glVertex2d(double a, double b) {
        GL11.glVertex2d(a, b);
    }

    public static void glTranslatef(float a, float b, float c) {
        GL11.glTranslatef(a, b, c);
    }

    public static void glTexEnv(int a, int b, FloatBuffer c) {
        GL11.glTexEnv(a, b, c);
    }

    public static void glTexEnv(int a, int b, IntBuffer c) {
        GL11.glTexEnv(a, b, c);
    }

    public static void glTexCoord1f(float a) {
        GL11.glTexCoord1f(a);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, ShortBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, FloatBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, IntBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, DoubleBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage1D(int a, int b, int c, int d, int e, int f, int g, long h) {
        GL11.glTexImage1D(a, b, c, d, e, f, g, h);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, IntBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, FloatBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, DoubleBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ByteBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ShortBuffer j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, long j) {
        GL12.glTexImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glCopyTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        GL12.glCopyTexSubImage3D(a, b, c, d, e, f, g, h, i);
    }

    public static void glDrawRangeElements(int a, int b, int c, ByteBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glDrawRangeElements(int a, int b, int c, int d, int e, long f) {
        GL12.glDrawRangeElements(a, b, c, d, e, f);
    }

    public static void glDrawRangeElements(int a, int b, int c, IntBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glDrawRangeElements(int a, int b, int c, ShortBuffer d) {
        GL12.glDrawRangeElements(a, b, c, d);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, long k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, DoubleBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ByteBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, FloatBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ShortBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, IntBuffer k) {
        GL12.glTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glMultiTexCoord1f(int a, float b) {
        GL13.glMultiTexCoord1f(a, b);
    }

    public static void glClientActiveTexture(int a) {
        GL13.glClientActiveTexture(a);
    }

    public static void glCompressedTexSubImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL13.glCompressedTexSubImage1D(a, b, c, d, e, f, g);
    }

    public static void glCompressedTexSubImage1D(int a, int b, int c, int d, int e, ByteBuffer f) {
        GL13.glCompressedTexSubImage1D(a, b, c, d, e, f);
    }

    public static void glCompressedTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, long k) {
        GL13.glCompressedTexSubImage3D(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glCompressedTexSubImage3D(int a, int b, int c, int d, int e, int f, int g, int h, int i, ByteBuffer j) {
        GL13.glCompressedTexSubImage3D(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glGetCompressedTexImage(int a, int b, ShortBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, ByteBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, long c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glGetCompressedTexImage(int a, int b, IntBuffer c) {
        GL13.glGetCompressedTexImage(a, b, c);
    }

    public static void glCompressedTexImage1D(int a, int b, int c, int d, int e, ByteBuffer f) {
        GL13.glCompressedTexImage1D(a, b, c, d, e, f);
    }

    public static void glCompressedTexImage1D(int a, int b, int c, int d, int e, int f, long g) {
        GL13.glCompressedTexImage1D(a, b, c, d, e, f, g);
    }

    public static void glActiveTexture(int a) {
        GL13.glActiveTexture(a);
        activeTexture = a - GL_TEXTURE0;
    }

    public static void glCompressedTexImage2D(int a, int b, int c, int d, int e, int f, int g, long h) {
        GL13.glCompressedTexImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexImage2D(int a, int b, int c, int d, int e, int f, ByteBuffer g) {
        GL13.glCompressedTexImage2D(a, b, c, d, e, f, g);
    }

    public static void glCompressedTexImage3D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL13.glCompressedTexImage3D(a, b, c, d, e, f, g, h, i);
    }

    public static void glCompressedTexImage3D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL13.glCompressedTexImage3D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, ByteBuffer h) {
        GL13.glCompressedTexSubImage2D(a, b, c, d, e, f, g, h);
    }

    public static void glCompressedTexSubImage2D(int a, int b, int c, int d, int e, int f, int g, int h, long i) {
        GL13.glCompressedTexSubImage2D(a, b, c, d, e, f, g, h, i);
    }

    public static void glMultiTexCoord3d(int a, double b, double c, double d) {
        GL13.glMultiTexCoord3d(a, b, c, d);
    }

    public static void glMultTransposeMatrix(DoubleBuffer a) {
        GL13.glMultTransposeMatrix(a);
    }

    public static void glMultTransposeMatrix(FloatBuffer a) {
        GL13.glMultTransposeMatrix(a);
    }

    public static void glMultiTexCoord4f(int a, float b, float c, float d, float e) {
        GL13.glMultiTexCoord4f(a, b, c, d, e);
    }

    public static void glMultiTexCoord1d(int a, double b) {
        GL13.glMultiTexCoord1d(a, b);
    }

    public static void glLoadTransposeMatrix(DoubleBuffer a) {
        GL13.glLoadTransposeMatrix(a);
    }

    public static void glLoadTransposeMatrix(FloatBuffer a) {
        GL13.glLoadTransposeMatrix(a);
    }

    public static void glSampleCoverage(float a, boolean b) {
        GL13.glSampleCoverage(a, b);
    }

    public static void glMultiTexCoord3f(int a, float b, float c, float d) {
        GL13.glMultiTexCoord3f(a, b, c, d);
    }

    public static void glMultiTexCoord2f(int a, float b, float c) {
        GL13.glMultiTexCoord2f(a, b, c);
    }

    public static void glMultiTexCoord2d(int a, double b, double c) {
        GL13.glMultiTexCoord2d(a, b, c);
    }

    public static void glMultiTexCoord4d(int a, double b, double c, double d, double e) {
        GL13.glMultiTexCoord4d(a, b, c, d, e);
    }

    public static void glWindowPos3f(float a, float b, float c) {
        GL14.glWindowPos3f(a, b, c);
    }

    public static void glWindowPos2f(float a, float b) {
        GL14.glWindowPos2f(a, b);
    }

    public static void glWindowPos2i(int a, int b) {
        GL14.glWindowPos2i(a, b);
    }

    public static void glBlendColor(float a, float b, float c, float d) {
        GL14.glBlendColor(a, b, c, d);
    }

    public static void glWindowPos2d(double a, double b) {
        GL14.glWindowPos2d(a, b);
    }

    public static void glWindowPos3d(double a, double b, double c) {
        GL14.glWindowPos3d(a, b, c);
    }

    public static void glFogCoordf(float a) {
        GL14.glFogCoordf(a);
    }

    public static void glWindowPos3i(int a, int b, int c) {
        GL14.glWindowPos3i(a, b, c);
    }

    public static void glFogCoordd(double a) {
        GL14.glFogCoordd(a);
    }

    public static void glFogCoordPointer(int a, DoubleBuffer b) {
        GL14.glFogCoordPointer(a, b);
    }

    public static void glFogCoordPointer(int a, FloatBuffer b) {
        GL14.glFogCoordPointer(a, b);
    }

    public static void glFogCoordPointer(int a, int b, long c) {
        GL14.glFogCoordPointer(a, b, c);
    }

    public static void glPointParameterf(int a, float b) {
        GL14.glPointParameterf(a, b);
    }

    public static void glMultiDrawArrays(int a, IntBuffer b, IntBuffer c) {
        GL14.glMultiDrawArrays(a, b, c);
    }

    public static void glSecondaryColor3f(float a, float b, float c) {
        GL14.glSecondaryColor3f(a, b, c);
    }

    public static void glSecondaryColor3b(byte a, byte b, byte c) {
        GL14.glSecondaryColor3b(a, b, c);
    }

    public static void glSecondaryColor3d(double a, double b, double c) {
        GL14.glSecondaryColor3d(a, b, c);
    }

    public static void glPointParameteri(int a, int b) {
        GL14.glPointParameteri(a, b);
    }

    public static void glBlendFuncSeparate(int a, int b, int c, int d) {
        GL14.glBlendFuncSeparate(a, b, c, d);
    }

    public static void glSecondaryColorPointer(int a, int b, DoubleBuffer c) {
        GL14.glSecondaryColorPointer(a, b, c);
    }

    public static void glSecondaryColorPointer(int a, int b, int c, long d) {
        GL14.glSecondaryColorPointer(a, b, c, d);
    }

    public static void glSecondaryColorPointer(int a, int b, FloatBuffer c) {
        GL14.glSecondaryColorPointer(a, b, c);
    }

    public static void glSecondaryColorPointer(int a, boolean b, int c, ByteBuffer d) {
        GL14.glSecondaryColorPointer(a, b, c, d);
    }

    public static void glBlendEquation(int a) {
        GL14.glBlendEquation(a);
    }

    public static void glPointParameter(int a, FloatBuffer b) {
        GL14.glPointParameter(a, b);
    }

    public static void glPointParameter(int a, IntBuffer b) {
        GL14.glPointParameter(a, b);
    }

    public static void glSecondaryColor3ub(byte a, byte b, byte c) {
        GL14.glSecondaryColor3ub(a, b, c);
    }

    public static void glBindBuffer(int a, int b) {
        GL15.glBindBuffer(a, b);
    }

    public static boolean glIsBuffer(int a) {
        return GL15.glIsBuffer(a);
    }

    public static void glBufferData(int a, DoubleBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, FloatBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, long b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, ByteBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, IntBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static void glBufferData(int a, ShortBuffer b, int c) {
        GL15.glBufferData(a, b, c);
    }

    public static ByteBuffer glMapBuffer(int a, int b, ByteBuffer c) {
        return GL15.glMapBuffer(a, b, c);
    }

    public static ByteBuffer glMapBuffer(int a, int b, long c, ByteBuffer d) {
        return GL15.glMapBuffer(a, b, c, d);
    }

    public static boolean glUnmapBuffer(int a) {
        return GL15.glUnmapBuffer(a);
    }

    public static int glGenQueries() {
        return GL15.glGenQueries();
    }

    public static void glGenQueries(IntBuffer a) {
        GL15.glGenQueries(a);
    }

    public static boolean glIsQuery(int a) {
        return GL15.glIsQuery(a);
    }

    public static int glGenBuffers() {
        return GL15.glGenBuffers();
    }

    public static void glGenBuffers(IntBuffer a) {
        GL15.glGenBuffers(a);
    }

    public static void glBeginQuery(int a, int b) {
        GL15.glBeginQuery(a, b);
    }

    public static void glEndQuery(int a) {
        GL15.glEndQuery(a);
    }

    public static void glGetQuery(int a, int b, IntBuffer c) {
        GL15.glGetQuery(a, b, c);
    }

    public static int glGetQuery(int a, int b) {
        return GL15.glGetQuery(a, b);
    }

    public static int glGetQueryi(int a, int b) {
        return GL15.glGetQueryi(a, b);
    }

    public static void glBufferSubData(int a, long b, ByteBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, ShortBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, DoubleBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, IntBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glBufferSubData(int a, long b, FloatBuffer c) {
        GL15.glBufferSubData(a, b, c);
    }

    public static void glDeleteBuffers(IntBuffer a) {
        GL15.glDeleteBuffers(a);
    }

    public static void glDeleteBuffers(int a) {
        GL15.glDeleteBuffers(a);
    }

    public static void glGetBufferParameter(int a, int b, IntBuffer c) {
        GL15.glGetBufferParameter(a, b, c);
    }

    public static int glGetBufferParameteri(int a, int b) {
        return GL15.glGetBufferParameteri(a, b);
    }

    public static ByteBuffer glGetBufferPointer(int a, int b) {
        return GL15.glGetBufferPointer(a, b);
    }

    public static void glDeleteQueries(IntBuffer a) {
        GL15.glDeleteQueries(a);
    }

    public static void glDeleteQueries(int a) {
        GL15.glDeleteQueries(a);
    }

    public static void glGetQueryObject(int a, int b, IntBuffer c) {
        GL15.glGetQueryObject(a, b, c);
    }

    public static int glGetQueryObjecti(int a, int b) {
        return GL15.glGetQueryObjecti(a, b);
    }

    public static void glGetQueryObjectu(int a, int b, IntBuffer c) {
        GL15.glGetQueryObjectu(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, ByteBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, ShortBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, IntBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, FloatBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static void glGetBufferSubData(int a, long b, DoubleBuffer c) {
        GL15.glGetBufferSubData(a, b, c);
    }

    public static int glGetQueryObjectui(int a, int b) {
        return GL15.glGetQueryObjectui(a, b);
    }

    public static void glStencilMaskSeparate(int a, int b) {
        GL20.glStencilMaskSeparate(a, b);
    }

    public static void glBlendEquationSeparate(int a, int b) {
        GL20.glBlendEquationSeparate(a, b);
    }

    public static void glUniform3(int a, IntBuffer b) {
        GL20.glUniform3(a, b);
    }

    public static void glUniform3(int a, FloatBuffer b) {
        GL20.glUniform3(a, b);
    }

    public static void glUniform1i(int a, int b) {
        GL20.glUniform1i(a, b);
    }

    public static void glUniform1f(int a, float b) {
        GL20.glUniform1f(a, b);
    }

    public static boolean glIsShader(int a) {
        return GL20.glIsShader(a);
    }

    public static boolean glIsProgram(int a) {
        return GL20.glIsProgram(a);
    }

    public static void glUniform2i(int a, int b, int c) {
        GL20.glUniform2i(a, b, c);
    }

    public static void glLinkProgram(int a) {
        GL20.glLinkProgram(a);
    }

    public static void glUniform1(int a, FloatBuffer b) {
        GL20.glUniform1(a, b);
    }

    public static void glUniform1(int a, IntBuffer b) {
        GL20.glUniform1(a, b);
    }

    public static void glUniform4i(int a, int b, int c, int d, int e) {
        GL20.glUniform4i(a, b, c, d, e);
    }

    public static void glUniform4f(int a, float b, float c, float d, float e) {
        GL20.glUniform4f(a, b, c, d, e);
    }

    public static void glUniform2f(int a, float b, float c) {
        GL20.glUniform2f(a, b, c);
    }

    public static void glUniform3f(int a, float b, float c, float d) {
        GL20.glUniform3f(a, b, c, d);
    }

    public static void glUniform3i(int a, int b, int c, int d) {
        GL20.glUniform3i(a, b, c, d);
    }

    public static void glUseProgram(int a) {
        GL20.glUseProgram(a);
    }

    public static void glUniform2(int a, FloatBuffer b) {
        GL20.glUniform2(a, b);
    }

    public static void glUniform2(int a, IntBuffer b) {
        GL20.glUniform2(a, b);
    }

    public static void glUniform4(int a, IntBuffer b) {
        GL20.glUniform4(a, b);
    }

    public static void glUniform4(int a, FloatBuffer b) {
        GL20.glUniform4(a, b);
    }

    public static int glGetProgrami(int a, int b) {
        return GL20.glGetProgrami(a, b);
    }

    public static void glGetShader(int a, int b, IntBuffer c) {
        GL20.glGetShader(a, b, c);
    }

    public static int glGetShader(int a, int b) {
        return GL20.glGetShader(a, b);
    }

    public static int glGetShaderi(int a, int b) {
        return GL20.glGetShaderi(a, b);
    }

    public static void glGetUniform(int a, int b, IntBuffer c) {
        GL20.glGetUniform(a, b, c);
    }

    public static void glGetUniform(int a, int b, FloatBuffer c) {
        GL20.glGetUniform(a, b, c);
    }

    public static void glDrawBuffers(int a) {
        GL20.glDrawBuffers(a);
    }

    public static void glDrawBuffers(IntBuffer a) {
        GL20.glDrawBuffers(a);
    }

    public static void glGetProgram(int a, int b, IntBuffer c) {
        GL20.glGetProgram(a, b, c);
    }

    public static int glGetProgram(int a, int b) {
        return GL20.glGetProgram(a, b);
    }

    public static int glCreateShader(int a) {
        return GL20.glCreateShader(a);
    }

    public static void glShaderSource(int a, CharSequence b) {
        GL20.glShaderSource(a, b);
    }

    public static void glShaderSource(int a, ByteBuffer b) {
        GL20.glShaderSource(a, b);
    }

    public static void glShaderSource(int a, CharSequence[] b) {
        GL20.glShaderSource(a, b);
    }

    public static void glDeleteShader(int a) {
        GL20.glDeleteShader(a);
    }

    public static void glDetachShader(int a, int b) {
        GL20.glDetachShader(a, b);
    }

    public static void glVertexAttrib1d(int a, double b) {
        GL20.glVertexAttrib1d(a, b);
    }

    public static void glGetAttachedShaders(int a, IntBuffer b, IntBuffer c) {
        GL20.glGetAttachedShaders(a, b, c);
    }

    public static void glUniformMatrix3(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix3(a, b, c);
    }

    public static int glGetActiveUniformSize(int a, int b) {
        return GL20.glGetActiveUniformSize(a, b);
    }

    public static void glVertexAttrib1s(int a, short b) {
        GL20.glVertexAttrib1s(a, b);
    }

    public static void glVertexAttrib1f(int a, float b) {
        GL20.glVertexAttrib1f(a, b);
    }

    public static void glVertexAttrib2s(int a, short b, short c) {
        GL20.glVertexAttrib2s(a, b, c);
    }

    public static void glUniformMatrix4(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix4(a, b, c);
    }

    public static void glGetShaderInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetShaderInfoLog(a, b, c);
    }

    public static String glGetShaderInfoLog(int a, int b) {
        return GL20.glGetShaderInfoLog(a, b);
    }

    public static String glGetProgramInfoLog(int a, int b) {
        return GL20.glGetProgramInfoLog(a, b);
    }

    public static void glGetProgramInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetProgramInfoLog(a, b, c);
    }

    public static void glAttachShader(int a, int b) {
        GL20.glAttachShader(a, b);
    }

    public static void glValidateProgram(int a) {
        GL20.glValidateProgram(a);
    }

    public static void glDeleteProgram(int a) {
        GL20.glDeleteProgram(a);
    }

    public static void glGetActiveUniform(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL20.glGetActiveUniform(a, b, c, d, e, f);
    }

    public static String glGetActiveUniform(int a, int b, int c) {
        return GL20.glGetActiveUniform(a, b, c);
    }

    public static String glGetActiveUniform(int a, int b, int c, IntBuffer d) {
        return GL20.glGetActiveUniform(a, b, c, d);
    }

    public static void glGetShaderSource(int a, IntBuffer b, ByteBuffer c) {
        GL20.glGetShaderSource(a, b, c);
    }

    public static String glGetShaderSource(int a, int b) {
        return GL20.glGetShaderSource(a, b);
    }

    public static void glVertexAttrib2f(int a, float b, float c) {
        GL20.glVertexAttrib2f(a, b, c);
    }

    public static void glVertexAttrib2d(int a, double b, double c) {
        GL20.glVertexAttrib2d(a, b, c);
    }

    public static void glVertexAttrib3s(int a, short b, short c, short d) {
        GL20.glVertexAttrib3s(a, b, c, d);
    }

    public static int glGetUniformLocation(int a, ByteBuffer b) {
        return GL20.glGetUniformLocation(a, b);
    }

    public static int glGetUniformLocation(int a, CharSequence b) {
        return GL20.glGetUniformLocation(a, b);
    }

    public static void glUniformMatrix2(int a, boolean b, FloatBuffer c) {
        GL20.glUniformMatrix2(a, b, c);
    }

    public static void glVertexAttrib3f(int a, float b, float c, float d) {
        GL20.glVertexAttrib3f(a, b, c, d);
    }

    public static void glVertexAttrib3d(int a, double b, double c, double d) {
        GL20.glVertexAttrib3d(a, b, c, d);
    }

    public static void glVertexAttrib4s(int a, short b, short c, short d, short e) {
        GL20.glVertexAttrib4s(a, b, c, d, e);
    }

    public static void glCompileShader(int a) {
        GL20.glCompileShader(a);
    }

    public static int glGetActiveUniformType(int a, int b) {
        return GL20.glGetActiveUniformType(a, b);
    }

    public static int glCreateProgram() {
        return GL20.glCreateProgram();
    }

    public static void glVertexAttrib4d(int a, double b, double c, double d, double e) {
        GL20.glVertexAttrib4d(a, b, c, d, e);
    }

    public static void glVertexAttrib4Nub(int a, byte b, byte c, byte d, byte e) {
        GL20.glVertexAttrib4Nub(a, b, c, d, e);
    }

    public static void glGetVertexAttrib(int a, int b, FloatBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glGetVertexAttrib(int a, int b, IntBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glGetVertexAttrib(int a, int b, DoubleBuffer c) {
        GL20.glGetVertexAttrib(a, b, c);
    }

    public static void glBindAttribLocation(int a, int b, CharSequence c) {
        GL20.glBindAttribLocation(a, b, c);
    }

    public static void glBindAttribLocation(int a, int b, ByteBuffer c) {
        GL20.glBindAttribLocation(a, b, c);
    }

    public static int glGetActiveAttribType(int a, int b) {
        return GL20.glGetActiveAttribType(a, b);
    }

    public static void glStencilFuncSeparate(int a, int b, int c, int d) {
        GL20.glStencilFuncSeparate(a, b, c, d);
    }

    public static void glVertexAttribPointer(int a, int b, int c, boolean d, int e, long f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, int d, FloatBuffer e) {
        GL20.glVertexAttribPointer(a, b, c, d, e);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, ByteBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, IntBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, boolean d, int e, ShortBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, int c, boolean d, int e, ByteBuffer f) {
        GL20.glVertexAttribPointer(a, b, c, d, e, f);
    }

    public static void glVertexAttribPointer(int a, int b, boolean c, int d, DoubleBuffer e) {
        GL20.glVertexAttribPointer(a, b, c, d, e);
    }

    public static void glVertexAttrib4f(int a, float b, float c, float d, float e) {
        GL20.glVertexAttrib4f(a, b, c, d, e);
    }

    public static void glGetVertexAttribPointer(int a, int b, ByteBuffer c) {
        GL20.glGetVertexAttribPointer(a, b, c);
    }

    public static ByteBuffer glGetVertexAttribPointer(int a, int b, long c) {
        return GL20.glGetVertexAttribPointer(a, b, c);
    }

    public static void glDisableVertexAttribArray(int a) {
        GL20.glDisableVertexAttribArray(a);
    }

    public static int glGetActiveAttribSize(int a, int b) {
        return GL20.glGetActiveAttribSize(a, b);
    }

    public static int glGetAttribLocation(int a, ByteBuffer b) {
        return GL20.glGetAttribLocation(a, b);
    }

    public static int glGetAttribLocation(int a, CharSequence b) {
        return GL20.glGetAttribLocation(a, b);
    }

    public static void glEnableVertexAttribArray(int a) {
        GL20.glEnableVertexAttribArray(a);
    }

    public static String glGetActiveAttrib(int a, int b, int c, IntBuffer d) {
        return GL20.glGetActiveAttrib(a, b, c, d);
    }

    public static String glGetActiveAttrib(int a, int b, int c) {
        return GL20.glGetActiveAttrib(a, b, c);
    }

    public static void glGetActiveAttrib(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL20.glGetActiveAttrib(a, b, c, d, e, f);
    }

    public static void glStencilOpSeparate(int a, int b, int c, int d) {
        GL20.glStencilOpSeparate(a, b, c, d);
    }

    public static void glUniformMatrix2x3(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix2x3(a, b, c);
    }

    public static void glUniformMatrix4x2(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix4x2(a, b, c);
    }

    public static void glUniformMatrix3x2(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix3x2(a, b, c);
    }

    public static void glUniformMatrix2x4(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix2x4(a, b, c);
    }

    public static void glUniformMatrix3x4(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix3x4(a, b, c);
    }

    public static void glUniformMatrix4x3(int a, boolean b, FloatBuffer c) {
        GL21.glUniformMatrix4x3(a, b, c);
    }

    public static int glGetFramebufferAttachmentParameteri(int a, int b, int c) {
        return GL30.glGetFramebufferAttachmentParameteri(a, b, c);
    }

    public static int glGetFramebufferAttachmentParameter(int a, int b, int c) {
        return GL30.glGetFramebufferAttachmentParameter(a, b, c);
    }

    public static void glGetFramebufferAttachmentParameter(int a, int b, int c, IntBuffer d) {
        GL30.glGetFramebufferAttachmentParameter(a, b, c, d);
    }

    public static void glRenderbufferStorageMultisample(int a, int b, int c, int d, int e) {
        GL30.glRenderbufferStorageMultisample(a, b, c, d, e);
    }

    public static void glUniform3ui(int a, int b, int c, int d) {
        GL30.glUniform3ui(a, b, c, d);
    }

    public static void glClearBuffer(int a, int b, IntBuffer c) {
        GL30.glClearBuffer(a, b, c);
    }

    public static void glClearBuffer(int a, int b, FloatBuffer c) {
        GL30.glClearBuffer(a, b, c);
    }

    public static String glGetStringi(int a, int b) {
        return GL30.glGetStringi(a, b);
    }

    public static void glUniform1ui(int a, int b) {
        GL30.glUniform1ui(a, b);
    }

    public static void glUniform4ui(int a, int b, int c, int d, int e) {
        GL30.glUniform4ui(a, b, c, d, e);
    }

    public static void glUniform2ui(int a, int b, int c) {
        GL30.glUniform2ui(a, b, c);
    }

    public static void glVertexAttribI1(int a, IntBuffer b) {
        GL30.glVertexAttribI1(a, b);
    }

    public static void glVertexAttribI3(int a, IntBuffer b) {
        GL30.glVertexAttribI3(a, b);
    }

    public static void glVertexAttribI4(int a, ShortBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI4(int a, IntBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI4(int a, ByteBuffer b) {
        GL30.glVertexAttribI4(a, b);
    }

    public static void glVertexAttribI3u(int a, IntBuffer b) {
        GL30.glVertexAttribI3u(a, b);
    }

    public static void glVertexAttribI1i(int a, int b) {
        GL30.glVertexAttribI1i(a, b);
    }

    public static void glVertexAttribI4u(int a, IntBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4u(int a, ByteBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4u(int a, ShortBuffer b) {
        GL30.glVertexAttribI4u(a, b);
    }

    public static void glVertexAttribI4ui(int a, int b, int c, int d, int e) {
        GL30.glVertexAttribI4ui(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, IntBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, ShortBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, ByteBuffer e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribIPointer(int a, int b, int c, int d, long e) {
        GL30.glVertexAttribIPointer(a, b, c, d, e);
    }

    public static void glVertexAttribI1ui(int a, int b) {
        GL30.glVertexAttribI1ui(a, b);
    }

    public static void glClearBufferfi(int a, int b, float c, int d) {
        GL30.glClearBufferfi(a, b, c, d);
    }

    public static void glVertexAttribI1u(int a, IntBuffer b) {
        GL30.glVertexAttribI1u(a, b);
    }

    public static void glVertexAttribI2i(int a, int b, int c) {
        GL30.glVertexAttribI2i(a, b, c);
    }

    public static void glVertexAttribI2u(int a, IntBuffer b) {
        GL30.glVertexAttribI2u(a, b);
    }

    public static void glVertexAttribI3ui(int a, int b, int c, int d) {
        GL30.glVertexAttribI3ui(a, b, c, d);
    }

    public static void glClearBufferu(int a, int b, IntBuffer c) {
        GL30.glClearBufferu(a, b, c);
    }

    public static void glVertexAttribI4i(int a, int b, int c, int d, int e) {
        GL30.glVertexAttribI4i(a, b, c, d, e);
    }

    public static void glVertexAttribI2(int a, IntBuffer b) {
        GL30.glVertexAttribI2(a, b);
    }

    public static void glVertexAttribI2ui(int a, int b, int c) {
        GL30.glVertexAttribI2ui(a, b, c);
    }

    public static void glVertexAttribI3i(int a, int b, int c, int d) {
        GL30.glVertexAttribI3i(a, b, c, d);
    }

    public static void glBindRenderbuffer(int a, int b) {
        GL30.glBindRenderbuffer(a, b);
    }

    public static int glGetFragDataLocation(int a, ByteBuffer b) {
        return GL30.glGetFragDataLocation(a, b);
    }

    public static int glGetFragDataLocation(int a, CharSequence b) {
        return GL30.glGetFragDataLocation(a, b);
    }

    public static ByteBuffer glMapBufferRange(int a, long b, long c, int d, ByteBuffer e) {
        return GL30.glMapBufferRange(a, b, c, d, e);
    }

    public static void glDeleteRenderbuffers(IntBuffer a) {
        GL30.glDeleteRenderbuffers(a);
    }

    public static void glDeleteRenderbuffers(int a) {
        GL30.glDeleteRenderbuffers(a);
    }

    public static int glCheckFramebufferStatus(int a) {
        return GL30.glCheckFramebufferStatus(a);
    }

    public static void glRenderbufferStorage(int a, int b, int c, int d) {
        GL30.glRenderbufferStorage(a, b, c, d);
    }

    public static int glGetRenderbufferParameter(int a, int b) {
        return GL30.glGetRenderbufferParameter(a, b);
    }

    public static void glGetRenderbufferParameter(int a, int b, IntBuffer c) {
        GL30.glGetRenderbufferParameter(a, b, c);
    }

    public static void glFramebufferTexture1D(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTexture1D(a, b, c, d, e);
    }

    public static int glGetRenderbufferParameteri(int a, int b) {
        return GL30.glGetRenderbufferParameteri(a, b);
    }

    public static void glFramebufferTexture3D(int a, int b, int c, int d, int e, int f) {
        GL30.glFramebufferTexture3D(a, b, c, d, e, f);
    }

    public static void glGenerateMipmap(int a) {
        GL30.glGenerateMipmap(a);
    }

    public static void glGetVertexAttribI(int a, int b, IntBuffer c) {
        GL30.glGetVertexAttribI(a, b, c);
    }

    public static void glGetVertexAttribIu(int a, int b, IntBuffer c) {
        GL30.glGetVertexAttribIu(a, b, c);
    }

    public static void glBindFragDataLocation(int a, int b, ByteBuffer c) {
        GL30.glBindFragDataLocation(a, b, c);
    }

    public static void glBindFragDataLocation(int a, int b, CharSequence c) {
        GL30.glBindFragDataLocation(a, b, c);
    }

    public static boolean glIsRenderbuffer(int a) {
        return GL30.glIsRenderbuffer(a);
    }

    public static int glGenRenderbuffers() {
        return GL30.glGenRenderbuffers();
    }

    public static void glGenRenderbuffers(IntBuffer a) {
        GL30.glGenRenderbuffers(a);
    }

    public static void glFramebufferTexture2D(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTexture2D(a, b, c, d, e);
    }

    public static void glFramebufferRenderbuffer(int a, int b, int c, int d) {
        GL30.glFramebufferRenderbuffer(a, b, c, d);
    }

    public static void glBlitFramebuffer(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j) {
        GL30.glBlitFramebuffer(a, b, c, d, e, f, g, h, i, j);
    }

    public static void glTexParameterI(int a, int b, IntBuffer c) {
        GL30.glTexParameterI(a, b, c);
    }

    public static void glGenFramebuffers(IntBuffer a) {
        GL30.glGenFramebuffers(a);
    }

    public static int glGenFramebuffers() {
        return GL30.glGenFramebuffers();
    }

    public static void glEndConditionalRender() {
        GL30.glEndConditionalRender();
    }

    public static void glDeleteFramebuffers(int a) {
        GL30.glDeleteFramebuffers(a);
    }

    public static void glDeleteFramebuffers(IntBuffer a) {
        GL30.glDeleteFramebuffers(a);
    }

    public static void glTexParameterIi(int a, int b, int c) {
        GL30.glTexParameterIi(a, b, c);
    }

    public static void glTexParameterIu(int a, int b, IntBuffer c) {
        GL30.glTexParameterIu(a, b, c);
    }

    public static boolean glIsFramebuffer(int a) {
        return GL30.glIsFramebuffer(a);
    }

    public static void glBeginConditionalRender(int a, int b) {
        GL30.glBeginConditionalRender(a, b);
    }

    public static void glBindFramebuffer(int a, int b) {
        GL30.glBindFramebuffer(a, b);
    }

    public static void glTexParameterIui(int a, int b, int c) {
        GL30.glTexParameterIui(a, b, c);
    }

    public static void glFlushMappedBufferRange(int a, long b, long c) {
        GL30.glFlushMappedBufferRange(a, b, c);
    }

    public static void glGetTexParameterI(int a, int b, IntBuffer c) {
        GL30.glGetTexParameterI(a, b, c);
    }

    public static int glGetTexParameterIui(int a, int b) {
        return GL30.glGetTexParameterIui(a, b);
    }

    public static void glDeleteVertexArrays(IntBuffer a) {
        GL30.glDeleteVertexArrays(a);
    }

    public static void glDeleteVertexArrays(int a) {
        GL30.glDeleteVertexArrays(a);
    }

    public static void glBindBufferRange(int a, int b, int c, long d, long e) {
        GL30.glBindBufferRange(a, b, c, d, e);
    }

    public static void glGetTexParameterIu(int a, int b, IntBuffer c) {
        GL30.glGetTexParameterIu(a, b, c);
    }

    public static void glEndTransformFeedback() {
        GL30.glEndTransformFeedback();
    }

    public static boolean glIsVertexArray(int a) {
        return GL30.glIsVertexArray(a);
    }

    public static void glFramebufferTextureLayer(int a, int b, int c, int d, int e) {
        GL30.glFramebufferTextureLayer(a, b, c, d, e);
    }

    public static void glGetTransformFeedbackVarying(int a, int b, IntBuffer c, IntBuffer d, IntBuffer e, ByteBuffer f) {
        GL30.glGetTransformFeedbackVarying(a, b, c, d, e, f);
    }

    public static String glGetTransformFeedbackVarying(int a, int b, int c, IntBuffer d, IntBuffer e) {
        return GL30.glGetTransformFeedbackVarying(a, b, c, d, e);
    }

    public static void glTransformFeedbackVaryings(int a, int b, ByteBuffer c, int d) {
        GL30.glTransformFeedbackVaryings(a, b, c, d);
    }

    public static void glTransformFeedbackVaryings(int a, CharSequence[] b, int c) {
        GL30.glTransformFeedbackVaryings(a, b, c);
    }

    public static void glBeginTransformFeedback(int a) {
        GL30.glBeginTransformFeedback(a);
    }

    public static void glBindVertexArray(int a) {
        GL30.glBindVertexArray(a);
    }

    public static void glBindBufferBase(int a, int b, int c) {
        GL30.glBindBufferBase(a, b, c);
    }

    public static int glGenVertexArrays() {
        return GL30.glGenVertexArrays();
    }

    public static void glGenVertexArrays(IntBuffer a) {
        GL30.glGenVertexArrays(a);
    }

    public static int glGetTexParameterIi(int a, int b) {
        return GL30.glGetTexParameterIi(a, b);
    }

    public static void glUniform3u(int a, IntBuffer b) {
        GL30.glUniform3u(a, b);
    }

    public static void glClampColor(int a, int b) {
        GL30.glClampColor(a, b);
    }

    public static void glGetUniformu(int a, int b, IntBuffer c) {
        GL30.glGetUniformu(a, b, c);
    }

    public static void glUniform2u(int a, IntBuffer b) {
        GL30.glUniform2u(a, b);
    }

    public static void glUniform4u(int a, IntBuffer b) {
        GL30.glUniform4u(a, b);
    }

    public static void glEnablei(int a, int b) {
        GL30.glEnablei(a, b);
    }

    public static boolean glIsEnabledi(int a, int b) {
        return GL30.glIsEnabledi(a, b);
    }

    public static void glColorMaski(int a, boolean b, boolean c, boolean d, boolean e) {
        GL30.glColorMaski(a, b, c, d, e);
    }

    public static void glDisablei(int a, int b) {
        GL30.glDisablei(a, b);
    }

    public static int glGetInteger(int a, int b) {
        return GL30.glGetInteger(a, b);
    }

    public static void glGetInteger(int a, int b, IntBuffer c) {
        GL30.glGetInteger(a, b, c);
    }

    public static void glGetBoolean(int a, int b, ByteBuffer c) {
        GL30.glGetBoolean(a, b, c);
    }

    public static boolean glGetBoolean(int a, int b) {
        return GL30.glGetBoolean(a, b);
    }

    public static void glUniform1u(int a, IntBuffer b) {
        GL30.glUniform1u(a, b);
    }

    public static void glGetUniformIndices(int a, CharSequence[] b, IntBuffer c) {
        GL31.glGetUniformIndices(a, b, c);
    }

    public static void glGetUniformIndices(int a, ByteBuffer b, IntBuffer c) {
        GL31.glGetUniformIndices(a, b, c);
    }

    public static int glGetActiveUniforms(int a, int b, int c) {
        return GL31.glGetActiveUniforms(a, b, c);
    }

    public static void glGetActiveUniforms(int a, IntBuffer b, int c, IntBuffer d) {
        GL31.glGetActiveUniforms(a, b, c, d);
    }

    public static String glGetActiveUniformName(int a, int b, int c) {
        return GL31.glGetActiveUniformName(a, b, c);
    }

    public static void glGetActiveUniformName(int a, int b, IntBuffer c, ByteBuffer d) {
        GL31.glGetActiveUniformName(a, b, c, d);
    }

    public static void glUniformBlockBinding(int a, int b, int c) {
        GL31.glUniformBlockBinding(a, b, c);
    }

    public static void glDrawArraysInstanced(int a, int b, int c, int d) {
        GL31.glDrawArraysInstanced(a, b, c, d);
    }

    public static int glGetActiveUniformsi(int a, int b, int c) {
        return GL31.glGetActiveUniformsi(a, b, c);
    }

    public static String glGetActiveUniformBlockName(int a, int b, int c) {
        return GL31.glGetActiveUniformBlockName(a, b, c);
    }

    public static void glGetActiveUniformBlockName(int a, int b, IntBuffer c, ByteBuffer d) {
        GL31.glGetActiveUniformBlockName(a, b, c, d);
    }

    public static void glDrawElementsInstanced(int a, IntBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, ShortBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, ByteBuffer b, int c) {
        GL31.glDrawElementsInstanced(a, b, c);
    }

    public static void glDrawElementsInstanced(int a, int b, int c, long d, int e) {
        GL31.glDrawElementsInstanced(a, b, c, d, e);
    }

    public static void glPrimitiveRestartIndex(int a) {
        GL31.glPrimitiveRestartIndex(a);
    }

    public static int glGetActiveUniformBlocki(int a, int b, int c) {
        return GL31.glGetActiveUniformBlocki(a, b, c);
    }

    public static int glGetUniformBlockIndex(int a, ByteBuffer b) {
        return GL31.glGetUniformBlockIndex(a, b);
    }

    public static int glGetUniformBlockIndex(int a, CharSequence b) {
        return GL31.glGetUniformBlockIndex(a, b);
    }

    public static void glCopyBufferSubData(int a, int b, long c, long d, long e) {
        GL31.glCopyBufferSubData(a, b, c, d, e);
    }

    public static int glGetActiveUniformBlock(int a, int b, int c) {
        return GL31.glGetActiveUniformBlock(a, b, c);
    }

    public static void glGetActiveUniformBlock(int a, int b, int c, IntBuffer d) {
        GL31.glGetActiveUniformBlock(a, b, c, d);
    }

    public static void glTexBuffer(int a, int b, int c) {
        GL31.glTexBuffer(a, b, c);
    }

    public static long glGetInteger64(int a) {
        return GL32.glGetInteger64(a);
    }

    public static long glGetInteger64(int a, int b) {
        return GL32.glGetInteger64(a, b);
    }

    public static void glGetInteger64(int a, int b, LongBuffer c) {
        GL32.glGetInteger64(a, b, c);
    }

    public static void glGetInteger64(int a, LongBuffer b) {
        GL32.glGetInteger64(a, b);
    }

    public static int glClientWaitSync(GLSync a, int b, long c) {
        return GL32.glClientWaitSync(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, int b, int c, long d, int e) {
        GL32.glDrawElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawElementsBaseVertex(int a, ByteBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, ShortBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glDrawElementsBaseVertex(int a, IntBuffer b, int c) {
        GL32.glDrawElementsBaseVertex(a, b, c);
    }

    public static void glTexImage3DMultisample(int a, int b, int c, int d, int e, int f, boolean g) {
        GL32.glTexImage3DMultisample(a, b, c, d, e, f, g);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, int d, int e, long f, int g) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e, f, g);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, ByteBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, ShortBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glDrawRangeElementsBaseVertex(int a, int b, int c, IntBuffer d, int e) {
        GL32.glDrawRangeElementsBaseVertex(a, b, c, d, e);
    }

    public static void glProvokingVertex(int a) {
        GL32.glProvokingVertex(a);
    }

    public static void glTexImage2DMultisample(int a, int b, int c, int d, int e, boolean f) {
        GL32.glTexImage2DMultisample(a, b, c, d, e, f);
    }

    public static void glGetMultisample(int a, int b, FloatBuffer c) {
        GL32.glGetMultisample(a, b, c);
    }

    public static void glFramebufferTexture(int a, int b, int c, int d) {
        GL32.glFramebufferTexture(a, b, c, d);
    }

    public static long glGetBufferParameteri64(int a, int b) {
        return GL32.glGetBufferParameteri64(a, b);
    }

    public static void glSampleMaski(int a, int b) {
        GL32.glSampleMaski(a, b);
    }

    public static boolean glIsSync(GLSync a) {
        return GL32.glIsSync(a);
    }

    public static GLSync glFenceSync(int a, int b) {
        return GL32.glFenceSync(a, b);
    }

    public static void glWaitSync(GLSync a, int b, long c) {
        GL32.glWaitSync(a, b, c);
    }

    public static void glDeleteSync(GLSync a) {
        GL32.glDeleteSync(a);
    }

    public static int glGetSynci(GLSync a, int b) {
        return GL32.glGetSynci(a, b);
    }

    public static int glGetSync(GLSync a, int b) {
        return GL32.glGetSync(a, b);
    }

    public static void glGetSync(GLSync a, int b, IntBuffer c, IntBuffer d) {
        GL32.glGetSync(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, int b, int c, long d, int e, int f) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d, e, f);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, IntBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, ShortBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertex(int a, ByteBuffer b, int c, int d) {
        GL32.glDrawElementsInstancedBaseVertex(a, b, c, d);
    }

    public static void glGetBufferParameter(int a, int b, LongBuffer c) {
        GL32.glGetBufferParameter(a, b, c);
    }

    public static long glGetBufferParameter(int a, int b) {
        return GL32.glGetBufferParameter(a, b);
    }

    public static void glGetSamplerParameter(int a, int b, FloatBuffer c) {
        GL33.glGetSamplerParameter(a, b, c);
    }

    public static void glGetSamplerParameter(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameter(a, b, c);
    }

    public static int glGetSamplerParameterIi(int a, int b) {
        return GL33.glGetSamplerParameterIi(a, b);
    }

    public static void glBindFragDataLocationIndexed(int a, int b, int c, CharSequence d) {
        GL33.glBindFragDataLocationIndexed(a, b, c, d);
    }

    public static void glBindFragDataLocationIndexed(int a, int b, int c, ByteBuffer d) {
        GL33.glBindFragDataLocationIndexed(a, b, c, d);
    }

    public static void glSamplerParameteri(int a, int b, int c) {
        GL33.glSamplerParameteri(a, b, c);
    }

    public static void glDeleteSamplers(int a) {
        GL33.glDeleteSamplers(a);
    }

    public static void glDeleteSamplers(IntBuffer a) {
        GL33.glDeleteSamplers(a);
    }

    public static void glSamplerParameter(int a, int b, IntBuffer c) {
        GL33.glSamplerParameter(a, b, c);
    }

    public static void glSamplerParameter(int a, int b, FloatBuffer c) {
        GL33.glSamplerParameter(a, b, c);
    }

    public static void glSamplerParameterI(int a, int b, IntBuffer c) {
        GL33.glSamplerParameterI(a, b, c);
    }

    public static void glSamplerParameterf(int a, int b, float c) {
        GL33.glSamplerParameterf(a, b, c);
    }

    public static void glSamplerParameterIu(int a, int b, IntBuffer c) {
        GL33.glSamplerParameterIu(a, b, c);
    }

    public static float glGetSamplerParameterf(int a, int b) {
        return GL33.glGetSamplerParameterf(a, b);
    }

    public static void glGetSamplerParameterI(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameterI(a, b, c);
    }

    public static void glGetSamplerParameterIu(int a, int b, IntBuffer c) {
        GL33.glGetSamplerParameterIu(a, b, c);
    }

    public static int glGetSamplerParameterIui(int a, int b) {
        return GL33.glGetSamplerParameterIui(a, b);
    }

    public static int glGetFragDataIndex(int a, CharSequence b) {
        return GL33.glGetFragDataIndex(a, b);
    }

    public static int glGetFragDataIndex(int a, ByteBuffer b) {
        return GL33.glGetFragDataIndex(a, b);
    }

    public static int glGetSamplerParameteri(int a, int b) {
        return GL33.glGetSamplerParameteri(a, b);
    }

    public static void glQueryCounter(int a, int b) {
        GL33.glQueryCounter(a, b);
    }

    public static long glGetQueryObjecti64(int a, int b) {
        return GL33.glGetQueryObjecti64(a, b);
    }

    public static void glVertexAttribP4u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP4u(a, b, c, d);
    }

    public static void glVertexAttribDivisor(int a, int b) {
        GL33.glVertexAttribDivisor(a, b);
    }

    public static void glVertexAttribP3u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP3u(a, b, c, d);
    }

    public static long glGetQueryObjectui64(int a, int b) {
        return GL33.glGetQueryObjectui64(a, b);
    }

    public static void glTexCoordP2ui(int a, int b) {
        GL33.glTexCoordP2ui(a, b);
    }

    public static void glMultiTexCoordP4ui(int a, int b, int c) {
        GL33.glMultiTexCoordP4ui(a, b, c);
    }

    public static void glVertexAttribP2ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP2ui(a, b, c, d);
    }

    public static void glMultiTexCoordP4u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP4u(a, b, c);
    }

    public static void glTexCoordP4ui(int a, int b) {
        GL33.glTexCoordP4ui(a, b);
    }

    public static void glMultiTexCoordP2ui(int a, int b, int c) {
        GL33.glMultiTexCoordP2ui(a, b, c);
    }

    public static void glMultiTexCoordP3ui(int a, int b, int c) {
        GL33.glMultiTexCoordP3ui(a, b, c);
    }

    public static void glTexCoordP3ui(int a, int b) {
        GL33.glTexCoordP3ui(a, b);
    }

    public static void glMultiTexCoordP1u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP1u(a, b, c);
    }

    public static void glMultiTexCoordP2u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP2u(a, b, c);
    }

    public static void glSecondaryColorP3u(int a, IntBuffer b) {
        GL33.glSecondaryColorP3u(a, b);
    }

    public static void glTexCoordP1ui(int a, int b) {
        GL33.glTexCoordP1ui(a, b);
    }

    public static void glMultiTexCoordP3u(int a, int b, IntBuffer c) {
        GL33.glMultiTexCoordP3u(a, b, c);
    }

    public static void glVertexAttribP1ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP1ui(a, b, c, d);
    }

    public static void glVertexAttribP3ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP3ui(a, b, c, d);
    }

    public static void glVertexAttribP4ui(int a, int b, boolean c, int d) {
        GL33.glVertexAttribP4ui(a, b, c, d);
    }

    public static void glVertexAttribP1u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP1u(a, b, c, d);
    }

    public static void glVertexAttribP2u(int a, int b, boolean c, IntBuffer d) {
        GL33.glVertexAttribP2u(a, b, c, d);
    }

    public static void glMultiTexCoordP1ui(int a, int b, int c) {
        GL33.glMultiTexCoordP1ui(a, b, c);
    }

    public static void glSecondaryColorP3ui(int a, int b) {
        GL33.glSecondaryColorP3ui(a, b);
    }

    public static void glBindSampler(int a, int b) {
        GL33.glBindSampler(a, b);
    }

    public static void glGenSamplers(IntBuffer a) {
        GL33.glGenSamplers(a);
    }

    public static int glGenSamplers() {
        return GL33.glGenSamplers();
    }

    public static void glVertexP2ui(int a, int b) {
        GL33.glVertexP2ui(a, b);
    }

    public static boolean glIsSampler(int a) {
        return GL33.glIsSampler(a);
    }

    public static void glVertexP3ui(int a, int b) {
        GL33.glVertexP3ui(a, b);
    }

    public static void glVertexP3u(int a, IntBuffer b) {
        GL33.glVertexP3u(a, b);
    }

    public static void glColorP4u(int a, IntBuffer b) {
        GL33.glColorP4u(a, b);
    }

    public static void glTexCoordP3u(int a, IntBuffer b) {
        GL33.glTexCoordP3u(a, b);
    }

    public static void glColorP3ui(int a, int b) {
        GL33.glColorP3ui(a, b);
    }

    public static void glTexCoordP4u(int a, IntBuffer b) {
        GL33.glTexCoordP4u(a, b);
    }

    public static void glVertexP2u(int a, IntBuffer b) {
        GL33.glVertexP2u(a, b);
    }

    public static void glNormalP3ui(int a, int b) {
        GL33.glNormalP3ui(a, b);
    }

    public static void glVertexP4u(int a, IntBuffer b) {
        GL33.glVertexP4u(a, b);
    }

    public static void glColorP3u(int a, IntBuffer b) {
        GL33.glColorP3u(a, b);
    }

    public static void glVertexP4ui(int a, int b) {
        GL33.glVertexP4ui(a, b);
    }

    public static void glNormalP3u(int a, IntBuffer b) {
        GL33.glNormalP3u(a, b);
    }

    public static void glTexCoordP1u(int a, IntBuffer b) {
        GL33.glTexCoordP1u(a, b);
    }

    public static void glTexCoordP2u(int a, IntBuffer b) {
        GL33.glTexCoordP2u(a, b);
    }

    public static void glColorP4ui(int a, int b) {
        GL33.glColorP4ui(a, b);
    }

    public static void glGetQueryObject(int a, int b, LongBuffer c) {
        GL33.glGetQueryObject(a, b, c);
    }

    public static long glGetQueryObject(int a, int b) {
        return GL33.glGetQueryObject(a, b);
    }

    public static long glGetQueryObjectu(int a, int b) {
        return GL33.glGetQueryObjectu(a, b);
    }

    public static void glGetQueryObjectu(int a, int b, LongBuffer c) {
        GL33.glGetQueryObjectu(a, b, c);
    }

    public static void glUniformMatrix2x3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2x3(a, b, c);
    }

    public static void glUniformMatrix4x2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4x2(a, b, c);
    }

    public static void glUniformMatrix3x2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3x2(a, b, c);
    }

    public static void glUniformMatrix2x4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2x4(a, b, c);
    }

    public static void glUniformMatrix3x4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3x4(a, b, c);
    }

    public static void glUniformMatrix4x3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4x3(a, b, c);
    }

    public static void glUniform3(int a, DoubleBuffer b) {
        GL40.glUniform3(a, b);
    }

    public static void glUniform1(int a, DoubleBuffer b) {
        GL40.glUniform1(a, b);
    }

    public static void glUniform2(int a, DoubleBuffer b) {
        GL40.glUniform2(a, b);
    }

    public static void glUniform4(int a, DoubleBuffer b) {
        GL40.glUniform4(a, b);
    }

    public static void glGetUniform(int a, int b, DoubleBuffer c) {
        GL40.glGetUniform(a, b, c);
    }

    public static void glDrawArraysIndirect(int a, long b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glDrawArraysIndirect(int a, ByteBuffer b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glDrawArraysIndirect(int a, IntBuffer b) {
        GL40.glDrawArraysIndirect(a, b);
    }

    public static void glBlendEquationi(int a, int b) {
        GL40.glBlendEquationi(a, b);
    }

    public static void glBlendFuncSeparatei(int a, int b, int c, int d, int e) {
        GL40.glBlendFuncSeparatei(a, b, c, d, e);
    }

    public static void glDrawElementsIndirect(int a, int b, IntBuffer c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glDrawElementsIndirect(int a, int b, long c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glDrawElementsIndirect(int a, int b, ByteBuffer c) {
        GL40.glDrawElementsIndirect(a, b, c);
    }

    public static void glBlendEquationSeparatei(int a, int b, int c) {
        GL40.glBlendEquationSeparatei(a, b, c);
    }

    public static void glUniformSubroutinesu(int a, IntBuffer b) {
        GL40.glUniformSubroutinesu(a, b);
    }

    public static int glGetUniformSubroutineui(int a, int b) {
        return GL40.glGetUniformSubroutineui(a, b);
    }

    public static void glGetQueryIndexed(int a, int b, int c, IntBuffer d) {
        GL40.glGetQueryIndexed(a, b, c, d);
    }

    public static int glGetQueryIndexed(int a, int b, int c) {
        return GL40.glGetQueryIndexed(a, b, c);
    }

    public static void glBindTransformFeedback(int a, int b) {
        GL40.glBindTransformFeedback(a, b);
    }

    public static void glDeleteTransformFeedbacks(int a) {
        GL40.glDeleteTransformFeedbacks(a);
    }

    public static void glDeleteTransformFeedbacks(IntBuffer a) {
        GL40.glDeleteTransformFeedbacks(a);
    }

    public static void glDrawTransformFeedback(int a, int b) {
        GL40.glDrawTransformFeedback(a, b);
    }

    public static int glGetSubroutineIndex(int a, int b, ByteBuffer c) {
        return GL40.glGetSubroutineIndex(a, b, c);
    }

    public static int glGetSubroutineIndex(int a, int b, CharSequence c) {
        return GL40.glGetSubroutineIndex(a, b, c);
    }

    public static void glPatchParameter(int a, FloatBuffer b) {
        GL40.glPatchParameter(a, b);
    }

    public static String glGetActiveSubroutineName(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineName(a, b, c, d);
    }

    public static void glGetActiveSubroutineName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL40.glGetActiveSubroutineName(a, b, c, d, e);
    }

    public static void glResumeTransformFeedback() {
        GL40.glResumeTransformFeedback();
    }

    public static void glEndQueryIndexed(int a, int b) {
        GL40.glEndQueryIndexed(a, b);
    }

    public static void glGetProgramStage(int a, int b, int c, IntBuffer d) {
        GL40.glGetProgramStage(a, b, c, d);
    }

    public static int glGetProgramStage(int a, int b, int c) {
        return GL40.glGetProgramStage(a, b, c);
    }

    public static void glGetActiveSubroutineUniform(int a, int b, int c, int d, IntBuffer e) {
        GL40.glGetActiveSubroutineUniform(a, b, c, d, e);
    }

    public static int glGetActiveSubroutineUniform(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniform(a, b, c, d);
    }

    public static void glGetUniformSubroutineu(int a, int b, IntBuffer c) {
        GL40.glGetUniformSubroutineu(a, b, c);
    }

    public static int glGetUniformSubroutineu(int a, int b) {
        return GL40.glGetUniformSubroutineu(a, b);
    }

    public static void glPatchParameteri(int a, int b) {
        GL40.glPatchParameteri(a, b);
    }

    public static void glPauseTransformFeedback() {
        GL40.glPauseTransformFeedback();
    }

    public static int glGetActiveSubroutineUniformi(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniformi(a, b, c, d);
    }

    public static void glGenTransformFeedbacks(IntBuffer a) {
        GL40.glGenTransformFeedbacks(a);
    }

    public static int glGenTransformFeedbacks() {
        return GL40.glGenTransformFeedbacks();
    }

    public static boolean glIsTransformFeedback(int a) {
        return GL40.glIsTransformFeedback(a);
    }

    public static void glDrawTransformFeedbackStream(int a, int b, int c) {
        GL40.glDrawTransformFeedbackStream(a, b, c);
    }

    public static void glBeginQueryIndexed(int a, int b, int c) {
        GL40.glBeginQueryIndexed(a, b, c);
    }

    public static int glGetProgramStagei(int a, int b, int c) {
        return GL40.glGetProgramStagei(a, b, c);
    }

    public static int glGetQueryIndexedi(int a, int b, int c) {
        return GL40.glGetQueryIndexedi(a, b, c);
    }

    public static void glMinSampleShading(float a) {
        GL40.glMinSampleShading(a);
    }

    public static int glGetSubroutineUniformLocation(int a, int b, CharSequence c) {
        return GL40.glGetSubroutineUniformLocation(a, b, c);
    }

    public static int glGetSubroutineUniformLocation(int a, int b, ByteBuffer c) {
        return GL40.glGetSubroutineUniformLocation(a, b, c);
    }

    public static void glGetActiveSubroutineUniformName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL40.glGetActiveSubroutineUniformName(a, b, c, d, e);
    }

    public static String glGetActiveSubroutineUniformName(int a, int b, int c, int d) {
        return GL40.glGetActiveSubroutineUniformName(a, b, c, d);
    }

    public static void glBlendFunci(int a, int b, int c) {
        GL40.glBlendFunci(a, b, c);
    }

    public static void glUniform1d(int a, double b) {
        GL40.glUniform1d(a, b);
    }

    public static void glUniform2d(int a, double b, double c) {
        GL40.glUniform2d(a, b, c);
    }

    public static void glUniform3d(int a, double b, double c, double d) {
        GL40.glUniform3d(a, b, c, d);
    }

    public static void glUniform4d(int a, double b, double c, double d, double e) {
        GL40.glUniform4d(a, b, c, d, e);
    }

    public static void glUniformMatrix3(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix3(a, b, c);
    }

    public static void glUniformMatrix4(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix4(a, b, c);
    }

    public static void glUniformMatrix2(int a, boolean b, DoubleBuffer c) {
        GL40.glUniformMatrix2(a, b, c);
    }

    public static void glReleaseShaderCompiler() {
        GL41.glReleaseShaderCompiler();
    }

    public static void glActiveShaderProgram(int a, int b) {
        GL41.glActiveShaderProgram(a, b);
    }

    public static void glBindProgramPipeline(int a) {
        GL41.glBindProgramPipeline(a);
    }

    public static void glShaderBinary(IntBuffer a, int b, ByteBuffer c) {
        GL41.glShaderBinary(a, b, c);
    }

    public static void glProgramParameteri(int a, int b, int c) {
        GL41.glProgramParameteri(a, b, c);
    }

    public static boolean glIsProgramPipeline(int a) {
        return GL41.glIsProgramPipeline(a);
    }

    public static void glProgramUniform3i(int a, int b, int c, int d, int e) {
        GL41.glProgramUniform3i(a, b, c, d, e);
    }

    public static void glGetProgramPipeline(int a, int b, IntBuffer c) {
        GL41.glGetProgramPipeline(a, b, c);
    }

    public static void glGetProgramBinary(int a, IntBuffer b, IntBuffer c, ByteBuffer d) {
        GL41.glGetProgramBinary(a, b, c, d);
    }

    public static void glProgramBinary(int a, int b, ByteBuffer c) {
        GL41.glProgramBinary(a, b, c);
    }

    public static void glUseProgramStages(int a, int b, int c) {
        GL41.glUseProgramStages(a, b, c);
    }

    public static void glGetShaderPrecisionFormat(int a, int b, IntBuffer c, IntBuffer d) {
        GL41.glGetShaderPrecisionFormat(a, b, c, d);
    }

    public static int glCreateShaderProgram(int a, CharSequence b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, ByteBuffer[] b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, ByteBuffer b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, CharSequence[] b) {
        return GL41.glCreateShaderProgram(a, b);
    }

    public static int glCreateShaderProgram(int a, int b, ByteBuffer c) {
        return GL41.glCreateShaderProgram(a, b, c);
    }

    public static void glDeleteProgramPipelines(int a) {
        GL41.glDeleteProgramPipelines(a);
    }

    public static void glDeleteProgramPipelines(IntBuffer a) {
        GL41.glDeleteProgramPipelines(a);
    }

    public static void glGenProgramPipelines(IntBuffer a) {
        GL41.glGenProgramPipelines(a);
    }

    public static int glGenProgramPipelines() {
        return GL41.glGenProgramPipelines();
    }

    public static void glProgramUniform1i(int a, int b, int c) {
        GL41.glProgramUniform1i(a, b, c);
    }

    public static int glGetProgramPipelinei(int a, int b) {
        return GL41.glGetProgramPipelinei(a, b);
    }

    public static void glProgramUniform2i(int a, int b, int c, int d) {
        GL41.glProgramUniform2i(a, b, c, d);
    }

    public static void glProgramUniform4i(int a, int b, int c, int d, int e, int f) {
        GL41.glProgramUniform4i(a, b, c, d, e, f);
    }

    public static void glDepthRangef(float a, float b) {
        GL41.glDepthRangef(a, b);
    }

    public static void glClearDepthf(float a) {
        GL41.glClearDepthf(a);
    }

    public static float glGetFloat(int a, int b) {
        return GL41.glGetFloat(a, b);
    }

    public static void glGetFloat(int a, int b, FloatBuffer c) {
        GL41.glGetFloat(a, b, c);
    }

    public static void glGetDouble(int a, int b, DoubleBuffer c) {
        GL41.glGetDouble(a, b, c);
    }

    public static double glGetDouble(int a, int b) {
        return GL41.glGetDouble(a, b);
    }

    public static void glProgramUniform4(int a, int b, IntBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform4(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform4(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform4(a, b, c);
    }

    public static void glProgramUniform2ui(int a, int b, int c, int d) {
        GL41.glProgramUniform2ui(a, b, c, d);
    }

    public static void glProgramUniform2d(int a, int b, double c, double d) {
        GL41.glProgramUniform2d(a, b, c, d);
    }

    public static void glProgramUniform4d(int a, int b, double c, double d, double e, double f) {
        GL41.glProgramUniform4d(a, b, c, d, e, f);
    }

    public static void glProgramUniform1ui(int a, int b, int c) {
        GL41.glProgramUniform1ui(a, b, c);
    }

    public static void glProgramUniform1u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform1u(a, b, c);
    }

    public static void glProgramUniformMatrix3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3(a, b, c, d);
    }

    public static void glProgramUniformMatrix3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3(a, b, c, d);
    }

    public static void glProgramUniform2u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform2u(a, b, c);
    }

    public static void glProgramUniformMatrix2x3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2x3(a, b, c, d);
    }

    public static void glProgramUniformMatrix2x3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2x3(a, b, c, d);
    }

    public static void glProgramUniform2(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform2(int a, int b, IntBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform2(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform2(a, b, c);
    }

    public static void glProgramUniform1d(int a, int b, double c) {
        GL41.glProgramUniform1d(a, b, c);
    }

    public static void glProgramUniform4ui(int a, int b, int c, int d, int e, int f) {
        GL41.glProgramUniform4ui(a, b, c, d, e, f);
    }

    public static void glProgramUniform1f(int a, int b, float c) {
        GL41.glProgramUniform1f(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniform1(int a, int b, IntBuffer c) {
        GL41.glProgramUniform1(a, b, c);
    }

    public static void glProgramUniformMatrix3x2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3x2(a, b, c, d);
    }

    public static void glProgramUniformMatrix3x2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3x2(a, b, c, d);
    }

    public static void glProgramUniform3d(int a, int b, double c, double d, double e) {
        GL41.glProgramUniform3d(a, b, c, d, e);
    }

    public static void glProgramUniform3ui(int a, int b, int c, int d, int e) {
        GL41.glProgramUniform3ui(a, b, c, d, e);
    }

    public static void glProgramUniformMatrix2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2(a, b, c, d);
    }

    public static void glProgramUniformMatrix2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2(a, b, c, d);
    }

    public static void glProgramUniform3u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform3u(a, b, c);
    }

    public static void glProgramUniform4u(int a, int b, IntBuffer c) {
        GL41.glProgramUniform4u(a, b, c);
    }

    public static void glProgramUniformMatrix4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4(a, b, c, d);
    }

    public static void glProgramUniformMatrix4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4(a, b, c, d);
    }

    public static void glProgramUniform2f(int a, int b, float c, float d) {
        GL41.glProgramUniform2f(a, b, c, d);
    }

    public static void glProgramUniform3f(int a, int b, float c, float d, float e) {
        GL41.glProgramUniform3f(a, b, c, d, e);
    }

    public static void glProgramUniform4f(int a, int b, float c, float d, float e, float f) {
        GL41.glProgramUniform4f(a, b, c, d, e, f);
    }

    public static void glProgramUniform3(int a, int b, IntBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glProgramUniform3(int a, int b, DoubleBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glProgramUniform3(int a, int b, FloatBuffer c) {
        GL41.glProgramUniform3(a, b, c);
    }

    public static void glVertexAttribL3d(int a, double b, double c, double d) {
        GL41.glVertexAttribL3d(a, b, c, d);
    }

    public static void glVertexAttribL1d(int a, double b) {
        GL41.glVertexAttribL1d(a, b);
    }

    public static void glVertexAttribL2(int a, DoubleBuffer b) {
        GL41.glVertexAttribL2(a, b);
    }

    public static void glGetVertexAttribL(int a, int b, DoubleBuffer c) {
        GL41.glGetVertexAttribL(a, b, c);
    }

    public static void glProgramUniformMatrix2x4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix2x4(a, b, c, d);
    }

    public static void glProgramUniformMatrix2x4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix2x4(a, b, c, d);
    }

    public static void glVertexAttribL3(int a, DoubleBuffer b) {
        GL41.glVertexAttribL3(a, b);
    }

    public static void glScissorArray(int a, IntBuffer b) {
        GL41.glScissorArray(a, b);
    }

    public static void glDepthRangeIndexed(int a, double b, double c) {
        GL41.glDepthRangeIndexed(a, b, c);
    }

    public static void glViewportArray(int a, FloatBuffer b) {
        GL41.glViewportArray(a, b);
    }

    public static void glVertexAttribL4d(int a, double b, double c, double d, double e) {
        GL41.glVertexAttribL4d(a, b, c, d, e);
    }

    public static void glGetProgramPipelineInfoLog(int a, IntBuffer b, ByteBuffer c) {
        GL41.glGetProgramPipelineInfoLog(a, b, c);
    }

    public static String glGetProgramPipelineInfoLog(int a, int b) {
        return GL41.glGetProgramPipelineInfoLog(a, b);
    }

    public static void glProgramUniformMatrix4x2(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4x2(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x2(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4x2(a, b, c, d);
    }

    public static void glVertexAttribL1(int a, DoubleBuffer b) {
        GL41.glVertexAttribL1(a, b);
    }

    public static void glViewportIndexed(int a, FloatBuffer b) {
        GL41.glViewportIndexed(a, b);
    }

    public static void glVertexAttribL4(int a, DoubleBuffer b) {
        GL41.glVertexAttribL4(a, b);
    }

    public static void glScissorIndexed(int a, IntBuffer b) {
        GL41.glScissorIndexed(a, b);
    }

    public static void glScissorIndexed(int a, int b, int c, int d, int e) {
        GL41.glScissorIndexed(a, b, c, d, e);
    }

    public static void glVertexAttribLPointer(int a, int b, int c, long d) {
        GL41.glVertexAttribLPointer(a, b, c, d);
    }

    public static void glVertexAttribLPointer(int a, int b, int c, DoubleBuffer d) {
        GL41.glVertexAttribLPointer(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x3(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix4x3(a, b, c, d);
    }

    public static void glProgramUniformMatrix4x3(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix4x3(a, b, c, d);
    }

    public static void glViewportIndexedf(int a, float b, float c, float d, float e) {
        GL41.glViewportIndexedf(a, b, c, d, e);
    }

    public static void glDepthRangeArray(int a, DoubleBuffer b) {
        GL41.glDepthRangeArray(a, b);
    }

    public static void glValidateProgramPipeline(int a) {
        GL41.glValidateProgramPipeline(a);
    }

    public static void glVertexAttribL2d(int a, double b, double c) {
        GL41.glVertexAttribL2d(a, b, c);
    }

    public static void glProgramUniformMatrix3x4(int a, int b, boolean c, DoubleBuffer d) {
        GL41.glProgramUniformMatrix3x4(a, b, c, d);
    }

    public static void glProgramUniformMatrix3x4(int a, int b, boolean c, FloatBuffer d) {
        GL41.glProgramUniformMatrix3x4(a, b, c, d);
    }

    public static void glMemoryBarrier(int a) {
        GL42.glMemoryBarrier(a);
    }

    public static void glTexStorage3D(int a, int b, int c, int d, int e, int f) {
        GL42.glTexStorage3D(a, b, c, d, e, f);
    }

    public static void glGetInternalformat(int a, int b, int c, IntBuffer d) {
        GL42.glGetInternalformat(a, b, c, d);
    }

    public static int glGetInternalformat(int a, int b, int c) {
        return GL42.glGetInternalformat(a, b, c);
    }

    public static void glTexStorage2D(int a, int b, int c, int d, int e) {
        GL42.glTexStorage2D(a, b, c, d, e);
    }

    public static void glBindImageTexture(int a, int b, int c, boolean d, int e, int f, int g) {
        GL42.glBindImageTexture(a, b, c, d, e, f, g);
    }

    public static void glTexStorage1D(int a, int b, int c, int d) {
        GL42.glTexStorage1D(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, int b, int c, long d, int e, int f, int g) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e, f, g);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, IntBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, ByteBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(int a, ShortBuffer b, int c, int d, int e) {
        GL42.glDrawElementsInstancedBaseVertexBaseInstance(a, b, c, d, e);
    }

    public static void glDrawArraysInstancedBaseInstance(int a, int b, int c, int d, int e) {
        GL42.glDrawArraysInstancedBaseInstance(a, b, c, d, e);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, ShortBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, ByteBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, int b, int c, long d, int e, int f) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d, e, f);
    }

    public static void glDrawElementsInstancedBaseInstance(int a, IntBuffer b, int c, int d) {
        GL42.glDrawElementsInstancedBaseInstance(a, b, c, d);
    }

    public static int glGetActiveAtomicCounterBuffer(int a, int b, int c) {
        return GL42.glGetActiveAtomicCounterBuffer(a, b, c);
    }

    public static void glGetActiveAtomicCounterBuffer(int a, int b, int c, IntBuffer d) {
        GL42.glGetActiveAtomicCounterBuffer(a, b, c, d);
    }

    public static void glDrawTransformFeedbackInstanced(int a, int b, int c) {
        GL42.glDrawTransformFeedbackInstanced(a, b, c);
    }

    public static void glDrawTransformFeedbackStreamInstanced(int a, int b, int c, int d) {
        GL42.glDrawTransformFeedbackStreamInstanced(a, b, c, d);
    }

    public static void glTextureView(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL43.glTextureView(a, b, c, d, e, f, g, h);
    }

    public static void glObjectLabel(int a, int b, ByteBuffer c) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glObjectLabel(a, b, c);
    }

    public static void glObjectLabel(int a, int b, CharSequence c) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glObjectLabel(a, b, c);
    }

    public static String glGetProgramResourceName(int a, int b, int c, int d) {
        return GL43.glGetProgramResourceName(a, b, c, d);
    }

    public static void glGetProgramResourceName(int a, int b, int c, IntBuffer d, ByteBuffer e) {
        GL43.glGetProgramResourceName(a, b, c, d, e);
    }

    public static int glGetDebugMessageLog(int a, IntBuffer b, IntBuffer c, IntBuffer d, IntBuffer e, IntBuffer f, ByteBuffer g) {
        return (GLContext.getCapabilities().GL_KHR_debug) ? KHRDebug.glGetDebugMessageLog(a, b, c, d, e, f, g) : -1;
    }

    public static void glDebugMessageControl(int a, int b, int c, IntBuffer d, boolean e) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glDebugMessageControl(a, b, c, d, e);
    }

    public static void glDebugMessageCallback(KHRDebugCallback a) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glDebugMessageCallback(a);
    }

    public static void glGetFramebufferParameter(int a, int b, IntBuffer c) {
        GL43.glGetFramebufferParameter(a, b, c);
    }

    public static String glGetObjectPtrLabel(PointerWrapper a, int b) {
        return (GLContext.getCapabilities().GL_KHR_debug) ? KHRDebug.glGetObjectPtrLabel(a, b) : "";
    }

    public static void glGetObjectPtrLabel(PointerWrapper a, IntBuffer b, ByteBuffer c) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glGetObjectPtrLabel(a, b, c);
    }

    public static void glInvalidateTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h) {
        GL43.glInvalidateTexSubImage(a, b, c, d, e, f, g, h);
    }

    public static void glGetProgramResource(int a, int b, int c, IntBuffer d, IntBuffer e, IntBuffer f) {
        GL43.glGetProgramResource(a, b, c, d, e, f);
    }

    public static void glCopyImageSubData(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k, int l, int m, int n, int o) {
        GL43.glCopyImageSubData(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o);
    }

    public static int glGetProgramResourceLocation(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceLocation(a, b, c);
    }

    public static int glGetProgramResourceLocation(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceLocation(a, b, c);
    }

    public static void glPopDebugGroup() {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glPopDebugGroup();
    }

    public static void glDebugMessageInsert(int a, int b, int c, int d, ByteBuffer e) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glDebugMessageInsert(a, b, c, d, e);
    }

    public static void glDebugMessageInsert(int a, int b, int c, int d, CharSequence e) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glDebugMessageInsert(a, b, c, d, e);
    }

    public static long glGetInternalformati64(int a, int b, int c) {
        return GL43.glGetInternalformati64(a, b, c);
    }

    public static void glFramebufferParameteri(int a, int b, int c) {
        GL43.glFramebufferParameteri(a, b, c);
    }

    public static void glInvalidateBufferData(int a) {
        GL43.glInvalidateBufferData(a);
    }

    public static void glInvalidateFramebuffer(int a, IntBuffer b) {
        GL43.glInvalidateFramebuffer(a, b);
    }

    public static void glInvalidateTexImage(int a, int b) {
        GL43.glInvalidateTexImage(a, b);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, ByteBuffer c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, long c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static void glMultiDrawElementsIndirect(int a, int b, IntBuffer c, int d, int e) {
        GL43.glMultiDrawElementsIndirect(a, b, c, d, e);
    }

    public static int glGetProgramInterfacei(int a, int b, int c) {
        return GL43.glGetProgramInterfacei(a, b, c);
    }

    public static void glGetProgramInterface(int a, int b, int c, IntBuffer d) {
        GL43.glGetProgramInterface(a, b, c, d);
    }

    public static void glPushDebugGroup(int a, int b, ByteBuffer c) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glPushDebugGroup(a, b, c);
    }

    public static void glPushDebugGroup(int a, int b, CharSequence c) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glPushDebugGroup(a, b, c);
    }

    public static String glGetObjectLabel(int a, int b, int c) {
        return (GLContext.getCapabilities().GL_KHR_debug) ? KHRDebug.glGetObjectLabel(a, b, c) : "";
    }

    public static void glGetObjectLabel(int a, int b, IntBuffer c, ByteBuffer d) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glGetObjectLabel(a, b, c, d);
    }

    public static int glGetFramebufferParameteri(int a, int b) {
        return GL43.glGetFramebufferParameteri(a, b);
    }

    public static void glInvalidateBufferSubData(int a, long b, long c) {
        GL43.glInvalidateBufferSubData(a, b, c);
    }

    public static void glMultiDrawArraysIndirect(int a, ByteBuffer b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glMultiDrawArraysIndirect(int a, long b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glMultiDrawArraysIndirect(int a, IntBuffer b, int c, int d) {
        GL43.glMultiDrawArraysIndirect(a, b, c, d);
    }

    public static void glInvalidateSubFramebuffer(int a, IntBuffer b, int c, int d, int e, int f) {
        GL43.glInvalidateSubFramebuffer(a, b, c, d, e, f);
    }

    public static int glGetProgramResourceIndex(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceIndex(a, b, c);
    }

    public static int glGetProgramResourceIndex(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceIndex(a, b, c);
    }

    public static void glObjectPtrLabel(PointerWrapper a, CharSequence b) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glObjectPtrLabel(a, b);
    }

    public static void glObjectPtrLabel(PointerWrapper a, ByteBuffer b) {
        if (GLContext.getCapabilities().GL_KHR_debug) KHRDebug.glObjectPtrLabel(a, b);
    }

    public static void glVertexAttribIFormat(int a, int b, int c, int d) {
        GL43.glVertexAttribIFormat(a, b, c, d);
    }

    public static void glTexStorage2DMultisample(int a, int b, int c, int d, int e, boolean f) {
        GL43.glTexStorage2DMultisample(a, b, c, d, e, f);
    }

    public static void glTexStorage3DMultisample(int a, int b, int c, int d, int e, int f, boolean g) {
        GL43.glTexStorage3DMultisample(a, b, c, d, e, f, g);
    }

    public static void glVertexAttribBinding(int a, int b) {
        GL43.glVertexAttribBinding(a, b);
    }

    public static void glTexBufferRange(int a, int b, int c, long d, long e) {
        GL43.glTexBufferRange(a, b, c, d, e);
    }

    public static void glVertexAttribLFormat(int a, int b, int c, int d) {
        GL43.glVertexAttribLFormat(a, b, c, d);
    }

    public static void glBindVertexBuffer(int a, int b, long c, int d) {
        GL43.glBindVertexBuffer(a, b, c, d);
    }

    public static void glVertexAttribFormat(int a, int b, int c, boolean d, int e) {
        GL43.glVertexAttribFormat(a, b, c, d, e);
    }

    public static void glShaderStorageBlockBinding(int a, int b, int c) {
        GL43.glShaderStorageBlockBinding(a, b, c);
    }

    public static void glVertexBindingDivisor(int a, int b) {
        GL43.glVertexBindingDivisor(a, b);
    }

    public static void glClearBufferSubData(int a, int b, long c, int d, int e, ByteBuffer f) {
        GL43.glClearBufferSubData(a, b, c, d, e, f);
    }

    public static void glDispatchCompute(int a, int b, int c) {
        GL43.glDispatchCompute(a, b, c);
    }

    public static void glDispatchComputeIndirect(long a) {
        GL43.glDispatchComputeIndirect(a);
    }

    public static void glClearBufferData(int a, int b, int c, int d, ByteBuffer e) {
        GL43.glClearBufferData(a, b, c, d, e);
    }

    public static void glGetInternalformat(int a, int b, int c, LongBuffer d) {
        GL43.glGetInternalformat(a, b, c, d);
    }

    public static int glGetProgramResourceLocationIndex(int a, int b, ByteBuffer c) {
        return GL43.glGetProgramResourceLocationIndex(a, b, c);
    }

    public static int glGetProgramResourceLocationIndex(int a, int b, CharSequence c) {
        return GL43.glGetProgramResourceLocationIndex(a, b, c);
    }

    public static void glBindTextures(int a, int b, IntBuffer c) {
        GL44.glBindTextures(a, b, c);
    }

    public static void glClearTexImage(int a, int b, int c, int d, ByteBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, DoubleBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, FloatBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, IntBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, ShortBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glClearTexImage(int a, int b, int c, int d, LongBuffer e) {
        GL44.glClearTexImage(a, b, c, d, e);
    }

    public static void glBufferStorage(int a, FloatBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, DoubleBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, ByteBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, long b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, LongBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, ShortBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBufferStorage(int a, IntBuffer b, int c) {
        GL44.glBufferStorage(a, b, c);
    }

    public static void glBindBuffersBase(int a, int b, int c, IntBuffer d) {
        GL44.glBindBuffersBase(a, b, c, d);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, LongBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ShortBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, ByteBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, DoubleBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, IntBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glClearTexSubImage(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, FloatBuffer k) {
        GL44.glClearTexSubImage(a, b, c, d, e, f, g, h, i, j, k);
    }

    public static void glBindBuffersRange(int a, int b, int c, IntBuffer d, PointerBuffer e, PointerBuffer f) {
        GL44.glBindBuffersRange(a, b, c, d, e, f);
    }

    public static void glBindVertexBuffers(int a, int b, IntBuffer c, PointerBuffer d, IntBuffer e) {
        GL44.glBindVertexBuffers(a, b, c, d, e);
    }

    public static void glBindImageTextures(int a, int b, IntBuffer c) {
        GL44.glBindImageTextures(a, b, c);
    }

    public static void glBindSamplers(int a, int b, IntBuffer c) {
        GL44.glBindSamplers(a, b, c);
    }
}
