package com.highfd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.bean.DicInfo;
import com.highfd.common.map.MapAll;
import com.highfd.service.DicService;

@Component
@Controller
public class DicInfoController {
	
	@Autowired
	DicService dicService;
	
	public static Map<String,DicInfo> map = new HashMap<String,DicInfo>();
	
	//根据类别 获得列表
	@RequestMapping(value = "/getDicInfoListByType", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getDicInfoList(HttpServletRequest request) throws Exception {
		String dicType = request.getParameter("dicType");
		
		List<DicInfo> queryAllDicList = new ArrayList<DicInfo>();
		for(Entry<String, DicInfo> entry:MapAll.mapDepat.entrySet()){ 
	          //System.out.println(entry.getKey()+"--->"+entry.getValue()); 
	          queryAllDicList.add(entry.getValue());
		} 

		
		//List<DicInfo> queryAllDicList = dicService.queryDicByType(dicType);
		JSONArray json = JSONArray.fromObject(queryAllDicList);
		return json.toString();
	}
	
	
	public static void main(String[] args) {
		String p="215";
		if(p.endsWith("6")){
			System.out.println(111);
		}
	}
	
	
}