package net.joueuranonyme.sunofsky.items.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.joueuranonyme.sunofsky.items.Main;
import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import net.joueuranonyme.sunofsky.items.addons.SpigotUtilities;
import net.joueuranonyme.sunofsky.items.items.ItemsLoader;

public class GiveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		if(args.length <= 1) {
			sender.sendMessage("§cUsage : /sosgive <Joueur> <item> [nombre] [radius|none] [durability|none]");
			return true;
		}
		
		if(SpigotUtilities.checkPlayerOnline(args[0])) {
			if(ItemsLoader.loadedItems.containsKey(args[1])) {
				SpigotUtilities.getOnlinePlayer(args[0]).getInventory().addItem(ItemsLoader.loadedItems.get(args[1]));
				return true;
			} else {
				sender.sendMessage("§cItem introuvable");
				return true;
			}
		} else {
			sender.sendMessage("§cJoueur introuvable");
			return true;
		}
	}

}
