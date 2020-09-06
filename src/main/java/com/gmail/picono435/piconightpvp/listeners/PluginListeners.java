package com.gmail.picono435.piconightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.picono435.piconightpvp.PicoNightPvPPlugin;
import com.gmail.picono435.piconightpvp.api.PicoNightPvPAPI;
import com.gmail.picono435.piconightpvp.events.TimeChangedWorldEvent;
import com.gmail.picono435.piconightpvp.managers.LanguageManager;

public class PluginListeners implements Listener {
	
	String w = "Picono435EPro";
	
	@EventHandler()
	public void onBecameDayChange(TimeChangedWorldEvent e) {
		if(!PicoNightPvPPlugin.getPlugin().getConfig().getStringList("pvp-worlds").contains(e.getWorld().getName())) return;
		if(w.equals("Picono435EPro")) {
			w = e.getWorld().getName();
		}
		if(!w.equals(e.getWorld().getName())) return;
		PicoNightPvPAPI.setCanPvP(0, e.getWorld());
		if(e.isNight()) {
			Bukkit.broadcastMessage(LanguageManager.getMessage("became-night"));
			return;
		} else {
			Bukkit.broadcastMessage(LanguageManager.getMessage("became-day"));
			return;
		}
	}
	
	@EventHandler()
	public void onTryKill(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		if(!PicoNightPvPPlugin.getPlugin().getConfig().getStringList("pvp-worlds").contains(e.getEntity().getWorld().getName())) return;
		if(!PicoNightPvPAPI.canPvP(e.getDamager().getWorld())) {
			Player p = (Player) e.getDamager();
			p.sendMessage(LanguageManager.getMessage("pvp-disabled", p));
			e.setCancelled(true);
		}
	}
	
	@EventHandler()
	public void onTryDamage(EntityDamageEvent e) {
		if(!PicoNightPvPPlugin.getPlugin().getConfig().getBoolean("allow-damage")) return;
		if(!(e.getEntity() instanceof Player)) return;
		if(e.getCause() == DamageCause.ENTITY_ATTACK) return;
		if(!PicoNightPvPPlugin.getPlugin().getConfig().getStringList("pvp-worlds").contains(e.getEntity().getWorld().getName())) return;
		if(!PicoNightPvPAPI.canPvP(e.getEntity().getWorld())) {
			Player p = (Player) e.getEntity();
			p.sendMessage(LanguageManager.getMessage("pvp-disabled", p));
			e.setCancelled(true);
		}
	}
}
