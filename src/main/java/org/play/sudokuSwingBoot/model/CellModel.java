package org.play.sudokuSwingBoot.model;

import java.io.Serializable;

public class CellModel implements Serializable {
	private final int id;
	private boolean isActive;
	private boolean isEnabled;
	private boolean isValid;
	private int value;

	public CellModel(int id) {
		this.id = id;
		this.isActive = false;
		this.isEnabled = true;
		this.isValid = true;
		this.value = 0;
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

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getText() {
		if (this.value <= 0)
			return "";
		return "" + this.value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value <= 0 || value > 9 ? 0 : value;
	}

	@Override
	public String toString() {
		return "CellModel("
			+ "id: " + id
			+ ", isActive: " + isActive
			+ ", isEnabled: " + isEnabled
			+ ", isValid: " + isValid
			+ ", value: " + value
			+ ")";
	}
}
