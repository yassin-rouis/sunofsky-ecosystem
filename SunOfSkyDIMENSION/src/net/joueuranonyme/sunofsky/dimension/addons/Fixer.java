package net.joueuranonyme.sunofsky.dimension.addons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fixer {
	private static String escapeDollarSign(String value) {
	    Pattern p = Pattern.compile("\\$");
	    int off = 0;
	    while (true) {
	        Matcher m = p.matcher(value.substring(off));
	        if (!m.find()) break;
	        int moff = m.start();
	        String left = value.substring(0, off+moff);
	        String right = value.substring(off+moff+1, value.length());
	        value = left+"\\$"+right;
	        off += moff+1+1;
	    }

	    return value;
	}
	private static String escapeAntiSlashSign(String value) {
	    Pattern p = Pattern.compile("\\\\");
	    int off = 0;
	    while (true) {
	        Matcher m = p.matcher(value.substring(off));
	        if (!m.find()) break;
	        int moff = m.start();
	        String left = value.substring(0, off+moff);
	        String right = value.substring(off+moff+1, value.length());
	        value = left+"\\\\"+right;
	        off += moff+1+1;
	    }

	    return value;
	}
	
	
	public static String fixString(String text) {
		return escapeDollarSign(escapeAntiSlashSign(text));
	}
}
