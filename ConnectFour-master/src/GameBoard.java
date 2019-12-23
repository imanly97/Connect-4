
public class GameBoard {

	//instance variables
	int numRows;
	int numCols;
	int winLength;
	int[][] board;

	int[] rowArray = {1,  1,  0, -1, -1, -1, 0, 1}; //array to represent the directions in which the check occurs -x
	int[] colArray = {0, -1, -1, -1,  0,  1, 1, 1}; //array to represent the directions in which the check occurs -y

	//constructor
	public GameBoard(int numRows, int numCols, int winLength) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.winLength = winLength;
		board = new int[numRows][numCols];
	}

	public GameBoard copy() {
		GameBoard copyBoard = new GameBoard(this.numRows, this.numCols, this.winLength);
		for(int i=0; i<copyBoard.numRows; i++) {
			for(int j=0; j<copyBoard.numCols; j++) {
				copyBoard.board[i][j] = this.board[i][j];
			}
		}
		return copyBoard;
	}

	public void printBoard() {
		System.out.println();
		System.out.print("  ");
		for(int i=0; i<numCols; i++) { //print indices to the top of the table
			System.out.print(i+1 + " ");
		}
		System.out.println();
		for(int i=0; i<this.numRows; i++) {
			System.out.print(i+1 + " "); //print numbers on the left
			for(int j=0; j<this.numCols; j++) {
				if (this.board[i][j] == 1) { //if player one, print x
					System.out.print("x ");
				} else if (this.board[i][j] == 2) { //if player two, print o
					System.out.print("o ");
				} else { //if no one has played to that spot, print a gap
					System.out.print("  ");
				}
			}
			System.out.println(i+1 + " "); //print numbers on the right
		}
		System.out.print("  ");
		for(int i=0; i<numCols; i++) { //print number on the bottom
			System.out.print(i+1 + " ");
		}
		System.out.println();
		System.out.println();
	}

	public boolean canDrop(int column) {		// check for open space in a given column
		if (!(board[0][column] == 0)) {
			return false;
		}

		if(column >= numCols || column < 0) {
			return false;
		}

		return true;
	}

	public GameBoard dropPiece(int column, int player) {		//drops piece into place
		try{
			if (!(board[0][column] == 0)) {
				return this;
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println("Game over. Tie game.");
		}
		if(column >= numCols || column < 0) {
			return this;
		}

		for(int i=0; i<numRows; i++) {
			if(!(board[i][column] == 0)) {
				board[i-1][column] = player;
				return this;
			}
		}

		board[numRows-1][column] = player;

		return this;

	}

	public int isGameOver() {
		int player = 0; //instantiating player number

		for(int i = 0; i < this.numCols; i++) { //iterate thru columns
			for (int j = 0; j < this.numRows; j++) { //iterate thru rows
				if (this.board[j][i] != 0) { 				// if an index is occupied...
					player = this.board[j][i];				// ...set player to the object there
					for (int k=0; k<8; k++) {					// look at adjacent spaces, then recursively search out from them
						if (isFour(this, i, j, colArray[k], rowArray[k], winLength - 1, player)) {
							return player;
						}
					}
				}
			}
		}

		return 0;
	}

	private boolean isFour(GameBoard gameBoard, int column, int row, int colStep, int rowStep, int winLength, int player) {
		try {
			if (gameBoard.board[row + rowStep][column + colStep] == player) { //if the next object matches...
				if (winLength == 1) {	//if you're at the length, return true
					return true;
				} else {			//else subtract 1 and check the next guy
					return isFour(gameBoard, column + colStep, row + rowStep, colStep, rowStep, winLength - 1, player);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return false;
	}

	public boolean boardIsFull(){
		for(int i = 0; i < this.board.length; i++){ //iterate through rows up down
			for(int j = 0; i < this.board[i].length; j++){ //iterate through columns left right

				try{
					if(this.board[i][j] == 0){
					return false;
					}
				}catch(IndexOutOfBoundsException e){
					return false;
				}
			}
		}
		return true;
	}
}
