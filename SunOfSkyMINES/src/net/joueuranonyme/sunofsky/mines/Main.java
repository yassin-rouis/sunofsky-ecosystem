package net.joueuranonyme.sunofsky.mines;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.mines.addons.YmlManager;
import net.joueuranonyme.sunofsky.mines.events.Events;
import net.joueuranonyme.sunofsky.mines.placeholders.Placeholders;

public class Main extends JavaPlugin {
    
	private static Main instance;
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return getPrefix("§7[§fSun§eOf§fSky §6MINES§7] §r");
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
	
    @Override
    public void onEnable() {	
    	instance = this;
    	YmlManager.init();
    	
    	Events.init();
    	Placeholders.init();
    	
    	Events.registerEvents(this);
    	Placeholders.registerPlaceholders(this);

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
    	Events.unload();
    	Main.printMc("§cPlugin désactivé!");
    }
    
    
}
