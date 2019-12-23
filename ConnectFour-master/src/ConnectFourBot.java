import java.util.concurrent.ThreadLocalRandom;


public class ConnectFourBot {

	//instance vars
	int playerNum;

	//constructor
	public ConnectFourBot(int playerNum) {
		this.playerNum = playerNum;
	}

	//methods
	public static int minimaxDecision(GameBoard gameBoard) {
		return ThreadLocalRandom.current().nextInt(0, gameBoard.numCols);

	}

}
