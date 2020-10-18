import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
// Gruppere knapper
import javafx.scene.control.ToggleGroup;
// Farger og tekst
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// For aa haandtere hendelser, f.eks. klikk
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

class InnstillingerVindu extends Stage {
    Slider rader, kolonner, bomber;
    ValgKnapp knapp1, knapp2, knapp3, knapp4;
    ToggleGroup gruppe;
    Stage root;
    Stage forrige;

    // Brukes i StartVindu
    public InnstillingerVindu(Stage rooten, Stage forrigeVindu, int rad, int kol, int bomb) {
        root = rooten;
        forrige = forrigeVindu;

        rader = new Slider(5, 26, rad);
        rader.setOnMousePressed(new MarkerEgendefinert());
        kolonner = new Slider(5, 46, kol);
        kolonner.setOnMousePressed(new MarkerEgendefinert());
        bomber = new Slider(2, 250, bomb);
        bomber.setOnMousePressed(new MarkerEgendefinert());

        GridPane kulisser = new GridPane();
        fyllKulisser(kulisser);
        kulisser.setVgap(5);
        kulisser.setPrefSize(345, 400);

        Scene scene = new Scene(kulisser);
        setTitle("Innstillinger");
        setScene(scene);
        show();
    }

    private void fyllKulisser(GridPane kulisser) {
        gruppe = new ToggleGroup();
        SettVerdier klikk = new SettVerdier();

        knapp1 = new ValgKnapp("Enkel", 9, 9, 10, gruppe, klikk);
        knapp2 = new ValgKnapp("Medium", 16, 16, 40, gruppe, klikk);
        knapp3 = new ValgKnapp("Vanskelig", 16, 30, 99, gruppe, klikk);
        knapp4 = new ValgKnapp("Egendefinert", 0, 0, 0, gruppe, null);

        // Disse legges til i Pane
        Button overskrift = new Button("Velg vanskelighetsgrad");
        overskrift.setMaxSize(345, 40);
        overskrift.setMinSize(345, 40);
        overskrift.setFont(new Font(15));
        overskrift.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        SliderPane radGlider = new SliderPane("Rader", rader);
        SliderPane kolGlider = new SliderPane("Kolonner", kolonner);
        SliderPane bomberGlider = new SliderPane("Bombeer", bomber);

        ValgPane valg1 = new ValgPane(knapp1);
        ValgPane valg2 = new ValgPane(knapp2);
        ValgPane valg3 = new ValgPane(knapp3);
        ValgPane valg4 = new ValgPane(knapp4, rader, kolonner, bomber);

        Button ferdig = new Button("Bekreft");
        ferdig.setOnAction(new GjennomfoerBytte());
        ferdig.setLayoutX(10 + 55);
        ferdig.setMaxSize(100, 30);
        ferdig.setMinSize(100, 30);

        Button tilbake = new Button("Tilbake");
        tilbake.setOnAction(new LukkOgVisForrigeVindu(this, forrige));
        tilbake.setLayoutX(345 - 110 - 55);
        tilbake.setMaxSize(100, 30);
        tilbake.setMinSize(100, 30);

        kulisser.addColumn(0, overskrift, new Label(" "), valg1, valg2, valg3, valg4, new Label(" "), radGlider,
                kolGlider, bomberGlider, new Label(" "), new Pane(ferdig, tilbake));
    }

    // Setter verdiene til gliderne tilsvarende knappen man trykket paa
    class SettVerdier implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            ValgKnapp knapp = (ValgKnapp) e.getSource();
            rader.setValue(knapp.hentRader());
            kolonner.setValue(knapp.hentKolonner());
            bomber.setValue(knapp.hentBomber());
        }
    }

    // Velger Egendefinert om man bruker sliderne
    class MarkerEgendefinert implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (gruppe.getSelectedToggle() != knapp4) {
                knapp4.fire();
            }
        }
    }

    // Bytter spillebrettet i MineSweeper
    class GjennomfoerBytte implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            forrige.close();
            ValgKnapp valgt = (ValgKnapp) gruppe.getSelectedToggle();
            if (valgt != null) {
                if (valgt == knapp4) {
                    knapp4.settRader(Math.round((float) rader.getValue()));
                    knapp4.settKolonner(Math.round((float) kolonner.getValue()));
                    knapp4.settBomber(Math.round((float) bomber.getValue()));
                }
                new MineSweeper(root, valgt.hentRader(), valgt.hentKolonner(), valgt.hentBomber());
                close();
            }
        }
    }
}