package net.yassinrouis.sunofsky.dimension;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}
	
    @Override
    public void onEnable() {	
    	instance = this;

    	
    	
    }

    @Override
    public void onDisable() {
    	
    }
    
    
}
