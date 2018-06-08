package com.highfd.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.GreatEventInfo;
import com.highfd.dao.GreatEventDAO;
import com.highfd.service.GreatEventService;

@Service
public class GreatEventServiceImpl implements GreatEventService {
	
	@Autowired
	GreatEventDAO dao;
	
	/**
	 * 添加站点大事记
	 */
	public void insertGreatEvent(final GreatEventInfo info) throws Exception{
		dao.insertGreatEvent(info);
	}
	
	/**
	 * 超级查询站点大事记列表
	 */
	public List<GreatEventInfo>  selectSuperGreatEventList(String id,String siteNumber,String param){
		return dao.selectSuperGreatEventList(id, siteNumber, param);
	}
	/**
	 * 查询站点大事记
	 */
	public List<GreatEventInfo>  selectGreatEventList(String id,String type,String siteNumber,String param,String state){
		return dao.selectGreatEventList(id, type,siteNumber, param,state);
	}
	
	/**
	 * 修改站点大事记
	 */
	public void updateApplyUser_FTP(final GreatEventInfo info) throws Exception{
		dao.updateApplyUser_FTP(info);
	}
	
	/**
	 * 删除站点大事记
	 */
	public void deleteApplyFile(final String id) throws Exception{
		dao.deleteApplyFile(id);
	}
}
