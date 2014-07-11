package ivybits;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.TrueTypeFont;
import tk.ivybits.engine.gl.scene.gl20.GL20Scene;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.model.ModelIO;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.IAtmosphere;
import tk.ivybits.engine.scene.node.IFog;
import tk.ivybits.engine.scene.node.ISceneGraph;
import tk.ivybits.engine.scene.node.ISceneNode;
import tk.ivybits.engine.scene.node.impl.DefaultSceneGraph;
import tk.ivybits.engine.util.FrameTimer;
import tk.ivybits.engine.util.Natives;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.*;
import static org.lwjgl.input.Keyboard.*;
import static tk.ivybits.engine.gl.GL.*;

public class Sandbox {
    private static float speed = 4f;
    private static FrameTimer timer;
    private static IScene scene;
    private static TrueTypeFont font;
    private static ICamera camera;
    private static Console console;

    public static void main(String[] args) throws Exception {
        setup();
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        font = new TrueTypeFont(new Font("Consolas", Font.PLAIN, 16), true);

        console = new Console();
        console.registerCommand("broadcast", new CommandExecutor() {
            @Override
            public void execute(String command, String[] args) {
                console.log(args[0]);
            }
        });

        ISceneGraph graph = new DefaultSceneGraph();

        // Create a scene graph
        scene = new GL20Scene(Display.getWidth(), Display.getHeight(), graph);
        ISceneNode root = graph.getRoot();

        IActor ship = root.track(new GeometryActor(ModelIO.read(new File("serenity.obj"))));
        ship.position(0, 40, 0);
        IActor crate = root.track(new GeometryActor(ModelIO.read(new File("ground2.obj"))));
        crate.position(0, 0, 0);

        // scene.createActor(ModelIO.read(new File("serenity.obj"))).position(0, 50, 0);

        //LensFlare flare = new LensFlare();
        //scene.track(flare).position(100, 50, 60);

//        BoundingBox bb = BoundingBox.getBoundingBox(ground);
//        System.out.println(bb);
//
//        for (int x = -2; x != 2; x++) {
//            for (int y = -2; y != 2; y++) {
//                IActor modelMatrix = scene.createActor(ground);
//                modelMatrix.position(x * bb.getLength() - bb.getLength(), 0, y * bb.getWidth() - bb.getWidth());
//            }
//        }

        //  IActor shiny = scene.createActor(ModelIO.read(new File("transformers-lowpoly.obj")));
        // shiny.position(0, 50, 0);

        final IAtmosphere atmo = graph.getAtmosphere();
        atmo.getFog().setFogColor(Color.WHITE).setFogNear(400).setFogFar(1100);
        console.registerCommand("fog", new CommandExecutor() {
            @Override
            public void execute(String command, String[] args) {
                IFog fog = atmo.getFog();
                switch (args[0]) {
                    case "color":
                        fog.setFogColor(Color.decode(args[1]));
                        break;
                    case "znear":
                        fog.setFogNear(Float.parseFloat(args[1]));
                        break;
                    case "zfar":
                        fog.setFogFar(Float.parseFloat(args[1]));
                        break;
                    case "on":
                        fog.setEnabled(true);
                        break;
                    case "off":
                        fog.setEnabled(false);
                        break;
                }
            }
        });
        console.registerCommand("bloom", new CommandExecutor() {
            @Override
            public void execute(String command, String[] args) {
                scene.getDrawContext().setEnabled(IDrawContext.BLOOM, !scene.getDrawContext().isEnabled(IDrawContext.BLOOM));
            }
        });

        root.createPointLight()
                .setPosition(0, 1000, 0)
                .setDiffuseColor(WHITE)
                .setIntensity(1.5f)
                .setSpecularColor(Color.BLACK);

        // Fetch the camera and configure it
        camera = scene.getCamera()
                .setAspectRatio((float) Display.getWidth() / Display.getHeight())
                .setPosition(5, 100, 5)
                .setFieldOfView(60)
                .setZNear(0.3f)
                .setZFar(Float.MAX_VALUE);

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        scene.setViewportSize(Display.getWidth(), Display.getHeight());

        timer = new FrameTimer();
        timer.start();
        //double n = 0;
        //double nm = 0.25;
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
            ship.rotate(ship.pitch(), ship.yaw() + 0.5f, ship.roll());
//            ship.position(ship.x(), (float) (40 + n), ship.z());
//            n += nm;
//            if(Math.abs(n) > 30) nm = -nm;
            //  flare.position(flare.x() + 0.5f, flare.y(), flare.z() + 0.25f);

            scene.draw();

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glActiveTexture(GL_TEXTURE0); // Who knows what texture was used in what shader?
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_CULL_FACE);
            glEnable(GL_TEXTURE_2D);

            ImmediateProjection.toOrthographicProjection();

            glColor4f(0.4f, 0.4f, 0.4f, 1);

            font.drawString(2, Display.getHeight() - 20, String.format("%s FPS (%d ms)", timer.fps(), timer.getLastDelta()), 1, 1);

            console.draw();
            glPopAttrib();
            ImmediateProjection.toFrustrumProjection();
            Display.update();
        }

        Display.destroy();
    }

    private static void input() throws IOException {
        while (Mouse.next()) {
            if (Mouse.isButtonDown(0)) {
                Mouse.setGrabbed(true);
            } else if (Mouse.isButtonDown(1)) {
                Mouse.setGrabbed(false);
            }
        }
        if (Mouse.isGrabbed()) {
            while (Keyboard.next()) {
                int key = Keyboard.getEventKey();
                if (Keyboard.getEventKeyState()) {
                    if (!console.isShowing())
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
                                scene.getSceneGraph().getRoot().createPointLight()
                                        .setPosition(camera.x(), camera.y(), camera.z())
                                        .setDiffuseColor(WHITE)
                                        .setIntensity(2);
                                break;
                            case Keyboard.KEY_P:
                                scene.getSceneGraph().getRoot().createSpotLight()
                                        .setPosition(camera.x(), camera.y(), camera.z())
                                        .setRotation(camera.pitch(), camera.yaw())
                                        .setDiffuseColor(Color.GREEN)
                                        .setIntensity(2)
                                        .setCutoff(10);
                                break;
                            case Keyboard.KEY_C:
                                // TODO
                                break;
                        }

                    console.charEntered(key, Keyboard.getEventCharacter());
                }
            }
            if (console.isShowing()) return;
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
    }

    private static void setup() {
        Natives.unpack();
        try {
            Display.setDisplayMode(new DisplayMode(1024, 600));
            Display.setTitle("Xy Sandbox");
            Display.setResizable(true);

            // Display.setFullscreen(true);
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
        System.out.println("# uniforms: " + glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS));
        System.out.println("CG shaders supported? " + GLContext.getCapabilities().GL_EXT_Cg_shader);
        System.out.println("Assembly shaders supported? " + GLContext.getCapabilities().GL_ARB_shader_objects);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        // Enable depth testing
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_MODELVIEW);

        //glEnable(GL_CULL_FACE);
        //glCullFace(GL_BACK);

        glShadeModel(GL_SMOOTH);

        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT, GL_DIFFUSE);
    }
}
