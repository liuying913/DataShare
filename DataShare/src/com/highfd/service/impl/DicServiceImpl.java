package com.highfd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.ApplyValidTime;
import com.highfd.bean.DicInfo;
import com.highfd.dao.DicDao;
import com.highfd.service.DicService;
import com.highfd.controller.DicInfoController;

@Service
public class DicServiceImpl implements DicService{

	@Autowired
	DicDao dao;
	
	//特别的  放键值对的
	public void getDicMapValue() throws Exception {
		List<DicInfo> allDicList = dao.queryAllDicList();
		for(int i=0;i<allDicList.size();i++){
			DicInfo dicInfo = allDicList.get(i);
			DicInfoController.map.put(dicInfo.getDicEnName(), dicInfo);
		}
	}
	
	public void deleteDictionary(String dicCode) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteDictionary(dicCode);
	}

	public void insertDictionary(DicInfo dicInfo) throws Exception {
		// TODO Auto-generated method stub
		dao.insertDictionary(dicInfo);
	}

	public List<DicInfo> queryAllDicList() throws Exception {
		// TODO Auto-generated method stub
		return dao.queryAllDicList();
	}

	public List<DicInfo> queryDicByType(String dicType) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDicByType(dicType);
	}

	public DicInfo queryDictionaryByCode(String dicCode) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDictionaryByCode(dicCode);
	}

	public DicInfo queryDictionaryByCode(String dicType, String dicName)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDictionaryByCode(dicType, dicName);
	}

	public DicInfo queryDictionaryByOrder(String dicType, String order,
			String dicCode) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDictionaryByOrder(dicType, order, dicCode);
	}

	public DicInfo queryDictionaryBydicCnName(String dicCnName, String dicCode)
			throws Exception {
		// TODO Auto-generated method stub
		return dao.queryDictionaryBydicCnName(dicCnName, dicCode);
	}

	public void updateDictionary(DicInfo dicInfo) throws Exception {
		// TODO Auto-generated method stub
		dao.updateDictionary(dicInfo);
	}
	
	//申请有效时长
	public List<ApplyValidTime> applyValidTime() throws Exception {
		return dao.applyValidTime();
	}

}
