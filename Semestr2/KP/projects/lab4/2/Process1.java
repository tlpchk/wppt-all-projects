import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Process1 implements ActionListener{
    private JFrame frame;
    private JPanel panel;
    private JTextField input;
    private JButton button;

    public static void main(String[] args) {
       Process1 process1 = new Process1();
       process1.go();
    }

    public void go(){
        frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel = new JPanel();
        GridLayout gridLayout = new GridLayout(10,1);
        panel.setLayout(gridLayout);
        frame.add(panel);

        input = new JTextField();
        button = new JButton("Click");

        panel.add(input);
        panel.add(button);

        button.addActionListener(this);
        frame.pack();

    }

    public void actionPerformed(ActionEvent evet) {

            for (int i=2; i<this.panel.getComponentCount(); i++) {
                this.panel.getComponent(i).setVisible(false);
                this.panel.remove(this.panel.getComponent(i));

            }

            try {
                Process process = Runtime.getRuntime().exec("main.exe "+input.getText());

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    JLabel label=new JLabel(line);
                    panel.add(label);
                }

                reader.close();

                } catch (IOException e) {
                e.printStackTrace();
            }
		frame.pack();
    }
}