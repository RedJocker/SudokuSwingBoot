package org.play.sudokuSwingBoot.gui.utils;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.SwingWorker;

public class SudokuWorker<Return>
	extends SwingWorker<Return, Void> {

	private Supplier<Return> backgroundTask;
	private Consumer<Return> callback;

	public SudokuWorker(Supplier<Return> producer,
		Consumer<Return> onDone
	) {
		this.backgroundTask = producer;
		this.callback = onDone;
	}

	@Override
	protected Return doInBackground() throws Exception {
		return this.backgroundTask.get();
	}

	@Override
	protected void done() {
		try {
			this.callback.accept(this.get());
		} catch(InterruptedException|CancellationException exception){
			//ignore
		} catch (ExecutionException exception) {
			//exception.printStackTrace();
		}
	}
}
