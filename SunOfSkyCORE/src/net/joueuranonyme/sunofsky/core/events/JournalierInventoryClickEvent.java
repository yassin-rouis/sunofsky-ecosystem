package net.joueuranonyme.sunofsky.core.events;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.joueuranonyme.sunofsky.core.commands.JournalierCommand;

public class JournalierInventoryClickEvent implements Listener {
	private static FileConfiguration config;
	private static FileConfiguration dataConfig;
	public static Player lastJoinedPlayer;
	public static Date lastJoinedPlayerDate;
	
	private static ItemStack newItem(final Material material, final String name, final String... description) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(description));
        item.setItemMeta(meta);
        return item;
    }
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getInventory().equals(JournalierCommand.inv)) return;
		 event.setCancelled(true);
		 
		 ItemStack clickedItem = event.getCurrentItem();
		 if (clickedItem == null || clickedItem.getType().isAir()) return;
		 
		 Player p = (Player) event.getWhoClicked();
		 if(event.getRawSlot() == 22) {
			 if(!JournalierCommand.hasPrize((Player) event.getWhoClicked())){
				 p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noPrizeMessage"));
			 } else {
				 ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				 Bukkit.dispatchCommand(console, config.getString("command").replaceAll("\\{PLAYER\\}", p.getName()));
				 p.sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("prizeWinned"));
				 p.closeInventory();
				 JournalierCommand.pickPrize((Player) event.getWhoClicked());
			 }
		 } else if (event.getRawSlot() == 49) {
			 p.closeInventory();
		 }
	}
	
	@EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(JournalierCommand.inv)) {
          e.setCancelled(true);
        }
    }



	public static boolean init() {		
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/journalier.yml", new String[] {
					"prefix", "noPrizeMessage", "prizeWinned", "command"
				});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
