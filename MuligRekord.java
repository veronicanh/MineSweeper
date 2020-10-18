import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
// Skrive til/fra fil
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
// For aa faa dagens dato
import java.text.SimpleDateFormat;
import java.util.Date;
// Events
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
// Font
import javafx.scene.text.Font;

class MuligRekord {
    private String tidSomTekst;
    private String nyFilTekst = "";
    private File fil;

    public MuligRekord(int rad, int kol, int bomber, String tiden) {
        tidSomTekst = tiden;
        String type;
        if (rad == 9 && kol == 9 && bomber == 10) {
            type = "enkel";
        } else if (rad == 16 && kol == 16 && bomber == 40) {
            type = "medium";
        } else if (rad == 16 && kol == 30 && bomber == 99) {
            type = "vanskelig";
        } else {
            type = "x";
        }
        if (!type.equals("x")) {
            sjekkOmRekord(type);
        }
    }

    // Legger til rekorden paa riktig posisjon, dersom den er bedre enn noen av de
    // 10 andre
    public void sjekkOmRekord(String type) {
        // Finner filen og lager Scanner
        String filnavn = type + "Rekorder.txt";
        Scanner scan;
        try {
            fil = new File("/Users/Veronica/Documents/MineSweeperRekorder/" + filnavn);
            scan = new Scanner(fil);
        } catch (Exception e) {
            System.out.println("Feil, Rekord.sjekkOmRekord(): " + e);
            return;
        }

        // Finner ut hva tiden er, som tall istedenfor String
        int minutt = 0;
        double sek;
        if (tidSomTekst.contains(":")) {
            String[] biter = tidSomTekst.split(":");
            minutt = Integer.parseInt(biter[0]);
            sek = Double.parseDouble(biter[1]);
        } else {
            sek = Double.parseDouble(tidSomTekst);
        }

        // Leser gjennom filen
        boolean plassertRekord = false;
        while (scan.hasNextLine()) {
            String linje = scan.nextLine();
            String filTid = linje.split(",")[0];
            int filMinutt = 0;
            double filSek;
            if (filTid.contains(":")) {
                String[] biter = filTid.split(":");
                filMinutt = Integer.parseInt(biter[0]);
                filSek = Double.parseDouble(biter[1]);
            } else {
                filSek = Double.parseDouble(filTid);
            }
            // Dersom tiden er bedre enn den i filen, saa skal den legges inn foran
            if (!plassertRekord && minutt == filMinutt && sek < filSek) {
                nyFilTekst += "FYLL_INN\n";
                plassertRekord = true;
            } else if (!plassertRekord && minutt < filMinutt) {
                nyFilTekst += "FYLL_INN\n";
                plassertRekord = true;
            }
            nyFilTekst += linje + "\n";
        }
        // Legger inn rekorden paa siste linje dersom den er daarligere enn de foran
        if (!plassertRekord && nyFilTekst.split("\n").length < 10) {
            plassertRekord = true;
            nyFilTekst += "FYLL_INN\n";
        }
        if (plassertRekord) {
            lagNyRekordVindu(true);
        }
        scan.close();
    }

    // Lager pop-up-vindu og ber bruker skrive inn navn
    private void lagNyRekordVindu(boolean foersteForsok) {
        Label overskrift;
        if (foersteForsok) {
            overskrift = new StyleLabel("Din tid (" + tidSomTekst + " sek) er blant de 10 beste", 300, 40,
                    new Font(13));
        } else {
            overskrift = new StyleLabel("Navnet kan ikke vaere lengere enn 10 tegn", 300, 40, new Font(13));
        }

        Label tekst = new Label("Skriv inn navnet og trykk enter");
        tekst.setLayoutX(10);
        tekst.setLayoutY(55);
        TextField navnBoks = new TextField();
        navnBoks.setLayoutX(180);
        navnBoks.setLayoutY(50);
        navnBoks.setMaxWidth(110);
        navnBoks.setMinWidth(110);

        Pane kulisser = new Pane();
        kulisser.getChildren().addAll(overskrift, tekst, navnBoks);
        kulisser.setMaxHeight(88);
        kulisser.setMinHeight(88);

        Scene scene = new Scene(kulisser);
        Stage teater = new Stage();
        navnBoks.setOnKeyReleased(new LagreInput(teater));
        teater.setTitle("Ny rekord!");
        teater.setScene(scene);
        teater.show();
    }

    class LagreInput implements EventHandler<KeyEvent> {
        Stage vindu;

        public LagreInput(Stage vinduet) {
            vindu = vinduet;
        }

        @Override
        public void handle(KeyEvent e) {
            if (e.getCode() == KeyCode.ENTER) {
                vindu.close();
                TextField navnBoks = (TextField) e.getSource();
                // Navn kan maks vaere 10 tegn
                if (navnBoks.getText().length() > 10) {
                    lagNyRekordVindu(false);
                } else if (navnBoks.getText().equals("")) {
                    skrivTilFil("-");
                } else {
                    skrivTilFil(navnBoks.getText());
                }
            }
        }
    }

    // Overskriver den gamle filen
    private void skrivTilFil(String navn) {
        // Forkorter nyFil til 10 linjer
        String finalFil = "";
        int teller = 0;
        for (String linje : nyFilTekst.split("\n")) {
            if (teller < 10) {
                finalFil += linje + "\n";
                teller++;
            }
        }
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        finalFil = finalFil.replace("FYLL_INN", (tidSomTekst + "," + navn + "," + formater.format(new Date())));
        // Skriver til filen
        try {
            PrintWriter printer = new PrintWriter(fil);
            printer.print(finalFil);
            printer.close();
        } catch (Exception e) {
            System.out.println("Feil, Rekord.skrivTilFil(): " + e);
        }
    }
}
