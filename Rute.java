import javafx.scene.control.Button;
import javafx.scene.text.*;
// Bakgrunn og farger:
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

// Rutene som brettet fylles med
class Rute extends Button {
    // Ulike tilstander en rute kan ha
    boolean bombe = false;
    boolean trykketPaa = false;
    boolean flagget = false;
    boolean brettTapt = false;
    // Variabler for naboer
    Lenkeliste<Rute> naboer;
    int bombeNaboer;
    // Trengs for aa endre bombeTelleren i flagg()
    BombeTeller bombeTeller;

    // Konstruktoer
    public Rute(BombeTeller bombeteller) {
        super(" ");
        this.bombeTeller = bombeteller;
        // Stoerrelsen paa teksten
        setFont(new Font(15));
        // Stoerrelsen paa rutene
        setMaxSize(30, 30);
        setMinSize(30, 30);
    }

    public void settSomBombe() {
        bombe = true;
    }

    // Setter naboene og teller ant bombeNaboer
    public void settNaboer(Lenkeliste<Rute> naboene) {
        naboer = naboene;
    }

    public Lenkeliste<Rute> hentNaboer() {
        return naboer;
    }

    public void tellBombeNaboer() {
        bombeNaboer = 0;
        for (Rute nabo : naboer) {
            if (nabo.bombe) {
                bombeNaboer++;
            }
        }
    }

    // Metoder for aa hente tilstanden
    public boolean erEnBombe() {
        return bombe;
    }

    public boolean erTrykketPaa() {
        return trykketPaa;
    }

    public boolean erFlagget() {
        return flagget;
    }

    // Kalles naar en rute hoyre-klikkes paa
    public void flagg() {
        if (trykketPaa) {
            return;
        }
        // Setter ruten som flagget, dersom den ikke var flagget fra foer
        if (!getText().equals("#")) {
            setText("#");
            flagget = true;
            bombeTeller.oppdaterAntBomber(-1); // Minsker antall bomber
        }
        // Fjerner flaggingen, dersom ruten var flagget fra foer
        else {
            flagget = false;
            setText(" ");
            bombeTeller.oppdaterAntBomber(+1); // Oeker ant bomber
        }
    }

    // Kalles naar en rute venstre-klikkes paa
    public void trykkPaa() throws TrykketPaaBombe {
        // Ingenting skal skje dersom en av disse er true;
        if (brettTapt || trykketPaa || flagget) {
            return;
        }

        // Om ruten er en bombe taper man brettet
        if (bombe) {
            trykketPaa = true;
            setText("x");
            setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            throw new TrykketPaaBombe();
        }
        // Om ruten har null naboer skal et stoerre omraade aapnes
        else if (bombeNaboer == 0) {
            Lenkeliste<Rute> besokt = new Lenkeliste<Rute>();
            Lenkeliste<Rute> aapneDisse = new Lenkeliste<Rute>();
            aapneDisse.leggTil(this);
            // Rekursiv metode som fyller aapneDisse med riktige ruter
            finnAapentOmraade(besokt, aapneDisse);
            for (Rute r : aapneDisse) {
                r.aapne();
            }
        } else {
            aapne();
        }
    }

    // Rekursiv. Kalles dersom ruten har 0 bombeNaboer. Legger alle ruter rundt som
    // ogsaa har 0 bombeNaboer (pluss en kant rundt dem) inn i aapneDisse
    public void finnAapentOmraade(Lenkeliste<Rute> besokt, Lenkeliste<Rute> aapneDisse) {
        // Grunnsteg: Ikke 0 bombeNaboer, eller ruten allerede er besokt
        if (bombeNaboer != 0 || besokt.finnesILista(this)) {
            return;
        }
        besokt.leggTil(this);

        for (Rute nabo : naboer) {
            // Legger til alle naboer i aapneDisse, dersom de ikke er der fra foer
            if (!aapneDisse.finnesILista(nabo)) {
                aapneDisse.leggTil(nabo);
            }
            nabo.finnAapentOmraade(besokt, aapneDisse);
        }
    }

    // Aapner ruten ved aa sette teksten til " " / antBombeNaboer / "x"
    public void aapne() {
        trykketPaa = true;
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        if (bombe) {
            setText("x");
            Color moerkeregroenn = Color.rgb(86, 130, 3, 0.5);
            setBackground(new Background(new BackgroundFill(moerkeregroenn, CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (bombeNaboer == 0) {
            setText(" ");
        } else {
            setText(bombeNaboer + "");
            if (bombeNaboer == 1) {
                setTextFill(Color.BLUE);
            } else if (bombeNaboer == 2) {
                setTextFill(Color.GREEN);
            } else if (bombeNaboer == 3) {
                setTextFill(Color.RED);
            } else if (bombeNaboer == 4) {
                setTextFill(Color.DARKBLUE);
            } else if (bombeNaboer == 5) {
                setTextFill(Color.BROWN);
            } else if (bombeNaboer == 6) {
                setTextFill(Color.DARKCYAN);
            }
        }
    }

    // Naar man vinner aapnes alle ruter, og fargen settes til lysegroenn
    public void aapneOgByttFarge() {
        aapne();
        if (!bombe) {
            Color lysegroenn = Color.rgb(86, 130, 3, 0.3);
            setBackground(new Background(new BackgroundFill(lysegroenn, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    // Naar man taper saa vises ruter som er bomber og flagg
    // Alle ruter settes som brettTapt for aa unnga at de kan trykkes paa
    public void aapneBomberOgFlagg() {
        brettTapt = true;
        if (trykketPaa) {
            return;
        }
        // Viser alle bomber som ikke er flagget
        if (bombe && !flagget) {
            setText("x");
            setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        // Fargekoder om flaggingen var korrekt eller ikke
        if (flagget) {
            if (!bombe) {
                setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                Color lysegroenn = Color.rgb(86, 130, 3, 0.3);
                setBackground(new Background(new BackgroundFill(lysegroenn, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    public void fjernBrettTapt() {
        brettTapt = false;
    }

    // * Utskrift til terminalen av brettet
    @Override
    public String toString() {
        if (bombe) {
            return "/";
        } else if (bombeNaboer == 0) {
            return " ";
        }
        return "" + bombeNaboer;
    }
}