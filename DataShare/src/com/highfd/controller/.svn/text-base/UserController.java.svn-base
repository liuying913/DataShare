package com.highfd.controller;

import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import listener.MyFTP;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.UploadResult;
import com.highfd.bean.UserInfo;
import com.highfd.beanFile.UserInfoPage;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.common.PageInfo;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.map.MapAll;
import com.highfd.common.path.GetXMLPath;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.UserService;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

@Component
@Controller
public class UserController {

	
	@Autowired
	protected MessageSource messageSource;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	ApplyFileService applyFileService;
	/**
	 * 判断是否登录系统
	 */
	@RequestMapping(value = "/loginFlag", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String loginFlag(HttpServletRequest request) {
		UserInfoPage up = new UserInfoPage();
		JSONArray json = null;
		HttpSession session = request.getSession(false);
		if(session == null){//没有session
			up.setCode("0");
			up.setMsg("没有登陆系统，请登陆系统！");
			json = JSONArray.fromObject(up);
		}else{
			if(null==session.getAttribute("userInfo")){
				up.setCode("0");
				up.setMsg("没有登陆系统，请登陆系统！");
				json = JSONArray.fromObject(up);
			}else{
				up.setCode("1");
				up.setMsg("登陆成功！");
				json = JSONArray.fromObject(up);
			}
		}
		return json.toString();
	}
	
	/**
	 * 登录系统 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login2", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String login(HttpServletRequest request) throws Exception {
		
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		if(userName==null||"".equals(userName)||userPwd==null||"".equals(userPwd)){
			return "{\"date\":[{\"code\":\""+0+"\",\"msg\":\"请输入用户名密码！\"}]}";
		}
		
		UserInfo userInfo = userService.queryUserInfo(userName, userPwd);
		if(null==userInfo){
			return "{\"date\":[{\"code\":\""+0+"\",\"msg\":\"用户名或密码错误！\"}]}";
		}
		HttpSession session = request.getSession(false);
		if(session != null){session.invalidate();}
		session = request.getSession(true);
		
		session.setAttribute("userInfo", userInfo);
		session.setAttribute("userType", userInfo.getUserType());
		session.setAttribute("imagePath", userInfo.getImgPath());
		session.setMaxInactiveInterval(24*3600);
		
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"登录成功！\"}]}";
	}
	
	/**
	 * 注销用户
	 * 
	 */
	@RequestMapping(value = "/logout", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String logout(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session == null){//没有session
			return "注销成功！";
		}else{
			session.invalidate();
			return "注销成功！";
		}
	}
	
	//跳转到 用户详细信息界面
	@RequestMapping(value = "logoutToJsp")
	@ResponseBody
	public ModelAndView logoutToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null){//没有session
			//return "注销成功！";
		}else{
			session.invalidate();
			//return "注销成功！";
		}
		//ModelAndView mav = new ModelAndView();
		//mav.setViewName("index");
		return new ModelAndView("redirect:index.action");
	}
	
	//跳转到 用户添加界面
	@RequestMapping(value = "addUserToJsp")
	@ResponseBody
	public ModelAndView addUserToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/addUserInfo");
		return mav;
	}
	
	/**
	 * session是否还在
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sessionFlag", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String sessionFlag(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession(false);
		if(session == null){//没有session
			return "登录窗口";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
			if (userInfo == null) {
				return "登录窗口";
			} else {
				return "已经登录过了";
			}
		}
	}
	
	
	
	
	/**
	 * 当获得FTP密码的时候，校验用户登录密码是否正确
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPwd", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String checkPwd(HttpServletRequest request) throws Exception {
		String code = "-1";
		String msg = "";
		String userPwd = request.getParameter("userPwd");
		HttpSession session = request.getSession(false);
		if(session == null){//没有session
			msg = "会话过期，请重新登录！";
		}else{
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
			if (userInfo == null) {
				msg = "会话过期，请重新登录！";
			} else {
				if(userInfo.getUserPwd().equals(userPwd)){
					code="1";
					msg = "密码正确！";
				}else{
					msg = "密码错误！";
				}
			}
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}]}";
	}
	
	
	//跳转到 用户个人中心的   编辑界面
	@RequestMapping(value = "querUserSelfToJsp")
	@ResponseBody
	public ModelAndView querUserSelfToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		
		if(null!=userInfo && null!=userInfo.getImgPath()){
			mav.addObject("imgPath", userInfo.getImgPath());
		}
		
		mav.addObject("userInfo", userInfo);
		mav.setViewName("user/showUserInfo");
		return mav;
	}
	
	//通过id获得用户信息
	@RequestMapping(value = "/querUserInfoById", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String querUserInfoById(HttpServletRequest request) throws Exception {
		
		String userId = request.getParameter("userId");
		MapAll.logger.error("用户id："+userId);
		UserInfoPage aup = new UserInfoPage();
		if(null!=userId && !"".equals(userId)){
			UserInfo userInfo = userService.queryUserInfo(Integer.valueOf(userId));
			MapAll.logger.error("用户OK："+userInfo.getUserName()+" "+userInfo.getUserGender());
			aup.setUserInfo(userInfo);
			aup.setCode("1");
			aup.setMsg("OK");
		}else{
			MapAll.logger.error("用户ERROE：");
			aup.setCode("0");
			aup.setMsg("ERROR");
		}
		JSONArray json = JSONArray.fromObject(aup);
		return json.toString();
	}
	
	
	//跳转到用户列表界面
	@RequestMapping(value = "userInfoListToJsp")
	@ResponseBody
	public ModelAndView userInfoListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		mav.setViewName("user/userInfoList");
		return mav;
	}
	//通过所有的用户
	@RequestMapping(value = "/queryAllUserInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryAllUserInfoList(HttpServletRequest request) throws Exception {
		String userParam = request.getParameter("userParam");
		
		int userId=-1;
		HttpSession session = request.getSession(false);
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			userId = userInfo.getUserId();
		}
		
		List<UserInfo> queryAllUserInfoList = userService.queryAllUserInfoList(userId,userParam);
		JSONArray json = JSONArray.fromObject(queryAllUserInfoList);
		return json.toString();
	}
	
	
	//跳转到用户编辑界面
	@RequestMapping(value = "updateUserInfoToJsp")
	@ResponseBody
	public ModelAndView updateUserInfoToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		mav.setViewName("user/updateUserInfo");
		return mav;
	}
	
	//跳转到用户编辑界面
	@RequestMapping(value = "updateUserHeadToJsp")
	@ResponseBody
	public ModelAndView updateUserHeadToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		
		UserInfo queryUserInfo = userService.queryUserInfo(Integer.valueOf(userId));
		if(null!=queryUserInfo && null!=queryUserInfo.getImgPath()){
			mav.addObject("imgPath", queryUserInfo.getImgPath());
		}
		mav.setViewName("user/updateUserHead");
		return mav;
	}
	
	//修改用户信息/用户名密码
	@RequestMapping(value = "/updateUserInfo2", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateUserInfo2(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		userService.updateUserInfo2(userInfo, "");
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功！\"}]}";
	}
	
	//修改用户信息/用户名密码
	@RequestMapping(value = "/updateUserInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		String str=request.getParameter("uOp");//upd pas
		userService.updateUserInfo(userInfo, str);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功！\"}]}";
	}
	
	//删除用户
	@RequestMapping(value = "/deleteUserInfo")
	@ResponseBody
	public ModelAndView deleteUserInfo(HttpServletRequest request) throws Exception {
		
		String userId = request.getParameter("userId");
		UserInfo userInfo = userService.queryUserInfo(Integer.valueOf(userId));
		
		//删除ftp账号密码
		MyFTP.deleteUser(userInfo.getUserName());
		
		//删除NAS盘上的ftp文件夹
		String pathType="/";
		if("win".equals(MapAll.winOrLinux)){pathType="\\";}
		String ftpRelatviePath = MapAll.FTPLocalPath+pathType+MapAll.userFtpSpaces+pathType+userInfo.getUserName();
		CopyFileUtil.deleteDirList(ftpRelatviePath);
		
		//删除oracle 中的用户密码
		userService.deleteUserInfo(Integer.valueOf(userId));
		
		String manager = request.getParameter("manager");
		if(null!=manager && !"".equals(manager)){
			ModelAndView mav = new ModelAndView();
			mav.addObject("applyTitle", "用户管理");
			mav.setViewName("manager/userInfoList");//管理模块
			return mav;
		}else{
			return new ModelAndView("manager/userInfoList");//用户个人中心模块
		}
		//userService.updateUserInfo(userInfo, "enable");
		
	}
	
	//添加用户
	@RequestMapping(value = "/insertUserInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertUserInfo(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		int userNextId = userService.insertUserInfo(userInfo);
		
		//*****ftp 添加用户*******
		String ftpUserName = userInfo.getUserName();
		String ftpPwd = "123456";//设置FTP六位数随机密码
		MyFTP.addUser(ftpUserName, ftpPwd, MapAll.FTPLocalPath);
		//****添加结束*********
		
		//******设置默认头像*******
		userService.setUserIniImg(request, userNextId+"");

		
		//创建 ftp文件夹（用户自己的空间）
		String pathType="/";
		String winOrLinux;
		try {
			winOrLinux = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"winOrLinux");
			if("win".equals(winOrLinux)){
				pathType="\\";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String userSelfPath = MapAll.FTPLocalPath+pathType+MapAll.userFtpSpaces+pathType+ftpUserName;
		CopyFileUtil.createLinuxFileDictory(userSelfPath);
		
		//添加缓存
		userService.userMap();
		
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"添加成功！\"}]}";
	}
	
	
	/*//添加头像
	@RequestMapping(value = "/insertHead", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertHead(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		String imgName = request.getParameter("imgName");
		String userId = request.getParameter("userId");
		userService.insertUserInfo(userInfo);
		return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"添加成功！\"}]}";
	}
	*/
	
	//跳转到  修改用户密码界面
	@RequestMapping(value = "/updatePasswordToJsp")
	@ResponseBody
	public ModelAndView updatePasswordToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		mav.setViewName("user/updatePassword");
		return mav;
	}
	
	//修改用户名密码
	@RequestMapping(value = "/updateUserPassword", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateUserPassword(HttpServletRequest request,@ModelAttribute UserInfo userInfo) throws Exception {
		String message="";
		String code="-1";
		String userId = request.getParameter("userId");
		UserInfo user = userService.queryUserInfo(Integer.valueOf(userId));//根据用户名获得userInfo
		if(null==user){
			message="没有该用户！";
		}else if(!user.getUserPwd().equals(userInfo.getOldUserPwd())){
			message="原密码输入不正确！";
		}else{
			user.setNewUserPwd(userInfo.getNewUserPwd());
			userService.updateUserInfo(user, "pas");
			
			System.out.println(userInfo.getUserId());
			String userName = MapAll.mapUser.get(userInfo.getUserId()).getUserName();
			MyFTP.setPwd(userName, userInfo.getNewUserPwd());
			
			
			code="1";
			message="修改成功！";
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	
	//跳转到  密码修改成功界面
	@RequestMapping(value = "/passwordSuccessToJsp")
	@ResponseBody
	public ModelAndView passwordSuccessToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyTitle = request.getParameter("applyTitle");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("userId", userId);
		mav.setViewName("user/passwordSuccess");
		return mav;
	}
	
	//个人中心  跳转到下载记录界面
	@RequestMapping(value = "userApplyDownListToJsp")
	@ResponseBody
	public ModelAndView userApplyDownList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyState = request.getParameter("applyState");
		String applyTitle = request.getParameter("applyTitle");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyState", applyState);
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("user/userApplyDownList");
		return mav;
	}
	
	
	//用户下载记录列表
	@RequestMapping(value = "userDownHistoryListToJsp")
	@ResponseBody
	public ModelAndView userDownHistoryListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		String applyId = request.getParameter("applyId");
		
		mav.addObject("siteType", 1);
		mav.addObject("fileType", 1);
		mav.addObject("applyId", applyId);
		mav.addObject("applyTitle", applyTitle);
		
		mav.setViewName("user/userDownHistoryList");
		return mav;
	}
	
	//用户下载记录json
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "user/downHistoryList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String downHistoryList(HttpServletRequest request) {
		String applyId = request.getParameter("applyId");
		PageInfo pageinfo = new PageInfo(); 
		pageinfo.setPageSize(8);
		//String siteType = request.getParameter("siteType");
		String currentPage = request.getParameter("currentPage");
		if(!("").equals(currentPage)&&currentPage!=null){
			pageinfo.setCurrentPage(Integer.parseInt(currentPage));
		}
		String year = "";
		List<Apply_User_Req> queryApplyUserReqList = applyFileService.query_Apply_User_ReqList(applyId);
		Apply_User_Req applyUserReq = new Apply_User_Req();
		if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
			applyUserReq = queryApplyUserReqList.get(0);
			String timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getS_StartTime());
			if(null==timestampToString2 || "".equals(timestampToString2)){
				timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getFlow_StartTime());
			}
			if(null==timestampToString2 || "".equals(timestampToString2)){
				timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getS_StartTime());
			}
			year = timestampToString2.substring(0, 4);
		}
		String fileType = request.getParameter("fileType");
		String fileParams = request.getParameter("fileParams");
		List<FileHistroyDown> downHistoryList = downHistoryService.getUserDownHistoryList(year ,applyId,fileType, fileParams,pageinfo);
		pageinfo.setList(downHistoryList);
		JSONArray json = JSONArray.fromObject(pageinfo);
		return json.toString();
	}
	
	
	//用户下载记录json
	@RequestMapping(value = "user/checkUserNameRepeat", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String checkUserNameRepeat(HttpServletRequest request) throws Exception {

		String userName = request.getParameter("userName");
		String userCName = request.getParameter("userCName");
		UserInfo userInfo = new UserInfo();
		if(null!=userName && !"".equals(userName)){
			userInfo = userService.queryUserInfoByParam("user_name", userName);
		}else if(null!=userCName && !"".equals(userCName)){
			userInfo = userService.queryUserInfoByParam("user_cname", userCName);
		}
		
		if(null==userInfo){
			return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"OK！\"}]}";
		}else{
			return "{\"date\":[{\"code\":\""+-1+"\",\"msg\":\"重复！\"}]}";
		}
		
		
	
	}
	
		
	@RequestMapping(value = "/avatarUpload", method = RequestMethod.POST)
	public void avatarUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) throws IOException {
		UploadResult result = new UploadResult();
		Locale locale = RequestContextUtils.getLocale(request);
		result.setMessageSource(messageSource, locale);

		try {
			doAvatarUpload(file, result, request, response);
		} catch (Exception e) {
			result.setError(e.getMessage());
		}
		String json = JSONObject.toJSONString(result, false).toString();
		response.getWriter().write(json);  
        response.getWriter().flush();  
        response.getWriter().close();  
		return;
	}
	
	 private void doAvatarUpload(MultipartFile file, UploadResult result,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			// 文件是否存在
			if (!validateFile(file, result)) {
				return;
			}
			
			String userUpId = request.getParameter("userUpId");
			
			String origFilename = file.getOriginalFilename();
			String ext = FilenameUtils.getExtension(origFilename).toLowerCase();
			// 后缀名是否合法
			if (!validateExt(ext,result)) {
				return;
			}
			
			
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			BufferedImage buffImg = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			buffImg.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
			
			String pathname = "/users/" + userUpId + "/avatar_temp.jpg";
			String urlPrefix = "/DataShare/uploads";
			String fileUrl = urlPrefix + pathname;
			// 一律存储为jpg
			storeImage(request,bufferedImage, "jpg", pathname);
			result.setFileUrl(fileUrl);
		}
	 
	
	 
	 public void storeImage(HttpServletRequest request,BufferedImage image, String formatName,String filename) throws IOException {
		 				
		String currentDirPath = request.getSession().getServletContext().getRealPath("/");
		//File dest = new File(pathResolver.getPath(filename, "/uploads"));
	 	File dest = new File(currentDirPath+"uploads"+filename);
		File parent = dest.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		ImageIO.write(image, formatName, dest);
	}
		
	 

	 
	 private boolean validateFile(MultipartFile partFile, UploadResult result) {
			if (partFile == null || partFile.isEmpty()) {
				result.setError("no file upload!");
				return false;
			}
			return true;
		}
	 
	 private boolean validateExt(String extension,UploadResult result) {
		 String [] extArr = new String[]{"jpg","jpeg","png","bmp"};
		 
		 for(String type:extArr)
		 {
			 if(type.equalsIgnoreCase(extension))
				 return true;
		 }
		 return false;
		}
	   
	 	@RequestMapping(value = "/avatarSave", method = RequestMethod.POST)
		@ResponseBody
		public String avatarSubmit(String userId,Integer top, Integer left, Integer width,Integer height, HttpServletRequest request,HttpServletResponse response, org.springframework.ui.Model modelMap)throws Exception {
			String userID = userId;
	 		// 读取头像临时文件
	 		String currentDirPath = request.getSession().getServletContext().getRealPath("/");
			//File dest = new File(pathResolver.getPath(filename, "/uploads"));
	 		String pathnameTemp =  "/users/" + userID + "/avatar_temp.jpg";
	 		
	 		File dest = new File(currentDirPath+"uploads"+pathnameTemp);
			
			BufferedImage buff = ImageIO.read(dest);
			// 保存头像原图
			String pathnameOrig = "/users/" + userID + "/avatar.jpg";
			storeImage(request,buff, "jpg", pathnameOrig);
			// 裁剪头像
			if (left != null && top != null && width != null && height != null) {
				buff = Scalr.crop(buff, left, top, width, height);
			}
			// 保存大头像
			String pathnameLarge = "/users/" + userID + "/avatar_large.jpg";
			Integer avatarLarge = 120;
			BufferedImage buffLarge = Scalr.resize(buff, Scalr.Method.QUALITY,avatarLarge, avatarLarge);
			storeImage(request,buffLarge, "jpg", pathnameLarge);
			// 保存小头像
			String pathnameSmall = "/users/" + userID + "/avatar_small.jpg";
			Integer avatarSmall =  50;
			BufferedImage buffSmall = Scalr.resize(buff, Scalr.Method.QUALITY,avatarSmall, avatarSmall);
			storeImage(request,buffSmall, "jpg", pathnameSmall);
			
			// 删除临时头像
			UserInfo user = new UserInfo();
			user.setUserId(Integer.parseInt(userId));
			user.setImgPath("avatar_large.jpg");
			
			userService.updateUserInfo(user, "img");
			
			
			
			FileUtils.deleteQuietly(dest);
			return "{\"value\":1,\"messages\":\"修改成功！\"}";
		}

}