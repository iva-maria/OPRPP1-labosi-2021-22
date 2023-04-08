package hr.fer.oprpp1.hw07.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {

	private List<Integer> numbers;
	
	private List<ListDataListener> listeners;
	
	public PrimListModel() {
		this.numbers = new ArrayList<>();
		this.listeners = new ArrayList<>();
		
		numbers.add(1);
	}
	
	public void next() {
		int currentNumber = numbers.get(numbers.size() - 1) + 1;
		while(true) {
			if(isPrime(currentNumber)) break;
			currentNumber++;
		}
		
		numbers.add(currentNumber);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize() - 1, getSize() - 1);
		for(ListDataListener l : listeners) l.intervalAdded(event);
	}
	
	private boolean isPrime(int number) {
		for(int i = 2; i <= number / 2; i++) {
			if(number % i == 0) return false;
		}
		return true;
	}

	@Override
	public int getSize() {
		return numbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return numbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
}
