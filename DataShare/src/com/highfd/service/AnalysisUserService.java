package com.highfd.service;

import java.util.List;
import com.highfd.bean.ApplyUser;

public interface AnalysisUserService {

	//通过用户id 获得 所属的  申请id列表（出来完成的）
	public List<ApplyUser> queryApplyUser(final String userId,final String startTime,final String endTime);
}
