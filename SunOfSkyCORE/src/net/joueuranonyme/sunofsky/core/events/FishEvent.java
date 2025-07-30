package net.joueuranonyme.sunofsky.core.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import net.joueuranonyme.sunofsky.core.evenements.Peche;

public class FishEvent implements Listener {
	
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
	
	@EventHandler
	public void onPlayerFishEvent(PlayerFishEvent event) {
		//event.getPlayer().sendMessage((event.getHook().isInWater()?"oui":"non"));
		Peche.onPlayerFishEvent(event);
	}


	public static boolean init() {
		return true;
	}
}
