package net.joueuranonyme.sunofsky.items.commands;

import org.bukkit.plugin.java.JavaPlugin;


public class Commands {
	public static void registerCommands(JavaPlugin plugin) {
		plugin.getCommand("sosgive").setExecutor(new GiveCommand());
	}
}
