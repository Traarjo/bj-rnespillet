package game.view;

import game.model.GameState;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import game.controller.GameController;
import game.model.Bear;
import javafx.scene.paint.Color;
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

                        /*Text text = new Text(String.valueOf(letter));
                        text.setFont(Font.font("Avenir Next", FontWeight.BOLD, 55));
                        text.setFill(Color.YELLOW);

                        hBox.getChildren().add(text);


                        Blend blend = new Blend();
                        blend.setMode(BlendMode.MULTIPLY);

                        DropShadow ds = new DropShadow();
                        ds.setColor(Color.rgb(254, 235, 66, 0.3));
                        ds.setOffsetX(5);
                        ds.setOffsetY(5);
                        ds.setRadius(5);
                        ds.setSpread(0.2);

                        blend.setBottomInput(ds);

                        DropShadow ds1 = new DropShadow();
                        ds1.setColor(Color.web("#f13a00"));
                        ds1.setRadius(20);
                        ds1.setSpread(0.2);

                        Blend blend2 = new Blend();
                        blend2.setMode(BlendMode.MULTIPLY);

                        InnerShadow is = new InnerShadow();
                        is.setColor(Color.web("#feeb42"));
                        is.setRadius(9);
                        is.setChoke(0.8);
                        blend2.setBottomInput(is);

                        InnerShadow is1 = new InnerShadow();
                        is1.setColor(Color.web("#f13a00"));
                        is1.setRadius(5);
                        is1.setChoke(0.4);
                        blend2.setTopInput(is1);

                        Blend blend1 = new Blend();
                        blend1.setMode(BlendMode.MULTIPLY);
                        blend1.setBottomInput(ds1);
                        blend1.setTopInput(blend2);

                        blend.setTopInput(blend1);

                        text.setEffect(blend);*/

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
