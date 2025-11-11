package game.scenes.menuScene;

import game.cellConfigurations.SaveManager;
import game.scenes.editorEscene.EditorScene;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static game.utils.AppParameters.appInitialHeight;
import static game.utils.AppParameters.appInitialWidth;
import static game.utils.Assets.*;

public class MenuScene {
    public Scene getMenuScene(Stage stage){
        Canvas canvas = new Canvas(appInitialWidth, (double) appInitialHeight /3);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        gc.drawImage(
                bannerImage,
                (canvas.getWidth() - bannerImage.getWidth()) / 2 + 10,
                (canvas.getHeight() - bannerImage.getHeight()) / 2,
                bannerImage.getWidth(),
                bannerImage.getHeight());

        Button playButton = new Button("Start");
        playButton.setFont(customFont);
        playButton.getStyleClass().add("btn");

        Button loadButton = new Button("Load");
        loadButton.setFont(customFont);
        loadButton.getStyleClass().add("btn");

        Button editorButton = new Button("Editor");
        editorButton.setFont(customFont);
        editorButton.getStyleClass().add("btn");

        HBox buttonContainer = new HBox(20, playButton, loadButton, editorButton);
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

        Label simulationSizeInputLabel = new Label("Simulation's Grid Size:");
        simulationSizeInputLabel.setLabelFor(simulationSizeInput);
        simulationSizeInputLabel.setFont(customFont);
        simulationSizeInputLabel.getStyleClass().add("label-style");

        VBox labelContainer = new VBox(50, fpsInputLabel, simulationSizeInputLabel);
        labelContainer.setAlignment(Pos.CENTER_LEFT);
        labelContainer.getStyleClass().add("vbox");

        VBox spinnerContainer = new VBox(20, fpsInput, simulationSizeInput);
        spinnerContainer.setAlignment(Pos.CENTER_RIGHT);
        spinnerContainer.getStyleClass().add("vbox");

        HBox inputContainer = new HBox(20, labelContainer, spinnerContainer);
        inputContainer.setAlignment(Pos.CENTER);

        VBox menuRoot = new VBox(50, canvas, buttonContainer, inputContainer);
        menuRoot.setAlignment(Pos.TOP_CENTER);
        menuRoot.getStyleClass().add("vbox");


        Scene menuScene = new Scene(menuRoot, appInitialWidth, appInitialHeight);
        menuScene.getStylesheets().add(StyleSheet);

        playButton.setOnAction(_ -> {
            Scene gameScene = new GameScene().getGameScene(
                    stage,
                    menuScene,
                    (double) fpsInput.getValue(),
                    simulationSizeInput.getValue(),
                    null);
            stage.setScene(gameScene); // Cambia la escena actual por gameScene
        });

        loadButton.setOnAction(_ -> {
            try {
                var Matrix = SaveManager.loadMatrix(stage);

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
                    img = successImage;
                }

                Scene gameScene = new GameScene().getGameScene(stage, menuScene, (double) fpsInput.getValue(), size, Matrix);

                Scene messageScene = new MessageScene().getScene(stage, gameScene, menuScene, message, btnLabel, img);
                stage.setScene(messageScene);


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        editorButton.setOnAction(_ -> {
            Scene editorScene = new EditorScene().getScene(stage, simulationSizeInput.getValue(), fpsInput.getValue(), menuScene);
            stage.setScene(editorScene);
        });

        return menuScene;
    }
}
