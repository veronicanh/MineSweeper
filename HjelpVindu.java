import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
// Farger og fonter
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


// Klasse for aa lage og vise rekord-oversikt-vindu
public class HjelpVindu extends Stage {
    // Bruker root i LukkOgVisForrigeVindu()
    public HjelpVindu(Stage root) {
        Label spilletSprs = new StyleLabel("Hvordan fungerer spillet?", 345, 40, new Font(15));
        String spilletTekst = "Trykk paa en rute for aa avsloere hva som ligger der. Dersom "
                + "det er en bombe saa taper du. Dersom det ikke er en bombe vil du faa se hvor "
                + "mange bomber som omringer denne ruten (blank dersom det er 0). Du vinner spillet "
                + "ved aa apne alle ruter som ikke er bomber. Du starter aldri paa en bombe.";
        Label spilletForklaring = new StyleLabel(spilletTekst, 300, "left", new Font(14));
        spilletForklaring.setWrapText(true);
        spilletForklaring.setLayoutX(10);
        spilletForklaring.setLayoutY(45);
        
        Label angreknapp = new StyleLabel("Hvordan fungerer 'angre'-knappen?", 345, 40, new Font(15));
        angreknapp.setLayoutY(165);
        String angreTekst = "Dersom du trykker paa en bombe, men vil fortsette spillet, saa kan "
                + "du trykke paa 'Angre'-knappen. Merk at du mister muligheten til aa sette ny rekord/ta tiden.";
        Label angreForklaring = new StyleLabel(angreTekst, 300, "left", new Font(14));
        angreForklaring.setWrapText(true);
        angreForklaring.setLayoutX(10);
        angreForklaring.setLayoutY(210);

        Label tidtaking = new StyleLabel("Jeg vil fjerne stoppeklokken!", 345, 40, new Font(15));
        tidtaking.setLayoutY(290);
        String tidTakingTekst = "Ved aa trykke paa klokken saa vil den bli usynlig. Trykk pa den igjen for aa se den.";
        Label tidTakingForklaring = new StyleLabel(tidTakingTekst, 300, "left", new Font(14));
        tidTakingForklaring.setWrapText(true);
        tidTakingForklaring.setLayoutX(10);
        tidTakingForklaring.setLayoutY(335);

        Rectangle boks = new Rectangle(345, 165);
        boks.setStroke(Color.valueOf("#adadad"));
        boks.setStrokeWidth(1);
        boks.setFill(null);
        Rectangle boks2 = new Rectangle(345, 125);
        boks2.setLayoutY(165);
        boks2.setStroke(Color.valueOf("#adadad"));
        boks2.setStrokeWidth(1);
        boks2.setFill(null);
        Rectangle boks3 = new Rectangle(345, 90);
        boks3.setLayoutY(290);
        boks3.setStroke(Color.valueOf("#adadad"));
        boks3.setStrokeWidth(1);
        boks3.setFill(null);

        Button tilbake = new Button("Tilbake");
        tilbake.setOnAction(new LukkOgVisForrigeVindu(this, root));
        tilbake.setLayoutX(10);
        tilbake.setLayoutY(400);

        Pane kulisser = new Pane(spilletSprs, spilletForklaring, angreknapp, angreForklaring, tidtaking, 
                tidTakingForklaring, boks, boks2, boks3, tilbake);
        kulisser.setPrefSize(345, 437);

        Scene scene = new Scene(kulisser);
        setTitle("Hjelp");
        setScene(scene);
        show();
    }
}