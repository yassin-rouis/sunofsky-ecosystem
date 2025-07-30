package net.joueuranonyme.sunofsky.core.placeholders;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.joueuranonyme.sunofsky.core.schedules.BossBarSchedule;

public class BossbarExpansion extends PlaceholderExpansion {
	@SuppressWarnings("unused")
	private final JavaPlugin plugin;
    
    public BossbarExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }
	
	
	@Override
    public String getAuthor() {
        return "SunOfSky (JoueurAnonyme)";
    }
    
    @Override
    public String getIdentifier() {
        return "sos.bossbar";
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }
    
    @Override
    public String onPlaceholderRequest(Player player, String params) {
    	return (BossBarSchedule.isPlayerEnabled(player) ? "§aActivée" : "§cDésactivée");
    }


	public static boolean init() {
		return true;
	}
}
