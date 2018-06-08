package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;


import com.highfd.bean.ApplyValidTime;
import com.highfd.bean.DicInfo;
import com.highfd.dao.DicDao;

@Resource
public class DicDaoImpl implements DicDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 添加数据字典信息
	 */

	@SuppressWarnings("unchecked")
	public void insertDictionary(final DicInfo dicInfo) throws Exception {
		String sql = "INSERT INTO DIC_INFO (dic_code,dic_cn_name,dic_en_name,dic_order,dic_type_code)"
				+ "VALUES(?,?,?,?,?)";

		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, dicInfo.getDicCode());
				pstmt.setString(2, dicInfo.getDicCnName());
				pstmt.setString(3, dicInfo.getDicEnName());
				pstmt.setString(4, dicInfo.getDicOrder());
				pstmt.setString(5, dicInfo.getDicTypeCode());
				pstmt.execute();
				return null;
			}
		});
	}

	/**
	 * 更新数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public void updateDictionary(final DicInfo dicInfo) throws Exception {
		String sql = "update DIC_INFO set dic_cn_name=?,dic_en_name=?,dic_order=?,dic_type_code=? where dic_code=?";
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
		String sql = "select * from DIC_INFO";
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
	public List<DicInfo> queryDicByType(String dicTypeCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dic_info t where 1=1 and t.dic_code =18 and t.dic_cn_name='国家测绘地理信息局' and t.dic_en_name='depart0001' and t.dic_type_code='dic-001'");
		sql.append("select * from dic_info t ");
		if(null!=dicTypeCode && !"".equals(dicTypeCode)){
			sql.append(" where t.DIC_TYPE_CODE ='"+dicTypeCode+"'");
		}
		List<DicInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	DicInfo dic = new DicInfo();
		    	dic.setDicCode(rs.getString("dic_code"));
				dic.setDicCnName(rs.getString("dic_cn_name"));
				dic.setDicEnName(rs.getString("dic_en_name"));
				dic.setDicOrder(rs.getString("dic_order"));
				dic.setDicTypeCode(rs.getString("dic_type_code"));
				return dic;
		   }
		});
		return eventInfoList;
	}

	/**
	 * 根据数据字典代码获取数据字典信息
	 */
	@SuppressWarnings("unchecked")
	public DicInfo queryDictionaryByCode(String dicCode) throws Exception {
		String sql = "select * from DIC_INFO where dic_code = ?";
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
		String sql = "select * from DIC_INFO where dic_cn_name = ? and dic_code != ?";
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
		String sql = "select * from DIC_INFO where dic_type_code=? and dic_order = ? and dic_code != ?";
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
		String sql = "select * from DIC_INFO where dic_type_code=? and dic_cn_name = ?";
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
		String sql = "delete DIC_INFO where dic_code=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, dicCode);
				pstmt.execute();
				return null;
			}
		});
	}

	//申请有效时长
	@SuppressWarnings("unchecked")
	public List<ApplyValidTime> applyValidTime() throws Exception {
		String sql = "select * from sys_apply_validTime";
		List<ApplyValidTime> dicList = (List<ApplyValidTime>) jdbcTemplate.query(sql,new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						ApplyValidTime dic = new ApplyValidTime();
						dic.setId(rs.getInt("id"));
						dic.setDays(rs.getInt("days"));
						return dic;
					}
				});
		return dicList;
	}

	
	
}
