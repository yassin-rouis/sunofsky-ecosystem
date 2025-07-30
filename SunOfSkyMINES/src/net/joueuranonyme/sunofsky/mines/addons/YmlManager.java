package net.joueuranonyme.sunofsky.mines.addons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.joueuranonyme.sunofsky.mines.Main;

public class YmlManager {
	public static void init() {
		init("plugins/SOS_Mines/mines.yml");
	}
	
	public static void init(String fileName) {
		File dir = new File(fileName);
		dir.getParentFile().mkdirs();

		try {
			File file = new File(fileName);

			if(!file.exists()) {
				switch(fileName) {

					// ========================= Dimension =========================
					
					case "plugins/SOS_Mines/mines.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║           -=≡ MINES ≡=-           ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							ores_world: "world"
							ores:
							  diamond_ore:
							    # Chance : Valeur flottante entre 0.0 et 100.0
							    chance: 0.5
							    zone:
							      x1: 0
							      y1: 0
							      z1: 0
							      x2: 10
							      y2: 10
							      z2: 10
							
							crops_world: "world"
							crops:
							  wheat:
							    zone:
							      x1: 0
							      y1: 0
							      z1: 0
							      x2: 10
							      y2: 10
							      z2: 10
							
							
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
	
	public static FileConfiguration readAndCheck(String filePath, String ...requiredParams) {
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
