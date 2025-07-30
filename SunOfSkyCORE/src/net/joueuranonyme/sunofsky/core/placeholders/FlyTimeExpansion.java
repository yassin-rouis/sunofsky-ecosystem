package net.joueuranonyme.sunofsky.core.placeholders;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.commands.FlyCommand;

public class FlyTimeExpansion extends PlaceholderExpansion {
	private static FileConfiguration config;
	
	@SuppressWarnings("unused")
	private final JavaPlugin plugin;
    
    public FlyTimeExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }
	
	
	@Override
    public String getAuthor() {
        return "SunOfSky (JoueurAnonyme)";
    }
    
    @Override
    public String getIdentifier() {
        return "sos.flytimer";
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }
    
    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if(player.getAllowFlight()) {
        	if(FlyCommand.hasPlayerTime(player)) {
        		int fullSeconds = FlyCommand.getPlayerTime(player);
        		int s = fullSeconds % 60;
        		int m = ((fullSeconds-s) / 60) % 60;
        		int h = (((fullSeconds - s) - (m*60)) / 60) / 60;
        		
        		String timer = config.getString("placeholder.timer.left");
        		
        		if(h > 0) {
        			timer += config.getString("placeholder.timer.hours").replaceAll("\\{h\\}", Integer.toString(h));
        			timer += config.getString("placeholder.timer.minutes").replaceAll("\\{m\\}", Integer.toString(m));
        			timer += config.getString("placeholder.timer.seconds").replaceAll("\\{s\\}", Integer.toString(s));
        		} else if (m > 0) {
        			timer += config.getString("placeholder.timer.minutes").replaceAll("\\{m\\}", Integer.toString(m));
        			timer += config.getString("placeholder.timer.seconds").replaceAll("\\{s\\}", Integer.toString(s));
        		} else if (s > 0) {
        			timer += config.getString("placeholder.timer.seconds").replaceAll("\\{s\\}", Integer.toString(s));
        		} else {
        			timer = config.getString("placeholder.disabled");
        		}
        		
        		return timer;
        	} else {
        		return config.getString("placeholder.enabled");
        	}
        } else {
        	return config.getString("placeholder.disabled");
        }
    }

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/fly.yml", new String[] {
				"placeholder", "placeholder.enabled", "placeholder.disabled", "placeholder.timer.seconds",
				"placeholder.timer.minutes", "placeholder.timer.hours", "placeholder.timer.left"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
