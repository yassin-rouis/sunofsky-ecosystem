package net.joueuranonyme.sunofsky.items.tabcomplete;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import net.joueuranonyme.sunofsky.items.items.ItemsLoader;

public class GiveCommand_Completer implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
	    List<String> items = new ArrayList<String>();
	    items.addAll(ItemsLoader.loadedItems.keySet());
	    
		if(args.length == 1) {
			Collection<? extends Player> ps = Bukkit.getOnlinePlayers();
			List<String> players = new ArrayList<String>();
			
			for(Player e: ps) {
				players.add(e.getName());
			}
			
			return players;
		} else if (args.length == 2) {
			return items;
		}
		
		return null;
	}
}
