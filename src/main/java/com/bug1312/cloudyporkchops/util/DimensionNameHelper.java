package com.bug1312.cloudyporkchops.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DimensionNameHelper {

	public static String idToName(String id) {
		String reformatted = id.substring(id.indexOf(':') + 1).replaceAll("_", " ");
		
		Pattern pattern = Pattern.compile("(^[a-z]| [a-z])");
		Matcher matcher = pattern.matcher(reformatted);
		
		StringBuffer noKeyName = new StringBuffer();
		while (matcher.find()) matcher.appendReplacement(noKeyName, matcher.group().toUpperCase());
		matcher.appendTail(noKeyName);

		return noKeyName.toString();
	}
	
}
