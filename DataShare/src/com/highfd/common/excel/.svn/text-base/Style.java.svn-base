package com.highfd.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class Style {
	
	
    public static CellStyle getStyle(final Workbook workbook,final short index,final boolean isYear){
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
		style.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		
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

    
    public static String setEarthQuakeSiteNumber(String key){
    	String[] fiveOrOne = key.split("_");
    	if(fiveOrOne[1].equals("1")){
    		return fiveOrOne[0]+"震前一天";
    	}else if(fiveOrOne[1].equals("2")){
    		return fiveOrOne[0]+"地震当天";
    	}else if(fiveOrOne[1].equals("3")){
    		return fiveOrOne[0]+"震后一天";
    	}else{
    		return fiveOrOne[0];
    	}
    }
}
