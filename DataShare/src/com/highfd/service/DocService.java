package com.highfd.service;

import java.util.List;
import com.highfd.bean.DocInfo;

public interface DocService {
	
	public void insertDocInfo(final DocInfo docInfo) throws Exception;

	/**
	 * 更新文档信息
	 */
	public void updateDocInfo(final DocInfo docInfo) throws Exception;

	/**
	 * 获得某一基准站的所有文档
	 */
	public List<DocInfo> queryDocInfoBySiteNumber(String siteNumber) throws Exception;
	
	public List<DocInfo> queryAllDocInfo(int userId,String keyWords) throws Exception;
	
	public List<DocInfo> queryAllDocInfo(String keyWords) throws Exception;
	
	//用户申请查看第四步，查看该用户本年度上传的成果
	public List<DocInfo> docInfoListThisYear(int applyId,String keyWords) throws Exception;

	/**
	 * 获得系统的所有文档
	 */
	public List<DocInfo> querySysDocInfoList(int pageNo, int pageSize)throws Exception;

	/**
	 * 根据文档ID获取文档信息
	 */
	public DocInfo queryDocInfo(final int id) throws Exception;

	/**
	 * 根据文档名称获取文档信息
	 */
	public DocInfo queryDocInfo(String docName) throws Exception;

	/**
	 * 删除文档信息
	 */
	public void deleteDocInfo(final int id) throws Exception;

	/**
	 * 获取文档记录数
	 */
	public int queryDocInfoTotal(String siteNumber) throws Exception;

	/**
	 * 获取系统文档记录数
	 */
	public int querySysDocInfoTotal() throws Exception;
	
	//获得  用户 应用软件
	public List<DocInfo> queryDocUseInfoList() throws Exception;
	
}
