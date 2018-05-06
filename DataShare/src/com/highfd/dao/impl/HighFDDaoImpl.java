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

import com.highfd.bean.DataQuality;
import com.highfd.bean.DataQualityInfo;

import com.highfd.bean.NewsInfo;

import com.highfd.bean.ideaInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.number.NumberChange;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.HighFDDao;
/**
 * DAO层
 * @author Douban73
 *
 */
@Resource
public class HighFDDaoImpl implements HighFDDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//获得新闻列表
	public List<NewsInfo>  getNewsInfoList(String id,String type, String selectParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select id,title,content,time,type,orders from news_info  where 1=1");
		if(null!=id && !"".equals(id)){
			sql.append(" and id="+id);
		}
		if(null!=type && !"".equals(type)){
			sql.append(" and type="+type);
		}
		if(null!=selectParam && !"".equals(selectParam)){
			sql.append(" and ( title like '%"+selectParam+"%' or  content like '%"+selectParam+"%' )  ");
		}
		sql.append(" order by orders ,time");
		@SuppressWarnings({ "unchecked"})
		List<NewsInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	NewsInfo info = new NewsInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setTitle(rs.getString("title"));
		    	info.setContent(rs.getString("content"));
		    	info.setOrders(rs.getString("orders"));
		    	info.setTime(TimeUtils.TimestampToString2(rs.getTimestamp("time")));
		    	/*if(MapAll.DBType){
		    		info.setTime("["+TimeUtils.TimestampToString2(rs.getTimestamp("time"))+"]");
		    	}else{
		    		info.setTime("["+rs.getString("time")+"]");
		    	}*/
		    	info.setType(rs.getString("type"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//获得最近30天新闻
	public List<NewsInfo>  getNewsInfoList(String id,String type){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select a.id,a.title,a.content,a.time,a.type,a.orders, 'in30' as flags from news_info a where a.time >= trunc(sysdate - 50) union select a.id,a.title,a.content,a.time,a.type,a.orders, 'out30' as flags from news_info a where a.id not in (select n.id from news_info n where n.time >= trunc(sysdate - 50)) ) order by orders ,time");
		
		@SuppressWarnings({ "unchecked"})
		List<NewsInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	NewsInfo info = new NewsInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setTitle(rs.getString("title"));
		    	info.setContent(rs.getString("content"));
		    	info.setOrders(rs.getString("orders"));
		    	info.setTime(TimeUtils.TimestampToString2(rs.getTimestamp("time")));
		    	info.setType(rs.getString("type"));
		    	info.setFlags(rs.getString("flags"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//添加新闻
	@SuppressWarnings("unchecked")
	public void insertNews(final NewsInfo info) throws Exception {
		String sql = "INSERT INTO news_info (id,TITLE,content,time,type,orders)VALUES(NEWS_INFO_SEQ.nextval,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getTitle());
				pstmt.setString(2, info.getContent());
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				
				/*try {
					pstmt.setTimestamp(3, TimeUtils.getTempTime(info.getTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				pstmt.setString(4, info.getType());
				pstmt.setString(5, info.getOrders());
				pstmt.execute();
				return null;
			}
		});
	}
	
	//修改新闻
	@SuppressWarnings("unchecked")
	public void updateNews(final NewsInfo info) throws Exception {
		String sql = "update news_info set TITLE = ?,content=?,orders=? where id = ?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getTitle());
				pstmt.setString(2, info.getContent());
				pstmt.setString(3, info.getOrders());
				pstmt.setInt(4, info.getId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	/**
	 * 删除新闻
	 */
	@SuppressWarnings("unchecked")
	public void deleteNewsInfo(final int id) throws Exception {
		String sql = "delete from news_info where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, id);
				pstmt.execute();
				return null;
			}
		});
	}
	
	//添加合作交流明细
	@SuppressWarnings("unchecked")
	public void insertIdea(final ideaInfo info) throws Exception {
		String sql = "INSERT INTO idea (content,personName,telPhone,createTime)VALUES(?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getContent());
				pstmt.setString(2, info.getPersonName());
				pstmt.setString(3, info.getTelPhone());
				pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
				return null;
			}
		});
	}
	
	

	/**
	 * 合作交流列表
	 */
	public List<ideaInfo> ideaList(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from idea ");
		if(null!=id && !"".equals(id)){
			sql.append(" where id="+id);
		}
		@SuppressWarnings({ "unchecked"})
		List<ideaInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ideaInfo info = new ideaInfo();
				info.setId(rs.getInt("id"));
				info.setContent(rs.getString("content"));
				info.setPersonName(rs.getString("personName"));
				info.setTelPhone(rs.getString("telPhone"));
				info.setTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createTime")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//删除合作交流
	@SuppressWarnings("unchecked")
	public void deleteIdea(final int id) throws Exception {
		String sql = "delete from idea where id="+id;
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	/**
	 * 添加   首页 每月数据整理状态
	 */
	@SuppressWarnings("unchecked")
	public void insertDataQualityInfo(final DataQualityInfo info) throws Exception {
		String sql = "INSERT INTO DATAQUALITY_INFO (id,TITLE,CONTENT,TIME,YEAR,MONTH,TYPE,earthquakeid)"
			+ "VALUES(NEWS_INFO_SEQ.nextval,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getTitle());
				pstmt.setString(2, info.getContent());
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(4, info.getYear());
				pstmt.setString(5, info.getMonth());
				pstmt.setString(6, info.getType());
				pstmt.setInt(7, info.getEarthquakeid());
				pstmt.execute();
				return null;
			}
		});
	}
	/**
	 * 查询  首页 每月数据整理状态
	 */
	public List<DataQuality> data30_DataQualityList (String id,String type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.title,t.content,t.time,t.year,t.month,t.type,t.earthquakeid from DATAQUALITY_INFO t where 1=1 ");
		if(id!=null && id.length()>0){
			sql.append(" and t.id= "+id);
		}
		if(type!=null && type.length()>0){
			sql.append(" and t.type= "+type);
		}
		sql.append(" order by t.time desc");
		
		@SuppressWarnings({ "unchecked"})
		List<DataQuality> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	DataQuality info = new DataQuality();
		    	info.setId(rs.getString("id"));
		    	info.setTitle(rs.getString("title"));
		    	info.setContent(rs.getString("content"));
		    	info.setTime(TimeUtils.TimestampToString(rs.getTimestamp("time")).substring(0, 10));
		    	info.setYear(rs.getString("year"));
		    	info.setMonth(rs.getString("month"));
		    	info.setType(rs.getString("type"));
		    	info.setEarthQuakeId(rs.getString("earthquakeid"));
				return info;
		   }
		});
		return eventInfoList;
	}
	/**
	 * 数据动态，整理的每月的数据
	 */
	public List<DataQuality> data30_DataQuality (final String year,final String month,final String type) throws Exception {
		//if(month.length()==1){month="0"+month;}
		StringBuffer sql = new StringBuffer();
		sql.append("select t.site_number,s.site_name,avg(t.mp1) as avgMp1,avg(mp2) as avgMp2,avg(t.o_slps) as avgOs,sum(t.filesize) as sumSize," +
				"COUNT (CASE WHEN t.fileflag = 1 or t.fileflag=2 THEN 1 ELSE NULL END) / count(*) as integrityRate " +
				"from FILE_INFO_"+year+" t,site_info s where t.site_number=s.site_number " +
				"and t.mp1 is not null and t.mp1 not like '%-%' and t.mp1 not like '%-1%' and t.mp2 is not null and t.mp2 not like '%-%' and t.mp2 not like '%-1%'  and t.o_slps is not null  and t.o_slps not like '%-%' and t.o_slps not like '%-1%' and t.ephem_number is not null and t.ephem_number>0 and " +
				"t.systemtime between to_date('"+year+"-"+month+"-01 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(month))+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  " +
				" and t.type="+type+" group by t.site_number ,s.site_name");
		
		@SuppressWarnings({ "unchecked"})
		List<DataQuality> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	DataQuality info = new DataQuality();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setMp1(NumberChange.getFourPoint(Double.valueOf(rs.getString("avgMp1"))));
		    	info.setMp2(NumberChange.getFourPoint(Double.valueOf(rs.getString("avgMp2"))));
		    	info.setO_slps(NumberChange.getFourPoint(Double.valueOf(rs.getString("avgOs"))));
		    	info.setFileSize(NumberChange.getFourPoint(Double.valueOf(rs.getString("sumSize"))/2014));
		    	info.setIntegrityRate(NumberChange.getFourPoint(Double.valueOf(rs.getString("integrityRate"))));
		    	info.setDepartName(MapAll.mapSite.get(info.getSiteNumber()).getDepartmentName());
		    	info.setDayValieSum(TimeUtils.getLastNumberOfMonth(Integer.valueOf(year),Integer.valueOf(month)));
		    	info.setDaySum(TimeUtils.getLastNumberOfMonth(Integer.valueOf(year),Integer.valueOf(month)));
				return info;
		   }
		});
		return eventInfoList;
	}
	

	



}