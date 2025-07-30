package net.joueuranonyme.sunofsky.items.items;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.joueuranonyme.sunofsky.items.Main;
import net.joueuranonyme.sunofsky.items.addons.MetadataLib;
import net.joueuranonyme.sunofsky.items.addons.YmlManager;

public class ItemsLoader {
	public static Map<String, ItemStack> loadedItems = new HashMap<String, ItemStack>();
	
	public static void loadItems() {
		File dir = new File("plugins/SOS_Items/items/");
		dir.mkdirs();
		
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            return !name.startsWith("-") && name.endsWith(".yml");
	        }
	    };
	    
	    //Main.printMc(String.format("§f• Configuration des items..."));
	    //Main.printMc(String.format("├╼ Listage des fichiers d'items..."));
	    
	    String[] unsafeItemFiles = dir.list(filter);
	    //Main.printMc(String.format("│  ╰╼ Trouvés %s fichiers", unsafeItemFiles.length));
		//Main.printMc(String.format("├╼ Configuration des items customisés normaux ..."));
		
		for(String unsafeItemFile: unsafeItemFiles) {
		    //Main.printMc(String.format("│  ├╼ Lecture du fichier \"%s\"", unsafeItemFile));
		    
		    FileConfiguration iC = null;
		    
			try {
				iC = YmlManager.read("plugins/SOS_Items/items/"+unsafeItemFile);
			} catch(Exception e) {
			    //Main.printMc(String.format("│  │  ╰╼ §c⚠ Erreur de lecture."));
				continue;
			}
			
			if(!iC.contains("material") || !iC.contains("id")) {
			    //Main.printMc(String.format("│  │  ╰╼ §c⚠ Un des paramètres [material, id] est manquant."));
				continue;
			}
			
			Material material = Material.getMaterial(iC.getString("material").toUpperCase());
			
			if(material == null) {
			    //Main.printMc(String.format("│  │  ╰╼ §c⚠ Le paramètre [material] fourni est invalide."));
				continue;
			}
			
		    //Main.printMc(String.format("│  │  ├╼ Création de l'item ..."));
			
			ItemStack iS = new ItemStack(material);
			MetadataLib.setString(iS, "id", iC.getString("id"));
			
			Integer durability = null;
			Integer radius = null;
			String id = iC.getString("id");
			
			if(iC.contains("durability")) {
				durability = iC.getInt("durability");
				MetadataLib.setInteger(iS, "durability", durability);
			}
			
			if(iC.contains("radius")) {
				radius = iC.getInt("radius");
				MetadataLib.setInteger(iS, "radius", radius);
			}
			
			if(iC.contains("name")) {
				MetadataLib.setString(iS, "defaultName", iC.getString("name"));
				MetadataLib.setName(iS, iC.getString("name")
						.replaceAll("\\{DURABILITY\\}", (durability != null ? durability.toString() : "---"))
						.replaceAll("\\{RADIUS\\}", (radius != null ? radius.toString() : "---"))
				);
			}
			
			if(iC.contains("lore")) {
				MetadataLib.setString(iS, "defaultLore", String.join("\n", iC.getStringList("lore")));
				List<String> nLore = new ArrayList<String>();
				for(String l: iC.getStringList("lore")) {
					nLore.add(l
							.replaceAll("\\{DURABILITY\\}", (durability != null ? durability.toString() : "---"))
							.replaceAll("\\{RADIUS\\}", (radius != null ? radius.toString() : "---"))
					);
				}
				MetadataLib.setLore(iS, nLore);
			}
			
			if(iC.contains("types")) {
				for(String type: iC.getStringList("types")) {
					switch(type) {
						// ===== OUTILS =====
						case "magnet": MetadataLib.setBoolean(iS, "t_isMagnet", true); break;
						case "rc_magnet": MetadataLib.setBoolean(iS, "t_isRcMagnet", true); break;
						case "head_drop": MetadataLib.setBoolean(iS, "t_dropsHeads", true); break;
						case "super_axe": MetadataLib.setBoolean(iS, "t_isSuperAxe", true); break;
						case "super_pickaxe": MetadataLib.setBoolean(iS, "t_isSuperPickaxe", true); break;
						case "super_hoe": MetadataLib.setBoolean(iS, "t_isSuperHoe", true); break;
						case "super_bucket": MetadataLib.setBoolean(iS, "t_isSuperBucket", true); break;
						case "multitool": MetadataLib.setBoolean(iS, "t_multitool", true); break;
						case "instant_break": MetadataLib.setBoolean(iS, "t_instantBreak", true); break;
						// ===== ARMURES =====
						case "normal_soul_sand": MetadataLib.setBoolean(iS, "t_normalOnSoulSand", true); break;
						case "night_vision": MetadataLib.setBoolean(iS, "t_givesNightVision", true); break;
						case "force_i": MetadataLib.setBoolean(iS, "t_givesForceI", true); break;
						case "cushioned_fall": MetadataLib.setBoolean(iS, "t_cushionFalls", true); break;
						case "fast_swimming": MetadataLib.setBoolean(iS, "t_givesFastSwimming", true); break;
					}
				}
			}
			
			if(iC.contains("enchants")) {
				for(String enchant: iC.getStringList("enchants")) {
					String[] l = enchant.split(":");
					
					if(l.length >= 1) {
						Enchantment e = Enchantment.getByKey(NamespacedKey.minecraft(l[0]));
						if(e == null) continue;
						
						if(l.length == 1) {
							iS.addUnsafeEnchantment(e, 1);
						} else if(l[1].matches("^[0-9]+$")) {
							iS.addUnsafeEnchantment(e, Integer.parseInt(l[1]));
						} else {
							continue;
						}
					}
					
					continue;
				}
			}
			
			if(iC.contains("right_command")) {
				MetadataLib.setString(iS, "t_rightCommand", iC.getString("right_command"));
			}
			
			if(iC.contains("left_command")) {
				MetadataLib.setString(iS, "t_leftCommand", iC.getString("left_command"));
			}
			
			loadedItems.put(id, iS);
			
		}
	}
}