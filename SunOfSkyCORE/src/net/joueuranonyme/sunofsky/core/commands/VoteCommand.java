package net.joueuranonyme.sunofsky.core.commands;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import net.joueuranonyme.sunofsky.core.Main;
import net.joueuranonyme.sunofsky.core.addons.YmlManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class VoteCommand implements CommandExecutor {
	private static FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(config == null) init();

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("soscore.vote.reload")) {
				sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§7Rechargement de la configuration ...");
				
				if(init()) {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§aRechargée !");
				} else {
					sender.sendMessage(Main.getPrefix(config.getString("prefix")) + "§cErreur lors du rechargement de la configuration !");
				}
				return true;
			}
		}
		
		TextComponent button = new TextComponent(config.getString("button.text"));
		button.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getString("button.link")));
		
		ComponentBuilder message = new ComponentBuilder();
		
		String text = String.join("\n", config.getStringList("message"));
		String[] array = text.split("\\{BUTTON\\}");
		List<TextComponent> list = new ArrayList<TextComponent>();
		
		for(String e : array) {
			TextComponent t = new TextComponent(e);
			list.add(t);
		}
		
		for(int i = 1; i < list.size(); i++) {
			list.add(i, button);
			i++;
		}
		
		for(TextComponent e : list) {
			message.append(e);
		}
		
		sender.spigot().sendMessage(message.create());
		
		return true;
		
	}

	public static boolean init() {
		try {
			config = YmlManager.readAndCheck("plugins/SOS_Core/vote.yml", new String[] {
					"button", "button.link", "button.text", "message"
			});
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
