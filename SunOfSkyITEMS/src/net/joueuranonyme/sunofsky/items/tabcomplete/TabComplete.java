package net.joueuranonyme.sunofsky.items.tabcomplete;

import org.bukkit.plugin.java.JavaPlugin;

public class TabComplete {
	public static void registerCompleters(JavaPlugin plugin) {
		plugin.getCommand("sosgive").setTabCompleter(new GiveCommand_Completer());
	}
}
