package game.scenes.menuScene;

import game.cellConfigurations.SaveManager;
import game.scenes.gameScene.GameScene;
import game.scenes.messageScene.MessageScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

import static game.utils.AppParameters.appInitialWidth;
import static game.utils.Assets.*;

public class MenuScene {
    public Scene getMenuScene(Stage stage){
        Canvas canvas = new Canvas(appInitialWidth, (double) appInitialWidth /3);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        gc.drawImage(
                bannerImage,
                (canvas.getWidth() - bannerImage.getWidth()) / 2,
                (canvas.getHeight() - bannerImage.getHeight()) / 2,
                bannerImage.getWidth(),
                bannerImage.getHeight());

        Button playButton = new Button("Start");
        playButton.setFont(customFont);
        playButton.getStyleClass().add("btn");

        Button loadButton = new Button("Load");
        loadButton.setFont(customFont);
        loadButton.getStyleClass().add("btn");

        HBox buttonContainer = new HBox(20, playButton, loadButton);
        buttonContainer.setAlignment(Pos.CENTER);

        Spinner<Integer> fpsInput = new Spinner<>();
        fpsInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 10)); // min, max, initial
        fpsInput.setEditable(true); // permitir escribir directamente
        fpsInput.setPrefWidth(150);
        fpsInput.getEditor().setFont(customFont);
        SpinnerValueFactory.IntegerSpinnerValueFactory fpsInputValueFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) fpsInput.getValueFactory();
        fpsInputValueFactory.setAmountToStepBy(5);

        Spinner<Integer> simulationSizeInput = new Spinner<>();
        simulationSizeInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 80)); // min, max, initial
        simulationSizeInput.setEditable(true); // permitir escribir directamente
        simulationSizeInput.setPrefWidth(150);
        simulationSizeInput.getEditor().setFont(customFont);
        SpinnerValueFactory.IntegerSpinnerValueFactory simulationSizeInputValueFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) simulationSizeInput.getValueFactory();
        simulationSizeInputValueFactory.setAmountToStepBy(5);

        Label fpsInputLabel = new Label("Simulation's FPS:");
        fpsInputLabel.setLabelFor(fpsInputLabel);
        fpsInputLabel.setFont(customFont);
        fpsInputLabel.getStyleClass().add("label-style");

        Label simulationSizeInputLabel = new Label("Grid Size:");
        simulationSizeInputLabel.setLabelFor(simulationSizeInput);
        simulationSizeInputLabel.setFont(customFont);
        simulationSizeInputLabel.getStyleClass().add("label-style");

        HBox inputContainer = new HBox(20, fpsInputLabel, fpsInput, simulationSizeInputLabel, simulationSizeInput);
        inputContainer.setAlignment(Pos.CENTER);

        VBox menuRoot = new VBox(50, canvas, buttonContainer, inputContainer);
        menuRoot.setAlignment(Pos.TOP_CENTER);
        menuRoot.getStyleClass().add("vbox");


        Scene menuScene = new Scene(menuRoot, appInitialWidth, appInitialWidth + 80);
        menuScene.getStylesheets().add(StyleSheet);

        playButton.setOnAction(_ -> {
            Scene gameScene = new GameScene().getGameScene(
                    stage,
                    menuScene,
                    (double) fpsInput.getValue(),
                    simulationSizeInput.getValue(),
                    null);
            gameScene.getStylesheets().add(StyleSheet);
            stage.setScene(gameScene); // Cambia la escena actual por gameScene
        });

        loadButton.setOnAction(_ -> {
            try {
                var Matrix = SaveManager.loadMatrix("save.dat");

                int size;
                String message;
                String btnLabel = "Start";
                Image img;

                if(Matrix == null){
                    size = simulationSizeInput.getValue();
                    message = "The position you're trying to access is either corrupted or hasn't been created yet. " +
                            "You can start the simulation with a randomized position instead.";
                    img = notFoundImage;
                } else {
                    size = Matrix.length;
                    message = String.format("The %d x %d position has been correctly loaded.", size, size);
                    img = null;
                }

                Scene gameScene = new GameScene().getGameScene(
                        stage,
                        menuScene,
                        (double) fpsInput.getValue(),
                        size,
                        Matrix);
                gameScene.getStylesheets().add(StyleSheet);

                Scene messageScene = new MessageScene().getScene(
                        stage,
                        gameScene,
                        message,
                        btnLabel,
                        img);
                stage.setScene(messageScene);


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return menuScene;
    }
}
