package net.joueuranonyme.sunofsky.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.evenements.Peche;

public class PecheCommand implements CommandExecutor {
	
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {		
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.site.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/peche <run|stop> ...");
			return true;
		}

		if (!sender.hasPermission("soscore.peche")) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
			return true;
		}
		
		if(args[0].equalsIgnoreCase("run")) {
			int time = 0;
			
			if(args.length == 2) {
				if (args[1].endsWith("s")) {
					time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
				} else if (args[1].endsWith("m")) {
					time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60;
				} else if (args[1].endsWith("h")) {
					time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60 * 60;
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/peche run [<time + s|m|h>]");
					return true;
				}
			} else if(args.length == 1){
				time = config.getInt("duration");
			} else {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/peche run [<time + s|m|h>]");
				return true;
			}
			
			if(!Peche.enabled()) {
				Peche.runEvent(time);
			} else {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("alreadyRunned"));
			}
		} else if (args[0].equalsIgnoreCase("stop")) {
			if(!Peche.stopEvent()) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("alreadyCancelled"));
			}
		}
		return true;
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/event_peche.yml", new String[] {
					"prefix", "duration", "alreadyRunned", "alreadyCancelled", "noPermission"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
