/**
 * @author Mohamed-Yassin ROUIS
 * 
 * Ce script a été écrit par Mohamed-Yassin ROUIS (alias JoueurAnonyme).
 * Toute copie, reproduction, modification, vente, location, partage,
 * ou utilisation outre que dans son contexte initial du code en partie ou en intégralité
 * est strictement interdit.
 * 
 * Mohamed-Yassin ROUIS | 2023 © Tous droits réservés.
 * 
 */


package net.yassinrouis.sunofsky.dimension.libs;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class Log {
	public Logger logger = Bukkit.getLogger();
	public String prefix;
	public boolean parseFromMinecraft = true;
	
	public Log() {
	}
	
	public Log(String prefix) {
		this.prefix = prefix;
	}
	
	private String parseColors(String str) {
		str = str.replaceAll("§0", "\u001B[0;30m");
		str = str.replaceAll("§1", "\u001B[0;34m");
		str = str.replaceAll("§2", "\u001B[0;32m");
		str = str.replaceAll("§3", "\u001B[0;36m");
		str = str.replaceAll("§4", "\u001B[0;31m");
		str = str.replaceAll("§5", "\u001B[0;35m");
		str = str.replaceAll("§6", "\u001B[0;33m");
		str = str.replaceAll("§7", "\u001B[0;37m");
		str = str.replaceAll("§8", "\u001B[0;90m");
		str = str.replaceAll("§9", "\u001B[0;94m");
		str = str.replaceAll("§a", "\u001B[0;92m");
		str = str.replaceAll("§b", "\u001B[0;96m");
		str = str.replaceAll("§c", "\u001B[0;91m");
		str = str.replaceAll("§d", "\u001B[0;95m");
		str = str.replaceAll("§e", "\u001B[0;93m");
		str = str.replaceAll("§f", "\u001B[0;97m");

		str = str.replaceAll("§n", "\u001B[4m");
		str = str.replaceAll("§l", "\u001B[1m");
		str = str.replaceAll("§r", "\u001B[0m");
		return str;
	}
	
	private String parseParams(Object[] objects) {
		String message;
		
		if(prefix == null) {
			message = "";
		} else {
			message = prefix;
		}
		
		if(objects.length == 0) return message;
		
		for(Object object: objects) {
			if(!message.equals("")) message += " ";
			message += object.toString();
		}
		
		if(parseFromMinecraft) {
			return parseColors(message);
		} else {
			return message;
		}
	}
	
	public void log(Object ...objects) {
		this.logger.log(Level.INFO, parseParams(objects));
	}
	
	public void info(Object ...objects) {
		this.logger.log(Level.INFO, parseParams(objects));
	}
	
	public void warning(Object ...objects) {
		this.logger.log(Level.WARNING, parseParams(objects));
	}
	
	public void severe(Object ...objects) {
		this.logger.log(Level.SEVERE, parseParams(objects));
	}
}
