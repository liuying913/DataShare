package com.highfd.service;

import java.util.List;

import com.highfd.bean.MessageBoard;
import com.highfd.bean.MessageBoardTotal;
import com.highfd.bean.MessageComment;

public interface MessageService {
	
	/**
	 * 查询留言板列表  前10条
	 */
	public List<MessageBoard>  boardTop10(String num);
	/**
	 * 查询留言板列表
	 */
	public List<MessageBoard>  getMessageBoardList(String id,String param);
	
	/**
	 * 添加留言板
	 */
	public void insertMessageBoard(final MessageBoard info) throws Exception;
	
	/**
	 * 查询留言板列表  评论
	 */
	public List<MessageComment>  getMessageCommentList(String boardId,String parentid,String type,String param);
	
	/**
	 * 添加留言板 评论
	 */
	public void insertMessageComment(final MessageComment info) throws Exception;
	
	/**
	 * 统计留言板总量
	 */
	public MessageBoardTotal  getMessageBoardTotal(Boolean monthFlag);
	
	//修改留言板 大   访问量
	public void updateMessageBoard(final String id,final String browse_number) throws Exception;
	
	//修改留言板 大   访问量
	public void updateMessageComment(final String id,final String comment_number) throws Exception;
	
	//删除留言板（非评论）
	public Integer deleteBoard(String commentid) throws Exception;
	
	//删除留言板评论
	public Integer deleteComment(final String board_id,final String commentid) throws Exception;
	
	public Integer deleteParentid(final String commentid) throws Exception;
	
}
