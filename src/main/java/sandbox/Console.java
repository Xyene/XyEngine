package sandbox;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import tk.ivybits.engine.gl.TrueTypeFont;

import static org.lwjgl.opengl.GL11.*;

public class Console {
    private List<String> lines = new ArrayList<>();
    private List<String> commandHistory = new ArrayList<>();
    private String editLine = "";
    private HashMap<String, CommandExecutor> commands = new HashMap<>();
    private TrueTypeFont font = new TrueTypeFont(new Font("Consolas", Font.PLAIN, 18), true);

    public boolean isShowing() {
        return showing;
    }

    private boolean showing;

    public void log(String text) {
        lines.add(0, "[system] " + text);
    }

    public Console() {

    }

    protected void processCommand(String line) {
        System.out.println("> " + line);
        if (line.startsWith("/")) {
            line = line.substring(1);
            if (line.isEmpty())
                return;
            String[] tokens = line.split(" ");
            String command = tokens[0].toLowerCase();
            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, args.length);
            CommandExecutor exec = commands.get(command);
            if (exec != null) {
                log("[command] " + line);
                try {
                    exec.execute(command, args);
                } catch (Throwable ex) {
                    log("Error while execution command '" + command + "': see console for details");
                    ex.printStackTrace();
                }
            } else {
                log("Unknown command '" + command + "'");
            }
            return;
        }
        lines.add(0, line);
    }

    public void draw() {
        if (showing) {
            glDisable(GL_TEXTURE_2D);
            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glColor4f(0, 0, 0, 0.5f);
            glBegin(GL_QUADS);
            int m = Display.getHeight() / 2;
            glVertex2f(0, m);
            glVertex2f(0, 0);
            glVertex2f(Display.getWidth(), 0);
            glVertex2f(Display.getWidth(), m);
            glEnd();
            glEnable(GL_TEXTURE_2D);
            glColor4f(0, 1, 0, 1);
            font.drawString(0, 0, ">>> " + editLine, 1, 1);
            for (int l = 0; l != Math.min(15, lines.size()); l++) {
                font.drawString(0, 25 + 20 * l, lines.get(l), 1, 1);
            }
            glPopAttrib();
        }
    }

    public void charEntered(int keycode, char ch) {
        if (keycode == Keyboard.KEY_SLASH && !showing) {
            showing = true;
            return;
        } else if (keycode == Keyboard.KEY_TAB) {
            showing = !showing;
            return;
        }
        if (showing)
            switch (keycode) {
                case Keyboard.KEY_BACK:
                    if (editLine.length() > 0)
                        editLine = editLine.substring(0, editLine.length() - 1);
                    break;
                case Keyboard.KEY_RETURN:
                    processCommand(editLine);
                    editLine = "";
                    break;
                case Keyboard.KEY_ESCAPE:
                    showing = false;
                    break;
                default:
                    if (ch > 0 && ch < 128)
                        editLine += ch;
            }
    }

    public void registerCommand(String label, CommandExecutor exec) {
        commands.put(label.toLowerCase(), exec);
    }
}
