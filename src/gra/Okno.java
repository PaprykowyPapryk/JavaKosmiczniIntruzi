package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Okno extends JPanel {

    static int poziomTrudnosci = 1;
    static Silnik silnikGry;
    JFrame f;
    Image imageL, img;
    JPanel panel, menuPanel;
    JLabel label1, label2, label3;
    BufferedImage bi;

    JRadioButton latwyButton, sredniButton, trudnyButton;
    ButtonGroup group;

    JButton kontynuujButton, startButton, opcjeButton, zakonczButton, powrotButton;


    public Okno() {

        silnikGry = new Silnik(poziomTrudnosci);

        f = new JFrame();


        ustawieniaOkna();
        ustawieniaButtonow();
        ustawieniaLabeli();
        ustawieniaPaneli();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 440);
        f.setResizable(false);
        f.setVisible(true);

        f.add(panel);
        if (silnikGry.zycia <= 0) {
            f.getContentPane().removeAll();
            f.add(panel);
            f.revalidate();
            f.repaint();
        }


    }

    private void ustawieniaOkna() {

        f.setTitle("Kosmiczni Intruzi by Patryk Zasada");
        f.setFocusTraversalKeysEnabled(false);
        ustawieniaZdj();


    }

    private void ustawieniaButtonow() {

        kontynuujButton = new JButton("Kontynuuj");
        kontynuujButton.setPreferredSize(new Dimension(330, 40));
        startButton = new JButton("Nowa Gra");
        startButton.setPreferredSize(new Dimension(330, 40));
        opcjeButton = new JButton("Opcje");
        opcjeButton.setPreferredSize(new Dimension(330, 40));
        zakonczButton = new JButton("Zakoncz");
        zakonczButton.setPreferredSize(new Dimension(330, 40));
        powrotButton = new JButton("Powrot do Menu");


        latwyButton = new JRadioButton("Łatwy");
        sredniButton = new JRadioButton("Sredni");
        trudnyButton = new JRadioButton("trudny");
        group = new ButtonGroup();
        group.add(latwyButton);
        group.add(sredniButton);
        group.add(trudnyButton);

        kontynuujButton.setEnabled(false);

        actionListenery();
    }

    private void ustawieniaLabeli() {

        label1 = new JLabel("Kosmiczni Intruzi", new ImageIcon(bi), JLabel.CENTER);
        label2 = new JLabel("Poziom trudnosci",
                JLabel.CENTER);
        label3 = new JLabel("Twórca: Patryk Zasada, Student II-go roku Infomatyki Stosowanej, WFiIS", JLabel.LEFT);


        label1.setPreferredSize(new Dimension(400, 190));
        label1.setVerticalTextPosition(JLabel.BOTTOM);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 30));

        label2.setPreferredSize(new Dimension(600, 40));
        label2.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 30));


        label3.setPreferredSize(new Dimension(570, 20));
        label3.setVerticalTextPosition(JLabel.BOTTOM);
        label3.setHorizontalTextPosition(JLabel.LEFT);
        label3.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 10));


    }

    private void ustawieniaPaneli() {
        panel = new JPanel();

        panel.add(label3, BorderLayout.NORTH);
        panel.add(label1, BorderLayout.NORTH);
        panel.add(kontynuujButton, BorderLayout.NORTH);
        panel.add(startButton, BorderLayout.NORTH);
        panel.add(opcjeButton, BorderLayout.NORTH);
        panel.add(zakonczButton, BorderLayout.NORTH);

        menuPanel = new JPanel();

        menuPanel.add(label2, BorderLayout.NORTH);
        menuPanel.add(latwyButton, BorderLayout.NORTH);
        menuPanel.add(sredniButton, BorderLayout.NORTH);
        menuPanel.add(trudnyButton, BorderLayout.NORTH);
        menuPanel.add(powrotButton, BorderLayout.NORTH);

    }

    private void actionListenery() {


        latwyButton.addActionListener(e -> {
            poziomTrudnosci = 1;
            kontynuujButton.setEnabled(false);
        });

        sredniButton.addActionListener(e -> {
            poziomTrudnosci = 2;
            kontynuujButton.setEnabled(false);
        });

        trudnyButton.addActionListener(e -> {
            poziomTrudnosci = 3;
            kontynuujButton.setEnabled(false);
        });

        kontynuujButton.addActionListener(e -> {
            f.getContentPane().removeAll();
            nowaGra(f, powrotButton);
        });
        startButton.addActionListener(e -> {
            kontynuujButton.setEnabled(true);
            f.getContentPane().removeAll();
            silnikGry = new Silnik(poziomTrudnosci);
            nowaGra(f, powrotButton);


        });

        opcjeButton.addActionListener(e -> {

            f.getContentPane().removeAll();
            f.add(menuPanel);
            menuPanel.requestFocus();
            f.add(powrotButton, BorderLayout.SOUTH);
            f.revalidate();
            f.repaint();

        });

        zakonczButton.addActionListener(e -> {

            System.exit(0);
        });

        powrotButton.addActionListener(e -> {
            if (silnikGry.zycia <= 0) {
                kontynuujButton.setEnabled(false);
            }
            f.getContentPane().removeAll();
            f.add(panel);
            f.revalidate();
            f.repaint();
        });

    }


    private void ustawieniaZdj() {

        imageL = new ImageIcon("img\\logo.png").getImage();
        img = new ImageIcon("img\\logo.png").getImage();

        f.setIconImage(imageL);
        bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 180, 100, 260, 200, null);

    }


    private static void nowaGra(JFrame f, JButton powrotButton) {
        f.add(silnikGry);
        powrotButton.setLayout(null);
        powrotButton.setPreferredSize(new Dimension(30, 30));
        powrotButton.setLocation(200, 300);

        f.add(powrotButton, BorderLayout.SOUTH);
        silnikGry.requestFocus();
        f.revalidate();
        f.repaint();
    }
}
