package org.play.sudokuSwingBoot.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.play.sudokuSwingBoot.service.SudokuFileService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuMenuBar extends JMenuBar{
	final SudokuFileService fileService;
	final SudokuViewModel sudokuViewModel;
	
	public SudokuMenuBar(
		final SudokuFileService fileService,
		final SudokuViewModel sudokuViewModel) {

		this.fileService = fileService;
		this.sudokuViewModel = sudokuViewModel;
		
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
		loadMenuItem.addActionListener(this::onLoad);
		menu.add(loadMenuItem);

		final JMenuItem saveMenuItem =
			new JMenuItem("Save game", KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.ALT_MASK));
		saveMenuItem.getAccessibleContext()
			.setAccessibleDescription("Save a sudoku game");
		saveMenuItem.addActionListener(this::onSave);
		menu.add(saveMenuItem);
	}

	private void onSave(ActionEvent e) {
		System.out.println("Save Clicked");
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
		int returnVal = fc.showSaveDialog(this.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				this.fileService.saveGame(
					file, sudokuViewModel.getBoard()
				);
			} catch (IOException ex) {
				ex.printStackTrace();
			}	
		}
	}

	private void onLoad(ActionEvent e) {
		System.out.println("Load Clicked");
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
		int returnVal = fc.showOpenDialog(this.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				List<CellModel> board = this.fileService.loadGame(
					file
				);
				sudokuViewModel.loadBoard(board);
			} catch (IOException ex) {
				ex.printStackTrace();
			}	
		}
	}
}
