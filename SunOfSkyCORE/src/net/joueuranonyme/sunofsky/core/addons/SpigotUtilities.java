/*
 * SunOfSky - JoueurAnonyme 2022 Ⓒ Copyright
 * All Rights Reserved
 *  
 */


package net.joueuranonyme.sunofsky.core.addons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpigotUtilities {	
	public static boolean checkPlayerOnline(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().toLowerCase().contains(s.toLowerCase()))
				return true; 
		} 
		return false;
	}
}
