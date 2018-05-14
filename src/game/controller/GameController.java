package game.controller;

import game.model.*;
import game.view.GameMenu;
import game.view.Size;
import game.view.Lane;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static game.view.Size.windowwidth;

public class GameController {
    private Bear bear;
    private StringProperty state = new SimpleStringProperty(GameState.PAUSED.toString());
    private List<Bee> bees = new ArrayList<>();
    private List<Honey> honeyPots = new ArrayList<>();
    private ScheduledExecutorService beeMover;
    private ScheduledExecutorService honeyMover;
    private Image bearImage;
    private Image beeImage;
    private Image honeyImage;
    private Path gameDataFile;
    /*private List<HighScore> highScores = new ArrayList<>();*/



    public GameController(Image bearImage, Image beeImage, Image honeyImage) {
        this.bearImage = bearImage;
        this.beeImage = beeImage;
        this.honeyImage = honeyImage;
        this.gameDataFile = Paths.get("gamestate");
        beeMover = Executors.newSingleThreadScheduledExecutor();
        beeMover.scheduleAtFixedRate(beeMover(), 7, 7, TimeUnit.MILLISECONDS);
        honeyMover = Executors.newSingleThreadScheduledExecutor();
        honeyMover.scheduleAtFixedRate(honeyMover(), 5, 10, TimeUnit.MILLISECONDS);

    }

    public Bear getBear() {
        return bear;
    }

    public void pause() {
        state.setValue(GameState.PAUSED.toString());
    }

    public void resume() {
        if(!isGameOver()){
            state.setValue(GameState.RUNNING.toString());
        }
    }

    /*public void addHighScore(HighScore score){
        highScores.add(score);
        Collections.sort(highScores, (o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

    }

    public List<HighScore> getHighScores(){
        return highScores;
    } */

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


    public void loadGame(){
        if(canLoadGame()){
            String currentLine;
            try(BufferedReader reader = Files.newBufferedReader(gameDataFile)){
                while((currentLine = reader.readLine()) != null){
                    String[] tokens = currentLine.split(";");

                    if(tokens[0].equals("Bear")){
                        bear = new Bear(bearImage.getWidth(), bearImage.getHeight());
                        bear.setEatenHoney(Integer.parseInt(tokens[1]));
                        bear.setLives(Integer.parseInt(tokens[2]));
                        bear.setxPosition(Double.parseDouble(tokens[3]));
                        bear.setyPosition(Double.parseDouble(tokens[4]));



                    }

                    else if(tokens[0].equals("Honey")){
                        honeyPots.add(new Honey(honeyImage.getWidth(), honeyImage.getHeight(), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[1])));

                    }

                    else if(tokens[0].equals("Bee")){
                        bees.add(new Bee(beeImage.getWidth(), beeImage.getHeight(), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[1])));

                    }
                }
                state.setValue(GameState.RUNNING.toString());


            } catch (IOException e) {
                //Failed to load file, loading a new game...
               newGame();
            }

            deleteGameFile();


        }
    }

    public boolean canLoadGame() {
        return Files.exists(gameDataFile);
    }

    private void saveGameState() {
       if(!isGameOver()){
           try {
               deleteGameFile();
               try(BufferedWriter writer = Files.newBufferedWriter(gameDataFile)){
                   writer.write("Bear;");
                   writer.write(Integer.toString(bear.getEatenHoney()));
                   writer.write(";");
                   writer.write(Integer.toString(bear.getLives()));
                   writer.write(";");
                   writer.write(Double.toString(bear.getxPosition()));
                   writer.write(";");
                   writer.write(Double.toString(bear.getyPosition()));
                   writer.newLine();

                   for(Honey honey : honeyPots){
                       writer.write("Honey;");
                       writer.write(Double.toString(honey.getxPosition()));
                       writer.write(";");
                       writer.write(Double.toString(honey.getyPosition()));
                       writer.newLine();
                   }

                   for(Bee bee : bees){
                       writer.write("Bee;");
                       writer.write(Double.toString(bee.getxPosition()));
                       writer.write(";");
                       writer.write(Double.toString(bee.getyPosition()));
                       writer.newLine();
                   }
                   writer.flush();



               }
           } catch (IOException ignored) {
           }


       }
    }

    private void deleteGameFile()  {
        if(Files.exists(gameDataFile)){
            try {
                Files.delete(gameDataFile);
            } catch (IOException ignored) {
            }
        }
    }

    private Runnable beeMover() {
        return () -> {
            if(isGameRunning()){
                bees.forEach(bee -> {
                    double currentPos = bee.getxPosition();
                    double newPosition = currentPos - bee.horizontalStepLength();
                    if (newPosition > -beeImage.getWidth()) {
                        bee.setxPosition(newPosition);
                    } else {
                        bee.setxPosition(windowwidth+beeImage.getWidth());
                        bee.setyPosition(Lane.randomLane());
                    }
                });

            }
        };
    }

    private Runnable honeyMover(){
        return () -> {
            if(isGameRunning()){
                honeyPots.forEach(honey -> {
                    double currentPos = honey.getxPosition();
                    double newPosition = currentPos - honey.horizontalStepLength();
                    if (newPosition > -honeyImage.getWidth()) {
                        honey.setxPosition(newPosition);
                    } else {
                        honey.setxPosition(windowwidth+honeyImage.getWidth());
                        honey.setyPosition(Lane.randomLane());
                    }
                });

            }
        };
    }

    public void loopTrees(ImageView trees1, ImageView trees2) {
        ParallelTransition parallelTransition;

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(8000), trees1);
        trees1.setFitWidth(windowwidth * 2.7);
        trees1.setFitHeight(windowwidth/3.4);
        translateTransition.setFromX(0);
        translateTransition.setToX(-3 * windowwidth);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(8000), trees2);
        trees2.setFitWidth(windowwidth * 2.7);
        trees2.setFitHeight(windowwidth/3.4);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-3 * windowwidth);
        translateTransition2.setInterpolator(Interpolator.LINEAR);


        parallelTransition = new ParallelTransition(translateTransition/*, translateTransition2*/);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

       /* if (!isGameRunning()){
            parallelTransition.stop();
        }*/
    }


    public boolean isGameRunning() {
        return state.get().equals(GameState.RUNNING.toString());
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
        if (newPosition < (windowwidth - bear.getWidth())) {
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
         return bear == null ||  bear.getLives() <= 0;
    }

    //dele opp denne?

    /**
     * Funksjonalitet som oppdateres hele tiden.
     * Viser hvor honning og bier kan legge seg på brettet, hvor mange det kan være, og sier at om man spiser honning/blir stukket av bie, så skal honning/bie forsvinne fra brettet.
     */
    public  synchronized void updateGameState() {
        if(isGameOver()){
          pause();
          new GameMenu(this, false);
        }
        else {
            Random random = new Random();

            List<Integer> xValue = Arrays.asList(100, 250, 400, 550, 700, 850);

            //Bier
            double xForNewBee = xValue.get(random.nextInt(6))+Size.width();

            if (bees.size() < 3 && bees.stream()
                    .noneMatch(bee -> bee.getxPosition() == xForNewBee && bee.getyPosition() == Lane.randomLane())) {
                bees.add(new Bee(beeImage.getWidth(), beeImage.getHeight(), Lane.randomLane(), xForNewBee));
            }

            //Honning
            double xForNewHoney = xValue.get(random.nextInt(6))+Size.width();

            if (honeyPots.size() < 6 && honeyPots.stream()
                    .noneMatch(honey -> honey.getxPosition() == xForNewHoney && honey.getyPosition() == Lane.randomLane())) {
                honeyPots.add(new Honey(honeyImage.getWidth(), honeyImage.getHeight(), Lane.randomLane(), xForNewHoney));
            }



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

    /**
     * Når nytt spill startes, skal en evt. eksisterende fil slettes først.
     * Bier og honning tømmes fra brettet, og man tegner en ny bjørn.
     * Så settes spillet i gang, spillet oppdateres. 
     */
    public void newGame() {
        deleteGameFile();
        if (isGameRunning()){
            pause();
        }
        bees.clear();
        honeyPots.clear();
        bear = new Bear(bearImage.getWidth(), bearImage.getHeight());
        state.setValue(GameState.RUNNING.toString());
    }



    public boolean isPaused() {
        return state.get().equals(GameState.PAUSED.toString());
    }
}