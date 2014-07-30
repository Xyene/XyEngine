package tk.ivybits.engine.gl.ui;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.lang.reflect.Field;
import java.nio.IntBuffer;

import static tk.ivybits.engine.gl.GL.*;

// TODO: make this more like a texture, maybe provide a getter to a Texture instance
public class UITexture {
    private final Component component;
    private final JFrame frame;
    private BufferedImage screenBuffer;
    private Graphics2D screen;
    private IntBuffer nativeBuffer;
    private int texture;
    private boolean needsBufferUpdate;
    private Component lastComponent;
    private boolean pressed;

    public void doMouseEvent() {
        mouseMoved(Mouse.getEventX(), Display.getHeight() - Mouse.getEventY());
        if (Mouse.getEventDX() == 0 && Mouse.getEventDY() == 0 && Mouse.getEventDWheel() == 0)
            mouseClicked(Mouse.getEventX(), Display.getHeight() - Mouse.getEventY(), Mouse.getEventButtonState());
    }

    protected void mouseMoved(int x, int y) {
        Component self = component.getComponentAt(x, y);
        if (self == null) return;

        if (pressed) { // Still holding
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_DRAGGED, System.currentTimeMillis(), InputEvent.BUTTON1_DOWN_MASK, x, y, x, y, 0, false, MouseEvent.BUTTON1));
        } else
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, x, y, 0, false, 0));
        if (self != lastComponent) {
            if (lastComponent != null)
                frame.dispatchEvent(new MouseEvent(lastComponent, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, x, y, x, y, 0, false, 0));
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, x, y, x, y, 0, false, 0));
            needsBufferUpdate = true;
        }
        lastComponent = self;
    }

    protected void mouseClicked(int x, int y, boolean flag) {
        Component self = component.getComponentAt(x, y);
        if (self == null) return;

        if (!pressed && flag) { // Mouse pressed
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), InputEvent.BUTTON1_DOWN_MASK, x, y, x, y, 1, false, MouseEvent.BUTTON1));
            pressed = true;
            needsBufferUpdate = true;
        }
        if (pressed && !flag) { // Mouse released
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), InputEvent.BUTTON1_DOWN_MASK, x, y, x, y, 1, false, MouseEvent.BUTTON1));
            frame.dispatchEvent(new MouseEvent(self, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), InputEvent.BUTTON1_DOWN_MASK, x, y, x, y, 1, false, MouseEvent.BUTTON1));
            pressed = false;
            needsBufferUpdate = true;
        }
    }

    public UITexture(int width, int height, Component component) {
        this.component = component;
        this.frame = new JFrame();

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (SwingUtilities.getWindowAncestor((Component) event.getSource()) == frame &&
                        // Don't hog resources for mouse events: javax.swing.* components don't repaint on mouse move
                        !(event instanceof MouseEvent && (event.getID() == MouseEvent.MOUSE_MOVED)))
                    needsBufferUpdate = true;
            }
        }, 0xFFFFFFFF);

        // Though not actually visible, the frame will attempt to gain focus (unfocusing the current display) -
        // prevent this
        frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.setAutoRequestFocus(false);

        frame.getRootPane().setDoubleBuffered(false);
        // Don't allocate space for borders
        frame.setUndecorated(true);

        frame.getRootPane().setBackground(new Color(0, 0, 0, 0));

        frame.setLayout(new BorderLayout());
        frame.add(component, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addNotify();
        try {
            // We need to tell the component that it is visible without actually making it visible
            Field visible = Component.class.getDeclaredField("visible");
            visible.setAccessible(true);
            visible.set(frame, true);
        } catch (ReflectiveOperationException ignored) {
            // Never happens
        }

        texture = glGenTextures();
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        resize(width, height);
        update();
    }

    public boolean needsUpdate() {
        boolean update = needsBufferUpdate;
        needsBufferUpdate = false;
        return update;
    }

    public void resize(int width, int height) {
        frame.setSize(width, height);
        frame.validate();
        screenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        screen = screenBuffer.createGraphics();

        AffineTransform flip = AffineTransform.getScaleInstance(1, -1);
        flip.translate(0, -screenBuffer.getHeight());
        screen.transform(flip);

        nativeBuffer = BufferUtils.createIntBuffer(width * height);
        needsBufferUpdate = true;
    }

    public void update() {
        screen.setBackground(new Color(0, 0, 0, 0));
        screen.clearRect(0, 0, screenBuffer.getWidth(), screenBuffer.getHeight());
        frame.printComponents(screen);

        nativeBuffer.clear();
        nativeBuffer.put(((DataBufferInt) screenBuffer.getData().getDataBuffer()).getData());
        nativeBuffer.flip();
        bind();
        glTexImage2D(GL_TEXTURE_2D,
                0,
                GL_RGBA,
                screenBuffer.getWidth(),
                screenBuffer.getHeight(),
                0,
                GL_BGRA,
                GL_UNSIGNED_BYTE,
                nativeBuffer);
        unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int id() {
        return texture;
    }

    public void destroy() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSED));
        glDeleteTextures(texture);
    }

    public int width() {
        return screenBuffer.getWidth();
    }

    public int height() {
        return screenBuffer.getHeight();
    }

    public Color getColor(int x, int y) {
        return new Color(screenBuffer.getRGB(x, y), true);
    }

    public void markDirty() {
        needsBufferUpdate = true;
    }
}
