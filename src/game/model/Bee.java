package game.model;

public class Bee {
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Bee(double width, double height, double yPosition, double xPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double horizontalStepLength() {
        return width / 40;
    }
}
