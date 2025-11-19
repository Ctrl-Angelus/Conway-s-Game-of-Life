package game.scenes.menuScene;

import game.cellConfigurations.SaveManager;
import game.scenes.editorEscene.EditorScene;
import game.scenes.gameScene.GameScene;
import game.scenes.informationScene.InformationScene;
import game.scenes.messageScene.MessageScene;
import game.utils.ColorPalette;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static game.utils.AppParameters.*;
import static game.utils.Assets.*;
import static game.utils.Components.*;

public class MenuScene {
    public Scene getMenuScene(Stage stage){
        Canvas canvas = new Canvas(appInitialWidth, appInitialHeight /3);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        drawImage(canvas, gc, bannerImage);

        Button playButton = new Button();
        playButton.setGraphic(getView(btnStartImage, buttonSize, true));
        playButton.getStyleClass().add("btn-sprite");

        Button loadButton = new Button();
        loadButton.setGraphic(getView(btnLoadImage, buttonSize, true));
        loadButton.getStyleClass().add("btn-sprite");

        Button editorButton = new Button();
        editorButton.setGraphic(getView(btnEditorImage, buttonSize, true));
        editorButton.getStyleClass().add("btn-sprite");

        HBox buttonContainer = new HBox(20, playButton, loadButton, editorButton);
        buttonContainer.setAlignment(Pos.CENTER);

        Spinner<Integer> fpsInput = new Spinner<>();
        setIntegerSpinnerConfig(fpsInput, customFont, buttonSize, 10, 60, 10, 5);

        Spinner<Integer> simulationSizeInput = new Spinner<>();
        setIntegerSpinnerConfig(simulationSizeInput, customFont, buttonSize, 10, 100, 80, 5);

        Label fpsInputLabel = new Label("Simulation's FPS:");
        fpsInputLabel.setLabelFor(fpsInputLabel);
        fpsInputLabel.setFont(customFont);
        fpsInputLabel.getStyleClass().add("label-style");

        Label simulationSizeInputLabel = new Label("Simulation's Grid Size:");
        simulationSizeInputLabel.setLabelFor(simulationSizeInput);
        simulationSizeInputLabel.setFont(customFont);
        simulationSizeInputLabel.getStyleClass().add("label-style");

        VBox labelContainer = new VBox(30, fpsInputLabel, simulationSizeInputLabel);
        labelContainer.setAlignment(Pos.CENTER_LEFT);
        labelContainer.getStyleClass().add("vbox");

        VBox spinnerContainer = new VBox(10, fpsInput, simulationSizeInput);
        spinnerContainer.setAlignment(Pos.CENTER_RIGHT);
        spinnerContainer.getStyleClass().add("vbox");

        HBox inputContainer = new HBox(20, labelContainer, spinnerContainer);
        inputContainer.setAlignment(Pos.CENTER);

        Button informationButton = new Button();
        informationButton.setGraphic(getView(btnInformationImage, buttonSize/3, true));
        informationButton.getStyleClass().add("btn-sprite");
        informationButton.setLayoutX(20);
        informationButton.setLayoutY(20);

        VBox menuRoot = new VBox(10, canvas, buttonContainer, inputContainer);
        menuRoot.getStyleClass().add("vbox");



        Scene menuScene = new Scene(new Group(menuRoot, informationButton), appInitialWidth, appInitialHeight, ColorPalette.backgroundColor);
        menuScene.getStylesheets().add(StyleSheet);

        playButton.setOnAction(_ -> {
            Scene gameScene = new GameScene().getGameScene(
                    stage,
                    menuScene,
                    (double) fpsInput.getValue(),
                    simulationSizeInput.getValue() + 2,
                    null);
            stage.setScene(gameScene); // Cambia la escena actual por gameScene
        });

        loadButton.setOnAction(_ -> {
            try {
                var Matrix = SaveManager.loadMatrix(stage);

                int size;
                String message;
                Image img;

                if(Matrix == null){
                    size = simulationSizeInput.getValue() + 2;
                    message = "The position you're trying to access is either corrupted or hasn't been created yet. " +
                            "You can start the simulation with a randomized position instead.";
                    img = notFoundImage;
                } else {
                    size = Matrix.length;
                    message = String.format("The %d x %d position has been correctly loaded.", size - 2, size - 2);
                    img = successImage;
                }

                Scene gameScene = new GameScene().getGameScene(stage, menuScene, (double) fpsInput.getValue(), size, Matrix);

                Scene messageScene = new MessageScene().getScene(stage, gameScene, menuScene, message, img);
                stage.setScene(messageScene);


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        editorButton.setOnAction(_ -> {
            Scene editorScene = new EditorScene().getScene(stage, simulationSizeInput.getValue() + 2, fpsInput.getValue(), menuScene);
            stage.setScene(editorScene);
        });

        informationButton.setOnAction(_ -> {
            Scene informationScene = InformationScene.getScene(stage, menuScene);
            stage.setScene(informationScene);
        });

        return menuScene;
    }
}
