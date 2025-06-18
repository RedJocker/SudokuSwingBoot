package org.play.sudokuSwingBoot.gui;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.util.Arrays;

@Component
public class SudokuView extends JFrame {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 400;
	private final SudokuViewModel viemModel;
	
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
		JButton button = new JButton("Click");
		JLabel label = new JLabel("");
		viemModel.observeNumberData((t) -> label.setText("clicked " + t));
		button.addActionListener( (e) -> {
			this.viemModel.onClickButton();
		});

		this.setLayout(borderLayout);
		
		this.add(sudokuTitle, BorderLayout.NORTH);
		this.add(label, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public SudokuView(
		final SudokuViewModel viewModel,
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		final ApplicationArguments args
	) {
		this.viemModel = viewModel;
		SwingUtilities.invokeLater((Runnable) () -> {
				initSudokuView(
					borderLayout,
					sudokuTitle,
					args
				);
		});
	}
}
