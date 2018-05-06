package com.highfd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.ManagerService;
import com.highfd.service.SiteStationService;

@Component
@Controller
@RequestMapping(value="/analysis")
public class AnalysisController {

	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	ManagerService managerService;

	//框架跳转
	@RequestMapping(value = "analysisMainToJsp")
	@ResponseBody
	public ModelAndView analysisMainToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("analysisMain");
	}
	
	/**
	 * 30S 统计分析  界面跳转（文件月度 文件年度 文件地图  台站地图）***********************************************************************************
	 */
	
	//30数据下载展示(跳转界面)
	@RequestMapping(value = "s30SDownHistoryListToJsp")
	@ResponseBody
	public ModelAndView s30SDownHistoryListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/downHistoryList");
		return mav;
	}
	//（表格）   文件月度
	@RequestMapping(value = "s30sFileMoreMonthToJsp")
	@ResponseBody
	public ModelAndView s30sFileMoreMonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/fileMoreMonth");
		return mav;
	}
	//（表格）  文件年度
	@RequestMapping(value = "s30sFileMoreYearToJsp")
	@ResponseBody
	public ModelAndView s30sFileMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/fileMoreYear");
		return mav;
	}
	//（地图）  文件
	@RequestMapping(value = "s30sFileMoreDayToJsp")
	@ResponseBody
	public ModelAndView s30sFileMoreDayToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/fileMoreDay");
		return mav;
	}
	//地图）   台站
	@RequestMapping(value = "s30sSiteMoreTimeToJsp")
	@ResponseBody
	public ModelAndView s30sSiteMoreTimeToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/siteMoreTime");
		return mav;
	}
	//陆态30秒日常数据       省份分析
	@RequestMapping(value = "s30sZoneToJsp")
	@ResponseBody
	public ModelAndView s30sZoneToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/zone");
		return mav;
	}
	//陆态30秒日常数据       部位分析
	@RequestMapping(value = "s30sDepToJsp")
	@ResponseBody
	public ModelAndView s30sDepToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/30s/dep");
		return mav;
	}
	
	/**
	 * 共享数据 统计分析  界面跳转（文件月度 文件年度 文件地图  台站地图）***********************************************************************************
	 */
	//共享数据列表
	@RequestMapping(value = "shareDownHistoryListToJsp")
	@ResponseBody
	public ModelAndView showPage1ToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/downHistoryList");
		return mav;
	}
	//（表格）   文件月度
	@RequestMapping(value = "shareFileMoreMonthToJsp")
	@ResponseBody
	public ModelAndView shareFileMoreMonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/fileMoreMonth");
		return mav;
	}
	//（表格）  文件年度
	@RequestMapping(value = "shareFileMoreYearToJsp")
	@ResponseBody
	public ModelAndView shareFileMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/fileMoreYear");
		return mav;
	}
	//（地图）  文件
	@RequestMapping(value = "shareFileMoreDayToJsp")
	@ResponseBody
	public ModelAndView shareFileMoreDayToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/fileMoreDay");
		return mav;
	}
	//地图）   台站
	@RequestMapping(value = "shareSiteMoreTimeToJsp")
	@ResponseBody
	public ModelAndView shareSiteMoreTimeToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/siteMoreTime");
		return mav;
	}
	//省份分析
	@RequestMapping(value = "shareZoneToJsp")
	@ResponseBody
	public ModelAndView shareZoneToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/zone");
		return mav;
	}
	//部位分析
	@RequestMapping(value = "shareDepToJsp")
	@ResponseBody
	public ModelAndView shareDepToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/share/dep");
		return mav;
	}
	
	/**
	 * 流动数据    统计分析  界面跳转（文件月度 文件年度 文件地图  台站地图）***********************************************************************************
	 */
	//共享数据列表
	@RequestMapping(value = "flowDownHistoryListToJsp")
	@ResponseBody
	public ModelAndView flowDownHistoryListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/flow/downHistoryList");
		return mav;
	}
	//（表格）  文件年度
	@RequestMapping(value = "flowFileMoreYearToJsp")
	@ResponseBody
	public ModelAndView flowFileMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/flow/fileMoreYear");
		return mav;
	}
	//省份分析
	@RequestMapping(value = "flowZoneToJsp")
	@ResponseBody
	public ModelAndView flowZoneToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/flow/zone");
		return mav;
	}
	//部位分析
	@RequestMapping(value = "flowDepToJsp")
	@ResponseBody
	public ModelAndView flowDepToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/flow/dep");
		return mav;
	}
	
	
	
	/**
	 * 地震应急数据 统计分析  界面跳转（文件月度 文件年度 文件地图  台站地图）***********************************************************************************
	 */
	
	//地震应急数据下载展示(跳转界面)
	@RequestMapping(value = "earthQuake/downHistoryListToJsp")
	@ResponseBody
	public ModelAndView earthQuakeDownHistoryListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 4);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/earthQuake/downHistoryList");
		return mav;
	}
	
	@RequestMapping(value = "earthQuake/downHistoryList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeDownHistoryList(HttpServletRequest request) {
		
		String year = request.getParameter("year");
		//year = "2017";
		String siteType = request.getParameter("siteType");
		String fileType = request.getParameter("fileType");
		String fileParams = request.getParameter("fileParams");
		List<FileHistroyDown> downHistoryList = downHistoryService.getDownHistoryList(year,fileType, fileParams);//4 地震应急数据
		JSONArray json = JSONArray.fromObject(downHistoryList);
		return json.toString();
	}
	
	
	//地震应急事件  表格统计分析
	@RequestMapping(value = "earthQuake/downTableToJsp")
	@ResponseBody
	public ModelAndView downTableTOJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/earthQuake/downTable");
		return mav;
	}
	/**
	 * *************界面跳转结束*********************************************************************
	 */
	
	
	
	
	//获得30S数据下载记录
	@RequestMapping(value = "s30S/downHistoryList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String downHistoryList(HttpServletRequest request) {
		String year = request.getParameter("year");
		year = "2017";//暂时按照全部的进行查询
		String siteType = request.getParameter("siteType");
		String fileType = request.getParameter("fileType");
		String fileParams = request.getParameter("fileParams");
		List<FileHistroyDown> downHistoryList = downHistoryService.getDownHistoryList(year,fileType, fileParams);//1 30秒数据
		JSONArray json = JSONArray.fromObject(downHistoryList);
		return json.toString();
	}
	
	//获得30S  单日   或者 时间跨度    地图展示
	//http://localhost:8080/DataShare/s30S/fileDownGroupForMap.action?startTime=2016-01-02&fileType=1
	@RequestMapping(value = "s30S/fileDownGroupForMap", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String fileDownGroupForMap(HttpServletRequest request) {
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(null==endTime || "".equals(endTime)){endTime = startTime;}//判断是一天的还是多天的
		
		String userId = request.getParameter("userId");
		String taizhang = request.getParameter("taizhang");
		String shengfen = request.getParameter("shengfen");
		String buwei = request.getParameter("buwei");
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, "1");
		
		Map<String, Map<String,SiteMapInfo>> resultMap = new HashMap<String, Map<String,SiteMapInfo>>();
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			
			SiteMapInfo siteMapInfo = new SiteMapInfo();
			siteMapInfo.setSiteName(siteInfo.getSiteName());
			siteMapInfo.setSiteNumber(siteInfo.getSiteNumber());
			siteMapInfo.setSiteLat(siteInfo.getSiteLat());
			siteMapInfo.setSiteLng(siteInfo.getSiteLng());
			
			siteMapInfo.setFileFlag(0);
			
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			fileInfoMap2.put("one", siteMapInfo);
			resultMap.put(siteMapInfo.getSiteName(), fileInfoMap2);
		}
		
		List<FileDownBySiteNumberBean> siteFileCountList = downHistoryService.fileDownGroupBySiteNumberForMap("1", startTime, endTime, userId);
		for(int i=0;i<siteFileCountList.size();i++){
			FileDownBySiteNumberBean siteInfo = siteFileCountList.get(i);
			
			SiteMapInfo siteMapInfo = new SiteMapInfo();
			siteMapInfo.setSiteName(siteInfo.getSiteName());
			siteMapInfo.setSiteNumber(siteInfo.getSiteNumber());
			siteMapInfo.setFileFlag(siteInfo.getCouNum());
			if(null!=resultMap.get(siteInfo.getSiteName())){
				siteMapInfo.setSiteLat(resultMap.get(siteInfo.getSiteName()).get("one").getSiteLat());
				siteMapInfo.setSiteLng(resultMap.get(siteInfo.getSiteName()).get("one").getSiteLng());
				resultMap.get(siteInfo.getSiteName()).put("one", siteMapInfo);
			}
		}
		JSONArray json = JSONArray.fromObject(resultMap);
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}
	
	
	//获得30S  表格展示
	//http://localhost:8080/DataShare/s30S/siteInfoDownGroupForTable.action?startTime=2016&endTime=1&fileType=1
	@RequestMapping(value = "s30S/siteInfoDownGroupForTable", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String siteInfoDownGroupForMap(HttpServletRequest request) {
		
		String startTime = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		
		String userId = request.getParameter("userId");
		
		String siteArray = request.getParameter("taizhang");
		String zoneArray = request.getParameter("shengfen");
		String departmentArray = request.getParameter("buwei");
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		String st = "";
		String et = "";
		
		if(null==endTime || "".equals(endTime)){//年度
			st = startTime+"-01-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(startTime), Integer.valueOf(12));
		}else{//月度
			st = startTime+"-"+endTime+"-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(startTime), Integer.valueOf(endTime));
		}
		
		Map<String, Map<String,FileDownBySiteNumberBean>> resultMap = new HashMap<String, Map<String,FileDownBySiteNumberBean>>();
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(siteArray,departmentArray,zoneArray,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, siteType);
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		for(int i=0;i<siteInfoList.size();i++){
			
			SiteInfo siteInfo = siteInfoList.get(i);
			
			Map<String,FileDownBySiteNumberBean> fileMap = new HashMap<String,FileDownBySiteNumberBean>();//根据  时间跨度，填放初始数据
			for(int j=1;j<=dayNumbers;j++){
				FileDownBySiteNumberBean fileInfo = new FileDownBySiteNumberBean();
				fileInfo.setCouNum(0);
				fileInfo.setSiteName(siteInfo.getSiteName());
				fileInfo.setSiteNumber(siteInfo.getSiteNumber());
				fileInfo.setSystemMonthYearDay(j+"");
				fileMap.put(j+"", fileInfo);
			}
			resultMap.put(siteInfo.getSiteName(), fileMap);
		}
		
		List<FileDownBySiteNumberBean> siteFileCountList = downHistoryService.siteInfoDownGroupForTable(fileType, st, et, userId);
		
		//有数据的 进行赋值操作
		for(int i=0;i<siteFileCountList.size();i++){//将设置好的 空对象进行赋值
			FileDownBySiteNumberBean fileInfo = siteFileCountList.get(i);
			if(null!=resultMap.get(fileInfo.getSiteName())){
				resultMap.get(fileInfo.getSiteName()).put(fileInfo.getSystemMonthYearDay(), fileInfo);
			}
		}
		
		/*for (String key : resultMap.keySet()) {
			System.out.println("key= "+ key );
			Map<String, FileDownBySiteNumberBean> map = resultMap.get(key);
			for (String day : map.keySet()) {
				System.out.println("   "+day+"  "+map.get(day).getCouNum()+"  "+map.get(day).getSystemMonthYearDay()+ " "+"  "+map.get(day).getSiteName());
			}
	    }*/
	
		JSONArray json = JSONArray.fromObject(resultMap);
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}
	
	
	//获得30S  省份下载分组统计分析
	@RequestMapping(value = "s30S/zoneDownAnaly", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String zoneDownAnaly(HttpServletRequest request) {
		
		String fileType =request.getParameter("fileType");//30日常数据
		String siteType =request.getParameter("siteType");//台站类型
		String userId = request.getParameter("userId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		List<GroupByDepPro> resultList = downHistoryService.zoneDown(fileType,siteType, startTime, endTime,userId);
		JSONArray json = JSONArray.fromObject(resultList);
		return json.toString();
	}
	//获得30S  部委下载分组统计分析
	@RequestMapping(value = "s30S/departmentDownAnaly", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String departmentDownAnaly(HttpServletRequest request) {
		
		String fileType =request.getParameter("fileType");//30日常数据
		String siteType =request.getParameter("siteType");//台站类型
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userId = request.getParameter("userId");
		
		List<GroupByDepPro> resultList = downHistoryService.departmentDown(fileType, siteType, startTime, endTime, userId);
		JSONArray json = JSONArray.fromObject(resultList);
		return json.toString();
	}
	
	//获得30S  月度下载分组统计分析
	@RequestMapping(value = "s30S/monthDownAnaly", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String monthDownAnaly(HttpServletRequest request) {
		
		List<GroupByDepPro> resultList = new ArrayList<GroupByDepPro>();
		String fileType ="1";//30日常数据
		String siteType ="1";//台站类型
		String year = request.getParameter("year");
		String startTime = year+"-01-01";
		String endTime = year+"-12-31";
		
		List<GroupByDepPro> monthDown = downHistoryService.monthDown(fileType, startTime, endTime);
		
		for (int i=1;i<=12;i++) {
			String month=i+"";
			if(i<=9){month="0"+i;}
			String timeStr=year+month;
			GroupByDepPro gd = new GroupByDepPro();
			gd.setGroupName(timeStr);
			gd.setGroupCnName(timeStr);
			
	    	gd.setDownNum("0");//初始值
			gd.setAvgDownNum("0");//初始值
			for(int j=0;j<monthDown.size();j++){
				GroupByDepPro gd2 = monthDown.get(j);
			    if(timeStr.equals(gd2.getGroupName())){
			    	gd.setDownNum(gd2.getDownNum());
					gd.setAvgDownNum(gd2.getDownNum());
			    }
			}
			resultList.add(gd);
		}
		JSONArray json = JSONArray.fromObject(resultList);
		return json.toString();
	}
	

	
	
	//地震应急数据  表格展示
	@RequestMapping(value = "earthQuake/downTable", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String downTable(HttpServletRequest request) {
		String earthQuakeId = request.getParameter("earthQuakeId");
		String userName = request.getParameter("userName");
		String year = request.getParameter("year");
		
		if(null==earthQuakeId || "".equals(earthQuakeId) || "all".equals(earthQuakeId)){
			earthQuakeId="";
		}
		if(null==userName || "".equals(userName) || "all".equals(userName)){
			userName="";
		}
		
		List<FileInfo> earthQuakeFileList = downHistoryService.downTable(userName, earthQuakeId, year);
		Map<String, Map<String, FileInfoDirect>> queryEarthQuakeDataJson = applyFileService.query_EarthQuake_Data_Json("", earthQuakeFileList, earthQuakeId,year);
		String resutlStr = applyFileService.query_EarthQuake_Data_Json(queryEarthQuakeDataJson);
		return resutlStr;
	}
	
	
	//地震应急数据  地图展示(年、应急事件、人)
	@RequestMapping(value = "earthQuake/downMap", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String downMap(HttpServletRequest request) throws Exception {
		
		String earthQuakeId = request.getParameter("earthQuakeId");
		String userName = request.getParameter("userName");
		String year = request.getParameter("year");
		
		if(null==earthQuakeId || "".equals(earthQuakeId) || "all".equals(earthQuakeId)){
			earthQuakeId="";
		}
		if(null==userName || "".equals(userName) || "all".equals(userName)){
			userName="";
		}
		
		List<SiteMapInfo> earthQuakeMap = downHistoryService.earthQuakeMap(userName, earthQuakeId, year);		
		JSONArray json = JSONArray.fromObject(earthQuakeMap);
		return json.toString();
	}
	
	

}
