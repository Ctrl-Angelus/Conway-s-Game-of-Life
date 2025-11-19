package game;

import game.scenes.menuScene.MenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static game.utils.AppParameters.*;
import static game.utils.Assets.iconImage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Scene menuScene = new MenuScene().getMenuScene(stage);

        stage.getIcons().add(iconImage); // Modifica el ícono
        stage.setScene(menuScene); // Añade la escena principal menuScene
        stage.setResizable(false);
        stage.setTitle(title);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
