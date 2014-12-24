package com.xzx.teamsys.listener;

import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import com.xzx.teamsys.util.ConfigureLoader;
import com.xzx.teamsys.util.StatementFactory;

/**
 * Application Lifecycle Listener implementation class DBOperatorListener
 *
 */
@WebListener
public class DBOperatorListener implements ServletContextListener,
		ServletRequestListener
{
	static private final String confFilePath = "/webService.properties";

	/**
	 * Default constructor.
	 */
	public DBOperatorListener()
	{
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent event)
	{
		try
		{
			StatementFactory.closeConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent event)
	{
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event)
	{
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event)
	{
		Properties conf = ConfigureLoader.loadConfigure(event
				.getServletContext().getResourceAsStream(confFilePath));
		try
		{
			StatementFactory.init("jdbc:mysql://localhost:3306/"
					+ conf.getProperty("db-name") + "?" + "user=" + conf.getProperty("db-user")
					+ "&password=" + conf.getProperty("db-password")
					+ "&useUnicode=true&characterEncoding=UTF8");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
