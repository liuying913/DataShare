package com.highfd.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.FTP.Ftp;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.common.latLon.LocationUtils;
import com.highfd.common.time.TimeUtils;
import com.highfd.controller.Data.earthQuake.EarthQuakeDataReductionController;
import com.highfd.dao.ApplyFileDAO;
import com.highfd.dao.ManagerDAO;
import com.highfd.dao.SiteStationDAO;
import com.highfd.service.HighFDService;
import com.highfd.service.ManagerService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;
import com.highfd.service.Z_EarthDataService;
import com.linux.StaticUtil;

@Service
public class Z_EarthDataServiceImpl implements Z_EarthDataService {
	
	@Autowired
	SiteStationDAO siteDao;
	@Autowired
	ApplyFileDAO applyDao;
	@Autowired
	ManagerDAO managerDao;
	@Autowired
	ManagerService managerService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	UserService userService;
	@Autowired
	HighFDService highFDService;
	
	/**
	 * 主方法
	 */
	public void mainData(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath) throws Exception{
		String hzType = earthQuake.getHz1Or50();
		if(null==hzType || "".endsWith(hzType)){
			start1Hz( earthQuake, siteInfo, startDate, filePath);
			start50Hz( earthQuake, siteInfo, startDate, filePath);
		}else{
			if(hzType.indexOf("1")>-1){
				start1Hz( earthQuake, siteInfo, startDate, filePath);
			}
			if(null!=hzType && hzType.indexOf("50")>-1){
				start50Hz( earthQuake, siteInfo, startDate, filePath);
			}
		}
	}
	
	//1HZ开始整理
	public void start1Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH,-2);
		for(int i=1;i<=3;i++){
			//前一天
		    cal.add(Calendar.DAY_OF_MONTH,1);
		    Date dateStrings = cal.getTime();
		    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据开始执行 1Hz  第"+i+"天："+dFormat.format(dateStrings));
			FileInfo fileInfo = setFileInfo("1",i, dFormat.format(dateStrings), earthQuake, siteInfo);
			downT02( fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
		}
	}
	
	//50HZ开始整理
	public void start50Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
	    Date dateStrings = cal.getTime();
	    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据开始执行 50Hz "+dFormat.format(dateStrings));
	    FileInfo fileInfo = setFileInfo("50",1, dFormat.format(dateStrings), earthQuake, siteInfo);
	    down_50HZ_T02(fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
	}
	
	
	
	/**
	 * 设置 1HZ fileInfo的各项参数
	 * hzType  1 /  50  （1或者50赫兹的分类）
	 * dayNumber 整理的第几天
	 */
	public FileInfo setFileInfo(String hzType,int dayNumber,String beforeDayStr,EarthQuake earthQuake,SiteInfo siteInfo) throws Exception{
		
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String yearDay =StaticUtil.getThreeLengthCountDays(StaticUtil.getCountDaysByNYR(beforeDayStr));//年积日
		String siteNumber = siteInfo.getSiteNumber();
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileYear(year4);
		fileInfo.setSiteNumber(siteNumber);
		fileInfo.setFileDayYear(yearDay);
		fileInfo.setEarthQuakeId(earthQuake.getId());//地震id
		fileInfo.setEarthQuake50Or1(hzType);//1Hz 还是 50Hz
		fileInfo.setEarthQuakeDay(dayNumber);//50Hz 第一、二、三天
		
		fileInfo.setSystemStr(beforeDayStr.substring(0, 10));
		fileInfo.setFileFlag(0);
		fileInfo.setType("4");
		return fileInfo;
	}
	
	/**
	 * 从接收机上 下载  1HZ ftp文件
	 */
	public void downT02(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception{
		
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String month = beforeDayStr.substring(5, 7);//月
		String day = beforeDayStr.substring(8, 10);//日
		
		List<String> fileNameList = new ArrayList<String>();//文件名列表
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		List<String> remotePathList = new ArrayList<String>();
		remotePathList.add("Internal/1hz/"+year4+"/"+month+"/"+day+"/");
		remotePathList.add("Internal/1Hz/"+year4+"/"+month+"/"+day+"/");
		remotePathList.add("Internal/1hZ/"+year4+"/"+month+"/"+day+"/");
		remotePathList.add("Internal/1HZ/"+year4+"/"+month+"/"+day+"/");
		
		
		String siteNumber = siteInfo.getSiteNumber();
		String url = siteInfo.getAddress();
		
		String to2_final_AllPath = filePath+"/1Hz/"+siteNumber+"/"+day;
	    new CopyFileUtil().createLinuxFileDictory2(to2_final_AllPath);//非静态方法

		//1赫兹  24小时，每小时4个文件
		int num_50hz = 1;
		for(int i = (int)'A'; i <= (int)'X'; ++i){
            char hours = (char)(i + 32);    
            for(int j=0;j<4;j++){
            	
            	String minutes = "";
            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+hours+minutes+"B"+".T02";
     			
     			fileInfo.setFileName(fileName);
    			fileInfo.setFilePath(to2_final_AllPath);
    			fileInfo.setFileSize(0d);
    			
    			//查询数据库中是否存在  数据是否存在
    			fileInfo.setEarthQuakeNum(num_50hz);
     	    	FileInfo dbFileInfo = managerDao.queryEarthQuakeOneDetail(year4, fileInfo);
     	    	fileInfo.setEarthQuakeNum(num_50hz++);
     	    	
     	    	if(null!=dbFileInfo){
     	    		if(dbFileInfo.getFileFlag()==1 || dbFileInfo.getFileFlag()==2){
     	    			continue;
     	    		}
     	    	}
     	    	FileInfo newFile = (FileInfo) fileInfo.clone();//克隆对象
     	    	fileList.add(newFile);//全部的文件
     	    	fileNameList.add(fileName.toLowerCase());
            }
        }
		Ftp ftp = new Ftp();
		ftp.oneLogindownFile(url, "", "", remotePathList, fileNameList, to2_final_AllPath);
		insertDB(fileList, year4, to2_final_AllPath);
	}
	
	
	/**
	 * 从接收机上 下载     50HZ ftp文件
	 */
	public void down_50HZ_T02(FileInfo fileInfo,SiteInfo siteInfo,String dateStrings,String filePath) throws Exception{
		
		int hour = Integer.valueOf(dateStrings.substring(11, 13));
        
		//未实行的功能
		if(hour==0){
			//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,0,5);
			//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,1,9);
			//前一天  
			String beforeDay = TimeUtils.getValidityTime(dateStrings, -1);
			String yearDay =StaticUtil.getThreeLengthCountDays(StaticUtil.getCountDaysByNYR(beforeDay));//年积日
			fileInfo.setFileDayYear(yearDay);
			checkHour( fileInfo, siteInfo, beforeDay, filePath,23,1);
        }else if(hour==23){
        	//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,hour-1,1);
			//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,hour,5);
			//后一天
			String nextDay = TimeUtils.getValidityTime(dateStrings, +1);
			String yearDay =StaticUtil.getThreeLengthCountDays(StaticUtil.getCountDaysByNYR(nextDay));//年积日
			fileInfo.setFileDayYear(yearDay);
			checkHour( fileInfo, siteInfo, nextDay, filePath,0,9);
        }else{
        	//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,hour-1,1);
			//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,hour,5);
			//当天
			checkHour( fileInfo, siteInfo, dateStrings, filePath,hour+1,9);
        }
	}
	
	public void checkHour(FileInfo fileInfo,SiteInfo siteInfo,String dateStrings,String filePath,int hour,int num){
		try {
			//压缩成d文件，并压缩成d.Z文件
			String year4 = StaticUtil.getYearByNYR(dateStrings)+"";//年
			String month = dateStrings.substring(5, 7);//月
			String day = dateStrings.substring(8, 10);//日
			
			String siteNumber = siteInfo.getSiteNumber();
			String url = siteInfo.getAddress();
			
			List<FileInfo> fileList = new ArrayList<FileInfo>();
		    List<String> fileNameList = new ArrayList<String>();//文件名列表
			List<String> remotePathList = new ArrayList<String>();
			remotePathList.add("Internal/50hz/"+year4+"/"+month+"/"+day+"/");
			remotePathList.add("Internal/50Hz/"+year4+"/"+month+"/"+day+"/");
			remotePathList.add("Internal/50hZ/"+year4+"/"+month+"/"+day+"/");
			remotePathList.add("Internal/50HZ/"+year4+"/"+month+"/"+day+"/");
			
			
			String to2_final_AllPath = filePath+"/50Hz/"+siteNumber+"/"+day;
		    CopyFileUtil.createLinuxFileDictory(to2_final_AllPath);
	 		
			//50赫兹  前后三个小时，每小时4个文件
	        for(int i = (hour+97); i <= (int)(hour+97); ++i){
	            char lowercase = (char)(i);    
	            for(int j=0;j<4;j++){
	            	String minutes = "";
	            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
	            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+lowercase+minutes+"C"+".T02";
	     			
	     			fileInfo.setFileName(fileName);
	    			fileInfo.setFilePath(to2_final_AllPath);
	    			fileInfo.setFileSize(0d);
	    			
	    			//查询数据库中是否存在  数据是否存在
	    			fileInfo.setEarthQuakeNum(num);
					
					FileInfo dbFileInfo = managerDao.queryEarthQuakeOneDetail(year4, fileInfo);
					
	     	    	fileInfo.setEarthQuakeNum(num++); 
	     	    	if(null!=dbFileInfo){
	     	    		if(dbFileInfo.getFileFlag()==1 || dbFileInfo.getFileFlag()==2){
	     	    			continue;
	     	    		}
	     	    	}
	     	    	FileInfo newFile = (FileInfo) fileInfo.clone();//克隆对象
	     	    	fileList.add(newFile);//全部的文件
	     	    	fileNameList.add(fileName.toLowerCase());
	            }
	        }
	        Ftp ftp = new Ftp();
	        ftp.oneLogindownFile(url, "", "", remotePathList, fileNameList, to2_final_AllPath);
	        insertDB(fileList, year4, to2_final_AllPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//数据整理  数据入库操作111
	public void insertDB(List<FileInfo> fileList,String year4,String to2_final_AllPath){
		List<FileInfo> insertList = new ArrayList<FileInfo>();
		try {
		    for(FileInfo info:fileList){
				FileInfo dbFileInfo = managerDao.queryEarthQuakeOneDetail(year4, info);
		    	File[] childFiles = new File(to2_final_AllPath).listFiles();
		    	boolean flag = false;
				for(int f=0;f<childFiles.length;f++){
					if(childFiles[f].getName().toLowerCase().equals(info.getFileName().toLowerCase())){
						info.setFileName(childFiles[f].getName());
						info.setFileSize(childFiles[f].length()/1024.00);
						if(null==dbFileInfo){
							info.setFileFlag(1);//第一次下载就有
							insertList.add(info);//入库
						}else{
							info.setFileFlag(2);//回补的
							info.setId(dbFileInfo.getId());
							managerDao.updateEarthQuakeOneDetail(year4, info);
						}
						flag=true;
						break;
					}
				}
				if(!flag){//如果ftp没有下载下来
					info.setFileFlag(5);
					if(null==dbFileInfo){
						insertList.add(info);//入库
					}else{
						info.setId(dbFileInfo.getId());
						managerDao.updateEarthQuakeOneDetail(year4, info);
					}
				}
		    }
		    applyDao.insert_EarhtData_Files(year4, insertList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ftp整理  数据入库操作
	public void saveToDB(FileInfo dbFileInfo,FileInfo fileInfo,String year4) throws Exception{
		if(null==dbFileInfo){
			List<FileInfo> list = new ArrayList<FileInfo>();
			list.add(fileInfo);
			applyDao.insert_EarhtData_Files(year4, list);
		}else{
			/*if(fileInfo.getFileFlag()==1){
				fileInfo.setFileFlag(1);
			}*/
			fileInfo.setId(dbFileInfo.getId());
			managerDao.updateEarthQuakeOneDetail(year4, fileInfo);
		}
	}
	
	public static void main(String[] args) {
        int hour=0;
        for(int i = (hour+97); i <= (int)(hour+97); ++i){
            char lowercase = (char)(i);
            System.out.println(lowercase);
        }
	}
	/**************************************************
	 * *********通过FTP文件夹中的文件  整理地震应急事件************
	 **************************************************
	 */
	//遍历、解析所有的地震应急事件 列表
	public List<String> getEarthQuakeEventList(File dir){
		File[] fs = dir.listFiles();
		if(null==fs){return null;}
		List<String> list = new ArrayList<String>();
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					getEarthQuakeEventList(fs[i]);
					File[] fileArray = fs[i].listFiles();
					for(int f=0;f<fileArray.length;f++){
						if(fileArray[f].getName().toLowerCase().equals("1hz")){
							if( CopyFileUtil.getDistinctEarthQuakeList(fs[i].getPath()) ){//存在 1hz 50hz 和 .txt文件
								System.err.println("  开始整理地震应急事件："+fs[i].getPath());
								histroyDataClean(fs[i].getPath());
							}
						}
					}
				}catch(Exception e){}
			}
		}
		return list;
	}
	
	//t02文件复制到 最终的NAS盘里面去  并  筛选台站进行RTDB的入库操作
	public void histroyDataClean(String earthQuakeEventPath) throws Exception{

		String txtPath = CopyFileUtil.getChildTxtFile(earthQuakeEventPath);//文本文件
		String fileName = new File(txtPath).getName();
		//地震详情入库
		EarthQuake earthQuake = ftpSupplyEarthQuakeEvent(txtPath,fileName.substring(0, fileName.length()-4));
		
		//将ftp上传的文件  复制到 最终目录下
		List<String> oneEventHzList = CopyFileUtil.getChildFiles(earthQuakeEventPath);
		for(String hz:oneEventHzList){
			if(hz.toLowerCase().indexOf("1hz")>-1){
				System.out.println("1HZ:"+hz);
				CopyFileUtil.copy(hz, EarthQuakeDataReductionController.Final_Path+"/"+earthQuake.getYear()+"/"+earthQuake.getName()+"/"+"1Hz");
			}else if(hz.toLowerCase().indexOf("50hz")>-1){
				System.out.println("50HZ:"+hz);
				CopyFileUtil.copy(hz, EarthQuakeDataReductionController.Final_Path+"/"+earthQuake.getYear()+"/"+earthQuake.getName()+"/"+"50Hz");
			}
		}
		
		List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
		double distianceByGrade = LocationUtils.getDistianceByGrade(earthQuake, earthQuakeConfig.get(0));//根据用户的手动 或者 自动 配置  获得应急数据的公里范围
		System.out.println("启动  地震应急事件 （ftp整理普通模式）！！！！！");
		String startTime = earthQuake.getStrTime();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
		String textName = earthQuake.getName();
		//开始 往磁盘写入 该地震的文本说明文件
		String filePath = EarthQuakeDataReductionController.Final_Path+"/"+startTime.substring(0, 4)+"/"+textName;
		FileTool.outToText(earthQuake, filePath,textName+".txt");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList("1", "");//1 30S台站
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			//根据震级 判断是否在200 到 600公里 范围内
			double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
			boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
			
			if(!rangeByDistance){continue;}
			mainHistoryData(earthQuake, siteInfo, startDate, filePath);
		}
	}
	
	
	//写入地震应急事件的明细信息
	public EarthQuake ftpSupplyEarthQuakeEvent(String path,String earthQuakeName){
		try {
			EarthQuake earthQuake = setEarthDetailByTxt(path);
			EarthQuake dbQarthQuake = managerService.queryEarthQuakeByRun(" and s.name like '%"+earthQuakeName+"%'");
			if(null==dbQarthQuake){
				earthQuake.setName(earthQuakeName);
				managerService.addEarthQuake(earthQuake);
				return managerService.queryEarthQuakeByRun(" and s.name like '%"+earthQuakeName+"%'");
			}else{
				return dbQarthQuake;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 通过ftp将数据进行反向回补
	 */
	public void mainHistoryData(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath) throws Exception{
		startHistory1Hz( earthQuake, siteInfo, startDate, filePath);
		startHistory50Hz( earthQuake, siteInfo, startDate, filePath);
	}
	
	//1HZ开始整理
	public void startHistory1Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH,-2);
		for(int i=1;i<=3;i++){
			//前一天
		    cal.add(Calendar.DAY_OF_MONTH,1);
		    Date dateStrings = cal.getTime();
		    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据开始执行 1Hz  第"+i+"天："+dFormat.format(dateStrings));
			FileInfo fileInfo = setFileInfo("1",i, dFormat.format(dateStrings), earthQuake, siteInfo);
			query_1_History( fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
		}
	}
	
	//50HZ开始整理
	public void startHistory50Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
	    Date dateStrings = cal.getTime();
	    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据开始执行 50Hz "+dFormat.format(dateStrings));
	    FileInfo fileInfo = setFileInfo("50",1, dFormat.format(dateStrings), earthQuake, siteInfo);
	    query_50_History(fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
	}
	
	/**
	 * 应急数据回补 1HZ  从接收机上获取
	 */
	public void query_1_History(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception{
		
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String day = beforeDayStr.substring(8, 10);//日
		String siteNumber = siteInfo.getSiteNumber();
		String to2_final_AllPath = filePath+"/1Hz/"+siteNumber+"/"+day;
		
		//1赫兹  24小时，每小时4个文件
		int num_50hz = 1;
		for(int i = (int)'A'; i <= (int)'X'; ++i){
            char hours = (char)(i + 32);    
            for(int j=0;j<4;j++){
            	
            	String minutes = "";
            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+hours+minutes+"B"+".T02";
     			
     			String final_AllPath = to2_final_AllPath+"/"+fileName;
     			fileInfo.setFileName(fileName);
    			fileInfo.setFilePath(to2_final_AllPath);
    			//查询数据库中是否存在  数据是否存在
    			fileInfo.setEarthQuakeNum(num_50hz);
     	    	fileInfo.setEarthQuakeNum(num_50hz++);
     	    	
    			File resultFile = FileTool.findFiles(to2_final_AllPath,fileName);//从临时NAS盘中 找到目标文件
    			if(null!=resultFile){//有该文件
    				fileInfo.setFileFlag(1);
    				fileInfo.setFileSize(new File(final_AllPath).length()/1024.00);
    			}else{
    				fileInfo.setFileFlag(5);
    				fileInfo.setFileSize(0d);
    			}
     	    	//入库
    			FileInfo dbFileInfo = managerDao.queryEarthQuakeOneDetail(year4, fileInfo);
     	    	saveToDB(dbFileInfo,fileInfo,year4);
            }
        }
	}
	
	
	/**
	 * 从接收机上 下载     50HZ ftp文件
	 */
	public void query_50_History(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception{
		
		//压缩成d文件，并压缩成d.Z文件
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String siteNumber = siteInfo.getSiteNumber();
		String day = beforeDayStr.substring(8, 10);//日

		String to2_final_AllPath = filePath+"/50Hz/"+siteNumber+"/"+day;
		
	    CopyFileUtil.createLinuxFileDictory(to2_final_AllPath);
 		
		//50赫兹  前后三个小时，每小时4个文件
		int num_One = 1;
		
		int hour = Integer.valueOf(beforeDayStr.substring(11, 13));
        
		//未实行的功能
		if(hour==0){
        	
        }
        
        for(int i = (hour+96); i <= (int)(hour+98); ++i){
            char lowercase = (char)(i);    
            for(int j=0;j<4;j++){
            	String minutes = "";
            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+lowercase+minutes+"C"+".T02";
     			String final_AllPath =  to2_final_AllPath+"/"+fileName;
     			
     			fileInfo.setFileName(fileName);
    			fileInfo.setFilePath(to2_final_AllPath);
    			
    			//查询数据库中是否存在  数据是否存在
    			fileInfo.setEarthQuakeNum(num_One);
     	    	fileInfo.setEarthQuakeNum(num_One++); 
     	    	
     	    	File resultFile = FileTool.findFiles(to2_final_AllPath,fileName);//从临时NAS盘中 找到目标文件
    			if(null!=resultFile){//有该文件
    				fileInfo.setFileFlag(1);
    				fileInfo.setFileSize(new File(final_AllPath).length()/1024.00);
    			}else{
    				fileInfo.setFileFlag(5);
    				fileInfo.setFileSize(0d);
    			}
     	    	
     	    	//入库
    			FileInfo dbFileInfo = managerDao.queryEarthQuakeOneDetail(year4, fileInfo);
     	    	saveToDB(dbFileInfo,fileInfo,year4);
            }
        }
	}
	
	
	//通过txt文件获取 地震应急事件的明细信息
	public EarthQuake setEarthDetailByTxt(String filePath){
		try {
			EarthQuake eq = new EarthQuake();
			System.out.println(filePath);
            String encoding="utf-8";
            File file=new File(filePath);
            eq.setName(file.getName().substring(0, file.getName().length()-4));
            eq.setCrawType("0");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(lineTxt.indexOf("深度")>-1){
	                   	 if(lineTxt.indexOf(":")>-1){
	                   		 eq.setHeight(lineTxt.split(":")[1].trim());
	                   	 }else  if(lineTxt.indexOf("：")>-1){
	                   		 eq.setHeight(lineTxt.split("：")[1].trim());
	                   	 }
                    }else if(lineTxt.indexOf("地点")>-1){
	                   	 if(lineTxt.indexOf(":")>-1){
	                   		 eq.setAddress(lineTxt.split(":")[1].trim());
	                   	 }else  if(lineTxt.indexOf("：")>-1){
	                   		 eq.setAddress(lineTxt.split("：")[1].trim());
	                   	 }
                    }else if(lineTxt.indexOf("震级")>-1){
	                   	 if(lineTxt.indexOf(":")>-1){
	                   		 eq.setGrade(lineTxt.split(":")[1].trim());
	                   	 }else  if(lineTxt.indexOf("：")>-1){
	                   		 eq.setGrade(lineTxt.split("：")[1].trim());
	                   	 }
                    }else if(lineTxt.indexOf("经度")>-1){
	                   	 if(lineTxt.indexOf(":")>-1){
	                   		 eq.setSiteLon(lineTxt.split(":")[1].trim());
	                   	 }else  if(lineTxt.indexOf("：")>-1){
	                   		 eq.setSiteLon(lineTxt.split("：")[1].trim());
	                   	 }
                    }else if(lineTxt.indexOf("纬度")>-1){
	                   	 if(lineTxt.indexOf(":")>-1){
	                   		 eq.setSiteLat(lineTxt.split(":")[1].trim());
	                   	 }else  if(lineTxt.indexOf("：")>-1){
	                   		 eq.setSiteLat(lineTxt.split("：")[1].trim());
	                   	 }
                    }else if(lineTxt.indexOf("时间")>-1){
                   	 	if(lineTxt.indexOf("：")>-1){
	                   	 	String time = lineTxt.split("：")[1].trim();
	                      	eq.setYear(time.substring(0, 4));
	                        eq.setStrTime(time);
	                   	 }
                    }
                }
                read.close();
	     }else{
	         System.out.println("找不到指定的文件");
	     }
            System.out.println(eq.getStrTime()+" "+eq.getName());
        return eq;
	     } catch (Exception e) {
	         System.out.println("读取文件内容出错");
	         e.printStackTrace();
	     }
	     return null;
	}
	
	
	
	
	/**
	 * 初始化 启动应急事件时候的台站列表
	 */
	public void iniMainData(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath) throws Exception{
		ini1Hz(earthQuake, siteInfo, startDate, filePath);
		ini50Hz(earthQuake, siteInfo, startDate, filePath);
		
	}
	
	//初始化1HZ数据
	public void ini1Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH,-2);
		for(int i=1;i<=3;i++){
			//前一天
		    cal.add(Calendar.DAY_OF_MONTH,1);
		    Date dateStrings = cal.getTime();
		    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据初始化 1Hz  第"+i+"天："+dFormat.format(dateStrings));
			FileInfo fileInfo = setFileInfo("1",i, dFormat.format(dateStrings), earthQuake, siteInfo);
			iniDownT02( fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
		}
	}
	
	//初始化50HZ
	public void ini50Hz(EarthQuake earthQuake,SiteInfo siteInfo,Date startDate,String filePath)throws Exception{
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
	    Date dateStrings = cal.getTime();
	    System.out.println(siteInfo.getSiteName()+"   "+Thread.currentThread().getId()+"  地震应急数据初始化 50Hz "+dFormat.format(dateStrings));
	    FileInfo fileInfo = setFileInfo("50",1, dFormat.format(dateStrings), earthQuake, siteInfo);
	    iniDown_50HZ_T02(fileInfo, siteInfo, dFormat.format(dateStrings), filePath);
	}
	/**
	 * 初始化1HZ数据
	 */
	public void iniDownT02(FileInfo fileInfo,SiteInfo siteInfo,String beforeDayStr,String filePath) throws Exception{
		
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String day = beforeDayStr.substring(8, 10);//日
		
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		String siteNumber = siteInfo.getSiteNumber();
		String to2_final_AllPath = filePath+"/1Hz/"+siteNumber+"/"+day;
	    new CopyFileUtil().createLinuxFileDictory2(to2_final_AllPath);//非静态方法

		//1赫兹  24小时，每小时4个文件
		int num_50hz = 1;
		for(int i = (int)'A'; i <= (int)'X'; ++i){
            char hours = (char)(i + 32);    
            for(int j=0;j<4;j++){
            	
            	String minutes = "";
            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+hours+minutes+"B"+".T02";
     			
     			fileInfo.setFileName(fileName);
    			fileInfo.setFilePath(to2_final_AllPath);
    			fileInfo.setFileSize(0d);
     	    	fileInfo.setEarthQuakeNum(num_50hz++);
     	    	FileInfo newFile = (FileInfo) fileInfo.clone();//克隆对象
     	    	fileList.add(newFile);//全部的文件
            }
        }
		iniInsertDB(fileList, year4, to2_final_AllPath);
	}
	
	
	/**
	 *初始化 50HZ ftp文件
	 */
	public void iniDown_50HZ_T02(FileInfo fileInfo,SiteInfo siteInfo,String dateStrings,String filePath) throws Exception{
		
		int hour = Integer.valueOf(dateStrings.substring(11, 13));
        
		//未实行的功能
		if(hour==0){
			//当天
			iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,0,5);
			//当天
			iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,1,9);
			//前一天  
			String beforeDay = TimeUtils.getValidityTime(dateStrings, -1);
			String yearDay =StaticUtil.getThreeLengthCountDays(StaticUtil.getCountDaysByNYR(beforeDay));//年积日
			fileInfo.setFileDayYear(yearDay);
			iniCheckHour( fileInfo, siteInfo, beforeDay, filePath,23,1);
        }else if(hour==23){
        	//当天
        	iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,hour-1,1);
			//当天
        	iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,hour,5);
			//后一天
			String nextDay = TimeUtils.getValidityTime(dateStrings, +1);
			String yearDay =StaticUtil.getThreeLengthCountDays(StaticUtil.getCountDaysByNYR(nextDay));//年积日
			fileInfo.setFileDayYear(yearDay);
			iniCheckHour( fileInfo, siteInfo, nextDay, filePath,0,9);
        }else{
        	//当天
        	iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,hour-1,1);
			//当天
        	iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,hour,5);
			//当天
        	iniCheckHour( fileInfo, siteInfo, dateStrings, filePath,hour+1,9);
        }
	}
	
	public void iniCheckHour(FileInfo fileInfo,SiteInfo siteInfo,String dateStrings,String filePath,int hour,int num){
		try {
			//压缩成d文件，并压缩成d.Z文件
			String year4 = StaticUtil.getYearByNYR(dateStrings)+"";//年
			String day = dateStrings.substring(8, 10);//日
			String siteNumber = siteInfo.getSiteNumber();
			List<FileInfo> fileList = new ArrayList<FileInfo>();
			String to2_final_AllPath = filePath+"/50Hz/"+siteNumber+"/"+day;
		    CopyFileUtil.createLinuxFileDictory(to2_final_AllPath);
	 		
			//50赫兹  前后三个小时，每小时4个文件
	        for(int i = (hour+97); i <= (int)(hour+97); ++i){
	            char lowercase = (char)(i);    
	            for(int j=0;j<4;j++){
	            	String minutes = "";
	            	if(j==0){minutes="00";}if(j==1){minutes="15";}if(j==2){minutes="30";}if(j==3){minutes="45";}
	            	String fileName = siteNumber.toUpperCase()+fileInfo.getFileDayYear()+lowercase+minutes+"C"+".T02";
	     			
	     			fileInfo.setFileName(fileName);
	    			fileInfo.setFilePath(to2_final_AllPath);
	    			fileInfo.setFileSize(0d);
	    			
	     	    	fileInfo.setEarthQuakeNum(num++); 
	     	    	FileInfo newFile = (FileInfo) fileInfo.clone();//克隆对象
	     	    	fileList.add(newFile);//全部的文件
	            }
	        }
	        iniInsertDB(fileList, year4, to2_final_AllPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	//初始化入库操作1HZ
	public void iniInsertDB(List<FileInfo> fileList,String year4,String to2_final_AllPath){
		List<FileInfo> insertList = new ArrayList<FileInfo>();
		try {
		    for(FileInfo info:fileList){
				info.setFileFlag(0);
				insertList.add(info);//入库
			
		    }
		    applyDao.insert_EarhtData_Files(year4, insertList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
