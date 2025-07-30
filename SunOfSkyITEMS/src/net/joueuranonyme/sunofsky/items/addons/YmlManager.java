package net.joueuranonyme.sunofsky.items.addons;

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

import net.joueuranonyme.sunofsky.items.Main;

public class YmlManager {
	public static void init() {
		init("plugins/SOS_Items/how-to.simple.yml");
		init("plugins/SOS_Items/coffre_vente.yml");
		init("plugins/SOS_Items/data/coffre_vente.yml");
	}
	
	public static void init(String fileName) {
		File dir = new File(fileName);
		dir.getParentFile().mkdirs();

		try {
			File file = new File(fileName);

			if(!file.exists()) {
				
				switch(fileName) {
					// ========================= config =========================
					
					case "plugins/SOS_Items/config.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
								prefix: ""
								""");
						pr.close();
					}
					
					// ========================= coffre vente data =========================
					
					case "plugins/SOS_Items/data/coffre_vente.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
								locations: {}
								""");
						pr.close();
					}
					
					// ========================= coffe vente =========================
					
					case "plugins/SOS_Items/coffre_vente.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║           -=≡ ITEMS ≡=-           ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#  ╔═══════════════════════════════════╗
							#  ║    Section : Coffres de vente     ╟╮
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							
							prefix: ""
							
							# Variables disponibles: {MULTIPLICATOR} {REFRESH}
							name: "§e§lCoffre de vente"
							lore:
							  - "§fCe coffre permet de §7vendre§f son"
							  - "§fcontenu §eautomatiquement§f"
							  - "§ftoutes les &e{REFRESH} §f!"
							  - ""
							  - "§fChaque item est vendu §e{MULTIPLICATOR}x §fson prix!"
							
							# Variables disponibles: {PLAYER} {MULTIPLICATOR} {REFRESH} {REMAINING}
							hologram:
							  - ""
							
							# Variables disponibles: {H} {M} {S}
							timeFormat: "§f{H}§7h §f{M}§7m §f{S}§7s"
							
							noLoanPermitted: "§cVous n'êtes pas autorisé à contracter un prêt"
							""");
						pr.close();
					}

					// ========================= How To - Simple =========================
					
					case "plugins/SOS_Items/how-to.simple.yml" -> {
						file.createNewFile();
						PrintWriter pr = new PrintWriter(fileName);
						pr.print("""
							#
							#  ╔═══════════════════════════════════╗
							#  ║      -=≡ ITEMS >> How-To ≡=-      ╟╮
							#  ║   A plugin for the SunOfSkyCORE   ║│
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#  ╔═══════════════════════════════════╗
							#  ║      Section : Items normaux      ╟╮
							#  ╚╤══════════════════════════════════╝│
							#   ╰───────────────────────────────────╯
							#
							# N.B.: Ce fichier n'est nullement consacré à la configuration du plugin.
							#     : Il sert de référence pour la configations d'items.
							#     : Ce fichier n'est pas lu par le plugin.
							
							# =========================== FORME GENERALE ===========================
							# Chaque fichier de customisation, dans le dossier "items",
							# doit contenir les données suivantes :
							
							material: <Objet minecraft>					# Texte
							id: <Identifiant SunOfSky de l'objet>		# Texte
							
							# Les paramètres ci-dessous sont faccultatifs :
							
							name: <Nom de l'item>						# Texte
							lore:										# Liste de texte
							  - <Description de l'item ligne 1>			# .. Texte
							  - <Description de l'item ligne 2>			# .. Texte
							  - <Etc...>								# .. Texte
							types: 
							  - <Type de customisation 1>				# Texte
							  - <Type de customisation 2>				# Texte
							  - <Etc ...>								# Texte
							durability: <Nombre d'utilisations>			# Valeur entière
							right_command: <Commande après CLQ. Droite>	# Texte
							left_command: <Commande après CLQ. Gauche>	# Texte
							enchants:									# Liste de texte
							  - <ID de l'enchantement 1>:<Niveau>		# .. Texte:Entier
							  - <ID de l'enchantement 2>:<Niveau>		# .. Texte:Entier
							  - <Etc ...>								# .. Texte:Entier
							radius: <Rayon>								# Valeur entière
							
							
							# =========================== TYPES ===========================
							# Le [type] est un type parmis les suivants :
							#  OUTILS :
							#   - magnet 			# Attire les items récupérés
							#   - rc_magnet			# Attire les items dans un rayon donné lors d'un clique droit (Rayon configurable, voir la section Exemples, défaut: 16 blocs)
							#   - head_drop			# Drop la tête du joueur
							#   - super_axe			# Coupe un arbre entier en un coup	[Conseillé sur: Hache]
							#   - super_pickaxe		# Mine dans un rayon de 3 blocs		[Conseillé sur: Pioche] (Rayon configurable, voir la section Exemples)
							#   - super_hoe			# Récolte dans un rayon de 3 blocs	[Conseillé sur: Houe] (Rayon configurable, voir la section Exemples)
							#   - super_bucket		# Seau ne se vidant jamais			[Conseillé sur: Seau d'eau, Seau de lave] (Nombre d'utilisations configurable)
							#	- multitool			# Change d'outil selon le bloc miné
							#	- instant_break		# Détruit un bloc instantanément
							#  ARMURES :
							#   - night_vision		# Donne au joueur la vision nocturne		[Conseillé sur: Casques]
							#   - force_i			# Donne au joueur l'effect Force I			
							#   - normal_soul_sand	# Pour marcher normalement sur la soul sand	[Conseillé sur: Jambières, Bottes]
							#   - cushioned_fall	# Amorti la chute du joueur					[Conseillé sur: Bottes]
							#   - fast_swimming		# Le joueur nage plus rapidement			[Conseillé sur: Jambières]
							
							# =========================== VARIABLES ET PLACEHOLDERS ===========================
							# --- Variables ---
							# Les paramètres [name, lore, right_command, left_command] possèdent leur propres variables.
							#
							# Pour le [name] et le [lore], les variables disponibles sont les suivantes:
							#   - {DURABILITY} : Durabilité restante
							#   - {RADIUS} : Rayon de fonctionnement de l'outil
							#
							# Pour le [right_command] et [left_command], les variables sont:
							#   - {PLAYER}
							#   - {WORLD}
							#   - {X}, {Y}, {Z}
							
							# =========================== EXEMPLES DE CONFIGURATION ===========================
							# Pour configurer une "Super Houe" avec un rayon de 5x5x5 blocs, il suffit de faire:
							types:
							  - super_hoe
							radius: 5
							
							# Pour configurer un "Seau infini", et le limiter, vous pouvez utilser la durabilité:
							types:
							  - super_bucket
							durability: 20
							
							
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
