import java.util.Scanner;

public class connectFour {

	public static GameBoard gameboard;
	public static int boardWidth;
	public static int boardHeight;
	public static int winningLength;
	public static Scanner scan = new Scanner(System.in);

	public static int gameChoice(){
			System.out.println();
			System.out.println(						//START OF GAME
				"Connect-Four by Onur Bagoren, Tom Hogrefe, and Isaac Manly \n" +
				"Choose your game: \n" +
				"1. Tiny 3x3x3 Connect-Three \n" +
				"2. Wider 3x5x3 Connect-Three \n" +
				"3. Standard 6x7x4 Connect-Four");

			int y = scan.nextInt();				//CHOOSE GAME
			return y;
  	}

	public static int difficultyPrompt(){
		int y = Integer.MAX_VALUE;
		int count = 0;
		while(y > 5){
			if(count == 0){
				System.out.println("Select a difficulty value between 1 and 5:");
			}
			y = scan.nextInt();
			if(y > 5){
				System.out.println("Please select a value between 1 and 5:");
			}
			count++;
		}
		return y;
	}

	public static GameBoard startGame(int choice){
		if(choice == 1) {
			boardWidth = 3;
			boardHeight = 3;
			winningLength = 3;
		} else if(choice == 2) {
			boardWidth = 3;
			boardHeight = 5;
			winningLength = 3;
		}
		else if(choice == 3) {
			boardWidth = 6;
			boardHeight = 7;
			winningLength = 4;
		}
		return new GameBoard(boardWidth, boardHeight, winningLength);
	}

	public static int botPrompt(){
		int y = Integer.MAX_VALUE;
		int count = 0;
		while(y > 4){
			if(count == 0){
				System.out.println(
					"Select a game choice: \n" +
					"1. Player vs Random Choice, \n" +
					"2. Player vs. MINIMAX Bot, \n" +
					"3. Player vs. α-β Pruning MINIMAX \n" +
					"4. Player vs. H-MINIMAX IDA"
				);
			}
			y = scan.nextInt();
			if(y > 4){
				System.out.println("Please select a value between 1 and 4:");
			}
			count++;
		}
		return y;
	}

	public static void userPrompt( Player p ){
		System.out.print("Player " + p.playerNum + ", drop your piece: ");
	}

	public static void main(String args[]) {

		Player p1 = new Player(1);
		int gameChoice = gameChoice();
		gameboard = startGame(gameChoice);
		int botChoice = botPrompt();

		switch(botChoice){
			case 1:
				gameboard.printBoard();
				ConnectFourBot c4bot = new ConnectFourBot(2);
				System.out.println("Random Player Chosen \n");
				
				while(gameboard.isGameOver() == 0 ){

					int choice = c4bot.minimaxDecision(gameboard);
					gameboard.dropPiece(choice, c4bot.playerNum);
					gameboard.printBoard();
					System.out.println("Bot chooses: " + (choice+1));

					if(gameboard.isGameOver() == 0 ){

						userPrompt(p1);
						int input = scan.nextInt() - 1;
						gameboard.dropPiece(input, 1);
						System.out.println();
						gameboard.printBoard();

					}
					
				}
				
				System.out.println("Winner: " + gameboard.isGameOver());
				break;
			case 2:
				gameboard.printBoard();
				MinimaxBot bot = new MinimaxBot(2);
				
				while(gameboard.isGameOver() == 0){

					double startTime = System.currentTimeMillis();

					int choice = bot.minimaxDecision(gameboard);
					gameboard.dropPiece(choice, bot.playerNum);
					gameboard.printBoard();
					System.out.println("Bot chooses: " + (choice+1));

					double elapsedTime = (System.currentTimeMillis() - startTime)/1000.0;

					System.out.println("Elapsed time: " + elapsedTime + " seconds.");
					System.out.println("Number of states visited: " + bot.count);
					System.out.println();

					if(gameboard.isGameOver() == 0){
						userPrompt(p1);
						int input = scan.nextInt() - 1;
						gameboard.dropPiece(input, 1);
						System.out.println();
						gameboard.printBoard();
					}
				}
				System.out.println("Winner: " + gameboard.isGameOver());
				break;
			case 3:
				gameboard.printBoard();
				ABprune abbot = new ABprune(2);
				
				while(gameboard.isGameOver() == 0){

					double startTime = System.currentTimeMillis();

					int choice = abbot.minimaxDecision(gameboard);
					gameboard.dropPiece(choice, abbot.playerNum);
					gameboard.printBoard();
					System.out.println("Bot chooses: " + (choice+1));

					double elapsedTime = (System.currentTimeMillis() - startTime)/1000;

					System.out.println("Elapsed time: " + elapsedTime + " seconds.");
					System.out.println("Number of states visited: " + abbot.count);
					System.out.println();

					if(gameboard.isGameOver() == 0){
						userPrompt(p1);
						int input = scan.nextInt() - 1;
						gameboard.dropPiece(input, 1);
						System.out.println();
						gameboard.printBoard();
					}
				}
				System.out.println("Winner: " + gameboard.isGameOver());
				break;
			case 4:
				int diff = difficultyPrompt();
				HMinimax hbot = new HMinimax(diff, 2);
				gameboard.printBoard();
				
				while(gameboard.isGameOver() == 0){
					
					double startTime = System.currentTimeMillis();
					
					int choice = hbot.minimaxDecision(gameboard);
					gameboard.dropPiece(choice, hbot.playerNum);
					gameboard.printBoard();
					System.out.println("Bot chooses: " + (choice+1));

					double elapsedTime = (System.currentTimeMillis() - startTime)/1000;

					System.out.println("Elapsed time: " + elapsedTime + " seconds.");
					System.out.println("Number of states visited: " + hbot.count);
					System.out.println();

					if(gameboard.isGameOver() == 0){
						userPrompt(p1);
						int input = scan.nextInt() - 1;
						gameboard.dropPiece(input, 1);
						System.out.println();
						gameboard.printBoard();
					}
				}
				System.out.println("Winner: " + gameboard.isGameOver());
				break;
		}
	}

}
