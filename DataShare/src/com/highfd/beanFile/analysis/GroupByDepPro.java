package com.highfd.beanFile.analysis;

public class GroupByDepPro {
	public String groupCnName;//部委名称、省份名称（中文名称）
	public String groupName;//部委名称、省份名称
	public String downNum;//单个部委的下载数量和
	public String avgDownNum;//单个部委下载和 平均到 单个文件上面
	public String siteInfoNum;//该部委、省份 的 台站数量
	public String dayNum;
	
	//部委    部委名称  下载数量  部委站数量                    平均下载次数
	
	public GroupByDepPro() {
		super();
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDownNum() {
		return downNum;
	}
	public void setDownNum(String downNum) {
		this.downNum = downNum;
	}
	public String getSiteInfoNum() {
		return siteInfoNum;
	}
	public void setSiteInfoNum(String siteInfoNum) {
		this.siteInfoNum = siteInfoNum;
	}
	public String getAvgDownNum() {
		return avgDownNum;
	}
	public void setAvgDownNum(String avgDownNum) {
		this.avgDownNum = avgDownNum;
	}
	public String getDayNum() {
		return dayNum;
	}
	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}
	public String getGroupCnName() {
		return groupCnName;
	}
	public void setGroupCnName(String groupCnName) {
		this.groupCnName = groupCnName;
	}
	
	

}
