package org.play.sudokuSwingBoot.gui;

import static org.play.sudokuSwingBoot.Sudoku.GRID_SIDE_SIZE;
import static org.play.sudokuSwingBoot.Sudoku.SQUARE_NUM_CELLS;
import static org.play.sudokuSwingBoot.Sudoku.SQUARE_SIDE_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.play.sudokuSwingBoot.model.CellModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SudokuSquare extends JPanel {

	private static Dimension PREFERED_DIMENSION =
		new Dimension(50, 50);
	private static Border BORDER_BLACK =
		BorderFactory.createLineBorder(Color.BLACK);

	private int id;
	private SudokuCell[] cells;
	
	public SudokuSquare() {
        this.setBorder(BORDER_BLACK);
        this.setPreferredSize(PREFERED_DIMENSION);
		GridLayout layout =
			new GridLayout(SQUARE_SIDE_SIZE, SQUARE_SIDE_SIZE);
		this.setLayout(layout);

		this.cells = new SudokuCell[SQUARE_NUM_CELLS];
		for (int i = 0; i < SQUARE_NUM_CELLS; i++) {
			SudokuCell cell = new SudokuCell();
			this.cells[i] = cell;
			this.add(cell);
		}
	}
	
	//    s0         s1         s2
	// 0  1  2  | 3  4  5  | 6  7  8 
	// 9  10 11 | 12 13 14 | 15 16 17
	// 18 19 20 | 21 22 23 | 24 25 26
	//    s3    |    s4    |    s5
	// 27 28 ...
	public void setSquareId(int id) { 
		this.id = id;                 
		final int squareRow = id / SQUARE_SIDE_SIZE; 
		final int squareCol = id % SQUARE_SIDE_SIZE; 

		for (int cellRow = 0; cellRow < SQUARE_SIDE_SIZE; cellRow++) {
			for (int cellCol = 0; cellCol < SQUARE_SIDE_SIZE; cellCol++) {
				int cellArrIndex = (SQUARE_SIDE_SIZE * cellRow) + cellCol;
				int cellId =
					(GRID_SIDE_SIZE
						* ((squareRow * SQUARE_SIDE_SIZE) + cellRow))
					+ ((squareCol * SQUARE_SIDE_SIZE) + cellCol);  
				this.cells[cellArrIndex].setId(cellId);
			}
		}
	}

	public void setOnClickCell(Consumer<Integer> onClickCell) {
		for (SudokuCell cell: this.cells) {
			cell.setOnClickCell(onClickCell);
		}
	}

    public void onCellChanged(CellModel cellModel) {
        int cellRow =  cellModel.getId() / GRID_SIDE_SIZE;
		int cellCol = cellModel.getId() % GRID_SIDE_SIZE;

		int squareRow = cellRow / SQUARE_SIDE_SIZE;
		int squareCol = cellCol / SQUARE_SIDE_SIZE;

		int squareCellRow = cellRow - (squareRow * SQUARE_SIDE_SIZE);
		int squareCellCol = cellCol - (squareCol * SQUARE_SIDE_SIZE); 

		int squareCellIndex = (squareCellRow * SQUARE_SIDE_SIZE)
			+ squareCellCol;
		cells[squareCellIndex].onCellChanged(cellModel);
    }

    public void onComplete(Boolean isComplete) {
		if (!isComplete)
			return ;
        for(SudokuCell cell: cells) {
			cell.onComplete(isComplete);
		}
    }
}
