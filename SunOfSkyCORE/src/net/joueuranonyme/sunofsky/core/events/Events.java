package net.joueuranonyme.sunofsky.core.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {
	public static void registerEvents(JavaPlugin plugin) {
    	PluginManager pm = Bukkit.getServer().getPluginManager();

    	pm.registerEvents(new FirstJoinEvent(), plugin);
    	pm.registerEvents(new FishEvent(), plugin);
    	pm.registerEvents(new JournalierInventoryClickEvent(), plugin);
    	pm.registerEvents(new Fly_PlayerChangeWorldEvent(), plugin);
    	pm.registerEvents(new Fly_PlayerJoinEvent(), plugin);
    	pm.registerEvents(new Fly_PlayerQuitEvent(), plugin);
    	pm.registerEvents(new Fly_PlayerKickEvent(), plugin);
	}

	public static void init() {
		FirstJoinEvent.init();
		FishEvent.init();
		JournalierInventoryClickEvent.init();
		Fly_PlayerChangeWorldEvent.init();
		Fly_PlayerJoinEvent.init();
		Fly_PlayerQuitEvent.init();
		Fly_PlayerKickEvent.init();
	}
}
