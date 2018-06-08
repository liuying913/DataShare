package com.highfd.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.highfd.bean.UserInfo;

public interface UserService {
	
	public int insertUserInfo(final UserInfo userInfo) throws Exception;
	
	//设置默认头像
	public void setUserIniImg(HttpServletRequest request,final String userID) throws Exception;

	//复制头像
	public void storeImage(HttpServletRequest request,BufferedImage image, String formatName,String filename) throws IOException;
		
	/*//map键值对
	public void userMap() throws IOException;*/
	
	//FTP 每个用户所对应的  文件权限
	public void userMap();
	
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo(final UserInfo userInfo,String str) throws Exception;
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo2(final UserInfo userInfo,String str);
	/**
	 * 获得所有用户信息
	 */
	public List<UserInfo>  queryAllUserInfoList(final int userId,final String userParam);
	
	
	

	/**
	 * 根据ID获取用户信息
	 */
	public UserInfo queryUserInfo(final int id) throws Exception;
	
	public UserInfo queryUserInfoByParam(final String userField, final String userParams) throws Exception;

	/**
	 * 根据用户名获取用户信息
	 */
	public UserInfo queryUserInfo(final String userName) throws Exception;

	/**
	 * 根据用户名和用户密码获取用户信息
	 */
	public UserInfo queryUserInfo(String userName, String userPwd)
			throws Exception;

	/**
	 * 删除用户信息
	 */
	public void deleteUserInfo(final int id) throws Exception ;

	/**
	 * 获取用户记录数
	 */
	public int queryUserInfoTotal() throws Exception;


	public String querySiteNumber(final int userId) throws Exception;
	
	public void updateUserType(final int userId,final int userType) throws Exception;
	
	public String queryUserType(final int userId) throws Exception;
}
