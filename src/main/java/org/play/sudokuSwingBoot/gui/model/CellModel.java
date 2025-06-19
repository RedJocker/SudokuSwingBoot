package org.play.sudokuSwingBoot.gui.model;

public class CellModel {
	private final int id;
	private boolean isActive;
	private boolean isEnabled;
	String text;

	public CellModel(int id) {
		this.id = id;
		this.isActive = false;
		this.isEnabled = true;
		this.text = "" + id;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
