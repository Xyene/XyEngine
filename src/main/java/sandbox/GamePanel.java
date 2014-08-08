
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
