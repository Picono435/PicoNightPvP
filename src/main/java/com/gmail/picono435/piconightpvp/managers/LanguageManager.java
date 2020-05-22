package com.gmail.picono435.piconightpvp.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

public class LanguageManager {
	
	private static FileConfiguration language;
	private static File language_file;
    
    public static String getMessage(String message) {
    	return ChatColor.translateAlternateColorCodes('&', language.getString(message));
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
