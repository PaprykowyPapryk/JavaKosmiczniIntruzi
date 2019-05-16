package gra;

public class przeciwnikStrzal extends przeciwnik {
    int check, czestotliwoscStrzalu;
    boolean strzal;

    public przeciwnikStrzal(int x, int y, int szerokosc, int wysokosc, int predkoscRuchu, int czestotliwoscStrzalu) {
        super(x, y, szerokosc, wysokosc, predkoscRuchu);
        this.czestotliwoscStrzalu = czestotliwoscStrzalu;
        this.strzal = true;
    }

    @Override
    public void akcja() {
        if (check == czestotliwoscStrzalu) {
            check = 0;
            strzal = true;
        } else {
            strzal = false;
            check++;
        }
    }
}
