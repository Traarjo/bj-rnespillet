package game;

import game.model.GameState;
import game.view.*;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import game.controller.GameController;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameApplication extends Application {
    private GameController gameController;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;
    private Image bearImage2;


    private static double windowWidth = Size.width();
    private static double windowHeight = Size.height();

    private static final Image IMAGE = new Image("bilder/bjornar.png");

    private static final int COLUMNS  =  5;
    private static final int COUNT    =  2;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 200;
    private static final int HEIGHT   = 271;


    public GameApplication() {
        bearImage = new Image("bilder/B1Preview.png", Size.width / 6, Size.height / 5, true, false);
        bearImage2 = new Image("bilder/B8Preview.png", Size.width / 6, Size.height / 5, true, false);
        beeImage = new Image("bilder/bie.png", Size.width / 10, Size.height / 8, true, false);
        honeyImage = new Image("bilder/honey.png", Size.width / 13, Size.height / 8, true, false);
        this.gameController = new GameController(bearImage, beeImage, honeyImage);

    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new MainMenu(gameController));
        borderPane.setCenter(new Level(gameController, bearImage, beeImage, honeyImage, bearImage2));
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


        new GameMenu(gameController, false);





    }

    private EventHandler<KeyEvent> keyhandler() {
        return event -> {
            event.consume();
            if (!gameController.isPaused()) {
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
                        new GameMenu(gameController, true);

                        break;
                }

            }

        };
    }

}
