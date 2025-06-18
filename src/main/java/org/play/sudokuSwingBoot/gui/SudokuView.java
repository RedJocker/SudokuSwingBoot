package org.play.sudokuSwingBoot.gui;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;


@Component
public class SudokuView extends JFrame {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 400;
	private final SudokuViewModel viemModel;
	private final SudokuGrid sudokuGrid;

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

		this.sudokuGrid.setOnClickCell(viemModel::onCellClick);
		this.setLayout(borderLayout);
		
		this.add(sudokuTitle, BorderLayout.NORTH);
		this.add(this.sudokuGrid, BorderLayout.CENTER);

		this.setVisible(true);
	}
	
	public SudokuView(
		final SudokuViewModel viewModel,
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		final ApplicationArguments args,
		final SudokuGrid sudokuGrid
	) {
		this.viemModel = viewModel;
		this.sudokuGrid = sudokuGrid;
		SwingUtilities.invokeLater((Runnable) () -> {
				initSudokuView(
					borderLayout,
					sudokuTitle,
					args
				);
		});
	}
}
