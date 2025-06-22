package org.play.sudokuSwingBoot.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;

import org.play.sudokuSwingBoot.service.SudokuFileFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Beans {

	@Bean
	@Scope("prototype")
	public BorderLayout borderLayout() {
		return new BorderLayout();
	}

	@Bean
	@Scope("singleton")
	public JFileChooser jFileChooser (SudokuFileFilter fileFilter) {
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./"));
		fc.addChoosableFileFilter(fileFilter);
		fc.setFileFilter(fileFilter);
		return fc;
	}
}
