package com.highfd.common.path;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.highfd.common.map.MapAll;

public class GetXMLPath {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "E://jdbc.properties";
		GetXMLPath g = new GetXMLPath();
		g.test();

	}
	
	public void test() throws IOException{
		String dbpath = GetXMLPath.getXMLPath(getClass());
		System.out.println(dbpath);
		System.out.println(GetXMLPath.getProperties(dbpath,"winOrLinux"));//判断是win 还是 linux
	}
	//通过相对路径找到xml
	public static String getXMLPath(Class classs){
		String path = classs.getResource("/").getFile().toString();
		//if(path.indexOf("WEB-INF") > 0){path = path.substring(0, path.indexOf("WEB-INF/classes")); }
		//if(path.indexOf("classes") > 0){path = path.substring(0, path.indexOf("classes")); }
		path += "jdbc.properties";
		return path;
	}
	/*
	//判断是什么系统
	public static String getWindowOrLinux(String path) throws IOException{
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty("winOrLinux").trim();
		return xmlPath;
	}
	
	//文件系统路径
	public static String getFileSystemPath(String path) throws IOException{
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty("fileSystemPath").trim();
		return xmlPath;
	}*/
	
	//获得配置参数
	public static String getProperties(String path,String pName) throws IOException{
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty(pName).trim();
		return xmlPath;
	}

}
