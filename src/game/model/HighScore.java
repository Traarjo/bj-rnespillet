package game.model;

/**
 * Denne koden skal egentlig finne navn og highscore til som skal registreres etter at man er ferdig med spillet.
 * Vi ble ikke helt ferdig med denne delen av koden, men her kan man se litt hvordan vi har tenkt.
 */

public class HighScore{
    private String name;
    private int score;

    public HighScore(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

}
