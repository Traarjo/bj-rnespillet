package game.model;

public class Honey {
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Honey(double width, double height, double yPosition, double xPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
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
