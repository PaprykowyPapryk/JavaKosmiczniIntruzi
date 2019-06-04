package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Gra extends JPanel implements ActionListener, KeyListener {
    static int poziomTrudnosci = 1;
    static Silnik s;

    public static void main(String arge[]) {

        JFrame f = new JFrame();

        ImageIcon imgLogoWindow = new ImageIcon("img\\logo.png");
        Image imageL = imgLogoWindow.getImage();
        f.setIconImage(imageL);
        f.setTitle("Kosmiczni Intruzi by Patryk Zasada");
        f.setFocusTraversalKeysEnabled(false);

        s = new Silnik(poziomTrudnosci);


        JButton kontynuujButton = new JButton("Kontynuuj");
        kontynuujButton.setPreferredSize(new Dimension(330, 40));
        JButton startButton = new JButton("Nowa Gra");
        startButton.setPreferredSize(new Dimension(330, 40));
        JButton opcjeButton = new JButton("Opcje");
        opcjeButton.setPreferredSize(new Dimension(330, 40));
        JButton zakonczButton = new JButton("Zakoncz");
        zakonczButton.setPreferredSize(new Dimension(330, 40));
        JButton powrotButton = new JButton("Powrot do Menu");

        ImageIcon icon = new ImageIcon("img\\logo.png");
        Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 180, 100, 260, 200, null);
        ImageIcon newIcon = new ImageIcon(bi);

        JLabel label1 = new JLabel("Kosmiczni Intruzi", newIcon,
                JLabel.CENTER);


        label1.setPreferredSize(new Dimension(400, 190));
        label1.setVerticalTextPosition(JLabel.BOTTOM);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 30));

        JLabel label3 = new JLabel("Twórca: Patryk Zasada, Student II-go roku Infomatyki Stosowanej, WFiIS",
                JLabel.LEFT);
        label3.setPreferredSize(new Dimension(570, 20));
        label3.setVerticalTextPosition(JLabel.BOTTOM);
        label3.setHorizontalTextPosition(JLabel.LEFT);
        label3.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 10));



        JPanel panel = new JPanel();
        panel.add(label3, BorderLayout.NORTH);
        panel.add(label1, BorderLayout.NORTH);
        panel.add(kontynuujButton, BorderLayout.NORTH);
        panel.add(startButton, BorderLayout.NORTH);
        panel.add(opcjeButton, BorderLayout.NORTH);
        panel.add(zakonczButton, BorderLayout.NORTH);


        JRadioButton latwyButton = new JRadioButton("Łatwy");
        JRadioButton sredniButton = new JRadioButton("Sredni");
        JRadioButton trudnyButton = new JRadioButton("trudny");
        ButtonGroup group = new ButtonGroup();
        group.add(latwyButton);
        group.add(sredniButton);
        group.add(trudnyButton);


        kontynuujButton.setEnabled(false);
        JPanel menuPanel = new JPanel();

        JLabel label2 = new JLabel("Poziom trudnosci",
                JLabel.CENTER);
        label2.setPreferredSize(new Dimension(600, 40));
        label2.setFont(new Font("Monospaced", Font.BOLD
                | Font.ITALIC, 30));
        menuPanel.add(label2, BorderLayout.NORTH);
        menuPanel.add(latwyButton, BorderLayout.NORTH);
        menuPanel.add(sredniButton, BorderLayout.NORTH);
        menuPanel.add(trudnyButton, BorderLayout.NORTH);
        menuPanel.add(powrotButton, BorderLayout.NORTH);

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


            f.add(s);
            powrotButton.setLayout(null);
            powrotButton.setPreferredSize(new Dimension(30, 30));
            powrotButton.setLocation(200, 300);

            f.add(powrotButton, BorderLayout.SOUTH);
            s.requestFocus();
            f.revalidate();
            f.repaint();


        });
        startButton.addActionListener(e -> {

            kontynuujButton.setEnabled(true);
            f.getContentPane().removeAll();


            s = new Silnik(poziomTrudnosci);
            f.add(s);
            powrotButton.setLayout(null);
            powrotButton.setPreferredSize(new Dimension(30, 30));
            powrotButton.setLocation(200, 300);

            f.add(powrotButton, BorderLayout.SOUTH);
            s.requestFocus();
            f.revalidate();
            f.repaint();


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
            if (s.zycia <= 0) {
                kontynuujButton.setEnabled(false);
            }
            f.getContentPane().removeAll();
            f.add(panel);
            f.revalidate();
            f.repaint();
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 440);
        f.setResizable(false);
        f.setVisible(true);

        f.add(panel);
        if (s.zycia <= 0) {
            f.getContentPane().removeAll();
            f.add(panel);
            f.revalidate();
            f.repaint();
        }

    }


    @Override

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("kliknalem eskejp");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
