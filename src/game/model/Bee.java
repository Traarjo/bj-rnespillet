package game.model;

public class Bee {
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    private long lastStungTime = 0;

    public Bee(double width, double height, double yPosition, double xPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
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


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


    public double horizontalStepLength() {
        return width / 10;
    }

    public long lastStungTime() {
        return lastStungTime;
    }

    public void setLastStungTime(long timestamp){
        this.lastStungTime = timestamp;
    }
}
