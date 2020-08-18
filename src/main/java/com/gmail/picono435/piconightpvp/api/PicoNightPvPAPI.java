package com.gmail.picono435.piconightpvp.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

public class PicoNightPvPAPI {
	
	private static Map<World, Boolean> nightWorlds = new HashMap<World, Boolean>();
	private static Map<World, Integer> canPvPWorlds = new HashMap<World, Integer>();
	
	/**
	 * Checks if it's night or not
	 * 
	 * @return true if it's night false if not
	 * @param world the world to execute this action
	 */
	public static boolean isNight(World world) {
		if(!nightWorlds.containsKey(world)) {
			System.out.println("NOT FOUND ;(");
			return false;
		}
		return nightWorlds.get(world);
	}
	
	/**
	 * Sets if it's night or not
	 * 
	 * @param newNight if it's night or not
	 * @param world the world to execute this action
	 */
	public static void setNight(boolean newNight, World world) {
		if(nightWorlds.containsKey(world)) {
			nightWorlds.replace(world, newNight);
			return;
		}
		nightWorlds.put(world, newNight);
	}
	
	/**
	 * Checks if PvP is allowed or not
	 * 
	 * @return true if you can pvp, false if not
	 * @param world the world to execute this action
	 * 
	 */
	public static boolean canPvP(World world) {
		int canPvP = 0;
		if(canPvPWorlds.containsKey(world)) {
			canPvP = canPvPWorlds.get(world);
		}
		boolean isNight = false;
		if(nightWorlds.containsKey(world)) {
			isNight = nightWorlds.get(world);
		}
		if(canPvP == 0) {
			return isNight != PicoNightPvPPlugin.getPlugin().getConfig().getBoolean("block-pvp-night");
		} else {
			return canPvP == 1;
		}
	}
	
	/**
	 * Sets if you can or cannot pvp
	 * 
	 * @param status the new status of canPvP
	 * @param world the world to execute this action
	 */
	public static void setCanPvP(int status, World world) {
		if(canPvPWorlds.containsKey(world)) {
			canPvPWorlds.replace(world, status);
			return;
		}
		canPvPWorlds.put(world, status);
	}
}
