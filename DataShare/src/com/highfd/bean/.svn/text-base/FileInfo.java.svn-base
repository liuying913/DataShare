package com.highfd.bean;

import java.sql.Timestamp;

public class FileInfo implements Cloneable {
	
	private int id;
	private String fileYear;
	private String fileDayYear;
	private String siteNumber;
	private String siteName;
	private String fileName;
	private String filePath;
	private Double fileSize;
	private int fileFlag;//文件是否完整
	private String fileComp;//文件是否完整
	public String allFilePathName;
	private Timestamp fileCreateTime;
	private Timestamp systemTime;
	private String ephemNumber;//历元数量
	
	private int earthQuakeId;//地震应急 id
	private String earthQuake50Or1;//应急数据的 50赫兹还是1赫兹
	private int earthQuakeDay;//第几天 共三天
	private int earthQuakeNum;//某天，第几个
	
	private String zoneName;//为了 树状结构而设定
	
	private Timestamp systemTimeStr;//为了整理数据 上传而设置的
	private String systemMonthDayNumer;//为了整理数据 上传而设置的
	
	
	
	private String ephem_number;
	private String mp1;
	private String mp2;
	private String o_slps;
	
	private String type;
	public String systemStr;
	public FileInfo() {
		super();
	}
	
	@Override
	public Object clone(){
		FileInfo info = null;
		try {
			info=(FileInfo)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSystemMonthDayNumer() {
		return systemMonthDayNumer;
	}
	public void setSystemMonthDayNumer(String systemMonthDayNumer) {
		this.systemMonthDayNumer = systemMonthDayNumer;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getFileYear() {
		return fileYear;
	}
	public void setFileYear(String fileYear) {
		this.fileYear = fileYear;
	}
	public String getFileDayYear() {
		return fileDayYear;
	}
	public void setFileDayYear(String fileDayYear) {
		this.fileDayYear = fileDayYear;
	}
	public Double getFileSize() {
		return fileSize;
	}
	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}
	public int getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(int fileFlag) {
		this.fileFlag = fileFlag;
	}
	public Timestamp getFileCreateTime() {
		return fileCreateTime;
	}
	public void setFileCreateTime(Timestamp fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}
	public Timestamp getSystemTimeStr() {
		return systemTimeStr;
	}
	public void setSystemTimeStr(Timestamp systemTimeStr) {
		this.systemTimeStr = systemTimeStr;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getFileComp() {
		return fileComp;
	}
	public void setFileComp(String fileComp) {
		this.fileComp = fileComp;
	}
	public String getEarthQuake50Or1() {
		return earthQuake50Or1;
	}
	public void setEarthQuake50Or1(String earthQuake50Or1) {
		this.earthQuake50Or1 = earthQuake50Or1;
	}
	public int getEarthQuakeNum() {
		return earthQuakeNum;
	}
	public void setEarthQuakeNum(int earthQuakeNum) {
		this.earthQuakeNum = earthQuakeNum;
	}
	public int getEarthQuakeDay() {
		return earthQuakeDay;
	}
	public void setEarthQuakeDay(int earthQuakeDay) {
		this.earthQuakeDay = earthQuakeDay;
	}
	public String getAllFilePathName() {
		return allFilePathName;
	}
	public void setAllFilePathName(String allFilePathName) {
		this.allFilePathName = allFilePathName;
	}
	public String getEphemNumber() {
		return ephemNumber;
	}
	public void setEphemNumber(String ephemNumber) {
		this.ephemNumber = ephemNumber;
	}
	public String getEphem_number() {
		return ephem_number;
	}
	public void setEphem_number(String ephemNumber) {
		ephem_number = ephemNumber;
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
	public String getSystemStr() {
		return systemStr;
	}
	public void setSystemStr(String systemStr) {
		this.systemStr = systemStr;
	}
	public int getEarthQuakeId() {
		return earthQuakeId;
	}
	public void setEarthQuakeId(int earthQuakeId) {
		this.earthQuakeId = earthQuakeId;
	}

}
