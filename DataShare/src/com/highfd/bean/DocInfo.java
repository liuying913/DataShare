package com.highfd.bean;

import java.sql.Timestamp;

/**
 * <p>
 * 文档信息实体类
 * </p>
 * @version 1.0
 * @see
 */
public class DocInfo implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6007393636832115377L;

	/**
	 * 主键
	 */
	private int docId;

	/**
	 * 基准站编号
	 */
	private int userId;
	private String userName;

	/**
	 * 文档名称
	 */
	private String docName;

	/**
	 * 文档路径
	 */
	private String docUrl;

	/**
	 * 文档描述
	 */
	private String docDesc;

	/**
	 * 上传日期
	 */
	private Timestamp docDate;
	private String docStrDate;
	
	/**
	 * 文档排序
	 */
	private String docOrder;
	
	/**
	 * @return the docId
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @param docName
	 *            the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * @return the docUrl
	 */
	public String getDocUrl() {
		return docUrl;
	}

	public String getDocStrDate() {
		return docStrDate;
	}

	public void setDocStrDate(String docStrDate) {
		this.docStrDate = docStrDate;
	}

	/**
	 * @param docUrl
	 *            the docUrl to set
	 */
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	/**
	 * @return the docDesc
	 */
	public String getDocDesc() {
		return docDesc;
	}

	/**
	 * @param docDesc
	 *            the docDesc to set
	 */
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	
	
	public Timestamp getDocDate() {
		return docDate;
	}

	public void setDocDate(Timestamp docDate) {
		this.docDate = docDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDocOrder() {
		return docOrder;
	}

	public void setDocOrder(String docOrder) {
		this.docOrder = docOrder;
	}
	
	

	
}
