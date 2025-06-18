package org.play.sudokuSwingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Arrays;

@SpringBootApplication
public class SudokuSwingBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SudokuSwingBootApplication.class)
			.headless(false)
			.web(WebApplicationType.NONE)
			.run(args);
	}

	public void run(String[] args) {
		System.out.println("HELLO!!!");
		System.out.println(Arrays.toString(args));
	}
}
