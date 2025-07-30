package net.joueuranonyme.sunofsky.core.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Placeholders {
	public static void registerPlaceholders(JavaPlugin plugin) {
		@SuppressWarnings("unused") // TEMPORAIRE
    	PluginManager pm = Bukkit.getServer().getPluginManager();

    	(new FlyTimeExpansion(plugin)).register();
    	(new BossbarExpansion(plugin)).register();
    	
	}

	public static void init() {
		FlyTimeExpansion.init();
		BossbarExpansion.init();
	}
}
