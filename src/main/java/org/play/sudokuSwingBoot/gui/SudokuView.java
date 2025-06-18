package org.play.sudokuSwingBoot.gui;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.util.Arrays;

@Component
public class SudokuView extends JFrame {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 400;

	public SudokuView(
		final BorderLayout borderLayout,
		final SudokuTitle sudokuTitle,
		ApplicationArguments args
	) {
		
		System.out.println("HELLO SUDOKU VIEW");
		System.out.println(
			Arrays.toString(args.getSourceArgs())
		);
		this.setTitle("Sudoku Swing Boot");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(borderLayout);
		
		this.add(sudokuTitle, BorderLayout.NORTH);
				
		this.setVisible(true);
	}
}
