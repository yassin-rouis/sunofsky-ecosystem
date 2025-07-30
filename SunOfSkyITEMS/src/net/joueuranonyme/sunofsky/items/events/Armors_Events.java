package net.joueuranonyme.sunofsky.items.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.joueuranonyme.sunofsky.items.Main;
import net.joueuranonyme.sunofsky.items.addons.MetadataLib;

public class Armors_Events implements Listener {
	
	private static Map<String, ItemStack> getArmors(Player p){
		Inventory inv = p.getInventory();
		ItemStack head = inv.getItem(39);
		ItemStack chest = inv.getItem(38);
		ItemStack legs = inv.getItem(37);
		ItemStack feet = inv.getItem(36);
		
		Map<String, ItemStack> r = new HashMap<String, ItemStack>();

		r.put("head", head);
		r.put("chest", chest);
		r.put("legs", legs);
		r.put("feet", feet);
		
		return r;
	}
	
	private static Location getLocUnder(Location l) {
		Location le = l.subtract(0, 1, 0);
		if(le.getBlockY() < le.getWorld().getMinHeight()) {
			return le.add(0, 1, 0);
		}
		if(le.getBlock().isEmpty()) {
			return getLocUnder(le);
		} else {
			return le;
		}
	}
	
	private static boolean armorHasMeta(Player p, String meta) {
		boolean cont = false;
		Map<String, ItemStack> armor = getArmors(p);
		
		for(ItemStack aS: armor.values()){
			if(aS == null) continue;
			if(!aS.hasItemMeta()) continue;
			cont = cont || (MetadataLib.has(aS, meta) && MetadataLib.getBoolean(aS, meta));
			if(cont) break;
		}
		
		return cont;
	}
	
	// ################### EFFECTS ###################
	
	private static void cushionFalls(Player player) {
		Vector velocity = player.getVelocity();
		Location pos = player.getLocation();
		
		if(player.isFlying()) return;
		if(velocity.getY() >= -0.1) return;
		
		if(!armorHasMeta(player, "t_cushionFalls")) return;

		int fall = (int) player.getFallDistance();
		int startFall = player.getLocation().getBlockY() + fall;
		int endFall = getLocUnder(player.getLocation()).getBlockY();

		int falling = startFall - endFall;
		float fallingPercent = ((float) fall)/((float) falling);
		int fallR = falling - fall;
		
		//player.sendMessage(Integer.toString(startFall) + " §8#§r " + Integer.toString(endFall) + " §2>§r " + Float.toString(fallingPercent) + " §c>>§r " + Float.toString(fallR));
		
		if(!(fallingPercent > 0.4 && fallR > 1)) return;
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 10, false, false, false));
	}
	
	private static void fastSwimming(Player player) {
		Location loc = player.getLocation();
		if(!loc.getBlock().getType().equals(Material.WATER)) return;

		if(!armorHasMeta(player, "t_givesFastSwimming")) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1, false, false, false));
	}
	
	private static void forceI(Player player) {
		Location loc = player.getLocation();
		
		if(!armorHasMeta(player, "t_givesForceI")) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1, false, false, false));
	}
	
	private static void nightVision(Player player) {
		if(!armorHasMeta(player, "t_givesNightVision")) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 1, false, false, false));
	}
	
	private static void normalSoulSand(Player player) {
		Location loc = player.getLocation();
		
		if(!armorHasMeta(player, "t_normalOnSoulSand")) return;
		if(!loc.getBlock().getType().equals(Material.SOUL_SAND)) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2, false, false, false));
	}

	// ################### DEFS ###################
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		Inventory inv = player.getInventory();
		
		Map<String, ItemStack> armor = getArmors(player);
		
		/*for(ItemStack aS: armor.values()) {
			if(aS == null) continue;
			if(MetadataLib.has(aS, "t_givesFastSwimming") && MetadataLib.getBoolean(aS, "t_givesFastSwimming")) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10000000, 1, false, false, true));
			}
		}*/
	}
	
	public static void init() {
		BukkitRunnable task = new BukkitRunnable() {

			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					cushionFalls(p);
					fastSwimming(p);
					nightVision(p);
					forceI(p);
					normalSoulSand(p);
				}
			}
		};
		
		task.runTaskTimer(Main.getInstance(), 0L, 1L);
	}

}
