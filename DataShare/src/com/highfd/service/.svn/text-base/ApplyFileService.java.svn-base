package com.highfd.service;

import java.util.List;
import java.util.Map;

import com.highfd.bean.ApplyUser;
import com.highfd.bean.Apply_User_Files;
import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.Apply_User_SiteInfo;
import com.highfd.bean.Apply_User_Zone;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.dataShare.SiteMapInfo;

public interface ApplyFileService {
	
	public void fileInfoMap();
	
	//申请入库
	public int saveApplyUser(final ApplyUser af);
	
	public void updateApplyUser(final int num,final ApplyUser af) throws Exception;
	
	//修改  申请流程状态 流程2
	public void updateApplyUserLiuCheng2(final int num,final ApplyUser af) throws Exception;

	public void updateApplyUser(final ApplyUser af) throws Exception;
	
	public void updateApplyUser_FTP(final String ftpUserName,final String ftpPwd,final String applyId) throws Exception;
	
	//获得申请信息
	public List<ApplyUser> queryApplyUser(final int userId,final ApplyUser af);
	
	public String queryApplyLiuChengByApplyId(final String applyId);
	
	public void insert_Apply_User_Files(final int fileId,final int applyId,final String datetype) throws Exception;
	/**
	 * 申请  文件列表 批量入库**************************************************************************************************
	 */
	public void insert_Apply_User_Files(final List<FileInfo> fileList,final int applyId,final String datetype);
	
	public void deleteApplyFile(final String applyId,final String type) throws Exception;

	public int  queryCount_ApplyFile(String applyId,String datetype) throws Exception;
	
	public void insert_Apply_User_Zone(final String zoneCode,final int applyId) throws Exception;
	
	public List<Apply_User_Files>  query_Apply_User_FilesList(String applyId);
	
	//根据 申请条件  开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	public List<FileInfo>  getApplyFileByApplyId(String applyId,String siteNumberArray,String yearNumber);
	
	//数据申请第二步  查询
	public List<FileInfo>  getShowFileByDay(String applyId,String siteNumberArray,String zoneArray,String siteType,String fileYear);
	
	public List<Apply_User_SiteInfo>  query_Apply_User_SiteInfoList(String applyId);
	
	public List<Apply_User_Zone>  query_Apply_User_ZoneList(String applyId);
	
	public void insert_Apply_User_Req(final Apply_User_Req aur) throws Exception ;
	
	public void update_Apply_User_Req(final Apply_User_Req aur) throws Exception ;
	
	public List<Apply_User_Req> query_Apply_User_ReqList(String applyId);
	
	public List<FileInfo>  getFileInfoList(String fileYear,String fileDayYear,String site_number,String type,String fileFlag);
	
	//数据申请第二步   申请数据
	public List<FileInfo>  getApplyFileByDay(String startTime,String endTime,String fileDayYear,String siteNumberArray,String zoneArray, String siteLat1,String siteLat2,String siteLon1,String siteLon2,String siteType,String fileType,String fileYear);
	
	public List<FileInfo>  getApplyFileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year);
    
	public List<FileInfo>  excelApplyFileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year);
	//获得文件列表
	public List<FileInfo>  getYearDayileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year);
	//根据申请id，删掉所有数据
	public void deleteUserApply(final String applyId) throws Exception;
	
	

	//申请文件列表
	public int  queryCount_Apply_User_SiteInfoList(String applyId,String datetype)throws Exception;
	public void deleteUserInfo(final String applyId,String datetype) throws Exception;
	public void insert_Apply_User_SiteInfo(final String siteNumber,final String applyId,String datetype) throws Exception;
	public void insert_Apply_User_SiteInfo(final List<SiteInfo> siteList,final int applyId,final String datetype);

	public ApplyUser getApplyStartAndEnd(final int applyId);
	//地震应急事件   单个详细信息
	public EarthQuake  earthQuake_detail(String id);
	//地震应急事件  列表
	public List<EarthQuake>  query_EarthQuake_List(String keyWords,boolean dataShowFlag,String startTime,String endTime,String year);
	//地震应急事件   日志目录查看
	public List<FileInfo>  query_EarthQuake_Data(String earthQuakeId,String year,String zoneArray,String departmentArray,String siteNumberArray);
	//地震应急事件   日志目录 整理
	//地震应急事件   日志目录 整理
	public Map<String, Map<String,FileInfoDirect>> query_EarthQuake_Data_Json(String siteNumberArray,List<FileInfo> earthQuakeFileList,String earthQuakeId,String year);
	
	//地震应急事件   日志目录 整理
	public String query_EarthQuake_Data_Json(Map<String, Map<String,FileInfoDirect>> fileListTo50Or1);
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String earthQuakeId,String year);
	/**
	 * 30秒日常整理*************************************************************************************************
	 */
	public void insert_30SData_Files(final String year,final List<FileInfo> fileList);
	
	//通过文件名称 查询文件是否存在
	public FileInfo  select_30SData_Files(final String year,final FileInfo fileInfo);
	
	/**
	 * 地震应急数据整理*************************************************************************************************
	 */
	public void insert_EarhtData_Files(final String year,final List<FileInfo> fileList);
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public void update_30S_apply(final String year,final FileInfo fileInfo);
	
	//根据  台站名称、年份、数据类型   判断是否有缺失的数据  （数据定时整理用到的）
	public boolean  queryHistoryFileInfo(String year,String fileDayYear,String siteNumber,String type,String earthQuakeId,String fileFlagSql);
	
}
