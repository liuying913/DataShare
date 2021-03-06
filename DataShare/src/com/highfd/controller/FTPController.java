package com.highfd.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import listener.EventListener;
import listener.ServerListener;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.highfd.common.map.MapAll;
import com.highfd.common.path.GetXMLPath;
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
public class FTPController {
	
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
	
	public static boolean oneFlag = true;
	@Scheduled(fixedRate = 1000*60*60*24*10000) 
	@RequestMapping("/FTPStart")
	public void FTPStart() { 
		
		try {
			
			String dbpath = GetXMLPath.getXMLPath(getClass());
			MapAll.winOrLinux = GetXMLPath.getProperties(dbpath,"winOrLinux");//判断是win 还是 linux
			MapAll.userFtpSpaces = GetXMLPath.getProperties(dbpath,"userFtpSpace");//用户 ftp空间位置
			
			MapAll.S30SBasePath = GetXMLPath.getProperties(dbpath,"S30SBasePath");
			MapAll.S30SWorkPath = GetXMLPath.getProperties(dbpath,"S30SWorkPath");
			MapAll.S30S_ZipPath = GetXMLPath.getProperties(dbpath,"S30S_ZipPath");
			
			MapAll.applyTempletPath = GetXMLPath.getProperties(dbpath,"applyTempletPath");//数据申请模板位置
			MapAll.software=GetXMLPath.getProperties(dbpath,"software");//软件下载地址
			
			MapAll.driverClassName = GetXMLPath.getProperties(dbpath,"driverClassName");
			MapAll.url = GetXMLPath.getProperties(dbpath,"url");
			MapAll.username = GetXMLPath.getProperties(dbpath,"username");
			MapAll.password = GetXMLPath.getProperties(dbpath,"password");
			
			String FTP_User = "";//FTP 用户配置文件
			if("win".equals(MapAll.winOrLinux)){
				MapAll.FTPLocalPath = GetXMLPath.getProperties(dbpath,"fileSystemPathWin");
				FTP_User = GetXMLPath.getProperties(dbpath,"ftpUserWin");
				MapAll.configFile = GetXMLPath.getProperties(dbpath,"configFileWin");
				MapAll.gnss_o = GetXMLPath.getProperties(dbpath,"gnss_oWin");
				MapAll.Share_o = GetXMLPath.getProperties(dbpath,"Share_oWin");
				MapAll.fileLogBaseName = GetXMLPath.getProperties(dbpath,"fileLogBaseNameWin");
			}else{
				MapAll.FTPLocalPath = GetXMLPath.getProperties(dbpath,"fileSystemPathLinux");
				FTP_User = GetXMLPath.getProperties(dbpath,"ftpUserLinxu");
				MapAll.configFile = GetXMLPath.getProperties(dbpath,"configFileLinux");
				MapAll.gnss_o = GetXMLPath.getProperties(dbpath,"gnss_oLinux");
				MapAll.Share_o = GetXMLPath.getProperties(dbpath,"Share_oLinux");
				MapAll.fileLogBaseName = GetXMLPath.getProperties(dbpath,"fileLogBaseNameLinux");
			}
			
			//仅执行一次
			if(oneFlag){
				siteService.mapSite();
				userService.userMap();//用户的所有申请单（有效期内）的  数据文件列表  权限 liucheng=5   地震5
				oneFlag=false;
			}else{return;}
			
			
			MapAll.logger.error("FTP server**********************");
			CrawTaskAnnotation.serverFactory = new FtpServerFactory();
			CrawTaskAnnotation.userManagerFactory = new PropertiesUserManagerFactory();
			CrawTaskAnnotation.userManagerFactory.setFile(new File(FTP_User));
			UserManager userManager = CrawTaskAnnotation.userManagerFactory.createUserManager();
			CrawTaskAnnotation.serverFactory.setUserManager(userManager);
			FtpServer server = CrawTaskAnnotation.serverFactory.createServer();
			
			//添加服务监听
			ServerListener t = new ServerListener();
			CrawTaskAnnotation.serverFactory.addListener("t", t);
			
			//添加事件监听
			Ftplet f = new EventListener();
			Map<String, Ftplet> ftplets = new HashMap<String, Ftplet> ();
			ftplets.put("f", f);
			CrawTaskAnnotation.serverFactory.setFtplets(ftplets);
			server.start();
			System.out.println("FTP 启动成功！！！！");
		} catch (FtpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*测试修改上传功能
		 * BaseUser userName = (BaseUser) userManager.getUserByName("20170217_180");
		List<Authority> authorities = new ArrayList<Authority>();
	    authorities.add(new WritePermission());//是否包括写入权限
	    //authorities.add(new ConcurrentLoginPermission(1, 1));
	    //authorities.add(new TransferRatePermission(10*1000, 10*1000));
	    userName.setAuthorities(authorities);
		userManager.save(userName);//user 必须是实现User接口的实体类  */		
		
		//气象数据回补
		//MetTypeTransfrom.analysisAll_M(new File("/root/nfs/Gnssdata/met_files"));
		//MetTypeTransfrom.analysisAll_M(new File("/root/tmp_nas/gnssdata/CMONOC-GNSS"));
		//MetTypeTransfrom.analysisAll_M(new File("/home/liuying/To2Ftp"));
	}
	
	/**
	 * 上传入库
	 */
	@RequestMapping(value = "/upLoadFTP", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String upLoadFTP(HttpServletRequest request) throws Exception {
		downHistoryService.upFileList(null,MapAll.FTPLocalPath);
		return "";
	}
	
	//下载记录 入库操作
	//@Scheduled(fixedRate = 1000*10) 
	//@RequestMapping("/downHistoryMap")
/*	public void downHistoryMap() throws Exception {
		
		Map<String, DownHistoryInfo> middelMapDown = MapAll.mapDown;
		//MapAll.mapDown = new Hashtable<String,DownHistoryInfo>();//下载集合
		
		Iterator<String> iterator = middelMapDown.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			DownHistoryInfo downInfo = middelMapDown.get(key);
			for(FileInfo info:MapAll.listFile){
				//通过文件 去数据库中查询来寻找id
				if(downInfo.getFileName().indexOf(info.getFileName().toLowerCase())>-1){//将 文件名称转成小写
					System.out.println(info.getAllFilePathName()+"  "+info.getId()+"  "+info.getFileName()+"   下载的文件名称"+downInfo.getFileName());
				   downInfo.setFileInfoId(info.getId());
				   break;
			   }
		    }
		    downHistoryService.insertDictionary(downInfo);
		    //MapAll.mapDown.remove(key);
		    //此处可以做额外的判断，相应符合条件的元素
			iterator.remove();
		}
	}
	*/
	
}
