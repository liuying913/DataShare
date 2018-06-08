package com.highfd.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highfd.beanFile.note.NoteInfo;
import com.highfd.dao.NoteInfoDao;
import com.highfd.service.NoteInfoService;

@Service
public class NoteInfoServiceImpl implements NoteInfoService {
	
	@Autowired
	NoteInfoDao dao;
	
	//获得消息列表
	public List<NoteInfo>  getNoteInfoList(boolean userType,int userId,String userName, String noteId,String noteFlag,String type){
		return dao.getNoteInfoList( userType,userId, userName, noteId, noteFlag, type);
	}
	
	//获得消息数量
	public int  queryCount_NoteInfo(String userId,String id,String readFlag,String type)throws Exception{
		return dao.queryCount_NoteInfo(userId, id, readFlag, type);
	}
	//创建消息
	public void insertNoteInfo(final NoteInfo info) throws Exception {
		dao.insertNoteInfo(info);
	}
	//删除通知
	public void deleteUserInfo(final NoteInfo info) throws Exception {
		dao.deleteUserInfo(info);
	}
	//修改消息
	public void updateNoteInfo(final NoteInfo noteInfo) throws Exception {
		dao.updateNoteInfo(noteInfo);
	}
	
	//获得 左侧菜单 消息分组数量
	public List<NoteInfo>  getLeftMenuNoteNumber(boolean userType,int userId,String userName,String readFlag){
		return dao.getLeftMenuNoteNumber(userType,userId, userName, readFlag);
	}
	
	//修改为已阅读
	public void updateNoteReadFlag(final String userId,final String applyFiletype) throws Exception {
		dao.updateNoteReadFlag(userId, applyFiletype);
	}

}
