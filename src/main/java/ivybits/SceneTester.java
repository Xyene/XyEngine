package ivybits;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.Projection;
import tk.ivybits.engine.gl.TrueTypeFont;
import tk.ivybits.engine.gl.shader.BloomShader;
import tk.ivybits.engine.gl.shader.PhongLightingShader;
import tk.ivybits.engine.scene.model.ModelIO;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.SceneFactory;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.light.AtmosphereModel;
import tk.ivybits.engine.scene.light.Lights;
import tk.ivybits.engine.scene.light.PointLight;
import tk.ivybits.engine.util.FrameTimer;
import tk.ivybits.engine.util.Natives;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static java.awt.Color.*;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;

public class SceneTester {
    private static float speed = 4f;
    private static boolean wireframe;
    private static FrameTimer timer;
    private static IScene scene;
    private static PhongLightingShader lighting;
    private static BloomShader bloom;
    private static TrueTypeFont font;
    private static ICamera camera;

    public static void loadShaders() {
        if (lighting != null) lighting.destroy();
        if (bloom != null) bloom.destroy();
        lighting = new PhongLightingShader(scene);
        // Set a shader that all objects should be rendered with by default
        scene.setDefaultShader(lighting);
        // Sets a post-render shader
        // If not null, the scene will be rendered to a texture, which will be passed
        // to the rendered shader
        bloom = new tk.ivybits.engine.gl.shader.BloomShader().setBloomIntensity(0.15f).setSampleCount(4);
        scene.setRenderedShader(scene.getRenderedShader() != null ? bloom : null);
    }

    public static void main(String[] args) throws Exception {
        setup();
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        font = new TrueTypeFont(new Font("Consolas", Font.PLAIN, 12), true);

        // Create a scene graph
        scene = SceneFactory.createScene();
        // Load a model into the scene
        IActor model = scene.createActor(ModelIO.read(new File("texturing.obj")));
        // We can transform it via the IActor instance returned
        model.position(0, 0, 0);

        AtmosphereModel atmo = scene.getAtmosphere();

        // Create a directional light at the same setDirection as the spotlight, but blue
        PointLight diffuseLight = Lights.newDirectionalLight()
                .setPosition(0, 300, 100)
                .setDiffuseColor(WHITE)
                .setIntensity(0.5f)
                .build();
        atmo.getPointLights().add(diffuseLight);

        loadShaders();

        // Fetch the camera and configure it
        camera = scene.getCamera()
                .setAspectRatio((float) Display.getWidth() / Display.getHeight())
                .setPosition(5, 12, 5)
                .setFieldOfView(60)
                .setZNear(0.3f)
                .setZFar(Float.MAX_VALUE);

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        scene.setViewportSize(Display.getWidth(), Display.getHeight());

        timer = new FrameTimer();
        timer.start();
        while (!Display.isCloseRequested()) {
            timer.update();
            if (Display.wasResized()) {
                glViewport(0, 0, Display.getWidth(), Display.getHeight());
                scene.setViewportSize(Display.getWidth(), Display.getHeight());
                camera.setAspectRatio(Display.getWidth() / (float) Display.getHeight());
            }
            input();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            scene.draw();

            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            glEnable(GL_TEXTURE_2D);

            Projection.toOrthographicProjection(0, 0, Display.getWidth(), Display.getHeight());

            glPushAttrib(GL_CURRENT_BIT);
            glColor4f(0.4f, 0.4f, 0.4f, 1);

            font.drawString(2, Display.getHeight() - 20, String.format(
                    "%s FPS\n" +
                            "%s point lights\n" +
                            "%s spot lights\n" +
                            "Render shader: %s\n" +
                            "Post shader:   %s\n",
                    timer.fps(),
                    scene.getAtmosphere().getPointLights().size(),
                    scene.getAtmosphere().getSpotLights().size(),
                    scene.getDefaultShader().getClass().getName(),
                    scene.getRenderedShader() != null ? scene.getRenderedShader().getClass().getName() : "None"), 1, 1);

            glPopAttrib();
            Projection.toFrustrumProjection();
            Display.update();
        }

        Display.destroy();
    }

    private static void input() throws IOException {
        if (Mouse.isGrabbed()) {
            while (Keyboard.next()) {
                int key = Keyboard.getEventKey();
                if (key == Keyboard.KEY_TAB) wireframe = !wireframe;
                if (Keyboard.getEventKeyState()) {
                    switch (key) {
                        case Keyboard.KEY_ESCAPE:
                            System.exit(0);
                        case Keyboard.KEY_ADD:
                            speed += 2f;
                            break;
                        case Keyboard.KEY_SUBTRACT:
                            speed -= 2f;
                            break;
                        case Keyboard.KEY_L:
                            scene.getAtmosphere().getPointLights().add(Lights.newDirectionalLight()
                                    .setPosition(camera.x(), camera.y(), camera.z())
                                    .setDiffuseColor(WHITE)
                                    .setIntensity(2)
                                    .build());
                            break;
                        case Keyboard.KEY_P:
                            Color[] colors = {RED, BLUE, GREEN, CYAN, YELLOW, MAGENTA, ORANGE, PINK};

                            scene.getAtmosphere().getSpotLights().add(Lights.newSpotLight()
                                    .setPosition(camera.x(), camera.y(), camera.z())
                                    .setDirection(camera.dx(), camera.dy(), camera.dz())
                                    .setDiffuseColor(colors[new Random().nextInt(colors.length)])
                                    .setIntensity(2)
                                    .setCutoff(10)
                                    .build());
                            break;
                        case Keyboard.KEY_B:
                            scene.setRenderedShader(scene.getRenderedShader() != null ? null : bloom);
                            break;
                        case Keyboard.KEY_R:
                            System.out.println("Reloading shaders...");
                            loadShaders();
                            break;
                        case Keyboard.KEY_C:
                            scene.getAtmosphere().getPointLights().clear();
                            scene.getAtmosphere().getSpotLights().clear();
                            break;
                        case Keyboard.KEY_U:
                            IActor model = scene.createActor(ModelIO.read(new File("texturing.obj")));
                            // We can transform it via the IActor instance returned
                            model.position(camera.x(), camera.y(), camera.z());
                            break;
                    }
                }
            }
            //camera.processKeyboard(speed, timer.getDelta() * (16 * speed));

            float delta = timer.getDelta() * (16 * speed);

            boolean keyUp = isKeyDown(KEY_UP) || isKeyDown(KEY_W);
            boolean keyDown = isKeyDown(KEY_DOWN) || isKeyDown(KEY_S);
            boolean keyLeft = isKeyDown(KEY_LEFT) || isKeyDown(KEY_A);
            boolean keyRight = isKeyDown(KEY_RIGHT) || isKeyDown(KEY_D);
            boolean flyUp = isKeyDown(KEY_SPACE);
            boolean flyDown = isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT);

            if (keyUp && keyRight && !keyLeft && !keyDown) {
                camera.move(delta * 0.003f, 0, -delta * 0.003f);
            }
            if (keyUp && keyLeft && !keyRight && !keyDown) {
                camera.move(-delta * 0.003f, 0, -delta * 0.003f);
            }
            if (keyUp && !keyLeft && !keyRight && !keyDown) {
                camera.move(0, 0, -delta * 0.003f);
            }
            if (keyDown && keyLeft && !keyRight && !keyUp) {
                camera.move(-delta * 0.003f, 0, delta * 0.003f);
            }
            if (keyDown && keyRight && !keyLeft && !keyUp) {
                camera.move(delta * 0.003f, 0, delta * 0.003f);
            }
            if (keyDown && !keyUp && !keyLeft && !keyRight) {
                camera.move(0, 0, delta * 0.003f);
            }
            if (keyLeft && !keyRight && !keyUp && !keyDown) {
                camera.move(-delta * 0.003f, 0, 0);
            }
            if (keyRight && !keyLeft && !keyUp && !keyDown) {
                camera.move(delta * 0.003f, 0, 0);
            }
            if (flyUp && !flyDown) {
                camera.setPosition(camera.x(), camera.y() + delta * 0.003f, camera.z());
            }
            if (flyDown && !flyUp) {
                camera.setPosition(camera.x(), camera.y() - delta * 0.003f, camera.z());
            }
            final float MAX_LOOK_UP = 90;
            final float MAX_LOOK_DOWN = -90;
            float mouseDX = Mouse.getDX() * 4 * 0.16f;
            float mouseDY = Mouse.getDY() * 4 * 0.16f;
            float pitch = camera.pitch(), yaw = camera.yaw();
            if (camera.yaw() + mouseDX >= 360) {
                yaw = camera.yaw() + mouseDX - 360;
            } else if (camera.yaw() + mouseDX < 0) {
                yaw = 360 - camera.yaw() + mouseDX;
            } else {
                yaw += mouseDX;
            }
            if (camera.pitch() - mouseDY >= MAX_LOOK_DOWN && camera.pitch() - mouseDY <= MAX_LOOK_UP) {
                pitch += -mouseDY;
            } else if (camera.pitch() - mouseDY < MAX_LOOK_DOWN) {
                pitch = MAX_LOOK_DOWN;
            } else if (camera.pitch() - mouseDY > MAX_LOOK_UP) {
                pitch = MAX_LOOK_UP;
            }
            camera.setRotation(pitch, yaw, camera.roll());
        }

        while (Mouse.next()) {
            if (Mouse.isButtonDown(0)) {
                Mouse.setGrabbed(true);
            } else if (Mouse.isButtonDown(1)) {
                Mouse.setGrabbed(false);
            }
        }
    }

    private static void setup() {
        Natives.unpack();
        try {
            Display.setDisplayMode(new DisplayMode(1024, 768));
            Display.setTitle("IvyEngine");
            Display.setResizable(true);
            //Display.setFullscreen(true);
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error setting up display");
            System.exit(0);
        }

        glClearColor(0.4f, 0.6f, 0.9f, 0f);
        glClearDepth(1.0);

        System.out.printf("OpenGL %s\n\t%s\n", glGetString(GL_VERSION), glGetString(GL_VENDOR));
        System.out.println(glGetInteger(GL_MAX_LIGHTS) + " lights supported");
        System.out.printf("%s max texture size\n", glGetInteger(GL_MAX_TEXTURE_SIZE));
        System.out.printf("\tNon power-of-2 textures supported? %s\n", GLContext.getCapabilities().GL_ARB_texture_non_power_of_two);
        System.out.println("Uniform buffers? " + GLContext.getCapabilities().GL_ARB_uniform_buffer_object);
        System.out.println("CG shaders supported? " + GLContext.getCapabilities().GL_EXT_Cg_shader);
        System.out.println("Assembly shaders supported? " + GLContext.getCapabilities().GL_ARB_shader_objects);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        // Enable depth testing
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_MODELVIEW);

        //glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_DIFFUSE);
    }
}
