package sandbox;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GamePanel extends JPanel {
    @Override
    public void paintComponent(Graphics _g) {
        Insets insets = getBorder().getBorderInsets(this);
        Graphics2D g = (Graphics2D) _g;
        g.setClip(new RoundRectangle2D.Float(insets.left, insets.top, getWidth() - insets.right, getHeight() - insets.bottom, 20, 20));
        g.setBackground(new Color(0, 0, 0, 0.25f));
        g.clearRect(0, 0, getWidth(), getHeight());
    }
}
