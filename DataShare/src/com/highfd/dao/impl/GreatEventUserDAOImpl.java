package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.bean.GreatEventInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.GreatEventDAO;

@Resource
public class GreatEventUserDAOImpl implements GreatEventDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 添加站点大事记
	 */
	@SuppressWarnings("unchecked")
	public void insertGreatEvent(final GreatEventInfo info) throws Exception {
		String sql = "INSERT INTO great_event_INFO (id,site_number,DESCRIBE,FEEDBACK,STARTTIME,ENDTIME,SN_NEW,SN_OLD,FW_NEW,FW_OLD,MODEL_NEW,MODEL_OLD," +
				"CREATETIME,HEIGHT_NEW,HEIGHT_OLD,RINEX_NEW,RINEX_OLD,REMARK,TYPE)"
			+ "VALUES(great_event_INFO_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getSiteNumber());
				pstmt.setString(2, info.getDescribe());
				pstmt.setString(3, info.getFeedback());
				try {
					pstmt.setTimestamp(4, TimeUtils.getTempByAllTime(info.getStartTimeStr()));
					pstmt.setTimestamp(5, TimeUtils.getTempByAllTime(info.getEndTimeStr()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pstmt.setString(6, info.getSn_new());
				pstmt.setString(7, info.getSn_old());
				pstmt.setString(8, info.getFw_new());
				pstmt.setString(9, info.getFw_old());
				pstmt.setString(10, info.getModel_new());
				pstmt.setString(11, info.getModel_old());
				pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(13, info.getHeight_new());
				pstmt.setString(14, info.getHeight_old());
				pstmt.setString(15, info.getRinex_new());
				pstmt.setString(16, info.getRinex_old());
				pstmt.setString(17, info.getRemark());
				pstmt.setString(18, info.getType());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	/**
	 * 超级查询站点大事记列表
	 */
	public List<GreatEventInfo>  selectSuperGreatEventList(String id,String siteNumber,String param){
		StringBuffer sql = new StringBuffer();
		sql.append("select g.site_number,a.endTime as at,b.endTime as bt,c.endTime as ct from " +
				"(select s.site_number  from GREAT_EVENT_INFO s group by s.site_number ) g left join " +
				"(select max(v.endtime) as endTime,v.site_number as site_number  from GREAT_EVENT_INFO v where v.type=1 group by v.site_number) a on g.site_number=a.site_number " +
				"left join " +
				"(select max(v.endtime) as endTime,v.site_number as site_number  from GREAT_EVENT_INFO v where v.type=2 group by v.site_number) b on g.site_number=b.site_number " +
				"left join " +
				"(select max(v.endtime) as endTime,v.site_number as site_number  from GREAT_EVENT_INFO v where v.type=3 group by v.site_number) c on g.site_number=c.site_number");
		if(null!= id  && !"".equals(id)){sql.append( " and g.id = "+id);}
		if(null!= siteNumber  && !"".equals(siteNumber)){sql.append( " and g.site_number = "+siteNumber);}
		if(null!=param && !"".equals(param)){sql.append(" and (g.site_number like '%"+param+"%')");}
		@SuppressWarnings({ "unchecked"})
		List<GreatEventInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GreatEventInfo info = new GreatEventInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	
		    	SiteInfo siteInfo = MapAll.mapSite.get(rs.getString("site_number"));
		    	info.setSiteName(siteInfo.getSiteName());
		    	info.setZoneName(siteInfo.getZoneName());
		    	info.setDepartmentName(siteInfo.getDepartmentName());
		    	
		    	info.setaTimeStr(null==rs.getTimestamp("at")?"无":TimeUtils.TimestampToString2(rs.getTimestamp("at")));
		    	info.setbTimeStr(null==rs.getTimestamp("bt")?"无":TimeUtils.TimestampToString2(rs.getTimestamp("bt")));
		    	info.setcTimeStr(null==rs.getTimestamp("ct")?"无":TimeUtils.TimestampToString2(rs.getTimestamp("ct")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	/**
	 * 查询站点大事记， 单中类型
	 */
	public List<GreatEventInfo>  selectGreatEventList(String id,String type,String siteNumber,String param,String state){
		StringBuffer sql = new StringBuffer();
		sql.append("select  g.id,g.site_number,s.site_name,g.describe,g.feedback,g.starttime,g.endtime,g.sn_new,g.sn_old,g.fw_new,g.fw_old,g.model_new,g.model_old,g.createtime," +
				"g.height_new,g.height_old,g.rinex_new,g.rinex_old,g.remark,s.site_zone,s.site_department,g.type " +
				"from  site_info s,  great_event_INFO g where g.site_number=s.site_number and s.site_type=1 ");
		if(null!= id  && !"".equals(id)){sql.append( " and g.id = "+id);}
		if(null!= siteNumber  && !"".equals(siteNumber)){sql.append( " and g.site_number = '"+siteNumber+"' ");}
		if(null!= type  && !"".equals(type)){sql.append( " and g.type = "+type);}
		if(null!=param && !"".equals(param)){sql.append(" and ( s.site_zone like '%"+param+"%' or g.site_number like '%"+param+"%' or g.describe like '%"+param+"%' or  g.feedback like '%"+param+"%' or g.sn_new like '%"+param+"%')");}
		if(null!=state && !"".equals(state)){sql.append(" and g.type = '" + state +"'");}
		sql.append(" order by g.endtime desc");
		
		@SuppressWarnings({ "unchecked"})
		List<GreatEventInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GreatEventInfo info = new GreatEventInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setZoneName(MapAll.mapZone.get(rs.getString("site_zone")).getZoneName());
		    	info.setDepartmentName(MapAll.mapDepat.get(rs.getString("site_department")).getDicCnName());
		    	
		    	info.setDescribe(rs.getString("describe"));
		    	info.setFeedback(rs.getString("feedback"));
		    	info.setStartTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("starttime")));
		    	info.setEndTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("endtime")));
		    	info.setSn_new(rs.getString("sn_new"));
		    	info.setSn_old(rs.getString("sn_old"));
		    	info.setFw_new(rs.getString("fw_new"));
		    	info.setFw_old(rs.getString("fw_old"));
		    	info.setModel_new(rs.getString("model_new"));
		    	info.setModel_old(rs.getString("model_old"));
		    	info.setHeight_new(rs.getString("height_new"));
		    	info.setHeight_old(rs.getString("height_old"));
		    	info.setRinex_new(rs.getString("rinex_new"));
		    	info.setRinex_old(rs.getString("rinex_old"));
		    	info.setRemark(rs.getString("remark"));
		    	info.setType(rs.getString("type"));
		    	if("1".equals(rs.getString("type"))){
		    		info.setTypeStr("更换接收机");
		    	}else if("2".equals(rs.getString("type"))){
		    		info.setTypeStr("更换天线");
		    	}else if("3".equals(rs.getString("type"))){
		    		info.setTypeStr("固件升级");
		    	}
		    	info.setCreatetimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("createtime")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 修改站点大事记
	 */
	@SuppressWarnings("unchecked")
	public void updateApplyUser_FTP(final GreatEventInfo info) throws Exception {
		String sql = "update great_event_INFO g set g.site_number=?,g.describe=?,g.feedback=?,g.starttime=?,g.endtime=?,g.sn_new=?,g.sn_old=?,g.fw_new=?,g.fw_old=?," +
				"g.model_new=?,g.model_old=?,g.createtime=?," +
				"g.height_new=?,g.height_old=?,g.rinex_new=?,g.rinex_old=?,g.remark=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				
				pstmt.setString(1, info.getSiteNumber());
				pstmt.setString(2, info.getDescribe());
				pstmt.setString(3, info.getFeedback());
				
				try {
					pstmt.setTimestamp(4, TimeUtils.getTempByAllTime(info.getStartTimeStr()));
					pstmt.setTimestamp(5, TimeUtils.getTempByAllTime(info.getEndTimeStr()));
					
					pstmt.setString(6, info.getSn_new());
					pstmt.setString(7, info.getSn_old());
					pstmt.setString(8, info.getFw_new());
					pstmt.setString(9, info.getFw_old());
					pstmt.setString(10, info.getModel_new());
					pstmt.setString(11, info.getModel_old());
					pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
					
					pstmt.setString(13, info.getHeight_new());
					pstmt.setString(14, info.getHeight_old());
					pstmt.setString(15, info.getRinex_new());
					pstmt.setString(16, info.getRinex_old());
					pstmt.setString(17, info.getRemark());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				pstmt.setInt(18, info.getId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 删除站点大事记
	 */
	@SuppressWarnings("unchecked")
	public void deleteApplyFile(final String id) throws Exception {
		String sql = "delete from great_event_INFO where id=? ";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, id);
				pstmt.execute();
				return null;
			}
		});
	}
}
