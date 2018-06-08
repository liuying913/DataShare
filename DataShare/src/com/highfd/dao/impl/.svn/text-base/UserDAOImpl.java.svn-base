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
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import com.highfd.bean.UserInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.UserDAO;

@Resource
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 添加用户信息
	 */
	@SuppressWarnings("unchecked")
	public int insertUserInfo(final UserInfo userInfo) throws Exception {
		final int userNextId = getNextId("USER_INFO_SEQ");
		String sql = "INSERT INTO user_info (user_id,user_name,user_pwd,user_cName,user_gender,user_phone,user_email,user_regDate,USER_AREA_CODE,USER_DEPARTMENT,USER_TYPE,USER_ENABLE,USER_MOBILE,user_unit,user_IDS,user_img)"
			+ "VALUES("+userNextId+",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback(){
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, userInfo.getUserName());
				pstmt.setString(2, "123456");
				pstmt.setString(3, userInfo.getUserCName());
				pstmt.setString(4, userInfo.getUserGender());
				pstmt.setString(5, userInfo.getUserPhone());
				pstmt.setString(6, userInfo.getUserEmail());
				pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(8, userInfo.getUserAreaCode());
				pstmt.setString(9, userInfo.getUserDepartment());
//				pstmt.setInt(10, userInfo.getUserType());
//				pstmt.setInt(11, userInfo.getUserEnable());
				pstmt.setInt(10, 2);
				pstmt.setInt(11, 1);
				pstmt.setString(12, userInfo.getUserMobile());
				pstmt.setString(13, userInfo.getUserUnit());
				pstmt.setString(14, userInfo.getUserIDS());
				pstmt.setString(15, "avatar_large.jpg");
				pstmt.execute();
				return userNextId;
			}
		});
		return userNextId;
	}
	
	/**
	 * 更新用户基本信息
	 */
	@SuppressWarnings("unchecked")
	public void updateUserInfo2(final UserInfo userInfo,String str) {
		String sql = "update user_info set user_cName=?,user_gender=?,user_phone=?,user_mobile=?,user_email=?,user_updateDate=?,USER_AREA_CODE=?,USER_DEPARTMENT=?,user_unit=?,user_IDS=?,user_type=?  where user_id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) {
				try {
					pstmt.setString(1, userInfo.getUserCName());
					pstmt.setString(2, userInfo.getUserGender());
					pstmt.setString(3, userInfo.getUserPhone());
					pstmt.setString(4, userInfo.getUserMobile());
					pstmt.setString(5, userInfo.getUserEmail());
					pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
					pstmt.setString(7, userInfo.getUserAreaCode());
					pstmt.setString(8, userInfo.getUserDepartment());
					pstmt.setString(9, userInfo.getUserUnit());
					pstmt.setString(10, userInfo.getUserIDS());
					pstmt.setString(11, userInfo.getUserTypeStr().equals("是")?"1":"2");
					pstmt.setInt(12, userInfo.getUserId());
					MapAll.logger.error("用户修改1111界面："+userInfo.getUserName()+"  "+userInfo.getUserGender());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				try {
					pstmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MapAll.logger.error("1  "+e.getMessage());
					MapAll.logger.error("2  "+e.getSQLState());
					MapAll.logger.error("3  "+e.toString());
				}
				MapAll.logger.error("用户修改2222界面："+userInfo.getUserName()+"  "+userInfo.getUserGender());
				return null;
			}
		});
	}
	/**
	 * 更新用户基本信息
	 */
	@SuppressWarnings("unchecked")
	public void updateUserInfo(final UserInfo userInfo,String str) {
		MapAll.logger.error("用户修改界面：dao");
		if("upd".equals(str)){
			String sql = "update user_info set user_cName=?,user_gender=?,user_phone=?,user_mobile=?,user_email=?,user_updateDate=?,USER_AREA_CODE=?,USER_DEPARTMENT=?,user_unit=?,user_IDS=?  where user_id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt) {
					try {
						pstmt.setString(1, userInfo.getUserCName());
						pstmt.setString(2, userInfo.getUserGender());
						pstmt.setString(3, userInfo.getUserPhone());
						pstmt.setString(4, userInfo.getUserMobile());
						pstmt.setString(5, userInfo.getUserEmail());
						pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
						pstmt.setString(7, userInfo.getUserAreaCode());
						pstmt.setString(8, userInfo.getUserDepartment());
						pstmt.setString(9, userInfo.getUserUnit());
						pstmt.setString(10, userInfo.getUserIDS());
						pstmt.setInt(11, userInfo.getUserId());
						MapAll.logger.error("用户修改1111界面："+userInfo.getUserName()+"  "+userInfo.getUserGender());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
					try {
						pstmt.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MapAll.logger.error("1  "+e.getMessage());
						MapAll.logger.error("2  "+e.getSQLState());
						MapAll.logger.error("3  "+e.toString());
					}
					MapAll.logger.error("用户修改2222界面："+userInfo.getUserName()+"  "+userInfo.getUserGender());
					return null;
				}
			});
		}else if("pas".equals(str)) {
			String sql = "update user_info set user_pwd=? where user_id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setString(1,userInfo.getNewUserPwd());
					pstmt.setInt(2, userInfo.getUserId());
					pstmt.execute();
					return null;
				}
			});
		}else if("enable".equals(str)) {
			String sql = "update user_info set user_Enable=0 where user_id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setInt(1, userInfo.getUserId());
					pstmt.execute();
					return null;
				}
			});
		}else if("img".equals(str)) {
			
			String sql = "update user_info set USER_IMG=? where user_id=?";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setString(1,userInfo.getImgPath());
					pstmt.setInt(2, userInfo.getUserId());
					pstmt.execute();
					return null;
				}
			});
		}

	}

	/**
	 * 获得所有用户信息
	 */
	public List<UserInfo>  queryAllUserInfoList(final int userId,final String userParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from user_info t where t.user_enable=1 ");
		
		if(userId==-1){//管理员
		}else{
			sql.append(" and user_Id="+userId);
		}
		
		if(null!=userParam && !"".equals(userParam)){
			sql.append("and (t.user_name like '%"+userParam+"%'  " +
					
					"or t.user_cName like '%"+userParam+"%'  " +
					"or t.user_gender like '%"+userParam+"%'  " +
					"or t.user_phone like '%"+userParam+"%'  " +
					"or t.user_mobile like '%"+userParam+"%'  " +
					"or t.user_email like '%"+userParam+"%'  " +
					"or t.user_cName like '%"+userParam+"%'  " +
					
					"or t.user_cName like '%"+userParam+"%'  " +
					"or t.user_unit like '%"+userParam+"%')");
		}
		sql.append(" order by t.user_regdate desc ");
		@SuppressWarnings({ "unchecked"})
		List<UserInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	UserInfo user = new UserInfo();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPwd(rs.getString("user_pwd"));
				user.setUserCName(rs.getString("user_cName"));
				user.setUserGender(rs.getString("user_gender"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserMobile(rs.getString("user_mobile"));
				user.setUserEmail(rs.getString("user_email"));
				//user.setUserRegDate(rs.getTimestamp("user_regDate"));
				user.setRegDateStr(TimeUtils.TimestampToString2(rs.getTimestamp("user_regDate")));
				user.setUserType(rs.getInt("user_type"));
				user.setUserUnit(rs.getString("user_unit"));
				user.setUserIDS(rs.getString("user_IDS"));
				user.setImgPath(rs.getString("user_img"));//头像
				return user;
		   }
		});
		return eventInfoList;
	}
	
	
	

	/**
	 * 根据ID获取用户信息
	 */
	@SuppressWarnings("unchecked")
	public UserInfo queryUserInfo(final int id) throws Exception {
		String sql = "select * from user_info where user_id = ?";
		Integer[] code = new Integer[] { id };
		UserInfo obj = (UserInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							UserInfo user = new UserInfo();
							user.setUserId(rs.getInt("user_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPwd(rs.getString("user_pwd"));
							user.setUserCName(rs.getString("user_cName"));
							user.setUserGender(rs.getString("user_gender"));
							user.setUserPhone(rs.getString("user_phone"));
							user.setUserMobile(rs.getString("user_mobile"));
							user.setUserEmail(rs.getString("user_email"));
							user.setUserRegDate(rs.getTimestamp("user_regDate"));
							user.setUserDepartment(rs.getString("user_department"));//所属部委
							user.setUserAreaCode(rs.getString("user_area_code"));//所属区域
							user.setUserType(rs.getInt("user_type"));
							user.setUserUnit(rs.getString("user_unit"));
							user.setUserIDS(rs.getString("user_IDS"));
							user.setRegDateStr(TimeUtils.TimestampToString(rs.getTimestamp("user_regDate")));
							user.setUserTypeStr(rs.getInt("user_type")==1?"是":"否");
							user.setImgPath(rs.getString("USER_IMG"));
							return user;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 根据用户名获取用户信息
	 */
	@SuppressWarnings("unchecked")
	public UserInfo queryUserInfo(final String userName) throws Exception {
		String sql = "select * from user_info where user_name = ?";
		String[] code = new String[] { userName };
		UserInfo obj = (UserInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							UserInfo user = new UserInfo();
							user.setUserId(rs.getInt("user_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPwd(rs.getString("user_pwd"));
							user.setUserCName(rs.getString("user_cName"));
							user.setUserGender(rs.getString("user_gender"));
							user.setUserPhone(rs.getString("user_phone"));
							user.setUserMobile(rs.getString("user_mobile"));
							user.setUserEmail(rs.getString("user_email"));
							user.setUserRegDate(rs.getTimestamp("user_regDate"));
							user.setUserUnit(rs.getString("user_unit"));
							user.setUserIDS(rs.getString("user_IDS"));
							return user;
						}
						return null;
					}
				});
		return obj;
	}
	
	
	@SuppressWarnings("unchecked")
	public UserInfo queryUserInfoByParam(final String userField, final String userParams) throws Exception {
		String sql = "select * from user_info where "+userField+" = ?";
		String[] code = new String[] { userParams };
		UserInfo obj = (UserInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							UserInfo user = new UserInfo();
							user.setUserId(rs.getInt("user_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPwd(rs.getString("user_pwd"));
							user.setUserCName(rs.getString("user_cName"));
							user.setUserGender(rs.getString("user_gender"));
							user.setUserPhone(rs.getString("user_phone"));
							user.setUserMobile(rs.getString("user_mobile"));
							user.setUserEmail(rs.getString("user_email"));
							user.setUserRegDate(rs.getTimestamp("user_regDate"));
							user.setUserUnit(rs.getString("user_unit"));
							user.setUserIDS(rs.getString("user_IDS"));
							return user;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 根据用户名和用户密码获取用户信息
	 */
	@SuppressWarnings("unchecked")
	public UserInfo queryUserInfo(String userName, String userPwd) throws Exception {
		String sql = "select * from user_info where user_name = ? and user_pwd=?";
		String[] code = new String[] { userName, userPwd };
		UserInfo obj = (UserInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)throws SQLException, DataAccessException {
						if (rs.next()) {
							UserInfo user = new UserInfo();
							user.setUserId(rs.getInt("user_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPwd(rs.getString("user_pwd"));
							user.setUserCName(rs.getString("user_cname"));
							user.setUserGender(rs.getString("user_gender"));
							user.setUserPhone(rs.getString("user_phone"));
							user.setUserEmail(rs.getString("user_email"));
							user.setUserMobile(rs.getString("user_mobile"));
							user.setUserRegDate(rs.getTimestamp("user_regDate"));
							user.setUserAreaCode(rs.getString("USER_AREA_CODE"));
							user.setUserDepartment(rs.getString("USER_DEPARTMENT"));
							user.setUserType(rs.getInt("user_type"));
							user.setUserUnit(rs.getString("user_unit"));
							user.setUserIDS(rs.getString("user_IDS"));
							user.setImgPath("/DataShare/uploads/users/"+rs.getString("user_id")+"/avatar_large.jpg");
							return user;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 删除用户信息
	 */
	@SuppressWarnings("unchecked")
	public void deleteUserInfo(final int id) throws Exception {
		String sql = "delete user_info where user_id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, id);
				pstmt.execute();
				return null;
			}
		});
	}

	/**
	 * 获取用户记录数
	 */
	public int queryUserInfoTotal() throws Exception {
		String sql = "select count(*) as count from user_info";
		return getCount(jdbcTemplate, sql);
	}
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

	@SuppressWarnings("unchecked")
	public String querySiteNumber(final int userId) throws Exception {
		String sql = "select SITE_NUMBER from USER_SITEINFO WHERE USER_ID="+userId+"";
		String siteNumber = (String) jdbcTemplate.query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("SITE_NUMBER");
						}
						return null;
					}
				});
		return siteNumber;
	}
	
	@SuppressWarnings("unchecked")
	public void updateUserType(final int userId,final int userType) throws Exception {
		String sql = "update user_info set user_type=? where user_id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, userType);
				pstmt.setInt(2, userId);
				pstmt.execute();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserType(final int userId) throws Exception {
		String sql = "select USER_TYPE from USER_INFO WHERE USER_ID="+userId+"";
		String siteNumber = (String) jdbcTemplate.query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("USER_TYPE");
						}
						return null;
					}
				});
		return siteNumber;
	}

	
	public int getNextId(final String seq) throws Exception {
		String sql2 = "select "+seq+".nextval from dual";
		return getNextID(jdbcTemplate, sql2);
	}
	@SuppressWarnings("unchecked")
	public int getNextID(JdbcTemplate jdbcTemplate, String sql)throws Exception {
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
}
