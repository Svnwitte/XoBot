package com.dridia.methods;

import xobot.script.methods.Bank;
import xobot.script.methods.NPCs;
import com.dridia.data.Data;
import xobot.script.methods.tabs.Inventory;
import xobot.script.wrappers.interactive.Item;
import xobot.script.wrappers.interactive.NPC;
/**
 * Created by mgrimberg on 2017-10-05.
 */
public class bankingMethods {

    public static boolean shouldBank(){
        return Inventory.getCount(3150) < 1 && Inventory.Contains(3157);
    }

    public static void doBank(){
        NPC n = NPCs.getNearest(494);
        if(n != null){
            n.interact(1);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Bank.isOpen();
                }
            }, 5000);
            Bank.depositAllExcept(303, 3157);

            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount(3142) < 1;
                }
            }, 5000);
        }else{
            //Found no bank. Teleport home?? This shouldn't even happend
        }

    }

}
