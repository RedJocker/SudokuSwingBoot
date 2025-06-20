package org.play.sudokuSwingBoot.gui;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;


@Component
public class SudokuView extends JFrame {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 400;
	private final SudokuViewModel viewModel;
	private final SudokuGrid sudokuGrid;
	private final SudokuControl sudokuControl;

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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.sudokuGrid.setOnClickCell(viewModel::onCellClick);
		this.viewModel.observeChangedData(
			(CellModel cellModel) -> {
				System.out.println(
					"id: " + cellModel.getId()
					+ ", isActive: " + cellModel.isActive()
				);
				sudokuGrid.onCellChanged(cellModel);
		});

		this.viewModel.observeIsComplete(sudokuGrid::onComplete);

		this.sudokuControl
			.setOnControlClick(this.viewModel::onControlClick);
		this.setLayout(borderLayout);
		
		this.add(sudokuTitle, BorderLayout.NORTH);
		this.add(this.sudokuGrid, BorderLayout.CENTER);
		this.add(this.sudokuControl, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public SudokuView(
		final SudokuViewModel viewModel,
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		final ApplicationArguments args,
		final SudokuGrid sudokuGrid,
		final SudokuControl sudokuControl
	) {
		this.viewModel = viewModel;
		this.sudokuGrid = sudokuGrid;
		this.sudokuControl = sudokuControl;
		SwingUtilities.invokeLater((Runnable) () -> {
				initSudokuView(
					borderLayout,
					sudokuTitle,
					args
				);
		});
	}
}
