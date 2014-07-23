package sandbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.gl11.GL11Scene;
import tk.ivybits.engine.gl.scene.gl20.GL20Scene;
import tk.ivybits.engine.gl.ui.UITexture;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.ModelIO;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.ISceneGraph;
import tk.ivybits.engine.scene.node.ISceneNode;
import tk.ivybits.engine.scene.node.impl.DefaultSceneGraph;
import tk.ivybits.engine.util.FrameTimer;
import tk.ivybits.engine.util.Natives;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.Color.*;
import static org.lwjgl.input.Keyboard.*;
import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.scene.geometry.ITesselator.*;

public class Sandbox {
    private static float speed = 0.5f;
    private static FrameTimer timer;
    private static IScene scene;
    private static ICamera camera;
    private static UITexture screenOverlay;

    public static void main(String[] args) throws Exception {
        setup();
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        ISceneGraph graph = new DefaultSceneGraph();

        // Create a scene graph
        scene = new GL20Scene(Display.getWidth(), Display.getHeight(), graph);
        ISceneNode root = graph.getRoot();

        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        for (UIManager.LookAndFeelInfo lfi : UIManager.getInstalledLookAndFeels()) {
            if (lfi.getName().contains("CDE"))
                UIManager.setLookAndFeel(lfi.getClassName());
        }
//        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            if ("Nimbus".equals(info.getName())) {
//                UIManager.setLookAndFeel(info.getClassName());
//                break;
//            }
//        }

        final JPanel onScreenUI = new JPanel();
        onScreenUI.setBackground(new Color(0, 0, 0, 0));
        onScreenUI.setLayout(new BorderLayout());

        JPanel opts = new JPanel();
        opts.setLayout(new BoxLayout(opts, BoxLayout.Y_AXIS));
        opts.setBackground(new Color(0, 0, 0, 0));

        final HealthBarPanel healthBar = new HealthBarPanel();

        onScreenUI.add(healthBar, BorderLayout.NORTH);

        final JSlider bar = new JSlider();
        bar.setMaximum(100);
        bar.setValue(100);
        bar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                healthBar.setHealth(bar.getValue());
            }
        });

        // UNCOMMENT THIS LINE FOR MESA scene.getDrawContext().setEnabled(IDrawContext.ANTIALIASING, false);

        JCheckBox msaa = ((JCheckBox) opts.add(new JCheckBox("MSAA", scene.getDrawContext().isEnabled(IDrawContext.ANTIALIASING))));
        msaa.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getDrawContext().setEnabled(IDrawContext.ANTIALIASING, !scene.getDrawContext().isEnabled(IDrawContext.ANTIALIASING));
            }
        });
        msaa.setEnabled(scene.getDrawContext().<Boolean>getCapability(IDrawContext.ANTIALIASING));
        JCheckBox bloom = ((JCheckBox) opts.add(new JCheckBox("Bloom", scene.getDrawContext().isEnabled(IDrawContext.BLOOM))));
        bloom.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getDrawContext().setEnabled(IDrawContext.BLOOM, !scene.getDrawContext().isEnabled(IDrawContext.BLOOM));
            }
        });
        bloom.setEnabled(scene.getDrawContext().<Boolean>getCapability(IDrawContext.BLOOM));
        JCheckBox normals = ((JCheckBox) opts.add(new JCheckBox("Normal mapping", scene.getDrawContext().isEnabled(IDrawContext.NORMAL_MAPS))));
        normals.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getDrawContext().setEnabled(IDrawContext.NORMAL_MAPS, !scene.getDrawContext().isEnabled(IDrawContext.NORMAL_MAPS));
            }
        });
        normals.setEnabled(scene.getDrawContext().<Boolean>getCapability(IDrawContext.NORMAL_MAPS));
        JCheckBox specular = ((JCheckBox) opts.add(new JCheckBox("Specular mapping", scene.getDrawContext().isEnabled(IDrawContext.SPECULAR_MAPS))));
        specular.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getDrawContext().setEnabled(IDrawContext.SPECULAR_MAPS, !scene.getDrawContext().isEnabled(IDrawContext.SPECULAR_MAPS));
            }
        });
        specular.setEnabled(scene.getDrawContext().<Boolean>getCapability(IDrawContext.SPECULAR_MAPS));
        JCheckBox alpha = ((JCheckBox) opts.add(new JCheckBox("Alpha testing", scene.getDrawContext().isEnabled(IDrawContext.ALPHA_TESTING))));
        alpha.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getDrawContext().setEnabled(IDrawContext.ALPHA_TESTING, !scene.getDrawContext().isEnabled(IDrawContext.ALPHA_TESTING));
            }
        });
        alpha.setEnabled(scene.getDrawContext().<Boolean>getCapability(IDrawContext.ALPHA_TESTING));

        opts.add(msaa);
        opts.add(bar);

        onScreenUI.add(opts, BorderLayout.EAST);

        opts.setBackground(new Color(0, 0, 0, 0));
        opts.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));


        onScreenUI.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        screenOverlay = new UITexture(Display.getWidth(), Display.getHeight(), onScreenUI);

        System.out.print("Reading models... ");
        IActor ship = root.track(new GeometryActor(ModelIO.read(new File("cylinder.obj"))));
        ship.position(0, 0, 0);
        ship.rotate(0, 0, 90);
        IActor crate = root.track(new GeometryActor(ModelIO.read(new File("ground3.obj"))));
        crate.position(0, 0, 0);
        System.out.print("Done.\n");

        root.createDirectionalLight()
                .setRotation(-80, 0)
                .setDiffuseColor(WHITE)
                .setIntensity(3f)
                .setSpecularColor(Color.BLACK);

        // Fetch the camera and configure it
        camera = scene.getCamera()
                .setAspectRatio((float) Display.getWidth() / Display.getHeight())
                .setPosition(5.9f, 3f, 8.2f)
                .setRotation(24f, 330f, 0)
                .setFieldOfView(60)
                .setZNear(0.3f)
                .setZFar(Float.MAX_VALUE);

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        scene.setViewportSize(Display.getWidth(), Display.getHeight());

        timer = new FrameTimer();
        timer.start();

        long frame = 0;
        while (!Display.isCloseRequested()) {
            frame++;
            timer.update();
            if (Display.wasResized()) {
                glViewport(0, 0, Display.getWidth(), Display.getHeight());
                scene.setViewportSize(Display.getWidth(), Display.getHeight());
                camera.setAspectRatio(Display.getWidth() / (float) Display.getHeight());
                screenOverlay.resize(Display.getWidth(), Display.getHeight());
            }
            if (screenOverlay.needsUpdate())
                screenOverlay.update();
            input();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            //ship.rotate(ship.pitch(), ship.yaw() + 0.5f, ship.roll());

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            scene.draw();
            glPopAttrib();

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glActiveTexture(GL_TEXTURE0); // Who knows what texture was used in what shader?
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_CULL_FACE);
            glEnable(GL_TEXTURE_2D);

            ImmediateProjection.toOrthographicProjection();

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            glColor4f(1, 1, 1, 1);

            screenOverlay.bind();

            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(0, 1);
            glVertex2f(0, Display.getHeight());
            glTexCoord2f(1, 1);
            glVertex2f(Display.getWidth(), Display.getHeight());
            glTexCoord2f(1, 0);
            glVertex2f(Display.getWidth(), 0);
            glEnd();

            screenOverlay.unbind();
            glPopAttrib();

            glColor4f(0.4f, 0.5f, 0.4f, 1);

            // fpsLabel.setText(String.format("%s FPS (%d ms)", timer.fps(), timer.getLastDelta()));
            if (frame == 100) {
                float fps = timer.fps();
                System.out.printf("%.1f (%.2fms/frame)\n", fps, 1000 / fps);
                frame = 0;
            }
            glPopAttrib();
            ImmediateProjection.toFrustrumProjection();
            Display.update();
        }

        Display.destroy();
        System.out.println(camera);
    }

    private static void input() throws IOException {
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                if (Mouse.getEventButton() == 0 && screenOverlay.getColor(Mouse.getEventX(), Mouse.getEventY()).getAlpha() == 0)
                    Mouse.setGrabbed(true);
                else if (Mouse.getEventButton() == 1)
                    Mouse.setGrabbed(false);
            }
            screenOverlay.doMouseEvent();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
        if (Mouse.isGrabbed()) {
            while (Keyboard.next()) {
                int key = Keyboard.getEventKey();
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
    }

    private static void setup() {
        System.out.print("Unpacking natives... ");
        Natives.unpack();
        System.out.print("Done.\n");
        try {
            System.out.print("Creating display... ");
            Display.setDisplayMode(new DisplayMode(1024, 600));
            Display.setTitle("Xy Sandbox");
            Display.setResizable(true);

            // Display.setFullscreen(true);
            Display.create();
            Keyboard.create();
            Mouse.create();
            System.out.print("Done.\n");
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

        System.out.println("(3.0) max samples: " + glGetInteger(GL_MAX_SAMPLES));

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

    private static class HealthBarPanel extends JPanel {
        BufferedImage background;
        BufferedImage colour;
        BufferedImage healthSub;
        Dimension dim;
        final int HEALTH_OFFSET = 155 / 2;

        {
            try {
                background = ImageIO.read(ClassLoader.getSystemResourceAsStream("tk/ivybits/engine/game/healthbar-bg.png"));
                colour = ImageIO.read(ClassLoader.getSystemResourceAsStream("tk/ivybits/engine/game/healthbar-colour.png"));
                healthSub = colour;
                dim = new Dimension(background.getWidth(), background.getHeight());
                setSize(dim);
                setPreferredSize(dim);
                setBackground(new Color(0, 0, 0, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, dim.width, dim.height, null, null);
            if (healthSub != null) g.drawImage(healthSub, 0, 0, healthSub.getWidth(), dim.height, null, null);
        }

        public void setHealth(int health) {
            int scaledHealth = (int) ((dim.width - HEALTH_OFFSET) * (health / 100.0));
            healthSub = health > 0 ? colour.getSubimage(0, 0, HEALTH_OFFSET + scaledHealth, colour.getHeight()) : null;
        }
    }
}
