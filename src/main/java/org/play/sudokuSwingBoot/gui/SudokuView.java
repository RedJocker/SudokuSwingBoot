package org.play.sudokuSwingBoot.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.play.sudokuSwingBoot.gui.utils.SudokuKeyAction;
import org.play.sudokuSwingBoot.model.CellModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;


@Component
public class SudokuView extends JFrame {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 400;
	private final SudokuViewModel viewModel;
	private final SudokuGrid sudokuGrid;
	private final SudokuControl sudokuControl;
	private final SudokuMenuBar sudokuMenuBar;

	private void initSudokuView(
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		final ApplicationArguments args
	) {
		System.out.println("HELLO SUDOKU VIEW");
		System.out.println(
			Arrays.toString(args.getSourceArgs())
		);

		this.setTitle("Sudoku Swing Boot");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.sudokuGrid.setOnClickCell(viewModel::onCellClick);
		this.viewModel.observeCellChange(
			(CellModel cellModel) -> {
				System.out.println(
					"id: " + cellModel.getId()
					+ ", isActive: " + cellModel.isActive()
				);
				sudokuGrid.onCellChanged(cellModel);
		});
		if (args.getSourceArgs().length != 0)
			viewModel.onImport(args);
		else
			this.viewModel.initBoard();
		this.viewModel.observeIsComplete(sudokuGrid::onComplete);
		this.viewModel.observeShouldExit(this::onExit);

		this.sudokuControl
			.setOnControlClick(this.viewModel::onControlClick);
		this.setLayout(borderLayout);

		this.add(sudokuTitle, BorderLayout.NORTH);
		this.add(this.sudokuGrid, BorderLayout.CENTER);
		this.add(this.sudokuControl, BorderLayout.SOUTH);
		createSudokuKeybinding("active-right", KeyEvent.VK_RIGHT);
		createSudokuKeybinding("active-left", KeyEvent.VK_LEFT);
		createSudokuKeybinding("active-up", KeyEvent.VK_DOWN);
		createSudokuKeybinding("active-down", KeyEvent.VK_UP);
		createSudokuKeybinding("put-1", KeyEvent.VK_1);
		createSudokuKeybinding("put-2", KeyEvent.VK_2);
		createSudokuKeybinding("put-3", KeyEvent.VK_3);
		createSudokuKeybinding("put-4", KeyEvent.VK_4);
		createSudokuKeybinding("put-5", KeyEvent.VK_5);
		createSudokuKeybinding("put-6", KeyEvent.VK_6);
		createSudokuKeybinding("put-7", KeyEvent.VK_7);
		createSudokuKeybinding("put-8", KeyEvent.VK_8);
		createSudokuKeybinding("put-9", KeyEvent.VK_9);
		createSudokuKeybinding("clear", KeyEvent.VK_0);
		createSudokuKeybinding("clear", KeyEvent.VK_X);

		this.setJMenuBar(this.sudokuMenuBar);
		this.setVisible(true);
	}

	private void onExit(Boolean shouldExit) {
		if (shouldExit == true) {
			this.dispose();
		}
	}

	private void createSudokuKeybinding(
		String sudokuEvent, int keyCode
	) {
		this.rootPane
			.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(
				KeyStroke.getKeyStroke(keyCode, 0),
				sudokuEvent
			);
		this.rootPane.getActionMap().put(
			sudokuEvent,
			new SudokuKeyAction((e) -> {
				viewModel.onSudokuKey(sudokuEvent);
			})
		);
	}

	public SudokuView(
		final SudokuViewModel viewModel,
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		final ApplicationArguments args,
		final SudokuGrid sudokuGrid,
		final SudokuControl sudokuControl,
		final SudokuMenuBar sudokuMenuBar
	) {
		this.viewModel = viewModel;
		this.sudokuGrid = sudokuGrid;
		this.sudokuControl = sudokuControl;
		this.sudokuMenuBar = sudokuMenuBar;
		SwingUtilities.invokeLater((Runnable) () -> {
				initSudokuView(
					borderLayout,
					sudokuTitle,
					args
				);
		});
	}
}
