package com.highfd.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.GreatEventInfo;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.GreatEventService;
import com.highfd.service.UserService;

@Component
@Controller
@RequestMapping(value="/greatEvent")
public class GreatEventController {

	@Autowired
	UserService userService;
	
	@Autowired
	DownHistoryService downHistoryService;
	
	@Autowired
	ApplyFileService applyFileService;
	
	@Autowired
	GreatEventService greatEventService;
	
	/**
	 * 站点大事记列表 jsp
	 */
	@RequestMapping(value = "/listToJsp")
	@ResponseBody
	public ModelAndView listToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/greatEvent/list");
		return mav;
	}
	
	/**
	 * 站点大事记详细信息列表 jsp
	 */
	@RequestMapping(value = "/detailToJsp")
	@ResponseBody
	public ModelAndView detailToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("type", type);
		mav.addObject("applyTitle", applyTitle);
		if("1".equals(type)){//更换接收机
			mav.setViewName("showData/greatEvent/gnssDetail");
		}else if("2".equals(type)){//天线
			mav.setViewName("showData/greatEvent/aerialDetail");
		}else if("3".equals(type)){//固件升级
			mav.setViewName("showData/greatEvent/firmwareDetail");
		}
		return mav;
	}
	
	
	/**
	 * 站点大事记编辑信息界面
	 */
	@RequestMapping(value = "/detailEditToJsp")
	@ResponseBody
	public ModelAndView detailEditToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("type", type);
		mav.addObject("applyTitle", applyTitle);
		if("1".equals(type)){//更换接收机
			mav.setViewName("showData/greatEvent/editGnssDetail");
		}else if("2".equals(type)){//天线
			mav.setViewName("showData/greatEvent/editAerialDetail");
		}else if("3".equals(type)){//固件升级
			mav.setViewName("showData/greatEvent/editFirmwareDetail");
		}
		return mav;
	}
	
	/**
	 * 需要站点大事记
	 */
	@RequestMapping(value = "/updateGreatEvent", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateGreatEvent(HttpServletRequest request,@ModelAttribute GreatEventInfo info) {
		String code = "-1";
		String msg = "提交失败！，请重新提交";
		try {
			greatEventService.updateApplyUser_FTP(info);
			code="1";
			msg = "保存成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
	}
	
	/**
	 * 超级站点大事记列表
	 */
	@RequestMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String list(HttpServletRequest request) {
		String param = request.getParameter("param");
		List<GreatEventInfo> greatEventList = greatEventService.selectSuperGreatEventList("", "", param);
		JSONArray json = JSONArray.fromObject(greatEventList);
		return json.toString();
	}
	
	/**
	 * 获得某个台站，某一类型的大事记列表
	 */
	@RequestMapping(value = "/typelList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String typelList(HttpServletRequest request) {
		String id = request.getParameter("id");
		String keyWords = request.getParameter("keyWords");
		String state = request.getParameter("state");
		String siteNumber = request.getParameter("siteNumber");
		String type = request.getParameter("type");
		List<GreatEventInfo> greatEventList = greatEventService.selectGreatEventList(id,type,siteNumber,keyWords,state);
		JSONArray json = JSONArray.fromObject(greatEventList);
		return json.toString();
	}
	
	/**
	 * 添加站点大事记
	 */
	@RequestMapping(value = "/addGreatEvent", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String addGreatEvent(HttpServletRequest request,@ModelAttribute GreatEventInfo info) {
		String code = "-1";
		String msg = "提交失败！，请重新提交";
		try {
			greatEventService.insertGreatEvent(info);
			code="1";
			msg = "保存成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
	}
	
	
	/**
	 * 站点大事记列表 jsp
	 */
	@RequestMapping(value = "/listToJsp2")
	@ResponseBody
	public ModelAndView listToJsp2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("showData/greatEvent/list2");
		return mav;
	}

	
	/**
	 * 删除站点大事记
	 */
	@RequestMapping(value = "/deleteGreatEvent")
	@ResponseBody
	public ModelAndView deleteGreatEvent(HttpServletRequest request) {
		String id = request.getParameter("id");
		try {
			greatEventService.deleteApplyFile(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", "站点大事记管理");
		mav.setViewName("manager/greatEventList");
		return mav;
	}
	
}