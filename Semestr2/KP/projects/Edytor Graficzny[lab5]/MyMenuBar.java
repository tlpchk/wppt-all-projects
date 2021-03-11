import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MyMenuBar extends JMenuBar {

    private JMenu file, info;
    private JMenuItem author, save, read, help;
    private String ErrorRead = "";
    private String HelpMessange = "";
    private String InfoMassange = "";
    Desktop desktop;

    public MyMenuBar(Panel panel)
    {
        ErrorRead+="Niepoprawnie dane\n\n";
        ErrorRead+="Kazda figura musi byc w osobnym wierszu\n";
        ErrorRead+="Punkty powinni miec calkowite wsporzedne\n";
        ErrorRead+="Miedzy punktami musi byc jeden odstep\n";
        ErrorRead+="W koncu kazdego wiersza musi byc symbol ';'\n\n";
        ErrorRead+="Pryklady\n";
        ErrorRead+="O (50,50) (100,100);    - Okrag z centrem w punkcie (50,50) \n";
        ErrorRead+="                                  i punktem (100,100) na brzegu  \n";
        ErrorRead+="P (100,100) (200,200);   - Prostokat, ograniczony \n";
        ErrorRead+="                                   punktami (100,100) i (200,200)\n";
        ErrorRead+="W (50,50) (100,100) (50,100);     - Wielokat z wierzcholkami \n";
        ErrorRead+="                                   w punktach (50,50),(100,100) i (50,100)\n";
        ErrorRead+="\n";

        HelpMessange+="Przycisk 'Circle' zaczyna tworzenie okraga\n";
        HelpMessange+="Okrag tworzy sie poprzez dwa klikniecia: pierwsze srodek, drugie promien\n";
        HelpMessange+="Przycisk 'Rectangle' zaczyna tworzenie prostokata\n";
        HelpMessange+="Prostokat tworzy sie poprzez dwa klikniecia, ktorymi on bedzie ograniczac sie\n";
        HelpMessange+="Przycisk 'Polygon' zaczyna tworzenie wielokata\n";
        HelpMessange+="Rysowanie wielokata odbywa sie poprzez wybranie\n";
        HelpMessange+="kilku punktow na ekranie i polacznie ich liniami\n";
        HelpMessange+="Zamykanie wielokata polega na kliknieciu blisko punktu poczatkowego\n";
        HelpMessange+="Pzycisk 'Reset' oczyszcza panel\n\n";
        HelpMessange+="Dostepne zapisanie figur z ekranu do pliku i wczytanie istniejacego pliku\n";
        HelpMessange+="Figury mozna przesuwac myszka, zmieniac rozmiar scrollem,zaznacyc,\n";
        HelpMessange+="             zmieniac color i usawac prawym przyciskiem myszki\n";
        HelpMessange+="Dostepne colory : zolty,niebieski,czerwony,zielony";

        InfoMassange+="Nazwa: 'CoTylkoNieZrobiszDlaZaliczenia' \n";
        InfoMassange+="Autor: Maksym Telepchuk\n";
        InfoMassange+="Przeznaczenie: Edytor Graficzny\n\n";
        InfoMassange+="Wroclaw, PWr, WPPT, Informatyka, 1 rok, 2 semestr\n";
        InfoMassange+="8.05.2017\n";


        file = new JMenu("File");
        save = new JMenuItem("Export");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panel.writeInFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        file.add(save);


        read = new JMenuItem("Import");
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panel.readFile();
                }catch (Exception e1) {
                    JOptionPane.showMessageDialog(null,
                            ErrorRead);
                }
            }
        });
        file.add(read);

        info = new JMenu("Info");
        help = new JMenuItem("Help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        HelpMessange);
            }
        });
        info.add(help);

        author = new JMenuItem("About");
        author.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        InfoMassange);
            }
        });
        info.add(author);

        this.add(file);
        this.add(info);
    }
}
