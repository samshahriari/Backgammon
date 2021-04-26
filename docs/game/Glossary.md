## Terminology

Each `board` contains 4 `quadrants`, each of which contains six `points`. There are a total of 24 points which alternate   
in colour and are occupied by `checkers`. There are `red` checkers and `white` checkers.

* `Board` - the entire board on which the game is played
* `Quadrant` - one of four areas of the board in which checkers must navigate
    * `Red home quadrant` - the quadrant to which red aims to bring his checkers
    * `White home quadrant` - the quadrant to which white aims to bring his checkers
    * `Red outer quadrant` - the quadrant adjacent to red home quadrant
    * `White outer quadrant` - the quadrant adjacent to white home quadrant
* `Pip` - one of the 24 narrow triangles on which the checkers move (aka points)
    * `Occupied` - a pip with a checker on it
        * `Stacked` - a pip with 2 or more checkers on it
    * `Unoccupied` - a pip without any checkers on it
* `Bar` - the raised strip that divides the board and holds hit checkers
* `Checker` - one of 30 pieces used as men in the game (15/player)
    * `White` - checkers belonging to white player
    * `Red` - checkers belonging to red players
* `Hit` - to send an opponents checker to the bar when it is landed on while alone
* `Die` - one of two six-faced dice used to determine movement
* `Doubles` - getting the same number on both dice: affords twice the number of moves that turn
* `Moves` - the number of moves a player has in his turn (default: 2, doubles: 4)
* `Bear in` - to navigate checkers to home board
* `Bear off` - to move checkers off the board
* `Enter` - to move checker from bar back onto the board
* `Player` - one of two players playing the game
    * `White` - player 1
    * `Red` - player 2
* `Roll` - to roll the dice
* `Stack` - a stack of checkers (2 or more)
* `Lone` - a lone checker that can be hit
* `Turn` - each players' turn