package net.joueuranonyme.sunofsky.items.events;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class SuperHoe_Event implements Listener {
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {		
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();

		if(!i.hasItemMeta()) return;
		
		if(!MetadataLib.has(i, "t_isSuperHoe")) return;
		if(!MetadataLib.getBoolean(i, "t_isSuperHoe")) return;
		
		int radius = 3;
		if(MetadataLib.has(event.getPlayer().getInventory().getItemInMainHand(), "radius")) {
			radius = MetadataLib.getInteger(i, "radius");
		}		
		
		Block b = event.getBlock();
		World w = b.getLocation().getWorld();
		Player p = event.getPlayer();
		
		switch(b.getType()) {
			case WHEAT:
			case POTATOES:
			case BEETROOTS:
			case CARROTS:
				event.setCancelled(true);
				break;
			default:
				return;
		}
		
		int mnX = b.getX()-radius,
			mnZ = b.getZ()-radius,
			y = b.getY(),
			mxX = b.getX()+radius,
			mxZ = b.getZ()+radius;
		
		for(int x = mnX; x < mxX+1; x++) {
			for(int z = mnZ; z < mxZ+1; z++) {
				boolean direct = false;
				if(MetadataLib.has(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) {
					if(MetadataLib.getBoolean(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) {
						direct = true;
					}
				}
				
				Block bl = w.getBlockAt(x, y, z);
				
				Material replant;
				switch(bl.getType()) {
					case WHEAT:
					case POTATOES:
					case BEETROOTS:
					case CARROTS:
						replant = bl.getType();
						break;
					default:
						continue;
				}
				
				Ageable age = (Ageable) bl.getBlockData();
				if(age.getAge() != age.getMaximumAge()) continue;
				
				if(direct) {
					Collection<ItemStack> drops = bl.getDrops(i, p);
					
					for(ItemStack item: drops) {
						switch(item.getType()) {
							case WHEAT_SEEDS:
							case CARROT:
							case BEETROOT:
							case POTATO:
								continue;		
						}
						
						p.getInventory().addItem(item);
					}
					
					ItemStack emptyIs = new ItemStack(Material.AIR);
					bl.breakNaturally(emptyIs);
				} else {
					bl.breakNaturally();
				}
				
				bl.setType(replant);
			}
		}
		return;
	}

}
