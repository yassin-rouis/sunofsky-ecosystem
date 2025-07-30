package net.joueuranonyme.sunofsky.core.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.commands.FlyCommand;

public class Fly_PlayerQuitEvent implements Listener {
	private static FileConfiguration config;

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(!FlyCommand.hasPlayerTime(p)) return;
		
		config.getConfigurationSection("players").set(p.getUniqueId().toString(), FlyCommand.getPlayerTime(p));
		
		YmlManager.write("plugins/SOS_Core/data/fly.yml", config);
	}
	
	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/data/fly.yml", new String[] {
				"players"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
