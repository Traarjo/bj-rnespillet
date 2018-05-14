package game.view;

/**
 * Koden som skal vise listen med High scores.
 * Ikke ferdig.
 */

import game.model.HighScore;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class HighScoreView {
    private List<HighScore> highScores;

    public HighScoreView(List<HighScore> highScores){
        this.highScores = highScores;
        Stage stage = new Stage();
        stage.setHeight(Size.height()/2);
        stage.setWidth(Size.width()/2);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("HighScore");
        stage.getIcons().add(new Image("bilder/icon_bear.png"));

        Label header = new Label("High scores\n\n");
        Label scores = new Label(highScores.stream().map(score -> score.getName() +" - "+score.getScore()).collect(Collectors.joining("\n")));

        Button ok = new Button("OK");
        ok.setOnAction(e -> stage.close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(header, scores, ok); // Legger til tekst over knappene
        stage.setScene(new Scene(vBox));

        stage.showAndWait();
    }
}
