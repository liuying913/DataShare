package com.highfd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.highfd.bean.EarthQuake;
import com.highfd.bean.EarthQuakeApply;
import com.highfd.bean.FileInfo;
import com.highfd.bean.SiteInfo;
import com.highfd.beanFile.analysis.FileHistroyDown;
import com.highfd.common.PageInfo;
import com.highfd.common.map.MapAll;
import com.highfd.common.time.TimeUtils;
import com.highfd.dao.ManagerDAO;

@Resource
public class ManagerDAOImpl implements ManagerDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	//添加地震应急事件
	@SuppressWarnings("unchecked")
	public int addEarthQuake(final EarthQuake earthQuake) throws Exception {
		String sql2 = "select EARTHQUAKE_SEQ.nextval from dual";
		earthQuake.setId(getNextID(jdbcTemplate, sql2));
		String sql = "INSERT INTO earthquake (id,name,year,createTime,site_lat,site_lon,grade,address,height,remark,iniType,crawtype,crawrange) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, earthQuake.getId());
				pstmt.setString(2, earthQuake.getName());
				pstmt.setString(3, TimeUtils.getSysYear());
				try {
					pstmt.setTimestamp(4, TimeUtils.getTempByAllTime(earthQuake.getStrTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				pstmt.setString(5, earthQuake.getSiteLat());
				pstmt.setString(6, earthQuake.getSiteLon());
				pstmt.setString(7, earthQuake.getGrade());
				pstmt.setString(8, earthQuake.getAddress());
				pstmt.setString(9, earthQuake.getHeight());
				pstmt.setString(10, earthQuake.getRemark());
				pstmt.setString(11, "1");
				pstmt.setString(12, earthQuake.getCrawType());
				pstmt.setString(13, earthQuake.getCrawRange());
				pstmt.execute();
				return earthQuake.getId();
			}
		});
		return earthQuake.getId();
	}
	
	//查询 未执行的地震应急事件
	@SuppressWarnings("unchecked")
	public EarthQuake queryEarthQuakeByRun(String iniSql) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from earthQuake s where 1=1 "+iniSql);
		List<EarthQuake> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuake info = new EarthQuake();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setYear(rs.getString("year"));
		    	info.setCreateTime(rs.getTimestamp("createTime"));//
		    	info.setStrTime(TimeUtils.TimestampToString(rs.getTimestamp("createTime")));
		    	info.setName(rs.getString("name"));
		    	info.setRemark(rs.getString("remark"));//描述
		    	info.setSiteLat(rs.getString("site_lat"));//纬度
		    	info.setSiteLon(rs.getString("site_lon"));//经度
		    	info.setGrade(rs.getString("grade"));//震级
		    	info.setAddress(rs.getString("address"));//地点
		    	info.setHeight(rs.getString("height"));//震源深度
		    	
		    	info.setIniType(rs.getString("initype"));
		    	info.setCrawType(rs.getString("crawtype"));
		    	info.setCrawRange(rs.getString("crawrange"));
				return info;
		   }
		});
		if(null==eventInfoList || eventInfoList.size()==0){
			return null;
		}else{
			return eventInfoList.get(0);
		}
	}

	/**
	 * 修改地震应急事件  状态标志位
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateEarthQuakeType(final String iniType ,final String id) throws Exception {
		String sql = "update EARTHQUAKE set initype=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, iniType);
				pstmt.setString(2, id);
				pstmt.execute();
				return null;
			}
		});
	}

	
	/**
	 * 添加应急事情  申请
	 */
	@SuppressWarnings("unchecked")
	public void insertEarthQuakeApply(final EarthQuakeApply info) throws Exception {
		String sql = "INSERT INTO EARTHQUAKE_APPLY (id,user_id,createtime,type,EARTHQUAKE_ID)"
			+ "VALUES(?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, info.getId());
				pstmt.setInt(2, info.getUserId());
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstmt.setString(4, "4");
				pstmt.setString(5, info.getEarthquake_id());
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	@SuppressWarnings("unchecked")
	public void updateEarthQuakeApplyType(final int id ,final int type, final String remark, final int days) throws Exception {
		String sql = "update EARTHQUAKE_APPLY set remark=?, type=?,starttime=?,endtime=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, remark);
				pstmt.setInt(2, type);
				try {
					pstmt.setTimestamp(3, TimeUtils.getTempTime(TimeUtils.getStrYesterday(0)));
					pstmt.setTimestamp(4, TimeUtils.getTempTime(TimeUtils.getStrYesterday(-days)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pstmt.setInt(5, id);
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 修改地震应急事件  状态标志位
	 */
	@SuppressWarnings("unchecked")
	public void updateEarthQuakeApplyType(final int id ,final int type) throws Exception {
		String sql = "update EARTHQUAKE_APPLY set type=? where id=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, type);
				pstmt.setInt(2, id);
				pstmt.execute();
				return null;
			}
		});
	}
	
	/**
	 * 应急数据申请   列表
	 */
	public List<EarthQuakeApply>  queryEarthQuakeApplyList(String id,String userId,String earthQuakeId,String type,boolean noCheckFlag, String txtParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id as aId,e.id as earthId,u.user_cname,u.user_name,u.user_unit,e.name,t.type,t.createtime,t.starttime,t.endtime,t.remark from EARTHQUAKE_APPLY t,EARTHQUAKE e,user_info u where e.id=t.earthquake_id and u.user_id=t.user_id ");
		if(null!=id && !"".equals(id)){
			sql.append(" and t.id="+id);
		}
		if(null!=userId && !"".equals(userId)){
			if(!userId.toLowerCase().equals("all")){
				sql.append(" and u.user_id="+userId);
			}
		}
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			sql.append(" and e.id="+earthQuakeId);
		}
		if(null!=type && !"".equals(type)){
			sql.append(" and t.type="+type);
		}
		
		if(noCheckFlag){//true的话，就是  除了  未审核的 剩下的全部
			sql.append(" and t.type!=4 ");
		}
		
		if(null!=txtParam && !"".equals(txtParam)){
			sql.append("and ( u.user_name like '%"+txtParam+"%' or u.user_cname like '%"+txtParam+"%' or  u.user_unit like '%"+txtParam+"%' or e.name  like '%"+txtParam+"%' )");
		}
		sql.append(" order by t.createtime desc ");
		
		@SuppressWarnings({ "unchecked"})
		List<EarthQuakeApply> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuakeApply info = new EarthQuakeApply();
		    	info.setId(rs.getInt("aId"));
		    	info.setEarthquake_id(rs.getString("earthId"));
		    	info.setUser_cname(rs.getString("user_name"));
		    	info.setUser_unit(rs.getString("user_unit"));
		    	info.setEarthquakeName(rs.getString("name"));
		    	info.setType(rs.getInt("type"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createtime")));
		    	info.setStartTimeStr(null==rs.getTimestamp("starttime")?"":TimeUtils.TimestampToString2(rs.getTimestamp("starttime")));
		    	info.setEndTimeStr(null==rs.getTimestamp("endtime")?"":TimeUtils.TimestampToString2(rs.getTimestamp("endtime")));
		    	info.setRemark(rs.getString("remark"));
				return info;
		   }
		});
		return eventInfoList;
	}
	
	/**
	 * 30S数据  申请过期
	 */
	@SuppressWarnings("unchecked")
	public void update30SApplyOverdue(String dayLangth) throws Exception {
		String sql = "update  APPLY_USER t set t.liucheng=7 where t.id in( " +
				"select a.id from (" +
					"select to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy')-to_date(to_char(t.applytime5,'mm/dd/yyyy'),'mm/dd/yyyy') as dayNum,t.id  from apply_user t where t.liucheng=5 )a where a.dayNum>"+dayLangth+"  )";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	/**
	 * 地震应急事件  申请过期
	 */
	@SuppressWarnings("unchecked")
	public void updateEarthQuakeApplyOverdue() throws Exception {
		String sql = "update EARTHQUAKE_APPLY a set a.type=7 where  a.id in (  select t.id from EARTHQUAKE_APPLY t where t.type=5  and (sysdate > t.endtime)  )";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	/**
	 *某用户  某个指定的应急事件，最后的有效事件是
	 */
	public String  applyEndTime(String userId,String earthQuakeId,String type){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.endtime from EARTHQUAKE_APPLY t where 1=1 and endtime is not null ");
		
		if(null!=userId && !"".equals(userId)){
			sql.append(" and t.user_id="+userId);
		}
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			sql.append(" and t.earthquake_id="+earthQuakeId);
		}
		if(null!=type && !"".equals(type)){
			sql.append(" and t.type="+type);
		}
		sql.append(" order by t.endtime desc ");
		
		@SuppressWarnings({ "unchecked"})
		List<EarthQuakeApply> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuakeApply info = new EarthQuakeApply();
		    	info.setEndTime(rs.getTimestamp("endtime"));
		    	info.setEndTimeStr(TimeUtils.TimestampToString2(rs.getTimestamp("endtime")));
				return info;
		   }
		});
		if(null!=eventInfoList && eventInfoList.size()>0){
			if(eventInfoList.get(0).getEndTime().getTime()>new Timestamp(System.currentTimeMillis()).getTime()){
				return "-1";
			}
		}
		return "1";
	}
	
	/**
	 *某用户  某个指定的应急事件，  有未审核的单子也不行
	 */
	public String queryNoApply(String userId,String earthQuakeId,String type){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as cou from EARTHQUAKE_APPLY t where 1=1 ");
		
		if(null!=userId && !"".equals(userId)){
			sql.append(" and t.user_id="+userId);
		}
		if(null!=earthQuakeId && !"".equals(earthQuakeId)){
			sql.append(" and t.earthquake_id="+earthQuakeId);
		}
		if(null!=type && !"".equals(type)){
			sql.append(" and t.type="+type);
		}
		
		@SuppressWarnings({ "unchecked"})
		List<EarthQuakeApply> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	EarthQuakeApply info = new EarthQuakeApply();
		    	info.setRemark(rs.getString("cou"));
				return info;
		   }
		});
		if(null!=eventInfoList && eventInfoList.size()>0){
			if("0".equals(eventInfoList.get(0).getRemark())){
				return "1";
			}
		}
		return "-1";
	}
	
	
	//用户下载的  地震应急事件 数据文件列表
	@SuppressWarnings("deprecation")
	public Integer  getEarthQuakeUserDownHistoryListCount(String year, String earthQuakeId,String userName,String iniParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from DOWNHISTORY_INFO_"+year+" d,EARTHQUAKE e,FILE_INFO_"+year+" f where d.fileinfoid=f.id   and f.earthquakeid=e.id and f.type=4 ");
		if(null!= userName  && !"".equals(userName)){
			sql.append( "and d.username='"+userName+"'");
		}
		if(null!= earthQuakeId  && !"".equals(earthQuakeId)){
			sql.append( " and e.id= "+earthQuakeId);
		}
		if(null!=iniParam && !"".equals(iniParam)){
			sql.append(" and ( e.year like '%"+iniParam+"%' or e.name like '%"+iniParam+"%' or  d.username like '%"+iniParam+"%' or  f.filename like '%"+iniParam+"%' or f.site_number like '%"+iniParam+"%')");
		}
		return jdbcTemplate.queryForInt(sql.toString());
	}
	
	
	//用户下载的  地震应急事件 数据文件列表
	public List<FileHistroyDown>  getUserDownHistoryList(String year, String earthQuakeId,String userName,String iniParam,PageInfo pageinfo){
		pageinfo.setRecordCount(getEarthQuakeUserDownHistoryListCount(year,earthQuakeId,userName,iniParam));
		pageinfo.setPageCount(pageinfo.getRecordCount()%pageinfo.getPageSize()==0?pageinfo.getRecordCount()/pageinfo.getPageSize():pageinfo.getRecordCount()/pageinfo.getPageSize()+1);
		StringBuffer sql = new StringBuffer();

		//新增分页查询
		sql.append("select year,name,filepath,username,site_number,filename,earthquake50or1,filesize,downtime  from  (select rownum r,year,name,filepath,username,site_number,filename,earthquake50or1,filesize,downtime from (select e.year,e.name,f.filepath,d.username,f.site_number,f.filename,f.earthquake50or1,f.filesize,d.downtime from DOWNHISTORY_INFO_"+year+" d,EARTHQUAKE e,FILE_INFO_"+year+" f where d.fileinfoid=f.id   and f.earthquakeid=e.id and f.type=4 ");
		if(null!= userName  && !"".equals(userName)){
			sql.append( "and d.username='"+userName+"'");
		}
		if(null!= earthQuakeId  && !"".equals(earthQuakeId)){
			sql.append( " and e.id= "+earthQuakeId);
		}
		
		if(null!=iniParam && !"".equals(iniParam)){
			sql.append(" and ( e.year like '%"+iniParam+"%' or e.name like '%"+iniParam+"%' or  d.username like '%"+iniParam+"%' or  f.filename like '%"+iniParam+"%' or f.site_number like '%"+iniParam+"%')");
		}
			
		sql.append(" order by d.id )t ");
		sql.append(" )tt where tt.r between "+((pageinfo.getCurrentPage()-1)*pageinfo.getPageSize() + 1)+" and "+(pageinfo.getCurrentPage()*pageinfo.getPageSize())+"");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		List<FileHistroyDown> listFileHistoryDown = new ArrayList<FileHistroyDown>();
		if(list!=null) {
			 for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   FileHistroyDown info = new FileHistroyDown();
			    	//info.setId(rs.get("id").toString());
				    info.setName(rs.get("name").toString());
			    	info.setYear(rs.get("year").toString());
			    	
			    	if(null!=MapAll.mapUserName.get(rs.get("username").toString())){
			    		info.setApply_unit(MapAll.mapUserName.get(rs.get("username").toString()).getUserUnit());
				    	info.setApply_person(MapAll.mapUserName.get(rs.get("username").toString()).getUserCName());
			    	}else{
			    		info.setApply_unit("");
				    	info.setApply_person("");
			    	}
			    	info.setFileyear(year);
			    	info.setSite_number(rs.get("site_number").toString());
			    	info.setFileName(rs.get("fileName").toString());
			    	info.setFilePath(rs.get("filepath").toString().substring(rs.get("filepath").toString().indexOf(year), rs.get("filepath").toString().length()));
			    	info.setDownTimeStr(TimeUtils.TimestampToString2(Timestamp.valueOf(rs.get("downtime").toString())));
			    	
			    	listFileHistoryDown.add(info);
			 }
		}
		return listFileHistoryDown;
	}
	
	//用户下载的  地震应急事件 数据文件列表
	public List<FileHistroyDown>  getUserDownHistoryListAll(String year, String earthQuakeId,String userName,String iniParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select year,name,filepath,username,site_number,filename,earthquake50or1,filesize,downtime from (select e.year,e.name,f.filepath,d.username,f.site_number,f.filename,f.earthquake50or1,f.filesize,d.downtime from DOWNHISTORY_INFO_"+year+" d,EARTHQUAKE e,FILE_INFO_"+year+" f where d.fileinfoid=f.id and f.earthquakeid=e.id and f.type=4 ");
		if(null!= userName  && !"".equals(userName)){
			sql.append( "and d.username='"+userName+"'");
		}
		if(null!= earthQuakeId  && !"".equals(earthQuakeId)){
			sql.append( " and e.id= "+earthQuakeId);
		}
		
		if(null!=iniParam && !"".equals(iniParam)){
			sql.append(" and ( e.year like '%"+iniParam+"%' or e.name like '%"+iniParam+"%' or  d.username like '%"+iniParam+"%' or  f.filename like '%"+iniParam+"%' or f.site_number like '%"+iniParam+"%')");
		}
		sql.append(" order by d.id )t ");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		List<FileHistroyDown> listFileHistoryDown = new ArrayList<FileHistroyDown>();
		if(list!=null) {
			 for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   FileHistroyDown info = new FileHistroyDown();
				    info.setName(rs.get("name").toString());
			    	info.setYear(rs.get("year").toString());
			    	if(null!=MapAll.mapUserName.get(rs.get("username").toString())){
			    		info.setApply_unit(MapAll.mapUserName.get(rs.get("username").toString()).getUserUnit());
				    	info.setApply_person(MapAll.mapUserName.get(rs.get("username").toString()).getUserCName());
			    	}else{
			    		info.setApply_unit("");
				    	info.setApply_person("");
			    	}
			    	info.setFileyear(year);
			    	info.setSite_number(rs.get("site_number").toString());
			    	info.setFileName(rs.get("fileName").toString());
			    	info.setFilePath(rs.get("filepath").toString().substring(rs.get("filepath").toString().indexOf(year), rs.get("filepath").toString().length()));
			    	info.setDownTimeStr(TimeUtils.TimestampToString2(Timestamp.valueOf(rs.get("downtime").toString())));
			    	
			    	listFileHistoryDown.add(info);
			    	
			 }
		}
		return listFileHistoryDown;
	}
	
	
	//查询地震应急事件中的某一条  数据是否存在
	@SuppressWarnings("unchecked")
	public FileInfo queryEarthQuakeOneDetail(String year,FileInfo info) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.fileflag from FILE_INFO_"+year+" t where t.type=4 and t.earthquakeid="+info.getEarthQuakeId()+" and t.earthquake50or1="+info.getEarthQuake50Or1()+" and t.earthquakeday="+info.getEarthQuakeDay()+" and t.earthquakenum="+info.getEarthQuakeNum()+" and t.site_number='"+info.getSiteNumber()+"'");
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
				return info;
		   }
		});
		if(null==eventInfoList || eventInfoList.size()==0){
			return null;
		}else{
			return eventInfoList.get(0);
		}
	}
	
	
	//修改地震应急事件中的某一条  数据是否存在
	@SuppressWarnings("unchecked")
	public void updateEarthQuakeOneDetail(String year,final FileInfo info) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update FILE_INFO_"+year+" t set t.fileflag=? , t.filecreatetime=? where t.id=? ");
		jdbcTemplate.execute(sql.toString(), new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, info.getFileFlag());
				pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(3, info.getId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	//地震应急在线整理  获取台站
	@SuppressWarnings("unchecked")
	public List<SiteInfo> earthQuakeAupplyGetSiteNumberStr(String zoneArray,String departmentArray,String siteNumberArray){
		StringBuffer sql = new StringBuffer("select s.site_number from SITE_INFO s where s.site_type=1 ");
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
			sql.append(" and s.SITE_NUMBER in("+siteNumberList+") ");
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
				sql.append(" and s.SITE_DEPARTMENT in("+departmentList+") ");
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
				sql.append(" and s.SITE_zone in("+zoneList+") ");
			}
		}
		
		List<SiteInfo> infoList = (List<SiteInfo>) jdbcTemplate.query(sql.toString(),new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1)throws SQLException {
				SiteInfo info = new SiteInfo();
				info.setSiteNumber(rs.getString("site_number"));
				return info;
			}
		});
		return infoList;
	}
	
	
	
	
	
	public int getNextId(final String seq) throws Exception {
		String sql2 = "select "+seq+".nextval from dual";
		return getNextID(jdbcTemplate, sql2);
	}
	@SuppressWarnings("unchecked")
	public int getNextID(JdbcTemplate jdbcTemplate, String sql)throws Exception {
		Integer obj = (Integer) jdbcTemplate.query(sql,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getInt(1);
						}
						return null;
					}
				});
		return obj;
	}
}
