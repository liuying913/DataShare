package com.highfd.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import listener.EventListener;
import listener.ServerListener;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.UpdateDownIdBean;
import com.highfd.common.linux.MetTypeTransfrom;
import com.highfd.common.map.MapAll;
import com.highfd.common.path.GetXMLPath;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.ManagerService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

/**
 * 基于注解的定时器
 */
@Component
@Controller
public class FTPController2 {
	
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	UserService userService;
	@Autowired
	ManagerService managerService;
	
	//下载记录  修改下载文件的 id
	@Scheduled(fixedRate = 1000*20) 
	//@RequestMapping("/downChangeFile")
	public void downChangeFile() throws Exception {
		
		/**
		 * 下载的数据，修改fileId
		 */
		for(int year=2012;year<=TimeUtils.getNowYear();year++){
			List<UpdateDownIdBean> downChangeFile = downHistoryService.downChangeFile(year+"");
			for(int i=0;i<downChangeFile.size();i++){
				UpdateDownIdBean bean = downChangeFile.get(i);
				downHistoryService.updateDownFileId(bean);
			}
		}
	
		
		/**
		 * 30S  数据 过期处理
		 */
		List<EarthQuakeConfig> earthQuakeConfig = siteService.earthQuakeConfigQuery();
		managerService.update30SApplyOverdue(earthQuakeConfig.get(0).getApplyperiod());
		
		/**
		 * 地震应急事件过期处理
		 */
		managerService.updateEarthQuakeApplyOverdue();
		
		//2018-06-01 ftp上传出现问题
		//userService.userMap();//用户的所有申请单（有效期内）的  数据文件列表  权限 liucheng=5   地震5
		
		//当超过2分钟就开始整理上传到ftp上的文件 o文件  和   T02文件
		if(TimeUtils.ftpUpTimeFlag(MapAll.ftpUpTime, 2)){
			System.out.println("超过2分钟开始执行  ftp数据回补");
			//30S 数据从用户上传到的ftp文件夹进行回补
			URL url = new URL("http://172.128.8.66/DataShare/supply/ftpFileToNAS.action");
	        URLConnection urlcon = url.openConnection();
	        InputStream is = urlcon.getInputStream();
	        
	        //地震应急  数据从用户上传到的ftp文件夹进行回补
	        URL url2 = new URL("http://172.128.8.66/DataShare/earthQuakeDataReduction/ftpSupplyEarthQuakeEvent.action");
	        URLConnection urlcon2 = url2.openConnection();
	        InputStream is2 = urlcon2.getInputStream();
		}
	
	}
}
