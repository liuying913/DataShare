package com.highfd.dao;

import java.util.List;
import com.highfd.beanFile.note.NoteInfo;
/**
 * DAO层
 * @author Douban73
 */
public interface NoteInfoDao {

	//获得消息列表
	public List<NoteInfo>  getNoteInfoList(boolean userType,int userId,String userName, String noteId,String noteFlag,String type);
	
	//获得消息数量
	public int  queryCount_NoteInfo(String userId,String id,String readFlag,String type)throws Exception;
	
	//创建消息
	public void insertNoteInfo(final NoteInfo info) throws Exception;
	
	//删除通知
	public void deleteUserInfo(final NoteInfo info) throws Exception;
	
	//修改消息
	public void updateNoteInfo(final NoteInfo noteInfo) throws Exception;
	
	
	//获得 左侧菜单 消息分组数量
	public List<NoteInfo>  getLeftMenuNoteNumber(boolean userType,int userId,String userName,String readFlag);
	
	//修改为已阅读
	@SuppressWarnings("unchecked")
	public void updateNoteReadFlag(final String userId,final String applyFiletype) throws Exception;
	
}
