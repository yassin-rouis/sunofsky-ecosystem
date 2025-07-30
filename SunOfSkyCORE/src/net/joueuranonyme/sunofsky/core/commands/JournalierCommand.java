package net.joueuranonyme.sunofsky.core.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;

public class JournalierCommand implements CommandExecutor {

	private static FileConfiguration config;
	private static FileConfiguration dataConfig;
	public static Inventory inv;
	
	private static ItemStack newItem(final Material material, final String name, final List<String> description) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        List<String> nlore = new ArrayList<String>();
        
        for(String s: description) {
        	nlore.add("§7"+s);
        }
        meta.setDisplayName("§f"+name);
        meta.setLore(nlore);
        item.setItemMeta(meta);
        return item;
    }
	
	private static ItemStack newItem(final Material material, final String name, final String... description) {
		return newItem(material, name, Arrays.asList(description));
    }
	
	public static void pickPrize(Player p) {
		LocalDate ld = LocalDate.now();
		LocalDate lf = LocalDate.parse(dataConfig.getString("date"));
		
		if(lf.plusDays(1).isBefore(ld) || lf.plusDays(1).isEqual(ld)) {
			dataConfig.set("date", ld.toString());
			List<String> players = new ArrayList<String>();
			players.add(p.getName());
			dataConfig.set("players", players);
			YmlManager.write("plugins/SOS_Core/data/journalier.yml", dataConfig);
		} else {
			List<String> players = dataConfig.getStringList("players");
			if(!players.contains(p.getName())) {
				players.add(p.getName());
				dataConfig.set("players", players);
				YmlManager.write("plugins/SOS_Core/data/journalier.yml", dataConfig);
			}
		}
	}
	
	public static boolean hasPrize(Player p) {
		LocalDate ld = LocalDate.now();
		LocalDate lf = LocalDate.parse(dataConfig.getString("date"));
		
		if(lf.plusDays(1).isBefore(ld) || lf.plusDays(1).isEqual(ld)) {
			return true;
		} else {
			List<String> players = dataConfig.getStringList("players");
			if(players.contains(p.getName())) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		init(false);


		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.journalier.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(hasPrize((Player) sender)) {
			try {
				ItemStack item = newItem(Material.getMaterial(config.getString("prize.id").toUpperCase()), config.getString("prize.name"), config.getStringList("prize.lore"));
				if(config.getBoolean("prize.enchant")) {
					item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(meta);
				}
				inv.setItem(22, item);
			} catch (Exception e) {
				System.out.println(e);
				ItemStack item = newItem(Material.GRASS_BLOCK, "???", "???");
				ItemMeta meta = item.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				item.setItemMeta(meta);
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				inv.setItem(22, item);
			}
		} else {
			try {
				ItemStack item = newItem(Material.getMaterial(config.getString("noPrize.id").toUpperCase()), config.getString("noPrize.name"), config.getStringList("noPrize.lore"));
				if(config.getBoolean("noPrize.enchant")) {
					item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(meta);
				}
				inv.setItem(22, item);
			} catch (Exception e) {
				ItemStack item = newItem(Material.BARRIER, "???", "???");
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				ItemMeta meta = item.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				item.setItemMeta(meta);
				inv.setItem(22, item);
			}
		}
		
		((Player) sender).openInventory(inv);
		return true;
	}
	
	public static void initInv() {
		inv = Bukkit.createInventory(null, 54, config.getString("title"));		
		for(int i: new Integer[] {0, 8, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53}) {
			inv.setItem(i, newItem(Material.BLACK_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {1, 7, 9, 17, 27, 35, 37, 43}) {
			inv.setItem(i, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {2, 6, 18, 26, 38, 42}) {
			inv.setItem(i, newItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {13, 21, 23, 31}) {
			inv.setItem(i, newItem(Material.YELLOW_STAINED_GLASS_PANE, " "));
		}
		
		inv.setItem(49, newItem(Material.BARRIER, config.getString("quitButton.name"), config.getStringList("quitButton.lore")));
	}
	
	public static boolean init() {
		return init(true);
	}

	public static boolean init(Boolean i) {

		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/journalier.yml", new String[] {
				"prefix", "title", "quitButton.name", "quitButton.lore",
				"prize.id", "prize.name", "prize.lore", "prize.enchant", 
				"noPrize.id", "noPrize.name", "noPrize.lore", "noPrize.enchant"
			});
			
			dataConfig = YmlManager.readAndCheck("plugins/SOS_Core/data/journalier.yml", new String[] {
				"date", "players"
			});
			
			if(i) initInv();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
