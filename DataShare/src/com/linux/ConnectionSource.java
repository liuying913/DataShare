package com.linux;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 从数据源中获取连接的类
 * 
 * @version 1.0
 * @Date 2008-12-12
 */
public class ConnectionSource {

	/**
	 * propFile:数据库配置文件
	 */
	private static String propFile = "database.properties";

	/**
	 * dataSource:数据源
	 */
	private static BasicDataSource dataSource = null;

	/**
	 * 初始化数据源
	 * 
	 * @throws IntegrateSQLException
	 */
	public static void init() throws DataServiceSQLException {
		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (SQLException e) {
				throw new DataServiceSQLException(e);
			}
			dataSource = null;
		}
		Properties p = new Properties();
		try {
			//p.load(PropertyHelper.getPropFile(DynamicConnSource.class, propFile));
			dataSource = (BasicDataSource) BasicDataSourceFactory
					.createDataSource(p);
		} catch (Exception e) {
			throw new DataServiceSQLException(e);
		}
	}

	/**
	 * 从数据源获得一个数据库连接
	 * 
	 * @return 一个数据库连接
	 * @throws DataServiceSQLException
	 */
	public static synchronized Connection getConnection()
			throws DataServiceSQLException {
		if (dataSource == null) {
			init();
		}
		Connection conn = null;
		if (dataSource != null) {
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				throw new DataServiceSQLException(e);
			}
		}
		return conn;
	}

	/**
	 * 关闭所有的连接
	 * 
	 * @throws DataServiceSQLException
	 */
	public static void closeAllConnections() throws DataServiceSQLException {
		try {
			if (dataSource != null) {
				dataSource.close();
			}
		} catch (SQLException e) {
			throw new DataServiceSQLException(e);
		}
	}

	/**
	 * 关闭指定的连接
	 * 
	 * @param connection
	 *            要关闭的连接
	 * @throws DataServiceSQLException
	 */
	public static void closeConnection(Connection connection)
			throws DataServiceSQLException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataServiceSQLException(e);
		}
	}

	/**
	 * 关闭指定的声明
	 * 
	 * @param statement
	 *            要关闭的声明
	 * @throws DataServiceSQLException
	 */
	public static void closeStatement(Statement statement)
			throws DataServiceSQLException {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			throw new DataServiceSQLException(e);
		}
	}

	/**
	 * 关闭指定的结果集
	 * 
	 * @param rs
	 *            要关闭的结果集
	 * @throws DataServiceSQLException
	 */
	public static void closeResultSet(ResultSet rs)
			throws DataServiceSQLException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DataServiceSQLException(e);
		}
	}

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rt = null;
		for (int i = 0; i < 600; i++) {
			try {
				con = ConnectionSource.getConnection();
				st = con.createStatement();
				rt = st.executeQuery("select * from t_student");
				while (rt.next()) {
					System.out.println(rt.getInt(1));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					ConnectionSource.closeResultSet(rt);
					ConnectionSource.closeStatement(st);
					ConnectionSource.closeConnection(con);
				} catch (DataServiceSQLException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
