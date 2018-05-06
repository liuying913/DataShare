package com.highfd.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.UserInfo;
import com.highfd.common.map.MapAll;
import com.highfd.dao.ApplyFileDAO;
import com.highfd.dao.UserDAO;
import com.highfd.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO dao;
	
	@Autowired
	ApplyFileDAO applyDao;
	
	/*//map键值对
	public void userMap() throws IOException{
		List<UserInfo> list = dao.queryAllUserInfoList(-1,"");
		for(UserInfo info:list){
			MapAll.mapUser.put(info.getUserId(), info);
			if(info.getUserType()==1){//管理员
				info.setFtpRealUserPath(MapAll.FTPLocalPath+"\\"+MapAll.userFtpSpaces);//ftp  绝对路径
				info.setFtpRelativePath("/"+MapAll.userFtpSpaces);//ftp  相对路径
			}else{
				info.setFtpRealUserPath(MapAll.FTPLocalPath+"\\"+MapAll.userFtpSpaces+"\\"+info.getUserName());
				info.setFtpRelativePath("/"+MapAll.userFtpSpaces+"/"+info.getUserName());
			}
			System.out.println(info.getUserName()+"  "+info.getFtpRealUserPath()+"     "+info.getFtpRelativePath());
			MapAll.mapUserName.put(info.getUserName(), info);
		}
	}*/
	
	//FTP 每个用户所对应的  文件权限
	public void userMap(){
		
		List<UserInfo> list = dao.queryAllUserInfoList(-1,"");
		
		for(UserInfo info:list){
			
			String pathType="/";
			if("win".equals(MapAll.winOrLinux)){pathType="\\";}
			
			MapAll.mapUser.put(info.getUserId(), info);
			if(info.getUserType()==1){//管理员
				info.setFtpRealUserPath(MapAll.FTPLocalPath+pathType+MapAll.userFtpSpaces);//ftp  绝对路径
				info.setFtpRelativePath("/"+MapAll.userFtpSpaces);//ftp  相对路径
			}else{
				info.setFtpRealUserPath(MapAll.FTPLocalPath+pathType+MapAll.userFtpSpaces+pathType+info.getUserName());
				info.setFtpRelativePath("/"+MapAll.userFtpSpaces+"/"+info.getUserName());
			}
			MapAll.mapUserName.put(info.getUserName(), info);
		}
	}
	
	
	
	public int insertUserInfo(final UserInfo userInfo) throws Exception{
		return dao.insertUserInfo(userInfo);
	}
	
	
	//设置默认头像
	public void setUserIniImg(HttpServletRequest request,final String userID) throws Exception{
		setUserImg(request,userID,"avatar.jpg");
		setUserImg(request,userID,"avatar_large.jpg");
		setUserImg(request,userID,"avatar_small.jpg");
	}
	//设置头像调用的方法
	public void setUserImg(HttpServletRequest request,final String userID,String imgName) throws Exception{
 		String currentDirPath = request.getSession().getServletContext().getRealPath("/");
 		String tempUserImg =  "/users/"+imgName;
 		File dest = new File(currentDirPath+"uploads"+tempUserImg);
		BufferedImage buff = ImageIO.read(dest);
		String pathnameOrig = "/users/" + userID + "/"+imgName;
		storeImage(request,buff, "jpg", pathnameOrig);
	}
	
	//复制头像
	public void storeImage(HttpServletRequest request,BufferedImage image, String formatName,String filename) throws IOException {
		String currentDirPath = request.getSession().getServletContext().getRealPath("/");
	 	File dest = new File(currentDirPath+"uploads"+filename);
		File parent = dest.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		ImageIO.write(image, formatName, dest);
	}
	
	
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo(final UserInfo userInfo,String str) throws Exception{
		dao.updateUserInfo(userInfo, str);
	}
	
	/**
	 * 更新用户基本信息
	 */
	public void updateUserInfo2(final UserInfo userInfo,String str) {
		dao.updateUserInfo2(userInfo, str);
	}

	/**
	 * 获得所有用户信息
	 */
	public List<UserInfo>  queryAllUserInfoList(final int userId,final String userParam){
		return dao.queryAllUserInfoList(userId,userParam);
	}
	
	
	

	/**
	 * 根据ID获取用户信息
	 */
	public UserInfo queryUserInfo(final int id) throws Exception{
		return dao.queryUserInfo(id);
	}
	
	public UserInfo queryUserInfoByParam(final String userField, final String userParams) throws Exception{
		return dao.queryUserInfoByParam(userField, userParams);
	}
	

	/**
	 * 根据用户名获取用户信息
	 */
	public UserInfo queryUserInfo(final String userName) throws Exception{
		return dao.queryUserInfo(userName);
	}

	/**
	 * 根据用户名和用户密码获取用户信息
	 */
	public UserInfo queryUserInfo(String userName, String userPwd)throws Exception{
		return dao.queryUserInfo(userName, userPwd);
	}

	/**
	 * 删除用户信息
	 */
	public void deleteUserInfo(final int id) throws Exception{
		dao.deleteUserInfo(id);
	}

	/**
	 * 获取用户记录数
	 */
	public int queryUserInfoTotal() throws Exception{
		return dao.queryUserInfoTotal();
	}


	public String querySiteNumber(final int userId) throws Exception{
		return dao.querySiteNumber(userId);
	}
	
	public void updateUserType(final int userId,final int userType) throws Exception{
		dao.updateUserType(userId, userType);
	}
	
	public String queryUserType(final int userId) throws Exception{
		return dao.queryUserType(userId);
	}
	
	
}
