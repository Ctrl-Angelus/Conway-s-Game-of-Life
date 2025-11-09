package game.gameScene;

import javafx.scene.canvas.GraphicsContext;

import static game.utils.ColorPalette.*;

public record Cell(int I, int J, double coordinateX, double coordinateY, boolean lifeState, double size){
    public void draw(GraphicsContext gc) { // Dibuja la célula a partir de las propiedades
        gc.setFill(lifeState ? Alive : Dead);
        gc.fillRect(coordinateX, coordinateY, size, size);
        gc.setStroke(Dead);
    }
}
