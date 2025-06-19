package org.play.sudokuSwingBoot.gui;

import java.util.function.Consumer;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.play.sudokuSwingBoot.gui.utils.LiveData;
import org.play.sudokuSwingBoot.gui.utils.SudokuWorker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.play.sudokuSwingBoot.Sudoku.GRID_NUM_CELLS;
import static org.play.sudokuSwingBoot.Sudoku.GRID_SIDE_SIZE;
import org.play.sudokuSwingBoot.Sudoku;


record SudokuBoardState(
	boolean isComplete, Set<Integer> invalidCells) {
}

@Component
@Scope("singleton")
class SudokuService {

	private void checkRow(
		int row,
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		int[] numbers = new int[10];

		for (int col = 0; col < GRID_SIDE_SIZE; col++) {
			int cellId = Sudoku.cellId(row, col);
			CellModel cell = cells[cellId];
			if (cell.getValue() == 0) {
				isComplete[0] = false;
			} else if (numbers[cell.getValue()] == 0) {
				numbers[cell.getValue()] = cellId + 1; //1-index
				                            //to avoid 0 default value
			} else {
				invalidCells.add(cellId);
				invalidCells.add(numbers[cell.getValue()] - 1);
			}
			//			System.out.println("cellId: " + cellId);
			//System.out.println("invalidCells: " + invalidCells.toString());
			//System.out.println("numbers: " + Arrays.toString(numbers));
		}
	}
	
	private void checkSameRow(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		for (int row = 0; row < GRID_SIDE_SIZE; row++) {
			checkRow(row, cells, invalidCells, isComplete);
		}
	}

	private void checkSameCol(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete
	) {
		// TODO:
		
	}

	private void checkSameSquare(
		CellModel[] cells,
		HashSet<Integer> invalidCells,
		boolean[] isComplete 
	) {
		// TODO:
	}
	
	SudokuBoardState boardState(CellModel[] cells) {
		HashSet<Integer> invalidCells = new HashSet<>();
		boolean[] isComplete = { true };
		checkSameRow(cells, invalidCells, isComplete);
		checkSameCol(cells, invalidCells, isComplete);
		checkSameSquare(cells, invalidCells, isComplete);
		isComplete[0] = isComplete[0] && invalidCells.isEmpty();
		return new SudokuBoardState(isComplete[0], invalidCells);
	}
}



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
		refreshCells = new HashSet();
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

	public void onCellClick(int cellId) {
		System.out.println("Cell clicked " + cellId);
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
			}
		);
		this.clickWorker.execute();
	}
}
