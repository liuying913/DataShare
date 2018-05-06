package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FTPApplyYear;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.model.AlarmInfo;
import com.jdbc.common.DBUtil;
import com.jdbc.dao.AlarmJdbcDao;

public class AlarmJdbcDaoImpl implements AlarmJdbcDao{
	

	
	//通过  ftp用户名进行查询 权限文件
	public List<FileInfo> applyFileList30SAndEarthQuake(List<FTPApplyYear> list,Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//根据获得的id进行查询
		//select f.filename,f.filepath from FILE_INFO_2016 f,APPLY_USER_FILES a where a.fileid=f.id and a.apply_id=160
		//union
		//select f.filename,f.filepath from EARTHQUAKE e,FILE_INFO_2017 f where e.id=f.earthquakeid and e.id=134;
		
		
		//全部查询（不通过参数）
		//select f.id, f.filename,f.filepath from FILE_INFO_2017 f , APPLY_USER u,APPLY_USER_FILES a where a.fileid=f.id and u.id=a.apply_id and u.userid=8 and u.liucheng=5 
		//select e.year,f.filename,f.filepath from EARTHQUAKE_APPLY t,EARTHQUAKE e,FILE_INFO_2017 f where e.id=f.earthquakeid and t.user_id=8 and t.earthquake_id=e.id and t.type=0 and e.id=134
		
		List<FileInfo> infoList = new ArrayList<FileInfo>();
		try {
			StringBuffer sql = new StringBuffer();
			if(null!=list && list.size()>0){
				for(int i=0;i<list.size();i++){
					FTPApplyYear ftpApplyYear = list.get(i);
					if(ftpApplyYear.getType().indexOf("30s")>-1){
						sql.append(" select f.filename,f.filepath from FILE_INFO_"+ftpApplyYear.getYear()+" f,APPLY_USER_FILES a where a.fileid=f.id and a.apply_id="+ftpApplyYear.getApplyId());
					}else if(ftpApplyYear.getType().indexOf("earthQuake")>-1){
						sql.append(" select f.filename,f.filepath from EARTHQUAKE e,FILE_INFO_"+ftpApplyYear.getYear()+" f where e.id=f.earthquakeid and e.id="+ftpApplyYear.getApplyId());
					}
					sql.append(" union");
				}
				sql.delete(sql.length()-5, sql.length());
			}
			stmt = conn.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				FileInfo info = new FileInfo();
				info.setFileName(rs.getString("fileName"));
				info.setFilePath(rs.getString("filePath"));
				infoList.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rs);
			DBUtil.closePsmt(stmt);
			DBUtil.closeConn(conn);
			
		}
		return infoList;
	}
	
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public boolean update_30S_apply(final String year,final FileInfo fileInfo,Connection conn) {
		PreparedStatement stmt = null;
		boolean flag = false;
		try {
			String sql = "update file_info_"+year+" set ephem_number=?, fileflag=?, MP1=?,MP2=?,O_SLPS=?, FILESIZE=? where site_number=? and filedayyear = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, fileInfo.getEphem_number());
			stmt.setInt(2, fileInfo.getFileFlag());
			stmt.setString(3, fileInfo.getMp1());
			stmt.setString(4, fileInfo.getMp2());
			stmt.setString(5, fileInfo.getO_slps());
			
			stmt.setDouble(6, fileInfo.getFileSize());
			stmt.setString(7, fileInfo.getSiteNumber());
			stmt.setString(8, fileInfo.getFileDayYear());
			flag = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closePsmt(stmt);
			DBUtil.closeConn(conn);
		}
		return flag;
	}
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay,String year, Connection conn){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<SiteInfo> infoList = new ArrayList<SiteInfo>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from SITE_INFO t where t.site_type=1 and t.site_number in (select t.site_number from FILE_INFO_"+year+" t where t.filedayyear="+yearDay+" and t.fileflag not in(1,2)) ");

			stmt = conn.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	//info.setZoneCode(rs.getString("zone_code"));
		    	//info.setZoneName(rs.getString("zone_name"));
		    	//info.setDepartmentCode(rs.getString("dic_en_name"));
		    	//info.setDepartmentName(rs.getString("dic_cn_name"));
		    	
		    	//info.setSiteUnit(rs.getString("site_unit"));
		    	//info.setSiteProgect(rs.getString("site_progect"));
		    	//info.setSiteOldNumber(rs.getString("site_old_number"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	info.setSiteType(rs.getString("site_type"));
		    	
				infoList.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rs);
			DBUtil.closePsmt(stmt);
			DBUtil.closeConn(conn);
		}
		return infoList;
	}
	
	/**
	 * 获得历元数量 数据库中历史记录的
	 */
	public List<FileInfo> getEphemNumber(String year, FileInfo info, Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<FileInfo> infoList = new ArrayList<FileInfo>();
		try {
			String sql = "select ephem_number from file_info_"+year+" where site_number=? and filedayyear = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, info.getSiteNumber());
			stmt.setString(2, info.getFileDayYear());
			rs = stmt.executeQuery();
			while (rs.next()) {
				FileInfo info2 = new FileInfo();
				info2.setEphem_number(rs.getString("ephem_number"));
				infoList.add(info2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeRs(rs);
			DBUtil.closePsmt(stmt);
			DBUtil.closeConn(conn);
		}
		return infoList;
	}
	
	
	//插入数据目录
	public boolean insert_30SData_Files(final String year,final FileInfo info,Connection conn) {
		boolean flag = false;
		PreparedStatement prapStmt = null;
		try {
			
			String sql = "INSERT INTO file_info_"+year+" (id,fileYear,fileDayYear,site_number,fileName,filePath,fileSize,fileflag," +
					"systemTime,fileCreateTime,   type,ephem_number,    mp1,mp2,o_slps,EARTHQUAKEID) VALUES(file_info_SEQ.nextval,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),  ?,?,?,  ?,?,?,?)";
			prapStmt = conn.prepareStatement(sql);
			prapStmt.setString(1, info.getFileYear());
			prapStmt.setString(2, info.getFileDayYear());
			prapStmt.setString(3, info.getSiteNumber());
			prapStmt.setString(4, info.getFileName());
			prapStmt.setString(5, info.getFilePath());
			prapStmt.setDouble(6, info.getFileSize());
			prapStmt.setInt(7, info.getFileFlag());
			prapStmt.setString(8, info.getSystemStr());
			
			prapStmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			prapStmt.setString(10, info.getType());
			prapStmt.setString(11, info.getEphem_number());
			
			prapStmt.setString(12, info.getMp1());
			prapStmt.setString(13, info.getMp2());
			prapStmt.setString(14, info.getO_slps());
			prapStmt.setInt(15, info.getEarthQuakeId());
			flag = prapStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.closePsmt(prapStmt);
			DBUtil.closeConn(conn);
		}
		return flag;
	}
	
	
	//**********************报警的****************************************************************************8
		public String selectEventStartTime(String number,String type,Connection conn) throws Exception {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select t.starttime from site_info_event t where t.endtime is null and t.sitenumber = ? and t.eventtype = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, number);
				stmt.setString(2, type);
				rs = stmt.executeQuery();
				while (rs.next()) {
					return rs.getString("starttime");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closeRs(rs);
				DBUtil.closePsmt(stmt);
				DBUtil.closeConn(conn);
			}
			return "";
		}
		
		//获得事件表的 序列id
		public int getNextSiteInfoEventId(Connection conn) throws Exception {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select site_info_event_SEQ.nextval as isd from dual";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closeRs(rs);
				DBUtil.closePsmt(stmt);
				DBUtil.closeConn(conn);
			}
			return -1;
		}
		
		
		public boolean insertEventInfo(final AlarmInfo ai,Connection conn) throws Exception {
			boolean flag = false;
			PreparedStatement stmt = null;
			try {
				String sql = "INSERT INTO site_info_event (id,sitenumber,deviceid,eventtype,starttime) VALUES(?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, ai.getId());
				stmt.setString(2, ai.getSiteNumber());
				stmt.setString(3, "");
				stmt.setString(4, ai.getAlarmId());
				stmt.setTimestamp(5, new java.sql.Timestamp(ai.getStarttime()));
				flag = stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
				DBUtil.closeConn(conn);
			}
			return flag;
		}
		
		public boolean updateEventInfoEndTime(final AlarmInfo eventInfo,Connection conn) throws Exception{
			PreparedStatement stmt = null;
			boolean flag = false;
			try {
				String sql = "UPDATE site_info_event set endtime=? WHERE sitenumber=? AND eventtype=? AND endtime is null ";
				stmt = conn.prepareStatement(sql);
				stmt.setTimestamp(1, new java.sql.Timestamp(eventInfo.getEndtime()));
				stmt.setString(2, eventInfo.getSiteNumber());
				stmt.setString(3, eventInfo.getAlarmId());
				flag = stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
				DBUtil.closeConn(conn);
			}
			return flag;
		}
		
		
		
		
		//插入地震应急数据事件
		public boolean insert_EarthQuakeEvent(final EarthQuake eq,Connection conn) {
			boolean flag = false;
			PreparedStatement prapStmt = null;
			try {
				String sql = "INSERT into EARTHQUAKE (id,NAME,year,CREATETIME,SITE_LAT,SITE_LON,GRADE,ADDRESS," +
						"HEIGHT,initype) VALUES(EARTHQUAKE_SEQ.nextval,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,  ?,?,?)";
				prapStmt = conn.prepareStatement(sql);
				prapStmt.setString(1, eq.getName());
				prapStmt.setString(2, eq.getYear());
				prapStmt.setString(3, eq.getStrTime());
				prapStmt.setString(4, eq.getSiteLat());
				prapStmt.setString(5, eq.getSiteLon());
				prapStmt.setString(6, eq.getGrade());
				prapStmt.setString(7, eq.getAddress());
				prapStmt.setString(8, eq.getHeight());
				
				prapStmt.setString(9, "-1");
				flag = prapStmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(prapStmt);
				DBUtil.closeConn(conn);
			}
			return flag;
		}
		
		//查询 应急数据事件列表
		public List<EarthQuake>  queryEarthEvent(Connection conn){
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<EarthQuake> infoList = new ArrayList<EarthQuake>();
			try {
				StringBuffer sql = new StringBuffer();
				sql.append("select t.id,t.name from EARTHQUAKE t ");
				stmt = conn.prepareStatement(sql.toString());
				rs = stmt.executeQuery();
				while (rs.next()) {
					EarthQuake info = new EarthQuake();
			    	info.setId(Integer.valueOf(rs.getString("id")));
			    	info.setName(rs.getString("name"));
					infoList.add(info);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closeRs(rs);
				DBUtil.closePsmt(stmt);
				DBUtil.closeConn(conn);
			}
			return infoList;
		}
		
		
}
