package game.view;

/**
 * JavaFX
 * Alertboks som kommer opp når man trykker x i hjørnet for å avslutte spillet.
 * Klikk på "ja" avslutter hele programmet
 * @author Tara Jørgensen
 */

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CloseWindow {
    public static void close() {
        Stage close = new Stage();
        close.setHeight(Size.height()/2);
        close.setWidth(Size.width()/2);
        close.setResizable(false);
        close.initModality(Modality.APPLICATION_MODAL);
        close.setTitle("Lukk programmet?");
        close.getIcons().add(new Image("bilder/icon_bear.png"));

        Label header = new Label("Er du sikker på at du vil lukke programmet?");
        Label info = new Label("Spillet vil ikke bli lagret.");

        Button yes = new Button ("Ja");
        Button no  = new Button ("Nei");
        yes.setOnAction(e -> System.exit(0));
        no.setOnAction(e -> close.close());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(yes, no); // Legger til knapper
        VBox vBox = new VBox();
        vBox.getChildren().addAll(header, info, hBox); // Legger til tekst over knappene

        close.setScene(new Scene(vBox));

        close.showAndWait();
    }
}
