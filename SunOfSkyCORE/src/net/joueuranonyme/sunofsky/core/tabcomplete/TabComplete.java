package net.joueuranonyme.sunofsky.core.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TabComplete {
	public static void registerCompleters(JavaPlugin plugin) {
		plugin.getCommand("rename").setTabCompleter(new RenameCommand_Completer());
	}
}
