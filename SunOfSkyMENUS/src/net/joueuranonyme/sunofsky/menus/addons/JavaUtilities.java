package net.joueuranonyme.sunofsky.menus.addons;

import java.util.ArrayList;
import java.util.List;

public class JavaUtilities {
	public static void replaceEach(List<String> list, String regex, String to){
		int i = 0;
		for(String str : list) {
			list.set(i, str.replaceAll(regex, Fixer.fixString(to)));
			i++;
		}
	}
}
