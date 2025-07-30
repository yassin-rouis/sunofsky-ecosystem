package net.joueuranonyme.sunofsky.items.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import net.joueuranonyme.sunofsky.items.Main;
import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class SuperAxe_Event implements Listener {
	
	private boolean isSameTreeLeaf(Material leaf, Material wood) {
		if(leaf.equals(Material.OAK_LEAVES) && (wood.equals(Material.OAK_WOOD) || wood.equals(Material.OAK_LOG))) return true;
		if(leaf.equals(Material.BIRCH_LEAVES) && (wood.equals(Material.BIRCH_WOOD) || wood.equals(Material.BIRCH_LOG))) return true;
		if(leaf.equals(Material.DARK_OAK_LEAVES) && (wood.equals(Material.DARK_OAK_WOOD) || wood.equals(Material.DARK_OAK_LOG))) return true;
		if((leaf.equals(Material.JUNGLE_LEAVES) || leaf.equals(Material.VINE)) && (wood.equals(Material.JUNGLE_WOOD) || wood.equals(Material.JUNGLE_LOG))) return true;
		if(leaf.equals(Material.SPRUCE_LEAVES) && (wood.equals(Material.SPRUCE_WOOD) || wood.equals(Material.SPRUCE_LOG))) return true;
		if(leaf.equals(Material.ACACIA_LEAVES) && (wood.equals(Material.ACACIA_WOOD) || wood.equals(Material.ACACIA_LOG))) return true;
		return false;
		
	}
	
	private boolean isWood(Material mat) {
		Material[] woods = new Material[] {
			Material.OAK_LOG, Material.OAK_WOOD,
			Material.BIRCH_LOG, Material.BIRCH_WOOD,
			Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD,
			Material.JUNGLE_LOG, Material.JUNGLE_WOOD,
			Material.SPRUCE_LOG, Material.SPRUCE_WOOD,
			Material.ACACIA_LOG, Material.ACACIA_WOOD
		};
		
		return Arrays.asList(woods).contains(mat);
	}
	
	private List<Location> getTree(Location l){
		Location sl = new Location(l.getWorld(), l.getX(), l.getY()+1, l.getZ());
		List<Location> bs = new ArrayList<Location>();
		bs.add(l);
		bs.addAll(getTreeBlocks(sl));
		return bs;
	}
	
	private List<Location> getTreeBlocks(Location l){
		return getTreeBlocks(l, new ArrayList<Location>());
	}
	
	private List<Location> getTreeBlocks(Location l, List<Location> exclude){
		List<Location> tree = new ArrayList<Location>();

		Location sl = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
		
		if(!isWood(l.getBlock().getType())) return tree;
		
		tree.add(sl);
		tree.addAll(getLeafBlocks(sl));
		
		for(int x = -1; x <= 1; x++) {
			for(int y = 0; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					Location nl = new Location(l.getWorld(), l.getX()+x, l.getY()+y, l.getZ()+z);
					if(exclude.contains(nl)) continue;
					exclude.add(nl);
					tree.addAll(getTreeBlocks(nl, exclude));
				}
			}
		}

		return tree;
	}
	
	private List<Location> getLeafBlocks(Location l){
		List<Location> leaves = new ArrayList<Location>();
		int radius = 0;
		Block b = l.getBlock();
		
		switch(b.getType()) {
			case BIRCH_WOOD:
			case BIRCH_LOG: radius = 2; break;
			case OAK_WOOD:
			case OAK_LOG:
			case DARK_OAK_WOOD:
			case DARK_OAK_LOG:
			case ACACIA_WOOD:
			case ACACIA_LOG: radius = 3; break;
			case SPRUCE_WOOD:
			case SPRUCE_LOG: radius = 4; break;
			case JUNGLE_WOOD:
			case JUNGLE_LOG: radius = 6; break;
		}
		
		/*Main.printMc("Radius :" + Integer.toString(radius));*/
		
		for(int x = -radius; x <= radius; x++) {
			for(int y = -radius; y <= radius; y++) {
				for(int z = -radius; z <= radius; z++) {
					Location nl = l.clone();
					nl.add(x,y,z);
					boolean isOk = isSameTreeLeaf(nl.getBlock().getType(), b.getType());/*
					Main.printMc("#LO> X:" + Integer.toString(x) + "\tY:" + Integer.toString(y) + "\tZ:" + Integer.toString(z));
					Main.printMc("#1 > " + nl.getBlock().getType().toString());
					Main.printMc("#2 > " + b.getType());*/
					if(isOk) {
						leaves.add(nl);
					}
				}
			}
		}
		
		return leaves;
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
		if(i == null) return;
		if(!i.hasItemMeta()) return;
		if(!MetadataLib.has(i, "t_isSuperAxe")) return;
		if(!MetadataLib.getBoolean(i, "t_isSuperAxe")) return;
		

		if(!isWood(event.getBlock().getType())) return;
		
		event.setCancelled(false);
		
		List<Location> tree = getTree(event.getBlock().getLocation());
		
		boolean direct = false;

		if(MetadataLib.has(i, "t_isMagnet")) {
			if(MetadataLib.getBoolean(i, "t_isMagnet")) direct = true;
		}
				
		
		for(Location l : tree) {
			// Hey moi-même, ajoute /* à la ligne d'en dessous pour switcher en mode de test !
			Block b = l.getBlock();
			if(direct) {
				for(ItemStack it : b.getDrops(i, event.getPlayer())) {
					event.getPlayer().getInventory().addItem(it);
				}
				b.setType(Material.AIR);
			} else {
				b.breakNaturally(i);
			}
			
			//*/ l.getBlock().setType(Material.RED_WOOL);
		}
	}
}
