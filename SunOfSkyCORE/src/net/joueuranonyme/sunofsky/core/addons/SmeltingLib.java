package net.joueuranonyme.sunofsky.core.addons;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/*
 * SunOfSky - JoueurAnonyme 2022 Ⓒ Copyright
 * All Rights Reserved
 *  
 */


public class SmeltingLib {
	public static Material getSmelted(Material material) {
		switch(material) {
			// ===== FOOD =====
			case PORKCHOP -> {return Material.COOKED_PORKCHOP;}
			case BEEF -> {return Material.COOKED_BEEF;}
			case CHICKEN -> {return Material.COOKED_CHICKEN;}
			case COD -> {return Material.COOKED_COD;}
			case SALMON -> {return Material.COOKED_SALMON;}
			case POTATO -> {return Material.BAKED_POTATO;}
			case MUTTON -> {return Material.COOKED_MUTTON;}
			case RABBIT -> {return Material.COOKED_RABBIT;}
			case KELP -> {return Material.DRIED_KELP;}
			
			// ===== ORES =====
			case RAW_COPPER -> {return Material.COPPER_INGOT;}
			case RAW_IRON -> {return Material.IRON_INGOT;}
			case RAW_GOLD -> {return Material.GOLD_INGOT;}
			case NETHER_GOLD_ORE -> {return Material.GOLD_INGOT;}
			case ANCIENT_DEBRIS -> {return Material.NETHERITE_SCRAP;}

			case COPPER_ORE -> {return Material.COPPER_INGOT;}
			case DIAMOND_ORE -> {return Material.DIAMOND;}
			case GOLD_ORE -> {return Material.GOLD_INGOT;}
			case IRON_ORE -> {return Material.IRON_INGOT;}
			case LAPIS_ORE -> {return Material.LAPIS_LAZULI;}
			case REDSTONE_ORE -> {return Material.REDSTONE;}
			case COAL_ORE -> {return Material.COAL;}
			case EMERALD_ORE -> {return Material.EMERALD;}
			case NETHER_QUARTZ_ORE -> {return Material.QUARTZ;}

			case DEEPSLATE_COPPER_ORE -> {return Material.COPPER_INGOT;}
			case DEEPSLATE_DIAMOND_ORE -> {return Material.DIAMOND;}
			case DEEPSLATE_GOLD_ORE -> {return Material.GOLD_INGOT;}
			case DEEPSLATE_IRON_ORE -> {return Material.IRON_INGOT;}
			case DEEPSLATE_LAPIS_ORE -> {return Material.LAPIS_LAZULI;}
			case DEEPSLATE_REDSTONE_ORE -> {return Material.REDSTONE;}
			case DEEPSLATE_COAL_ORE -> {return Material.COAL;}
			case DEEPSLATE_EMERALD_ORE -> {return Material.EMERALD;}
			
			// ===== Furnace-Only =====
			case SAND -> {return Material.GLASS;}		
			case RED_SAND -> {return Material.GLASS;}
			case COBBLESTONE -> {return Material.STONE;}
			case SANDSTONE -> {return Material.SMOOTH_SANDSTONE;}
			case RED_SANDSTONE -> {return Material.SMOOTH_RED_SANDSTONE;}
			case STONE -> {return Material.SMOOTH_STONE;}
			case QUARTZ_BLOCK -> {return Material.SMOOTH_QUARTZ;}
			case CLAY_BALL -> {return Material.BRICK;}
			case NETHER_BRICKS -> {return Material.CRACKED_NETHER_BRICKS;}
			case BASALT -> {return Material.SMOOTH_BASALT;}
			case CLAY -> {return Material.TERRACOTTA;}
			case STONE_BRICKS -> {return Material.CRACKED_STONE_BRICKS;}
			case POLISHED_BLACKSTONE_BRICKS -> {return Material.CRACKED_POLISHED_BLACKSTONE_BRICKS;}
			case COBBLED_DEEPSLATE -> {return Material.DEEPSLATE;}
			case DEEPSLATE_BRICKS -> {return Material.CRACKED_DEEPSLATE_BRICKS;}
			case DEEPSLATE_TILES -> {return Material.CRACKED_DEEPSLATE_TILES;}

			case WHITE_TERRACOTTA -> {return Material.WHITE_GLAZED_TERRACOTTA;}
			case ORANGE_TERRACOTTA -> {return Material.ORANGE_GLAZED_TERRACOTTA;}
			case PINK_TERRACOTTA -> {return Material.PINK_GLAZED_TERRACOTTA;}
			case LIGHT_BLUE_TERRACOTTA -> {return Material.LIGHT_BLUE_GLAZED_TERRACOTTA;}
			case YELLOW_TERRACOTTA -> {return Material.YELLOW_GLAZED_TERRACOTTA;}
			case LIME_TERRACOTTA -> {return Material.LIME_GLAZED_TERRACOTTA;}
			case MAGENTA_TERRACOTTA -> {return Material.MAGENTA_GLAZED_TERRACOTTA;}
			case GRAY_TERRACOTTA -> {return Material.GRAY_GLAZED_TERRACOTTA;}
			case LIGHT_GRAY_TERRACOTTA -> {return Material.LIGHT_GRAY_GLAZED_TERRACOTTA;}
			case CYAN_TERRACOTTA -> {return Material.CYAN_GLAZED_TERRACOTTA;}
			case PURPLE_TERRACOTTA -> {return Material.PURPLE_GLAZED_TERRACOTTA;}
			case BLUE_TERRACOTTA -> {return Material.BLUE_GLAZED_TERRACOTTA;}
			case BROWN_TERRACOTTA -> {return Material.BROWN_GLAZED_TERRACOTTA;}
			case GREEN_TERRACOTTA -> {return Material.GREEN_GLAZED_TERRACOTTA;}
			case RED_TERRACOTTA -> {return Material.RED_GLAZED_TERRACOTTA;}
			case BLACK_TERRACOTTA -> {return Material.BLACK_GLAZED_TERRACOTTA;}

			case CACTUS -> {return Material.GREEN_DYE;}
			
			case ACACIA_LOG -> {return Material.CHARCOAL;}
			case BIRCH_LOG -> {return Material.CHARCOAL;}
			case DARK_OAK_LOG -> {return Material.CHARCOAL;}
			case JUNGLE_LOG -> {return Material.CHARCOAL;}
			case OAK_LOG -> {return Material.CHARCOAL;}
			case SPRUCE_LOG -> {return Material.CHARCOAL;}
			
			case STRIPPED_ACACIA_LOG -> {return Material.CHARCOAL;}
			case STRIPPED_BIRCH_LOG -> {return Material.CHARCOAL;}
			case STRIPPED_DARK_OAK_LOG -> {return Material.CHARCOAL;}
			case STRIPPED_JUNGLE_LOG -> {return Material.CHARCOAL;}
			case STRIPPED_OAK_LOG -> {return Material.CHARCOAL;}
			case STRIPPED_SPRUCE_LOG -> {return Material.CHARCOAL;}
			
			case ACACIA_WOOD -> {return Material.CHARCOAL;}
			case BIRCH_WOOD -> {return Material.CHARCOAL;}
			case DARK_OAK_WOOD -> {return Material.CHARCOAL;}
			case JUNGLE_WOOD -> {return Material.CHARCOAL;}
			case OAK_WOOD -> {return Material.CHARCOAL;}
			case SPRUCE_WOOD -> {return Material.CHARCOAL;}
			
			case STRIPPED_ACACIA_WOOD -> {return Material.CHARCOAL;}
			case STRIPPED_BIRCH_WOOD -> {return Material.CHARCOAL;}
			case STRIPPED_DARK_OAK_WOOD -> {return Material.CHARCOAL;}
			case STRIPPED_JUNGLE_WOOD -> {return Material.CHARCOAL;}
			case STRIPPED_OAK_WOOD -> {return Material.CHARCOAL;}
			case STRIPPED_SPRUCE_WOOD -> {return Material.CHARCOAL;}
			
			case CHORUS_FRUIT -> {return Material.POPPED_CHORUS_FRUIT;}
			case WET_SPONGE -> {return Material.SPONGE;}
			
			case SEA_PICKLE -> {return Material.LIME_DYE;}

			case IRON_SWORD -> {return Material.IRON_NUGGET;}
			case IRON_PICKAXE -> {return Material.IRON_NUGGET;}
			case IRON_AXE -> {return Material.IRON_NUGGET;}
			case IRON_SHOVEL -> {return Material.IRON_NUGGET;}
			case IRON_HOE -> {return Material.IRON_NUGGET;}
			case CHAINMAIL_HELMET -> {return Material.IRON_NUGGET;}
			case CHAINMAIL_CHESTPLATE -> {return Material.IRON_NUGGET;}
			case CHAINMAIL_LEGGINGS -> {return Material.IRON_NUGGET;}
			case CHAINMAIL_BOOTS -> {return Material.IRON_NUGGET;}
			case IRON_HELMET -> {return Material.IRON_NUGGET;}
			case IRON_CHESTPLATE -> {return Material.IRON_NUGGET;}
			case IRON_LEGGINGS -> {return Material.IRON_NUGGET;}
			case IRON_BOOTS -> {return Material.IRON_NUGGET;}
			case IRON_HORSE_ARMOR -> {return Material.IRON_NUGGET;}

			case GOLDEN_SWORD -> {return Material.GOLD_NUGGET;}
			case GOLDEN_PICKAXE -> {return Material.GOLD_NUGGET;}
			case GOLDEN_AXE -> {return Material.GOLD_NUGGET;}
			case GOLDEN_SHOVEL -> {return Material.GOLD_NUGGET;}
			case GOLDEN_HOE -> {return Material.GOLD_NUGGET;}
			case GOLDEN_HELMET -> {return Material.GOLD_NUGGET;}
			case GOLDEN_CHESTPLATE -> {return Material.GOLD_NUGGET;}
			case GOLDEN_LEGGINGS -> {return Material.GOLD_NUGGET;}
			case GOLDEN_BOOTS -> {return Material.GOLD_NUGGET;}
			case GOLDEN_HORSE_ARMOR -> {return Material.GOLD_NUGGET;}
			
			default -> {return null;}
		}
	}
	
	public static boolean isSmeltable(Material material) {
		if(material == null) return false;
		return true;
	}
	
	public static boolean isSmeltable(ItemStack item) {
		if(item == null) return false;
		Material material = getSmelted(item.getType());
		if(material == null) return false;
		return true;
	}
	
	public static ItemStack getSmelted(ItemStack item) {
		if(item == null) return null;
		Material material = getSmelted(item.getType());
		if(material == null) return item;
		item.setType(material);
		return item;
	}
	
	public static int smelt(Inventory inv) {
		return smelt(inv, true);
	}
	
	public static int smelt(Inventory inv, boolean advancedCounting) {
		int t = 0;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack it = getSmelted(inv.getItem(i));
			if(it == null) continue;
			if(advancedCounting) {
				t += it.getAmount();
			} else {
				t++;
			}
			inv.setItem(i, it);
		}
		return t;
	}
	
	public static int howMuchSmeltable(Inventory inv) {
		return howMuchSmeltable(inv, true);
	}
	
	public static int howMuchSmeltable(Inventory inv, boolean advancedCounting) {
		int t = 0;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack it = inv.getItem(i);
			if(!isSmeltable(it)) continue;
			if(advancedCounting) {
				t += it.getAmount();
			} else {
				t++;
			}
		}
		return t;
	}
}
