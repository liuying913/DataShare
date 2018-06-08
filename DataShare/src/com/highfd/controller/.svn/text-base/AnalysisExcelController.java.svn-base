package com.highfd.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.common.excel.Style;
import com.highfd.service.ApplyFileService;
import com.highfd.service.DownHistoryService;
import com.highfd.service.SiteStationService;

@Component
@Controller
@RequestMapping(value="/analysisExcel")
public class AnalysisExcelController {

	@Autowired
	ApplyFileService applyFileService;
	@Autowired
	SiteStationService siteService;
	@Autowired
	DownHistoryService downHistoryService;

	//统计分析  地区导出
	@RequestMapping("s30S/zone")  
	 public void zone(HttpServletRequest request, HttpServletResponse response){
		
		String fileType = request.getParameter("fileType");//30日常数据
		String siteType = request.getParameter("siteType");;//台站类型
		String userId = request.getParameter("userId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		System.out.println(fileType +"  "+ siteType);
		List<GroupByDepPro> resultList = downHistoryService.zoneDown(fileType,siteType, startTime, endTime,userId);
	    String fileName = "文件下载地区统计";
	
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
	         boolean isYear = false;
	       
	         //月度标题
	         Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("省份名称");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 2800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
             tilteCell = titleRow.createCell(1);//创建一列  
             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
             //tilteCell.setCellStyle(style);//设置居中
             tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
             tilteCell.setCellValue("总下载数量");
        	 sheet.setColumnWidth(1, 3600); //第一个参数代表列id(从0开始),第2个参数代表宽度值
         
        	 
        	 tilteCell = titleRow.createCell(2);//创建一列  
             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
             //tilteCell.setCellStyle(style);//设置居中
             tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
             tilteCell.setCellValue("平均下载数量");
        	 sheet.setColumnWidth(2, 4800); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	        //数据
	         for (GroupByDepPro gbp : resultList) {
	            
	             Row row = sheet.createRow(rowNumber++);//创建行
	             Cell cell = row.createCell(0);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getGroupCnName());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(1);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getDownNum());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(2);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getAvgDownNum());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
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
	
	
	//统计分析  部委 导出
	@RequestMapping("s30S/dep")  
	 public void dep(HttpServletRequest request, HttpServletResponse response){
		
		String fileType = request.getParameter("fileType");//30日常数据
		String siteType = request.getParameter("siteType");;//台站类型
		String userId = request.getParameter("userId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		System.out.println(fileType +"  "+ siteType);
		List<GroupByDepPro> resultList = downHistoryService.departmentDown(fileType,siteType, startTime, endTime,userId);
	    String fileName = "文件下载部委统计";
	
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
	         boolean isYear = false;
	       
	         //月度标题
	         Row titleRow = sheet.createRow(rowNumber++);//创建标题行
	         Cell tilteCell = titleRow.createCell(0);//创建一列
	         tilteCell.setCellValue("省份名称");
	         //tilteCell.setCellStyle(style);//设置居中
	         tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	         sheet.setColumnWidth(0, 4200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
             tilteCell = titleRow.createCell(1);//创建一列  
             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
             //tilteCell.setCellStyle(style);//设置居中
             tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
             tilteCell.setCellValue("总下载数量");
        	 sheet.setColumnWidth(1, 4200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
         
        	 
        	 tilteCell = titleRow.createCell(2);//创建一列  
             tilteCell.setCellType(Cell.CELL_TYPE_STRING);  
             //tilteCell.setCellStyle(style);//设置居中
             tilteCell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
             tilteCell.setCellValue("平均下载数量");
        	 sheet.setColumnWidth(2, 4200); //第一个参数代表列id(从0开始),第2个参数代表宽度值
	         
	        //数据
	         for (GroupByDepPro gbp : resultList) {
	            
	             Row row = sheet.createRow(rowNumber++);//创建行
	             Cell cell = row.createCell(0);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getGroupCnName());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(1);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getDownNum());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
	             
	             cell = row.createCell(2);//创建列  
	             cell.setCellType(Cell.CELL_TYPE_STRING);  
	             cell.setCellValue(gbp.getAvgDownNum());  
	             cell.setCellStyle(style);//设置居中
	             cell.setCellStyle(Style.getStyle(workbook,IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(),isYear));//设置背景颜色
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
