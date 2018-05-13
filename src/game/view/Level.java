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

    private Image beeImage;
    private Image honeyImage;
    private GraphicsContext gc;
    private AnimationTimer timer;
    private Image renderImage;
    private Thread run = run();

    public Level(GameController gameController, Image bearImage, Image beeImage, Image honeyImage, Image bearImage2,
                 Image bearImage3, Image bearImage4,
                 Image bearImage5, Image bearImage6,
                 Image bearImage7, Image bearImage8,
                 Image bearImage9, Image bearImage10,
                 Image bearImage11, Image bearImage12,
                 Image bearImage13, Image bearImage14,
                 Image bearImage15, Image bearImage16,
                 Image bearImage17, Image bearImage18,
                 Image bearImage19, Image bearImage20) {
        this.gameController = gameController;
        this.bearImage = bearImage;
        this.renderImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        this.bearImage2 = bearImage2;
        this.bearImage3 = bearImage3;
        this.bearImage4 = bearImage4;
        this.bearImage5 = bearImage5;
        this.bearImage6 = bearImage6;
        this.bearImage7 = bearImage7;
        this.bearImage8 = bearImage8;
        this.bearImage9 = bearImage9;
        this.bearImage10 = bearImage10;
        this.bearImage11 = bearImage11;
        this.bearImage12 = bearImage12;
        this.bearImage13 = bearImage13;
        this.bearImage14 = bearImage14;
        this.bearImage15 = bearImage15;
        this.bearImage16 = bearImage16;
        this.bearImage17 = bearImage17;
        this.bearImage18 = bearImage18;
        this.bearImage19 = bearImage19;
        this.bearImage20 = bearImage20;
        setId("level");
        Canvas canvas = new Canvas(Size.windowwidth, Size.windowheight);
        gc = canvas.getGraphicsContext2D();
        getChildren().addAll(createTrees(), canvas);

        gameController.getState().addListener((observable, oldValue, currentState) -> {
            if (currentState.equals(GameState.NEW_LEVEL.toString())){
                render();
            } else if (currentState.equals(GameState.PAUSED.toString())) {
                timer.stop();
                if (gameController.isGameOver()) {
                    //TODO show scoeboard..
                    /*HBox hBox = new HBox();
                    hBox.setTranslateX(Size.windowwidth / 3);
                    hBox.setTranslateY(Size.windowwidth / 4);
                    getChildren().add(hBox);


                    Text text = new Text("Game Over");
                    text.setFont(Font.font("Avenir Next", FontWeight.BOLD, 55));
                    text.setFill(Color.GREEN);
                    hBox.getChildren().add(text);*/
                }
            } else if (currentState.equals(GameState.RUNNING.toString())) {
                timer.start();
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameController.isPaused()){
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
        gameController.getBees().forEach(bee -> gc.drawImage(beeImage, bee.getxPosition(), bee.getyPosition(), bee.getWidth(), bee.getHeight()));
        gameController.getHoneyPots().forEach(honey -> gc.drawImage(honeyImage, honey.getxPosition(), honey.getyPosition(), honey.getWidth(), honey.getHeight()));

        switch (bear.getEatenHoney()) {
            case 10:
                bearImage = bearImage3;
                bearImage2 = bearImage4;
                break;
            case 20:
                bearImage = bearImage5;
                bearImage2 = bearImage6;
                break;
            case 30:
                bearImage = bearImage7;
                bearImage2 = bearImage8;
                break;
            case 40:
                bearImage = bearImage9;
                bearImage2 = bearImage10;
                break;
            case 50:
                bearImage = bearImage11;
                bearImage2 = bearImage12;
                break;
            case 70:
                bearImage = bearImage13;
                bearImage2 = bearImage14;
                break;
            case 80:
                bearImage = bearImage15;
                bearImage2 = bearImage16;
                break;
            case 90:
                bearImage = bearImage17;
                bearImage2 = bearImage18;
                break;
            case 100:
                bearImage = bearImage19;
                bearImage2 = bearImage20;
                break;



        }

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
