package com.gmail.picono435.piconightpvp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gmail.picono435.piconightpvp.api.PicoNightPvPAPI;
import com.gmail.picono435.piconightpvp.commands.NightPvPCommand;
import com.gmail.picono435.piconightpvp.events.TimeChangedWorldEvent;
import com.gmail.picono435.piconightpvp.listeners.PluginListeners;
import com.gmail.picono435.piconightpvp.managers.LanguageManager;

public class PicoNightPvPPlugin extends JavaPlugin {
	
	public static Plugin plugin;
	
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();
		sendConsoleMessage("[PicoNightPvP] Plugin created by: Picono435#2011. Thank you for use it.");
		if(!verificarLicenca()) return;
		sendConsoleMessage(ChatColor.AQUA + "[PicoNightPvP] Creating and configurating the language file selected...");
		LanguageManager.createLanguageFile();
		if(!getConfig().contains("config-version") || !getConfig().getString("config-version").equalsIgnoreCase(getDescription().getVersion())) {
			sendConsoleMessage(ChatColor.YELLOW + "You were using a old configuration file... Updating it and removing comments, for more information check our WIKI.");
			getConfig().options().copyDefaults(true);
			getConfig().set("config-version", getDescription().getVersion());
			saveConfig();
		}
		
		int pluginId = 8043;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("premium_version", () -> "Free"));
		
		//FAZENDO TIMER PARA EVENTO
		sendConsoleMessage(ChatColor.AQUA + "[PicoNightPvP] Finishing enabling the plugin...");
		new BukkitRunnable() {
			@Override
			public void run() {
				for(String worldname : getConfig().getStringList("pvp-worlds")) {
					World world = Bukkit.getWorld(worldname);
					if(world == null) {
						sendConsoleMessage(ChatColor.DARK_RED + "[PicoNightPvP] We didn't find any world with the name: " + worldname + ".");
						return;
					}
					if(!isCurrentlyNight(worldname) && PicoNightPvPAPI.isNight(world)) {
						PicoNightPvPAPI.setNight(false, world);
						TimeChangedWorldEvent event = new TimeChangedWorldEvent(world, world.getTime(), false);
						Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(event));
					}
					if(isCurrentlyNight(worldname) && !PicoNightPvPAPI.isNight(world)) {
						PicoNightPvPAPI.setNight(true, world);
						TimeChangedWorldEvent event = new TimeChangedWorldEvent(world, world.getTime(), true);
						Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(event));
					}
				}
			}
		}.runTaskTimerAsynchronously(this, 0, 1);
		
		//REGISTRANDO EVENTOS
		Bukkit.getPluginManager().registerEvents(new PluginListeners(), this);
		this.getCommand("nightpvp").setExecutor(new NightPvPCommand());
		sendConsoleMessage(ChatColor.GREEN + "[PicoNightPvP] The plugin was succefully enabled.");
		
		checkVersion();
	}
	
	public void onDisable() {
		sendConsoleMessage(ChatColor.GREEN + "[PicoNightPvP] The plugin was succefully disabled.");
	}
	
	private static boolean isCurrentlyNight(String worldname) {
		long time = PicoNightPvPPlugin.getPlugin().getServer().getWorld(worldname).getTime();
		
		if(time > 0 && time < 12300) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static void sendConsoleMessage(String message) {
		PicoNightPvPPlugin.getPlugin().getServer().getConsoleSender().sendMessage(message);
	}
	
	public static boolean isPremium() {
		return false;
	}
	
	private boolean verificarLicenca() {
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] You are using the FREE version of the plugin!");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] Want to buy the premium version? Buy it in our site.");
		sendConsoleMessage(ChatColor.YELLOW + "[PicoNightPvP] Our site is: https://piconodev.tk/plugins/premium");
		return true;
	}
	
	private void checkVersion() {
		String version = "1.0";
		try {
            URL url = new URL("https://api.github.com/repos/Picono435/PicoNightPvP/releases/latest");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(content.toString());
            
            version = (String)json.get("tag_name");
			
		} catch(Exception ex) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoNightPvP] Could not get the lastest version.");
			return;
		}
		try {
			DefaultArtifactVersion pluginVesion = new DefaultArtifactVersion(getPlugin().getDescription().getVersion());
			DefaultArtifactVersion lastestVersion = new DefaultArtifactVersion(version);
			if(lastestVersion.compareTo(pluginVesion) > 0) {
				new BukkitRunnable() {
					public void run() {
						sendConsoleMessage(ChatColor.DARK_RED + "[PicoNightPvP] You are using a old version of the plugin. Please download the new version in our pages.");
						return;
					}
				}.runTaskLater(this, 5L);
			} else {
				new BukkitRunnable() {
					public void run() {
						sendConsoleMessage(ChatColor.GREEN + "[PicoNightPvP] You are using the lastest version of the plugin.");
						return;
					}
				}.runTaskLater(this, 5L);
			}
		} catch (Exception e) {
			sendConsoleMessage(ChatColor.DARK_RED + "[PicoNightPvP] Could not get the lastest version.");
			return;
		}
	}
}
