package game.model;

import game.view.Size;
import javafx.scene.image.Image;

/**
 * Her oppretter man bjørnen, og gir den størrelse, posisjon og andre verdier.
 * Sjekke om bjørnen spiser honning eller om den blir stukket av bie, altså kræsj-funksjon
 */

public class Bear {
    public int eatenHoney = 0;
    private int lives;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    Image bear_img = new Image("bilder/B1R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);

    public Bear(double width, double height) {
        this.xPosition = bear_img.getWidth() * 2;
        this.yPosition = bear_img.getHeight() * 2.5;
        this.eatenHoney = 0;
        this.lives = 3;
        this.width = width;
        this.height = height;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }
    public double getxPosition() {
        return xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    public double getyPosition() {
        return yPosition;
    }

    public int getEatenHoney() {
        return eatenHoney;
    }

    public void setEatenHoney(int eatenHoney){ this.eatenHoney = eatenHoney;}

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean checkIfGotStungBy(Bee bee) {
        if (xAxisIsInRange(bee.getxPosition(), bee.getWidth()) && yAxisIsInRange(bee.getyPosition(), bee.getHeight())) {
            lives--;
            if(lives < 0){
                lives = 0;
            }
           return true;
        }
        return false;
    }


    public boolean ateHoney(Honey honey) {
        if (xAxisIsInRange(honey.getxPosition(), honey.getWidth()) && yAxisIsInRange(honey.getyPosition(), honey.getHeight())) {
            setEatenHoney(getEatenHoney()+1);
            return true;
        }
        return false;
    }


    /**
     * Her sjekkes det om x-posisjon, y-posisjon, høyde og vidde er det samme.
     */
    private boolean xAxisIsInRange(double objectXPosition, double objectWidth) {
        double highestX = this.xPosition + getWidth();
        double highestObjectX = objectXPosition + objectWidth;
        return (xPosition < objectXPosition && highestX > highestObjectX)
                || isInRange(xPosition, objectXPosition, highestObjectX)
                || isInRange(highestX, objectXPosition, highestObjectX);
    }
    private boolean yAxisIsInRange(double objectYPosition, double objectHeight) {
        double highestY = this.yPosition + getHeight();
        double highestObjectY = objectYPosition + objectHeight;
        return (yPosition < objectYPosition && highestY > highestObjectY)
                || isInRange(yPosition, objectYPosition, highestObjectY)
                || isInRange(highestY, objectYPosition, highestObjectY);
    }
    private boolean isInRange(double val, double lowest, double highest) {
        return val >= lowest && val <= highest;
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double startPosition() {
        return height * 2.5;
    }
    public double horizontalStepLength() {
        return width / 5;
    }
    public double verticalStepLength() {
        return height * 1.25;
    }
}
