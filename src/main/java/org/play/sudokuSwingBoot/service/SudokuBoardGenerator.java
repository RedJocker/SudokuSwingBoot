package org.play.sudokuSwingBoot.service;

import org.play.sudokuSwingBoot.model.SudokuRawBoard;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qqwing.QQWing;

@Component
@Scope("singleton")
public class SudokuBoardGenerator {

	private final QQWing qqwing;

	public SudokuBoardGenerator(QQWing qqWing) {
		this.qqwing = qqWing;
	}

	public SudokuRawBoard generateBoard() {
		qqwing.generatePuzzle();
		return new SudokuRawBoard(qqwing.getPuzzle());
	}
}
