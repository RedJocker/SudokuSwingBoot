package org.play.sudokuSwingBoot.gui;

import static org.play.sudokuSwingBoot.Sudoku.GRID_NUM_CELLS;
import static org.play.sudokuSwingBoot.Sudoku.GRID_SIDE_SIZE;

import java.util.HashSet;
import java.util.function.Consumer;

import org.play.sudokuSwingBoot.gui.model.CellModel;
import org.play.sudokuSwingBoot.gui.utils.LiveData;
import org.play.sudokuSwingBoot.gui.utils.SudokuWorker;
import org.play.sudokuSwingBoot.service.SudokuBoardGenerator;
import org.play.sudokuSwingBoot.service.SudokuService;
import org.play.sudokuSwingBoot.service.model.SudokuBoardState;
import org.play.sudokuSwingBoot.service.model.SudokuRawBoard;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuViewModel {

	private final SudokuBoardGenerator generator;
	private final SudokuService sudokuService;
	private LiveData<CellModel[]> cells;
	private LiveData<Boolean> isComplete;
	private SudokuWorker<SudokuBoardState> clickWorker = null;
	private int currentActiveCellId = -1;
	private HashSet<Integer> refreshCells;

	public SudokuViewModel(
		SudokuService sudokuService,
		SudokuBoardGenerator sudokuBoardGenerator
	) {
		this.sudokuService = sudokuService;
		this.generator = sudokuBoardGenerator;
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

	private void currentCellUpdateValue(int value) {
		final CellModel cell = cells.getData()[currentActiveCellId];
		if (!cell.isEnabled())
			return ;
		cell.setValue(value);
		this.refreshCells.add(currentActiveCellId);
	}

	private void currentCellClearActive() {
		if (isValidCellId(currentActiveCellId)) {
			cells.getData()[currentActiveCellId]
				.setActive(false);
			this.refreshCells.add(currentActiveCellId);
		}
	}

	private void currentCellUpdateActive(int cellId) {
		currentCellClearActive();
		if (cellId != currentActiveCellId && isValidCellId(cellId)) {
			currentActiveCellId = cellId;
			cells.getData()[currentActiveCellId]
				.setActive(true);
			this.refreshCells.add(currentActiveCellId);
		} else {
			currentActiveCellId = -1;
		}
	}

	public void initBoard() {
		SudokuWorker<SudokuRawBoard> initWorker =
			new SudokuWorker<SudokuRawBoard>(
			() -> {
				System.out.println("generating board");
				return this.generator.generateBoard();
			},
			(SudokuRawBoard board) -> {
				for (int i = 0; i < GRID_NUM_CELLS; i++) {
					int value = board.rawBoard()[i];
					CellModel cell = cells.getData()[i];
					cell.setValue(value);
					cell.setEnabled(value == 0);
					refreshCells.add(i);
				}
				cells.setData(cells.getData(), true);
				refreshCells.clear();
				System.out.println("finished loading board");
			}
		);
		initWorker.execute();
	}

	/**
	 *  Spawns a SwingWorker that computes board state,
	 *  getting completion status and invalid squares,
	 *  updates cellModels with new status and trigger cells observers
	 */
	private void spawnBoardStateWorker() {
		// reset invalid state,
		// worker will recompute all invalid and set it again
		for (CellModel c: cells.getData()) {
			if(!c.isValid()) {
				c.setValid(true);
				refreshCells.add(c.getId());
		    }
		}

		if (clickWorker != null
			&& !clickWorker.isDone() && !clickWorker.isCancelled()) {
			System.out.println("cancelling previous worker");
			boolean interruptIfRunning = true;
			clickWorker.cancel(interruptIfRunning);
		}

		this.clickWorker = new SudokuWorker<SudokuBoardState>(
			() -> {
				System.out.println("working");
				return this.sudokuService.boardState(cells.getData());
			},
			(SudokuBoardState boardState) -> {
				System.out.println(boardState);
				for (int cellId : boardState.invalidCells()) {
					this.cells.getData()[cellId]
						.setValid(false);
					refreshCells.add(cellId);
				}
				cells.setData(cells.getData(), true);
				refreshCells.clear();
				isComplete.setData(boardState.isComplete());
				System.out.println("finished working");
			}
		);
		this.clickWorker.execute();
	}

	public void observeCellChange(Consumer<CellModel> callback) {
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
		this.currentCellUpdateActive(cellId);
		this.cells.setData(cells.getData(), true);
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
		int newValue = buttonId;
		currentCellUpdateValue(newValue);
		this.spawnBoardStateWorker();
	}

    public void onSudokuKey(String keyEvent) {
		System.out.println("keyEvent: " + keyEvent);
		if (isComplete.getData()) {
			System.out.println("Game is already complete");
			return;
		}

		switch(keyEvent) {
		    case "active-right" -> {
		    	onSudokuKeyMoveActive(1);
		    }
		    case "active-left" -> {
		    	onSudokuKeyMoveActive(-1);
		    }
		    case "active-up" -> {
		    	onSudokuKeyMoveActive(GRID_SIDE_SIZE);
		    }
		    case "active-down" -> {
		    	onSudokuKeyMoveActive(-GRID_SIDE_SIZE);
		    }
		    case "clear" -> {
		    	this.currentCellUpdateValue(0);
		    	this.spawnBoardStateWorker();
		    }
		    default -> {
				if (!isValidCellId(currentActiveCellId)) {
					System.out.println("No Cell Selected");
				} else if (keyEvent.startsWith("put-")) {
		    		int value = keyEvent.charAt(4) - '0';
		    		this.currentCellUpdateValue(value);
		    		this.spawnBoardStateWorker();
		    	}
		    }
		}
    }

	private void onSudokuKeyMoveActive(int offset) {
		int newCellId;
		if (Math.abs(offset) == 1) {
			newCellId =
				((currentActiveCellId + offset + GRID_SIDE_SIZE)
					% GRID_SIDE_SIZE)
				+ ((currentActiveCellId / GRID_SIDE_SIZE)
					* GRID_SIDE_SIZE);
		} else {
			newCellId =
				((currentActiveCellId + offset + GRID_NUM_CELLS)
					% GRID_NUM_CELLS);
		}
		this.currentCellUpdateActive(newCellId);
		cells.setData(cells.getData(), true);
		this.refreshCells.clear();
	}
}
