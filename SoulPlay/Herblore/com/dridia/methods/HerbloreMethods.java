package com.dridia.methods;

import xobot.script.methods.Packets;
import xobot.script.methods.Widgets;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Time;
import xobot.script.util.Timer;
import xobot.script.wrappers.interactive.Item;
import xobot.script.wrappers.interactive.Player;
import com.dridia.data.Data;

public class HerbloreMethods {

    public static boolean canMix() {
        return Inventory.Contains(Data.mainIng) && Inventory.Contains(Data.secondaryIng) && !areMixing();
    }

    public static void doMix() {
        Item herb = Inventory.getItem(Data.secondaryIng);
        Item vial = Inventory.getItem(Data.mainIng);

        if (herb != null && vial != null) {
            Packets.sendAction(447, herb.getID(), herb.getSlot(), 3214);
            Time.sleep(190, 250);
            Packets.sendAction(870, vial.getID(), vial.getSlot(), 3214);

            Methods.conditionalSleep( new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Widgets.getBackDialogId() == 4429;
                }
            }, 5000);

            if(Widgets.getBackDialogId() == 4429) {
                Packets.sendAction(315, -1, -1, 1747);
                Time.sleep(700, 900);
            }
        }
    }

    private static boolean areMixing(){
        Timer t = new Timer();
        int startCount = Inventory.getAll().length;
        while(t.getElapsed() < 2120){
            if(Player.getMyPlayer().getAnimation() == 363 || Inventory.getAll().length < startCount){
                return true;
            }
            Time.sleep(40, 100);
        }
        return false;
    }
}
