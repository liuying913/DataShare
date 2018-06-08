package com.highfd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.highfd.bean.UserInfo;
import com.highfd.beanFile.note.NoteInfo;
import com.highfd.service.NoteInfoService;

@Component
@Controller
@RequestMapping(value="/note")
public class NoteInfoController {

	@Autowired
	NoteInfoService noteInfoService;

	//获得通知列表
	@RequestMapping(value = "getNoteInfoList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getNoteInfoList(HttpServletRequest request) {
		String id = request.getParameter("id");//消息id
		String readFlag = request.getParameter("readFlag");//是否阅读
		int userId = 0;//用户id
		
		/**
		1,2);//通知 用户 有未完成的消息
	      
		4,1);//通知 管理员 有未审核的申请
		4,2);//通知 用户 等待审核的申请

		5,2);//通知 用户 申请通过
		6,2);//通知 用户 申请未通过
		
		7   2过期了
		*/
		
		boolean userType = true;//是否是管理员
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		userId = userInfo.getUserId();
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			userType = false;
		}
		
		List<NoteInfo> noteInfoList = noteInfoService.getNoteInfoList(userType,userId, "",id, "1", "");
		JSONArray json = JSONArray.fromObject(noteInfoList);
		return json.toString();
	}
	
	//获得通知数量
	@RequestMapping(value = "queryCount_NoteInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String queryCount_NoteInfo(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");//消息id
		String readFlag = request.getParameter("readFlag");//是否阅读
		int userId = 0;//用户id
		boolean userType = true;//是否是管理员
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		userId = userInfo.getUserId();
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			userType = false;
		}
		List<NoteInfo> noteInfoList = noteInfoService.getNoteInfoList(userType,userId,"", id, "1", "");
		return "{\"date\":[{\"code\":\""+noteInfoList.size()+"\",\"msg\":\"消息数量\"}]}";
	}
	
	
	//获得通知列表
	@RequestMapping(value = "getLeftMenuNoteNumber", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String getLeftMenuCount(HttpServletRequest request) {
		String readFlag = request.getParameter("readFlag");//是否阅读
		int userId = 0;//用户id
		boolean userType = true;//是否是管理员
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		userId = userInfo.getUserId();
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			userType = false;
		}
		List<NoteInfo> noteInfoList = noteInfoService.getLeftMenuNoteNumber(userType,userId, "", "1");
		
		Map<String,NoteInfo> map = new HashMap<String,NoteInfo>();
		
		NoteInfo iniNote = new NoteInfo();
		iniNote.setCou(0);
		
		map.put("211", new NoteInfo(2,1,1));//未完成
		map.put("214",  new NoteInfo(2,1,4));map.put("244",  new NoteInfo(2,4,4));//待审核
		map.put("219",  new NoteInfo(2,1,9));map.put("249",  new NoteInfo(2,4,9));//审核通过 + 审核不通过
		
		map.put("114",  new NoteInfo(1,1,4));map.put("144",  new NoteInfo(1,4,4));//管理员通知待审核
		
		map.put("991",  new NoteInfo(9,9,1));map.put("992",  new NoteInfo(9,9,2));//大菜单上面的
		//管理员才有的审核
		if(userType){
			map.put("993",  new NoteInfo(9,9,3));
		}
		
		for(NoteInfo note:noteInfoList){
			String mapKey = note.getIsManager()+""+note.getApplyFileType()+""+note.getProcess();
			if(note.getProcess()==5 || note.getProcess()==6){//通过、未通过、  过期的；
				mapKey = note.getIsManager()+""+note.getApplyFileType()+"9";
				int cou1 = map.get(mapKey).getCou();
				int cou2 = note.getCou();
				note.setCou(cou1+cou2);
				note.setProcess(9);
			}
			map.put(mapKey, note);
			//左侧大菜单上面的数值
			if(mapKey.startsWith("21")){
				int cou1 = map.get(mapKey).getCou();
				NoteInfo bigNote = map.get("991");
				bigNote.setCou(bigNote.getCou()+cou1);
			}else if(mapKey.startsWith("24")){
				int cou1 = map.get(mapKey).getCou();
				NoteInfo bigNote = map.get("992");
				bigNote.setCou(bigNote.getCou()+cou1);
			}
			//管理员才有的审核
			if(userType){
				if(mapKey.equals("114") || mapKey.equals("144")){
					int cou1 = map.get(mapKey).getCou();
					NoteInfo bigNote = map.get("993");
					bigNote.setCou(bigNote.getCou()+cou1);
				}
			}
			
		}
		
		List<NoteInfo> resultList = new ArrayList<NoteInfo>();
	    for (String key : map.keySet()) {
	       NoteInfo noteInfo = new NoteInfo();
		   noteInfo = map.get(key);
		   resultList.add(noteInfo);
		}
	    
		JSONArray json = JSONArray.fromObject(resultList);
		return json.toString();
	}
	
	
}
