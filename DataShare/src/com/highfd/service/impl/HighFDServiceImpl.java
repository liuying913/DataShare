package com.highfd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.bean.DataQuality;
import com.highfd.bean.DataQualityInfo;
import com.highfd.bean.EarthQuake;
import com.highfd.bean.NewsInfo;
import com.highfd.bean.ideaInfo;
import com.highfd.common.ZZBDS.NewsZZBDS;
import com.highfd.dao.HighFDDao;
import com.highfd.service.HighFDService;

@Service
public class HighFDServiceImpl implements HighFDService {
	
	@Autowired
	HighFDDao dao;
	
	//获得所有的台站
	public List<NewsInfo>  getNewsInfoList(String id,String type, String selectParam) {
		return dao.getNewsInfoList(id,type, selectParam);
	}
	
	//获得最近30天新闻
	public List<NewsInfo>  getNewsInfoList(String id,String type){
		return dao.getNewsInfoList(id, type);
	}
	//添加合作交流明细
	public void insertIdea(final ideaInfo info) throws Exception {
		dao.insertIdea(info);
	}
	
	//添加新闻
	public void insertNews(final String contents) throws Exception {
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setContent(contents);
		newsInfo.setTitle(NewsZZBDS.getTitle(contents));
		newsInfo.setType("1");
		dao.insertNews(newsInfo);
	}
	
	
	//新增新闻
		public void insertNews(final NewsInfo info) throws Exception {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setId(info.getId());
			newsInfo.setTitle(info.getTitle());
			newsInfo.setTime(info.getTime());
			newsInfo.setContent(info.getContent());
			newsInfo.setType("1");
			newsInfo.setOrders(info.getOrders());
			dao.insertNews(newsInfo);
		}
	
	//更新新闻
	public void updateNews(final NewsInfo info) throws Exception {
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setId(info.getId());
		newsInfo.setTitle(info.getTitle());
		newsInfo.setContent(info.getContent());
		newsInfo.setOrders(info.getOrders());
		dao.updateNews(newsInfo);
	}
	
	/**
	 * 合作交流列表
	 */
	public List<ideaInfo> ideaList(String id) throws Exception {
		return dao.ideaList(id);
	}
	
	//删除合作交流
	public void deleteIdea(final int id) throws Exception{
		dao.deleteIdea(id);
	}
	
	
	
	/**
	 * 添加   首页 每月数据整理状态
	 */
	public void insertDataQualityInfo(final String yearMonthDay) throws Exception {
		DataQualityInfo info = new DataQualityInfo();
		String year = yearMonthDay.substring(0, 4);
		String month = yearMonthDay.substring(5, 7);
		info.setYear(year);
		info.setMonth(month);
		String title = "陆态30S日常数据"+info.getYear()+"年"+info.getMonth()+"月份整理完毕！";
		String content = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+info.getYear()+"年"+info.getMonth()+"月份陆态网络GNSS基准站30s采样率数据已汇集整理完毕，需要使用的单位或个人，请自行申请下载。</br>";
		info.setTitle(title);
		info.setContent(content);
		info.setType("1");
		dao.insertDataQualityInfo(info);
	}
	
	/**
	 * 添加   地震应急数据 整理情况
	 */
	public void insertEarthQuakeDataQualityInfo(final EarthQuake earthQuake) throws Exception {
		
		String startTime = earthQuake.getStrTime();
		DataQualityInfo info = new DataQualityInfo();
		String year = startTime.substring(0, 4);
		String month = startTime.substring(5, 7);
		String day = startTime.substring(8, 10);
		info.setYear(year);
		info.setMonth(month);
		info.setDay(day);
		String title = info.getYear()+"年"+info.getMonth()+"月"+info.getDay()+"日  "+earthQuake.getName()+earthQuake.getGrade()+"级地震陆态网络GNSS高频应急数据已汇集整理完毕！";
		String content = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+info.getYear()+"年"+info.getMonth()+"月"+info.getDay()+"日  "+earthQuake.getName()+earthQuake.getGrade()+"级地震陆态网络GNSS高频（1Hz和50Hz）应急数据已汇集整理完毕，需要使用的单位或个人，请自行申请下载。</br>";
		info.setTitle(title);
		info.setContent(content);
		info.setType("4");
		info.setEarthquakeid(earthQuake.getId());
		dao.insertDataQualityInfo(info);
	}
	/**
	 * 查询  首页 每月数据整理状态
	 */
	public List<DataQuality> data30_DataQualityList (String id,String type) throws Exception {
		return dao.data30_DataQualityList(id, type);
	}
	/**
	 * 数据动态，整理的每月的数据
	 */
	public List<DataQuality> data30_DataQuality (DataQuality data) throws Exception {
		return dao.data30_DataQuality(data.getYear(), data.getMonth(),data.getType());
	}
	
	/**
	 * 删除新闻
	 */
	public void deleteNewsInfo(final int id) throws Exception {
		dao.deleteNewsInfo(id);
	}

	
	


}
