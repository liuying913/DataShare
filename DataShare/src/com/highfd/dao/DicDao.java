package com.highfd.dao;

import java.util.List;

import com.highfd.bean.ApplyValidTime;
import com.highfd.bean.DicInfo;

public interface DicDao {
	
	public void insertDictionary(final DicInfo dicInfo) throws Exception;

	/**
	 * 更新数据字典信息
	 */
	public void updateDictionary(final DicInfo dicInfo) throws Exception;

	/**
	 * 获得所有的数据字典信息列表
	 */
	public List<DicInfo> queryAllDicList()throws Exception;

	/**
	 * 获得某一类型的数据字典信息列表
	 */
	public List<DicInfo> queryDicByType(String dicType) throws Exception;

	/**
	 * 根据数据字典代码获取数据字典信息
	 */
	public DicInfo queryDictionaryByCode(String dicCode) throws Exception;
	/**
	 * 根据数据中文名称获取数据字典信息
	 */
	public DicInfo queryDictionaryBydicCnName(String dicCnName,String dicCode) throws Exception;
	/**
	 * 根据数据字典类型和名称查出字典的编码
	 */
	public DicInfo queryDictionaryByOrder(String dicType, String order,String dicCode)throws Exception;
	
	/**
	 * 根据数据字典类型和名称查出字典的编码
	 */
	public DicInfo queryDictionaryByCode(String dicType, String dicName)throws Exception;

	/**
	 * 删除数据字典信息
	 */
	public void deleteDictionary(final String dicCode) throws Exception;
	
	//申请有效时长
	public List<ApplyValidTime> applyValidTime() throws Exception;


}
