package com.highfd.service;

import java.util.List;

import com.highfd.bean.GreatEventInfo;

public interface GreatEventService {
	
	/**
	 * 添加站点大事记
	 */
	public void insertGreatEvent(final GreatEventInfo info) throws Exception;
	
	/**
	 * 超级查询站点大事记列表
	 */
	public List<GreatEventInfo>  selectSuperGreatEventList(String id,String siteNumber,String param);
	
	/**
	 * 查询站点大事记
	 */
	public List<GreatEventInfo>  selectGreatEventList(String id,String type,String siteNumber,String param,String state);
	
	/**
	 * 修改站点大事记
	 */
	public void updateApplyUser_FTP(final GreatEventInfo info) throws Exception;
	
	/**
	 * 删除站点大事记
	 */
	public void deleteApplyFile(final String id) throws Exception;
}
