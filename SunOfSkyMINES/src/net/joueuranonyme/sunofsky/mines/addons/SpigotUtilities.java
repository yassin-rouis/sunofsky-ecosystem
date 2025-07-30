package net.joueuranonyme.sunofsky.mines.addons;

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
