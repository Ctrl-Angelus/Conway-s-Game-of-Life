package game.menuScene;

import game.gameScene.GameScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static game.utils.AppParameters.realWidth;
import static game.utils.Assets.*;

public class MenuScene {
    public Scene getMenuScene(Stage stage){
        Canvas canvas = new Canvas(realWidth, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        gc.drawImage(bannerImage, 10, 15, realWidth, 270);

        Button playButton = new Button("Start");
        playButton.setFont(customFont);
        playButton.getStyleClass().add("btn");

        Spinner<Integer> speedSpinner = new Spinner<>();
        speedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 60, 10)); // min, max, initial
        speedSpinner.setEditable(true); // permitir escribir directamente
        speedSpinner.setPrefWidth(100);
        speedSpinner.getEditor().setFont(customFont);
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) speedSpinner.getValueFactory();
        factory.setAmountToStepBy(5);


        VBox menuRoot = new VBox(20, canvas, playButton, speedSpinner);
        menuRoot.setAlignment(Pos.TOP_CENTER);
        menuRoot.getStyleClass().add("vbox");


        Scene menuScene = new Scene(menuRoot, realWidth, realWidth + 80);

        menuScene.getStylesheets().add(StyleSheet);

        playButton.setOnAction(_ -> {
            Scene gameScene = new GameScene().getGameScene(stage, menuScene, (double) speedSpinner.getValue());
            gameScene.getStylesheets().add(StyleSheet);
            stage.setScene(gameScene); // Cambia la escena actual por gameScene
        });

        return menuScene;
    }
}
