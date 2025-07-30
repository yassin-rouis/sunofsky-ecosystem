package net.joueuranonyme.sunofsky.items.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {
	public static void registerEvents(JavaPlugin plugin) {
    	PluginManager pm = Bukkit.getServer().getPluginManager();
    	pm.registerEvents(new Armors_Events(), plugin);
    	pm.registerEvents(new Magnet_Event(), plugin);
    	pm.registerEvents(new RcMagnet_Event(), plugin);
    	pm.registerEvents(new HeadDrop_Event(), plugin);
    	pm.registerEvents(new Multitool_Event(), plugin);
    	pm.registerEvents(new SuperPickaxe_Event(), plugin);
    	pm.registerEvents(new SuperAxe_Event(), plugin);
    	pm.registerEvents(new InstantBreak_Event(), plugin);
    	pm.registerEvents(new SuperHoe_Event(), plugin);
    	pm.registerEvents(new SuperBucket_Event(), plugin);
    	pm.registerEvents(new Commands_Event(), plugin);
    	pm.registerEvents(new InstantBreak_Event(), plugin);
	}

	public static void init() {
		Armors_Events.init();
	}

	public static void unload() {
		//Plugin.unload();
	}
}
