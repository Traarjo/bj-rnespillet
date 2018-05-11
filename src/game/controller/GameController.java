package game.controller;

import game.GameApplication;
import game.model.GameState;
import game.view.GameMenu;
import game.view.Size;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import game.model.Bear;
import game.model.Bee;
import game.model.Honey;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameController {
    private Bear bear;
    private StringProperty state = new SimpleStringProperty(GameState.PAUSED.toString());
    private List<Bee> bees = new ArrayList<>();
    private List<Honey> honeyPots = new ArrayList<>();
    private Thread beeMover;
    private Thread honeyMover;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;
    private static double windowWidth = Size.width();
    private static double windowHeight = Size.height();
    private int points = 0;

    public Text score = new Text();
    BorderPane borderPane = new BorderPane();

    public GameController(Image bearImage, Image beeImage, Image honeyImage) {
        this.bearImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
    }

    public Bear getBear() {
        return bear;
    }

    public void pause() {
        beeMover.interrupt();
        state.setValue(GameState.PAUSED.toString());
    }

    public void resume() {
        if(!isGameOver()){
            beeMover = beeMover();
            beeMover.start();
            state.setValue(GameState.RUNNING.toString());
        }
    }

    public StringProperty getState() {
        return state;
    }

    public void exit() {
        if(!isPaused()){
            pause();
        }
        saveGameState();
        System.exit(0);
    }

    private void saveGameState() {
        //TODO: Lage?
    }

    private Thread beeMover() {
        return new Thread(() -> {
            while (state.get().equals(GameState.RUNNING.toString())) {
                bees.forEach(bee -> {
                    double currentPos = bee.getxPosition();
                    double newPosition = currentPos - bee.horizontalStepLength();
                    if (newPosition > -beeImage.getWidth()) {
                        bee.setxPosition(newPosition);
                    } else {
                        bee.setxPosition(Size.windowwidth+beeImage.getWidth());
                    }
                });
                try {
                    //Let the bees sleep after each movement
                    Thread.sleep(7);
                } catch (InterruptedException ignored) {}
            }
        });
    }

    private Thread honeyMover() {
        return new Thread(() -> {
            while (state.get().equals(GameState.RUNNING.toString())) {
                honeyPots.forEach(honey -> {
                    double currentPos = honey.getxPosition();
                    double newPosition = currentPos - honey.horizontalStepLength();
                    if (newPosition > -honeyImage.getWidth()) {
                        honey.setxPosition(newPosition);
                    } else {
                        honey.setxPosition(Size.windowwidth+honeyImage.getWidth());
                    }
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
            }
        });

    }

   public void moveBearUp() {
        double current = bear.getyPosition();
        double newPosition = current - bear.verticalStepLength();
        if (newPosition > 0) {
            bear.setyPosition(newPosition);
        }
   }
    public void moveBearLeft() {
        double current = bear.getxPosition();
        double newPosition = current - bear.horizontalStepLength();
        if (newPosition > 0) {
            bear.setxPosition(newPosition);
        }
    }
    public void moveBearDown() {
        double current = bear.getyPosition();
        double newPosition = current + bear.verticalStepLength();
        if (newPosition <= (Size.windowheight - bear.verticalStepLength())) {
            bear.setyPosition(newPosition);
        }
    }
    public void moveBearRight() {
        double current = bear.getxPosition();
        double newPosition = current + bear.horizontalStepLength();
        if (newPosition < (Size.windowwidth - bear.getWidth())) {
            bear.setxPosition(newPosition);
        }
    }

    public List<Honey> getHoneyPots() {
        return honeyPots;
    }

    public List<Bee> getBees() {
        return bees;
    }

    public boolean isGameOver(){
         return bear.getLives() <= 0;
    }

    //dele opp denne?
    public  synchronized void updateGameState() {
        if (bear.getLives() == 3) {
            System.out.println("tre liv");
        } else if (bear.getLives() == 2) {
            System.out.println("to liv");
        } else if (bear.getLives() == 1) {
            System.out.println("et liv");
        } else {
            System.out.println("null liv");
        }

        if(isGameOver()){
          pause();
          new GameMenu(this, false);
        }
        else {
            scoreText();
            Random random = new Random();

            double lane1 = bear.startPosition() - bear.verticalStepLength();
            double lane2 = bear.startPosition();
            double lane3 = bear.startPosition() + bear.verticalStepLength();


            List<Integer> xValuesBee = Arrays.asList(20, 200, 350, 500, 650, 770);
            List<Integer> yValuesBee = Arrays.asList((int)lane1, (int)lane2, (int)lane3);

            //Bier
            double xForNewBee = xValuesBee.get(random.nextInt(6))+(int)windowWidth;
            double yForNewBee = yValuesBee.get(random.nextInt(3));

            if (bees.size() < 3 && bees.stream()
                    .noneMatch(bee -> bee.getxPosition() == xForNewBee && bee.getyPosition() == yForNewBee)) {
                bees.add(new Bee(beeImage.getWidth(), beeImage.getHeight(), yForNewBee, xForNewBee));
            }

            //Honning
            double xForNewHoney = xValuesBee.get(random.nextInt(6))+(int)windowWidth;
            double yForNewHoney = yValuesBee.get(random.nextInt(3));

            if (honeyPots.size() < 4 && honeyPots.stream()
                    .noneMatch(honey -> honey.getxPosition() == xForNewHoney && honey.getyPosition() == yForNewHoney)) {
                honeyPots.add(new Honey(honeyImage.getWidth(), honeyImage.getHeight(), yForNewHoney, xForNewHoney));
            }

           /* List<Honey> eatenHoney = honeyPots.stream().filter(honey -> bear.ateHoney(honey)).collect(Collectors.toList());
            honeyPots.removeAll(eatenHoney);*/

            List<Honey> eatenHoneypots = new ArrayList<>();
            honeyPots.forEach(honey -> {
                boolean gotEaten = bear.ateHoney(honey);
                if(gotEaten){
                    eatenHoneypots.add(honey);
                }
            });
            honeyPots.removeAll(eatenHoneypots);


            List<Bee> deadBees = new ArrayList<>();
            bees.forEach(bee -> {
                boolean gotStung = bear.checkIfGotStungBy(bee);
                if(gotStung){
                    deadBees.add(bee);
                }
            });
            bees.removeAll(deadBees);
        }
    }

    public void drawScore(BorderPane borderPane){
        borderPane.getChildren().add(score);
        score.setTranslateX(500);
        score.setTranslateY(500);

    }

    public void scoreText(){
        score.setText("Score: " + bear.getEatenHoney());
    }

    public void newGame() {
        if (state.equals(GameState.RUNNING.toString())){
            state.setValue(GameState.PAUSED.toString());
        }
        bees = new ArrayList<>();
        honeyPots = new ArrayList<>();
        bear = new Bear(bearImage.getWidth(), bearImage.getHeight());
        beeMover = beeMover();
        honeyMover = honeyMover();
        state.setValue(GameState.NEW_LEVEL.toString());
        state.setValue(GameState.RUNNING.toString());
        beeMover.start();
        honeyMover.start();
        drawScore(borderPane);
    }
    public boolean isPaused() {
        return state.get().equals(GameState.PAUSED.toString());
    }
}