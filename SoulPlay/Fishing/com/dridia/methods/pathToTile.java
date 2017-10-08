package com.dridia.methods;

import xobot.script.methods.Players;
import xobot.script.wrappers.Tile;

/**
 * Created by mgrimberg on 2017-10-03.
 */
public class pathToTile {

    public Tile[] path;
    private int counter = 0;

    public pathToTile(Tile[] path){
        this.path = path;
    }

    //Return true when destination reached?
    public Tile getNextTile(){
        if(counter < path.length) {
            Tile returnTile = path[counter];
            counter++;
            return returnTile;
        }
        return null;
    }

    public boolean destinationReached(){
        return (walkingMethods.distanceBetween(path[path.length - 1], Players.getMyPlayer().getLocation())) <= 2;
    }

}
