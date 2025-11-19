package game.scenes.gameScene;

import game.cellConfigurations.SaveManager;
import game.utils.Cell;
import game.utils.Components;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static game.utils.AppParameters.*;
import static game.utils.Assets.*;
import static game.utils.ColorPalette.*;
import static game.scenes.gameScene.GenerationalLogic.*;

public class GameScene {

    public static int generations;
    public static int SimulationSize; // Tamaño de la matriz de células
    public static double cellSize;
    public static double realWidth; // Tamaño real del Canvas luego de calcular el tamaño de la célula

    public GameScene(){
        resetGenerations();
    }
    public static void setSimulationParameters(int inputSize){
        SimulationSize = inputSize;
        cellSize = appInitialWidth / SimulationSize;
        realWidth = cellSize * SimulationSize;
    }
    public static void resetGenerations(){
        generations = 0;
    }

    public Scene getGameScene(Stage stage, Scene alternativeScene, double FPS, int simulationSize, Cell[][] firstGeneration){
        setSimulationParameters(simulationSize);

        Canvas canvas = new Canvas(realWidth, realWidth); // Lienzo (mejor optimización para dibujar muchos trazos)
        GraphicsContext gc = canvas.getGraphicsContext2D();

        cellMatrix = (firstGeneration == null) ? randomFirstGenerationCells() : firstGeneration;
        drawGeneration(gc, cellMatrix);
        // Generación aleatoria de células para la posición inicial

        Text generationCounter = new Text("Gen: 0");
        generationCounter.setFont(customFont);
        generationCounter.getStyleClass().add("text-style");


        Button playBtn = new Button();
        playBtn.setGraphic(Components.getView(btnPlayVariantImage, buttonSize, true));
        playBtn.getStyleClass().add("btn-sprite");


        Button nextStepBtn = new Button();
        nextStepBtn.setGraphic(Components.getView(btnStepImage, buttonSize, true));
        nextStepBtn.getStyleClass().add("btn-sprite");


        Button saveBtn = new Button();
        saveBtn.setGraphic(Components.getView(btnSaveImage, buttonSize, true));
        saveBtn.getStyleClass().add("btn-sprite");


        Button menuBtn = new Button();
        menuBtn.setGraphic(Components.getView(btnMenuImage, buttonSize, true));
        menuBtn.getStyleClass().add("btn-sprite");

        StackPane textContainer = new StackPane(generationCounter);
        textContainer.setPrefWidth(realWidth/4);

        HBox buttonContainer = new HBox();
        buttonContainer.setPrefHeight(appInitialHeight - appInitialWidth);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(textContainer, menuBtn, saveBtn, nextStepBtn, playBtn);

        VBox rootGroup = new VBox(canvas, buttonContainer); // genera un grupo que va a almacenar el canvas y el texto
        rootGroup.getStyleClass().add("container");

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
                playBtn.setGraphic(Components.getView(btnPlayVariantImage, buttonSize, true));
            } else {
                gameLoopActive = true;
                timer.start();
                playBtn.setGraphic(Components.getView(btnPauseImage, buttonSize, true));
            }
        });

        nextStepBtn.setOnAction(_ -> {
            if(gameLoopActive){
                gameLoopActive = false;
                timer.stop();
                playBtn.setGraphic(Components.getView(btnPlayVariantImage, buttonSize, true));
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
