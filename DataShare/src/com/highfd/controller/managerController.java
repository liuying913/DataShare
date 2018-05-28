package com.highfd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listener.MyFTP;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.NewsInfo;
import com.highfd.bean.UserInfo;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.HighFDService;
import com.highfd.service.ManagerService;
import com.highfd.service.MessageService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

@Component
@Controller
@RequestMapping(value="/manager")
public class managerController {

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
	MessageService messageService;
	
	//综合管理  用户列表
	@RequestMapping(value = "userInfoListToJsp")
	@ResponseBody
	public ModelAndView userInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manager/userInfoList");
		return mav;
	}

	//跳转到 用户详细信息界面
	@RequestMapping(value = "managerUserInfoToJsp")
	@ResponseBody
	public ModelAndView managerUserInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		UserInfo userInfo = userService.queryUserInfo(Integer.valueOf(userId));
		
		if(null!=userInfo && null!=userInfo.getImgPath()){
			mav.addObject("imgPath", userInfo.getImgPath());
		}
		
		mav.addObject("userInfo", userInfo);
		mav.setViewName("manager/managerUserInfo");
		return mav;
	}
	
	//修改用户名密码
	@RequestMapping(value = "/managerUpdateUserPassword", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateUserPassword(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		String message="";
		String code="-1";
		String userId = request.getParameter("userId");
		UserInfo user = userService.queryUserInfo(Integer.valueOf(userId));//根据用户名获得userInfo
		if(null==user){
			message="没有该用户！";
		}else{
			user.setNewUserPwd(userInfo.getNewUserPwd());
			userService.updateUserInfo(user, "pas");
			
			System.out.println(userInfo.getUserId());
			String userName =user.getUserName();
			MyFTP.setPwd(userName, userInfo.getNewUserPwd());
			
			code="1";
			message="修改成功！";
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	
	//跳转到  密码修改成功界面
	@RequestMapping(value = "/successToJsp")
	@ResponseBody
	public ModelAndView successToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		String message = request.getParameter("message");
		String url = request.getParameter("url");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", title);
		mav.addObject("message", message);
		mav.addObject("url", url);
		mav.addObject("userId", userId);
		mav.setViewName("manager/success");
		return mav;
	}
	
	//应急事件列表
	@RequestMapping(value = "apply/earthQuakeListToJsp")
	@ResponseBody
	public ModelAndView ContingencyEventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("apply/earthQuake/list");
		return mav;
	}
	
	
	//应急事件列表(管理模块)
	@RequestMapping(value = "manager/earthQuakeListToJsp")
	@ResponseBody
	public ModelAndView earthQuakeListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/eventList");
		return mav;
	}
	
	
	//添加应急事件
	@RequestMapping(value = "addEarthQuakeToJsp")
	@ResponseBody
	public ModelAndView addEarthQuakeToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manager/addEarthQuake");
		return mav;
	}
	
	
	
	
	
	//添加新闻
	@RequestMapping(value = "/newsEdit", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String newsEdit(HttpServletRequest request) {
		String code = "-1";
		String msg = "提交失败！，请重新提交";
		try {
			String contents = request.getParameter("contents");
			highFDService.insertNews(contents);
			code="1";
			msg = "提交成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}]}";
	}
	
	
	//跳转到新闻列表
	@RequestMapping(value = "newsInfoListToJsp")
	@ResponseBody
	public ModelAndView newsInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("main/newsEdit/newsInfoList");
		return mav;
	}
	
	
	//获得新闻列表json
	@RequestMapping(value = "/getNewsInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewsInfoList(HttpServletRequest request) {
		String type = request.getParameter("type");
		String selectParam = request.getParameter("selectParam");
		List<NewsInfo> siteInfoList = highFDService.getNewsInfoList("",type,selectParam);
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return json.toString();
	}
	
	
	//删除新闻
	@RequestMapping(value = "deleteNewsToJsp")
	@ResponseBody
	public ModelAndView deleteNewsToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		//String applyTitle = request.getParameter("applyTitle");
		highFDService.deleteNewsInfo(Integer.valueOf(id));
		mav.addObject("applyTitle", "新闻管理");
		mav.setViewName("/main/newsEdit/newsInfoList");
		return mav;
	}
	
	
	//站点大事记列表
	@RequestMapping(value = "greatEventListToJsp")
	@ResponseBody
	public ModelAndView greatEventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/greatEventList");
		return mav;
	}
	
	//添加站点大事记
	@RequestMapping(value = "addGreatEventtToJsp")
	@ResponseBody
	public ModelAndView addGreatEventtToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/addGreatEvent");
		return mav;
	}
	
	//应急数据设置
	@RequestMapping(value = "earthquakeConfigEditToJsp")
	@ResponseBody
	public ModelAndView earthquakeConfigEditToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/updateEarthQuakeConfig");
		return mav;
	}
	
	//留言板管理
	@RequestMapping(value = "boardListToJsp")
	@ResponseBody
	public ModelAndView boardListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("/main/message/boardList");
		return mav;
	}
	
	//删除 留言评论 信息
	@RequestMapping(value = "deleteBoard")
	@ResponseBody
	public ModelAndView deleteBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		messageService.deleteBoard(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", "留言管理");
		mav.setViewName("/main/message/boardList");
		return mav;
	}
	
	//常规数据  在线整理
	@RequestMapping(value = "headAnaplerosisToJsp")
	@ResponseBody
	public ModelAndView headAnaplerosisToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 1);
		mav.setViewName("manager/headAnaplerosis");
		return mav;
	}
	
	//共享数据  在线整理
	@RequestMapping(value = "supplyShareDataToJsp")
	@ResponseBody
	public ModelAndView supplyShareDataToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("siteType", 3);
		mav.setViewName("manager/headShareAnaplerosis");
		return mav;
	}
	
	//地震应急事件列表（在线整理用的）
	@RequestMapping(value = "earthQuakeSupplyEventListToJsp")
	@ResponseBody
	public ModelAndView earthQuakeSupplyEventListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/earthQuakeSupplyEventList");
		return mav;
	}
	
	//地震应急事件  在线整理
	@RequestMapping(value = "earthQuakeSupplyDataToJsp")
	@ResponseBody
	public ModelAndView earthQuakeSupplyDataToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String earthQuakeId = request.getParameter("earthQuakeId");
		mav.addObject("earthQuakeId", earthQuakeId);
		mav.setViewName("manager/earthQuakeSupplyData");
		return mav;
	}
	
	//数据动态管理
	@RequestMapping(value = "qualityDataToJsp")
	@ResponseBody
	public ModelAndView qualtyDataToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("manager/qualityList");
		return mav;
	}
}
