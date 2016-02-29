import java.util.Random;

/**
 * The MineField class produces a mine-field and some operations to check
 * whether a move results in stepping on a mine or how many mines are around the
 * move that has been done.
 * 
 * @author unknown
 * @version 1.1
 * @since 2016-02-29
 */

class MineField {

	private boolean[][] minefield, visiblefield;
	private boolean steppedOnMine;
	private final int rowMax = 5;
	private final int colMax = 10;

	/**
	 * The constructor of the class MineField has the responsibility of
	 * initializing the two-dimensional truth tables.
	 */
	MineField() {
		steppedOnMine = false;

		initiateBooleanTablesWithFalse();
		initiateMineField();
		
	}

	/**
	 * Method initiates two-dimensional boolean-matrices's cells to "false".
	 * 
	 * @param mineField
	 * @param visibleField
	 * @return nothing.
	 */
	private void initiateBooleanTablesWithFalse() {
		minefield = new boolean[rowMax][colMax];
		visiblefield = new boolean[rowMax][colMax];
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				minefield[row][col] = false;
				visiblefield[row][col] = false;
			}
		}
	}
	/**
	 * Method randomly places 15 mines on the "mineField".
	 * @return nothing.
	 */
	private void initiateMineField(){
		int mineCounter = 15;
		int randomRow, randomCol;
		Random RGenerator = new Random();

		while (mineCounter > 0) {

			randomRow = Math.abs(RGenerator.nextInt() % rowMax);
			randomCol = Math.abs(RGenerator.nextInt() % colMax);

			if (placeMineWhereNoMineExists(randomRow, randomCol)) {
				mineCounter--;
			}
		}
	}

	/**
	 * Method tries to place a mine in a randomly selected cell within the minefield table, and if there is a mine it returns false and
	 * if there's no mine it places a mine in that cell and returns true.
	 * 
	 * @param randomRow, row to try and place a mine
	 * @param randomCol, column to try and place a mine
	 * @return true if has placed a mine, false if not.
	 */
	private boolean placeMineWhereNoMineExists(int randomRow, int randomCol) {
		if (minefield[randomRow][randomCol]) {
			return false;
		} else {
			minefield[randomRow][randomCol] = true;
			return true;
		}
	}
	/**
	 * Method runs through the entire minefield table and shows every cell in the visiblefield where a mine exists. Method also
	 * alters the "steppedOnMine variable to true" and shows the visibleField table.
	 * @return nothing.
	 */
	private void displayAllMines() {
		for (int row = 0; row < rowMax; row++) {
			for (int col = 0; col < colMax; col++) {
				if (minefield[row][col]) {
					visiblefield[row][col] = true;
				}
			}
		}
		steppedOnMine = true;
		displayCurrentState();

	}

	private char drawChar(int row, int col) {
		int count = 0;
		if (visiblefield[row][col]) {
			if (minefield[row][col])
				return '*';
			for (int irow = row - 1; irow <= row + 1; irow++) {
				for (int icol = col - 1; icol <= col + 1; icol++) {
					if (icol >= 0 && icol < colMax && irow >= 0 && irow < rowMax) {
						if (minefield[irow][icol])
							count++;
					}
				}
			}
		} else {
			if (steppedOnMine) {
				return '-';
			}
			{

				return '?';
			}
		}
		switch (count) {
		case 0:
			return '0';
		case 1:
			return '1';
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		case 7:
			return '7';
		case 8:
			return '8';

		default:
			return 'X';
		}
	}

	public boolean getBoom() {

		return steppedOnMine;
	}

	public boolean legalMoveString(String input) {
		String[] separated = input.split(" ");
		int row;
		int col;
		try {

			row = Integer.parseInt(separated[0]);
			col = Integer.parseInt(separated[1]);
			if (row < 0 || col < 0 || row >= rowMax || col >= colMax) {
				throw new java.io.IOException();
			}
		} catch (Exception e) {
			System.out.println("\nInvalid Input!");
			return false;
		}

		if (legalMoveValue(row, col)) {
			return true;

		} else {
			return false;
		}
	}

	private boolean legalMoveValue(int row, int col) {

		if (visiblefield[row][col]) {
			System.out.println("You stepped in allready revealed area!");
			return false;
		} else {
			visiblefield[row][col] = true;
		}

		if (minefield[row][col]) {
			displayAllMines();
			return false;
		}

		return true;
	}

	public void displayCurrentState() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for (int row = 0; row < rowMax; row++) {
			System.out.print(row + " |");
			for (int col = 0; col < colMax; col++) {
				System.out.print(" " + drawChar(row, col));

			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}

}
