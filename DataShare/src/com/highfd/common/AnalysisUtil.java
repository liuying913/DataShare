package com.highfd.common;

import java.text.DecimalFormat;

import com.highfd.beanFile.analysis.GroupByDepPro;

public class AnalysisUtil {
	
	public static String getAvgDown(GroupByDepPro gd){
		Double avgValue=Double.valueOf(gd.getDownNum())/ Double.valueOf(gd.getSiteInfoNum())/Double.valueOf(gd.getDayNum())   *300;
		DecimalFormat df = new DecimalFormat("#.00");
	    return df.format(Double.parseDouble(avgValue+""));
		
	}

}
