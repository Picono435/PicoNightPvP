package com.gmail.picono435.piconightpvp.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

import me.clip.placeholderapi.PlaceholderAPI;

public class LanguageManager {
	
	private static FileConfiguration language;
	private static File language_file;
    
	public static String getMessage(String message) {
    	String chat = ChatColor.translateAlternateColorCodes('&', language.getString(message));
    	
    	if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
    		chat = PlaceholderAPI.setPlaceholders(null, chat);
    	}
    	
    	return chat;
    }
	
    public static String getMessage(String message, Player p) {
    	String chat = ChatColor.translateAlternateColorCodes('&', language.getString(message));
    	
    	if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
    		chat = PlaceholderAPI.setPlaceholders(p, chat);
    	}
    	
    	return chat;
    }
    
    public static void createLanguageFile() {
    	String lang = PicoNightPvPPlugin.getPlugin().getConfig().getString("lang");
    	language_file = new File(PicoNightPvPPlugin.getPlugin().getDataFolder(), "langs" + File.separatorChar + lang + ".yml");
        if (!language_file.exists()) {
        	language_file.getParentFile().mkdirs();
            PicoNightPvPPlugin.getPlugin().saveResource("langs" + File.separatorChar + lang + ".yml", false);
         }

        language = new YamlConfiguration();
        try {
            language.load(language_file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
