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

package sandbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.gl11.GL11Scene;
import tk.ivybits.engine.gl.scene.gl20.GL20Scene;
import tk.ivybits.engine.gl.ui.UITexture;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.FPSCamera;
import tk.ivybits.engine.io.model.ModelIO;
import tk.ivybits.engine.scene.node.ISceneGraph;
import tk.ivybits.engine.scene.node.ISceneNode;
import tk.ivybits.engine.scene.node.impl.DefaultSceneGraph;
import tk.ivybits.engine.scene.node.impl.GeometryActor;
import tk.ivybits.engine.util.FrameTimer;
import tk.ivybits.engine.util.Natives;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.Color.*;
import static org.lwjgl.input.Keyboard.*;
import static tk.ivybits.engine.gl.GL.*;

import static tk.ivybits.engine.scene.IDrawContext.Capability.*;

public class Sandbox {
    private static float speed = 0.5f;
    private static FrameTimer timer;
    private static IScene scene;
    private static FPSCamera camera;
    private static UITexture screenOverlay;

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Mouse.setGrabbed(false);
                Display.destroy();
                throwable.printStackTrace();
            }
        });
        setup();
        glDebugMessageCallback(new KHRDebugCallback(new KHRDebugCallback.Handler() {
            @Override
            public void handleMessage(int i, int i2, int i3, int i4, String s) {
                try {
                    throw new OpenGLException(s);
                } catch (OpenGLException e) {
                    e.printStackTrace();
                }
            }
        }));

        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        ISceneGraph graph = new DefaultSceneGraph();

        // Create a scene graph
        if (GLContext.getCapabilities().OpenGL20)
            scene = new GL20Scene(Display.getWidth(), Display.getHeight(), graph);
        else
            scene = new GL11Scene(Display.getWidth(), Display.getHeight(), graph);
        ISceneNode root = graph.getRoot();

        setupUI();

        System.out.print("Reading models... ");

        IActor circle = root.track(new MagicCircleActor());
        circle.position(0, 0.1f, 0);
        circle.rotate(0, 0, 0);

        IActor ground = root.track(new GeometryActor(ModelIO.readSystem("tk/ivybits/engine/game/model/ground3.obj")));
        ground.position(0, 0, 0);

        IActor ship = root.track(new GeometryActor(ModelIO.readSystem("tk/ivybits/engine/game/model/cylinder2.obj")));
        ship.position(0, -2.5f, 10);
        ship.rotate(0, 0, 0);

        System.out.print("Done.\n");

        root.createDirectionalLight()
                .setRotation(-80, 0)
                .setDiffuseColor(WHITE)
                .setIntensity(3f)
                .setSpecularColor(Color.BLACK);

        // Fetch the camera and configure it
        camera = new FPSCamera(scene)
                .setAspectRatio((float) Display.getWidth() / Display.getHeight())
                .setRotation(24f, 330f, 0)
                .setPosition(5.9f, 3f, 8.2f)
                .setFieldOfView(60)
                .setClip(Float.MAX_VALUE, 0.3f);

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
            scene.draw();

            glEnable(GL_TEXTURE_2D);

            ImmediateProjection.toOrthographicProjection();

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

            ImmediateProjection.toFrustrumProjection();

            if (frame == 100) {
                float fps = timer.fps();
                Display.setTitle(String.format("Xy Sandbox | %.1f (%.2fms/frame), %d/%d objects drawn", fps, 1000 / fps, ((GL20Scene) scene).drawn, root.getActors().size()));
                frame = 0;
            }

            Display.update();
            // Display.sync(60);
        }

        screenOverlay.destroy();
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

        while (Keyboard.next()) {
            int key = Keyboard.getEventKey();
            if (Keyboard.getEventKeyState()) {
                switch (key) {
                    case Keyboard.KEY_ESCAPE:
                        System.exit(0);
                    case Keyboard.KEY_ADD:
                        speed *= 2f;
                        break;
                    case Keyboard.KEY_SUBTRACT:
                        speed /= 2f;
                        break;
                    case Keyboard.KEY_LCONTROL:
                    case Keyboard.KEY_RCONTROL:
                        Mouse.setGrabbed(false);
                        onScreenUI.add(new SpellcastDrawPanel(), BorderLayout.CENTER);
                        onScreenUI.revalidate();
                        screenOverlay.markDirty();
                        break;
                }
            }
        }

        if (Mouse.isGrabbed()) {
            //camera.processKeyboard(speed, timer.getDelta() * (16 * speed));

            float delta = timer.getDelta() * (16 * speed);

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

            if (isKeyDown(KEY_W)) camera.walkForward(delta * 0.003f);
            if (isKeyDown(KEY_S)) camera.walkBackwards(delta * 0.003f);
            if (isKeyDown(KEY_D)) camera.strafeRight(delta * 0.003f);
            if (isKeyDown(KEY_A)) camera.strafeLeft(delta * 0.003f);

            if (isKeyDown(KEY_SPACE))
                camera.setPosition(camera.x(), camera.y() + delta * 0.003f, camera.z());
            if (isKeyDown(KEY_LSHIFT) || isKeyDown(KEY_RSHIFT))
                camera.setPosition(camera.x(), camera.y() - delta * 0.003f, camera.z());

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
            //Display.setVSyncEnabled(true);

            // Display.setFullscreen(true);
            Display.create(new PixelFormat(), new ContextAttribs().withDebug(true));
            Keyboard.create();
            Mouse.create();
            System.out.print("Done.\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error setting up display");
            System.exit(0);
        }

        // glClearColor(0.4f, 0.6f, 0.9f, 0f);
        glClearColor(1, 1, 1, 0f);
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
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_DEPTH_TEST);
    }

    static final JPanel onScreenUI = new JPanel();

    private static void setupUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo lfi : UIManager.getInstalledLookAndFeels()) {
            if (lfi.getName().contains("Nimbus"))
                UIManager.setLookAndFeel(lfi.getClassName());
        }

        onScreenUI.setBackground(new Color(0, 0, 0, 0));
        onScreenUI.setLayout(new BorderLayout());

        GamePanel opts = new GamePanel() {
            @Override
            public Component add(Component box) {
                super.add(box);
                if (box instanceof JCheckBox) {
                    box.setForeground(Color.GREEN);
                    ((JCheckBox) box).setOpaque(false);
                }
                return box;
            }
        };
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

        scene.getDrawContext().setEnabled(OBJECT_SHADOWS, false);

        // UNCOMMENT THIS LINE FOR MESA scene.getDrawContext().setEnabled(IDrawContext.ANTIALIASING, false);
        if (scene.getDrawContext().isSupported(ANTIALIASING)) {
            JCheckBox msaa = ((JCheckBox) opts.add(new JCheckBox("MSAA ", scene.getDrawContext().isEnabled(ANTIALIASING))));
            msaa.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(ANTIALIASING, !scene.getDrawContext().isEnabled(ANTIALIASING));
                }
            });
        }
        if (scene.getDrawContext().isSupported(BLOOM)) {
            JCheckBox bloom = ((JCheckBox) opts.add(new JCheckBox("Bloom ", scene.getDrawContext().isEnabled(BLOOM))));
            bloom.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(BLOOM, !scene.getDrawContext().isEnabled(BLOOM));
                }
            });
        }
        if (scene.getDrawContext().isSupported(NORMAL_MAPS)) {
            JCheckBox normals = ((JCheckBox) opts.add(new JCheckBox("Normal mapping ", scene.getDrawContext().isEnabled(NORMAL_MAPS))));
            normals.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(NORMAL_MAPS, !scene.getDrawContext().isEnabled(NORMAL_MAPS));
                }
            });
        }
        if (scene.getDrawContext().isSupported(SPECULAR_MAPS)) {
            JCheckBox specular = ((JCheckBox) opts.add(new JCheckBox("Specular mapping ", scene.getDrawContext().isEnabled(SPECULAR_MAPS))));
            specular.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(SPECULAR_MAPS, !scene.getDrawContext().isEnabled(SPECULAR_MAPS));
                }
            });
        }
        if (scene.getDrawContext().isSupported(ALPHA_TESTING)) {
            JCheckBox alpha = ((JCheckBox) opts.add(new JCheckBox("Alpha testing ", scene.getDrawContext().isEnabled(ALPHA_TESTING))));
            alpha.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(ALPHA_TESTING, !scene.getDrawContext().isEnabled(ALPHA_TESTING));
                }
            });
        }
        if (scene.getDrawContext().isSupported(FOG)) {
            JCheckBox alpha = ((JCheckBox) opts.add(new JCheckBox("Fog ", scene.getDrawContext().isEnabled(FOG))));
            alpha.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(FOG, !scene.getDrawContext().isEnabled(FOG));
                }
            });
        }
        if (scene.getDrawContext().isSupported(OBJECT_SHADOWS)) {
            JCheckBox alpha = ((JCheckBox) opts.add(new JCheckBox("Shadows ", scene.getDrawContext().isEnabled(OBJECT_SHADOWS))));
            alpha.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    scene.getDrawContext().setEnabled(OBJECT_SHADOWS, !scene.getDrawContext().isEnabled(OBJECT_SHADOWS));
                }
            });
        }

        opts.add(bar);

        onScreenUI.add(opts, BorderLayout.EAST);

        opts.setBackground(new Color(0, 0, 0, 0));
        opts.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        onScreenUI.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        screenOverlay = new UITexture(Display.getWidth(), Display.getHeight(), onScreenUI);
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
