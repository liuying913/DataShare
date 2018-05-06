package com.highfd.service;

import java.util.List;
import java.util.Map;
import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UpdateDownIdBean;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.PageInfo;

public interface DownHistoryService {
	
	public void insertDictionary(final DownHistoryInfo info) throws Exception;
	
	//查询 数据下载记录
	public List<FileHistroyDown>  getDownHistoryList(String year,String fileType,String siteParam);
	
	//查询 地震应急数据下载记录
	public List<FileHistroyDown>  earthQuakeDownHistoryList(String fileType,String siteParam);
	
	//综合用户查询 数据下载记录
	@SuppressWarnings("unchecked")
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String applyId,String fileType,String siteParam,PageInfo pageinfo);
	
	//综合用户查询所有数据下载记录
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String applyId,String fileType,String siteParam);
	
	public void upFileList(final FileInfo info,String path) throws Exception;
	
	public void insertFileInfo(final FileInfo info) throws Exception;
	
	//目录整理   单站、多站   单月  年度
	public Map<String, Map<String,FileInfoDirect>> s30s260MonthData(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers);
	
	//目录整理   单站、多站   单月  年度   转化成json
	public String dataToJson(Map<String, Map<String,FileInfoDirect>> resultMap);
	
	//目录整理  单站月度   柱状图
	public List<FileInfoDirect> s30sOneMonthData(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers);
	
	//目录整理   单站、多站   单月  年度(excel)
	public Map<String, Map<Integer,FileInfoDirect>> s30s260MonthData2(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers);
	
	//统计分析地图30S 文件下载
	public List<FileDownBySiteNumberBean>  fileDownGroupBySiteNumberForMap(String fileType,String startTime,String endTime,String userId);
	//统计分析  表格 文件下载情况
	public List<FileDownBySiteNumberBean>  siteInfoDownGroupForTable(String fileType,String startTime,String endTime,String userId);
	//统计分析   部委下载分组统计分析
	public List<GroupByDepPro>  departmentDown(String fileType,String siteType,String startTime,String endTime,String userId);
	//统计分析   省份下载分组统计分析
	public List<GroupByDepPro>  zoneDown(String fileType,String siteType,String startTime,String endTime,String userId);
	//统计分析   月度分组统计分析
	public List<GroupByDepPro>  monthDown(String fileType,String startTime,String endTime);
	//通过applyID 获得userId
	public Integer applyId_UserId(String id);
	//地震应急事件   表格展示
	public List<FileInfo>  downTable(String userName,String earthQuakeId,String year);
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String userName,String earthQuakeId,String year);
	
	
	/**
	 * 将 用户下载的文件 修改文件的id
	 */
	public List<UpdateDownIdBean>  downChangeFile(final String year);
	
	/**
	 * 修改下载文件的 id
	 */
	public void updateDownFileId(final UpdateDownIdBean bean) throws Exception;
}
