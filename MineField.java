
/**
 * Represents a two dimensional mine field divided into cells. The field maintains the state of probing each cell.
 * 
 * Each cell can be specified using a row and a column pair.
 */

import java.util.Random;

class MineField {

	private byte[] cells;
	/** The pitch of the field is effectivelty amount of cells along its horizontal dimension. */
	private int pitch;

	/**
	 * Create mine field of specified size, randomly populated with specified
	 * amount of mines.
	 */
	MineField(int rows, int cols, int minesToLay) {
		this.pitch = cols;
		cells = new byte[rows * cols];
		layMines(minesToLay);
	}

	/**
	 * Add specified amount of mines to this mine field, randomly. The field may
	 * already have been mined earlier.
	 */
	private void layMines(int minesToLay) {
		Random rg = new Random();
		while(minesToLay > 0) {
			int i = rg.nextInt(cells.length);
			if((cells[i] & 2) == 0) {
				cells[i] |= 2;
				minesToLay--;
			}
		}
	}

	/**
	 * Evaluate whether specified row and column values designate a position
	 * that is valid (i.e. in range) of this field.
	 */
	public boolean validPosition(int row, int col) {
		return row >= 0 && col >= 0 && row < cells.length / pitch && col < pitch;
	}

	/**
	 * Evaluate whether the cell designated by specified row and column values,
	 * contains a mine.
	 */
	public boolean isMined(int row, int col) {
		return (cells[row * pitch + col] & 2) != 0;
	}

	/**
	 * Evaluate whether the cell designated by specified row and column values,
	 * has been probed, i.e. uncovered.
	 */
	public boolean isProbed(int row, int col) {
		return (cells[row * pitch + col] & 1) != 0;
	}

	/**
	 * Modify the state of a cell to reflect that it has been probed.
	 */
	public void probe(int row, int col) {
		cells[row * pitch + col] |= 1;
	}

	/**
	 * Return amount of cells along "vertical" axis on this field.
	 */
	public int rows() {
		return cells.length / pitch;
	}

	/**
	 * Return amount of cells along "horizontal" axis on this field.
	 */
	public int cols() {
		return pitch;
	}

	public int mines() {
		int mineCount = 0;
		for(int i = 0; i < cells.length; i++) {
			if((cells[i] & 2) != 0) {
				mineCount++;
			}
		}
		return mineCount;
	}
	
	/**
	 * Return amount of mines surrounding the cell at specified position.
	 */
	public int getMineCountAt(int row, int col) {
		int count = 0;
		for(int i = row - 1; i <= row + 1; i++) {
			for(int j = col - 1; j <= col + 1; j++) {
				if(validPosition(i, j) && (cells[i * pitch + j] & 2) != 0) {
					count++;
				}
			}
		}
		return count;
	}
}
