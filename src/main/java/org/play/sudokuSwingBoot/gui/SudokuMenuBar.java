package org.play.sudokuSwingBoot.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuMenuBar extends JMenuBar{
	public SudokuMenuBar(final SudokuViewModel sudokuViewModel) {

		final JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext()
			.setAccessibleDescription("File Menu");
		this.add(menu);


		final JMenuItem newGameMenuItem =
			new JMenuItem("New game", KeyEvent.VK_N);
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.ALT_MASK));
		newGameMenuItem.getAccessibleContext()
			.setAccessibleDescription("New sudoku game");
		newGameMenuItem.addActionListener(
			(e) -> sudokuViewModel.onNewGame()
		);
		menu.add(newGameMenuItem);

		final JMenuItem loadMenuItem =
			new JMenuItem("Load saved game", KeyEvent.VK_L);
		loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.ALT_MASK));
		loadMenuItem.getAccessibleContext()
			.setAccessibleDescription("Load saved sudoku game");
		loadMenuItem.addActionListener(
			(e) -> System.out.println("Load Clicked")
		);
		menu.add(loadMenuItem);

		final JMenuItem saveMenuItem =
			new JMenuItem("Save game", KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.ALT_MASK));
		saveMenuItem.getAccessibleContext()
			.setAccessibleDescription("Save a sudoku game");
		saveMenuItem.addActionListener(
			(e) -> System.out.println("Save Clicked")
		);
		menu.add(saveMenuItem);
	}
}
