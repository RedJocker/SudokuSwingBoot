package org.play.sudokuSwingBoot.gui.utils;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;


public class SudokuKeyAction extends AbstractAction {

	private final Consumer<ActionEvent> onSudokuKeyAction;

	public SudokuKeyAction(Consumer<ActionEvent> onSudokuKeyAction) {
		this.onSudokuKeyAction = onSudokuKeyAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		onSudokuKeyAction.accept(e);
	}
}
