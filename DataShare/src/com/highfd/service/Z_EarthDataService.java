package com.highfd.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;

public interface Z_EarthDataService {
	
	/**
	 * 出来数据主方法
	 */
	public void mainData(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath) throws Exception;
	/**
	 * 设置 1HZ/50HZ fileInfo的各项参数
	 */
	public FileInfo setFileInfo(String hzType,int dayNumber,String beforeDayStr,EarthQuake earthQuake,SiteInfo siteInfo) throws Exception;
	
	/**
	 * 从接收机上 下载     1HZ ftp文件
	 */
	public void downT02(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception;
	
	
	/**
	 * 从接收机上 下载     50HZ ftp文件
	 */
	public void down_50HZ_T02(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception;
	
	//入库操作
	public void saveToDB(FileInfo dbFileInfo,FileInfo fileInfo,String year4) throws Exception;
	
	/**
	 ************************地震应急事件   FTP方式回补************************ 
	 */
	public List<String> getEarthQuakeEventList(File dir);
	
		
	//1HZ开始整理
	public void start1Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception;
	
	//50HZ开始整理
	public void start50Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception;
}
