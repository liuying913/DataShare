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
import com.highfd.bean.DocInfo;
import com.highfd.common.map.MapAll;
import com.highfd.dao.DocDAO;

@Resource
public class DocDAOImpl implements DocDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	/**
	 * 添加文档信息
	 */
	@SuppressWarnings("unchecked")
	public void insertDocInfo(final DocInfo docInfo) throws Exception {
		String sql = "INSERT INTO doc_use_info (doc_id, user_id,doc_name,doc_url,doc_desc,doc_date,DOC_ORDER)"
				+ "VALUES(DOC_INFO_SEQ.nextval,?,?,?,?,?,?)";
		
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1,    docInfo.getUserId());
				pstmt.setString(2, docInfo.getDocName());
				pstmt.setString(3, docInfo.getDocUrl());
				pstmt.setString(4, docInfo.getDocDesc());
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(6, docInfo.getDocOrder());
				pstmt.execute();
				return null;
			}
		});
	}

	/**
	 * 更新文档信息
	 */
	@SuppressWarnings("unchecked")
	public void updateDocInfo(final DocInfo docInfo) throws Exception {
		String sql = "update DOC_USE_INFO set user_id=?,doc_name=?,doc_url=?,doc_desc=?,doc_date=?,DOC_ORDER=? where doc_id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, docInfo.getUserId());
				pstmt.setString(2, docInfo.getDocName());
				pstmt.setString(3, docInfo.getDocUrl());
				pstmt.setString(4, docInfo.getDocDesc());
				pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(6, docInfo.getDocOrder());
				pstmt.setInt(7, docInfo.getDocId());
				pstmt.execute();
				return null;
			}
		});
	}

	/**
	 * 获得某一基准站的所有文档
	 */
	@SuppressWarnings("unchecked")
	public List<DocInfo> queryDocInfoBySiteNumber(String userId) throws Exception {
		String sql = "select * from DOC_USE_INFO where user_id=?";
		String[] user_id = new String[] { userId };
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql,
				user_id, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setUserId(rs.getInt("user_id"));
						if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId()).getUserCName());}
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocDate(rs.getTimestamp("doc_date"));
						return doc;
					}
				});
		return dicList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocInfo> queryAllDocInfo(int userId,String keyWords) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from doc_use_info where user_id !=-1 ");
		if(userId==-1){//管理员
		}else{
			sql.append(" and user_Id="+userId);
		}
		
		if(null!=keyWords && !"".equals(keyWords)){
			sql.append(" and (DOC_NAME like '%"+keyWords+"%' or DOC_URL like '%"+keyWords+"%' or  DOC_DESC like '%"+keyWords+"%')");
		}
		sql.append(" order by doc_date desc");
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql.toString(),new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setUserId(rs.getInt("user_id"));
						if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId())==null?"":MapAll.mapUser.get(doc.getUserId()).getUserCName());}
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocStrDate(rs.getDate("doc_date")+"");
						doc.setDocDate(rs.getTimestamp("doc_date"));
						return doc;
					}
				});
		return dicList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DocInfo> queryAllDocInfo(String keyWords) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from doc_use_info where 1=1 and user_id = -1 ");
		
		if(null!=keyWords && !"".equals(keyWords)){
			sql.append(" and (DOC_NAME like '%"+keyWords+"%' or DOC_URL like '%"+keyWords+"%' or  DOC_DESC like '%"+keyWords+"%')");
		}
		sql.append(" order by DOC_ORDER ");
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql.toString(),new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setUserId(rs.getInt("user_id"));
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocStrDate(rs.getDate("doc_date")+"");
						doc.setDocDate(rs.getTimestamp("doc_date"));
						doc.setDocOrder(rs.getString("DOC_ORDER"));
						return doc;
					}
				});
		return dicList;
	}
	
	//用户申请查看第四步，查看该用户本年度上传的成果
	@SuppressWarnings("unchecked")
	public List<DocInfo> docInfoListThisYear(int applyId,String keyWords) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select t.userid,to_char(r.s_starttime,'yyyy') as years from APPLY_USER t,APPLY_USER_REQUIEMENT r where t.id=r.applyid and t.id="+applyId+") a,(select d.*,to_char(d.doc_date,'yyyy') as years from doc_use_info d where 1=1 ) b where a.years = b.years and a.userid=b.user_id");
		if(null!=keyWords && !"".equals(keyWords)){
			sql.append(" and (DOC_NAME like '%"+keyWords+"%' or DOC_URL like '%"+keyWords+"%' or  DOC_DESC like '%"+keyWords+"%')");
		}
		
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql.toString(),new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setUserId(rs.getInt("user_id"));
						if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId()).getUserCName());}
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocStrDate(rs.getDate("doc_date")+"");
						doc.setDocDate(rs.getTimestamp("doc_date"));
						return doc;
					}
				});
		return dicList;
	}

	/**
	 * 获得系统的所有文档
	 */
	@SuppressWarnings("unchecked")
	public List<DocInfo> querySysDocInfoList(int pageNo, int pageSize)
			throws Exception {
		String sql = "select * from DOC_USE_INFO where user_id is null";
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql,new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setUserId(rs.getInt("user_id"));
						if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId()).getUserCName());}
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocDate(rs.getTimestamp("doc_date"));
						return doc;
					}
				});
		return dicList;
	}

	/**
	 * 根据文档ID获取文档信息
	 */
	@SuppressWarnings("unchecked")
	public DocInfo queryDocInfo(final int id) throws Exception {
		String sql = "select * from DOC_USE_INFO where doc_id = ?";
		Integer[] code = new Integer[] { id };
		DocInfo obj = (DocInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DocInfo doc = new DocInfo();
							doc.setDocId(rs.getInt("doc_id"));
							doc.setUserId(rs.getInt("user_id"));
							if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId()).getUserCName());}
							doc.setDocName(rs.getString("doc_name"));
							doc.setDocUrl(rs.getString("doc_url"));
							doc.setDocDesc(rs.getString("doc_desc"));
							doc.setDocDate(rs.getTimestamp("doc_date"));
							return doc;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 根据文档名称获取文档信息
	 */
	@SuppressWarnings("unchecked")
	public DocInfo queryDocInfo(String docName) throws Exception {
		String sql = "select * from DOC_USE_INFO where doc_name=?";
		String[] code = new String[] { docName };
		DocInfo obj = (DocInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							DocInfo doc = new DocInfo();
							doc.setDocId(rs.getInt("doc_id"));
							doc.setUserId(rs.getInt("user_id"));
							if(-1!=doc.getUserId()){doc.setUserName(MapAll.mapUser.get(doc.getUserId()).getUserCName());}
							doc.setDocName(rs.getString("doc_name"));
							doc.setDocUrl(rs.getString("doc_url"));
							doc.setDocDesc(rs.getString("doc_desc"));
							doc.setDocDate(rs.getTimestamp("doc_date"));
							return doc;
						}
						return null;
					}
				});
		return obj;
	}

	/**
	 * 删除文档信息
	 */
	@SuppressWarnings("unchecked")
	public void deleteDocInfo(final int id) throws Exception {
		String sql = "delete from DOC_USE_INFO where doc_id=?";
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
	 * 获取文档记录数
	 */
	public int queryDocInfoTotal(String user_id) throws Exception {
		String sql = "select count(*) as count from DOC_USE_INFO where user_id='"
				+ user_id + "'";
		return this.getCount(jdbcTemplate, sql);
	}

	/**
	 * 获取系统文档记录数
	 */
	public int querySysDocInfoTotal() throws Exception {
		String sql = "select count(*) as count from DOC_USE_INFO where user_id is null";
		return this.getCount(jdbcTemplate, sql);
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
	
	
	//获得  用户 应用软件
	@SuppressWarnings("unchecked")
	public List<DocInfo> queryDocUseInfoList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from doc_use_info where user_id=-1 ");
		List<DocInfo> dicList = (List<DocInfo>) jdbcTemplate.query(sql.toString(),new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)throws SQLException {
						DocInfo doc = new DocInfo();
						doc.setDocId(rs.getInt("doc_id"));
						doc.setDocName(rs.getString("doc_name"));
						doc.setDocUrl(rs.getString("doc_url"));
						doc.setDocDesc(rs.getString("doc_desc"));
						doc.setDocStrDate(rs.getDate("doc_date")+"");
						doc.setDocDate(rs.getTimestamp("doc_date"));
						return doc;
					}
				});
		return dicList;
	}
	
}
