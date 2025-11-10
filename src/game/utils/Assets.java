package game.utils;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import java.util.Objects;

public class Assets {

    // Hoja de estilos css para los componentes
    public static final String StyleSheet = Objects.requireNonNull(
            Assets.class.getResource("/game/resources/styles/styles.css")
    ).toExternalForm();

    // Fuente personalizada "Medodica"
    private static final int defaultFontSize = 35;
    public static Font customFont = Font.loadFont(
            Assets.class.getResourceAsStream("/game/resources/fonts/Medodica.otf"), defaultFontSize
    );

    // Icono personalizado (creado en InkScape) presente en la carpeta game.resources
    public static final Image iconImage = new Image(
            Objects.requireNonNull(Assets.class.getResourceAsStream("/game/resources/icons/icon.png"))
    );

    // Banner creado en LibreSprite
    public static final Image bannerImage = new Image(
            Objects.requireNonNull(Assets.class.getResourceAsStream("/game/resources/sprites/banner.png"))
    );

    public static final Image notFoundImage = new Image(
            Objects.requireNonNull(Assets.class.getResourceAsStream("/game/resources/sprites/messages/message-not-found.png"))
    );

    public static final Image successImage = new Image(
            Objects.requireNonNull(Assets.class.getResourceAsStream("/game/resources/sprites/messages/message-success.png"))
    );
}
