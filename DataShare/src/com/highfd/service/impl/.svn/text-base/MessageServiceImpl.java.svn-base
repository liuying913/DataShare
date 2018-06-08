package com.highfd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.MessageBoard;
import com.highfd.bean.MessageBoardTotal;
import com.highfd.bean.MessageComment;
import com.highfd.dao.MessageDAO;
import com.highfd.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageDAO dao;
	

	
	/**
	 * 查询留言板列表  前10条
	 */
	public List<MessageBoard>  boardTop10(String num){
		return dao.boardTop10(num);
	}
	/**
	 * 查询留言板列表
	 */
	public List<MessageBoard>  getMessageBoardList(String id,String param){
		return dao.getMessageBoardList(id, param);
	}
	
	/**
	 * 添加留言板
	 */
	public void insertMessageBoard(final MessageBoard info) throws Exception{;
		dao.insertMessageBoard(info);
	}
	
	/**
	 * 查询留言板列表  评论
	 */
	public List<MessageComment>  getMessageCommentList(String boardId,String parentid,String type,String param){
		return dao.getMessageCommentList(boardId,parentid, type, param);
	}
	
	/**
	 * 添加留言板 评论
	 */
	public void insertMessageComment(final MessageComment info) throws Exception{
		dao.insertMessageComment(info);
	}
	
	/**
	 * 统计留言板总量
	 */
	public MessageBoardTotal  getMessageBoardTotal(Boolean monthFlag){
		return dao.getMessageBoardTotal(monthFlag);
	}
	
	//修改留言板 大   访问量
	public void updateMessageBoard(final String id,final String browse_number) throws Exception {
		dao.updateMessageBoard(id, browse_number);
	}
	
	//修改留言板 大   访问量
	public void updateMessageComment(final String id,final String comment_number) throws Exception {
		dao.updateMessageComment(id, comment_number);
	}
	
	//删除留言板（非评论）
	public Integer deleteBoard(String id) throws Exception {
		return dao.deleteBoard(id);
	}
	
	//删除留言板 评论
	public Integer deleteComment(String board_id,String commentid) throws Exception {
		return dao.deleteComment(board_id,commentid);
	}
	
	public Integer deleteParentid(String commentid) throws Exception {
		return dao.deleteParentid(commentid);
	}
	
}
