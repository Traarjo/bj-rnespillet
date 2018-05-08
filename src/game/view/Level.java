package game.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import game.controller.GameController;
import game.model.Bear;

public class Level extends Pane {
    private GameController gameController;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;
    private GraphicsContext gc;
    private AnimationTimer timer;

    private static double windowWidth = Size.width();
    private static double windowHeight = Size.height();


    public Level(GameController gameController, Image bearImage, Image beeImage, Image honeyImage) {
        this.gameController = gameController;
        this.bearImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        setId("level");
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        gc = canvas.getGraphicsContext2D();
        getChildren().addAll(createTrees(), canvas);

        gameController.isPaused().addListener((observable, oldValue, paused) -> {
            if (paused) {
                timer.stop();

            } else {
                timer.start();
            }
        });



        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameController.updateGameState();
                render();
            }
        };
        gameController.startMovingBees();
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
        gc.clearRect(0, 0, 1200, 1000);
        Bear bear = gameController.getBear();
        gc.drawImage(bearImage, bear.getxPosition(), bear.getyPosition(), bear.getWidth(), bear.getHeight());
        gameController.getBees().forEach(bee -> gc.drawImage(beeImage, bee.getxPosition(), bee.getyPosition(), bee.getWidth(), bee.getHeight()));
        gameController.getHoneyPots().forEach(honey -> gc.drawImage(honeyImage, honey.getxPosition(), honey.getyPosition(), honey.getWidth(), honey.getHeight()));

    }


}
