package game.gameScene;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static game.utils.AppParameters.*;
import static game.utils.ColorPalette.*;
import static game.gameScene.GenerationalLogic.*;

import static game.utils.Assets.customFont;

public class GameScene {

    public static int generations;

    public GameScene(){
        resetGenerations();
    }
    public static void resetGenerations(){
        generations = 0;
    }

    public Scene getGameScene(Stage stage, Scene alternativeScene, double FPS){
        Canvas canvas = new Canvas(realWidth, realWidth); // Lienzo (mejor optimización para dibujar muchos trazos)
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawFirstGenerationCells(gc); // Generación aleatoria de células para la posición inicial

        Text generationCounter = new Text("Generations: 0");
        generationCounter.setFont(customFont);
        generationCounter.getStyleClass().add("generationCounter-style");
        generationCounter.setFill(textColor);
        generationCounter.setLayoutX(10);
        generationCounter.setLayoutY(realWidth + 50);

        Button pauseBtn = new Button("Play");
        pauseBtn.getStyleClass().add("btn");
        pauseBtn.setFont(customFont);
        pauseBtn.setLayoutX(realWidth - 160);
        pauseBtn.setLayoutY(realWidth + 10);

        Button nextStepBtn = new Button("Step");
        nextStepBtn.getStyleClass().add("btn");
        nextStepBtn.setFont(customFont);
        nextStepBtn.setLayoutX(realWidth - 320);
        nextStepBtn.setLayoutY(realWidth + 10);

        Button menuBtn = new Button("Menu");
        menuBtn.getStyleClass().add("btn");
        menuBtn.setFont(customFont);
        menuBtn.setLayoutX(realWidth - 480);
        menuBtn.setLayoutY(realWidth + 10);

        Pane pane = new Pane();
        pane.getChildren().addAll(generationCounter, pauseBtn, nextStepBtn, menuBtn);

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

        pauseBtn.setOnAction(_ -> {
            if(gameLoopActive){
                gameLoopActive = false;
                timer.stop();
                pauseBtn.setText("Play");
            } else {
                gameLoopActive = true;
                timer.start();
                pauseBtn.setText("Pause");
            }
        });

        nextStepBtn.setOnAction(_ -> {
            if(gameLoopActive){
                gameLoopActive = false;
                timer.stop();
                pauseBtn.setText("Play");
            }
            update(gc, generationCounter);
        });

        menuBtn.setOnAction(_ -> {
            timer.stop();
            stage.setScene(alternativeScene);
        });

        return new Scene(rootGroup, realWidth, realWidth + 80, backgroundColor);
    }
}
