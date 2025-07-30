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

public class PubCommand implements CommandExecutor {
	
	private static FileConfiguration config;
	
	public static boolean hasPlayerTime(Player p) {
		return playersTime.containsKey(p);
	}
	
	public static Integer getPlayerTime(Player p) {
		return playersTime.get(p);
	}
	
	public static Integer removePlayerTime(Player p) {
		return playersTime.remove(p);
	}	

	private static Map<Player, Integer> playersTime = new HashMap<Player, Integer>();
	private static Map<Player, BukkitRunnable> playersRunnable = new HashMap<Player, BukkitRunnable>();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.pub.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(args.length > 0) {			
			if(args.length == 2) {
				if(args[0].equals("clear") && sender.hasPermission("soscore.pub.cancel_others")) {
					Player p = Bukkit.getPlayerExact(args[1]);
					if(p == null) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("playerNotFound"));
						return true;
					}
					
					if(!playersTime.containsKey(p) || !playersRunnable.containsKey(p)) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPlayerDelay"));
						return true;
					} else if (playersRunnable.get(p).isCancelled()) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPlayerDelay"));
						return true;
					}
					
					playersTime.remove(p);
					playersRunnable.get(p).cancel();
					playersRunnable.remove(p);

					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("delayCancelled").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())));
					p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("delayCancelledTarget"));
					
					return true;
				} else if(args[0].equals("clear")) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPermission"));
					return true;
				}
			}
			
			if(((!playersTime.containsKey(sender) || playersTime.get(sender) <= 0) && sender.hasPermission("soscore.pub.use")) || sender.hasPermission("soscore.pub.unlimited_use")) {
				String pub = config.getString("message").replaceAll("\\{PUB\\}", Fixer.fixString(String.join(" ", args)).replaceAll("&", "§").replaceAll("§§", "&"));
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(pub);
				}
				
				Player player = (Player) sender;
				
				playersTime.put(player, config.getInt("delay"));
				
				BukkitRunnable task = new BukkitRunnable() {
					public void run() {
						Player p = player;
						int fullseconds = playersTime.get(p);
						
						if(fullseconds > 0) {
							playersTime.put(p, fullseconds-1);
						} else {
							playersTime.remove(p);
							playersRunnable.remove(p);
							this.cancel();
						}
					}
				};
				
				task.runTaskTimer(Main.getInstance(), 20L, 20L);
				playersRunnable.put((Player) sender, task);
			} else {
				int fullSeconds = playersTime.get(sender);
        		int s = fullSeconds % 60;
        		int m = ((fullSeconds-s) / 60) % 60;
        		int h = (((fullSeconds - s) - (m*60)) / 60) / 60;
        		
        		String timer = "";
        		
        		if(h > 0) {
        			timer = String.format("%sh %sm %ss", h, m, s);
        		} else if (m > 0) {
        			timer = String.format("%sm %ss", m, s);
        		} else if (s > 0) {
        			timer = String.format("%ss", s);
        		}
        		
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("waitForDelay").replaceAll("\\{TIME\\}", timer));
			}
		
			return true;
		} else {
			return false;
		}
		
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
			config = YmlManager.readAndCheck("plugins/SOS_Core/pub.yml", new String[] {
					"prefix", "message", "delay", "playerNotFound", "noPlayerDelay", "delayCancelled",
					"delayCancelledTarget", "noPermission", "waitForDelay"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
