
public class ABprune {

  //insance variables
  static int playerNum;
	public static int count = 0;

  //constructor
  public ABprune(int playerNum) {
    this.playerNum = playerNum;
	}

  //methods

    /*minimaxDecision based on textbook P.166 */


  public static int switchPlayer(int a){
    if(a == 1){
      return 2;
    }
    return 1;
  }

  public static int minimaxDecision(GameBoard currentBoard) {          //GameBoard is taken as a state

    if (willOpponentWin(currentBoard) != -1) {
      return willOpponentWin(currentBoard);
    }

    int max = Integer.MIN_VALUE;                                                      //set initial values
    int move = -1;
    int utility = 0;

    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;

    System.out.println("Just a moment.. I'm thinking..");

    for(int i = 0; i < currentBoard.numCols; i++){
      GameBoard shallow = currentBoard.copy();
      if( shallow.canDrop( i ) ){ //for available states
        utility = maxVal(shallow.dropPiece( i, playerNum ), alpha, beta);
          if(utility > max){
            max = utility;
            move = i;
          }
      }
    }

    return move;
  }

  public static int maxVal(GameBoard board, int alpha, int beta) {
    int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
    if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
      if ( gameOver == playerNum ) {                                   // if game winner is the computer
        return 1;                                                      // return max utility as 1
      } else {
        //board.printBoard();
        return 0;                                                      // return max utility as 0
      }
    }

    int u = Integer.MIN_VALUE;

    for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru columns to find moves with high utility (bad for bot)
      GameBoard testBoard = board.copy();                              // create copy of board to test moves
      if ( testBoard.canDrop(i) ) {
        u = Math.max(u, minVal(testBoard.dropPiece(i, switchPlayer(playerNum)), alpha, beta));
        if ( u >= beta ) {
          return u;
        }
        alpha = Math.max(alpha, u);
      }
    }
    count++;
    return u;
  }

  public static int minVal(GameBoard board, int alpha, int beta) {
    int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
    if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
      if ( gameOver == playerNum ) {                                   // if game winner is the player
        return 1;                                                      // return min utility as 1
      } else {
        //board.printBoard();
        return 0;                                                      // return min utility as 0
      }
    }

    int u = Integer.MAX_VALUE;

    for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru columns to find moves with high utility (bad for bot)
      GameBoard testBoard = board.copy();                              // create copy of board to test moves
      if ( testBoard.canDrop(i) ) {
        u = Math.min(u, maxVal(testBoard.dropPiece(i, playerNum), alpha, beta));
        if ( u <= alpha ) {
          return u;
        }
        beta = Math.min(beta, u);
      }
    }
    count++;
    return u;
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
