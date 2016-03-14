import java.util.Scanner;

/**
 * The Minesweeper class has responsibility of running the game, asking for
 * player for a move while checking move against minefield for rules. And when a
 * game is done it will use the ranking class inorder to put the player in it or
 * not.
 * 
 * @author unknown
 * @version 1.1
 * @since 2016-02-29
 */
public class Minesweeper {

	private static MineField field;
	private static Ranking rank;
	private static Scanner in;

	public static void main(String[] args) {
		initiateGame();
	}
	private static void initiateGame(){
		rank = new Ranking();
		mainMessage();
		loopGameUntilExit();
	}

	private static void loopGameUntilExit() {
		boolean exit = false;
		String input;
		field = new MineField();
		int result = 0;		
		while (!exit) {
			field.displayCurrentState();
			System.out.print("\nPlease enter your move(row col): ");
			in = new Scanner(System.in);
			input = in.nextLine();

			if (input.equals("top")) {
				rank.displayHighScore();
			}
			else if (input.equals("restart")) {
				field = restart(result);
			}
			else if (input.equals("exit")) {
				rank.recordName(result);
				System.out.println("\nThank you for playing :) Have a nice day!");
				exit = true;
			}
			else if (field.legalMoveString(input)) {
				result++;
				if (result == 35) {
					System.out.println("Congratulations you WON the game!");
					field = restart(result);
				}
			} else if (field.getSteppedOnMine()) {
				System.out.println(
						"\nBooooooooooooooooooooooooooooom!You stepped on a mine!You survived " + result + " turns");
				field = restart(result);
			}

		}

	}
	private static MineField restart(int result){
		rank.recordName(result);
		return new MineField();
	}

	private static void mainMessage() {
		System.out.println("Welcome to Minesweeper!");
		System.out.println("To play just input some coordinates and try not to step ont mine :)");
		System.out.println("Usefull commands:");
		System.out.println("restart- Starts a new game.");
		System.out.println("exit- Quits the game.");
		System.out.println("top- Reveals the top scoreboard.");
		System.out.println("Have Fun !");
	}
}
