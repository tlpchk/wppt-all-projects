import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane.*;
import javax.swing.JLabel.*;
import javax.swing.JPanel.*;
import java.awt.Font.*;
import java.awt.GridLayout.*;

public class Pascal{
    int ilosc;
    WierszTrojkataPascala troikat;
    String wierszString = "";
    JFrame frame;

    public static void main(String[] args) {
        Pascal pascal = new Pascal();
        pascal.go();
    }

    public void go(){
        start();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ilosc+1,1));
        frame.add(panel);

        troikat = new WierszTrojkataPascala(ilosc);

        for(int i=0;i<=ilosc;i++) {
            wierszString = "";
            for (int v = 0; v <= i; v++) {
                wierszString = wierszString +"  " + troikat.troikat[i][v];
            }

            JLabel label = new JLabel(wierszString, SwingConstants.CENTER);
            panel.add(label);
            label.setFont(new Font("Trebuchet MS", 0, (int)(20-i*0.3)));

        }
        frame.pack();
        frame.setVisible(true);
    }

    public void start(){
        int g=0;
        while(g<1){
            String input = JOptionPane.showInputDialog("Podaj ilosc wierzow");
            if(input.length()>0){
                int liczba;
                try{
                    liczba=Integer.parseInt(input);
                    if(liczba>=0 && liczba<=33) {
                        ilosc = liczba;
                        g++;
                    }
                }
                catch (Exception ex){}
            }
        }
    }
}
