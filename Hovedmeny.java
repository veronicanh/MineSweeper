import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// Farger og tekst
import javafx.scene.text.Font;

public class Hovedmeny extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage root) {
        Label overskrift = new StyleLabel("Velkommen til MineSweeper", 345, 40, new Font(15));

        Button start = new StyleButton("Start nytt spill", 299, 60);
        start.setOnAction(new AapneNyttVindu(root, root, 26, 46, 250));

        Button rekorder = new StyleButton("Rekorder", 299, 30);
        rekorder.setOnAction(new AapneNyttVindu(root, "rekordtavle"));

        Button hjelp = new StyleButton("Hjelp", 299, 30);
        hjelp.setOnAction(new AapneNyttVindu(root, "hjelp"));

        Button avslutt = new StyleButton("Avslutt", 299, 30);
        avslutt.setOnAction(new LukkVindu(root));

        // Maa lage en kulisser-bit, for at overskrifen skal dekke hele vinduet
        GridPane kulisserBit = new GridPane();
        kulisserBit.setVgap(19);
        kulisserBit.addColumn(0, start, rekorder, hjelp, avslutt);
        kulisserBit.setLayoutX(23);
        kulisserBit.setLayoutY(60);

        Pane kulisser = new Pane(new Button(), overskrift, kulisserBit); // Knappen for at "nytt spill" ikke skal
                                                                         // markeres
        kulisser.setPrefSize(345, 232 + 30 + 25);

        Scene scene = new Scene(kulisser);
        root.setTitle("Hovedmeny");
        root.setScene(scene);
        root.show();
    }
}
