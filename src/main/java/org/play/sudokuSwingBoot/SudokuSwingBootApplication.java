package org.play.sudokuSwingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

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
		createAndShowGUI();
	}

	private static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Hello World Swing Example");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(500, 360);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel label = new JLabel("Hello World Swing");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        label.setPreferredSize(new Dimension(150, 100));

        label.setText("Hello World Swing");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        jFrame.add(label);
        jFrame.setVisible(true);
    }
}
