package com.highfd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.DicInfo;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.ZoneInfo;
import com.highfd.common.map.MapAll;
import com.highfd.dao.ApplyFileDAO;
import com.highfd.dao.SiteStationDAO;
import com.highfd.service.SiteStationService;

@Service
public class SiteStationServiceImpl implements SiteStationService {
	
	@Autowired
	SiteStationDAO dao;
	
	@Autowired
	ApplyFileDAO Filedao;
	
	//map键值对
	public void mapSite(){
		List<SiteInfo> list = dao.getSiteInfoList("","");//台站列表
		for(SiteInfo info:list){
			MapAll.mapSite.put(info.getSiteNumber(), info);
		}
		
		List<ZoneInfo> zoneInfoList = dao.getZoneInfoList();//省份列表
		for(ZoneInfo info:zoneInfoList){
			MapAll.mapZone.put(info.getZoneCode(), info);
		}
		
		List<DicInfo> departList = dao.getDicInfoList("dic-001");//部委列表
		for(DicInfo info:departList){
			//System.out.println(info.getDicEnName()+" 部委英文编号");
			//System.out.println(info.getDicCnName()+"部委中文名称");
			MapAll.mapDepat.put(info.getDicEnName(), info);
		}
		
		List<EarthQuake> earthQuakeList = Filedao.query_EarthQuake_List("",false,"","","");//地震应急事件列表
		for(EarthQuake info:earthQuakeList){
			MapAll.mapEarthQuake.put(info.getId()+"", info);
		}
		
	}
	
	
	public List<SiteInfo>  getSiteInfoList(String siteType,String userParam){
		return dao.getSiteInfoList( siteType, userParam);
	}
	
	//搜索   省份  台站  部位  查询 台站信息
	public List<SiteInfo>  getSiteInfoListByIn(String siteType,String siteParam){
		return dao.getSiteInfoListByIn(siteType, siteParam);
	}
	
	public List<SiteInfo> getSuperSiteInfoList(String siteNumberArray,String siteName,String zoneArray,String siteLat1,String siteLat2,String siteLon1,String siteLon2,String type){
		return dao.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, type);
	}
	
	//根据  部委列表  省份列表、台站列表、台站名称 进行查询
	public List<SiteInfo> getSiteInfoList(String siteNumberArray,String departmentArray,String zoneArray,String type){
		return dao.getSiteInfoList(siteNumberArray, departmentArray, zoneArray, type);
	}
	
	//根据  部委列表  省份列表、台站列表、台站名称 进行查询
	public String getSiteNumberStr(String siteNumberArray,String departmentArray,String zoneArray,String type){
		StringBuffer sb = new StringBuffer("");
		List<SiteInfo> siteInfoList = dao.getSiteInfoList(siteNumberArray, departmentArray, zoneArray, type);
		if(null!=siteInfoList){
			for(int i=0;i<siteInfoList.size();i++){
				SiteInfo siteInfo = siteInfoList.get(i);
				sb.append(siteInfo.getSiteNumber()+",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	public List<ZoneInfo>  getZoneInfoList(){
		return dao.getZoneInfoList();
	}
	
	public List<SiteInfo>  getZoneSiteInfoList(String smallLat,String bigLat,String smallLon,String bigLon,String siteTypeStr){
		return dao.getZoneSiteInfoList(smallLat,bigLat,smallLon,bigLon, siteTypeStr);
	}
	
	public List<SiteInfo>  applySiteZoneList(String applyId,String siteType){
		return dao.applySiteZoneList(applyId,siteType);
	}
	//获得广域站列表
	public List<SiteInfo>  getZoneSiteInfoList_GY(String smallLat,String bigLat,String smallLon,String bigLon){
		return dao.getZoneSiteInfoList_GY( smallLat, bigLat, smallLon, bigLon);
	}
	//获得  申请界面  广域站列表
	public List<SiteInfo>  applyZoneSiteList_GY(String applyId,String siteType){
		return dao.applyZoneSiteList_GY(applyId,siteType);
	}
	
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception {
		dao.updateBaseSiteInfo(siteInfo);
	}
	
	//获得 部位 分组
	public Map<String,String> departmentGroup(String siteType){
		return dao.departmentGroup(siteType);
	}
	//获得 省份 分组
	public Map<String,String> zoneGroup(String siteType){
		return dao.zoneGroup(siteType);
	}
	
	
    //获得 部位 列表
	public List<DicInfo>  getDicInfoList(String dicTypeCode){
		return dao.getDicInfoList(dicTypeCode);
	}
	
	//根据地震应急事件Id  获得  台站列表
	public List<SiteInfo>  getEarthquakeSiteList(String earthquakeId, String year){
		List<SiteInfo> siteNewList = new ArrayList<SiteInfo>();
		List<SiteInfo> siteList = dao.getEarthquakeSiteList(earthquakeId,year);
		for(int i=0;i<siteList.size();i++){
			SiteInfo siteInfo = siteList.get(i);
			if(!siteNewList.contains(siteInfo)){
				siteNewList.add(siteInfo);
			}
		}
		return siteNewList;
	}
	
	//通过省份编号 获得台站列表
	public String getSiteNumberListByZoneCode(String type,String zoneArray){
		return dao.getSiteNumberListByZoneCode(type, zoneArray);
	}
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay){
		return dao.getSiteInfoListByFileFlag(yearDay);
	}
	
	
	//查询应急数据
	public List<EarthQuakeConfig> earthQuakeConfigQuery() {
		return dao.earthQuakeConfigQuery();
	}
	//插入应急数据
	public void earthQuakeConfigInsert(EarthQuakeConfig earthquakeconfig) throws Exception {
		dao.earthQuakeConfigInsert(earthquakeconfig);
	}
	//修改应急数据
	public void earthQuakeConfigUpdate(EarthQuakeConfig earthquakeconfig) throws Exception {
		dao.earthQuakeConfigUpdate(earthquakeconfig);
	}
}
