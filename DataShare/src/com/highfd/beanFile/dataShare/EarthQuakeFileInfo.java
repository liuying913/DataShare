package com.highfd.beanFile.dataShare;

import java.util.List;

import com.highfd.bean.FileInfo;

public class EarthQuakeFileInfo {
	
	private String id;
	private String earthQuake;//50赫兹还是1赫兹
	private List<FileInfo> fileList;
	
	public EarthQuakeFileInfo() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEarthQuake() {
		return earthQuake;
	}
	public void setEarthQuake(String earthQuake) {
		this.earthQuake = earthQuake;
	}
	public List<FileInfo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FileInfo> fileList) {
		this.fileList = fileList;
	}
}
