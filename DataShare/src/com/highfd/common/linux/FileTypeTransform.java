package com.highfd.common.linux;

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
import java.text.ParseException;
import java.util.ArrayList;
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

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.FTP.Ftp;
import com.highfd.common.ZZBDS.ZZbds;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.common.time.TimeUtils;
import com.highfd.controller.Z_DataSupplyController;
import com.highfd.service.ApplyFileService;
import com.linux.GpsWeek;
import com.linux.Z_TEQC_LINUX;

public class FileTypeTransform {

	public static String S30SBasePath = "/root/tmp_nas/gnssdata/CMONOC-GNSS";//30S 临时nas盘 o文件（人家的接收机存储文件）
	public static String S30SWorkPath = "/home/liuying/To2Work01";
	public static String S30S_ZipPath = "/root/nfs/Gnssdata/30sdz";//最终位置  30秒 Zip文件位置
	public static String S30S_Ftp_WorkPath = "/home/liuying/To2Ftp";
	public static String S30S_To2_Ftp_Path = "Internal/30sd";
	//程序超时时间秒数
	private static int timeoutSeconds = 20;//add 原来60秒
	private static String fileLogName = "";
	
	public FileTypeTransform(){
		fileLogName = Z_DataSupplyController.fileLogName;
	}
	
	/**
	 * 获得o文件里面的数值
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
	 * 解析TEQC最终生成的文件，进行入库操作
	 * 
	 * @param SfilePath
	 * @param siteNumber
	 * @param beforreDayStr 
	 */
	@SuppressWarnings("resource")
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
						System.out.println("历元分析：  "+SfilePath+"分析结果："+" "+ map.get(allLength-4)+" "+ map.get(allLength - 3)+" "+ map.get(allLength - 2)+" "+ map.get(allLength - 1));
					}
					
					if (lineTXT.contains("Epochs w/ observations")) {
						if(lineTXT.contains(":")){
							String ss[] = lineTXT.split(":");
							if(ss.length==2){
								info.setEphem_number(ss[1].trim());
								CopyFileUtil.writeTxtFile(fileLogName, "历元数量："+info.getEphem_number());
							}else{
								CopyFileUtil.writeTxtFile(fileLogName, "历元数量解析有问题A："+lineTXT);
							}
						}else{
							CopyFileUtil.writeTxtFile(fileLogName, "历元数量解析有问题B："+lineTXT);
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
			System.out.println("jiexiSfile() siteNumber:[" + siteNumber + "],time:[" + beforreDayStr + "] YYS result file not exist!");
		}
		return null;
	}
	
	
	
	public static String t02_To_tgd(String t02_file_Ftp_WordPath){
		String commandTo2_To_Tgd = "chmod 777 "+t02_file_Ftp_WordPath+"; runpkr00 -g -d " +t02_file_Ftp_WordPath+";exit";//解压缩成 tgd
		String result = to2ToTgd(commandTo2_To_Tgd);
		
		String tgdFileName = t02_file_Ftp_WordPath.replace("T02", "tgd");
		try {
			CopyFileUtil.writeTxtFile(fileLogName, "T02转tgd命令： "+commandTo2_To_Tgd);
			if(CopyFileUtil.fileExist(tgdFileName)){
				CopyFileUtil.writeTxtFile(fileLogName, "T02转tgd成功！！！     返回值: "+result);
				if(null==result){
					CopyFileUtil.writeTxtFile(fileLogName, "T02转tgd成功！！！     重点关注！！！！！！！！");
				}
				return result;
			}else{
				CopyFileUtil.writeTxtFile(fileLogName, "T02转tgd失败： ");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String tgd_To_o(String year4,String year2,String yearDay,String siteNumber,String beforeDayStr){
		String commandTgd_To_o = "chmod 777 "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;" //放开权限
                        +"teqc +quiet ++err "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/teqc.log "+
                             		  "+obs "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"o " +
                             		  "+nav "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"n " +
	                                  "+met "+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"m " +
                                      "-week "+GpsWeek.getGPSWeek(beforeDayStr)+" " +
		                                   ""+S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toUpperCase()+yearDay+"aD.tgd;exit";
		System.out.println("  tgd-o: "+commandTgd_To_o);
		String result = to2ToTgd(commandTgd_To_o);
		
		String oFileName = S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay+"/"+siteNumber+yearDay+"0."+year2+"o";
		try {
			CopyFileUtil.writeTxtFile(fileLogName, "tgd转o命令： "+commandTgd_To_o);
			if(CopyFileUtil.fileExist(oFileName)){
				CopyFileUtil.writeTxtFile(fileLogName, "tgd转o成功！！！      返回值: "+result);
				if(null==result){
					CopyFileUtil.writeTxtFile(fileLogName, "tgd转o成功！！！      重点关注下！！！！！！！");
				}
				return result;
			}else{
				CopyFileUtil.writeTxtFile(fileLogName, "tgd转o失败： "+oFileName);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String o_To_S(String o_file_WorkPath){
		String command_O_S = "chmod 777 "+o_file_WorkPath+";teqc +qc "+o_file_WorkPath+";exit";
		System.out.println("  o-s: "+command_O_S);
		String result = to2ToTgd(command_O_S);
		String s_file_WorkPath = o_file_WorkPath.substring(0, o_file_WorkPath.length()-1)+"S";
		try {
			CopyFileUtil.writeTxtFile(fileLogName, "o转s命令： "+command_O_S);
			if(CopyFileUtil.fileExist(s_file_WorkPath)){
				CopyFileUtil.writeTxtFile(fileLogName, "o转s成功！！！     返回值: "+result);
				if(null==result){
					CopyFileUtil.writeTxtFile(fileLogName, "o转s成功！！！     重点研究下！！！！！！");
				}
				return "1";
			}else{
				CopyFileUtil.writeTxtFile(fileLogName, "o转s失败： "+o_file_WorkPath);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * d.Z文件解压成o文件
	 */
	private boolean dz_To_O(String oPathAndName,String zPathAndName){
		String dPath = oPathAndName;
		dPath = dPath.substring(0,dPath.toLowerCase().lastIndexOf("o"))+"d";
		String cmd2 = "chmod 777 "+zPathAndName +";uncompress -c -f   \"" + zPathAndName + "\" > \"" +dPath+"\";exit";
		CopyFileUtil.writeTxtFile(fileLogName, "d.Z解压命令："+cmd2);
		int zipReturnCode =  execute(cmd2);
		if(zipReturnCode == 0){
			File f = new File(zPathAndName);
			f.delete();
			CopyFileUtil.writeTxtFile(fileLogName, "d.Z解压失败！！！");
			return false;
		} 
			
		String cmd = "chmod 777 "+dPath +";crx2rnx < \"" + dPath + "\" > \"" +oPathAndName+"\";exit";
		CopyFileUtil.writeTxtFile(fileLogName, "d.Z转o文件命令："+cmd);
		int dReturnCode =  execute(cmd);
		if(dReturnCode == 0){
			File f = new File(dPath);
			f.delete();
			CopyFileUtil.writeTxtFile(fileLogName, "d.Z转o文件失败！！！");
			return false;
		}
		return true;
	}

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
	
	
	
	//从FTP上面下载To2文件
	public static int T02_Down(SiteInfo siteInfo,String year4,String month,String day,String yearDay){
		
		String siteNumber = siteInfo.getSiteNumber();
		
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
    																	CopyFileUtil.writeTxtFile(fileLogName, "ftp下载T02文件成功："+url+" "+ftpWorkPath+"/"+fileName);
    		System.out.println("FTP下载成功T02文件："+fileName);
    	}
    	return ftpStatus;
	}
	
	
	

	
	public static String fileTypeChange(String t02_file_Ftp_WordPath,String o_file_Ftp_WorkPath,FileInfo fileInfo2,String siteNumber,String year4,String year2,String yearDay,String beforeDayStr){
		
		//转成tgd文件
		String flag1 = FileTypeTransform.t02_To_tgd(t02_file_Ftp_WordPath);
		if(flag1==null){
			return "bad";
		}
		//tgd转成o文件
		String flag2 = FileTypeTransform.tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);
		if(flag2==null){
			return "bad";
		}
		//o文件转s文件
		String result3 = FileTypeTransform.o_To_S(o_file_Ftp_WorkPath);
		if(result3==null){
			return null;
		}
		return result3;
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
	
	//通过正则表达式 计算历元数
	public void goToDB(String year4, FileInfo fileInfo,String o_file, String z_file_ResultPath,ApplyFileService applyFileService){
		
		FileTypeTransform t = new FileTypeTransform();
		//入库操作
		if(fileInfo.getFileFlag()!=1 && fileInfo.getFileFlag()!=2 && fileInfo.getFileFlag()!=3){//ftp下载的有问题,那么还是以o文件为准
			CopyFileUtil.writeTxtFile(fileLogName, "没有入库操作！！！ ");
		}else{
			t.fileCompress(o_file,z_file_ResultPath);//压缩成d文件，并压缩成d.Z文件
			fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
		}
		
		FileInfo select_30sDataFiles = applyFileService.select_30SData_Files(year4, fileInfo);
		if(select_30sDataFiles==null){
			List<FileInfo> list = new ArrayList<FileInfo>();list.add(fileInfo);applyFileService.insert_30SData_Files(year4, list);//入 关系库
		}else{
			applyFileService.update_30S_apply(year4, fileInfo);
		}
	}
	
	public static boolean isNumeric(String str){
	   for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		      return false;
		   }
	   }
	   return true;
	}
	
	private String fileCompress(String unzipPath,String zipPath){
		String dPath = unzipPath;
		if(unzipPath.toLowerCase().endsWith(".z") || unzipPath.toLowerCase().endsWith(".gz") || unzipPath.toLowerCase().endsWith(".zip")){
			return dPath;
		}
		if(unzipPath.toLowerCase().endsWith("o") && unzipPath.toLowerCase().contains(".")){
			dPath = dPath.substring(0,dPath.toLowerCase().lastIndexOf("o"))+"d";
			String cmd = "chmod 777 "+unzipPath +";rnx2crx < \"" + unzipPath + "\" > \"" +dPath+"\";exit";
			CopyFileUtil.writeTxtFile(fileLogName, "执行压缩操作: "+cmd);
			int dReturnCode =  execute(cmd);
			if(dReturnCode == 0){
				File f = new File(dPath);
				f.delete();
				CopyFileUtil.writeTxtFile(fileLogName, "创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
				return null;
			}
		}
		
		boolean createFileDictory = CopyFileUtil.createFileDictory(zipPath);
		if(!createFileDictory){
			System.out.println("创建d.Z文件时  目录结构失败！"+zipPath);
		}
		String cmd2 = "chmod 777 "+dPath +";compress -c -f   \"" + dPath + "\" > \"" +zipPath+"\";exit";
		CopyFileUtil.writeTxtFile(fileLogName, "执行生成d.Z操作: "+cmd2);
		int zipReturnCode =  execute(cmd2);
		if(zipReturnCode == 0){
			File f = new File(zipPath);
			f.delete();
			CopyFileUtil.writeTxtFile(fileLogName, "创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
			return null;
		} 
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
				in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
				out.println(command);
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
	
	//从FTP上面下载To2文件进行解析
	
	//解析T02， 并且排除正则表达式
	
	
	public static void main(String[] args) throws Exception {
		String path = "D:\\tomcat\\dZ";
		analysisAll_o(new File(path), null);
	}
	
	//查询所有的o文件，并进行压缩 转换 入库
	public static void analysisAll_o(final File dir,ApplyFileService applyFileService) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		FileTypeTransform t = new FileTypeTransform();
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_o(fs[i], applyFileService);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("o")){//主要小写这里
					FileInfo gnssFileInfo = FileTypeTransform.getFileInfoBy_o_Path(fs[i]);
					String year2= gnssFileInfo.getFileYear().substring(2,4);
					String z_file_ResultPath =  S30S_ZipPath+"/"+gnssFileInfo.getFileYear()+"/"+gnssFileInfo.getFileDayYear()+"/"+gnssFileInfo.getSiteNumber().toLowerCase()+gnssFileInfo.getFileDayYear()+"0."+year2+"d.Z";
					t.goToDB(gnssFileInfo.getFileYear() ,  gnssFileInfo, fs[i].getPath(),  z_file_ResultPath ,applyFileService);//入库操作
				}
			}
		}
	}
	
	//根据o文件路径，返回文件对象
	public static FileInfo getFileInfoBy_o_Path(File oFile){
		FileInfo fileInfo = FileTool.getFileInfo(oFile.getName());
		String o_file_Ftp_WorkPath = oFile.getPath();
		String s_file_Ftp_WorkPath = oFile.getPath().substring(0, oFile.getPath().length()-1)+"S";
		String result = o_To_S(oFile.getPath());
		if(result==null){//o转s文件失败！  启动程序  根据正则表达式计算历元数量
			CopyFileUtil.writeTxtFile(fileLogName, "启动正则表达式！！！");
			fileInfo = o_supplAnaly(fileInfo,o_file_Ftp_WorkPath,false);	
			return fileInfo;
		}else{//转o文件成功
			fileInfo = s_setFileInfo(fileInfo, s_file_Ftp_WorkPath, false);
		}
		return fileInfo;
	}
	
	
	
	public static String getT02Year(File file){
		try{
		    String year = file.getParentFile().getName();
		    int yearNum = Integer.valueOf(year);
		    String todayYear = TimeUtils.getSysYear();
		    if(yearNum>=2000 && yearNum<=Integer.valueOf(todayYear)){
			  return yearNum+"";
		    }
		    return null;
	    }catch(Exception e){
	    	return null;
		}
	}
	
	
	//遍历、解析所有的T02文件
	public static void analysisAll_T02(final File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_T02(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				//System.out.println(fileName);
				if(fileName.toLowerCase().endsWith("ad.t02")){//主要小写这里
					boolean getFileInfoBy_to2_Path = getFileInfoBy_to2_Path(fs[i]);
					CopyFileUtil.writeTxtFile(Z_DataSupplyController.fileLogName, "to2转o是否成功： "+getFileInfoBy_to2_Path);
				}
			}
		}
	}
	
	//根据t02文件  转成o文件
	public static boolean getFileInfoBy_to2_Path(File to2File){
		try {
			String year4 = getT02Year(to2File);
			String year2 = year4.substring(2, 4);
			String to2PathAndName = to2File.getPath();
			String fileName = to2File.getName();
			String siteNumber = fileName.substring(0, 4).toUpperCase();
			String yearDay = fileName.substring(4, 7);
			String todayStr = DayNumberOfOneYear.yearDayToDate(Integer.valueOf(yearDay), year4);
			return to2_To_O(to2PathAndName, siteNumber, year4, year2, yearDay, todayStr);
		}catch (NumberFormatException e){
			e.printStackTrace();
			return false;
		}catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * to2 转换成o文件
	 */
	public static boolean to2_To_O(String t02_file_Ftp_WordPath,String siteNumber,String year4,String year2,String yearDay,String beforeDayStr){
		//转成tgd文件
		String flag1 = FileTypeTransform.t02_To_tgd(t02_file_Ftp_WordPath);
		if(flag1==null){
			return false;
		}
		//tgd转成o文件
		String flag2 = FileTypeTransform.tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);
		if(flag2==null){
			return false;
		}
		return true;
	}
	
	

	
	//遍历、解析所有的T02文件
	public static void analysisAll_dZ(final File dir) throws Exception{
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_dZ(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("d.z")){//主要小写这里
					boolean fileInfoByDZPath = getFileInfoBy_dZ_Path(fs[i]);
					CopyFileUtil.writeTxtFile(Z_DataSupplyController.fileLogName, "d.Z转o是否成功： "+fileInfoByDZPath);
				}
			}
		}
	}
	
	//根据d.Z文件  转成o文件
	public static boolean getFileInfoBy_dZ_Path(File dzFile){
		String zPathAndName = dzFile.getPath();
		
		String year2 = zPathAndName.substring(zPathAndName.length()-5, zPathAndName.length()-3);
		String oName = dzFile.getName().toUpperCase().substring(0,7)+"0."+year2+"o";
		String oPathAndName = dzFile.getParent()+"/"+oName;
		
		FileTypeTransform ftr = new FileTypeTransform();
		boolean dzToO = ftr.dz_To_O(oPathAndName, zPathAndName);
		return dzToO;
	}

	
	//通过正则表达式 计算历元数
	public FileInfo Zbds(FileInfo fileInfo3,String o_file_Ftp_WorkPath,boolean flag){
		
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
		
		System.out.println(o_file_Ftp_WorkPath+" 通过正则表达式计算历元数量");
		FileInfo oSupplAnaly = o_supplAnaly(fileInfo2,o_file_Ftp_WorkPath,flag);	
		fileInfo2.setEphem_number(oSupplAnaly.getEphem_number());
		fileInfo2.setMp1("-1");
		fileInfo2.setMp2("-1");
		fileInfo2.setO_slps("-1");
		fileInfo2.setFileFlag(oSupplAnaly.getFileFlag());
		return fileInfo2;
	}
	
	/**
	 * 补充抓取
	 * @param flag  是否是通过FTP下载 进行的o文件到s文件
	 */
	public static FileInfo o_supplAnaly(FileInfo fileInfo,String o_file_WorkPath,boolean flag){
		int lyNumber = ZZbds.txt2String(o_file_WorkPath);
		fileInfo.setEphem_number(lyNumber+"");
		CopyFileUtil.writeTxtFile(fileLogName, "历元数量："+lyNumber+"");
		fileInfo.setMp1("-1");
		fileInfo.setMp2("-1");
		fileInfo.setO_slps("-1");
		if(flag){//是否是补回操作
			fileInfo.setFileFlag(Integer.valueOf(lyNumber)>=600?1:3);
		}else{
			fileInfo.setFileFlag(Integer.valueOf(lyNumber)>=600?2:3);
		}
		return fileInfo;
	}
	
}
