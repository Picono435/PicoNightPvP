package com.gmail.picono435.piconightpvp.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class TimeChangedWorldEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private World world;
    private long time;
    private boolean isNight;
    
    public TimeChangedWorldEvent(World newworld, long newtime, boolean isNighta) {
        world = newworld;
        time = newtime;
        isNight = isNighta;
    }

    public World getWorld() {
        return world;
    }
    
    public long getTime() {
        return time;
    }
   
    public boolean isNight() {
    	return isNight;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
