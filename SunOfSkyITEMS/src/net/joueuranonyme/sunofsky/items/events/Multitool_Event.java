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

public class Multitool_Event implements Listener {
	@SuppressWarnings("incomplete-switch")
	public static void change(ItemStack s, String type) {
		Material m = s.getType();
		switch(type) {
			case "axe" -> {
				switch(m) {
					case WOODEN_PICKAXE:
					case WOODEN_SHOVEL:
					case WOODEN_HOE: m = (Material.WOODEN_AXE); break;
					
					case STONE_PICKAXE:
					case STONE_SHOVEL:
					case STONE_HOE: m = (Material.STONE_AXE); break;
					
					case IRON_PICKAXE:
					case IRON_SHOVEL:
					case IRON_HOE: m = (Material.IRON_AXE); break;
					
					case GOLDEN_PICKAXE:
					case GOLDEN_SHOVEL:
					case GOLDEN_HOE: m = (Material.GOLDEN_AXE); break;
					
					case DIAMOND_PICKAXE:
					case DIAMOND_SHOVEL:
					case DIAMOND_HOE: m = (Material.DIAMOND_AXE); break;
					
					case NETHERITE_PICKAXE:
					case NETHERITE_SHOVEL:
					case NETHERITE_HOE: m = (Material.NETHERITE_AXE); break;
				}
			}
			case "pickaxe" -> {
				switch(m) {
					case WOODEN_AXE:
					case WOODEN_SHOVEL:
					case WOODEN_HOE: m = (Material.WOODEN_PICKAXE); break;
					
					case STONE_AXE:
					case STONE_SHOVEL:
					case STONE_HOE: m = (Material.STONE_PICKAXE); break;
					
					case IRON_AXE:
					case IRON_SHOVEL:
					case IRON_HOE: m = (Material.IRON_PICKAXE); break;
					
					case GOLDEN_AXE:
					case GOLDEN_SHOVEL:
					case GOLDEN_HOE: m = (Material.GOLDEN_PICKAXE); break;
					
					case DIAMOND_AXE:
					case DIAMOND_SHOVEL:
					case DIAMOND_HOE: m = (Material.DIAMOND_PICKAXE); break;
					
					case NETHERITE_AXE:
					case NETHERITE_SHOVEL:
					case NETHERITE_HOE: m = (Material.NETHERITE_PICKAXE); break;
				}
			}
			case "shovel" -> {
				switch(m) {
					case WOODEN_AXE:
					case WOODEN_PICKAXE:
					case WOODEN_HOE: m = (Material.WOODEN_SHOVEL); break;
					
					case STONE_AXE:
					case STONE_PICKAXE:
					case STONE_HOE: m = (Material.STONE_SHOVEL); break;
					
					case IRON_AXE:
					case IRON_PICKAXE:
					case IRON_HOE: m = (Material.IRON_SHOVEL); break;
					
					case GOLDEN_AXE:
					case GOLDEN_PICKAXE:
					case GOLDEN_HOE: m = (Material.GOLDEN_SHOVEL); break;
					
					case DIAMOND_AXE:
					case DIAMOND_PICKAXE:
					case DIAMOND_HOE: m = (Material.DIAMOND_SHOVEL); break;
					
					case NETHERITE_AXE:
					case NETHERITE_PICKAXE:
					case NETHERITE_HOE: m = (Material.NETHERITE_SHOVEL); break;
				}
			}
			case "hoe" -> {
				switch(m) {
					case WOODEN_AXE:
					case WOODEN_PICKAXE:
					case WOODEN_SHOVEL: m = (Material.WOODEN_HOE); break;
					
					case STONE_AXE:
					case STONE_PICKAXE:
					case STONE_SHOVEL: m = (Material.STONE_HOE); break;
					
					case IRON_AXE:
					case IRON_PICKAXE:
					case IRON_SHOVEL: m = (Material.IRON_HOE); break;
					
					case GOLDEN_AXE:
					case GOLDEN_PICKAXE:
					case GOLDEN_SHOVEL: m = (Material.GOLDEN_HOE); break;
					
					case DIAMOND_AXE:
					case DIAMOND_PICKAXE:
					case DIAMOND_SHOVEL: m = (Material.DIAMOND_HOE); break;
					
					case NETHERITE_AXE:
					case NETHERITE_PICKAXE:
					case NETHERITE_SHOVEL: m = (Material.NETHERITE_HOE); break;
				}
			}
		}
		s.setType(m);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		ItemStack t = event.getPlayer().getInventory().getItemInMainHand();
		if(t == null) return;
		if(!t.hasItemMeta()) return;
		if(!MetadataLib.has(t, "t_multitool") || !MetadataLib.getBoolean(t, "t_multitool")) return;
		
		Material m = t.getType();
		Block b = event.getClickedBlock();
		
		switch(b.getType()) {
			case ACACIA_BOAT: change(t, "axe"); break;
			case ACACIA_BUTTON: change(t, "axe"); break;
			case ACACIA_DOOR: change(t, "axe"); break;
			case ACACIA_FENCE: change(t, "axe"); break;
			case ACACIA_FENCE_GATE: change(t, "axe"); break;
			//case ACACIA_LEAVES:
			case ACACIA_LOG: change(t, "axe"); break;
			case ACACIA_PLANKS: change(t, "axe"); break;
			case ACACIA_PRESSURE_PLATE: change(t, "axe"); break;
			//case ACACIA_SAPLING:
			case ACACIA_SIGN: change(t, "axe"); break;
			case ACACIA_SLAB: change(t, "axe"); break;
			case ACACIA_STAIRS: change(t, "axe"); break;
			case ACACIA_TRAPDOOR: change(t, "axe"); break;
			case ACACIA_WALL_SIGN: change(t, "axe"); break;
			case ACACIA_WOOD: change(t, "axe"); break;
			case ACTIVATOR_RAIL: change(t, "pickaxe"); break;
			case AMETHYST_BLOCK: change(t, "pickaxe"); break;
			case AMETHYST_CLUSTER: change(t, "pickaxe"); break;
			case ANCIENT_DEBRIS: change(t, "pickaxe"); break;
			case ANDESITE: change(t, "pickaxe"); break;
			case ANDESITE_SLAB: change(t, "pickaxe"); break;
			case ANDESITE_STAIRS: change(t, "pickaxe"); break;
			case ANDESITE_WALL: change(t, "pickaxe"); break;
			case ANVIL: change(t, "pickaxe"); break;
			case ARMOR_STAND: change(t, "axe"); break;
			case ATTACHED_MELON_STEM: change(t, "axe"); break;
			case ATTACHED_PUMPKIN_STEM: change(t, "axe"); break;
			// case AZALEA:
			// case AZALEA_LEAVES:
			// case AZURE_BLUET:
			case BAMBOO: change(t, "axe"); break;
			case BAMBOO_SAPLING: change(t, "axe"); break;
			case BARREL: change(t, "axe"); break;
			// case BARRIER:
			case BASALT: change(t, "pickaxe"); break;
			case BEACON: change(t, "pickaxe"); break;
			case BEDROCK: change(t, "pickaxe"); break;
			case BEEHIVE: change(t, "axe"); break;
			// case BEETROOT:
			// case BEETROOTS:
			case BEE_NEST: change(t, "axe"); break;
			case BELL: change(t, "pickaxe"); break;
			case BIG_DRIPLEAF: change(t, "axe"); break;
			case BIG_DRIPLEAF_STEM: change(t, "axe"); break;
			case BIRCH_BOAT: change(t, "axe"); break;
			case BIRCH_BUTTON: change(t, "axe"); break;
			case BIRCH_DOOR: change(t, "axe"); break;
			case BIRCH_FENCE: change(t, "axe"); break;
			case BIRCH_FENCE_GATE: change(t, "axe"); break;
			//case BIRCH_LEAVES:
			case BIRCH_LOG: change(t, "axe"); break;
			case BIRCH_PLANKS: change(t, "axe"); break;
			case BIRCH_PRESSURE_PLATE: change(t, "axe"); break;
			//case BIRCH_SAPLING:
			case BIRCH_SIGN: change(t, "axe"); break;
			case BIRCH_SLAB: change(t, "axe"); break;
			case BIRCH_STAIRS: change(t, "axe"); break;
			case BIRCH_TRAPDOOR: change(t, "axe"); break;
			case BIRCH_WALL_SIGN: change(t, "axe"); break;
			case BIRCH_WOOD: change(t, "axe"); break;
			case BLACKSTONE: change(t, "pickaxe"); break;
			case BLACKSTONE_SLAB: change(t, "pickaxe"); break;
			case BLACKSTONE_STAIRS: change(t, "pickaxe"); break;
			case BLACKSTONE_WALL: change(t, "pickaxe"); break;
			case BLACK_BANNER: change(t, "axe"); break;
			case BLACK_BED: change(t, "axe"); break;
			//case BLACK_CANDLE:
			//case BLACK_CANDLE_CAKE:
			//case BLACK_CARPET:
			case BLACK_CONCRETE: change(t, "pickaxe"); break;
			case BLACK_CONCRETE_POWDER: change(t, "shovel"); break;
			case BLACK_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case BLACK_SHULKER_BOX: change(t, "pickaxe"); break;
			// case BLACK_STAINED_GLASS:
			// case BLACK_STAINED_GLASS_PANE:
			case BLACK_TERRACOTTA: change(t, "pickaxe"); break;
			case BLACK_WALL_BANNER: change(t, "axe"); break;
			//case BLACK_WOOL:
			case BLAST_FURNACE: change(t, "pickaxe"); break;
			case BLUE_BANNER: change(t, "axe"); break;
			case BLUE_BED: change(t, "axe"); break;
			//case BLUE_CANDLE:
			//case BLUE_CANDLE_CAKE:
			//case BLUE_CARPET:
			case BLUE_CONCRETE: change(t, "pickaxe"); break;
			case BLUE_CONCRETE_POWDER: change(t, "shovel"); break;
			case BLUE_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case BLUE_ICE: change(t, "pickaxe"); break;
			//case BLUE_ORCHID:
			case BLUE_SHULKER_BOX: change(t, "pickaxe"); break;
			// case BLUE_STAINED_GLASS:
			// case BLUE_STAINED_GLASS_PANE:
			case BLUE_TERRACOTTA: change(t, "pickaxe"); break;
			case BLUE_WALL_BANNER: change(t, "axe"); break;
			//case BLUE_WOOL:
			case BONE_BLOCK: change(t, "pickaxe"); break;
			case BOOKSHELF: change(t, "axe"); break;
			case BRAIN_CORAL: change(t, "pickaxe"); break;
			case BRAIN_CORAL_BLOCK: change(t, "pickaxe"); break;
			//case BRAIN_CORAL_FAN:
			//case BRAIN_CORAL_WALL_FAN:
			case BREWING_STAND: change(t, "pickaxe"); break;
			//case BRICK:
			case BRICKS: change(t, "pickaxe"); break;
			case BRICK_SLAB: change(t, "pickaxe"); break;
			case BRICK_STAIRS: change(t, "pickaxe"); break;
			case BRICK_WALL: change(t, "pickaxe"); break;
			case BROWN_BANNER: change(t, "axe"); break;
			case BROWN_BED: change(t, "axe"); break;
			//case BROWN_CANDLE:
			//case BROWN_CANDLE_CAKE:
			//case BROWN_CARPET:
			case BROWN_CONCRETE: change(t, "pickaxe"); break;
			case BROWN_CONCRETE_POWDER: change(t, "shovel"); break;
			//case BROWN_DYE:
			case BROWN_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			//case BROWN_MUSHROOM:
			case BROWN_MUSHROOM_BLOCK: change(t, "axe"); break;
			case BROWN_SHULKER_BOX: change(t, "pickaxe"); break;
			// case BROWN_STAINED_GLASS:
			// case BROWN_STAINED_GLASS_PANE:
			case BROWN_TERRACOTTA: change(t, "pickaxe"); break;
			case BROWN_WALL_BANNER: change(t, "axe"); break;
			//case BROWN_WOOL:
			//case BUBBLE_COLUMN:
			case BUBBLE_CORAL: change(t, "pickaxe"); break;
			case BUBBLE_CORAL_BLOCK: change(t, "pickaxe"); break;
			//case BUBBLE_CORAL_FAN:
			//case BUBBLE_CORAL_WALL_FAN:
			//case BUCKET:
			case BUDDING_AMETHYST: change(t, "pickaxe"); break;
			//case BUNDLE:
			case CACTUS: change(t, "axe"); break;
			//case CAKE:
			case CALCITE: change(t, "pickaxe"); break;
			case CAMPFIRE: change(t, "axe"); break;
			//case CANDLE:
			//case CANDLE_CAKE:
			//case CARROT:
			//case CARROTS:
			//case CARROT_ON_A_STICK:
			case CARTOGRAPHY_TABLE: change(t, "axe"); break;
			case CARVED_PUMPKIN: change(t, "axe"); break;
			//case CAT_SPAWN_EGG:
			case CAULDRON: change(t, "pickaxe"); break;
			//case CAVE_AIR:
			//case CAVE_SPIDER_SPAWN_EGG:
			//case CAVE_VINES:
			//case CAVE_VINES_PLANT:
			case CHAIN: change(t, "pickaxe"); break;
			//case CHAINMAIL_BOOTS:
			//case CHAINMAIL_CHESTPLATE:
			//case CHAINMAIL_HELMET:
			//case CHAINMAIL_LEGGINGS:
			//case CHAIN_COMMAND_BLOCK:
			//case CHARCOAL:
			case CHEST: change(t, "axe"); break;
			//case CHEST_MINECART:
			//case CHICKEN:
			//case CHICKEN_SPAWN_EGG:
			case CHIPPED_ANVIL: change(t, "pickaxe"); break;
			case CHISELED_DEEPSLATE: change(t, "pickaxe"); break;
			case CHISELED_NETHER_BRICKS: change(t, "pickaxe"); break;
			case CHISELED_POLISHED_BLACKSTONE: change(t, "pickaxe"); break;
			case CHISELED_QUARTZ_BLOCK: change(t, "pickaxe"); break;
			case CHISELED_RED_SANDSTONE: change(t, "pickaxe"); break;
			case CHISELED_SANDSTONE: change(t, "pickaxe"); break;
			case CHISELED_STONE_BRICKS: change(t, "pickaxe"); break;
			case CHORUS_FLOWER: change(t, "axe"); break;
			//case CHORUS_FRUIT:
			//case CHORUS_PLANT:
			case CLAY: change(t, "shovel"); break;
			//case CLAY_BALL:
			//case CLOCK:
			//case COAL:
			case COAL_BLOCK: change(t, "pickaxe"); break;
			case COAL_ORE: change(t, "pickaxe"); break;
			case COARSE_DIRT: change(t, "shovel"); break;
			case COBBLED_DEEPSLATE: change(t, "pickaxe"); break;
			case COBBLED_DEEPSLATE_SLAB: change(t, "pickaxe"); break;
			case COBBLED_DEEPSLATE_STAIRS: change(t, "pickaxe"); break;
			case COBBLED_DEEPSLATE_WALL: change(t, "pickaxe"); break;
			case COBBLESTONE: change(t, "pickaxe"); break;
			case COBBLESTONE_SLAB: change(t, "pickaxe"); break;
			case COBBLESTONE_STAIRS: change(t, "pickaxe"); break;
			case COBBLESTONE_WALL: change(t, "pickaxe"); break;
			//case COBWEB:
			case COCOA: change(t, "axe"); break;
			//case COCOA_BEANS:
			//case COD:
			//case COD_BUCKET:
			//case COD_SPAWN_EGG:
			//case COMMAND_BLOCK:
			//case COMMAND_BLOCK_MINECART:
			case COMPARATOR: change(t, "pickaxe"); break;
			//case COMPASS:
			case COMPOSTER: change(t, "axe"); break;
			case CONDUIT: change(t, "pickaxe"); break;
			//case COOKED_BEEF:
			//case COOKED_CHICKEN:
			//case COOKED_COD:
			//case COOKED_MUTTON:
			//case COOKED_PORKCHOP:
			//case COOKED_RABBIT:
			//case COOKED_SALMON:
			//case COOKIE:
			case COPPER_BLOCK: change(t, "pickaxe"); break;
			//case COPPER_INGOT:
			case COPPER_ORE: change(t, "pickaxe"); break;
			//case CORNFLOWER:
			//case COW_SPAWN_EGG:
			case CRACKED_DEEPSLATE_BRICKS: change(t, "pickaxe"); break;
			case CRACKED_DEEPSLATE_TILES: change(t, "pickaxe"); break;
			case CRACKED_NETHER_BRICKS: change(t, "pickaxe"); break;
			case CRACKED_POLISHED_BLACKSTONE_BRICKS: change(t, "pickaxe"); break;
			case CRACKED_STONE_BRICKS: change(t, "pickaxe"); break;
			case CRAFTING_TABLE: change(t, "axe"); break;
			// case CREEPER_BANNER_PATTERN:
			// case CREEPER_HEAD:
			// case CREEPER_SPAWN_EGG:
			// case CREEPER_WALL_HEAD:
			case CRIMSON_BUTTON: change(t, "axe"); break;
			case CRIMSON_DOOR: change(t, "axe"); break;
			case CRIMSON_FENCE: change(t, "axe"); break;
			case CRIMSON_FENCE_GATE: change(t, "axe"); break;
			// case CRIMSON_FUNGUS:
			case CRIMSON_HYPHAE: change(t, "axe"); break;
			case CRIMSON_NYLIUM: change(t, "pickaxe"); break;
			case CRIMSON_PLANKS: change(t, "axe"); break;
			case CRIMSON_PRESSURE_PLATE: change(t, "axe"); break;
			//case CRIMSON_ROOTS:
			case CRIMSON_SIGN: change(t, "axe"); break;
			case CRIMSON_SLAB: change(t, "axe"); break;
			case CRIMSON_STAIRS: change(t, "axe"); break;
			case CRIMSON_STEM: change(t, "axe"); break;
			case CRIMSON_TRAPDOOR: change(t, "axe"); break;
			case CRIMSON_WALL_SIGN: change(t, "axe"); break;
			// case CROSSBOW:
			case CRYING_OBSIDIAN: change(t, "pickaxe"); break;
			case CUT_COPPER: change(t, "pickaxe"); break;
			case CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case CUT_RED_SANDSTONE: change(t, "pickaxe"); break;
			case CUT_RED_SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case CUT_SANDSTONE: change(t, "pickaxe"); break;
			case CUT_SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case CYAN_BANNER: change(t, "axe"); break;
			case CYAN_BED: change(t, "axe"); break;
			// case CYAN_CANDLE:
			// case CYAN_CANDLE_CAKE:
			// case CYAN_CARPET:
			case CYAN_CONCRETE: change(t, "pickaxe"); break;
			case CYAN_CONCRETE_POWDER: change(t, "shovel"); break;
			// case CYAN_DYE:
			case CYAN_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case CYAN_SHULKER_BOX: change(t, "pickaxe"); break;
			// case CYAN_STAINED_GLASS:
			// case CYAN_STAINED_GLASS_PANE:
			case CYAN_TERRACOTTA: change(t, "pickaxe"); break;
			case CYAN_WALL_BANNER: change(t, "axe"); break;
			// case CYAN_WOOL:
			case DAMAGED_ANVIL: change(t, "pickaxe"); break;
			// case DANDELION:
			case DARK_OAK_BOAT: change(t, "axe"); break;
			case DARK_OAK_BUTTON: change(t, "axe"); break;
			case DARK_OAK_DOOR: change(t, "axe"); break;
			case DARK_OAK_FENCE: change(t, "axe"); break;
			case DARK_OAK_FENCE_GATE: change(t, "axe"); break;
			// case DARK_OAK_LEAVES:
			case DARK_OAK_LOG: change(t, "axe"); break;
			case DARK_OAK_PLANKS: change(t, "axe"); break;
			case DARK_OAK_PRESSURE_PLATE: change(t, "axe"); break;
			// case DARK_OAK_SAPLING:
			case DARK_OAK_SIGN: change(t, "axe"); break;
			case DARK_OAK_SLAB: change(t, "axe"); break;
			case DARK_OAK_STAIRS: change(t, "axe"); break;
			case DARK_OAK_TRAPDOOR: change(t, "axe"); break;
			case DARK_OAK_WALL_SIGN: change(t, "axe"); break;
			case DARK_OAK_WOOD: change(t, "axe"); break;
			case DARK_PRISMARINE: change(t, "pickaxe"); break;
			case DARK_PRISMARINE_SLAB: change(t, "pickaxe"); break;
			case DARK_PRISMARINE_STAIRS: change(t, "pickaxe"); break;
			case DAYLIGHT_DETECTOR: change(t, "axe"); break;
			case DEAD_BRAIN_CORAL: change(t, "pickaxe"); break;
			case DEAD_BRAIN_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case DEAD_BRAIN_CORAL_FAN:
			// case DEAD_BRAIN_CORAL_WALL_FAN:
			case DEAD_BUBBLE_CORAL: change(t, "pickaxe"); break;
			case DEAD_BUBBLE_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case DEAD_BUBBLE_CORAL_FAN:
			// case DEAD_BUBBLE_CORAL_WALL_FAN:
			// case DEAD_BUSH:
			case DEAD_FIRE_CORAL: change(t, "pickaxe"); break;
			case DEAD_FIRE_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case DEAD_FIRE_CORAL_FAN:
			// case DEAD_FIRE_CORAL_WALL_FAN:
			case DEAD_HORN_CORAL: change(t, "pickaxe"); break;
			case DEAD_HORN_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case DEAD_HORN_CORAL_FAN:
			// case DEAD_HORN_CORAL_WALL_FAN:
			case DEAD_TUBE_CORAL: change(t, "pickaxe"); break;
			case DEAD_TUBE_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case DEAD_TUBE_CORAL_FAN:
			// case DEAD_TUBE_CORAL_WALL_FAN:
			// case DEBUG_STICK:
			case DEEPSLATE: change(t, "pickaxe"); break;
			case DEEPSLATE_BRICKS: change(t, "pickaxe"); break;
			case DEEPSLATE_BRICK_SLAB: change(t, "pickaxe"); break;
			case DEEPSLATE_BRICK_STAIRS: change(t, "pickaxe"); break;
			case DEEPSLATE_BRICK_WALL: change(t, "pickaxe"); break;
			case DEEPSLATE_COAL_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_COPPER_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_DIAMOND_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_EMERALD_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_GOLD_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_IRON_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_LAPIS_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_REDSTONE_ORE: change(t, "pickaxe"); break;
			case DEEPSLATE_TILES: change(t, "pickaxe"); break;
			case DEEPSLATE_TILE_SLAB: change(t, "pickaxe"); break;
			case DEEPSLATE_TILE_STAIRS: change(t, "pickaxe"); break;
			case DEEPSLATE_TILE_WALL: change(t, "pickaxe"); break;
			case DETECTOR_RAIL: change(t, "pickaxe"); break;
			// case DIAMOND:
			// case DIAMOND_AXE:
			case DIAMOND_BLOCK: change(t, "pickaxe"); break;
			// case DIAMOND_BOOTS:
			// case DIAMOND_CHESTPLATE:
			// case DIAMOND_HELMET:
			// case DIAMOND_HOE:
			// case DIAMOND_HORSE_ARMOR:
			// case DIAMOND_LEGGINGS:
			case DIAMOND_ORE: change(t, "pickaxe"); break;
			// case DIAMOND_PICKAXE:
			// case DIAMOND_SHOVEL:
			// case DIAMOND_SWORD:
			case DIORITE: change(t, "pickaxe"); break;
			case DIORITE_SLAB: change(t, "pickaxe"); break;
			case DIORITE_STAIRS: change(t, "pickaxe"); break;
			case DIORITE_WALL: change(t, "pickaxe"); break;
			case DIRT: change(t, "shovel"); break;
			case DIRT_PATH: change(t, "shovel"); break;
			case DISPENSER: change(t, "pickaxe"); break;
			// case DOLPHIN_SPAWN_EGG:
			// case DONKEY_SPAWN_EGG:
			// case DRAGON_BREATH:
			// case DRAGON_EGG:
			// case DRAGON_HEAD:
			// case DRAGON_WALL_HEAD:
			// case DRIED_KELP:
			case DRIED_KELP_BLOCK: change(t, "hoe"); break;
			case DRIPSTONE_BLOCK: change(t, "pickaxe"); break;
			case DROPPER: change(t, "pickaxe"); break;
			// case DROWNED_SPAWN_EGG:
			// case EGG:
			// case ELDER_GUARDIAN_SPAWN_EGG:
			// case ELYTRA:
			// case EMERALD:
			case EMERALD_BLOCK: change(t, "pickaxe"); break;
			case EMERALD_ORE: change(t, "pickaxe"); break;
			// case ENCHANTED_BOOK:
			// case ENCHANTED_GOLDEN_APPLE:
			case ENCHANTING_TABLE: change(t, "pickaxe"); break;
			// case ENDERMAN_SPAWN_EGG:
			// case ENDERMITE_SPAWN_EGG:
			case ENDER_CHEST: change(t, "pickaxe"); break;
			// case ENDER_EYE:
			// case ENDER_PEARL:
			case END_CRYSTAL: change(t, "pickaxe"); break;
			// case END_GATEWAY:
			// case END_PORTAL:
			case END_PORTAL_FRAME: change(t, "pickaxe"); break;
			// case END_ROD:
			// case END_STONE:
			case END_STONE_BRICKS: change(t, "pickaxe"); break;
			case END_STONE_BRICK_SLAB: change(t, "pickaxe"); break;
			case END_STONE_BRICK_STAIRS: change(t, "pickaxe"); break;
			case END_STONE_BRICK_WALL: change(t, "pickaxe"); break;
			// case EVOKER_SPAWN_EGG:
			// case EXPERIENCE_BOTTLE:
			case EXPOSED_COPPER: change(t, "pickaxe"); break;
			case EXPOSED_CUT_COPPER: change(t, "pickaxe"); break;
			case EXPOSED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case EXPOSED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case FARMLAND: change(t, "shovel"); break;
			// case FEATHER:
			// case FERMENTED_SPIDER_EYE:
			// case FERN:
			// case FILLED_MAP:
			// case FIRE:
			// case FIREWORK_ROCKET:
			// case FIREWORK_STAR:
			// case FIRE_CHARGE:
			case FIRE_CORAL: change(t, "pickaxe"); break;
			case FIRE_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case FIRE_CORAL_FAN:
			// case FIRE_CORAL_WALL_FAN:
			// case FISHING_ROD:
			case FLETCHING_TABLE: change(t, "axe"); break;
			// case FLINT:
			// case FLINT_AND_STEEL:
			case FLOWERING_AZALEA: change(t, "hoe"); break;
			case FLOWERING_AZALEA_LEAVES: change(t, "axe"); break;
			// case FLOWER_BANNER_PATTERN:
			// case FLOWER_POT:
			// case FOX_SPAWN_EGG:
			case FROSTED_ICE: change(t, "pickaxe"); break;
			case FURNACE: change(t, "pickaxe"); break;
			case FURNACE_MINECART: change(t, "pickaxe"); break;
			// case GHAST_SPAWN_EGG:
			// case GHAST_TEAR:
			case GILDED_BLACKSTONE: change(t, "pickaxe"); break;
			// case GLASS:
			// case GLASS_BOTTLE:
			// case GLASS_PANE:
			// case GLISTERING_MELON_SLICE:
			// case GLOBE_BANNER_PATTERN:
			case GLOWSTONE: change(t, "pickaxe"); break;
			// case GLOWSTONE_DUST:
			// case GLOW_BERRIES:
			// case GLOW_INK_SAC:
			// case GLOW_ITEM_FRAME:
			// case GLOW_LICHEN:
			// case GLOW_SQUID_SPAWN_EGG:
			// case GOAT_SPAWN_EGG:
			// case GOLDEN_APPLE:
			// case GOLDEN_AXE:
			// case GOLDEN_BOOTS:
			// case GOLDEN_CARROT:
			// case GOLDEN_CHESTPLATE:
			// case GOLDEN_HELMET:
			// case GOLDEN_HOE:
			// case GOLDEN_HORSE_ARMOR:
			// case GOLDEN_LEGGINGS:
			// case GOLDEN_PICKAXE:
			// case GOLDEN_SHOVEL:
			// case GOLDEN_SWORD:
			 case GOLD_BLOCK: change(t, "pickaxe"); break;
			// case GOLD_INGOT:
			// case GOLD_NUGGET:
			case GOLD_ORE: change(t, "pickaxe"); break;
			case GRANITE: change(t, "pickaxe"); break;
			case GRANITE_SLAB: change(t, "pickaxe"); break;
			case GRANITE_STAIRS: change(t, "pickaxe"); break;
			case GRANITE_WALL: change(t, "pickaxe"); break;
			// case GRASS:
			case GRASS_BLOCK: change(t, "shovel"); break;
			case GRAVEL: change(t, "shovel"); break;
			case GRAY_BANNER: change(t, "axe"); break;
			case GRAY_BED: change(t, "axe"); break;
			// case GRAY_CANDLE:
			// case GRAY_CANDLE_CAKE:
			// case GRAY_CARPET:
			case GRAY_CONCRETE: change(t, "pickaxe"); break;
			case GRAY_CONCRETE_POWDER: change(t, "shovel"); break;
			// case GRAY_DYE:
			case GRAY_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case GRAY_SHULKER_BOX: change(t, "pickaxe"); break;
			// case GRAY_STAINED_GLASS:
			// case GRAY_STAINED_GLASS_PANE:
			case GRAY_TERRACOTTA: change(t, "pickaxe"); break;
			case GRAY_WALL_BANNER: change(t, "axe"); break;
			// case GRAY_WOOL:
			case GREEN_BANNER: change(t, "axe"); break;
			case GREEN_BED: change(t, "axe"); break;
			// case GREEN_CANDLE:
			// case GREEN_CANDLE_CAKE:
			// case GREEN_CARPET:
			case GREEN_CONCRETE: change(t, "pickaxe"); break;
			case GREEN_CONCRETE_POWDER: change(t, "shovel"); break;
			// case GREEN_DYE:
			case GREEN_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case GREEN_SHULKER_BOX: change(t, "pickaxe"); break;
			// case GREEN_STAINED_GLASS:
			// case GREEN_STAINED_GLASS_PANE:
			case GREEN_TERRACOTTA: change(t, "pickaxe"); break;
			case GREEN_WALL_BANNER: change(t, "axe"); break;
			// case GREEN_WOOL:
			case GRINDSTONE: change(t, "pickaxe"); break;
			// case GUARDIAN_SPAWN_EGG:
			// case GUNPOWDER:
			// case HANGING_ROOTS:
			case HAY_BLOCK: change(t, "hoe"); break;
			// case HEART_OF_THE_SEA:
			case HEAVY_WEIGHTED_PRESSURE_PLATE: change(t, "pickaxe"); break;
			// case HOGLIN_SPAWN_EGG:
			// case HONEYCOMB:
			// case HONEYCOMB_BLOCK:
			// case HONEY_BLOCK:
			// case HONEY_BOTTLE:
			case HOPPER: change(t, "pickaxe"); break;
			case HOPPER_MINECART: change(t, "pickaxe"); break;
			case HORN_CORAL: change(t, "pickaxe"); break;
			case HORN_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case HORN_CORAL_FAN:
			// case HORN_CORAL_WALL_FAN:
			// case HORSE_SPAWN_EGG:
			// case HUSK_SPAWN_EGG:
			case ICE: change(t, "pickaxe"); break;
			case INFESTED_CHISELED_STONE_BRICKS: change(t, "pickaxe"); break;
			case INFESTED_COBBLESTONE: change(t, "pickaxe"); break;
			case INFESTED_CRACKED_STONE_BRICKS: change(t, "pickaxe"); break;
			case INFESTED_DEEPSLATE: change(t, "pickaxe"); break;
			case INFESTED_MOSSY_STONE_BRICKS: change(t, "pickaxe"); break;
			case INFESTED_STONE: change(t, "pickaxe"); break;
			case INFESTED_STONE_BRICKS: change(t, "pickaxe"); break;
			// case INK_SAC:
			// case IRON_AXE:
			case IRON_BARS: change(t, "pickaxe"); break;
			case IRON_BLOCK: change(t, "pickaxe"); break;
			// case IRON_BOOTS:
			// case IRON_CHESTPLATE:
			case IRON_DOOR: change(t, "pickaxe"); break;
			// case IRON_HELMET:
			// case IRON_HOE:
			// case IRON_HORSE_ARMOR:
			// case IRON_INGOT:
			// case IRON_LEGGINGS:
			// case IRON_NUGGET:
			case IRON_ORE: change(t, "pickaxe"); break;
			// case IRON_PICKAXE:
			// case IRON_SHOVEL:
			// case IRON_SWORD:
			case IRON_TRAPDOOR: change(t, "pickaxe"); break;
			// case ITEM_FRAME:
			case JACK_O_LANTERN: change(t, "axe"); break;
			// case JIGSAW:
			case JUKEBOX: change(t, "axe"); break;
			case JUNGLE_BOAT: change(t, "axe"); break;
			case JUNGLE_BUTTON: change(t, "axe"); break;
			case JUNGLE_DOOR: change(t, "axe"); break;
			case JUNGLE_FENCE: change(t, "axe"); break;
			case JUNGLE_FENCE_GATE: change(t, "axe"); break;
			// case JUNGLE_LEAVES:
			case JUNGLE_LOG: change(t, "axe"); break;
			case JUNGLE_PLANKS: change(t, "axe"); break;
			case JUNGLE_PRESSURE_PLATE: change(t, "axe"); break;
			// case JUNGLE_SAPLING:
			case JUNGLE_SIGN: change(t, "axe"); break;
			case JUNGLE_SLAB: change(t, "axe"); break;
			case JUNGLE_STAIRS: change(t, "axe"); break;
			case JUNGLE_TRAPDOOR: change(t, "axe"); break;
			case JUNGLE_WALL_SIGN: change(t, "axe"); break;
			case JUNGLE_WOOD: change(t, "axe"); break;
			// case KELP:
			// case KELP_PLANT:
			// case KNOWLEDGE_BOOK:
			case LADDER: change(t, "axe"); break;
			case LANTERN: change(t, "pickaxe"); break;
			case LAPIS_BLOCK: change(t, "pickaxe"); break;
			// case LAPIS_LAZULI:
			case LAPIS_ORE: change(t, "pickaxe"); break;
			case LARGE_AMETHYST_BUD: change(t, "pickaxe"); break;
			// case LARGE_FERN:
			// case LAVA:
			// case LAVA_BUCKET:
			case LAVA_CAULDRON: change(t, "pickaxe"); break;
			// case LEAD:
			// case LEATHER:
			// case LEATHER_BOOTS:
			// case LEATHER_CHESTPLATE:
			// case LEATHER_HELMET:
			// case LEATHER_HORSE_ARMOR:
			// case LEATHER_LEGGINGS:
			case LECTERN: change(t, "axe"); break;
			case LEVER: change(t, "pickaxe"); break;
			// case LIGHT:
			case LIGHTNING_ROD: change(t, "pickaxe"); break;
			case LIGHT_BLUE_BANNER: change(t, "axe"); break;
			case LIGHT_BLUE_BED: change(t, "axe"); break;
			// case LIGHT_BLUE_CANDLE:
			// case LIGHT_BLUE_CANDLE_CAKE:
			// case LIGHT_BLUE_CARPET:
			case LIGHT_BLUE_CONCRETE: change(t, "pickaxe"); break;
			case LIGHT_BLUE_CONCRETE_POWDER: change(t, "shovel"); break;
			// case LIGHT_BLUE_DYE:
			case LIGHT_BLUE_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case LIGHT_BLUE_SHULKER_BOX: change(t, "pickaxe"); break;
			// case LIGHT_BLUE_STAINED_GLASS:
			// case LIGHT_BLUE_STAINED_GLASS_PANE:
			case LIGHT_BLUE_TERRACOTTA: change(t, "pickaxe"); break;
			case LIGHT_BLUE_WALL_BANNER: change(t, "axe"); break;
			// case LIGHT_BLUE_WOOL:
			case LIGHT_GRAY_BANNER: change(t, "axe"); break;
			case LIGHT_GRAY_BED: change(t, "axe"); break;
			// case LIGHT_GRAY_CANDLE:
			// case LIGHT_GRAY_CANDLE_CAKE:
			// case LIGHT_GRAY_CARPET:
			case LIGHT_GRAY_CONCRETE: change(t, "pickaxe"); break;
			case LIGHT_GRAY_CONCRETE_POWDER: change(t, "shovel"); break;
			// case LIGHT_GRAY_DYE:
			case LIGHT_GRAY_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case LIGHT_GRAY_SHULKER_BOX: change(t, "pickaxe"); break;
			// case LIGHT_GRAY_STAINED_GLASS:
			// case LIGHT_GRAY_STAINED_GLASS_PANE:
			case LIGHT_GRAY_TERRACOTTA: change(t, "pickaxe"); break;
			case LIGHT_GRAY_WALL_BANNER: change(t, "axe"); break;
			// case LIGHT_GRAY_WOOL:
			case LIGHT_WEIGHTED_PRESSURE_PLATE: change(t, "pickaxe"); break;
			// case LILAC:
			// case LILY_OF_THE_VALLEY:
			case LILY_PAD: change(t, "axe"); break;
			case LIME_BANNER: change(t, "axe"); break;
			case LIME_BED: change(t, "axe"); break;
			// case LIME_CANDLE:
			// case LIME_CANDLE_CAKE:
			//case LIME_CARPET:
			case LIME_CONCRETE: change(t, "pickaxe"); break;
			case LIME_CONCRETE_POWDER: change(t, "shovel"); break;
			// case LIME_DYE:
			case LIME_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case LIME_SHULKER_BOX: change(t, "pickaxe"); break;
			// case LIME_STAINED_GLASS:
			// case LIME_STAINED_GLASS_PANE:
			case LIME_TERRACOTTA: change(t, "pickaxe"); break;
			case LIME_WALL_BANNER: change(t, "axe"); break;
			// case LIME_WOOL:
			// case LINGERING_POTION:
			// case LLAMA_SPAWN_EGG:
			case LODESTONE: change(t, "pickaxe"); break;
			case LOOM: change(t, "axe"); break;
			case MAGENTA_BANNER: change(t, "axe"); break;
			case MAGENTA_BED: change(t, "axe"); break;
			// case MAGENTA_CANDLE:
			// case MAGENTA_CANDLE_CAKE:
			//case MAGENTA_CARPET:
			case MAGENTA_CONCRETE: change(t, "pickaxe"); break;
			case MAGENTA_CONCRETE_POWDER: change(t, "shovel"); break;
			// case MAGENTA_DYE:
			case MAGENTA_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case MAGENTA_SHULKER_BOX: change(t, "pickaxe"); break;
			// case MAGENTA_STAINED_GLASS:
			// case MAGENTA_STAINED_GLASS_PANE:
			case MAGENTA_TERRACOTTA: change(t, "pickaxe"); break;
			case MAGENTA_WALL_BANNER: change(t, "axe"); break;
			// case MAGENTA_WOOL:
			case MAGMA_BLOCK: change(t, "pickaxe"); break;
			// case MAGMA_CREAM:
			// case MAGMA_CUBE_SPAWN_EGG:
			// case MAP:
			case MEDIUM_AMETHYST_BUD: change(t, "pickaxe"); break;
			case MELON: change(t, "axe"); break;
			// case MELON_SEEDS:
			// case MELON_SLICE:
			// case MELON_STEM:
			// case MILK_BUCKET:
			case MINECART: change(t, "pickaxe"); break;
			// case MOJANG_BANNER_PATTERN:
			// case MOOSHROOM_SPAWN_EGG:
			case MOSSY_COBBLESTONE: change(t, "pickaxe"); break;
			case MOSSY_COBBLESTONE_SLAB: change(t, "pickaxe"); break;
			case MOSSY_COBBLESTONE_STAIRS: change(t, "pickaxe"); break;
			case MOSSY_COBBLESTONE_WALL: change(t, "pickaxe"); break;
			case MOSSY_STONE_BRICKS: change(t, "pickaxe"); break;
			case MOSSY_STONE_BRICK_SLAB: change(t, "pickaxe"); break;
			case MOSSY_STONE_BRICK_STAIRS: change(t, "pickaxe"); break;
			case MOSSY_STONE_BRICK_WALL: change(t, "pickaxe"); break;
			case MOSS_BLOCK: change(t, "hoe"); break;
			case MOSS_CARPET: change(t, "hoe"); break;
			case MOVING_PISTON: change(t, "pickaxe"); break;
			// case MULE_SPAWN_EGG:
			// case MUSHROOM_STEM:
			// case MUSHROOM_STEW:
			// case MUSIC_DISC_11:
			// case MUSIC_DISC_13:
			// case MUSIC_DISC_BLOCKS:
			// case MUSIC_DISC_CAT:
			// case MUSIC_DISC_CHIRP:
			// case MUSIC_DISC_FAR:
			// case MUSIC_DISC_MALL:
			// case MUSIC_DISC_MELLOHI:
			// case MUSIC_DISC_OTHERSIDE:
			// case MUSIC_DISC_PIGSTEP:
			// case MUSIC_DISC_STAL:
			// case MUSIC_DISC_STRAD:
			// case MUSIC_DISC_WAIT:
			// case MUSIC_DISC_WARD:
			// case MUTTON:
			case MYCELIUM: change(t, "shovel"); break;
			// case NAME_TAG:
			// case NAUTILUS_SHELL:
			// case NETHERITE_AXE:
			case NETHERITE_BLOCK: change(t, "pickaxe"); break;
			// case NETHERITE_BOOTS:
			// case NETHERITE_CHESTPLATE:
			// case NETHERITE_HELMET:
			// case NETHERITE_HOE:
			// case NETHERITE_INGOT:
			// case NETHERITE_LEGGINGS:
			// case NETHERITE_PICKAXE:
			// case NETHERITE_SCRAP:
			// case NETHERITE_SHOVEL:
			// case NETHERITE_SWORD:
			case NETHERRACK: change(t, "pickaxe"); break;
			case NETHER_BRICK: change(t, "pickaxe"); break;
			case NETHER_BRICKS: change(t, "pickaxe"); break;
			case NETHER_BRICK_FENCE: change(t, "pickaxe"); break;
			case NETHER_BRICK_SLAB: change(t, "pickaxe"); break;
			case NETHER_BRICK_STAIRS: change(t, "pickaxe"); break;
			case NETHER_BRICK_WALL: change(t, "pickaxe"); break;
			case NETHER_GOLD_ORE: change(t, "pickaxe"); break;
			// case NETHER_PORTAL:
			case NETHER_QUARTZ_ORE: change(t, "pickaxe"); break;
			// case NETHER_SPROUTS:
			// case NETHER_STAR:
			// case NETHER_WART:
			case NETHER_WART_BLOCK: change(t, "hoe"); break;
			case NOTE_BLOCK: change(t, "axe"); break;
			case OAK_BOAT: change(t, "axe"); break;
			case OAK_BUTTON: change(t, "axe"); break;
			case OAK_DOOR: change(t, "axe"); break;
			case OAK_FENCE: change(t, "axe"); break;
			case OAK_FENCE_GATE: change(t, "axe"); break;
			// case OAK_LEAVES:
			case OAK_LOG: change(t, "axe"); break;
			case OAK_PLANKS: change(t, "axe"); break;
			case OAK_PRESSURE_PLATE: change(t, "axe"); break;
			// case OAK_SAPLING:
			case OAK_SIGN: change(t, "axe"); break;
			case OAK_SLAB: change(t, "axe"); break;
			case OAK_STAIRS: change(t, "axe"); break;
			case OAK_TRAPDOOR: change(t, "axe"); break;
			case OAK_WALL_SIGN: change(t, "axe"); break;
			case OAK_WOOD: change(t, "axe"); break;
			case OBSERVER: change(t, "pickaxe"); break;
			case OBSIDIAN: change(t, "pickaxe"); break;
			// case OCELOT_SPAWN_EGG:
			case ORANGE_BANNER: change(t, "axe"); break;
			case ORANGE_BED: change(t, "axe"); break;
			// case ORANGE_CANDLE:
			// case ORANGE_CANDLE_CAKE:
			//case ORANGE_CARPET:
			case ORANGE_CONCRETE: change(t, "pickaxe"); break;
			case ORANGE_CONCRETE_POWDER: change(t, "shovel"); break;
			// case ORANGE_DYE:
			case ORANGE_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case ORANGE_SHULKER_BOX: change(t, "pickaxe"); break;
			// case ORANGE_STAINED_GLASS:
			// case ORANGE_STAINED_GLASS_PANE:
			case ORANGE_TERRACOTTA: change(t, "pickaxe"); break;
			// case ORANGE_TULIP:
			case ORANGE_WALL_BANNER: change(t, "axe"); break;
			// case ORANGE_WOOL:
			// case OXEYE_DAISY:
			case OXIDIZED_COPPER: change(t, "pickaxe"); break;
			case OXIDIZED_CUT_COPPER: change(t, "pickaxe"); break;
			case OXIDIZED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case OXIDIZED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case PACKED_ICE: change(t, "pickaxe"); break;
			// case PAINTING:
			// case PANDA_SPAWN_EGG:
			// case PAPER:
			// case PARROT_SPAWN_EGG:
			// case PEONY:
			case PETRIFIED_OAK_SLAB: change(t, "pickaxe"); break;
			// case PHANTOM_MEMBRANE:
			// case PHANTOM_SPAWN_EGG:
			// case PIGLIN_BANNER_PATTERN:
			// case PIGLIN_BRUTE_SPAWN_EGG:
			// case PIGLIN_SPAWN_EGG:
			// case PIG_SPAWN_EGG:
			// case PILLAGER_SPAWN_EGG:
			case PINK_BANNER: change(t, "axe"); break;
			case PINK_BED: change(t, "axe"); break;
			// case PINK_CANDLE:
			// case PINK_CANDLE_CAKE:
			// case PINK_CARPET:
			case PINK_CONCRETE: change(t, "pickaxe"); break;
			case PINK_CONCRETE_POWDER: change(t, "shovel"); break;
			// case PINK_DYE:
			case PINK_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case PINK_SHULKER_BOX: change(t, "pickaxe"); break;
			// case PINK_STAINED_GLASS:
			// case PINK_STAINED_GLASS_PANE:
			case PINK_TERRACOTTA: change(t, "pickaxe"); break;
			// case PINK_TULIP:
			case PINK_WALL_BANNER: change(t, "axe"); break;
			// case PINK_WOOL:
			case PISTON: change(t, "pickaxe"); break;
			case PISTON_HEAD: change(t, "pickaxe"); break;
			// case PLAYER_HEAD:
			// case PLAYER_WALL_HEAD:
			case PODZOL: change(t, "shovel"); break;
			case POINTED_DRIPSTONE: change(t, "pickaxe"); break;
			// case POISONOUS_POTATO:
			// case POLAR_BEAR_SPAWN_EGG:
			case POLISHED_ANDESITE: change(t, "pickaxe"); break;
			case POLISHED_ANDESITE_SLAB: change(t, "pickaxe"); break;
			case POLISHED_ANDESITE_STAIRS: change(t, "pickaxe"); break;
			case POLISHED_BASALT: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_BRICKS: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_BRICK_SLAB: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_BRICK_STAIRS: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_BRICK_WALL: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_BUTTON: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_PRESSURE_PLATE: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_SLAB: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_STAIRS: change(t, "pickaxe"); break;
			case POLISHED_BLACKSTONE_WALL: change(t, "pickaxe"); break;
			case POLISHED_DEEPSLATE: change(t, "pickaxe"); break;
			case POLISHED_DEEPSLATE_SLAB: change(t, "pickaxe"); break;
			case POLISHED_DEEPSLATE_STAIRS: change(t, "pickaxe"); break;
			case POLISHED_DEEPSLATE_WALL: change(t, "pickaxe"); break;
			case POLISHED_DIORITE: change(t, "pickaxe"); break;
			case POLISHED_DIORITE_SLAB: change(t, "pickaxe"); break;
			case POLISHED_DIORITE_STAIRS: change(t, "pickaxe"); break;
			case POLISHED_GRANITE: change(t, "pickaxe"); break;
			case POLISHED_GRANITE_SLAB: change(t, "pickaxe"); break;
			case POLISHED_GRANITE_STAIRS: change(t, "pickaxe"); break;
			// case POPPED_CHORUS_FRUIT:
			// case POPPY:
			// case PORKCHOP:
			// case POTATO:
			// case POTATOES:
			// case POTION:
			// case POTTED_ACACIA_SAPLING:
			// case POTTED_ALLIUM:
			// case POTTED_AZALEA_BUSH:
			// case POTTED_AZURE_BLUET:
			// case POTTED_BAMBOO:
			// case POTTED_BIRCH_SAPLING:
			// case POTTED_BLUE_ORCHID:
			// case POTTED_BROWN_MUSHROOM:
			// case POTTED_CACTUS:
			// case POTTED_CORNFLOWER:
			// case POTTED_CRIMSON_FUNGUS:
			// case POTTED_CRIMSON_ROOTS:
			// case POTTED_DANDELION:
			// case POTTED_DARK_OAK_SAPLING:
			// case POTTED_DEAD_BUSH:
			// case POTTED_FERN:
			// case POTTED_FLOWERING_AZALEA_BUSH:
			// case POTTED_JUNGLE_SAPLING:
			// case POTTED_LILY_OF_THE_VALLEY:
			// case POTTED_OAK_SAPLING:
			// case POTTED_ORANGE_TULIP:
			// case POTTED_OXEYE_DAISY:
			// case POTTED_PINK_TULIP:
			// case POTTED_POPPY:
			// case POTTED_RED_MUSHROOM:
			// case POTTED_RED_TULIP:
			// case POTTED_SPRUCE_SAPLING:
			// case POTTED_WARPED_FUNGUS:
			// case POTTED_WARPED_ROOTS:
			// case POTTED_WHITE_TULIP:
			// case POTTED_WITHER_ROSE:
			// case POWDER_SNOW:
			// case POWDER_SNOW_BUCKET:
			case POWDER_SNOW_CAULDRON: change(t, "pickaxe"); break;
			case POWERED_RAIL: change(t, "pickaxe"); break;
			case PRISMARINE: change(t, "pickaxe"); break;
			case PRISMARINE_BRICKS: change(t, "pickaxe"); break;
			case PRISMARINE_BRICK_SLAB: change(t, "pickaxe"); break;
			case PRISMARINE_BRICK_STAIRS: change(t, "pickaxe"); break;
			// case PRISMARINE_CRYSTALS:
			// case PRISMARINE_SHARD:
			case PRISMARINE_SLAB: change(t, "pickaxe"); break;
			case PRISMARINE_STAIRS: change(t, "pickaxe"); break;
			case PRISMARINE_WALL: change(t, "pickaxe"); break;
			// case PUFFERFISH:
			// case PUFFERFISH_BUCKET:
			// case PUFFERFISH_SPAWN_EGG:
			case PUMPKIN: change(t, "axe"); break;
			// case PUMPKIN_PIE:
			// case PUMPKIN_SEEDS:
			// case PUMPKIN_STEM:
			case PURPLE_BANNER: change(t, "axe"); break;
			case PURPLE_BED: change(t, "axe"); break;
			// case PURPLE_CANDLE:
			// case PURPLE_CANDLE_CAKE:
			// case PURPLE_CARPET:
			case PURPLE_CONCRETE: change(t, "pickaxe"); break;
			case PURPLE_CONCRETE_POWDER: change(t, "shovel"); break;
			// case PURPLE_DYE:
			case PURPLE_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case PURPLE_SHULKER_BOX: change(t, "pickaxe"); break;
			// case PURPLE_STAINED_GLASS:
			// case PURPLE_STAINED_GLASS_PANE:
			case PURPLE_TERRACOTTA: change(t, "pickaxe"); break;
			case PURPLE_WALL_BANNER: change(t, "axe"); break;
			// case PURPLE_WOOL:
			case PURPUR_BLOCK: change(t, "pickaxe"); break;
			case PURPUR_PILLAR: change(t, "pickaxe"); break;
			case PURPUR_SLAB: change(t, "pickaxe"); break;
			case PURPUR_STAIRS: change(t, "pickaxe"); break;
			// case QUARTZ:
			case QUARTZ_BLOCK: change(t, "pickaxe"); break;
			case QUARTZ_BRICKS: change(t, "pickaxe"); break;
			case QUARTZ_PILLAR: change(t, "pickaxe"); break;
			case QUARTZ_SLAB: change(t, "pickaxe"); break;
			case QUARTZ_STAIRS: change(t, "pickaxe"); break;
			// case RABBIT:
			// case RABBIT_FOOT:
			// case RABBIT_HIDE:
			// case RABBIT_SPAWN_EGG:
			// case RABBIT_STEW:
			case RAIL: change(t, "pickaxe"); break;
			// case RAVAGER_SPAWN_EGG:
			case RAW_COPPER: change(t, "pickaxe"); break;
			case RAW_COPPER_BLOCK: change(t, "pickaxe"); break;
			case RAW_GOLD: change(t, "pickaxe"); break;
			case RAW_GOLD_BLOCK: change(t, "pickaxe"); break;
			case RAW_IRON: change(t, "pickaxe"); break;
			case RAW_IRON_BLOCK: change(t, "pickaxe"); break;
			// case REDSTONE:
			case REDSTONE_BLOCK: change(t, "pickaxe"); break;
			case REDSTONE_LAMP: change(t, "pickaxe"); break;
			case REDSTONE_ORE: change(t, "pickaxe"); break;
			case REDSTONE_TORCH: change(t, "axe"); break;
			case REDSTONE_WALL_TORCH: change(t, "axe"); break;
			// case REDSTONE_WIRE:
			case RED_BANNER: change(t, "axe"); break;
			case RED_BED: change(t, "axe"); break;
			// case RED_CANDLE:
			// case RED_CANDLE_CAKE:
			// case RED_CARPET:
			case RED_CONCRETE: change(t, "pickaxe"); break;
			case RED_CONCRETE_POWDER: change(t, "shovel"); break;
			// case RED_DYE:
			case RED_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			// case RED_MUSHROOM:
			case RED_MUSHROOM_BLOCK: change(t, "axe"); break;
			case RED_NETHER_BRICKS: change(t, "pickaxe"); break;
			case RED_NETHER_BRICK_SLAB: change(t, "pickaxe"); break;
			case RED_NETHER_BRICK_STAIRS: change(t, "pickaxe"); break;
			case RED_NETHER_BRICK_WALL: change(t, "pickaxe"); break;
			case RED_SAND: change(t, "shovel"); break;
			case RED_SANDSTONE: change(t, "pickaxe"); break;
			case RED_SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case RED_SANDSTONE_STAIRS: change(t, "pickaxe"); break;
			case RED_SANDSTONE_WALL: change(t, "pickaxe"); break;
			case RED_SHULKER_BOX: change(t, "pickaxe"); break;
			// case RED_STAINED_GLASS:
			// case RED_STAINED_GLASS_PANE:
			case RED_TERRACOTTA: change(t, "pickaxe"); break;
			// case RED_TULIP:
			case RED_WALL_BANNER: change(t, "axe"); break;
			// case RED_WOOL:
			case REPEATER: change(t, "pickaxe"); break;
			// case REPEATING_COMMAND_BLOCK:
			case RESPAWN_ANCHOR: change(t, "pickaxe"); break;
			case ROOTED_DIRT: change(t, "shovel"); break;
			// case ROSE_BUSH:
			// case ROTTEN_FLESH:
			// case SADDLE:
			// case SALMON:
			// case SALMON_BUCKET:
			// case SALMON_SPAWN_EGG:
			case SAND: change(t, "shovel"); break;
			case SANDSTONE: change(t, "pickaxe"); break;
			case SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case SANDSTONE_STAIRS: change(t, "pickaxe"); break;
			case SANDSTONE_WALL: change(t, "pickaxe"); break;
			case SCAFFOLDING: change(t, "axe"); break;
			case SCULK_SENSOR: change(t, "hoe"); break;
			// case SCUTE:
			// case SEAGRASS:
			case SEA_LANTERN: change(t, "pickaxe"); break;
			// case SEA_PICKLE:
			// case SHEARS:
			// case SHEEP_SPAWN_EGG:
			// case SHIELD:
			case SHROOMLIGHT: change(t, "hoe"); break;
			case SHULKER_BOX: change(t, "pickaxe"); break;
			// case SHULKER_SHELL:
			// case SHULKER_SPAWN_EGG:
			// case SILVERFISH_SPAWN_EGG:
			// case SKELETON_HORSE_SPAWN_EGG:
			// case SKELETON_SKULL:
			// case SKELETON_SPAWN_EGG:
			// case SKELETON_WALL_SKULL:
			// case SKULL_BANNER_PATTERN:
			// case SLIME_BALL:
			// case SLIME_BLOCK:
			// case SLIME_SPAWN_EGG:
			case SMALL_AMETHYST_BUD: change(t, "pickaxe"); break;
			// case SMALL_DRIPLEAF:
			case SMITHING_TABLE: change(t, "pickaxe"); break;
			case SMOKER:  change(t, "pickaxe"); break;
			case SMOOTH_BASALT: change(t, "pickaxe"); break;
			case SMOOTH_QUARTZ: change(t, "pickaxe"); break;
			case SMOOTH_QUARTZ_SLAB: change(t, "pickaxe"); break;
			case SMOOTH_QUARTZ_STAIRS: change(t, "pickaxe"); break;
			case SMOOTH_RED_SANDSTONE: change(t, "pickaxe"); break;
			case SMOOTH_RED_SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case SMOOTH_RED_SANDSTONE_STAIRS: change(t, "pickaxe"); break;
			case SMOOTH_SANDSTONE: change(t, "pickaxe"); break;
			case SMOOTH_SANDSTONE_SLAB: change(t, "pickaxe"); break;
			case SMOOTH_SANDSTONE_STAIRS: change(t, "pickaxe"); break;
			case SMOOTH_STONE: change(t, "pickaxe"); break;
			case SMOOTH_STONE_SLAB: change(t, "pickaxe"); break;
			case SNOW: change(t, "shovel"); break;
			// case SNOWBALL:
			// case SNOW_BLOCK:
			case SOUL_CAMPFIRE: change(t, "axe"); break;
			// case SOUL_FIRE:
			case SOUL_LANTERN: change(t, "pickaxe"); break;
			case SOUL_SAND: change(t, "shovel"); break;
			case SOUL_SOIL: change(t, "shovel"); break;
			case SOUL_TORCH: change(t, "axe"); break;
			case SOUL_WALL_TORCH: change(t, "axe"); break;
			case SPAWNER: change(t, "pickaxe"); break;
			// case SPECTRAL_ARROW:
			// case SPIDER_EYE:
			// case SPIDER_SPAWN_EGG:
			// case SPLASH_POTION:
			case SPONGE: change(t, "hoe"); break;
			// case SPORE_BLOSSOM:
			case SPRUCE_BOAT: change(t, "axe"); break;
			case SPRUCE_BUTTON: change(t, "axe"); break;
			case SPRUCE_DOOR: change(t, "axe"); break;
			case SPRUCE_FENCE: change(t, "axe"); break;
			case SPRUCE_FENCE_GATE: change(t, "axe"); break;
			// case SPRUCE_LEAVES:
			case SPRUCE_LOG: change(t, "axe"); break;
			case SPRUCE_PLANKS: change(t, "axe"); break;
			case SPRUCE_PRESSURE_PLATE: change(t, "axe"); break;
			// case SPRUCE_SAPLING:
			case SPRUCE_SIGN: change(t, "axe"); break;
			case SPRUCE_SLAB: change(t, "axe"); break;
			case SPRUCE_STAIRS: change(t, "axe"); break;
			case SPRUCE_TRAPDOOR: change(t, "axe"); break;
			case SPRUCE_WALL_SIGN: change(t, "axe"); break;
			case SPRUCE_WOOD: change(t, "axe"); break;
			// case SPYGLASS:
			// case SQUID_SPAWN_EGG:
			// case STICK:
			case STICKY_PISTON: change(t, "pickaxe"); break;
			case STONE: change(t, "pickaxe"); break;
			case STONECUTTER: change(t, "pickaxe"); break;
			// case STONE_AXE:
			case STONE_BRICKS: change(t, "pickaxe"); break;
			case STONE_BRICK_SLAB: change(t, "pickaxe"); break;
			case STONE_BRICK_STAIRS: change(t, "pickaxe"); break;
			case STONE_BRICK_WALL: change(t, "pickaxe"); break;
			case STONE_BUTTON: change(t, "pickaxe"); break;
			// case STONE_HOE:
			// case STONE_PICKAXE:
			case STONE_PRESSURE_PLATE: change(t, "pickaxe"); break;
			// case STONE_SHOVEL:
			case STONE_SLAB: change(t, "pickaxe"); break;
			case STONE_STAIRS: change(t, "pickaxe"); break;
			// case STONE_SWORD:
			// case STRAY_SPAWN_EGG:
			// case STRIDER_SPAWN_EGG:
			// case STRING:
			case STRIPPED_ACACIA_LOG: change(t, "axe"); break;
			case STRIPPED_ACACIA_WOOD: change(t, "axe"); break;
			case STRIPPED_BIRCH_LOG: change(t, "axe"); break;
			case STRIPPED_BIRCH_WOOD: change(t, "axe"); break;
			case STRIPPED_CRIMSON_HYPHAE: change(t, "axe"); break;
			case STRIPPED_CRIMSON_STEM: change(t, "axe"); break;
			case STRIPPED_DARK_OAK_LOG: change(t, "axe"); break;
			case STRIPPED_DARK_OAK_WOOD: change(t, "axe"); break;
			case STRIPPED_JUNGLE_LOG: change(t, "axe"); break;
			case STRIPPED_JUNGLE_WOOD: change(t, "axe"); break;
			case STRIPPED_OAK_LOG: change(t, "axe"); break;
			case STRIPPED_OAK_WOOD: change(t, "axe"); break;
			case STRIPPED_SPRUCE_LOG: change(t, "axe"); break;
			case STRIPPED_SPRUCE_WOOD: change(t, "axe"); break;
			case STRIPPED_WARPED_HYPHAE: change(t, "axe"); break;
			case STRIPPED_WARPED_STEM: change(t, "axe"); break;
			// case STRUCTURE_BLOCK:
			// case STRUCTURE_VOID:
			// case SUGAR:
			case SUGAR_CANE: change(t, "axe"); break;
			// case SUNFLOWER:
			// case SUSPICIOUS_STEW:
			// case SWEET_BERRIES:
			// case SWEET_BERRY_BUSH:
			// case TALL_GRASS:
			// case TALL_SEAGRASS:
			// case TARGET:
			case TERRACOTTA: change(t, "pickaxe"); break;
			// case TINTED_GLASS:
			// case TIPPED_ARROW:
			// case TNT:
			case TNT_MINECART: change(t, "pickaxe"); break;
			case TORCH: change(t, "axe"); break;
			// case TOTEM_OF_UNDYING:
			// case TRADER_LLAMA_SPAWN_EGG:
			case TRAPPED_CHEST: change(t, "axe"); break;
			// case TRIDENT:
			// case TRIPWIRE:
			// case TRIPWIRE_HOOK:
			// case TROPICAL_FISH:
			// case TROPICAL_FISH_BUCKET:
			// case TROPICAL_FISH_SPAWN_EGG:
			case TUBE_CORAL: change(t, "pickaxe"); break;
			case TUBE_CORAL_BLOCK: change(t, "pickaxe"); break;
			// case TUBE_CORAL_FAN:
			// case TUBE_CORAL_WALL_FAN:
			case TUFF: change(t, "pickaxe"); break;
			// case TURTLE_EGG:
			// case TURTLE_HELMET:
			// case TURTLE_SPAWN_EGG:
			// case TWISTING_VINES:
			case TWISTING_VINES_PLANT: change(t, "axe"); break;
			// case VEX_SPAWN_EGG:
			// case VILLAGER_SPAWN_EGG:
			// case VINDICATOR_SPAWN_EGG:
			// case VINE:
			// case VOID_AIR:
			case WALL_TORCH: change(t, "axe"); break;
			// case WANDERING_TRADER_SPAWN_EGG:
			case WARPED_BUTTON: change(t, "axe"); break;
			case WARPED_DOOR: change(t, "axe"); break;
			case WARPED_FENCE: change(t, "axe"); break;
			case WARPED_FENCE_GATE: change(t, "axe"); break;
			// case WARPED_FUNGUS:
			// case WARPED_FUNGUS_ON_A_STICK:
			case WARPED_HYPHAE: change(t, "axe"); break;
			case WARPED_NYLIUM: change(t, "axe"); break;
			case WARPED_PLANKS: change(t, "axe"); break;
			case WARPED_PRESSURE_PLATE: change(t, "axe"); break;
			// case WARPED_ROOTS:
			case WARPED_SIGN: change(t, "axe"); break;
			case WARPED_SLAB: change(t, "axe"); break;
			case WARPED_STAIRS: change(t, "axe"); break;
			case WARPED_STEM: change(t, "axe"); break;
			case WARPED_TRAPDOOR: change(t, "axe"); break;
			case WARPED_WALL_SIGN: change(t, "axe"); break;
			case WARPED_WART_BLOCK: change(t, "hoe"); break;
			// case WATER:
			// case WATER_BUCKET:
			case WATER_CAULDRON: change(t, "pickaxe"); break;
			case WAXED_COPPER_BLOCK: change(t, "pickaxe"); break;
			case WAXED_CUT_COPPER: change(t, "pickaxe"); break;
			case WAXED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case WAXED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case WAXED_EXPOSED_COPPER: change(t, "pickaxe"); break;
			case WAXED_EXPOSED_CUT_COPPER: change(t, "pickaxe"); break;
			case WAXED_EXPOSED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case WAXED_EXPOSED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case WAXED_OXIDIZED_COPPER: change(t, "pickaxe"); break;
			case WAXED_OXIDIZED_CUT_COPPER: change(t, "pickaxe"); break;
			case WAXED_OXIDIZED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case WAXED_OXIDIZED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case WAXED_WEATHERED_COPPER: change(t, "pickaxe"); break;
			case WAXED_WEATHERED_CUT_COPPER: change(t, "pickaxe"); break;
			case WAXED_WEATHERED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case WAXED_WEATHERED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			case WEATHERED_COPPER: change(t, "pickaxe"); break;
			case WEATHERED_CUT_COPPER: change(t, "pickaxe"); break;
			case WEATHERED_CUT_COPPER_SLAB: change(t, "pickaxe"); break;
			case WEATHERED_CUT_COPPER_STAIRS: change(t, "pickaxe"); break;
			// case WEEPING_VINES:
			// case WEEPING_VINES_PLANT:
			case WET_SPONGE: change(t, "hoe"); break;
			case WHEAT: change(t, "hoe"); break;
			// case WHEAT_SEEDS:
			case WHITE_BANNER: change(t, "axe"); break;
			case WHITE_BED: change(t, "axe"); break;
			// case WHITE_CANDLE:
			// case WHITE_CANDLE_CAKE:
			// case WHITE_CARPET:
			case WHITE_CONCRETE: change(t, "pickaxe"); break;
			case WHITE_CONCRETE_POWDER: change(t, "shovel"); break;
			// case WHITE_DYE:
			case WHITE_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case WHITE_SHULKER_BOX: change(t, "pickaxe"); break;
			// case WHITE_STAINED_GLASS:
			// case WHITE_STAINED_GLASS_PANE:
			case WHITE_TERRACOTTA: change(t, "pickaxe"); break;
			// case WHITE_TULIP:
			case WHITE_WALL_BANNER: change(t, "axe"); break;
			// case WHITE_WOOL:
			// case WITCH_SPAWN_EGG:
			// case WITHER_ROSE:
			// case WITHER_SKELETON_SKULL:
			// case WITHER_SKELETON_SPAWN_EGG:
			// case WITHER_SKELETON_WALL_SKULL:
			// case WOLF_SPAWN_EGG:
			// case WOODEN_AXE:
			// case WOODEN_HOE:
			// case WOODEN_PICKAXE:
			// case WOODEN_SHOVEL:
			// case WOODEN_SWORD:
			// case WRITABLE_BOOK:
			// case WRITTEN_BOOK:
			case YELLOW_BANNER: change(t, "axe"); break;
			case YELLOW_BED: change(t, "axe"); break;
			// case YELLOW_CANDLE:
			// case YELLOW_CANDLE_CAKE:
			// case YELLOW_CARPET:
			case YELLOW_CONCRETE: change(t, "pickaxe"); break;
			case YELLOW_CONCRETE_POWDER: change(t, "shovel"); break;
			// case YELLOW_DYE:
			case YELLOW_GLAZED_TERRACOTTA: change(t, "pickaxe"); break;
			case YELLOW_SHULKER_BOX: change(t, "pickaxe"); break;
			// case YELLOW_STAINED_GLASS:
			// case YELLOW_STAINED_GLASS_PANE:
			case YELLOW_TERRACOTTA: change(t, "pickaxe"); break;
			case YELLOW_WALL_BANNER: change(t, "axe"); break;
			// case YELLOW_WOOL:
			// case ZOGLIN_SPAWN_EGG:
			// case ZOMBIE_HEAD:
			// case ZOMBIE_HORSE_SPAWN_EGG:
			// case ZOMBIE_SPAWN_EGG:
			// case ZOMBIE_VILLAGER_SPAWN_EGG:
			// case ZOMBIE_WALL_HEAD:
			// case ZOMBIFIED_PIGLIN_SPAWN_EGG:
			// case ACACIA_LEAVES:
			// case ACACIA_SAPLING:
			// case AIR:
			// case ALLIUM:
			// case AMETHYST_SHARD:
			// case APPLE:
			// case ARROW:
			// case AXOLOTL_BUCKET:
			// case AXOLOTL_SPAWN_EGG:
			// case BAKED_POTATO:
			// case BAT_SPAWN_EGG:
			// case BEEF:
			// case BEETROOT_SEEDS:
			// case BEETROOT_SOUP:
			// case BEE_SPAWN_EGG:
			// case BIRCH_LEAVES:
			// case BIRCH_SAPLING:
			// case BLACK_CANDLE:
			// case BLACK_CANDLE_CAKE:
			// case BLACK_CARPET:
			// case BLACK_DYE:
			// case BLACK_WOOL:
			// case BLAZE_POWDER:
			// case BLAZE_ROD:
			// case BLAZE_SPAWN_EGG:
			// case BLUE_CANDLE:
			// case BLUE_CANDLE_CAKE:
			// case BLUE_CARPET:
			// case BLUE_DYE:
			// case BLUE_ORCHID:
			// case BLUE_WOOL:
			// case BONE:
			// case BONE_MEAL:
			// case BOOK:
			// case BOW:
			// case BOWL:
			// case BRAIN_CORAL_FAN:
			// case BRAIN_CORAL_WALL_FAN:
			// case BREAD:
			// case BRICK:
			// case BROWN_CANDLE:
			// case BROWN_CANDLE_CAKE:
			// case BROWN_CARPET:
			// case BROWN_DYE:
			// case BROWN_MUSHROOM:
			// case BROWN_WOOL:
			// case BUBBLE_COLUMN:
			// case BUBBLE_CORAL_FAN:
			// case BUBBLE_CORAL_WALL_FAN:
			// case BUCKET:
			// case BUNDLE:
			// case CAKE:
			// case CANDLE:
			// case CANDLE_CAKE:
			// case CARROT:
			// case CARROTS:
			// case CARROT_ON_A_STICK:
			// case CAT_SPAWN_EGG:
			// case CAVE_AIR:
			// case CAVE_SPIDER_SPAWN_EGG:
			// case CAVE_VINES:
			// case CAVE_VINES_PLANT:
			// case CHAINMAIL_BOOTS:
			// case CHAINMAIL_CHESTPLATE:
			// case CHAINMAIL_HELMET:
			// case CHAINMAIL_LEGGINGS:
			// case CHAIN_COMMAND_BLOCK:
			// case CHARCOAL:
			// case CHEST_MINECART:
			// case CHICKEN:
			// case CHICKEN_SPAWN_EGG:
			// case CHORUS_FRUIT:
			// case CHORUS_PLANT:
			// case CLAY_BALL:
			// case CLOCK:
			// case COAL:
			// case COCOA_BEANS:
			// case COD:
			// case COD_BUCKET:
			// case COD_SPAWN_EGG:
			// case COMMAND_BLOCK:
			// case COMMAND_BLOCK_MINECART:
			// case COMPASS:
			// case COOKED_BEEF:
			// case COOKED_CHICKEN:
			// case COOKED_COD:
			// case COOKED_MUTTON:
			// case COOKED_PORKCHOP:
			// case COOKED_RABBIT:
			// case COOKED_SALMON:
			// case COOKIE:
			// case COPPER_INGOT:
			// case CORNFLOWER:
			// case COW_SPAWN_EGG:
			// case CRIMSON_ROOTS:
			// case LIME_CARPET:
			// case MAGENTA_CARPET:
			// case ORANGE_CARPET:
			default:
				break;
		}
		
	}
}
