package com.highfd.bean;
/**
 * 数据质量 （数据动态用，每月一个）
 * @author 刘颖
 *
 */
public class DataQuality {
	public String id;
	public String siteName;
	public String siteNumber;
	public String daySum;//应该有的总天数
	public String dayValieSum;//有效天数
	public int fileFlag;
	public String zoneName;
	public String departName;
	public String fileSize;
	
	public String ephemNumber;
	public String integrityRate;//完整率
	public String mp1;
	public String mp2;
	public String o_slps;
	
	public String title;
	public String content;
	public String time;
	public String year;
	public String month;
	public String type;
	
	
	//地震应急
	public String earthQuakeName;
	public String earthQuakeId;
	public String earthQuakeTimeStr;
	public String siteLat;//纬度
	public String siteLon;//经度
	public String grade;//级别
	public String address;//地点
	public String height;//震源深度
	public String remark;//备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getDaySum() {
		return daySum;
	}
	public void setDaySum(String daySum) {
		this.daySum = daySum;
	}
	public String getDayValieSum() {
		return dayValieSum;
	}
	public void setDayValieSum(String dayValieSum) {
		this.dayValieSum = dayValieSum;
	}
	public int getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(int fileFlag) {
		this.fileFlag = fileFlag;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getEphemNumber() {
		return ephemNumber;
	}
	public void setEphemNumber(String ephemNumber) {
		this.ephemNumber = ephemNumber;
	}
	public String getIntegrityRate() {
		return integrityRate;
	}
	public void setIntegrityRate(String integrityRate) {
		this.integrityRate = integrityRate;
	}
	public String getMp1() {
		return mp1;
	}
	public void setMp1(String mp1) {
		this.mp1 = mp1;
	}
	public String getMp2() {
		return mp2;
	}
	public void setMp2(String mp2) {
		this.mp2 = mp2;
	}
	public String getO_slps() {
		return o_slps;
	}
	public void setO_slps(String oSlps) {
		o_slps = oSlps;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSiteLat() {
		return siteLat;
	}
	public void setSiteLat(String siteLat) {
		this.siteLat = siteLat;
	}
	public String getSiteLon() {
		return siteLon;
	}
	public void setSiteLon(String siteLon) {
		this.siteLon = siteLon;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEarthQuakeTimeStr() {
		return earthQuakeTimeStr;
	}
	public void setEarthQuakeTimeStr(String earthQuakeTimeStr) {
		this.earthQuakeTimeStr = earthQuakeTimeStr;
	}
	public String getEarthQuakeId() {
		return earthQuakeId;
	}
	public void setEarthQuakeId(String earthQuakeId) {
		this.earthQuakeId = earthQuakeId;
	}
	public String getEarthQuakeName() {
		return earthQuakeName;
	}
	public void setEarthQuakeName(String earthQuakeName) {
		this.earthQuakeName = earthQuakeName;
	}
	
}
