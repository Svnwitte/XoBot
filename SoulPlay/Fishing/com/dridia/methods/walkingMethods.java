/**
 * Created by mgrimberg on 2017-10-03.
 */

package com.dridia.methods;

import xobot.client.Npc;
import xobot.script.methods.*;
import xobot.script.methods.input.KeyBoard;
import xobot.script.methods.tabs.Inventory;
import xobot.script.util.Time;
import xobot.script.wrappers.Tile;
import xobot.script.wrappers.interactive.GameObject;
import xobot.script.wrappers.interactive.NPC;
import xobot.script.wrappers.interactive.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class walkingMethods {

    private static Tile fairyRing = new Tile(3129, 3497);
    private static Tile[] pathToRing = {new Tile(3103, 3504), new Tile(3117, 3516), new Tile(3135, 3516), fairyRing};

    public static void walkToSmallFishingSpot() {
            GameObject g = GameObjects.getNearest(14127);
            if(g != null && !new Tile(2811, 3016).isReachable()) {
                Packets.sendAction(502, g.uid, g.getX(), g.getY(), g.getId(), 1);
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return GameObjects.getNearest(12128) != null;
                    }
                }, 10000);
            }else if ((g = GameObjects.getNearest(12128)) != null) {
                Packets.sendAction(502, g.uid, g.getX(), g.getY(), g.getId(), 1);
                Time.sleep(2000);
                KeyBoard.typeWord("333", true);
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                 public boolean isValid() {
                    return new Tile(2811, 3016).isReachable();
                }
                }, 10000);
            }else {
                Walking.walkTo(new Tile(2811, 3016));
                Methods.conditionalSleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return distanceBetween(new Tile(2811, 3016), Players.getMyPlayer().getLocation()) <= 2;
                    }
                }, 20000);
            }
    }

    public static void walkToBigFishingSpot() {
        GameObject g = GameObjects.getNearest(14127);
        if(g != null) {
            Packets.sendAction(502, g.uid, g.getX(), g.getY(), g.getId(), 1);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return GameObjects.getNearest(12128) != null;
                }
            }, 10000);
        }else if ((g = GameObjects.getNearest(12128)) != null) {
            Packets.sendAction(502, g.uid, g.getX(), g.getY(), g.getId(), 1);
            Time.sleep(2000);
            KeyBoard.typeWord("231", true);
            Methods.conditionalSleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                        return new Tile(2899, 3118).isReachable();
                    }
            }, 10000);
        }
    }

    public static double distanceBetween(Tile a, Tile b){
        int x = b.getX() - a.getX();
        int y = b.getY() - a.getY();
        return Math.sqrt((x * x) + (y * y));

    }
}

