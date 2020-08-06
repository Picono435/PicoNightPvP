package com.gmail.picono435.piconightpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;
import com.gmail.picono435.piconightpvp.api.PicoNightPvPAPI;
import com.gmail.picono435.piconightpvp.managers.LanguageManager;

import net.md_5.bungee.api.ChatColor;

public class NightPvPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("nightpvp")) {
			if(!sender.hasPermission("piconightpvp.toggle")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9PicoNightPvP&8]&f PicoNightPvP v" + PicoNightPvPPlugin.getPlugin().getDescription().getVersion() + "."));
				return true;
			}
			if(args.length < 1) {
				if(sender instanceof Player) {
					sender.sendMessage(LanguageManager.getMessage("help-command", (Player)sender));
				} else {
					sender.sendMessage(LanguageManager.getMessage("help-command"));
				}
				return true;
			}
			if(args[0].equals("enable")) {
				if(PicoNightPvPAPI.canPvP()) {
					sender.sendMessage(ChatColor.RED + "The PvP is already enabled.");
					return true;
				}
				if(sender instanceof Player) {
					Bukkit.broadcastMessage(LanguageManager.getMessage("forced-enable-command", (Player)sender));
				} else {
					Bukkit.broadcastMessage(LanguageManager.getMessage("forced-enable-command"));
				}
				PicoNightPvPAPI.setCanPvP(1);
				return true;
			}
			
			if(args[0].equals("disable")) {
				if(!PicoNightPvPAPI.canPvP()) {
					sender.sendMessage(ChatColor.RED + "The PvP is already disabled.");
					return true;
				}
				if(sender instanceof Player) {
					Bukkit.broadcastMessage(LanguageManager.getMessage("forced-disable-command", (Player)sender));
				} else {
					Bukkit.broadcastMessage(LanguageManager.getMessage("forced-disable-command"));
				}
				PicoNightPvPAPI.setCanPvP(2);
				return true;
			}
			
			if(sender instanceof Player) {
				sender.sendMessage(LanguageManager.getMessage("help-command", (Player)sender));
			} else {
				sender.sendMessage(LanguageManager.getMessage("help-command"));
			}
			return true;
		}
		return false;
	}
}
