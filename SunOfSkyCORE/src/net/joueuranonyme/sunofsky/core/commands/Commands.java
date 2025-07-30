package net.joueuranonyme.sunofsky.core.commands;

import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.ConsoleColors;

public class Commands {	
	public static void registerCommands(JavaPlugin plugin) {

		//plugin.getCommand("test").setExecutor(new TestCommand());
		plugin.getCommand("bvn").setExecutor(new BienvenueCommand());
		plugin.getCommand("vote").setExecutor(new VoteCommand());
		plugin.getCommand("site").setExecutor(new SiteCommand());
		plugin.getCommand("furnace").setExecutor(new FurnaceCommand());
	    //plugin.getCommand("fly").setExecutor(new FlyCommand());
	    plugin.getCommand("flytimer").setExecutor(new FlyCommand());
	    plugin.getCommand("peche").setExecutor(new PecheCommand());
	    plugin.getCommand("pub").setExecutor(new PubCommand());
	    plugin.getCommand("journalier").setExecutor(new JournalierCommand());
	    plugin.getCommand("bbar").setExecutor(new BossBarCommand());
	    plugin.getCommand("rename").setExecutor(new RenameCommand());
	}

	public static void init() {
		
		// ======
		
		if(!BossBarCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [BossBarCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!JournalierCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [JournalierCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!BienvenueCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [BienvenueCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!VoteCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [VoteCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!SiteCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [SiteCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!FurnaceCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [FurnaceCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!FlyCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [FlyCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!PecheCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [PecheCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
		
		if(!PubCommand.init()) {
			Main.print(
				ConsoleColors.RED_BACKGROUND_BRIGHT + 
				ConsoleColors.WHITE + 
				"[ERROR] Impossible de charger la commande [PubCommand], elle est ignorée, et risque de ne pas fonctionner sur le serveur. " + 
				ConsoleColors.RESET
			);
		}
	}
}
