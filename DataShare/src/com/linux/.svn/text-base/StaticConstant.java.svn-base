package com.linux;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;

/**
 * Description: 初始化到内存中的数据类
 * @author wj
 * @version 1.0
 */
public class StaticConstant {
	
	private static Logger logger = Logger.getLogger(StaticConstant.class);
	
	private StaticConstant(){
		
	}
	private static StaticConstant sc = new StaticConstant();
	
	public static StaticConstant getInstance(){
		return sc;
	}
	
	private static ResourceBundle config = ResourceBundle.getBundle("tools");
	
	public String getGravityFilePath(){
		return config.getString("gravityFilePath");
	}
	
	public String getTeqcPath(){
		return config.getString("teqcPath");
	}
	
	public String getJieyasuoPath(){
		return config.getString("jieyasuoPath");
	}
	
	public String getRinexPath(){
		return config.getString("rinexPath");
	}
	
	public String getRinexPathForLinux(){
		return config.getString("rinexPathForLinux");
	}
	
	public String getFileapthOfT02(){
		return config.getString("fileapthOfT02");
	}
	
	public int getDelHisDay(){
		return config.getString("delHisDay")==null ? 5:Integer.parseInt(config.getString("delHisDay"));
	}
	//nas 路径
	public String getNasPath(){
		if(config.getString("nasPath")==null || "".equals(config.getString("nasPath"))){
			logger.error("getNasPath() nas root path is null!");
			return null;
		}
		return config.getString("nasPath");  
	}
}
