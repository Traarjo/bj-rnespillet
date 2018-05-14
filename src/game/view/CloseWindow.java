package game.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CloseWindow {
    public static void close() {
        Stage close = new Stage();
        close.initModality(Modality.APPLICATION_MODAL);
        close.setTitle("Lukk programmet?");
        close.getIcons().add(new Image("bilder/icon_bear.png"));

        Label sikker = new Label("Er du sikker på at du vil lukke programmet?");
        Label info = new Label("Spillet vil ikke bli lagret.");

        Button yes = new Button ("Ja");
        Button no  = new Button ("Nei");
        yes.setOnAction(e -> System.exit(0));
        no.setOnAction(e -> close.close());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(yes, no); // Legger til knapper
        VBox vBox = new VBox();
        vBox.getChildren().addAll(sikker, info, hBox); // Legger til tekst over knappene
        StackPane layout = new StackPane();
        layout.getChildren().add(vBox);

        Scene scene = new Scene (layout, Size.windowwidth/2.5, Size.windowheight/2.5);
        close.setScene(scene);
        close.setResizable(false);
        close.showAndWait();
    }
}
