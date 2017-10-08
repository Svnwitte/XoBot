package com.dridia.customListeners.Inventory;

import java.util.EventListener;

public interface InventoryListener extends EventListener {

	void onInventoryChanged(InventoryEvent inventoryEvent);
	
}
