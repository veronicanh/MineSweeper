import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
// Farger og fonter
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// Lese fra fil
import java.io.File;
import java.util.Scanner;

// Klasse for aa lage og vise rekord-oversikt-vindu
public class RekordVindu extends Stage {
    // Bruker root i LukkOgVisForrigeVindu()
    public RekordVindu(Stage root) {
        // Lager ett pane til hver vanskelighetsgrad
        Pane enkel = lesFilTilKulisser("enkel");
        enkel.setLayoutX(10);
        enkel.setLayoutY(10);
        Pane medium = lesFilTilKulisser("medium");
        medium.setLayoutX(315);
        medium.setLayoutY(10);
        Pane vanskelig = lesFilTilKulisser("vanskelig");
        vanskelig.setLayoutX(620);
        vanskelig.setLayoutY(10);

        Button tilbake = new Button("Tilbake");
        tilbake.setOnAction(new LukkOgVisForrigeVindu(this, root));
        tilbake.setLayoutX(10);
        tilbake.setLayoutY(250);

        Pane kulisser = new Pane(enkel, medium, vanskelig, tilbake);
        kulisser.setPrefSize((305 * 3) + 10, 250 + 35);

        Scene scene = new Scene(kulisser);
        setTitle("Rekorder");
        setScene(scene);
        show();
    }

    // Metode for aa lese inn data fra fil om rekordenex
    private Pane lesFilTilKulisser(String type) {
        String filnavn = type + "Rekorder.txt";
        Scanner scan;
        try {
            scan = new Scanner(new File("/Users/Veronica/Documents/MineSweeperRekorder/" + filnavn));
        } catch (Exception e) {
            Label feilmelding = new Label(
                    "Feil, fant ikke filen: /Users/Vernica/Documents/MineSweeperRekorder/" + filnavn);
            feilmelding.setWrapText(true);
            feilmelding.setMaxWidth(280);
            return new Pane(feilmelding);
        }

        GridPane liste = new GridPane();
        liste.setHgap(5);
        liste.setVgap(2);
        liste.setLayoutY(45);

        int teller = 1;
        // Listen skal ha 10 linjer
        while (teller < 11) {
            String[] biter;
            if (scan.hasNextLine()) {
                biter = scan.nextLine().split(",");
            } else {
                String[] biters = { "-.-", "", "" };
                biter = biters;
            }
            // Lager labler med infoen fra filen
            Label telleren = new StyleLabel(teller + ".", 30, "right", new Font("Courier New", 14));
            Label tiden = new StyleLabel(biter[0] + "", 60, "right", new Font("Courier New", 14));
            Label navn = new StyleLabel(" " + biter[1], 100, "left", new Font("Courier New", 14));
            Label dato = new StyleLabel(biter[2], 90, "left", new Font("Courier New", 14));
            liste.addRow(teller, telleren, tiden, navn, dato);
            teller++;
        }
        Label overskrift = new StyleLabel(type.toUpperCase(), 295, 40, new Font("Courier New", 19));
        // Bokser for aa ramme inn
        Rectangle boks = new Rectangle(295, 230);
        boks.setStroke(Color.valueOf("#adadad"));
        boks.setStrokeWidth(1);
        boks.setFill(null);
        Rectangle boks2 = new Rectangle(295, 40);
        boks2.setStroke(Color.valueOf("#adadad"));
        boks2.setStrokeWidth(1);
        boks2.setFill(null);
        // Legger til alt som er laget i denne metoden
        Pane kulisser = new Pane(overskrift, liste, boks, boks2);
        return kulisser;
    }
}