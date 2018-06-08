package com.jdbc.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.map.MapAll;
import com.highfd.model.AlarmInfo;
import com.jdbc.dao.AlarmJdbcDao;
import com.jdbc.dao.impl.AlarmJdbcDaoImpl;
import com.jdbc.service.AlarmJdbcService;

public class AlarmJdbcServiceImpl implements AlarmJdbcService {

	private AlarmJdbcDao alarmJdbcDao = new AlarmJdbcDaoImpl();
	public static Connection getAlarmConnection() {

		Connection conn = null;
		try {
			Class.forName(MapAll.driverClassName);
			conn = DriverManager.getConnection(MapAll.url, MapAll.username,MapAll.password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getAlarmConnection2() {

		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@172.128.8.10:1521:datashare", "datashare","datashare");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "ltwl","ltwl");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) {
		getAlarmConnection2();
	}
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public boolean update_30S_apply(final String year,final FileInfo fileInfo) {
		return alarmJdbcDao.update_30S_apply(year, fileInfo, getAlarmConnection2());
	}
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay,String year4){
		return alarmJdbcDao.getSiteInfoListByFileFlag(yearDay, year4, getAlarmConnection2());
	}
	
	
	
	//插入数据目录
	public boolean insert_30SData_Files(final String year,final FileInfo info) {
		return alarmJdbcDao.insert_30SData_Files(year, info, getAlarmConnection2());
	}
	/**
	 * 获得历元数量 数据库中历史记录的
	 */
	public List<FileInfo> getEphemNumber(String year, FileInfo info) throws Exception {
		return alarmJdbcDao.getEphemNumber(year, info,  getAlarmConnection2());
	}
	
	public boolean updateEventInfoEndTime(AlarmInfo eventInfo,Connection conn) throws Exception{
		return alarmJdbcDao.updateEventInfoEndTime(eventInfo, conn);
	}
	public boolean insertEventInfo(final AlarmInfo eventInfo,Connection conn) throws Exception {
		return alarmJdbcDao.insertEventInfo(eventInfo, conn);
	}
	public String selectEventStartTime(String number,String type,Connection conn) throws Exception {
		return alarmJdbcDao.selectEventStartTime(number,type, conn);
	}
	public int getNextSiteInfoEventId(Connection conn) throws Exception{
		return alarmJdbcDao.getNextSiteInfoEventId(conn);
	}
	

	//插入地震应急数据事件
	public boolean insert_EarthQuakeEvent(final EarthQuake eq) {
		return alarmJdbcDao.insert_EarthQuakeEvent(eq, getAlarmConnection2());
	}
	
	//查询 应急数据事件列表
	public List<EarthQuake>  queryEarthEvent(){
		return alarmJdbcDao.queryEarthEvent(getAlarmConnection2());
	}
	
}
