package net.joueuranonyme.sunofsky.menus.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {
	public static void registerEvents(JavaPlugin plugin) {
    	PluginManager pm = Bukkit.getServer().getPluginManager();
    	pm.registerEvents(new ShopMenu_Events(), plugin);
	}

	public static void init() {
		ShopMenu_Events.init();
	}
}
