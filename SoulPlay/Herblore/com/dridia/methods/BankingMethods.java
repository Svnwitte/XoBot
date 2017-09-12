package com.dridia.methods;

import xobot.script.methods.Bank;
import xobot.script.methods.GameObjects;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Time;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.Item;
import com.dridia.data.Data;

public class BankingMethods {

    public static boolean canBank() {
        final GameObject bank = GameObjects.getNearest(26972);
        return bank != null && bank.getDistance() <= 3;
    }

    public static boolean needBank() {
        return (!Inventory.Contains(Data.mainIng) || !Inventory.Contains(Data.secondaryIng));
    }

    public static void doBank() {
        final GameObject bank = GameObjects.getNearest(26972);

        if (bank != null && bank.getDistance() <= 3 && !Bank.isOpen()) {
            bank.interact("Bank");
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Bank.isOpen();
                }
            }, 5000);
        }

        if (Bank.isOpen()) {
            if (Inventory.getCount() > 0) {
                Bank.depositAll();
                Time.sleep(490, 590);
            }
            if ((!Inventory.Contains(Data.mainIng) && !Inventory.Contains(Data.secondaryIng))) {
                Item herb = Bank.getItem(Data.secondaryIng);
                Item vial = Bank.getItem(Data.mainIng);
                if (herb != null && vial != null) {
                    Bank.withdraw(Data.mainIng, 14);
                    Methods.conditionalSleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Inventory.Contains(Data.mainIng);
                        }
                    }, 3000);
                    Bank.withdraw(Data.secondaryIng, 14);
                    Methods.conditionalSleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Inventory.Contains(Data.secondaryIng);
                        }
                    }, 3000);
                }else{
                    Data.outOfSupplies = true;
                }
            }
        }
    }
}
