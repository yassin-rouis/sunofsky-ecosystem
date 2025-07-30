package net.joueuranonyme.sunofsky.items.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class InstantBreak_Event implements Listener {
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
		
		ItemStack t = event.getPlayer().getInventory().getItemInMainHand();
		Block b = event.getClickedBlock();
		
		if(t == null) return;
		if(!t.hasItemMeta()) return;
		if(!MetadataLib.has(t, "t_instantBreak")) return;
		if(!MetadataLib.getBoolean(t, "t_instantBreak")) return;
		
		switch(b.getType()) {
			case COAL_ORE:
			case COPPER_ORE:
			case IRON_ORE:
			case GOLD_ORE:
			case DIAMOND_ORE:
			case REDSTONE_ORE:
			case LAPIS_ORE:
			case EMERALD_ORE:
			case DEEPSLATE_COAL_ORE:
			case DEEPSLATE_COPPER_ORE:
			case DEEPSLATE_IRON_ORE:
			case DEEPSLATE_GOLD_ORE:
			case DEEPSLATE_DIAMOND_ORE:
			case DEEPSLATE_REDSTONE_ORE:
			case DEEPSLATE_LAPIS_ORE:
			case DEEPSLATE_EMERALD_ORE:
			case NETHER_GOLD_ORE:
			case NETHER_QUARTZ_ORE:
			case ANCIENT_DEBRIS:
			case BEDROCK:
				return;
		}
		
		event.setCancelled(true);
		
		if(!MetadataLib.has(t, "t_isMagnet")) {
			try {
				if(!MetadataLib.getBoolean(t, "t_isMagnet")) {
					for(ItemStack it : b.getDrops(t, event.getPlayer())) {
						event.getPlayer().getInventory().addItem(it);
					}
					b.setType(Material.AIR);
				} else {
					b.breakNaturally(t);
				}
			} catch (Exception e) {
				b.breakNaturally(t);
			}
		} else {
			b.breakNaturally(t);
		}
	}
}
