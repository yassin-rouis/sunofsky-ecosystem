package net.joueuranonyme.sunofsky.core.commands;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.User;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import net.ess3.api.MaxMoneyException;
import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.SmeltingLib;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;

public class FurnaceCommand implements CommandExecutor {
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.furnace.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(args.length == 0) {
			ItemStack stack = ((Player) sender).getInventory().getItemInMainHand();
			long unitPrice = config.getLong("price");
			int nbItems = 0;
			long price;
			switch(config.getString("per")) {
				case "slot" -> {
					nbItems = (SmeltingLib.isSmeltable(stack.getType())?1:0);
					price = unitPrice * nbItems;
				}
				case "use" -> {
					nbItems = (SmeltingLib.isSmeltable(stack.getType())?1:0);
					price = unitPrice * nbItems;
				}
				
				default -> {
					nbItems = stack.getAmount();
					price = unitPrice * nbItems;
				}
			}
			
			if(!SmeltingLib.isSmeltable(stack.getType())) return true;
			
			BigDecimal ecoPrice = new BigDecimal(price);
			
			try {
				if(Economy.hasEnough(((Entity) sender).getUniqueId(), ecoPrice)) {
					Economy.subtract(((Entity) sender).getUniqueId(), ecoPrice);
					((Player) sender).getInventory().setItemInMainHand(SmeltingLib.getSmelted(stack));
					
					if(config.getString("per").equals("use")) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("useMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)));
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("handMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)).replaceAll("\\{PRICE\\}", Long.toString(price)));
					}
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noEnoughMoney"));
					return true;
				}
			} catch (ArithmeticException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-AE-1]");
				return true;
			} catch (UserDoesNotExistException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-UDNE-1]");
				return true;
			} catch (NoLoanPermittedException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noLoanPermitted"));
				return true;
			} catch (MaxMoneyException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-MME-1]");
				return true;
			}
			
		}
		
		if(args.length == 1 && args[0].equalsIgnoreCase("hand")) {
			ItemStack stack = ((Player) sender).getInventory().getItemInMainHand();
			long unitPrice = config.getLong("price");
			int nbItems = 0;
			long price;
			switch(config.getString("per")) {
				case "slot" -> {
					nbItems = (SmeltingLib.isSmeltable(stack.getType())?1:0);
					price = unitPrice * nbItems;
				}
				case "use" -> {
					nbItems = (SmeltingLib.isSmeltable(stack.getType())?1:0);
					price = unitPrice * nbItems;
				}
				
				default -> {
					nbItems = stack.getAmount();
					price = unitPrice * nbItems;
				}
			}
			
			if(!SmeltingLib.isSmeltable(stack.getType())) return true;
			
			BigDecimal ecoPrice = new BigDecimal(price);
			
			try {
				if(Economy.hasEnough(((Entity) sender).getUniqueId(), ecoPrice)) {
					Economy.subtract(((Entity) sender).getUniqueId(), ecoPrice);
					((Player) sender).getInventory().setItemInMainHand(SmeltingLib.getSmelted(stack));
					
					if(config.getString("per").equals("use")) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("useMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)));
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("handMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)).replaceAll("\\{PRICE\\}", Long.toString(price)));
					}
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noEnoughMoney"));
					return true;
				}
			} catch (ArithmeticException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-AE-1]");
				return true;
			} catch (UserDoesNotExistException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-UDNE-1]");
				return true;
			} catch (NoLoanPermittedException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noLoanPermitted"));
				return true;
			} catch (MaxMoneyException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-MME-1]");
				return true;
			}
			
		}
		
		if(args.length == 1 && args[0].equalsIgnoreCase("all")) {
			Inventory inv = ((Player) sender).getInventory();
			long unitPrice = config.getLong("price");
			int nbItems = 0;
			long price;
			switch(config.getString("per")) {
				case "slot" -> {
					nbItems = SmeltingLib.howMuchSmeltable(inv, false);
					price = unitPrice * nbItems;
				}
				case "use" -> {
					nbItems = (SmeltingLib.howMuchSmeltable(inv) > 0?1:0);
					price = unitPrice * nbItems;
				}
				
				default -> {
					nbItems = SmeltingLib.howMuchSmeltable(inv, true);
					price = unitPrice * nbItems;
				}
			}
			
			BigDecimal ecoPrice = new BigDecimal(price);
			
			try {
				if(Economy.hasEnough(((Entity) sender).getUniqueId(), ecoPrice)) {
					Economy.subtract(((Entity) sender).getUniqueId(), ecoPrice);
					SmeltingLib.smelt(inv);
					if(config.getString("per").equals("slot")) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("allSlotsMessage").replaceAll("\\{NB_SLOTS\\}", Integer.toString(nbItems)).replaceAll("\\{PRICE\\}", Long.toString(price)));
					} else if(config.getString("per").equals("use")) {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("useMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)));
					} else {
						sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("allItemsMessage").replaceAll("\\{NB_ITEMS\\}", Integer.toString(nbItems)).replaceAll("\\{PRICE\\}", Long.toString(price)));
					}
					
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noEnoughMoney"));
				}
			} catch (ArithmeticException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-AE-1]");
			} catch (UserDoesNotExistException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-UDNE-1]");
			} catch (NoLoanPermittedException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noLoanPermitted"));
			} catch (MaxMoneyException e) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : furcom-MME-1]");
			}
			return true;
		}
		return true;
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/furnace.yml", new String[] {
					"prefix", "price", "per", "cooldown", "noEnoughMoney", "noLoanPermitted",
					"useMessage", "handMessage", "allSlotsMessage", "allItemsMessage"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
