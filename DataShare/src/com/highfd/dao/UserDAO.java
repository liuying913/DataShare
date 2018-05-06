package com.highfd.dao;

import java.util.List;

import com.highfd.bean.UserInfo;

public interface UserDAO {
	
	public int insertUserInfo(final UserInfo userInfo) throws Exception;
	
	
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo(final UserInfo userInfo,String str) throws Exception;

	/**
	 * 获得所有用户信息
	 */
	public List<UserInfo>  queryAllUserInfoList(final int userId,final String userParam);
	
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo2(final UserInfo userInfo,String str);
	

	/**
	 * 根据ID获取用户信息
	 */
	public UserInfo queryUserInfo(final int id) throws Exception;

	/**
	 * 根据用户名获取用户信息
	 */
	public UserInfo queryUserInfo(final String userName) throws Exception;
	
	public UserInfo queryUserInfoByParam(final String userField, final String userParams) throws Exception;

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
