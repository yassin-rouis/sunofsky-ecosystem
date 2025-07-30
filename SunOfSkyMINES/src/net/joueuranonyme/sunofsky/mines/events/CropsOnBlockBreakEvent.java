package net.joueuranonyme.sunofsky.mines.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import net.joueuranonyme.sunofsky.mines.addons.YmlManager;
import net.joueuranonyme.sunofsky.mines.Main;

public class CropsOnBlockBreakEvent implements Listener {

	private static FileConfiguration config;
	private static Map<Material, List<Location>> pMatLocs = new HashMap<Material, List<Location>>();
	private static World pWorld;
	
	public static boolean isHandled(Block b) {
		if(!pMatLocs.containsKey(b.getType())) return false;
		return pMatLocs.get(b.getType()).contains(b.getLocation());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) return;
		Block b = event.getBlock();
		if(!pMatLocs.containsKey(b.getType())) return;
		if(!pMatLocs.get(b.getType()).contains(b.getLocation())) return;
		Ageable ageable = (Ageable) b.getBlockData();
		event.setCancelled(true);
		if(ageable.getAge() != ageable.getMaximumAge()) return;
		
		for(ItemStack i: b.getDrops()) {
			event.getPlayer().getInventory().addItem(i);
			BukkitRunnable task = new BukkitRunnable() {
				Location loc = b.getLocation();
				Material cropType = b.getType();
				@Override
				public void run() {
					autoGrowCrop(loc, cropType);
				}
			};
			
			task.runTaskLater(Main.getInstance(), 100L);
		}
		
		b.setType(Material.AIR);
	}
	
	private static void autoGrowCrop(Location loc, Material cropType) {
		Block b = pWorld.getBlockAt(loc);
		if(!b.getType().equals(cropType) && !b.getType().equals(Material.AIR)) return;
		
		if(b.getType().equals(Material.AIR)) {
			b.setType(cropType);
		} else {
			Ageable ageable = (Ageable) b.getBlockData();
			if(ageable.getAge() >= ageable.getMaximumAge()) return;
			
			ageable.setAge(ageable.getAge()+1);
			b.setBlockData(ageable);
		}

		BukkitRunnable task = new BukkitRunnable() {
			Location loc = b.getLocation();
			Material cropType = b.getType();
			@Override
			public void run() {
				autoGrowCrop(loc, cropType);
			}
		};
		
		task.runTaskLater(Main.getInstance(), 5L);
		return;
	}


	public static boolean init() {
		try {
			Main.printMc("Initialisation des ressources pouvant être récoltées... §8Cette opération peut prendre un certain temps...");
			config = YmlManager.readAndCheck("plugins/SOS_Mines/mines.yml", new String[] {
					"crops", "crops_world"
			});
			
			int loadedCrops = 0;
			int loadedCropsTypes = 0;
			
			Main.printMc(String.format("§f█ §nConfiguration des blocs ..."));

			Main.printMc("├╼ Vérification du monde...");
			
			World world = Bukkit.getWorld(config.getString("crops_world"));
			
			if(world == null) {
				Main.printMc("╰╼ §c⚠ Le monde specifié '"+config.getString("crops_world")+"' est introuvable. Vérifiez son orthographe, et réessayez. §7Chargement annulé");
				return false;
			} else {
				Main.printMc("├╼ Monde §avalide§r...");
				pWorld = world;
			}
			
			for(String crop : config.getConfigurationSection("crops").getKeys(false)) {
				int typeLoadedCrops = 0;
				
				Main.printMc(String.format("├╼ §fConfiguration des '§2%s§f' ...", crop));
				Main.printMc(String.format("│  ├╼ Vérification de l'identifiant..."));
				
				Material material = Material.getMaterial(crop.toUpperCase());

				
				if(material == null) {
					Main.printMc(String.format("│  ╰╼ §c⚠ L'identifiant '%s' n'est pas reconnu, vérifiez son orthographe, et réessayez. §7N.B.: Cette ressource est ignoré.", crop));
					continue;
				}
				
				switch(material) {
					case POTATOES:
					case BEETROOTS:
					case CARROTS:
					case WHEAT: {
						Main.printMc("│  ├╼ Identifiant §avalide§r...");
						break;
					}
					default: {
						Main.printMc(String.format("│  ╰╼ §c⚠ L'identifiant '%s' n'est pas reconnu, vérifiez son orthographe, et réessayez. §7N.B.: Cette ressource est ignoré.", crop));
						continue;
					}
				}
				Main.printMc("│  ├╼ Vérification de la configuration...");
				
				boolean valid = true;
				if(!config.getConfigurationSection("crops."+crop).getKeys(false).contains("zone")) valid = false;
				
				if(valid) {
					for(String zone_check: new String[] {"x1","y1","z1","x2","y2","z2"}) {
						if(!config.getConfigurationSection("crops."+crop+".zone").getKeys(false).contains(zone_check)) valid = false;
					}
				}
				
				if(!valid) {
					Main.printMc(String.format("│  ╰╼ §c⚠ La section '%s' n'est pas correctement configurée. Vérifiez chaques paramètres et réessayez. §7N.B.: Cette ressource est ignoré.", crop));
					continue;
				} else {
					Main.printMc("│  ├╼ Configuration §avalide§r...");
				}
				
				int x1 = config.getInt("crops."+crop+".zone.x1"),
					y1 = config.getInt("crops."+crop+".zone.y1"),
					z1 = config.getInt("crops."+crop+".zone.z1"),
					x2 = config.getInt("crops."+crop+".zone.x2"),
					y2 = config.getInt("crops."+crop+".zone.y2"),
					z2 = config.getInt("crops."+crop+".zone.z2");

				BoundingBox zone = new BoundingBox(x1, y1, z1, x2, y2, z2);
				BoundingBox volumeZone = new BoundingBox(zone.getMinX(), zone.getMinY(), zone.getMinZ(), zone.getMaxX()+1, zone.getMaxY()+1, zone.getMaxZ()+1);
				int volume = (int) volumeZone.getVolume();
				
				List<Location> blockLocs = new ArrayList<Location>();
				
				Main.printMc(String.format("│  ├╼ §fScan de §2%s§f blocs... §8Cette opération peut prendre un certain temps...", volume));
				int scanned = 0;
				
				Main.printMc(String.format("│  │  ├╼ §eEn cours§r [ §20%%§r]..."));
				for(int x = (int) zone.getMinX(); x < zone.getMaxX() + 1; x++) {
					for(int y = (int) zone.getMinY(); y < zone.getMaxY() + 1; y++) {
						for(int z = (int) zone.getMinZ(); z < zone.getMaxZ() + 1; z++) {

							scanned++;
							int p_pre = (int) (((float) (scanned-1))*10/volume);
							int p_act = (int) (((float) (scanned))*10/volume);
							
							if(p_pre < p_act) {
								Main.printMc(String.format("│  │  ├╼ §eEn cours§r [§2%s%%§r]...", p_act*10));
							}
							
							Block b = world.getBlockAt(x,y,z);

							boolean positionOk = b.getType().equals(material);
							//boolean positionOk = b.getType().equals(material) || (world.getBlockAt(x,y-1,z).getType().equals(Material.FARMLAND) && b.isEmpty());
							
							if(!positionOk) continue;
							b.setType(material);

							Ageable ageable = (Ageable) b.getBlockData();
							ageable.setAge(ageable.getMaximumAge());
							b.setBlockData(ageable);

							
							typeLoadedCrops++;
							blockLocs.add(b.getLocation());
						}
					}
				}
				
				Main.printMc(String.format("│  │  ╰╼ §aTerminé !"));
				Main.printMc(String.format("│  ├╼ §2%s§r blocs ont été scannés et §2%s§r ont été validés.", typeLoadedCrops, typeLoadedCrops));

				pMatLocs.put(material, blockLocs);
				loadedCropsTypes ++;
				loadedCrops += scanned;
				
				Main.printMc(String.format("│  ╰╼ §aTerminé ! §fLa configuration du bloc '§2%s§f' est prête.", crop));
			}
			Main.printMc(String.format("╰╼ §aTerminé ! §fLa configuration des blocs est terminée. §2%s§f types de plantes ont été configurées, et §2%s§f blocs ont été chargés.", loadedCropsTypes, loadedCrops));
			
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	
	public static void unload() {
		
	}
}
