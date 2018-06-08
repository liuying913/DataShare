package com.highfd.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.DownHistoryInfo;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UpdateDownIdBean;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.AnalysisUtil;
import com.highfd.common.PageInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.number.NumberChange;
import com.highfd.common.time.DayNumberOfOneYear;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.DownHistoryDao;
import com.highfd.dao.SiteStationDAO;
import com.highfd.service.DownHistoryService;

@Service
public class DownHistoryServiceImpl implements DownHistoryService {
	
	@Autowired
	DownHistoryDao dao;
	
	@Autowired
	SiteStationDAO sitedao;
	
	public void insertDictionary(final DownHistoryInfo info) throws Exception {
		dao.insertDictionary(info);
	}
	
	public void insertFileInfo(final FileInfo info) throws Exception {
		dao.insertFileInfo(info);
	}
	
	//查询 数据下载记录
	public List<FileHistroyDown>  getDownHistoryList(String year,String fileType,String siteParam){
		return dao.getDownHistoryList(year,fileType, siteParam);
	}
	
	//查询 地震应急数据下载记录
	public List<FileHistroyDown>  earthQuakeDownHistoryList(String fileType,String siteParam){
		return dao.earthQuakeDownHistoryList(fileType, siteParam);
	}
	
	//综合用户查询 数据下载记录
	@SuppressWarnings("unchecked")
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String applyId,String fileType,String siteParam,PageInfo pageinfo){
		return dao.getUserDownHistoryList(year, applyId,fileType, siteParam,pageinfo);
	}
	
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String applyId,String fileType,String siteParam){
		return dao.getUserDownHistoryListAll(year, applyId, fileType, siteParam);
	}
	
	//统计分析地图30S 文件下载
	public List<FileDownBySiteNumberBean>  fileDownGroupBySiteNumberForMap(String fileType,String startTime,String endTime,String userId){
		return dao.fileDownGroupBySiteNumberForMap(fileType, startTime, endTime, userId);
	}
	
	//统计分析  表格 文件下载情况
	public List<FileDownBySiteNumberBean>  siteInfoDownGroupForTable(String fileType,String startTime,String endTime,String userId){
		return dao.siteInfoDownGroupForTable(fileType, startTime, endTime, userId);
	}
	
	//目录整理   单站、多站   单月  年度
	public Map<String, Map<String,FileInfoDirect>> s30s260MonthData(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers){
		
		Map<String, Map<String,FileInfoDirect>> resultMap = new TreeMap<String, Map<String,FileInfoDirect>>();
		
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			Map<String,FileInfoDirect> fileMap = new TreeMap<String,FileInfoDirect>();//根据  时间跨度，填放初始数据
			for(int j=1;j<=dayNumbers;j++){
				FileInfoDirect fileInfo = new FileInfoDirect();
				fileInfo.setFileFlag(0);
				fileInfo.setZoneName(siteInfo.getZoneName());
				fileMap.put(NumberChange.getThreeNumber(j), fileInfo);
			}
			resultMap.put(siteInfo.getZoneName()+"_"+siteInfo.getSiteName(), fileMap);
			//System.out.println(siteInfo.getZoneName()+"_"+siteInfo.getSiteName()+"   LLL");
		}	
		
		//有数据的 进行赋值操作
		for(int i=0;i<fileInfoAllList.size();i++){
			FileInfo fileInfo = fileInfoAllList.get(i);
			FileInfoDirect fd = new FileInfoDirect();
			fd.setFileFlag(fileInfo.getFileFlag());
			fd.setEphemNumber(fileInfo.getEphemNumber());
			String key = fileInfo.getZoneName()+"_"+fileInfo.getSiteName();
			if(null!=resultMap.get(key) ){
				resultMap.get(key).put(NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer()),fd);
				//System.out.println(NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer())+"  >");
				//System.out.println(fileInfo.getSiteName()+"  "+fileInfo.getSystemMonthDayNumer()+"  "+fileInfo.getFileFlag()+"  MMMMMMMqq");
			}
		}
		
		//排序(根据缺失数量、省份名称、台站名称)
		Map<String, Map<String,FileInfoDirect>> resultMap2 = new TreeMap<String, Map<String,FileInfoDirect>>();
		 for (Entry<String, Map<String, FileInfoDirect>> entry : resultMap.entrySet()) {
		     Map<String, FileInfoDirect> OneMap = entry.getValue();
		     
		     int i=0;
		     for (Entry<String, FileInfoDirect> fileOrderMap : OneMap.entrySet()) {
		    	 FileInfoDirect value = fileOrderMap.getValue();
		    	 if(value.getFileFlag()!=1 && value.getFileFlag()!=2){
		    		 i++;
		    	 }
		     }
		     
		     String strNum="";
		     if(i<=9){strNum="00"+i;}else if(i<=99){strNum="0"+i;}else{strNum=i+"";}
		     resultMap2.put(strNum+"&"+entry.getKey(), resultMap.get(entry.getKey()));
		 }
		
		return resultMap2;
	}
	
	//目录整理   单站、多站   单月  年度   转化成json
	public String dataToJson(Map<String, Map<String,FileInfoDirect>> resultMap){
		StringBuffer resultMapStr = new StringBuffer();
		//遍历map中的键  
		for (String siteName : resultMap.keySet()) {  
		    //System.out.println("名称 = " + siteName);  
		    StringBuffer siteBuffer = new StringBuffer("");;
		    
		    Map<String, FileInfoDirect> map = resultMap.get(siteName);
		    for (String key : map.keySet()) {
		    	//"058":{"fileFlag":1,"zoneName":"","ephemNumber":""},
		    	String zoneName = map.get(key).getZoneName()==null?"\"\"":"\""+map.get(key).getZoneName()+"\"";
		    	String ephNum = "\"\"";
		    	siteBuffer.append("\"1"+key+"\":{\"fileFlag\":"+map.get(key).getFileFlag()+",\"zoneName\":"+zoneName+",\"ephemNumber\":"+ephNum+"},");
			    //System.out.println("\"1"+key+"\":{\"fileFlag\":"+map.get(key).getFileFlag()+",\"zoneName\":"+zoneName+",\"ephemNumber\":"+ephNum+"},");
		    	//System.out.println("key= "+ key + " and value= " + map.get(key).getZoneName()+map.get(key).getEphemNumber());
			}
		    siteBuffer.deleteCharAt(siteBuffer.length()-1);
		    String midStr = "";
		    midStr="\""+siteName+"\":{"+siteBuffer+"},";
		    resultMapStr.append(midStr);
		}  
		resultMapStr.deleteCharAt(resultMapStr.length()-1);
		String rd = "{"+resultMapStr+"}";
		return rd;
	}
	
	
	
	
	//目录整理  单站月度   柱状图
	public List<FileInfoDirect> s30sOneMonthData(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers){
		
		Map<String, Map<String,FileInfoDirect>> resultMap = new TreeMap<String, Map<String,FileInfoDirect>>();
		String zKey= "";
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			Map<String,FileInfoDirect> fileMap = new TreeMap<String,FileInfoDirect>();//根据  时间跨度，填放初始数据
			for(int j=1;j<=dayNumbers;j++){
				FileInfoDirect fileInfo = new FileInfoDirect();
				fileInfo.setFileFlag(0);
				fileInfo.setZoneName(siteInfo.getZoneName());
				fileInfo.setDayNumber(NumberChange.getThreeNumber(j));
				fileInfo.setEphemNumber("-");
				fileInfo.setMp1("-");
				fileInfo.setMp2("-");
				fileMap.put(NumberChange.getThreeNumber(j), fileInfo);
			}
			zKey = siteInfo.getZoneName()+"_"+siteInfo.getSiteName();
			resultMap.put(zKey, fileMap);
			//System.out.println(siteInfo.getZoneName()+"_"+siteInfo.getSiteName()+"   LLL");
		}	
		
		//有数据的 进行赋值操作
		for(int i=0;i<fileInfoAllList.size();i++){
			FileInfo fileInfo = fileInfoAllList.get(i);
			//修改于2017-02-03  数据目录 单站月度界面
			if(!fileInfo.getSiteNumber().equals(siteInfoList.get(0).getSiteNumber())){
				continue;
			}
			//over
			
			FileInfoDirect fd = new FileInfoDirect();
			fd.setFileFlag(fileInfo.getFileFlag());
			
			if(null==fileInfo.getEphemNumber() ){
				fd.setEphemNumber("-");
			}else if(fileInfo.getEphemNumber().equals("0")){
				fd.setEphemNumber("-");
			}else if(fileInfo.getEphemNumber().equals("-1")){
				fd.setEphemNumber("-");
			}else{
				fd.setEphemNumber(fileInfo.getEphemNumber());
			}
			
			if(null==fileInfo.getMp1()){
				fd.setMp1("-");
			}else if(fileInfo.getMp1().equals("-1")){
				fd.setMp1("-");
			}else{
				fd.setMp1(fileInfo.getMp1());
			}
			
			if(null==fileInfo.getMp2()){
				fd.setMp2("-");
			}else if(fileInfo.getMp2().equals("-1")){
				fd.setMp2("-");
			}else{
				fd.setMp2(fileInfo.getMp2());
			}
			
			//fd.setMp1((Math.random()) +"");
			//fd.setMp2((Math.random()) +"");
			
			fd.setO_slps(fileInfo.getO_slps());
			fd.setDayNumber(NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer()));
			if(null!=resultMap.get(zKey) ){
				resultMap.get(zKey).put(NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer()),fd);
				//System.out.println("##  "+NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer()) +"  "+fd.getDayNumber()+"/"+fd.getEphemNumber()+" / "+fd.getFileFlag()+"  "+fd.getMp1());
				//System.out.println(NumberChange.getThreeNumber(fileInfo.getSystemMonthDayNumer())  +"  rgrrg");
				//System.out.println(fileInfo.getSiteName()+"  "+fileInfo.getSystemMonthDayNumer()+"  "+fileInfo.getFileFlag()+"  MMMMMMMqq");
			}
		}
		
		List<FileInfoDirect> list = new ArrayList<FileInfoDirect>();
		for (FileInfoDirect value : resultMap.get(zKey).values()) { 
			//System.out.println(value.getDayNumber());
			value.setDayNumber(NumberChange.getOnlyNumber(value.getDayNumber()));
			list.add(value);
		}  
		return list;
	}
	
	
	//目录整理   单站、多站   单月  年度(excel)
	public Map<String, Map<Integer,FileInfoDirect>> s30s260MonthData2(List<SiteInfo> siteInfoList,List<FileInfo> fileInfoAllList,int dayNumbers){
		
		Map<String, Map<Integer,FileInfoDirect>> resultMap = new TreeMap<String, Map<Integer,FileInfoDirect>>();
		
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			Map<Integer,FileInfoDirect> fileMap = new TreeMap<Integer,FileInfoDirect>();//根据  时间跨度，填放初始数据
			for(int j=1;j<=dayNumbers;j++){
				FileInfoDirect fileInfo = new FileInfoDirect();
				fileInfo.setFileFlag(0);
				fileMap.put(j, fileInfo);
			}
			resultMap.put(siteInfo.getSiteName(), fileMap);
		}	
		
		//有数据的 进行赋值操作
		for(int i=0;i<fileInfoAllList.size();i++){
			
			FileInfo fileInfo = fileInfoAllList.get(i);
			
			FileInfoDirect fd = new FileInfoDirect();
			fd.setFileFlag(fileInfo.getFileFlag());
			fd.setEphemNumber(fileInfo.getEphemNumber());
			
			if(null!=resultMap.get(fileInfo.getSiteName()) ){
				resultMap.get(fileInfo.getSiteName()).put(Integer.valueOf(fileInfo.getSystemMonthDayNumer()),fd);
				//System.out.println(fileInfo.getSiteName()+" "+fileInfo.getSystemMonthDayNumer()+"  "+fd.getFileFlag());
			}
		}
		return resultMap;
	}
	
	//统计分析   部委下载分组统计分析
	public List<GroupByDepPro>  departmentDown(String fileType,String siteType,String startTime,String endTime,String userId){
		List<GroupByDepPro> resultList = new ArrayList<GroupByDepPro>();
		String timeCha = TimeUtils.getTimeCha(startTime, endTime);
		Map<String, String> depMap = sitedao.departmentGroup(siteType);//获得各个部委  台站数量
		List<GroupByDepPro> departmentDown = dao.departmentDown(fileType, startTime, endTime, userId);
		
		for (String key : depMap.keySet()) {
			
			GroupByDepPro gd = new GroupByDepPro();
			gd.setGroupName(key);
			gd.setDayNum(timeCha);//时间差
			gd.setSiteInfoNum(depMap.get(key));//设置该部委的台站数量
			gd.setGroupCnName(MapAll.mapDepat.get(gd.groupName).getDicCnName());
			
	    	//gd.setDownNum((int)(Math.random()*100) +"");//初始值
			//gd.setAvgDownNum((int)(Math.random()*100) +"");//初始值
			for(int i=0;i<departmentDown.size();i++){
				GroupByDepPro gd2 = departmentDown.get(i);
			    if(key.equals(gd2.getGroupName())){
			    	//设置 文件下载总数
			    	gd.setDownNum(gd2.getDownNum());
			    	//设置  单个文件的  下载次数
					gd.setAvgDownNum(AnalysisUtil.getAvgDown(gd));//设置 单个部委下载文件的总数量 除以 台站数量 除以 时间
			    }
			}
			resultList.add(gd);
		}
		return resultList;
	}
	
	//统计分析   省份下载分组统计分析
	public List<GroupByDepPro>  zoneDown(String fileType,String siteType,String startTime,String endTime,String userId){
		
		List<GroupByDepPro> resultList = new ArrayList<GroupByDepPro>();
		String timeCha = TimeUtils.getTimeCha(startTime, endTime);
		Map<String, String> depMap = sitedao.zoneGroup(siteType);//获得各个部委  台站数量
		List<GroupByDepPro> departmentDown = dao.zoneDown(fileType, startTime, endTime, userId);
		for (String key : depMap.keySet()) {
			GroupByDepPro gd = new GroupByDepPro();
			gd.setGroupName(key);
			gd.setDayNum(timeCha);//时间差
			gd.setSiteInfoNum(depMap.get(key));//设置该部委的台站数量
			gd.setGroupCnName(MapAll.mapZone.get(gd.getGroupName()).getZoneName());
			
	    	//gd.setDownNum((int)(Math.random()*100) +"");//初始值
			//gd.setAvgDownNum((int)(Math.random()*100) +"");//初始值
			for(int i=0;i<departmentDown.size();i++){
				GroupByDepPro gd2 = departmentDown.get(i);
			    if(key.equals(gd2.getGroupName())){
			    	//设置 文件下载总数
			    	gd.setDownNum(gd2.getDownNum());
			    	//设置  单个文件的  下载次数
					gd.setAvgDownNum(AnalysisUtil.getAvgDown(gd));//设置 单个部委下载文件的总数量 除以 台站数量 除以 时间
			    }
			}
			resultList.add(gd);
		}
		return resultList;
	}
	
	
	//统计分析   月度分组统计分析
	public List<GroupByDepPro>  monthDown(String fileType,String startTime,String endTime){
		return dao.monthDown(fileType, startTime, endTime);
	}
	
	
	
	
	
	
	
	
	
	public void upFileList(final FileInfo info,String path) throws Exception {
		//递归显示C盘下所有文件夹及其中文件
		File root = new File(path);
		showAllFiles(root);
	}
	
	
	public static void main(String[] args) throws Exception {
		String path="E:\\new";
		File root = new File(path);
		new DownHistoryServiceImpl().showAllFiles(root);
	}
	
	
	//获得磁盘上 所有文件，并入库
	 public void showAllFiles(final File dir) throws Exception{
		  File[] fs = dir.listFiles();
		  if(null==fs){return;}
		  
		  for(int i=0; i<fs.length; i++){
			  if(fs[i].isDirectory()){
				  try{
					  showAllFiles(fs[i]);
				  }catch(Exception e){}
			  }else{
				  String filePath = fs[i].getAbsolutePath();
				  //System.out.println(filePath);
				  //if(filePath.indexOf("sd")>-1){
					  FileInfo info = new FileInfo();
					  String[] split = filePath.split("\\\\");
					  
					  info.setFileYear(split[split.length-3]);//年
					  info.setFileDayYear(split[split.length-2]);//年积日
					  info.setSiteNumber(fs[i].getName().substring(0, 4).toUpperCase());//台站名称
					  info.setFileName(split[split.length-1]);//文件名称
					  info.setFilePath(fs[i].getParent());//路径
					  info.setFileSize(Math.rint(fs[i].length()/1024.00));//大小
					  info.setFileFlag(1);
					  //设置文件 类型（30s 流动  共享）
					  if(info.getFilePath().indexOf("30sdz")>-1){//30S日常数据
						  info.setType("1");
					  }else if(info.getFilePath().indexOf("reg")>-1){//流动数据 
						  info.setType("2");
					  }else if(info.getFilePath().indexOf("Plus-GNSS")>-1){//共享数据
						  info.setType("3");
					  }
					  
					  info.setSystemTimeStr(DayNumberOfOneYear.getDateAfter(info.getFileYear(),info.getFileDayYear()));
					  //System.out.println(info.getFileName()+" 年" +info.getFileYear()+" 年积日"+info.getFileDayYear()+"  路径"+info.getFilePath()+" 文件大小"+info.getFileSize()+" 台站名称"+info.getSiteNumber());
					  
					  insertFileInfo(info);
				  //}else{
					  //System.out.println("else "+filePath);
				  //}
				  //System.out.println(fs[i].getAbsolutePath()+"  文件名称："+fs[i].getName()+"  "+fs[i].getParent());
			  }
		  }
	 }
	 
	//通过applyID 获得userId
	public Integer applyId_UserId(String id){
		return dao.applyId_UserId(id);
	}
	
	//地震应急事件   表格展示
	public List<FileInfo>  downTable(String userName,String earthQuakeId,String year){
		return dao.downTable(userName, earthQuakeId, year);
	}
	
	//地震应急事件   地图查看
	public List<SiteMapInfo>  earthQuakeMap(String userName,String earthQuakeId,String year){
		List<EarthQuakeConfig> earthQuakeConfig = sitedao.earthQuakeConfigQuery();
		EarthQuakeConfig earthConfig = earthQuakeConfig.get(0);
		return dao.earthQuakeMap(userName, earthQuakeId, year,earthConfig);
	}	
	/**
	 * 将 用户下载的文件 修改文件的id
	 */
	public List<UpdateDownIdBean>  downChangeFile(final String year){
		return dao.downChangeFile(year);
	}
	
	/**
	 * 修改下载文件的 id
	 * @param bean
	 * @throws Exception
	 */
	public void updateDownFileId(final UpdateDownIdBean bean) throws Exception {
		dao.updateDownFileId(bean);
	}
	
}
