package gra;

public class Wybuch {

    int x ;
    int y ;
    int czasTrwania;

    public Wybuch() {
        x=0;
        y=0 ;
        czasTrwania=0;
    }


    public Wybuch(int x, int y, int czasTrwania) {
        this.x = x;
        this.y = y;
        this.czasTrwania = czasTrwania;
    }


    public void odliczanie(){
        czasTrwania--;
    }
}
