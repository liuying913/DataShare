import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class MysqlToOracle {
	
	public static ArrayList<SiteInfos> mysql(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dataShare";
        String user = "root"; 
        String password = "datashare.java.1234";
        ArrayList<SiteInfos> list = new ArrayList<SiteInfos>();
        try { 
	         Class.forName(driver);
	         Connection conn = DriverManager.getConnection(url, user, password);
	         
	         Statement statement = conn.createStatement();
	         String sql = "select * from site_info ";
	         ResultSet rs = statement.executeQuery(sql);
	         while(rs.next()) {
	        	 SiteInfos f = new SiteInfos();
	        	 
	        	 f.setSITE_NUMBER(rs.getString("SITE_NUMBER"));
	        	 f.setSITE_NAME(rs.getString("SITE_NAME"));
	        	 f.setSITE_ZONE(rs.getString("SITE_ZONE"));
	        	 f.setSITE_DEPARTMENT(rs.getString("SITE_DEPARTMENT"));
	        	 f.setSITE_LAT(rs.getString("SITE_LAT"));
	        	 f.setSITE_LON(rs.getString("SITE_LON"));
	        	 f.setSITE_ADDRESS(rs.getString("SITE_ADDRESS"));
	        	 f.setSITE_UNIT(rs.getString("SITE_UNIT"));
	        	 f.setSITE_PROGECT(rs.getString("SITE_PROGECT"));
	        	 f.setSITE_TYPE(rs.getString("SITE_TYPE"));
	        	 f.setSITE_OLD_NUMBER(rs.getString("SITE_OLD_NUMBER"));
	        	 f.setUSER_ORDER(rs.getString("USER_ORDER"));
	        	 
	        	 
	        	
	        	 list.add(f);
	         }
	         rs.close();
	         conn.close();
	         
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
	}
	
	//INSERT INTO file_info VALUES ('1394', '2016', '001', 'BJFS', 'BJFS0010.16d.Z', 'D:\\soreOutData\\30sdz\\2016\\001', '686', '1', to_date('2016-10-16 14:09:19','yyyy-mm-dd hh24:mi:ss'), to_date('2016-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'), '1', null, null, null, null);
	public static void oracle(ArrayList<SiteInfos> list)throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@172.128.8.10:1521:datashare";
		String user = "datashare";
		String password = "datashare";
		Connection conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(false);  
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		long startTime = new Date().getTime();
		for(int x = 0; x < list.size(); x++){
			SiteInfos f = list.get(x);
			String sql = "INSERT INTO site_info VALUES ('"+f.getSITE_NUMBER()+"', '"+f.getSITE_NAME()+"', '"+f.getSITE_ZONE()+"', '"+f.getSITE_DEPARTMENT()+"', '"+f.getSITE_LAT()+"', '"+f.getSITE_LON()+"', '"+f.getSITE_ADDRESS()+"', '"+f.getSITE_UNIT()         +"', '"+f.getSITE_PROGECT()+"', '"+f.getSITE_TYPE()+"', '"+f.getSITE_OLD_NUMBER()+"', '"+f.getUSER_ORDER()+"',null)";
		    //System.out.println(sql);
			stmt.addBatch(sql);  
			if(x%1000 == 0){
				stmt.executeBatch();
				conn.commit();  
				System.out.println(new Date().getTime()-startTime);
				startTime = new Date().getTime();
		    }
		}  
		stmt.executeBatch();  
		conn.commit();  
		stmt.close();
		conn.close();
	}

	public static void main(String[] args) throws Exception {
		ArrayList<SiteInfos> mysql = mysql();
		oracle(mysql);
	}
}

class SiteInfos{
	public String SITE_NUMBER;
	public String SITE_NAME;
	public String SITE_ZONE;
	public String SITE_DEPARTMENT;
	
	public String SITE_LAT;
	public String SITE_LON;
	public String SITE_ADDRESS;
	public String SITE_UNIT;
	public String SITE_PROGECT;
	public String SITE_TYPE;
	public String SITE_OLD_NUMBER;
	public String USER_ORDER;
	public String SITE_UPDATEDATE;
	public String getSITE_NUMBER() {
		return SITE_NUMBER;
	}
	public void setSITE_NUMBER(String sITENUMBER) {
		SITE_NUMBER = sITENUMBER;
	}
	public String getSITE_NAME() {
		return SITE_NAME;
	}
	public void setSITE_NAME(String sITENAME) {
		SITE_NAME = sITENAME;
	}
	public String getSITE_ZONE() {
		return SITE_ZONE;
	}
	public void setSITE_ZONE(String sITEZONE) {
		SITE_ZONE = sITEZONE;
	}
	public String getSITE_DEPARTMENT() {
		return SITE_DEPARTMENT;
	}
	public void setSITE_DEPARTMENT(String sITEDEPARTMENT) {
		SITE_DEPARTMENT = sITEDEPARTMENT;
	}
	public String getSITE_LAT() {
		return SITE_LAT;
	}
	public void setSITE_LAT(String sITELAT) {
		SITE_LAT = sITELAT;
	}
	public String getSITE_LON() {
		return SITE_LON;
	}
	public void setSITE_LON(String sITELON) {
		SITE_LON = sITELON;
	}
	public String getSITE_ADDRESS() {
		return SITE_ADDRESS;
	}
	public void setSITE_ADDRESS(String sITEADDRESS) {
		SITE_ADDRESS = sITEADDRESS;
	}
	public String getSITE_UNIT() {
		return SITE_UNIT;
	}
	public void setSITE_UNIT(String sITEUNIT) {
		SITE_UNIT = sITEUNIT;
	}
	public String getSITE_PROGECT() {
		return SITE_PROGECT;
	}
	public void setSITE_PROGECT(String sITEPROGECT) {
		SITE_PROGECT = sITEPROGECT;
	}
	public String getSITE_TYPE() {
		return SITE_TYPE;
	}
	public void setSITE_TYPE(String sITETYPE) {
		SITE_TYPE = sITETYPE;
	}
	public String getSITE_OLD_NUMBER() {
		return SITE_OLD_NUMBER;
	}
	public void setSITE_OLD_NUMBER(String sITEOLDNUMBER) {
		SITE_OLD_NUMBER = sITEOLDNUMBER;
	}
	public String getUSER_ORDER() {
		return USER_ORDER;
	}
	public void setUSER_ORDER(String uSERORDER) {
		USER_ORDER = uSERORDER;
	}
	public String getSITE_UPDATEDATE() {
		return SITE_UPDATEDATE;
	}
	public void setSITE_UPDATEDATE(String sITEUPDATEDATE) {
		SITE_UPDATEDATE = sITEUPDATEDATE;
	}

	

}
