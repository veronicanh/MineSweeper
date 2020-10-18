import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
// For aa haandtere hendelser, f.eks. klikk
import javafx.event.EventHandler;

import javafx.scene.layout.*;
import javafx.geometry.Insets;

import javafx.application.Platform;
import javafx.event.ActionEvent;
// For aa kunne skille mellom venstre/hoyre-klikk
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
// For aa handtere tastatur-kommandoer:
// "F" = fullscreen, (CMD+)Z = angre, (CMD+)N = ny, I = instillinger, slett = avslutt

public class MineSweeper extends Stage {
    // Dimensjonene paa rutenettet
    private int antRad, antKol, antBomber;
    // Andre variabler
    Brett brett;
    // Variabler knyttet til stoppeklokken
    Button stoppeklokke = new Button("0,0");
    boolean fortsett = true;
    boolean foersteTrekk = true;

    public MineSweeper(Stage root, int rad, int kol, int bomber) {
        antRad = rad;
        antKol = kol;
        antBomber = bomber;
        Pane kulisser = new Pane();
        BombeTeller bombeTeller = new BombeTeller();
        bombeTeller.setLayoutY(10);
        bombeTeller.setLayoutX((((antKol * 30)) / 2) - 25); // Oeverst ca. i midten

        brett = new Brett(antBomber, antKol, antRad, new Klikkbehandler(), bombeTeller);
        brett.setLayoutX(10);
        brett.setLayoutY(50);

        Button nyRunde = new StyleButton(68, 27, "Ny runde", 10, 10);
        nyRunde.setOnAction(new StarteNyRunde());

        Button angre = new StyleButton(51, 27, "Angre", (antKol * 31) + 10 - 52, 10);
        angre.setOnAction(new AngreTrekk());

        Button innstillinger = new StyleButton(80, 27, "Innstillinger", 10, (antRad * 31) + 60);
        innstillinger.setOnAction(new AapneNyttVindu(root, this, antRad, antKol, antBomber));

        stoppeklokke.setText("0.0");
        stoppeklokke.setBackground(
                new Background(new BackgroundFill(Color.valueOf("#f4f4f4"), CornerRadii.EMPTY, Insets.EMPTY)));
        stoppeklokke.setBorder(new Border(new BorderStroke(Color.valueOf("#b8b8b8"), BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(1))));
        stoppeklokke.setFont(new Font("Courier New", 13));
        stoppeklokke.setOnAction(new SkjulTekst());
        stoppeklokke.setMaxWidth(80);
        stoppeklokke.setMinWidth(80);
        stoppeklokke.setLayoutY((antRad * 31) + 60);
        stoppeklokke.setLayoutX((((antKol * 30)) / 2) - 20); // Nederst ca. i midten

        Button hovedmeny = new StyleButton(85, 27, "Hovedmeny", (antKol * 31) - 76, (antRad * 31) + 60);
        hovedmeny.setOnAction(new LukkOgVisForrigeVindu(this, root));

        kulisser.setPrefSize((antKol * 31) + 20, (antRad * 31) + 80 + 20);
        kulisser.getChildren().addAll(nyRunde, angre, bombeTeller, brett, innstillinger, stoppeklokke, hovedmeny);

        Scene scene = new Scene(kulisser);
        scene.setOnKeyReleased(new TastaturLeser());
        setTitle("MineSweeper");
        setScene(scene);
        if (rad > 22) {
            setFullScreen(true);
        }
        show();
    }

    // Naar man trykker paa en rute skjer dette
    class Klikkbehandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Rute r = ((Rute) e.getSource());
            if (foersteTrekk) {
                stoppeklokke.setText("0.0");
                new SekundTeller().start();
                fortsett = true;
                brett.settBomber(r);
                foersteTrekk = false;
            }
            // Venstreklikk
            if (e.getButton() == MouseButton.PRIMARY) {
                try {
                    r.trykkPaa();
                    boolean vunnet = brett.sjekkOmVunnet();
                    if (vunnet) {
                        fortsett = false;
                        if (!stoppeklokke.getText().contains("-")) {
                            new MuligRekord(antRad, antKol, antBomber, stoppeklokke.getText());
                        }
                    }
                } catch (TrykketPaaBombe f) {
                    brett.taptBrett();
                    fortsett = false;
                }
            }
            // Hoyreklikk
            else {
                r.flagg();
            }
        }
    }

    // Naar man trykker paa knappen "Ny runde" skjer dette
    class StarteNyRunde implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            brett.fyllBrettet();
            fortsett = false;
            foersteTrekk = true;
            stoppeklokke.setText("0.0");
        }
    }

    // Naar man trykker paa knappen "Angre" skjer dette
    class AngreTrekk implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            brett.angreBrett();
            fortsett = false;
            stoppeklokke.setText("-.-");
        }
    }

    // Gjoer at man kan bruke "F" for fullscreen, (CMD+)Z for angre, (CMD+)N for ny,
    // og slett-knappen for avslutt
    class TastaturLeser implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent e) {
            if (e.getCode() == KeyCode.F) {
                setFullScreen(true);
            }
        }
    }

    class SkjulTekst implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (stoppeklokke.getTextFill().equals(Color.valueOf("#f4f4f4"))) {
                stoppeklokke.setTextFill(Color.BLACK);
            } else {
                stoppeklokke.setTextFill(Color.valueOf("#f4f4f4"));
            }
        }
    }

    // Oeker stoppeklokken med 1, venter ett sek og gjentar
    class SekundTeller extends Thread {
        @Override
        public void run() {
            while (fortsett) {
                if (!isShowing()) {
                    fortsett = false;
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Noe galt med stoppeklokke-traaden: " + e);
                }
                Platform.runLater(new OekStoppeklokke());
            }
        }
    }

    // Kalles paa i SekundTeller
    class OekStoppeklokke implements Runnable {
        @Override
        public void run() {
            String tekst = stoppeklokke.getText();

            double tall = 0;
            int minutt = 0;

            if (tekst.contains(":")) {
                String[] biter = tekst.split(":");
                minutt = Integer.parseInt(biter[0]);
                tall = 0.1 + (Double.parseDouble(biter[1]));

            } else {
                tall = 0.1 + (Double.parseDouble(tekst));
            }
            String tallTekst;
            if (tall > 60 || minutt > 0) {
                double fakeTall = tall;
                while (fakeTall > 60) {
                    fakeTall -= 60;
                    minutt += 1;
                }
                if (fakeTall < 10) {
                    tallTekst = (String.format("%d:0%.1f", minutt, fakeTall)).replace(",", ".");
                } else {
                    tallTekst = (String.format("%d:%.1f", minutt, fakeTall)).replace(",", ".");
                }
            } else {
                tallTekst = (String.format("%.1f", tall)).replace(",", ".");
            }
            stoppeklokke.setText(tallTekst + "");
        }
    }
}
