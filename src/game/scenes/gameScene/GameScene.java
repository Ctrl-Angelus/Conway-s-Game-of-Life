package game.scenes.gameScene;

import game.cellConfigurations.SaveManager;
import game.utils.Cell;
import game.utils.Components;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static game.utils.AppParameters.*;
import static game.utils.Assets.*;
import static game.utils.ColorPalette.*;
import static game.scenes.gameScene.GenerationalLogic.*;

public class GameScene {

    public static int generations;
    public static int SimulationSize;
    public static double cellSize;
    public static double realWidth; // Tamaño real del Canvas luego de calcular el tamaño de la célula

    public GameScene(){
        resetGenerations();
    }
    public static void setSimulationParameters(int inputSize){
        SimulationSize = inputSize;
        cellSize = (double) appInitialWidth / SimulationSize;
        realWidth = cellSize * SimulationSize;
    }
    public static void resetGenerations(){
        generations = 0;
    }

    public Scene getGameScene(Stage stage, Scene alternativeScene, double FPS, int simulationSize, Cell[][] firstGeneration){
        setSimulationParameters(simulationSize);

        Canvas canvas = new Canvas(realWidth, realWidth); // Lienzo (mejor optimización para dibujar muchos trazos)
        GraphicsContext gc = canvas.getGraphicsContext2D();

        cellMatrix = (firstGeneration == null) ? randomFirstGenerationCells(gc) : firstGeneration;
        drawGeneration(gc, cellMatrix);
        // Generación aleatoria de células para la posición inicial

        Text generationCounter = new Text("Gen: 0");
        generationCounter.setFont(customFont);
        generationCounter.getStyleClass().add("text-style");
        generationCounter.setFill(textColor);
        generationCounter.setLayoutX(20);
        generationCounter.setLayoutY(realWidth + 60);

        Button playBtn = new Button();
        playBtn.setGraphic(Components.getView(btnPlayVariantImage, 140, true));
        playBtn.getStyleClass().add("btn-sprite");
        playBtn.setLayoutX(realWidth - 160);
        playBtn.setLayoutY(realWidth + 7);

        Button nextStepBtn = new Button();
        nextStepBtn.setGraphic(Components.getView(btnStepImage, 140, true));
        nextStepBtn.getStyleClass().add("btn-sprite");
        nextStepBtn.setLayoutX(realWidth - 320);
        nextStepBtn.setLayoutY(realWidth + 7);

        Button saveBtn = new Button();
        saveBtn.setGraphic(Components.getView(btnSaveImage, 140, true));
        saveBtn.getStyleClass().add("btn-sprite");
        saveBtn.setLayoutX(realWidth - 480);
        saveBtn.setLayoutY(realWidth + 7);

        Button menuBtn = new Button();
        menuBtn.setGraphic(Components.getView(btnMenuImage, 140, true));
        menuBtn.getStyleClass().add("btn-sprite");
        menuBtn.setLayoutX(realWidth - 640);
        menuBtn.setLayoutY(realWidth + 7);

        Pane pane = new Pane();
        pane.getChildren().addAll(generationCounter, playBtn, nextStepBtn, saveBtn, menuBtn);

        Group rootGroup = new Group(canvas, pane); // genera un grupo que va a almacenar el canvas y el texto

        // GameLoop
        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= NANOSECONDS/FPS) {
                    update(gc, generationCounter);
                    lastUpdate = now;
                }
            }
        };

        playBtn.setOnAction(_ -> {
            if(gameLoopActive){
                gameLoopActive = false;
                timer.stop();
                playBtn.setGraphic(Components.getView(btnPlayVariantImage, 140, true));
            } else {
                gameLoopActive = true;
                timer.start();
                playBtn.setGraphic(Components.getView(btnPauseImage, 140, true));
            }
        });

        nextStepBtn.setOnAction(_ -> {
            if(gameLoopActive){
                gameLoopActive = false;
                timer.stop();
                playBtn.setGraphic(Components.getView(btnPlayVariantImage, 140, true));
            }
            update(gc, generationCounter);
        });

        menuBtn.setOnAction(_ -> {
            timer.stop();
            stage.setScene(alternativeScene);
        });

        saveBtn.setOnAction(_ -> {
            try {
                SaveManager.saveMatrix(cellMatrix, stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(rootGroup, realWidth, appInitialHeight, backgroundColor);
        scene.getStylesheets().add(StyleSheet);

        return scene;
    }
}
