package com.dridia.customListeners;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import com.dridia.customListeners.Inventory.InventoryEvent;
import com.dridia.customListeners.Inventory.InventoryEventSource;
import com.dridia.customListeners.Inventory.InventoryListener;


public class EventDispatcher {

	private final List<EventListener> listeners;
	private final Object syncLock = new Object();
	private volatile boolean running;

	public EventDispatcher() {
		this.listeners = new ArrayList<EventListener>();
		this.running = true;

		new Thread(new InventoryEventSource(this)).start();
	}

	public void addListener(EventListener listener) {
		synchronized (syncLock) {
			listeners.add(listener);
		}
	}

	public void removeListener(EventListener listener) {
		synchronized (syncLock) {
			listeners.remove(listener);
		}
	}

	public void clearListeners() {
		synchronized (syncLock) {
			listeners.clear();
		}
	}

	public void fireEvent(EventObject event) {
		synchronized (syncLock) {
			for (EventListener listener : listeners) {
				if (listener instanceof InventoryListener && event instanceof InventoryEvent) {
					((InventoryListener) listener).onInventoryChanged((InventoryEvent) event);
				}
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}