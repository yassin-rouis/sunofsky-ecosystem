package net.joueuranonyme.sunofsky.menus.commands;

import org.bukkit.plugin.java.JavaPlugin;


public class Commands {
	public static void registerCommands(JavaPlugin plugin) {
		plugin.getCommand("sosshop").setExecutor(new ShopCommand());
	}
}
