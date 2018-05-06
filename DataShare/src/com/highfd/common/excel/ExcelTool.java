package com.highfd.common.excel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTool {
    

    public static void main(final String[] args) throws Exception{
        //ExcelTool excel = new ExcelTool();
        //if(excel.createExcelFile()) {
            System.out.println("data.xlsx is created successfully.");
            new ExcelTool().createExcelFile();
        //}
    }
    @SuppressWarnings("deprecation")
	public boolean createExcelFile() throws IOException {
        boolean isCreateSuccess = false;
        Workbook workbook = new XSSFWorkbook();
        if(workbook != null) {
        	
            Sheet sheet = workbook.createSheet("erer");//sheet页
            Row titleRow = sheet.createRow(0);
            String[] titleArray = "日期,行业,职位/简历数".split(","); //中文标题
            for(int i = 0; i < titleArray.length; i++) {
                Cell titleCell = titleRow.createCell(i, Cell.CELL_TYPE_STRING);
                CellStyle style = getStyle(workbook);
                titleCell.setCellStyle(style);
                titleCell.setCellValue(titleArray[i]);
                sheet.autoSizeColumn(i);
            }
            List<String> dataList = new ArrayList<String>();
            dataList.add("1");dataList.add("2");dataList.add("3");dataList.add("4");
            for (int rowNum = 0; rowNum < dataList.size(); rowNum++) {
            	String piData = dataList.get(rowNum);
                Row row = sheet.createRow(rowNum+1);
                //for(int i = 0; i < 3; i++) {
                    Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
                    cell.setCellValue(piData);
                    
                    cell = row.createCell(1, Cell.CELL_TYPE_STRING);
                    cell.setCellValue(piData);
                    
                    cell = row.createCell(2, Cell.CELL_TYPE_STRING);
                    cell.setCellValue(piData);
                //}
            }
            FileOutputStream outputStream = new FileOutputStream("D:/"+new Date().getSeconds()+".xlsx");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            isCreateSuccess = true;
        }
        return isCreateSuccess;
    }
    private CellStyle getStyle(final Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); 
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short)14);
        //headerFont.setColor(HSSFColor.RED.index);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);

        // 设置单元格边框及颜色
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setWrapText(true);
        
        return style;
    }
}