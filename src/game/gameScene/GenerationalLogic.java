package game.gameScene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Random;

import static game.utils.AppParameters.*;

public class GenerationalLogic extends GameScene{
    public static void drawFirstGenerationCells(GraphicsContext gc){
        cellMatrix = new Cell[SIZE][SIZE];
        Random random = new Random();
        double coordinateX;
        double coordinateY = 0;

        for (int i = 0; i < SIZE; i++) {
            coordinateX = 0; // Resetea el valor de la coordenada X para cada nueva fila

            for (int j = 0; j < SIZE; j++) {
                int randomValue = random.nextInt(0, 2); // Valor de vida aleatorio
                boolean randomLifeState = (randomValue != 0); // 0 -> Falso, 1 -> Verdadero

                Cell cell = new Cell(
                        i, j,
                        coordinateX, coordinateY,
                        (!checkForBorders(i, j) && randomLifeState), // Evalúa si la célula esta en un borde o no, para colocar el life state
                        cellSize);

                cell.draw(gc); // Usa el Graphic context y la información de la célula para dibujarla
                cellMatrix[i][j] = cell; // Añade la célula a la matriz de 2 dimensiones

                coordinateX += cellSize;
            }
            coordinateY += cellSize;
        }
    }

    private static boolean checkForBorders(int i, int j){
        // Verifica si la célula está en un lateral de la matriz
        return (i == 0 || i == SIZE-1) || (j == 0 || j == SIZE-1);
    }

    public static void update(GraphicsContext gc, Text generationCounter){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, realWidth, realWidth); // Limpia el canvas

        setNextGenerationStates(); // Actualiza los valores de la vida de las células en la copia de la matriz
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copyMatrix[i][j].draw(gc); // Dibuja la nueva generación a partir de los valores calculados
            }
        }
        cellMatrix = copyMatrix.clone(); // Clona la copia de la matriz para usarla como referencia en la nueva generación
        copyMatrix = new Cell[SIZE][SIZE]; // Crea una matriz vacía que se usará para calcular los valores de la siguiente generación
        generations++;
        generationCounter.setText("Generations: " + generations); // Actualiza el contador de generaciones
    }

    private static void setNextGenerationStates(){
        copyMatrix = new Cell[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell cell = cellMatrix[i][j];
                int neighbors = checkNeighbors(i, j); // Calcula los vecinos vivos
                boolean newState = calculateNewState(cell, neighbors); // Calcula el estado de la célula para la nueva generación
                copyMatrix[i][j] = new Cell(i, j, cell.coordinateX(), cell.coordinateY(), newState, cellSize);
            }
        }
    }

    private static boolean calculateNewState(Cell cell, int neighbors) {
        boolean newState = cell.lifeState(); // Estado actual de la célula

        // Condiciones del juego de Conway
        if (cell.lifeState() && neighbors < 2){
            newState = false; // Muerte por aislamiento
        } else if (cell.lifeState() && neighbors > 3) {
            newState = false; // Muerte por sobrepoblación
        } else if (!cell.lifeState() && neighbors == 3) {
            newState = true; // Nacimiento de la célula
        }
        return newState;
    }

    private static int checkNeighbors(int i, int j){

        int neighbors = 0; // Valor por defecto

        if(checkForBorders(i, j)){ return 0; } // Las células del borde exterior permanecerán muertas

        int left = i-1;
        int up = j-1;
        int right = i+1;
        int down = j+1;

        // Analiza el estado de todas las células vecinas, incluyendo las esquinas
        neighbors += cellMatrix[left][up].lifeState() ? 1 : 0;
        neighbors += cellMatrix[i][up].lifeState() ? 1 : 0;
        neighbors += cellMatrix[right][up].lifeState() ? 1 : 0;
        neighbors += cellMatrix[right][j].lifeState() ? 1 : 0;
        neighbors += cellMatrix[right][down].lifeState() ? 1 : 0;
        neighbors += cellMatrix[i][down].lifeState() ? 1 : 0;
        neighbors += cellMatrix[left][down].lifeState() ? 1 : 0;
        neighbors += cellMatrix[left][j].lifeState() ? 1 : 0;

        return neighbors;
    }
}
