/**
 * Created by mgrimberg on 2017-10-03.
 */

package com.dridia.methods;

import xobot.script.methods.NPCs;
import xobot.script.methods.Packets;
import xobot.script.methods.Players;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Time;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.Item;
import xobot.script.wrappers.interactive.NPC;

public class fishingMethods {

    private static Tile fairyRing = new Tile(2121, 2121);

    public static boolean shouldFishSmall() {
        return Inventory.getCount(3150) < 26 && !Inventory.Contains(3142);
    }

    public static boolean shouldFishBig() {
        if(Inventory.Contains(3159) && walkingMethods.distanceBetween(new Tile(2901, 3114), Players.getMyPlayer().getLocation()) < 25){
            return true;
        }else if(Inventory.getCount(3150) >= 26){
            return true;
        }else if(Inventory.Contains(3150) && Inventory.Contains(3142)){
            return true;
        }else{
            return false;
        }
    }

    public static void fishSmall() {
        NPC n = NPCs.getNearest(952);
        if(n == null) {
            walkingMethods.walkToSmallFishingSpot();
        }else{
            n.interact(0);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return areFishing();
                }
            }, 2500);
        }
    }

    public static void fishBig() {
        NPC n = NPCs.getNearest(1236);
        if(n == null) {
            walkingMethods.walkToBigFishingSpot();
        }else{
            if(Inventory.Contains(3157) && Inventory.Contains(3150)){ // Add fish to vessel
                Item vessel = Inventory.getItem(3157);
                Item fish = Inventory.getItem(3150);
                Packets.sendAction(447, fish.getID(), fish.getSlot(), 3214);
                Time.sleep(300,400);
                Packets.sendAction(870, vessel.getID(), vessel.getSlot(), 3214);
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Inventory.Contains(3159);
                    }
                }, 2500);
            }else {
                n.interact(0);
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return areFishing();
                    }
                }, 2500);
            }
        }
    }

    public static boolean areFishing(){
        return Players.getMyPlayer().getAnimation() == 621 || Players.getMyPlayer().getAnimation() == 1193 && !Inventory.isFull();
    }
}

