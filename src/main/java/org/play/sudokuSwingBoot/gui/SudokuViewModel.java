package org.play.sudokuSwingBoot.gui;

import java.util.function.Consumer;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.play.sudokuSwingBoot.gui.utils.LiveData;
import org.play.sudokuSwingBoot.gui.utils.SudokuWorker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("singleton")
public class SudokuViewModel {
	private static int NUM_CELLS = 81;
	
	private LiveData<CellModel[]> cells;
	private SudokuWorker<Integer> clickWorker = null;
	private int currentActiveCellId = -1;

	private boolean isValidCellId(int cellId) {
		return cellId >=0 && cellId < NUM_CELLS;
	}
	
	public SudokuViewModel() {

		CellModel[] cellsArr = new CellModel[NUM_CELLS];
		for (int i = 0; i < NUM_CELLS; i++) {
			cellsArr[i] = new CellModel(i);
		}
		cells = new LiveData<CellModel[]>(cellsArr);
	}
	
	public void observeChangedData(Consumer<CellModel> callback) {
		cells.observe((cellsArr) -> {
				if (isValidCellId(currentActiveCellId)) {
					callback.accept(
						cellsArr[currentActiveCellId]
					);
				}
		});
	}

	public void onCellClick(int cellId) {

		System.out.println("Cell clicked " + cellId);
		if (isValidCellId(currentActiveCellId)) {
			cells.getData()[currentActiveCellId]
				.setActive(false);
			cells.setData(cells.getData(), true);
		}
		if (cellId != currentActiveCellId && isValidCellId(cellId)) {
			currentActiveCellId = cellId;
			cells.getData()[currentActiveCellId]
				.setActive(true);
			cells.setData(cells.getData(), true);
		} else {
			currentActiveCellId = -1;
		}
	}

	public void onControlClick(int buttonId) {

		System.out.println("Button Clicked: " + buttonId);
		if (!isValidCellId(currentActiveCellId)) {
			System.out.println("No Cell Selected");
			return;
		}

		CellModel cell = cells.getData()[currentActiveCellId];
		if (buttonId == cell.getValue())
			return;

		cell.setValue(buttonId);
		cells.setData(cells.getData(), true);
	}
	
	public void onClickButton() {
		// if (clickWorker != null
		// 	&& !clickWorker.isDone() && !clickWorker.isCancelled()) {
		// 	System.out.println("cancelling previous");
		// 	boolean interruptIfRunning = true;
		// 	clickWorker.cancel(interruptIfRunning);
		// }
		// System.out.println("onClickButton");
		// this.clickWorker = new SudokuWorker<Integer>(
		// 	() -> {
		// 		try {
		// 			Thread.sleep(3000);
		// 		} catch (InterruptedException e) {	
		// 			//e.printStackTrace();
		// 		}
		// 		return 1;
		// 	},
		// 	this.cells::setData
		// );
		// this.clickWorker.execute();
	}
}
