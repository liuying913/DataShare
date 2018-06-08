package com.highfd.common.ZZBDS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.FTP.Ftp;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.jdbc.service.impl.AlarmJdbcServiceImpl;
import com.linux.GpsWeek;
import com.linux.StaticUtil;
import com.linux.Z_TEQC_LINUX;

/**
 * 由于之前Ftp链接不上，通过T02文件进行回补
 * @author 刘颖
 */
public class ZZbds {

	public static String S30SWorkPath = "/home/liuying/To2Work01";
	public static String S30S_Ftp_WorkPath = "/home/liuying/To2Ftp";
	
	
	public static String S30S_ZipPath = "/root/nfs/Gnssdata/30sdz";//30秒 Zip文件位置
	public static String S30S_To2_Ftp_Path = "Internal/30sd";
	//程序超时时间秒数
	private static int timeoutSeconds = 5;//add 原来60秒
	
	
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
    	String ftpPath = S30S_To2_Ftp_Path+"/"+year4+"/"+month+"/"+day+"/";
    	String fileName = siteNumber+yearDay+"aD.T02";
    	String ftpWorkPath = S30S_Ftp_WorkPath+"/"+year4+"/"+yearDay;
    	
    	//file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
    	System.out.println("准备下载："+ftpPath+" "+fileName +"  下载到"+ftpWorkPath);
    	int ftpStatus = Ftp.downFile(url, "", "", ftpPath, fileName, ftpWorkPath);
    	
    	if(ftpStatus==1){//FTP 下载成功
    		System.out.println(fileName+"   FTP下载成功T02文件");
    		//转成tgd文件
    		String flag1 = t02_To_tgd(t02_file_Ftp_WordPath);if(flag1==null){System.out.println(t02_file_Ftp_WordPath+" to2转tgd文件出错！");return setFileInfo(fileInfo2,6);}
    		//tgd转成o文件
    		String flag2 = tgd_To_o( year4,year2,yearDay,siteNumber,beforeDayStr);if(flag2==null){System.out.println(t02_file_Ftp_WordPath+" tgd转o文件出错！");return setFileInfo(fileInfo2,6);}
    		
    		ZZbds z = new ZZbds();
    		//压缩成d文件，并压缩成d.Z文件
    		z.fileCompress(o_file_Ftp_WorkPath,S30S_ZipPath+"/"+year4+"/"+yearDay+"/"+siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");
    		
    		//o文件转s文件
    		String result3 = o_To_S(o_file_Ftp_WorkPath);
    		if(result3!=null){
    			System.out.println(o_file_Ftp_WorkPath+" 奇迹了  o文件转s文件正确！");
    			fileInfo2 = s_setFileInfo(fileInfo2, s_file_Ftp_WorkPath, false);
    		}else{
    			//启动程序  根据正则表达式计算历元数量
    			System.out.println(o_file_Ftp_WorkPath+" o文件转s文件出错！启动程序  根据正则表达式计算历元数量");
				FileInfo oSupplAnaly = o_supplAnaly(fileInfo2,o_file_Ftp_WorkPath,false);	
				fileInfo2.setEphem_number(oSupplAnaly.getEphem_number());
				fileInfo2.setMp1("-1");
				fileInfo2.setMp2("-1");
				fileInfo2.setO_slps("-1");
				fileInfo2.setFileFlag(oSupplAnaly.getFileFlag());
    			return fileInfo2;
    		}
    	}else{
    		System.out.println("下载失败！");
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
			System.out.println("***********8结算S文件出现问题！！！！");
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
			System.out.println("计算结果"+fileInfo.getSiteNumber()+"   "+fileInfo.getEphem_number() +"   "+fileInfo.getFileFlag() );
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
	public static FileInfo o_supplAnaly(FileInfo fileInfo,String o_file_WorkPath,boolean flag){
		int lyNumber = ZZbds.txt2String(o_file_WorkPath);
		fileInfo.setEphem_number(lyNumber+"");
		fileInfo.setMp1("-1");
		fileInfo.setMp2("-1");
		fileInfo.setO_slps("-1");
		//fileInfo.setFileFlag(7);//通过正则表达式搞的
		//从临时NAS盘获得的o文件
		if(flag){//是否是补回操作
			fileInfo.setFileFlag(Integer.valueOf(lyNumber)>=600?1:3);
		}else{
			fileInfo.setFileFlag(Integer.valueOf(lyNumber)>=600?2:3);
		}
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
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public static void main2(String []abc) throws ParseException, InterruptedException{
		// 16  8  5
		// 13  1  1  0  0  0.0000000  0 20G17G28G10G09G04G08R04G01R16R10R17R18
		 String regex = "\\s+[0-9]{2}\\s+[0-9]{1,2}\\s+[1-9]{1,2}.*";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(" 13  1  1  0  0  0.0000000  0 20G17G28G10G09G04G08R04G01R16R10R17R18");  
		 if(matcher.matches()){	
			 System.out.println("111");
		 }else{
			 System.out.println("222");
		 }
	}*/
	
	
	/**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static int txt2String(String  filePath){
    	File file = new File(filePath);
        int lyNumber=0;
        int errNumber=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            
            String regex = "\\s+[0-9]{2}\\s+[0-9]{1,2}\\s+[1-9]{1,2}.*";  
       		Pattern pattern = Pattern.compile(regex); 
       		
       		String regex2 = ".*0000000  0  0+.*";  
       		Pattern pattern2 = Pattern.compile(regex2); 
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            
	       		Matcher matcher = pattern.matcher(s);  
	       		if(matcher.matches()){	
	       			//System.out.println(s);
	       			lyNumber++;
	       		}else{
	       			//System.out.println("222");
	       		}
	       		
	       		Matcher matcher2 = pattern2.matcher(s);  
	       		if(matcher2.matches()){	
	       			errNumber++;
	       		}else{
	       			//System.out.println("222");
	       		}
            }
            System.out.println(lyNumber +"  历元数量  "+ errNumber);
            if(lyNumber>2880){
            	System.out.println(file.getName()+"  历元数量大于2880");
            }else{
            	lyNumber = lyNumber - errNumber;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return lyNumber;
    }
    
    
}
