package net.joueuranonyme.sunofsky.items.events;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import net.joueuranonyme.sunofsky.items.addons.ParticleLib;
import net.joueuranonyme.sunofsky.items.addons.Fixer;

public class Commands_Event implements Listener {

	@EventHandler
	public void onPlayerInteractEventRight(PlayerInteractEvent event) {
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		Player p = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		
		if(item == null) return;
		if(!item.hasItemMeta()) return;
		if(!MetadataLib.has(item, "t_rightCommand")) return;
		
		event.setCancelled(true);
		
		String command = MetadataLib.getString(item, "t_rightCommand");
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(console, command
				.replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName()))
				.replaceAll("\\{WORLD\\}", Fixer.fixString(p.getWorld().getName()))
				.replaceAll("\\{X\\}", Double.toString(p.getLocation().getX()))
				.replaceAll("\\{Y\\}", Double.toString(p.getLocation().getY()))
				.replaceAll("\\{Z\\}", Double.toString(p.getLocation().getZ())));
		
		return;
	}

	@EventHandler
	public void onPlayerInteractEventLeft(PlayerInteractEvent event) {
		if(!(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) return;
		
		Player p = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		
		if(item == null) return;
		if(!item.hasItemMeta()) return;
		if(!MetadataLib.has(item, "t_leftCommand")) return;
		
		event.setCancelled(true);
		
		String command = MetadataLib.getString(item, "t_leftCommand");
		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(console, command
				.replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName()))
				.replaceAll("\\{WORLD\\}", Fixer.fixString(p.getWorld().getName()))
				.replaceAll("\\{X\\}", Double.toString(p.getLocation().getX()))
				.replaceAll("\\{Y\\}", Double.toString(p.getLocation().getY()))
				.replaceAll("\\{Z\\}", Double.toString(p.getLocation().getZ())));
		
		return;
	}
}
