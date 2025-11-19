package game.scenes.informationScene;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import static game.utils.AppParameters.*;
import static game.utils.Assets.*;
import static game.utils.ColorPalette.backgroundColor;
import static game.utils.Components.getView;

public class InformationScene {
    public static Scene getScene(Stage stage, Scene parentScene){

        Label mainParagraph = new Label(
                """
                The Game of Life, also known as Conway's Game of Life or simply Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970. It is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves.
                """
        );
        mainParagraph.setFont(customFont);
        mainParagraph.getStyleClass().add("message");
        mainParagraph.setPrefWidth(appInitialWidth * 0.8);
        mainParagraph.setTextAlignment(TextAlignment.LEFT);


        Label rulesParagraph = new Label(
                """
                It consists of a regular grid made of cells. Each of those cells can be in one of two states: Dead or Alive. The complete set of cells is called a Generation. Each generation, the model determines the state of the cell for the next generation, by checking the state of all their Neighbors cells (8 in total, including the diagonals) and applying the following rules according to the number of alive neighbors and the current state of the cell:
               """
        );

        rulesParagraph.setFont(customFont);
        rulesParagraph.getStyleClass().add("message");
        rulesParagraph.setPrefWidth(appInitialWidth * 0.8);
        rulesParagraph.setTextAlignment(TextAlignment.LEFT);

        Label ruleList = new Label(
                """
                Survival: If the Cell is Alive, and has two or three Alive neighbors, it will be Alive in the next generation.
               \s
                Overpopulation: If the Cell is Alive but has more than three Alive neighbors, it will be Dead in the next generation.
               \s
                Isolation: If the Cell is Alive but has less than two Alive neighbors, it will be Dead in the next generation.
               \s
                Birth: If the Cell is Dead, but has exactly three neighbors Alive, it will be Alive in the next generation.
              """
        );

        ruleList.setFont(customFont);
        ruleList.getStyleClass().add("message");
        ruleList.setPrefWidth(appInitialWidth * 0.8);
        ruleList.setTextAlignment(TextAlignment.LEFT);

        HBox imageContainer = new HBox();
        imageContainer.getChildren().addAll(
                getView(patternOscillatoryImage, appInitialWidth/3.5, true),
                getView(patternStillImage, appInitialWidth/3.5, true),
                getView(patternSpaceshipImage, appInitialWidth/3.5, true)
        );
        imageContainer.setAlignment(Pos.CENTER);

        Label patternList = new Label(
                """
                Still life: The cell configuration allows them to remain in the current state indefinitely; none of the cells switch states in the next generation, as long as they are not forced by external cells.
                
                Oscillatory life: the cell structure switches between a handful of configurations in a cycle that repeats itself indefinitely, as long as they are not interrupted by external cells.
                
                Spaceships The cell structure is oscillatory, but the way it cycles through the different configurations allows it to move around the grid indefinitely, until it encounters another group of cells that interrupts the cycle, or the simulation border, where some of them will become still lives or oscillatory lives.
                """
        );
        patternList.setFont(customFont);
        patternList.getStyleClass().add("message");
        patternList.setPrefWidth(appInitialWidth * 0.8);
        patternList.setTextAlignment(TextAlignment.LEFT);

        Label information = new Label("Created by Miguel Angel Ortiz Loaiza");
        information.setFont(customFont);
        information.getStyleClass().add("message");
        information.setPrefWidth(appInitialWidth * 0.8);
        information.setTextAlignment(TextAlignment.LEFT);

        Button returnButton = new Button();
        returnButton.setGraphic(getView(btnReturnImage, buttonSize, true));
        returnButton.getStyleClass().add("btn-sprite");

        VBox root = new VBox(
                50,
                getView(bannerImage, appInitialWidth * 0.7, true),
                mainParagraph,
                rulesParagraph,
                ruleList,
                imageContainer,
                patternList,
                information,
                returnButton);

        root.setAlignment(Pos.CENTER);
        root.setStyle(
                """
                -fx-padding: 100 0 100 0;
                -fx-background-color: transparent;
                """
        );
        root.setPadding(Insets.EMPTY);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        returnButton.setOnAction(_ -> stage.setScene(parentScene));

        Scene scene = new Scene(scrollPane, appInitialWidth, appInitialHeight, backgroundColor);
        scene.getStylesheets().add(StyleSheet);

        Platform.runLater(() -> {
            scrollPane.layout();
            scrollPane.setVvalue(0.0); // 0 = parte superior
        });

        return scene;
    }
}
