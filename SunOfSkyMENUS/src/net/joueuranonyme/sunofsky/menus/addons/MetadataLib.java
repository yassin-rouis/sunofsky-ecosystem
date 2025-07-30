package net.joueuranonyme.sunofsky.menus.addons;

import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.joueuranonyme.sunofsky.menus.Main;

public class MetadataLib {
	public static boolean has(ItemStack item, String metadataName) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		return im.getPersistentDataContainer().getKeys().contains(key);
	}
	
	public static void setString(ItemStack item, String metadataName, String data) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		im.getPersistentDataContainer().set(key, PersistentDataType.STRING, data);
		item.setItemMeta(im);
	}
	
	public static void setInteger(ItemStack item, String metadataName, int data) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		im.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, data);
		item.setItemMeta(im);
	}
	
	public static void setFloat(ItemStack item, String metadataName, float data) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		im.getPersistentDataContainer().set(key, PersistentDataType.FLOAT, data);
		item.setItemMeta(im);
	}
	
	public static void setBoolean(ItemStack item, String metadataName, boolean data) {
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		im.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (data ? (byte) 1 : (byte) 0));
		item.setItemMeta(im);
	}
	
	public static String getString(ItemStack item, String metadataName) {
		if(!has(item, metadataName)) return null;
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		try {
			return im.getPersistentDataContainer().get(key, PersistentDataType.STRING);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static Integer getInteger(ItemStack item, String metadataName) {
		if(!has(item, metadataName)) return null;
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		try {
			return im.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static Float getFloat(ItemStack item, String metadataName) {
		if(!has(item, metadataName)) return null;
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		try {
			return im.getPersistentDataContainer().get(key, PersistentDataType.FLOAT);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static Boolean getBoolean(ItemStack item, String metadataName) {
		if(!has(item, metadataName)) return null;
		NamespacedKey key = new NamespacedKey(Main.getInstance(), metadataName);
		ItemMeta im = item.getItemMeta();
		try {
			return (im.getPersistentDataContainer().get(key, PersistentDataType.BYTE) == (byte) 1 ? true : false);
		} catch(Exception e) {
			return null;
		}
	}
	
	public static void setName(ItemStack item, String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
	}
	
	public static void setLore(ItemStack item, List<String> lore) {
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
	}
}
