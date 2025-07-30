package net.joueuranonyme.sunofsky.core.commands;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;

public class RenameCommand implements CommandExecutor {
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.rename.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cCette commande est réservée aux joueurs.");
			return true;
		}
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("name")) {
				sender.sendMessage("§cUsage : /rename name <Nom de l'item ...>");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("lore")) {
				sender.sendMessage("§cUsage : /rename lore <N° de ligne> <Contenu de la ligne ...>");
				return true;
			}
		}
		
		if(args.length >= 2) {
			if(args[0].equalsIgnoreCase("lore")) {
				if(args.length == 2) {
					sender.sendMessage("§cUsage : /rename lore <N° de ligne> <Contenu de la ligne ...>");
					return true;
				}
				
				if(!args[1].matches("^[0-9]+$")) {
					sender.sendMessage(config.getString("prefix") + "§cUsage : /rename lore <N° de ligne> <Contenu de la ligne ...>");
					return true;
				} else {
					int line = Integer.parseInt(args[1]);
					Player player = (Player) sender;
					ItemStack item = player.getInventory().getItemInMainHand();
					
					if(item.getType().isAir()) {
						player.sendMessage(config.getString("prefix") + config.getString("noItemInHand"));
						return true;
					}
					
					ItemMeta meta = item.getItemMeta();
					List<String> itemLore = meta.getLore();
					List<String> lore = new ArrayList<String>();
					String text = String.join(" ", Arrays.asList(args).subList(2, args.length));
					
					if(itemLore != null) {
						for(String lr : itemLore) {
							lore.add(lr);
						}
					}
					
					while(line > lore.size()) {
						lore.add("§r ");
					}
					
					lore.set(line-1, text.replaceAll("&", "§").replaceAll("§§", "&"));
					
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					player.getInventory().setItemInMainHand(item);
					
					player.sendMessage(config.getString("prefix") + config.getString("reloredItem"));
					
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("name")) {
				Player player = (Player) sender;
				ItemStack item = player.getInventory().getItemInMainHand();
				
				if(item.getType().isAir()) {
					player.sendMessage(config.getString("prefix") + config.getString("noItemInHand"));
					return true;
				}
				
				ItemMeta meta = item.getItemMeta();
				String text = String.join(" ", Arrays.asList(args).subList(1, args.length));
				
				meta.setDisplayName(text.replaceAll("&", "§").replaceAll("§§", "&"));
				item.setItemMeta(meta);
				
				player.getInventory().setItemInMainHand(item);
				
				player.sendMessage(config.getString("prefix") + config.getString("renamedItem"));
				
				return true;
				
			}
		}
		
		

		
		sender.sendMessage("§cUsage : /rename <name|lore> ...");		
		return true;
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/rename.yml", new String[] {
				"prefix", "renamedItem", "reloredItem", "noItemInHand"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
