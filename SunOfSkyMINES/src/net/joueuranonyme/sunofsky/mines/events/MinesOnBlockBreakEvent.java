package net.joueuranonyme.sunofsky.mines.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import net.joueuranonyme.sunofsky.mines.addons.YmlManager;
import net.joueuranonyme.sunofsky.mines.Main;

public class MinesOnBlockBreakEvent implements Listener {

	private static FileConfiguration config;
	private static Map<Material, List<Location>> pMatLocs = new HashMap<Material, List<Location>>();
	private static Map<Material, List<Location>> pMatActualLocs = new HashMap<Material, List<Location>>();
	private static Map<Material, Double> pMatChances = new HashMap<Material, Double>();
	private static Map<Location, BukkitRunnable> pLocsTasks = new HashMap<Location, BukkitRunnable>();
	private static World pWorld;
	
	public static boolean isHandled(Block b) {
		List<Location> locs = new ArrayList<Location>();
		
		for(List<Location> list: pMatLocs.values()) {
			locs.addAll(list);
		}
		
		for(List<Location> list: pMatActualLocs.values()) {
			locs.addAll(list);
		}
		
		return locs.contains(b.getLocation());
	}
	
	public static void breakBlock(Block b, Player p) {
		if(pMatActualLocs.containsKey(b.getType())) {
			if(!pMatActualLocs.get(b.getType()).contains(b.getLocation())) return;
			
			List<Location> oreStonesRemaining = new ArrayList<Location>();
			
			for(Location loc: pMatLocs.get(b.getType())) oreStonesRemaining.add(loc);
			
			oreStonesRemaining.removeAll(pMatActualLocs.get(b.getType()));
			
			boolean ok = false;
			Location randLoc = null;
			
			while(!ok) {
				int randomInt = (int) (Math.random() * (oreStonesRemaining.size()-1));
				randLoc = oreStonesRemaining.get(randomInt);
				ok = b.getWorld().getBlockAt(randLoc).getType().equals(Material.STONE);
			}
			
			oreStonesRemaining.remove(randLoc);
			
			Material mat = b.getType();
			b.getWorld().getBlockAt(randLoc).setType(mat);
			
			pMatActualLocs.get(mat).add(randLoc);

			if(p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
				ItemStack iss = new ItemStack(mat);
				p.getInventory().addItem(iss);
			} else {
				for(ItemStack is: b.getDrops(p.getInventory().getItemInMainHand(), p))	p.getInventory().addItem(is);
			}		
			
			b.setType(Material.STONE);
			
			pMatActualLocs.get(mat).remove(b.getLocation());
			return;
		}
		
		if(b.getType().equals(Material.STONE)) {
			BukkitRunnable task = new BukkitRunnable() {
				Block bl = b;
				
				@Override
				public void run() {
					bl.setType(Material.STONE);
					cancel();
					pLocsTasks.remove(b.getLocation());
				}
			};
			
			task.runTaskLater(Main.getInstance(), 100L);
			
			if(p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
				ItemStack iss = new ItemStack(Material.STONE);
				p.getInventory().addItem(iss);
			} else {
				ItemStack isc = new ItemStack(Material.COBBLESTONE);
				p.getInventory().addItem(isc);
			}
			
			b.setType(Material.COBBLESTONE);
			pLocsTasks.put(b.getLocation(), task);
			return;
		}
		
		if(b.getType().equals(Material.COBBLESTONE)) {
			if(pLocsTasks.containsKey(b.getLocation())) {
				pLocsTasks.get(b.getLocation()).cancel();
				pLocsTasks.remove(b.getLocation());
			}
			
			BukkitRunnable task = new BukkitRunnable() {
				Block bl = b;
				
				@Override
				public void run() {
					bl.setType(Material.STONE);
					cancel();
					pLocsTasks.remove(b.getLocation());
				}
			};
			
			task.runTaskLater(Main.getInstance(), 100L);

			for(ItemStack is: b.getDrops())	p.getInventory().addItem(is);
			
			b.setType(Material.BEDROCK);
			pLocsTasks.put(b.getLocation(), task);
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(event.isCancelled()) return;
		if(!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) return;
		Block b = event.getBlock();

		if(!b.getWorld().equals(pWorld)) return;
		
		List<Location> stones = new ArrayList<Location>();
		
		for(Material mat: pMatLocs.keySet()) {
			stones.addAll(pMatLocs.get(mat));
		}
		
		if(!(
			pMatActualLocs.containsKey(b.getType()) 
			|| pLocsTasks.containsKey(b.getLocation()) 
			|| (stones.contains(b.getLocation())
				&& (b.getType().equals(Material.STONE) 
					|| b.getType().equals(Material.COBBLESTONE))))) return;
		
		event.setCancelled(true);
		
		breakBlock(b, event.getPlayer());
	}


	public static boolean init() {
		try {
			Main.printMc("Initialisation des blocs pouvant être minés... §8Cette opération peut prendre un certain temps...");
			config = YmlManager.readAndCheck("plugins/SOS_Mines/mines.yml", new String[] {
					"ores", "ores_world"
			});
			
			int loadedBlocks = 0;
			int loadedOres = 0;
			
			Main.printMc(String.format("§f█ §nConfiguration des blocs ..."));

			Main.printMc("├╼ Vérification du monde...");
			
			World world = Bukkit.getWorld(config.getString("ores_world"));
			
			if(world == null) {
				Main.printMc("╰╼ §c⚠ Le monde specifié '"+config.getString("ores_world")+"' est introuvable. Vérifiez son orthographe, et réessayez. §7Chargement annulé");
				return false;
			} else {
				Main.printMc("├╼ Monde §avalide§r...");
				pWorld = world;
			}
			
			for(String ore : config.getConfigurationSection("ores").getKeys(false)) {
				int oreLoadedBlocks = 0;				
				
				Main.printMc(String.format("├╼ §fConfiguration des '§6%s§f' ...", ore));
				
				Main.printMc("│  ├╼ Vérification de l'identifiant...");
				
				Material material = Material.getMaterial(ore.toUpperCase());

				
				if(material == null) {
					Main.printMc(String.format("│  ╰╼ §c⚠ L'identifiant '%s' n'est pas reconnu, vérifiez son orthographe, et réessayez. §7N.B.: Cette ressource est ignoré.", ore));
					continue;
				}
				
				switch(material) {
					case COAL_ORE:
					case COPPER_ORE:
					case REDSTONE_ORE:
					case IRON_ORE:
					case GOLD_ORE:
					case LAPIS_ORE:
					case DIAMOND_ORE: 
					case EMERALD_ORE: {
						Main.printMc("│  ├╼ Identifiant §avalide§r...");
						break;
					}
					default: {
						Main.printMc(String.format("│  ╰╼ §c⚠ L'identifiant '%s' n'est pas reconnu, vérifiez son orthographe, et réessayez. §7N.B.: Cette ressource est ignoré.", ore));
						continue;
					}
				}
				
				Main.printMc("│  ├╼ Vérification de la configuration...");

				boolean valid = true;
				for(String check: new String[] {"chance","zone"}) {
					if(!config.getConfigurationSection("ores."+ore).getKeys(false).contains(check)) valid = false;
				}
				
				if(valid) {
					for(String zone_check: new String[] {"x1","y1","z1","x2","y2","z2"}) {
						if(!config.getConfigurationSection("ores."+ore+".zone").getKeys(false).contains(zone_check)) valid = false;
					}
				}
				
				double chance = 0;
				
				if(valid) {
					chance = config.getDouble("ores."+ore+".chance")/100.0;
					if(!(chance <= 100 && chance >= 0)) {
						valid = false;
					}
				}
				
				if(!valid) {
					Main.printMc(String.format("│  ╰╼ §c⚠ La section '%s' n'est pas correctement configurée. Vérifiez chaques paramètres et réessayez. §7N.B.: Cette ressource est ignoré.", ore));
					continue;
				} else {
					Main.printMc("│  ├╼ Configuration §avalide§r...");
				}
				
				int x1 = config.getInt("ores."+ore+".zone.x1"),
					y1 = config.getInt("ores."+ore+".zone.y1"),
					z1 = config.getInt("ores."+ore+".zone.z1"),
					x2 = config.getInt("ores."+ore+".zone.x2"),
					y2 = config.getInt("ores."+ore+".zone.y2"),
					z2 = config.getInt("ores."+ore+".zone.z2");

				BoundingBox zone = new BoundingBox(x1, y1, z1, x2, y2, z2);
				BoundingBox volumeZone = new BoundingBox(zone.getMinX(), zone.getMinY(), zone.getMinZ(), zone.getMaxX()+1, zone.getMaxY()+1, zone.getMaxZ()+1);
				int volume = (int) volumeZone.getVolume();
				
				List<Location> blockLocs = new ArrayList<Location>();
				
				Main.printMc(String.format("│  ├╼ §fScan de §6%s§f blocs... §8Cette opération peut prendre un certain temps...", volume));
				int scanned = 0;
				
				Main.printMc(String.format("│  │  ├╼ §eEn cours§r [ §60%%§r]..."));
				
				for(int x = (int) zone.getMinX(); x < zone.getMaxX() + 1; x++) {
					for(int y = (int) zone.getMinY(); y < zone.getMaxY() + 1; y++) {
						for(int z = (int) zone.getMinZ(); z < zone.getMaxZ() + 1; z++) {

							scanned++;
							int p_pre = (int) (((float) (scanned-1))*10/volume);
							int p_act = (int) (((float) (scanned))*10/volume);
							
							if(p_pre < p_act) {
								Main.printMc(String.format("│  │  ├╼ §eEn cours§r [§6%s%%§r]...", p_act*10));
							}
							
							Block b = world.getBlockAt(x,y,z);
							
							if(b.getType() != Material.STONE) continue;
							boolean exposed = world.getBlockAt(x+1,y,z).isEmpty()
								|| world.getBlockAt(x,y+1,z).isEmpty()
								|| world.getBlockAt(x,y,z+1).isEmpty()
								|| world.getBlockAt(x-1,y,z).isEmpty()
								|| world.getBlockAt(x,y-1,z).isEmpty()
								|| world.getBlockAt(x,y,z-1).isEmpty();
							
							if(!exposed) continue;
							
							oreLoadedBlocks++;
							blockLocs.add(b.getLocation());
						}
					}
				}
				
				Main.printMc(String.format("│  │  ╰╼ §aTerminé !"));
				Main.printMc(String.format("│  ├╼ §6%s§r blocs ont été scannés et §a%s§r ont été validés.", scanned, oreLoadedBlocks));
				
				pMatLocs.put(material, blockLocs);
				pMatChances.put(material, chance);
				loadedOres ++;
				loadedBlocks += oreLoadedBlocks;
				
				Main.printMc(String.format("│  ╰╼ §aTerminé ! §fLa configuration du bloc '§6%s§f' est prête.", ore));
			}
			Main.printMc(String.format("╰╼ §aTerminé ! §fLa configuration des blocs est terminée. §6%s§f ressources ont été configurées, et §6%s§f blocs ont été chargés.", loadedOres, loadedBlocks));

			Main.printMc(String.format(""));
			Main.printMc(String.format("§f█ §nGénération aléatoire des minerais ..."));
			Main.printMc(String.format("├╼ §fListage des blocs de roche ..."));
			
			List<Location> stonesRemaining = new ArrayList<Location>();
			for(Entry<Material, List<Location>> matE: pMatLocs.entrySet()) {
				for(Location matL : matE.getValue()) {
					stonesRemaining.add(matL);
				}
			}
			
			for(Material mat: pMatChances.keySet()) {
				Main.printMc(String.format("├╼ §fGénération des '§6%s§f' ...", mat.name()));
				
				List<Location> oreStones = pMatLocs.get(mat);
				List<Location> oreStonesRemaining = new ArrayList<Location>();
				
				for(Location loc: oreStones) {
					if(stonesRemaining.contains(loc)) {
						oreStonesRemaining.add(loc);
					}
				}
				
				int generatedBlocks = 0;
				int maxBlocksGeneration = oreStonesRemaining.size();
				
				if(maxBlocksGeneration == 0) {
					Main.printMc(String.format("│  ╰╼ §c⚠ Génération ignorée : manque de roche cible."));
					continue;
				}
				
				Main.printMc(String.format("│  ├╼ Sélection aléatoire des positions ..."));
				
				while((((float) generatedBlocks)/maxBlocksGeneration) < pMatChances.get(mat)) {
					Random rand = new Random();
					Location randLoc = oreStonesRemaining.get(rand.nextInt(oreStonesRemaining.size()));
					oreStonesRemaining.remove(randLoc);
					stonesRemaining.remove(randLoc);
					generatedBlocks ++;
					pWorld.getBlockAt(randLoc).setType(mat);
					
					if(pMatActualLocs.containsKey(mat)) {
						pMatActualLocs.get(mat).add(randLoc);
					} else {
						List<Location> newList = new ArrayList<Location>();
						newList.add(randLoc);
						pMatActualLocs.put(mat, newList);
					}
				}

				Main.printMc(String.format("│  ╰╼ §6%s§r/§6%s§r blocs générés ...", generatedBlocks, maxBlocksGeneration));
			}
			
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	public static void unload() {
		for(List<Location> locs: pMatActualLocs.values()) {
			for(Location loc: locs) {
				pWorld.getBlockAt(loc).setType(Material.STONE);
			}
		}
	}
}
