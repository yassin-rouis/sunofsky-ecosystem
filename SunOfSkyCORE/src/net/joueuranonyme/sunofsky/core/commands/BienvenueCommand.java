package net.joueuranonyme.sunofsky.core.commands;

import java.util.Date;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.events.FirstJoinEvent;

public class BienvenueCommand implements CommandExecutor {
	
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.bvn.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(FirstJoinEvent.lastJoinedPlayer == null || FirstJoinEvent.lastJoinedPlayerDate == null) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noNewPlayerMessage"));
		} else if(FirstJoinEvent.lastJoinedPlayerDate.getTime() + 300 < (new Date()).getTime()) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noNewPlayerMessage"));
		} else {			
			List<?> loadedMessages = YmlManager.read("plugins/SOS_Core/bienvenue.yml").getList("messages");
			
			int randomInt = (int)(Math.random()*(loadedMessages.size()));
			((Player) sender).chat((String) loadedMessages.get(randomInt));
			
		}
		
		return true;
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/bienvenue.yml", new String[] {
				"prefix", "messages", "noNewPlayerMessage"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
