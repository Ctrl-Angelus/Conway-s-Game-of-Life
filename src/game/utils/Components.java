package game.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.Objects;

public class Components {
    public static ImageView getView(Image image, double width, boolean preserveRatio){
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

    public static void setIntegerSpinnerConfig(Spinner<Integer> spinner, Font font, double width, int min, int max, int initial, int step){
        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial)
        );
        spinner.setEditable(true); // permitir escribir directamente
        spinner.setPrefWidth(width);
        spinner.getEditor().setFont(font);
        SpinnerValueFactory.IntegerSpinnerValueFactory ValueFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) spinner.getValueFactory();
        ValueFactory.setAmountToStepBy(step);
    }

    public static void drawImage(Canvas canvas, GraphicsContext gc, Image image){
        double canvasRatio = canvas.getWidth() / canvas.getHeight();
        double imageRatio = image.getWidth() / image.getHeight();

        double drawWidth, drawHeight;

        if (imageRatio > canvasRatio) {
            // La imagen es más ancha: ajustamos al ancho del canvas
            drawWidth = canvas.getWidth();
            drawHeight = canvas.getWidth() / imageRatio;
        } else {
            // La imagen es más alta: ajustamos a la altura del canvas
            drawHeight = canvas.getHeight();
            drawWidth = canvas.getHeight() * imageRatio;
        }
        drawWidth *= 0.95;
        drawHeight *= 0.95;

        double x = (canvas.getWidth() - drawWidth) / 2;
        double y = (canvas.getHeight() - drawHeight) / 2;

        gc.drawImage(image, x, y, drawWidth, drawHeight);
    }
}
