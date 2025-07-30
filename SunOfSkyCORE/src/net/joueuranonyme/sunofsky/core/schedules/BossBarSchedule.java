package net.joueuranonyme.sunofsky.core.schedules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;

public class BossBarSchedule {
	private static FileConfiguration config;
	private static FileConfiguration dataConfig;
	
	private static BukkitRunnable clock;
	private static BossBar bbar;
	private static int timer = 0;
	private static int maxTimer = 0;
	private static boolean reversed = false;
	private static int message = -1;
	
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
	
	public static Boolean disablePlayer(Player p) {
		if(isPlayerDisabled(p)) {
			return true;
		} else {
			List<String> l = dataConfig.getStringList("disabled_players");
			l.add(p.getName());
			dataConfig.set("disabled_players", l);
			if(bbar != null) {
				bbar.removePlayer(p);
			}
			YmlManager.write("plugins/SOS_Core/data/boss_bar.yml", dataConfig);
			return false;
		}
	}
	
	public static Boolean enablePlayer(Player p) {
		if(isPlayerEnabled(p)) {
			return true;
		} else {
			List<String> l = dataConfig.getStringList("disabled_players");
			l.remove(p.getName());
			dataConfig.set("disabled_players", l);
			if(bbar != null) {
				bbar.addPlayer(p);
			}
			YmlManager.write("plugins/SOS_Core/data/boss_bar.yml", dataConfig);
			return false;
		}
	}
	
	public static Boolean isPlayerDisabled(Player p) {
		if(dataConfig.getStringList("disabled_players").contains(p.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean isPlayerEnabled(Player p) {
		return !isPlayerDisabled(p);
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
	
	public static void schedule() {
		if(clock != null) {
			clock.cancel();	
		}
		
		if(bbar != null) {
			bbar.removeAll();
		}

		if(!config.isList("messages")) return;
		if(config.getStringList("messages").size() == 0) return;
		
		bbar = Bukkit.createBossBar(config.getStringList("messages").get(0), getColor(config.getInt("color")), getStyle(config.getInt("style")));
		
		clock = new BukkitRunnable() {
			public void run() {
				if(timer <= 0) {
					timer = config.getInt("delay") * 20;
					maxTimer = config.getInt("delay") * 20;
					
					if(!reversed && config.getBoolean("reverse")) {
						reversed = true;
						return;
					}
					
					reversed = false;
				
					if(message >= config.getStringList("messages").size()-1) {
						message = 0;
					} else {
						message += 1;
					}
					
					bbar.setTitle(config.getStringList("messages").get(message));
					bbar.setProgress(0.0);
					
					for(Player p: Bukkit.getOnlinePlayers()) {
						if(isPlayerEnabled(p)) bbar.addPlayer(p);
					}
				} else {
					timer = timer - 1;

					if(reversed && config.getBoolean("reverse")) {
						bbar.setProgress((((double) timer) / maxTimer));
					} else {
						bbar.setProgress(1.0 - (((double) timer) / maxTimer));
					}
				}
			}
		};
		
		clock.runTaskTimer(Main.getInstance(), 1L, 1L);
		
		return;
	}
	
	public static void unschedule(){
		if(bbar != null) {
			bbar.removeAll();
		}
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/boss_bar.yml", new String[] {
					"delay", "messages", "style", "color", "reverse"
			});
			
			dataConfig = YmlManager.readAndCheck("plugins/SOS_Core/data/boss_bar.yml", new String[] {
				"disabled_players"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
