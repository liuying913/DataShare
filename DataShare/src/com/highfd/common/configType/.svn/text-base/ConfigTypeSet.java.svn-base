package com.highfd.common.configType;

import java.util.List;

import com.highfd.bean.EarthQuakeApply;

public class ConfigTypeSet {
	
	/**
	 * 地震应急事件，申请参数
	 * 0:申请完成   3:申请通过  4:拒绝';
	 * @param list
	 */
	public static List<EarthQuakeApply> setEarthQuakeType(List<EarthQuakeApply> list){
		if(null!=list && list.size()>0){
			for(int i=0;i<list.size();i++){
				EarthQuakeApply info = list.get(i);
				int type = info.getType();
				if(type==4){
					info.setTypeStr("待审核");
				}else if(type==5){
					info.setTypeStr("审核通过");
				}else if(type==6){
					info.setTypeStr("未通过");
				}else if(type==7){
					info.setTypeStr("已过期");
				}
			}
		}
		return list;
	}

}
