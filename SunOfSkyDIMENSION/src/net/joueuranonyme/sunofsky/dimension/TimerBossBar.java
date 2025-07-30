package net.joueuranonyme.sunofsky.dimension;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.joueuranonyme.sunofsky.dimension.addons.Fixer;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;

public class TimerBossBar {
	private static boolean shown = false;
	private static BossBar bbar;
	private static FileConfiguration config;
	
	private static List<Player> disabledBossBarPlayerList = new ArrayList<Player>();
	
	public static boolean isPlayerEnabled(Player p) {
		return !disabledBossBarPlayerList.contains(p);
	}
	
	public static void enablePlayer(Player p) {
		if(isPlayerEnabled(p)) return;
		disabledBossBarPlayerList.remove(p);
		
		if(bbar != null) {
			if(shown) bbar.addPlayer(p);
		}
	}
	
	public static void disablePlayer(Player p) {
		if(!isPlayerEnabled(p)) return;
		disabledBossBarPlayerList.add(p);
		
		if(bbar != null) {
			if(shown) bbar.removePlayer(p);
		}
	}
	
	
	private static BarColor getColor(int i) {
		switch(i) {
			case 1 -> {return BarColor.WHITE;}
			case 2 -> {return BarColor.RED;}
			case 3 -> {return BarColor.YELLOW;}
			case 4 -> {return BarColor.GREEN;}
			case 5 -> {return BarColor.BLUE;}
			case 6 -> {return BarColor.PURPLE;}
			case 7 -> {return BarColor.PINK;}
			default -> {return BarColor.RED;}
		}
	}
	
	private static BarStyle getStyle(int i) {
		switch(i) {
			case 1 -> {return BarStyle.SOLID;}
			case 2 -> {return BarStyle.SEGMENTED_6;}
			case 3 -> {return BarStyle.SEGMENTED_10;}
			case 4 -> {return BarStyle.SEGMENTED_12;}
			case 5 -> {return BarStyle.SEGMENTED_20;}
			default -> {return BarStyle.SOLID;}
		}
	}
	
	public static String getStringTime(int t) {
		int s = t%60;
		int m = ((t-s)/60)%60;
		int h = (t-s-(m*60))/3600;
		
		if(t <= 0) {
			return null;
		} else {
			return ( h > 0 ? Integer.toString(h) + "h" : "" ) + ( m > 0 ? Integer.toString(m) + "m" : "" ) + ( s > 0 ? Integer.toString(s) + "s" : "" );
		}
	}
	
	
	public static void show() {
		bbar = Bukkit.createBossBar(config.getString("message").replaceAll("\\{TIME\\}", getStringTime(Dimension.getConfig().getInt("duration"))), getColor(config.getInt("color")), getStyle(config.getInt("style")));
		
		shown = true;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(isPlayerEnabled(p)) {
				bbar.addPlayer(p);
			}
		}
	}
	
	public static void update() {
		bbar.setTitle(config.getString("message").replaceAll("\\{TIME\\}", getStringTime(Dimension.getTime())));
		bbar.setProgress(Dimension.getPercentTime());
	}
	
	public static void hide() {
		if(bbar == null) return;
		
		bbar.removeAll();
		shown = false;
	}
	
	
	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Dimension/boss_bar.yml", new String[] {
				"message", "style", "color"
			});
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	

	
	public static void unload(){
		if(bbar != null) {
			bbar.removeAll();
		}
		
		bbar = null;
	}
}
