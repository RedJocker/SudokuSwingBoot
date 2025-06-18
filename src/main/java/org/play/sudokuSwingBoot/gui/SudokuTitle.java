package org.play.sudokuSwingBoot.gui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

@Component
@Scope("singleton")
public class SudokuTitle extends JLabel {

	private static String TITLE_TEXT = "S U D O K U";
	private static Dimension PREFERED_DIMENSION =
		new Dimension(150, 50);
	private static Border BORDER_BLACK =
		BorderFactory.createLineBorder(Color.BLACK);

	public SudokuTitle() {
        this.setBorder(BORDER_BLACK);
        this.setPreferredSize(PREFERED_DIMENSION);
        this.setText(TITLE_TEXT);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
	}
}
