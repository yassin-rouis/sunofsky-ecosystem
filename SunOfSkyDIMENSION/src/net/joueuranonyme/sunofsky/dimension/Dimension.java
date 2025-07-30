package net.joueuranonyme.sunofsky.dimension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.joueuranonyme.sunofsky.dimension.addons.Fixer;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;

public class Dimension {
	private static FileConfiguration config;
	
	public static FileConfiguration getConfig() {
		return config;
	}

	public static List<Entity> customEntities = new ArrayList<Entity>();
	public static Map<Player, Location> locations = new HashMap<Player, Location>();
	
	private static BukkitRunnable task;
	private static boolean openedDimension = false;
	private static int time = 0;
	private static int maxTime = 0;

	public static boolean isOpened() {
		if(time > 0 && openedDimension) return true;
		return false;
	}
	
	public static int getTime() {
		return time;
	}
	
	public static double getPercentTime() {
		return ((double) time) / ((double) maxTime);
	}
	
	public static void setTime(int newTime) {
		setTime(newTime, true);
	}
	
	public static void addTime(int newTime) {
		setTime(newTime, false);
	}
	
	private static void setTime(int newTime, boolean overwrite) {
		if(overwrite) {
			time = newTime;
			maxTime = newTime;
		} else {
			time = time + newTime;
			
			if(time > maxTime) {
				maxTime = time;
			}
		}
	}
	
	public static void openDimension() {
		openDimension(null);
	}
	
	public static void openDimension(CommandSender sender) {
		
		openedDimension = true;
		
		if(time <= 0) {
			setTime(config.getInt("duration"), true);
		}
		
		int s = time%60;
		int m = ((time-s)/60)%60;
		int h = (time-s-(m*60))/60;
		
		String timer = ( h > 0 ? Integer.toString(h) + "h" : "" ) + ( m > 0 ? Integer.toString(m) + "m" : "" ) + ( s > 0 ? Integer.toString(s) + "s" : "" );
		
		for(Player p: Bukkit.getOnlinePlayers()) {
			if(sender == null || sender.getName().equals("CONSOLE")) {
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("dimensionOpened").replaceAll("\\{TIME\\}", timer));
			} else {
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("dimensionOpenedByPlayer").replaceAll("\\{TIME\\}", timer).replaceAll("\\{PLAYER\\}", sender.getName()));
			}
		}
			
		TimerBossBar.show();
		
		if(sender == null || sender.getName().equals("CONSOLE")) {
			Main.printMc(Main.getPrefix() + "§aEvent lancé par une commande/la console/le serveur");
		} else {
			Main.printMc(Main.getPrefix() + String.format("§aEvent lancé par §6%s§a", sender.getName()));
		}
		
		task = new BukkitRunnable(){
			@Override
			public void run() {
				if(time <= 0 && openedDimension) {
					openedDimension = false;
					time = 0;
					maxTime = 0;
					
					Main.printMc("§aEvent terminé automatiquement");
					
					for(Player p: Bukkit.getOnlinePlayers()) {
						p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("dimensionClosed"));
					}

					Dimension.kickPlayers();
					
					TimerBossBar.hide();
					cancel();
					
					task = null;
					
				} else if (time > 0 && !openedDimension) {
					openedDimension = false;
					time = 0;
					maxTime = 0;
					
					for(Player p: Bukkit.getOnlinePlayers()) {
						p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("dimensionClosedByAdmin"));
					}
					
					Main.printMc("§aEvent terminé manuellement");
					
					Dimension.kickPlayers();

					TimerBossBar.hide();
					cancel();
					
					task = null;
					
				} else if(time > 0 && openedDimension) {
					TimerBossBar.update();
					time --;
				}
			}
		};
		
		task.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
	
	public static void closeDimension() {
		openedDimension = false;
	}
	
	public static void kickPlayers() {
		try {
			for(Player p : locations.keySet()) {
				if(!p.isOnline()) continue;
				
				if(p.getWorld() == Bukkit.getWorld(config.getString("world"))){
					p.teleport(locations.get(p));
					p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("teleportedToLastLocation"));
				}
				locations.remove(p);
			}
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			for(Player p : Bukkit.getWorld(config.getString("world")).getPlayers()) {
				Bukkit.dispatchCommand(console, config.getString("forceKickCommand").replaceAll("\\{PLAYER\\}", p.getName()));
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("teleportedToForcedLocation"));
			}
		} catch(Exception e) {
			
		}
	}
	
	public static void addPlayerLocation(Player p) {
		locations.put(p, p.getLocation());
	}
	

	public static boolean init() {
		TimerBossBar.init();
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/dimension.yml", new String[] {
				"duration", "dimensionOpened", "dimensionClosed", "dimensionClosedByAdmin", "timeAdded", "world", "prefix", "teleportedToLastLocation", "teleportedToForcedLocation", "forceKickCommand"
			});
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static void unload() {
		for(Entity e: customEntities) {
			e.remove();
		}
	}
}
