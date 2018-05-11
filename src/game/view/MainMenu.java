package game.view;

import game.model.GameState;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import game.controller.GameController;

public class MainMenu extends MenuBar {
    private Menu fileMenu;
    private MenuItem newGameItem;
    private MenuItem loadGameItem;
    private MenuItem pauseResumeItem;
    private MenuItem exit;
    private Menu helpMenu;
    private MenuItem aboutItem;

    public MainMenu(GameController gameController) {
        fileMenu = new Menu("Fil");
        newGameItem = new MenuItem("Nytt spill");
        loadGameItem = new MenuItem("Last inn spill");
        pauseResumeItem = new MenuItem("Pause");

        newGameItem.setOnAction(e -> {
            e.consume();
            gameController.newGame();
        });
        loadGameItem.setOnAction(event -> {

        });
        pauseResumeItem.setOnAction(e -> {
            e.consume();
            if(!gameController.isPaused()){
                gameController.pause();
                new GameMenu(gameController, true);
            }
            else{
                gameController.resume();
            }
        });
        gameController.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(GameState.PAUSED.toString())) {
                pauseResumeItem.setText("Fortsett");
            } else {
                pauseResumeItem.setText("Pause");

            }
        });


        exit = new MenuItem("Avslutt");
        exit.setOnAction(e -> {
            e.consume();
            gameController.exit();
        });

        fileMenu.getItems().addAll(newGameItem, loadGameItem, pauseResumeItem, exit);

        helpMenu = new Menu("Hjelp");

        aboutItem = new MenuItem("Om");
        aboutItem.setOnAction(e -> {
            e.consume();
            gameController.pause();
            new game.view.Tutorial();
        });
        helpMenu.getItems().addAll(aboutItem);

        getMenus().addAll(fileMenu, helpMenu);
    }
}
