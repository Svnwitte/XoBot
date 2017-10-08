package com.dridia.customListeners.Inventory;


import com.dridia.customListeners.EventDispatcher;
import xobot.script.methods.tabs.Inventory;
import xobot.script.wrappers.interactive.Item;

public class InventoryEventSource implements Runnable {

	private final EventDispatcher dispatcher;
	private final int slotCount;
	private Item[] inventoryCache;


	public InventoryEventSource(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
		this.inventoryCache = new Item[28];
		this.slotCount = 28;

		for (int i = 0; i < slotCount; i++) {
			inventoryCache[i] = Inventory.getItemAt(i);
		}
	}

	@Override
	public void run() {
		while (dispatcher.isRunning()) {
			for (int i = 0; i < slotCount; i++) {
				Item oldItem = inventoryCache[i];
				Item newItem = Inventory.getItemAt(i);
				if (oldItem != null && newItem != null) {
					if (oldItem.getID() == newItem.getID()) {
						if (oldItem.getStack() != newItem.getStack()) {
							dispatcher.fireEvent(new InventoryEvent(i, oldItem, newItem));
							inventoryCache[i] = newItem;
						}
					} else {
						dispatcher.fireEvent(new InventoryEvent(i, oldItem, newItem));
						inventoryCache[i] = newItem;
					}
				} else if (oldItem != null || newItem != null) {
					dispatcher.fireEvent(new InventoryEvent(i, oldItem, newItem));
					inventoryCache[i] = newItem;
				}
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException ignored) {
			}
		}
	}
}