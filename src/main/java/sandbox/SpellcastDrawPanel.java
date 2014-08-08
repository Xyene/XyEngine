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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SpellcastDrawPanel extends GamePanel {
    private BufferedImage buffer;
    private Graphics2D graphics;

    public SpellcastDrawPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        MouseAdapter adapter = new MouseAdapter() {
            int _x;
            int _y;

            @Override
            public void mousePressed(MouseEvent e) {
                _x = e.getX();
                _y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                graphics.drawLine(_x, _y, x, y);
                _x = x;
                _y = y;
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    public void paintComponent(Graphics _g) {
        super.paintComponent(_g);
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight())
            reset();
        _g.drawImage(buffer, 0, 0, null);
    }

    private void reset() {
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = buffer.createGraphics();

        graphics.setBackground(new Color(0, 0, 0, 0));
        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.setColor(new Color(
                Color.ORANGE.getRed() / 255f,
                Color.ORANGE.getGreen() / 255f,
                Color.ORANGE.getBlue() / 255f,
                0.5f));
        graphics.setStroke(new WobbleStroke(8f, 4));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }
}
