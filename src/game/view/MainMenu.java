package game.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import game.controller.GameController;

public class MainMenu extends MenuBar {
    private Menu fileMenu;
    private MenuItem pauseResumeItem;
    private MenuItem exit;
    private Menu helpMenu;
    private MenuItem aboutItem;

    public MainMenu(GameController gameController) {
        fileMenu = new Menu("Fil");
        pauseResumeItem = new MenuItem("Pause");
        pauseResumeItem.setOnAction(e -> {
            e.consume();
            if(!gameController.isPaused().get()){
                gameController.pause();
            }
            else{
                gameController.resume();
            }


        });
        gameController.isPaused().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
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

        fileMenu.getItems().addAll(pauseResumeItem, exit);


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
