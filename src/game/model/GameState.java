package game.model;

/**
 * GameState inneholder tre states, og det er faste verdier (konstanter) for hva som er lov.
 * GameState er for å si fra om spillet kjører eller er pauset.
 * Mye av oppførselen til spillet er avhengig av om spillet kjører eller ikke.
 */

public enum GameState {
    NEW_LEVEL, RUNNING, PAUSED
}
