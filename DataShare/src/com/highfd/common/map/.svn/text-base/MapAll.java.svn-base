package com.highfd.common.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.highfd.bean.DicInfo;
import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UserInfo;
import com.highfd.bean.ZoneInfo;

public class MapAll {
	
	public static Map<String,DownHistoryInfo> mapDown = new Hashtable<String,DownHistoryInfo>();//下载集合
	
	public static Map<Integer,FileInfo> mapFile = new HashMap<Integer,FileInfo>();//所有文件 的键值对???
	public static List<FileInfo> listFile = new ArrayList<FileInfo>();//所有文件 列表
	
	public static Map<Integer,UserInfo> mapUser = new HashMap<Integer,UserInfo>();
	public static Map<String,UserInfo> mapUserName = new HashMap<String,UserInfo>();
	
	
	public static Map<String,SiteInfo>  mapSite = new HashMap<String,SiteInfo>(); //台站列表
	public static Map<String,ZoneInfo>  mapZone = new HashMap<String,ZoneInfo>();//省份列表
	public static Map<String,DicInfo>  mapDepat = new HashMap<String,DicInfo>();//部委列表
	public static Map<String,EarthQuake>  mapEarthQuake = new HashMap<String,EarthQuake>();//应急数据事件列表
	
	
	public static final Logger logger = LoggerFactory.getLogger("itemCRUD");//日志
	public static ApplicationContext ctx = null;//spirng容器
	
	public static String FTPLocalPath = "";//FTP跟目录
	
	
	public static String configFile = "";//头像、文件路径
	public static String userId = "";
	
	public static String winOrLinux = "";
	public static String userFtpSpaces = "";//用户 ftp空间位置
	
	public static String driverClassName = "";//数据库驱动
	public static String url = "";//数据库链接地址
	public static String username = "";//数据库用户名
	public static String password = "";//数据库密码
	
	
	public static String S30SBasePath = "";//30临时NAS盘路径
	public static String S30SWorkPath = "";//拷贝到本地的路径
	public static String S30S_ZipPath = "";//最终放到的NAS盘位置
	
	public static String applyTempletPath="";//数据申请模板文件位置
	public static String software = "";//软件下载路径
	
	
	public static String gnss_o = "";//将gnss接收机上转化的o文件 放到ftp文件夹下，进行数据补录
	public static String Share_o="";
	public static String fileLogBaseName = "";//在线数据整理 日志路径
	
	
	public static long ftpUpTime = 0L;//将o文件、d.Z文件 上传到ftp上，ftp进行自动整理

}
