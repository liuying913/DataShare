package com.highfd.controller;

import java.text.DecimalFormat;
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

import com.highfd.bean.ApplyUser;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.AnalysisUserService;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.SiteStationService;

@Component
@Controller
@RequestMapping(value="/analysisUser")
public class AnalysisUserController {

	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	AnalysisUserService analysisUserService;
	
	//30数据   申请人下载界面跳转
	@RequestMapping(value = "userApplyToJsp")
	@ResponseBody
	public ModelAndView userApplyToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String fileType = request.getParameter("fileType");
		mav.addObject("siteType", request.getParameter("siteType"));
		mav.addObject("fileType", fileType);
		mav.addObject("applyTitle", request.getParameter("applyTitle"));
		if("1".equals(fileType)){
			mav.setViewName("analysis/30s/userApplyToJsp");
		}else if("2".equals(fileType)){
			mav.setViewName("analysis/flow/userApplyToJsp");
		}else if("3".equals(fileType)){
			mav.setViewName("analysis/share/userApplyToJsp");
		}else if("4".equals(fileType)){
			mav.setViewName("analysis/earthQuake/userApplyToJsp");
		}
		return mav;
	}
	
	
	//通过用户id 获得该用户的申请列表
	@RequestMapping(value = "userApplyFileList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String userApplyFileList(HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		//String fileType =request.getParameter("fileType");//文件类型
		//String siteType =request.getParameter("siteType");//台站类型
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		List<ApplyUser> applyUserList = analysisUserService.queryApplyUser(userId,startTime, endTime);

		JSONArray json = JSONArray.fromObject(applyUserList);
		return json.toString();
	}
	

	
}
