package com.highfd.controller.Data.earthQuake;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.common.latLon.LocationUtils;
import com.highfd.common.map.MapAll;
import com.highfd.service.HighFDService;
import com.highfd.service.ManagerService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;
import com.highfd.common.file.CopyFileUtil;
import com.highfd.common.file.FileTool;
import com.highfd.service.Z_EarthDataService;

/**
 * 地震应急数据   整理
 */
@Component
@Controller
@RequestMapping(value="/earthQuakeDataReduction")
public class EarthQuakeDataReductionController {
	
	@Autowired
	SiteStationService siteService;
	@Autowired
	ManagerService managerService;
	@Autowired
	UserService userService;
	@Autowired
	HighFDService highFDService;
	@Autowired
	Z_EarthDataService z_EarthDataService;
	
	public static String Final_Path = "/root/nfs/Earthquake";//地震应急数据 最终存放的位置
	/**
	 ************************添加应急事件 （普通模式）**************************
	 */
	@RequestMapping(value = "/addEarthQuake", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String addEarthQuake(HttpServletRequest request,@ModelAttribute EarthQuake earthQuake) {
		String code="1";
		String message="";
		try{
			int earthQuakeId = managerService.addEarthQuake(earthQuake);
			earthQuake.setId(earthQuakeId);
			new DataCleanThread(earthQuakeId+"").start();
			message="添加成功！";
		}catch (Exception e){
			code="0";
			message="添加失败！";
			e.printStackTrace();
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	
	class DataCleanThread extends Thread {
		
		private String earthId;
		public DataCleanThread(String earthId){this.earthId = earthId;}
		public void run() {
			try {
				EarthQuake earthQuake = managerService.queryEarthQuakeByRun(" and s.id="+earthId);
				highFDService.insertEarthQuakeDataQualityInfo(earthQuake);//执行首页的报告
				List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
				double distianceByGrade = LocationUtils.getDistianceByGrade(earthQuake, earthQuakeConfig.get(0));//根据用户的手动 或者 自动 配置  获得应急数据的公里范围
				System.out.println("启动  地震应急事件 （普通模式）！！！！！");
				String startTime = earthQuake.getStrTime();
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
				String textName = startTime.substring(0, 10).replaceAll("-", "")+"-"+earthQuake.getName();
				//开始 往磁盘写入 该地震的文本说明文件
				String filePath = Final_Path+"/"+startTime.substring(0, 4)+"/"+textName;
				FileTool.outToText(earthQuake, filePath,textName+".txt");
				
				List<SiteInfo> siteInfoList = siteService.getSiteInfoList("1", "");//1 30S台站
				//初始化站点
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					//根据震级 判断是否在200 到 600公里 范围内
					double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
					boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
					if(!rangeByDistance){continue;}
					z_EarthDataService.iniMainData(earthQuake, siteInfo, startDate, filePath);
				}
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					//根据震级 判断是否在200 到 600公里 范围内
					double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
					boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
					if(!rangeByDistance){continue;}
					z_EarthDataService.mainData(earthQuake, siteInfo, startDate, filePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	
	/**
	 **************添加应急事件（极速模式）******************************
	 */
	@RequestMapping(value = "/addEarthQuakeByEmergency", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String addEarthQuakeByEmergency(HttpServletRequest request,@ModelAttribute EarthQuake earthQuake) {
		String code="1";
		String message="";
		try{
			int earthQuakeId = managerService.addEarthQuake(earthQuake);
			earthQuake.setId(earthQuakeId);
			URL url = new URL("http://localhost:80/DataShare/earthQuakeDataReduction/emergencyDataClean.action?earthId="+earthQuakeId);
	        URLConnection urlcon = url.openConnection();
	        urlcon.getInputStream();
			message="添加成功！";
		}catch (Exception e){
			code="0";
			message="添加失败！";
			e.printStackTrace();
		}
		return "{\"date\":[{\"code\":\""+code+"\",\"msg\":\""+message+"\"}]}";
	}
	/**
	 * 1、应急模式整理数据
	 * 2、应急模式 开启多线程下载
     * 3、(线程个数为 台站数量)
	 */
	@RequestMapping(value = "/emergencyDataClean", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void emergency(HttpServletRequest request) throws Exception {
		String earthId = request.getParameter("earthId");
		EarthQuake earthQuake = managerService.queryEarthQuakeByRun(" and s.id="+earthId);
		highFDService.insertEarthQuakeDataQualityInfo(earthQuake);//执行首页的报告
		List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
		double distianceByGrade = LocationUtils.getDistianceByGrade(earthQuake, earthQuakeConfig.get(0));//根据用户的手动 或者 自动 配置  获得应急数据的公里范围
		System.out.println("启动  地震应急  极速整理模式！！！！！");
		String startTime = earthQuake.getStrTime();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
		String textName = startTime.substring(0, 10).replaceAll("-", "")+"-"+earthQuake.getName();
		//开始 往磁盘写入 该地震的文本说明文件
		String filePath = Final_Path+"/"+startTime.substring(0, 4)+"/"+textName;
		FileTool.outToText(earthQuake, filePath,textName+".txt");
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList("1", "");//1 30S台站
		//初始化站点
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			//根据震级 判断是否在200 到 600公里 范围内
			double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
			boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
			if(!rangeByDistance){continue;}
			new DataThread( startDate,earthQuake,siteInfo,filePath).start();
			z_EarthDataService.iniMainData(earthQuake, siteInfo, startDate, filePath);
		}
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			//根据震级 判断是否在200 到 600公里 范围内
			double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
			boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
			if(!rangeByDistance){continue;}
			new DataThread( startDate,earthQuake,siteInfo,filePath).start();
		}
	}
	
	class DataThread extends Thread {
		
		private Date startDate;
		private EarthQuake earthQuake;
		private SiteInfo siteInfo;
		private String filePath;
		public DataThread(Date startDate,EarthQuake earthQuake,SiteInfo siteInfo,String filePath){
			this.startDate = startDate;
			this.earthQuake=earthQuake;
			this.siteInfo = siteInfo;
			this.filePath = filePath;
		}
		public void run() {
			try {
				z_EarthDataService.mainData(earthQuake, siteInfo, startDate, filePath);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 连续7天补充地震应急数据
	 */
	@RequestMapping(value = "earthQuake7Day")
	@Scheduled(fixedRate = 1000*60*60*24)
	public void earthQuake7Day() throws Exception {
		EarthQuake earthQuake = managerService.queryEarthQuakeByRun(" and s.year>=2017 and s.initype>0 and s.initype<=7 ");
		System.out.println("启动 七天回补程序  ");
		if(null==earthQuake){return;}
	    int iniType = Integer.valueOf(earthQuake.getIniType());
		
		List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
		//根据用户的手动 或者 自动 配置  获得应急数据的公里范围
		double distianceByGrade = LocationUtils.getDistianceByGrade(earthQuake, earthQuakeConfig.get(0));
		
		managerService.updateEarthQuakeType((iniType+1)+"", earthQuake.getId()+"");
		String startTime = earthQuake.getStrTime();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
		String filePath = Final_Path+"/"+startTime.substring(0, 4)+"/"+startTime.substring(0, 10).replaceAll("-", "")+"-"+earthQuake.getName();
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList("1", "");//1 30S台站
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			//根据震级 判断是否在200 到 600公里 范围内
			double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
			boolean rangeByDistance = LocationUtils.rangeByDistance(distance, distianceByGrade);
			if(!rangeByDistance){continue;}
			z_EarthDataService.mainData(earthQuake, siteInfo, startDate, filePath);
		}
	}
	
	
	/**
	 ************************地震应急事件  在线回补 **************************
	 */
	private static boolean supplyDataFlag_earthQuake = true;//true:可以执行  false：不能执行
	@RequestMapping(value = "/earthQuakeSupplyData", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String earthQuakeSupplyData(HttpServletRequest request,@ModelAttribute EarthQuake earthQuake) {
		String message="";
		boolean midFlag = supplyDataFlag_earthQuake;
		if(midFlag){
			String earthQuakeId = request.getParameter("earthQuakeId");
			String siteNumberArray = request.getParameter("taizhang");
			String zoneArray = request.getParameter("shengfen");
			String departmentArray = request.getParameter("buwei");
			String hzType = request.getParameter("hzType");
			String siteNumberStr = managerService.earthQuakeAupplyGetSiteNumberStr(zoneArray, departmentArray, siteNumberArray);
			new SupplyThread(earthQuakeId,siteNumberStr,hzType).start();
		}
		return "{\"date\":[{\"code\":\""+supplyDataFlag_earthQuake+"\",\"msg\":\""+message+"\"}]}";
	}

	class SupplyThread extends Thread {
		
		private String earthId;
		private String siteNumberStr;
		private String hzType;
		public SupplyThread(String earthId,String siteNumberStr,String hzType){this.earthId = earthId;this.siteNumberStr=siteNumberStr;this.hzType=hzType;}
		public void run() {
			try {
				supplyDataFlag_earthQuake=false;
				EarthQuake earthQuake = managerService.queryEarthQuakeByRun(" and s.id="+earthId);
				List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
				double distianceByGrade = LocationUtils.getDistianceByGrade(earthQuake, earthQuakeConfig.get(0));//根据用户的手动 或者 自动 配置  获得应急数据的公里范围
				System.out.println("启动  地震应急事件 （在线整理）！！！！！");
				String startTime = earthQuake.getStrTime();
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
				earthQuake.setHz1Or50(hzType);
				
				String filePath = Final_Path+"/"+startTime.substring(0, 4)+"/"+startTime.substring(0, 10).replaceAll("-", "")+"-"+earthQuake.getName();
				
				List<SiteInfo> siteInfoList = siteService.getSiteInfoList("1", "");//1 30S台站
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					//根据震级 判断是否在200 到 600公里 范围内
					double distance = LocationUtils.getDistance(Double.valueOf(siteInfo.getSiteLat()), Double.valueOf(siteInfo.getSiteLng()), Double.valueOf(earthQuake.getSiteLat()), Double.valueOf(earthQuake.getSiteLon()));
					if(!LocationUtils.rangeByDistance(distance, distianceByGrade)){continue;}
					if(null!=siteNumberStr && siteNumberStr.length()>0 && siteNumberStr.indexOf(siteInfo.getSiteNumber())==-1){
						continue;
					}
					z_EarthDataService.mainData(earthQuake, siteInfo, startDate, filePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				supplyDataFlag_earthQuake=true;
			}
		}
	}
	
	
	/**
	 ************************地震应急事件   FTP方式回补************************ 
	 */
	@RequestMapping(value = "/ftpSupplyEarthQuakeEvent", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public void ftpSupplyEarthQuakeEvent(HttpServletRequest request) throws Exception {
		z_EarthDataService.getEarthQuakeEventList(new File(MapAll.gnss_o));
	}
	
	public static void main(String[] args) {
	    String sourFolder = "D:/soreOutData/earthquake/2017"; // 可以修改源文件夹路径
	    String desFolder = "C:/root/nfs/Earthquake/2017/11/22/33"; // 可以修改目标文件夹路径
	    CopyFileUtil.copy(sourFolder, desFolder); // 调用copy()方法
	    System.out.println("文件夹复制成功！");
	}
}