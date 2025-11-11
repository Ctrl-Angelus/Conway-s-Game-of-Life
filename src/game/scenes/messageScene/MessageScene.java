package game.scenes.messageScene;

import game.utils.Components;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static game.utils.AppParameters.appInitialHeight;
import static game.utils.AppParameters.appInitialWidth;
import static game.utils.Assets.*;


public class MessageScene {
    public Scene getScene(Stage stage, Scene alternativeScene, Scene parentScene, String message, Image img){

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

        Button sceneButton = new Button();
        sceneButton.setGraphic(Components.getView(btnContinueImage, 150, true));
        sceneButton.getStyleClass().add("btn-sprite");

        Button returnButton = new Button();
        returnButton.setGraphic(Components.getView(btnReturnImage, 150, true));
        returnButton.getStyleClass().add("btn-sprite");

        HBox buttonContainer = new HBox(50, returnButton, sceneButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox componentContainer = new VBox(50, canvas, sceneMessage, buttonContainer);
        componentContainer.setAlignment(Pos.CENTER);
        componentContainer.getStyleClass().add("vbox");

        Scene scene = new Scene(componentContainer, appInitialWidth, appInitialHeight);
        scene.getStylesheets().add(StyleSheet);

        sceneButton.setOnAction(_ -> stage.setScene(alternativeScene));

        returnButton.setOnAction(_ -> stage.setScene(parentScene));

        return scene;
    }
}
