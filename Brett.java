import javafx.scene.layout.GridPane;
// For variabelen EventHandler<MouseEvent> klikk;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Random;

class Brett extends GridPane {
    // Variabler som omhandler rutenettet
    int antRad, antKol, antBomber;
    Rute[][] rutenett;
    // Brukes for aa sette ant bomber, og sette vinn/tap tekst
    BombeTeller bombeTeller;
    // Sendes inn til alle ruter som lages
    EventHandler<MouseEvent> klikk;

    // Konstruktoer
    public Brett(int antallBomber, int kol, int rad, EventHandler<MouseEvent> klikkbehandler, BombeTeller bombeteller) {
        antRad = rad;
        antKol = kol;
        if (antallBomber >= (kol * rad)) {
            antBomber = (kol * rad)-9;
        } else {
            antBomber = antallBomber;
        }
        rutenett = new Rute[rad][kol];
        bombeTeller = bombeteller;
        klikk = klikkbehandler;
        // Mellomrom mellom ruter
        setVgap(1);
        setHgap(1);
        // Fyller brettet
        fyllBrettet();
    }

    // Kalles ogsaa naar man skal starte ny runde, derfor public
    public void fyllBrettet() {
        bombeTeller.settAntBomber(antBomber);
        // Toemmer GridPane-et
        getChildren().clear();

        for (int r = 0; r < antRad; r++) {
            for (int k = 0; k < antKol; k++) {
                Rute rute = new Rute(bombeTeller);
                rute.setOnMouseClicked(klikk);
                // Legger ruten til baade i listen og i GridPane
                add(rute, k, r);
                rutenett[r][k] = rute;
            }
        }
        // Setter naboene til alle rutene
        for (int r = 0; r < antRad; r++) {
            for (int k = 0; k < antKol; k++) {
                finnNaboer(r, k);
            }
        }
    }

    // Setter x antall tilfeldige ruter som bomber ved foerste klikk
    public void settBomber(Rute foersteKlikk) {
        Lenkeliste<Rute> ungaa = foersteKlikk.hentNaboer();
        ungaa = ungaa.kopierListe(foersteKlikk);
        Random tilfeldig = new Random();
        int teller = 0;
        while (teller != antBomber) {
            int rad = tilfeldig.nextInt(antRad);
            int kol = tilfeldig.nextInt(antKol);
            Rute r = rutenett[rad][kol];
            if (! r.erEnBombe() && !ungaa.finnesILista(r)) {
                r.settSomBombe();
                teller ++;
            }
        }
        for (Rute[] rad : rutenett) {
            for (Rute r : rad) {
                r.tellBombeNaboer();
            }
        }
    }

    // Metode for aa sette naboer
    private void finnNaboer(int rad, int kol) {
        Lenkeliste<Rute> naboer = new Lenkeliste<Rute>();
        for (int r = -1; r < 2; r ++) {
            for (int k = -1; k < 2; k ++) {
                int radKordinat = rad + r;
                int kolKordinat = kol + k;
                if ((radKordinat == rad && kolKordinat == kol) != true) {
                    if ((radKordinat < 0 || radKordinat > (antRad - 1) ||
                            kolKordinat < 0 || kolKordinat > (antKol - 1)) != true) {
                        naboer.leggTil(rutenett[radKordinat][kolKordinat]);
                    }
                }
            }
        }
        rutenett[rad][kol].settNaboer(naboer);
    }

    // Kalles naar man trykker paa en bombe
    // Aapner alle ruter som er bomber eller flagget, bombetelleren settes til :(
    public void taptBrett() {
        bombeTeller.tap();
        for (Rute[] rad : rutenett) {
            for (Rute r : rad) {
                r.aapneBomberOgFlagg();
            }
        }
    }

    // Kalles etter hvert klikk, dersom brettet er vunnet aapnes alle ruter,
    // og bombetelleren settes til :)
    public boolean sjekkOmVunnet() {
        boolean vunnet = erVunnet();
        if (vunnet) {
            bombeTeller.vinn();
            for (Rute[] rad : rutenett) {
                for (Rute r : rad) {
                    r.aapneOgByttFarge();
                }
            }
        }
        return vunnet;
    }

    // Sjekker om bretter er vunnet, kalles kun i metoden sjekkOmVunnet()
    private boolean erVunnet() {
        for (Rute[] rad : rutenett) {
            for (Rute r : rad) {
                if (!r.erEnBombe() && !r.erTrykketPaa()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Setter brettet som ikke-feilet. Maa lage nye bombe- og flagget-ruter, siden
    // jeg ikke faar til aa sette de tilbake til standard knapp-utseende *
    public void angreBrett() {
        if (erVunnet()) {return;}
        bombeTeller.oppdaterAntBomber(0);
        for (int r = 0; r < antRad; r++) {
            for (int k = 0; k < antKol; k++) {
                Rute gammel = rutenett[r][k];
                gammel.fjernBrettTapt();
                if (gammel.erEnBombe() || gammel.erFlagget()) {
                    // Fjerner den gamle fra GridPane
                    getChildren().remove(gammel);
                    // Lager en ny, identisk rute som ersatter den gamle
                    Rute ny = new Rute(bombeTeller);
                    ny.setOnMouseClicked(klikk);
                    add(ny, k, r);
                    rutenett[r][k] = ny;
                    // Setter tilstanden til den nye ruten som det samme den gamle hadde
                    if (gammel.erEnBombe()) {
                        ny.settSomBombe();
                    }
                    if (gammel.erFlagget()) {
                        ny.flagg();
                        bombeTeller.oppdaterAntBomber(+1); // Motvirker -1 som skjer i flagg()
                    }
                }
            }
        }
        // Maa sette naboene paa nytt for at det skal bli riktig
        for (int r = 0; r < antRad; r++) {
            for (int k = 0; k < antKol; k++) {
                finnNaboer(r, k);
            }
        }
    }

    // * Utskrift til terminalen av brettet
    @Override
    public String toString() {
        String str = "";
        for (Rute[] rad : rutenett) {
            for (Rute r : rad) {
                str += r.toString() + " ";
            }
            str += "\n";
        }
        return str;
    }

}