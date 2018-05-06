package com.highfd.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;
import com.highfd.service.Z_DataReductionService;

/**
 * 30日常数据 定时整理
 */
@Component
@Controller
public class Z_DataReductionController {
	
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	HighFDService highFDService;
	@Autowired
	Z_DataReductionService z_DataReductionService;

	/**
	 * 每天定时任务
	 */
	//@Scheduled(cron = "0 1 10 * * ?")
	//@Scheduled(fixedRate = 1000*60*60*24)
	public void S30DataEveryDay() throws Exception {
		Date date0 = new Date();;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date0);
	    cal.add(Calendar.DAY_OF_MONTH,-1);
	    Date dateStrings = cal.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("开始执行  **   定时器每天执行："+dFormat.format(dateStrings));
		z_DataReductionService.data30S(dFormat.format(dateStrings),"");
	}
	
	/**
	 * 每月第二天 补全上个月数据
	 * @throws Exception
	 */
	//@Scheduled(cron = "0 0/1 1-23 * * ?")
	//@Scheduled(fixedRate = 100000*60*60*24)
	//@Scheduled(cron = "0 0 12 * * ?")
	//@Scheduled(cron = "0 1 10 ? * *")
	public void S30DataMonth() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取前一个月第一天
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.add(Calendar.MONTH, -4);
	    calendar1.set(Calendar.DAY_OF_MONTH,0);
	    String firstDay = sdf.format(calendar1.getTime());
	    //获取前一个月最后一天
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.set(Calendar.DAY_OF_MONTH, 0);
	    String lastDay = sdf.format(calendar2.getTime());
	      
	    System.out.println("每月二号 补数据  日期："+firstDay+" 到 "+lastDay);
	
		Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDay);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(lastDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date0);
		while(cal.getTime().compareTo(date1)<0){
		    cal.add(Calendar.DAY_OF_MONTH,1);
		    Date dateStrings = cal.getTime();
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("每月二号 补数据    开始执行："+dFormat.format(dateStrings));
			z_DataReductionService.data30S(dFormat.format(dateStrings),"");
		}
		//开始执行 每月的 报告
		highFDService.insertDataQualityInfo(lastDay);
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