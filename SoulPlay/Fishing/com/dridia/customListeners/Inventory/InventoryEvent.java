package com.dridia.customListeners.Inventory;

import java.util.EventObject;

import xobot.script.wrappers.interactive.Item;

@SuppressWarnings("serial")
public class InventoryEvent extends EventObject {


    private final int itemSlot;
    private final Item oldItem;
    private final Item newItem;
    

    public InventoryEvent(int itemSlot, Item oldItem, Item newItem) {
    	super(itemSlot);
        this.itemSlot = itemSlot;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public Item getOldItem() {
        return oldItem;
    }
    public Item getNewItem() {
        return newItem;
    }

}
