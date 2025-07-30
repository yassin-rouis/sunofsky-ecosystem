package net.joueuranonyme.sunofsky.menus.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.menus.commands.ShopCommand;

public class TabComplete {
	public static void registerCompleters(JavaPlugin plugin) {
		plugin.getCommand("sosshop").setTabCompleter(new ShopCommand_Completer());
	}
}
