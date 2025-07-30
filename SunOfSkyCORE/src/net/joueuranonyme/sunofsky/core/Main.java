package net.joueuranonyme.sunofsky.core;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.commands.Commands;
import net.joueuranonyme.sunofsky.core.events.Events;
import net.joueuranonyme.sunofsky.core.placeholders.Placeholders;
import net.joueuranonyme.sunofsky.core.schedules.Schedules;
import net.joueuranonyme.sunofsky.core.tabcomplete.TabComplete;


public class Main extends JavaPlugin {
	
	private static Main instance;
	public static Main getInstance() {
		return instance;
	}
	
	public static String getPrefix() {
		return getPrefix("§7[§fSun§eOf§fSky §6CORE§7] §r");
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
    	
    	Commands.init();
    	Events.init();
    	Placeholders.init();
    	Schedules.init();
    	
    	Schedules.schedule();
    	
    	if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
    		Placeholders.registerPlaceholders(this);
    	}
    	
    	Commands.registerCommands(this);
    	Events.registerEvents(this);
    	TabComplete.registerCompleters(this);

    	YmlManager.init();

    	Main.printMc("");
    	Main.printMc("      §f┌━━━━━━━━━━━━━━━━━━━━━━━━┐");
    	Main.printMc("§8  §7  §f ≡│  §f▒█████§8═§6═══════§f▒█████  §f│≡ §7  §8  ");
    	Main.printMc("§8  §7  §f≡≡│  §f▒█      ▒§e████§f ▒█      §f│≡≡§7  §8  ");
    	Main.printMc("§8  §7 =§f≡≡│  §f▒█████  ▒§e█ §f▒§e█§f ▒█████  §f│≡≡§7= §8  ");
    	Main.printMc("§8  §7==§f≡≡│      §f▒█  ▒§e████§f     ▒█  §f│≡≡§7==§8- ");
    	Main.printMc("§8 -§7==§f≡≡│  §f▒█████§8═§6═══════§f▒█████  §f│≡≡§7==§8--");
    	Main.printMc("§8--§7==§f≡≡│                        §f│≡≡§7==§8- ");
    	Main.printMc("§8 -§7==§f≡≡│ §f▒████§8═§6══════════§f▒████  §f│≡≡§7==§8  ");
    	Main.printMc("§8  §7==§f≡≡│ §f▒█    ▒§e████§f▒§e███§f ▒█     §f│≡≡§7==§8  ");
    	Main.printMc("§8  §7 =§f≡≡│ §f▒█    ▒§e█ §f▒§e█§f▒§e█§f   ▒██    §f│≡≡§7= §8  ");
    	Main.printMc("§8  §7  §f≡≡│ §f▒█    ▒§e████§f▒§e█§f   ▒█     §f│≡≡§7  §8  ");
    	Main.printMc("§8  §7  §f ≡│ §f▒████§8═§6══════════§f▒████  §f│≡ §7  §8  ");
    	Main.printMc("      §f└━━━━━━━━━━━━━━━━━━━━━━━━┘");
    	Main.printMc("");
    	
    	String thisVersion = "Bêta Build [14/08/2022 #1]";
    	
    	FileConfiguration cc = YmlManager.read("plugins/SOS_Core/data/changelog.yml");
    	String version = cc.getString("version");
    	boolean showChanges = cc.getBoolean("showChangeLogs");
    	if(!thisVersion.equals(version) && showChanges) {
        	Main.printMc("");
        	Main.printMc("§7==== §6SunOfSKyCORE §fa été mis à jour ! §7====");
        	Main.printMc("");
        	Main.printMc("§7=============== §fChangelog §7===============");
        	Main.printMc("");
        	Main.printMc("§fDe: §c" + version + " §f--> À : §a" + thisVersion);
        	Main.printMc("");
        	Main.printMc("§6CORE :");
        	Main.printMc("§7[§6~§7] §fFix d'un bug au niveau du flytimer");
        	Main.printMc("");
        	Main.printMc("§7=============== §fChangelog §7===============");
        	Main.printMc("");
        	cc.set("version", thisVersion);
        	YmlManager.write("plugins/SOS_Core/data/changelog.yml", cc);
    	}
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
    	Schedules.end();
    	Main.printMc("§cPlugin désactivé!");
    }
    
    
}
