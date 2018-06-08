package com.highfd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import com.highfd.bean.ApplyUser;
import com.highfd.bean.Apply_User_Files;
import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.Apply_User_SiteInfo;
import com.highfd.bean.Apply_User_Zone;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.ApplyFileDAO;

@Resource
public class ApplyFileDAOImpl implements ApplyFileDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 申请  主表 信息**************************************************************************************************
	 */
	public int saveApplyUser(final ApplyUser af) {
		
		try {
			final int id = this.getNextID(jdbcTemplate, "SELECT APPLY_USER_SEQ.nextval FROM DUAL");
			final String sql = "insert into apply_user(id,userId,apply_Unit,apply_Person,apply_PersonId,apply_Phone,appply_Email," +
			"result_Name,result_Money,result_Person,result_from,result_profile," +
			"response_person,response_phone,createTime,LiuCheng,applyTime1"
			+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    jdbcTemplate.update(
		            new PreparedStatementCreator() {
		                public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
		                    PreparedStatement pstmt = getJdbcTemplate().getDataSource().getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		                    pstmt.setInt(1, id);
		                    pstmt.setInt(2, af.getUserId());
		    				pstmt.setString(3, af.getApply_Unit());
		    				pstmt.setString(4, af.getApply_Person());
		    				pstmt.setString(5, af.getApply_PersonId());
		    				pstmt.setString(6, af.getApply_Phone());
		    				pstmt.setString(7, af.getAppply_Email());
		    				pstmt.setString(8, af.getResult_Name());
		    				pstmt.setString(9, af.getResult_Money());
		    				pstmt.setString(10, af.getResult_Person());
		    				pstmt.setString(11,af.getResult_from());
		    				pstmt.setString(12,af.getResult_profile());
		    				pstmt.setString(13,af.getResponse_person());
		    				pstmt.setString(14,af.getResponse_phone());
		    				pstmt.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
		    				pstmt.setString(16,"1");
		    				pstmt.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
		                    return pstmt;
		                }
		            });
		    return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}
	
	
	@SuppressWarnings("unchecked")
	public int getNextID(JdbcTemplate jdbcTemplate, String sql) throws Exception {
		Integer obj = (Integer) jdbcTemplate.query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getInt(1);
						}
						return null;
					}
				});
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public void updateApplyUser(final int num,final ApplyUser af) throws Exception {
		if(num==5){//审核通过
			String sql = "update apply_user set applyTime5=?, applyTime7=?, LiuCheng=?,remark=? , year=? where id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					try {
						pstmt.setTimestamp(2, TimeUtils.getTempTime(TimeUtils.getStrYesterday(af.getApplyperiod())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					pstmt.setInt(3, Integer.valueOf(af.getLiuCheng()));
					pstmt.setString(4, af.getRemark());
					pstmt.setString(5, af.getYear());
					pstmt.setInt(6, af.getId());
					pstmt.execute();
					return null;
				}
			});
		}else if(num==6){//审核不通过
			String sql = "update apply_user set applyTime"+num+"=?, LiuCheng=?,remark=? where id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					pstmt.setInt(2, Integer.valueOf(af.getLiuCheng()));
					pstmt.setString(3, af.getRemark());
					pstmt.setInt(4, af.getId());
					pstmt.execute();
					return null;
				}
			});
		}else if(null==af.getApplyWorldPath()){
			String sql = "update apply_user set applyTime"+num+"=?, LiuCheng=? where id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					pstmt.setInt(2, num);
					pstmt.setInt(3, af.getId());
					pstmt.execute();
					return null;
				}
			});
		}else{
			String sql = "update apply_user set applyTime"+num+"=?, LiuCheng=?,apply_World_Path=? where id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					pstmt.setInt(2, num);
					pstmt.setString(3, af.getApplyWorldPath());
					pstmt.setInt(4, af.getId());
					pstmt.execute();
					return null;
				}
			});
		}
		
	}

	//修改  申请流程状态 流程2
	@SuppressWarnings("unchecked")
	public void updateApplyUserLiuCheng2(final int num,final ApplyUser af) throws Exception {
		String sql = "update apply_user set applyTime"+num+"=?, LiuCheng=?,year=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(2, num);
				pstmt.setString(3, af.getYear());
				pstmt.setInt(4, af.getId());
				pstmt.execute();
				return null;
			}
		});
	
		
	}
	
	@SuppressWarnings("unchecked")
	public void updateApplyUser(final ApplyUser af) throws Exception {
		String sql = "update apply_user set applyTime5=?, applyTime7=?, LiuCheng=?,remark=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				try {
					pstmt.setTimestamp(2, TimeUtils.getTempTime(TimeUtils.getStrYesterday(af.getApplyperiod())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				pstmt.setInt(3, Integer.valueOf(af.getLiuCheng()));
				pstmt.setString(4, af.getRemark());
				pstmt.setInt(5, af.getId());
				pstmt.execute();
				return null;
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<ApplyUser> queryApplyUser(final int userId,final ApplyUser af) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from apply_user t where 1=1");
		
		if(userId!=-1){//不是管理员
			sql.append(" and userId = "+userId);
		}
		if(null!=af.getAllOrNo() && "no".equals(af.getAllOrNo())){
			sql.append(" and id = "+af.getId());
		}else{
			sql.append(" and liucheng in "+af.getLiuCheng());
		}
		String userParam = af.getSelectParams();
		if(null!=userParam && !"".equals(userParam)){
			sql.append("and (apply_Unit like '%"+userParam+"%'  " +
					"or apply_Person like '%"+userParam+"%'  " +
					"or apply_PersonId like '%"+userParam+"%'  " +
					"or apply_Phone like '%"+userParam+"%'  " +
					"or appply_Email like '%"+userParam+"%'  " +
					
					"or result_Name like '%"+userParam+"%'  " +
					"or result_Money like '%"+userParam+"%'  " +
					"or result_Person like '%"+userParam+"%'  "+
					"or result_from like '%"+userParam+"%'  " +
					"or result_profile like '%"+userParam+"%'  " +
					
					"or response_person like '%"+userParam+"%'  " +
					"or response_phone like '%"+userParam+"%')");
		}
		
		sql.append(" order by t.createtime desc ");
		List<ApplyUser> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ApplyUser apply = new ApplyUser();
		    	apply.setId(Integer.valueOf(rs.getString("id")));
		    	apply.setUserId(Integer.valueOf(rs.getString("userId")));
		    	
		    	apply.setApply_Unit(rs.getString("apply_Unit"));
		    	apply.setApply_Person(rs.getString("apply_Person"));
		    	apply.setApply_PersonId(rs.getString("apply_PersonId"));
		    	apply.setApply_Phone(rs.getString("apply_Phone"));
		    	apply.setAppply_Email(rs.getString("appply_Email"));
		    	
		    	apply.setResult_Name(rs.getString("result_Name"));
		    	apply.setResult_Money(rs.getString("result_Money"));
		    	apply.setResult_Person(rs.getString("result_Person"));
		    	apply.setResult_from(rs.getString("result_from"));
		    	apply.setResult_profile(rs.getString("result_profile"));
		    	
		    	apply.setResponse_person(rs.getString("response_person"));
		    	apply.setResponse_phone(rs.getString("response_phone"));
		    	
		    	apply.setCreateTime(rs.getTimestamp("createTime"));
		    	
		    	String liuCheng = rs.getString("LiuCheng");
		    	apply.setLiuCheng(liuCheng);
		    	if(liuCheng.equals("5")){
		    		apply.setLiuChengName("通过");
		    	}else if(liuCheng.equals("6")){
		    		apply.setLiuChengName("未通过");
		    	}else if(liuCheng.equals("7")){
		    		apply.setLiuChengName("过期");
		    	}
		    	
		    	apply.setApplyWorldPath(rs.getString("apply_World_Path"));
		    	apply.setRemark(rs.getString("remark"));
		    	
		    	apply.setFtpUserName(rs.getString("FtpUserName"));
		    	apply.setFtpPwd(rs.getString("FtpPwd"));
		    	
		    	//apply.setApplyTime1(rs.getString("applyTime1"));
		    	apply.setApplyTime1(null==rs.getTimestamp("applyTime1")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime1")));
		    	apply.setApplyTime2(null==rs.getTimestamp("applyTime2")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime2")));
		    	apply.setApplyTime3(null==rs.getTimestamp("applyTime3")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime3")));
		    	apply.setApplyTime4(null==rs.getTimestamp("applyTime4")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime4")));
		    	//apply.setApplyTime5(rs.getString("applyTime5"));
		    	apply.setApplyTime6(null==rs.getTimestamp("applyTime6")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime6")));
		    	//apply.setApplyTime7(rs.getString("applyTime7"));
		    	
		    	//开始和时间、结束时间
		    	apply.setApplyTime5(null==rs.getTimestamp("applyTime5")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime5")));
		    	apply.setApplyTime7(null==rs.getTimestamp("applyTime7")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime7")));
				return apply;
		   }
		});
		return eventInfoList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String queryApplyLiuChengByApplyId(final String applyId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_user t where 1=1");
		if(null!=applyId && !"".equals(applyId)){sql.append(" and id = "+applyId);}
		List<ApplyUser> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ApplyUser apply = new ApplyUser();
		    	String liuCheng = rs.getString("LiuCheng");
		    	apply.setLiuCheng(liuCheng);
				return apply;
		   }
		});
		if(null!=eventInfoList && eventInfoList.size()>0){
			return eventInfoList.get(0).getLiuCheng();
		}else{
			return null;
		}
		
	}
	
	
	/**
	 * 申请  查询  条件**************************************************************************************************
	 */
	@SuppressWarnings("unchecked")
	public void insert_Apply_User_Req(final Apply_User_Req aur) throws Exception {
		
		String sql = "INSERT INTO apply_User_requiement (id,s_StartTime,s_EndTime,s_Lat_small,s_Lat_big,s_Lon_small,s_Lon_big,s_Apply_Zone,s_Apply_Site," +
				"flow_StartTime,flow_EndTime,flow_Lat_small,flow_Lat_big,flow_Lon_small,flow_Lon_big,flow_Apply_Zone,flow_Apply_Site," +
				"share_StartTime,share_EndTime,share_Lat_small,share_Lat_big,share_Lon_small,share_Lon_big,share_Apply_Zone,share_Apply_Site,applyId) VALUES(apply_User_requiement_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setTimestamp(1, aur.getS_StartTime());
				pstmt.setTimestamp(2, aur.getS_EndTime());
				pstmt.setDouble(3, Double.valueOf(aur.getS_Lat_small()));
				pstmt.setDouble(4, Double.valueOf(aur.getS_Lat_big()));
				pstmt.setDouble(5, Double.valueOf(aur.getS_Lon_small()));
				pstmt.setDouble(6, Double.valueOf(aur.getS_Lon_big()));
				pstmt.setInt(7, Integer.valueOf(aur.getS_Apply_Zone()));
				pstmt.setInt(8, Integer.valueOf(aur.getS_Apply_Site()));
				
				pstmt.setTimestamp(9, aur.getFlow_StartTime());
				pstmt.setTimestamp(10, aur.getFlow_EndTime());
				pstmt.setDouble(11, Double.valueOf(aur.getFlow_Lat_small()));
				pstmt.setDouble(12, Double.valueOf(aur.getFlow_Lat_big()));
				pstmt.setDouble(13, Double.valueOf(aur.getFlow_Lon_small()));
				pstmt.setDouble(14, Double.valueOf(aur.getFlow_Lon_big()));
				pstmt.setInt(15, Integer.valueOf(aur.getFlow_Apply_Zone()));
				pstmt.setInt(16, Integer.valueOf(aur.getFlow_Apply_Site()));
				
				pstmt.setTimestamp(17, aur.getShare_StartTime());
				pstmt.setTimestamp(18, aur.getShare_EndTime());
				pstmt.setDouble(19, Double.valueOf(aur.getShare_Lat_small()));
				pstmt.setDouble(20, Double.valueOf(aur.getShare_Lat_big()));
				pstmt.setDouble(21, Double.valueOf(aur.getShare_Lon_small()));
				pstmt.setDouble(22, Double.valueOf(aur.getShare_Lon_big()));
				pstmt.setInt(23, Integer.valueOf(aur.getShare_Apply_Zone()));
				pstmt.setInt(24, Integer.valueOf(aur.getShare_Apply_Site()));
				
				pstmt.setInt(25, Integer.valueOf(aur.getApplyId()));
				pstmt.execute();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void update_Apply_User_Req(final Apply_User_Req aur) throws Exception {
		String sql = "update apply_User_requiement set s_StartTime=?, s_EndTime=?, s_Lat_small=?, s_Lat_big=?, s_Lon_small=?,  s_Lon_big=?,  s_Apply_Zone=?,  s_Apply_Site=?," +
				"  flow_StartTime=?,  flow_EndTime=?,  flow_Lat_small=?,  flow_Lat_big=?,  flow_Lon_small=?,  flow_Lon_big=?,  flow_Apply_Zone=?,  flow_Apply_Site=?," +
				"  share_StartTime=?,  share_EndTime=?,  share_Lat_small=?,  share_Lat_big=?,  share_Lon_small=?,  share_Lon_big=?,  share_Apply_Zone=?,  share_Apply_Site=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setTimestamp(1, aur.getS_StartTime());
				pstmt.setTimestamp(2, aur.getS_EndTime());
				pstmt.setDouble(3, Double.valueOf(aur.getS_Lat_small()));
				pstmt.setDouble(4, Double.valueOf(aur.getS_Lat_big()));
				pstmt.setDouble(5, Double.valueOf(aur.getS_Lon_small()));
				pstmt.setDouble(6, Double.valueOf(aur.getS_Lon_big()));
				pstmt.setInt(7, Integer.valueOf(aur.getS_Apply_Zone()));
				pstmt.setInt(8, Integer.valueOf(aur.getS_Apply_Site()));
				
				pstmt.setTimestamp(9, aur.getFlow_StartTime());
				pstmt.setTimestamp(10, aur.getFlow_EndTime());
				pstmt.setDouble(11, Double.valueOf(aur.getFlow_Lat_small()));
				pstmt.setDouble(12, Double.valueOf(aur.getFlow_Lat_big()));
				pstmt.setDouble(13, Double.valueOf(aur.getFlow_Lon_small()));
				pstmt.setDouble(14, Double.valueOf(aur.getFlow_Lon_big()));
				pstmt.setInt(15, Integer.valueOf(aur.getFlow_Apply_Zone()));
				pstmt.setInt(16, Integer.valueOf(aur.getFlow_Apply_Site()));
				
				pstmt.setTimestamp(17, aur.getShare_StartTime());
				pstmt.setTimestamp(18, aur.getShare_EndTime());
				pstmt.setDouble(19, Double.valueOf(aur.getShare_Lat_small()));
				pstmt.setDouble(20, Double.valueOf(aur.getShare_Lat_big()));
				pstmt.setDouble(21, Double.valueOf(aur.getShare_Lon_small()));
				pstmt.setDouble(22, Double.valueOf(aur.getShare_Lon_big()));
				pstmt.setInt(23, Integer.valueOf(aur.getShare_Apply_Zone()));
				pstmt.setInt(24, Integer.valueOf(aur.getShare_Apply_Site()));
				
				pstmt.setInt(25, aur.getId());

				pstmt.execute();
				return null;
			}
		});
	}

	public List<Apply_User_Req> query_Apply_User_ReqList(String applyId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_User_requiement t where applyId="+applyId);
		@SuppressWarnings({ "unchecked"})
		List<Apply_User_Req> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	Apply_User_Req info = new Apply_User_Req();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setS_StartTime(rs.getTimestamp("s_StartTime")==null?new Timestamp(0L):rs.getTimestamp("s_StartTime"));
		    	info.setS_EndTime(rs.getTimestamp("s_EndTime")==null?new Timestamp(0L):rs.getTimestamp("s_EndTime"));
		    	info.setS_Lat_small(Double.valueOf(rs.getString("s_Lat_small")==null?"0":rs.getString("s_Lat_small")));
		    	info.setS_Lat_big(Double.valueOf(rs.getString("s_Lat_big")==null?"0":rs.getString("s_Lat_big")));
		    	info.setS_Lon_small(Double.valueOf(rs.getString("s_Lon_small")==null?"0":rs.getString("s_Lon_small")));
		    	info.setS_Lon_big(Double.valueOf(rs.getString("s_Lon_big")==null?"0":rs.getString("s_Lon_big")));
		    	info.setS_Apply_Zone(Integer.valueOf(rs.getString("s_Apply_Zone")==null?"1":rs.getString("s_Apply_Zone")));
		    	info.setS_Apply_Site(Integer.valueOf(rs.getString("s_Apply_Site")==null?"1":rs.getString("s_Apply_Site")));
		    	info.setS_Start(TimeUtils.TimestampToString2(info.getS_StartTime()));
		    	info.setS_End(TimeUtils.TimestampToString2(info.getS_EndTime()));
		    	
		    	info.setFlow_StartTime(rs.getTimestamp("Flow_StartTime")==null?new Timestamp(0L):rs.getTimestamp("Flow_StartTime"));
		    	info.setFlow_EndTime(rs.getTimestamp("Flow_EndTime")==null?new Timestamp(0L):rs.getTimestamp("Flow_EndTime"));
		    	info.setFlow_Lat_small(Double.valueOf(rs.getString("Flow_Lat_small")==null?"0":rs.getString("Flow_Lat_small")));
		    	info.setFlow_Lat_big(Double.valueOf(rs.getString("Flow_Lat_big")==null?"0":rs.getString("Flow_Lat_big")));
		    	info.setFlow_Lon_small(Double.valueOf(rs.getString("Flow_Lon_small")==null?"0":rs.getString("Flow_Lon_small")));
		    	info.setFlow_Lon_big(Double.valueOf(rs.getString("Flow_Lon_big")==null?"0":rs.getString("Flow_Lon_big")));
		    	info.setFlow_Apply_Zone(Integer.valueOf(rs.getString("Flow_Apply_Zone")==null?"1":rs.getString("Flow_Apply_Zone")));
		    	info.setFlow_Apply_Site(Integer.valueOf(rs.getString("Flow_Apply_Site")==null?"1":rs.getString("Flow_Apply_Site")));
		    	info.setF_Start(TimeUtils.TimestampToString2(info.getFlow_StartTime()));
		    	info.setF_End(TimeUtils.TimestampToString2(info.getFlow_EndTime()));
		    	
		    	info.setShare_StartTime(rs.getTimestamp("Share_StartTime")==null?new Timestamp(0L):rs.getTimestamp("Share_StartTime"));
		    	info.setShare_EndTime(rs.getTimestamp("Share_EndTime")==null?new Timestamp(0L):rs.getTimestamp("Share_EndTime"));
		    	info.setShare_Lat_small(Double.valueOf(rs.getString("Share_Lat_small")==null?"0":rs.getString("Share_Lat_small")));
		    	info.setShare_Lat_big(Double.valueOf(rs.getString("Share_Lat_big")==null?"0":rs.getString("Share_Lat_big")));
		    	info.setShare_Lon_small(Double.valueOf(rs.getString("Share_Lon_small")==null?"0":rs.getString("Share_Lon_small")));
		    	info.setShare_Lon_big(Double.valueOf(rs.getString("Share_Lon_big")==null?"0":rs.getString("Share_Lon_big")));
		    	info.setShare_Apply_Zone(Integer.valueOf(rs.getString("Share_Apply_Zone")==null?"1":rs.getString("Share_Apply_Zone")));
		    	info.setShare_Apply_Site(Integer.valueOf(rs.getString("Share_Apply_Site")==null?"1":rs.getString("Share_Apply_Site")));
		    	info.setSh_Start(TimeUtils.TimestampToString2(info.getShare_StartTime()));
		    	info.setSh_End(TimeUtils.TimestampToString2(info.getShare_EndTime()));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	

	//数据申请第二步   申请数据
	public List<FileInfo>  getApplyFileByDay(String startTime,String endTime,String fileDayYear,String siteNumberArray,String zoneArray, String siteLat1,String siteLat2,String siteLon1,String siteLon2,String siteType,String fileType,String fileYear){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from ");
		sql.append("(");
		sql.append("select t.site_number,t.site_name,z.zone_name from site_info t ,zone_info z where t.SITE_ZONE=z.zone_code ");
		
		//如果选择了台站，那么将不走 部委 跟 省份
		//站点列表
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}else{
			//省份列表
			StringBuffer zoneList = new StringBuffer();
			if(null!=zoneArray && !"".equals(zoneArray)){
				String[] split = zoneArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						zoneList.append("'"+split[i]+"'");
					}else{
						zoneList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_zone in("+zoneList+") ");
			}
		}
		
		if(siteLat2!=null && !"0".equals(siteLat2) && siteLat1!=null && !"0".equals(siteLat1)){
			//纬度
			if(null!=siteLat1 && null!=siteLat2 && !"".equals(siteLat1) && !"".equals(siteLat2)){
				sql.append(" and t.site_lat>="+siteLat1 +" and t.site_lat<="+siteLat2+" ");
			}
		}
		
		if(null!=siteLon1 && !"0".equals(siteLon1) && null!=siteLon2 && !"0".equals(siteLon2)){
			//纬度
			if(null!=siteLon1 && null!=siteLon2 && !"".equals(siteLon1) && !"".equals(siteLon2)){
				sql.append(" and t.SITE_Lon>="+siteLon1 +" and t.SITE_Lon<="+siteLon2+" ");
			}
		}
		
		//台站类型
		if(null!=siteType && !"".equals(siteType)){
			sql.append(" and t.SITE_type in ("+siteType+") ");
		}
		sql.append(")a,");
		
		
		String dbYear = fileYear;
		if(fileYear.indexOf("-")>-1){//日度数据
			dbYear = fileYear.substring(0, 4);
		}
		
		sql.append("(");
		sql.append("select t.id,t.site_number,t.fileName,t.fileYear,t.fileDayYear,t.fileflag,t.filePath,t.fileSize,t.fileCreateTime,t.systemTime,t.type,ephem_number,t.mp1,t.mp2,t.o_slps from file_info_"+dbYear+" t where 1=1 ");
		
		
		//年积日
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}
		
		//时间
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//文件类型
		if(null!=fileType && !"".equals(fileType)){
			sql.append("and t.type='"+fileType+"' ");
		}
		sql.append(")b where a.site_number = b.site_number order by a.site_number ");
		
		
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setZoneName(rs.getString("ZONE_NAME"));
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else{
		    		info.setFileComp("缺失");
		    	}
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	
		    	//获得历元数量
		    	String ephemNumber = rs.getString("ephem_number");
		    	if(null==ephemNumber){
		    		ephemNumber="0";
		    	}else if(ephemNumber.indexOf("-")>-1){
		    		ephemNumber="0";
		    	}
		    	info.setEphemNumber(ephemNumber);
		    	
		    	info.setMp1(rs.getString("mp1"));
		    	info.setMp2(rs.getString("mp2"));
		    	info.setO_slps(rs.getString("o_slps"));
		    	//优化效率
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	//info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	//info.setSystemMonthDayNumer(rs.getTimestamp("systemTime").getDate()+"");
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	public List<FileInfo>  getApplyFileInfoList(String startTime,String endTime,String fileYear,String fileDayYear,
			String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,String type,String fileFlag,final boolean isYear,String year){
		StringBuffer sql = new StringBuffer();
		
		String dbYear = year;
		if(year.indexOf("-")>-1){//日度数据
			dbYear = dbYear.substring(0, 4);
		}
		sql.append("select t.id,t.site_number,t.fileName,t.fileYear,t.fileDayYear,t.fileflag,t.filePath,t.fileSize,t.fileCreateTime,t.systemTime,t.type,s.SITE_NAME,z.ZONE_NAME,ephem_number,t.mp1,t.mp2,t.o_slps  from file_info_"+dbYear+" t ,site_info s,zone_info z where 1=1 ");
		sql.append(" and s.SITE_NUMBER= t.site_number and s.SITE_ZONE=z.ZONE_CODE ");
		
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//年
		if(null!=fileYear && !"".equals(fileYear)){
			sql.append("and t.fileYear='"+fileYear+"' ");
		}
		//年积日
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}
		/*//纬度
		if(null!=smallLat && !"".equals(smallLat) && null!=bigLat && !"".equals(bigLat) ){
			sql.append("and s.site_lat>"+smallLat+" and s.site_lat<"+bigLat);
		}
		//经度
		if(null!=smallLon && !"".equals(smallLon) && null!=bigLon && !"".equals(bigLon) ){
			sql.append("and s.SITE_Lon>"+smallLon+" and s.SITE_Lon<"+bigLon);
		}*/
		//台站
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}
		
		//文件类型
		if(null!=type && !"".equals(type)){
			sql.append("and t.type='"+type+"' ");
		}
		//sql.append(" order by t.fileDayYear");
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			@SuppressWarnings("deprecation")
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	
		    	//info.setSiteName(MapAll.mapSite.get(rs.getString("site_number").toUpperCase()).getSiteName());
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setZoneName(rs.getString("ZONE_NAME"));
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else{
		    		info.setFileComp("缺失");
		    	}
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setEphemNumber(rs.getString("ephem_number"));//获得历元数量
		    	info.setMp1(rs.getString("mp1"));
		    	info.setMp2(rs.getString("mp2"));
		    	info.setO_slps(rs.getString("o_slps"));
		    	//优化效率
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	if(isYear){
		    		/*Timestamp timestamp = rs.getTimestamp("systemTime");
		    		String dayNumberOfOneYear = DayNumberOfOneYear.getDayNumberOfOneYear(timestamp);*/
		    		String fileDayYear = rs.getString("fileDayYear");
		    		if(fileDayYear.length()==2){
		    			info.setSystemMonthDayNumer("0"+fileDayYear);
		    		}else if(fileDayYear.length()==1){
		    			info.setSystemMonthDayNumer("00"+fileDayYear);
		    		}else{
		    			info.setSystemMonthDayNumer(fileDayYear);
		    		}
		    	}else{
		    		info.setSystemMonthDayNumer(rs.getTimestamp("systemTime").getDate()+"");
		    	}
		    	info.setType(rs.getString("type"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	public List<FileInfo>  excelApplyFileInfoList(String startTime,String endTime,
			String fileYear,String fileDayYear,
			String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,
			String type,
			String fileFlag,final boolean isYear,String year){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.site_number,t.fileName,t.fileYear,t.fileDayYear,t.fileflag,t.filePath,t.fileSize,t.systemTime,s.SITE_NAME,z.ZONE_NAME,ephem_number from file_info_"+year+" t ,site_info s,zone_info z where 1=1 ");
		sql.append(" and s.SITE_NUMBER= t.site_number and s.SITE_ZONE=z.ZONE_CODE ");
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}
		//年
		if(null!=fileYear && !"".equals(fileYear)){
			sql.append("and t.fileYear='"+fileYear+"' ");
		}
		//年积日
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}
		//文件类型
		if(null!=type && !"".equals(type)){
			sql.append("and t.type='"+type+"' ");
		}
		//sql.append(" order by t.fileDayYear");
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			@SuppressWarnings("deprecation")
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	
		    	//info.setSiteName(MapAll.mapSite.get(rs.getString("site_number").toUpperCase()).getSiteName());
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setZoneName(rs.getString("ZONE_NAME"));
		    	
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==3){
		    		info.setFileComp("缺失");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==4){
		    		info.setFileComp("无");
		    	}
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setEphemNumber(rs.getString("ephem_number"));//获得历元数量
		    	
		    	if(isYear){
		    		Timestamp timestamp = rs.getTimestamp("systemTime");
		    		String dayNumberOfOneYear = DayNumberOfOneYear.getDayNumberOfOneYear(timestamp);
		    		info.setSystemMonthDayNumer(dayNumberOfOneYear);
		    	}else{
		    		info.setSystemMonthDayNumer(rs.getTimestamp("systemTime").getDate()+"");
		    	}
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	//优化效率
		    	/*info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	
		    	info.setType(rs.getString("type"));*/
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	

	public List<FileInfo>  getYearDayileInfoList(String startTime,String endTime,
			String fileYear,String fileDayYear,
			String siteNumberArray, String smallLat,String bigLat,String smallLon,String bigLon,
			String type,
			String fileFlag,final boolean isYear,String year){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.fileYear,t.fileDayYear,t.site_number,t.fileName,t.filePath,t.fileSize,t.fileflag,t.fileCreateTime,t.systemTime,t.type,s.SITE_NAME ,z.ZONE_NAME from file_info_"+year+" t ,site_info s,zone_info z where 1=1 ");
		sql.append(" and s.SITE_NUMBER= t.site_number and s.SITE_ZONE=z.ZONE_CODE ");
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.systemTime between '"+startTime+"' and '"+endTime+"' ");
		}
		//年
		if(null!=fileYear && !"".equals(fileYear)){
			sql.append("and t.fileYear='"+fileYear+"' ");
		}
		//年积日
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}
		
		//文件类型
		if(null!=type && !"".equals(type)){
			sql.append("and t.type='"+type+"' ");
		}
		//sql.append(" order by t.fileDayYear");
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setZoneName(rs.getString("ZONE_NAME"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//数据申请第二步  查询
	public List<FileInfo>  getShowFileByDay(String applyId,String siteNumberArray,String zoneArray,String siteType,String fileYear){
		StringBuffer sql = new StringBuffer();
	
		String dbYear = fileYear;
		if(fileYear.indexOf("-")>-1){//日度数据
			dbYear = fileYear.substring(0, 4);
		}
		sql.append("select * from ");
		sql.append(" (");
		sql.append("select t.id,t.fileYear,t.fileDayYear,t.site_number,t.fileName,t.filePath,t.fileSize,t.fileflag,t.fileCreateTime,t.type,t.ephem_number from file_info_"+dbYear+" t ,apply_user_files a where a.fileId=t.id ");
		
		//申请id 
		if(null!=applyId && !"".equals(applyId)){
			sql.append("and a.apply_id='"+applyId+"' ");
		}
		
		/*//时间
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.systemTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ");
		}*/
		
		sql.append(" ) a, (");
		sql.append("select t.site_number,t.site_name,t.site_type, z.zone_code,z.zone_name,t.site_lat,t.SITE_Lon from site_info t ,zone_info z where t.SITE_ZONE=z.zone_code ");
		
		
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}else{
			//省份列表
			StringBuffer zoneList = new StringBuffer();
			if(null!=zoneArray && !"".equals(zoneArray)){
				String[] split = zoneArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						zoneList.append("'"+split[i]+"'");
					}else{
						zoneList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_zone in("+zoneList+") ");
			}
		}
		
		//台站类型
		if(null!=siteType && !"".equals(siteType)){
			sql.append(" and t.SITE_type in ("+siteType+") ");
		}
		sql.append(" )b where a.site_number=b.site_number order by a.site_number ");
		
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	info.setEphemNumber(rs.getString("ephem_number"));//获得历元数量
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else{
		    		info.setFileComp("缺失");
		    	}
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	info.setZoneName(rs.getString("zone_name"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//根据 申请条件  开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	public List<FileInfo>  getApplyFileByApplyId(String applyId,String siteNumberArray,String yearNumber){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.fileYear,t.fileDayYear,t.site_number,s.SITE_NAME,t.fileName,t.filePath,t.fileSize,t.fileflag,t.fileCreateTime,t.type,t.ephem_number " +
				"from file_info_"+yearNumber+" t ,site_info s,apply_user_files a where  s.SITE_NUMBER=t.site_number and a.fileId=t.id ");
		//台站
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}
		
		//申请id 
		if(null!=applyId && !"".equals(applyId)){
			sql.append("and a.apply_id='"+applyId+"' ");
		}
		
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	info.setEphemNumber(rs.getString("ephem_number"));//获得历元数量
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("补回");
		    	}else{
		    		info.setFileComp("缺失");
		    	}
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	info.setZoneName(MapAll.mapSite.get(rs.getString("site_number")).getZoneName());
		    	info.setType(rs.getString("type"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	
	
	/**
	 * 申请  文件  列表**************************************************************************************************
	 */
	@SuppressWarnings("unchecked")
	public void insert_Apply_User_Files(final int fileId,final int applyId,final String datetype) throws Exception {
		String sql = "INSERT INTO apply_user_files (fileId,apply_id,type) VALUES(?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, fileId);
				pstmt.setInt(2,applyId);
				pstmt.setString(3, datetype);
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 申请  文件列表 批量入库**************************************************************************************************
	 */
	@SuppressWarnings("unchecked")
	public void insert_Apply_User_Files(final List<FileInfo> fileList,final int applyId,final String datetype) {
		
		/*BasicDataSource basicDataSource = (BasicDataSource)jdbcTemplate.getDataSource();
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");
		basicDataSource.setDefaultAutoCommit(false);
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");*/
		
		String sql = "INSERT INTO apply_user_files (id,fileId,apply_id,type) VALUES(apply_user_files_SEQ.nextval,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
				for(int i=0; i<fileList.size(); i++){
					FileInfo info = fileList.get(i);
					prapStmt.setInt(1, info.getId());
					prapStmt.setInt(2, applyId);
					prapStmt.setString(3, datetype);
					prapStmt.addBatch(); 
					if(i%10000 == 0){
						prapStmt.executeBatch();
				    }
				}
				return prapStmt.executeBatch().length;
			}
		});
	}
	
	public void insert_Apply_User_Files2(final List<FileInfo> fileList,final int applyId,final String datetype){
		String sql = "INSERT INTO apply_user_files (fileId,apply_id,type) VALUES(?,?,?)";
	    jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {
	    
	    	public int getBatchSize() {
				return 0;
			}

			public void setValues(PreparedStatement prapStmt, int i)throws SQLException {
				prapStmt.setInt(1, fileList.get(i).getId());
				prapStmt.setInt(2, applyId);
				prapStmt.setString(3, datetype);
			}    
			
	   }); 
	   //return batchUpdate.length;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void deleteApplyFile(final String applyId,final String type) throws Exception {
		String sql = "delete from apply_user_files where apply_id=? and type=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.setString(2, type);
				pstmt.execute();
				return null;
			}
		});
	}

	public int  queryCount_ApplyFile(String applyId,String datetype) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from apply_user_files t where apply_id="+applyId +" and type="+ datetype);
		return getCount(jdbcTemplate, sql.toString());
	}
	
	public List<Apply_User_Files>  query_Apply_User_FilesList(String applyId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_user_files t where  apply_id="+applyId);
		@SuppressWarnings({ "unchecked"})
		List<Apply_User_Files> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	Apply_User_Files info = new Apply_User_Files();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileId(Integer.valueOf(rs.getString("fileId")));
		    	info.setApplyId(Integer.valueOf(rs.getString("apply_id")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	/**
	 * 单纯的 文件  列表**************************************************************************************************
	 */
	public List<FileInfo>  getFileInfoList(String fileYear,String fileDayYear,String site_number,String type,String fileFlag){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.fileYear,t.fileDayYear,t.site_number,t.fileName,t.filePath,t.fileSize,t.fileflag,t.fileCreateTime,t.type from file_info_"+fileYear+" t where 1=1 ");
		if(null!=fileYear && !"".equals(fileYear)){
			sql.append("and t.fileYear='"+fileYear+"' ");
		}
		if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}
		
		//台站
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=site_number && !"".equals(site_number)){
			String[] split = site_number.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}
		
		/*if(null!=site_number && !"".equals(site_number)){
			sql.append("and t.site_number='"+site_number+"' ");
		}*/
		if(null!=type && !"".equals(type)){
			sql.append("and t.type='"+type+"' ");
		}
		if(null!=fileFlag && !"".equals(fileFlag)){
			sql.append("and t.fileFlag='"+fileFlag+"' ");
		}
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	info.setType(rs.getString("type"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 申请台站列表**************************************************************************************************
	 */
	public List<Apply_User_SiteInfo>  query_Apply_User_SiteInfoList(String applyId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_User_SiteInfo t where apply_id="+applyId);
		@SuppressWarnings({ "unchecked"})
		List<Apply_User_SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	Apply_User_SiteInfo info = new Apply_User_SiteInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setSite_number(rs.getString("site_number"));
		    	info.setApplyId(Integer.valueOf(rs.getString("apply_id")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	public int  queryCount_Apply_User_SiteInfoList(String applyId,String datetype) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from apply_User_SiteInfo t where apply_id="+applyId+" and type="+datetype);
		return getCount(jdbcTemplate, sql.toString());
	}
	@SuppressWarnings("unchecked")
	public void deleteUserInfo(final String applyId,final String datetype) throws Exception {
		String sql = "delete from apply_User_SiteInfo where apply_id=? and type=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.setString(2,datetype);
				pstmt.execute();
				return null;
			}
		});
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert_Apply_User_SiteInfo(final String siteNumber,final String applyId,final String datetype) throws Exception {
		String sql = "INSERT INTO apply_User_SiteInfo (site_number,apply_id,type) VALUES(?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, siteNumber);
				pstmt.setString(2,applyId);
				pstmt.setString(3,datetype);
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	/**
	 * 申请  台站入库**************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert_Apply_User_SiteInfo(final List<SiteInfo> siteList,final int applyId,final String datetype) {
		
		String sql = "INSERT INTO apply_User_SiteInfo (id,site_number,apply_id,type) VALUES(apply_User_SiteInfo_SEQ.nextval,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
				for(int i=0; i<siteList.size(); i++){
					SiteInfo info = siteList.get(i);
					prapStmt.setString(1, info.getSiteNumber());
					prapStmt.setInt(2, applyId);
					prapStmt.setString(3, datetype);
					prapStmt.addBatch(); 
				}
				return prapStmt.executeBatch().length;
			}
		});
	}
	
	
	/**
	 * 申请  省份   列表**************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert_Apply_User_Zone(final String zoneCode,final int applyId) throws Exception {
		String sql = "INSERT INTO apply_User_Zone (zone_code,apply_id) VALUES(?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, zoneCode);
				pstmt.setInt(2,applyId);
				pstmt.execute();
				return null;
			}
		});
	}

	public List<Apply_User_Zone>  query_Apply_User_ZoneList(String applyId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_User_Zone t where apply_id="+applyId);
		@SuppressWarnings({ "unchecked"})
		List<Apply_User_Zone> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	Apply_User_Zone info = new Apply_User_Zone();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setZone_code(rs.getString("zone_code"));
		    	info.setApplyId(Integer.valueOf(rs.getString("apply_id")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//工具类
	@SuppressWarnings("unchecked")
	public int getCount(JdbcTemplate jdbcTemplate, String sql) throws Exception {
		Integer obj = (Integer) jdbcTemplate.query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getInt("count");
						}
						return null;
					}
				});
		return obj;
	}
	
	//根据申请id，删掉所有数据
	@SuppressWarnings("unchecked")
	public void deleteUserApply(final String applyId) throws Exception {
		String sql = "delete from apply_user where id=? ";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.execute();
				return null;
			}
		});
		
		jdbcTemplate.execute("delete from apply_user_files where apply_id=? ", new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.execute();
				return null;
			}
		});
		
		jdbcTemplate.execute("delete from apply_user_requiement where id=? ", new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.execute();
				return null;
			}
		});
		
		jdbcTemplate.execute("delete from apply_user_siteinfo where apply_id=? ", new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, applyId);
				pstmt.execute();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void updateApplyUser_FTP(final String ftpUserName,final String ftpPwd,final String applyId) throws Exception {
		String sql = "update apply_user set FtpUserName=?,FtpPwd=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				
				pstmt.setString(1, ftpUserName);
				pstmt.setString(2, ftpPwd);
				pstmt.setString(3, applyId);
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	//地震应急事件   单个详细信息
	public EarthQuake  earthQuake_detail(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from earthQuake s where id="+id);
		@SuppressWarnings({ "unchecked"})
		List<EarthQuake> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuake info = new EarthQuake();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setYear(rs.getString("year"));
		    	info.setCreateTime(rs.getTimestamp("createTime"));//
		    	info.setStrTime(TimeUtils.TimestampToString(rs.getTimestamp("createTime")));
		    	info.setName(rs.getString("name"));
		    	info.setRemark(rs.getString("remark"));//描述
		    	info.setSiteLat(rs.getString("site_lat"));//纬度
		    	info.setSiteLon(rs.getString("site_lon"));//经度
		    	info.setGrade(rs.getString("grade"));//震级
		    	info.setAddress(rs.getString("address"));//地点
		    	info.setHeight(rs.getString("height"));//震源深度
				return info;
		   }
		});
		if(null!=eventInfoList && eventInfoList.size()>0){
			return eventInfoList.get(0);
		}else{
			return null;
		}
		
	}
	
	//地震应急事件  列表
	public List<EarthQuake>  query_EarthQuake_List(String keyWords,boolean dataShowFlag,String startTime,String endTime,String year){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from earthquake s where 1=1 ");
		if(null!=keyWords && !"".equals(keyWords)){
			sql.append(" and (s.name like '%"+keyWords+"%' or s.year like '%"+keyWords+"%' or  s.remark like '%"+keyWords+"%' or s.grade like '%"+keyWords+"%' or s.site_lon like '%"+keyWords+"%' or s.site_lat like '%"+keyWords+"%')");
		}
		if(dataShowFlag){
			sql.append(" and s.year>=2017 ");
		}
		if(null!=year && !"".equals(year)){
			sql.append(" and s.year='"+year+"' ");
		}
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and s.createTime between to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  ");
		}
		sql.append(" order by s.createtime desc");
		@SuppressWarnings({ "unchecked"})
		List<EarthQuake> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuake info = new EarthQuake();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setYear(rs.getString("year"));
		    	info.setCreateTime(rs.getTimestamp("createTime"));//
		    	info.setStrTime(TimeUtils.TimestampToString(rs.getTimestamp("createTime")));
		    	info.setName(rs.getString("name"));
		    	info.setRemark(rs.getString("remark"));
		    	info.setSiteLat(rs.getString("site_lat"));//纬度
		    	info.setSiteLon(rs.getString("site_lon"));//经度
		    	info.setGrade(rs.getString("grade"));//震级
		    	info.setAddress(rs.getString("address"));//地点
		    	info.setHeight(rs.getString("height"));//震源深度
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//地震应急事件   日志目录查看
	public List<FileInfo>  query_EarthQuake_Data(String earthQuakeId,String year,String zoneArray,String departmentArray,String siteNumberArray){
		StringBuffer sql = new StringBuffer();
		sql.append("select f.site_number,f.earthQuake50Or1,f.earthQuakeDay,f.earthQuakeNum,f.fileFlag,f.fileName,f.filePath,f.fileSize from file_info_"+year+" f,site_info s where f.site_number=s.site_number ");
		
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and s.SITE_NUMBER in("+siteNumberList+") ");
		}else{
			//部委列表
			StringBuffer departmentList = new StringBuffer();
			if(null!=departmentArray && !"".equals(departmentArray)){
				String[] split = departmentArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						departmentList.append("'"+split[i]+"'");
					}else{
						departmentList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and s.SITE_DEPARTMENT in("+departmentList+") ");
			}
			
			//省份列表
			StringBuffer zoneList = new StringBuffer();
			if(null!=zoneArray && !"".equals(zoneArray)){
				String[] split = zoneArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						zoneList.append("'"+split[i]+"'");
					}else{
						zoneList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and s.SITE_zone in("+zoneList+") ");
			}
		}
		sql.append(" and f.earthQuakeId="+earthQuakeId+" and f.type=4 ");
		
		
		
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(MapAll.mapSite.get(rs.getString("site_number")).getSiteName());
		    	info.setEarthQuake50Or1(rs.getString("earthQuake50Or1"));
		    	info.setEarthQuakeDay(Integer.valueOf(rs.getString("earthQuakeDay")));
		    	info.setEarthQuakeNum(Integer.valueOf(rs.getString("earthQuakeNum")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileFlag")));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String earthQuakeId,String year,final EarthQuakeConfig earthConfig){
		/*StringBuffer sql = new StringBuffer();
		sql.append(" select a.site_number,a.site_name,a.site_lat,a.site_lon,b.counts from site_info a,(select t.site_number,  COUNT (CASE WHEN t.fileflag in (1,2) THEN 1  ELSE null END) counts  from FILE_INFO_"+year+" t where  t.type=4 and t.earthquakeid="+earthQuakeId+" group by t.site_number) b where a.site_number=b.site_number");
		
		@SuppressWarnings({ "unchecked"})
		List<SiteMapInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteMapInfo info = new SiteMapInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("site_lon"));
		    	info.setFileFlag(Integer.valueOf(rs.getString("counts")));
				return info;
		   }
		});
		return eventInfoList;*/
		

		
		/*String earthQuakeIdSql="";
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			earthQuakeIdSql = " and f.earthquakeid='"+earthQuakeId+"' ";
		}*/
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.site_number,a.site_name,a.site_lat as siteLat,a.site_lon as siteLon,b.counts,c.id,c.name,c.site_lat as eLat,c.site_lon as eLon,c.crawtype,c.crawrange,c.grade  from site_info a," +
				"(select t.site_number,t.earthquakeid,  COUNT (CASE WHEN t.fileflag in (1,2) THEN 1  ELSE null END) counts from FILE_INFO_"+year+" t where  t.type=4 and t.earthquakeid="+earthQuakeId+" group by t.site_number,t.earthquakeid) b ,EARTHQUAKE c where a.site_number=b.site_number and b.earthquakeid=c.id and  a.site_type=1 ");
		System.out.println(sql);
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
	
	/**
	 * 30S日常数据整理入库**************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert_30SData_Files(final String year,final List<FileInfo> fileList) {
		/*BasicDataSource basicDataSource = (BasicDataSource)jdbcTemplate.getDataSource();
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");
		basicDataSource.setDefaultAutoCommit(false);
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");*/
		
		String sql = "INSERT INTO file_info_"+year+" (id,fileYear,fileDayYear,site_number,fileName,filePath,fileSize,fileflag," +
				"systemTime,fileCreateTime,   type,ephem_number,    mp1,mp2,o_slps) VALUES(file_info_SEQ.nextval,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),  ?,?,?,  ?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
				for(int i=0; i<fileList.size(); i++){
					FileInfo info = fileList.get(i);
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
					
					prapStmt.addBatch(); 
					if(i%10000 == 0){
						prapStmt.executeBatch();
				    }
				}
				return prapStmt.executeBatch().length;
			}
		});
	}
	
	//通过文件名称 查询文件是否存在
	public FileInfo  select_30SData_Files(final String year,final FileInfo fileInfo){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file_info_"+year+"  where FILENAME='"+fileInfo.getFileName()+"'");
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
				return info;
		   }
		});
		if(null==eventInfoList || eventInfoList.size()==0){
			return null;
		}else{
			return eventInfoList.get(0);
		}
	}
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	@SuppressWarnings("unchecked")
	public void update_30S_apply(final String year,final FileInfo fileInfo) {
		
		String sql = "update file_info_"+year+" set ephem_number=?, fileflag=?, MP1=?,MP2=?,O_SLPS=?, FILESIZE=? where site_number=? and filedayyear = ?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, fileInfo.getEphem_number());
				pstmt.setInt(2, fileInfo.getFileFlag());
				pstmt.setString(3, fileInfo.getMp1());
				System.out.println(year+"  "+fileInfo.getMp1()+"  "+fileInfo.getFileDayYear()+"  v  "+fileInfo.getSiteNumber());
				pstmt.setString(4, fileInfo.getMp2());
				pstmt.setString(5, fileInfo.getO_slps());
				
				pstmt.setDouble(6, fileInfo.getFileSize());
				pstmt.setString(7, fileInfo.getSiteNumber());
				pstmt.setString(8, fileInfo.getFileDayYear());
				pstmt.execute();
				return null;
			}
		});
	}
	/**
	 * 地震应急数据整理*************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked" })
	public void insert_EarhtData_Files(final String year,final List<FileInfo> fileList){
		/*BasicDataSource basicDataSource = (BasicDataSource)jdbcTemplate.getDataSource();
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");
		basicDataSource.setDefaultAutoCommit(false);
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");*/
		
		String sql = "INSERT INTO file_info_"+year+" (id,fileYear,fileDayYear,site_number,fileName,filePath,fileSize,fileflag," +
				"systemTime,fileCreateTime,   type,ephem_number,    mp1,mp2,o_slps,earthQuakeId,earthQuake50Or1,earthQuakeDay,earthQuakeNum) VALUES(file_info_SEQ.nextval,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),  ?,?,?,  ?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
				for(int i=0; i<fileList.size(); i++){
					FileInfo info = fileList.get(i);
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
					prapStmt.setString(16, info.getEarthQuake50Or1());
					prapStmt.setInt(17, info.getEarthQuakeDay());
					prapStmt.setInt(18, info.getEarthQuakeNum());
					
					prapStmt.addBatch(); 
					if(i%10000 == 0){
						prapStmt.executeBatch();
				    }
				}
				return prapStmt.executeBatch().length;
			}
		});
	}
	
	//根据  台站名称、年份、数据类型   判断是否有缺失的数据  （数据定时整理用到的）
	public List<FileInfo>  queryHistoryFileInfo(String year,String fileDayYear,String siteNumber,String type,String earthQuakeId,String fileFlagSql){
		String sql = "select t.id from FILE_INFO_"+year+" t where t.site_number = '"+siteNumber+"' and t.type="+type+" and t.filedayyear='"+fileDayYear+"' "+ fileFlagSql;
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			sql+=" and t.EARTHQUAKEID ="+earthQuakeId;
		}
		
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<ApplyUser> getApplyStartAndEnd(final int applyId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from apply_user t where 1=1 and id="+applyId);
		
		
		List<ApplyUser> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ApplyUser apply = new ApplyUser();
		    	apply.setId(Integer.valueOf(rs.getString("id")));
		    	apply.setUserId(Integer.valueOf(rs.getString("userId")));
		    	
		    	apply.setApply_Unit(rs.getString("apply_Unit"));
		    	apply.setApply_Person(rs.getString("apply_Person"));
		    	apply.setApply_PersonId(rs.getString("apply_PersonId"));
		    	apply.setApply_Phone(rs.getString("apply_Phone"));
		    	apply.setAppply_Email(rs.getString("appply_Email"));
		    	
		    	apply.setResult_Name(rs.getString("result_Name"));
		    	apply.setResult_Money(rs.getString("result_Money"));
		    	apply.setResult_Person(rs.getString("result_Person"));
		    	apply.setResult_from(rs.getString("result_from"));
		    	apply.setResult_profile(rs.getString("result_profile"));
		    	
		    	apply.setResponse_person(rs.getString("response_person"));
		    	apply.setResponse_phone(rs.getString("response_phone"));
		    	
		    	apply.setCreateTime(rs.getTimestamp("createTime"));
		    	
		    	String liuCheng = rs.getString("LiuCheng");
		    	apply.setLiuCheng(liuCheng);
		    	if(liuCheng.equals("5")){
		    		apply.setLiuChengName("通过");
		    	}else if(liuCheng.equals("6")){
		    		apply.setLiuChengName("未通过");
		    	}else if(liuCheng.equals("7")){
		    		apply.setLiuChengName("过期");
		    	}
		    	
		    	apply.setApplyWorldPath(rs.getString("apply_World_Path"));
		    	apply.setRemark(rs.getString("remark"));
		    	
		    	apply.setFtpUserName(rs.getString("FtpUserName"));
		    	apply.setFtpPwd(rs.getString("FtpPwd"));
		    	

		    	apply.setApplyTime1(null==rs.getTimestamp("applyTime1")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime1")));
		    	apply.setApplyTime2(null==rs.getTimestamp("applyTime2")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime2")));
		    	apply.setApplyTime3(null==rs.getTimestamp("applyTime3")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime3")));
		    	apply.setApplyTime4(null==rs.getTimestamp("applyTime4")?"":TimeUtils.TimestampToString(rs.getTimestamp("applyTime4")));
		    	//apply.setApplyTime5(rs.getString("applyTime5"));
		    	apply.setApplyTime6(null==rs.getTimestamp("applyTime6")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime6")));
		    	//apply.setApplyTime7(rs.getString("applyTime7"));
		    	
		    	//开始和时间、结束时间
		    	apply.setApplyTime5(null==rs.getTimestamp("applyTime5")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime5")));
		    	apply.setApplyTime7(null==rs.getTimestamp("applyTime7")?"":TimeUtils.TimestampToString2(rs.getTimestamp("applyTime7")));
				return apply;
		   }
		});
		return eventInfoList;
	}
	
}
