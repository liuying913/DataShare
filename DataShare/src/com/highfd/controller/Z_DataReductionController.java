package com.highfd.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.highfd.controller.Data.share.service.ShareDataReductionService;
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
	@Autowired
	ShareDataReductionService shareService;

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
		shareService.shareDataReductionMain(dFormat.format(dateStrings),"");
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
			shareService.shareDataReductionMain(dFormat.format(dateStrings),"");
		}
		//开始执行 每月的 报告
		highFDService.insertDataQualityInfo(lastDay);
	}
}