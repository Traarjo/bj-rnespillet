package game.model;

public class Bear {
    private int eatenHoney;
    private int lives;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Bear(double width, double height) {
        this.xPosition = 100;
        this.yPosition = 500;
        this.eatenHoney = 0;
        this.lives = 3;
        this.width = width;
        this.height = height;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public int getEatenHoney() {
        return eatenHoney;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


    public boolean checkIfGotStungBy(Bee bee) {
        if (xAxisIsInRange(bee.getxPosition(), bee.getWidth()) && yAxisIsInRange(bee.getyPosition(), bee.getHeight())) {
            bee.setLastStungTime(System.currentTimeMillis());
            lives = lives--;
            return true;

        }
        return false;
    }


   /* public boolean isGameOver() {
        if (livesLeft() = 0) {
            System.out.println("Game over");
            return true;
        }

        return false;
    }  */

    public boolean ateHoney(Honey honey) {
        if (xAxisIsInRange(honey.getxPosition(), honey.getWidth()) && yAxisIsInRange(honey.getyPosition(), honey.getHeight())) {
            eatenHoney = eatenHoney++;
            return true;

        }

        return false;
    }

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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double horizontalStepLength() {
        return width / 6;
    }

    public double verticalStepLength() {
        return height * 1.25;
    }

    private boolean isInRange(double val, double lowest, double highest) {

        return val >= lowest && val <= highest;
    }
}
