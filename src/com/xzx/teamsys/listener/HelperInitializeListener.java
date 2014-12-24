package com.xzx.teamsys.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.xzx.teamsys.util.URLHelper;

/**
 * Application Lifecycle Listener implementation class HelperInitializeListener
 *
 */
@WebListener
public class HelperInitializeListener implements ServletContextListener
{

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		String contextPath = sce.getServletContext().getContextPath();
		URLHelper.init(contextPath, contextPath + "/css", contextPath + "/js", contextPath + "/img");
	}

}
