package org.play.sudokuSwingBoot.model;

import java.util.Set;

public record SudokuBoardState(
	boolean isComplete, Set<Integer> invalidCells) {
}
