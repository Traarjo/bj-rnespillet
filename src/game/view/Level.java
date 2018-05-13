package game.view;

import game.model.GameState;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import game.controller.GameController;
import game.model.Bear;
import javafx.scene.text.Text;

import static java.lang.Thread.sleep;

public class Level extends Pane {
    private GameController gameController;
    private Image bearRun1;
    private Image bearRun2;
    private Image bearImage;
    private Image bearImage2;


    private Image beeImage;
    private Image honeyImage;
    private GraphicsContext gc;
    private AnimationTimer timer;
    private Image bearImageToRender;
    private Thread bearAnimation;
    private BearImages bearImages;
    private StatusLine statusLine;

    public Level(GameController gameController, Image beeImage, Image honeyImage,
                 StatusLine statusLine) {
        this.gameController = gameController;
<<<<<<< HEAD
=======
        this.renderImage = bearImage;
>>>>>>> master
        this.beeImage = beeImage;

        this.bearImage = bearImage;
        this.honeyImage = honeyImage;
<<<<<<< HEAD
        this.bearImages = new BearImages();
        this.bearImage = bearImages.getBearImage(0);
        this.bearImage2 = bearImages.getBearImage2(0);
        this.bearImageToRender = bearImage;
        this.statusLine = statusLine;
=======
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
        this.bearRun1 = bearImage;
        this.bearRun2 = bearImage2;

>>>>>>> master
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
            } else if (gameController.isGameRunning()) {
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

       bearAnimation = bearAnimationThread();
       bearAnimation.start();

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
        bearImage = bearImages.getBearImage(bear.getEatenHoney());
        bearImage2 = bearImages.getBearImage2(bear.getEatenHoney());

        statusLine.lives(bear.getLives());
        statusLine.points(bear.getEatenHoney());

        gc.drawImage(bearImageToRender, bear.getxPosition(), bear.getyPosition(), bear.getWidth(), bear.getHeight());
        gameController.getBees().forEach(bee -> gc.drawImage(beeImage, bee.getxPosition(), bee.getyPosition(), bee.getWidth(), bee.getHeight()));
        gameController.getHoneyPots().forEach(honey -> gc.drawImage(honeyImage, honey.getxPosition(), honey.getyPosition(), honey.getWidth(), honey.getHeight()));

<<<<<<< HEAD
=======
        switch (bear.getEatenHoney()) {
            case 0:
                bearRun1 = bearImage;
                bearRun2= bearImage2;
                break;
            case 10:
                bearRun1 = bearImage3;
                bearRun2 = bearImage4;
                break;
            case 20:
                bearRun1 = bearImage5;
                bearRun2 = bearImage6;
                break;
            case 30:
                bearRun1 = bearImage7;
                bearRun2 = bearImage8;
                break;
            case 40:
                bearRun1 = bearImage9;
                bearRun2 = bearImage10;
                break;
            case 50:
                bearRun1 = bearImage11;
                bearRun2 = bearImage12;
                break;
            case 70:
                bearRun1 = bearImage13;
                bearRun2 = bearImage14;
                break;
            case 80:
                bearRun1 = bearImage15;
                bearRun2 = bearImage16;
                break;
            case 90:
                bearRun1 = bearImage17;
                bearRun2 = bearImage18;
                break;
            case 100:
                bearRun1 = bearImage19;
                bearRun2 = bearImage20;
                break;



        }

        run().start();
>>>>>>> master
    }
    private Thread bearAnimationThread() {
        return new Thread(() ->
        {
<<<<<<< HEAD


            while (true){
                if (bearImageToRender == bearImage) {
                    try {
                        Thread.sleep(100);
                        bearImageToRender = bearImage2;
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    try {
                        Thread.sleep(100);
                        bearImageToRender = bearImage;
                    } catch (InterruptedException ignored) {
                    }
=======
            if (renderImage == bearRun1) {
               try {
                    run.sleep(100);
                   renderImage = bearRun2;
               } catch (InterruptedException ignored) {
               }
            } else {
                try {
                    run.sleep(100);
                    renderImage = bearRun1;
                } catch (InterruptedException ignored) {
>>>>>>> master
                }

            }

        });
    }
}
