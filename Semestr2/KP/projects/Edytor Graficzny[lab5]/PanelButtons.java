import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PanelButtons extends JPanel {
    JButton CircleButton = new JButton("Circle");
    JButton RectangleButton = new JButton("Rectangle");
    JButton PolygonButton = new JButton("Polygon");
    JButton ResetButton = new JButton("Reset");

    public PanelButtons(Panel panel){
        ActionListener a1 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();

                if (source == CircleButton) {
                    panel.setCountCircles(panel.getCountCircles()+1);
                }

                if (source == RectangleButton) {
                    panel.setCountRectanges(panel.getCountRectanges()+1);
                }

                if (source == PolygonButton) {
                    panel.setCountPolygons(panel.getCountPolygons()+1);
                }

                if (source == ResetButton) {
                    panel.setFigury(new ArrayList<Figura>());
                    panel.setCountCircles(0);
                    panel.setCountRectanges(0);
                    panel.setCountPolygons(0);
                    panel.setCoordsOfPolygonsX(new ArrayList<Integer>());
                    panel.setCoordsOfPolygonsY(new ArrayList<Integer>());
                    panel.setCoordsOfCircle(new ArrayList<Integer>());
                    panel.setCoordsOfRectangle(new ArrayList<Integer>());

                    panel.repaint();
                }
            }
        };

        CircleButton.addActionListener(a1);
        PolygonButton.addActionListener(a1);
        RectangleButton.addActionListener(a1);
        ResetButton.addActionListener(a1);

        ResetButton.setBackground(Color.lightGray);

        this.setLayout(new GridLayout(1,4));
        this.add(CircleButton);
        this.add(PolygonButton);
        this.add(RectangleButton);
        this.add(ResetButton);

    }
}
