package org.play.sudokuSwingBoot.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class SudokuCell extends JLabel implements MouseListener {

	private static Border BORDER_GRAY = 
		BorderFactory.createLineBorder(Color.GRAY);
	
	private int id = -1;
	private Consumer<Integer> onClickCell = null;
	
	public SudokuCell() {
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setBorder(BORDER_GRAY);
	}

	public void setId(int id) {
		this.id = id;
		this.setText("" + id);
	}

	public void setOnClickCell(Consumer<Integer> onClickCell) {
		this.onClickCell = onClickCell;
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (onClickCell != null && this.id >= 0 && this.id < 9 * 9) 
			onClickCell.accept(this.id);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// ignore
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// ignore
	}
}
