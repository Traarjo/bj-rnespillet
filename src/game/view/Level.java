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


/**
 * Level inneholder animasjonstråden som får bjørnen til å løpe
 * Tegner bie og honning
 * Inneholder render(), som er den som tegner alt
 * Styrer AnimationTimer ut i fra spillet sin state
 */

public class Level extends Pane {
    private GameController gameController;
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

    /**
     *
     * @param gameController tar inn gameController
     * @param beeImage tar inn biebildet
     * @param honeyImage tar inn bildet av honning
     * @param statusLine tar inn statuslinjen
     *
     */
    public Level(GameController gameController, Image beeImage, Image honeyImage,
                 StatusLine statusLine) {
        this.gameController = gameController;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        this.bearImages = new BearImages();
        this.bearImage = bearImages.getBearImage(0);
        this.bearImage2 = bearImages.getBearImage2(0);
        this.bearImageToRender = bearImage;
        this.statusLine = statusLine;
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

    private ImageView createTrees2() {
        ImageView trees = new ImageView("bilder/trees.png");
        trees.setPickOnBounds(true);
        trees.setPreserveRatio(true);
        trees.setFitWidth(2700);
        return trees;
    }

    private ImageView createTrees() {
        ImageView trees1 = new ImageView("bilder/trees.png");
        ImageView trees2 = new ImageView("bilder/trees.png");
        gameController.loopTrees(trees1, trees2);
        return trees1;


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

    }
    private Thread bearAnimationThread() {
        return new Thread(() ->
        {


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
                }

            }
        });
    }
}
