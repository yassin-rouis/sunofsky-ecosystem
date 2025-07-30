package net.joueuranonyme.sunofsky.dimension;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.dimension.events.Events;
import net.joueuranonyme.sunofsky.dimension.placeholders.Placeholders;
import net.joueuranonyme.sunofsky.dimension.addons.YmlManager;
import net.joueuranonyme.sunofsky.dimension.commands.Commands;

public class Main extends JavaPlugin {
    
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return getPrefix("§7[§fSun§eOf§fSky §6DIMENSION§7] §r");
	}
	
	public static String getPrefix(String text) {
		return text+"§r";
	}
	
	public static void printMc(String ...str) {
		Bukkit.getServer().getConsoleSender().sendMessage(Main.getPrefix() + String.join(" ", str));
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
    	TimerBossBar.init();
    	Dimension.init();
    	Commands.init();
    	Events.init();
    	Placeholders.init();
    	
    	Commands.registerCommands(this);
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
    	TimerBossBar.unload();
    	Dimension.unload();
    	Main.printMc("§cPlugin désactivé!");
    }
    
    
}
