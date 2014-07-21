package sandbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUITest {
    public static void main(String[] argv) throws Exception {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        final JPanel onScreenUI = new JPanel();
        // onScreenUI.setBackground(new Color(0, 0, 0, 0));
        onScreenUI.setLayout(null);

        final JFrame frame = new JFrame();
        JList opts = new JList();
        opts.setListData(new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"});

        final JTabbedPane tabs = new JTabbedPane();
        tabs.add("List Options", new JScrollPane(opts));
        MouseAdapter mover = new MouseAdapter() {
            Point offset = new Point(0, 0);
            @Override
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
                System.out.println(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                offset = new Point(0, 0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println(e);
                Point xy = e.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(xy, onScreenUI);

                int width = tabs.getWidth();
                int height = tabs.getHeight();

                tabs.setBounds(xy.x - offset.x, xy.y - offset.y, width, height);
            }
        };
        tabs.addMouseListener(mover);
        tabs.addMouseMotionListener(mover);


        //  side.setBackground(new Color(0, 0, 0, 0));
        //tabs.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        onScreenUI.add(tabs);
        tabs.setBounds(0, 0, 100, 300);
        onScreenUI.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(780, 560);
        frame.setLayout(new BorderLayout());
        frame.add(onScreenUI, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
