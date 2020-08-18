package com.gmail.picono435.piconightpvp.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

public class PicoNightPvPAPI {
	
	private Map<World, Boolean> nightWorlds = new HashMap<World, Boolean>();
	private Map<World, Integer> canPvPWorlds = new HashMap<World, Integer>();
	
	/**
	 * Checks if it's night or not
	 * 
	 * @return true if it's night false if not
	 */
	public boolean isNight(World world) {
		return nightWorlds.get(world);
	}
	
	/**
	 * Sets if it's night or not
	 * 
	 * @return true if it's night false if not
	 */
	public void setNight(boolean newNight, World world) {
		if(nightWorlds.containsKey(world)) {
			nightWorlds.remove(world);
		}
		nightWorlds.put(world, newNight);
	}
	
	public boolean canPvP(World world) {
		int canPvP = canPvPWorlds.get(world);
		boolean isNight = nightWorlds.get(world);
		if(canPvP == 0) {
			return isNight != PicoNightPvPPlugin.getPlugin().getConfig().getBoolean("block-pvp-night");
		} else {
			return canPvP == 1;
		}
	}
	
	public void setCanPvP(int status, World world) {
		if(canPvPWorlds.containsKey(world)) {
			canPvPWorlds.remove(world);
		}
		canPvPWorlds.put(world, status);
	}
}
