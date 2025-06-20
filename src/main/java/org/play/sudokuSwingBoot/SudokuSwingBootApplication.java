package org.play.sudokuSwingBoot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SudokuSwingBootApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SudokuSwingBootApplication.class)
			.headless(false)
			.web(WebApplicationType.NONE)
			.run(args);
	}
}
