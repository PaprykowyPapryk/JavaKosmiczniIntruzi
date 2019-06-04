package gra;

public class Pocisk {

    int x = 0;
    int y = 0;
    int predkoscPocisku;

    public Pocisk(int x, int y, int przedkoscPocisku) {
        this.x = x;
        this.y = y;
        this.predkoscPocisku = przedkoscPocisku;
    }

    public void remove() {
        x=-100;
        y=-100;
    }
}
