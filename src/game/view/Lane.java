package game.view;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Klasse som inneholder de alternative Y-posisjonene til honning og bier.
 * @return en tilfeldig Y posisjon/lane
 * @author Tara Jørgensen
 */
public class Lane {

    public static double randomLane() {
        Image bearImage = new Image("bilder/B1R.png", Size.windowwidth / 6, Size.windowheight / 5, true, false);
        Random random = new Random();

        double lane1 = bearImage.getHeight() * 2.5 - bearImage.getHeight() * 1.25;
        double lane2 = bearImage.getHeight() * 2.5;
        double lane3 = bearImage.getHeight() * 2.5 + bearImage.getHeight() * 1.25;

        List<Integer> lane = Arrays.asList((int)lane1, (int)lane2, (int)lane3);

        return lane.get(random.nextInt(3));
    }
}
