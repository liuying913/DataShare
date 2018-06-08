package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.FTPApplyYear;
import com.highfd.bean.FileInfo;
import com.highfd.bean.UserInfo;
import com.highfd.dao.FtpDao;
import com.highfd.dao.UserDAO;

@Resource
public class FtpDaoImpl implements FtpDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserDAO userDao;
	
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 网页FTP查看时，获取该用户的文件列表
	 */
	public List<FileInfo> queryAllFilesByUserName(String userName,String filePath){
		
		try {
			UserInfo userInfo = userDao.queryUserInfo(userName);
			if(null!=userInfo){
				return queryFtpApplyYear(userInfo.getUserId()+"", "5", "5",filePath);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过用户id 查询改用户申请的30S数据id 和 应急数据ID
	 */
	public List<FileInfo>  queryFtpApplyYear(String userId,String type30S,String typeEarthQuake,String filePath) throws Exception{
		//获得申请的应急数据 和 常规数据的  申请id 和年份
		List<FTPApplyYear> queryFtpApplyYear = queryFtpApplyYear(userId, type30S, typeEarthQuake);
		if(null==queryFtpApplyYear || queryFtpApplyYear.size()==0){
			return null;
		}
		return applyFileList30SAndEarthQuake(queryFtpApplyYear,filePath);
		
	}
	
	/**
	 * 通过用户id 查询改用户申请的30S数据id 和 应急数据ID
	 */
	public List<FTPApplyYear>  queryFtpApplyYear(String userId,String type30S,String typeEarthQuake){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.earthquake_id as id,e.year as year, 'earthQuake' as types from " +
				"EARTHQUAKE_APPLY a,EARTHQUAKE e where a.earthquake_id=e.id and a.type="+typeEarthQuake+" and a.user_id="+userId +
				"union select u.id as id,u.year as year ,'30s'as types from APPLY_USER u where u.liucheng="+type30S+" and u.userid="+userId );
		@SuppressWarnings({ "unchecked"})
		List<FTPApplyYear> ftpApplyYearList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FTPApplyYear info = new FTPApplyYear();
				info.setApplyId(rs.getInt("id"));
				info.setYear(rs.getString("year"));
				info.setType(rs.getString("types"));
				return info;
		   }
		});
		return ftpApplyYearList;
	}
	
	
	/**
	 * **************查询用户的   ftp权限文件******************************************************
	 */
	public List<FileInfo> applyFileList30SAndEarthQuake(List<FTPApplyYear> list,String filePath ) throws Exception {
		
		//根据获得的id进行查询
		//select f.filename,f.filepath from FILE_INFO_2016 f,APPLY_USER_FILES a where a.fileid=f.id and a.apply_id=160
		//union
		//select f.filename,f.filepath from EARTHQUAKE e,FILE_INFO_2017 f where e.id=f.earthquakeid and e.id=134;
		
		
		//全部查询（不通过参数）
		//select f.id, f.filename,f.filepath from FILE_INFO_2017 f , APPLY_USER u,APPLY_USER_FILES a where a.fileid=f.id and u.id=a.apply_id and u.userid=8 and u.liucheng=5 
		//select e.year,f.filename,f.filepath from EARTHQUAKE_APPLY t,EARTHQUAKE e,FILE_INFO_2017 f where e.id=f.earthquakeid and t.user_id=8 and t.earthquake_id=e.id and t.type=0 and e.id=134
		
		StringBuffer sql = new StringBuffer();
		if(null!=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				FTPApplyYear ftpApplyYear = list.get(i);
				if(ftpApplyYear.getType().indexOf("30s")>-1){
					sql.append(" select f.filename,f.filepath from FILE_INFO_"+ftpApplyYear.getYear()+" f,APPLY_USER_FILES a where a.fileid=f.id and a.apply_id="+ftpApplyYear.getApplyId() +" and f.filepath like '"+filePath+"%'");
				}else if(ftpApplyYear.getType().indexOf("earthQuake")>-1){
					sql.append(" select f.filename,f.filepath from EARTHQUAKE e,FILE_INFO_"+ftpApplyYear.getYear()+" f where e.id=f.earthquakeid and e.id="+ftpApplyYear.getApplyId() +" and f.filepath like '"+filePath+"%'");
				}
				sql.append(" union");
			}
			sql.delete(sql.length()-5, sql.length());
		}
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> ftpApplyYearList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
				info.setFileName(rs.getString("fileName"));
				info.setFilePath(rs.getString("filePath"));
				return info;
		   }
		});
		return ftpApplyYearList;
	}
	
	
	
	/**
	 * Ftp下载文件入库
	 */
	@SuppressWarnings("unchecked")
	public void insertDictionary(final DownHistoryInfo info) {
		String year = "2017";
		/*
		String ftpFnameSub = info.getFtpUserName().substring(0, info.getFtpUserName().indexOf("_"));
		System.out.println(ftpFnameSub +"  "+ftpFnameSub.length());
		if(ftpFnameSub.length()>10){
			year = TimeUtils.getSysYear();
		}else {
			year = FileTool.getYearByZ(info.getFileName());
		}*/
		String sql = "INSERT INTO downHistory_info_"+year+" (id,username,fileInfoId,downTime,fileName)VALUES(downHistory_info_SEQ.nextval,?,?,?,?)";
		
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {
				stmt.setString(1, info.getUserName());
				stmt.setInt(2, info.getFileInfoId());
				stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				stmt.setString(4, info.getFileName());
				stmt.execute();
				return null;
			}
		});
	}
}
