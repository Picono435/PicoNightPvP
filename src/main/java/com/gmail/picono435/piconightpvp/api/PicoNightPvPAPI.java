package com.gmail.picono435.piconightpvp.api;

public class PicoNightPvPAPI {
	
	private static boolean isNight = false;
	
	public static boolean isNight() {
		return isNight;
	}
	
	public static void setNight(boolean newNight) {
		isNight = newNight;
	}
}
