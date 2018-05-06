package com.highfd.controller;

import java.net.URL;
import java.net.URLConnection;
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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.ApplyUser;
import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UserInfo;
import com.highfd.beanFile.ApplyUserPage;
import com.highfd.beanFile.note.NoteInfo;
import com.highfd.common.file.FileOperateUtil;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.NoteInfoService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

@Component
@Controller
public class ApplyFileController {

	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	NoteInfoService noteInfoService;
	@Autowired
	UserService userService;
	
	//***********跳转界面流程1到5************************************************
	
	@RequestMapping(value = "applyPage0ToJsp")
	@ResponseBody
	public ModelAndView applyPage0ToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		String applyTitle = request.getParameter("applyTitle");
		String isManager = request.getParameter("isManager");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyId", applyId);
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("isManager", isManager);
		mav.setViewName("apply/applyPage0");
		return mav;
	}
	
	//数据申请 流程界面 跳转  总控制
	@RequestMapping(value = "applyOrShowToJsp")
	@ResponseBody
	public ModelAndView applyOrShowToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		String applyTitle = request.getParameter("applyTitle");
		String isManager = request.getParameter("isManager");
		int thisNum = Integer.valueOf(request.getParameter("thisNum"));//目前第几个界面
		String backOrDown = request.getParameter("backOrDown");//上一步 或者下一步
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyId", applyId);
		mav.addObject("applyTitle", applyTitle);
		mav.addObject("isManager", isManager);
		
		String liuCheng = applyFileService.queryApplyLiuChengByApplyId(applyId);
		if(null!=liuCheng){
			if(backOrDown.equals("back")){
				thisNum = thisNum-1;
				mav.setViewName("apply/showApplyPage"+thisNum);
			}else if(backOrDown.equals("down")){
				int IntLC = Integer.valueOf(liuCheng);//数据流程 走到第几步骤了
				if(IntLC==4){IntLC=3;}//如果流程时4，那么变成3
				thisNum = thisNum+1;//最大为4
				
				//申请走完的流程
				if(IntLC>=5){
					if(thisNum<4){
						mav.setViewName("apply/showApplyPage"+thisNum);
					}else{
						ApplyUser af = new ApplyUser();
						af.setAllOrNo("no");
						af.setId(Integer.valueOf(applyId));
						List<ApplyUser> applyUserList = applyFileService.queryApplyUser(-1, af);//管理员身份进行的查看
						ApplyUser applyUser = applyUserList.get(0);
						mav.addObject("applyId", applyId);
						if(applyUser.getLiuCheng().equals("5")){//审核通过
							mav.addObject("applyCheck", "审核通过");
							mav.addObject("applyCheckText", applyUser.getRemark());
							mav.setViewName("apply/showApplyPage5Over");
						}else if( applyUser.getLiuCheng().equals("6") ){//审核未通过
							mav.addObject("applyCheck", "审核未通过");
							mav.addObject("applyCheckText", applyUser.getRemark());
							mav.setViewName("apply/showApplyPage5Over");
						}else if( applyUser.getLiuCheng().equals("7") ){//审核未通过
							mav.addObject("applyCheck", "申请过期");
							mav.addObject("applyCheckText", applyUser.getRemark());
							mav.setViewName("apply/showApplyPage5Over");
						}
					}
				}else{//未完成的申请
					
					if(thisNum>IntLC){
						if(thisNum<4){
							mav.setViewName("apply/applyPage"+thisNum);
						}else{
							//管理员 需要进行审核   非管理员 等待审核
							HttpSession session = request.getSession(false);
							if(session.getAttribute("userType").toString().equals("1")){//管理员  并且是  审核菜单过来的
								if(null!=isManager && "false".equals(isManager)){
									mav.addObject("message", "申请完成，请耐心等待审批结果！");
									mav.setViewName("apply/showApplyPage5NoCheck");
								}else{
									mav.setViewName("apply/showApplyPage5Check");
								}
							}else{
								mav.addObject("message", "申请完成，请耐心等待审批结果！");
								mav.setViewName("apply/showApplyPage5NoCheck");
							}
						}
					}else{
						mav.setViewName("apply/showApplyPage"+thisNum);
					}
				}
				
				
			}
			
			
			
			
			
			
			
		}else{
			if(backOrDown.equals("back")){
				thisNum = thisNum-1;
				mav.setViewName("apply/applyPage"+thisNum);
			}else if(backOrDown.equals("down")){
				thisNum = thisNum+1;
				mav.setViewName("apply/applyPage"+thisNum);
			}
		}
		return mav;
	}
	
	
	
	//跳转界面流程5_  审核or审核完成的
	@RequestMapping(value = "showPage5ToJsp")
	@ResponseBody
	public ModelAndView showPage5ToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		ApplyUser af = new ApplyUser();
		af.setAllOrNo("no");
		af.setId(Integer.valueOf(applyId));
		List<ApplyUser> applyUserList = applyFileService.queryApplyUser(-1, af);//管理员身份进行的查看
		ApplyUser applyUser = applyUserList.get(0);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyId", applyId);
		if(applyUser.getLiuCheng().equals("5")){//审核通过
			mav.addObject("applyCheck", "审核通过");
			mav.addObject("applyCheckText", applyUser.getRemark());
			mav.setViewName("apply/showApplyPage5Over");
		}else if( applyUser.getLiuCheng().equals("6") ){//审核未通过
			mav.addObject("applyCheck", "审核未通过");
			mav.addObject("applyCheckText", applyUser.getRemark());
			mav.setViewName("apply/showApplyPage5Over");
		}else{
			
			//管理员 需要进行审核   非管理员 等待审核
			String isManager = request.getParameter("isManager");
			mav.addObject("isManager", isManager);
			HttpSession session = request.getSession(false);
			if(session.getAttribute("userType").toString().equals("1")){//管理员  并且是  审核菜单过来的
				if(null!=isManager && "false".equals(isManager)){
					mav.addObject("message", "申请完成，请耐心等待审批结果！");
					mav.setViewName("apply/showApplyPage5NoCheck");
				}else{
					mav.setViewName("apply/showApplyPage5Check");
				}
			}else{
				mav.addObject("message", "申请完成，请耐心等待审批结果！");
				mav.setViewName("apply/showApplyPage5NoCheck");
			}
		}
		
		return mav;
	}
	
	//跳转到  申请列表页面   1:未完成 2:未处理 3:已完成
	@RequestMapping(value = "applyUserListToJsp")
	@ResponseBody
	public ModelAndView applyUserListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyState = request.getParameter("applyState");
		String applyTitle = request.getParameter("applyTitle");
		String isManager = request.getParameter("isManager");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyState", applyState);
		mav.addObject("applyTitle", applyTitle);
		if("1".equals(applyState)){//未完成
			mav.setViewName("apply/applyUserListNoOver");
		}else if("3".equals(applyState)){
			mav.setViewName("apply/applyUserListOver");
		}else if("all".equals(applyState)){
			mav.addObject("isManager", isManager);
			mav.setViewName("apply/applyUserListOver");//审核通过、审核未通过、过期的
			HttpSession session = request.getSession(false);
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			int userId = userInfo.getUserId();
			noteInfoService.updateNoteReadFlag(userId+"", "1");
		}else{//待审核
			mav.addObject("isManager", isManager);
			mav.setViewName("apply/waitCheckList");
		}
		return mav;
	}
	
	//从申请界面列表  跳转到  流程1  （包括进入未审核 对应的流程界面）
	@RequestMapping(value = "applyListToPage1ToJsp")
	@ResponseBody
	public ModelAndView applyListToPage1ToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		String liuCheng = request.getParameter("liuCheng");
		String isManager = request.getParameter("isManager");
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyId", applyId);
		mav.addObject("isManager", isManager);
		if      ("1".equals(liuCheng)){
			mav.setViewName("apply/applyPage2");
		}else if("2".equals(liuCheng)){
			mav.setViewName("apply/applyPage3");
		}else if("3".equals(liuCheng)){
			mav.setViewName("apply/applyPage4");
		}else if("4".equals(liuCheng)){
			mav.setViewName("apply/showApplyPage1");
		}else if("5".equals(liuCheng)){
			mav.setViewName("apply/showApplyPage1");
		}else if("6".equals(liuCheng)){
			mav.setViewName("apply/showApplyPage1");
		}
		return mav;
	}
	
	
	
	
	//*******************************************************************************************
	//保存申请信息  流程一
	@RequestMapping(value = "/saveApplyUser", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String saveApplyUser(HttpServletRequest request,@ModelAttribute ApplyUser applyUser) throws Exception {
		//String userId2 = request.getParameter("userId");
		
		//通过session获得 userId
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();
		applyUser.setUserId(userId);
		int saveApplyUser = applyFileService.saveApplyUser(applyUser);
		
		//消息处理
		NoteInfo noteInfo = new NoteInfo();
		noteInfo.setApplyId(saveApplyUser);
		noteInfo.setContent("您有未完成的陆态30秒数据申请！");
		noteInfo.setApplyFileType(1);//30数据
		noteInfo.setIsManager(2);//通知 用户 有未完成的消息
		noteInfo.setProcess(1);//未完成申请
		
		noteInfoService.insertNoteInfo(noteInfo);
		
		return "{\"date\":[{\"code\":\""+saveApplyUser+"\",\"msg\":\"网络超时\"}]}";
	}
	
	//获得申请列表
	@RequestMapping(value = "/queryApplyUserList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryApplyUserList(HttpServletRequest request) {
		String applyState = request.getParameter("applyState");
		String isManager = request.getParameter("isManager");
		ApplyUser af = new ApplyUser();
		af.setAllOrNo("yes");
		
		String selectParams = request.getParameter("selectParams");
		af.setSelectParams(selectParams);
		HttpSession session = request.getSession(false);
		int userId=-1;
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		
		
		if("1".equals(applyState)){//未完成
			af.setLiuCheng("('1','2','3')");
		}else if("2".equals(applyState)){//未审核
			af.setLiuCheng("('4')");
		}else if("3".equals(applyState)){//已完成  并且通过审核的
			af.setLiuCheng("('5','6')");
		}else if("4".equals(applyState)){//已完成  并且  没有  通过审核的
			af.setLiuCheng("('6')");
		}else if("ftp".equals(applyState)){//ftp
			af.setLiuCheng("('5')");
		}else if("all".equals(applyState)){//all
			af.setLiuCheng("('5','6','7')");
		}else if("7".equals(applyState)){//7
			af.setLiuCheng("('7')");
		}
		
		
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			userId = userInfo.getUserId();
		}else{
			String userIdStr = request.getParameter("userId");
			if(null!=userIdStr && !"".equals(userIdStr) && !"all".equals(userIdStr)){
				userId = Integer.valueOf(userIdStr);
			}
		}
		if("1".equals(applyState)){//未完成
			userId = userInfo.getUserId();
		}else if("2".equals(applyState) || "all".equals(applyState)){//待审核
			if(session.getAttribute("userType").toString().equals("1") && null!=isManager && "true".equals(isManager)){
				userId=-1;
			}else{
				userId = userInfo.getUserId();
			}
		}
		List<ApplyUser> queryApplyUserList = applyFileService.queryApplyUser(userId,af);
		JSONArray json = JSONArray.fromObject(queryApplyUserList);
		return json.toString();
	}
	
	//获得 单个 applyUser的申请信息
	@RequestMapping(value = "/queryApplyFile", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryApplyFile(HttpServletRequest request) {
		
		String applyId = request.getParameter("applyId");
		ApplyUser af = new ApplyUser();
		af.setAllOrNo("no");
		af.setId(Integer.valueOf(applyId));
		
		/*HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		int userId = userInfo.getUserId();*/
		
		List<ApplyUser> queryApplyFile = applyFileService.queryApplyUser(-1, af);
		ApplyUserPage aup = new ApplyUserPage();
		aup.setApplyUser(queryApplyFile.get(0));
		aup.setCode("1");
		aup.setMsg("OK");
		JSONArray json = JSONArray.fromObject(aup);
		return json.toString();
	}
	
	
	//删除未完成申请
	@RequestMapping(value = "/deleteUserApply", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteUserApply(HttpServletRequest request,@ModelAttribute ApplyUser applyUser) throws Exception {
		//String userId2 = request.getParameter("userId");
		String applyId = request.getParameter("applyId");
	    applyFileService.deleteUserApply(applyId);
		
		// 删除消息
	    NoteInfo noteInfo = new NoteInfo();
		noteInfo.setApplyId(Integer.valueOf(applyId));
		noteInfo.setApplyFileType(1);//30数据
		noteInfo.setIsManager(2);//用户
		noteInfoService.deleteUserInfo(noteInfo);
		
		
		String saveApplyUser = "1";
		return "{\"date\":[{\"code\":\""+saveApplyUser+"\",\"msg\":\"网络超时\"}]}";
	}
	
	//未完成申请 跳转到jsp界面
	@RequestMapping(value = "noOverApplyToJsp")
	@ResponseBody
	public ModelAndView noOverApplyToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String applyId = request.getParameter("applyId");
	    applyFileService.deleteUserApply(applyId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("applyState", 1);
		mav.addObject("applyTitle", "未审核申请");
		
		mav.setViewName("apply/applyUserListNoOver");//未完成
		return mav;
	}

	
	//下载审批表
	@RequestMapping(value = "/ApplyTableDown", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String ApplyTableDown(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String storeName = ServletRequestUtils.getStringParameter(request,"storeName");
		String realName = ServletRequestUtils.getStringParameter(request,"realName");
		String contentType = "application/octet-stream";
		int download = FileOperateUtil.download(request, response, storeName, contentType,realName);
		return download+"";
	}
	

	//数据申请界面  主要查询
	//通过查询获得的  文件列表（开始时间、结束时间、经纬度、台站）
	@RequestMapping(value = "/getBigFileInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getBigFileInfoList(HttpServletRequest request) {
		
		String applyOrShow = request.getParameter("applyOrShow");
		
		String startTime = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		
		String fileYear = request.getParameter("fileYear");
		String fileDayYear = request.getParameter("fileDayYear");
		String siteType = request.getParameter("Datetype");
		String fileFlag = request.getParameter("fileFlag");
		
		String siteNumberArray = request.getParameter("taizhang");
		String provinceArray = request.getParameter("shengfen");
		
		String smallLat = request.getParameter("dongwei");
		String bigLat = request.getParameter("xiwei");
		String smallLon = request.getParameter("dongjin");
		String bigLon = request.getParameter("xijin");
		
		siteService.mapSite();
		List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray,"",provinceArray,smallLat, bigLat, smallLon, bigLon,siteType);
		
		List<FileInfo> selectFileList = new ArrayList<FileInfo>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		if("apply".equals(applyOrShow)){
			//List<FileInfo> fileInfoList = applyFileService.getFileInfoList(fileYear,fileDayYear,site_number,type,fileFlag);
			fileInfoList = applyFileService.getApplyFileInfoList(startTime, endTime, fileYear, fileDayYear, "",  "", "", "", "",siteType, fileFlag,false,TimeUtils.getYearByTime(startTime));
		}else{
			String applyId = request.getParameter("applyId");
			fileInfoList = applyFileService.getApplyFileByApplyId(applyId, "",fileYear);
			//List<Apply_User_Files> queryApplyUserFilesList = applyFileService.query_Apply_User_FilesList(applyId);
		}
		
		for(int i=0;i<fileInfoList.size();i++){
			FileInfo fileInfo = fileInfoList.get(i);
			for(int j=0;j<siteInfoList.size();j++){
				SiteInfo siteInfo = siteInfoList.get(j);
				if(fileInfo.getSiteNumber().equals(siteInfo.getSiteNumber())){
					selectFileList.add(fileInfo);
					continue;
				}
			}
		}
		
		Map<String, Map<String,List<FileInfo>>> map = new HashMap<String, Map<String,List<FileInfo>>>();
		for(int i=0;i<selectFileList.size();i++){
			FileInfo fileInfo = selectFileList.get(i);
			Map<String, List<FileInfo>> yearMap = map.get(fileInfo.getFileYear());
			if(null==yearMap){
				List<FileInfo> dayFileList = new ArrayList<FileInfo>();  dayFileList.add(fileInfo);
				yearMap = new HashMap<String, List<FileInfo>>();         yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}else{
				List<FileInfo> dayFileList = yearMap.get(fileInfo.getFileDayYear());
				if(null==dayFileList){
					dayFileList = new ArrayList<FileInfo>();dayFileList.add(fileInfo);
				}else{
					dayFileList.add(fileInfo);
				}
				yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}
		}
		
		JSONArray json = JSONArray.fromObject(map);
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}
	
	
	//保存查询条件、查询结果集   流程2
	@RequestMapping(value = "/saveBigFileInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String saveBigFileInfoList(HttpServletRequest request) {
		try{
			
			String applyId = request.getParameter("applyId");
			
			String startTime = request.getParameter("kaishi");
			String endTime = request.getParameter("jieshu");
			
			String fileYear = request.getParameter("fileYear");
			String fileDayYear = request.getParameter("fileDayYear");
			String siteType = request.getParameter("Datetype");
			String fileFlag = request.getParameter("fileFlag");
			
			String siteNumberArray = request.getParameter("taizhang");
			String zoneArray = request.getParameter("shengfen");
			
			String smallLat = request.getParameter("dongwei");
			String bigLat = request.getParameter("xiwei");
			String smallLon = request.getParameter("dongjin");
			String bigLon = request.getParameter("xijin");
			
			//siteService.mapSite();
			List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray,"",zoneArray,smallLat, bigLat, smallLon, bigLon,siteType);
			
			//List<FileInfo> fileInfoList = applyFileService.getApplyFileInfoList(startTime, endTime, fileYear, fileDayYear, siteNumberArray,  smallLat, bigLat, smallLon, bigLon,siteType, fileFlag,false);
			List<FileInfo> fileInfoList = applyFileService.getApplyFileInfoList(startTime, endTime, fileYear, fileDayYear, "",  "", "", "", "",siteType, fileFlag,false,TimeUtils.getYearByTime(startTime));
			
			List<FileInfo> selectFileList = new ArrayList<FileInfo>();
			for(int i=0;i<fileInfoList.size();i++){
				FileInfo fileInfo = fileInfoList.get(i);
				for(int j=0;j<siteInfoList.size();j++){
					SiteInfo siteInfo = siteInfoList.get(j);
					if(fileInfo.getSiteNumber().equals(siteInfo.getSiteNumber())){
						selectFileList.add(fileInfo);
						continue;
					}
				}
			}
			
			if(null!=selectFileList && selectFileList.size()>=0){
				
				String datetype = request.getParameter("Datetype");
				//(1)保存申请的文件信息
				int countApplyFile = applyFileService.queryCount_ApplyFile(applyId,datetype);
				if(countApplyFile>0){
					applyFileService.deleteApplyFile(applyId,datetype);
				}
				applyFileService.insert_Apply_User_Files(selectFileList, Integer.valueOf(applyId), datetype);
				
				//(2)保存申请的台站列表
				int applyUserSiteInfoList = applyFileService.queryCount_Apply_User_SiteInfoList(applyId,datetype);
				if(applyUserSiteInfoList>0){//数据库里面有，就删除
					applyFileService.deleteUserInfo(applyId,datetype);//删除
				}
				applyFileService.insert_Apply_User_SiteInfo(siteInfoList, Integer.valueOf(applyId), datetype);
				
				//(3)保存查询条件
				List<Apply_User_Req> queryApplyUserReqList = applyFileService.query_Apply_User_ReqList(applyId);
				Apply_User_Req applyUserReq = new Apply_User_Req();
				if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
					applyUserReq = queryApplyUserReqList.get(0);
				}
				
				if(null==smallLat || "".equals(smallLat)){smallLat="0";}
				if(null==bigLat || "".equals(bigLat)){bigLat="0";}
				if(null==smallLon || "".equals(smallLon)){smallLon="0";}
				if(null==bigLon || "".equals(bigLon)){bigLon="0";}
				if(datetype.indexOf("1")>-1){
					applyUserReq.setS_StartTime(TimeUtils.getTempTime(startTime));
					applyUserReq.setS_EndTime(TimeUtils.getTempTime(endTime));
					applyUserReq.setS_Lat_small(Double.valueOf(smallLat));
					applyUserReq.setS_Lat_big(Double.valueOf(bigLat));
					applyUserReq.setS_Lon_small(Double.valueOf(smallLon));
					applyUserReq.setS_Lon_big(Double.valueOf(bigLon));
				}else if(datetype.indexOf("2")>-1){
					applyUserReq.setFlow_StartTime(TimeUtils.getTempTime(startTime));
					applyUserReq.setFlow_EndTime(TimeUtils.getTempTime(endTime));
					applyUserReq.setFlow_Lat_small(Double.valueOf(smallLat));
					applyUserReq.setFlow_Lat_big(Double.valueOf(bigLat));
					applyUserReq.setFlow_Lon_small(Double.valueOf(smallLon));
					applyUserReq.setFlow_Lon_big(Double.valueOf(bigLon));
				}else if(datetype.indexOf("3")>-1){
					applyUserReq.setShare_StartTime(TimeUtils.getTempTime(startTime));
					applyUserReq.setShare_EndTime(TimeUtils.getTempTime(endTime));
					applyUserReq.setShare_Lat_small(Double.valueOf(smallLat));
					applyUserReq.setShare_Lat_big(Double.valueOf(bigLat));
					applyUserReq.setShare_Lon_small(Double.valueOf(smallLon));
					applyUserReq.setShare_Lon_big(Double.valueOf(bigLon));
				}
				if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
					applyFileService.update_Apply_User_Req(applyUserReq);
				}else{
					applyUserReq.setApplyId(Integer.valueOf(applyId));
					applyFileService.insert_Apply_User_Req(applyUserReq);
				}
				//(4) 修改申请表
				ApplyUser au = new ApplyUser();
				au.setId(Integer.valueOf(applyId));
				au.setYear(TimeUtils.getYearByTime(startTime));
				applyFileService.updateApplyUserLiuCheng2(2, au);
			}
			
			JSONArray json = JSONArray.fromObject("[1]");
			return json.toString();
		}catch(Exception e){
			JSONArray json = JSONArray.fromObject("[0]");
			e.printStackTrace();
			System.err.println("申请条件保存失败！！！");
			return json.toString();
		}
		
	}
	
	
	//仅仅获得 树桩结构 右侧的部分数据
	//获得文件列表——数据申请页面，树状结构  与   table
	@RequestMapping(value = "/getFileInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getFileInfoList(HttpServletRequest request) {
		
		String applyOrShow = request.getParameter("applyOrShow");
		String fileYear = request.getParameter("fileYear");
		String fileDayYear = request.getParameter("fileDayYear");
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			if(fileDayYear.length()==1){
				fileDayYear="00"+fileDayYear;
			}else if(fileDayYear.length()==2){
				fileDayYear="0"+fileDayYear;
			}
		}
		String siteNumberArray = request.getParameter("taizhang");
		String siteType = request.getParameter("Datetype");
		String fileType = request.getParameter("filetype");
		fileType=siteType;//跟台站类型保持一致
		String fileFlag = request.getParameter("fileFlag");
		
		//时间
		String startTime = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		
		//经纬度坐标
		String smallLat = request.getParameter("dongwei");
		String bigLat = request.getParameter("xiwei");
		String smallLon = request.getParameter("dongjin");
		String bigLon = request.getParameter("xijin");
		if(smallLat!=null && smallLat.indexOf("最")>-1){smallLat=null;}
		if(bigLat!=null && bigLat.indexOf("最")>-1){bigLat=null;}
		if(smallLon!=null && smallLon.indexOf("最")>-1){smallLon=null;}
		if(bigLon!=null && bigLon.indexOf("最")>-1){bigLon=null;}
		
		String zoneArray = request.getParameter("shengfen");

		
		List<FileInfo> selectFileList = new ArrayList<FileInfo>();
		if("apply".equals(applyOrShow)){
			selectFileList = applyFileService.getApplyFileByDay(startTime,endTime,fileDayYear, siteNumberArray, zoneArray, smallLat, bigLat, smallLon, bigLon, siteType, fileType,fileYear);
		}else{
			String applyId = request.getParameter("applyId");
			selectFileList = applyFileService.getShowFileByDay(applyId, siteNumberArray, zoneArray, siteType, fileYear);
		}
		
		//SiteInfo siteInfo = MapAll.mapSite.get(siteNumber);
		Map<String, Map<String,List<FileInfo>>> map = new HashMap<String, Map<String,List<FileInfo>>>();
		for(int i=0;i<selectFileList.size();i++){
			FileInfo fileInfo = selectFileList.get(i);
			Map<String, List<FileInfo>> yearMap = map.get(fileInfo.getFileYear());
			if(null==yearMap){
				List<FileInfo> dayFileList = new ArrayList<FileInfo>();  dayFileList.add(fileInfo);
				yearMap = new HashMap<String, List<FileInfo>>();         yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}else{
				List<FileInfo> dayFileList = yearMap.get(fileInfo.getFileDayYear());
				if(null==dayFileList){
					dayFileList = new ArrayList<FileInfo>();dayFileList.add(fileInfo);
				}else{
					dayFileList.add(fileInfo);
				}
				yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}
		}
		
		//当数据未null时
		if(map.size()==0){
			Map<String,List<FileInfo>> minMap = new HashMap<String,List<FileInfo>>();
			FileInfo f = new FileInfo();
			f.setFileDayYear(fileDayYear);
			List<FileInfo> minList = new ArrayList<FileInfo>();
			minList.add(f);
			minMap.put(fileDayYear, minList);
			map.put(fileYear, minMap);
		}
		
		JSONArray json = JSONArray.fromObject(map);
		String p = json.toString();
		p=p.substring(1, p.length());
		p=p.substring(0, p.length()-1);
		return p;
	}
	
	
	
	//申请查看界面， 开始事件、结束事件、经纬度坐标
	@RequestMapping(value = "/getTimeAndJW", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getTimeAndJW(HttpServletRequest request){
		
		String applyId = request.getParameter("applyId");
		
		List<Apply_User_Req> queryApplyUserReqList = applyFileService.query_Apply_User_ReqList(applyId);
		Apply_User_Req applyUserReq = new Apply_User_Req();
		if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
			applyUserReq = queryApplyUserReqList.get(0);
		}
		JSONArray json = JSONArray.fromObject(applyUserReq);
		return json.toString();
	}
	
	
	
	//申请查看申请的开始时间 和结束时间
	@RequestMapping(value = "/getApplyStartAndEnd", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getApplyStartAndEnd(HttpServletRequest request){
	    String applyId = request.getParameter("applyId");
	    ApplyUser applyStartAndEnd = applyFileService.getApplyStartAndEnd(Integer.valueOf(applyId));
		if(null!=applyStartAndEnd){
			JSONArray json = JSONArray.fromObject(applyStartAndEnd);
			return json.toString();
		}
		return null;
	}
	
	//插入申请的文件数据
	@RequestMapping(value = "/insertApplyUserFiles", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertApplyUserFiles(HttpServletRequest request) throws Exception {
		applyFileService.insert_Apply_User_Files(1, 1,"1");
		return "";
	}
	
	
	
	//到上传文件的位置
	@RequestMapping(value = "ApplyToUpload")
	public ModelAndView toUpload() {
		return new ModelAndView("upload");
	}

	//上传文件（用户申请最后一个流程）
	@RequestMapping(value = "/ApplyUpload", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ModelAndView upload(HttpServletRequest request){
		try {
			String docName = FileOperateUtil.upload(request);
			//map.put("result", result);
			//return new ModelAndView("list", map);
			//return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"网络超时\"}]}";
			String applyId = request.getParameter("applyId");
			ApplyUser au = new ApplyUser();
			au.setId(Integer.valueOf(applyId));
			au.setApplyWorldPath(docName);
			applyFileService.updateApplyUser(4, au);//保存上传的文件
			
			
			
			//*******消息(管通知理员)*********
			NoteInfo noteInfo = new NoteInfo();//通知管理员 有未审核的申请
			noteInfo.setApplyId(Integer.valueOf(applyId));
			noteInfo.setContent("您有未审核的陆态30秒数据申请！");
			noteInfo.setApplyFileType(1);//30数据
			noteInfo.setIsManager(1);//通知 管理员
			noteInfo.setProcess(4);//有未审核的数据
			noteInfoService.insertNoteInfo(noteInfo);
			
			
			//*******消息通知(普通用户)*********
			NoteInfo noteInfo2 = new NoteInfo();
			noteInfo2.setApplyId(Integer.valueOf(applyId));
			noteInfo2.setContent("您有等待审核的陆态30秒数据申请！");
			noteInfo2.setApplyFileType(1);//30数据
			noteInfo2.setIsManager(2);//通知 用户
			noteInfo2.setProcess(4);//有等待审核的申请
			noteInfo2.setReadFlag(1);
			noteInfoService.updateNoteInfo(noteInfo2);
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("applyId", applyId);
			mav.addObject("message", "申请完成，请耐心等待审批结果！");
			mav.setViewName("apply/applyPage4");
			
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			//return "{\"date\":[{\"code\":\""+0+"\",\"msg\":\"网络超时\"}]}";
			return new ModelAndView("apply/applyPage4");
		}
	}
	
	//审核结果
	@RequestMapping(value = "/ApplyCheck", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String ApplyCheck(HttpServletRequest request){
		try {
			String applyId = request.getParameter("applyId");
			String applyCheck = request.getParameter("applyCheck");
			String applyCheckText = request.getParameter("applyCheckText");
			
			ApplyUser au = new ApplyUser();
			au.setId(Integer.valueOf(applyId));
			au.setLiuCheng(applyCheck);
			au.setRemark(applyCheckText);
			
			if(applyCheck.equals("5")){//审核通过的话 设置申请期限
				//获得配置参数（30秒期限）
				List<EarthQuakeConfig> earthQuakeConfigQuery = siteService.earthQuakeConfigQuery();
				String days = earthQuakeConfigQuery.get(0).getApplyperiod();
				au.setApplyperiod(-Integer.valueOf(days));
			}
			
			applyFileService.updateApplyUser(au);//审核通过 或者 不通过 保存
			
			if(applyCheck.equals("5")){//审核通过的话 设置申请期限
				userService.userMap();//用户的所有申请单（有效期内）的  数据文件列表
			}
			
			
			//普通用户 将等待申请状态改成 已读
			NoteInfo updateNote = new NoteInfo();
			updateNote.setApplyId(Integer.valueOf(applyId));
			updateNote.setApplyFileType(1);//30数据
			updateNote.setIsManager(2);//普通用户
			updateNote.setProcess(4);//待审核
			updateNote.setReadFlag(2);//已经读取
			updateNote.setContent("您有未审核的陆态30秒数据申请！");
			noteInfoService.updateNoteInfo(updateNote);
			
			
			//短信发送提醒
			ApplyUser applyStartAndEnd = applyFileService.getApplyStartAndEnd(Integer.valueOf(applyId));
			String apply_Phone = applyStartAndEnd.getApply_Phone();
			URL url = null;
			if(applyCheck.equals("5")){//审核通过的话，FTP服务器生成ftp账号密码
				//url = new URL("http://172.128.8.15:80/Sms/earthQuakeDataReduction/emergencyDataClean.action?phone="+apply_Phone+"&message="+"您的陆态30秒数据申请通过了！");
			}else{
				//url = new URL("http://172.128.8.15:80/Sms/earthQuakeDataReduction/emergencyDataClean.action?phone="+apply_Phone+"&message="+"您的陆态30秒数据申请未获得通过！");
			}
			//URLConnection urlcon = url.openConnection();
	        //urlcon.getInputStream();
			
			
			if(applyCheck.equals("5")){//审核通过的话，FTP服务器生成ftp账号密码
				
				//用户消息处理
				NoteInfo noteInfo = new NoteInfo();
				noteInfo.setApplyId(Integer.valueOf(applyId));
				noteInfo.setContent("您的陆态30秒数据申请通过了！");
				noteInfo.setApplyFileType(1);//30数据
				noteInfo.setIsManager(2);//普通用户
				noteInfo.setProcess(5);//申请通过
				noteInfoService.insertNoteInfo(noteInfo);

				userService.userMap();
				
				//短信通知   通过了
				
			}else{
				//用户消息提醒
				NoteInfo noteInfo = new NoteInfo();
				noteInfo.setApplyId(Integer.valueOf(applyId));
				noteInfo.setContent("您的陆态30秒数据申请未获得通过！");
				noteInfo.setApplyFileType(1);//30数据
				noteInfo.setIsManager(2);//普通用户
				noteInfo.setProcess(6);//申请 不 通过
				noteInfoService.insertNoteInfo(noteInfo);
				
				//短信通知   未通过
			}
			
			
			//管理员 将等待申请状态改成 已读
			NoteInfo updateManagerNote = new NoteInfo();
			updateManagerNote.setApplyId(Integer.valueOf(applyId));
			updateManagerNote.setApplyFileType(1);//30数据
			updateManagerNote.setIsManager(1);//管理员
			updateManagerNote.setProcess(4);//待审核
			updateManagerNote.setReadFlag(2);//已经读取
			updateManagerNote.setContent("您有等待审核的陆态30秒数据申请！");
			noteInfoService.updateNoteInfo(updateManagerNote);
			
			return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功\"}]}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"date\":[{\"code\":\""+0+"\",\"msg\":\"保存失败\"}]}";
		}
	}
	
	
	//数据申请 第二个界面   点击下一步时，判断是否选择了数据
	@RequestMapping(value = "/checkRequiement", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String checkRequiement(HttpServletRequest request){
		String applyId = request.getParameter("applyId");
		List<Apply_User_Req> queryApplyUserReqList = applyFileService.query_Apply_User_ReqList(applyId);
		if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
			return "{\"date\":[{\"code\":\""+1+"\",\"msg\":\"保存成功\"}]}";
		}else{
			return "{\"date\":[{\"code\":\""+0+"\",\"msg\":\"保存失败\"}]}";
		}
	}
}
