package com.highfd.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.bean.ApplyUser;
import com.highfd.dao.AnalysisUserDAO;

@Resource
public class AnalysisUserDAOImpl implements AnalysisUserDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//通过用户id 获得 所属的  申请id列表（出来完成的）
	@SuppressWarnings("unchecked")
	public List<ApplyUser> queryApplyUser(final String userId,final String startTime,final String endTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apply_user t where 1=1 and liucheng=5 ");
		if(null!=userId && !"".equals(userId)){
			sql.append(" and userId = "+userId);
		}
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and applyTime5 between '"+startTime+"' and '"+endTime+"' ");
		}
		
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
		    	apply.setLiuCheng(rs.getString("LiuCheng"));
		    	apply.setApplyWorldPath(rs.getString("apply_World_Path"));
		    	apply.setRemark(rs.getString("remark"));
		    	
		    	apply.setFtpUserName(rs.getString("FtpUserName"));
		    	apply.setFtpPwd(rs.getString("FtpPwd"));
		    	
		    	apply.setApplyTime1(rs.getString("applyTime1"));
		    	apply.setApplyTime2(rs.getString("applyTime2"));
		    	apply.setApplyTime3(rs.getString("applyTime3"));
		    	apply.setApplyTime4(rs.getString("applyTime4"));
		    	apply.setApplyTime5(rs.getString("applyTime5"));
		    	apply.setApplyTime6(rs.getString("applyTime6"));
		    	apply.setApplyTime7(rs.getString("applyTime7"));
				return apply;
		   }
		});
		return eventInfoList;
	}
}
