package net.joueuranonyme.sunofsky.items;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import net.joueuranonyme.sunofsky.items.addons.YmlManager;
import net.joueuranonyme.sunofsky.items.commands.Commands;
import net.joueuranonyme.sunofsky.items.events.Events;
import net.joueuranonyme.sunofsky.items.items.ItemsLoader;
import net.joueuranonyme.sunofsky.items.items.SellChestsLoader;
import net.joueuranonyme.sunofsky.items.tabcomplete.TabComplete;

public class Main extends JavaPlugin {
    
	private static Main instance;
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	private static HolographicDisplaysAPI hdAPI;
	
	public static Main getInstance() {
		return instance;
	}
	
	public static HolographicDisplaysAPI getHDAPI() {
		return hdAPI;
	}
	
	public static String getPrefix() {
		return getPrefix("§7[§fSun§eOf§fSky §6ITEMS§7] §r");
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
    	
    	if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
    		getLogger().severe("*** HolographicDisplays n'est pas installé ou activé. ***");
    		getLogger().severe("*** Le plugin va être désactivé ***");
    		this.setEnabled(false);
    		return;
    	}

        //hdAPI = HolographicDisplaysAPI.get(this);
    	
    	YmlManager.init();
    	Events.init();
    	SellChestsLoader.init();
    	
    	ItemsLoader.loadItems();
    	TabComplete.registerCompleters(this);
    	
    	Events.registerEvents(this);
    	Commands.registerCommands(this);

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
