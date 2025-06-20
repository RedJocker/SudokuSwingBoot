package org.play.sudokuSwingBoot.gui;

import static org.play.sudokuSwingBoot.Sudoku.GRID_NUM_CELLS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.play.sudokuSwingBoot.gui.utils.LiveData;
import org.play.sudokuSwingBoot.gui.utils.SudokuWorker;
import org.play.sudokuSwingBoot.service.SudokuService;
import org.play.sudokuSwingBoot.service.model.SudokuBoardState;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuViewModel {

	private final SudokuService sudokuService;
	private LiveData<CellModel[]> cells;
	private LiveData<Boolean> isComplete;
	private SudokuWorker<SudokuBoardState> clickWorker = null;
	private int currentActiveCellId = -1;
	private HashSet<Integer> refreshCells;

	public SudokuViewModel(SudokuService sudokuService) {
		this.sudokuService = sudokuService;

		CellModel[] cellsArr = new CellModel[GRID_NUM_CELLS];
		for (int i = 0; i < GRID_NUM_CELLS; i++) {
			cellsArr[i] = new CellModel(i);
		}
		cells = new LiveData<CellModel[]>(cellsArr);
		isComplete = new LiveData<Boolean>(false);
		refreshCells = new HashSet<>();
	}
	
	private boolean isValidCellId(int cellId) {
		return cellId >=0 && cellId < GRID_NUM_CELLS;
	}

	public void observeChangedData(Consumer<CellModel> callback) {
		cells.observe((cellsArr) -> {
			for (int cellIdToRefresh : this.refreshCells) {
				callback.accept(
					cellsArr[cellIdToRefresh]
				);
			}
		});
	}

	public void observeIsComplete(Consumer<Boolean> onComplete){
		isComplete.observe(onComplete);
	}

	public void onCellClick(int cellId) {
		System.out.println("Cell clicked " + cellId);
		if (isComplete.getData()) {
			System.out.println("Game is already complete");
			return;
		}
		if (isValidCellId(currentActiveCellId)) {
			cells.getData()[currentActiveCellId]
				.setActive(false);
			this.refreshCells.add(currentActiveCellId);
		}
		if (cellId != currentActiveCellId && isValidCellId(cellId)) {
			currentActiveCellId = cellId;
			cells.getData()[currentActiveCellId]
				.setActive(true);
			this.refreshCells.add(currentActiveCellId);
		} else {
			currentActiveCellId = -1;
		}
		cells.setData(cells.getData(), true);
		this.refreshCells.clear();
	}

	public void onControlClick(int buttonId) {

		System.out.println("Button Clicked: " + buttonId);
		if (isComplete.getData()) {
			System.out.println("Game is already complete");
			return;
		}
		if (!isValidCellId(currentActiveCellId)) {
			System.out.println("No Cell Selected");
			return;
		}

		CellModel cell = cells.getData()[currentActiveCellId];
		if (buttonId == cell.getValue())
			return;

		cell.setValue(buttonId);
		this.refreshCells.add(cell.getId());
		
		// reset invalid state
		for (CellModel c: cells.getData()) {
			if(!c.isValid()) {
				c.setValid(true);
				refreshCells.add(c.getId());
		    }
		}
		this.spawnBoardStateWorker();
	}

	private void spawnBoardStateWorker() {
		if (clickWorker != null
			&& !clickWorker.isDone() && !clickWorker.isCancelled()) {
			System.out.println("cancelling previous");
			boolean interruptIfRunning = true;
			clickWorker.cancel(interruptIfRunning);
		}

		this.clickWorker = new SudokuWorker<SudokuBoardState>(
			() -> {
				System.out.println("working");
				System.out.println(Arrays.toString(cells.getData()));
				return this.sudokuService.boardState(cells.getData());
			},
			(SudokuBoardState boardState) -> {
				System.out.println(boardState);
				for (int cellId : boardState.invalidCells()) {
					this.cells.getData()[cellId].setValid(false);
					refreshCells.add(cellId);
				}
				
				cells.setData(cells.getData(), true);
				refreshCells.clear();
				isComplete.setData(boardState.isComplete());
			}
		);
		this.clickWorker.execute();
	}
}
