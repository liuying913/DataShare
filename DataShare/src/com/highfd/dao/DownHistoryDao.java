package com.highfd.dao;

import java.util.List;

import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.UpdateDownIdBean;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.PageInfo;

public interface DownHistoryDao {
	
	public void insertDictionary(final DownHistoryInfo info) throws Exception;
	//查询 数据下载记录
	public List<FileHistroyDown>  getDownHistoryList(String year,String fileType,String siteParam);
	
	//查询 地震应急数据下载记录
	public List<FileHistroyDown>  earthQuakeDownHistoryList(String fileType,String siteParam);
	
	//综合用户查询 数据下载记录
	@SuppressWarnings("unchecked")
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String applyId,String fileType,String siteParam,PageInfo pageinfo);
	
	//综合用户查询全部数据下载记录
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String applyId,String fileType,String siteParam);
	
	public Integer  getUserDownHistoryListCount(String year, String applyId,String fileType,String siteParam);
	
	public void insertFileInfo(final FileInfo info) throws Exception;
	
	//统计分析地图30S 文件下载
	public List<FileDownBySiteNumberBean>  fileDownGroupBySiteNumberForMap(String fileType,String startTime,String endTime,String userId);
	
	//统计分析  表格 文件下载情况
	public List<FileDownBySiteNumberBean>  siteInfoDownGroupForTable(String fileType,String startTime,String endTime,String userId);
	
	//统计分析   部委下载分组统计分析
	public List<GroupByDepPro>  departmentDown(String fileType,String startTime,String endTime,String userId);
	
	//统计分析   省份下载分组统计分析
	public List<GroupByDepPro>  zoneDown(String fileType,String startTime,String endTime,String userId);
	
	//统计分析   月度分组统计分析
	public List<GroupByDepPro>  monthDown(String fileType,String startTime,String endTime);
	
	//通过applyID 获得userId
	public Integer applyId_UserId(String id);
	
	//地震应急事件   表格展示
	public List<FileInfo>  downTable(String userName,String earthQuakeId,String year);
	
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String userName,String earthQuakeId,String year,final EarthQuakeConfig earthConfig);
	
	/**
	 * 将 用户下载的文件 修改文件的id
	 */
	public List<UpdateDownIdBean>  downChangeFile(final String year);
	/**
	 * 修改下载文件的 id
	 * @param bean
	 * @throws Exception
	 */
	public void updateDownFileId(final UpdateDownIdBean bean) throws Exception;
}
