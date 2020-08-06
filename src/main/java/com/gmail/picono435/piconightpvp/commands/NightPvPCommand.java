package com.gmail.picono435.piconightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;

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
				sender.sendMessage("");
				return true;
			}
			return true;
		}
		return false;
	}
}
