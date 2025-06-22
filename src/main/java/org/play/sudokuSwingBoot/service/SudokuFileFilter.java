package org.play.sudokuSwingBoot.service;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		return f.getName().endsWith(".sudoku");
	}

	@Override
	public String getDescription() {
		return "Sudoku Files";
	}
}
