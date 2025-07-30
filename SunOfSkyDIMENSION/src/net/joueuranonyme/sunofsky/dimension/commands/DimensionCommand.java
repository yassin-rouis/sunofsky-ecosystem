package net.joueuranonyme.sunofsky.dimension.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.joueuranonyme.sunofsky.dimension.Main;
import net.joueuranonyme.sunofsky.dimension.TimerBossBar;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;
import net.joueuranonyme.sunofsky.dimension.events.EntitySpawn;
import net.joueuranonyme.sunofsky.dimension.Dimension;
public class DimensionCommand implements CommandExecutor {
	private static FileConfiguration config;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("sos.dimension.reload")) {
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
			if(!Dimension.isOpened()) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("closed"));
				return true;
			} else {
				
				if(((Player) sender).getWorld() != Bukkit.getWorld(config.getString("world"))) {
					Dimension.addPlayerLocation(((Player) sender));
				}
				
				Location dimensionSpawn = Bukkit.getWorld(config.getString("world")).getSpawnLocation();
				((Entity) sender).teleport(dimensionSpawn);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("teleport"));
				return true;
			}
		}
		
		
		if(args.length > 0) {			
			if(args[0].equalsIgnoreCase("set")) {
				if(sender.hasPermission("sos.dimension.set")) {
					int time;
					if(args.length == 2) {
						if (args[1].endsWith("s")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
						} else if (args[1].endsWith("m")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60;
						} else if (args[1].endsWith("h")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60 * 60;
						} else {
							sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/dimension set [<time + s|m|h>]");
							return true;
						}
					} else if(args.length == 1){
						time = config.getInt("duration");
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/dimension set [<time + s|m|h>]");
						return true;
					}
					
					Dimension.setTime(time);
					
					if(!Dimension.isOpened()) {
						Dimension.openDimension(sender);
					} else {
						TimerBossBar.update();
					}
					
					return true;
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				}
			}
			
			
			if(args[0].equalsIgnoreCase("add")) {
				if(sender.hasPermission("sos.dimension.add")) {
					int time;
					if(args.length == 2) {
						if (args[1].endsWith("s")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
						} else if (args[1].endsWith("m")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60;
						} else if (args[1].endsWith("h")) {
							time = Integer.parseInt(args[1].substring(0, args[1].length() - 1)) * 60 * 60;
						} else {
							sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/dimension add [<time + s|m|h>]");
							return true;
						}
						
					} else if(args.length == 1){
						time = config.getInt("duration");
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/dimension add [<time + s|m|h>]");
						return true;
					}
					
					Dimension.addTime(time);
					
					if(!Dimension.isOpened()) {
						Dimension.openDimension(sender);
					} else {
						if(sender.getName().equals("CONSOLE")) {
							sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("timeAdded").replaceAll("\\{TIME_ADDED\\}", TimerBossBar.getStringTime(time)).replaceAll("\\{TIME\\}", TimerBossBar.getStringTime(Dimension.getTime())));
						} else {
							sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("timeAddedByPlayer").replaceAll("\\{TIME_ADDED\\}", TimerBossBar.getStringTime(time)).replaceAll("\\{TIME\\}", TimerBossBar.getStringTime(Dimension.getTime())).replaceAll("\\{PLAYER\\}", sender.getName()));
						}
						TimerBossBar.update();
					}
					
					return true;
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				}
			}
			
			
			if(args[0].equalsIgnoreCase("stop")) {
				if(sender.hasPermission("sos.dimension.stop")) {
					if(Dimension.isOpened()) {
						Dimension.closeDimension();
					}
					
					return true;
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				}
			}
		}
		
		sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/dimension <add|set|clear> ...");
		return true;	
	}

	public static boolean init() {
		try {
			if(!(Dimension.init() && TimerBossBar.init() && EntitySpawn.init())) {
				return false;
			}
			
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/dimension.yml", new String[] {
				"prefix", "closed", "teleport", "world", "noPermission"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}