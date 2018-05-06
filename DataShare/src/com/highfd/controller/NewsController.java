package com.highfd.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.highfd.bean.DataQuality;
import com.highfd.bean.DocInfo;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.NewsInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.ideaInfo;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.excel.XwpfTUtil;
import com.highfd.common.file.FileOperateUtil;
import com.highfd.common.json.Json;
import com.highfd.common.map.MapAll;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DocService;
import com.highfd.service.HighFDService;
import com.highfd.service.SiteStationService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
@Controller
public class NewsController {

	@Autowired
	HighFDService highFDService;
	@Autowired
	DocService docService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;

	//获得新闻列表json
	@RequestMapping(value = "/news/getNewsInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewsInfoList(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<NewsInfo> siteInfoList = highFDService.getNewsInfoList("",type,"");
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return "{\"newsList\": [{\"News\": "+json.toString()+"}]}";
	}
	
	
	//判断新闻是否在30天之内
	@RequestMapping(value = "/news/getNews30DayFlag", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNews30DayFlag(HttpServletRequest request) {
		List<NewsInfo> siteInfoList = highFDService.getNewsInfoList("","");
		JSONArray json = JSONArray.fromObject(siteInfoList);
		return "{\"newsList\": [{\"News\": "+json.toString()+"}]}";
	}
	
	//*******新闻详细信息  跳转  + json*****************************************
	//获得新闻详细信息
	@RequestMapping(value = "/news/newsDetailToJsp")
	@ResponseBody
	public ModelAndView newsDetailToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("main/foreGround/inewDetail");
		return mav;
	}
	
	
	/**
	 *  编辑新闻之前获取新闻详细内容
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/news/bfmodifyNews")
		@ResponseBody
		public ModelAndView bfModifyNews(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
			//String id = request.getParameter("id");
			ModelAndView mav = new ModelAndView();
			mav.addObject("id", id);
			
			List<NewsInfo> newsInfoList = highFDService.getNewsInfoList(id, "","");
			mav.addObject("operType", "update");
			mav.addObject("news", newsInfoList.get(0));
			mav.setViewName("main/foreGround/newsDetailInfo");
			return mav;
		}
		
		
		/**
		 *  编辑新闻
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/news/modifyNews", produces = { "application/json;charset=UTF-8" })
		@ResponseBody
		public String modifyNews(NewsInfo info ) throws Exception {
			String res = "true";
			//更新数据库记录
			try {
				highFDService.updateNews(info);
			} catch (Exception e) {
				res = "false";
			}
			return res;
		}
			
	/**
	 *  编辑新闻
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/news/insertNewsoper", produces = { "application/json;charset=UTF-8" })
		@ResponseBody
		public String insertNewsOperation(NewsInfo info ) throws Exception {
			String res = "true";
			//更新数据库记录
			try {
				//String suid = UUID.randomUUID().toString();
				//int newsID = Integer.parseInt(suid);
				//info.setId(newsID);
				highFDService.insertNews(info);
			} catch (Exception e) {
				res = "false";
				e.printStackTrace();
			}
			return res;
		}
	
	
	//*******数据详细信息  跳转  + json*****************************************
	//获得新闻详细信息
	@RequestMapping(value = "/news/dataDetailToJsp")
	@ResponseBody
	public ModelAndView dataDetailToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("main/foreGround/idataDetail");
		return mav;
	}
	//获得新闻详细信息
	@RequestMapping(value = "/news/getNewsInoDetail", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNewsInoDetail(HttpServletRequest request) {
		String id = request.getParameter("id");
		List<NewsInfo> siteInfoList = highFDService.getNewsInfoList(id,"","");
		JSONArray json = JSONArray.fromObject(siteInfoList.get(0));
		return "{\"newsList\": [{\"News\": "+json.toString()+"}]}";
	}
	
	
	
	//************首页 界面url链接**********************************************************
	@RequestMapping(value = "index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		if(null!=id && !"".equals(id)){
			if("1".equals(id)){
				mav.setViewName("index");
			}
			
			else if("2".equals(id)){//平台简介
				mav.setViewName("main/foreGround/iCV");
			}else if("21".equals(id)){//中心简介
				mav.setViewName("main/foreGround/iCenter");
			}else if("22".equals(id)){//中心理念
				mav.setViewName("main/foreGround/iCenterIdeals");
			}
			
			else if("3".equals(id)){
				mav.setViewName("main/foreGround/iNewList");
			}else if("4".equals(id)){
				mav.setViewName("main/foreGround/iHelp");
			}else if("5".equals(id)){
				mav.setViewName("main/foreGround/iUsers");
			}
			
			else if("6".equals(id)){//共享指南  使用说明
				mav.setViewName("main/foreGround/gxzn");
			}else if("61".equals(id)){//共享指南  注意事项
				mav.setViewName("main/foreGround/gxjnZysx");
			}else if("62".equals(id)){//共享指南  数据介绍
				mav.setViewName("main/foreGround/gxznSjjs");
			}
			
			else if("7".equals(id)){//数据动态
				mav.setViewName("main/foreGround/sjdt");
			}else if("8".equals(id)){//下载专区
				mav.setViewName("main/foreGround/xzzq");
			}else if("9".equals(id)){//合作交流
				mav.setViewName("main/message/main");
			}else if("10".equals(id)){//新闻编辑
				mav.setViewName("main/newsEdit/newsEdit");
			}
			
			
			else{
				mav.setViewName("index");
			}
		}
		return mav;
	}
	

	//************数据 界面url链接**********************************************************
	@RequestMapping(value = "login")
	@ResponseBody
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("pid", pid);
		if(null!=id && !"".equals(id)){
			if("2".equals(id)){//数据查询
				mav.setViewName("main/dataShowMain");
			}else if("3".equals(id)){//数据申请
				mav.setViewName("main/applyMain");
			}else if("4".equals(id)){//个人中心
				mav.setViewName("main/userCenterMain");
			}else if("5".equals(id)){//统计分析
				mav.setViewName("main/analysisMain");
			}else if("6".equals(id)){//综合管理
				mav.setViewName("main/managerMain");
			}else if("7".equals(id)){//综合管理
				mav.setViewName("main/noteMain");
			}
			
			
			else{
				mav.setViewName("index");
			}
		}
		return mav;
	}
	
	
	
	
	/**
	 * 数据动态，列表信息
	 */
	@RequestMapping(value = "/news/data30_DataQualityList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String data30_DataQualityList(HttpServletRequest request) throws Exception {
		List<DataQuality> data30DataQualityList = highFDService.data30_DataQualityList("", "");
		JSONArray json = JSONArray.fromObject(data30DataQualityList);
		return json.toString();
	}
	
	//*******数据详细信息  跳转  + json*****************************************
	//获得新闻详细信息
	@RequestMapping(value = "/news/data30_DataQualityDetailToJsp")
	@ResponseBody
	public ModelAndView data30_DataQualityDetailToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		List<DataQuality> dataList = highFDService.data30_DataQualityList(id, "");
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		if(null!=dataList && dataList.size()>0){
			DataQuality dataQuality = dataList.get(0);
			if(dataQuality.getType().equals("1")){
				mav.setViewName("main/foreGround/data30_DataQualityDetail");
			}else if(dataQuality.getType().equals("4")){
				mav.setViewName("main/foreGround/dataEarthQuake_DataQualityDetail");
			}
		}
		return mav;
	}
	/**
	 * 数据动态，整理的每月的数据
	 */
	@RequestMapping(value = "/news/data30_DataQualityDetail", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String data30_DataQuality(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id"); 
		DataQuality bigData = highFDService.data30_DataQualityList(id, "").get(0);
		if(bigData.getType().equals("1")){//30S 数据
			
			List<DataQuality> resultDataList = highFDService.data30_DataQuality(bigData);
				DataQuality dataQuality0 = resultDataList.get(0);
				dataQuality0.setTitle(bigData.getTitle());
				dataQuality0.setContent(bigData.getContent());
				dataQuality0.setTime(bigData.getTime());
			JSONArray json = JSONArray.fromObject(resultDataList);
			return json.toString();
		}else if(bigData.getType().equals("4")){// 地震应急数据
			//地震应急事件   单个详细信息
			EarthQuake earthQuakeDetail = applyFileService.earthQuake_detail(bigData.getEarthQuakeId());
			bigData.setEarthQuakeName(earthQuakeDetail.getName());
			bigData.setEarthQuakeTimeStr(earthQuakeDetail.getStrTime());
			bigData.setSiteLat(earthQuakeDetail.getSiteLat());
			bigData.setSiteLon(earthQuakeDetail.getSiteLon());
			bigData.setGrade(earthQuakeDetail.getGrade());
			bigData.setHeight(earthQuakeDetail.getHeight());
			bigData.setAddress(earthQuakeDetail.getAddress());
			bigData.setRemark(earthQuakeDetail.getRemark());
			JSONArray json = JSONArray.fromObject(bigData);
			return json.toString();
		}else{
			return null;
		}
	}
	
	//************foot**********************************************************
	@RequestMapping(value = "head")
	@ResponseBody
	public ModelAndView head(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("head");
		return mav;
	}
	//************foot**********************************************************
	@RequestMapping(value = "foot")
	@ResponseBody
	public ModelAndView foot(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("foot");
		return mav;
	}
	
	
	//获得文档列表
	@RequestMapping(value = "/queryDocUseInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryDocUseInfoList(HttpServletRequest request) throws Exception {
		List<DocInfo> queryAllDocInfo = docService.queryDocUseInfoList();
		JSONArray json = JSONArray.fromObject(queryAllDocInfo);
		return "{\"newsList\": [{\"News\": "+json.toString()+"}]}";
	}
	
	
	//获得下载  列表
	@RequestMapping(value = "/queryDownList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryDownList(HttpServletRequest request) throws Exception {
		List<DocInfo> queryAllDocInfo = docService.queryDocUseInfoList();
		JSONArray json = JSONArray.fromObject(queryAllDocInfo);
		return "{\"newsList\": [{\"News\": "+json.toString()+"}]}";
	}
	
	
	
	@RequestMapping(value = "downRuanJian")
	public ModelAndView downRuanJian(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String storeName = ServletRequestUtils.getStringParameter(request,"storeName");
		String realName = ServletRequestUtils.getStringParameter(request,"realName");
		//String applyId = request.getParameter("applyId");
		
		
		
		String contentType = "application/octet-stream";
		//storeName="D:\\LTWL\\2014.xlsx";
		//storeName="\\docPath\\applyData.docx";
		//storeName = request.getSession().getServletContext().getRealPath("/")+ FileOperateUtil.UPLOADDIR+"/"+applyWorldPath;
		storeName = "FlashFXP.zip";
		FileOperateUtil.download(request, response, storeName, contentType,realName);

		return null;
	}
	
	
	//添加  交流合作
	@RequestMapping(value = "/news/insertIdea", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String insertIdea(HttpServletRequest request,@ModelAttribute ideaInfo info) {
		String code = "-1";
		String msg = "提交失败！，请重新提交";
		try {
			highFDService.insertIdea(info);
			code="1";
			msg = "提交成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}]}";
	}
	
	
	//跳转合作交流详细信息界面
	@RequestMapping(value = "/idea/ideaDetailToJsp")
	@ResponseBody
	public ModelAndView ideaDetailToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		mav.addObject("id", id);
		mav.setViewName("manager/showIdeaDetail");
		return mav;
	}
	
	//跳转到合作交流管理界面
	@RequestMapping(value = "/idea/ideaListToJsp")
	@ResponseBody
	public ModelAndView ideaListToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/ideaList");
		return mav;
	}
	//交流合作列表json
	@RequestMapping(value = "/idea/ideaList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String ideaList(HttpServletRequest request) {
		List<ideaInfo> ideaList = new ArrayList<ideaInfo> ();
		String id = request.getParameter("id");
		try {
			ideaList = highFDService.ideaList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray json = JSONArray.fromObject(ideaList);
		return json.toString();
	}
	
	
	
	//删除某一条合作交流，并调整到合作交流列表界面
	@RequestMapping(value = "/idea/deleteIdeaToJsp")
	@ResponseBody
	public ModelAndView deleteIdeaToJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		highFDService.deleteIdea(Integer.valueOf(id));
		String applyTitle = request.getParameter("applyTitle");
		mav.addObject("applyTitle", applyTitle);
		mav.setViewName("manager/ideaList");
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	//************数据 界面url链接**********************************************************
	@RequestMapping(value = "zzzzzOne")
	@ResponseBody
	public ModelAndView zzzzzOne(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		mav.addObject("id", id);
		mav.setViewName("main/zzzzzOne");
		return mav;
	}
	
	
	
	//word导出
    @RequestMapping("/news/exportWord")  
	 public void exportWord(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
        Map<String, Object> params = new HashMap<String, Object>();  
  
        params.put("${name}", "liuying123");  
       
  
        XwpfTUtil xwpfTUtil = new XwpfTUtil();  
  
        XWPFDocument doc;  
        String fileNameInResource = "news.docx";  
        InputStream is;  
        /*is = new FileInputStream(filePath);*/  
        is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);  
        doc = new XWPFDocument(is);  
  
        xwpfTUtil.replaceInPara(doc, params);  
        //替换表格里面的变量  
        xwpfTUtil.replaceInTable(doc, params);  
        OutputStream os = response.getOutputStream();  
  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition","attachment;filename=11111111.docx");  
  
        doc.write(os);  
  
        xwpfTUtil.close(os);  
        xwpfTUtil.close(is);  
  
        os.flush();  
        os.close(); 
	}
    
    
    
    
    /**
	 * 数据动态，列表信息
	 */
	@RequestMapping(value = "/news/weixin", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String weixin(HttpServletRequest request) throws Exception {
		String msg_signature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		//System.out.println("msg_signature: "+msg_signature);
		//System.out.println("timestamp: "+timestamp);
		//System.out.println("nonce: "+nonce);
		//System.out.println("echostr: "+echostr);
		//System.out.println();
		return "111";
	}
	
	
	
	
	
	//************报警测试**********************************************************
	@RequestMapping(value = "/news/alarmTest")
	@ResponseBody
	public ModelAndView alarmTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String siteType = request.getParameter("siteType");
		mav.addObject("siteType", siteType);
		mav.setViewName("alarm/alarmTest");
		return mav;
	}
	
	// 多站 单日数据  地图
	@RequestMapping(value = "/news/querycdayData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String querycdayData(HttpServletRequest request) throws Exception {
		Map<String, SiteInfo> mapSite = MapAll.mapSite;
		String siteNumberArray = request.getParameter("taizhang");
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		
		Map<String, Map<String,SiteMapInfo>> resultMap = new HashMap<String, Map<String,SiteMapInfo>>();
		
		//*************测试
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(siteNumberArray,buwei,shengfen,"1");
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			
			SiteMapInfo fd = new SiteMapInfo();
			
			fd.setFileFlag(22);
			fd.setSiteNumber(siteInfo.getSiteNumber());
			fd.setSiteName(siteInfo.getSiteName());
			fd.setSiteLat(siteInfo.getSiteLat());
			fd.setSiteLng(siteInfo.getSiteLng());
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			fileInfoMap2.put("one", fd);
			resultMap.put(siteInfo.getSiteName(), fileInfoMap2);
		}
		//***测试*****************
		
		List<FileInfo> fileInfoAllList = getSiteStates();
		
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
			//if(null!=resultMap.get(fileInfo.getSiteName()) ){
				fileInfoMap2.put("one", fd);
				resultMap.put(fd.getSiteName(), fileInfoMap2);
			//}
		}
		
		JSONArray json = JSONArray.fromObject(resultMap);
		return Json.getJsonArrayToStr(json);
	}
	
	
	public static List<FileInfo> getSiteStates() throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "ltwl";
		String password = "ltwl";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		String sql = "select a.site_number,a.fileflag from FILE_INFO_2017 a , (select t.site_number as numbers, max(t.filecreatetime) as maxtime from FILE_INFO_2017 t group by t.site_number) b where a.site_number= b.numbers and a.filecreatetime= b.maxtime";
		List<FileInfo> list = new ArrayList<FileInfo>();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSiteNumber(rs.getString("site_number"));
			fileInfo.setFileFlag(rs.getInt("fileflag"));
			list.add(fileInfo);
		}
		stmt.close();
		conn.close();
		return list;
	}
	
}

/*class FileInfo{
	public String siteNumber;
	public String fileFlag;
	public FileInfo() {
		super();
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}
	
}*/
