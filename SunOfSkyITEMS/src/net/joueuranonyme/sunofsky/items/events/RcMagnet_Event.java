package net.joueuranonyme.sunofsky.items.events;

import java.util.Collection;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import net.joueuranonyme.sunofsky.items.addons.ParticleLib;

public class RcMagnet_Event implements Listener {

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

		if(item == null) return;
		if(!item.hasItemMeta()) return;
		if(!MetadataLib.has(item, "t_isRcMagnet")) return;
		if(!MetadataLib.getBoolean(item, "t_isRcMagnet")) return;

		Player player = event.getPlayer();

		BoundingBox bb = player.getBoundingBox();

		Integer radius = MetadataLib.getInteger(player.getInventory().getItemInMainHand(), "radius");

		if(radius != null) {
			if(radius >= 0) {
				bb.expand(radius);
			} else {
				bb.expand(16);
			}
		} else {
			bb.expand(16);
		}

		Collection<Entity> entities = player.getWorld().getNearbyEntities(bb);

		for(Entity entity: entities) {
			if(!(entity instanceof Item)) continue;
			Item i = (Item) entity;
			player.getInventory().addItem(i.getItemStack());
			ParticleLib.spawnLine(Particle.ELECTRIC_SPARK, entity.getLocation(), player.getLocation(), 15);
			entity.remove();
		}
	}
}
