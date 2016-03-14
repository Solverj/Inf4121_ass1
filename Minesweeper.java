
/**
 * Represents a Minesweeper game. The game tracks a mine field allowing the user to probe the field at various locations, per game rules. No marking of mines is possible here.  
 */

import java.util.Scanner;

public class Minesweeper {

	private static final Scanner in = new Scanner(System.in);
	
	private MineField field;
	private Ranking ranking = new Ranking();
	private int turns;

	public static void main(String[] args) {
		Minesweeper game = new Minesweeper(5, 10, 15);
		game.start();
	}

	Minesweeper(int rows, int cols, int mines) {
		reset(rows, cols, mines);
	}

	private void start()
	{
		System.out.print(
				  "Welcome to Minesweeper!\n"
				+ "You are given a field where mines have been laid. With every turn you are asked to probe a single arbitrary cell of this field. Probing a cell with a mine trips it ending your game. Uncovering any other cell tells you how many mines are in a 1-cell vicinity."
				+ "Useful commands:"
				+ "restart -- Starts a new game."
				+ "exit -- Quits the game."
				+ "top -- Reveals the top scoreboard.");
		printMineField(field);
		while(handleInput(in.nextLine()));
		System.out.println("Thank you for playing :) Have a nice day!");
	}
	
	private void reset(int rows, int cols, int mines)
	{
		field = new MineField(rows, cols, mines);
		turns = 0;
	}

	private boolean handleInput(String input) {
		System.out.print("Enter command or row and column (separated by whitespace): ");
		switch(input) {
			case "top":
				ranking.show();
				break;
			case "restart":
				reset(field.rows(), field.cols(), field.mines());
				break;
			case "exit":
				ranking.recordScore(turns);
				return false;
			default:
				return handleProbeCmd(input);
		}
		return true;
	}

	private int[] parsePosition(String s) {
		String[] pairs = s.split(" ");
		if(pairs.length != 2) {
			return null;
		}
		try {
			return new int[] { Integer.parseInt(pairs[0]), Integer.parseInt(pairs[1]) };
		} catch(NumberFormatException e) {
			return null;
		}
	}

	private boolean handleProbeCmd(String s) {
		int[] pos = parsePosition(s);
		if(pos == null) {
			System.out.println("Invalid input -- ignoring.");
			return true;
		}
		final int row = pos[0], col = pos[1];
		if(!field.validPosition(row, col)) {
			System.out.println("Not probing -- specified row or column out of range.");
			return true;
		}
		if(field.isProbed(row, col)) {
			System.out.println("Ignoring -- already probed, you won't see anything new there.");
			return true;
		}
		field.probe(row, col);
		printMineField(field);
		if(field.isMined(row, col)) {
			System.out.println("Your probing has tripped a mine. Game over, man. You survived " + turns + " turns.");
			return false;
		}
		turns++;
		return true;
	}

	public void printMineField(MineField field) {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row = 0; row < field.rows(); row++) {
			System.out.print(row + " |");
			for(int col = 0; col < field.cols(); col++) {
				System.out.print(" " + fieldChar(field, row, col));
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}

	private char fieldChar(MineField field, int row, int col) {
		return field.isProbed(row, col)
				? (field.isMined(row, col) ? '*' : Character.forDigit(field.getMineCountAt(row, col), 10)) : '?';
	}
}
