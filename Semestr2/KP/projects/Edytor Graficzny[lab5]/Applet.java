//<applet code="Applet.class",width="600" height=800>
//</applet>
import javax.swing.*;
import java.awt.*;

public class Applet extends JApplet{

    private MyMenuBar menuBar;
    private Panel drawing;
    private PanelButtons panelButtons;

    public void init() {
        drawing = new Panel();
        panelButtons = new PanelButtons(drawing);
        menuBar=new MyMenuBar(drawing);
        setJMenuBar(menuBar);

        drawing.addMouseListener(drawing);
        drawing.addMouseMotionListener(drawing);
        drawing.addMouseWheelListener(drawing);
        drawing.setFocusable(true);
        drawing.setBackground(Color.WHITE);

        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, panelButtons);
        cp.add(BorderLayout.CENTER, drawing);
    }
}

