package com.highfd.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.highfd.bean.DataQuality;
import com.highfd.bean.DataQualityInfo;
import com.highfd.bean.NewsInfo;
import com.highfd.bean.ideaInfo;
/**
 * DAO层
 * @author Douban73
 */
public interface HighFDDao {

	public List<NewsInfo>  getNewsInfoList(String id,String type, String selectParam);
	
	//获得最近30天新闻
	public List<NewsInfo>  getNewsInfoList(String id,String type);
	
	//添加合作交流明细
	public void insertIdea(final ideaInfo info) throws Exception;
	
	//添加新闻
	public void insertNews(final NewsInfo info) throws Exception;
	
	//修改新闻
	public void updateNews(final NewsInfo info) throws Exception;
	/**
	 * 合作交流列表
	 */
	public List<ideaInfo> ideaList(String id) throws Exception;
	
	//删除合作交流
	public void deleteIdea(final int id) throws Exception;
	
	
	/**
	 * 数据动态  新闻列表
	 */
	public List<DataQuality> data30_DataQualityList (String id,String type) throws Exception;
	
	/**
	 * 添加   首页 每月数据整理状态
	 */
	public void insertDataQualityInfo(final DataQualityInfo info) throws Exception;
	/**
	 * 数据动态，整理的每月的数据
	 */
	public List<DataQuality> data30_DataQuality (String year,String month,String type) throws Exception;
	
	/**
	 * 删除新闻
	 */
	public void deleteNewsInfo(final int id) throws Exception;


	/**
	 * 删除数据质量记录信息
	 */
	public void deleteDataQualityById(final String id) throws Exception;
	
	/**
	 * 模糊查询  每月数据整理状态
	 */
	public List<DataQuality> select_DataQualityList (String iniparam) throws Exception;
}
