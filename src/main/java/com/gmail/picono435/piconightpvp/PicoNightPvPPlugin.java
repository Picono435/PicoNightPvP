package com.gmail.picono435.piconightpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.picono435.piconightpvp.api.PicoNightPvPAPI;
import com.gmail.picono435.piconightpvp.events.TimeChangedWorldEvent;
import com.gmail.picono435.piconightpvp.listeners.PluginListeners;
import com.gmail.picono435.piconightpvp.managers.LanguageManager;

public class PicoNightPvPPlugin extends JavaPlugin {
	
	public static Plugin plugin;
	
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		sendConsoleMessage("[PicoNightPvP] Plugin created by: Picono435#2011. Thank you for use it.");
		if(verificarLicenca());
		LanguageManager.createLanguageFile();
		
		//FAZENDO TIMER PARA EVENTO
		new BukkitRunnable() {
			@Override
			public void run() {
				for(String worldname : getConfig().getStringList("pvp-worlds")) {
					if(isDay(worldname) && PicoNightPvPAPI.isNight()) {
						PicoNightPvPAPI.setNight(false);
						TimeChangedWorldEvent event = new TimeChangedWorldEvent(PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname), PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname).getTime(), false);
						Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(event));
					}
					if(!isDay(worldname) && !PicoNightPvPAPI.isNight()) {
						PicoNightPvPAPI.setNight(true);
						TimeChangedWorldEvent event = new TimeChangedWorldEvent(PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname), PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname).getTime(), true);
						Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(event));
					}
				}
			}
		}.runTaskTimerAsynchronously(this, 0, 1);
		
		//REGISTRANDO EVENTOS
		Bukkit.getPluginManager().registerEvents(new PluginListeners(), this);
	}
	
	private static boolean isDay(String worldname) {
		if(Bukkit.getServer().getWorld(worldname) == null) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoNightPvP] We didn't find any world with the name: " + worldname + " so we will say that it's day!");
			return true;
		}
		long time = PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname).getTime();
		
		if(time > 0 && time < 12300) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static void sendConsoleMessage(String message) {
		PicoNightPvPPlugin.getPlugin().getServer().getConsoleSender().sendMessage(message);
	}
	
	private boolean verificarLicenca() {
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] You are using the FREE version of the plugin!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] Want to buy the premium version? Call me on discord!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] My discord is: Picono435#2011");
		return true;
	}
}
