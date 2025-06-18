package org.play.sudokuSwingBoot.gui.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

public class LiveData<T> {
	private T data;
	private ArrayList<Consumer<T>> observers;

	public LiveData(T initialDataValue) {
		this.data = initialDataValue;
		this.observers = new ArrayList<>();
	}

	public void observe(Consumer<T> onChange) {
		this.observers.add(onChange);

		if (SwingUtilities.isEventDispatchThread()) {
			onChange.accept(data);
			return;
		}

		try {
			SwingUtilities.invokeAndWait((Runnable) () -> {
				onChange.accept(data);
			});
		} catch (InterruptedException exception) {
			//ignore
		} catch (InvocationTargetException exception) {
			exception.printStackTrace();
		}
	}

	public void setData(T data, boolean forceUpdate) {
		if (!forceUpdate && this.data == data)
			return;
		this.data = data;
		for (Consumer<T> callback : this.observers) {
			callback.accept(this.data);
		}
	}

	public void setData(T data) {
		this.setData(data, false);
	}

	public T getData() {
		return this.data;
	}
}
