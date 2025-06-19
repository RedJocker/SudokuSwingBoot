package org.play.sudokuSwingBoot.gui;

import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.JPanel;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.play.sudokuSwingBoot.Sudoku.GRID_SIDE_SIZE;
import static org.play.sudokuSwingBoot.Sudoku.SQUARE_SIDE_SIZE;
import static org.play.sudokuSwingBoot.Sudoku.GRID_NUM_SQUARES;

@Component
@Scope("singleton")
public class SudokuGrid extends JPanel {
	
	private SudokuSquare[] squares;
	
	public SudokuGrid(
		SudokuSquare square0,
		SudokuSquare square1,
		SudokuSquare square2,
		SudokuSquare square3,
		SudokuSquare square4,
		SudokuSquare square5,
		SudokuSquare square6,
		SudokuSquare square7,
		SudokuSquare square8
	) {
		GridLayout layout =
			new GridLayout(SQUARE_SIDE_SIZE, SQUARE_SIDE_SIZE);
		this.setLayout(layout);
		
		this.squares = new SudokuSquare[]{
			square0,
			square1,
			square2,
			square3,
			square4,
			square5,
			square6,
			square7,
			square8
		};
		
		for (int i = 0; i < GRID_NUM_SQUARES; i++) {
			SudokuSquare square = this.squares[i];
			square.setSquareId(i);
			this.add(square);
		}
	}

	public void setOnClickCell(Consumer<Integer> onClickCell) {
		for (SudokuSquare square: this.squares) {
			square.setOnClickCell(onClickCell);
		}
	}

	public void onCellChanged(CellModel cellModel) {
		int cellRow =  cellModel.getId() / GRID_SIDE_SIZE;
		int cellCol = cellModel.getId() % GRID_SIDE_SIZE;

		int squareRow = cellRow / SQUARE_SIDE_SIZE;
		int squareCol = cellCol / SQUARE_SIDE_SIZE;
		int squareId = (squareRow * SQUARE_SIDE_SIZE) + squareCol;

		squares[squareId].onCellChanged(cellModel);
	}
}
