package game.view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StatusLine extends HBox {
    private Image heartImage;
    private List<ImageView> hearts;
    private Text score;
    private Text lives;
    private Text gameOver;
    private int numberOflives;
    private int points;

    public StatusLine(){
        this.points = 0;
        this.numberOflives = 3;
        this.score = new Text();
        this.lives = new Text();
        this.gameOver = new Text();
        heartImage = new Image("bilder/hjerte.png", 20, 20, true, false);
        hearts = new ArrayList<>();
        setMargin(score, new Insets(0, 100, 0, 100));
        setMargin(lives, new Insets(0, 100, 0, 100));
        render();

    }

    private void render(){
        getChildren().clear();
        score.setText("Score: "+points);
        lives.setText("Lives: "+numberOflives);

        getChildren().add(score);
        if(hearts.size() != numberOflives){
            hearts.clear();
            for(int i = 1; i <= numberOflives; i++){
                hearts.add(new ImageView(heartImage));
            }
        }
        getChildren().add(lives);
        getChildren().addAll(hearts);
        if (hearts.size() <=0) {
            gameOver.setText("Spillet er over");
            getChildren().remove(lives);
            getChildren().add(gameOver);
        }
    }

    public void lives(int lives){
        if(lives != numberOflives){
            numberOflives = lives;
            render();
        }

    }

    public void points(int score){
        if(score != points){
            points = score;
            render();
        }

    }
}
