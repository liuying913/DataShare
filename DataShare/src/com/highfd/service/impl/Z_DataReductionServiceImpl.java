package com.highfd.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.FTP.Ftp;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.common.linux.FileTypeTransform;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;
import com.highfd.service.Z_DataReductionService;
import com.linux.StaticUtil;

@Service
public class Z_DataReductionServiceImpl implements Z_DataReductionService {
	
	public static String S30SBasePath = "/root/tmp_nas/gnssdata/CMONOC-GNSS";//30S 临时nas盘 o文件（人家的接收机存储文件）
	public static String S30SWorkPath = "/home/liuying/To2Work01";
	public static String S30S_ZipPath = "/root/nfs/Gnssdata/30sdz";//最终位置  30秒 Zip文件位置
	
	public static String S30S_Ftp_WorkPath = "/home/liuying/To2Ftp";
	public static String S30S_To2_Ftp_Path = "Internal/30sd";
	
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	HighFDService highFDService;
	
	public void data30S(String beforeDayStr,String siteNumberParam) throws InterruptedException{
		
		//清空整理文件的空间
		//CopyFileUtil.deleteDirList("/java");
		//CopyFileUtil.deleteDirList("/javaFtp");
		
		//30S数据启动整理
		FileTypeTransform t = new FileTypeTransform();
		System.out.println(beforeDayStr);
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String year2 =StaticUtil.getYearTwoLength(beforeDayStr);//年
		String month = beforeDayStr.substring(5, 7);//月
		String day = beforeDayStr.substring(8, 10);//日
		
		int doy= StaticUtil.getCountDaysByNYR(beforeDayStr);
		// 三位数年纪日 001,065,123
		String yearDay =StaticUtil.getThreeLengthCountDays(doy);
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoListByIn("1", siteNumberParam);//1 30S台站
		for(int i=0;i<siteInfoList.size();i++){
			
			SiteInfo siteInfo = siteInfoList.get(i);
			String siteNumber = siteInfo.getSiteNumber();
			String sitePath = S30SBasePath+"/"+siteNumber;
			
			//将气象文件（n文件）放到指定文件
			copyN_File( siteNumber, year4, year2, yearDay, sitePath);
			
			//如果整理好了的，那么走下一个
			boolean historyFlag = applyFileService.queryHistoryFileInfo(year4, yearDay, siteNumber, "1", "", " and t.fileflag in (1,2) ");
			if(historyFlag){continue;}
			
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=format.format(date);
			System.out.println("开始执行下一个台站："+beforeDayStr+" "+ siteNumber+"  "+time);
			
			
			//设置路径
			String o_file_Name = siteNumber+yearDay+"0."+year2+"o";
			String o_file_WorkPath =           S30SWorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
			String s_file_WorkPath =           S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"S";
			String z_file_ResultPath =         S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z";
			
			String o_file_Ftp_WorkPath =  S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
			
			//入关系库对象
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileYear(year4);
			fileInfo.setSiteNumber(siteNumber);
			fileInfo.setFileDayYear(yearDay);
			fileInfo.setFileName(siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");
			fileInfo.setFilePath(S30S_ZipPath+"/"+year4+"/"+yearDay);
			fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
			fileInfo.setSystemStr(beforeDayStr);
			fileInfo.setFileFlag(5);
			fileInfo.setType("1");//连接不上
			
			File findFiles = FileTool.findFiles(sitePath,o_file_Name);//从临时NAS盘中 找到目标文件
			if(null!=findFiles){
				String o_file_NAS_Path = findFiles.getAbsoluteFile().toString();//获得o文件路径（临时NAS盘上面的）
				
				//复制文件
				if(CopyFileUtil.copyFile(o_file_NAS_Path, o_file_WorkPath, true)){
					//执行TEQC将o文件转换成YYS文件
		    		if(FileTypeTransform.o_To_S(o_file_WorkPath)!=null){
		    			
		    			fileInfo = FileTypeTransform.s_setFileInfo(fileInfo, s_file_WorkPath, true);
		    			if(fileInfo.getFileFlag()==6){//从s获得数据时,出现问题
		    				//存在o文件的情况下，结算错误， 启动程序 从ftp下载t02文件 
			    			System.out.println("临时NAS盘 s文件结算失败,   启动T02  "+o_file_WorkPath);
			    			
			    			//判断t02的是否有问题
			    			FileInfo t02NzzFile = T02AnalyWithOutZZ(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr,false);
			    			if(t02NzzFile.getFileFlag()!=2 && t02NzzFile.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
			    				FileInfo zbds = t.Zbds(fileInfo, o_file_WorkPath, true);
			    				t.goToDB( year4,  zbds, o_file_WorkPath,  z_file_ResultPath ,applyFileService);//入库操作
			    			}else{
			    				t.goToDB( year4,  t02NzzFile, o_file_Ftp_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
			    			}
		    			}else{
							if(Integer.valueOf(fileInfo.getEphem_number())<2736){
								
								//判断t02的是否有问题
				    			FileInfo t02NzzFile = T02AnalyWithOutZZ(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr,false);
				    			if(t02NzzFile.getFileFlag()!=2 && t02NzzFile.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
				    				//T02有问题，以o文件为主
				    				t.goToDB( year4,  fileInfo, o_file_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
				    			}else{
				    				if(Integer.valueOf(fileInfo.getEphem_number())>Integer.valueOf(t02NzzFile.getEphem_number())){
				    					t.goToDB( year4,  fileInfo, o_file_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
									}else{
										t.goToDB( year4,  t02NzzFile, o_file_Ftp_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
									}
				    			}
							}else{
								t.goToDB( year4,  fileInfo, o_file_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
							}
		    			}
						/*// 解析结果文件入库
						if(new File(s_file_WorkPath).length() > 100){}else{
							System.out.println("test() "+s_file_WorkPath+" 文件大小小于100字节，不能上传！");
							MapAll.logger.error("test() "+s_file_WorkPath+" 文件大小小于100字节，不能上传！");
							//teqc 转成o文件失败，从ftp重新下载
							FileInfo out_NasFile = getTo2_Out_Nas(fileInfo,siteInfo, year4, year2, month, day, yearDay, beforeDayStr);
							list.add(out_NasFile);
							//applyFileService.insert_30SData_Files(year4, list);
						}*/
		    		}else{
		    			//存在o文件的情况下，转化错误， 启动程序 从ftp下载t02文件 
		    			System.out.println("临时NAS盘有o文件   o文件转s文件失败,   启动T02  "+o_file_WorkPath);
		    			
		    			//判断t02的s是否有问题
		    			FileInfo t02NzzFile = T02AnalyWithOutZZ(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr,false);
		    			if(t02NzzFile.getFileFlag()!=2 && t02NzzFile.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
		    				FileInfo zbds = t.Zbds(fileInfo, o_file_WorkPath, true);
		    				t.goToDB( year4,  zbds, o_file_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
		    				System.out.println("走原来的o文件， 入库");
		    			}else{
		    				System.out.println("t02 ok， 入库");
		    				t.goToDB( year4,  t02NzzFile, o_file_Ftp_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
		    			}
		    		}

				}else{
					System.out.println("从临时NAS盘复制o文件失败,启动T02"+sitePath+"/"+o_file_Name);
					FileInfo fileInfo_Ftp = T02_analysis(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr,false);
					t.goToDB( year4,  fileInfo_Ftp, o_file_Ftp_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
				}
			}else{
				System.out.println("从临时NAS盘未找到  o文件,从接收机上下载T02文件  文件路径："+fileInfo.getFilePath() +"  文件名称："+fileInfo.getFileName());
				FileInfo fileInfo_Ftp = T02_analysis(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr,false);
				t.goToDB( year4,  fileInfo_Ftp, o_file_Ftp_WorkPath,  z_file_ResultPath,applyFileService);//入库操作
			}
			
			//将气象文件（n文件）放到指定文件
			copyN_File( siteNumber, year4, year2, yearDay, S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay);
			
			//启动下一个台站
			System.out.println("该站跑完！");
		}
	}

	
	//从FTP上面下载To2文件进行解析
	public static FileInfo T02_analysis(FileInfo fileInfo3,SiteInfo siteInfo,String year4,String year2,String month,String day,String yearDay,String beforeDayStr,boolean flag){
		
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setFileYear(fileInfo3.getFileYear());
		fileInfo2.setSiteNumber(fileInfo3.getSiteNumber());
		fileInfo2.setFileDayYear(fileInfo3.getFileDayYear());
		fileInfo2.setFileName(fileInfo3.getFileName());
		fileInfo2.setFilePath(fileInfo3.getFilePath());
		fileInfo2.setFileSize(fileInfo3.getFileSize());
		fileInfo2.setSystemStr(fileInfo3.getSystemStr());
		fileInfo2.setFileFlag(fileInfo3.getFileFlag());
		fileInfo2.setType(fileInfo3.getType());
		
		//压缩成d文件，并压缩成d.Z文件
		String siteNumber = siteInfo.getSiteNumber();
		
		String o_file_Name = siteNumber+yearDay+"0."+year2+"o";
		String t02_file_Ftp_WordPath= S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.T02";
		String o_file_Ftp_WorkPath =  S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
		String s_file_Ftp_WorkPath =  S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"S";
		
		//下载t02文件。。。
    	String url = siteInfo.getAddress();
    	String fileName = siteNumber+yearDay+"aD.T02";
    	String ftpWorkPath = S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay;
    	
    	List<String> remotePathList = new ArrayList<String>();//ftp路径集合
		remotePathList.add("Internal/30sd/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30Sd/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30sD/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30SD/"+year4+"/"+month+"/"+day+"/");
		List<String> fileNameList = new ArrayList<String>();//ftp文件名列表
		fileNameList.add(fileName.toLowerCase());
		Ftp ftp = new Ftp();
    	int ftpStatus = ftp.oneLogindownFile(url, "", "", remotePathList, fileNameList, ftpWorkPath);
    	
    	if(ftpStatus==1){//FTP 下载成功
    		System.out.println("FTP下载成功T02文件："+fileName);
    		//转成tgd文件
    		String flag1 = FileTypeTransform.t02_To_tgd(t02_file_Ftp_WordPath);if(flag1==null){System.out.println(t02_file_Ftp_WordPath+" to2转tgd文件出错！");return FileTypeTransform.setFileInfo(fileInfo2,6);}
    		//tgd转成o文件
    		String flag2 = FileTypeTransform.tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);if(flag2==null){System.out.println(t02_file_Ftp_WordPath+" tgd转o文件出错！");return FileTypeTransform.setFileInfo(fileInfo2,6);}
    		//o文件转s文件
    		String result3 = FileTypeTransform.o_To_S(o_file_Ftp_WorkPath);
    		if(result3!=null){
    			fileInfo2 = FileTypeTransform.s_setFileInfo(fileInfo2, s_file_Ftp_WorkPath, flag);
    		}else{
    			//启动程序  根据正则表达式计算历元数量
    			System.out.println(o_file_Ftp_WorkPath+" o文件转s文件出错！启动程序  根据正则表达式计算历元数量");
				FileInfo oSupplAnaly = FileTypeTransform.o_supplAnaly(fileInfo2,o_file_Ftp_WorkPath,flag);	
				fileInfo2.setEphem_number(oSupplAnaly.getEphem_number());
				fileInfo2.setMp1("-1");
				fileInfo2.setMp2("-1");
				fileInfo2.setO_slps("-1");
				fileInfo2.setFileFlag(oSupplAnaly.getFileFlag());
    			return fileInfo2;
    		}
    	}else{
    		System.out.println("下载失败： "+ftpStatus);
    		//FTp下载失败！
    		return FileTypeTransform.setFileInfo(fileInfo2,ftpStatus);
    	}
		return fileInfo2;
	}
	
	
	//解析T02， 并且排除正则表达式
	public static FileInfo T02AnalyWithOutZZ(FileInfo fileInfo3,SiteInfo siteInfo,String year4,String year2,String month,String day,String yearDay,String beforeDayStr,boolean flag){
		
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setFileYear(fileInfo3.getFileYear());
		fileInfo2.setSiteNumber(fileInfo3.getSiteNumber());
		fileInfo2.setFileDayYear(fileInfo3.getFileDayYear());
		fileInfo2.setFileName(fileInfo3.getFileName());
		fileInfo2.setFilePath(fileInfo3.getFilePath());
		fileInfo2.setFileSize(fileInfo3.getFileSize());
		fileInfo2.setSystemStr(fileInfo3.getSystemStr());
		fileInfo2.setFileFlag(fileInfo3.getFileFlag());
		fileInfo2.setType(fileInfo3.getType());
		
		//压缩成d文件，并压缩成d.Z文件
		String siteNumber = siteInfo.getSiteNumber();
		
		String o_file_Name = siteNumber+yearDay+"0."+year2+"o";
		String t02_file_Ftp_WordPath= S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.T02";
		String o_file_Ftp_WorkPath =  S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+o_file_Name;
		String s_file_Ftp_WorkPath =  S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"S";
		
		//下载t02文件。。。
    	String url = siteInfo.getAddress();
    	String fileName = siteNumber+yearDay+"aD.T02";
    	String ftpWorkPath = S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay;
    	
    	List<String> remotePathList = new ArrayList<String>();//ftp路径集合
		remotePathList.add("Internal/30sd/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30Sd/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30sD/"+year4+"/"+month+"/"+day+"/");remotePathList.add("Internal/30SD/"+year4+"/"+month+"/"+day+"/");
		List<String> fileNameList = new ArrayList<String>();//ftp文件名列表
		fileNameList.add(fileName.toLowerCase());
		Ftp ftp = new Ftp();
    	int ftpStatus = ftp.oneLogindownFile(url, "", "", remotePathList, fileNameList, ftpWorkPath);
    	
    	System.out.println("下载状态："+ ftpStatus);
    	if(ftpStatus==1){//FTP 下载成功
    		System.out.println("FTP下载成功T02文件："+fileName);
    		//转成tgd文件
    		String flag1 = FileTypeTransform.t02_To_tgd(t02_file_Ftp_WordPath);if(flag1==null){System.out.println(t02_file_Ftp_WordPath+" to2转tgd文件出错！");return FileTypeTransform.setFileInfo(fileInfo2,6);}
    		//tgd转成o文件
    		String flag2 = FileTypeTransform.tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);if(flag2==null){System.out.println(t02_file_Ftp_WordPath+" tgd转o文件出错！");return FileTypeTransform.setFileInfo(fileInfo2,6);}
    		//o文件转s文件
    		String result3 = FileTypeTransform.o_To_S(o_file_Ftp_WorkPath);
    		if(result3!=null){
    			fileInfo2 = FileTypeTransform.s_setFileInfo(fileInfo2, s_file_Ftp_WorkPath, flag);
    		}else{
    			/*//启动程序  根据正则表达式计算历元数量
    			System.out.println(o_file_Ftp_WorkPath+" o文件转s文件出错！启动程序  根据正则表达式计算历元数量");
				FileInfo oSupplAnaly = o_supplAnaly(fileInfo2,o_file_Ftp_WorkPath,flag);	
				fileInfo2.setEphem_number(oSupplAnaly.getEphem_number());
				fileInfo2.setMp1("-1");
				fileInfo2.setMp2("-1");
				fileInfo2.setO_slps("-1");
				fileInfo2.setFileFlag(oSupplAnaly.getFileFlag());
    			return fileInfo2;*/
    			System.out.println(t02_file_Ftp_WordPath+" o转s文件出错！");
    			return FileTypeTransform.setFileInfo(fileInfo2,6);
    		}
    	}else{
    		//FTp下载失败！
    		return FileTypeTransform.setFileInfo(fileInfo2,ftpStatus);
    	}
		return fileInfo2;
	}
	
	
	
	public static void copyN_File(String siteNumber,String year4,String year2,String yearDay,String sitePath){
		try {
			if(CopyFileUtil.fileExist("/root/nfs/Gnssdata/met30Sm"+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"m")){
				return;
			}
			//将气象文件（n文件）放到指定文件
			String n_File_Name=siteNumber+yearDay+"0."+year2+"m";//o文件 文件名称
			//String n_File_NASPath = sitePath+n_File_Name; //临时NAS盘中的路径
			//String n_File_WorkPath = S30SWorkPath+"/"+year4+"/"+yearDay+"/"+n_File_Name;//FTP下载后 转换的路径
			String n_file_ResultPath =         "/root/nfs/Gnssdata/met30Sm"+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"m";//最终路径
			String real_n_name = FileTool.findFiles2(sitePath,n_File_Name);//从临时NAS盘中 找到目标文件
			//将临时NAS盘 气象文件（n文件）放到指定文件
			if(null!=real_n_name){
				//System.out.println("发现气象文件"+sitePath+"/"+real_n_name);
				boolean copyFile = CopyFileUtil.copyFile(sitePath+"/"+real_n_name, n_file_ResultPath, true);
				if(copyFile){
					System.out.println("原气象文件位置：  "+sitePath);
					//System.out.println("气象文件复制成功！"+sitePath+"/"+real_n_name);
				}else{
					System.out.println("气象文件复制失败！！！！"+sitePath+"/"+real_n_name);
				}
			}else{
				System.out.println("没有找到气象文件"+sitePath+"/"+real_n_name);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
