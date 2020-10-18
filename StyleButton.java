import javafx.scene.control.Button;

// Klasse som gjoer at man slipper aa skrive samme kode flere ganger
public class StyleButton extends Button {
    // width+height
    public StyleButton(String text, int width, int height) {
        super(text);
        // Stoerrelsen paa knappen
        setMinSize(width, height);
        setMaxSize(width, height);
    }

    // Layout
    public StyleButton(int width, int height, String text, int layoutX, int layoutY) {
        super(text);
        // Stoerrelsen paa knappen
        setMinSize(width, height);
        setMaxSize(width, height);
        // Plassering
        setLayoutX(layoutX);
        setLayoutY(layoutY);
    }
}