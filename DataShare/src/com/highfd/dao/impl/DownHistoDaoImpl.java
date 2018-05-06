package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;


import com.highfd.bean.ApplyUser;
import com.highfd.bean.DicInfo;
import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.UpdateDownIdBean;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.PageInfo;
import com.highfd.common.file.FileTool;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.DownHistoryDao;


@Resource
public class DownHistoDaoImpl implements DownHistoryDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	//统计分析  地图 文件下载情况
	public List<FileDownBySiteNumberBean>  fileDownGroupBySiteNumberForMap(String fileType,String startTime,String endTime,String userId){
		StringBuffer sql = new StringBuffer();
		String year = startTime.substring(0, 4);
		
		sql.append("select f.site_number,count(*) as conNum from file_info_"+year+" f,downhistory_info_"+year+" d " +
		"where f.id=d.fileInfoId ");

		if(!userId.equals("all")){
			sql.append(" and d.username='"+MapAll.mapUser.get(Integer.valueOf(userId.trim())).getUserName()+"'");
		}
		
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and f.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		sql.append(" group by f.site_number");
		
		@SuppressWarnings({ "unchecked"})
		List<FileDownBySiteNumberBean> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileDownBySiteNumberBean info = new FileDownBySiteNumberBean();
		    	
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(MapAll.mapSite.get(rs.getString("site_number")).getSiteName());
		    	info.setCouNum(Integer.valueOf(rs.getString("conNum")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//统计分析  表格 文件下载情况
	public List<FileDownBySiteNumberBean>  siteInfoDownGroupForTable(String fileType,String startTime,String endTime,String userId){
		StringBuffer sql = new StringBuffer();
		
		//分组日期有歧义f.fileDayYear
		String year = startTime.substring(0, 4);
		sql.append("select f.site_number,f.systemTime,count(*) as conNum from file_info_"+year+" f,downhistory_info_"+year+" d " +
		"where f.id=d.fileInfoId ");
		
		if(!userId.equals("all")){
			sql.append(" and d.username='"+MapAll.mapUser.get(Integer.valueOf(userId.trim())).getUserName()+"'");
		}
		
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and f.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}

		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		sql.append(" group by f.site_number,f.systemTime");
		@SuppressWarnings({ "unchecked"})
		List<FileDownBySiteNumberBean> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    @SuppressWarnings("deprecation")
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileDownBySiteNumberBean info = new FileDownBySiteNumberBean();
		    	
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(MapAll.mapSite.get(rs.getString("site_number")).getSiteName());
		    	info.setCouNum(Integer.valueOf(rs.getString("conNum")));
		    	info.setSystemMonthYearDay(rs.getTimestamp("systemTime").getDate()+"");
				return info;
		   }
		});
		return eventInfoList;
	}
	
	

	//综合查询 数据下载记录
	public List<FileHistroyDown>  getDownHistoryList(String year,String fileType,String siteParam){
		StringBuffer sql = new StringBuffer();
		/*sql.append("select a.id,a.apply_unit,a.apply_person,a.apply_personId,a.apply_phone,a.appply_email,a.result_name,a.result_money,a.result_person," +
				"a.result_from,a.result_profile,a.response_person,a.response_phone,a.createTime," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName " +
				"from  file_info f ,downhistory_info d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");*/
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_"+year+" f ,downhistory_info_"+year+" d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		
		sql.append(" union all ");
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_2016 f ,downhistory_info_2016 d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		
		sql.append(" union all ");
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_2015 f ,downhistory_info_2015 d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		
		sql.append(" union all ");
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_2014 f ,downhistory_info_2014 d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		
		sql.append(" union all ");
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_2013 f ,downhistory_info_2013 d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		
		sql.append(" union all ");
		sql.append("select a.id,a.apply_unit,a.apply_person," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				"from  file_info_2012 f ,downhistory_info_2012 d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (a.apply_unit like '%"+siteParam+"%' or a.apply_person like '%"+siteParam+"%' or f.fileyear like '%"+siteParam+"%' or f.fileDayyear like '%"+siteParam+"%' or d.FILENAME like '%"+siteParam+"%' or d.FTPUSERNAME like '%"+siteParam+"%' )");
		}
		@SuppressWarnings({ "unchecked"})
		List<FileHistroyDown> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileHistroyDown info = new FileHistroyDown();
		    	info.setId(rs.getString("id"));
		    	info.setApply_unit(rs.getString("apply_unit"));
		    	info.setApply_person(rs.getString("apply_person"));
		    	
		    	info.setFileyear(rs.getString("fileyear"));
		    	info.setFileDayyear(rs.getString("fileDayyear"));
		    	info.setSite_number(rs.getString("site_number"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setDownTimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("downtime")));
		    	
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==3){
		    		info.setFileComp("缺失");
		    	}else{
		    		info.setFileComp("测试用");
		    	}
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//综合查询 地震应急数据下载记录
	public List<FileHistroyDown>  earthQuakeDownHistoryList(String fileType,String siteParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select e.name,e.createTime,e.grade,f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime,f.fileFlag " +
				" from file_info f,earthquake e,downhistory_info d where e.id=f.earthQuakeId and d.fileInfoId=f.id ");
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		@SuppressWarnings({ "unchecked"})
		List<FileHistroyDown> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileHistroyDown info = new FileHistroyDown();
		    	
		    	info.setName(rs.getString("name"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("createTime")));
		    	info.setGrade(rs.getString("grade"));
		    	
		    	info.setFileyear(rs.getString("fileyear"));
		    	info.setFileDayyear(rs.getString("fileDayyear"));
		    	info.setSite_number(rs.getString("site_number"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setDownTimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("downtime")));
		    	
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==3){
		    		info.setFileComp("缺失");
		    	}else{
		    		info.setFileComp("测试用");
		    	}
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//综合用户查询 数据下载记录
	@SuppressWarnings("unchecked")
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String applyId,String fileType,String selectParam,PageInfo pageinfo){
		pageinfo.setRecordCount(getUserDownHistoryListCount(year,applyId,fileType,selectParam));
		pageinfo.setPageCount(pageinfo.getRecordCount()%pageinfo.getPageSize()==0?pageinfo.getRecordCount()/pageinfo.getPageSize():pageinfo.getRecordCount()/pageinfo.getPageSize()+1);
		StringBuffer sql = new StringBuffer();
		/*sql.append("select a.id,a.apply_unit,a.apply_person,a.apply_personId,a.apply_phone,a.appply_email,a.result_name,a.result_money,a.result_person," +
				"a.result_from,a.result_profile,a.response_person,a.response_phone,a.createTime," +
				"f.fileyear,f.fileDayyear,f.site_number,f.fileName " +
				"from  file_info f ,downhistory_info d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");*/
//		sql.append("select a.id,a.apply_unit,a.apply_person," +
//				"f.fileyear,f.fileDayyear,f.site_number,f.fileName,d.downtime ,f.fileFlag  " +
//				"from  file_info_"+year+" f ,downhistory_info_"+year+" d,apply_user a where a.id=d.applyId and d.fileInfoId=f.id");
		//新增分页查询
		sql.append(" select b.id,b.apply_unit,b.apply_person,a.fileyear,a.filedayyear,a.site_number,a.filename,c.downtime,a.fileflag from file_info_"+year+" a, apply_user b," +
				"( select tt.apply_id,tt.fileid,tt.downtime from " +
				"(select rownum r, t.apply_id,t.fileid,t.downtime from " +
				"(select af.apply_id,af.fileid,d.downtime from downhistory_info_"+year+" d,APPLY_USER_FILES af where af.fileid=d.fileinfoid ");
		if(null!= fileType  && !"".equals(fileType)){
			//sql.append( " and f.type= "+fileType);
		}
		if(null!= applyId  && !"".equals(applyId)){
			sql.append( " and af.apply_id= "+applyId);
		}
		sql.append("  )t ");
		sql.append(" )tt where tt.r between "+((pageinfo.getCurrentPage()-1)*pageinfo.getPageSize() + 1)+" and "+(pageinfo.getCurrentPage()*pageinfo.getPageSize())+"");
		sql.append(" ) c where a.id=c.fileid and b.id=c.apply_id");
		if(null!=selectParam && !"".equals(selectParam)){
			sql.append(" and (b.apply_unit like '%"+selectParam+"%' or b.apply_person  like '%"+selectParam+"%' or a.filedayyear like '%"+selectParam+"%'  or a.site_number like '%"+selectParam+"%') ");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		List<FileHistroyDown> listFileHistoryDown = new ArrayList<FileHistroyDown>();
		if(list!=null) {
			 for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   FileHistroyDown info = new FileHistroyDown();
			    	info.setId(rs.get("id").toString());
			    	info.setApply_unit(rs.get("apply_unit").toString());
			    	info.setApply_person(rs.get("apply_person").toString());
			    	info.setFileyear(rs.get("fileyear").toString());
			    	info.setFileDayyear(rs.get("fileDayyear").toString());
			    	info.setSite_number(rs.get("site_number").toString());
			    	info.setFileName(rs.get("fileName").toString());
			    	info.setDownTimeStr(TimeUtils.TimestampToString2(Timestamp.valueOf(rs.get("downtime").toString())));
			    	if(Integer.valueOf(rs.get("fileflag").toString())==1){
			    		info.setFileComp("完整");
			    	}else if(Integer.valueOf(rs.get("fileflag").toString())==2){
			    		info.setFileComp("补回");
			    	}else{
			    		info.setFileComp("缺失");
			    	}
			    	listFileHistoryDown.add(info);
			    	
			 }
		}
//		List<FileHistroyDown> eventInfoList = jdbcTemplate.query(sql.toString(),new RowMapper(){
//		    public FileHistroyDown mapRow(ResultSet rs, int arg1) throws SQLException {
//		    	FileHistroyDown info = new FileHistroyDown();
//		    	info.setId(rs.getString("id"));
//		    	info.setApply_unit(rs.getString("apply_unit"));
//		    	info.setApply_person(rs.getString("apply_person"));
//		    	
//		    	info.setFileyear(rs.getString("fileyear"));
//		    	info.setFileDayyear(rs.getString("fileDayyear"));
//		    	info.setSite_number(rs.getString("site_number"));
//		    	info.setFileName(rs.getString("fileName"));
//		    	info.setDownTimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("downtime")));
//		    	
//		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
//		    		info.setFileComp("完整");
//		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
//		    		info.setFileComp("补回");
//		    	}else if(Integer.valueOf(rs.getString("fileflag"))==3){
//		    		info.setFileComp("缺失");
//		    	}else{
//		    		info.setFileComp("测试用");
//		    	}
//				return info;
//		   });
		return listFileHistoryDown;
	}

	//综合用户查询 数据下载记录
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String applyId,String fileType,String selectParam){
		StringBuffer sql = new StringBuffer();
		//新增分页查询
		sql.append(" select b.id,b.apply_unit,b.apply_person,a.fileyear,a.filedayyear,a.site_number,a.filename,c.downtime,a.fileflag from file_info_"+year+" a, apply_user b," +
				"(select t.apply_id,t.fileid,t.downtime from " +
				"(select af.apply_id,af.fileid,d.downtime from downhistory_info_"+year+" d,APPLY_USER_FILES af where af.fileid=d.fileinfoid ");
		if(null!= fileType  && !"".equals(fileType)){
			//sql.append( " and f.type= "+fileType);
		}
		if(null!= applyId  && !"".equals(applyId)){
			sql.append( " and af.apply_id= "+applyId);
		}
		sql.append("  )t  ) c where a.id=c.fileid and b.id=c.apply_id");
		if(null!=selectParam && !"".equals(selectParam)){
			sql.append(" and (b.apply_unit like '%"+selectParam+"%' or b.apply_person  like '%"+selectParam+"%' or a.filedayyear like '%"+selectParam+"%'  or a.site_number like '%"+selectParam+"%') ");
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		List<FileHistroyDown> listFileHistoryDown = new ArrayList<FileHistroyDown>();
		if(list!=null) {
			 for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   FileHistroyDown info = new FileHistroyDown();
			    	info.setId(rs.get("id").toString());
			    	info.setApply_unit(rs.get("apply_unit").toString());
			    	info.setApply_person(rs.get("apply_person").toString());
			    	info.setFileyear(rs.get("fileyear").toString());
			    	info.setFileDayyear(rs.get("fileDayyear").toString());
			    	info.setSite_number(rs.get("site_number").toString());
			    	info.setFileName(rs.get("fileName").toString());
			    	info.setDownTimeStr(TimeUtils.TimestampToString2(Timestamp.valueOf(rs.get("downtime").toString())));
			    	if(Integer.valueOf(rs.get("fileflag").toString())==1){
			    		info.setFileComp("完整");
			    	}else if(Integer.valueOf(rs.get("fileflag").toString())==2){
			    		info.setFileComp("补回");
			    	}else{
			    		info.setFileComp("缺失");
			    	}
			    	listFileHistoryDown.add(info);
			    	
			 }
		}
		return listFileHistoryDown;
	}
	
	@SuppressWarnings("deprecation")
	public Integer  getUserDownHistoryListCount(String year, String applyId,String fileType,String selectParam){
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select count(*) from file_info_"+year+" a, apply_user b," +
				"( select tt.apply_id,tt.fileid,tt.downtime from " +
				"(select rownum r, t.apply_id,t.fileid,t.downtime from " +
				"(select af.apply_id,af.fileid,d.downtime from downhistory_info_"+year+" d,APPLY_USER_FILES af where af.fileid=d.fileinfoid ");
		if(null!= fileType  && !"".equals(fileType)){
			//sql.append( " and f.type= "+fileType);
		}
		if(null!= applyId  && !"".equals(applyId)){
			sql.append( " and af.apply_id= "+applyId);
		}
		sql.append("  )t ");
		sql.append(" )tt  ) c where a.id=c.fileid and b.id=c.apply_id");
		if(null!=selectParam && !"".equals(selectParam)){
			sql.append(" and (b.apply_unit like '%"+selectParam+"%' or b.apply_person  like '%"+selectParam+"%' or a.filedayyear like '%"+selectParam+"%'  or a.site_number like '%"+selectParam+"%') ");
		}
		if(null!= fileType  && !"".equals(fileType)){
			//sql.append( " and f.type= "+fileType);
		}
		return jdbcTemplate.queryForInt(sql.toString());
		
	}
	
	/**
	 * 插入下载数据
	 */

	@SuppressWarnings("unchecked")
	public void insertDictionary(final DownHistoryInfo info) throws Exception {
		
		String year = FileTool.getYearByZ(info.getFileName());
		String sql = "INSERT INTO downHistory_info_"+year+" (id,username,fileInfoId,downTime,fileName)VALUES(downHistory_info_SEQ.nextval,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getUserName());
				pstmt.setInt(2, info.getFileInfoId());
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(4, info.getFileName());
				
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	/**
	 * 将 用户下载的文件 修改文件的id
	 */
	public List<UpdateDownIdBean>  downChangeFile(final String year){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select d.id as dId,f.id as fId from DOWNHISTORY_INFO_"+year+" d,file_info_"+year+" f where d.filename=f.filename and d.FILEINFOID=0");
		
		@SuppressWarnings({ "unchecked"})
		List<UpdateDownIdBean> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	UpdateDownIdBean info = new UpdateDownIdBean();
		    	info.setfId(rs.getString("fId"));
		    	info.setdId(rs.getString("dId"));
		    	info.setYear(year);
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	/**
	 * 修改下载文件的 id
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateDownFileId(final UpdateDownIdBean bean) throws Exception {
		String sql = "update DOWNHISTORY_INFO_"+bean.getYear()+" set fileInfoId=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, bean.getfId());
				pstmt.setString(2, bean.getdId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * FTP 上传记录
	 */

	@SuppressWarnings("unchecked")
	public void insertFileInfo(final FileInfo info) throws Exception {
		String sql = "INSERT INTO file_info (fileYear,fileDayYear,site_number,fileName,filePath,fileSize,fileFlag,fileCreateTime,systemTime,type)VALUES(?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getFileYear());
				pstmt.setString(2, info.getFileDayYear());
				pstmt.setString(3, info.getSiteNumber());
				pstmt.setString(4, info.getFileName());
				pstmt.setString(5, info.getFilePath());
				pstmt.setDouble(6, info.getFileSize());
				pstmt.setInt(7, info.getFileFlag());
				pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
				pstmt.setTimestamp(9, info.getSystemTimeStr());
				pstmt.setString(10, info.getType());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	
	//统计分析   省份下载分组统计分析
	public List<GroupByDepPro>  zoneDown(String fileType,String startTime,String endTime,String userId){
		StringBuffer sql = new StringBuffer();
		String year = startTime.substring(0, 4);
		sql.append("select s.site_zone,count(*) conNum from downhistory_info_"+year+" d,file_info_"+year+" f,site_info s " +
		"where d.fileInfoId=f.id and s.site_number=f.site_number ");
		
		if(!userId.equals("all")){
			sql.append(" and d.username='"+MapAll.mapUser.get(Integer.valueOf(userId.trim())).getUserName()+"'");
		}
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and f.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		sql.append(" group by s.site_zone");
		
		@SuppressWarnings({ "unchecked"})
		List<GroupByDepPro> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GroupByDepPro info = new GroupByDepPro();
		    	info.setGroupName(rs.getString("site_zone"));
		    	info.setDownNum(rs.getString("conNum"));
				return info;
		   }
		});
		return eventInfoList;
	}
	//统计分析   部委下载分组统计分析
	public List<GroupByDepPro>  departmentDown(String fileType,String startTime,String endTime,String userId){
		StringBuffer sql = new StringBuffer();
		String year = startTime.substring(0, 4);
		
		
		sql.append("select s.site_department,count(*) conNum from downhistory_info_"+year+" d,file_info_"+year+" f,site_info s " +
		"where d.fileInfoId=f.id and s.site_number=f.site_number ");
		
		if(!userId.equals("all")){
			sql.append(" and d.username='"+MapAll.mapUser.get(Integer.valueOf(userId.trim())).getUserName()+"'");
		}

		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and f.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		sql.append(" group by s.site_department");
		
		@SuppressWarnings({ "unchecked"})
		List<GroupByDepPro> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GroupByDepPro info = new GroupByDepPro();
		    	info.setGroupName(rs.getString("site_department"));
		    	info.setDownNum(rs.getString("conNum"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//统计分析   月度分组统计分析
	public List<GroupByDepPro>  monthDown(String fileType,String startTime,String endTime){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select DATE_FORMAT(downtime,'%Y%m') months,count(*) conNum from downhistory_info d,file_info f,site_info s " +
				"where d.fileInfoId=f.id and s.site_number=f.site_number ");
		
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and f.systemTime between '"+startTime+"' and '"+endTime+"' ");
		}
		
		if(null!= fileType  && !"".equals(fileType)){
			sql.append( " and f.type= "+fileType);
		}
		sql.append(" group by months");
		
		@SuppressWarnings({ "unchecked"})
		List<GroupByDepPro> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GroupByDepPro info = new GroupByDepPro();
		    	info.setGroupName(rs.getString("months"));
		    	info.setDownNum(rs.getString("conNum"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
/**
 * *****************待开发****************************************************************************************************************************
 */
	/**
	 * 更新数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public void updateDictionary(final DicInfo dicInfo) throws Exception {
		String sql = "update downHistory_info set dic_cn_name=?,dic_en_name=?,dic_order=?,dic_type_code=? where dic_code=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, dicInfo.getDicCnName());
				pstmt.setString(2, dicInfo.getDicEnName());
				pstmt.setInt(3, Integer.valueOf(dicInfo.getDicOrder()));
				pstmt.setString(4, dicInfo.getDicTypeCode());
				pstmt.setString(5, dicInfo.getDicCode());
				pstmt.execute();
				return null;
			}
		});
	}

	/**
	 * 获得所有的数据字典信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DicInfo> queryAllDicList()
			throws Exception {
		String sql = "select * from downHistory_info";
		List<DicInfo> dicList = (List<DicInfo>) jdbcTemplate.query(sql,new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						DicInfo dic = new DicInfo();
						dic.setDicCode(rs.getString("dic_code"));
						dic.setDicCnName(rs.getString("dic_cn_name"));
						dic.setDicEnName(rs.getString("dic_en_name"));
						dic.setDicOrder(String.valueOf(rs.getInt("dic_order")));
						dic.setDicTypeCode(rs.getString("dic_type_code"));
						return dic;
					}
				});
		return dicList;
	}

	/**
	 * 获得某一类型的数据字典信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DicInfo> queryDicByType(String dicType) throws Exception {
		String sql = "select * from downHistory_info where dic_type_code=? order by dic_code asc";
		String[] dic_type = new String[] { dicType };
		List<DicInfo> dicList = jdbcTemplate.query(sql, dic_type,
				new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						DicInfo dic = new DicInfo();
						dic.setDicCode(rs.getString("dic_code"));
						dic.setDicCnName(rs.getString("dic_cn_name"));
						dic.setDicEnName(rs.getString("dic_en_name"));
						dic.setDicOrder(rs.getString("dic_order"));
						dic.setDicTypeCode(rs.getString("dic_type_code"));
						return dic;
					}
				});
		return dicList;
	}

	/**
	 * 根据数据字典代码获取数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public DicInfo queryDictionaryByCode(String dicCode) throws Exception {
		String sql = "select * from downHistory_info where dic_code = ?";
		String[] code = new String[] { dicCode };
		DicInfo obj = (DicInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DicInfo dic = new DicInfo();
							dic.setDicCode(rs.getString("dic_code"));
							dic.setDicCnName(rs.getString("dic_cn_name"));
							dic.setDicEnName(rs.getString("dic_en_name"));
							dic.setDicOrder(rs.getString("dic_order"));
							dic.setDicTypeCode(rs.getString("dic_type_code"));
							return dic;
						}
						return null;
					}
				});
		return obj;
	}
	/**
	 * 根据数据中文名称获取数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public DicInfo queryDictionaryBydicCnName(String dicCnName,String dicCode) throws Exception {
		String sql = "select * from downHistory_info where dic_cn_name = ? and dic_code != ?";
		String[] code = new String[] { dicCnName, dicCode };
		DicInfo obj = (DicInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DicInfo dic = new DicInfo();
							dic.setDicCode(rs.getString("dic_code"));
							dic.setDicCnName(rs.getString("dic_cn_name"));
							dic.setDicEnName(rs.getString("dic_en_name"));
							dic.setDicOrder(rs.getString("dic_order"));
							dic.setDicTypeCode(rs.getString("dic_type_code"));
							return dic;
						}
						return null;
					}
				});
		return obj;
	}
	/**
	 * 根据数据字典类型和名称查出字典的编码
	 */
	@SuppressWarnings("unchecked")
	public DicInfo queryDictionaryByOrder(String dicType, String order,String dicCode)
			throws Exception {
		String sql = "select * from downHistory_info where dic_type_code=? and dic_order = ? and dic_code != ?";
		String[] code = new String[] { dicType, order, dicCode };
		DicInfo obj = (DicInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DicInfo dic = new DicInfo();
							dic.setDicCode(rs.getString("dic_code"));
							dic.setDicCnName(rs.getString("dic_cn_name"));
							dic.setDicEnName(rs.getString("dic_en_name"));
							dic.setDicOrder(rs.getString("dic_order"));
							dic.setDicTypeCode(rs.getString("dic_type_code"));
							return dic;
						}
						return null;
					}
				});
		return obj;
	}
	
	/**
	 * 根据数据字典类型和名称查出字典的编码
	 */
	@SuppressWarnings("unchecked")
	public DicInfo queryDictionaryByCode(String dicType, String dicName)
			throws Exception {
		String sql = "select * from downHistory_info where dic_type_code=? and dic_cn_name = ?";
		String[] code = new String[] { dicType, dicName };
		DicInfo obj = (DicInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DicInfo dic = new DicInfo();
							dic.setDicCode(rs.getString("dic_code"));
							dic.setDicCnName(rs.getString("dic_cn_name"));
							dic.setDicEnName(rs.getString("dic_en_name"));
							dic.setDicOrder(rs.getString("dic_order"));
							dic.setDicTypeCode(rs.getString("dic_type_code"));
							return dic;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 删除数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public void deleteDictionary(final String dicCode) throws Exception {
		String sql = "delete downHistory_info where dic_code=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, dicCode);
				pstmt.execute();
				return null;
			}
		});
	}

	
	
	//通过applyID 获得userId
	public Integer applyId_UserId(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.userId from apply_user a where a.id="+id);
		@SuppressWarnings({ "unchecked"})
		List<ApplyUser> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ApplyUser info = new ApplyUser();
		    	info.setId(Integer.valueOf(rs.getString("userId")));
				return info;
		   }
		});
		return eventInfoList.get(0).getId();
	}
	
	//地震应急事件   表格展示
	public List<FileInfo>  downTable(String userName,String earthQuakeId,String year){
		
		String earthQuakeIdSql="";
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			earthQuakeIdSql = " and f.earthquakeid='"+earthQuakeId+"' ";
		}
		String userNameSql="";
		if(null!=userName && !"".equals(userName)){
			userNameSql=" and d.username='"+userName+"' ";
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select aa.id,aa.site_number,aa.earthQuake50Or1,aa.earthQuakeDay,aa.earthQuakeNum,aa.fileFlag,aa.fileName,aa.filePath,aa.fileSize,bb.counts from (select f.id,f.site_number,f.earthQuake50Or1,f.earthQuakeDay,f.earthQuakeNum,f.fileFlag,f.fileName,f.filePath,f.fileSize from file_info_"+year+" f where f.type=4 "+earthQuakeIdSql+" ) aa left join (select f.id,count(*) counts from file_info_"+year+" f ,DOWNHISTORY_INFO_"+year+" d where f.id=d.fileinfoid and f.type=4 "+earthQuakeIdSql+userNameSql+" group by f.id ) bb on aa.id=bb.id order by aa.site_number,aa.earthquakeday ");
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	System.out.println(rs.getString("site_number"));
		    	System.out.println(MapAll.mapSite.get(rs.getString("site_number")));
System.out.println(MapAll.mapSite.get(rs.getString("site_number")).getSiteName());
		    	info.setSiteName(MapAll.mapSite.get(rs.getString("site_number")).getSiteName());
		    	info.setEarthQuake50Or1(rs.getString("earthQuake50Or1"));
		    	info.setEarthQuakeDay(Integer.valueOf(rs.getString("earthQuakeDay")));
		    	info.setEarthQuakeNum(Integer.valueOf(rs.getString("earthQuakeNum")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("counts")==null?"0":rs.getString("counts")));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String userName,String earthQuakeId,String year,final EarthQuakeConfig earthConfig){
		
		String earthQuakeIdSql="";
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			earthQuakeIdSql = " and f.earthquakeid='"+earthQuakeId+"' ";
		}
		String userNameSql="";
		if(null!=userName && !"".equals(userName)){
			userNameSql=" and d.username='"+userName+"' ";
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select bb.site_name,bb.site_number,bb.site_lat as siteLat,bb.site_lon as siteLon,aa.counts,cc.id,cc.name,cc.site_lat as eLat,cc.site_lon as eLon,cc.crawtype,cc.crawrange,cc.grade from (select a.site_number,a.id,b.counts from (select f.site_number,e.id from FILE_INFO_"+year+" f,EARTHQUAKE e where f.earthquakeid=e.id and f.type=4 "+earthQuakeIdSql+" group by f.site_number,e.id )a left join (select f.earthquakeid, f.site_number,count(*) counts from FILE_INFO_"+year+" f,DOWNHISTORY_INFO_"+year+" d where f.id=d.fileinfoid and f.type=4 "+earthQuakeIdSql+userNameSql+"  group by f.earthquakeid,f.site_number) b on a.site_number=b.site_number and a.id=b.earthquakeid) aa, site_info bb,EARTHQUAKE cc where aa.site_number=bb.site_number and aa.id=cc.id ");
		final Set<String> repeatDis = new HashSet<String>();
		final List<SiteMapInfo> sList = new ArrayList<SiteMapInfo>();
		@SuppressWarnings({ "unchecked"})
		List<SiteMapInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteMapInfo info = new SiteMapInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteLat(rs.getString("siteLat"));
		    	info.setSiteLng(rs.getString("siteLon"));
		    	info.setFileFlag(Integer.valueOf(rs.getString("counts")==null?"0":rs.getString("counts")));
		    	info.setEarthQuakeFlag("false");
		    	
		    	String id = rs.getString("id");
		    	if(!repeatDis.contains(id)){
		    		SiteMapInfo eInfo = new SiteMapInfo();
		    		eInfo.setSiteNumber(rs.getString("name"));
		    		eInfo.setSiteName(rs.getString("name"));
		    		eInfo.setSiteLat(rs.getString("eLat"));
		    		eInfo.setSiteLng(rs.getString("eLon"));
		    		eInfo.setFileFlag(-1);
		    		eInfo.setEarthQuakeFlag("true");
		    		eInfo.setCrawtype(rs.getString("crawtype"));
		    		eInfo.setGrade(rs.getString("grade"));
		    		if(null!=eInfo.getCrawtype() && !"".equals(eInfo) && "1".equals(eInfo)){
		    			eInfo.setCrawrange(rs.getString("crawrange"));
		    		}else{
		    			Double grade = Double.valueOf(eInfo.getGrade());
		    			if(grade>=5 && grade<6){
		    				eInfo.setCrawrange(earthConfig.getEarthquakefirst());
		    			}else if(grade>=6 && grade<7){
		    				eInfo.setCrawrange(earthConfig.getEarthquakesecond());
		    			}else if(grade>=7 && grade<8){
		    				eInfo.setCrawrange(earthConfig.getEarthquakethird());
		    			}else if(grade>=8){
		    				eInfo.setCrawrange(earthConfig.getEarthquakeforth());
		    			}
		    		}
		    		repeatDis.add(id);
		    		sList.add(eInfo);
		    	}
		    	
				return info;
		   }
		});
		eventInfoList.addAll(sList);
		return eventInfoList;
	}
}
