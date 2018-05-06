package com.highfd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.ApplyUser;
import com.highfd.dao.AnalysisUserDAO;

import com.highfd.service.AnalysisUserService;


@Service
public class AnalysisUserServiceImpl implements AnalysisUserService {
	
	@Autowired
	AnalysisUserDAO dao;
	
	//通过用户id 获得 所属的  申请id列表（出来完成的）
	public List<ApplyUser> queryApplyUser(final String userId,final String startTime,final String endTime) {
		return dao.queryApplyUser(userId, startTime, endTime);
	}
}
