package com.highfd.common.json;

import net.sf.json.JSONArray;

public class Json {
	
	public static String getJsonArrayToStr(JSONArray json){
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}

}
