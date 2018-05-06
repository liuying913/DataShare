
import java.sql.*;

public class SqlServer2005Test {
    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://172.128.8.42:1433;DatabaseName=TPPDB";
    
    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        try {
            Class.forName(cfn);
            con = DriverManager.getConnection(url,"alarm","Trimble123456");
            //[Storage]  历元数量
            String sql = "select * from Modules";//查询test表  [MeteorologicSensorHistory]   [ReferenceStationHistory]
            statement = con.prepareStatement(sql);
            res = statement.executeQuery();
            while(res.next()){
                String title = res.getString("ModuleConfigName");//获取test_name列的元素                                                                                                                                                    ;
                System.out.println("姓名："+title);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            try {
                if(res != null) res.close();
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
    }
    
    
    
    
    
    /*public static int callmonitor(){
        String monitorServer = "tcp://localhost:8181/ServerInfo.rem".Replace("localhost", getIp);
        try
        {
            WellKnownClientTypeEntry remoteType = new WellKnownClientTypeEntry(typeof(ServerInfo), monitorServer);
            if (!ConnectRemoteStatus)
            {
                RemotingConfiguration.RegisterWellKnownClientType(remoteType);
                ConnectRemoteStatus = true;
            }
            ServerInfo si = new ServerInfo();
            mre = new ManualResetEvent(false);
            AsyncCallback RemoteCallback = new AsyncCallback(EITuxedo.RemoteSetAsyncCallBack);
            IAsyncResult RemAr = new RemoteSetAsyncDelegate(si.Set).BeginInvoke(currServiceName, 0, 1, "test", RemoteCallback, null);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
        }
        return 0;
    }*/
}