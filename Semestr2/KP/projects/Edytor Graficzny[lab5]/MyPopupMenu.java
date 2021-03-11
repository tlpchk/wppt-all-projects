import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPopupMenu extends JPopupMenu {
    public MyPopupMenu(Panel panel) {

        JMenuItem itemRed = new JMenuItem("Red");
        itemRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figura figura : panel.getFigury()) {
                    if (figura.getSelect()) {
                        figura.setRed();
                    }
                }
                panel.repaint();
            }
        });
        JMenuItem itemGreen = new JMenuItem("Green");
        itemGreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figura figura : panel.getFigury()) {
                    if (figura.getSelect()) {
                        figura.setGreen();
                    }
                }
                panel.repaint();
            }
        });
        JMenuItem itemBlue = new JMenuItem("Blue");
        itemBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figura figura : panel.getFigury()) {
                    if (figura.getSelect()) {
                        figura.setBlue();
                    }
                }
                panel.repaint();
            }
        });
        JMenuItem itemYellow = new JMenuItem("Yellow");
        itemYellow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figura figura : panel.getFigury()) {
                    if (figura.getSelect()) {
                        figura.setYellow();
                    }
                }
                panel.repaint();
            }
        });

        JMenuItem itemDelete = new JMenuItem("Delete");
        itemDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figura figura : panel.getFigury()) {
                    if (figura.getSelect()) {
                        panel.getFigury().remove(panel.getFigury().size() - 1);
                        break;
                    }
                }
                panel.repaint();
            }
        });

        itemDelete.setBackground(Color.lightGray);
        this.add(itemRed);
        this.add(itemGreen);
        this.add(itemBlue);
        this.add(itemYellow);
        this.add(itemDelete);
    }

}

