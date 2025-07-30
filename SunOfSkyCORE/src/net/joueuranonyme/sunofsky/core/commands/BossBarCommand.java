package net.joueuranonyme.sunofsky.core.commands;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.Fixer;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.schedules.BossBarSchedule;

public class BossBarCommand implements CommandExecutor {
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.bossbar.reload")) {
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
			if(BossBarSchedule.isPlayerEnabled((Player) sender)) {
				BossBarSchedule.disablePlayer((Player) sender);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("disabled"));
			} else {
				BossBarSchedule.enablePlayer((Player) sender);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("enabled"));
			}
			return true;
		}
		
		if(args.length == 1) {
			if((args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) && BossBarSchedule.isPlayerDisabled((Player) sender)) {
				BossBarSchedule.enablePlayer((Player) sender);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("enabled"));
			} else if((args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) && BossBarSchedule.isPlayerEnabled((Player) sender)) {
				BossBarSchedule.disablePlayer((Player) sender);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("disabled"));
			} else if((args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) && BossBarSchedule.isPlayerEnabled((Player) sender)) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("alreadyEnabled"));
			} else if((args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) && BossBarSchedule.isPlayerDisabled((Player) sender)) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("disabled"));
			/*} else if(sender.hasPermission("soscore.bossbar.other")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/bossbar <on|enabled | off|disable> [Joueur]");
			*/} else {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§c/bossbar <on|enabled | off|disable>");
			}
			return true;
		}
		
		return false;		
	}
	
	public boolean checkOnline(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toLowerCase().contains(s.toLowerCase()))
				return true; 
		} 
		return false;
	}
	
	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/boss_bar.yml", new String[] {
					"prefix", "disabled", "enabled", "alreadyDisabled", "alreadyEnabled"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
