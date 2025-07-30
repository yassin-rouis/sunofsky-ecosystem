package net.joueuranonyme.sunofsky.core.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.Fixer;
import net.joueuranonyme.sunofsky.core.addons.SpigotUtilities;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FlyCommand implements CommandExecutor {
	private static FileConfiguration config;
	
	public static Set<Player> flying;
	private static Map<UUID, Integer> playersTime = new HashMap<UUID, Integer>();
	private static Map<UUID, BukkitRunnable> playersRunnable = new HashMap<UUID, BukkitRunnable>();
	
	public static void setPlayerTime(Player p, int t) {
		playersTime.put(p.getUniqueId(), t);
	}
	
	public static boolean hasPlayerTime(Player p) {
		return playersTime.containsKey(p.getUniqueId());
	}
	
	public static Integer getPlayerTime(Player p) {
		return playersTime.get(p.getUniqueId());
	}
	
	public static Integer removePlayerTime(Player p) {
		return playersTime.remove(p.getUniqueId());
	}
	
	public FlyCommand() {
		FlyCommand.flying = new HashSet<Player>();
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.fly.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (args.length == 0) {
				if (!sender.hasPermission("soscore.fly.toggle.self")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				if (!(sender instanceof Player)) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playersOnly"));
					return true;
				} 
				final Player p = (Player) sender;
				toggleFly(sender, p.getName());
			} else if (args.length == 1) {
				if (!sender.hasPermission("soscore.fly.toggle.other")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				if (SpigotUtilities.checkPlayerOnline(args[0])) {
					toggleFly(sender, args[0]);
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
					return true;
				} 
			}/* else if (args.length == 2) {
				if (!sender.hasPermission("soscore.fly.speed.self")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				if (!(sender instanceof Player)) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playersOnly"));
					return true;
				} 
				if (args[0].equalsIgnoreCase("speed")) {
					final Player p = (Player)sender;
					setSpeed(sender, p, args[1]);
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("syntaxError"));
					return true;
				} 
			} else if (args.length == 3) {
				if (!sender.hasPermission("soscore.fly.speed.other")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				if (checkOnline(args[0])) {
					if (args[0].equalsIgnoreCase("speed")) {
						if (checkOnline(args[1])) {
							final Player p = Bukkit.getPlayer(findPlayer(args[1]));
							setSpeed(sender, p, args[2]);
						} else {
							sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
							return true;
						} 
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("syntaxError"));
						return true;
					} 
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
				} 
			}*/
		} else if (cmd.getName().equalsIgnoreCase("flytimer")) { 
			if (args.length < 2) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "/flytimer <add|set|clear> <name> [<time + s|m|h> (ex. /flytimer notch 10m)]"); 
				return true;
			}
			
			final Player p;
			
			if(args[0].equalsIgnoreCase("clear")) {
				if (!sender.hasPermission("soscore.fly.timer")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				
				if (SpigotUtilities.checkPlayerOnline(args[1])) {
					p = Bukkit.getPlayer(args[1]);
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
					return true;
				}
				
				removePlayerTime(p);
				p.setAllowFlight(false);
				FlyCommand.flying.remove(p);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyCleared").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())));
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyClearedTarget"));
				
			} else {
				if (!sender.hasPermission("soscore.fly.timer")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				} 
				if (args.length != 3) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "/flytimer <add|set|clear> <name> [<time + s|m|h> (ex. /flytimer notch 10m)]"); 
					return true;
				}
				if (SpigotUtilities.checkPlayerOnline(args[1])) {
					p = Bukkit.getPlayer(args[1]);
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
					return true;
				} 
				int time = 0;
				if (args[2].endsWith("s")) {
					time = Integer.parseInt(args[2].substring(0, args[2].length() - 1));
				} else if (args[2].endsWith("m")) {
					time = Integer.parseInt(args[2].substring(0, args[2].length() - 1)) * 60;
				} else if (args[2].endsWith("h")) {
					time = Integer.parseInt(args[2].substring(0, args[2].length() - 1)) * 60 * 60;
				} else {
					p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("timeSpecification"));
					return true;
				}
				
				p.setAllowFlight(true);
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("allowTempFly").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName()).replaceAll("\\{TIME\\}", Fixer.fixString(args[2]))));
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("allowTempFlyTarget").replaceAll("\\{TIME\\}", Fixer.fixString(args[2])));
				FlyCommand.flying.add(p);
				
				if(!hasPlayerTime(p) || args[0].equalsIgnoreCase("set")) {
					setPlayerTime(p, time);
					
					if(playersRunnable.containsKey(p.getUniqueId())) {
						playersRunnable.get(p.getUniqueId()).cancel();
					}
					
					runTimer(p);
				} else {
					setPlayerTime(p, getPlayerTime(p)+time);
					//removePlayerTime(p);
				}
			}
			
		} 
		return true;
	}
	
	public static void runTimer(Player p) {
		BukkitRunnable task = new BukkitRunnable() {
			public void run() {
				Player player = p;
				
				if(!p.isFlying()) return;
				int fullseconds = getPlayerTime(player);
				int seconds = fullseconds % 60;
				int minutes = (fullseconds-seconds)/60;
				
				if(fullseconds > 0) {
					setPlayerTime(player, fullseconds-1);
					if(fullseconds > 10) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.format(config.getString("flyTimer").replace("{TIME}", "%s"), String.format("%sm %ss", minutes, seconds))));
					} else {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.format(config.getString("flyTimerWarning").replace("{TIME }", "%s"), String.format("%sm %ss", minutes, seconds))));
					}
				} else {
					removePlayerTime(player);
					player.setAllowFlight(false);
					FlyCommand.flying.remove(player);
					player.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyLimitEnded"));
					this.cancel();
				}
			}
		};
		
		task.runTaskTimer(Main.getInstance(), 20L, 20L);
		playersRunnable.put(p.getUniqueId(), task);
	}
	
	public void toggleFly(CommandSender sender, String name) {
		Player p = Bukkit.getPlayer(name);
		if (p == null) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerOffline"));
			return;
		} 
		if (FlyCommand.flying.contains(p)) {
			p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyToggledOFFTarget"));
			FlyCommand.flying.remove(p);
			p.setAllowFlight(false);
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyToggledOFF").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())));
		} else {
			p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyToggledONTarget"));
			FlyCommand.flying.add(p);
			p.setAllowFlight(true);
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flyToggledON").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())));
		} 
	}
	
	public void setSpeed(CommandSender sender, Player p, String speed) {
		int integer = Integer.parseInt(speed);
		if (integer > 99) {
			sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("numberUnder100"));
			return;
		} 
		float f = (float)(integer * 0.01D);
		p.setFlySpeed(f);
		sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flySpeed").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())).replaceAll("\\{SPEED\\}", speed));
		p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("flySpeedTarget").replaceAll("\\{SPEED\\}", speed));
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/fly.yml", new String[] {
				"prefix", "playersOnly", "playerNotFound", "playerOffline", "syntaxError", "timeSpecification", "noPermission", "numberUnder100", "flyLimitEnded", "allowTempFly", "allowTempFlyTarget", "flyToggledOFF", "flyToggledON", "flyToggledOFFTarget", "flyToggledONTarget", "flySpeed", "flySpeedTarget", "flyTimer", "flyTimerWarning", "flyCleared", "flyClearedTarget"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}