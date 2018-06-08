package com.highfd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeApply;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UserInfo;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.beanFile.note.NoteInfo;
import com.highfd.common.PageInfo;
import com.highfd.common.configType.ConfigTypeSet;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.HighFDService;
import com.highfd.service.ManagerService;
import com.highfd.service.NoteInfoService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

@Component
@Controller
@RequestMapping(value="/earthQuake")
public class EarthQuakeController {

	@Autowired
	UserService userService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	ManagerService managerService;
	@Autowired
	HighFDService highFDService;
	@Autowired
	NoteInfoService noteInfoService;

	//数据申请     应急事件列表
	@RequestMapping(value = "eventListToJsp")
	@ResponseBody
	public ModelAndView eventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String earthquake_id = request.getParameter("earthQuakeId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("earthQuakeId", earthquake_id);
		mav.setViewName("apply/earthQuake/eventList");
		return mav;
	}
	
	//针对某个应急事件的   用户申请列表
	@RequestMapping(value = "applyListToJsp")
	@ResponseBody
	public ModelAndView applyListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String earthQuakeId = request.getParameter("earthQuakeId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("earthQuakeId", earthQuakeId);
		mav.setViewName("apply/earthQuake/applyList");
		return mav;
	}
	
	//某用户  某个指定的应急事件，最后的有效时间  是
	@RequestMapping(value = "/applyEndTime", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String applyEndTime(HttpServletRequest request) {
		try{
			HttpSession session = request.getSession(false);
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			String userId = userInfo.getUserId()+"";
			String earthQuakeId = request.getParameter("earthQuakeId");
			//String type = request.getParameter("type");
			String applyFlag = managerService.applyEndTime(userId, earthQuakeId, "5");
			String queryNoApplyFlag = managerService.queryNoApply(userId, earthQuakeId, "4");
			if("-1".equals(applyFlag)){//  您有未过期的申请单
				return "{\"date\":[{\"applyFlag\":\""+applyFlag+"\",\"msg\":\"您有未过期的申请单！\"}]}";
			}else if("-1".equals(queryNoApplyFlag)){//您有未审核的申请单
				return "{\"date\":[{\"applyFlag\":\""+queryNoApplyFlag+"\",\"msg\":\"您有未审核的申请单！\"}]}";
			}else{
				return "{\"date\":[{\"applyFlag\":\""+1+"\",\"msg\":\"申请完成！\"}]}";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//应急事件申请列表(根据权限查询)
	@RequestMapping(value = "/queryApplyList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryApplyList(HttpServletRequest request) {
		try{
			
			String userId=null;
			HttpSession session = request.getSession(false);
			if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
				UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
				userId = userInfo.getUserId()+"";
			}else{
				userId = request.getParameter("userId");
			}
			String id = request.getParameter("id");
			String earthQuakeId = request.getParameter("earthQuakeId");
			String state = request.getParameter("state");
			String txtParam = request.getParameter("txtParam");
			
			String noCheckFlag = request.getParameter("noCheckFlag");
			boolean flag = true;
			if(null!=noCheckFlag && !"".equals(noCheckFlag)){
				flag = false;
			}
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList(id, userId, earthQuakeId, state,flag,  txtParam);
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//应急事件申请列表(根据权限查询)
	@RequestMapping(value = "/applyEventListForUser", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String applyEventListForUser(HttpServletRequest request) {
		try{
			HttpSession session = request.getSession(false);
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			String userId = userInfo.getUserId()+"";
			String earthQuakeId = request.getParameter("earthQuakeId");
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList("", userId, earthQuakeId, null,false,"");
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//普通用户 待审核 列表 (仅仅该用户的  未审核记录)
	@RequestMapping(value = "/waitCheckListForUser", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String waitCheckListForUser(HttpServletRequest request) {
		try{
			String userId=null;
			HttpSession session = request.getSession(false);
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			userId = userInfo.getUserId()+"";
			String state = request.getParameter("state");
			String txtParam = request.getParameter("txtParam");
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList("", userId, "", state,false,  txtParam);
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	//已审核申请 、  管理员的  列表
	@RequestMapping(value = "overCheckListToJsp")
	@ResponseBody
	public ModelAndView overCheckListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();
		
		String isManager = request.getParameter("isManager");
		if(null!=isManager && "false".equals(isManager)){
			noteInfoService.updateNoteReadFlag(userId+"", "4");
		}
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("isManager", isManager);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("apply/earthQuake/overCheckList");
		return mav;
	}
	
	
	//已审核申请 、  管理员的  列表(Json)
	@RequestMapping(value = "/overCheckList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String overCheckList(HttpServletRequest request) {
		try{
			String isManager = request.getParameter("isManager");
			String userId=null;
			HttpSession session = request.getSession(false);
			if(session.getAttribute("userType").toString().equals("1")  && null!=isManager && "true".equals(isManager)){//不是管理员
				userId = "all";
			}else{
				UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
				userId = userInfo.getUserId()+"";
			}
			
			String state = request.getParameter("state");
			String txtParam = request.getParameter("txtParam");
			
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList("", userId, "", state,true,  txtParam);
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	
	//等待审核列表  普通用户
	@RequestMapping(value = "waitCheckListForUserToJsp")
	@ResponseBody
	public ModelAndView waitCheckListForUserToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("apply/earthQuake/waitCheckListForUser");
		return mav;
	}
	
	//等待审核  管理员
	@RequestMapping(value = "waitCheckListForManagerToJsp")
	@ResponseBody
	public ModelAndView waitCheckListForManagerToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("apply/earthQuake/waitCheckListForManager");
		return mav;
	}
	
	
	//应急事件申请   审核 界面
	@RequestMapping(value = "checkToJsp")
	@ResponseBody
	public ModelAndView checkToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("id", id);
		mav.setViewName("apply/earthQuake/check");
		return mav;
	}
	
	//应急事件申请   审核 界面
	@RequestMapping(value = "queryCheckToJsp")
	@ResponseBody
	public ModelAndView queryCheckToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("id", id);
		mav.setViewName("apply/earthQuake/queryCheck");
		return mav;
	}
	
	//应急事件申请列表(根据权限查询)
	@RequestMapping(value = "/queryCheck", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryCheck(HttpServletRequest request) {
		try{
			String id = request.getParameter("id");
			String noCheckFlag = request.getParameter("noCheckFlag");
			boolean flag = true;
			if(null!=noCheckFlag && !"".equals(noCheckFlag)){
				flag = false;
			}
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList(id, "", "", "",flag,"");
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList.get(0));
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//添加应急事件 申请
	@RequestMapping(value = "/insertApply", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertApply(HttpServletRequest request,@ModelAttribute EarthQuakeApply earthQuakeApply) {
		String code="1";
		String message="";
		try {
			HttpSession session = request.getSession(false);
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			int nextId = managerService.getNextId("EARTHQUAKE_APPLY_SEQ");
			int userId = userInfo.getUserId();
			earthQuakeApply.setUserId(userId);
			earthQuakeApply.setId(nextId);
			managerService.insertEarthQuakeApply(earthQuakeApply);
			
			
			NoteInfo noteInfo = new NoteInfo();//通知管理员 有未审核的申请
			noteInfo.setApplyId(nextId);
			noteInfo.setContent("您有未审核的地震应急数据申请！");
			noteInfo.setApplyFileType(4);//地震应急数据
			noteInfo.setIsManager(1);//通知 管理员
			noteInfo.setProcess(4);//有待审核的数据
			noteInfoService.insertNoteInfo(noteInfo);
			
		
			//*******消息通知(普通用户)*********
			NoteInfo noteInfo2 = new NoteInfo();
			noteInfo2.setApplyId(nextId);
			noteInfo2.setContent("您有等待审核的地震应急数据申请！");
			noteInfo2.setApplyFileType(4);//地震应急数据
			noteInfo2.setIsManager(2);//通知 用户
			noteInfo2.setProcess(4);//有等待审核的申请
			noteInfo2.setReadFlag(1);
			
			noteInfoService.insertNoteInfo(noteInfo2);
			
			
		} catch (Exception e) {
			code = "-1";
			message = "添加失败！";
			e.printStackTrace();
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	
	//修改应急事件申请 状态
	@RequestMapping(value = "/updateApplyType", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateApplyType(HttpServletRequest request,@ModelAttribute EarthQuakeApply earthQuakeApply) {
		String code="1";
		String message="";
		try {
			int applyId = earthQuakeApply.getId();
			
			int applyCheck = earthQuakeApply.getType();
			List<EarthQuakeConfig> earthQuakeConfigQuery = siteService.earthQuakeConfigQuery();
			String days = earthQuakeConfigQuery.get(0).getApplyvalidityday();
			managerService.updateEarthQuakeApplyType(applyId, applyCheck, earthQuakeApply.getRemark(),Integer.valueOf(days));
			
			//普通用户 将等待申请状态改成 已读
			NoteInfo updateNote = new NoteInfo();
			updateNote.setApplyId(Integer.valueOf(applyId));
			updateNote.setApplyFileType(4);//30数据
			updateNote.setIsManager(2);//普通用户
			updateNote.setProcess(4);//待审核
			updateNote.setReadFlag(2);//已经读取
			updateNote.setContent("您有未审核的地震应急数据申请！");
			noteInfoService.updateNoteInfo(updateNote);
			
			if(applyCheck==5){//审核通过的话
				
				//用户消息处理
				NoteInfo noteInfo = new NoteInfo();
				noteInfo.setApplyId(Integer.valueOf(applyId));
				noteInfo.setContent("您的地震应急数据申请通过了！");
				noteInfo.setApplyFileType(4);//30数据
				noteInfo.setIsManager(2);//普通用户
				noteInfo.setProcess(5);//申请通过
				noteInfoService.insertNoteInfo(noteInfo);

				userService.userMap();//用户的所有申请单（有效期内）的  数据文件列表
			}else{
				//用户消息提醒
				NoteInfo noteInfo = new NoteInfo();
				noteInfo.setApplyId(Integer.valueOf(applyId));
				noteInfo.setContent("您的地震应急数据申请未获得通过！");
				noteInfo.setApplyFileType(4);//30数据
				noteInfo.setIsManager(2);//普通用户
				noteInfo.setProcess(6);//申请 不 通过
				noteInfoService.insertNoteInfo(noteInfo);
			}
			
			
			//管理员 将等待申请状态改成 已读
			NoteInfo updateManagerNote = new NoteInfo();
			updateManagerNote.setApplyId(Integer.valueOf(applyId));
			updateManagerNote.setApplyFileType(4);//30数据
			updateManagerNote.setIsManager(1);//管理员
			updateManagerNote.setProcess(4);//待审核
			updateManagerNote.setReadFlag(2);//已经读取
			updateManagerNote.setContent("您有等待审核的地震应急数据申请！");
			noteInfoService.updateNoteInfo(updateManagerNote);
			
		} catch (Exception e) {
			code = "-1";
			message = "修改失败！";
			e.printStackTrace();
		}
		System.out.println("审核完成了吗");
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	
	
	
	//下载数据查询     应急事件列表
	@RequestMapping(value = "userEventListToJsp")
	@ResponseBody
	public ModelAndView userEventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String earthquake_id = request.getParameter("earthQuakeId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("earthQuakeId", earthquake_id);
		mav.setViewName("apply/earthQuake/userEventList");
		return mav;
	}
	
	
	//下载数据查询     应急事件列表  
	@RequestMapping(value = "userDownHistoryListToJsp")  
	@ResponseBody
	public ModelAndView userDownHistoryListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String earthquake_id = request.getParameter("earthQuakeId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("earthQuakeId", earthquake_id);
		mav.setViewName("apply/earthQuake/userDownHistoryList");
		return mav;
	}
	
	
	//用户下载的  地震应急事件 数据文件列表
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downHistoryList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String downHistoryList(HttpServletRequest request) {
		String earthQuakeId = request.getParameter("earthQuakeId");
		String iniParam = request.getParameter("iniParam");
		
		String userName=null;
		HttpSession session = request.getSession(false);
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			userName = userInfo.getUserName();
		}
		
		EarthQuake earthQuake = applyFileService.earthQuake_detail(earthQuakeId);
		String year = earthQuake.getYear();
		
		PageInfo pageinfo = new PageInfo(); 
		pageinfo.setPageSize(8);
		
		String currentPage = request.getParameter("currentPage");
		if(!("").equals(currentPage)&&currentPage!=null){
			pageinfo.setCurrentPage(Integer.parseInt(currentPage));
		}
		
		List<FileHistroyDown> downHistoryList = managerService.getUserDownHistoryList(year, earthQuakeId, userName, iniParam, pageinfo);
		pageinfo.setList(downHistoryList);
		JSONArray json = JSONArray.fromObject(pageinfo);
		return json.toString();
	}
	
	//统计分析下面的  综合统计
	@RequestMapping(value = "analyTableToJsp")
	@ResponseBody
	public ModelAndView analyTableToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/earthQuake/userApplyList");
		return mav;
	}
	//综合分析  申请列表
	@RequestMapping(value = "/analyTableApplyList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String analyTableApplyList(HttpServletRequest request) {
		try{
			String userId = request.getParameter("userId");
			String id = request.getParameter("id");
			String earthQuakeId = request.getParameter("earthQuakeId");
			String state = request.getParameter("state");
			String txtParam = request.getParameter("txtParam");
			List<EarthQuakeApply> applyList = managerService.queryEarthQuakeApplyList(id, userId, earthQuakeId, state, true,  txtParam);
			ConfigTypeSet.setEarthQuakeType(applyList);
			JSONArray json = JSONArray.fromObject(applyList);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	
	//统计分析下面的  事件申请单 地图展示
	@RequestMapping(value = "eventApplyMapToJsp")
	@ResponseBody
	public ModelAndView eventApplyMapToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("analysis/earthQuake/downMap");
		
		return mav;
	}
	//获得30S  单日   或者 时间跨度    地图展示
	//http://localhost:8080/DataShare/eventDownMap.action?startTime=2016-01-02&fileType=1
	@RequestMapping(value = "eventDownMap", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String eventDownMap(HttpServletRequest request) {
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(null==endTime || "".equals(endTime)){endTime = startTime;}//判断是一天的还是多天的
		
		String userId = request.getParameter("userId");
		String eventId = request.getParameter("eventId");
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
		
		queryEarthQuakeList = applyFileService.query_EarthQuake_List(keyWords,true,startTime,endTime,year);
		json = JSONArray.fromObject(queryEarthQuakeList);
		return json.toString();
	}
	
	
	//地震应急    事件列表
	@RequestMapping(value = "/earthQuakeListOver2017", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeListOver2017(HttpServletRequest request) {
		JSONArray json = new JSONArray();
		List<EarthQuake> queryEarthQuakeList = new ArrayList<EarthQuake>();
		String keyWords = request.getParameter("keyWords");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		String year = request.getParameter("year");
		if(null==year || "".equals(year)){
			year = TimeUtils.getNowYear()+"";
		}
		queryEarthQuakeList = applyFileService.query_EarthQuake_List(keyWords,true,startTime,endTime,year);
		json = JSONArray.fromObject(queryEarthQuakeList);
		return json.toString();
	}
}
