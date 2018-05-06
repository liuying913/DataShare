package com.highfd.common.linux;

import java.io.File;
import com.highfd.service.impl.Z_DataReductionServiceImpl;

public class MetTypeTransfrom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		analysisAll_M(new File("/root/nfs/Gnssdata/met_files"));
		analysisAll_M(new File("/root/tmp_nas/gnssdata/CMONOC-GNSS"));
		analysisAll_M(new File("/home/liuying/To2Ftp"));
	}
	
	
	
	//查询所有的o文件，并进行压缩 转换 入库
	public static void analysisAll_M(final File dir){
		File[] fs = dir.listFiles();
		if(null==fs){return;}
		for(int i=0; i<fs.length; i++){
			if(fs[i].isDirectory()){
				try{
					analysisAll_M(fs[i]);
				}catch(Exception e){}
			}else{
				String fileName = fs[i].getName();
				if(fileName.toLowerCase().endsWith("m")){//主要小写这里
					String year2 = fileName.substring(fileName.length()-3, fileName.length()-1);
					String year4 = "20"+year2;
					String siteNumber = fileName.substring(0, 4).toUpperCase();
					String yearDay = fileName.substring(4, 7);
					//String dataStr = DayNumberOfOneYear.yearDayToDate(Integer.valueOf(yearDay), year4);
					String sitePath = fs[i].getParent();
					Z_DataReductionServiceImpl.copyN_File(siteNumber, year4, year2, yearDay, sitePath);
				}
			}
		}
	}

}
