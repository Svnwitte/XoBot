package com.dridia.methods;

import com.dridia.data.Data;
import xobot.script.methods.Packets;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Time;
import xobot.script.wrappers.interactive.Item;

/**
 * Created by mgrimberg on 2017-09-20.
 */
public class DecantingMethods {


    public static boolean canDecant() {
        return Inventory.getCount(Data.finishedId) > 1;
    }

    public static void doDecant() {

        if (Inventory.getItem(Data.finishedId) != null) {
            int i = 3;
            while(Inventory.Contains(Data.finishedId)){
                Item potion = Inventory.getItem(Data.finishedId + ((i%3)*2));
                Packets.sendAction(447, potion.getID(), potion.getSlot(), 3214);
                Time.sleep(300, 400);
                Packets.sendAction(870, Data.finishedId, potion.getSlot()+1, 3214);
                i++;
                Time.sleep(150);//In order to not get kicked out of the game
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return (Inventory.getItemAt(potion.getSlot())).getID() != potion.getID();
                    }
                }, 2500);
            }
        }
    }
}
