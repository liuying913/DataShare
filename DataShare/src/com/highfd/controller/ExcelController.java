package com.highfd.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.highfd.bean.Apply_User_Req;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.FileInfo;
import com.highfd.bean.GreatEventInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UserInfo;
import com.highfd.beanFile.MonthDayLog.FileInfoDirect;
import com.highfd.beanFile.analysis.FileDownBySiteNumberBean;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.beanFile.dataShare.SiteMapInfo;
import com.highfd.common.ZoneSite;
import com.highfd.common.excel.Style;
import com.highfd.common.number.NumberChange;
import com.highfd.common.time.TimeUtils;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.GreatEventService;
import com.highfd.service.ManagerService;
import com.highfd.service.SiteStationService;
import com.highfd.service.UserService;

@Component
@Controller
@RequestMapping(value="/excel")
public class ExcelController {
	@Autowired
	UserService userService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	DownHistoryService downHistoryService;
	@Autowired
	GreatEventService greatEventService;
	@Autowired
	ManagerService managerService;

	//数据目录下载  月度 年度 
	@RequestMapping("dataShare")  
	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
		
 		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		String year = request.getParameter("year");
		String endTime = request.getParameter("ddlmonth");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		String fileName="";
		if(year.indexOf("-")>-1){//日度数据
			st=year;
			et=st;
			isYear = false;
			fileName=year+"数据目录";
		}else{
			if(null==endTime || "".equals(endTime)){//年度
				st = year+"-01-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(12));
				fileName=year+"年数据目录";
			}else{//月度
				isYear= false;
				st = year+"-"+endTime+"-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(endTime));
				fileName=year+"年"+endTime+"月"+"数据目录";
			}
		}
		
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		String taizhang = request.getParameter("taizhang");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		List<FileInfo> fileInfoAllList = applyFileService.getApplyFileInfoList(st, et, "", "", ZoneSite.siteListToStr(siteInfoList),  "", "", "", "",fileType, "",isYear,year);
		
		//台站名称  （年积日、对象）
		Map<String, Map<String, FileInfoDirect>> resultMap = downHistoryService.s30s260MonthData(siteInfoList, fileInfoAllList, dayNumbers);
	
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         
	         //产生工作表对象  
	         Sheet sheet = workbook.createSheet(fileName); 

	         int rowNumber = 0;//行序列
	         
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue("陆态网络"+fileName);
	         //upCell.setCellStyle(style);//设置居中
	         //upCell.setCellStyle(getStyle(workbook,IndexedColors.AQUA.getIndex()));//设置背景颜色
	         upCell.setCellStyle(setTextStyle(workbook));
	         if(year.indexOf("-")>-1){//日度数据
	        	 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers+2));//合并单元格
	         }else{
	        	 if(isYear){
	        		 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers+2));//合并单元格
	        	 }else{
	        		 sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers+1));//合并单元格
	        	 }
	         }
	         if(year.indexOf("-")>-1){//日度数据
	        	 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	        	 Cell cell = titleRow.createCell(3);//创建一列
	        	 cell.setCellValue("图例    白色:空缺    绿色:完整    黄色:补回    红色:缺失(历元数量小于600则认为是缺失)");
	        	 sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));//合并单元格
	         }else{
	        	 if(isYear){
	        		 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
		        	 Cell cell = titleRow.createCell(184);//创建一列
		        	 cell.setCellValue("图例    白色:空缺    绿色:完整    黄色:补回    红色:缺失(历元数量小于600则认为是缺失)");
		        	 sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));//合并单元格
	        	 }else{
	        		 int title2=14;
	        		 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
		        	 Cell cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("图例");
		        	 
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("：");
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("空缺");
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));
		        	 
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("完整");
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellStyle(getStyle(workbook,IndexedColors.BRIGHT_GREEN.getIndex(),isYear));
		        	 
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("补回");
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),isYear));
		        	 
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("缺失");
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),isYear));
		        	 
		        	 cell = titleRow.createCell(title2++);//创建一列
		        	 cell.setCellValue("(历元数量小于600则认为是缺失)");
		        	 sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));//合并单元格
		        	 sheet.addMergedRegion(new CellRangeAddress(1, 1, 24, 29));//合并单元格
	        	 }
	         }      
	         
	         //标题
	         Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	         titleRow.setHeight((short)400);
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("地区");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 2400); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         tilteCell = titleRow.createCell(1);//创建一列
	         tilteCell.setCellValue("台站");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(1, 2400); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         for(int i=1;i<=dayNumbers;i++){
	             tilteCell = titleRow.createCell(i+1);//创建一列  
	             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
	             //tilteCell.setCellStyle(style);//设置居中
	             
	             //BRIGHT_GREEN  翠绿
	             //AUTOMATIC 黑色
	             //BROWN shen红色  CORAL浅红  GREY_25_PERCENT浅土黄色
	             tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	             if(isYear){//年度数据
	            	 sheet.setColumnWidth(i+1, 120); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else if(dayNumbers>1){//月度
	            	 tilteCell.setCellValue(i+"");
	            	 sheet.setColumnWidth(i+1, 1200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else{//日度
	            	 tilteCell.setCellValue("目录状态");
	            	 sheet.setColumnWidth(i, 10000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }
	         }
	         
	         if(isYear){//年度  追加完整率
	        	 tilteCell = titleRow.createCell(dayNumbers+2);//创建一列
		         tilteCell.setCellValue("完整率");
		         //tilteCell.setCellStyle(style);//设置居中
		         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
		         sheet.setColumnWidth(1, 2800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         }
	         if(year.indexOf("-")>-1){//日度数据+历元数量
	        	 tilteCell = titleRow.createCell(dayNumbers+2);//创建一列
		         //tilteCell.setCellStyle(style);//设置居中
		         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
		         tilteCell.setCellValue("历元数量");
		         sheet.setColumnWidth(dayNumbers+1, 15000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         }
	         
	         //数据
	         //年度完整率
	         for (String siteNumber : resultMap.keySet()) {
	             Map<String, FileInfoDirect> dayMap = resultMap.get(siteNumber); 
	             Row row = sheet.createRow(rowNumber++);//创建行
	             row.setHeight((short)350);
	             
	             int dayNumber = 0;
	             Cell cell = row.createCell(dayNumber++);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(siteNumber.split("_")[0].split("&")[1]);  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(dayNumber++);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(siteNumber.split("_")[1]);  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));//设置背景颜色
	             
	             double yearWzl=0.0;//年度完整率
	             String ephemNumber = "";
	             for (String days : dayMap.keySet()) {
	            	 FileInfoDirect fileInfoD = dayMap.get(days);    ephemNumber = fileInfoD.getEphemNumber();//日度的历元数量
	            	 
	            	 cell = row.createCell(dayNumber++);//创建列  
		             //cell.setCellValue(fileInfoD.getFileFlag());
	            	 //System.out.println(fileInfoD.getFileFlag());
		             if(fileInfoD.getFileFlag()==1){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.BRIGHT_GREEN.getIndex(),isYear));
		            	 yearWzl++;
		             }else if(fileInfoD.getFileFlag()==2){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),isYear));
		            	 yearWzl++;
		             }else if(fileInfoD.getFileFlag()>=3){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),isYear));
		             }else{
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));
		             }
	             }
	             
	             if(year.indexOf("-")>-1){//日度数据+历元数量
	            	 cell = row.createCell(dayNumber++);//创建列
	            	 cell.setCellValue(" "+ephemNumber+" ");
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));
		         }
	             
	             if(isYear){//年度  追加完整率
			         cell = row.createCell(dayNumber++);//创建列
	            	 cell.setCellValue(NumberChange.getFiveNumber(yearWzl,year)+"%");
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));
		         }
	         }
	        
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}
	
	
	//数据目录下载(日度)
	@RequestMapping("dataShareDayExcel")  
	public void dataShareDayExcel(HttpServletRequest request, HttpServletResponse response){
		
 		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		String year = request.getParameter("year");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		String fileName="";
		st=year;
		et=st;
		isYear = false;
		fileName=year+"数据目录";
		
		String buwei = request.getParameter("buwei");
		String shengfen = request.getParameter("shengfen");
		String taizhang = request.getParameter("taizhang");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		
		Map<String, Map<String,SiteMapInfo>> resultMap = new HashMap<String, Map<String,SiteMapInfo>>();
		
		//*************测试
		Map<String, SiteInfo> mapSite = new HashMap<String, SiteInfo>();
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			mapSite.put(siteInfo.getSiteNumber(), siteInfo);
			SiteMapInfo fd = new SiteMapInfo();
			
			fd.setFileFlag(0);
			fd.setSiteNumber(siteInfo.getSiteNumber());
			fd.setSiteName(siteInfo.getSiteName());
			fd.setSiteLat(siteInfo.getSiteLat());
			fd.setSiteLng(siteInfo.getSiteLng());
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			fileInfoMap2.put("one", fd);
			resultMap.put(siteInfo.getSiteNumber(), fileInfoMap2);
		}
		//***测试*****************
		List<FileInfo> fileInfoAllList = applyFileService.getApplyFileInfoList(st, et, "", "", taizhang,  "", "", "", "",fileType, "",true,year);
		
		//有数据的 进行赋值操作
		for(int i=0;i<fileInfoAllList.size();i++){
			FileInfo fileInfo = fileInfoAllList.get(i);
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			SiteMapInfo fd = new SiteMapInfo();
			fd.setEphemNumber(fileInfo.getEphemNumber());//设置历元数量
			fd.setFileFlag(fileInfo.getFileFlag());
			fd.setSiteNumber(fileInfo.getSiteNumber());
			if(null!=resultMap.get(fileInfo.getSiteNumber()) ){
				fileInfoMap2.put("one", fd);
				resultMap.put(fileInfo.getSiteNumber(), fileInfoMap2);
			}
		}
	
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         
	         //产生工作表对象  
	         Sheet sheet = workbook.createSheet(fileName); 

	         int rowNumber = 0;//行序列
	         int dayNumbers = 1;
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue("陆态网络"+fileName);
	         //upCell.setCellStyle(style);//设置居中
	         //upCell.setCellStyle(getStyle(workbook,IndexedColors.AQUA.getIndex()));//设置背景颜色
	         upCell.setCellStyle(setTextStyle(workbook));
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers+2));//合并单元格


	         //提示行
        	 Row titleRow = sheet.createRow(rowNumber++);//创建标题行
        	 Cell cell = titleRow.createCell(2);//创建一列
        	 cell.setCellValue("图例    白色:空缺    绿色:完整    黄色:补回    红色:缺失(历元数量小于600则认为是缺失)");
        	 sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));//合并单元格
 
	         
	         //标题行
	         titleRow = sheet.createRow(rowNumber++);//创建标题行
	         titleRow.setHeight((short)400);
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("地区");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 4000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         tilteCell = titleRow.createCell(1);//创建一列
	         tilteCell.setCellValue("台站");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(1, 4000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	
             tilteCell = titleRow.createCell(2);//创建一列  
             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
             //tilteCell.setCellStyle(style);//设置居中
             
             //BRIGHT_GREEN  翠绿
             //AUTOMATIC 黑色
             //BROWN shen红色  CORAL浅红  GREY_25_PERCENT浅土黄色
             tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
             tilteCell.setCellValue("目录状态");
        	 sheet.setColumnWidth(2, 15000); //第一个参数代表列id(从0开始),第2个参数代表宽度值

	         
	         //历元数量
        	 tilteCell = titleRow.createCell(3);//创建一列
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),isYear));//设置背景颜色
	         tilteCell.setCellValue("历元数量");
	         sheet.setColumnWidth(3, 4000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         //数据
	         for (String siteNumber : resultMap.keySet()) {
	             Map<String, SiteMapInfo> dayMap = resultMap.get(siteNumber); 
	             Row row = sheet.createRow(rowNumber++);//创建行
	             row.setHeight((short)350);
	             
	             int dayNumber = 0;
	             cell = row.createCell(dayNumber++);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(mapSite.get(siteNumber).getSiteName());  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(dayNumber++);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(mapSite.get(siteNumber).getZoneName());  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));//设置背景颜色
	             
	             String ephemNumber = "";

            	 SiteMapInfo fileInfoD = dayMap.get("one");    
            	 ephemNumber = fileInfoD.getEphemNumber();//日度的历元数量
            	 
            	 cell = row.createCell(dayNumber++);//创建列  
	             //cell.setCellValue(fileInfoD.getFileFlag());
            	 //System.out.println(fileInfoD.getFileFlag());
	             if(fileInfoD.getFileFlag()==1){
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.BRIGHT_GREEN.getIndex(),isYear));
	             }else if(fileInfoD.getFileFlag()==2){
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),isYear));
	             }else if(fileInfoD.getFileFlag()>=3){
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),isYear));
	             }else{
	            	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));
	             }
	             
	             //日度数据+历元数量
	             cell = row.createCell(dayNumber++);//创建列
            	 cell.setCellValue(" "+ephemNumber+" ");
            	 cell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),isYear));

	         }
	        
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}
	
	
	//数据分析  地图   下载
	@RequestMapping("analysis")  
	 public void exportAnalysisExcel(HttpServletRequest request, HttpServletResponse response){
		String year = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		String fileName="";
		if(year.indexOf("-")>-1){//日度数据
			st=year;
			et=st;
			isYear = false;
			fileName=year+"数据目录";
		}else{
			if(null==endTime || "".equals(endTime)){//年度
				st = year+"-01-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(12));
				fileName=year+"年数据目录";
			}else{//月度
				isYear= false;
				st = year+"-"+endTime+"-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(endTime));
				fileName=year+"年"+endTime+"月"+"数据目录";
			}
		}
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		
		String userId = request.getParameter("userId");
		String taizhang = request.getParameter("taizhang");
		String shengfen = request.getParameter("shengfen");
		String buwei = request.getParameter("buwei");
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(taizhang,buwei,shengfen,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, "1");
		
		Map<String, Map<String,SiteMapInfo>> resultMap = new HashMap<String, Map<String,SiteMapInfo>>();
		for(int i=0;i<siteInfoList.size();i++){
			SiteInfo siteInfo = siteInfoList.get(i);
			
			SiteMapInfo siteMapInfo = new SiteMapInfo();
			siteMapInfo.setSiteName(siteInfo.getSiteName());
			siteMapInfo.setSiteNumber(siteInfo.getSiteNumber());
			siteMapInfo.setSiteLat(siteInfo.getSiteLat());
			siteMapInfo.setSiteLng(siteInfo.getSiteLng());
			
			siteMapInfo.setFileFlag(0);
			
			Map<String, SiteMapInfo> fileInfoMap2 = new HashMap<String, SiteMapInfo>();
			fileInfoMap2.put("one", siteMapInfo);
			resultMap.put(siteMapInfo.getSiteName(), fileInfoMap2);
		}
		
		List<FileDownBySiteNumberBean> siteFileCountList = downHistoryService.fileDownGroupBySiteNumberForMap("1", st, et, userId);
		for(int i=0;i<siteFileCountList.size();i++){
			FileDownBySiteNumberBean siteInfo = siteFileCountList.get(i);
			
			SiteMapInfo siteMapInfo = new SiteMapInfo();
			siteMapInfo.setSiteName(siteInfo.getSiteName());
			siteMapInfo.setSiteNumber(siteInfo.getSiteNumber());
			siteMapInfo.setFileFlag(siteInfo.getCouNum());
			if(null!=resultMap.get(siteInfo.getSiteName())){
				siteMapInfo.setSiteLat(resultMap.get(siteInfo.getSiteName()).get("one").getSiteLat());
				siteMapInfo.setSiteLng(resultMap.get(siteInfo.getSiteName()).get("one").getSiteLng());
				resultMap.get(siteInfo.getSiteName()).put("one", siteMapInfo);
			}
		}
	
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         
	         //产生工作表对象  
	         Sheet sheet = workbook.createSheet(fileName); 

	         int rowNumber = 0;//行序列
	         
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue("陆态网络"+fileName);
	         //upCell.setCellStyle(style);//设置居中
	         //upCell.setCellStyle(getStyle(workbook,IndexedColors.AQUA.getIndex()));//设置背景颜色
	         upCell.setCellStyle(setTextStyle(workbook));
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers));//合并单元格
	         
	         //月度标题
	         Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("台站名称");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 2800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         for(int i=1;i<=dayNumbers;i++){
	             tilteCell = titleRow.createCell(i);//创建一列  
	             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
	             //tilteCell.setCellStyle(style);//设置居中
	             tilteCell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             if(isYear){//年度数据
	            	 sheet.setColumnWidth(i, 130); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else if(dayNumbers>1){//月度
	            	 tilteCell.setCellValue(i+"");
	            	 sheet.setColumnWidth(i, 1200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else{//日度
	            	 tilteCell.setCellValue("下载数量");
	            	 sheet.setColumnWidth(i, 10000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }
	         }
	         
	         //数据
	         for (String siteNumber : resultMap.keySet()) {
	             Map<String, SiteMapInfo> dayMap = resultMap.get(siteNumber); 
	             Row row = sheet.createRow(rowNumber++);//创建行
	             Cell cell = row.createCell(0);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(siteNumber);  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             
	             int dayNumber = 1;
	             for (String days : dayMap.keySet()) {
	            	 SiteMapInfo fileInfoD = dayMap.get(days);
	            	 cell = row.createCell(dayNumber++);//创建列  
		             cell.setCellValue(fileInfoD.getFileFlag());
		             if(fileInfoD.getFileFlag()==1){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.GREEN.getIndex(),isYear));
		             }else if(fileInfoD.getFileFlag()==2){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),isYear));
		             }else if(fileInfoD.getFileFlag()==3){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),isYear));
		             }else{
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));
		             }
	             }
	         }
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}
	
	//分析  表格  下载
	@RequestMapping("exportAnalysisTableExcel")  
	 public void exportAnalysisTableExcel(HttpServletRequest request, HttpServletResponse response){
		String year = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		String st = "";
		String et = "";
		
		//时间判断
		boolean isYear = true;
		String fileName="";
		if(year.indexOf("-")>-1){//日度数据
			st=year;
			et=st;
			isYear = false;
			fileName=year+"数据目录";
		}else{
			if(null==endTime || "".equals(endTime)){//年度
				st = year+"-01-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(12));
				fileName=year+"年数据目录";
			}else{//月度
				isYear= false;
				st = year+"-"+endTime+"-01";
				et = TimeUtils.getLastDayOfMonth(Integer.valueOf(year), Integer.valueOf(endTime));
				fileName=year+"年"+endTime+"月"+"数据目录";
			}
		}
		int dayNumbers = TimeUtils.getBetweenTwoDayNumbers(st, et);
		
		String userId = request.getParameter("userId");
		
		String siteArray = request.getParameter("taizhang");
		String zoneArray = request.getParameter("shengfen");
		String departmentArray = request.getParameter("buwei");
		
		String fileType = request.getParameter("fileType");
		String siteType = request.getParameter("siteType");
		
		Map<String, Map<String,FileDownBySiteNumberBean>> resultMap = new HashMap<String, Map<String,FileDownBySiteNumberBean>>();
		
		List<SiteInfo> siteInfoList = siteService.getSiteInfoList(siteArray,departmentArray,zoneArray,siteType);
		//List<SiteInfo> siteInfoList = siteService.getSuperSiteInfoList(siteNumberArray, siteName, zoneArray, siteLat1, siteLat2, siteLon1, siteLon2, siteType);
		for(int i=0;i<siteInfoList.size();i++){
			
			SiteInfo siteInfo = siteInfoList.get(i);
			
			Map<String,FileDownBySiteNumberBean> fileMap = new HashMap<String,FileDownBySiteNumberBean>();//根据  时间跨度，填放初始数据
			for(int j=1;j<=dayNumbers;j++){
				FileDownBySiteNumberBean fileInfo = new FileDownBySiteNumberBean();
				fileInfo.setCouNum(0);
				fileInfo.setSiteName(siteInfo.getSiteName());
				fileInfo.setSiteNumber(siteInfo.getSiteNumber());
				fileInfo.setSystemMonthYearDay(j+"");
				fileMap.put(j+"", fileInfo);
			}
			resultMap.put(siteInfo.getSiteName(), fileMap);
		}
		
		List<FileDownBySiteNumberBean> siteFileCountList = downHistoryService.siteInfoDownGroupForTable(fileType, st, et, userId);
		
		//有数据的 进行赋值操作
		for(int i=0;i<siteFileCountList.size();i++){//将设置好的 空对象进行赋值
			FileDownBySiteNumberBean fileInfo = siteFileCountList.get(i);
			if(null!=resultMap.get(fileInfo.getSiteName())){
				resultMap.get(fileInfo.getSiteName()).put(fileInfo.getSystemMonthYearDay(), fileInfo);
			}
		}
		
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         
	         //产生工作表对象  
	         Sheet sheet = workbook.createSheet(fileName); 

	         int rowNumber = 0;//行序列
	         
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue("陆态网络"+fileName);
	         //upCell.setCellStyle(style);//设置居中
	         //upCell.setCellStyle(getStyle(workbook,IndexedColors.AQUA.getIndex()));//设置背景颜色
	         upCell.setCellStyle(setTextStyle(workbook));
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, dayNumbers));//合并单元格
	         //月度标题
	         Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("台站名称");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 2800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	         for(int i=1;i<=dayNumbers;i++){
	             tilteCell = titleRow.createCell(i);//创建一列  
	             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
	             //tilteCell.setCellStyle(style);//设置居中
	             tilteCell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             if(isYear){//年度数据
	            	 sheet.setColumnWidth(i, 130); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else if(dayNumbers>1){//月度
	            	 tilteCell.setCellValue(i+"");
	            	 sheet.setColumnWidth(i, 1200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }else{//日度
	            	 tilteCell.setCellValue(i+"");
	            	 sheet.setColumnWidth(i, 10000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	             }
	         }
	         //数据
	         for (String siteNumber : resultMap.keySet()) {
	             Map<String, FileDownBySiteNumberBean> dayMap = resultMap.get(siteNumber); 
	             Row row = sheet.createRow(rowNumber++);//创建行
	             Cell cell = row.createCell(0);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(siteNumber);  
	             //cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             int dayNumber = 1;
	             for (String days : dayMap.keySet()) {
	            	 FileDownBySiteNumberBean fileInfoD = dayMap.get(days);
	            	 cell = row.createCell(dayNumber++);//创建列  
		             //cell.setCellValue(fileInfoD.getFileFlag());
		             if(fileInfoD.getCouNum()==1){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.GREEN.getIndex(),isYear));
		             }else if(fileInfoD.getCouNum()==2){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),isYear));
		             }else if(fileInfoD.getCouNum()==3){
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),isYear));
		             }else{
		            	 cell.setCellStyle(getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));
		             }
	             }
	         }
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}
	
	//站点大事记下载excel
	@RequestMapping("exportGreatEvent")  
	 public void exportGreatEventExcel(HttpServletRequest request, HttpServletResponse response){
		
		String id = request.getParameter("id");
		String keyWords = request.getParameter("keyWords");
		String siteNumber = request.getParameter("siteNumber");
		String type = request.getParameter("type");
		String state = request.getParameter("state");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());//标记一个当前日期作为文件导出日期并命名文件
		String fileName="";//文件名 

		fileName="站点大事记"+date;
		List<GreatEventInfo> greatEventList = greatEventService.selectGreatEventList(id,type,siteNumber,keyWords,state);
		List<GreatEventInfo> greatEventListtype1 = new ArrayList<GreatEventInfo>();//存储type类型为1的数据
		List<GreatEventInfo> greatEventListtype2 = new ArrayList<GreatEventInfo>();//存储type类型为2的数据
		List<GreatEventInfo> greatEventListtype3 = new ArrayList<GreatEventInfo>();//存储type类型为3的数据
		for(int i=0;i<greatEventList.size();i++){
			if(greatEventList.get(i).getType().equals("1")){
				GreatEventInfo greateventinfo = greatEventList.get(i);
				greatEventListtype1.add(greateventinfo);
			}else if(greatEventList.get(i).getType().equals("2")){
				GreatEventInfo greateventinfo = greatEventList.get(i);
				greatEventListtype2.add(greateventinfo);
			}else if(greatEventList.get(i).getType().equals("3")){
				GreatEventInfo greateventinfo = greatEventList.get(i);
				greatEventListtype3.add(greateventinfo);
			}
			
		}
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         CellStyle style = workbook.createCellStyle();
             style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION); // 创建一个居中格式
	         XSSFFont f  = workbook.createFont();
	         f.setFontName("黑体");//设置字体
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置字体类型
	         f.setFontHeightInPoints((short) 12);//字号      
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗      
	         style.setFont(f);
	         //产生工作表对象  
	         String tittle[] = {"省份","站点","编码","问题描述","维修反馈","发现时间","解决时间","接收机类型（变更前）","接收机类型（变更后）","SN版本(变更前)","SN版本(变更后)","FW版本(变更前)","FW版本(变更后)"};
	         if(greatEventListtype1!=null&&greatEventListtype1.size()>0){
	        	 Sheet sheet1 = workbook.createSheet(greatEventListtype1.get(0).getTypeStr()); 
		         Row titleRow = sheet1.createRow(0);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	tilteCell.setCellStyle(style);
		        	sheet1.autoSizeColumn((short)i); //设置自适应宽度
		     	 }
	        	for(int i=0;i<greatEventListtype1.size();i++){
	        		Row upRow = sheet1.createRow(i+1);//创建数据行
	        		upRow.createCell(0).setCellValue(greatEventListtype1.get(i).zoneName);
		    		upRow.createCell(1).setCellValue(greatEventListtype1.get(i).siteName);
		    		upRow.createCell(2).setCellValue(greatEventListtype1.get(i).siteNumber);
		    		upRow.createCell(3).setCellValue(greatEventListtype1.get(i).describe);
		    		upRow.createCell(4).setCellValue(greatEventListtype1.get(i).feedback);
		    		upRow.createCell(5).setCellValue(greatEventListtype1.get(i).startTimeStr);
		    		upRow.createCell(6).setCellValue(greatEventListtype1.get(i).endTimeStr);
		    		upRow.createCell(7).setCellValue(greatEventListtype1.get(i).model_old);
		    		upRow.createCell(8).setCellValue(greatEventListtype1.get(i).model_new);
		    		upRow.createCell(9).setCellValue(greatEventListtype1.get(i).sn_old);
		    		upRow.createCell(10).setCellValue(greatEventListtype1.get(i).sn_new);
		    		upRow.createCell(11).setCellValue(greatEventListtype1.get(i).fw_old);
		    		upRow.createCell(12).setCellValue(greatEventListtype1.get(i).fw_new);
		    		sheet1.autoSizeColumn((short)i);
	        	}
	         }
	         if(greatEventListtype2!=null&&greatEventListtype2.size()>0){
	        	 Sheet sheet2 = workbook.createSheet(greatEventListtype2.get(0).getTypeStr()); 
		         Row titleRow = sheet2.createRow(0);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	tilteCell.setCellStyle(style);
		        	sheet2.autoSizeColumn((short)i); 
		     	 }
	        	for(int i=0;i<greatEventListtype2.size();i++){
	        		Row upRow = sheet2.createRow(i+1);//创建标题行
	          		upRow.createCell(0).setCellValue(greatEventListtype2.get(i).zoneName);
		    		upRow.createCell(1).setCellValue(greatEventListtype2.get(i).siteName);
		    		upRow.createCell(2).setCellValue(greatEventListtype2.get(i).siteNumber);
		    		upRow.createCell(3).setCellValue(greatEventListtype2.get(i).describe);
		    		upRow.createCell(4).setCellValue(greatEventListtype2.get(i).feedback);
		    		upRow.createCell(5).setCellValue(greatEventListtype2.get(i).startTimeStr);
		    		upRow.createCell(6).setCellValue(greatEventListtype2.get(i).endTimeStr);
		    		upRow.createCell(7).setCellValue(greatEventListtype2.get(i).model_old);
		    		upRow.createCell(8).setCellValue(greatEventListtype2.get(i).model_new);
		    		upRow.createCell(9).setCellValue(greatEventListtype2.get(i).sn_old);
		    		upRow.createCell(10).setCellValue(greatEventListtype2.get(i).sn_new);
		    		upRow.createCell(11).setCellValue(greatEventListtype2.get(i).fw_old);
		    		upRow.createCell(12).setCellValue(greatEventListtype2.get(i).fw_new);
		    		sheet2.autoSizeColumn((short)i);
	        	}
	         }
	         if(greatEventListtype3!=null&&greatEventListtype3.size()>0){
	        	 Sheet sheet3 = workbook.createSheet(greatEventListtype3.get(0).getTypeStr()); 
		         Row titleRow = sheet3.createRow(0);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	tilteCell.setCellStyle(style);
		        	sheet3.autoSizeColumn((short)i);
		     	 }
	        	for(int i=0;i<greatEventListtype3.size();i++){
	        		Row upRow = sheet3.createRow(i+1);//创建标题行
	          		upRow.createCell(0).setCellValue(greatEventListtype3.get(i).zoneName);
		    		upRow.createCell(1).setCellValue(greatEventListtype3.get(i).siteName);
		    		upRow.createCell(2).setCellValue(greatEventListtype3.get(i).siteNumber);
		    		upRow.createCell(3).setCellValue(greatEventListtype3.get(i).describe);
		    		upRow.createCell(4).setCellValue(greatEventListtype3.get(i).feedback);
		    		upRow.createCell(5).setCellValue(greatEventListtype3.get(i).startTimeStr);
		    		upRow.createCell(6).setCellValue(greatEventListtype3.get(i).endTimeStr);
		    		upRow.createCell(7).setCellValue(greatEventListtype3.get(i).model_old);
		    		upRow.createCell(8).setCellValue(greatEventListtype3.get(i).model_new);
		    		upRow.createCell(9).setCellValue(greatEventListtype3.get(i).sn_old);
		    		upRow.createCell(10).setCellValue(greatEventListtype3.get(i).sn_new);
		    		upRow.createCell(11).setCellValue(greatEventListtype3.get(i).fw_old);
		    		upRow.createCell(12).setCellValue(greatEventListtype3.get(i).fw_new);
		    		sheet3.autoSizeColumn((short)i);
	        	}
	         }
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成..."); 
	}
	
	/**
	 * 数据目录-----> 数据文件属性
	 */
	@RequestMapping("exportDataExcel")  
	 public void exportDataExcel(HttpServletRequest request, HttpServletResponse response){
		String applyOrShow = request.getParameter("applyOrShow");
		String fileYear = request.getParameter("fileYear");
		String fileDayYear = request.getParameter("fileDayYear");
		String smallLat = request.getParameter("dongwei");
		String bigLat = request.getParameter("xiwei");
		String smallLon = request.getParameter("dongjin");
		String bigLon = request.getParameter("xijin");
		String provinceArray = request.getParameter("shengfen");
		String siteNumberArray = request.getParameter("taizhang");
		String siteType = request.getParameter("Datetype");
		String fileFlag = request.getParameter("fileFlag");
		
		//时间
		String startTime = request.getParameter("kaishi");
		String endTime = request.getParameter("jieshu");
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//String date = sdf.format(new Date());//标记一个当前日期作为文件导出日期并命名文件
		String fileName="";//文件名 
		fileName="30S日常数据("+startTime+"至"+endTime+")";
		
		fileYear = startTime.substring(0, 4);
		System.out.println(fileYear);
		List<FileInfo> selectFileList = new ArrayList<FileInfo>();
		if("apply".equals(applyOrShow)){
			selectFileList = applyFileService.getApplyFileByDay(startTime,endTime,"", siteNumberArray, provinceArray, smallLat, bigLat, smallLon, bigLon, siteType, "1",fileYear);
		}else{
			String applyId = request.getParameter("applyId");
			selectFileList = applyFileService.getShowFileByDay(applyId, siteNumberArray, provinceArray, siteType, fileYear);
		}
		
		Map<String, Map<String,List<FileInfo>>> map = new HashMap<String, Map<String,List<FileInfo>>>();
		for(int i=0;i<selectFileList.size();i++){
			FileInfo fileInfo = selectFileList.get(i);
			Map<String, List<FileInfo>> yearMap = map.get(fileInfo.getFileYear());
			if(null==yearMap){
				List<FileInfo> dayFileList = new ArrayList<FileInfo>();  dayFileList.add(fileInfo);
				yearMap = new HashMap<String, List<FileInfo>>();         yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}else{
				List<FileInfo> dayFileList = yearMap.get(fileInfo.getFileDayYear());
				if(null==dayFileList){
					dayFileList = new ArrayList<FileInfo>();dayFileList.add(fileInfo);
				}else{
					dayFileList.add(fileInfo);
				}
				yearMap.put(fileInfo.getFileDayYear(), dayFileList);
				map.put(fileInfo.getFileYear(), yearMap);
			}
		}
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	    	 
	         //进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         Sheet sheet = workbook.createSheet(fileName); 
	         CellStyle style = workbook.createCellStyle();
	         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
	         //产生工作表对象  
	         XSSFFont f  = workbook.createFont();
	         f.setFontName("黑体");//设置字体
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置字体类型
	         f.setFontHeightInPoints((short) 14);//字号      
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗      
	         style.setFont(f);
	         
	         int rowNumber = 0;//行序列
	         //标题
	         Row upRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell upCell = upRow.createCell(0);//创建一列
	         upCell.setCellValue("陆态网络"+fileName);
	         //upCell.setCellStyle(style);//设置居中
	         //upCell.setCellStyle(getStyle(workbook,IndexedColors.AQUA.getIndex()));//设置背景颜色
	         upCell.setCellStyle(setTextStyle(workbook));
	         
	         sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));//合并单元格
	        
	         String tittle[] = {"台站编号","台站名称","文件名称","文件大小(KB)","历元数","MP1","MP2","是否完整"};
	         //产生工作表对象  
	         if(selectFileList!=null&&selectFileList.size()>0){
	        	 
		         Row titleRow = sheet.createRow(rowNumber++);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	//tilteCell.setCellStyle(style);
		        	tilteCell.setCellStyle(getTitleStyle(workbook,IndexedColors.ROYAL_BLUE.getIndex(),false));//设置背景颜色
//		        	sheet.autoSizeColumn((short)i);
//		        	sheet.setColumnWidth(120); 
		        	sheet.setColumnWidth(i, 5120);
	        	
		     	 }
	        	for(int i=0;i<selectFileList.size();i++){
	        		upRow = sheet.createRow(rowNumber++);//创建数据行
	        		Cell dataCell0 = upRow.createCell(0);
	        		Cell dataCell1 = upRow.createCell(1);
	        		Cell dataCell2 = upRow.createCell(2);
	        		Cell dataCell3 = upRow.createCell(3);
	        		Cell dataCell4 = upRow.createCell(4);
	        		Cell dataCell5 = upRow.createCell(5);
	        		Cell dataCell6 = upRow.createCell(6);
	        		Cell dataCell7 = upRow.createCell(7);
	        		
	        		dataCell0.setCellValue(selectFileList.get(i).getSiteNumber());
	        		dataCell1.setCellValue(selectFileList.get(i).getSiteName());
	        		dataCell2.setCellValue(selectFileList.get(i).getFileName());
	        		dataCell3.setCellValue(selectFileList.get(i).getFileSize());
	        		dataCell4.setCellValue(selectFileList.get(i).getEphemNumber());
	        		dataCell5.setCellValue(selectFileList.get(i).getMp1());
	        		dataCell6.setCellValue(selectFileList.get(i).getMp2());
	        		dataCell7.setCellValue(selectFileList.get(i).getFileComp());
		    		
	        		dataCell0.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell1.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell2.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell3.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell4.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell5.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell6.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
	        		dataCell7.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));//设置背景颜色
		    		sheet.setColumnWidth(i, 5120);
//		    		sheet.autoSizeColumn((short)i);
	        	}
	         }
 
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成...");  
	}
	

	
	//用户管理应急数据下载记录导出excel
	@RequestMapping("exportEarthHistoryExcel")  
	 public void exportEarthHistoryExcel(HttpServletRequest request, HttpServletResponse response){
		String earthQuakeId = request.getParameter("earthQuakeId");
		String iniParam = request.getParameter("iniParam");
		String userName=null;
		HttpSession session = request.getSession(false);
		if(!session.getAttribute("userType").toString().equals("1")){//不是管理员
			UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
			userName = userInfo.getUserName();
		}
		EarthQuake earthQuake = applyFileService.earthQuake_detail(earthQuakeId);
		String year = earthQuake.getYear();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());//标记一个当前日期作为文件导出日期并命名文件
		String fileName="";//文件名 
		fileName="应急记录"+date;

		List<FileHistroyDown> downHistoryList = managerService.getUserDownHistoryListAll(year, earthQuakeId, userName, iniParam);
		
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         CellStyle style = workbook.createCellStyle();
             style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION); // 创建一个居中格式
	         XSSFFont f  = workbook.createFont();
	         f.setFontName("黑体");//设置字体
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置字体类型
	         f.setFontHeightInPoints((short) 12);//字号      
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗      
	         style.setFont(f);
	         //产生工作表对象  
	         String tittle[] = {"年","地震名称","申请人","所属单位","文件路径","文件名称","下载时间"};
	         if(downHistoryList!=null&&downHistoryList.size()>0){
	        	 Sheet sheet = workbook.createSheet("应急记录" + date); 
		         Row titleRow = sheet.createRow(0);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	tilteCell.setCellStyle(style);
		        	sheet.autoSizeColumn((short)i); //设置自适应宽度
		     	 }
	        	for(int i=0;i<downHistoryList.size();i++){
	        		Row upRow = sheet.createRow(i+1);//创建数据行
	        		upRow.createCell(0).setCellValue(downHistoryList.get(i).getYear());
		    		upRow.createCell(1).setCellValue(downHistoryList.get(i).getName());
		    		upRow.createCell(2).setCellValue(downHistoryList.get(i).getApply_person());
		    		upRow.createCell(3).setCellValue(downHistoryList.get(i).getApply_unit());
		    		upRow.createCell(4).setCellValue(downHistoryList.get(i).getFilePath());
		    		upRow.createCell(5).setCellValue(downHistoryList.get(i).getFileName());
		    		upRow.createCell(6).setCellValue(downHistoryList.get(i).getDownTimeStr());
		    		sheet.autoSizeColumn((short)i);
	        	}
	         }
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成..."); 
	}
	
	//用户管理下载记录导出excel
	@RequestMapping("exportUserHistoryExcel")  
	 public void exportUserHistoryExcel(HttpServletRequest request, HttpServletResponse response){
		String applyId = request.getParameter("applyId");
		String year = "";
		List<Apply_User_Req> queryApplyUserReqList = applyFileService.query_Apply_User_ReqList(applyId);
		Apply_User_Req applyUserReq = new Apply_User_Req();
		if(null!=queryApplyUserReqList && queryApplyUserReqList.size()>0){
			applyUserReq = queryApplyUserReqList.get(0);
			String timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getS_StartTime());
			if(null==timestampToString2 || "".equals(timestampToString2)){
				timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getFlow_StartTime());
			}
			if(null==timestampToString2 || "".equals(timestampToString2)){
				timestampToString2 = TimeUtils.TimestampToString2(applyUserReq.getS_StartTime());
			}
			year = timestampToString2.substring(0, 4);
		}
		String fileType = request.getParameter("fileType");
		String fileParams = request.getParameter("fileParams");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());//标记一个当前日期作为文件导出日期并命名文件
		String fileName="";//文件名 
		fileName="下载记录"+date;

		List<FileHistroyDown> downHistoryList = downHistoryService.getUserDownHistoryListAll(year ,applyId,fileType, fileParams);
		
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         // 产生工作簿对象  
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         CellStyle style = workbook.createCellStyle();
             style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION); // 创建一个居中格式
	         XSSFFont f  = workbook.createFont();
	         f.setFontName("黑体");//设置字体
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置字体类型
	         f.setFontHeightInPoints((short) 12);//字号      
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗      
	         style.setFont(f);
	         //产生工作表对象  
	         String tittle[] = {"文件名称","台站名称","文件目录","申请单位","申请人","下载时间","数据情况"};
	         if(downHistoryList!=null&&downHistoryList.size()>0){
	        	 Sheet sheet = workbook.createSheet("下载记录" + date); 
		         Row titleRow = sheet.createRow(0);//标题
		     	 for(int i=0;i<tittle.length;i++){
		     		Cell tilteCell = titleRow.createCell(i);
		        	tilteCell.setCellValue(tittle[i]);
		        	tilteCell.setCellStyle(style);
		        	sheet.autoSizeColumn((short)i); //设置自适应宽度
		     	 }
	        	for(int i=0;i<downHistoryList.size();i++){
	        		Row upRow = sheet.createRow(i+1);//创建数据行
	        		upRow.createCell(0).setCellValue(downHistoryList.get(i).getFileName());
		    		upRow.createCell(1).setCellValue(downHistoryList.get(i).getSite_number());
		    		upRow.createCell(2).setCellValue(downHistoryList.get(i).getFileyear()+"/"+downHistoryList.get(i).getFileDayyear());
		    		upRow.createCell(3).setCellValue(downHistoryList.get(i).getApply_unit());
		    		upRow.createCell(4).setCellValue(downHistoryList.get(i).getApply_person());
		    		upRow.createCell(5).setCellValue(downHistoryList.get(i).getDownTimeStr());
		    		upRow.createCell(6).setCellValue(downHistoryList.get(i).getFileComp());
		    		sheet.autoSizeColumn((short)i);
	        	}
	         }
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成..."); 
	}
	
	//设置文字样式
	public CellStyle setTextStyle(final Workbook workbook){
		 CellStyle style = workbook.createCellStyle();
		 Font ztFont = workbook.createFont();  
         //ztFont.setItalic(true);                     // 设置字体为斜体字  
         ztFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());            // 将字体设置为“红色”  
         ztFont.setFontHeightInPoints((short)22);    // 将字体大小设置为18px  
         //ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上  
         //ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
         //ztFont.setStrikeout(true);                  // 是否添加删除线  
         style.setFont(ztFont);                    // 将字体应用到样式上面  
         style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
         style.setFillForegroundColor(IndexedColors.WHITE.getIndex());//背景颜色
         style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
 		 style.setFillPattern(CellStyle.SOLID_FOREGROUND);
         return style;
         
	}
	
	   private CellStyle getTitleStyle(final Workbook workbook,final short index,final boolean isYear){
	        CellStyle style = workbook.createCellStyle();
	        
	        Font headerFont = workbook.createFont(); // 字体
	        headerFont.setFontHeightInPoints((short)14);
	        headerFont.setColor(HSSFColor.RED.index);
	        headerFont.setFontName("宋体");
	        headerFont.setColor(IndexedColors.WHITE.getIndex());//白色字体
	        style.setFont(headerFont);
	/*        style.setAlignment(CellStyle.ALIGN_CENTER); 
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        // 设置单元格字体
	        Font headerFont = workbook.createFont(); // 字体
	        headerFont.setFontHeightInPoints((short)14);
	        //headerFont.setColor(HSSFColor.RED.index);
	        headerFont.setFontName("宋体");
	        style.setFont(headerFont);
	        style.setWrapText(true);

	        style.setFillBackgroundColor(HSSFCellStyle.ALIGN_RIGHT);
	        
	        // 设置单元格边框及颜色
	        style.setBorderBottom((short)1);
	        style.setBorderLeft((short)1);
	        style.setBorderRight((short)1);
	        style.setBorderTop((short)1);
	        style.setWrapText(true);*/
	        //short index = IndexedColors.RED.getIndex();
			style.setFillForegroundColor(index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setAlignment(CellStyle.ALIGN_CENTER); // 上下居中
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			
		    //        设置单元格边框  
	        //============================  
	      
	        // 创建单元格样式对象  
	        // 设置单元格边框样式  
	        // CellStyle.BORDER_DOUBLE      双边线  
	        // CellStyle.BORDER_THIN        细边线  
	        // CellStyle.BORDER_MEDIUM      中等边线  
	        // CellStyle.BORDER_DASHED      虚线边线  
	        // CellStyle.BORDER_HAIR        小圆点虚线边线  
	        // CellStyle.BORDER_THICK       粗边线  
			style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			style.setRightBorderColor(IndexedColors.LIGHT_BLUE.getIndex());
			if(!isYear){
				style.setBorderBottom(CellStyle.BORDER_THIN);  
				//style.setBorderTop(CellStyle.BORDER_THIN);  
				style.setBorderLeft(CellStyle.BORDER_THIN);  
				style.setBorderRight(CellStyle.BORDER_THIN);  
			}else{
				style.setBorderBottom(CellStyle.BORDER_THIN);  
				//style.setBorderTop(CellStyle.BORDER_THIN);  
				style.setBorderLeft(CellStyle.BORDER_THIN);  
				style.setBorderRight(CellStyle.BORDER_THIN);  
			}
	        // 设置单元格边框颜色  
			//style.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));  
			//style.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));  
			//style.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));  
	        //borderCell.setCellStyle(style); 
	        return style;
	    }
	   
    private CellStyle getStyle(final Workbook workbook,final short index,final boolean isYear){
        CellStyle style = workbook.createCellStyle();
        
/*        style.setAlignment(CellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short)14);
        //headerFont.setColor(HSSFColor.RED.index);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);

        style.setFillBackgroundColor(HSSFCellStyle.ALIGN_RIGHT);
        
        // 设置单元格边框及颜色
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setWrapText(true);*/
        //short index = IndexedColors.RED.getIndex();
		style.setFillForegroundColor(index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(CellStyle.ALIGN_CENTER); // 上下居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		
	    //        设置单元格边框  
        //============================  
      
        // 创建单元格样式对象  
        // 设置单元格边框样式  
        // CellStyle.BORDER_DOUBLE      双边线  
        // CellStyle.BORDER_THIN        细边线  
        // CellStyle.BORDER_MEDIUM      中等边线  
        // CellStyle.BORDER_DASHED      虚线边线  
        // CellStyle.BORDER_HAIR        小圆点虚线边线  
        // CellStyle.BORDER_THICK       粗边线  
		if(!isYear){
			style.setBottomBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setLeftBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setRightBorderColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
			style.setBorderBottom(CellStyle.BORDER_THIN);  
			//style.setBorderTop(CellStyle.BORDER_THIN);  
			style.setBorderLeft(CellStyle.BORDER_THIN);  
			style.setBorderRight(CellStyle.BORDER_THIN);  
		}else{
			//年度 无边框
			style.setBorderBottom(CellStyle.BORDER_NONE);  
			//style.setBorderTop(CellStyle.BORDER_THIN);  
			style.setBorderLeft(CellStyle.BORDER_NONE);  
			style.setBorderRight(CellStyle.BORDER_NONE);  
		}
        // 设置单元格边框颜色  
		//style.setBottomBorderColor(new XSSFColor(java.awt.Color.RED));  
		//style.setTopBorderColor(new XSSFColor(java.awt.Color.GREEN));  
		//style.setLeftBorderColor(new XSSFColor(java.awt.Color.BLUE));  
        //borderCell.setCellStyle(style); 
        return style;
    }
    
    
    
	//用户管理下载记录导出excel
	@RequestMapping("earthQuakeExcel")  
	 public void earthQuakeExcel(HttpServletRequest request, HttpServletResponse response){
		
		String earthQuakeId = request.getParameter("earthQuakeId");
		String siteNumberArray = request.getParameter("taizhang");
		String zoneArray = request.getParameter("shengfen");
		String departmentArray = request.getParameter("buwei");
		
		//地震应急事件   单个详细信息
		EarthQuake earthQuakeDetail = applyFileService.earthQuake_detail(earthQuakeId);
		List<FileInfo> earthQuakeFileList = applyFileService.query_EarthQuake_Data(earthQuakeId,earthQuakeDetail.getYear(),zoneArray, departmentArray, siteNumberArray);
		Map<String, Map<String, FileInfoDirect>> queryEarthQuakeDate = applyFileService.query_EarthQuake_Data_Json(siteNumberArray, earthQuakeFileList, earthQuakeId,earthQuakeDetail.getYear());
		

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());//标记一个当前日期作为文件导出日期并命名文件
		String fileName="";//文件名 
		fileName=earthQuakeDetail.getName()+"数据—"+date;
		
	     // 生成提示信息，
	     response.setContentType("application/vnd.ms-excel");
	     String codedFileName = null;
	     OutputStream fOut = null;
	     try{
	         // 进行转码，使其支持中文文件名
	         codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
	         response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
	         
	         // 产生工作簿对象  
	         int rowNumber = 0;//行序列
	         XSSFWorkbook workbook = new XSSFWorkbook();
	         CellStyle style = workbook.createCellStyle();
             style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION); // 创建一个居中格式
             XSSFFont f  = workbook.createFont();
	         f.setFontName("黑体");//设置字体
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//设置字体类型
	         f.setFontHeightInPoints((short) 12);//字号      
	         f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//加粗      
	         style.setFont(f);
	         
	         //产生标题  
	         Sheet sheet = workbook.createSheet(earthQuakeDetail.getName()+"1HZ数据—"+date);
	         sheet.setColumnWidth(0, 4800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         Row excelRow = sheet.createRow(rowNumber++);//标题
	         
	         int titleLength = 24*4+1;
	         int realLength = 0;
	     	 for(int i=0;i<titleLength;i++){
	     		 Cell tilteCell = excelRow.createCell(i);
	        	 tilteCell.setCellStyle(style);
	        	 if(i==0){
	        		 tilteCell.setCellValue("台站");
	        	 }else{
	        		 if(i%4==1){
	 	     			if(realLength<=9){
	 	     				tilteCell.setCellValue("0"+realLength);
	 		        	}else{
	 		        		tilteCell.setCellValue(realLength);
	 		        	}
	 	     			realLength++;//sheet.addMergedRegion(new CellRangeAddress((rowNumber-1), (rowNumber-1), 1+realLength*4, realLength*4+4));//合并单元格
	 	     		 }
	        		 sheet.setColumnWidth(i, 400); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	        	 }
	     	 }
	     	 
	     	for(int i=0;i<25;i++){
	     		sheet.addMergedRegion(new CellRangeAddress((rowNumber-1), (rowNumber-1), 1+i*4, i*4+4));//合并单元格
	     	 } 
	     	 
	     	for (String key : queryEarthQuakeDate.keySet()) {
				   Map<String, FileInfoDirect> map = queryEarthQuakeDate.get(key);
				   
				   String[] fiveOrOne = key.split("_");
				   //1Hz数据
				   if(!fiveOrOne[1].equals("50")){
					   
					   excelRow = sheet.createRow(rowNumber++);
					   
					   int dateNum = 0;
					   Cell dateCell = excelRow.createCell(dateNum++);
					   dateCell.setCellValue(Style.setEarthQuakeSiteNumber(key));
					   dateCell.setCellStyle(style);
					   for (String num : map.keySet()) {
						   
						   int fileFlag = map.get(num).getFileFlag();
						   dateCell = excelRow.createCell(dateNum++);
						   dateCell.setCellValue("");
						   //dateCell.setCellStyle(style);
						   
						   if(fileFlag==1){
							   dateCell.setCellStyle(getStyle(workbook,IndexedColors.GREEN.getIndex(),false));
				           }else if(fileFlag>=0){
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),false));
				           }else if(fileFlag>=3){
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),false));
				           }else{
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));
				           }
						   
					   }
				   }
			 }
	     	
	     	
	     	//50 HZ  
	     	 int rowNumber50 = 0;
	         Sheet sheet51 = workbook.createSheet(earthQuakeDetail.getName()+"50HZ数据—"+date); 
	         sheet51.setColumnWidth(0, 4800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         Row excelRow51 = sheet51.createRow(rowNumber50++);//标题
	     	 for(int i=0;i<13;i++){
	     		 Cell tilteCell = excelRow51.createCell(i);
	        	 tilteCell.setCellStyle(style);
	        	 if(i==0){
	        		 tilteCell.setCellValue("台站");
	        	 }else{
	        		 if(i<=9){
	      				tilteCell.setCellValue("0"+i);
	 	        	}else{
	 	        		tilteCell.setCellValue(i);
	 	        	}
	        	 }
	     	 }
	     	 
	     	for (String key : queryEarthQuakeDate.keySet()) {
				   Map<String, FileInfoDirect> map = queryEarthQuakeDate.get(key);
				   
				   String[] fiveOrOne = key.split("_");
				   //50Hz数据
				   if(fiveOrOne[1].equals("50")){
					   
					   excelRow51 = sheet51.createRow(rowNumber50++);
					   
					   int dateNum = 0;
					   Cell dateCell = excelRow51.createCell(dateNum++);
					   dateCell.setCellValue(Style.setEarthQuakeSiteNumber(key));
					   dateCell.setCellStyle(style);
					   for (String num : map.keySet()) {
						   
						   sheet51.setColumnWidth(dateNum, 3500); //第一个参数代表列id(从0开始),第2个参数代表宽度值
						   int fileFlag = map.get(num).getFileFlag();
						   dateCell = excelRow51.createCell(dateNum++);
						   dateCell.setCellValue("");
						   //dateCell.setCellStyle(style);
						   
						   if(fileFlag==1){
							   dateCell.setCellStyle(getStyle(workbook,IndexedColors.GREEN.getIndex(),false));
				           }else if(fileFlag>=0){
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.RED.getIndex(),false));
				           }else if(fileFlag>=3){
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.YELLOW.getIndex(),false));
				           }else{
				               dateCell.setCellStyle(getStyle(workbook,IndexedColors.WHITE.getIndex(),false));
				           }
						   
					   }
				   }
			 }
	       
	         fOut = response.getOutputStream();  
	         workbook.write(fOut);  
	     }catch (Exception e1){
	    	 e1.printStackTrace();
	     }finally{  
	         try {  
	             fOut.flush();  
	             fOut.close();  
	         }catch(IOException e){}  
	     }  
	     System.out.println("文件生成..."); 
	}
}
