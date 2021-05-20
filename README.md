# Backgammon Project Description

### Outline

A backgammon game created in Java. The game has a graphical interface and can be played by two people using the same screen.   
Developed by Jordan Jass Olsson & Sam Shahriari as part of the course DD1349.

For instructions on how to play backgammon, check out https://www.bkgm.com/rules.html.

### Installation

Compile all files in `src/` using `javac *.java` and start the game with `java Game`.

### Information

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
