package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class silnik extends JPanel implements ActionListener, KeyListener {

    private Image backgroundImage;
    private static Font monoFont = new Font("Monospaced", Font.BOLD
            | Font.ITALIC, 36);
    private static Font tekstFont = new Font("Monospaced", Font.BOLD
            | Font.ITALIC, 15);
    private static Color m_tBlue = new Color(0, 0, 0, 10);

    ArrayList<przeciwnik> przeciwnicySzablony = new ArrayList<>();
    ArrayList<pocisk> pociski = new ArrayList<>();
    ArrayList<pocisk> pociskiPrzeciwnikow = new ArrayList<>();
    ArrayList<przeciwnik> przeciwnicy = new ArrayList<>();


    Timer t = new Timer(10, this);

    int xgracza = 0, ygracza = 330, velx = 0, odliczanie = 0, poziomTrudnosci = 1, zycia = 3, punkty = 0;
    boolean escape = false;

    public silnik(int poziomTrudnosci) {


        this.poziomTrudnosci = poziomTrudnosci;
        przeciwnicySzablony.add(new przeciwnikRuch(300, 50, 20, 20, 1, 100,
                5));
        przeciwnicySzablony.add(new przeciwnikStrzal(500, 100, 20, 20, 1, 50));
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);


    }

    public void dodajPrzeciwnika() {

        if (odliczanie == 66 / poziomTrudnosci || odliczanie == 120 / poziomTrudnosci || odliczanie == 180 / poziomTrudnosci || odliczanie == 240 / poziomTrudnosci) {

            Random generator = new Random();
            int i = generator.nextInt(550) + 1;

            przeciwnicy.add(new przeciwnik(i, -100, 30, 30, 1));
            odliczanie++;

        } else if (odliczanie == 126 / poziomTrudnosci) {

            Random generator = new Random();
            int i = generator.nextInt(550) + 1;

            przeciwnicy.add(new przeciwnikRuch(i, -100, 30, 30, 1, 130, 1));
            odliczanie++;
        } else if (odliczanie == 300 / poziomTrudnosci) {

            Random generator = new Random();
            int i = generator.nextInt(550) + 1;

            przeciwnicy.add(new przeciwnikStrzal(i, -100, 30, 30, 1, 100 / poziomTrudnosci));
            odliczanie++;
        } else if (odliczanie >= 303 / poziomTrudnosci) {
            odliczanie = 0;
        } else {
            odliczanie++;
        }

    }

    public void ruchPrzeciwnikow() {
        Iterator<przeciwnik> i = przeciwnicy.iterator();
        while (i.hasNext()) {
            przeciwnik s = i.next();
            s.y += s.predkoscRuchu * poziomTrudnosci * ((punkty / 50) + 1);
            if (s.y >= 330) {
                i.remove();
            }

        }

    }

    public void analizaPociskow() {
        for (pocisk s : pociski) {
            Iterator<pocisk> d = pociskiPrzeciwnikow.iterator();
            while (d.hasNext()) {
                pocisk e = d.next();
                if ((s.y <= e.y) && (s.x >= e.x) && s.x <= e.x + 5) {
                    d.remove();
                }

            }

        }
    }

    public void ruchPociskow() {
        Iterator<pocisk> i = pociski.iterator();
        while (i.hasNext()) {
            pocisk s = i.next();


            if (s.y <= 0) {
                i.remove();
            } else {
                s.y -= 5;
            }


        }
    }

    public void analizaPociskowPrzeciwnikow() {
        Iterator<pocisk> i = pociskiPrzeciwnikow.iterator();
        while (i.hasNext()) {
            pocisk s = i.next();


            if (s.y >= 330) {
                i.remove();
            } else {
                s.y += s.predkoscPocisku * poziomTrudnosci * ((punkty / 50) + 1);
            }


        }
    }

    public void analizaKolizjiPrzeciwnikow() {


        Iterator<przeciwnik> i = przeciwnicy.iterator();
        while (i.hasNext()) {
            przeciwnik s = i.next();
            for (pocisk object : pociski) {


                if ((object.x >= s.x - 8) && (object.x <= (s.x + s.szerokosc + 1) && (object.y <= s.y + s.wysokosc))) {
                    i.remove();
                    punkty += poziomTrudnosci;
                }

            }
        }
    }

    public void analizaKolizjiGracza() {


        Iterator<pocisk> i = pociskiPrzeciwnikow.iterator();
        while (i.hasNext()) {
            pocisk s = i.next();


            if ((s.x >= xgracza) && (s.x <= (xgracza + 30) && (s.y >= ygracza - 5))) {
                zycia--;
                i.remove();
            }

        }

        Iterator<przeciwnik> t = przeciwnicy.iterator();
        while (t.hasNext()) {
            przeciwnik d = t.next();

            if ((d.x >= xgracza - 10) && (d.x + d.szerokosc <= (xgracza + 45) && (d.y + d.wysokosc >= ygracza))) {
                zycia--;
                t.remove();
            }
        }

    }

    public void akcjePrzeciwnikow() {
        for (przeciwnik object : przeciwnicy) {

            object.akcja();
            if (object instanceof przeciwnikStrzal) {
                if (((przeciwnikStrzal) object).strzal) {
                    pociskiPrzeciwnikow.add(new pocisk(object.x + 12, object.y, 2));
                }
            }
        }
    }

    public void analiza() {
        dodajPrzeciwnika();
        ruchPociskow();
        analizaPociskow();
        analizaKolizjiPrzeciwnikow();
        akcjePrzeciwnikow();
        analizaPociskowPrzeciwnikow();
        analizaKolizjiGracza();
        ruchPrzeciwnikow();

    }


    public void actionPerformed(ActionEvent e) {
        if (xgracza < 0) {
            velx = 0;
            xgracza = 530;
        }

        if (xgracza > 530) {
            velx = 0;
            xgracza = 1;
        }

        xgracza += velx;

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_LEFT) {

            velx = -5;
        }
        if (code == KeyEvent.VK_RIGHT) {

            velx = 5;
        }
        if (code == KeyEvent.VK_SPACE) {
            pociski.add(new pocisk(xgracza + 14, ygracza + 15, 5));
        }


    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        velx = 0;

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("img\\kosmos.jpg");
        Image image = img.getImage();

        ImageIcon img1 = new ImageIcon("img\\statek.png");
        Image imageStatek = img1.getImage();

        ImageIcon img2 = new ImageIcon("img\\statekPrzeciwnik.png");
        Image imageStatekPrzeciwnik = img2.getImage();

        ImageIcon img3 = new ImageIcon("img\\statekPrzeciwnikStrzal.png");
        Image imageStatekPrzeciwnikStrzal = img3.getImage();

        ImageIcon img4 = new ImageIcon("img\\statekPrzeciwnikRuch.png");
        Image imageStatekPrzeciwnikRuch = img4.getImage();


        g.drawImage(image, 0, 0, 580, 370, null);
        if (zycia > 0) {
            g.setColor(Color.RED);
            analiza();
            for (pocisk object : pociski) {
                g.fillOval(object.x, object.y, 10, 10);
            }
            for (pocisk object : pociskiPrzeciwnikow) {
                g.fillOval(object.x, object.y, 5, 5);
            }
            for (przeciwnik object : przeciwnicy) {


                if (object instanceof przeciwnikStrzal) {
                    g.drawImage(imageStatekPrzeciwnikStrzal, object.x, object.y, object.szerokosc, object.wysokosc, null);
                } else if (object instanceof przeciwnikRuch) {
                    g.drawImage(imageStatekPrzeciwnikRuch, object.x, object.y, object.szerokosc, object.wysokosc, null);
                } else {
                    g.drawImage(imageStatekPrzeciwnik, object.x, object.y, object.szerokosc, object.wysokosc, null);
                }
            }


            g.drawImage(imageStatek, xgracza, ygracza, 39, 30, null);

            g.setFont(tekstFont);
            g.setColor(Color.WHITE);
            g.drawString("Punkty:", 5, 370);
            g.drawString(String.valueOf(punkty), 75, 370);


            g.drawString("Zycia:", 470, 370);
            for (int i = 0; i < zycia; i++) {
                g.setColor(Color.RED);
                g.fillRect(530 + (i * 15), 360, 10, 10);
            }

        } else {

            g.setFont(monoFont);
            FontMetrics fm = g.getFontMetrics();

            g.setColor(Color.WHITE);
            g.drawString("PRZEGRALES", 170, 200);
            g.drawString("wcisnij powrot do menu", 50, 300);

            g.setColor(m_tBlue);
            g.fillRect(2, 2, 580, 380);


        }
    }

}
