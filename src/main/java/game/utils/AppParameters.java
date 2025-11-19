package game.utils;

import javafx.stage.Screen;

public class AppParameters {
    public static final double appInitialHeight = Screen.getPrimary().getVisualBounds().getHeight() *0.95;
    public static final double appInitialWidth = appInitialHeight- appInitialHeight * 0.1;

    public static Cell[][] cellMatrix;
    public static Cell[][] copyMatrix;

    public static final double NANOSECONDS = 1_000_000_000; // nanosegundos
    public static boolean gameLoopActive = false;

    public static final String title = "Conway's Game of Life";
    public static final double buttonSize = appInitialWidth/6;
}
