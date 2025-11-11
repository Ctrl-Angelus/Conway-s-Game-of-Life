package game.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Components {
    public static ImageView getView(Image image, int width, boolean preserveRatio){
        ImageView View = new ImageView(image);
        View.setFitWidth(width);
        View.setPreserveRatio(preserveRatio);
        return View;
    }

    public static Image getImage(String path){
        return new Image(
                Objects.requireNonNull(Assets.class.getResourceAsStream(path))
        );
    }
}
