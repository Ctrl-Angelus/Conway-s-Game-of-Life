package game.scenes.messageScene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static game.utils.AppParameters.appInitialWidth;
import static game.utils.Assets.*;

public class MessageScene {
    public Scene getScene(Stage stage, Scene alternativeScene, String message, String btnText, Image img){

        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        if (img != null){
            canvas.setWidth(appInitialWidth);
            canvas.setHeight(img.getHeight());
            gc.drawImage(
                    img,
                    (canvas.getWidth() - img.getWidth()) / 2,
                    0,
                    img.getWidth(),
                    img.getHeight());
        }


        Label sceneMessage = new Label(message);
        sceneMessage.setFont(customFont);
        sceneMessage.getStyleClass().add("message");

        Button sceneButton = new Button(btnText);
        sceneButton.setFont(customFont);
        sceneButton.getStyleClass().add("btn");

        VBox componentContainer = new VBox(50, canvas, sceneMessage, sceneButton);
        componentContainer.setAlignment(Pos.CENTER);
        componentContainer.getStyleClass().add("vbox");

        Scene scene = new Scene(componentContainer, appInitialWidth, appInitialWidth);
        scene.getStylesheets().add(StyleSheet);

        sceneButton.setOnAction(_ -> stage.setScene(alternativeScene));

        return scene;
    }
}
