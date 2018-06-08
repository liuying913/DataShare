package com.highfd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeApply;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.common.PageInfo;
import com.highfd.dao.ManagerDAO;
import com.highfd.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	ManagerDAO dao;
	
	//添加地震应急事件
	public int addEarthQuake(final EarthQuake earthQuake) throws Exception{
		return dao.addEarthQuake(earthQuake);
	}
	
	//查询 未执行的地震应急事件
	public EarthQuake queryEarthQuakeByRun(String iniSql) throws Exception {
		return dao.queryEarthQuakeByRun(iniSql);
	}
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeType(final String iniType ,final String id) throws Exception {
		dao.updateEarthQuakeType(iniType, id);
	}
	
	
	public int getNextId(final String seq) throws Exception {
		return dao.getNextId(seq);
	}
	
	/**
	 * 添加应急事情  申请
	 */
	public void insertEarthQuakeApply(final EarthQuakeApply info) throws Exception {
		dao.insertEarthQuakeApply(info);
	}

	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeApplyType(final int id ,final int type, final String remark, final int days) throws Exception {
		dao.updateEarthQuakeApplyType(id, type, remark, days);
	}
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeApplyType(final int id ,final int type) throws Exception{
		dao.updateEarthQuakeApplyType(id, type);
	}
	
	//应急数据申请   列表
	public List<EarthQuakeApply>  queryEarthQuakeApplyList(String id,String userId,String earthQuakeId,String type,boolean noCheckFlag, String txtParam){
		return dao.queryEarthQuakeApplyList(id, userId, earthQuakeId, type,noCheckFlag,  txtParam);
	}
	
	/**
	 *某用户  某个指定的应急事件，最后的有效事件是
	 */
	public String  applyEndTime(String userId,String earthQuakeId,String type){
		return dao.applyEndTime(userId, earthQuakeId, type);
	}
	
	/**
	 * 地震应急事件  申请过期
	 */
	public void updateEarthQuakeApplyOverdue() throws Exception {
		dao.updateEarthQuakeApplyOverdue();
	}
	
	/**
	 * 30S数据  申请过期
	 */
	public void update30SApplyOverdue(String dayLangth) throws Exception {
		dao.update30SApplyOverdue(dayLangth);
	}
	
	/**
	 *某用户  某个指定的应急事件，  有未审核的单子也不行
	 */
	public String queryNoApply(String userId,String earthQuakeId,String type){
		return dao.queryNoApply(userId, earthQuakeId, type);
	}
	
	
	
	//用户下载的  地震应急事件 数据文件列表
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String earthQuakeId,String userName,String iniParam,PageInfo pageinfo){
		return dao.getUserDownHistoryList(year, earthQuakeId, userName, iniParam, pageinfo);
	}
	
	//用户下载的  地震应急事件 数据文件列表
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String earthQuakeId,String userName,String iniParam){
		return dao.getUserDownHistoryListAll(year, earthQuakeId, userName, iniParam);
	}
	
	//查询地震应急事件中的某一条  数据是否存在
	public FileInfo queryEarthQuakeOneDetail(String year,FileInfo info) throws Exception {
		return dao.queryEarthQuakeOneDetail(year, info);
	}
	
	//修改地震应急事件中的某一条  数据是否存在
	public void updateEarthQuakeOneDetail(String year,FileInfo info) throws Exception {
		dao.updateEarthQuakeOneDetail(year, info);
	}
	
	//地震应急在线整理  获取台站
	public String earthQuakeAupplyGetSiteNumberStr(String zoneArray,String departmentArray,String siteNumberArray){
		List<SiteInfo> siteInfoList = dao.earthQuakeAupplyGetSiteNumberStr(zoneArray, departmentArray, siteNumberArray);
		StringBuffer sb = new StringBuffer("");
		for(SiteInfo info:siteInfoList){
			sb.append(info.getSiteNumber()+",");
		}
		return sb.toString();
	}
	
}
