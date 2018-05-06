package com.highfd.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.ApplyUser;
import com.highfd.bean.Apply_User_Files;
import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.Apply_User_SiteInfo;
import com.highfd.bean.Apply_User_Zone;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.ZoneSite;
import com.highfd.common.json.Json;
import com.highfd.dao.ApplyFileDAO;
import com.highfd.dao.SiteStationDAO;
import com.highfd.service.ApplyFileService;

@Service
public class ApplyFileServiceImpl implements ApplyFileService {
	
	@Autowired
	ApplyFileDAO dao;
	@Autowired
	SiteStationDAO siteDao;
	
	//map键值对
	public void fileInfoMap(){
		/*List<FileInfo> list = new ArrayList<FileInfo>();
		for(int i=2012;i<=2017;i++){
			List<FileInfo> listYear = dao.getFileInfoList(i+"", "", "", "", "");
			list.addAll(listYear);
		}
		
		MapAll.listFile = new ArrayList<FileInfo>();
		for(FileInfo info:list){
			MapAll.mapFile.put(info.getId(), info);//????
			MapAll.listFile.add(info);
		}*/
	}
	
	
	//申请入库
	public int saveApplyUser(final ApplyUser af){
		return dao.saveApplyUser(af);
	}
	
	public void updateApplyUser(final int num,final ApplyUser af) throws Exception {
		dao.updateApplyUser(num, af);
	}
	
	//修改  申请流程状态 流程2
	public void updateApplyUserLiuCheng2(final int num,final ApplyUser af) throws Exception {
		dao.updateApplyUserLiuCheng2(num, af);
	}
	
	public void updateApplyUser(final ApplyUser af) throws Exception {
		dao.updateApplyUser(af);
	}
	public void updateApplyUser_FTP(final String ftpUserName,final String ftpPwd,final String applyId) throws Exception {
		dao.updateApplyUser_FTP(ftpUserName, ftpPwd, applyId);
	}
	//获得申请信息
	public List<ApplyUser> queryApplyUser(final int userId,final ApplyUser af){
		return dao.queryApplyUser(userId, af);
	}
	
	public String queryApplyLiuChengByApplyId(final String applyId) {
		if(null==applyId || "".equals(applyId)){
			return null;
		}else{
			return dao.queryApplyLiuChengByApplyId(applyId);
		}
		
	}
	
	public void insert_Apply_User_Files(int fileId, int applyId,final String datetype)throws Exception {
		dao.insert_Apply_User_Files(fileId, applyId, datetype);
	}
	/**
	 * 申请  文件列表 批量入库**************************************************************************************************
	 */
	public void insert_Apply_User_Files(final List<FileInfo> fileList,final int applyId,final String datetype) {
		dao.insert_Apply_User_Files(fileList, applyId, datetype);
	}
	public void insert_Apply_User_Zone(String zoneCode, int applyId)throws Exception {
		dao.insert_Apply_User_Zone(zoneCode, applyId);
	}
	
	public List<Apply_User_Files>  query_Apply_User_FilesList(String applyId){
		return dao.query_Apply_User_FilesList(applyId);
	}
	
	//根据 申请条件  开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	public List<FileInfo>  getApplyFileByApplyId(String applyId,String siteNumberArray,String yearNumber){
		return dao.getApplyFileByApplyId(applyId, siteNumberArray, yearNumber);
	}
	
	//数据申请第二步  查询
	public List<FileInfo>  getShowFileByDay(String applyId,String siteNumberArray,String zoneArray,String siteType,String fileYear){
		return dao.getShowFileByDay(applyId, siteNumberArray, zoneArray, siteType, fileYear);
	}
	
	public List<Apply_User_SiteInfo>  query_Apply_User_SiteInfoList(String applyId){
		return dao.query_Apply_User_SiteInfoList(applyId);
	}
	
	public List<Apply_User_Zone>  query_Apply_User_ZoneList(String applyId){
		return dao.query_Apply_User_ZoneList(applyId);
	}
	
	public void insert_Apply_User_Req(final Apply_User_Req aur) throws Exception{
		dao.insert_Apply_User_Req(aur);
	}
	
	public void update_Apply_User_Req(final Apply_User_Req aur) throws Exception {
		dao.update_Apply_User_Req(aur);
	}
	
	public List<Apply_User_Req> query_Apply_User_ReqList(String applyId){
		return dao.query_Apply_User_ReqList(applyId);
	}
	
	public List<FileInfo>  getFileInfoList(String fileYear,String fileDayYear,String site_number,String type,String fileFlag){
		return dao.getFileInfoList(fileYear, fileDayYear, site_number, type, fileFlag);
	}
	
	public List<FileInfo>  getApplyFileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year){
		return dao.getApplyFileInfoList(startTime, endTime, fileYear, fileDayYear, siteNumberArray,  smallLat, bigLat, smallLon, bigLon,type, fileFlag,isYear, year);
	}
	
	//数据申请第二步   申请数据
	public List<FileInfo>  getApplyFileByDay(String startTime,String endTime,String fileDayYear,String siteNumberArray,String zoneArray, String siteLat1,String siteLat2,String siteLon1,String siteLon2,String siteType,String fileType,String fileYear){
		return dao.getApplyFileByDay( startTime, endTime,fileDayYear, siteNumberArray, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, siteType, fileType, fileYear);
	}
	
	public List<FileInfo>  excelApplyFileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year){
		return dao.excelApplyFileInfoList(startTime, endTime, fileYear, fileDayYear, siteNumberArray,  smallLat, bigLat, smallLon, bigLon,type, fileFlag,isYear, year);
	}
	
	
	public List<FileInfo>  getYearDayileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,boolean isYear,String year){
		return dao.getYearDayileInfoList(startTime, endTime, fileYear, fileDayYear, siteNumberArray,  smallLat, bigLat, smallLon, bigLon,type, fileFlag,isYear, year);
	}
	
	//根据申请id，删掉所有数据
	public void deleteUserApply(final String applyId) throws Exception{
		dao.deleteUserApply(applyId);
	}
	
	public void deleteApplyFile(final String applyId,final String type) throws Exception {
		dao.deleteApplyFile(applyId, type);
	}

	public int  queryCount_ApplyFile(String applyId,String datetype) throws Exception{
		return dao.queryCount_ApplyFile(applyId, datetype);
	}

	
	//申请文件列表
	public int queryCount_Apply_User_SiteInfoList(String applyId,String datetype)throws Exception{
		return dao.queryCount_Apply_User_SiteInfoList(applyId, datetype);
	}
	public void deleteUserInfo(final String applyId,String datetype) throws Exception {
		dao.deleteUserInfo(applyId,datetype);
	}
	public void insert_Apply_User_SiteInfo(String siteNumber, String applyId,String datetype)throws Exception {
	   dao.insert_Apply_User_SiteInfo(siteNumber, applyId, datetype);
	}
	public void insert_Apply_User_SiteInfo(final List<SiteInfo> siteList,final int applyId,final String datetype) {
		dao.insert_Apply_User_SiteInfo(siteList, applyId, datetype);
	}
	
	public ApplyUser getApplyStartAndEnd(final int applyId) {
		List<ApplyUser> getApplyStartAndEnd = dao.getApplyStartAndEnd(applyId);
		if(null!=getApplyStartAndEnd && getApplyStartAndEnd.size()>0){
			return getApplyStartAndEnd.get(0);
		}
		return null;
	}
	
	//地震应急事件   单个详细信息
	public EarthQuake  earthQuake_detail(String id){
		return dao.earthQuake_detail(id);
	}
	//地震应急事件  列表
	public List<EarthQuake>  query_EarthQuake_List(String keyWords,boolean dataShowFlag,String startTime,String endTime,String year){
		return dao.query_EarthQuake_List(keyWords,dataShowFlag,startTime,endTime, year);
	}
	//地震应急事件   日志目录查看
	public List<FileInfo>  query_EarthQuake_Data(String earthQuakeId,String year,String zoneArray,String departmentArray,String siteNumberArray){
		return dao.query_EarthQuake_Data( earthQuakeId, year, zoneArray, departmentArray, siteNumberArray);
	}
	
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String earthQuakeId,String year){
		List<EarthQuakeConfig> earthQuakeConfig = siteDao.earthQuakeConfigQuery();
		EarthQuakeConfig earthConfig = earthQuakeConfig.get(0);
		return dao.earthQuakeMap(earthQuakeId, year,earthConfig);
	}
	
	//地震应急事件   日志目录 整理
	public Map<String, Map<String,FileInfoDirect>> query_EarthQuake_Data_Json(String siteNumberArray,List<FileInfo> earthQuakeFileList,String earthQuakeId,String year){
		
		Map<String, Map<String,FileInfoDirect>> resultMap = new TreeMap<String, Map<String,FileInfoDirect>>();
		
		//for(int i=0;i<siteInfoList.size();i++){
			//SiteInfo siteInfo = siteInfoList.get(i);//选中的台站
			for(int j=0;j<earthQuakeFileList.size();j++){
				FileInfo fileInfo = earthQuakeFileList.get(j);//整理的文件列表
				//if(!siteInfo.getSiteNumber().equalsIgnoreCase(fileInfo.getSiteNumber())){continue;}//如果 台站 文件 一致时继续
				FileInfoDirect fd = new FileInfoDirect();
				fd.setFileFlag(fileInfo.getFileFlag());
				if(fileInfo.getEarthQuake50Or1().indexOf("1")>-1){
					if(fileInfo.getEarthQuakeDay()==1){//前一天
						if(null==resultMap.get(fileInfo.getSiteName()+"_1")){
							resultMap.put(fileInfo.getSiteName()+"_1", ZoneSite.putFileMap(96));
						}
						resultMap.get(fileInfo.getSiteName()+"_1").put(fileInfo.getEarthQuakeNum()+"",fd);               //System.out.println(fileInfo.getSiteName()+"  第"+1+"天   第号"+fileInfo.getEarthQuakeNum()+" "+fd.getFileFlag());
					}else if(fileInfo.getEarthQuakeDay()==2){//当天
						if(null==resultMap.get(fileInfo.getSiteName()+"_2")){
							resultMap.put(fileInfo.getSiteName()+"_2", ZoneSite.putFileMap(96));
						}
						resultMap.get(fileInfo.getSiteName()+"_2").put(fileInfo.getEarthQuakeNum()+"",fd);               //System.out.println(fileInfo.getSiteName()+"  第"+2+"天   第号"+fileInfo.getEarthQuakeNum()+" "+fd.getFileFlag());
					}else if(fileInfo.getEarthQuakeDay()==3){//后一天
						if(null==resultMap.get(fileInfo.getSiteName()+"_3")){
							resultMap.put(fileInfo.getSiteName()+"_3", ZoneSite.putFileMap(96));
						}
						resultMap.get(fileInfo.getSiteName()+"_3").put(fileInfo.getEarthQuakeNum()+"",fd);               //System.out.println(fileInfo.getSiteName()+"  第"+3+"天   第号"+fileInfo.getEarthQuakeNum()+" "+fd.getFileFlag());
					}
					
				}else if(fileInfo.getEarthQuake50Or1().indexOf("50")>-1){
					if(null==resultMap.get(fileInfo.getSiteName()+"_50")){
						resultMap.put(fileInfo.getSiteName()+"_50",ZoneSite.putFileMap(12));
					}
					resultMap.get(fileInfo.getSiteName()+"_50").put(fileInfo.getEarthQuakeNum()+"",fd);                 //System.out.println(fileInfo.getSiteName()+"  第"+50+"天   第号"+fileInfo.getEarthQuakeNum()+" "+fd.getFileFlag());
				}
				
			}
		//}
	    return resultMap;
	}
	
	//地震应急事件   日志目录 整理
	public String query_EarthQuake_Data_Json(Map<String, Map<String,FileInfoDirect>> fileListTo50Or1){
		StringBuffer sb = new StringBuffer("");
		 
		sb.append("{");
		for (String key : fileListTo50Or1.keySet()) {
			   sb.append("\""+key+"\":");
			   Map<String, FileInfoDirect> map = fileListTo50Or1.get(key);
			   sb.append(Json.getJsonArrayToStr(JSONArray.fromObject(map)));
			   sb.append(",");
		 }
		 sb.deleteCharAt(sb.length()-1);
		 sb.append("}");
		 return sb.toString();
	}
	
	/**
	 * 30秒日常整理*************************************************************************************************
	 */
	public void insert_30SData_Files(final String year,final List<FileInfo> fileList){
		dao.insert_30SData_Files(year, fileList);
	}
	
	//通过文件名称 查询文件是否存在
	public FileInfo  select_30SData_Files(final String year,final FileInfo fileInfo){
		return dao.select_30SData_Files(year, fileInfo);
	}
	
	/**
	 * 地震应急数据整理*************************************************************************************************
	 */
	public void insert_EarhtData_Files(final String year,final List<FileInfo> fileList){
		dao.insert_EarhtData_Files(year, fileList);
	}

	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public void update_30S_apply(final String year,final FileInfo fileInfo){
		dao.update_30S_apply(year, fileInfo);
	}
	
	//根据  台站名称、年份、数据类型   判断是否有缺失的数据  （数据定时整理用到的）
	public boolean  queryHistoryFileInfo(String year,String fileDayYear,String siteNumber,String type,String earthQuakeId,String fileFlagSql){
		List<FileInfo> queryFileInfoZZ = dao.queryHistoryFileInfo(year, fileDayYear, siteNumber, type, earthQuakeId, fileFlagSql);
		if(null!=queryFileInfoZZ && queryFileInfoZZ.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
}
