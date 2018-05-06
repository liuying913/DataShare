package com.highfd.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.ZoneSite;
import com.highfd.common.json.Json;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

@Component
@Controller
@RequestMapping(value="/showData")
public class DataShowController {

	@Autowired
	UserService userService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	DownHistoryService downHistoryService;

	
	/**
	 * **********30S 日常数据**********************************************************
	 * @param request
	 */
	@RequestMapping(value = "show30SToJsp")
	@ResponseBody
	public ModelAndView show30SToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/show");
		return mav;
	}
	//30S 260站 月度 展示
	@RequestMapping(value = "s30sMoreMonthToJsp")
	@ResponseBody
	public ModelAndView s30s260MonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/moreMonth");
		return mav;
	}
	//30S 260站 年度 展示
	@RequestMapping(value = "s30sMoreYearToJsp")
	@ResponseBody
	public ModelAndView s30sMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/moreYear");
		return mav;
	}
	//30S 单站 月度 展示
	@RequestMapping(value = "s30sOneMonthToJsp")
	@ResponseBody
	public ModelAndView s30sOneMonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/oneMonth");
		return mav;
	}
	//30S 单站 年度 展示
	@RequestMapping(value = "s30sOneYearToJsp")
	@ResponseBody
	public ModelAndView s30sOneYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/oneYear");
		return mav;
	}
	//30S 260站 日度  地图跳转
	@RequestMapping(value = "s30sDayToJsp")
	@ResponseBody
	public ModelAndView s30sDayToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/30s/moreDay");
		return mav;
	}

	/**
	 * **********流动观测数据目录生成**********************************************************
	 * @param request
	 */
	@RequestMapping(value = "showFlowToJsp")
	@ResponseBody
	public ModelAndView showFlowToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/flow/show");
		return mav;
	}
	//流动观测数据 260站 年度 展示
	@RequestMapping(value = "flowMoreYearToJsp")
	@ResponseBody
	public ModelAndView flowMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/flow/moreYear");
		return mav;
	}
	//流动观测数据 单站 年度 展示
	@RequestMapping(value = "flowOneYearToJsp")
	@ResponseBody
	public ModelAndView flowOneYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/flow/oneYear");
		return mav;
	}
	//流动观测数据 260站 日度  地图跳转
	@RequestMapping(value = "flowDayToJsp")
	@ResponseBody
	public ModelAndView flowDayToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 2);
		mav.addObject("fileType", 2);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/flow/moreDay");
		return mav;
	}
	
	/**
	 * **********共享数据**********************************************************
	 * @param request
	 */
	@RequestMapping(value = "showShareToJsp")
	@ResponseBody
	public ModelAndView showShareToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/show");
		return mav;
	}
	//共享数据 260站 月度 展示
	@RequestMapping(value = "shareMoreMonthToJsp")
	@ResponseBody
	public ModelAndView shareMoreMonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/moreMonth");
		return mav;
	}
	//共享数据 260站 年度 展示
	@RequestMapping(value = "shareMoreYearToJsp")
	@ResponseBody
	public ModelAndView shareMoreYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/moreYear");
		return mav;
	}
	//共享数据 单站 月度 展示
	@RequestMapping(value = "shareOneMonthToJsp")
	@ResponseBody
	public ModelAndView shareOneMonthToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/oneMonth");
		return mav;
	}
	//共享数据 单站 年度 展示
	@RequestMapping(value = "shareOneYearToJsp")
	@ResponseBody
	public ModelAndView shareOneYearToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/oneYear");
		return mav;
	}
	//共享数据 260站 日度  地图跳转
	@RequestMapping(value = "shareDayToJsp")
	@ResponseBody
	public ModelAndView shareDayToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.addObject("fileType", 3);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/share/moreDay");
		return mav;
	}

	
	/**
	 * **********地震应急数据**********************************************************
	 * @param request
	 */
	//应急事件列表
	@RequestMapping(value = "contingencyEventListToJsp")
	@ResponseBody
	public ModelAndView ContingencyEventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showData/earthQuake/eventList");
		return mav;
	}
	
	//展示应急事件 详细信息
	@RequestMapping(value = "showEventMessageToJsp")
	@ResponseBody
	public ModelAndView showEventMessageToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String earthQuakeId = request.getParameter("earthQuakeId");
		mav.addObject("earthQuakeId", earthQuakeId);
		mav.setViewName("showData/earthQuake/showMessage");
		return mav;
	}
	
	//数据展示
	@RequestMapping(value = "showEarthQuakeToJsp")
	@ResponseBody
	public ModelAndView showEarthQuakeToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String earthQuakeId = request.getParameter("earthQuakeId");
		mav.addObject("earthQuakeId", earthQuakeId);
		mav.setViewName("showData/earthQuake/show");
		return mav;
	}
	
	//地图展示
	@RequestMapping(value = "earthQuakeMapToJsp")
	@ResponseBody
	public ModelAndView earthQuakeMapToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String earthQuakeId = request.getParameter("earthQuakeId");
		mav.addObject("earthQuakeId", earthQuakeId);
		mav.setViewName("showData/earthQuake/map");
		return mav;
	}

	
	/**
	 * **********Json数据查询**********************************************************
	 * @param request
	 */
	//地震应急事件   单个详细信息
	@RequestMapping(value = "/earthQuakeMessage", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeMessage(HttpServletRequest request) {
		String id = request.getParameter("earthQuakeId");
		EarthQuake earthQuake = applyFileService.earthQuake_detail(id);
		JSONArray json = JSONArray.fromObject(earthQuake);
		return json.toString();
	}
	
	//地震应急    事件列表
	@RequestMapping(value = "/contingencyEventList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String contingencyEventList(HttpServletRequest request) {
		JSONArray json = new JSONArray();
		List<EarthQuake> queryEarthQuakeList = new ArrayList<EarthQuake>();
		String keyWords = request.getParameter("keyWords");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		String year = request.getParameter("year");
		
		/*if(null==keyWords || "".equals(keyWords)){
			for (Entry<String, EarthQuake> entry : MapAll.mapEarthQuake.entrySet()) {
		       EarthQuake earthQuake = entry.getValue();
		       queryEarthQuakeList.add(earthQuake);
		    }
			json = JSONArray.fromObject(MapAll.mapEarthQuake);
		}else{
			queryEarthQuakeList = applyFileService.query_EarthQuake_List(keyWords);
		}*/
		
		boolean show2017=false;
		String show = request.getParameter("show");
		if(null!=show && !"".equals(show)){
			show2017=true;
		}
		queryEarthQuakeList = applyFileService.query_EarthQuake_List(keyWords,show2017,startTime,endTime,year);
		json = JSONArray.fromObject(queryEarthQuakeList);
		return json.toString();
	}

	//地震应急    日志目录查看
	@RequestMapping(value = "/earthQuakeData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeData(HttpServletRequest request) {
		String earthQuakeId = request.getParameter("earthQuakeId");
		String siteNumberArray = request.getParameter("taizhang");
		String zoneArray = request.getParameter("shengfen");
		String departmentArray = request.getParameter("buwei");
		
		//地震应急事件   单个详细信息
		EarthQuake earthQuakeDetail = applyFileService.earthQuake_detail(earthQuakeId);
		List<FileInfo> earthQuakeFileList = applyFileService.query_EarthQuake_Data(earthQuakeId,earthQuakeDetail.getYear(),zoneArray, departmentArray, siteNumberArray);
		Map<String, Map<String, FileInfoDirect>> queryEarthQuakeDataJson = applyFileService.query_EarthQuake_Data_Json(siteNumberArray, earthQuakeFileList, earthQuakeId,earthQuakeDetail.getYear());
		String resutlStr = applyFileService.query_EarthQuake_Data_Json(queryEarthQuakeDataJson);
		return resutlStr;
	}
	
	
	
	//数据整理   单站 多站   月度、年度 数据
	@RequestMapping(value = "/s30s260MonthData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryApplyFile(HttpServletRequest request) {
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		String year = request.getParameter("year");
		String endTime = request.getParameter("ddlmonth");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		if(null==endTime || "".equals(endTime)){//年度
			st = year+"-01-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(12));
		}else{//月度
			isYear= false;
			st = year+"-"+endTime+"-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(endTime));
		}
		
		//String fileYear = request.getParameter("fileYear");
		//String fileDayYear = request.getParameter("fileDayYear");
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		String taizhang = request.getParameter("taizhang");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, siteTypeInt+"");
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		
		List<FileInfo> fileInfoAllList = applyFileService.getApplyFileInfoList(st, et, "", "", ZoneSite.siteListToStr(siteInfoList),  "", "", "", "",fileType, "",isYear,year);
		
		Map<String, Map<String, FileInfoDirect>> s30s260MonthData = downHistoryService.s30s260MonthData(siteInfoList, fileInfoAllList, dayNumbers);
		//JSONArray json = JSONArray.fromObject(resultMap);
		return downHistoryService.dataToJson(s30s260MonthData);
	}
	
	//获得30S  每日历元数量
	@RequestMapping(value = "/s30sOneMonthData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String zoneDownAnaly(HttpServletRequest request) {
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		String year = request.getParameter("year");
		String endTime = request.getParameter("ddlmonth");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		if(null==endTime || "".equals(endTime)){//年度
			st = year+"-01-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(12));
		}else{//月度
			isYear= false;
			st = year+"-"+endTime+"-01";
			et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(endTime));
		}
		
		//String fileYear = request.getParameter("fileYear");
		//String fileDayYear = request.getParameter("fileDayYear");
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		String taizhang = request.getParameter("taizhang");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, siteTypeInt+"");
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		
		List<FileInfo> fileInfoAllList = applyFileService.getApplyFileInfoList(st, et, "", "", "",  "", "", "", "",fileType, "",isYear,year);
		
		List<FileInfoDirect> s30sOneMonthData = downHistoryService.s30sOneMonthData(siteInfoList, fileInfoAllList, dayNumbers);
		JSONArray json = JSONArray.fromObject(s30sOneMonthData);
		return json.toString();
	}
	
	// 多站 单日数据  地图
	@RequestMapping(value = "/querycdayData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String querycdayData(HttpServletRequest request) {
		
		String startTime = request.getParameter("year");
		String st = startTime;
		String et = startTime;
		String year = st.substring(0, st.indexOf("-"));
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		String siteNumberArray = request.getParameter("taizhang");
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		
		Map<String, Map<String,SiteMapInfo>> resultMap = new HashMap<String, Map<String,SiteMapInfo>>();
		
		//*************测试
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(siteNumberArray,buwei,shengfen,siteType);
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			
			SiteMapInfo fd = new SiteMapInfo();
			
			fd.setFileFlag(0);
			fd.setSiteNumber(siteInfo.getSiteNumber());
			fd.setSiteName(siteInfo.getSiteName());
			fd.setSiteLat(siteInfo.getSiteLat());
			fd.setSiteLng(siteInfo.getSiteLng());
			
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			fileInfoMap2.put("one", fd);
			resultMap.put(siteInfo.getSiteName(), fileInfoMap2);
		}
		//***测试*****************
		
		Map<String, SiteInfo> mapSite = MapAll.mapSite;
		List<FileInfo> fileInfoAllList = applyFileService.getApplyFileInfoList(st, et, "", "", siteNumberArray,  "", "", "", "",fileType, "",true,year);
		
		//有数据的 进行赋值操作
		for(int i=0;i<fileInfoAllList.size();i++){
			FileInfo fileInfo = fileInfoAllList.get(i);
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			SiteMapInfo fd = new SiteMapInfo();
			
			fd.setFileFlag(fileInfo.getFileFlag());
			fd.setSiteNumber(fileInfo.getSiteNumber());
			fd.setSiteName(mapSite.get(fileInfo.getSiteNumber()).getSiteName());
			fd.setSiteLat(mapSite.get(fileInfo.getSiteNumber()).getSiteLat());
			fd.setSiteLng(mapSite.get(fileInfo.getSiteNumber()).getSiteLng());
			
			//fileInfoMap2.put(fileInfo.getSystemMonthDayNumer(), fd);
			if(null!=resultMap.get(fileInfo.getSiteName()) ){
				fileInfoMap2.put("one", fd);
				resultMap.put(fileInfo.getSiteName(), fileInfoMap2);
			}
		}
		
		JSONArray json = JSONArray.fromObject(resultMap);
		return Json.getJsonArrayToStr(json);
	}
	
	
	//地震应急数据  地图展示
	@RequestMapping(value = "/earthQuakeMap", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeMap(HttpServletRequest request) {
		
		String earthQuakeId = request.getParameter("earthQuakeId");
		
		EarthQuake earthQuakeDetail = applyFileService.earthQuake_detail(earthQuakeId);//获得年份
		List<SiteMapInfo> earthQuakeMap = applyFileService.earthQuakeMap(earthQuakeId, earthQuakeDetail.getYear());
		
		JSONArray json = JSONArray.fromObject(earthQuakeMap);
		return json.toString();
	}
	
	
	//查询文件列表 中的 编号列表
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getYearDayFileInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getYearDayFileInfoList(HttpServletRequest request) throws ParseException {
		String fileYear = "";
		String startTime = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		if(startTime==null || "".equals(startTime) || startTime.indexOf("-")==-1){
			fileYear = new Date().getYear()+"";
		}else{
			fileYear = startTime.substring(0,startTime.indexOf("-"));
		}
		Map<String, Map<String,List<FileInfo>>> map = new TreeMap<String, Map<String,List<FileInfo>>>();
		
		List<FileInfo> list = new ArrayList<FileInfo>();
		Map<String, List<FileInfo>> yearMap = new TreeMap<String, List<FileInfo>>();
		Integer startTime_int = DayNumberOfOneYear.getDayNumberOfOneYear(startTime);
		Integer endTime_int   = DayNumberOfOneYear.getDayNumberOfOneYear(endTime);
		for(int i=startTime_int;i<=endTime_int;i++){
			String strNum = i+"";
			if(strNum.length()==1){
				strNum="00"+strNum;
			}else if(strNum.length()==2){
				strNum="0"+strNum;
			}
			strNum="1"+strNum;
			yearMap.put(strNum, list);
		}
		map.put(fileYear, yearMap);
		
		JSONArray json = JSONArray.fromObject(map);
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}
}
