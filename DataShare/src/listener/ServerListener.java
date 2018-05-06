package listener;
import java.net.InetAddress;
import java.util.List;
import java.util.Set;

import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.ipfilter.IpFilter;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.mina.filter.firewall.Subnet;


public class ServerListener implements Listener{

	public Set<FtpIoSession> getActiveSessions() {
		System.out.println("server111");
		return null;
	}

	public List<InetAddress> getBlockedAddresses() {
		System.out.println("server222");
		return null;
	}

	public List<Subnet> getBlockedSubnets() {
		System.out.println("server333");
		return null;
	}

	public DataConnectionConfiguration getDataConnectionConfiguration() {
		System.out.println("server444");
		return null;
	}

	public int getIdleTimeout() {
		System.out.println("server555");
		return 0;
	}

	public IpFilter getIpFilter() {
		System.out.println("server666");
		return null;
	}

	public int getPort() {
		System.out.println("server7");
		return 0;
	}

	public String getServerAddress() {
		System.out.println("server8");
		return null;
	}

	public SslConfiguration getSslConfiguration() {
		System.out.println("server9");
		return null;
	}

	public boolean isImplicitSsl() {
		System.out.println("server10");
		return false;
	}

	public boolean isStopped() {
		System.out.println("server11");
		return false;
	}

	public boolean isSuspended() {
		System.out.println("server12");
		return false;
	}

	public void resume() {
		System.out.println("server13");
		
	}

	public void start(FtpServerContext arg0) {
		
	}

	public void stop() {
		System.out.println("server15");
		
	}

	public void suspend() {
		System.out.println("server16");
		
	}
	
	
}