package com.highfd.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highfd.bean.DocInfo;
import com.highfd.dao.DocDAO;
import com.highfd.service.DocService;

@Service
public class DocServiceImpl implements DocService {
	
	@Autowired
	DocDAO dao;
	
	public void insertDocInfo(final DocInfo docInfo) throws Exception{
		dao.insertDocInfo(docInfo);
	}

	/**
	 * 更新文档信息
	 */
	public void updateDocInfo(final DocInfo docInfo) throws Exception{
		dao.updateDocInfo(docInfo);
	}

	/**
	 * 获得某一基准站的所有文档
	 */
	public List<DocInfo> queryDocInfoBySiteNumber(String siteNumber) throws Exception{
		return dao.queryDocInfoBySiteNumber(siteNumber);
	}
	public List<DocInfo> queryAllDocInfo(int userId,String keyWords) throws Exception {
		return dao.queryAllDocInfo(userId, keyWords);
	}
	
	public List<DocInfo> queryAllDocInfo(String keyWords) throws Exception {
		return dao.queryAllDocInfo(keyWords);
	}
	//用户申请查看第四步，查看该用户本年度上传的成果
	public List<DocInfo> docInfoListThisYear(int applyId,String keyWords) throws Exception {
		return dao.docInfoListThisYear(applyId, keyWords);
	}
	/**
	 * 获得系统的所有文档
	 */
	public List<DocInfo> querySysDocInfoList(int pageNo, int pageSize)throws Exception{
		return dao.querySysDocInfoList(pageNo, pageSize);
	}

	/**
	 * 根据文档ID获取文档信息
	 */
	public DocInfo queryDocInfo(final int id) throws Exception{
		return dao.queryDocInfo(id);
	}

	/**
	 * 根据文档名称获取文档信息
	 */
	public DocInfo queryDocInfo(String docName) throws Exception{
		return dao.queryDocInfo(docName);
	}

	/**
	 * 删除文档信息
	 */
	public void deleteDocInfo(final int id) throws Exception{
		dao.deleteDocInfo(id);
	}

	/**
	 * 获取文档记录数
	 */
	public int queryDocInfoTotal(String siteNumber) throws Exception{
		return dao.querySysDocInfoTotal();
	}

	/**
	 * 获取系统文档记录数
	 */
	public int querySysDocInfoTotal() throws Exception{
		return dao.querySysDocInfoTotal();
	}
	
	//获得  用户 应用软件
	public List<DocInfo> queryDocUseInfoList() throws Exception {
		return dao.queryDocUseInfoList();
	}
}
