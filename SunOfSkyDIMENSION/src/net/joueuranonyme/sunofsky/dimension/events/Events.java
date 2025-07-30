package net.joueuranonyme.sunofsky.dimension.events;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.dimension.Main;
import net.joueuranonyme.sunofsky.dimension.addons.ConsoleColors;
import net.joueuranonyme.sunofsky.dimension.commands.DimensionCommand;

public class Events {
	public static void registerEvents(JavaPlugin plugin) {
    	PluginManager pm = Bukkit.getServer().getPluginManager();

    	pm.registerEvents(new EntitySpawn(), plugin);
    	pm.registerEvents(new DimensionPlayerJoinEvent(), plugin);
    	pm.registerEvents(new EntityKilled(), plugin);
	}

	public static void init() {
		if(!EntitySpawn.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger l'event [EntitySpawn], il est ignoré, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!EntityKilled.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger l'event [EntityKilled], il est ignoré, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!DimensionPlayerJoinEvent.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger l'event [DimensionPlayerJoinEvent], il est ignoré, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
	}
}