package net.joueuranonyme.sunofsky.items.items;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings.Visibility;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.ess3.api.MaxMoneyException;
import net.joueuranonyme.sunofsky.items.Main;
import net.joueuranonyme.sunofsky.items.addons.Fixer;
import net.joueuranonyme.sunofsky.items.addons.YmlManager;

public class SellChestsLoader {
	private static FileConfiguration data;
	private static HolographicDisplaysAPI HDAPI = Main.getHDAPI();
	private static Map<Location, UUID> chestsOwners = new HashMap<Location, UUID>();
	private static Map<Location, Integer> chestsTimes = new HashMap<Location, Integer>();
	private static Map<Location, Hologram> chestsHolograms = new HashMap<Location, Hologram>();
	private static Map<Location, Integer> chestsRemainingTimes = new HashMap<Location, Integer>();
	private static Map<Location, BukkitRunnable> chestsTasks = new HashMap<Location, BukkitRunnable>();
	private static Map<Location, Float> chestsMultipliers = new HashMap<Location, Float>();
	public static FileConfiguration config;
	
	public static boolean isChestData(Location loc) {
		if( !chestsOwners.containsKey(loc) 
		 || !chestsTimes.containsKey(loc) 
		 || !chestsTasks.containsKey(loc) 
		 || !chestsRemainingTimes.containsKey(loc) 
		 || !chestsMultipliers.containsKey(loc)) return false;
		return true;
	}
	
	public static boolean isChest(Location loc) {
		if( !loc.getBlock().getType().equals(Material.CHEST)
		 || !isChestData(loc)) return false;
		return true;
	}
	
	public static OfflinePlayer getChestOwner(Location loc) {
		if(!chestsOwners.containsKey(loc)) return null;
		return Bukkit.getOfflinePlayer(chestsOwners.get(loc));
	}
	
	public static Integer getChestTime(Location loc) {
		if(!chestsTimes.containsKey(loc)) return null;
		return chestsTimes.get(loc);
	}
	
	public static String getChestTimeParsed(Location loc) {
		if(!chestsTimes.containsKey(loc)) return null;
		int time = chestsTimes.get(loc);
		int s = time%60;
		int m = ((time-s)/60)%60;
		int h = (((time-s)/60)-m)/60;
		
		return config.getString("timeFormat").replaceAll("\\{H\\}", Integer.toString(h)).replaceAll("\\{M\\}", Integer.toString(m)).replaceAll("\\{S\\}", Integer.toString(s));
	}
	
	public static Integer getChestRemainingTime(Location loc) {
		if(!chestsRemainingTimes.containsKey(loc)) return null;
		return chestsRemainingTimes.get(loc);
	}
	
	public static String getChestRemainingTimeParsed(Location loc) {
		if(!chestsRemainingTimes.containsKey(loc)) return null;
		int time = chestsRemainingTimes.get(loc);
		int s = time%60;
		int m = ((time-s)/60)%60;
		int h = (((time-s)/60)-m)/60;
		
		return config.getString("timeFormat").replaceAll("\\{H\\}", Integer.toString(h)).replaceAll("\\{M\\}", Integer.toString(m)).replaceAll("\\{S\\}", Integer.toString(s));
	}
	
	public static Float getChestMultiplicator(Location loc) {
		if(!chestsMultipliers.containsKey(loc)) return null;
		return chestsMultipliers.get(loc);
	}
	
	public static void removeChest(Location loc) {
		chestsOwners.remove(loc);
		chestsTimes.remove(loc);
		chestsRemainingTimes.remove(loc);
		chestsTasks.get(loc).cancel();
		chestsTasks.remove(loc);
		chestsMultipliers.remove(loc);
	}
	
	public static Hologram getHologram(Location loc) {
		if(!chestsHolograms.containsKey(loc)) return null;
		return chestsHolograms.get(loc);
	}
	
	public static void removeHologram(Location loc) {
		Hologram h = getHologram(loc);
		if(h == null) return;
		h.delete();
	}
	
	public static void setHologramVisibility(Location loc, boolean visibility) {
		Hologram h = getHologram(loc);
		if(h == null) return;
		h.getVisibilitySettings().setGlobalVisibility((visibility? Visibility.VISIBLE :Visibility.HIDDEN));
	}
	
	public static void updateHologram(Location loc) {
		Hologram h = getHologram(loc);
		if(h == null) return;
		List<String> text = config.getStringList("hologram");
		for(int i = 0; i < text.size(); i++) {
			String t = text.get(i);
			if(t == "") continue;
			t = t.replaceAll("\\{PLAYER\\}", Fixer.fixString(getChestOwner(loc).getName()));
			t = t.replaceAll("\\{MULTIPLICATOR\\}", Float.toString(getChestMultiplicator(loc)));
			t = t.replaceAll("\\{REFRESH\\}", Fixer.fixString(getChestTimeParsed(loc)));
			t = t.replaceAll("\\{REMAINING\\}", Fixer.fixString(getChestRemainingTimeParsed(loc)));
			h.getLines().insertText(i, t);
		}
	}
	
	public static void sellChestContent(Location loc, OfflinePlayer owner, float multiplier) {
		if(!isChest(loc)) return;
		Block b = loc.getBlock();
		Chest c = (Chest) b;
		Inventory chestInv = c.getInventory();
		float gain = 0;
		
		for(ItemStack i: chestInv.getContents()) {
			gain += ShopGuiPlusApi.getItemStackPriceSell(owner.getPlayer(), i);
		}
		
		try {
			Economy.add(owner.getUniqueId(), new BigDecimal(gain));
		} catch (NoLoanPermittedException e) {
			owner.getPlayer().sendMessage(Main.getPrefix(config.getString("prefix")) + config.getString("noLoanPermitted"));
			return;
		} catch (ArithmeticException e) {
			Main.printMc(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : selche-AE-1]");
			e.printStackTrace();
			return;
		} catch (UserDoesNotExistException e) {
			Main.printMc(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : selche-UDNE-1]");
			e.printStackTrace();
			return;
		} catch (MaxMoneyException e) {
			Main.printMc(Main.getPrefix(config.getString("prefix")) + "§cUne erreur inattendue s'est produite. §7[Code d'erreur : selche-MME-1]");
			e.printStackTrace();
			return;
		}
		
		chestInv.clear();
	}
	
	public static void initChest(Location location, OfflinePlayer owner, int timer, float multiplier) {
		chestsOwners.put(location, owner.getUniqueId());
		chestsTimes.put(location, timer);
		chestsRemainingTimes.put(location, timer);
		chestsMultipliers.put(location, multiplier);
		
		if(chestsTasks.containsKey(location)) {
			chestsTasks.get(location).cancel();
			chestsTasks.remove(location);
		}
		
		BukkitRunnable task = new BukkitRunnable() {
			Location loc = location;
			public void run() {
				if(!isChest(loc)) {
					cancel();
					chestsTasks.remove(loc);
					return;
				}
				
				
				if(chestsRemainingTimes.get(loc) > 0) {
					chestsRemainingTimes.put(loc, chestsRemainingTimes.get(loc)-1);
					updateHologram(loc);
					return;
				}

				if(chestsRemainingTimes.get(loc) == 0) {
					sellChestContent(loc, Bukkit.getOfflinePlayer(chestsOwners.get(loc)), chestsMultipliers.get(loc));
					chestsRemainingTimes.put(loc, chestsTimes.get(loc));
					updateHologram(loc);
				}
			}
		};
		
		if(chestsHolograms.containsKey(location)) {
			chestsHolograms.get(location).delete();
			chestsHolograms.remove(location);
		}
		
		Hologram hologram = HolographicDisplaysAPI.get(Main.getInstance()).createHologram(location.clone().add(0, 1, 0));
		chestsHolograms.put(location, hologram);
		updateHologram(location);
		
		task.runTaskTimer(Main.getPlugin(null), 20L, 20L);
		chestsTasks.put(location, task);
	}
	
	public static void init() {
		config = YmlManager.readAndCheck("plugins/SOS_Items/coffre_vente.yml", new String[] {
			"prefix", "name", "lore", "hologram"
		});
		
		data = YmlManager.readAndCheck("plugins/SOS_Items/data/coffre_vente.yml", new String[] {
			"locations"
		});
		
		for(String id: data.getConfigurationSection("locations").getKeys(false)) {
			ConfigurationSection chestData = data.getConfigurationSection("locations").getConfigurationSection(id);
			if( !chestData.contains("location")
			 || !chestData.contains("owner")
			 || !chestData.contains("timer")
			 || !chestData.contains("multiplier")) continue;
			
			if( !chestData.isLocation("location")
			 || !chestData.isOfflinePlayer("owner")
			 || !chestData.isInt("timer")
			 || !chestData.isDouble("multiplier")) continue;
			
			Location location = chestData.getLocation("location");
			OfflinePlayer owner = chestData.getOfflinePlayer("owner");
			int timer = chestData.getInt("timer");
			float multiplier = (float) chestData.getDouble("multiplier");
			
			if(!location.getBlock().getType().equals(Material.CHEST)) continue;
			
			initChest(location, owner, timer, multiplier);
			
		}
	}
}
