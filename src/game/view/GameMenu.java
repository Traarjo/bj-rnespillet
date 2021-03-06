package game.view;

/**
 * GameMenu-klassen inneholder den dataen som skal vises i menyen før spillet startes, og når det er pauset.
 */

import game.controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameMenu {
    private VBox vbox;
    private Button newGameButton;
    private Button loadGameButton;
    private Button quitButton;
    private Button tutorial;
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
        loadGameButton = new Button("Last inn spill");
        newGameButton = new Button("Nytt spill");
        tutorial = new Button("Tutorial");
        quitButton = new Button("Avslutt");

        quitButton.setFont(new Font("arial", 14));

        newGameButton.setOnAction(e -> {
            e.consume();
            stage.close();
            gameController.newGame();
        });
        loadGameButton.setOnAction(event -> {
            try {
                stage.close();
                gameController.loadGame();
            } catch (Exception exception){
                Stage noFile = new Stage();
                noFile.setHeight(Size.height()/3);
                noFile.setWidth(Size.width()/3);
                noFile.setResizable(false);
                noFile.initModality(Modality.APPLICATION_MODAL);
                noFile.setTitle("Ingen fil tilgjengelig");
                noFile.getIcons().add(new Image("bilder/icon_bear.png"));

                Label text = new Label("Feil ved lasting av fil! \n\n");
                Label text2= new Label ("Det oppstod en feil \nog vi klarte ikke hente det tidligere lagrede spillet\n\n");

                Button ok = new Button ("Ok");
                ok.setOnAction(e -> noFile.close());

                VBox vBox = new VBox();
                vBox.getChildren().addAll(text, text2, ok);

                noFile.setScene(new Scene(vBox));
                noFile.showAndWait();
            }
        });
        tutorial.setOnAction(e -> Tutorial.tutorial());

        quitButton.setOnAction(e -> {
            e.consume();
            gameController.exit();
        });
        /**
         * Om spillet er pauset og man kan resume det, står det "Lagre og Avslutt" istedenfor bare "Avslutt"
         */
        if (canResumeGame){
            quitButton.setText("Lagre og Avslutt");
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
        vbox.getChildren().addAll(tutorial);
        vbox.getChildren().addAll(quitButton);
        stage.setScene(new Scene(vbox));
        stage.show();
    }
}
