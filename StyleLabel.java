import javafx.scene.control.Label;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Klasse som gjoer at man slipper aa skrive samme kode flere ganger
public class StyleLabel extends Label {
    // Brukes for overskrifter
    // width+height, center alignment, font, lysegraa bakgrunn
    public StyleLabel(String text, int width, int height, Font font) {
        super(text);
        // Stoerrelsen paa knappen
        setMinSize(width, height);
        setMaxSize(width, height);
        // Justering av tekst
        setAlignment(Pos.CENTER);
        // Tekst-font og farge
        setFont(font);
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    // width, alignment, font, svart skrift
    public StyleLabel(String text, int width, String alignment, Font font) {
        super(text);
        // Bredden paa knappen
        setMinWidth(width);
        setMaxWidth(width);
        // Justering av tekst
        if (alignment.equals("left")) {
            setAlignment(Pos.CENTER_LEFT);
        } else if (alignment.equals("right")) {
            setAlignment(Pos.CENTER_RIGHT);
        }
        // Tekst-font og farge
        setFont(font);
        setTextFill(Color.BLACK);
    }
}