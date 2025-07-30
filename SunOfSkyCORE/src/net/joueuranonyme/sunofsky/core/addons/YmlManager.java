package net.joueuranonyme.sunofsky.core.addons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.joueuranonyme.sunofsky.core.Main;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YmlManager {
	public static void init() {
		init("plugins/SOS_Core/bienvenue.yml");
		init("plugins/SOS_Core/fly.yml");
		init("plugins/SOS_Core/vote.yml");
		init("plugins/SOS_Core/site.yml");
		init("plugins/SOS_Core/pub.yml");
		init("plugins/SOS_Core/rename.yml");
		init("plugins/SOS_Core/furnace.yml");
		init("plugins/SOS_Core/event_peche.yml");
		init("plugins/SOS_Core/boss_bar.yml");
		init("plugins/SOS_Core/journalier.yml");
		init("plugins/SOS_Core/data/journalier.yml");
		init("plugins/SOS_Core/data/fly.yml");
		init("plugins/SOS_Core/data/changelog.yml");
		init("plugins/SOS_Core/data/boss_bar.yml");
		init("plugins/SOS_Core/data/LISEZ-MOI.txt");
	}
	
	public static void init(String fileName) {
		File dir = new File(fileName);
		dir.getParentFile().mkdirs();
		
		try {
			File file = new File(fileName);
			
			if (!file.exists()) {
				PrintWriter pr;
				String str;
				switch (fileName) {
					case "plugins/SOS_Core/rename.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║            Rename             ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								prefix: "§f§lSun§7§lOf§e§lSky §eRenamer §8> §f"
								
								renamedItem: "§aL'item a été renommé"
								reloredItem: "§aL'item a été reloré"
								
								noItemInHand: "§cVous devez avoir un item en main pour utiliser cette commande"
								
								""");
						pr.close();
					}
					case "plugins/SOS_Core/data/LISEZ-MOI.txt" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								CE DOSSIER CONTIENT DES DONNEES NECESSAIRES AU BON
								FONCTIONNEMENT DU PLUGIN.
								
								A moins que vous savez ce que vous faites, merci de
								ne pas toucher aux fichiers/dossiers présents dans
								ce répertoire.
								
								---
								
								Un plugin créé par JoueurAnonyme, SunOfSky (c) 2022
								
								""");
						pr.close();
					}
					case "plugins/SOS_Core/site.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║             Site              ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								button:
								  link: "https://www.google.com/"
								  text: "Cliquez ici"
								
								# Utilisez {BUTTON} pour placer le bouton
								message :
								  - "§8-----§7=====§f[ §6§lSite §f]§7=====§8-----"
								  - ""
								  - " Visitez notre site en cliquant ici : "
								  - "					{BUTTON}"
								  - ""
								  - "§8-----§7=====§f[ §6§lSite §f]§7=====§8-----"
								""");
						pr.close();
					}
					case "plugins/SOS_Core/fly.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║              Fly              ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								# ========== Permissions ==========
								#
								# soscore.fly.reload
								#		 - Permission de recharger la configuration du fly
								# soscore.fly.toggle.self
								#		 - Changer sa propre possibilité de fly
								# soscore.fly.toggle.other
								#		 - Changer la possibilité de fly des autres
								#
								# soscore.fly.timer
								#		 - Permission d'ajouter/modifier/supprimer du temps de fly
								#
								# =================================
								
								# ========== Messages ==========
	
								prefix: "§fFly§lMachine §7> §r"
	
								playersOnly: "§cCette commande ne peut être lancée que par un joueur"
								playerNotFound: "§cLe joueur est introuvable"
								playerOffline: "§cLe joueur n'est pas en ligne"
								
								syntaxError: "§cVérifiez votre syntaxe"
								timeSpecification: "§cPour spécifier un temps, entrez un nombre suivi de h/m/s (ex. /flytimer notch 10m)"
								noPermission: "§cVous n'avez pas la permission d'utiliser cette commande."
								numberUnder100: "§cChoisissez un nombre en dessous de 100%"
								
								flyLimitEnded: "§6Votre temps de vol est écoulé !"
								
								# Variables disponibles : {TIME} {PLAYER}
								allowTempFly: "§a{PLAYER} §fest autorisé à voler pendant §a{TIME}§f."
								# Variables disponibles : {TIME}
								allowTempFlyTarget: "Vous êtes autorisé à voler pendant §a{TIME}§f."
								
								# Variables disponibles : {PLAYER}
								flyToggledOFF: "§fLe fly de §c{PLAYER} §fest §cdésactivé"
								flyToggledON: "§fLe fly de §a{PLAYER} §fest §aactivé"
								
								flyToggledOFFTarget: "§fVotre fly est §cdésactivé"
								flyToggledONTarget: "§fVotre fly est §aactivé"
								
								# Variables disponibles : {SPEED}% {PLAYER}
								flySpeed: "§fLa vitesse de vol de §a{PLAYER} §fest mise à §a{SPEED}%."
								# Variables disponibles : {SPEED}%
								flySpeedTarget: "§fVotre vitesse de vol est mise à §a{SPEED}%."
								
								# Message affiché au dessus de la barre d'inventaire.
								# Variables disponibles : {TIME}
								flyTimer: "§6Temps de vol restant : {TIME}"
								flyTimerWarning: "§cTemps de vol restant : {TIME} !"
								
								
								# Variables disponibles : {PLAYER}
								flyCleared: "§cLe temps de fly restant de {PLAYER}§c a été retiré !"
								flyClearedTarget: "§cVotre temps de fly restant vous a été retiré !"
								
								# ========== Placeholder [ %sos.flytimer% ] ==========
								
								placeholder:
								  disabled: "§cFly désactivé"
								  enabled: "§aFly activé"
								  timer:
								    left: "§fTemps restant: "
								    seconds: "§f{s}§7s"	# {s}
								    minutes: "§f{m}§7m " # {m}
								    hours: "§f{h}§7h "	 # {h}
								
								
								""");
						pr.close();
					}
					case "plugins/SOS_Core/data/journalier.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								date: "1970-01-01"
								players: []
								""");
						pr.close();
					}
					case "plugins/SOS_Core/data/fly.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								players: {}
								""");
						pr.close();
					}
					case "plugins/SOS_Core/data/changelog.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								version: "Ancienne version inconnue"
								showChangeLogs: true
								""");
						pr.close();
					}
					case "plugins/SOS_Core/bienvenue.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║	    Messages de Bienvenue     ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								
								# Utilisez {PLAYER} pour mentionner le nouveau joueur
								# Pour ajouter le signe ", faites \\"
								messages:
								  - "Bienvenue {PLAYER} !"
								  - "Bienvenue sur SunOfSky {PLAYER} !"
								  - "Coucou {PLAYER}, bienvenue !"
								
								prefix: "§fSun§eOf§6Welcomes §7> §r"
								
								# Si aucun nouveau joueur n'a rejoint le serveur depuis 5 minutes :
								noNewPlayerMessage: "§cAucun utilisateur n'a rejoint le serveur ces 5 dernières minutes"
								""");
						pr.close();
					}
					case "plugins/SOS_Core/pub.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║              Pub              ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								# ========== Permissions ==========
								# soscore.pub.reload
								# 	- Permission de recharger la configuration des pubs.
								# soscore.pub.use
								# 	- Permission d'utiliser la "/pub".
								# soscore.pub.unlimited_use
								#	- Permission d'utiliser la "/pub" sans prise en compte des délais
								# soscore.pub.cancel_others
								#	- Permission d'annuler les délais d'attente des joueurs.
								#
								# =================================
								
								prefix: "§ePub §7> §r"
								# Variables disponibles : {PUB}
								message: "§e§lSun§7§lOf§f§lSky §6Pub » {PUB}"
								# Délai entre chaques pubs en secondes
								delay: 300
								
								# ========== Messages =========
								
								noPermission: "§cVous n'avez pas la permission de faire celà."
								playerNotFound: "§cLe joueur est introuvable."
								noPlayerDelay: "§cCe joueur n'a aucun délai pour l'instant."
								
								# Variables disponibles : {TIME}
								waitForDelay: "§cVotre prochaine pub est disponible dans {TIME}."
								
								# Variables disponilbes: {PLAYER}
								delayCancelled: "§6Le délai d'attente de {PLAYER} a été annulé."
								delayCancelledTarget: "§6Votre délai d'attente a été annulé."
								
								""");
						pr.close();
					}
					case "plugins/SOS_Core/furnace.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# ╔═══════════════════════════════╗
								# ║        Fours (Furnace)        ╟╮
								# ╚╤══════════════════════════════╝│
								#  ╰───────────────────────────────╯
								#
								
								prefix: "§eB§6U§cR§4N§8E§lR §7> §r"
								
								# Types de prix :
								# par "item" (défaut) - La cuisson de chaque item coute <price>
								# par "slot" - La cuisson de chaque slot (indépendant du nombre dans le slot (sauf 0/slot)) coute <price>
								# par "use" - L'utilisation de la commande coute <price> (sauf si rien ne peut être cuit dans l'inventaire
								price: 50
								per: "item"
								
								cooldown: 0
								
								noEnoughMoney: "§cVous n'avez pas les fonds suffisants pour effectuer cette action !"
								noLoanPermitted: "§cVous n'êtes pas autorisé à contracter un prêt pour faire cette action !"
								
								# Variables disponibles : {NB_ITEMS}
								useMessage: "§6{NB_ITEMS} §aitems ont été cuits, et §c- 50$§a ont été retirés de votre compte. §7(50$/utilisation)"
								
								# Variables disponibles : {NB_ITEMS} {PRICE}
								handMessage: "§6{NB_ITEMS} §aitems ont été cuits, et §c- {PRICE}$§a ont été retirés de votre compte. §7(50$/item)"
								# Variables disponibles : {NB_SLOTS} {PRICE}
								allSlotsMessage: "§6{NB_SLOTS} §aslots ont été cuits, et §c- {PRICE}$§a ont été retirés de votre compte. §7(50$/slot)"
								# Variables disponibles : {NB_ITEMS} {PRICE}
								allItemsMessage: "§6{NB_ITEMS} §aitems ont été cuits, et §c- {PRICE}$§a ont été retirés de votre compte. §7(50$/slot)"
								""");
						pr.close();
					}
					case "plugins/SOS_Core/data/boss_bar.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								disabled_players: []
								""");
						pr.close();
					}
					case "plugins/SOS_Core/vote.yml" -> { 
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# =================================
								# |             Vote              |
								# =================================
								#
								
								button:
								  link: "https://www.google.com/"
								  text: "Cliquez ici"
								
								# Utilisez {BUTTON} pour placer le bouton
								message :
								  - "§8-----§7=====§f[ §6§lVote §f]§7=====§8-----"
								  - ""
								  - " Votez maintenant et gagnez des récompenses en cliquant ici : "
								  - "					{BUTTON}"
								  - ""
								  - "§8-----§7=====§f[ §6§lVote §f]§7=====§8-----"
								""");
						pr.close();
					}
					case "plugins/SOS_Core/journalier.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# =================================
								# |       Cadeau Journalier       |
								# =================================
								#
								
								
								prefix: "§c§lCadeau §f§lJournalier §7> §r"
								title: "§c§lCadeau §f§lJournalier"
								
								prize:
								  id: "tripwire"
								  name: "Titre"
								  lore:
								    - "Ligne 1"
								  enchant: true
								
								noPrize:
								  id: "barrier"
								  name: "§cAucun prix pour le moment."
								  lore:
								    - "Revenez demain !"
								  enchant: true
								
								quitButton:
								  name: "§cQuitter"
								  lore: []
								
								noPrizeMessage: "§cVous avez déjà récupérer votre récompense, revenez demain !"
								prizeWinned: "§aVous avez reçu votre clef journalière."
								command: "give {PLAYER} paper 1"
								""");
						pr.close();
					}
					case "plugins/SOS_Core/event_peche.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# =================================
								# |          Event Pêche          |
								# =================================
								#
								
								# ========== Permissions ==========
								#
								# soscore.peche
								#		 - Permission de lancer/arrêter l'event via "/peche".
								# soscore.peche.reload
								#		 - Permission de recharger la configuration de l'event.
								#
								# =================================
								
								
								
								# ========== Tuto Pêche ==========
								#
								# Dans la section "prizes", vous pouvez créer autant d'objets que vous voulez.
								# Chaque prix doit avoir un identifiant UNIQUE, en alphanumérique.
								# Dans ce prix, il y a deux paramètres : la commande (executée lors du gain),
								# et la chance. La chance doit être un nombre supérieur ou égal à 0.
								# Plus la chance est importante, plus le joueur aura de chance de gagner ce prix.
								# Vous pouvez choisir d'annoncer le gain d'un prix avec le paramètre "announce".
								#
								# !!! Si le format n'est pas respecté, le joueur ne recevra AUCUNE récompense à la place
								# !!! de la configuration défaillante.
								#
								# === Format:
								#
								# prizes:
								#	 <identifiant>:
								#		 chance: <chance>
								#		 command: "/<command>"
								#		 name: "§eQuoi ? Feur ..."
								#		 announce: false
								#
								# ================================
								
								prefix: "§6§lE§f§lv§6§le§f§ln§6§lt§f§l_§6§lP§f§lê§6§lc§f§lh§6§le	§7> §r"
								
								# ========== Paramètres de l'event ==========
								
								# Variables disponibles : {PLAYER}
								prizes:
								  objet1:
								    chance: 1
								    command: "give {PLAYER} diamond 5"
								    name: "§bPack de diamand"
								    announce: true
								  objet2:
								    chance: 10
								    command: "give {PLAYER} iron_ingot 10"
								    name: "§7Pack de fer"
								    announce: false
								
								# Durée de l'évent en secondes.
								duration: 600
								# Nombre minimum de joueurs.
								minPlayers: 5
								
								# Position d'un côté du pavé où l'eau est considérée comme faisant parti de l'event.
								# !!! Chaque valeur des X, Y, Z, du pos1 doivent être INFERIEURs au pos2.
								# !!! Si ce n'est pas le cas, permuttez tout simplement les valeurs.
								pos1:
								  X: 0
								  Y: 0
								  Z: 0
								# Position du côté opposé au côté "pos1".
								pos2:
								  X: 0
								  Y: 0
								  Z: 0
								
								# Monde dans lequel se déroule l'event.
								world: "world"
								
								# ========== Messages ==========
								
								noEnoughPlayers: "§cL'event pêche a été annulé pour manque de joueurs!"
								eventStarted: "§fL'event pêche a §6§oc§f§oo§6§om§f§om§6§oe§f§on§6§oc§f§oé §f!"
								eventEnded: "§fL'event pêche a vient de se terminer !"
								eventCancelled: "§cL'event pêche a été arrêté par un membre du staff :( ."
								
								# Variables disponibles : {PRIZE}
								winnedPrize: "§6Vous venez de gagner un(e) §f{PRIZE}§6 !"
								# Variables disponibles : {PLAYER} {PRIZE}
								broadcastWinnedPrize: "§f{PLAYER} §6vient de gagner un(e) §f{PRIZE}§6 !"
								
								alreadyRunned: "§cL'event est déjà lancé !"
								alreadyCancelled: "§cL'event est déjà arrêté !"
								noPermission: "§cVous n'avez pas la permission d'utiliser cette commande."
								""");
						pr.close();
					}
					case "plugins/SOS_Core/boss_bar.yml" -> {
						file.createNewFile();
						pr = new PrintWriter(fileName);
						pr.print("""
								#
								# =================================
								# |           Boss Bar            |
								# =================================
								#
								
								
								messages:
								  - \"§fCeci est le §epremier §fmessage\"
								  - \"§fCeci est le §6second §fmessage\"
								  - \"§fCeci est le §cTROISIEME §fmessage\"
								
								# Délai entre chaque affiches en secondes (entier)
								delay: 15
								
								# === Styles ===
								# 1: Solide				 [																			 ]
								# 2: Segmenté x6		[			|		 |			|			|		 |			]
								# 3: Segmenté x10	 [	 |	 |	 |	 |	 |	 |	 |	 |	 |	 ]
								# 4: Segmenté x12	 [	 |	|	|	|	 |	|	|	 |	|	|	|	 ]
								# 5: Segmenté x20	 [ | | | | | | | | | | | | | | | | | | | ]
								
								style: 1
								
								# === Couleurs ===
								# 1: Blanc			2: Rouge
								# 3: Jaune			4: Vert
								# 5: Bleu			 6: Violet
								# 7: Rose
								
								color: 3
								
								reverse: true
								
								prefix: \"§e§lBossBar §7> §f\"
								
								alreadyDisabled: \"§cVotre BossBar est déjà désactivée\"
								alreadyEnabled: \"§cVotre BossBar est déjà activée\"
								enabled: \"§aVotre BossBar a été §2activée§a.\"
								disabled: \"§aVotre BossBar a été §cdésactivée§a..\"
								
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
			System.out.println(String.valueOf(Main.getPrefix()) + Color.RED + "Impossible de créer un fichier de configuration.");
			throw new Error(String.valueOf(Main.getPrefix()) + "Erreur lors de l'écriture d'un fichier : Manque de permissions ? (" + fileName + ")", e);
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