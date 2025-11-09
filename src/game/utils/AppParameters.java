package game.utils;

import game.gameScene.Cell;

public class AppParameters {
    private static final int width = 900;
    public static final int SIZE = 100;

    public static final double cellSize = (double) width/SIZE;
    public static final double realWidth = cellSize * SIZE; // Tamaño real del Canvas luego de calcular el tamaño de la célula

    public static Cell[][] cellMatrix;
    public static Cell[][] copyMatrix;

    public static final double NANOSECONDS = 1_000_000_000; // nanosegundos
    public static boolean gameLoopActive = false;

    public static final String title = "Conway's Game of Life";
}
