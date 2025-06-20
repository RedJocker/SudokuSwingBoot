package org.play.sudokuSwingBoot.gui;

import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuControl extends JPanel {

	private static final int BUTTONS_LENGTH = 10;
	
	private final JButton[] buttons;
	private Consumer<Integer> onControlClick = null;
	
	public void setOnControlClick(Consumer<Integer> onControlClick) {
		this.onControlClick = onControlClick;
	}
	
	public SudokuControl() {

		GridLayout layout = new GridLayout(2, 5, 4, 8);
		this.setLayout(layout);
		
	    this.buttons = new JButton[BUTTONS_LENGTH];
		for (int buttonId = 1; buttonId < BUTTONS_LENGTH; buttonId++) {
			this.buttons[buttonId] = new JButton("" + buttonId);
			this.add(buttons[buttonId]);
		}
		this.buttons[0] = new JButton("X");
		this.add(buttons[0]);

		for (int buttonId = 0; buttonId < BUTTONS_LENGTH; buttonId++) {
		    this.buttons[buttonId].setHorizontalAlignment(JLabel.CENTER);
		    this.buttons[buttonId].setVerticalAlignment(JLabel.CENTER);
			final int id = buttonId;
			this.buttons[buttonId].addActionListener((e) -> {
				if (this.onControlClick != null) {
					this.onControlClick.accept(id);
				}
			});
		}
	}
}
