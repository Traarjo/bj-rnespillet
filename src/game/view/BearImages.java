package game.view;

/**
 * BearImages inneholder alle bjørnebildene, og en if-else if-metode for å få den til animasjonen og å bytte bjørn når man får x antall poeng.
 */

import javafx.scene.image.Image;

public class BearImages {

    private Image bearImage;
    private Image bearImage2;
    private Image bearImage3;
    private Image bearImage4;
    private Image bearImage5;
    private Image bearImage6;
    private Image bearImage7;
    private Image bearImage8;
    private Image bearImage9;
    private Image bearImage10;
    private Image bearImage11;
    private Image bearImage12;
    private Image bearImage13;
    private Image bearImage14;
    private Image bearImage15;
    private Image bearImage16;
    private Image bearImage17;
    private Image bearImage18;
    private Image bearImage19;
    private Image bearImage20;

    public BearImages(){
        bearImage = newImage("bilder/B1R.png");
        bearImage2 = newImage("bilder/B1R2.png");
        bearImage3 = newImage("bilder/B2R.png");
        bearImage4 = newImage("bilder/B2R2.png");
        bearImage5 = newImage("bilder/B3R.png");
        bearImage6 = newImage("bilder/B3R2.png");
        bearImage7 = newImage("bilder/B4R.png");
        bearImage8 = newImage("bilder/B4R2.png");
        bearImage9 = newImage("bilder/B5R.png");
        bearImage10 = newImage("bilder/B5R2.png");
        bearImage11 = newImage("bilder/B6R.png");
        bearImage12 = newImage("bilder/B6R2.png");
        bearImage13 = newImage("bilder/B7R.png");
        bearImage14 = newImage("bilder/B7R2.png");
        bearImage15 = newImage("bilder/B8R.png");
        bearImage16 = newImage("bilder/B8R2.png");
        bearImage17 = newImage("bilder/B9R.png");
        bearImage18 = newImage("bilder/B9R2.png");
        bearImage19 = newImage("bilder/B10R.png");
        bearImage20 = newImage("bilder/B10R2.png");
    }

    private Image newImage(String filename) {
        return new Image(filename, Size.windowwidth / 6, Size.windowheight / 5, true, false);
    }


    public Image getBearImage(int eatenHoney) {
        if (eatenHoney < 10) {
            return bearImage;
        } else if (eatenHoney >= 10 && eatenHoney < 20) {
            return bearImage3;
        } else if (eatenHoney >= 20 && eatenHoney < 30) {
            return bearImage5;
        } else if (eatenHoney >= 30 && eatenHoney < 40) {
            return bearImage7;
        } else if (eatenHoney >= 40 && eatenHoney < 50) {
            return bearImage9;
        } else if (eatenHoney >= 50 && eatenHoney < 60) {
            return bearImage11;
        } else if (eatenHoney >= 60 && eatenHoney < 70) {
            return bearImage13;
        } else if (eatenHoney >= 70 && eatenHoney < 80) {
            return bearImage15;
        } else if (eatenHoney >= 80 && eatenHoney < 90) {
            return bearImage17;
        } else {
            return bearImage19;
        }
    }


    public Image getBearImage2(int eatenHoney) {
        if (eatenHoney < 10) {
            return bearImage2;
        } else if (eatenHoney >= 10 && eatenHoney < 20) {
            return bearImage4;
        } else if (eatenHoney >= 20 && eatenHoney < 30) {
            return bearImage6;
        } else if (eatenHoney >= 30 && eatenHoney < 40) {
            return bearImage8;
        } else if (eatenHoney >= 40 && eatenHoney < 50) {
            return bearImage10;
        } else if (eatenHoney >= 50 && eatenHoney < 60) {
            return bearImage12;
        } else if (eatenHoney >= 60 && eatenHoney < 70) {
            return bearImage14;
        } else if (eatenHoney >= 70 && eatenHoney < 80) {
            return bearImage16;
        } else if (eatenHoney >= 80 && eatenHoney < 90) {
            return bearImage18;
        } else {
            return bearImage20;
        }
    }
}
