package game;

import game.menuScene.MenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Objects;

import static game.gameScene.Constants.*;

public class Main extends Application {

    // Hoja de estilos css para los componentes
    public static final String StyleSheet = Objects.requireNonNull(
            Main.class.getResource("resources/styles/styles.css")
    ).toExternalForm();

    // Fuente personalizada "Medodica"
    public static Font customFont = Font.loadFont(
            Main.class.getResourceAsStream("resources/fonts/Medodica.otf"), defaultFontSize
    );

    // Icono personalizado (creado en InkScape) presente en la carpeta game.resources
    public static final Image iconImage = new Image(
            Objects.requireNonNull(Main.class.getResourceAsStream("resources/icons/icon.png"))
    );

    public static final Image bannerImage = new Image(
            Objects.requireNonNull(Main.class.getResourceAsStream("resources/sprites/banner.png"))
    );

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
