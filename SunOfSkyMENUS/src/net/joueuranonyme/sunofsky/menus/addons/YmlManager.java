package net.joueuranonyme.sunofsky.menus.addons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.joueuranonyme.sunofsky.menus.Main;

public class YmlManager {
	public static void init() {
		init("plugins/SOS_Menus/config.yml");
	}
	
	public static void init(String fileName) {
		File dir = new File(fileName);
		dir.getParentFile().mkdirs();

		try {
			File file = new File(fileName);

			if(!file.exists()) {
				
				switch(fileName) {
					// ========================= How To - Simple =========================
					
					case "plugins/SOS_Menus/config.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║           -=≡ MENUS ≡=-           ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							prefix: "§fSun§eOf§fSky §6Shops §7> "
							
							title: "Shop"
							
							# Variables disponibles : {PLAYER}
							quitCommand: "tellraw {PLAYER} \"§fSun§eOf§fSky §6Shops §7> §fA bientôt !\""
							quitButton:
							  name: "§c§lQuitter"
							  lore: []
							
							backButton:
							  name: "§7§lRetour"
							  lore: []
							
							infoItem:
							  # Variables disponibles : {NB} {NAME} {UNIT_PRICE} {PRICE} {MONEY}
							  name: "§fCliquez ici pour acheter §e{NAME}"
							  lore:
							    - "§8-------------"
							    - "§f Vous êtes sur le point d'acheter :"
							    - ""
							    - "§a§l{NAME} §2§lx{NB} §fpour §6{PRICE}❆"
							    - "§7({UNIT_PRICE}❆/u)"
							    - ""
							    - "§7Vous avez actuellement §e{MONEY}❆"
							    - ""
							    - "§8-------------"
							
							buyButton:
							  # Variables disponibles : {NB} {NAME} {UNIT_PRICE} {PRICE} {MONEY}
							  name: "§a§lAcheter"
							  lore:
							    - "§7Prix : §c{PRICE}❆"
							    - "§7Votre compte: §e{MONEY}❆"
							    
							disabledBuyButton:
							  # Variables disponibles : {NB} {NAME} {UNIT_PRICE} {PRICE} {MONEY}
							  name: "§7§lAcheter"
							  lore:
							    - "§7Prix : §c{PRICE}❆"
							    - "§7Votre compte: §e{MONEY}❆"
							    - ""
							    - "§cVous n'avez pas les fonds suffisants"
							
							addButton:
							  name: "§a§lAjouter (+1)"
							  lore: []
							
							disabledAddButton:
							  name: "§7§lAjouter (+1)"
							  lore: 
							    - "Maximum atteint"
							
							removeButton:
							  name: "§c§lRetirer (-1)"
							  lore: []
							
							disabledRemoveButton:
							  name: "§7§lRetirer (-1)"
							  lore: 
							    - "Minimum atteint"
							
							confirmButton:
							  name: "§a§lConfirmer"
							  lore: []
							  
							cancelButton:
							  name: "§c§lAnnuler"
							  lore: []
							  
							messages:
							  # Variables disponibles : {NB} {NAME} {PRICE}
							  buyed: "§aVous venez d'acheter {NB}x {NAME} pour {PRICE}"
							  noMoney: "§cVous n'avez pas les fonds suffisants pour effectuer cette achat. Veuillez réessayer. §7Si ce message réapparait, veuillez contacter le staff."
							  errorWhenBuying: "§cUne erreur est survenue lors de l'achat. Veuillez réessayer. §7Si ce message réapparait, veuillez contacter le staff."
							  noLoanPermitted: "§cVous n'êtes pas autorisé à contracter un prêt pour faire cette action !"
							
							""");
						pr.close();
					}

					// ========================= Défaut =========================
					
					default -> {
						throw new Error("Fichier introuvable : \"" + fileName + "\"");
					}
				}
			}
		} catch (IOException e) {
			System.out.println(Main.getPrefix() + Color.RED + "Impossible de créer un fichier de configuration.");
			throw new Error(Main.getPrefix() + "Erreur lors de l'écriture d'un fichier : Manque de permissions ? (" + fileName + ")", e);
		}
	}
	
	public static FileConfiguration read(String filePath) {
		File file = new File(filePath);
		
		if(!file.exists()) {
			init();
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	
	public static FileConfiguration readAndCheck(String filePath, String[] requiredParams) {
		File file = new File(filePath);
		FileConfiguration config = read(filePath);
		
		boolean valid = true;
		List<String> missingParams = new ArrayList<String>();
		
		for(String param: requiredParams) {
			if(!config.contains(param)){
				missingParams.add(param);
				valid = false;
			}
		}
		
		if(!valid) {
			if(missingParams.size() == 1) {
				Main.printMc(String.format("§cUn paramètre est manquant dans le fichier \"%s >> %s\" : [%s]", file.getPath(), file.getName(), String.join(", ", missingParams)));
				throw new Error();
			} else {
				Main.printMc(String.format("§cPlusieurs paramètres sont manquants dans le fichier \"%s >> %s\" : [%s]", file.getPath(), file.getName(), String.join(", ", missingParams)));
				throw new Error();
			}
		}
		
		return config;
	}
	
	public static void write(String filePath, FileConfiguration fileData) {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		
		try {
			fileData.save(file);
		} catch (IOException e) {
			System.out.println(Main.getPrefix() + Color.RED + "Impossible de créer un fichier de configuration.");
			throw new Error(Main.getPrefix() + "Erreur lors de l'écriture d'un fichier : Manque de permissions ? (" + filePath + ")", e);
		}
	}
}
