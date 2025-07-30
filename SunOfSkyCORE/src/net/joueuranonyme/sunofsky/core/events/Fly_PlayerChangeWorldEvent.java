package net.joueuranonyme.sunofsky.core.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerFishEvent;

import net.joueuranonyme.sunofsky.core.commands.FlyCommand;
import net.joueuranonyme.sunofsky.core.evenements.Peche;

public class Fly_PlayerChangeWorldEvent implements Listener {
	
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
	
	@EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
		if(FlyCommand.hasPlayerTime(event.getPlayer())) {
			event.getPlayer().setAllowFlight(true);
		}
	}


	public static boolean init() {
		return true;
	}
}
