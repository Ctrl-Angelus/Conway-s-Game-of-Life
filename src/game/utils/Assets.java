package game.utils;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import java.util.Objects;

import static game.utils.AppParameters.appInitialHeight;
import static game.utils.Components.getImage;

public class Assets {

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
    public static final Image iconImage = getImage("/game/resources/icons/icon.png");

    // Banner creado en LibreSprite
    public static final Image bannerImage = getImage("/game/resources/sprites/banner.png");
    public static final Image notFoundImage = getImage("/game/resources/sprites/messages/message-not-found.png");
    public static final Image successImage = getImage("/game/resources/sprites/messages/message-success.png");
    public static final Image btnStartImage = getImage("/game/resources/sprites/buttons/btn-start.png");
    public static final Image btnLoadImage = getImage("/game/resources/sprites/buttons/btn-load.png");
    public static final Image btnEditorImage = getImage("/game/resources/sprites/buttons/btn-editor.png");
    public static final Image btnReturnImage = getImage("/game/resources/sprites/buttons/btn-return.png");
    public static final Image btnMenuImage = getImage("/game/resources/sprites/buttons/btn-menu.png");
    public static final Image btnPlayImage = getImage("/game/resources/sprites/buttons/btn-play.png");
    public static final Image btnPlayVariantImage = getImage("/game/resources/sprites/buttons/btn-play-variant.png");
    public static final Image btnPauseImage = getImage("/game/resources/sprites/buttons/btn-pause.png");
    public static final Image btnContinueImage = getImage("/game/resources/sprites/buttons/btn-continue.png");
    public static final Image btnSaveImage = getImage("/game/resources/sprites/buttons/btn-save.png");
    public static final Image btnStepImage = getImage("/game/resources/sprites/buttons/btn-step.png");
    public static final Image btnInformationImage = getImage("/game/resources/sprites/buttons/btn-info.png");
}
