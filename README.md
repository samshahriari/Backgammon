# Backgammon Project Description

### Outline

A backgammon game created in Java. The game has a graphical interface and can be played by two people using the same screen.   
Developed by Jordan Jass Olsson & Sam Shahriari as part of the course DD1349.

For instructions on how to play backgammon, check out https://www.bkgm.com/rules.html.

### Installation/Execution

To start the game, simply run Backgammon.jar.   
If this does not work, compile all files in `src/` using `javac *.java` and start the game with `java Game`.

### Information

The basic structure consists of the 6 classes `Game`, `Board`, `Pip`, `Player`, `Move`, and `GUI`.
The class `Game` manages higher-level logic and contains the main method that runs the game. `Game` uses the `Board`
class to create backgammon boards populated by checkers. `Board` uses the `Pip` class to create pips, and each pip
contains information about how many checkers of what color are on it. Multiple classes use the Enum `Player` to keep
track of who is currently playing. So, `Pip` is used in `Board` to create pips and set checkers, and `Board` is used
in `Game` to create and populate the playing field. Meanwhile, `Game` also uses the `Move` class to evaluate the ability
of checkers to move, the playability of turns, and ultimately, to move checkers from one pip to another. `Move` achieves
this through a chain of methods that successively evaluate movement in a greater and greater scope. Finally, `Game` uses
the `GUI` class to generate graphics and allow the game to be played in a window. `GUI` uses the GUI-framework Java Swing
to achieve this, and contains many sub-classes for various different panels used to draw the different parts of
the board, as well as to dynamically update the graphics according to the game-state. The project has been extracted to a
JAR-file to make it easier to start up. The game does not use any external libraries.

#### [Board](src/Board.java)

Class for creating and initializing a backgammon board. Contains information about pips and prints the board.

#### [Game](src/Game.java)

Class for playing backgammon games. Is the brain behind the game. Integrates all the other classes and logic.

#### [GUI](src/GUI.java)

Class for creating game graphics. Made using Swing.

#### [Move](src/Move.java)

Class that manages checker movement between pips on a backgammon board. Manages checker hits and blocks.

#### [Pip](src/Pip.java)

Class for board pips holding information about the color and number of checkers on a pip. Allows for adding/removing 
checkers from pips.

#### [Player](src/Player.java)

Enum that keeps track of the different players/checker colors.

### Important documents

- [Glossary](docs/Glossary.md)
