package game;

import game.model.Bear;
import game.model.GameState;
import game.model.Honey;
import game.view.*;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import game.controller.GameController;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import game.controller.GameController;

public class GameApplication extends Application {
    private GameController gameController;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;
    private Image bearImage2;
    private Image bearImage3;
    private Image bearImage4;
    private Image bearImage5;
    private Image bearImage6;
    private Image bearImage7;
    private Image bearImage8;
    private Image bearImage9;
    private Image bearImage10;
    private Image bearImage11;
    private Image bearImage12;
    private Image bearImage13;
    private Image bearImage14;
    private Image bearImage15;
    private Image bearImage16;
    private Image bearImage17;
    private Image bearImage18;
    private Image bearImage19;
    private Image bearImage20;
    private Image heartImage;
    private Bear bear;


    public GameApplication() {
        bearImage = new Image("bilder/B1R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage2 = new Image("bilder/B1R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage3 = new Image("bilder/B2R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage4 = new Image("bilder/B2R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage5 = new Image("bilder/B3R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage6 = new Image("bilder/B3R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage7 = new Image("bilder/B4R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage8 = new Image("bilder/B4R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage9 = new Image("bilder/B5R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage10 = new Image("bilder/B5R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage11 = new Image("bilder/B6R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage12 = new Image("bilder/B6R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage13 = new Image("bilder/B7R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage14 = new Image("bilder/B7R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage15 = new Image("bilder/B8R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage16 = new Image("bilder/B8R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage17 = new Image("bilder/B9R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage18 = new Image("bilder/B9R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage19 = new Image("bilder/B10R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        bearImage20 = new Image("bilder/B10R2.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);

        beeImage = new Image("bilder/bie.png", Size.windowwidth / 10, Size.windowheight / 8, true, false);
        honeyImage = new Image("bilder/honey.png", Size.windowwidth / 13, Size.windowheight / 8, true, false);
        heartImage = new Image("bilder/hjerte.png", Size.windowwidth / 13, Size.windowheight / 8, true, false);
        this.gameController = new GameController(bearImage, beeImage, honeyImage);


    }

    @Override
    public void start(Stage stage) throws Exception {
        /*VBox vBox = new VBox();
        Image heartImage = new Image("bilder/hjerte.png");
        Label label1 = new Label("Liv");
        label1.setGraphic(new ImageView(heartImage));
        vBox.getChildren().add(label1);*/
        BorderPane borderPane = new BorderPane();
        VBox pane2 = new VBox();
        pane2.getChildren().add(new MainMenu(gameController));
        pane2.getChildren().add(new Text("Score: ")); //GameController.score? scoreText() ? drawScore() ?
        borderPane.setTop(pane2);
        //borderPane.setTop(new MainMenu(gameController));
        borderPane.setCenter(new Level(gameController, bearImage, beeImage, honeyImage, bearImage2,
                bearImage3, bearImage4,
                bearImage5, bearImage6,
                bearImage7, bearImage8,
                bearImage9, bearImage10,
                bearImage11, bearImage12,
                bearImage13, bearImage14,
                bearImage15, bearImage16,
                bearImage17, bearImage18,
                bearImage19, bearImage20));
        Scene scene = new Scene(borderPane, Size.windowwidth, Size.windowheight);
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
                    case SPACE:
                        gameController.pause();
                        new GameMenu(gameController, true);
                        break;
                }
            }
        };
    }
}
