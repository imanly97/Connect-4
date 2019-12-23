
public class MinimaxBot {

  //insance variables
  static int playerNum;
  static int count = 0;

  //constructor
  public MinimaxBot(int playerNum) {
    this.playerNum = playerNum;
	}

  //methods

    /*minimaxDecision based on textbook P.166 */

  public static int minimaxDecision(GameBoard currentBoard) {          //GameBoard is taken as a state

    if (willOpponentWin(currentBoard) != -1) {
      return willOpponentWin(currentBoard);
    }

    int max = Integer.MIN_VALUE;                                                      //set initial values
    int move = -1;
    int utility = -1;
    System.out.println("Just a moment.. I'm thinking..");
    for(int i = 0; i < currentBoard.numCols; i++) {                    //loop through columns/potential moves
      GameBoard testBoard = currentBoard.copy();                       //create shallow copy of the board in order to test each iteration on
      if ( testBoard.canDrop( i ) ) {                                  //if there is a spot for the bot to drop the piece
        utility = minVal( testBoard.dropPiece( i, playerNum ) );        //find the min val after dropping a piece to column i
        if ( utility > max ) {                                           //if this minVal is larger than the max utility, remember its utility and move
          max = utility;
          move = i;
        }
      }
    }
    return move;
  }

  public static int maxVal(GameBoard board) {
    int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
    if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
      if ( gameOver == playerNum ) {                                   // if game winner is the computer
        return 1;                                                      // return max utility as 1
      } else {
        //board.printBoard();
        return 0;                                                      // return max utility as 0
      }
    }

    int max = -1;                                                      // initialize values to look for a new max
    int move = -1;
    int minVal = -1;

    for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru columns to find moves with high utility (bad for bot)
      GameBoard testBoard = board.copy();                              // create copy of board to test moves
      if ( testBoard.canDrop(i) ) {
        minVal = minVal( testBoard.dropPiece(i, playerNum) );
        if ( minVal > max ) {
          max = minVal;
          move = i;
        }
      }
    }
    count++;
    return max;
  }

  public static int minVal(GameBoard board) {
    int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
    if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
      if ( gameOver == playerNum ) {                                   // if game winner is the player
        return 1;                                                      // return min utility as 1
      } else {
        //board.printBoard();
        return 0;                                                      // return min utility as 0
      }
    }

    int min = 2;
    int move = -1;
    int maxVal = 2;

    for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru the columns to find optimal move with smallest min utility value
      GameBoard testBoard = board.copy();                              // create copy of the board to test the moves (!!!!)
      if ( testBoard.canDrop(i) ) {                                    // if there is an eligible spot
        maxVal = maxVal( testBoard.dropPiece(i, 1) );          // find the max utility value for the bot to play the move
        if ( maxVal < min ) {                                          // if the max utility value found for the bot is less than the min utility value
          min = maxVal;                                                // make max utility value the minimum value
          move = i;                                                    // move at column i
        }
      }
    }
    count++;
    return min;
  }

  public static int willOpponentWin(GameBoard board) {
    int move = -1;

    for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru columns to find moves with high utility (bad for bot)
      GameBoard testBoard = board.copy();                              // create copy of board to test moves
      if ( testBoard.canDrop(i) ) {
        testBoard.dropPiece(i, 1);
        if (testBoard.isGameOver() != playerNum && testBoard.isGameOver() != 0) {
          move = i;
        }
      }
    }
    return move;
  }

}
