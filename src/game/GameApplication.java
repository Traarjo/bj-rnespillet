package game;

import game.view.Size;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import game.controller.GameController;
import game.view.Level;
import game.view.MainMenu;
import game.view.Tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameApplication extends Application {
    private GameController gameController;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;


    public GameApplication() {
        bearImage = new Image("bilder/B1Preview.png", Size.width / 6, Size.height / 5, true, false);
        beeImage = new Image("bilder/bie.png", Size.width / 10, Size.height / 8, true, false);
        honeyImage = new Image("bilder/honey.png", Size.width / 13, Size.height / 8, true, false);
        this.gameController = new GameController(bearImage, beeImage, honeyImage);

    }


    @Override
    public void start(Stage stage) throws Exception {

       /* Random random = new Random();
        List<Integer> xValues = Arrays.asList(50,100,150,200,250,300);
        xValues.get(random.nextInt(6));

        List<Integer> yValues = Arrays.asList(150,300,450);
        yValues.get(random.nextInt(3));*/


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new MainMenu(gameController));
        borderPane.setCenter(new Level(gameController, bearImage, beeImage, honeyImage));
        Scene scene = new Scene(borderPane, Size.width, Size.height);
        scene.setOnKeyPressed(keyhandler());
        scene.getStylesheets().add("Stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Bjørnespillet");
        stage.getIcons().add(new Image("bilder/icon_bear.png"));
        stage.setOnCloseRequest(event -> {
            event.consume();
            gameController.exit();
        });

        stage.setResizable(false);
        stage.show();
        new Tutorial();





    }

    private EventHandler<KeyEvent> keyhandler() {
        return event -> {
            event.consume();
            if (!gameController.isPaused().get()) {
                KeyCode key = event.getCode();
                switch (key) {
                    case W:
                        gameController.moveBearUp();
                        break;
                    case A:
                        gameController.moveBearLeft();
                        break;
                    case S:
                        gameController.moveBearDown();
                        break;
                    case D:
                        gameController.moveBearRight();
                        break;
                    case ESCAPE:
                        gameController.pause();
                        break;
                }

            } else {
                gameController.resume();
            }

        };
    }

}
