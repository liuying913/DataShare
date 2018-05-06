package com.highfd.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.highfd.bean.SiteInfo;
import com.highfd.bean.ZoneSiteInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
public class ZoneSite {
	
	//将台站列表    ————> 省份中包括（台站） 列表
	public List<ZoneSiteInfo> siteLisToZoneList(List<SiteInfo> siteInfoList){
		Map<String,List<SiteInfo>> map = new LinkedHashMap<String,List<SiteInfo>>();
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			List<SiteInfo> list = map.get(siteInfo.getZoneCode());
			if(list==null){
				List<SiteInfo> list3 = new ArrayList<SiteInfo>();
				list3.add(siteInfo);
				map.put(siteInfo.getZoneCode(), list3);
				//System.out.println(siteInfo.getZoneCode());
			}else{
				list.add(siteInfo);
				map.put(siteInfo.getZoneCode(), list);
			}
		}
		List<ZoneSiteInfo> listAll = new ArrayList<ZoneSiteInfo>();
	    for (String key : map.keySet()) {
		   //System.out.println("key= "+ key + " and value= " + map.get(key));
		   ZoneSiteInfo zsi = new ZoneSiteInfo();
		   zsi.setId(map.get(key).get(0).getZoneCode());
		   zsi.setZoneName(map.get(key).get(0).getZoneName());
		   zsi.setCitys(map.get(key));
		   listAll.add(zsi);
		}
	    return listAll;
	}
	
	
	//list集合去重
	public static List<SiteInfo>  distinctSiteList(List<SiteInfo> siteList){
		List<SiteInfo> siteNewList = new ArrayList<SiteInfo>();
		for(int i=0;i<siteList.size();i++){
			SiteInfo siteInfo = siteList.get(i);
			if(!siteNewList.contains(siteInfo)){
				siteNewList.add(siteInfo);
			}
		}
		return siteNewList;
	}

    //初始化  列表指（地震应急模块，1赫兹、50赫兹）
	public static Map<String,FileInfoDirect> putFileMap(int number){
		Map<String,FileInfoDirect> siteDayFileMap = new TreeMap<String,FileInfoDirect>();//填放初始数据
		for(int j=1;j<=number;j++){
			FileInfoDirect fileInfo = new FileInfoDirect();
			fileInfo.setFileFlag(0);
			siteDayFileMap.put(j+"", fileInfo);
		}
		return siteDayFileMap;
	}
	
	
	
	
	public static String siteListToStr(List<SiteInfo> siteInfoList){
		StringBuffer sb = new StringBuffer("");
		if(siteInfoList!=null && siteInfoList.size()>0){
			for(int i=0;i<siteInfoList.size();i++){
				SiteInfo siteInfo = siteInfoList.get(i);
				sb.append(siteInfo.getSiteNumber()+",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	
}
