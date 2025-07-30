package net.joueuranonyme.sunofsky.menus.events;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.joueuranonyme.sunofsky.menus.Main;
import net.joueuranonyme.sunofsky.menus.addons.Fixer;
import net.joueuranonyme.sunofsky.menus.addons.JavaUtilities;
import net.joueuranonyme.sunofsky.menus.addons.MetadataLib;
import net.joueuranonyme.sunofsky.menus.addons.YmlManager;
import net.joueuranonyme.sunofsky.menus.commands.ShopCommand;

public class ShopMenu_Events implements Listener {
	
	private static PlayerPointsAPI PPA;
	private static FileConfiguration config;
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
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
	
	@EventHandler
	public void onShopInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		if(inv== null) return;
		if(inv.getItem(22) == null) return;
		if(!MetadataLib.has(inv.getItem(22), "shops_itemId")) return;
		if(MetadataLib.getBoolean(inv.getItem(22), "shops_confirmation")) return;

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		
		ItemStack clickedItem = event.getCurrentItem();
		ItemStack item = inv.getItem(22);
		event.setCancelled(true);
		
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		if(event.getSlot() == 20) {
			if(item.getAmount() > 1) {
				if(event.getClick().equals(ClickType.LEFT)) {
					item.setAmount(item.getAmount()-1);
				} else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
					item.setAmount(1);
				}
			}
		}
		
		if(event.getSlot() == 24) {
			if(item.getAmount() < MetadataLib.getInteger(item, "shops_maxItems")) {
				if(event.getClick().equals(ClickType.LEFT)) {
					item.setAmount(item.getAmount()+1);
				} else if (event.getClick().equals(ClickType.SHIFT_LEFT)) {
					item.setAmount(MetadataLib.getInteger(item, "shops_maxItems"));
				}
			}
		}
		
		if(event.getSlot() == 31) {
			if(MetadataLib.getBoolean(inv.getItem(31), "shops_canBuy")) {
				openConfirmation(p, item);
			} else {
				return;
			}
		}
		
		if(event.getSlot() == 40) {
			p.closeInventory();
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			Bukkit.dispatchCommand(console, config.getString("quitCommand").replaceAll("\\{PLAYER\\}", Fixer.fixString(event.getWhoClicked().getName())));
			return;
		}

		// --------------------
		
		int price = MetadataLib.getInteger(item, "shops_itemPrice") * item.getAmount();
		UUID uuid = ((Entity) p).getUniqueId();
		
		int money = PPA.look(uuid);
		boolean hasEnoughMoney = money >= price;

		String stringMoney = df.format(money).replaceAll("\u202F", "'");
		String stringPrice = df.format(price).replaceAll("\u202F", "'");
		String stringNb = Integer.toString(item.getAmount());
		String stringUnitPrice = Float.toString(MetadataLib.getInteger(item, "shops_itemPrice"));
		
		String itemName = MetadataLib.getString(item, "shops_itemName").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);

		ItemMeta itemMeta = item.getItemMeta();
		
		if(MetadataLib.getString(item, "shops_itemLore") != null) {
			List<String> itemLore = new ArrayList<String>();
			for(String line: MetadataLib.getString(item, "shops_itemLore").split("\n")) {
				itemLore.add(line);
			}
			JavaUtilities.replaceEach(itemLore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(itemLore, "\\{PLAYER\\}", event.getWhoClicked().getName());
			JavaUtilities.replaceEach(itemLore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(itemLore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(itemLore, "\\{PRICE\\}", stringPrice);
	
			itemMeta.setLore(itemLore);
		}
		
		itemMeta.setDisplayName(itemName);
		item.setItemMeta(itemMeta);
		
		if(item.getAmount() == 1) {
			String name = config.getString("disabledRemoveButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			List<String> lore = config.getStringList("disabledRemoveButton.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{PLAYER\\}", event.getWhoClicked().getName());
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			inv.setItem(20,newItem(Material.GRAY_STAINED_GLASS, name, lore));
			
		} else {
			String name = config.getString("removeButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			List<String> lore = config.getStringList("removeButton.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{PLAYER\\}", event.getWhoClicked().getName());
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			inv.setItem(20, newItem(Material.RED_STAINED_GLASS, name, lore));
		}
		
		if(item.getAmount() == MetadataLib.getInteger(item, "shops_maxItems")) {
			String name = config.getString("disabledAddButton.name");
			List<String> lore = config.getStringList("disabledAddButton.lore");
			
			inv.setItem(24, newItem(Material.GRAY_STAINED_GLASS, name, lore));
		} else {
			String name = config.getString("addButton.name");
			List<String> lore = config.getStringList("addButton.lore");
			
			inv.setItem(24, newItem(Material.GREEN_STAINED_GLASS, name, lore));
		}
		
		if(hasEnoughMoney) {
			String name = config.getString("buyButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			List<String> lore = config.getStringList("buyButton.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{PLAYER\\}", event.getWhoClicked().getName());
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			ItemStack button = newItem(Material.LIME_DYE, name, lore);
			MetadataLib.setBoolean(button, "shops_canBuy", true);
			
			inv.setItem(31, button);
		} else {
			String name = config.getString("disabledBuyButton.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
			List<String> lore = config.getStringList("disabledBuyButton.lore");
			JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
			JavaUtilities.replaceEach(lore, "\\{PLAYER\\}", event.getWhoClicked().getName());
			JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
			JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
			JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
			
			ItemStack button = newItem(Material.GRAY_DYE, name, lore);
			MetadataLib.setBoolean(button, "shops_canBuy", false);
			
			inv.setItem(31, button);
		}
		

		String name = config.getString("infoItem.name").replaceAll("\\{MONEY\\}", stringMoney).replaceAll("\\{NB\\}", stringNb).replaceAll("\\{UNIT_PRICE\\}", stringUnitPrice).replaceAll("\\{PRICE\\}", stringPrice);
		List<String> lore = config.getStringList("infoItem.lore");
		JavaUtilities.replaceEach(lore, "\\{MONEY\\}", stringMoney);
		JavaUtilities.replaceEach(lore, "\\{NAME\\}", itemName);
		JavaUtilities.replaceEach(lore, "\\{PLAYER\\}", event.getWhoClicked().getName());
		JavaUtilities.replaceEach(lore, "\\{NB\\}", stringNb);
		JavaUtilities.replaceEach(lore, "\\{UNIT_PRICE\\}", stringUnitPrice);
		JavaUtilities.replaceEach(lore, "\\{PRICE\\}", stringPrice);
		
		inv.setItem(4, newItem(Material.WRITABLE_BOOK, name, lore));
	}
	
	@EventHandler
	public void onConfirmInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		if(inv == null) return;
		if(inv.getItem(22) == null) return;
		if(!MetadataLib.has(inv.getItem(22), "shops_itemId")) return;
		if(!MetadataLib.getBoolean(inv.getItem(22), "shops_confirmation")) return;
		event.setCancelled(true);
		
		ItemStack clickedItem = event.getCurrentItem();
		ItemStack item = inv.getItem(22);
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		if(event.getSlot() == 20 || event.getSlot() == 21) {
			p.closeInventory();
			return;
		}
		
		if(event.getSlot() == 39) {
			p.closeInventory();
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			Bukkit.dispatchCommand(console, "sosshop " + MetadataLib.getString(item, "shops_itemId") + " " + event.getWhoClicked().getName());
			return;
		}
		
		if(event.getSlot() == 40) {
			p.closeInventory();
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			Bukkit.dispatchCommand(console, config.getString("quitCommand").replaceAll("\\{PLAYER\\}", Fixer.fixString(event.getWhoClicked().getName())));
			return;
		}

		
		if(event.getSlot() == 23 || event.getSlot() == 24) {
			int price = MetadataLib.getInteger(item, "shops_itemPrice") * item.getAmount();
			UUID uuid = ((Entity) p).getUniqueId();
			
			int money = PPA.look(uuid);
			boolean hasEnoughMoney = money >= price;
			
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(0);
			df.setGroupingSize(3);
			df.setGroupingUsed(true);
			
			String stringPrice = df.format(price).replaceAll("\u202F", "'");
			
			if(hasEnoughMoney) {
				try {
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					Bukkit.dispatchCommand(console, MetadataLib.getString(item, "shops_command").replaceAll("\\{PLAYER\\}", Fixer.fixString(p.getName())).replaceAll("\\{NB\\}", Integer.toString(item.getAmount())));
					PPA.take(uuid, price);
					p.sendMessage(config.getString("prefix") + config.getString("messages.buyed").replaceAll("\\{PRICE\\}", stringPrice).replaceAll("\\{NB\\}", Integer.toString(item.getAmount())).replaceAll("\\{NAME\\}", Fixer.fixString(item.getItemMeta().getDisplayName())));
				} catch(CommandException e) {
					p.sendMessage(config.getString("prefix") + config.getString("messages.errorWhenBuying"));
				}
			} else {
				p.sendMessage(config.getString("prefix") + config.getString("messages.noMoney"));
			}
			

			p.closeInventory();
			return;
		}
	}
	
	public void openConfirmation(Player p, ItemStack item) {
		Inventory inv = Bukkit.createInventory(null, 45, "Confirmation de l'achat");
		
		for(int i: new Integer[] {0, 8, 36, 44, 1, 7, 9, 17, 27, 35, 37, 43}) {
			inv.setItem(i, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {18, 26}) {
			inv.setItem(i, newItem(Material.WHITE_STAINED_GLASS_PANE, " "));
		}
		
		for(int i: new Integer[] {20, 21}) {
			inv.setItem(i, newItem(Material.RED_STAINED_GLASS_PANE, config.getString("cancelButton.name"), config.getStringList("cancelButton.lore")));
		}
		
		for(int i: new Integer[] {20, 21}) {
			inv.setItem(i, newItem(Material.RED_STAINED_GLASS_PANE, config.getString("cancelButton.name"), config.getStringList("cancelButton.lore")));
		}
		
		for(int i: new Integer[] {23, 24}) {
			inv.setItem(i, newItem(Material.GREEN_STAINED_GLASS_PANE, config.getString("confirmButton.name"), config.getStringList("confirmButton.lore")));
		}
		ItemStack i;
		i = item;
		MetadataLib.setBoolean(i, "shops_confirmation", true);
		
		inv.setItem(22, i);
		inv.setItem(40, newItem(Material.BARRIER, config.getString("quitButton.name"), config.getStringList("quitButton.lore")));
		inv.setItem(39, newItem(Material.ARROW, config.getString("backButton.name"), config.getStringList("backButton.lore")));
		
		p.openInventory(inv);
	}
	
	
	@EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        if (e.getInventory().equals(ShopCommand.inv)) {
          e.setCancelled(true);
        }
    }



	public static boolean init() {		
		PPA = Main.getPlayerPoints().getAPI();
		
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Menus/config.yml", new String[] {
				"prefix", "title",
				"quitCommand",
				"quitButton.name", "quitButton.lore", 
				"backButton.name", "backButton.lore", 
				"buyButton.name", "buyButton.lore",
				"addButton.name", "addButton.lore",
				"confirmButton.name", "confirmButton.lore",
				"cancelButton.name", "cancelButton.lore",
				"removeButton.name", "removeButton.lore",
				"disabledAddButton.name", "disabledAddButton.lore",
				"disabledRemoveButton.name", "disabledRemoveButton.lore",
				"messages.buyed", "messages.noMoney", "messages.errorWhenBuying", "messages.noLoanPermitted"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
