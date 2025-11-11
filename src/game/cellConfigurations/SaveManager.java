package game.cellConfigurations;

import game.utils.Cell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class SaveManager {
    public static void saveMatrix(Cell[][] matrix, Stage stage) throws IOException {
        var file = saveFile(stage);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(matrix);
            System.out.println("File saved correctly.");
        }
        catch (IOException e) {
            System.out.println("There was a problem when trying to save the position.");
            // e.printStackTrace();
        }
    }
    public static Cell[][] loadMatrix(Stage stage) throws IOException, ClassNotFoundException {
        var file = loadFile(stage);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("File loaded correctly.");
            return (Cell[][]) in.readObject();

        }
        catch (IOException | ClassNotFoundException  e) {
            System.out.println("That initial position doesn't exists or it's corrupted.");
            e.printStackTrace();
            return null;
        }
    }

    public static File loadFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Initial Positions", "*.dat")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // Process the selected file
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }

    public static File saveFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Initial Position");

        // Carpeta inicial (opcional)
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // Nombre sugerido
        fileChooser.setInitialFileName("initial_state.dat");

        // Filtros de extensión
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos binarios (*.dat)", "*.dat")
        );

        // Mostrar el diálogo
        return fileChooser.showSaveDialog(stage);
    }
}
