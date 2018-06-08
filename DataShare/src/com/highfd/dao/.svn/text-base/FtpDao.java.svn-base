package com.highfd.dao;

import java.util.List;

import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.FTPApplyYear;
import com.highfd.bean.FileInfo;

public interface FtpDao {
	
	
	/**
	 * 网页FTP查看时，获取该用户的文件列表
	 */
	public List<FileInfo> queryAllFilesByUserName(String userName,String filePath);

	/**
	 * 通过用户id 查询改用户申请的30S数据id 和 应急数据ID
	 */
	public List<FileInfo>  queryFtpApplyYear(String userId,String type30S,String typeEarthQuake,String filePath) throws Exception;
	
	/**
	 * 通过用户id 查询改用户申请的30S数据id 和 应急数据ID
	 */
	public List<FTPApplyYear>  queryFtpApplyYear(String userId,String type30S,String typeEarthQuake);
	
	/**
	 * **************查询用户的   ftp权限文件******************************************************
	 */
	public List<FileInfo> applyFileList30SAndEarthQuake(List<FTPApplyYear> list,String filePath ) throws Exception;
	
	/**
	 * Ftp下载文件入库
	 */
	public void insertDictionary(final DownHistoryInfo info);
}
