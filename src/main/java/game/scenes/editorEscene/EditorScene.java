package game.scenes.editorEscene;

import game.cellConfigurations.SaveManager;
import game.scenes.gameScene.GameScene;
import game.scenes.gameScene.GenerationalLogic;
import game.utils.Cell;
import game.utils.Components;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

import static game.utils.AppParameters.appInitialHeight;
import static game.utils.AppParameters.buttonSize;
import static game.utils.Assets.*;
import static game.utils.ColorPalette.backgroundColor;

public class EditorScene extends GameScene {
    private static Cell[][] editorMatrix;
    public Scene getScene(Stage stage, int simulationSize, double fpsValue, Scene parentScene){
        setSimulationParameters(simulationSize);

        Canvas canvas = new Canvas(realWidth, realWidth); // Lienzo (mejor optimización para dibujar muchos trazos)
        GraphicsContext gc = canvas.getGraphicsContext2D();
        editorMatrix = generateBlankMatrix(SimulationSize);

        GenerationalLogic.drawGeneration(gc, editorMatrix);

        Button playBtn = new Button();
        playBtn.setGraphic(Components.getView(btnPlayImage, buttonSize, true));
        playBtn.getStyleClass().add("btn-sprite");

        Button saveBtn = new Button();
        saveBtn.setGraphic(Components.getView(btnSaveImage, buttonSize, true));
        saveBtn.getStyleClass().add("btn-sprite");

        Button loadBtn = new Button();
        loadBtn.setGraphic(Components.getView(btnLoadImage, buttonSize, true));
        loadBtn.getStyleClass().add("btn-sprite");

        Button menuBtn = new Button();
        menuBtn.setGraphic(Components.getView(btnMenuImage, buttonSize, true));
        menuBtn.getStyleClass().add("btn-sprite");

        HBox buttonContainer = new HBox(30, menuBtn, loadBtn, saveBtn, playBtn);
        buttonContainer.setPrefWidth(realWidth);
        buttonContainer.setPrefHeight(appInitialHeight - realWidth);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setLayoutY(realWidth);
        buttonContainer.setLayoutX(0);

        Group componentContainer = new Group(buttonContainer, canvas);

        Scene scene = new Scene(componentContainer, realWidth, appInitialHeight, backgroundColor);
        scene.getStylesheets().add(StyleSheet);

        playBtn.setOnAction(_ -> {
            Scene gameScene = new GameScene().getGameScene(
                    stage,
                    parentScene,
                    fpsValue,
                    SimulationSize,
                    editorMatrix);
            stage.setScene(gameScene); // Cambia la escena actual por gameScene
        });

        saveBtn.setOnAction(_ -> {
            try {
                SaveManager.saveMatrix(editorMatrix, stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        menuBtn.setOnAction(_ -> stage.setScene(parentScene));

        loadBtn.setOnAction(_ -> {
            try {
                var loadedMatrix = SaveManager.loadMatrix(stage);

                if(loadedMatrix == null){
                    System.out.println("The position you're trying to access is either corrupted or hasn't been created yet.");
                } else {
                    setSimulationParameters(loadedMatrix.length);
                    editorMatrix = loadedMatrix;
                    GenerationalLogic.drawGeneration(gc, editorMatrix);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        canvas.setOnMouseClicked(mouseEvent -> {


            for (Cell[] cellRow : editorMatrix){
                for (Cell cell : cellRow){
                    boolean conditionXCoordinate = (mouseEvent.getX() >= cell.coordinateX()) && (mouseEvent.getX() <= cell.coordinateX() + cell.size());
                    boolean conditionYCoordinate = (mouseEvent.getY() >= cell.coordinateY()) && (mouseEvent.getY() <= cell.coordinateY() + cell.size());

                    if (conditionXCoordinate && conditionYCoordinate && !GenerationalLogic.checkForBorders(cell.I(), cell.J())){
                        //System.out.println(cell);
                        cell = new Cell(cell.I(), cell.J(), cell.coordinateX(), cell.coordinateY(), !cell.lifeState(), cell.size());
                        editorMatrix[cell.I()][cell.J()] = cell;
                        GenerationalLogic.drawGeneration(gc, editorMatrix);
                        //System.out.println(cell);
                    }
                }
            }
        });

        return scene;
    }

    private static Cell[][] generateBlankMatrix(int simulationSize){
        var matrix = new Cell[simulationSize][simulationSize];
        double coordinateX;
        double coordinateY = 0;
        for (int i = 0; i < simulationSize; i++) {
            coordinateX = 0;
            for (int j = 0; j < simulationSize; j++) {
                matrix[i][j] = new Cell(i, j, coordinateX, coordinateY, false, cellSize);
                coordinateX += cellSize;
            }
            coordinateY += cellSize;
        }
        return matrix;
    }
}
