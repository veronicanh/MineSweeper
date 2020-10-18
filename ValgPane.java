import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
// Koble sammen tekst med slider
import javafx.beans.binding.Bindings;

class ValgPane extends Pane {
    Lenkeliste<Button> labler;

    public ValgPane(ValgKnapp knapp) {
        knapp.setLayoutX(10);
        knapp.setLayoutY(0);
        knapp.setMaxSize(90, 30);
        knapp.setMinSize(90, 30);
        getChildren().add(knapp);
        
        Label label = new Label(knapp.hentRader() + "x" + knapp.hentKolonner() + "   " + knapp.hentBomber() + " bomber");
        label.setLayoutX(110);
        label.setLayoutY(0);
        label.setMaxSize(200, 30);
        label.setMinSize(200, 30);
        getChildren().add(label);
    }

    public ValgPane(ValgKnapp knapp, Slider rad, Slider kol, Slider bomb) {
        knapp.setLayoutX(10);
        knapp.setLayoutY(0);
        knapp.setMaxSize(90, 30);
        knapp.setMinSize(90, 30);
        getChildren().add(knapp);

        labler = new Lenkeliste<Button>();
        Label label = new Label();
        label.setLayoutX(110);
        label.setLayoutY(0);
        label.setMaxSize(200, 30);
        label.setMinSize(200, 30);

        label.textProperty().bind(Bindings.format("%.0fx%.0f   %.0f bomber",
                rad.valueProperty(), kol.valueProperty(), bomb.valueProperty()));
        getChildren().add(label);
    }
}