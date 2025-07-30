package net.joueuranonyme.sunofsky.core.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.commands.FlyCommand;

public class Fly_PlayerJoinEvent implements Listener {
	private static FileConfiguration config;

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		if(!config.getConfigurationSection("players").getKeys(false).contains(p.getUniqueId().toString())) return;
		
		int time = config.getInt("players."+p.getUniqueId().toString());
		
		if(time == 0) return;
		
		FlyCommand.setPlayerTime(p, time);
		FlyCommand.runTimer(p);
		p.setAllowFlight(true);
		
		config.getConfigurationSection("players").set(p.getUniqueId().toString(), 0);
		
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
