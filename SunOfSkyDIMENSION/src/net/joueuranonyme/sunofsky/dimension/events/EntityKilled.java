package net.joueuranonyme.sunofsky.dimension.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.joueuranonyme.sunofsky.dimension.Dimension;
import net.joueuranonyme.sunofsky.dimension.Main;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;

public class EntityKilled implements Listener {
	private static FileConfiguration config;	
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		if(!event.getEntity().getWorld().getName().equals(Dimension.getConfig().getString("world"))) return;
		if(!Dimension.customEntities.contains(event.getEntity())) return;
		
		String entityId = event.getEntity().getType().name().toLowerCase();
		
		event.getDrops().clear();
		
		double max = 0;
		
		for(String loot : config.getConfigurationSection("mobs." + entityId + ".loots").getKeys(false)) {
			if(!(config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("chance") &&
				 config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("command") )){
				continue;
			}
			max += config.getDouble("mobs." + entityId + ".loots." + loot + ".chance");
		}
		
		double random = (new Random()).nextDouble(max);
		
		ConfigurationSection cs = null;
		double pA = 0, pB = 0;
		
		for(String loot : config.getConfigurationSection("mobs." + entityId + ".loots").getKeys(false)) {
			if(!(config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("chance") &&
				 config.getConfigurationSection("mobs." + entityId + ".loots." + loot).contains("command") )){
				continue;
			}
			
			pB = pA + config.getDouble("mobs." + entityId + ".loots." + loot + ".chance");
			
			if(pA < random && random < pB) {
				cs = config.getConfigurationSection("mobs." + entityId + ".loots." + loot);
			}
			
			pA += config.getDouble("mobs." + entityId + ".loots." + loot + ".chance");
		}
		
		if(cs != null) {
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			Bukkit.dispatchCommand(console, cs.getString("command").replaceAll("\\{PLAYER\\}", event.getEntity().getKiller().getName()));
			
			if(cs.contains("message")) {
				event.getEntity().getKiller().sendMessage(Main.getPrefix(config.getString("prefix")) + cs.getString("message"));
			}
			
			if(cs.contains("announce")) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(Main.getPrefix(config.getString("prefix")) + cs.getString("announce").replaceAll("\\{PLAYER\\}", event.getEntity().getKiller().getName()));
				}
			}
		}
	}
	

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/mobs.yml", new String[] {
				"mobs", "prefix"
			});
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
