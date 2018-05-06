package com.highfd.common.file;

import com.highfd.controller.Z_DataSupplyController;

public class FileNameAndPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

	
	/**
	 * 设置o文件的名称
	 */
	public static String get_O_Name(String siteNumber,String yearDay,String year2){
		String o_file_Name = siteNumber+yearDay+"0."+year2+"o";
		return o_file_Name;
	}
	
	/**
	 * 设置 整理o文件的 路径+名称
	 */
	public static String get_O_NameAndPath(String yearDay,String year4,String o_file_Name){
		String o_file_WorkPath = Z_DataSupplyController.S30SWorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
		return o_file_WorkPath;
	}
	
	/**
	 * 设置 o文件 通过ftp下载后整理的路径 +名称
	 */
	public static String get_O_Ftp_NameAndPath(String yearDay,String year4,String o_file_Name){
		String o_file_WorkPath = Z_DataSupplyController.S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
		return o_file_WorkPath;
	}
	
	public static String get_S_NameAndPath(String o_file_WorkPath){
		String s_file_WorkPath = o_file_WorkPath.substring(0, o_file_WorkPath.length()-1)+"S";
		return s_file_WorkPath;
		
	}
	
}
