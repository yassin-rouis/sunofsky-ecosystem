package net.joueuranonyme.sunofsky.items.events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import net.joueuranonyme.sunofsky.mines.events.MinesOnBlockBreakEvent;

public class SuperPickaxe_Event implements Listener {

	@EventHandler(priority=EventPriority.HIGH)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();

		if(event.isCancelled()) return;
		if(i == null) return;
		if(!i.hasItemMeta()) return;
		if(!MetadataLib.has(i, "t_isSuperPickaxe")) return;
		if(!MetadataLib.getBoolean(i, "t_isSuperPickaxe")) return;
		int radius = 3;
		if(MetadataLib.has(event.getPlayer().getInventory().getItemInMainHand(), "radius")) {
			radius = MetadataLib.getInteger(i, "radius");
		}
		
		Block b = event.getBlock();
		World w = b.getLocation().getWorld();
		Player p = event.getPlayer();
		
		int mnX = b.getX()-radius,
			mnY = b.getY()-radius,
			mnZ = b.getZ()-radius,
			mxX = b.getX()+radius,
			mxY = b.getY()+radius,
			mxZ = b.getZ()+radius;
		
		switch(b.getType()) {
			case COBBLESTONE:
			case STONE:
				
			case COAL_ORE:
			case COPPER_ORE:
			case IRON_ORE:
			case GOLD_ORE:
			case DIAMOND_ORE:
			case REDSTONE_ORE:
			case LAPIS_ORE:
			case EMERALD_ORE:
				
			case DEEPSLATE:
			case COBBLED_DEEPSLATE:
				
			case DEEPSLATE_COAL_ORE:
			case DEEPSLATE_COPPER_ORE:
			case DEEPSLATE_IRON_ORE:
			case DEEPSLATE_GOLD_ORE:
			case DEEPSLATE_DIAMOND_ORE:
			case DEEPSLATE_REDSTONE_ORE:
			case DEEPSLATE_LAPIS_ORE:
			case DEEPSLATE_EMERALD_ORE:
			
			case ANDESITE:
			case GRANITE:
			case DIORITE:
			case CALCITE:
			
			case NETHER_GOLD_ORE:
			case NETHER_QUARTZ_ORE:
			case ANCIENT_DEBRIS:
			case NETHERRACK:
			case BASALT:
			case GLOWSTONE:
			case END_STONE:
				
			case SANDSTONE:
			case RED_SANDSTONE:
			case BLACKSTONE:
			case GILDED_BLACKSTONE:
				event.setCancelled(true);
				break;
			
			default:
				return;
		}
		
		boolean useMineHandling = MinesOnBlockBreakEvent.isHandled(b);
		
		for(int x = mnX; x < mxX+1; x++) {
			for(int y = mnY; y < mxY+1; y++) {
				for(int z = mnZ; z < mxZ+1; z++) {
					boolean direct = false;
					if(MetadataLib.has(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) {
						if(MetadataLib.getBoolean(event.getPlayer().getInventory().getItemInMainHand(), "t_isMagnet")) {
							direct = true;
						}
					}
					
					Block bl = w.getBlockAt(x, y, z);
					
					switch(bl.getType()) {
						case COBBLESTONE:
						case STONE:
							
						case COAL_ORE:
						case COPPER_ORE:
						case IRON_ORE:
						case GOLD_ORE:
						case DIAMOND_ORE:
						case REDSTONE_ORE:
						case LAPIS_ORE:
						case EMERALD_ORE:
							
						case DEEPSLATE:
						case COBBLED_DEEPSLATE:
							
						case DEEPSLATE_COAL_ORE:
						case DEEPSLATE_COPPER_ORE:
						case DEEPSLATE_IRON_ORE:
						case DEEPSLATE_GOLD_ORE:
						case DEEPSLATE_DIAMOND_ORE:
						case DEEPSLATE_REDSTONE_ORE:
						case DEEPSLATE_LAPIS_ORE:
						case DEEPSLATE_EMERALD_ORE:
						
						case ANDESITE:
						case GRANITE:
						case DIORITE:
						case CALCITE:
						
						case NETHER_GOLD_ORE:
						case NETHER_QUARTZ_ORE:
						case ANCIENT_DEBRIS:
						case NETHERRACK:
						case BASALT:
						case GLOWSTONE:
						case END_STONE:
							
						case SANDSTONE:
						case RED_SANDSTONE:
						case BLACKSTONE:
						case GILDED_BLACKSTONE:
							break;
						
						default:
							continue;
					}
					
					if(useMineHandling) {
						if(!MinesOnBlockBreakEvent.isHandled(bl)) continue;
					}
					
					if(MinesOnBlockBreakEvent.isHandled(bl)) {
						MinesOnBlockBreakEvent.breakBlock(bl, p);
						continue;
					}
					
					if(direct) {
						for(ItemStack item: bl.getDrops(i, p)) {
							p.getInventory().addItem(item);
						}
						
						ItemStack emptyIs = new ItemStack(Material.AIR);
						bl.breakNaturally(emptyIs);
					} else {
						bl.breakNaturally();
					}
				}
			}
		}
		return;
	}

}
