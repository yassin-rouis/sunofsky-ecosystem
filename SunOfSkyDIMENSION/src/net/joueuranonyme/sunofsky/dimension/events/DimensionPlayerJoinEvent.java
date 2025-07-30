package net.joueuranonyme.sunofsky.dimension.events;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.joueuranonyme.sunofsky.dimension.Dimension;
import net.joueuranonyme.sunofsky.dimension.Main;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;

public class DimensionPlayerJoinEvent implements Listener {
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
	private static FileConfiguration config;
	
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if(event.getPlayer().getWorld().getName().equals(config.getString("world"))) {
			if(Dimension.locations.containsKey(event.getPlayer())) {
				event.getPlayer().teleport(Dimension.locations.get(event.getPlayer()));
				event.getPlayer().sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("teleportedToLastLocation"));
			} else {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				Bukkit.dispatchCommand(console, config.getString("forceKickCommand").replaceAll("\\{PLAYER\\}", event.getPlayer().getName()));
				event.getPlayer().sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("teleportedToForcedLocation"));
			}
		}
	}


	

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/dimension.yml", new String[] {
				"world", "teleportedToLastLocation", "teleportedToForcedLocation", "forceKickCommand"
			});
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
