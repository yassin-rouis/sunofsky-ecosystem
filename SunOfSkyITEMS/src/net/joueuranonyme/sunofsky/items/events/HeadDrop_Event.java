package net.joueuranonyme.sunofsky.items.events;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadDrop_Event implements Listener {
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player kd = event.getEntity().getPlayer();
		Player kr = event.getEntity().getKiller().getPlayer();
		ItemStack i = kr.getInventory().getItemInMainHand();
		if(i == null) return;
		if(!i.hasItemMeta()) return;
		if (!MetadataLib.has(i, "t_dropsHeads")) return; 
		if (!MetadataLib.getBoolean(i, "t_dropsHeads")) return; 
		ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta headMeta = (SkullMeta)headItem.getItemMeta();
		headMeta.setOwnerProfile(kd.getPlayerProfile());
		headItem.setItemMeta((ItemMeta)headMeta);
		kr.getInventory().addItem(new ItemStack[] { headItem });
	}
}