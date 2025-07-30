package net.joueuranonyme.sunofsky.mines.events;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.joueuranonyme.sunofsky.mines.Main;

public class Events {
	public static void registerEvents(JavaPlugin plugin) {
    	PluginManager pm = Bukkit.getServer().getPluginManager();
    	pm.registerEvents(new MinesOnBlockBreakEvent(), plugin);
    	pm.registerEvents(new CropsOnBlockBreakEvent(), plugin);
	}

	public static void init() {
		if(Bukkit.getServer().getWorlds().get(0) == null) {
			BukkitRunnable delayed = new BukkitRunnable() {
				@Override
				public void run() {
					MinesOnBlockBreakEvent.init();
					CropsOnBlockBreakEvent.init();
				}
			};
			delayed.runTaskLater(Main.getInstance(), 1L);
		} else {
			MinesOnBlockBreakEvent.init();
			CropsOnBlockBreakEvent.init();
		}
	}

	public static void unload() {
		MinesOnBlockBreakEvent.unload();
		CropsOnBlockBreakEvent.unload();
	}
}