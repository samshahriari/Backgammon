# Backgammon Project Description

### Future changes

- The main method in [Game](src/Game.java) will be split up to different methods.
- The game is now played in the terminal but will in the future be a seperate app with a GUI.

### Outline

A backgammon game created in Java. The game has a GUI and can be played by two people using the same screen.   
Developed by Jordan Jass Olsson & Sam Shahriari as part of the course DD1349.

### Installation

[Work in progress] Compile all files in `src/` using `javac *.java` and start the game with `java Game`.

### Information

#### [Board](src/Board.java)

Class for creating and initializing a backgammon board. Contains information about pips and prints the board.

#### [Color](src/Color.java)

Enum that keeps track of the different players/checker colors.

#### [Game](src/Game.java)

Class for playing backgammon games.

#### [Move](src/Move.java)

Class that manages checker movement between pips on a backgammon board. Manages checker hits and blocks.

#### [Pip](src/Pip.java)

Class for board pips holding information about the color and number of checkers on a pip. Allows for adding/removing 
checkers from pips.


[Work in progress: more info on class architecture, methods, libraries, etc. later]
