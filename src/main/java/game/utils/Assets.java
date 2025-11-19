package game.utils;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import java.util.Objects;

import static game.utils.AppParameters.appInitialHeight;
import static game.utils.Components.getImage;

public class Assets {

    private static final String path = "/game/resources/";

    // Hoja de estilos css para los componentes
    public static final String StyleSheet = Objects.requireNonNull(
            Assets.class.getResource("/game/resources/styles/styles.css")
    ).toExternalForm();

    // Fuente personalizada "Medodica"
    private static final double fontRelativeSize = appInitialHeight / 1080;
    private static final double defaultFontSize = 45 * fontRelativeSize;
    public static Font customFont = Font.loadFont(
            Assets.class.getResourceAsStream("/game/resources/fonts/Medodica.otf"), defaultFontSize
    );

    // Icono personalizado (creado en InkScape) presente en la carpeta game.resources
    public static final Image iconImage = getImage(path + "icons/icon.png");

    // Banner creado en LibreSprite
    public static final Image bannerImage = getImage(path + "sprites/banner.png");
    public static final Image notFoundImage = getImage(path + "sprites/messages/message-not-found.png");
    public static final Image successImage = getImage(path + "sprites/messages/message-success.png");
    public static final Image btnStartImage = getImage(path + "sprites/buttons/btn-start.png");
    public static final Image btnLoadImage = getImage(path + "sprites/buttons/btn-load.png");
    public static final Image btnEditorImage = getImage(path + "sprites/buttons/btn-editor.png");
    public static final Image btnReturnImage = getImage(path + "sprites/buttons/btn-return.png");
    public static final Image btnMenuImage = getImage(path + "sprites/buttons/btn-menu.png");
    public static final Image btnPlayImage = getImage(path + "sprites/buttons/btn-play.png");
    public static final Image btnPlayVariantImage = getImage(path + "sprites/buttons/btn-play-variant.png");
    public static final Image btnPauseImage = getImage(path + "sprites/buttons/btn-pause.png");
    public static final Image btnContinueImage = getImage(path + "sprites/buttons/btn-continue.png");
    public static final Image btnSaveImage = getImage(path + "sprites/buttons/btn-save.png");
    public static final Image btnStepImage = getImage(path + "sprites/buttons/btn-step.png");
    public static final Image btnInformationImage = getImage(path + "sprites/buttons/btn-info.png");
    public static final Image patternStillImage = getImage(path + "sprites/patterns/pattern-still.png");
    public static final Image patternOscillatoryImage = getImage(path + "sprites/patterns/pattern-oscillators.png");
    public static final Image patternSpaceshipImage = getImage(path + "sprites/patterns/pattern-spaceships.png");
}
