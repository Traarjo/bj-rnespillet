package game.view;

import game.model.GameState;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private Image bearImage2;
    private Image beeImage;
    private Image honeyImage;
    private GraphicsContext gc;
    private AnimationTimer timer;
    private Image renderImage;

    private static double windowWidth = Size.width();
    private static double windowHeight = Size.height();


    public Level(GameController gameController, Image bearImage, Image beeImage, Image honeyImage, Image bearImage2) {
        this.gameController = gameController;
        this.bearImage = bearImage;
        this.renderImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        this.bearImage2 = bearImage2;
        setId("level");
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        gc = canvas.getGraphicsContext2D();
        getChildren().addAll(createTrees(), canvas);

        gameController.getState().addListener((observable, oldValue, currentState) -> {
            if(currentState.equals(GameState.NEW_LEVEL.toString())){
                render();
            }

            else if (currentState.equals(GameState.PAUSED.toString())) {
                timer.stop();
                if(gameController.isGameOver()){
                    //TODO show scoeboard..
                }
            } else if(currentState.equals(GameState.RUNNING.toString())) {
                timer.start();
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
        gc.clearRect(0, 0, windowWidth, windowHeight);
        Bear bear = gameController.getBear();

        gc.drawImage(renderImage, bear.getxPosition(), bear.getyPosition(), bear.getWidth(), bear.getHeight());
        setNextBearRenderImage();
        gameController.getBees().forEach(bee -> gc.drawImage(beeImage, bee.getxPosition(), bee.getyPosition(), bee.getWidth(), bee.getHeight()));
        gameController.getHoneyPots().forEach(honey -> gc.drawImage(honeyImage, honey.getxPosition(), honey.getyPosition(), honey.getWidth(), honey.getHeight()));

    }

    private void setNextBearRenderImage() {
        if(renderImage == bearImage){
            renderImage = bearImage2;
        }
        else{
            renderImage = bearImage;
        }
    }


}
