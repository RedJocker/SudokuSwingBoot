package org.play.sudokuSwingBoot.service.model;

import java.util.Set;

public record SudokuBoardState(
	boolean isComplete, Set<Integer> invalidCells) {
}
