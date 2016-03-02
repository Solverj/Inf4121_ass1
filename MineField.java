import java.util.Arrays;
import java.util.Random;

/**
 * The MineField class produces a mine-field and some operations to check
 * whether a move results in stepping on a mine or how many mines are around the
 * move that has been done.
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
	 * @return nothing.
	 */
	private void initiateBooleanTablesWithFalse() {
		minefield = new boolean[rowMax][colMax];
		visiblefield = new boolean[rowMax][colMax];
		for (int row = 0; row < rowMax; row++) {
			Arrays.fill(minefield[row], false);
			Arrays.fill(visiblefield[row], false);
		}
	}

	/**
	 * Method randomly places 15 mines on the "mineField".
	 * @return nothing.
	 */
	private void initiateMineField() {
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
	 *
	 * @param randomRow,
	 *            row to try and place a mine.
	 * @param randomCol,
	 *            column to try and place a mine.
	 */
	private boolean placeMineWhereNoMineExists(int randomRow, int randomCol) {
		if(minefield[randomRow][randomCol]) return false;
		minefield[randomRow][randomCol] = true;
		return true;
	}

	/**
	 * Method displays all mines
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

	private boolean perifiralWithinBoundry(int perifiralColumn, int perifiralRow){
		return perifiralColumn >= 0 && perifiralColumn < colMax && perifiralRow >= 0 && perifiralRow < rowMax;
	}
	
	private int identifyPerifiralMines(int row, int col) {
		int mineCounter = 0;
		for (int perifiralRow = row - 1; perifiralRow <= row + 1; perifiralRow++) {
			for (int perifiralColumn = col - 1; perifiralColumn <= col + 1; perifiralColumn++) {
				if (perifiralWithinBoundry(perifiralColumn, perifiralRow)) {
					if (minefield[perifiralRow][perifiralColumn])
						mineCounter++;
				}
			}
		}
		return mineCounter;
	}
	private char assignCellValue(int row, int col) {
		int mineCounter = 0;
		if (visiblefield[row][col]) {
			if (minefield[row][col]) {
				return '*';
			}
			mineCounter = identifyPerifiralMines(row, col);		
		} else {
			return steppedOnMine ? '-' : '?';
		}
		return (mineCounter <= 8 && mineCounter >= 0) ? Character.forDigit(mineCounter, 10) : 'X';
	}


	private boolean isInputWithoutBounds(int row, int col){
		return (row < 0 || col < 0 || row >= rowMax || col >= colMax);
	}

	public boolean legalMoveString(String input) {
		String[] separated = input.split(" ");
		int row;
		int col;
		row = Integer.parseInt(separated[0]);
		col = Integer.parseInt(separated[1]);
		if (isInputWithoutBounds(row, col)) {
			System.out.println("\nInvalid Input!");
			return false;
		}
		if (legalMoveValue(row, col)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean getSteppedOnMine() {
		return steppedOnMine;
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
				System.out.print(" " + assignCellValue(row, col));
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}

}
