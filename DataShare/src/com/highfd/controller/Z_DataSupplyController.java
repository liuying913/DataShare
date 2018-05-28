package com.highfd.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;
import com.highfd.service.Z_DataReductionService;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.common.linux.FileTypeTransform;
import com.highfd.common.map.MapAll;
import com.highfd.controller.Data.share.service.ShareDataReductionService;
/**
 * 基于注解的定时器
 */
@Component
@Controller
@RequestMapping(value="/supply")
public class Z_DataSupplyController {
	
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	HighFDService highFDService;
	@Autowired
	Z_DataReductionService z_DataReductionService;
	@Autowired
	ShareDataReductionService shareDataReductionService;
	
	public static String S30SBasePath = "/root/tmp_nas/gnssdata/CMONOC-GNSS";//30S 临时nas盘 o文件（人家的接收机存储文件）
	public static String S30SWorkPath = "/home/liuying/To2Work01";
	public static String S30S_ZipPath = "/root/nfs/Gnssdata/30sdz";//最终位置  30秒 Zip文件位置
	public static String S30S_Ftp_WorkPath = "/home/liuying/To2Ftp";
	public static String S30S_To2_Ftp_Path = "Internal/30sd";
	public static String fileLogName = "";//数据在线整理路径

	/**
	 * FTP 文件夹
	 * 将用户从接收机上转化的o文件 放入系统中
	 */
	@RequestMapping(value = "ftpFileToNAS", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String ftpFileToNAS(HttpServletRequest request) throws Exception {
		
		//创建日志文件
		FileTool.createDataApplyLog();
		
		//查询所有的T02文件，并进行压缩 转换 入库
		FileTypeTransform.analysisAll_T02(new File(MapAll.gnss_o));
		
		//查询所有的d.Z文件，并进行压缩 转换 入库
		FileTypeTransform.analysisAll_dZ(new File(MapAll.gnss_o));
		
		//查询所有的o文件，并进行压缩 转换 入库
		FileTypeTransform.analysisAll_o(new File(MapAll.gnss_o),applyFileService);
		
		//整理共享的o文件
		shareDataReductionService.analysisAll_o(new File(MapAll.Share_o),applyFileService);
		
		//整理共享的d.Z文件
		System.out.println("开始整理O文件数据"+MapAll.Share_o);
		
		return null;
	}
	
	/**
	 * 根据参数进行 数据整理
	 */
	private static boolean supplyDataFlag = true;//true:可以执行  false：不能执行
	@RequestMapping(value = "supplyData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String supplyData(HttpServletRequest request) throws Exception {
		boolean midFlag = supplyDataFlag;
		if(supplyDataFlag){
			String siteNumberStr = request.getParameter("siteNumberStr");
			String departmentStr = request.getParameter("departmentStr");
			String zoneStr = request.getParameter("zoneStr");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			new DataSupplyThread(siteNumberStr,departmentStr,zoneStr,startTime,endTime).start();
		}
		return "{\"date\":[{\"code\":\""+midFlag+"\",\"msg\":\"操作开始或结束！\"}]}";
	}
	
	
	class DataSupplyThread extends Thread {
		
		private String siteNumberStr;
		private String departmentStr;
		private String zoneStr;
		private String startTime;
		private String endTime;
		
		public DataSupplyThread(String siteNumberStr,String departmentStr,String zoneStr,String startTime,String endTime){
			this.siteNumberStr = siteNumberStr;
			this.departmentStr = departmentStr;
			this.zoneStr = zoneStr;
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		public void run() {
			supplyDataFlag = false;
			try {
				FileTool.createDataApplyLog();//创建日志文件
				String siteNumberParam = siteService.getSiteNumberStr(siteNumberStr, departmentStr, zoneStr, "1");
				Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);//后一天开始 
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);//到该天
				Calendar cal = Calendar.getInstance();
				cal.setTime(date0);
				cal.add(Calendar.DAY_OF_MONTH,-1);
				while(cal.getTime().compareTo(date1)<0){
				    cal.add(Calendar.DAY_OF_MONTH,1);
				    Date dateStrings = cal.getTime();
					SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println("补数据  开始执行："+dFormat.format(dateStrings));
					z_DataReductionService.data30S(dFormat.format(dateStrings),siteNumberParam);
				}
				String txtFile = CopyFileUtil.readTxtFile(fileLogName);
				System.out.println("读文件结果：  "+txtFile);
			}catch (ParseException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				supplyDataFlag=true;
			}
		}
	}
	
}