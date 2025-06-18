package org.play.sudokuSwingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SudokuSwingBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SudokuSwingBootApplication.class, args);
	}

	public void run(String[] args) {
		System.out.println("HELLO!!!");
	}
}
