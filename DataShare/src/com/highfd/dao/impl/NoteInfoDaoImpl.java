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
import com.highfd.beanFile.note.NoteInfo;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.NoteInfoDao;
/**
 * DAO层
 * @author liuying 2016-12-10 01:15
 * update  liuying 2017-05-19 00:04
 *
 */
@Resource
public class NoteInfoDaoImpl implements NoteInfoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	//获得消息列表
	public List<NoteInfo>  getNoteInfoList(boolean userType,int userId,String userName, String noteId,String readFlag,String applyfiletype){
		StringBuffer sql = new StringBuffer();
		sql.append("select n.id,n.applyid,n.applyfiletype,n.content,n.process,n.ismanager,n.readflag,n.createtime " +
				"from APPLY_USER t,user_info u,note_info n where t.userid=u.user_id and n.applyid=t.id and (");
		
		if(userType){//管理员
			sql.append(" n.ismanager=1 or ");
		}
		sql.append(" (u.user_id='"+userId+"' and n.ismanager=2 ) or (u.user_name='"+userName+"' and n.ismanager=2 )     ) ");
		
		
		if(null!=noteId && !"".equals(noteId)){//消息Id
			sql.append(" and n.id="+noteId);
		}
		
		
		if(null!=readFlag && !"".equals(readFlag)){//是否阅读
			sql.append(" and n.readflag= "+readFlag);
		}
		/*if(null!=applyfiletype && !"".equals(applyfiletype)){//数据类型
			sql.append(" and n.applyfiletype="+applyfiletype);
		}*/
		sql.append(" union ");
		
		
		sql.append("select n.id,n.applyid,n.applyfiletype,n.content,n.process,n.ismanager,n.readflag,n.createtime " +
		"from EARTHQUAKE_APPLY t,user_info u,note_info n where t.user_id=u.user_id and n.applyid=t.id and (");

		if(userType){//管理员
			sql.append(" n.ismanager=1 or ");
		}
		sql.append(" (u.user_id='"+userId+"' and n.ismanager=2 ) or (u.user_name='"+userName+"' and n.ismanager=2 )     ) ");
		
		
		if(null!=noteId && !"".equals(noteId)){//消息Id
			sql.append(" and n.id="+noteId);
		}
		
		
		if(null!=readFlag && !"".equals(readFlag)){//是否阅读
			sql.append(" and n.readflag= "+readFlag);
		}
		/*if(null!=applyfiletype && !"".equals(applyfiletype)){//数据类型
			sql.append(" and n.applyfiletype="+applyfiletype);
		}*/

		@SuppressWarnings({ "unchecked"})
		List<NoteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	NoteInfo info = new NoteInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setApplyId(Integer.valueOf(rs.getString("applyid")));
		    	info.setApplyFileType(Integer.valueOf(rs.getString("applyfiletype")));
		    	info.setContent(rs.getString("content"));
		    	info.setProcess(Integer.valueOf(rs.getString("process")));
		    	info.setCreateStrTime(TimeUtils.TimestampToString(rs.getTimestamp("createTime")));
		    	info.setReadFlag(Integer.valueOf(rs.getString("readflag")));
		    	info.setIsManager(Integer.valueOf(rs.getString("ismanager")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//获得消息数量
	public int  queryCount_NoteInfo(String applyId,String id,String readFlag,String type) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from note_info t where 1=1");
		if(null!=id && !"".equals(id)){
			sql.append(" and t.id="+id);
		}
		if(null!=readFlag && !"".equals(readFlag)){//流程状态
			sql.append(" and t.readFlag="+readFlag);
		}
		if(null!=type && !"".equals(type)){//类型
			sql.append(" and t.type="+type);
		}//
		if(null!=applyId && !"".equals(applyId)){//申请id
			sql.append(" and t.applyId="+applyId);
		}
		return getCount(jdbcTemplate, sql.toString());
	}

	
	//创建消息
	@SuppressWarnings("unchecked")
	public void insertNoteInfo(final NoteInfo info) throws Exception {
		String sql = "INSERT INTO note_info (id,applyId,content,createTime,readFlag,ismanager,applyFileType,process)VALUES(note_info_SEQ.nextval,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, info.getApplyId());
				pstmt.setString(2, info.getContent());
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				if(info.getReadFlag()==2){
					pstmt.setInt(4, 2);//是否阅读
				}else{
					pstmt.setInt(4, 1);//是否阅读
				}
				
				pstmt.setInt(5,info.getIsManager());// 1管理员的通知  2普通用户的通知
				pstmt.setInt(6,info.getApplyFileType());//1 30S日常数据  4地震应急数据
				pstmt.setInt(7, info.getProcess());//流程
				pstmt.execute();
				return null;
			}
		});
	}
	

	//删除通知
	@SuppressWarnings("unchecked")
	public void deleteUserInfo(final NoteInfo info) throws Exception {
		String sql = "delete note_info where applyId=? and　process=? and applyfiletype=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, info.getApplyId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	//修改消息
	@SuppressWarnings("unchecked")
	public void updateNoteInfo(final NoteInfo noteInfo) throws Exception {
		String sql = "update note_info set content=?,PROCESS=?,READFLAG=?,createTime=? where APPLYFILETYPE=? and ismanager=? and applyId=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, noteInfo.getContent());
				pstmt.setInt(2, noteInfo.getProcess());
				pstmt.setInt(3, noteInfo.getReadFlag());
				pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				
				pstmt.setInt(5, noteInfo.getApplyFileType());
				pstmt.setInt(6, noteInfo.getIsManager());
				pstmt.setInt(7, noteInfo.getApplyId());
				pstmt.execute();
				return null;
			}
		});
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
	
	
	//获得 左侧菜单 消息分组数量
	public List<NoteInfo>  getLeftMenuNoteNumber(boolean userType,int userId,String userName,String readFlag){
		StringBuffer sql = new StringBuffer();
		sql.append("select n.applyfiletype,n.process,n.ismanager ,count(*) as cou " +
				"from APPLY_USER t,user_info u,note_info n where t.userid=u.user_id and n.applyid=t.id and (");
		
		if(userType){//管理员
			sql.append(" n.ismanager=1 or ");
		}
		sql.append(" (u.user_id='"+userId+"' and n.ismanager=2 ) or (u.user_name='"+userName+"' and n.ismanager=2 )     ) ");
		
		
		
		if(null!=readFlag && !"".equals(readFlag)){//是否阅读
			sql.append(" and n.readflag= "+readFlag);
		}
		/*if(null!=applyfiletype && !"".equals(applyfiletype)){//数据类型
			sql.append(" and n.applyfiletype="+applyfiletype);
		}*/
		sql.append(" group by n.applyfiletype,n.process,n.ismanager");
		sql.append(" union ");
		
		
		sql.append("select n.applyfiletype,n.process ,n.ismanager,count(*) as cou  " +
		"from EARTHQUAKE_APPLY t,user_info u,note_info n where t.user_id=u.user_id and n.applyid=t.id and (");

		if(userType){//管理员
			sql.append(" n.ismanager=1 or ");
		}
		sql.append(" (u.user_id='"+userId+"' and n.ismanager=2 ) or (u.user_name='"+userName+"' and n.ismanager=2 )     ) ");
		
		if(null!=readFlag && !"".equals(readFlag)){//是否阅读
			sql.append(" and n.readflag= "+readFlag);
		}
		/*if(null!=applyfiletype && !"".equals(applyfiletype)){//数据类型
			sql.append(" and n.applyfiletype="+applyfiletype);
		}*/
		sql.append(" group by n.applyfiletype,n.process,n.ismanager");
		@SuppressWarnings({ "unchecked"})
		List<NoteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	NoteInfo info = new NoteInfo();
		    	info.setApplyFileType(Integer.valueOf(rs.getString("applyfiletype")));
		    	info.setProcess(Integer.valueOf(rs.getString("process")));
		    	info.setIsManager(Integer.valueOf(rs.getString("ismanager")));
		    	info.setCou(Integer.valueOf(rs.getString("cou")));
		    	//info.setReadFlag(1);
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//修改为已阅读
	@SuppressWarnings("unchecked")
	public void updateNoteReadFlag(final String userId,final String applyFiletype) throws Exception {
		String sql ="";
		if(applyFiletype.equals("1")){
			sql = "update note_info n set n.readflag=2 where n.id in(select t.id from NOTE_INFO t ,APPLY_USER a      where t.applyid=a.id and t.process in(5,6) and a.userid="+userId+")";
		}else if(applyFiletype.equals("4")){
			sql = "update note_info n set n.readflag=2 where n.id in(select t.id from NOTE_INFO t ,EARTHQUAKE_APPLY a where t.applyid=a.id and t.process in(5,6)  and a.user_id="+userId+")";
		}
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.execute();
				return null;
			}
		});
	}
}