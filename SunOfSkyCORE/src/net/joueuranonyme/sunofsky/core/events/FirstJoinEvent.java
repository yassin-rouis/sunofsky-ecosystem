package net.joueuranonyme.sunofsky.core.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinEvent implements Listener {
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
	  if(!event.getPlayer().hasPlayedBefore()) {
		 lastJoinedPlayer = event.getPlayer();
		 lastJoinedPlayerDate = new Date(System.currentTimeMillis());
	  }
	}


	public static boolean init() {
		return true;
	}
}
