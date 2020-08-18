package com.gmail.picono435.piconightpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;
import com.gmail.picono435.piconightpvp.api.PicoNightPvPAPI;
import com.gmail.picono435.piconightpvp.managers.LanguageManager;

public class NightPvPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("nightpvp")) {
			if(!sender.hasPermission("piconightpvp.toggle")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9PicoNightPvP&8]&f PicoNightPvP v" + PicoNightPvPPlugin.getPlugin().getDescription().getVersion() + ". (https://discord.gg/wQj53Hy)"));
				return true;
			}
			if(!(sender instanceof Player)) {
				return true;
			}
			Player p = (Player)sender;
			if(args.length < 1) {
				p.sendMessage(LanguageManager.getMessage("help-command", (Player)sender));
				return true;
			}
			if(args[0].equals("enable")) {
				if(PicoNightPvPAPI.canPvP(p.getWorld())) {
					p.sendMessage(ChatColor.RED + "The PvP is already enabled.");
					return true;
				}
				Bukkit.broadcastMessage(LanguageManager.getMessage("forced-enable-command", (Player)sender));
				PicoNightPvPAPI.setCanPvP(1, p.getWorld());
				return true;
			}
			
			if(args[0].equals("disable")) {
				if(!PicoNightPvPAPI.canPvP(p.getWorld())) {
					p.sendMessage(ChatColor.RED + "The PvP is already disabled.");
					return true;
				}
				Bukkit.broadcastMessage(LanguageManager.getMessage("forced-disable-command", (Player)sender));
				PicoNightPvPAPI.setCanPvP(2, p.getWorld());
				return true;
			}
			
			p.sendMessage(LanguageManager.getMessage("help-command", (Player)sender));
			return true;
		}
		return false;
	}
}
