package net.joueuranonyme.sunofsky.menus.tabcomplete;

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

import net.joueuranonyme.sunofsky.menus.commands.ShopCommand;

public class ShopCommand_Completer implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		File dir = new File("plugins/SOS_Menus/shops/");
		dir.mkdirs();
		
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            return !name.startsWith("-") && name.endsWith(".yml");
	        }
	    };
	    
	    String[] unsafeShopsFiles = dir.list(filter);
	    List<String> menus = new ArrayList<String>();

	    menus.addAll(ShopCommand.shops.keySet());
	    
		if(args.length == 1) {
			if(menus.size()==0) {
				menus.add("<Aucun menu trouvé>");
			}
			return menus;
		} else if (args.length == 2) {
			Collection<? extends Player> ps = Bukkit.getOnlinePlayers();
			List<String> players = new ArrayList<String>();
			
			for(Player e: ps) {
				players.add(e.getName());
			}
			
			return players;
		}
		
		return null;
	}
}
