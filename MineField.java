import java.util.Arrays;
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

    private int[] gameField;
    private boolean[] visibleField;
    private boolean steppedOnMine;
    private final int rowMax = 5;
    private final int colMax = 10;
    private final int maxIndex = rowMax * colMax;
    private final int bomb = -2;

    private enum Direction {
	moveUP, moveDOWN, moveLEFT, moveRIGHT;
    }

    /**
     * The constructor of the class MineField has the responsibility of
     * initializing the two-dimensional truth tables.
     */
    MineField() {
	steppedOnMine = false;
	initiateField();
	initiateMineField();
    }

    /**
     * Method initiates two-dimensional boolean-matrices's cells to "false".
     * 
     * @return nothing.
     */
    private void initiateField() {
	gameField = new int[maxIndex];
	visibleField = new boolean[maxIndex];
	// minefield = new boolean[rowMax][colMax];
	// visiblefield = new boolean[rowMax][colMax];
	Arrays.fill(gameField, 0);
	Arrays.fill(visibleField, false);
	// for (int row = 0; row < rowMax; row++) {
	// Arrays.fill(minefield[row], false);
	// Arrays.fill(visiblefield[row], false);
	// }
    }

    private int rowAndColToIndex(int row, int col) {
	return ((row * 10) + col);
    }

    /**
     * Method randomly places 15 mines on the "mineField".
     * 
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

    private void increasePerifiralCellsByOne(int index) {
	int up = index / 10;
	int down = index * 10;
	int left = index - 1;
	int right = index + 1;
	if (up > (colMax - 1))
	    gameField[up]++;
	if (down < (maxIndex - colMax))
	    gameField[down]++;
	if (index % 10 != 0)
	    gameField[left]++;
	if (index % 10 != (colMax - 1))
	    gameField[right]++;
    }

    /**
     *
     * @param randomRow
     *            , row to try and place a mine.
     * @param randomCol
     *            , column to try and place a mine.
     */
    private boolean placeMineWhereNoMineExists(int randomRow, int randomCol) {
	int cell = rowAndColToIndex(randomRow, randomCol);
	if (gameField[cell] != bomb)
	    return false;
	gameField[cell] = bomb;
	increasePerifiralCellsByOne(cell);
	return true;
    }

    /**
     * Method displays all mines
     * 
     * @return nothing.
     */
    private void displayAllMines() {
	steppedOnMine = true;
	displayCurrentState();
    }

    private boolean isIndexWithinBounds(int index) {
	return (index >= 0 && index < maxIndex);
    }

    public boolean legalMoveString(String input) {
	String[] separated = input.split(" ");
	int row = Integer.parseInt(separated[0]);
	int col = Integer.parseInt(separated[1]);
	int index = rowAndColToIndex(row, col);
	if (isIndexWithinBounds(index)) {
	    System.out.println("\nInvalid Input!");
	    return false;
	}
	if (legalMoveValue(index)) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean getSteppedOnMine() {
	return steppedOnMine;
    }

    private boolean legalMoveValue(int index) {
	if (visibleField[index]) {
	    System.out.println("You stepped in allready revealed area!");
	    return false;
	}
	visibleField[index] = true;

	if (gameField[index] == bomb) {
	    displayAllMines();
	    return false;
	}
	return true;
    }

    public void displayCurrentState() {
	int index;
	System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
	System.out.println("   ---------------------");
	for (int row = 0; row < rowMax; row++) {
	    System.out.print(row + " |");
	    for (int col = 0; col < colMax; col++) {
		index = rowAndColToIndex(row, col);
		if(visibleField[index])
		System.out.print(" " + gameField[index]);
	    }
	    System.out.println(" |");
	}
	System.out.println("   ---------------------");
    }

}
