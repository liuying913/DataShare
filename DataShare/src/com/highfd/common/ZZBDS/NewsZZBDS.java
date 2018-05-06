package com.highfd.common.ZZBDS;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsZZBDS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String htmlSource="<div class=\"container-fluid\"><div class=\"row-fluid\"><div class=\"span12\"><h3>h3. 这是一套可视化布局系统.</h3></div></div></div>";
		getTitle(htmlSource);
	}

	
	public static void t(){
		String p="<div class=\"container-fluid\"><div class=\"row-fluid\"><div class=\"span12\"><h3>h3. 这是一套可视化布局系统.</h3></div></div></div>";
		String regex = "\\s+<h3>+</h3>";  
		Pattern pattern = Pattern.compile(regex); 
   		Matcher matcher = pattern.matcher(p);  
   		if(matcher.matches()){	
   			String group = matcher.group();
   			System.out.println(group);
   		}else{
   			
   		}
	}
	
	
	
	public static String getTitle(String htmlSource){
		List<String> list = new ArrayList<String>();
		//Pattern pa = Pattern.compile("<title>.*?</title>", Pattern.CANON_EQ);也可以
		Pattern pa = Pattern.compile("<h[0-9].*?</h[0-9]");//源码中标题正则表达式
		Matcher ma = pa.matcher(htmlSource);
		while (ma.find()){
		    list.add(ma.group());//将符合el的字串加入到list中
		    System.out.println(ma.group());
		    String title = ma.group();
		    if(title.indexOf(">")>-1){
		    	title=title.substring(title.indexOf(">")+1, title.length());
		    	System.out.println(title);
		    }
		    if(title.indexOf("</")>-1){
		    	title=title.substring(0, title.indexOf("</"));
		    	System.out.println(title);
		    }
		    return ma.group();
		}
		return "";
	}
	
	

}
