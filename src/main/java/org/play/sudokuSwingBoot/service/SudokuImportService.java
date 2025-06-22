package org.play.sudokuSwingBoot.service;

import static org.play.sudokuSwingBoot.Sudoku.cellId;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.play.sudokuSwingBoot.model.CellModel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuImportService {

	public List<CellModel> importGame(File textFile) {

		List<CellModel> boardModel = null;
		try(
			final FileReader freader = new FileReader(textFile);
			final BufferedReader reader = new BufferedReader(freader);
			Stream<String> importStream = reader.lines();
		) {
			 boardModel = importGame(importStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardModel;
	}

	public List<CellModel> importGame(ApplicationArguments args) {
		Stream<String> stream = Arrays.stream(args.getSourceArgs())
			.flatMap(s -> Arrays.stream(s.split("\\s+")));
		return importGame(stream);
	}

	private List<CellModel> importGame(Stream<String> importLines) {
		List<CellModel> boardModel = importLines
			.filter((s) -> !s.isBlank())
			.map(String::trim)
			.map(this::parseImportLine)
			.toList();
		System.out.println(boardModel);
		if (boardModel.contains(null))
			return null;
		else
			return boardModel;
	}

	private CellModel parseImportLine(String importLine) {
		if (!this.isValidImportLine(importLine)) {
			System.out.println("Invalid line " + importLine);
			return null;
		}
		String[] lineArr = importLine.split(";", 2);

		String[] rowToCol = lineArr[0].split(",", 2);

		String[] valueToIsEnabled =
			lineArr[1].split(",", 2);

		int row = rowToCol[0].charAt(0) - '0';
		int col = rowToCol[1].charAt(0) - '0';
		int cellId = cellId(row, col);
		CellModel cell = new CellModel(cellId);
		int value = valueToIsEnabled[0].charAt(0) - '0';
		boolean isEnabled = valueToIsEnabled[1]
			.equals("true");
		cell.setEnabled(isEnabled);
		cell.setValue(value);
		return cell;
	}

	private boolean isValidImportLine(String importLine) {
		return importLine
			.matches("^[0-8],[0-8];[1-9],(true|false)$");
	}
}
