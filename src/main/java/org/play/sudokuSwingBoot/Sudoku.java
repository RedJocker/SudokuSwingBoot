package org.play.sudokuSwingBoot;

public class Sudoku {

	public static int GRID_SIDE_SIZE = 9;
	public static int SQUARE_SIDE_SIZE = 3;
	public static int GRID_NUM_CELLS = 81;
	public static int GRID_NUM_SQUARES = 9;
	public static int SQUARE_NUM_CELLS = 9;

	public static int cellId(int row, int col) {
		return (row * GRID_SIDE_SIZE) + col;
	}
}
