package com.highfd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.ZoneInfo;
import com.highfd.bean.ZoneSiteInfo;
import com.highfd.common.PageInfo;
import com.highfd.common.ZoneSite;
import com.highfd.common.map.MapAll;
import com.highfd.service.ApplyFileService;
import com.highfd.service.SiteStationService;

@Controller
public class SiteStationController {
	
	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;
	
	public static Map<String,String> map= new HashMap<String,String>();
	
	//跳转到 基本站列表界面
	@RequestMapping(value = "baseSiteInfoListToJsp")
	@ResponseBody
	public ModelAndView baseSiteInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		
		if(siteType.equals("1")){
			mav.setViewName("site/baseSiteInfoList");
		}else if(siteType.equals("2")){
			mav.setViewName("site/areaSiteInfoList");
		}else if(siteType.equals("3")){
			mav.setViewName("site/shareSiteInfoList");
		}
		
		return mav;
	}
	//跳转到 基本站详细信息界面
	@RequestMapping(value = "showBaseSiteInfoToJsp")
	@ResponseBody
	public ModelAndView showBaseSiteInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		String siteNumber = request.getParameter("siteNumber");
		mav.addObject("siteNumber", siteNumber);
		
		if(siteType.equals("1")){
			mav.setViewName("site/showBaseSiteInfo");
		}else if(siteType.equals("2")){
			mav.setViewName("site/showAreaSiteInfo");
		}else if(siteType.equals("3")){
			mav.setViewName("site/showShareSiteInfo");
		}
		
		return mav;
	}
	//跳转到 单个台站修改界面
	@RequestMapping(value = "/updateBaseSiteInfoToJsp")
	@ResponseBody
	public ModelAndView updateBaseSiteInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		String siteNumber = request.getParameter("siteNumber");
		mav.addObject("siteNumber", siteNumber);
		
		mav.setViewName("site/updateBaseSiteInfo");
		return mav;
	}
	
	
	//跳转到流动站列表界面
	@RequestMapping(value = "areaSiteInfoListToJsp")
	@ResponseBody
	public ModelAndView areaSiteInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("site/areaSiteInfoList");
		return mav;
	}
	/*//跳转到流动台站详细信息界面
	@RequestMapping(value = "showAreaSiteInfoToJsp")
	@ResponseBody
	public ModelAndView showAreaSiteInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		String siteNumber = request.getParameter("siteNumber");
		mav.addObject("siteNumber", siteNumber);
		
		mav.setViewName("site/showAreaSiteInfo");
		return mav;
	}*/
	//跳转到流动站修改界面
	@RequestMapping(value = "/updateAreaSiteInfoToJsp")
	@ResponseBody
	public ModelAndView updateAreaSiteInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		String siteNumber = request.getParameter("siteNumber");
		mav.addObject("siteNumber", siteNumber);
		
		mav.setViewName("site/updateAreaSiteInfo");
		return mav;
	}
/**
 * **********上面是跳转  下面是数据********************************************************************************************************8	
 */
	
	
	//修改台站信息
	@RequestMapping(value = "/updateSiteInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateSiteInfo(HttpServletRequest request,@ModelAttribute SiteInfo siteInfo) throws Exception {
		siteService.updateBaseSiteInfo(siteInfo);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功！\"}]}";
	}
	
	//获得单个台站信息
	@RequestMapping(value = "/getBaseSiteInfoById", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getBaseSiteInfoById(HttpServletRequest request) {
		String siteNumber = request.getParameter("siteNumber");
		siteService.mapSite();
		SiteInfo siteInfo = MapAll.mapSite.get(siteNumber);
		JSONArray json = JSONArray.fromObject(siteInfo);
		return json.toString();
	}
	

	
	
	//获得站点列表
	@RequestMapping(value = "/getSiteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getSiteInfoList(HttpServletRequest request) {
		String siteType = request.getParameter("siteType");
		String userParam = request.getParameter("userParam");
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(siteType,userParam);
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return json.toString();
	}
	
	//获得站点列表（暂时未用到）
	@RequestMapping(value = "/getSuperSiteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getSuperSiteInfoList(HttpServletRequest request) {

		String siteNumberArray = request.getParameter("siteNumberArray");
		String siteName = request.getParameter("siteName");
		String zoneArray = request.getParameter("zoneArray");
		String type = request.getParameter("type");
		
		String siteLat1 = request.getParameter("siteLat1");//纬度
		String siteLat2 = request.getParameter("siteLat2");//纬度
		String siteLon1 = request.getParameter("siteLon1");//经度
		String siteLon2 = request.getParameter("siteLon2");//经度
		
		List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, type);
		
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return json.toString();
	}
	
	//获得省份列表
	@RequestMapping(value = "/getZoneInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getZoneInfoList() {
		List<ZoneInfo> siteInfoList = siteService.getZoneInfoList();
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return json.toString();
	}
	
	//获得树状结构上部的  全部的 省份台站列表  json省份——台站列表
	@RequestMapping(value = "/getZoneSiteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getZoneSiteInfoList(HttpServletRequest request) {
		String siteType = request.getParameter("siteType");
		//int siteTypeInt = Integer.valueOf(siteType)+1;
		//String siteTypeStr = siteTypeInt+"";
		String smallLat = request.getParameter("dongwei");
		String bigLat = request.getParameter("xiwei");
		String smallLon = request.getParameter("dongjin");
		String bigLon = request.getParameter("dongjin");
		
		List<SiteInfo> siteInfoList = new ArrayList<SiteInfo>();
		if(siteType.equals("1")){
			siteInfoList = siteService.getZoneSiteInfoList(smallLat, bigLat, smallLon, bigLon,siteType);
		}else if(siteType.equals("2")){
			siteInfoList = siteService.getZoneSiteInfoList_GY(smallLat, bigLat, smallLon, bigLon);
		}else if(siteType.equals("3")){
			siteInfoList = siteService.getZoneSiteInfoList(smallLat, bigLat, smallLon, bigLon,"3");
		}
		List<ZoneSiteInfo> listAll = new ZoneSite().siteLisToZoneList(siteInfoList);//将 台站列表——————>省份包裹着台站列表
		JSONArray json = JSONArray.fromObject(listAll);
		return "{\"provincesList\":"+json.toString()+"}";
	}
	
	
	//申请台站 展示的时候用
	//获得树状结构上部的  全部的 省份台站列表  json（省份——包裹台站）列表
	@RequestMapping(value = "/ShowZoneSiteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String ShowZoneSiteInfoList(HttpServletRequest request) {
		
		String applyId = request.getParameter("applyId");
		String siteType = request.getParameter("siteType");
		int siteTypeInt = Integer.valueOf(siteType);
		List<SiteInfo> siteInfoList = new ArrayList<SiteInfo>();
		if(siteTypeInt==1){
			siteInfoList = siteService.applySiteZoneList(applyId,siteTypeInt+"");
		}else if(siteTypeInt==2){
			siteInfoList = siteService.applyZoneSiteList_GY(applyId,siteTypeInt+"");
		}else if(siteTypeInt==3){
			siteInfoList = siteService.applySiteZoneList(applyId,siteTypeInt+"");
		}
		List<ZoneSiteInfo> listAll = new ZoneSite().siteLisToZoneList(siteInfoList);//将 台站列表——————>省份包裹着台站列表
		JSONArray json = JSONArray.fromObject(listAll);
		return "{\"provincesList\":"+json.toString()+"}";
	}
	
	
	//根据地震应急事件Id  获得  台站列表
	@RequestMapping(value = "/getEarthquakeSiteList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getEarthquakeSiteList(HttpServletRequest request) {
		String earthQuakeId = request.getParameter("earthQuakeId");
		
		//地震应急事件   单个详细信息
		EarthQuake earthQuakeDetail = applyFileService.earthQuake_detail(earthQuakeId);
		
		List<SiteInfo> siteInfoList = new ArrayList<SiteInfo>();	
		siteInfoList = siteService.getEarthquakeSiteList(earthQuakeId,earthQuakeDetail.getYear());//查询改地震应急事件 的台站列表，并去重
		List<ZoneSiteInfo> listAll = new ZoneSite().siteLisToZoneList(siteInfoList);//将 台站列表——————>省份包裹着台站列表
		JSONArray json = JSONArray.fromObject(listAll);
		return "{\"provincesList\":"+json.toString()+"}";
	}
	
	
	//查询应急数据
	@RequestMapping(value = "/earthQuakeConfigQuery", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeConfigQuery() {
		List<EarthQuakeConfig> earthquakeconfigList = siteService.earthQuakeConfigQuery();
		JSONArray json = JSONArray.fromObject(earthquakeconfigList);
		return json.toString();
	}
	//查询应急数据
	@RequestMapping(value = "/earthQuakeConfigUpdate", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeConfigUpdate(@ModelAttribute EarthQuakeConfig earthquakeconfig) {
 		String code = "-1";
		String msg = "保存失败！";
		try {
			if(earthquakeconfig.getId()!=null&&!("").equals(earthquakeconfig.getId())){
				siteService.earthQuakeConfigUpdate(earthquakeconfig);
			}else{
				earthquakeconfig.setId(1000);
				siteService.earthQuakeConfigInsert(earthquakeconfig);
			}
			code="1";
			msg = "保存成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
	}
	
}