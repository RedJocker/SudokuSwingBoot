package org.play.sudokuSwingBoot.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FileService {


	public void saveGame(File saveFile, List<CellModel> boardModel) throws IOException {
		if (!saveFile.exists()) {
			saveFile.createNewFile();
		}

		final FileOutputStream fout = new FileOutputStream(saveFile);
		final ObjectOutputStream out = new ObjectOutputStream(
			new BufferedOutputStream(fout)
		);

		out.writeObject(boardModel);
		out.close();
	}

	@SuppressWarnings("unchecked")
	public List<CellModel> loadGame(File loadFile) throws IOException {

		final FileInputStream fin = new FileInputStream(loadFile);
		final ObjectInputStream in = new ObjectInputStream(
			new BufferedInputStream(fin)
		);

		List<CellModel> boardModel = null;
		try {
			boardModel = (List<CellModel>) in.readObject();
		} catch (ClassNotFoundException| ClassCastException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();
		return boardModel;
	}
}
