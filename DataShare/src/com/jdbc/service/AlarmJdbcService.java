package com.jdbc.service;

import java.sql.Connection;
import java.util.List;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.model.AlarmInfo;

public interface AlarmJdbcService {
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public boolean update_30S_apply(final String year,final FileInfo fileInfo);
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay,String year);
	
	
	//插入数据目录
	public boolean insert_30SData_Files(final String year,final FileInfo info);
	
	/**
	 * 获得历元数量 数据库中历史记录的
	 */
	public List<FileInfo> getEphemNumber(String year, FileInfo info) throws Exception;
	
    public String selectEventStartTime(String number,String type,Connection conn) throws Exception;

	public boolean insertEventInfo(final AlarmInfo eventInfo,Connection conn) throws Exception;
	
	public boolean updateEventInfoEndTime(AlarmInfo eventInfo,Connection conn) throws Exception;
	
	public int getNextSiteInfoEventId(Connection conn) throws Exception;
	
	
	//插入地震应急数据事件
	public boolean insert_EarthQuakeEvent(final EarthQuake eq);
	
	//查询 应急数据事件列表
	public List<EarthQuake>  queryEarthEvent();
	
}
