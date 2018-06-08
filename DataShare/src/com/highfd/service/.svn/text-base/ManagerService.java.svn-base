package com.highfd.service;

import java.util.List;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeApply;
import com.highfd.bean.FileInfo;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.common.PageInfo;

public interface ManagerService {
	
	//添加地震应急事件
	public int addEarthQuake(final EarthQuake earthQuake) throws Exception;
	
	//查询 未执行的地震应急事件
	public EarthQuake queryEarthQuakeByRun(String iniSql) throws Exception;
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeType(final String iniType ,final String id) throws Exception;
	
	public int getNextId(final String seq) throws Exception;
	
	/**
	 * 添加应急事情  申请
	 */
	public void insertEarthQuakeApply(final EarthQuakeApply info) throws Exception;
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeApplyType(final int id ,final int type, final String remark, final int days) throws Exception;
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	public void updateEarthQuakeApplyType(final int id ,final int type) throws Exception;
	
	//应急数据申请   列表
	public List<EarthQuakeApply>  queryEarthQuakeApplyList(String id,String userId,String earthQuakeId,String type,boolean noCheckFlag, String txtParam);
	
	/**
	 *某用户  某个指定的应急事件，最后的有效事件是
	 */
	public String  applyEndTime(String userId,String earthQuakeId,String type);
	
	/**
	 * 地震应急事件  申请过期
	 */
	public void updateEarthQuakeApplyOverdue() throws Exception;
	
	/**
	 * 30S数据  申请过期
	 */
	public void update30SApplyOverdue(String dayLangth) throws Exception;
	
	/**
	 *某用户  某个指定的应急事件，  有未审核的单子也不行
	 */
	public String queryNoApply(String userId,String earthQuakeId,String type);
	
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String earthQuakeId,String userName,String iniParam,PageInfo pageinfo);
	/**
	 * 查询地震应急事件全部数据进行导出
	 */
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String earthQuakeId,String userName,String iniParam);
	
	//查询地震应急事件中的某一条  数据是否存在
	public FileInfo queryEarthQuakeOneDetail(String year,FileInfo info) throws Exception;
	
	//修改地震应急事件中的某一条  数据是否存在
	public void updateEarthQuakeOneDetail(String year,FileInfo info) throws Exception;
	
	//地震应急在线整理  获取台站
	public String earthQuakeAupplyGetSiteNumberStr(String zoneArray,String departmentArray,String siteNumberArray);
}
