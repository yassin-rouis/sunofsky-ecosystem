package net.joueuranonyme.sunofsky.dimension.commands;

import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.dimension.Main;
import net.joueuranonyme.sunofsky.dimension.addons.ConsoleColors;

public class Commands {	
	public static void registerCommands(JavaPlugin plugin) {
		plugin.getCommand("dimension").setExecutor(new DimensionCommand());
	}

	public static void init() {
		if(!DimensionCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [DimensionCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
	}
}
