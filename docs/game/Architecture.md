## Backgammon decomposition

#### Features

* Checker class
    * Two colours: white and red
    * One-way movement
    * 

* Board class
    * 4 areas total
    * 2 home areas + 2 outer areas
    * 2 goals
    * 6 positions per area

* Position class      
    * Colour of checkers
    * Number of checkers
    * boolean isAlone

* Player class
    * Checker colour

* Dice class
    * 2 dice
    * Indicates movement distance
    * Doubles give additional movement
    
* Game class
    * Boolean bearingOffStatus
    * Scoreboard
    * Creates instances of board, players, etc.
    * Disallow movement onto enemy chips that are not alone
    * Disallow movement outside of the board (i.e. past the last position before the goal)