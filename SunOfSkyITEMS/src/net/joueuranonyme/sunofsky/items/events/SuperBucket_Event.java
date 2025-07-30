package net.joueuranonyme.sunofsky.items.events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class SuperBucket_Event implements Listener {
	
	@EventHandler
	public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();

		if(!i.hasItemMeta()) return;
		if(!MetadataLib.has(i, "t_isSuperBucket")) return;
		if(!MetadataLib.getBoolean(i, "t_isSuperBucket")) return;
		if(!(i.getType().equals(Material.WATER_BUCKET) || i.getType().equals(Material.LAVA_BUCKET))) return;
		
		event.setItemStack(i);
	}

}
