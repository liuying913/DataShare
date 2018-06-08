package com.highfd.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.SiteStationService;
import com.linux.GpsWeek;
import com.linux.StaticUtil;
import com.linux.Z_TEQC_LINUX;
import com.highfd.common.FTP.Ftp;
import com.highfd.common.ZZBDS.ZZbds;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.jdbc.service.impl.AlarmJdbcServiceImpl;
/**
 * 基于注解的定时器
 */
//@Component
//@Controller
public class Z_RedateController {
	
	//@Autowired
	//ApplyFileService applyFileService;
	
	//@Autowired
	//SiteStationService siteService;
	
	public static String S30SBasePath = "/root/tmp_nas/gnssdata/CMONOC-GNSS";//30S 临时nas盘 o文件
	public static String S30SWorkPath = "/home/liuying/supplyWork";
	public static String S30S_ZipPath = "/root/nfs/Gnssdata/30sdz";//30秒 Zip文件位置
	
	public static String S30S_Ftp_WorkPath = "/home/liuying/supplyFtp";
	public static String S30S_To2_Ftp_Path = "Internal/30sd";
	//程序超时时间秒数
	private static int timeoutSeconds = 20;//add 原来60秒
	
	
	public static void main(String[] args) throws Exception {
		Z_RedateController z = new Z_RedateController();
		z.S30Data();
	}
	/**
	 * 开始整理数据
	 * @throws Exception
	 */
	//@Scheduled(cron = "0 0/1 1-23 * * ?")
	//@Scheduled(fixedRate = 100000*60*60*24) 
	public void S30Data() throws Exception {
		Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-31");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date0);
		while(cal.getTime().compareTo(date1)<=0){
		  //...
		  cal.add(Calendar.DAY_OF_MONTH,1);
		  Date dateStrings = cal.getTime();
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("开始执行："+dFormat.format(dateStrings));
			t(dFormat.format(dateStrings));
		}
	}
	
	
	public void t(String beforeDayStr) throws InterruptedException{
		
		//清空整理文件的空间
		CopyFileUtil.deleteDirList("/home/liuying/supplyManager");
		CopyFileUtil.deleteDirList("/home/liuying/supplyFtp");
		
		//30S数据启动整理
		Z_RedateController t = new Z_RedateController();
		//String beforeDayStr = TimeUtils.getStrYesterday(1);
		System.out.println(beforeDayStr);
		String year4 = StaticUtil.getYearByNYR(beforeDayStr)+"";//年
		String year2 =StaticUtil.getYearTwoLength(beforeDayStr);//年
		String month = beforeDayStr.substring(5, 7);//月
		String day = beforeDayStr.substring(8, 10);//日
		int doy= StaticUtil.getCountDaysByNYR(beforeDayStr);
		// 三位数年纪日 001,065,123
		String yearDay =StaticUtil.getThreeLengthCountDays(doy);
		
		//List<SiteInfo> siteInfoList = siteService.getSiteInfoListByFileFlag(yearDay);//1 30S台站
		AlarmJdbcServiceImpl a = new AlarmJdbcServiceImpl();
		List<SiteInfo> siteInfoList = a.getSiteInfoListByFileFlag(yearDay,year4);
		
		/*List<SiteInfo> siteInfoList = new ArrayList<SiteInfo>();//1 30S台站
		SiteInfo s = new SiteInfo();
		s.setSiteNumber("BJFS");s.setSiteName("房山");s.setAddress("172.128.16.49");
		siteInfoList.add(s);*/
		
		for(int i=0;i<siteInfoList.size();i++){
			
			SiteInfo siteInfo = siteInfoList.get(i);
			String siteNumber = siteInfo.getSiteNumber();
			System.out.println();
			System.out.println("开始执行下一个台站："+beforeDayStr+" "+ siteNumber);
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
			fileInfo.setFileFlag(0);
			fileInfo.setType("1");
			
			String sitePath = S30SBasePath+"/"+siteNumber;
			File findFiles = FileTool.findFiles(sitePath,o_file_Name);//从临时NAS盘中 找到目标文件
			if(null!=findFiles){
				String o_file_NAS_Path = findFiles.getAbsoluteFile().toString();//获得o文件路径（临时NAS盘上面的）
				
				//复制文件 多复制几次
				boolean o_o = CopyFileUtil.copyFile(o_file_NAS_Path, o_file_WorkPath, true);//Thread.sleep(1000);
				//if(!o_o){o_o = CopyFileUtil.copyFile(o_file_NAS_Path, o_file_WorkPath, true);}Thread.sleep(1000);
				//if(!o_o){o_o = CopyFileUtil.copyFile(o_file_NAS_Path, o_file_WorkPath, true);}
				if(o_o){//复制成功，启动整理

					// 执行TEQC将o文件转换成YYS文件
		    		String result3 = o_To_S(o_file_WorkPath);
		    		if(result3!=null){
		    			fileInfo = s_setFileInfo(fileInfo, s_file_WorkPath, true);
		    			if(fileInfo.getFileFlag()==6){//从s获得数据时,出现问题
		    				FileInfo fileInfo_Ftp = T02_analysis( fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr);
		    				//入库操作
		    				if(fileInfo_Ftp.getFileFlag()!=2 && fileInfo_Ftp.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
								t.fileCompress(o_file_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
								//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
							}else{
								t.fileCompress(o_file_Ftp_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
								fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
								//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo_Ftp);applyFileService.insert_30SData_Files(year4, list);//入 关系库
							}
		    			}else{
							if(Integer.valueOf(fileInfo.getEphem_number())<2736){
								FileInfo fileInfo_Ftp = T02_analysis(fileInfo, siteInfo, year4, year2, month, day, yearDay, beforeDayStr);
								if(fileInfo_Ftp.getFileFlag()!=2 && fileInfo_Ftp.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
									System.out.println(fileInfo.getEphem_number()+"   5555   "+fileInfo.getFileFlag());
									//FileInfo sSetFileInfo = s_setFileInfo(fileInfo,s_file_WorkPath,true);
									t.fileCompress(o_file_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
									fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
									//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
								}else{
									//当从ftp下载文件没问题时，判断两个历元大小
									if(Integer.valueOf(fileInfo.getEphem_number())>Integer.valueOf(fileInfo_Ftp.getEphem_number())){
										t.fileCompress(o_file_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
										fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
										//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
									}else{
										t.fileCompress(o_file_Ftp_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
										fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
										//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo_Ftp);applyFileService.insert_30SData_Files(year4, list);//入 关系库
									}
								}
							}else{
								//入库
								t.fileCompress(o_file_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
								fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
								//List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
							}
		    			}
		    		}else{
		    			//启动程序 从ftp下载t02文件
		    			System.out.println("启动 补充处理操作   o文件转s文件失败"+o_file_WorkPath);
						//MapAll.logger.error("启动 补充处理操作   o文件转s文件失败"+o_file_WorkPath);
						
						FileInfo oSupplAnaly = o_supplAnaly(fileInfo,o_file_WorkPath);	
						AlarmJdbcServiceImpl b = new AlarmJdbcServiceImpl();
						b.update_30S_apply(year4, oSupplAnaly);
						t.fileCompress(o_file_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
						
		    		}

				}else{}
			}else{}
			//System.out.println(fileInfo.getSiteNumber()+"  "+fileInfo.getEphem_number()+"  "+fileInfo.getMp1()+"  "+fileInfo.getMp2()+"  "+fileInfo.getO_slps()+"  "+fileInfo.getFileFlag());
		}
		//applyFileService.insert_30SData_Files(year4, list);
	}
	
	
	
	//从FTP上面下载To2文件进行解析
	public static FileInfo T02_analysis(FileInfo fileInfo3,SiteInfo siteInfo,String year4,String year2,String month,String day,String yearDay,String beforeDayStr){
		
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
    	String remotePath = S30S_To2_Ftp_Path+"/"+year4+"/"+month+"/"+day+"/";
    	String fileName = siteNumber+yearDay+"aD.T02";
    	String ftpWorkPath = S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay;
    	
    	//file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
    	//暂时修改aaaa 注意点
    	//int ftpStatus = Ftp.downFile(url, "", "", remotePath, fileName, ftpWorkPath);
    	int ftpStatus = 5;
    	
    	if(ftpStatus==1){//FTP 下载成功
    		System.out.println("FTP下载成功T02文件："+fileName);
    		//转成tgd文件
    		String flag1 = t02_To_tgd(t02_file_Ftp_WordPath);if(flag1==null){System.out.println(t02_file_Ftp_WordPath+" to2转tgd文件出错！");return setFileInfo(fileInfo2,6);}
    		//tgd转成o文件
    		String flag2 = tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);if(flag2==null){System.out.println(t02_file_Ftp_WordPath+" tgd转o文件出错！");return setFileInfo(fileInfo2,6);}
    		//o文件转s文件
    		String result3 = o_To_S(o_file_Ftp_WorkPath);
    		if(result3!=null){
    			fileInfo2 = s_setFileInfo(fileInfo2, s_file_Ftp_WorkPath, false);
    		}else{
    			System.out.println(o_file_Ftp_WorkPath+" o文件转s文件出错！");
    			return setFileInfo(fileInfo2,6);
    		}
    	}else{
    		//FTp下载失败！
    		return setFileInfo(fileInfo2,ftpStatus);
    	}

		return fileInfo2;
	}
	

	
	public static boolean isNumeric(String str){
	   for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		      return false;
		   }
	   }
	   return true;
	}
	
	/**
	 * 获得o文件里面的数值
	 * @param fileInfo
	 * @param o_file_Ftp_WorkPath
	 * @param s_file_Ftp_WorkPath
	 * @param flag  是否是通过FTP下载 进行的o文件到s文件
	 */
	public static FileInfo s_setFileInfo(FileInfo fileInfo,String s_file_Ftp_WorkPath,boolean flag){
		FileInfo sFile2 = jiexiSfile(s_file_Ftp_WorkPath, "", "");//解析o文件，并将结果入库
		if(null==sFile2){
			fileInfo.setEphem_number("-1");
			fileInfo.setMp1("-1");
			fileInfo.setMp2("-1");
			fileInfo.setO_slps("-1");
			fileInfo.setFileFlag(6);
		}else{
			fileInfo.setEphem_number(sFile2.getEphem_number());
			fileInfo.setMp1(sFile2.getMp1());
			fileInfo.setMp2(sFile2.getMp2());
			fileInfo.setO_slps(sFile2.getO_slps());
			if(flag){//从临时NAS盘获得的o文件
				fileInfo.setFileFlag(Integer.valueOf(sFile2.getEphem_number())>=600?1:3);
			}else{//从T02文件转换过来的o文件
				fileInfo.setFileFlag(Integer.valueOf(sFile2.getEphem_number())>=600?2:3);
			}
		}
		
		return fileInfo;
	}
	
	
	/**
	 * 补充抓取
	 * @param fileInfo
	 * @param o_file_Ftp_WorkPath
	 * @param s_file_Ftp_WorkPath
	 * @param flag  是否是通过FTP下载 进行的o文件到s文件
	 */
	public static FileInfo o_supplAnaly(FileInfo fileInfo,String o_file_WorkPath){
		int lyNumber = ZZbds.txt2String(o_file_WorkPath);
		fileInfo.setEphem_number(lyNumber+"");
		fileInfo.setMp1("-1");
		fileInfo.setMp2("-1");
		fileInfo.setO_slps("-1");
		//fileInfo.setFileFlag(7);//通过正则表达式搞的
		//从临时NAS盘获得的o文件
		fileInfo.setFileFlag(Integer.valueOf(lyNumber)>=600?11:31);
		return fileInfo;
	}
	
	//file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
	public static FileInfo setFileInfo(FileInfo fileInfo,int fileFlag){
		fileInfo.setEphem_number("0");
		fileInfo.setMp1("-1");
		fileInfo.setMp2("-1");
		fileInfo.setO_slps("-1");
		fileInfo.setFileFlag(fileFlag);
		return fileInfo;
	}
	
	
	public static String t02_To_tgd(String t02_file_Ftp_WordPath){
		String commandTo2_To_Tgd = "chmod 777 "+t02_file_Ftp_WordPath+"; runpkr00 -g -d " +t02_file_Ftp_WordPath+";exit";//解压缩成 tgd
		String result = to2ToTgd(commandTo2_To_Tgd);
		return result;
	}
	
	public static String tgd_To_o(String year4,String year2,String yearDay,String siteNumber,String beforeDayStr){
		String commandTgd_To_o = "chmod 777 "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;" //放开权限
        +"teqc +quiet ++err "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/teqc.log "+
               "+obs "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"o " +
               "+nav "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"n " +
	            "+met "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"m " +
	            "-week "+GpsWeek.getGPSWeek(beforeDayStr)+" "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;exit";
		String result = to2ToTgd(commandTgd_To_o);
		return result;
	}
	public static String o_To_S(String o_file_WorkPath){
		String command_O_S = "chmod 777 "+o_file_WorkPath+";teqc +qc "+o_file_WorkPath+";exit";
		String result = to2ToTgd(command_O_S);
		return result;
	}
	
/*	public static void main(String[] args) throws Exception {
		
		String s = args[0];
		System.out.println(s);
		o_To_S(s);
		
		Z_DataReductionController z = new Z_DataReductionController();
		//z.S30Data();
		jiexiSfile("D:\\GSMQ0140.16S","","");
		
		Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-01");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-23");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date0);
		while(cal.getTime().compareTo(date1)<=0){
		  //...
		  cal.add(Calendar.DAY_OF_MONTH,1);
		  Date dateStrings = cal.getTime();
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(dFormat.format(dateStrings));
		}
		
	}*/
	
	/**
	 * 解析TEQC最终生成的文件，进行入库操作
	 * 
	 * @param SfilePath
	 * @param siteNumber
	 * @param beforreDayStr 
	 */
	public static FileInfo jiexiSfile(String SfilePath, String siteNumber,String beforreDayStr) {
		
		FileInfo info = new FileInfo();
		info.setEphem_number("0");
		File f = new File(SfilePath);
		if (f.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String lineTXT = null;
				while ((lineTXT = br.readLine()) != null) {
					lineTXT = lineTXT.toString().trim();
					// 发现你这个标石后立即跳出循环，不再解析文件
					if (lineTXT.contains("Processing parameters are:")) {
						break;
					}
					// 找到目标数据的那一行
					if (lineTXT.contains("SUM")) {
						String ss[] = lineTXT.split(" ");
						Map<Integer, String> map = new HashMap<Integer, String>();
						int allLength = 0;
						for (int d = 0; d < ss.length; d++) {
							if(!"".equals(ss[d].trim())) {
								map.put(allLength, ss[d]);
								allLength += 1;
							}
						}
						info.setMp1(map.get(allLength - 3));
						info.setMp2(map.get(allLength - 2));
						info.setO_slps(map.get(allLength - 1));
						System.out.println("历元分析：  "+SfilePath+"分析结果："+map.get(allLength-5)+" "+ map.get(allLength-4)+" "+ map.get(allLength - 3)+" "+ map.get(allLength - 2)+" "+ map.get(allLength - 1));
					}
					
					if (lineTXT.contains("Epochs w/ observations")) {
						if(lineTXT.contains(":")){
							String ss[] = lineTXT.split(":");
							if(ss.length==2){
								info.setEphem_number(ss[1].trim());
								System.out.println("历元分析：  "+SfilePath+"历元数量："+info.getEphem_number());
							}else{
								System.out.println("历元分析：  "+SfilePath+"历元数量解析有问题A："+lineTXT);
							}
						}else{
							System.out.println("历元分析：  "+SfilePath+"历元数量解析有问题B："+lineTXT);
						}
					}
				}
				return info;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("jiexiSfile() siteNumber:[" + siteNumber + "],time:["
					+ beforreDayStr + "] YYS result file not exist!");
		}
		return null;
	}
	
	
	/*public static void main(String[] args) {
		String command1 = "chmod 777 /home/tomcat/apache-tomcat-51jobrecruit/aaa.T02;runpkr00 -g -d /home/tomcat/apache-tomcat-51jobrecruit/aaa.T02;exit";
		to2ToTgd(command1);

		String command2 = "chmod 777 /home/tomcat/apache-tomcat-51jobrecruit/aaa.tgd;teqc +quiet ++err /home/tomcat/apache-tomcat-51jobrecruit/teqc.log +obs /home/tomcat/apache-tomcat-51jobrecruit/aaa.17o +nav /home/tomcat/apache-tomcat-51jobrecruit/aaa.17n +met /home/datashare/mysql/aaa.17m -week 1932 /home/tomcat/apache-tomcat-51jobrecruit/aaa.tgd;exit";
		to2ToTgd(command2);
		
		String command3 = "chmod 777 /home/tomcat/apache-tomcat-51jobrecruit/AHAQ0010.13o;teqc +qc /home/tomcat/apache-tomcat-51jobrecruit/AHAQ0010.13o;exit";
		String result3 = to2ToTgd(command3);
		
		if(result3!=null){
			String resultSfilePath = "/home/tomcat/apache-tomcat-51jobrecruit/AHAQ0010.13S";
			// 解析结果文件入库
			if(new File(resultSfilePath).length() > 100){
				Z_TEQC_LINUX.jiexiSfile(resultSfilePath, "", "");
			}else{
				System.out.println("test() "+resultSfilePath+" 文件大小小于100字节，不能上传！");
			}
		}
		
		String unzipPath = "/home/tomcat/apache-tomcat-51jobrecruit/AHAQ0010.13o";
		Z_DataReductionController t = new Z_DataReductionController();
		t.fileCompress(unzipPath,"");
	}*/
	
	
	public static String to2ToTgd(String command1){
		//超时执行的话 结果还是null
		String result = null;
		result = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1 } );
		if(result==null){
			System.out.println("*****executeFileForLinux 方法执行超时*****【"+command1+"】");
			return null;
		}
		return result;
	}
	
	
	/***
	 * 方法参数说明
	 * @param target 调用方法的当前对象
	 * @param methodName 方法名称
	 * @param parameterTypes 调用方法的参数类型
	 * @param params 参数  可以传递多个参数
	 * 
	 * */
	public static String callMethod(final Object target , final String methodName ,final Class<?>[] parameterTypes,final Object[]params){
		ExecutorService executorService = Executors.newSingleThreadExecutor();  
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
            	String value = null  ; 
            	try {
					Method method = null ; 
					method = target.getClass().getDeclaredMethod(methodName , parameterTypes ) ;  
					
					Object returnValue = method.invoke(target, params) ;  
					value = returnValue != null ? returnValue.toString() : null ;
				} catch (Exception e) {
					e.printStackTrace() ;
					throw e ; 
				}
                return value ;
            }  
        });  
          
        executorService.execute(future);  
        String result = null;  
        try{
        	/**获取方法返回值 并设定方法执行的时间为60秒*/
            result = future.get(timeoutSeconds , TimeUnit.SECONDS );  
            
        }catch (InterruptedException e) {  
            future.cancel(true);  
            System.out.println("方法执行中断"); 
        }catch (ExecutionException e) {  
            future.cancel(true);  
            System.out.println("Excuti on异常");  
        }catch (TimeoutException e) {  
            future.cancel(true);  
            System.out.println("TimeoutException异常");
        }
        executorService.shutdownNow(); 
        return result ;
	}
	
	private String fileCompress(String unzipPath,String zipPath){
		String dPath = unzipPath;
		
		if(unzipPath.toLowerCase().endsWith(".z") || unzipPath.toLowerCase().endsWith(".gz")
				|| unzipPath.toLowerCase().endsWith(".zip")){
			return dPath;
		}
		if(unzipPath.toLowerCase().endsWith("o") && unzipPath.toLowerCase().contains(".")){
			dPath = dPath.substring(0,dPath.toLowerCase().lastIndexOf("o"))+"d";
			String cmd = "chmod 777 "+unzipPath +";rnx2crx < \"" + unzipPath + "\" > \"" +dPath+"\";exit";
			int dReturnCode =  execute(cmd);
			
			if(dReturnCode == 0){
				File f = new File(dPath);
				f.delete();
				System.out.println("创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
				return null;
			}
		}
		
		boolean createFileDictory = CopyFileUtil.createFileDictory(zipPath);
		if(!createFileDictory){
			System.out.println("创建d.Z文件时  目录结构失败！"+zipPath);
		}
		//zipPath = dPath+".Z";
		String cmd2 = "chmod 777 "+dPath +";gzip -c -f   \"" + dPath + "\" > \"" +zipPath+"\";exit";
		int zipReturnCode =  execute(cmd2);
		if(zipReturnCode == 0){
			File f = new File(zipPath);
			f.delete();
			System.out.println("创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
			return null;
		} 
		
		System.out.println("创建d.Z文件时  压缩文件成功！ "+cmd2);
		return zipPath;
	}
	
	private int execute(String command){
		
		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(proc
						.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(proc.getOutputStream())), true);
				//logger.info("------正在执行Linux命令(command):【" + "chmod 777 "+cshPath+";csh "+cshPath+";exit】");
				//**************此处代码执行多个任务，至关重要的代码
				out.println(command);
				//logger.info("------command-------" + command);
					String line;
					//while ((line = in.readLine()) != null) {
						//logger.info("------" + line+"------");
					//}
					
					//**************阻塞式依次执行，至关重要的代码
					proc.waitFor();
					
					Thread.sleep(100);
					return 1;
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
			if(proc!=null){
				proc.destroy();
			}
		}
		return 0;
	}
	

	
}				
/*String absolutePath = findFiles.getAbsoluteFile().toString();//获得T02文件路径（临时NAS盘上面的）
boolean copyFile = CopyFileUtil.copyFile(absolutePath, S30SWorkPath+"/"+siteInfo.getSiteNumber(), true);//复制文件
if(copyFile){//复制成功，启动整理
	// 解压缩T02文件
	String command1 = "chmod 777 "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.T02;" +  //开放权限
			          "runpkr00 -g -d " + S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.T02;exit";//解压缩成 tgd
	
	//解压tgd文件，转换成o文件
	String command2 = "chmod 777 "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;" //放开权限
	                 +"teqc +quiet ++err "+S30SWorkPath+"/"+year4+"/"+yearDay+"/teqc.log "+
                            "+obs "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+month+"o " +
		                    "+nav "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+month+"n " +
				            "+met "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+month+"m " +
						"-week "+GpsWeek.getGPSWeek(beforeDayStr)+" "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;exit";
	
	// 删除掉已经存在的m文件和n文件，teqc转换后后不会自动替换原有的m文件和n文件
	String file2Path = S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+month+"n";
	String file3Path = S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+month+"m";
	
	if(!FileTool.deleteFile(file2Path)){System.out.println("test() "+file2Path+" file delete is failed!");}
	if(!FileTool.deleteFile(file3Path)){System.out.println("test() "+file3Path+" file delete is failed!");}
	String flag1 = to2ToTgd(command1);if(flag1==null){continue;}
	String flag2 = to2ToTgd(command2);if(flag2==null){continue;}
	
	//30s文件名称
	String fileNameForO = siteNumber+yearDay+"0."+year2+"o";
	// 执行TEQC将30S的文件转换成YYS文件
	String command3 = "chmod 777 "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+fileNameForO+";teqc +qc "+S30SWorkPath+"/"+year4+"/"+yearDay+"/"+fileNameForO+";exit";
	String result3 = to2ToTgd(command3);
	
	if(result3!=null){
		String  resultSfilePath = S30SWorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"S";
		// 解析结果文件入库
		if(new File(resultSfilePath).length() > 100){
			
			String o_path = S30SWorkPath+"/"+year4+"/"+yearDay+"/"+fileNameForO;
			Z_DataReductionController t = new Z_DataReductionController();
			t.fileCompress(o_path,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");//压缩成d文件，并压缩成d.Z文件
			
			FileInfo jiexiSfile = jiexiSfile(resultSfilePath, "", "");//解析o文件，并将结果入库
			
			fileInfo.setSystemTime(TimeUtils.getTempTime(beforeDayStr));
			fileInfo.setMp1(jiexiSfile.getMp1());
			fileInfo.setMp2(jiexiSfile.getMp2());
			fileInfo.setO_slps(jiexiSfile.getO_slps());
			fileInfo.setEphem_number(jiexiSfile.getEphem_number());
			List list = new ArrayList();list.add(fileInfo);
			applyFileService.insert_30SData_Files(year4, list);
		}else{
			System.out.println("test() "+resultSfilePath+" 文件大小小于100字节，不能上传！");
		}
	}
	
}*/