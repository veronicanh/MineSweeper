import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

class ValgKnapp extends ToggleButton {
    int rader, kolonner, bomber;

    public ValgKnapp(String tekst, int rad, int kol, int bomb, ToggleGroup gruppe, EventHandler<ActionEvent> klikk) {
        super(tekst);
        rader = rad;
        kolonner = kol;
        bomber = bomb;
        setOnAction(klikk);
        setToggleGroup(gruppe);
    }

    // Brukes for knappen "Egendefinert"
    public int hentRader() {
        return rader;
    }

    public int hentKolonner() {
        return kolonner;
    }

    public int hentBomber() {
        return bomber;
    }

    public void settRader(int nyVerdi) {
        rader = nyVerdi;
    }

    public void settKolonner(int nyVerdi) {
        kolonner = nyVerdi;
    }

    public void settBomber(int nyVerdi) {
        bomber = nyVerdi;
    }
}