package net.joueuranonyme.sunofsky.dimension.addons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.joueuranonyme.sunofsky.dimension.Main;

public class YmlManager {
	public static void init() {
		init("plugins/SOS_Dimension/dimension.yml");
		init("plugins/SOS_Dimension/boss_bar.yml");
		init("plugins/SOS_Dimension/mobs.yml");
	}
	
	public static void init(String fileName) {
		File dir = new File(fileName);
		dir.getParentFile().mkdirs();

		try {
			File file = new File(fileName);

			if(!file.exists()) {
				switch(fileName) {

					// ========================= Dimension =========================
					
					case "plugins/SOS_Dimension/dimension.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║         -=≡ DIMENSION ≡=-         ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							prefix: "§6§lDimension §8> §r"
							
							# ========== Dimension ==========
							
							# Durée en secondes.
							duration: 900
							
							# Nom du monde dans lequel a lieu l'event
							world: "dimension"
							
							# Commande à exécuter pour forcer un joueur à quitter la dimension (au cas ou le retour automatique n'a pas fonctionné)
							forceKickCommand: "spawn {PLAYER}"
							
							noPermission: "§cVous n'avez pas la permission d'exécuter cette commande"
							
							closed: "§cLa dimension est fermée ! Elle s'ouvre chaque jours à §6...§c !"
							teleport: "§aTéléportation à la dimension ..."
							
							# Messages de la dimension
							# Variables disponibles : {TIME}
							dimensionOpened: "§eLa §6§ldimension§e vient de s'§aouvrir§e pour §6{TIME}§e ! Faites \\"§6/dimension§e\\" pour vous y rendre !"
							dimensionClosed: "§eLa §6§ldimension§e vient de se §cfermer§e! Revenez demain à §6..h§e pour obtenir de nouveaux loots !"
							dimensionClosedByAdmin: "La §6§ldimension§e a été §cfermée§e par un administrateur ! Revenez demain à §6..h§e pour obtenir de nouveaux loots !"
							
							# Variables disponibles : {TIME_ADDED} {TIME}
							timeAdded: " §6{TIME_ADDED}§e ont été ajoutées au temps de la §6§ldimension§e ! Il reste maintenant §6{TIME}§e!"
							
							# Message lorsqu'un joueur ouvre la dimension
							# Variables disponibles : {TIME} {PLAYER}
							dimensionOpenedByPlayer: "§6{PLAYER}§e a ouvert la §6§ldimension§e pour §6{TIME}§e ! Faites \\"§6/dimension§e\\" pour vous y rendre !"
							
							# Message lorsqu'un joueur ajoute du temps à la dimension
							# Variables disponibles : {TIME_ADDED} {PLAYER} {TIME}
							timeAddedByPlayer: "§6{PLAYER}§e a ajouté §6{TIME_ADDED}§e au temps de la §6§ldimension§e ! Il reste maintenant §6{TIME}§e !"
							
							teleportedToLastLocation: "§aVous avez été téléporté à votre dernier emplacement."
							teleportedToForcedLocation: "§6Impossible de vous téléporter à votre dernier emplacement. §aVous avez été téléporté au spawn."
							
							alreadyOpened: "§cLa dimension est déjà ouverte, utilisez \\"/dimension add ...\\" à la place"
							
							""");
						pr.close();
					}

					// ========================= Dimension =========================
					
					case "plugins/SOS_Dimension/mobs.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║ -=≡ DIMENSION > Mobs Spawning ≡=- ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							# ===== Tuto : Mobs Spawning =====
							#
							# Ici vous pouvez personnaliser les loots de chaques mobs.
							# Tous les mobs vanilla sont pris en compte.
							#
							# Pour ajouter un loot à un mob, il suffit de faire :
							# =====
							# mobs:
							#   <ID du mob>:
							#     full: false
							#     mobName: "<nom du mob personnalisé>" # Facultatif
							#     loots:
							#       <nom du loot>:
							#         chance: <chance d'apparition>
							#         command: "<commande>" # Accepte la variable : {PLAYER}
							#         message: "§aEt §eBAMMMM§a un cadeau de dingue
							#         announce: "§aIl vient de faire péter le gros lot ! Qui ça ? C'est {PLAYER} !"
							# =====
							# <ID du mob> ..........: identifiant vanilla du mob. 
							# <Nom du loot> ........: Nom quelconque, UNIQUE que vous pouvez attribuer à votre loot.
							# <Chance d'apparition> : nombre de préférence entre 0.0 et 100.0, indiquant la chance d'apparition d'un loot.
							# <Commande> ...........: est la commande que le mob executera selon la <chance>. La commande ne doit pas commencer par "/". Accepte la variable {PLAYER}.
							# 
							# [full] ....: Permet de désigner si le joueur doit AU MOINS gagner un des loots personnalisés (true) ou peut ne rien recevoir (false).
							# [mobName] .: Si présent, changera le nom des mobs qui possèdent un loot personnalisé.
							# [message] .: Si présent, envoye un message lors de la réception du gain au joueur.
							# [announce] : Si présent, envoye un message lors de la réception du gain d'un joueur à tout le monde. Accepte la variable {PLAYER}.
							#
							# ================================
							
							prefix: "§6§lDimension §ePrizes §7> §r"
							
							mobs:
							  pig:
							    full: false
							    mobName: "§eCochon §lTirelire"
							    loots:
							      petit_cochon_tirelire:
							        chance: 10.0
							        command: "economy give {PLAYER} 50"
							        message: "§eOuw de petites économies... 50$ !"
							      moyen_cochon_tirelire:
							        chance: 3.0
							        command: "economy give {PLAYER} 100"
							        message: "§eVoilà un joli morceau! 100$!"
							      grand_cochon_tirelire:
							        chance: 1.0
							        command: "economy give {PLAYER} 200"
							        message: "§eJe suis §l$ RICHHH $§e! 200$!"
							        announce: "§6{PLAYER}§e a tué un \\"§eCochon §lTirelire§e\\" et obtenu 200$ !"
							
							""");
						pr.close();
					}

					// ========================= Boss BAR =========================
					
					case "plugins/SOS_Dimension/boss_bar.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║   -=≡ DIMENSION >> Boss Bar ≡=-   ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							# Variables disponibles : {TIME}
							message: "La §6§lDimension§r est ouverte ({TIME})"
							
							# === Styles ===
							# 1: Solide         [                                       ]
							# 2: Segmenté x6    [      |     |      |      |     |      ]
							# 3: Segmenté x10   [   |   |   |   |   |   |   |   |   |   ]
							# 4: Segmenté x12   [   |  |  |  |   |  |  |   |  |  |  |   ]
							# 5: Segmenté x20   [ | | | | | | | | | | | | | | | | | | | ]
							
							style: 1
							
							# === Couleurs ===
							# 1: Blanc      2: Rouge
							# 3: Jaune      4: Vert
							# 5: Bleu       6: Violet
							# 7: Rose
							
							color: 3
							
							
							
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
