package com.highfd.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jdom2.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * 基于注解的定时器
 */
@Component
@Controller
public class AlarmEmailAnnotation {
	
	public static String dateString;
	public static List<Element> rootList = null;
	public static Set<Integer> rootSet = new TreeSet<Integer>();;
	
	public static Map<String,Integer> areaIndustryMap = null;
	public static int zlzpOldNumber = 0;
	public static boolean todayOneFLag = false;
	
	
	//@Scheduled(cron = "0 0 18 * * ?")
	//@Scheduled(fixedRate = 1000*60*60*24) 
	public void craw51JobRun() throws Exception {//2 51job 1 智联招聘   4：智联招聘 ，5：51Job
		standerIndustry();
    	Date date0 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-04-30");
    	Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-05-20");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date0);
    	cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
    	//List<DataCollect> queryALLCountDate = new ArrayList<DataCollect>();
    	while(cal.getTime().compareTo(date1)<0){
    	    cal.add(Calendar.DAY_OF_MONTH,1);
    	    Date dateStrings = cal.getTime();
    		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
    		String time = dFormat.format(dateStrings);
    		//System.out.println("中间数据入库  开始执行："+time);
    		
    		//List<DataCollect> dataList = new ArrayList<DataCollect>();
    		//dataList = crawTimerService.queryZLZP_JL(time);//智联招聘 简历1
    		//dataList = service156.queryZHYC_JL(time);//英才  简历3
    		//dataList = service156.queryZHYC_ZW(time);//英才  职位4
    		//dataList = service153.query51_ZW(time);//51job 职位6
    		//if(null!=dataList && dataList.size()>0){
    		//	crawTimerService.insertDataCollect(dataList);
    		//}
    		
    		//导出
    	    //List<DataCollect> midData = crawTimerService.queryALLCountDate("", "", "4", time);
    		//queryALLCountDate.addAll(midData);
    		
    	}
    	//ExcelTool.ttttt(queryALLCountDate);
    	//System.out.println("结束了："+new Date());
	}
	
	public static Map<String,String> regionNameMap = new LinkedHashMap<String,String>();
	 public static void getProvinceMap(){
		regionNameMap.put("217","湖北");regionNameMap.put("218","湖南");regionNameMap.put("210","江苏");
		regionNameMap.put("209","上海");regionNameMap.put("222","重庆");regionNameMap.put("224","贵州");
		regionNameMap.put("212","安徽");regionNameMap.put("214","江西");regionNameMap.put("223","四川");
		regionNameMap.put("211","浙江");regionNameMap.put("225","云南");regionNameMap.put("216","河南");
		regionNameMap.put("204","山西");regionNameMap.put("201","北京");
	 }
	 /*Iterator it = regionNameMap.keySet().iterator();
		while(it.hasNext()){        //省份循环
		    String regionName = it.next().toString();
		  crawTimerService.insertAllCollectData(time, regionName, "6");
	 }*/
	
	public static List<String> titleIndustryName = new ArrayList<String>();
	public void standerIndustry(){
		titleIndustryName = new ArrayList<String>();
		 String standerStr = "电子信息,汽车零部件及加工,钢铁冶金,石油化工,纺织工业,食品加工,建筑建材,装备制造,生物医药";
		 for(int i=0;i<standerStr.split(",").length;i++){
			 titleIndustryName.add(standerStr.split(",")[i]);
		 }
	 }

}