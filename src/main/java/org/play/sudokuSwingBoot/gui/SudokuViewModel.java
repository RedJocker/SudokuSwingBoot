package org.play.sudokuSwingBoot.gui;

import java.util.function.Consumer;

import org.play.sudokuSwingBoot.gui.utils.LiveData;
import org.play.sudokuSwingBoot.gui.utils.SudokuWorker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SudokuViewModel {
	private LiveData<Integer> numberData = 
		new LiveData<Integer>(0);

	private SudokuWorker<Integer> clickWorker = null;

	public void observeNumberData(Consumer<Integer> callback) {
		numberData.observe(callback);
	}

	public void onClickButton() {
		if (clickWorker != null
			&& !clickWorker.isDone() && !clickWorker.isCancelled()) {
			System.out.println("cancelling previous");
			boolean interruptIfRunning = true;
			clickWorker.cancel(interruptIfRunning);
		}
		System.out.println("onClickButton");
		this.clickWorker = new SudokuWorker<Integer>(
			() -> {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {	
					//e.printStackTrace();
				}
				return this.numberData.getData() + 1;
			},
			this.numberData::setData
		);
		this.clickWorker.execute();
	}
}
