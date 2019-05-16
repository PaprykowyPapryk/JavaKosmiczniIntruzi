package gra;

public class przeciwnikRuch extends przeciwnik {

    int zakresRuchu, xRuchu, predkoscRuchuPoprzek;
    boolean flagaRuchu;

    //zalozenie w prawo idzie poprawnie

    public przeciwnikRuch(int x, int y, int szerokosc, int wysokosc, int predkoscRuchu, int zakresRuchu, int predkoscRuchuPoprzek) {

        super(x, y, szerokosc, wysokosc, predkoscRuchu);
        this.zakresRuchu = zakresRuchu;
        this.xRuchu = x;
        this.flagaRuchu = true;
        this.predkoscRuchuPoprzek = predkoscRuchuPoprzek;
    }

    public void akcja() {

        if (x <= xRuchu) {
            flagaRuchu = true;
        } else if (x >= xRuchu + zakresRuchu) {
            flagaRuchu = false;
        }
        if (flagaRuchu) {
            x += predkoscRuchuPoprzek;
        } else if (!flagaRuchu) {
            x -= predkoscRuchuPoprzek;
        }

    }
}
