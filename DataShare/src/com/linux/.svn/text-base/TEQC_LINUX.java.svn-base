package com.linux;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;
public class TEQC_LINUX implements Runnable {

	//程序超时时间秒数
	private static int timeoutSeconds = 60;
	public void run() {
		test();
	}

	private static Logger logger = Logger.getLogger(TEQC_LINUX.class);

	/**
	 * 将历时文件夹下面的文件全部清空
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteHistoryFile(String path) {
		File f = new File(path);

		if (!f.exists()) {
			logger.error("deleteHistoryFile() " + path + " not exist!");
			return false;
		}
		if (f.isFile()) {
			logger.error("deleteHistoryFile() " + path + " is a file!");
			return false;
		}

		File oneFile = null;
		boolean delRes = false;
		if (f.isDirectory()) {

			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				oneFile = files[i];
				if (oneFile.isFile()) {
					delRes = oneFile.delete();
					if (!delRes) {
						logger.error("deleteHistoryFile() "
								+ oneFile.getAbsolutePath()
								+ " file delete is failed!");
					}
				}
			}
		}
		f.delete();
		return true;

	}

	private boolean deleteFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			logger.error("deleteFile() " + filePath + "  file is not exist!");
			return false;
		}
		if (f.isDirectory()) {
			logger.error("deleteFile() " + filePath
					+ " is directory,delete file is failed!");
			return false;
		}
		return f.delete();
	}

	public static void main(String[] args) {
		// TEQC_LINUX t = new TEQC_LINUX();
		// t.test();

		// deleteHistoryFile("E:\\test\\2013\\141");
//
//		// 当前日期前一天的日期
//		String beforeDayStr = StaticUtil.getStrDateByBeforeDays(22);
//		//07-19 -07-22
//
//		int doy = StaticUtil.getCountDaysByNYR(beforeDayStr);
//
//		System.out.println(doy);
//		
		//System.out.println(StaticUtil.getCountDaysByNYR("2013-07-19"));
		//System.out.println(StaticUtil.getCountDaysByNYR("2013-07-22"));
		
//		String fullpath = "D:\\teqc\\rinex\\rinex\\2013\\010\\BJFS0100.13r";
//		TEQC_LINUX t = new TEQC_LINUX();
//		System.out.println(t.getAllStr(fullpath));
	}
	

	public void test() {
		
		logger.info("test() teqc begin!");
		StaticConstant sc = StaticConstant.getInstance();
		
		// teqc 命令
		String teqcPath = "teqc";// 换成LINUX环境
		// T02文件解压缩软件路径
		String jieyasuoPath = "runpkr00";
		
		// 本地每日24个RINEX文件存在的路径，这个可以灵活配置
		String rinexPathForLinux = sc.getRinexPathForLinux();
		
		// 当前日期前一天的日期
		String beforeDayStr = StaticUtil.getStrDateByBeforeDays(1);
		
		String twoYUE = beforeDayStr.substring(5, 7);
		String twoRI = beforeDayStr.substring(8, 10);
		
		int doy= StaticUtil.getCountDaysByNYR(beforeDayStr);
		// 年纪日 001,065,123
		String doyThreeLength =StaticUtil.getThreeLengthCountDays(doy);
		
		String yearFourLength = StaticUtil.getYearByNYR(beforeDayStr)+"";
		String yearTwoLength =StaticUtil.getYearTwoLength(beforeDayStr);
		
		// 所有站点编号以及对应的接收机IP
		String allGnssSiteNumberStr = getAllGnssSiteNumberAndReceiveIp();
		
		// 单个站点编号
		String siteNumber = null;
		
		// 接收机IP
		String siteIp = null;
		// //历史文件清除的日期 172.128.9.65 的机器去除历史文件删除的操作
		// int delHisDay = sc.getDelHisDay();
		// String historyTime = StaticUtil.getStrDateByBeforeDays(delHisDay);
		// int hisDoy= StaticUtil.getCountDaysByNYR(historyTime);
		// //年纪日 001,065,123
		// String hisDoyThreeLength =StaticUtil.getThreeLengthCountDays(hisDoy);
		// String hisYearFourLength = StaticUtil.getYearByNYR(historyTime)+"";
		//		
		// if(!deleteHistoryFile(rinexPathForLinux+hisYearFourLength+"/"+hisDoyThreeLength)){
		// logger.error("test() history file delete failed!");
		// }
		// T02文件在接口机中的主目录，用于1hz文件不全时候ftp下载使用
		String fileapthOfT02 = null;
		if(allGnssSiteNumberStr != null && !("").equals(allGnssSiteNumberStr)){
			
			String siteNumbers [] = allGnssSiteNumberStr.split(",");
			
			if(siteNumbers != null){
				for (int i = 0; i < siteNumbers.length; i++) {
					
					siteNumber = siteNumbers[i].split(";")[0].trim().toLowerCase();
					
					logger.info("*********siteNumber:"+siteNumber+"*************");
					siteIp = siteNumbers[i].split(";")[1].trim();
					fileapthOfT02 = siteNumbers[i].split(";")[2].trim();     // to2 下载地址
					
					// 30S文件来源 0正常，-1文件补全
					String state_30S = "";
					
					String siteNumberAndDoy = siteNumber+doyThreeLength;     //
					
					// 24个文件完整是否完整判断
					if(isFull(siteNumber,"GNSS_RINEX_1HZ",beforeDayStr,
							rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"0."+yearTwoLength+"r",
							rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/",
							yearTwoLength,doyThreeLength)){
						
						// 24个1HZ文件转成1个1HZ文件命令
						// teqc bjfs001a.13o bjfs001b.13o bjfs001c.13o
						// bjfs001d.13o bjfs001e.13o bjfs001f.13o bjfs001g.13o
						// bjfs001h.13o bjfs001i.13o bjfs001j.13o bjfs001k.13o
						// bjfs001l.13o bjfs001m.13o bjfs001n.13o bjfs001o.13o
						// bjfs001p.13o bjfs001q.13o bjfs001r.13o bjfs001s.13o
						// bjfs001t.13o bjfs001u.13o bjfs001v.13o bjfs001w.13o
						// bjfs001x.13o > bjfs001.13o
						StringBuffer command1 = new StringBuffer("");
						
						// command1.append("cmd /c "+teqcPath+" ");
						// 换成LINUX环境
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"a."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"b."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"c."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"d."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"e."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"f."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"g."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"h."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"i."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"j."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"k."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"l."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"m."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"n."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"o."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"p."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"q."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"r."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"s."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"t."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"u."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"v."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"w."+yearTwoLength+"o;");
						command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"x."+yearTwoLength+"o;");
						
						//这里将TEQC报的错误信息当到一个日志文件中，此项设置重要
						command1.append(teqcPath+" +quiet ++err "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/teqc.log ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"a."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"b."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"c."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"d."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"e."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"f."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"g."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"h."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"i."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"j."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"k."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"l."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"m."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"n."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"o."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"p."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"q."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"r."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"s."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"t."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"u."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"v."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"w."+yearTwoLength+"o ");
						command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"x."+yearTwoLength+"o ");
						command1.append("> "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o;exit");
						logger.info("command1:"+command1.toString());
						
						//2016-1-23 add 添加程序卡死代码 wujian
						String result = null;
						result = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1.toString() } );
						
						if(result==null){
							logger.error("*****executeFileForLinux 方法执行超时*****【"+command1+"】");
							continue;
						}
						// add end
						
						// 1个1HZ文件转换成1个30s的文件命令
						String command2 = "chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o;"+teqcPath+" -O.dec 30 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o > "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"0."+yearTwoLength+"o;exit";
						
						logger.info("command2:"+command2.toString());
						//2016-1-23 add 添加程序卡死代码 wujian
						String result4 = null;
						result4 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command2 } );
						
						if(result4==null){
							logger.error("*****executeFileForLinux 方法执行超时*****【"+command2+"】");
							continue;
						}
						state_30S = "0";
						
					}else{
						logger.info("**********************TO2***********************");
						// 自动续传功能，支持3次断开重连,20秒相应超时时间限制
						String command   = "cd "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+";wget -T 20 -c -t 3 ftp://"+siteIp+fileapthOfT02+"/"+yearFourLength+"/"+twoYUE+"/"+twoRI+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.T02;exit";
						
						File f = new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength);
						if(!f.exists()){
							f.mkdirs();
						}
						//2016-1-23 注销
//						logger.info("******command***:"+command);
//						if(!executeFileForLinux(command)){
//							logger.error("test() command:["+command.toString()+"] is failed!");
//							continue ;
//						}
						//2016-1-23 add 
						//超时执行的话 结果还是null
						String result0 = null;
						
						//卡死设置1分钟
						result0 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command } );
						
						if(result0==null){
							logger.error("*****executeFileForLinux 方法执行超时*****【"+command+"】");
							continue;
						}
						//2016-1-23 add over
						
						// T02下载成功的时候继续执行
						if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.T02").exists()){
							// 解压缩T02文件
							String command1 = "chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.T02;"// 权限放开
							+jieyasuoPath+" -g -d " + rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.T02;exit";//   解压缩  shegnc tgd
							// 执行解压缩的文件，生成m文件n文件和30s文件
							String command2 = "chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.tgd;" //放开权限
							
							//生存o文件
							+teqcPath+" +quiet ++err "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/teqc.log "+
							"+obs "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o " +
									"+nav "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"n " +
											"+met "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"m " +
													"-week "+GpsWeek.getGPSWeek(beforeDayStr)+" "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber.toUpperCase()+doyThreeLength+"aD.tgd;exit";
							
							
							// 删除掉已经存在的m文件和n文件，teqc转换后后不会自动替换原有的m文件和n文件
							String file2Path = rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"n";
							String file3Path = rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"m";
							
							if(!deleteFile(file2Path)){
								logger.error("test() "+file2Path+" file delete is failed!");
							}
							if(!deleteFile(file3Path)){
								logger.error("test() "+file3Path+" file delete is failed!");
							}
//							if(!executeFileForLinux(command1)){
//								logger.error("test() command:["+command1+"] unpress T02 file error!");
//								continue ;
//							}
//							if(!executeFileForLinux(command2)){
//								logger.error("test() command:["+command2+"] unpress T02 file to 30S and m file and n file is failed!");
//								continue ;
//							}
							
							//2016-1-23 add 
							//超时执行的话 结果还是null
							String result1 = null;
							result1 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1 } );
							
							if(result1==null){
								logger.error("*****executeFileForLinux 方法执行超时*****【"+command1+"】");
								continue;
							}
							String result2 = null;
							result2 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command2 } );
							
							if(result2==null){
								logger.error("*****executeFileForLinux 方法执行超时*****【"+command2+"】");
								continue;
							}
							//2016-1-23 add over
							
							state_30S = "-1";
						
						}else{
							// T02 下载失败！
							logger.error("test() command:["+command+"] "+siteNumber+ " T02 file download is failed!");
							//T02 下载失败的时候，强行执行将现有的1HZ文件转换成30S文件的操作，此操作存在风险，可能会引起TEQC崩溃
							logger.info("test() *****正在将不全的1HZ文件强制转换成30S文件******");
							String r_file_fullpath =
							rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"0."+yearTwoLength+"r";
							String allStr = getAllStr(r_file_fullpath);//abcdefhilm...
							
							StringBuffer command1 = new StringBuffer("");
							
							if(allStr!=null && !"".equals(allStr)){
								for (int j = 0; j < allStr.length(); j++) {
									
									command1.append("chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+allStr.charAt(j)+"."+yearTwoLength+"o;");
								}
								//这里将TEQC报的错误信息当到一个日志文件中，此项设置重要
								command1.append(teqcPath+" +quiet ++err "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/teqc.log ");
																
								for (int m = 0; m < allStr.length(); m++) {
																	
									command1.append(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+allStr.charAt(m)+"."+yearTwoLength+"o ");
								}
								command1.append("> "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o;exit");
								String command2 = "chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o;"+teqcPath+" -O.dec 30 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"."+yearTwoLength+"o > "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumberAndDoy+"0."+yearTwoLength+"o;exit";
								
								logger.info("command1:"+command1.toString());
//								if(!executeFileForLinux(command1.toString())){
//									logger.error("test() 多个不全1HZ文件转换1个总的1HZ文件命令 commmand1:["+command1.toString()+"] is failed!");
//									continue ;
//									
//								}
								String result = null;
								result = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1.toString() } );//2016-1-31 update 注意最后参数类型是String不是StringBuffer
								
								if(result==null){
									logger.error("*****executeFileForLinux 方法执行超时*****【"+command1+"】");
									continue;
								}
//								logger.info("command2:"+command2.toString());
//								if(!executeFileForLinux(command2)){
//									logger.error("test() 1个总的不全的1HZ文件转换30S文件命令 commmand2:["+command2.toString()+"] is failed!");
//									continue;
//								}
								String result1 = null;
								result1 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command2 } );
								
								if(result1==null){
									logger.error("*****executeFileForLinux 方法执行超时*****【"+command2+"】");
									continue;
								}
								
								//不全的1HZ文件转换成30S文件的状态
								state_30S = "-2";
								// 将1hz文件不全转换的30s文件入库
								if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o").length() > 100){
									saveFileToNac(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o", siteNumber, "GNSS_RINEX_30S", beforeDayStr+" 0:0:0",state_30S);
								}else{
									logger.error("test() 1HZ文件不全生成的30S文件："+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o"+" 文件大小小于100字节，不能上传！");
								}
							}
							continue;
						}
						
					}
					
					// 30s文件名称
					String fileNameForO = siteNumber+doyThreeLength+"0."+yearTwoLength+"o";
					
					// 执行TEQC将30S的文件转换成YYS文件
					String command3 = "chmod 777 "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+fileNameForO+";"+ teqcPath+" +qc "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+fileNameForO+";exit";
					
					String result1 = null;
					result1 = callMethod(new TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command3 } );
					
					if(result1==null){
						logger.error("*****executeFileForLinux 方法执行超时*****【"+command3+"】");
						continue;
					}
					
					if(result1!=null){
						
						// TEQC执行完毕后的S文件的路径
						String  resultSfilePath = rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"S";
						
						logger.info("resultSfilePath:"+resultSfilePath);
						// 解析结果文件入库
						if(new File(resultSfilePath).length() > 100){
							jiexiSfile(resultSfilePath, siteNumber.toUpperCase(), beforeDayStr); //oracle
						}else{
							logger.error("test() "+resultSfilePath+" 文件大小小于100字节，不能上传！");
						}
						
						// YYS文件入库
						if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"S").length() > 100){
							saveFileToNac(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"S", siteNumber, "GNSS_SOLUTIONS_TEQC", beforeDayStr+" 0:0:0",state_30S);
						}else{
							logger.error("test() "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"S"+" 文件大小小于100字节，不能上传！");
						}
						
						// 30s文件入库
						if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o").length() > 100){
							saveFileToNac(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o", siteNumber, "GNSS_RINEX_30S", beforeDayStr+" 0:0:0",state_30S);
						}else{
							logger.error("test() "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"o"+" 文件大小小于100字节，不能上传！");
						}
						
						// m文件入库
						if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"m").length() > 100){
							saveFileToNac(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"m", siteNumber, "GNSS_RINEX_MET", beforeDayStr+" 0:0:0",state_30S);
						}else{
							logger.error("test() "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"m"+" 文件大小小于100字节，不能上传！");
						}
						
						// n文件入库
						if(new File(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"n").length() > 100){
							saveFileToNac(rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"n", siteNumber, "GNSS_RINEX_NAV", beforeDayStr+" 0:0:0",state_30S);
						}else{
							logger.error("test() "+rinexPathForLinux+yearFourLength+"/"+doyThreeLength+"/"+siteNumber+doyThreeLength+"0."+yearTwoLength+"n"+" 文件大小小于100字节，不能上传！");
						}
					}
					
					// 执行30S文件的来源的插入
					// insert30SStateByTime(siteNumber, beforeDayStr,
					// state_30S);
				}
			}
		}
		logger.info("test() teqc end!");
	}

	public static boolean convert(String command, String filePath) {
		Runtime runtime = Runtime.getRuntime();
		boolean b = false;
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					outputStream);
			Process process = runtime.exec(command);
			InputStream bi = new BufferedInputStream(process.getInputStream());
			byte[] bs = new byte[600];
			int a = 0;
			while ((a = bi.read(bs)) != -1) {
				outputStream.write(bs, 0, a);
			}
			bi.close();
			outputStream.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 方法描述：存储数据质量信息
	 * 
	 * @return boolean false 失败，true 成功
	 */
	public static void insertStationQualityInfo(String siteNumber, String time,
			String info1, String info2, String info3, String info4, String info5) {

		// 先执行删除操作，避免
		String selectSql = "SELECT * FROM TEQC_INFO WHERE SSSS= ? AND DATATIME = ?";

		String insertSql = "INSERT INTO TEQC_INFO (ID,GCSC,SJYXX,MP1,MP2,GCWYTB,DATATIME,SSSS,CREATETIME) VALUES (?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		String getIdSql = "SELECT TEQC_INFO_SEQ.NEXTVAL FROM DUAL";
		PreparedStatement st = null;
		ResultSet rt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (info1.equals("n/a")) {
			info1 = "0";
		}

		if (info2.equals("n/a")) {
			info2 = "0";
		}

		if (info3.equals("n/a")) {
			info3 = "0";
		}

		if (info4.equals("n/a")) {
			info4 = "0";
		}
		if (info5.equals("n/a")) {
			info5 = "0";
		}

		try {
			con = ConnectionSource.getConnection();
			boolean defaultCommit = con.getAutoCommit();
			// 设置成不自动提交
			con.setAutoCommit(false);
			st = con.prepareStatement(selectSql);
			st.setString(1, siteNumber);
			st.setDate(2, new java.sql.Date(sdf.parse(time).getTime()));
			rt = st.executeQuery();

			if (rt.next()) {
				String updateSql = "UPDATE TEQC_INFO SET GCSC = ?,SJYXX = ?,MP1= ?,MP2= ?,GCWYTB= ? WHERE DATATIME = ?  AND SSSS=?";

				st = con.prepareStatement(updateSql);
				st.setString(1, info1);
				st.setString(2, info2);
				st.setString(3, info3);
				st.setString(4, info4);
				st.setString(5, info5);
				st.setDate(6, new java.sql.Date(sdf.parse(time).getTime()));
				st.setString(7, siteNumber);
			} else {
				st = con.prepareStatement(insertSql);
				st.setInt(1, getNextID(getIdSql));
				st.setString(2, info1);
				st.setString(3, info2);
				st.setString(4, info3);
				st.setString(5, info4);
				st.setString(6, info5);
				st.setDate(7, new java.sql.Date(sdf.parse(time).getTime()));
				st.setString(8, siteNumber);
				st.setTimestamp(9, new Timestamp(new Date().getTime()));
			}
			st.executeUpdate();

			con.commit();
			con.setAutoCommit(defaultCommit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				ConnectionSource.closeResultSet(rt);
				ConnectionSource.closeStatement(st);
				ConnectionSource.closeConnection(con);
			} catch (DataServiceSQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 方法描述：当天的每小时1HZ文件信息入库
	 * 
	 * @return
	 */
	public static void insert1HZStateByTime(String siteNumber, String workDate,
			String state) {

		// 先执行删除操作，避免
		String selectSql = "SELECT * FROM GNSS_RINEX_1HZ WHERE SSSS= ? AND WORKDATE = ? AND FILETYPE= 'GNSS_RINEX_1HZ'";

		String insertSql = "INSERT INTO GNSS_RINEX_1HZ(CREATEDATE,SSSS,FILETYPE,WORKDATE,STATE) VALUES (?,?,?,?,?)";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try {
			con = ConnectionSource.getConnection();
			boolean defaultCommit = con.getAutoCommit();
			// 设置成不自动提交
			con.setAutoCommit(false);
			st = con.prepareStatement(selectSql);
			st.setString(1, siteNumber);
			st.setTimestamp(2, new Timestamp(sdf.parse(workDate).getTime()));
			rt = st.executeQuery();

			if (rt.next()) {
				String updateSql = "UPDATE GNSS_RINEX_1HZ SET STATE = ? WHERE SSSS = ? AND WORKDATE = ? AND FILETYPE= 'GNSS_RINEX_1HZ'";

				st = con.prepareStatement(updateSql);
				st.setString(1, state);
				st.setString(2, siteNumber);
				st.setTimestamp(3, new Timestamp(sdf.parse(workDate).getTime()));
			} else {
				st = con.prepareStatement(insertSql);
				st.setTimestamp(1, new Timestamp(new Date().getTime()));
				st.setString(2, siteNumber);
				st.setString(3, "GNSS_RINEX_1HZ");
				st.setTimestamp(4, new Timestamp(sdf.parse(workDate).getTime()));
				st.setString(5, state);
			}
			st.executeUpdate();

			con.commit();
			con.setAutoCommit(defaultCommit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				ConnectionSource.closeResultSet(rt);
				ConnectionSource.closeStatement(st);
				ConnectionSource.closeConnection(con);
			} catch (DataServiceSQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 方法描述：当天的30S文件信息入库
	 * 
	 * @return
	 */
	public static void insert30SStateByTime(String siteNumber, String workDate,
			String state) {

		// 先执行删除操作，避免
		String selectSql = "SELECT * FROM GNSS_RINEX_30S WHERE SSSS = ? AND WORKDATE = ? AND FILETYPE = 'GNSS_RINEX_30S'";

		String insertSql = "INSERT INTO GNSS_RINEX_30S(SSSS,WORKDATE,STATE,FILETYPE,CREATEDATE) VALUES (?,?,?,?,?)";
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			con = ConnectionSource.getConnection();
			boolean defaultCommit = con.getAutoCommit();
			// 设置成不自动提交
			con.setAutoCommit(false);
			st = con.prepareStatement(selectSql);
			st.setString(1, siteNumber);
			st.setDate(2, new java.sql.Date(sdf.parse(workDate).getTime()));
			rt = st.executeQuery();

			if (rt.next()) {

				String updateSql = "UPDATE GNSS_RINEX_30S SET STATE = ? WHERE SSSS = ? AND WORKDATE = ?";
				st = con.prepareStatement(updateSql);
				st.setString(1, state);
				st.setString(2, siteNumber);
				st.setDate(3, new java.sql.Date(sdf.parse(workDate).getTime()));
			} else {

				st = con.prepareStatement(insertSql);
				st.setString(1, siteNumber);
				st.setDate(2, new java.sql.Date(sdf.parse(workDate).getTime()));
				st.setString(3, state);
				st.setString(4, "GNSS_RINEX_30S");
				st.setTimestamp(5, new Timestamp(new Date().getTime()));
			}

			st.executeUpdate();

			con.commit();
			con.setAutoCommit(defaultCommit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert30SStateByTime() is failed!");
		} finally {
			try {
				ConnectionSource.closeResultSet(rt);
				ConnectionSource.closeStatement(st);
				ConnectionSource.closeConnection(con);
			} catch (DataServiceSQLException e) {
				e.printStackTrace();
				logger.error("insert30SStateByTime() is failed!");
			}
		}
	}

	/**
	 * 根据sql语句获取主键ID
	 * 
	 * @param sql
	 * @return int 主键
	 * @throws DataServiceSQLException
	 */
	public static int getNextID(String sql) throws DataServiceSQLException {
		int nextId = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionSource.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				nextId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("getNextID() is failed!");
			throw new DataServiceSQLException(e);
		} finally {
			ConnectionSource.closeResultSet(resultSet);
			ConnectionSource.closeStatement(statement);
			ConnectionSource.closeConnection(connection);

		}
		return nextId;
	}

	/**
	 * 获取数据库中的260多个陆态网络站点名称字符串，用“,”分割
	 * 
	 * @return String
	 * @throws DataServiceSQLException
	 */
	public static String getAllGnssSiteNumberAndReceiveIp() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String sql = "SELECT SITE_CODE,IP,T0230SPATH FROM SITE_RECEIVE_INFO";

		StringBuffer sb = new StringBuffer("");

		String allSiteNumberStr = null;
		try {
			connection = ConnectionSource.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				sb.append(resultSet.getString("SITE_CODE"));
				sb.append(";");// 用分号分隔站点编号和接收机的IP地址
				sb.append(resultSet.getString("IP") == null ? " " : resultSet
						.getString("IP"));
				sb.append(";");
				sb.append(resultSet.getString("T0230SPATH"));
				sb.append(",");
			}

			if (sb.toString().contains(",")) {

				allSiteNumberStr = (sb.toString()).substring(0, sb.toString()
						.length() - 1);
			}
		} catch (DataServiceSQLException e) {
			logger.error("getAllGnssSiteNumberAndReceiveIp() is failed!");
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("getAllGnssSiteNumberAndReceiveIp() is failed!");
		} finally {
			try {
				ConnectionSource.closeResultSet(resultSet);
				ConnectionSource.closeStatement(statement);
				ConnectionSource.closeConnection(connection);
			} catch (DataServiceSQLException e) {
				e.printStackTrace();
				logger.error("getAllGnssSiteNumberAndReceiveIp() is failed!");
			}

		}

		return allSiteNumberStr;
	}

	/**
	 * 对文件进行解析，完成24个文件的入库（存入那磁）操作，返回24个文件是否完整（24个文件的完整度平均值大于等于95完整，反之不完整）
	 * 
	 * @param siteNumber
	 *            站点编号
	 * @param fileType
	 *            文件类型
	 * @param workDate
	 *            时间
	 * @param filePath
	 *            本地配置文件路径
	 * @param jueduiPath
	 *            本地单网单天每小时文件起始路径
	 * @param yearTwoLength
	 *            两位年
	 * @param doyThreeLength
	 *            三位年纪日
	 * @return
	 */
	public static boolean isFull(String siteNumber, String fileType,
			String workDate, String filePath, String jueduiPath,
			String yearTwoLength, String doyThreeLength) {

		File f = new File(filePath);
		String jsonStr = null;
		boolean isFull = false;

		String full = "ABCDEFGHIJKLMNOPQRSTUVWX";
		String s = "";
		if (f.exists()) {

			BufferedReader bw = null;
			try {
				bw = new BufferedReader(new FileReader(f));
				String line = null;
				double d = 0;

				// 调用c语言写的接口进入文件保存入库操作
				DataStore a = new DataStore();
				int b = a.initConn(); // 1 表示连接成功

				if (b != 1) {
					logger.error("isFull() connection datasource is failed!");
					return false;
				}

				//假如存在有小时文件完整度小于等于百分之九十的情况，就走T02下载方案
				boolean ifExistOneRenixFileXiaoyu90 = false;
				try {
					while ((line = bw.readLine()) != null) {
						// logger.info("***line***"+line);
						// 一个字母，a表示SSSSDOYa.YYo，b表示SSSSDOYb.YYo，...x表示SSSSDOYx.YYo
						String str = line.trim().split(" ")[0].trim()
								.toUpperCase();

						s += str;
						// 百分比数值（99.9这类的一位小数）
						String bjb = line.trim().split(" ")[1].trim();
						
						//9.16号添加小时文件完整度判断逻辑
						if(Double.parseDouble(bjb)<=90){
							ifExistOneRenixFileXiaoyu90 = true;
						}
						if (bjb != null && !bjb.equals("0.0")) {
							d += Double.parseDouble(bjb);

							String state_ONE_HOUR = "";
							// 每小时文件状态位标石 完整度大于95则标石为0正常 小于95标石为-2有缺失
							if (Double.valueOf(bjb) >= 95) {
								state_ONE_HOUR = "0";
							} else {
								state_ONE_HOUR = "-2";
							}
							jsonStr = "{\"SSSS\":\"" + siteNumber.toUpperCase()
									+ "\",\"FILETYPE\":\"" + fileType
									+ "\",\"WORKDATE\":\"" + workDate + " "
									+ repalceStr(str) + ":0:0\",\"STATE\":\""
									+ state_ONE_HOUR + "\"}";

							// 判断在于100个字节的文件才能上传
							if (new File(jueduiPath + siteNumber
									+ doyThreeLength + str.toLowerCase() + "."
									+ yearTwoLength + "o").length() > 100) {
								int n = a.saveFile(jueduiPath + siteNumber
										+ doyThreeLength + str.toLowerCase()
										+ "." + yearTwoLength + "o", jsonStr);// 1
																				// 表示保存文件成功\
								if (n != 1) {
									logger.error("***********siteNumber:"
											+ siteNumber + " isFull() ["
											+ jueduiPath + siteNumber
											+ doyThreeLength
											+ (str.toLowerCase()) + "."
											+ yearTwoLength + "o"
											+ "] file save nas fail！********");
								}
							}
							// insert1HZStateByTime(siteNumber, workDate+"
							// "+repalceStr(str)+":0:0", state_ONE_HOUR);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				} finally {
					logger.info("ing close");
					a.closeConn();
					logger.info("end close");
				}
				//24个小时文件全部存在，并且是不存在小时文件完整度小于等于90
				if (full.equals(s) && (!ifExistOneRenixFileXiaoyu90)) {
					if (d / 24 >= 95) {
						isFull = true;
						logger.info("*****isFull() ******siteNumber:"
								+ siteNumber + "[" + workDate
								+ "]24 rinex file wanzhengdu is [" + d / 24
								+ "],>=95!");
					} else {
						logger.info("*****isFull() *****siteNumber:"
								+ siteNumber + "[" + workDate
								+ "]24 rinex file wanzhengdu is [" + d / 24
								+ "],< 95,will download T02 file from ftp!");
					}
				}
				bw.close();
			} catch (FileNotFoundException e) {
				logger.error("isFull() is failed!");
			} catch (IOException e) {
				logger.error("isFull() is failed!");
			}
		}
		return isFull;
	}

	/**
	 * 根据R文件返回所有的小时字符串（例如：abcdfklo...）
	 * @param filePath
	 * @return
	 */
	
	private String getAllStr(String filePath){
		
		File f = new File(filePath);
		String s = "";
		if (f.exists()) {

			BufferedReader bw = null;
			try {
				bw = new BufferedReader(new FileReader(f));
				String line = null;

				while ((line = bw.readLine()) != null) {
					String str = line.trim().split(" ")[0].trim()
							.toLowerCase();
					s += str;
				}
				bw.close();
			} catch (FileNotFoundException e) {
				logger.error("isFull() is failed!");
			} catch (IOException e) {
				logger.error("isFull() is failed!");
			}
		}
		return s;
		
	}
	
	/**
	 * 字符串替换，a替换成0，b替换成1，c替换成2....x替换成23
	 * 
	 * @param str
	 * @return
	 */
	public static String repalceStr(String str) {
		if (str.equals("A")) {
			return "0";
		} else if (str.equals("B")) {
			return "1";
		} else if (str.equals("C")) {
			return "2";
		} else if (str.equals("D")) {
			return "3";
		} else if (str.equals("E")) {
			return "4";
		} else if (str.equals("F")) {
			return "5";
		} else if (str.equals("G")) {
			return "6";
		} else if (str.equals("H")) {
			return "7";
		} else if (str.equals("I")) {
			return "8";
		} else if (str.equals("J")) {
			return "9";
		} else if (str.equals("K")) {
			return "10";
		} else if (str.equals("L")) {
			return "11";
		} else if (str.equals("M")) {
			return "12";
		} else if (str.equals("N")) {
			return "13";
		} else if (str.equals("O")) {
			return "14";
		} else if (str.equals("P")) {
			return "15";
		} else if (str.equals("Q")) {
			return "16";
		} else if (str.equals("R")) {
			return "17";
		} else if (str.equals("S")) {
			return "18";
		} else if (str.equals("T")) {
			return "19";
		} else if (str.equals("U")) {
			return "20";
		} else if (str.equals("V")) {
			return "21";
		} else if (str.equals("W")) {
			return "22";
		} else if (str.equals("X")) {
			return "23";
		}
		return null;
	}

	/**
	 * 执行命令 将TEQC执行结果输出到文件 windows 系统
	 * 
	 * @param command
	 * @return 返回执行最终结果，是否成功
	 */

	public static boolean executeResultToFile(String command) {

		boolean result = false;

		try {
			Process child = Runtime.getRuntime().exec(command);

			child.getErrorStream().close();
			child.waitFor();
			child.destroy();
			result = true;

		} catch (Exception e) {
			logger.error("executeResultToFile() is failed!");
		}

		// 休眠100毫秒让结果文件正确生成
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 执行命令 TEQC执行一个文件 windows 系统
	 * 
	 * @param command
	 * @return 返回执行最终结果，是否成功
	 */

	public static boolean executeFile(String command) {

		boolean result = false;

		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(command);
			proc.getErrorStream().close();
		} catch (IOException e) {
			logger.error("********command[" + command
					+ "]execute error!*************");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (proc != null) {

			BufferedReader in = new BufferedReader(new InputStreamReader(proc
					.getInputStream()));

			try {
				String line;

				// TEQC执行一个文件后，自动生成多个文件，这个while必须存在
				while ((line = in.readLine()) != null) {
					// pw.write(line);
					// if(line.indexOf("retrun 1")!=-1){
					// save
					// logger.info(" --- \n have value ! " );
					// }
					// logger.info("---" + line + "---");
				}

				proc.waitFor();
				in.close();
				proc.destroy();
				return true;
			} catch (Exception e) {
				logger.error("***********command[" + command
						+ "]execute error!**********");
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		// 休眠100毫秒让结果文件正确生成
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * LINUX环境下执行命令（多个命令 “;”分割command）
	 */
	public static boolean executeFileForLinux(String command) {

		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			// proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(proc.getOutputStream())), true);
				// logger.info("------正在执行Linux命令(command):【" + "chmod 777
				// "+cshPath+";csh "+cshPath+";exit】");
				// **************此处代码执行多个任务，至关重要的代码
				out.println(command);
				// logger.info("------command-------" + command);
				// String line;
				// while ((line = in.readLine()) != null) {
				// logger.info("line:"+line);
				// }

				// **************阻塞式依次执行，至关重要的代码
				proc.waitFor();
				Thread.sleep(100);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return false;
	}

	/**
	 * LINUX环境下执行命令（多个命令 “;”分割command）
	 */
	public static boolean executeFileForLinuxToResult(String command) {

		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			// proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(proc.getOutputStream())), true);
				// logger.info("------正在执行Linux命令(command):【" + "chmod 777
				// "+cshPath+";csh "+cshPath+";exit】");
				// **************此处代码执行多个任务，至关重要的代码
				out.println(command);
				// logger.info("------command-------" + command);
				String line;
				while ((line = in.readLine()) != null) {

					logger.info("line:" + line);
				}

				// **************阻塞式依次执行，至关重要的代码
				proc.waitFor();

				Thread.sleep(100);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return false;
	}

	/**
	 * LINUX环境下执行命令（多个命令 “;”分割command）
	 */

	public static boolean executeFileForLinuxForT02(String command) {

		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(proc.getOutputStream())), true);
				// logger.info("------正在执行Linux命令(command):【" + "chmod 777
				// "+cshPath+";csh "+cshPath+";exit】");
				// **************此处代码执行多个任务，至关重要的代码
				out.println(command);
				// logger.info("------command-------" + command);
				String line;
				while ((line = in.readLine()) != null) {
					// logger.info("------" + line+"------");
				}

				// **************阻塞式依次执行，至关重要的代码
				proc.waitFor();

				Thread.sleep(100);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
		return false;
	}

	/**
	 * 执行命令 TEQC执行30S文件
	 * 
	 * @param command
	 * @return 返回执行最终结果，是否成功
	 */

	public static boolean execute30S(String command) {

		boolean result = false;

		try {
			Process child = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(child
					.getInputStream()));

			String line;
			// 此处的while循环不能删除
			while ((line = in.readLine()) != null) {
			}
			child.waitFor();
			child.destroy();
			result = true;

			// 休眠100毫秒让结果文件正确生成
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 保存文件到那磁盘，统一调用C接口
	 * 
	 * @param jueduilocalPath
	 *            本地文件的绝对路径
	 * @param siteNumer
	 *            站点编号
	 * @param fileType
	 *            文件类型
	 * @param workDate
	 *            时间
	 * @param state
	 *            状态
	 * @return 是否成功
	 */
	public static boolean saveFileToNac(String jueduilocalPath,
			String siteNumber, String fileType, String workDate, String state) {

		if (!new File(jueduilocalPath).exists()) {
			logger.error("saveFileToNac() [" + jueduilocalPath
					+ "]file not exist!");
			return false;
		}
		String jsonStr = "{\"SSSS\":\"" + siteNumber.toUpperCase()
				+ "\",\"FILETYPE\":\"" + fileType + "\",\"WORKDATE\":\""
				+ workDate + "\",\"STATE\":\"" + state + "\"}";
		// 调用c语言写的接口进入文件保存入库操作
		DataStore a = new DataStore();
		int b = a.initConn(); // 1 表示连接成功
		if (b != 1) {
			logger.error("saveFileToNac() connection datasource is failed");
			return false;
		}
		int n = 0;

		try {
			n = a.saveFile(jueduilocalPath, jsonStr);// 1 表示保存文件成功
			if (n != 1) {
				logger.error("saveFileToNac() [" + jueduilocalPath
						+ "]file save nas is failed!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			a.closeConn();
		}

		return b == 1 && n == 1;

	}

	/**
	 * 解析TEQC最终生成的文件，进行入库操作
	 * 
	 * @param SfilePath
	 * @param siteNumber
	 * @param beforreDayStr 
	 */
	public static void jiexiSfile(String SfilePath, String siteNumber,
			String beforreDayStr) {
		File f = new File(SfilePath);
		if (f.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
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
							if (!"".equals(ss[d].trim())) {
								map.put(allLength, ss[d]);
								allLength += 1;
							}
						}
						// 将站点的质量信息入库
						insertStationQualityInfo(siteNumber.toUpperCase(),
								beforreDayStr, map.get(allLength - 5), map
										.get(allLength - 4), map
										.get(allLength - 3), map
										.get(allLength - 2), map
										.get(allLength - 1));
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		} else {
			logger.error("jiexiSfile() siteNumber:[" + siteNumber + "],time:["
					+ beforreDayStr + "] YYS result file not exist!");
		}
	}

	/**
	 * 将s2中所有字符在s1中剔除
	 * 
	 * @param s1
	 *            第一个字符串
	 * @param s2
	 *            第二个字符串
	 * @return
	 */
	public static String subtractStr(String s1, String s2) {

		if (s1 == null || s2 == null) {
			return null;
		}

		for (int i = 0; i < s2.length(); i++) {
			s1 = s1.replace(s2.charAt(i) + "", "");
		}

		return s1;
	}
	
	

	//以下程序代码 于 2016-1-23 增加， 为了出现java调用linux系统命令时程序卡死的情况，让程序直接跳过
	
	/***
	 * 方法参数说明
	 * @param target 调用方法的当前对象
	 * @param methodName 方法名称
	 * @param parameterTypes 调用方法的参数类型
	 * @param params 参数  可以传递多个参数
	 * 
	 * */
	public String callMethod(final Object target , final String methodName ,final Class<?>[] parameterTypes,final Object[]params){
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
            logger.error("方法执行中断"); 
        }catch (ExecutionException e) {  
            future.cancel(true);  
            logger.error("Excuti on异常");  
        }catch (TimeoutException e) {  
            future.cancel(true);  
            logger.error("TimeoutException异常");
           // throw new RuntimeException("invoke timeout" , e );
        }
        executorService.shutdownNow(); 
        return result ;
	}
}
