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

public class Silnik extends JPanel implements ActionListener, KeyListener {

    private Image image, imageStatek, imageStatekPrzeciwnik, imageStatekPrzeciwnikStrzal, imageStatekPrzeciwnikRuch;

    private static Font monoFont;
    private static Font tekstFont;
    private static Color m_tBlue;

    ArrayList<PrzeciwnikPodstawowy> przeciwnicy;
    ArrayList<Pocisk> pociski, pociskiPrzeciwnikow;
    ArrayList<>

    Random generator;

    Timer timer;

    int xgracza = 0, ygracza = 330, velx = 0, odliczanie = 0, poziomTrudnosci = 1, zycia = 3, punkty = 0;
    Rectangle r1,r2;

    public Silnik(int poziomTrudnosci) {

        wczytajImg();

        monoFont = new Font("Monospaced", Font.BOLD | Font.ITALIC, 36);
        tekstFont = new Font("Monospaced", Font.BOLD | Font.ITALIC, 15);
        m_tBlue = new Color(0, 0, 0, 10);

        generator = new Random();

        timer = new Timer(10, this);

        pociski = new ArrayList<>();
        pociskiPrzeciwnikow = new ArrayList<>();
        przeciwnicy = new ArrayList<>();

        this.poziomTrudnosci = poziomTrudnosci;


        timer.start();
        addKeyListener(this);
        setFocusable(true);
        // setFocusTraversalKeysEnabled(false);


    }

    public void wczytajImg() {
        image = new ImageIcon("img\\kosmos.jpg").getImage();
        imageStatek = new ImageIcon("img\\statek.png").getImage();
        imageStatekPrzeciwnik = new ImageIcon("img\\statekPrzeciwnik.png").getImage();
        imageStatekPrzeciwnikStrzal = new ImageIcon("img\\statekPrzeciwnikStrzal.png").getImage();
        imageStatekPrzeciwnikRuch = new ImageIcon("img\\statekPrzeciwnikRuch.png").getImage();
    }

    public void dodajPrzeciwnika() {
        int i = 0;
        if(odliczanie%(60/poziomTrudnosci)==0){

            i = generator.nextInt(550) + 1;
            przeciwnicy.add(new PrzeciwnikPodstawowy(i, -100, 30, 30, 1));
            odliczanie++;

        } else if (odliczanie == 126 / poziomTrudnosci) {
            i = generator.nextInt(550) + 1;

            przeciwnicy.add(new PrzeciwnikRuch(i, -100, 30, 30, 1, 130, 1));
            odliczanie++;
        } else if (odliczanie == 300 / poziomTrudnosci) {

            i = generator.nextInt(550) + 1;

            przeciwnicy.add(new PrzeciwnikStrzal(i, -100, 30, 30, 1, 100 / poziomTrudnosci));
            odliczanie++;
        } else if (odliczanie >= 303 / poziomTrudnosci) {
            odliczanie = 0;
        } else {
            odliczanie++;
        }
    }

    public void ruchPrzeciwnikow() {

        Iterator<PrzeciwnikPodstawowy> i = przeciwnicy.iterator();
        while (i.hasNext()) {
            PrzeciwnikPodstawowy s = i.next();
            s.y += s.predkoscRuchu * poziomTrudnosci * ((punkty / 50) + 1);
            if (s.y >= 330) {
                i.remove();
            }
        }

    }

    public void analizaPociskow() {
        for (Pocisk s : pociski) {
            Iterator<Pocisk> d = pociskiPrzeciwnikow.iterator();
            while (d.hasNext()) {
                Pocisk e = d.next();
                if ((s.y <= e.y) && (s.x >= e.x) && s.x <= e.x + 5) {
                    d.remove();
                }

            }

        }
    }

    public void ruchPociskow() {
        Iterator<Pocisk> i = pociski.iterator();
        while (i.hasNext()) {
            Pocisk s = i.next();


            if (s.y <= 0) {
                i.remove();
            } else {
                s.y -= 5;
            }


        }
    }

    public void analizaPociskowPrzeciwnikow() {
        Iterator<Pocisk> i = pociskiPrzeciwnikow.iterator();
        while (i.hasNext()) {
            Pocisk s = i.next();


            if (s.y >= 330) {
                i.remove();
            } else {
                s.y += s.predkoscPocisku * poziomTrudnosci * ((punkty / 50) + 1);
            }


        }
    }

    public void analizaKolizjiPrzeciwnikow() {


        Iterator<PrzeciwnikPodstawowy> i = przeciwnicy.iterator();
        while (i.hasNext()) {
            PrzeciwnikPodstawowy s = i.next();
            for (Pocisk object : pociski) {
                if ((object.x >= s.x - 8) && (object.x <= (s.x + s.szerokosc + 1) && (object.y <= s.y + s.wysokosc))) {

                    i.remove();
                    punkty += poziomTrudnosci;
                }

            }
        }
    }

    public void analizaKolizjiGracza() {
        
        Iterator<Pocisk> i = pociskiPrzeciwnikow.iterator();
        while (i.hasNext()) {
            Pocisk s = i.next();
            if ((s.x >= xgracza) && (s.x <= (xgracza + 40) && (s.y >= ygracza))) {
                zycia--;
                i.remove();
            }
        }
        Iterator<PrzeciwnikPodstawowy> t = przeciwnicy.iterator();
        while (t.hasNext()) {
             PrzeciwnikPodstawowy d = t.next();
             r1 = new Rectangle(d.x,d.y,d.szerokosc,d.wysokosc);
             r2 = new Rectangle(xgracza,ygracza,40,30);
            if(r1.intersects(r2))
            {
                zycia--;
                t.remove();
            }
        }

    }

    public void akcjePrzeciwnikow() {
        for (PrzeciwnikPodstawowy object : przeciwnicy) {
            object.akcja();
            if ((object instanceof PrzeciwnikStrzal)&&((PrzeciwnikStrzal) object).strzal) {
                    pociskiPrzeciwnikow.add(new Pocisk(object.x + 12, object.y, 2));
            }
        }
    }

    public void analiza() {

        analizaPolozeniaGracza();
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
        analiza();
        repaint();
    }

    private void analizaPolozeniaGracza() {

        if (xgracza < 0) {
            velx = 0;
            xgracza = 530;
        }

        if (xgracza > 530) {
            velx = 0;
            xgracza = 1;
        }

        xgracza += velx;
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
            pociski.add(new Pocisk(xgracza + 14, ygracza + 15, 5));
        }


    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        velx = 0;

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 580, 370, null);
        
        if (zycia > 0) {
            rysujPrzeciwnikow(g);
            rysujPociski(g);
            rysujStatekGracza(g);
            rysujPunkty(g);
            rysujZycia(g);

        } else {
            timer.stop();
            rysujPrzegrana(g);
        }
    }

    private void rysujPrzegrana(Graphics g) {
        g.setFont(monoFont);
        FontMetrics fm = g.getFontMetrics();

        g.setColor(Color.WHITE);
        g.drawString("PRZEGRALES", 170, 200);
        g.drawString("wcisnij powrot do menu", 50, 300);
    }

    private void rysujZycia(Graphics g) {
        g.drawString("Zycia:", 470, 370);
        for (int i = 0; i < zycia; i++) {
            g.setColor(Color.RED);
            g.fillRect(530 + (i * 15), 360, 10, 10);
        }
    }

    private void rysujPunkty(Graphics g) {
        g.setFont(tekstFont);
        g.setColor(Color.WHITE);
        g.drawString("Punkty:", 5, 370);
        g.drawString(String.valueOf(punkty), 75, 370);
    }

    private void rysujStatekGracza(Graphics g) {
        g.drawImage(imageStatek, xgracza, ygracza, 40, 30, null);

    }

    private void rysujPociski(Graphics g) {
        g.setColor(Color.RED);
        for (Pocisk object : pociski) {
            g.fillOval(object.x, object.y, 10, 10);
        }
        for (Pocisk object : pociskiPrzeciwnikow) {
            g.fillOval(object.x, object.y, 5, 5);
        }
    }

    private void rysujPrzeciwnikow(Graphics g) {
        for (PrzeciwnikPodstawowy object : przeciwnicy) {
            if (object instanceof PrzeciwnikStrzal) {
                g.drawImage(imageStatekPrzeciwnikStrzal, object.x, object.y, object.szerokosc, object.wysokosc, null);
            } else if (object instanceof PrzeciwnikRuch) {
                g.drawImage(imageStatekPrzeciwnikRuch, object.x, object.y, object.szerokosc, object.wysokosc, null);
            } else {
                g.drawImage(imageStatekPrzeciwnik, object.x, object.y, object.szerokosc, object.wysokosc, null);
            }
        }
    }

}
