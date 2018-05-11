package game.view;


import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class Size {
    public static double windowwidth = width();
    public static double windowheight = height();


    public static double width() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double windowwidth = primaryScreenBounds.getWidth()/2;
        return windowwidth;
    }
    public  static double height() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double windowheight = primaryScreenBounds.getHeight()/1.5;
        return windowheight;
    }
}