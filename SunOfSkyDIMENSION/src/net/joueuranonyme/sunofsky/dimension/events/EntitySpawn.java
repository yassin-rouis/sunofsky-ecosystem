package net.joueuranonyme.sunofsky.dimension.events;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntitySpawnEvent;

import net.joueuranonyme.sunofsky.dimension.Dimension;
import net.joueuranonyme.sunofsky.dimension.TimerBossBar;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;

public class EntitySpawn implements Listener {
	private static FileConfiguration config;	
	
	@EventHandler
	public void onEntitySpawnEvent(CreatureSpawnEvent event) {
		if(!event.getEntity().getWorld().getName().equals(Dimension.getConfig().getString("world"))) return;
		if(!event.getSpawnReason().equals(SpawnReason.SPAWNER)) return;
		String entityId = event.getEntity().getType().name().toLowerCase();		
		if(!config.getConfigurationSection("mobs").contains(entityId)) return;
		
		
		if(!(config.getConfigurationSection("mobs." + entityId).contains("full") &&
			 config.getConfigurationSection("mobs." + entityId).contains("loots") )){
			return;
		}
		
		if(!config.getBoolean("mobs." + entityId + ".full")) {
			
			double max = 0;
		
			for(String loot : config.getConfigurationSection("mobs." + entityId + ".loots").getKeys(false)) {
				if(!(config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("chance") &&
					 config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("command") )){
					continue;
				}
				max += config.getDouble("mobs." + entityId + ".loots." + loot + ".chance");
			}
			
			double random = (new Random()).nextDouble() * 100;
			
			if(random >= max) {
				return;
			}
		}
		
		if(config.getConfigurationSection("mobs." + entityId).contains("mobName")) {
			event.getEntity().setCustomName(config.getString("mobs." + entityId + ".mobName"));
			event.getEntity().setCustomNameVisible(true);
		}
		
		Dimension.customEntities.add(event.getEntity());
	}
	

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/mobs.yml", new String[] {
				"mobs"
			});
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
