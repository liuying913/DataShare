package com.highfd.controller;

import java.util.List;
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

import com.highfd.bean.ApplyUser;
import com.highfd.bean.DocInfo;
import com.highfd.bean.UserInfo;
import com.highfd.common.file.FileOperateUtil;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DocService;
import com.highfd.service.UserService;

@Component
@Controller
public class DocController {

	@Autowired
	DocService docService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	UserService userService;

	
	//跳转到 成果列表
	@RequestMapping(value = "docInfoListToJsp")
	@ResponseBody
	public ModelAndView docInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/docInfoList");
		return mav;
	}

	
	//获得文档列表
	@RequestMapping(value = "/queryDocInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryDocInfoList(HttpServletRequest request) throws Exception {
		
		//userService.userMap();
		String keyWords = request.getParameter("keyWords");
		HttpSession session = request.getSession(false);
		int userId = -1;
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			userId = userInfo.getUserId();
		}else{
			String userIdStr = request.getParameter("userId");
			if(null==userIdStr || "".equals(userIdStr) || "all".equals(userIdStr)){
				
			}else{
				userId = Integer.valueOf(userIdStr);
			}
			
			//针对  用户申请流程时， 查看上传文件列表而用的
			String applyId = request.getParameter("applyId");
			if(null!=applyId && !"".equals(applyId)){
				ApplyUser af = new ApplyUser();
				af.setAllOrNo("no");
				af.setId(Integer.valueOf(applyId));
				List<ApplyUser> queryApplyFile = applyFileService.queryApplyUser(-1, af);
				userId = queryApplyFile.get(0).getUserId();
			}
		}

		List<DocInfo> queryAllDocInfo = docService.queryAllDocInfo(userId, keyWords);
		JSONArray json = JSONArray.fromObject(queryAllDocInfo);
		return json.toString();
	}
	
	//用户申请查看第四步，查看该用户本年度上传的成果
	@RequestMapping(value = "/docInfoListThisYear", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String docInfoListThisYear(HttpServletRequest request) throws Exception {
		
		//userService.userMap();
		String keyWords = request.getParameter("keyWords");
		String applyId = request.getParameter("applyId");
		List<DocInfo> queryAllDocInfo = docService.docInfoListThisYear(Integer.valueOf(applyId), keyWords);
		JSONArray json = JSONArray.fromObject(queryAllDocInfo);
		return json.toString();
	}
	
	
	//文档下载
	@RequestMapping(value = "downDoc")
	public ModelAndView downDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId= request.getParameter("docId");
		DocInfo docInfo = docService.queryDocInfo(Integer.valueOf(docId));
		String contentType = "application/octet-stream";
		String storeName=docInfo.getDocUrl();
		FileOperateUtil.download(request, response, storeName, contentType,"");
		return null;
	}
	
	//删除文档
	@RequestMapping(value = "deleteDoc")
	@ResponseBody
	public ModelAndView deleteDoc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId= request.getParameter("docId");
		docService.deleteDocInfo(Integer.valueOf(docId));
		//userService.userMap();
		//List<DocInfo> queryAllDocInfo = docService.queryAllDocInfo();
		
		String applyTitle = "成果管理";
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/docInfoList");
		return mav;
	}
	
	//上传文件 指定到界面
	@RequestMapping(value = "upDocToJsp")
	@ResponseBody
	public ModelAndView updateDocToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("doc/updateDocInfo");
	}
	
	//查看文档详细信息
	@RequestMapping(value = "/upDocAjax", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String upDocAjax(HttpServletRequest request,@ModelAttribute DocInfo info) throws Exception {
		String uploadPath = FileOperateUtil.upload(request);
		
		if(null==info || null==info.getDocName() || "".equals(info.getDocName()) || "".equals(info)){
			return "{\"date\":[{\"code\":\""+-1+"\",\"msg\":\"请输入填写文件名称！\"}]}";
		}
		if(null==uploadPath || "".equals(uploadPath) ){
			return "{\"date\":[{\"code\":\""+-1+"\",\"msg\":\"请输入选择文件！\"}]}";
		}
		
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		info.setUserId(userInfo.getUserId());
		info.setDocUrl(uploadPath);
		docService.insertDocInfo(info);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"上传成功！\"}]}";
	}
	
	//上传文件 保存（产生提示信息）
	@RequestMapping(value = "upDocToSave2")
	public ModelAndView upDocToSave2(HttpServletRequest request,@ModelAttribute DocInfo info) throws Exception {
		String uploadPath = FileOperateUtil.upload(request);
		ModelAndView mav = new ModelAndView();
		
		if(null==info || null==info.getDocName() || "".equals(info.getDocName()) || "".equals(info) || null==uploadPath || "".equals(uploadPath) ){
			mav.setViewName("doc/updateDocInfo");
			
			if(null==info || null==info.getDocName() || "".equals(info.getDocName()) || "".equals(info) ){
				mav.addObject("tmpDocName", "error");
			}
			if(null==uploadPath || "".equals(uploadPath) ){
				mav.addObject("tmpPath", "error");
			}
			return mav;
		}
		
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		info.setUserId(userInfo.getUserId());
		info.setDocUrl(uploadPath);
		docService.insertDocInfo(info);
		
		String applyTitle = "成果管理";
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/docInfoList");
		return mav;
	}
	
	//上传文件 保存
	@RequestMapping(value = "upDocToSave")
	public ModelAndView upload(HttpServletRequest request,@ModelAttribute DocInfo info) throws Exception {
		String uploadPath = FileOperateUtil.upload(request);
		ModelAndView mav = new ModelAndView();
		if(null==uploadPath || "".equals(uploadPath) || null==info.getDocName() || "".equals(info)){
			mav.addObject("applyTitle", "文件上传");
			mav.setViewName("doc/updateDocInfo");
			return mav;
		}
		
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		info.setUserId(userInfo.getUserId());
		info.setDocUrl(uploadPath);
		docService.insertDocInfo(info);
		
		String applyTitle = "成果管理";
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/docInfoList");
		return mav;
	}

	
	//查看文档详细信息
	@RequestMapping(value = "/queryDocInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryDocInfo(HttpServletRequest request) throws Exception {
		String docId= request.getParameter("docId");
		DocInfo docInfo = docService.queryDocInfo(Integer.valueOf(docId));
		JSONArray json = JSONArray.fromObject(docInfo);
		return json.toString();
	}
	

	//****软件*************************************************************
	//列表(软件)
	@RequestMapping(value = "/doc/softListToJsp")
	@ResponseBody
	public ModelAndView softListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/softList");
		return mav;
	}
	
	//列表json(软件)
	@RequestMapping(value = "/doc/queryDocSoftList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryDocSoftList(HttpServletRequest request) throws Exception {
		String keyWords = request.getParameter("keyWords");
		List<DocInfo> queryAllDocInfo = docService.queryAllDocInfo(keyWords);
		JSONArray json = JSONArray.fromObject(queryAllDocInfo);
		return json.toString();
	}
	
	//上传文件 (软件)
	@RequestMapping(value = "/doc/upSoftToJsp")
	@ResponseBody
	public ModelAndView upSoftToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("doc/updateSoft");
	}
	
	//保存(软件)
	@RequestMapping(value = "/doc/upSoftToSave")
	public ModelAndView upSoftToSave(HttpServletRequest request,@ModelAttribute DocInfo info) throws Exception {
		String uploadPath = FileOperateUtil.upload(request);
		ModelAndView mav = new ModelAndView();
		if(null==uploadPath || "".equals(uploadPath) || null==info.getDocName() || "".equals(info)){
			mav.addObject("applyTitle", "文件上传");
			mav.setViewName("doc/updateSoft");
			return mav;
		}
		info.setUserId(-1);
		info.setDocUrl(uploadPath);
		docService.insertDocInfo(info);
		
		mav.addObject("applyTitle", "软件管理");
		mav.setViewName("doc/softList");
		return mav;
	}
	
	//删除(软件)
	@RequestMapping(value = "/doc/deleteSoft")
	@ResponseBody
	public ModelAndView deleteSoft(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String docId= request.getParameter("docId");
		docService.deleteDocInfo(Integer.valueOf(docId));
		String applyTitle = "软件管理";
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("doc/softList");
		return mav;
	}
	//****软件结束*************************************************************
}
