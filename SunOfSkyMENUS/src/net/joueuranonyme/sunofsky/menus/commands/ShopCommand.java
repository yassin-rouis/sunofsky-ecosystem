package net.joueuranonyme.sunofsky.menus.commands;

import java.io.File;
import java.io.FilenameFilter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import net.joueuranonyme.sunofsky.menus.addons.Fixer;
import net.joueuranonyme.sunofsky.menus.addons.JavaUtilities;
import net.joueuranonyme.sunofsky.menus.addons.MetadataLib;
import net.joueuranonyme.sunofsky.menus.addons.SpigotUtilities;
import net.joueuranonyme.sunofsky.menus.Main;
import net.joueuranonyme.sunofsky.menus.addons.YmlManager;
import net.joueuranonyme.sunofsky.menus.events.ShopMenu_Events;

public class ShopCommand implements CommandExecutor {

	private static PlayerPointsAPI PPA;
	private static FileConfiguration guiConfig;
	public static Map<String, ItemStack> shops = new HashMap<String, ItemStack>();
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

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		init();
		initInv();
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("sosmenus.shop.reload")) {
				sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§cErreur lors du rechargement de la guiConfiguration !");
				}
				return true;
			}
		}
		
		if(args.length == 0) {
			return false;
		}
	    
		if(shops.keySet().contains(args[0].toLowerCase())) {
			Inventory nv;
			nv = inv;
			inv.setItem(20, newItem(Material.GRAY_STAINED_GLASS, guiConfig.getString("disabledRemoveButton.name"), guiConfig.getStringList("disabledRemoveButton.lore")));

			DecimalFormat df = new DecimalFormat();
			df.setGroupingSize(3);
			df.setGroupingUsed(true);
			
			Player targetPlayer = null;			
			
			if(args.length == 2 && SpigotUtilities.checkPlayerOnline(args[1])) {
				if(sender.hasPermission("sos.menus.others")) {
					sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§cVous n'avez pas la permission de faire cela!");
					return true;
				}
				targetPlayer = SpigotUtilities.getOnlinePlayer(args[1]);
			} else if (args.length == 2) {
				sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§cJoueur introuvable !");
				return true;
			} else if (args.length == 1) {
				if(sender instanceof Player) {
					targetPlayer = (Player) sender;
				} else {
					sender.sendMessage(Main.getPrefix(guiConfig.getString("prefix")) + "§cVous n'êtes pas un joueur pour exécuter/recevoir cette commande.");
					return true;
				}
			}
			ItemStack shopItem = new ItemStack(shops.get(args[0].toLowerCase()).getType());
			shopItem.setItemMeta(shops.get(args[0].toLowerCase()).getItemMeta());

			int price = MetadataLib.getInteger(shopItem, "shops_itemPrice") * shopItem.getAmount();
			UUID uuid = targetPlayer.getUniqueId();
			
			int money = PPA.look(uuid);
			boolean hasEnoughMoney = money >= price;

			String stringMoney = df.format(money).replaceAll("\u202F", "'");
			String stringPrice = df.format(price).replaceAll("\u202F", "'");
			String stringNb = Integer.toString(shopItem.getAmount());
			String stringUnitPrice = Float.toString(MetadataLib.getInteger(shopItem, "shops_itemPrice"));	
			

			ItemMeta itemMeta = shopItem.getItemMeta();
			/*itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll("\\{NB\\}", "1").replaceAll("\\{PLAYER\\}", Fixer.fixString(targetPlayer.getName())));
			List<String> llore = itemMeta.getLore();
			if(llore != null) {
				JavaUtilities.replaceEach(llore, "\\{NB\\}", "1");
				JavaUtilities.replaceEach(llore, "\\{PLAYER\\}", Fixer.fixString(targetPlayer.getName()));
				itemMeta.setLore(llore);
			}*/
			
			String itemName = MetadataLib.getString(shopItem, "shops_itemName").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			String stringName = itemName;
			List<String> itemLore = new ArrayList<String>();
			if(MetadataLib.getString(shopItem, "shops_itemLore") != null) {
				for(String line: MetadataLib.getString(shopItem, "shops_itemLore").split("\n")) {
					itemLore.add(line);
				}
				JavaUtilities.replaceEach(itemLore, "\\{MONEY\\}", stringMoney);
				JavaUtilities.replaceEach(itemLore, "\\{PLAYER\\}", sender.getName());
				JavaUtilities.replaceEach(itemLore, "\\{NB\\}", stringNb);
				JavaUtilities.replaceEach(itemLore, "\\{UNIT_PRICE\\}", stringUnitPrice);
				JavaUtilities.replaceEach(itemLore, "\\{PRICE\\}", stringPrice);
				
				itemMeta.setLore(itemLore);
			}
			
			itemMeta.setDisplayName(itemName);
			shopItem.setItemMeta(itemMeta);
			nv.setItem(22, shopItem);
			

			if(hasEnoughMoney) {
				String name = guiConfig.getString("buyButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{NAME\\}", Fixer.fixString(stringName)).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
				List<String> lore = guiConfig.getStringList("buyButton.lore");
				JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
				JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
				JavaUtilities.replaceEach(lore, "\\{NAME\\}", stringName);
				JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
				JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
				
				ItemStack button = newItem(Material.LIME_DYE, name, lore);
				MetadataLib.setBoolean(button, "shops_canBuy", true);
				
				nv.setItem(31, button);
			} else {
				String name = guiConfig.getString("disabledBuyButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{NAME\\}", Fixer.fixString(stringName)).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
				List<String> lore = guiConfig.getStringList("disabledBuyButton.lore");
				JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
				JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
				JavaUtilities.replaceEach(lore, "\\{NAME\\}", stringName);
				JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
				JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
				
				ItemStack button = newItem(Material.GRAY_DYE, name, lore);
				MetadataLib.setBoolean(button, "shops_canBuy", false);
				
				nv.setItem(31, button);
			}
			
			if(1 == MetadataLib.getInteger(shopItem, "shops_maxItems")) {
				String name = guiConfig.getString("disabledAddButton.name");
				List<String> lore = guiConfig.getStringList("disabledAddButton.lore");
				
				inv.setItem(24, newItem(Material.GRAY_STAINED_GLASS, name, lore));
			} else {
				String name = guiConfig.getString("addButton.name");
				List<String> lore = guiConfig.getStringList("addButton.lore");
				
				inv.setItem(24, newItem(Material.GREEN_STAINED_GLASS, name, lore));
			}
			
			String name = guiConfig.getString("infoItem.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{NAME\\}", Fixer.fixString(stringName)).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			List<String> lore = guiConfig.getStringList("infoItem.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{NAME\\}", stringName);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			nv.setItem(4, newItem(Material.WRITABLE_BOOK, name, lore));
			
			name = guiConfig.getString("infoItem.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{NAME\\}", Fixer.fixString(stringName)).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			lore = guiConfig.getStringList("infoItem.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{NAME\\}", stringName);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			nv.setItem(4, newItem(Material.WRITABLE_BOOK, name, lore));
			
			targetPlayer.openInventory(nv);
		} else {
			sender.sendMessage(String.format(Main.getPrefix(guiConfig.getString("prefix")) + "§cLe shop [%s] n'existe pas.", args[0].toLowerCase()));
		}
	    
		return true;
	}
	
	public static void initInv() {
		inv = Bukkit.createInventory(null, 45, guiConfig.getString("title"));
		
		for(int i: new Integer[] {0, 8, 36, 44, 1, 7, 9, 17, 27, 35, 37, 43}) {
			inv.setItem(i, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {18, 26}) {
			inv.setItem(i, newItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		}
		

		inv.setItem(40, newItem(Material.BARRIER, guiConfig.getString("quitButton.name"), guiConfig.getStringList("quitButton.lore")));
		inv.setItem(4, newItem(Material.WRITABLE_BOOK, guiConfig.getString("infoItem.name"), guiConfig.getStringList("infoItem.lore")));
		inv.setItem(20, newItem(Material.RED_STAINED_GLASS, guiConfig.getString("removeButton.name"), guiConfig.getStringList("removeButton.lore")));
		inv.setItem(24, newItem(Material.LIME_STAINED_GLASS, guiConfig.getString("addButton.name"), guiConfig.getStringList("addButton.lore")));

	}

	public static boolean init() {
		PPA = Main.getPlayerPoints().getAPI();
		
		try {
			shops.clear();
			ShopMenu_Events.init();
			guiConfig = YmlManager.readAndCheck("plugins/SOS_Menus/config.yml", new String[] {
				"prefix", "title",
				"quitButton.name", "quitButton.lore", 
				"backButton.name", "backButton.lore", 
				"infoItem.name", "infoItem.lore",
				"buyButton.name", "buyButton.lore",
				"disabledBuyButton.name", "disabledBuyButton.lore",
				"addButton.name", "addButton.lore",
				"removeButton.name", "removeButton.lore",
				"disabledAddButton.name", "disabledAddButton.lore",
				"confirmButton.name", "confirmButton.lore",
				"cancelButton.name", "cancelButton.lore",
				"disabledRemoveButton.name", "disabledRemoveButton.lore",
				"messages.buyed", "messages.noMoney", "messages.errorWhenBuying", "messages.noLoanPermitted"
			});
			


			File dir = new File("plugins/SOS_Menus/shops/");
			dir.mkdirs();
			
			FilenameFilter filter = new FilenameFilter() {
		        @Override
		        public boolean accept(File f, String name) {
		            return !name.startsWith("-") && name.endsWith(".yml");
		        }
		    };
		    
		    String[] unsafeShopsFiles = dir.list(filter);
		    
		    for(String unsafeShopFile: unsafeShopsFiles) {
		    	FileConfiguration iC = null;
		    	
		    	try {
					iC = YmlManager.read("plugins/SOS_Menus/shops/"+unsafeShopFile);
				} catch(Exception e) {
					continue;
				}
		    	
		    	if(!iC.contains("material") || !iC.contains("id")) {
					continue;
				}
				
				Material material = Material.getMaterial(iC.getString("material").toUpperCase());
				
				if(material == null) {
					continue;
				}
				
				ItemStack iS = new ItemStack(material);
				MetadataLib.setString(iS, "shops_itemId", iC.getString("id").toLowerCase());
				MetadataLib.setBoolean(iS, "shops_confirmation", false);
				MetadataLib.setInteger(iS, "shops_itemPrice", iC.getInt("price"));
				
				if(iC.getKeys(false).contains("maxItems")) {
					if(iC.getInt("maxItems") < material.getMaxStackSize()) {
						MetadataLib.setInteger(iS, "shops_maxItems", iC.getInt("maxItems"));
					} else if (iC.getInt("maxItems") >= material.getMaxStackSize()){
						MetadataLib.setInteger(iS, "shops_maxItems", material.getMaxStackSize());
					} else {
						MetadataLib.setInteger(iS, "shops_maxItems", 1);
					}
				} else {
					MetadataLib.setInteger(iS, "shops_maxItems", material.getMaxStackSize());
				}
				
				if(iC.getKeys(false).contains("enchant") && iC.getBoolean("enchant")) {
					iS.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta meta = iS.getItemMeta();
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					iS.setItemMeta(meta);
				}
				
				if(iC.getKeys(false).contains("name")) {
					ItemMeta meta = iS.getItemMeta();
					meta.setDisplayName(iC.getString("name"));
					iS.setItemMeta(meta);
					MetadataLib.setString(iS, "shops_itemName", iC.getString("name"));
				}
				
				if(iC.getKeys(false).contains("lore")) {
					ItemMeta meta = iS.getItemMeta();
					meta.setLore(iC.getStringList("lore"));
					iS.setItemMeta(meta);
					MetadataLib.setString(iS, "shops_itemLore", String.join("\n", iC.getStringList("lore")));
				}
				
				if(iC.getKeys(false).contains("command")) {
					MetadataLib.setString(iS, "shops_command", iC.getString("command"));
				}
				
				shops.put(iC.getString("id").toLowerCase(), iS);
		    }
		    
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}
