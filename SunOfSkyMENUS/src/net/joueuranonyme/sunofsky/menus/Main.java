package net.joueuranonyme.sunofsky.menus;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.menus.addons.YmlManager;
import net.joueuranonyme.sunofsky.menus.commands.Commands;
import net.joueuranonyme.sunofsky.menus.events.Events;
import net.joueuranonyme.sunofsky.menus.tabcomplete.TabComplete;
public class Main extends JavaPlugin {
    
	private static Main instance;
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	private static PlayerPoints playerPoints;
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return getPrefix("§7[§fSun§eOf§fSky §6MENUS§7] §r");
	}
	
	public static String getPrefix(String text) {
		return text+"§r";
	}
	
	public static void printMc(String ...str) {
		console.sendMessage(Main.getPrefix() + String.join(" ", str));
	}
	
	public static void print(String ...str) {
		System.out.println(Main.getPrefix() + "e");
	}
	
	public static void printNormally(Object o) {
		System.out.println(o);
	}
	
	private boolean hookPlayerPoints() {
	    final Plugin plugin = this.getServer().getPluginManager().getPlugin("PlayerPoints");
	    playerPoints = PlayerPoints.class.cast(plugin);
	    return playerPoints != null; 
	}

	public static PlayerPoints getPlayerPoints() {
    	return playerPoints;
	}
	
    @Override
    public void onEnable() {	
    	instance = this;
    	
    	if(!hookPlayerPoints()) {
    		throw new Error("Impossible d'accrocher le plugin \"PlayerPoints\".");
    	}
    	
    	YmlManager.init();
    	Events.init();
    	
    	Events.registerEvents(this);
    	Commands.registerCommands(this);
    	TabComplete.registerCompleters(this);

    	Main.printMc("");
    	Main.printMc("§aPlugin activé!");
    	Main.printMc("§7§lCe plugin est développé pour, et SEULEMENT pour le serveur SunOfSky minecraft, par JoueurAnonyme.");
    	Main.printMc("§7Vous n'êtes pas autorisé à publier, copier, louer, transférer, prêter, décompiler, désassembler,");
    	Main.printMc("§7lire, reproduire, réécrire, en partie ou en intégralité, le code, et toutes les ressources liées");
    	Main.printMc("§7au plugin, avec, ou sans autorisation du développeur cité ci-dessus.");
    	Main.printMc("");
    	
    }

    @Override
    public void onDisable() {
    	Main.printMc("§cPlugin désactivé!");
    }
    
    
}
