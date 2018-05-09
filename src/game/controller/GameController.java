package game.controller;

import game.model.GameState;
import game.view.Size;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import game.model.Bear;
import game.model.Bee;
import game.model.Honey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static game.view.Size.width;

public class GameController {
    private Bear bear;
    private StringProperty state = new SimpleStringProperty(GameState.PAUSED.toString());

    private List<Bee> bees = new ArrayList<>();
    private List<Honey> honeyPots = new ArrayList<>();
    private Thread beeMover;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;


    public GameController(Image bearImage, Image beeImage, Image honeyImage) {
        this.bearImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
    }

    private void createBees(double width, double height) {
        bees.add(new Bee(width, height, 200, Math.random()*50.0));
        //bees.add(new Bee(width, height, 350, Math.random()*100.0));
        //bees.add(new Bee(width, height, 500, Math.random()*150.0));

  }


    private void createHoneyPots(double width, double height) {
        honeyPots.add(new Honey(width, height, 250, Math.random()*500.0));
       // honeyPots.add(new Honey(width, height, 400, Math.random()*500.0));
        // honeyPots.add(new Honey(width, height, 550, Math.random()*500.0));

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

    }

    public Bear getBear() {
        return bear;
    }

    private Thread beeMover() {
        return new Thread(() ->
        {

            while (state.get().equals(GameState.RUNNING.toString())) {
                bees.forEach(bee -> {
                        double currentPos = bee.getxPosition();
                        double newPosition = currentPos + bee.horizontalStepLength();
                        if (newPosition < width) {
                            bee.setxPosition(newPosition);
                        } else {
                            bee.setxPosition(0);
                        }
                });
                try {
                    //Let the bees sleep after each movement
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
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
        if (newPosition <= (Size.height - bear.verticalStepLength())) {
            bear.setyPosition(newPosition);
        }
    }

    public void moveBearRight() {
        double current = bear.getxPosition();
        double newPosition = current + bear.horizontalStepLength();
        if (newPosition < (width - bear.getWidth())) {
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

    public  synchronized void updateGameState() {
        if(isGameOver()){
          pause();
        }
        else {
            Random random = new Random();
            List<Integer> xValues = Arrays.asList(20, 200, 350, 500, 650, 770);
            double xForNewHoney = xValues.get(random.nextInt(6));

            List<Integer> yValues = Arrays.asList(250, 400, 550);
            double yForNewHoney = yValues.get(random.nextInt(3));

            if (honeyPots.size() < 6 && honeyPots.stream()
                    .noneMatch(honey -> honey.getxPosition()
                            == xForNewHoney && honey.getyPosition() == yForNewHoney)) {
                honeyPots.add(new Honey(honeyImage.getWidth(), honeyImage.getHeight(), yForNewHoney, xForNewHoney));
            }

            List<Integer> xValuesBee = Arrays.asList(20, 200, 350, 500, 650, 770);
            double xForNewBee = xValuesBee.get(random.nextInt(6));

            List<Integer> yValuesBee = Arrays.asList(250, 400, 550);
            double yForNewBee = yValuesBee.get(random.nextInt(3));

            if (bees.size() < 3 && bees.stream()
                    .noneMatch(bee -> bee.getxPosition()
                            == xForNewBee && bee.getyPosition() == yForNewBee)) {
                bees.add(new Bee(beeImage.getWidth(), beeImage.getHeight(), yForNewBee, xForNewBee));
            }


            List<Honey> eatenHoney = honeyPots.stream().filter(honey -> bear.ateHoney(honey)).collect(Collectors.toList());
            honeyPots.removeAll(eatenHoney);
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

    public void newGame() {
        if(state.equals(GameState.RUNNING.toString())){
            state.setValue(GameState.PAUSED.toString());
        }
        bees = new ArrayList<>();
        honeyPots = new ArrayList<>();
        bear = new Bear(bearImage.getWidth(), bearImage.getHeight());
        createHoneyPots(honeyImage.getWidth(), honeyImage.getHeight());
        createBees(beeImage.getWidth(), beeImage.getHeight());
        beeMover = beeMover();
        state.setValue(GameState.NEW_LEVEL.toString());
        state.setValue(GameState.RUNNING.toString());
        beeMover.start();





    }


    public boolean isPaused() {
        return state.get().equals(GameState.PAUSED.toString());
    }
}
