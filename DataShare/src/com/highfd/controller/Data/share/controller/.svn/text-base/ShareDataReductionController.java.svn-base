package com.highfd.controller.Data.share.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.common.file.FileTool;
import com.highfd.controller.Data.share.service.ShareDataReductionService;
import com.highfd.service.ApplyFileService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;

/**
 * 共享数据 定时整理
 */
@Component
@Controller
@RequestMapping(value="/shareDataReduction")
public class ShareDataReductionController {
	
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	HighFDService highFDService;
	@Autowired
	ShareDataReductionService shareService;

	/**
	 * 每天定时任务  整理共享数据(30所在的方法里面了)
	 */
	//@Scheduled(cron = "0 1 10 * * ?")
	//@Scheduled(fixedRate = 1000*60*60*24)
	public void shareDataReductionEveryDay() throws Exception {
		Date date0 = new Date();;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date0);
	    cal.add(Calendar.DAY_OF_MONTH,-1);
	    Date dateStrings = cal.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("开始执行  **   定时器每天执行 共享数据整理："+dFormat.format(dateStrings));
		shareService.shareDataReductionMain(dFormat.format(dateStrings),"");
	}
	
	/**
	 * 每月第二天 补全上个月 共享台站数据
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
			System.out.println("每月二号 补共享台站数据    开始执行："+dFormat.format(dateStrings));
			shareService.shareDataReductionMain(dFormat.format(dateStrings),"");
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 根据界面参数     进行共享数据整理
	 */
	private static boolean supplyShareDataFlag = true;//true:可以执行  false：不能执行
	@RequestMapping(value = "supplyShareData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String supplyData(HttpServletRequest request) throws Exception {
		boolean midFlag = supplyShareDataFlag;
		if(supplyShareDataFlag){
			String siteNumberStr = request.getParameter("siteNumberStr");
			String departmentStr = request.getParameter("departmentStr");
			String zoneStr = request.getParameter("zoneStr");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			new ShowDataSupplyThread(siteNumberStr,departmentStr,zoneStr,startTime,endTime).start();
		}
		System.out.println("{\"date\":[{\"code\":\""+midFlag+"\",\"msg\":\"操作开始或结束！\"}]}");
		return "{\"date\":[{\"code\":\""+midFlag+"\",\"msg\":\"操作开始或结束！\"}]}";
	}
	
	
	class ShowDataSupplyThread extends Thread {
		
		private String siteNumberStr;
		private String departmentStr;
		private String zoneStr;
		private String startTime;
		private String endTime;
		
		public ShowDataSupplyThread(String siteNumberStr,String departmentStr,String zoneStr,String startTime,String endTime){
			this.siteNumberStr = siteNumberStr;
			this.departmentStr = departmentStr;
			this.zoneStr = zoneStr;
			this.startTime = startTime;
			this.endTime = endTime;
		}
		
		public void run() {
			supplyShareDataFlag = false;
			try {
				String siteNumberParam = siteService.getSiteNumberStr(siteNumberStr, departmentStr, zoneStr, "3");
				Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);//后一天开始 
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);//到该天
				Calendar cal = Calendar.getInstance();
				cal.setTime(date0);
				cal.add(Calendar.DAY_OF_MONTH,-1);
				while(cal.getTime().compareTo(date1)<0){
				    cal.add(Calendar.DAY_OF_MONTH,1);
				    Date dateStrings = cal.getTime();
					SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
					//System.out.println("补数据  开始执行："+dFormat.format(dateStrings));
					shareService.shareDataReductionMain(dFormat.format(dateStrings),siteNumberParam);
				}
			}catch (ParseException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				supplyShareDataFlag=true;
				System.out.println("跑完了！！！！！！1");
			}
		}
	}
}