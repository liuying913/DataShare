package com.highfd.beanFile.note;

import java.sql.Timestamp;

public class NoteInfo {
	

	public int id;
	
	/**
	 * /哪个用户的消息
	 */
	public int applyId;
	public String content;//消息内容
	public Timestamp createTime;
	public String createStrTime;//消息发生时间
	
	/**
	 *  11,2);//通知 用户 有未完成的消息
			
		12,1);//通知管理员 有未审核的申请
		13,2);//通过用户有 等待审核的申请
		
		15,2);//通知 用户申请通过
		16,2);//通知 用户申请未通过
		18,1);//对于管理员来说，申请流程完毕
		
		18,2);//用户流程完毕
	 */
	public int process;
	
	/**
	 * 1未读取  2读取
	 */
	public int readFlag;
	
	/**
	 *  1管理员的通知  2普通用户的通知
	 */
	public int isManager;
	
	/**
	 * 1 30S日常数据  4地震应急数据
	 */
	public int applyFileType;
	
	public int cou;
	
	public NoteInfo() {
		super();
	}
	
	
	public NoteInfo(int isManager, int applyFileType,int process) {
		super();
		this.isManager = isManager;
		this.process = process;
		this.applyFileType = applyFileType;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateStrTime() {
		return createStrTime;
	}
	public void setCreateStrTime(String createStrTime) {
		this.createStrTime = createStrTime;
	}
	public int getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}
	public int getIsManager() {
		return isManager;
	}
	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
	public int getApplyFileType() {
		return applyFileType;
	}
	public void setApplyFileType(int applyFileType) {
		this.applyFileType = applyFileType;
	}
	public int getCou() {
		return cou;
	}
	public void setCou(int cou) {
		this.cou = cou;
	}
	
}
