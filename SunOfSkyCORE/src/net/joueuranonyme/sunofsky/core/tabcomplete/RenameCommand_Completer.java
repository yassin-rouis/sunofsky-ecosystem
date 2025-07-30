package net.joueuranonyme.sunofsky.core.tabcomplete;

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

public class RenameCommand_Completer implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		if(args.length == 1) {
			List<String> t = new ArrayList<String>();
			t.add("lore");
			t.add("name");
			return t;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("lore")) {
			List<String> t = new ArrayList<String>();
			t.add("1");
			t.add("2");
			t.add("3");
			t.add("4");
			t.add("5");
			return t;
		}
		
		return null;
	}
}
