import javafx.scene.control.Button;
// Bakgrunn og farger:
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

// Egen klasse for BombeTeller, for aa haandtere farge-endring.
class BombeTeller extends Button {
    int antallBomber;

    // Variabler for de ulike fargene telleren har, fra moerkest til lysest
    Color darkest =     Color.DARKRED;
    Color dark =        Color.BROWN;
    Color darkMedium =  Color.CHOCOLATE;
    Color lightMedium = Color.SANDYBROWN;
    Color light =       Color.NAVAJOWHITE;
    Color lightest =    Color.ANTIQUEWHITE;

    // Angir ved hvilket ant bomber telleren skal bytte til en lysere farge
    int grenseverdi;

    // Kalles naar et nytt spill er startet
    public void settAntBomber(int startAntall) {
        antallBomber = startAntall;
        grenseverdi = (startAntall / 6); // delt paa 6 siden det er 6 farger
        setText(antallBomber + " BOMBER");
        // Bakrunnen starter som den moerkeste fargen
        setBackground(new Background(new BackgroundFill(darkest, CornerRadii.EMPTY, Insets.EMPTY)));
        setTextFill(Color.WHITE);
    }

    // Kalles naar en rute flagges
    public void oppdaterAntBomber(int endring) {
        antallBomber += endring;
        setText(antallBomber + " BOMBER");

        // lightest
        if (antallBomber <= grenseverdi) {
            setBackground(new Background(new BackgroundFill(lightest, CornerRadii.EMPTY, Insets.EMPTY)));
            setTextFill(lightMedium);
        } // light
        else if (antallBomber <= (grenseverdi * 2)) {
            setBackground(new Background(new BackgroundFill(light, CornerRadii.EMPTY, Insets.EMPTY)));
            setTextFill(darkMedium);
        } // lightMedium
        else if (antallBomber <= (grenseverdi * 3)) {
            setBackground(new Background(new BackgroundFill(lightMedium, CornerRadii.EMPTY, Insets.EMPTY)));
            setTextFill(Color.WHITE);
        } // darkMedium
        else if (antallBomber <= (grenseverdi * 4)) {
            setBackground(new Background(new BackgroundFill(darkMedium, CornerRadii.EMPTY, Insets.EMPTY)));
            setTextFill(Color.WHITE);
        } // dark
        else if (antallBomber <= (grenseverdi * 5)) {
            setTextFill(Color.WHITE);
            setBackground(new Background(new BackgroundFill(dark, CornerRadii.EMPTY, Insets.EMPTY)));
        } // darkest
        else {
            setTextFill(Color.WHITE);
            setBackground(new Background(new BackgroundFill(darkest, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    // Setter teksten for tap
    public void tap() {
        setText("DU TAPTE");
        setTextFill(Color.WHITE);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // Setter teksten for vinn
    public void vinn() {
        setText("DU VANT!");
        setTextFill(Color.BLACK);
        Color moerkeregroenn = Color.rgb(86, 130, 3, 0.5);
        setBackground(new Background(new BackgroundFill(moerkeregroenn, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}