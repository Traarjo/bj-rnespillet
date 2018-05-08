package game.view;


import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class Size {
    public static double width = width();
    public static double height = height();


    public static double width() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = primaryScreenBounds.getWidth()/2;
        return width;
    }
    public  static double height() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double height = primaryScreenBounds.getHeight()/1.5;
        return height;
    }
}