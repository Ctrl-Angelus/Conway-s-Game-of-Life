package game.utils;

import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;

import static game.utils.ColorPalette.*;

public record Cell(int I, int J, double coordinateX, double coordinateY, boolean lifeState, double size) implements Serializable {

    public void draw(GraphicsContext gc) { // Dibuja la célula a partir de las propiedades
        gc.setFill(lifeState ? Alive : Dead);
        gc.setStroke(strokeColor);
        gc.strokeRect(coordinateX, coordinateY, size, size);
        gc.fillRect(coordinateX, coordinateY, size, size);
        gc.setStroke(Dead);
    }
}
