package com.gmail.picono435.piconightpvp.api;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

public class PicoNightPvPAPI {
	
	private static boolean isNight = false;
	private static int canPvP = 0;
	
	public static boolean isNight() {
		return isNight;
	}
	
	public static void setNight(boolean newNight) {
		isNight = newNight;
	}
	
	public static boolean canPvP() {
		if(canPvP == 0) {
			return isNight != PicoNightPvPPlugin.getPlugin().getConfig().getBoolean("block-pvp-night");
		} else {
			return canPvP == 1;
		}
	}
	
	public static void setCanPvP(int status) {
		canPvP = status;
	}
}
