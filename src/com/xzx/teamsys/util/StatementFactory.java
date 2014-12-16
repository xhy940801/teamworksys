package com.xzx.teamsys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StatementFactory
{
	static private String url;
	static private ThreadLocal<Connection> threadLocal;
	static public void init(String url) throws ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		threadLocal = new ThreadLocal<Connection>();
		StatementFactory.url = url;
	}
	
	static public boolean hasConnection()
	{
		return threadLocal.get() != null;
	}
	
	static public Connection getConnection() throws SQLException
	{
		Connection conn = threadLocal.get();
		if(conn == null)
		{
			conn = DriverManager.getConnection(url);
			threadLocal.set(conn);
		}
		return conn;
	}
}
