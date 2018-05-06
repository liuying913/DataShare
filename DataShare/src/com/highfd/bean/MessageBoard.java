package com.highfd.bean;

/**
 * 留言板列表
 * @author Administrator
 *
 */
public class MessageBoard {
	
	public int id;
	public int userId;
	public String user_name;
	public String title;
	
	/**
	 * 留言内容
	 */
	public String contents;
	
	/**
	 * 浏览量
	 */
	public String browse_number;
	
	/**
	 * 回复量
	 */
	public String comment_number;
	public String contact_mobile;//联系人
	public String contact_people;
	public String orders;
	public String type;
	public String remark;
	public String createTimeStr;
	
	
	public MessageBoard() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getBrowse_number() {
		return browse_number;
	}
	public void setBrowse_number(String browseNumber) {
		browse_number = browseNumber;
	}
	public String getComment_number() {
		return comment_number;
	}
	public void setComment_number(String commentNumber) {
		comment_number = commentNumber;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contactMobile) {
		contact_mobile = contactMobile;
	}
	public String getContact_people() {
		return contact_people;
	}
	public void setContact_people(String contactPeople) {
		contact_people = contactPeople;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	

}
