package com.highfd.dao;

import java.util.List;
import java.util.Map;

import com.highfd.bean.DicInfo;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.ZoneInfo;

public interface SiteStationDAO {
	
	public List<SiteInfo>  getSiteInfoList(String siteType,String userParam);
	
	public List<SiteInfo> getSuperSiteInfoList(String siteNumberArray,String siteName,String zoneArray,String siteLat1,String siteLat2,String siteLon1,String siteLon2,String type);
	
	//根据  部委列表  省份列表、台站列表、台站名称 进行查询
	public List<SiteInfo> getSiteInfoList(String siteNumberArray,String departmentArray,String zoneArray,String type);
	//搜索   省份  台站  部位  查询 台站信息
	public List<SiteInfo>  getSiteInfoListByIn(String siteType,String siteParam);
	
	public List<ZoneInfo>  getZoneInfoList();
	
	public List<SiteInfo>  getZoneSiteInfoList(String smallLat,String bigLat,String smallLon,String bigLon,String siteTypeStr);
	public List<SiteInfo>  applySiteZoneList(String applyId,String siteType);
	//获得广域站列表
	public List<SiteInfo>  getZoneSiteInfoList_GY(String smallLat,String bigLat,String smallLon,String bigLon);
	//获得  申请界面  广域站列表
	public List<SiteInfo>  applyZoneSiteList_GY(String applyId,String siteType);
	
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception;
	//获得 部位 分组
	public Map<String,String>  departmentGroup(String siteType);
	//获得 省份 分组
	public Map<String,String> zoneGroup(String siteType);
    //获得 部位 列表
	public List<DicInfo>  getDicInfoList(String dicTypeCode);
	
	//根据地震应急事件Id  获得  台站列表
	public List<SiteInfo>  getEarthquakeSiteList(String earthquakeId, String year);
	//通过省份编号 获得台站列表
	public String getSiteNumberListByZoneCode(String type,String zoneArray);
	
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay);
	
	//查询应急数据
	public List<EarthQuakeConfig> earthQuakeConfigQuery();

	//修改应急数据
	public void earthQuakeConfigInsert(EarthQuakeConfig earthquakeconfig) throws Exception ;

	//插入应急数据
	public void earthQuakeConfigUpdate(EarthQuakeConfig earthquakeconfig) throws Exception;
	
}
