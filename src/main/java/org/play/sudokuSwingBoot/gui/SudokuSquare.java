package org.play.sudokuSwingBoot.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class SudokuSquare extends JPanel {

	private static int SIZE = 9;
	private static int SIDE_SIZE = 3;
	private static Dimension PREFERED_DIMENSION =
		new Dimension(50, 50);
	private static Border BORDER_BLACK =
		BorderFactory.createLineBorder(Color.BLACK);

	private int id;
	private SudokuCell[] cells;
	
	public SudokuSquare() {
        this.setBorder(BORDER_BLACK);
        this.setPreferredSize(PREFERED_DIMENSION);
		GridLayout layout = new GridLayout(3, 3);
		this.setLayout(layout);

		this.cells = new SudokuCell[SIZE];
		for (int i = 0; i < SIZE; i++) {
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
		final int squareRow = id / SIDE_SIZE; 
		final int squareCol = id % SIDE_SIZE; 

		for (int cellRow = 0; cellRow < SIDE_SIZE; cellRow++) {
			for (int cellCol = 0; cellCol < SIDE_SIZE; cellCol++) {
				int cellArrIndex = (SIDE_SIZE * cellRow) + cellCol;
				int cellId = (SIZE * ((squareRow * SIDE_SIZE) + cellRow))
					+ ((squareCol * SIDE_SIZE) + cellCol);  
				this.cells[cellArrIndex].setId(cellId);
			}
		}
	}

	public void setOnClickCell(Consumer<Integer> onClickCell) {
		for (SudokuCell cell: this.cells) {
			cell.setOnClickCell(onClickCell);
		}
	}
}
