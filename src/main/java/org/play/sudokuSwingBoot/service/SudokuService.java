package org.play.sudokuSwingBoot.service;

import static org.play.sudokuSwingBoot.Sudoku.GRID_SIDE_SIZE;
import static org.play.sudokuSwingBoot.Sudoku.SQUARE_SIDE_SIZE;

import java.util.HashSet;

import org.play.sudokuSwingBoot.Sudoku;
import org.play.sudokuSwingBoot.model.CellModel;
import org.play.sudokuSwingBoot.model.SudokuBoardState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class SudokuService {

	private void checkCell(
		int row,
		int col,
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete,
		int[] numbers
	) {
		
		int cellId = Sudoku.cellId(row, col);
		CellModel cell = cells[cellId];
		if (cell.getValue() == 0) {
			isComplete[0] = false;
		} else if (numbers[cell.getValue()] == 0) {
			numbers[cell.getValue()] = cellId + 1; //1-index
			//to avoid 0 default value
		} else {
			invalidCells.add(cellId);
			invalidCells.add(numbers[cell.getValue()] - 1);
		}
	}
	
	private void checkRow(
		int row,
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		int[] numbers = new int[10];
		
		for (int col = 0; col < GRID_SIDE_SIZE; col++) {
			checkCell(row, col,
				cells, invalidCells, isComplete, numbers);
		}
	}
	
	private void checkSameRow(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		for (int row = 0; row < GRID_SIDE_SIZE; row++) {
			checkRow(row, cells, invalidCells, isComplete);
		}
	}

	private void checkCol(
		int col,
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		int[] numbers = new int[10];

		for (int row = 0; row < GRID_SIDE_SIZE; row++) {
			checkCell(row, col,
				cells, invalidCells, isComplete, numbers);
		}
	}

	private void checkSameCol(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		for (int col = 0; col < GRID_SIDE_SIZE; col++) {
			checkCol(col, cells, invalidCells, isComplete);
		}
	}
	
	private void checkSquare(
		int row,
		int col,
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		int[] numbers = new int[10];

		for (int rowSquare = row;
			 rowSquare < (row + SQUARE_SIDE_SIZE);
			 rowSquare++) {
			for (int colSquare = col;
				 colSquare < (col + SQUARE_SIDE_SIZE);
				 colSquare++) {
				checkCell(rowSquare, colSquare,
					cells, invalidCells, isComplete, numbers);
			}
		}
	}

	private void checkSameSquare(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete 
	) {
		for (int row = 0;
			 row < GRID_SIDE_SIZE;
			 row += SQUARE_SIDE_SIZE) {

			for (int col = 0;
				 col < GRID_SIDE_SIZE;
				 col += SQUARE_SIDE_SIZE) {
				checkSquare(row, col, cells,
					invalidCells, isComplete);
			}
		}
	}
	
	public SudokuBoardState boardState(CellModel[] cells) {
		HashSet<Integer> invalidCells = new HashSet<>();
		boolean[] isComplete = { true };
		checkSameRow(cells, invalidCells, isComplete);
		checkSameCol(cells, invalidCells, isComplete);
		checkSameSquare(cells, invalidCells, isComplete);
		isComplete[0] = isComplete[0] && invalidCells.isEmpty();
		return new SudokuBoardState(isComplete[0], invalidCells);
	}
}
