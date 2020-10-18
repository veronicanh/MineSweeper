import javafx.scene.control.Button;
import javafx.scene.control.Slider;
// Bakgrunn og farger:
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
// Koble sammen slider og knapp
import javafx.beans.binding.Bindings;

class SliderPane extends Pane {
    private Slider slider;

    public SliderPane(String type, Slider slideren) {
        slider = slideren;
        slider.setLayoutX(135);
        slider.setLayoutY(0);
        if (type.equals("Rader")) {
            slider.setMinSize(110, 30);
            slider.setMaxSize(110, 30);
        } else {
            slider.setMinSize(200, 30);
            slider.setMaxSize(200, 30);
        }
        slider.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Button tekst = new Button(type);
        tekst.setLayoutX(10);
        tekst.setLayoutY(0);
        tekst.setMinSize(70, 30);
        tekst.setMaxSize(70, 30);
        tekst.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Button verdi = new Button();
        verdi.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
        verdi.setLayoutX(75);
        verdi.setLayoutY(0);
        verdi.setMinSize(45, 30);
        verdi.setMaxSize(45, 30);
        verdi.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        getChildren().add(tekst);
        getChildren().add(verdi);
        getChildren().add(slider);
    }
}