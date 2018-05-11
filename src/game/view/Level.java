package game.view;

import game.model.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import game.controller.GameController;
import game.model.Bear;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Level extends Pane {
    private GameController gameController;
    private Image bearImage;
    private Image bearImage2;
    private Image beeImage;
    private Image honeyImage;
    private GraphicsContext gc;
    private AnimationTimer timer;
    private Image renderImage;
    private Thread run = run();

    public Level(GameController gameController, Image bearImage, Image beeImage, Image honeyImage, Image bearImage2) {
        this.gameController = gameController;
        this.bearImage = bearImage;
        this.renderImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        this.bearImage2 = bearImage2;
        setId("level");
        Canvas canvas = new Canvas(Size.windowwidth, Size.windowheight);
        gc = canvas.getGraphicsContext2D();
        getChildren().addAll(createTrees(), canvas);

        gameController.getState().addListener((observable, oldValue, currentState) -> {
            if(currentState.equals(GameState.NEW_LEVEL.toString())){
                render();
            }

            else if (currentState.equals(GameState.PAUSED.toString())) {
                timer.stop();
                if (gameController.isGameOver()) {
                    //TODO show scoeboard..
                    String gameOver = "Game over!";
                    HBox hBox = new HBox();
                    hBox.setTranslateX(Size.windowwidth/3);
                    hBox.setTranslateY(Size.windowwidth/4);
                    getChildren().add(hBox);

                    for (int i = 0; i < gameOver.toCharArray().length; i++) {
                        char letter = gameOver.charAt(i);

                        Text text = new Text(String.valueOf(letter));
                        text.setFont(Font.font(48));
                        text.setFont(Font.font("Avenir Next", FontWeight.BOLD, 45));

                        hBox.getChildren().add(text);

                        FadeTransition ft = new FadeTransition();
                        ft.setToValue(1);
                        ft.play();

                    }

                } else if (currentState.equals(GameState.RUNNING.toString())) {
                    timer.start();
                }
            }
        });




        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(!gameController.isPaused()){
                    gameController.updateGameState();
                    render();

                }

            }
        };
        timer.start();

    }

    private ImageView createTrees() {
        ImageView trees = new ImageView("bilder/trees.png");
        trees.setPickOnBounds(true);
        trees.setPreserveRatio(true);
        trees.setFitWidth(2700);
        return trees;
    }

    private void render() {
        gc.clearRect(0, 0, Size.windowwidth, Size.windowheight);
        Bear bear = gameController.getBear();

        gc.drawImage(renderImage, bear.getxPosition(), bear.getyPosition(), bear.getWidth(), bear.getHeight());
        run();
        gameController.getBees().forEach(bee -> gc.drawImage(beeImage, bee.getxPosition(), bee.getyPosition(), bee.getWidth(), bee.getHeight()));
        gameController.getHoneyPots().forEach(honey -> gc.drawImage(honeyImage, honey.getxPosition(), honey.getyPosition(), honey.getWidth(), honey.getHeight()));
        run().start();
    }
    public Thread run() {
        return new Thread(() ->
        {
            if (renderImage == bearImage) {
               try {
                    run.sleep(100);
                   renderImage = bearImage2;
                } catch (InterruptedException ignored) {
                }

            } else {
                try {
                    run.sleep(100);
                    renderImage = bearImage;
                } catch (InterruptedException ignored) {
                }
            }

        });

    }
}
