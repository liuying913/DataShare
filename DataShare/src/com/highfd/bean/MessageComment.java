package com.highfd.bean;

public class MessageComment {
	
	public int id;
	
	/**
	 * 留言板id
	 */
	public String board_id;
	public String user_id;
	
	/**
	 * 对谁说的
	 */
	public String face_user_id;
	public String contents;
	
	/**
	 * 1:评论第一级  2:评论第二级
	 */
	public String type;
	public String createTimeStr;
	public String remark;
	
	public String parentId;
	public String imgPath;
	
	public MessageComment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBoard_id() {
		return board_id;
	}

	public void setBoard_id(String boardId) {
		board_id = boardId;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getFace_user_id() {
		return face_user_id;
	}

	public void setFace_user_id(String faceUserId) {
		face_user_id = faceUserId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	
}
