import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;

// Uavhengige EventHandler-klasser som kan brukes paa tvers av de andre klassene


// Event der naavaerende vindu skal lukkes, og forrige aapnes
class LukkOgVisForrigeVindu implements EventHandler<ActionEvent> {
    Stage naavaerende;
    Stage forrige;

    public LukkOgVisForrigeVindu(Stage naavaerendeVindu, Stage forrigeVindu) {
        naavaerende = naavaerendeVindu;
        forrige = forrigeVindu;
    }

    @Override
    public void handle(ActionEvent e) {
        naavaerende.close();
        forrige.show();
    }
}

// Event der naavaerende vindu skal sjules, og ett nytt vindu aapnes
class AapneNyttVindu implements EventHandler<ActionEvent> {
    Stage root;
    String type;
    public AapneNyttVindu(Stage rooten, String typen) {
        root = rooten;
        type = typen;
    }

    Stage forrige;
    int rad, kol, bomber;
    // For instillingervindu
    public AapneNyttVindu(Stage rooten, Stage forrigeVindu, int r, int k, int b) {
        root = rooten;
        forrige = forrigeVindu;
        rad = r;
        kol = k;
        bomber = b;
        type = "innstillinger";
    }

    @Override
    public void handle(ActionEvent e) {
        root.hide();
        if (type.equals("rekordtavle")) {
            new RekordVindu(root);
        } else if (type.equals("hjelp")) {
            new HjelpVindu(root);
        } else if (type.equals("innstillinger")) {
            new InnstillingerVindu(root, forrige, rad, kol, bomber);
        }
    }
}

// Brukes kun i Hovedmeny
class LukkVindu implements EventHandler<ActionEvent> {
    Stage vindu;

    public LukkVindu(Stage vinduet) {
        vindu = vinduet;
    }

    @Override
    public void handle(ActionEvent e) {
        vindu.close();
    }
}