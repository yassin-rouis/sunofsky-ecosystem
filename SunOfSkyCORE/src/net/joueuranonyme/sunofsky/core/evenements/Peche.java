package net.joueuranonyme.sunofsky.core.evenements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.Fixer;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;

public class Peche implements Listener {
	private static FileConfiguration config = YmlManager.readAndCheck("plugins/SOS_Core/event_peche.yml", new String[] {
			"prizes", "prefix", "pos1", "pos1.X", "pos1.Y", "pos1.Z", "pos2", "pos2.X", "pos2.Y", "pos2.Z", "world",
			"duration", "minPlayers", "noEnoughPlayers", "eventStarted", "eventEnded", "eventCancelled",
			"broadcastWinnedPrize", "winnedPrize"
	});
	
	private static boolean isInitialized = false;
	private static boolean enabled = false;
	public static int timeLeft = 0;
	
	private static BukkitRunnable task;
	
	public static boolean init() {
		return isInitialized;
	}
	
	public static void init(boolean i) {
		isInitialized = i;
	}
	
	public static boolean enabled() {
		return enabled;
	}
	
	public static void enabled(boolean i) {
		enabled = i;
	}
	
	public static int timeLeft() {
		return timeLeft * (enabled ? 1 : 0);
	}
	
	private static void setTimeLeft(int t) {
		timeLeft = t;
	}
	
	public static void onPlayerFishEvent(PlayerFishEvent event) {
		
		if(!enabled || timeLeft <= 0) return;
		if(!event.getHook().getWorld().getName().equals(config.getString("world"))) return;
		
		if(event.getCaught() == null) return;
		if(event.getCaught().getTicksLived() > 0) return;
	    
	    BoundingBox bb = new BoundingBox(
			config.getDouble("pos1.X"),
			config.getDouble("pos1.Y"), 
			config.getDouble("pos1.Z"), 
			config.getDouble("pos2.X"), 
			config.getDouble("pos2.Y"), 
			config.getDouble("pos2.Z")
		);
	    Location loc = event.getHook().getLocation();
		if(!bb.contains(loc.getX(), loc.getY(), loc.getZ())) return;
		
		event.getHook().remove();
	    event.setCancelled(true);
		
		
		List<String> prizes = new ArrayList<String>();
		
		for(String prizeId : config.getConfigurationSection("prizes").getKeys(false)) {
			if(!(config.getConfigurationSection("prizes." + prizeId).contains("chance") &&config.getConfigurationSection("prizes." + prizeId).contains("announce") && config.getConfigurationSection("prizes."+prizeId).contains("command") && config.getConfigurationSection("prizes."+prizeId).contains("name"))){
				return;
			}
			
			for(int i = 0; i < config.getInt("prizes." + prizeId + ".chance"); ++i) {
			    prizes.add(prizeId);
			}
		}
		
		int randomId = ThreadLocalRandom.current().nextInt(0, prizes.size());

		
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(console, config.getString("prizes." + prizes.get(randomId) + ".command").replaceAll("\\{PLAYER\\}", Fixer.fixString(event.getPlayer().getName())));
		event.getHook().getWorld().spawnParticle(Particle.BUBBLE_POP, event.getHook().getLocation(), 100, 0.2, 0, 0.2);

		event.getPlayer().sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("winnedPrize").replaceAll("\\{PRIZE\\}", Fixer.fixString(config.getString("prizes." + prizes.get(randomId) + ".name"))));
		if(config.getBoolean("prizes." + prizes.get(randomId) + ".announce")){
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("broadcastWinnedPrize").replaceAll("\\{PRIZE\\}", Fixer.fixString(config.getString("prizes." + prizes.get(randomId) + ".name"))).replaceAll("\\{PLAYER\\}", Fixer.fixString(event.getPlayer().getName())));
			}
		}
	}
	public static boolean runEvent(int time) {
		if(task == null || task.isCancelled()) {
			if(Bukkit.getOnlinePlayers().size() < config.getInt("minPlayers")) {
				Bukkit.getServer().getConsoleSender().sendMessage(Main.getPrefix("EventPeche") + "§cL'event pêche a été annulé pour manque de joueurs.");
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noEnoughPlayers"));
				}
				return false;
			}
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("eventStarted"));
			}
			
			enabled = true;
			timeLeft = time;
			
			task = new BukkitRunnable() {
				public void run() {
					if(timeLeft() > 0) {
						setTimeLeft(timeLeft() - 1);
						return;
					}
				
					setTimeLeft(0);
					enabled(false);
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("eventEnded"));
					}
					
					this.cancel();
				}
			};
			
			task.runTaskTimer(Main.getInstance(), 20L, 20L);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean stopEvent() {
		if(task.isCancelled()) {
			return false;
		} else {
			task.cancel();
			enabled = false;
			timeLeft = 0;
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("eventCancelled"));
			}
			return true;
		}
	}
}
