package game.controller;

/**
 * GameController
 *Denne klassen sørger for funksjonen til objektene som blir plassert i GameApplication.
 * Movement-metoder for bjørn, bie og honning, samt lagring og lasting fra fil
 * Pause-metoden ligger også her.
 *
 */

import game.model.*;
import game.view.GameMenu;
import game.view.Size;
import game.view.Lane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
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

    /**
     * Setter i gang beeMover() og honeyMover() og sier hvor raskt de skal gå over spillvinduet.
     * @param bearImage tar inn bildet av bjørnen
     * @param beeImage tar inn bildet av bien
     * @param honeyImage tar inn bildet av honning
     */
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

    /**
     * Henter opp og loader spillet fra filen som er lagret.
     */
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

    /**
     * Lagrer fil med x og y posisjonen til bjørnen, biene og honningen, samt score og liv.
     */
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

    /**
     * Sletter fil, hvis det eksisterer en fil fra før.
     */
    private void deleteGameFile()  {
        if(Files.exists(gameDataFile)){
            try {
                Files.delete(gameDataFile);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Singlethread, sørger for at bien beveger seg når spillet er i gang, biens forhåndsbestemte horisontale steglengde trekkes
     * fra dens nåværende posisjon. Om biens x-posisjon går utenfor spillet, settes den på en ny x-posisjon rett utenfor spillet
     * på motsatt side (resetter x-posisjonen). Biens y-posisjon bestemmes av Lane klassens metode randomLane()
     *
     */
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

    /**
     * Singlethread, samme metode som over, bare for honningen. Valgte denne måten for å enkelt velge hastigheten
     * til bien og honningen separat.
     *
     */
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

    public boolean isGameRunning() {
        return state.get().equals(GameState.RUNNING.toString());
    }

    /**
     * Metoder for å flytte bjørnen opp, ned og til sidene.
     */
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

    /**
     * Spillet er over om bjørnen har 0 liv igjen.
     * @return true
     */
    public boolean isGameOver(){
        if (bear == null ||  bear.getLives() <= 0) {
            return true;
        }
        return false;
    }



    /**
     * Funksjonalitet som oppdateres hele tiden.
     * Viser hvor honning og bier kan legge seg på brettet, hvor mange det kan være, og sier at om man spiser honning/blir
     * stukket av bie, så skal honning/bie forsvinne fra brettet. Setter animasjonene på pause om gamestaten er pauset.
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