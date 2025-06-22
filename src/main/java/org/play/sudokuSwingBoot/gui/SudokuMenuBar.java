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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.play.sudokuSwingBoot.model.CellModel;
import org.play.sudokuSwingBoot.service.SudokuFileService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuMenuBar extends JMenuBar{
	final SudokuFileService fileService;
	final SudokuViewModel sudokuViewModel;
	final JFileChooser fileChooser;
	
	public SudokuMenuBar(
		final SudokuFileService fileService,
		final SudokuViewModel sudokuViewModel,
		final JFileChooser fileChooser
	) {

		this.fileService = fileService;
		this.sudokuViewModel = sudokuViewModel;
		this.fileChooser = fileChooser;
		
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
			(e) -> {
				int result = JOptionPane
					.showConfirmDialog(this.getParent(),
						"Do you really want to start a new game?",
						"New game",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					sudokuViewModel.onNewGame();
				}
            }
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

		final JMenuItem importMenuItem =
			new JMenuItem("Import game", KeyEvent.VK_I);
		importMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_I, ActionEvent.ALT_MASK));
		importMenuItem.getAccessibleContext()
			.setAccessibleDescription("Import game from text file");
		importMenuItem.addActionListener(this::onImport);
		menu.add(importMenuItem);

		final JMenuItem exitMenuItem =
			new JMenuItem("Quit", KeyEvent.VK_Q);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		exitMenuItem.getAccessibleContext()
			.setAccessibleDescription("Quit Sudoku");
		exitMenuItem.addActionListener(this::onExit);
		menu.add(exitMenuItem);
	}

	private void onSave(ActionEvent e) {
		System.out.println("Save Clicked");
		int returnVal = fileChooser.showSaveDialog(this.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
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

		int returnVal = fileChooser.showOpenDialog(this.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
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

	private void onExit(ActionEvent e) {
		System.out.println("Exit Clicked");
		int result = JOptionPane
			.showConfirmDialog(this.getParent(),
				"Do you really want to start a new game?",
				"New game",
				JOptionPane.YES_NO_OPTION);
		if (result == JFileChooser.APPROVE_OPTION)
			sudokuViewModel.onExit();
	}

	private void onImport(ActionEvent e) {
		System.out.println("Import Clicked");

		final JFileChooser importFileChooser = new JFileChooser();
		importFileChooser.setCurrentDirectory(new File("./"));

		int returnVal = importFileChooser.showOpenDialog(this.getParent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File importFile = importFileChooser.getSelectedFile();
			this.sudokuViewModel.onImport(importFile);
		}
	}
}
