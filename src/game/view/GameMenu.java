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
    private Button galleryButton;
    private Button scoreboardButton;
    private Button quitButton;
    private GameController gameController;



    public GameMenu(GameController gameController, boolean canResumeGame){
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Meny");
        stage.getIcons().add(new Image("bilder/icon_bear.png"));

        this.gameController = gameController;
        vbox = new VBox();
        //                  <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />

        newGameButton = new Button();
        galleryButton = new Button();
        scoreboardButton = new Button();
        quitButton = new Button();



        newGameButton.setText("Nytt spill");
        galleryButton.setText("Galleri");
        scoreboardButton.setText("Poengtavle");
        quitButton.setText("Avslutt");
        quitButton.setFont(new Font("arial", 14));

        newGameButton.setOnAction(e -> {
            e.consume();
            stage.close();
            gameController.newGame();
        });



        galleryButton.setOnAction(e -> {
            e.consume();
            //TODO
        });

        scoreboardButton.setOnAction(e -> {
            e.consume();
            //TODO
        });

        quitButton.setOnAction(e -> {
            e.consume();
            gameController.exit();
        });
        if(canResumeGame){
            Button continiueGameButton = new Button();
            continiueGameButton.setText("Fortsett");
            continiueGameButton.setOnAction(e -> {
                e.consume();
                stage.close();
                gameController.resume();
            });
            vbox.getChildren().add(continiueGameButton);
        }
        vbox.getChildren().addAll(newGameButton, galleryButton, scoreboardButton,quitButton);
        stage.setScene(new Scene(vbox));
        stage.show();
    }

}
