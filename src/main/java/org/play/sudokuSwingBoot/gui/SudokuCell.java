package org.play.sudokuSwingBoot.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.play.sudokuSwingBoot.model.CellModel;

public class SudokuCell extends JLabel implements MouseListener {

	private static Border BORDER_GRAY = 
		BorderFactory.createLineBorder(Color.GRAY);
	private static Border BORDER_GRAY_THICK =
		BorderFactory.createLineBorder(Color.GRAY, 3);
	private static Border BORDER_DARK_THICK =
		BorderFactory.createLineBorder(Color.BLACK, 3);

	private int id = -1;
	private Consumer<Integer> onClickCell = null;
	
	public SudokuCell() {
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setBorder(BORDER_GRAY);
	}

	public void setId(int id) {
		this.id = id;
		this.setText("");
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

    public void onCellChanged(CellModel cellModel) {
		System.out.println("SudokuCell.onCellChanged\n\t"
			+ cellModel.toString());

		if (cellModel.isActive() && !cellModel.isEnabled()) {
			this.setBorder(BORDER_GRAY_THICK);
		} else if (cellModel.isActive()) {
			this.setBorder(BORDER_DARK_THICK);
		} else {
			this.setBorder(BORDER_GRAY);
		}

		if (!cellModel.isValid() && !cellModel.isEnabled()) {
			this.setBackground(new Color(120, 20, 20));
			this.setOpaque(true);
		} else if (!cellModel.isValid()) {
			this.setBackground(Color.RED);
			this.setOpaque(true);
		} else if (!cellModel.isEnabled()) {
			this.setBackground(Color.LIGHT_GRAY);
			this.setOpaque(true);
		} else {
			this.setBackground(Color.WHITE);
			this.setOpaque(false);
		}

		this.setText(cellModel.getText());
    }

    public void onComplete(Boolean isComplete) {
		if (!isComplete)
			return ;

		this.setBackground(Color.GREEN);
		this.setOpaque(true);
    }
}
