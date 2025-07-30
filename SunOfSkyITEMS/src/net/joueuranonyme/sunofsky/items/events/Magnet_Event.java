package net.joueuranonyme.sunofsky.items.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class Magnet_Event implements Listener {
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

		if(item == null) return;
		if(!item.hasItemMeta()) return;
		
		if(!MetadataLib.has(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) return;
		if(!MetadataLib.getBoolean(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) return;
		
		event.setCancelled(true);
		
		for(ItemStack i: event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer())) {
			event.getPlayer().getInventory().addItem(i);
		}
		
		ItemStack emptyIs = new ItemStack(Material.AIR);
		event.getBlock().breakNaturally(emptyIs);
	}
}
