package game.utils;


public class AppParameters {
    public static final int appInitialWidth = 900;
    public static final int appInitialHeight = 980;

    public static Cell[][] cellMatrix;
    public static Cell[][] copyMatrix;

    public static final double NANOSECONDS = 1_000_000_000; // nanosegundos
    public static boolean gameLoopActive = false;

    public static final String title = "Conway's Game of Life";
}
