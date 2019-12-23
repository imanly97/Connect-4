import java.util.stream.*;

public class HMinimax {

	//insance variables
	static int difficulty;
	static int playerNum;
	static int count = 0;

	static int[] rowArray = {1,  1,  0, -1, -1, -1, 0, 1};                      //array to represent the directions in which the check occurs -x
	static int[] colArray = {0, -1, -1, -1,  0,  1, 1, 1};                      //array to represent the directions in which the check occurs -y

	//constructor
	public HMinimax(int difficulty, int playerNum) {
		this.difficulty = difficulty;
		this.playerNum = playerNum;
	}

	//methods

	/*minimaxDecision based on textbook P.166 */

	public static int minimaxDecision(GameBoard currentBoard) {                  //GameBoard is taken as a state
		int depth = 2 * difficulty;
		double max = -1;                                                           //set initial values
		int move = 0;
		double utility = -1;
		System.out.println("Just a moment.. I'm thinking..");
		for(int i = 0; i < currentBoard.numCols; i++) {                            //loop through columns/potential moves
			GameBoard testBoard = currentBoard.copy();                               //create shallow copy of the board in order to test each iteration on
			if ( testBoard.canDrop( i ) ) {                                          //if there is a spot for the bot to drop the piece
			utility = minVal( testBoard.dropPiece( i, playerNum ), depth );        //find the min val after dropping a piece to column i
				if ( utility > max ) {                                                 //if this minVal is larger than the max utility, remember its utility and move
					max = utility;
					move = i;
				}
			}
		}
		return move;
	}

	public static double maxVal(GameBoard board, int depth) {
		int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
		if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
			if ( gameOver == playerNum ) {                                   // if game winner is the computer
				return 1;                                                      // return max utility as 1
			} else {
				return 0;                                                      // return max utility as 0
			}
		}
		if (depth == 0) {
			return heuristic(board);
		}
		double max = -1;                                                      // initialize values to look for a new max
		int move = -1;
		double minVal = -1;

		for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru columns to find moves with high utility (bad for bot)
			GameBoard testBoard = board.copy();                              // create copy of board to test moves
			if ( testBoard.canDrop(i) ) {
				minVal = minVal( testBoard.dropPiece(i, playerNum), depth - 1 );
				if ( minVal > max ) {
					max = minVal;
					move = i;
				}
			}
		}
		count++;
		return max;
	}

	public static double minVal(GameBoard board, int depth) {
		int gameOver = board.isGameOver();                                 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
		if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
			if ( gameOver == playerNum ) {                                   // if game winner is the computer
				return 1;                                                      // return max utility as 1
			} else {
				return 0;                                                      // return max utility as 0
			}
		}                         				 																 // return the player that has won the game if game is over, 0 if the game is still proceeding TERMINAL TEST
		if (depth == 0) {
			return heuristic(board);
		}

		if ( gameOver != 0 ) {                                             // if the state of the board is over after a certain play
			if ( gameOver == playerNum ) {                                   // if game winner is the player
				return 1;                                                      // return min utility as 1
			} else {
				return 0;                                                      // return min utility as 0
			}
		}

		double min = 2;
		int move = -1;
		double maxVal = 2;

		for( int i = 0; i < board.numCols; i++ ) {                         // iterate thru the columns to find optimal move with smallest min utility value
			GameBoard testBoard = board.copy();                              // create copy of the board to test the moves (!!!!)
			if ( testBoard.canDrop(i) ) {                                    // if there is an eligible spot
				maxVal = maxVal( testBoard.dropPiece(i, 1), depth - 1 );          // find the max utility value for the bot to play the move
				if ( maxVal < min ) {                                          // if the max utility value found for the bot is less than the min utility value
					min = maxVal;                                                // make max utility value the minimum value
					move = i;                                                    // move at column i
				}
			}
		}
		count++;
		return min;
	}

	public static double heuristic(GameBoard board) {

		int[] bot = {0, 0, 0, 0, 0, 0, 0};
		int[] opponent = {0, 0, 0, 0, 0, 0, 0};

		int[] utilBot = {0, 0, 0};
		int[] utilOpp = {0, 0, 0};

		int util = 0;
		int length = 0;
		int player = 0;

	for(int i = 0; i < board.numCols; i++) { //iterate thru columns
		for (int j = 0; j < board.numRows; j++) { //iterate thru rows
			if (board.board[j][i] != 0) { 				// if an index is occupied...
				player = board.board[j][i];				// ...set player to the object there
				for (int k=0; k<8; k++) {					// look at adjacent spaces, then recursively search out from them
					util = getUtility(board, i, j, colArray[k], rowArray[k], player, board.winLength-1);    // returns 2-digit int
					if (util == 30) {               // 3 more pieces in a row
						if (player == playerNum) {
							utilBot[0]++;
						} else {
							utilOpp[0]++;
						}
					} else if (util == 21) {        // 2 pieces and a space
						if (player == playerNum) {
							utilBot[1]++;
						} else {
							utilOpp[1]++;
					}
					} else if (util == 12) {        // a piece and two spaces
						if (player == playerNum) {
							utilBot[2]++;
						} else {
							utilOpp[2]++;
						}
					}
				}
			}
		}
	}

	double output = ((300*utilBot[0]+2*utilBot[1]+1*utilBot[2]+1)/         //calculate value of state
					(300*utilOpp[0]+2*utilBot[1]+1*utilOpp[2]+1));

	output = output/2;

	return output;

	}

	private static int getLength(GameBoard gameBoard, int column, int row, int colStep, int rowStep, int player) {
		try {
			if (gameBoard.board[row + rowStep][column + colStep] == player) { //if the next object matches...
				return (1 + getLength(gameBoard, column + colStep, row + rowStep, colStep, rowStep, player));
			} else if (gameBoard.board[row + rowStep][column + colStep] == 0) {
				return 100;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
		return 0;
	}


	/* 
	0 if there is an opponent around the position
	11 if no space around 1
	21 if no space around 2
	30 if no space around 3
	*/

	private static int getUtility(GameBoard gameBoard, int column, int row, int colStep, int rowStep, int player, int length) {
		try {
			int current = gameBoard.board[column][row];
			if (length > 0) {
				if (current == player) {
					return 10 + getUtility(gameBoard, column + colStep, row + rowStep, colStep, rowStep, player, length-1);   //add 10 for same piece
				} else if (current == 0) {
					return 1 + getUtility(gameBoard, column + colStep, row + rowStep, colStep, rowStep, player, length-1);    //add 1 for space
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
}