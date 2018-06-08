package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.bean.DicInfo;
import com.highfd.bean.EarthQuakeConfig;
import com.highfd.bean.SiteInfo;
import com.highfd.bean.UserInfo;
import com.highfd.bean.ZoneInfo;
import com.highfd.beanFile.analysis.GroupByDepPro;
import com.highfd.common.map.MapAll;
import com.highfd.dao.SiteStationDAO;

@Resource
public class SiteStationDAOImpl implements SiteStationDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
	
	
	//搜索   省份  台站  部位  查询 台站信息
	public List<SiteInfo>  getSiteInfoList(String siteType,String siteParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select s.site_number,s.site_name,s.site_lat,s.SITE_Lon,s.SITE_ADDRESS,s.site_unit,s.site_progect,s.site_old_number,z.zone_code,z.zone_name,d.dic_cn_name,d.dic_en_name,s.site_type from site_info s,zone_info z ,dic_info d where s.site_zone = z.zone_code and s.SITE_DEPARTMENT =d.dic_en_name");
		if(null!= siteType  && !"".equals(siteType)){
			sql.append( " and s.site_type= "+siteType);
		}
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (s.site_number like '%"+siteParam+"%' or s.site_name like '%"+siteParam+"%' or  z.zone_name like '%"+siteParam+"%' or d.dic_cn_name like '%"+siteParam+"%')");
		}
		//sql.append(" and s.site_number in('XIAG','YNCX','YNJD','YNLJ','YNSD','YNTC','YNYA','YNYL','YNYS')");
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartmentCode(rs.getString("dic_en_name"));
		    	info.setDepartmentName(rs.getString("dic_cn_name"));
		    	
		    	info.setSiteUnit(rs.getString("site_unit"));
		    	info.setSiteProgect(rs.getString("site_progect"));
		    	info.setSiteOldNumber(rs.getString("site_old_number"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	info.setSiteType(rs.getString("site_type"));
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//搜索   省份  台站  部位  查询 台站信息
	public List<SiteInfo>  getSiteInfoListByIn(String siteType,String siteParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select s.site_number,s.site_name,s.site_lat,s.SITE_Lon,s.SITE_ADDRESS,s.site_unit,s.site_progect,s.site_old_number,z.zone_code,z.zone_name,d.dic_cn_name,d.dic_en_name,s.site_type from site_info s,zone_info z ,dic_info d where s.site_zone = z.zone_code and s.SITE_DEPARTMENT =d.dic_en_name");
		if(null!= siteType  && !"".equals(siteType)){
			sql.append( " and s.site_type= "+siteType);
		}
		//如果选择了台站，那么将不走 部委 跟 省份
		//站点列表
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteParam && !"".equals(siteParam)){
			String[] split = siteParam.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and s.SITE_NUMBER in("+siteNumberList+") ");
		}
		//sql.append(" and s.site_number in('XIAG','YNCX','YNJD','YNLJ','YNSD','YNTC','YNYA','YNYL','YNYS')");
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartmentCode(rs.getString("dic_en_name"));
		    	info.setDepartmentName(rs.getString("dic_cn_name"));
		    	
		    	info.setSiteUnit(rs.getString("site_unit"));
		    	info.setSiteProgect(rs.getString("site_progect"));
		    	info.setSiteOldNumber(rs.getString("site_old_number"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	info.setSiteType(rs.getString("site_type"));
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//根据  经纬度、省份列表、台站列表、台站名称 进行查询
	public List<SiteInfo> getSuperSiteInfoList(String siteNumberArray,String siteName,String zoneArray,String siteLat1,String siteLat2,String siteLon1,String siteLon2,String type){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.site_number,t.site_name,t.site_type, z.zone_code,z.zone_name,t.site_lat,t.SITE_Lon,t.SITE_ADDRESS  from site_info t ,zone_info z where t.SITE_ZONE=z.zone_code ");
		
		//如果选择了台站，那么将不走 部委 跟 省份
		//站点列表
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}else{
			/*//部委列表
			StringBuffer departmentList = new StringBuffer();
			if(null!=departmentArray && !"".equals(departmentArray)){
				String[] split = departmentArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						departmentList.append("'"+split[i]+"'");
					}else{
						departmentList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_DEPARTMENT in("+departmentList+") ");
			}*/
			
			//省份列表
			StringBuffer zoneList = new StringBuffer();
			if(null!=zoneArray && !"".equals(zoneArray)){
				String[] split = zoneArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						zoneList.append("'"+split[i]+"'");
					}else{
						zoneList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_zone in("+zoneList+") ");
			}
		}
		
		//台站名称
		if(null!=siteName && !"".equals(siteName)){
			sql.append(" and t.SITE_name = '"+siteName+"' ");
		}
		
		if(siteLat2!=null && !"0".equals(siteLat2) && siteLat1!=null && !"0".equals(siteLat1)){
			//纬度
			if(null!=siteLat1 && null!=siteLat2 && !"".equals(siteLat1) && !"".equals(siteLat2)){
				sql.append(" and t.site_lat>="+siteLat1 +" and t.site_lat<="+siteLat2+" ");
			}
		}
		
		if(null!=siteLon1 && !"0".equals(siteLon1) && null!=siteLon2 && !"0".equals(siteLon2)){
			//纬度
			if(null!=siteLon1 && null!=siteLon2 && !"".equals(siteLon1) && !"".equals(siteLon2)){
				sql.append(" and t.SITE_Lon>="+siteLon1 +" and t.SITE_Lon<="+siteLon2+" ");
			}
		}
		
		
		//台站类型
		if(null!=type && !"".equals(type)){
			sql.append(" and t.SITE_type in ("+type+") ");
		}
		
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//根据  部委列表  省份列表、台站列表、台站名称 进行查询
	public List<SiteInfo> getSiteInfoList(String siteNumberArray,String departmentArray,String zoneArray,String type){
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select t.site_number,t.site_name,t.site_type, z.zone_code,z.zone_name,t.site_lat,t.SITE_Lon,t.SITE_ADDRESS  from site_info t ,zone_info z where t.SITE_ZONE=z.zone_code ");
		
		
		//如果选择了台站，那么将不走 部委 跟 省份
		//站点列表
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}else{
			//部委列表
			StringBuffer departmentList = new StringBuffer();
			if(null!=departmentArray && !"".equals(departmentArray)){
				String[] split = departmentArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						departmentList.append("'"+split[i]+"'");
					}else{
						departmentList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_DEPARTMENT in("+departmentList+") ");
			}
			
			//省份列表
			StringBuffer zoneList = new StringBuffer();
			if(null!=zoneArray && !"".equals(zoneArray)){
				String[] split = zoneArray.split(",");
				for(int i=0;i<split.length;i++){
					if(i==split.length-1){
						zoneList.append("'"+split[i]+"'");
					}else{
						zoneList.append("'"+split[i]+"',");
					}
				}
				sql.append(" and t.SITE_zone in("+zoneList+") ");
			}
		}
		
		//台站类型
		if(null!=type && !"".equals(type)){
			sql.append(" and t.SITE_type in ("+type+") ");
		}
		
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	info.setAddress(rs.getString("site_address"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	public List<SiteInfo>  getZoneSiteInfoList(String smallLat,String bigLat,String smallLon,String bigLon,String siteTypeStr){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t.site_number,t.site_name,t.site_type,z.zone_id,z.zone_code,z.zone_name,t.SITE_DEPARTMENT,z.orders from site_info t, zone_info z  where t.site_zone=z.zone_code ");
		
		//纬度
		if(null!=smallLat && !"".equals(smallLat) && null!=bigLat && !"".equals(bigLat) ){
			sql.append(" and t.site_lat>"+smallLat+" and t.site_lat<"+bigLat);
		}
		//经度
		if(null!=smallLon && !"".equals(smallLon) && null!=bigLon && !"".equals(bigLon) ){
			sql.append(" and t.SITE_Lon>"+smallLon+" and t.SITE_Lon<"+bigLon);
		}
		//台站类型
		if(null!=siteTypeStr && !"".equals(siteTypeStr)){
			sql.append(" and t.SITE_type ="+siteTypeStr);
		}
		sql.append(" order by z.orders");
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	//System.out.println(rs.getString("SITE_DEPARTMENT") +"  测试");
		    	//System.out.println(MapAll.mapDepat.get(rs.getString("SITE_DEPARTMENT")));
		    	if(null==MapAll.mapDepat.get(rs.getString("SITE_DEPARTMENT"))){
		    		//System.out.println("部委null");
		    	}
		    	info.setDepartmentCode(rs.getString("SITE_DEPARTMENT")==null?"":rs.getString("SITE_DEPARTMENT"));
		    	info.setDepartmentName(rs.getString("SITE_DEPARTMENT")==null?"":MapAll.mapDepat.get(rs.getString("SITE_DEPARTMENT")).getDicCnName());
		    	info.setOrder(rs.getString("orders")==null?0:Integer.valueOf(rs.getString("orders")));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	public List<SiteInfo>  applySiteZoneList(String applyId,String siteType){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.site_number,t.site_name,t.site_type,z.zone_id,z.zone_code,z.zone_name from site_info t, zone_info z,apply_user_siteInfo a where t.site_zone=z.zone_code and  a.site_number = t.site_number ");
		//台站类型
		//sql.append("and t.site_type=1 ");
		//申请id
		if(null!=applyId && !"".equals(applyId) ){
			sql.append("  and a.apply_id= "+applyId);
		}
		//申请的台站类型
		if(null!=siteType && !"".equals(siteType) ){
			sql.append(" and a.type="+siteType);
		}
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//获得广域站列表
	public List<SiteInfo>  getZoneSiteInfoList_GY(String smallLat,String bigLat,String smallLon,String bigLon){
		StringBuffer sql = new StringBuffer();
		sql.append("select SITE_NUMBER,SITE_NAME,SITE_ZONE,SITE_DEPARTMENT,site_lat,SITE_Lon,SITE_ADDRESS,site_unit,site_progect,site_type,site_old_number,z.zone_id,z.zone_code,z.zone_name from site_info t, zone_info z  where t.site_zone=z.zone_code ");
		
		//台站类型
		sql.append("and t.site_type=2 ");

		//纬度
		if(null!=smallLat && !"".equals(smallLat) && null!=bigLat && !"".equals(bigLat) ){
			sql.append(" and t.site_lat>"+smallLat+" and t.site_lat<"+bigLat);
		}
		//经度
		if(null!=smallLon && !"".equals(smallLon) && null!=bigLon && !"".equals(bigLon) ){
			sql.append(" and t.SITE_Lon>"+smallLon+" and t.SITE_Lon<"+bigLon);
		}
		
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartmentCode(rs.getString("SITE_DEPARTMENT"));
		    	info.setDepartmentName(MapAll.mapDepat.get(rs.getString("SITE_DEPARTMENT")).getDicCnName());
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//获得  申请界面  广域站列表
	public List<SiteInfo>  applyZoneSiteList_GY(String applyId,String siteType){
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.SITE_NUMBER,t.SITE_NAME,z.zone_code,z.zone_name ,t.site_type from site_info t, zone_info z ,apply_user_siteInfo a where t.site_zone=z.zone_code and  a.site_number = t.site_number ");
		//台站类型
		sql.append("and t.site_type=2 ");
		//申请id
		if(null!=applyId && !"".equals(applyId) ){
			sql.append("  and a.apply_id= "+applyId);
		}
		//申请的台站类型
		if(null!=siteType && !"".equals(siteType) ){
			sql.append(" and a.type="+siteType);
		}
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSiteType(rs.getString("site_type"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	
	//通过省份编号 获得台站列表
	public String getSiteNumberListByZoneCode(String type,String zoneArray){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.SITE_NUMBER from site_info t where 1=1 ");
		//台站类型
		sql.append("and t.site_type= "+type);
		//省份
		StringBuffer zoneCodeList = new StringBuffer();
		if(null!=zoneArray && !"".equals(zoneArray)){
			String[] split = zoneArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					zoneCodeList.append("'"+split[i]+"'");
				}else{
					zoneCodeList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_ZONE in("+zoneCodeList+") ");
		}
		
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	
				return info;
		   }
		});
		
		StringBuffer bf = new StringBuffer("");
		for(int i=0;i<eventInfoList.size();i++){
			SiteInfo siteInfo = eventInfoList.get(i);
			bf.append(siteInfo.getSiteNumber()+",");
		}
		bf.deleteCharAt(bf.length()-1);
		return bf.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    //获得  省份  列表
    public List<ZoneInfo>  getZoneInfoList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.zone_id,t.zone_code,t.zone_name from zone_info t where 1=1");
		@SuppressWarnings({ "unchecked"})
		List<ZoneInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ZoneInfo info = new ZoneInfo();
		    	info.setZoneId(Integer.valueOf(rs.getString("zone_id")));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
				return info;
		   }
		});
		return eventInfoList;
	}
    
    //获得 部位 列表
	public List<DicInfo>  getDicInfoList(String dicTypeCode){
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dic_info t where 1=1 and t.dic_code =18 and t.dic_cn_name='国家测绘地理信息局' and t.dic_en_name='depart0001' and t.dic_type_code='dic-001'");
		sql.append("select * from dic_info t ");
		if(null!=dicTypeCode && !"".equals(dicTypeCode)){
			sql.append(" where t.DIC_TYPE_CODE ='"+dicTypeCode+"'");
		}
		@SuppressWarnings({ "unchecked"})
		List<DicInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	DicInfo info = new DicInfo();
		    	
		    	info.setDicCode(rs.getString("dic_code"));
		    	info.setDicCnName(rs.getString("dic_cn_name"));
		    	info.setDicEnName(rs.getString("dic_en_name"));
		    	info.setDicOrder(rs.getString("dic_order"));
		    	info.setDicTypeCode(rs.getString("dic_type_code"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	//获得 部位 分组
	public Map<String,String> departmentGroup(String siteType){
		Map<String,String> map = new HashMap<String,String>();
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dic_info t where 1=1 and t.dic_code =18 and t.dic_cn_name='国家测绘地理信息局' and t.dic_en_name='depart0001' and t.dic_type_code='dic-001'");
		sql.append("select s.site_department,count(*) as couNum from site_info s, dic_info d where s.site_department=d.dic_en_name and  d.dic_type_code='dic-001' ");
		if(null!=siteType && !"".equals(siteType)){
			sql.append(" and s.site_type="+siteType);
		}
		sql.append(" group by s.site_department");
		@SuppressWarnings({ "unchecked"})
		List<GroupByDepPro> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GroupByDepPro info = new GroupByDepPro();
		    	info.setGroupName(rs.getString("site_department"));
		    	info.setDownNum(rs.getString("couNum"));
				return info;
		   }
		});
		for(int i=0;i<eventInfoList.size();i++){
			GroupByDepPro ro = eventInfoList.get(i);
			map.put(ro.getGroupName(), ro.getDownNum());
		}
		return map;
	}
	
	//获得 省份 分组
	public Map<String,String> zoneGroup(String siteType){
		Map<String,String> map = new HashMap<String,String>();
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dic_info t where 1=1 and t.dic_code =18 and t.dic_cn_name='国家测绘地理信息局' and t.dic_en_name='depart0001' and t.dic_type_code='dic-001'");
		sql.append("select s.site_zone,count(*) couNum from site_info s, zone_info z where s.site_zone=z.zone_code ");
		if(null!=siteType && !"".equals(siteType)){
			sql.append(" and s.site_type="+siteType);
		}
		sql.append(" group by s.site_zone");
		@SuppressWarnings({ "unchecked"})
		List<GroupByDepPro> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	GroupByDepPro info = new GroupByDepPro();
		    	info.setGroupName(rs.getString("site_zone"));
		    	info.setDownNum(rs.getString("couNum"));
				return info;
		   }
		});
		for(int i=0;i<eventInfoList.size();i++){
			GroupByDepPro ro = eventInfoList.get(i);
			map.put(ro.getGroupName(), ro.getDownNum());
		}
		return map;
	}
	
	
	/**
	 * 根据ID获得台站信息
	 */
	@SuppressWarnings("unchecked")
	public UserInfo getBaseSiteInfoById(final String siteNumber) throws Exception {
		String sql = "select * from site_info where site_number = ?";
		String[] code = new String[] { siteNumber };
		UserInfo obj = (UserInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							SiteInfo info = new SiteInfo();
					    	info.setSiteNumber(rs.getString("site_number"));
					    	info.setSiteName(rs.getString("site_name"));
					    	
					    	info.setZoneCode(rs.getString("zone_code"));//所属省份
					    	info.setZoneName(rs.getString("zone_code"));
					    	
					    	info.setDepartmentCode(rs.getString("SITE_DEPARTMENT"));//所属部委
					    	info.setDepartmentName(rs.getString("SITE_DEPARTMENT"));
					    	
					    	//info.setDepartmentCode(rs.getString("dic_en_name"));
					    	//info.setDepartmentName(rs.getString("dic_cn_name"));
					    	
					    	info.setSiteLat(rs.getString("site_lat"));
					    	info.setSiteLng(rs.getString("SITE_Lon"));
					    	info.setAddress(rs.getString("SITE_ADDRESS"));
					    	
					    	info.setOrder(Integer.valueOf(rs.getString("user_order")));
							return info;
						}
						return null;
					}
				});
		return obj;
	}
	
	
	
	/**
	 * 更新台站信息
	 */
	@SuppressWarnings("unchecked")
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception {
		String sql = "update site_info set SITE_NAME=?,SITE_ZONE=?,SITE_DEPARTMENT=?,site_lat=?,SITE_Lon=?,SITE_ADDRESS=?,site_unit=?,site_progect=?,site_old_number=?,user_order=?,site_updateDate=?  where SITE_NUMBER=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, siteInfo.getSiteName());
				pstmt.setString(2, siteInfo.getZoneCode());
				pstmt.setString(3, siteInfo.getDepartmentCode());
				pstmt.setString(4, siteInfo.getSiteLat());
				pstmt.setString(5, siteInfo.getSiteLng());
				pstmt.setString(6, siteInfo.getAddress());
				pstmt.setString(7,siteInfo.getSiteUnit());
				pstmt.setString(8, siteInfo.getSiteProgect());
				pstmt.setString(9,siteInfo.getSiteOldNumber());
				pstmt.setInt(10, siteInfo.getOrder());
				pstmt.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
				pstmt.setString(12, siteInfo.getSiteNumber());
				pstmt.execute();
				return null;
			}
		});

	}
	public List<GroupByDepPro> monthDown(String fileType, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	//根据地震应急事件Id  获得  台站列表
	public List<SiteInfo>  getEarthquakeSiteList(String earthquakeId, String year){
		StringBuffer sql = new StringBuffer();
		sql.append("select f.site_number from file_info_"+year+" f where f.earthQuakeId="+earthquakeId);
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	SiteInfo siteInfo = MapAll.mapSite.get(info.getSiteNumber());
		    	info.setSiteName(siteInfo.getSiteName());
		    	info.setSiteType(siteInfo.getSiteType());
		    	info.setZoneCode(siteInfo.getZoneCode());
		    	info.setZoneName(siteInfo.getZoneName());
		    	info.setDepartmentCode(siteInfo.getDepartmentCode());
		    	info.setDepartmentName(siteInfo.getDepartmentName());
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	//数据补充处理，通过缺失文件进行查找
	public List<SiteInfo>  getSiteInfoListByFileFlag(String yearDay){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, t.rowid from SITE_INFO t where t.site_type=1 and t.site_number in (select t.site_number from FILE_INFO_2016 t where t.filedayyear="+yearDay+" and t.fileflag not in(1,2,3))");
		
		@SuppressWarnings({ "unchecked"})
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartmentCode(rs.getString("dic_en_name"));
		    	info.setDepartmentName(rs.getString("dic_cn_name"));
		    	
		    	info.setSiteUnit(rs.getString("site_unit"));
		    	info.setSiteProgect(rs.getString("site_progect"));
		    	info.setSiteOldNumber(rs.getString("site_old_number"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	info.setSiteType(rs.getString("site_type"));
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	
	//插入应急数据
	@SuppressWarnings("unchecked")
	public void earthQuakeConfigInsert(final EarthQuakeConfig earthquakeconfig) throws Exception{
		String sql = "INSERT INTO earthquakeconfig (id,earthquakefirst,earthquakesecond,earthquakethird,earthquakeforth,applyvalidityday,applyperiod )"
			+ "VALUES(?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, earthquakeconfig.getId());
				pstmt.setString(2, earthquakeconfig.getEarthquakefirst());
				pstmt.setString(3, earthquakeconfig.getEarthquakesecond());
				pstmt.setString(4, earthquakeconfig.getEarthquakethird());
				pstmt.setString(5, earthquakeconfig.getEarthquakeforth());
				pstmt.setString(6, earthquakeconfig.getApplyvalidityday());
				pstmt.setString(7, earthquakeconfig.getApplyperiod());
				pstmt.execute();
				return null;
			}
		});
	}
	//查询应急数据
	public List<EarthQuakeConfig> earthQuakeConfigQuery() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.earthquakefirst,t.earthquakesecond,t.earthquakethird,t.earthquakeforth," +
				"t.applyvalidityday,t.applyperiod from earthquakeconfig t ");
		@SuppressWarnings({ "unchecked"})
		List<EarthQuakeConfig> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuakeConfig info = new EarthQuakeConfig();
		    	info.setId(rs.getInt("id"));
		    	info.setEarthquakefirst(rs.getString("earthquakefirst"));
		    	info.setEarthquakesecond(rs.getString("earthquakesecond"));
		    	info.setEarthquakethird(rs.getString("earthquakethird"));
		    	info.setEarthquakeforth(rs.getString("earthquakeforth"));
		    	info.setApplyvalidityday(rs.getString("applyvalidityday"));
		    	info.setApplyperiod(rs.getString("applyperiod"));
				return info;
		   }
		});
		return eventInfoList;
	}
	//修改应急数据
	@SuppressWarnings("unchecked")
	public void earthQuakeConfigUpdate(final EarthQuakeConfig earthquakeconfig) throws Exception{
		String sql = "update earthquakeconfig set earthquakefirst=?,earthquakesecond=?,earthquakethird=?," +
				"earthquakeforth=?,applyvalidityday=?,applyperiod=? where id = ?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, earthquakeconfig.getEarthquakefirst());
				pstmt.setString(2, earthquakeconfig.getEarthquakesecond());
				pstmt.setString(3, earthquakeconfig.getEarthquakethird());
				pstmt.setString(4, earthquakeconfig.getEarthquakeforth());
				pstmt.setString(5, earthquakeconfig.getApplyvalidityday());
				pstmt.setString(6, earthquakeconfig.getApplyperiod());
				pstmt.setInt(7, earthquakeconfig.getId());
				pstmt.execute();
				return null;
			}
		});
	}
}
