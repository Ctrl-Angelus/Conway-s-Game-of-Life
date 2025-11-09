
<hr>
<img 
    width=855 
    style="image-rendering: pixelated; image-rendering: crisp-edges; display: block; margin: auto" 
    src="src/game/resources/sprites/banner.png"
    alt = "Game Banner"
>
<hr>


### **What's Conway's Game of Life?**
The Game of Life is a `Cellular Automata` (a mathematical and computational model that evolves over time by applying a basic set of rules).
It consists of a regular grid made of cells. Each of those cells can be in one of two states: Dead or Alive. 
The complete set of cells is called a `Generation`. 
Each generation, the model determines the state of the cell for the next generation, 
by checking the state of all their `Neighbors cells` (8 in total including the diagonals) and applying the following rules according to the 
number of alive neighbors and the current state of the cell:
<br>
- **Survival:** If the Cell is `Alive`, and has two or three `Alive` neighbors, it will be `Alive` in the next generation.
- **Overpopulation:** If the Cell is `Alive` but has more than three `Alive` neighbors, it will be `Dead` in the next generation.
- **Isolation:** If the Cell is `Alive` but has less than two `Alive` neighbors, it will be `Dead` in the next generation.
- **Birth:** If the Cell is `Dead`, but has exactly three neighbors `Alive`, it will be `Alive` in the next generation.

<hr>

### **TODO list for the Project**
- [X]  Implement the game rules.
- [X]  Create a menu scene.
- [ ]  Add inputs to customize the simulation parameters.
- [ ]  Add the basic patterns and information scene.
- [ ]  Add the initial position editor.
- [ ]  Allow users to save and open custom initial positions.

<hr>

## **(Coded using the JavaFX library)**
<hr>

### **JVM and project's configurations needed (Intellij IDEA):** 

``` java
--module-path "$JAVAFX_HOME$\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
```

`$JAVAFX_HOME$`: Environment variable that contains the path to the javafx home directory. 
It has to be added to the IDE environment variables.

``` java
JAVAFX_HOME=C:\Java\javafx-sdk-25.0.1
```



