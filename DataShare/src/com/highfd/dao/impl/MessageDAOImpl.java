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
import com.highfd.bean.MessageBoard;
import com.highfd.bean.MessageBoardTotal;
import com.highfd.bean.MessageComment;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.MessageDAO;
/**
 * 留言板
 * @author Administrator
 *
 */
@Resource
public class MessageDAOImpl implements MessageDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 查询留言板列表  前10条
	 */
	public List<MessageBoard>  boardTop10(String num){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select rownum as rnum, t.id,t.user_id,u.user_name,t.title,t.contents,t.browse_number,t.comment_number,t.contact_mobile,t.contact_people,t.orders,t.type,t.remark,t.createtime " +
				"from MESSAGE_BOARD t,user_info u where u.user_id=t.user_id order by t.createtime desc) a where a.rnum<="+num);
		
		@SuppressWarnings({ "unchecked"})
		List<MessageBoard> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	MessageBoard info = new MessageBoard();
		    	info.setId(rs.getInt("id"));
		    	info.setUserId(rs.getInt("user_id"));
		    	info.setUser_name(rs.getString("user_name"));
		    	info.setTitle(rs.getString("title"));
		    	info.setContents(rs.getString("contents"));
		    	info.setBrowse_number(rs.getString("browse_number"));
		    	info.setComment_number(rs.getString("comment_number"));
		    	info.setContact_mobile(rs.getString("contact_mobile"));
		    	info.setContact_people(rs.getString("contact_people"));
		    	info.setOrders(rs.getString("orders"));
		    	info.setType(rs.getString("type"));
		    	info.setRemark(rs.getString("remark"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createtime")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 查询留言板列表
	 */
	public List<MessageBoard>  getMessageBoardList(String id,String param){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.user_id,u.user_name,t.title,t.contents,t.browse_number,t.comment_number,t.contact_mobile,t.contact_people,t.orders,t.type,t.remark,t.createtime from MESSAGE_BOARD t,user_info u where u.user_id=t.user_id ");
		
		if(null!= id  && !"".equals(id)){sql.append( " and t.id= "+id);}
		if(null!=param && !"".equals(param)){
			sql.append(" and (u.user_name like '%"+param+"%' or t.title like '%"+param+"%' or t.contents like '%"+param +"%' or t.contact_people like '%"+param +"%' or t.contact_mobile like '%"+param+"%')");
		}
		sql.append(" order by t.createtime desc");
		
		@SuppressWarnings({ "unchecked"})
		List<MessageBoard> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	MessageBoard info = new MessageBoard();
		    	info.setId(rs.getInt("id"));
		    	info.setUserId(rs.getInt("user_id"));
		    	info.setUser_name(rs.getString("user_name"));
		    	info.setTitle(rs.getString("title"));
		    	info.setContents(rs.getString("contents"));
		    	info.setBrowse_number(rs.getString("browse_number"));
		    	info.setComment_number(rs.getString("comment_number"));
		    	info.setContact_mobile(rs.getString("contact_mobile"));
		    	info.setContact_people(rs.getString("contact_people"));
		    	info.setOrders(rs.getString("orders"));
		    	info.setType(rs.getString("type"));
		    	info.setRemark(rs.getString("remark"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createtime")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 添加留言板
	 */
	@SuppressWarnings("unchecked")
	public void insertMessageBoard(final MessageBoard info) throws Exception {
		String sql = "INSERT INTO MESSAGE_BOARD (id,user_id,title,contents,browse_number,comment_number,contact_mobile,contact_people,orders,type,createtime )"
			+ "VALUES(MESSAGEBOARD_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, info.getUserId());
				pstmt.setString(2, info.getTitle());
				pstmt.setString(3, info.getContents());
				pstmt.setString(4, "0");
				pstmt.setString(5, "0");
				pstmt.setString(6, info.getContact_mobile());
				pstmt.setString(7, info.getContact_people());
				pstmt.setString(8, info.getOrders());
				pstmt.setString(9, info.getType());
				pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 查询留言板列表  评论
	 * @param siteType
	 * @param siteParam
	 * @return
	 */
	public List<MessageComment>  getMessageCommentList(String boardId,String parentid, String type,String param){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.board_id,t.user_id,t.face_user_id,t.contents,t.type,t.createtime,t.remark,t.parentid from MESSAGE_COMMENT t where 1=1 ");
		
		if(null!= boardId  && !"".equals(boardId)){sql.append( " and t.board_id= "+boardId +" and t.parentid is null ");}
		if(null!= parentid  && !"".equals(parentid)){sql.append( " and t.parentid= "+parentid);}
		if(null!= type  && !"".equals(type)){sql.append( " and t.type= "+type);}
		if(null!=param && !"".equals(param)){
			sql.append(" and (u.user_name like '%"+param+"%' or t.title like '%"+param+"%' or t.contents like '%"+param +"%' or t.contact_people like '%"+param +"%' or t.contact_mobile like '%"+param+"%')");
		}
		
		if(null!= boardId  && !"".equals(boardId)){sql.append( " order by t.createtime desc ");}
		if(null!= parentid  && !"".equals(parentid)){sql.append( " order by t.createtime ");}
		@SuppressWarnings({ "unchecked"})
		List<MessageComment> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	MessageComment info = new MessageComment();
		    	info.setId(rs.getInt("id"));
		    	info.setBoard_id(rs.getString("board_id"));
		    	info.setUser_id(rs.getString("user_id")==null?"":MapAll.mapUser.get(Integer.valueOf(rs.getString("user_id"))).getUserCName());
		    	info.setFace_user_id(rs.getString("face_user_id")==null?"":MapAll.mapUser.get(Integer.valueOf(rs.getString("user_id"))).getUserCName());
		    	//info.setImgPath(rs.getString("user_id")==null?"":MapAll.mapUser.get(Integer.valueOf(rs.getString("user_id"))).getImgPath());
		    	info.setImgPath("/DataShare/uploads/users/"+rs.getString("user_id")+"/avatar_large.jpg");
		    	info.setContents(rs.getString("contents"));
		    	info.setType(rs.getString("type"));
		    	info.setRemark(rs.getString("remark"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createtime")));
		    	info.setParentId(rs.getString("parentid"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 添加留言板 评论
	 */
	@SuppressWarnings("unchecked")
	public void insertMessageComment(final MessageComment info) throws Exception {
		String sql = "INSERT INTO MESSAGE_COMMENT (id,board_id,user_id,face_user_id,contents,type,createtime,remark,parentid)"
			+ "VALUES(MESSAGEBOARD_SEQ.nextval,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, info.getBoard_id());
				pstmt.setString(2, info.getUser_id());
				pstmt.setString(3, info.getFace_user_id());
				pstmt.setString(4, info.getContents());
				pstmt.setString(5, info.getType());
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(7, info.getRemark());
				pstmt.setString(8, info.parentId);
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	
	/**
	 * 统计留言板总量
	 */
	public MessageBoardTotal  getMessageBoardTotal(Boolean monthFlag){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as cou ,sum(t.comment_number) as sumCom,sum(t.browse_number) as sumBro from MESSAGE_BOARD t where 1=1 ");
		if(monthFlag){
			sql.append(" and t.createtime  between to_date('"+TimeUtils.thisMonthFirstDaate()+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and to_date('"+TimeUtils.thisMonthLastDate()+" 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		@SuppressWarnings({ "unchecked"})
		List<MessageBoardTotal> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	MessageBoardTotal info = new MessageBoardTotal();
		    	info.setCou(rs.getString("cou"));
		    	info.setSumCom(rs.getString("sumCom"));
		    	info.setSumBro(rs.getString("sumBro"));
				return info;
		   }
		});
		
		MessageBoardTotal info2 = new MessageBoardTotal();
		info2.setCou("0");
    	info2.setSumCom("0");
    	info2.setSumBro("0");
		return eventInfoList.get(0)==null?info2:eventInfoList.get(0);
	}
	
	
	//修改留言板 大   访问量
	@SuppressWarnings("unchecked")
	public void updateMessageBoard(final String id,final String browse_number) throws Exception {
		String sql = "update MESSAGE_BOARD set browse_number=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, browse_number);
				pstmt.setString(2, id);
				pstmt.execute();
				return null;
			}
		});
	}
	
	//修改留言板 大   访问量
	@SuppressWarnings("unchecked")
	public void updateMessageComment(final String id,final String comment_number) throws Exception {
		String sql = "update MESSAGE_BOARD set comment_number=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, comment_number);
				pstmt.setString(2, id);
				pstmt.execute();
				return null;
			}
		});
	}
	
	//删除留言板（非评论）
	public Integer deleteBoard(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append( "delete MESSAGE_BOARD t where t.id = ?");
		Object[] args = {id};
		return jdbcTemplate.update(sql.toString(),args);
	}

	//ParentId为评论所对应的二级回复
	//想要删除二级回复根据commentid找到对应的comment记录
	public Integer deleteComment(String boardId,String commentid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append( "delete MESSAGE_COMMENT t where t.board_id = ? and t.id = ?");
		Object[] args = {boardId,commentid};
		return jdbcTemplate.update(sql.toString(),args);
	}

	public Integer deleteParentid(String commentid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append( "delete MESSAGE_COMMENT t where t.id = ?");
		Object[] args = {commentid};
		return jdbcTemplate.update(sql.toString(),args);
	}
	
}
