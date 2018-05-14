package game;

import game.view.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import game.controller.GameController;


public class GameApplication extends Application {
    private GameController gameController;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;


    public GameApplication() {
        bearImage = new Image("bilder/B1R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        beeImage = new Image("bilder/bie.png", Size.windowwidth / 10, Size.windowheight / 8, true, false);
        honeyImage = new Image("bilder/honey.png", Size.windowwidth / 13, Size.windowheight / 8, true, false);
        this.gameController = new GameController(bearImage, beeImage, honeyImage);



    }

    @Override
    public void start(Stage stage) throws Exception {
        StatusLine statusLine = new StatusLine();
        BorderPane borderPane = new BorderPane();
        VBox menuAndScore = new VBox();
        menuAndScore.getChildren().add(new MainMenu(gameController));
        menuAndScore.getChildren().add(statusLine);
        borderPane.setTop(menuAndScore);
        borderPane.setCenter(new Level(gameController, beeImage, honeyImage, statusLine));
        Scene scene = new Scene(borderPane, Size.windowwidth, Size.windowheight);
        scene.setOnKeyPressed(keyhandler());
        scene.getStylesheets().add("Stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Bjørnespillet");
        stage.getIcons().add(new Image("bilder/icon_bear.png"));
        stage.setOnCloseRequest(event -> {
            event.consume();
            gameController.pause();
            CloseWindow.close();
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
                    case SPACE:
                        gameController.pause();
                        new GameMenu(gameController, true);
                        break;
                }
            }
        };
    }
}
