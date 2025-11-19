package game.cellConfigurations;

import game.utils.Cell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SaveManager {
    private static final Logger log = Logger.getLogger(SaveManager.class.getName());

    public static void saveMatrix(Cell[][] matrix, Stage stage) throws IOException {
        var file = saveFile(stage);

        try (var out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(matrix);
            System.out.println("File saved correctly.");
        }
        catch (IOException exception)
        {
            System.err.println("There was a problem when trying to save the position: ");
            log.log(Level.WARNING, exception.getMessage(), exception);
        }
    }
    public static Cell[][] loadMatrix(Stage stage) throws IOException, ClassNotFoundException {
        var file = loadFile(stage);

        try (var in = new ObjectInputStream(new FileInputStream(file)))
        {
            System.out.println("File loaded correctly.");
            return (Cell[][]) in.readObject();
        }
        catch (IOException | ClassNotFoundException exception)
        {
            System.out.println("Selected file: " + file.getAbsolutePath());
            System.err.println("That initial position doesn't exists or it's corrupted.");
            log.log(Level.WARNING, exception.getMessage(), exception);
            return null;
        }
    }

    public static File loadFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        fileChooser.setInitialDirectory(new File("C:/"));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Initial Positions", "*.dat")
        );

        return fileChooser.showOpenDialog(stage);
    }

    public static File saveFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Initial Position");
        fileChooser.setInitialDirectory(new File("C:/"));

        fileChooser.setInitialFileName("initial_state.dat"); // Nombre sugerido

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Binary files (*.dat)", "*.dat") // Filtros de extensión
        );

        return fileChooser.showSaveDialog(stage);
    }
}
