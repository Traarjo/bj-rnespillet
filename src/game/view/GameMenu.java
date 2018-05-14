package game.view;

import game.controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameMenu {
    private VBox vbox;
    private Button newGameButton;
    private Button loadGameButton;
    private Button galleryButton;
    private Button scoreboardButton;
    private Button quitButton;
    private GameController gameController;

    public GameMenu(GameController gameController, boolean canResumeGame){
        Stage stage = new Stage();
        stage.setHeight(Size.height()/2);
        stage.setWidth(Size.width()/2);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Meny");
        stage.getIcons().add(new Image("bilder/icon_bear.png"));

        this.gameController = gameController;
        vbox = new VBox();
        //<Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />

        loadGameButton = new Button("Last inn spill");
        newGameButton = new Button("Nytt spill");
        galleryButton = new Button("Galleri");
        scoreboardButton = new Button("Poengtavle");
        quitButton = new Button("Avslutt");

        quitButton.setFont(new Font("arial", 14));

        newGameButton.setOnAction(e -> {
            e.consume();
            stage.close();
            gameController.newGame();
        });
        loadGameButton.setOnAction(event -> {
            stage.close();
            gameController.loadGame();

        });
        galleryButton.setOnAction(e -> {
            e.consume();
            //TODO
        });
        scoreboardButton.setOnAction(e -> {
            e.consume();
            new HighScoreView(gameController.getHighScores());
        });
        quitButton.setOnAction(e -> {
            e.consume();
            gameController.exit();
        });
        if (canResumeGame){
            Button continueGameButton = new Button("Fortsett");
            continueGameButton.setOnAction(e -> {
                e.consume();
                stage.close();
                gameController.resume();
            });
            vbox.getChildren().add(continueGameButton);
        }

        vbox.getChildren().add(newGameButton);
        if(gameController.canLoadGame()){
            vbox.getChildren().addAll(loadGameButton);
        }
        vbox.getChildren().addAll(galleryButton, scoreboardButton,quitButton);
        stage.setScene(new Scene(vbox));
        stage.show();
    }
}
