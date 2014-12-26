package com.xzx.teamsys.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.xzx.teamsys.dao.GroupDAO;
import com.xzx.teamsys.dao.ProjectDAO;
import com.xzx.teamsys.dao.ProjectUserLinkerDAO;
import com.xzx.teamsys.dao.TaskDAO;
import com.xzx.teamsys.dao.TransactionManager;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.dao.UserDetailDAO;
import com.xzx.teamsys.dao.jdbcimpl.GroupDAOJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.ProjectDAOJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.ProjectUserLinkerDAOJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.TaskDAOJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.TransactionManagerJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.UserDAOJDBCImpl;
import com.xzx.teamsys.dao.jdbcimpl.UserDetailDAOJDBCImpl;
import com.xzx.teamsys.service.GroupService;
import com.xzx.teamsys.service.ProjectService;
import com.xzx.teamsys.service.TaskService;
import com.xzx.teamsys.service.UserService;
import com.xzx.teamsys.service.defaultimpl.GroupServiceDefaultImpl;
import com.xzx.teamsys.service.defaultimpl.ProjectServiceDefaultImpl;
import com.xzx.teamsys.service.defaultimpl.TaskServiceDefaultImpl;
import com.xzx.teamsys.service.defaultimpl.UserServiceDefaultImpl;


/**
 * Application Lifecycle Listener implementation class ServiceInitializeListener
 *
 */
@WebListener
public class ServiceInitializeListener implements ServletContextListener
{

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce)
	{
		
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		GroupDAO groupDAO = new GroupDAOJDBCImpl();
		ProjectDAO projectDAO = new ProjectDAOJDBCImpl();
		ProjectUserLinkerDAO projectUserLinkerDAO = new ProjectUserLinkerDAOJDBCImpl();
		TaskDAO taskDAO = new TaskDAOJDBCImpl();
		TransactionManager transactionManager = new TransactionManagerJDBCImpl();
		UserDAO userDAO = new UserDAOJDBCImpl();
		UserDetailDAO userDetailDAO = new UserDetailDAOJDBCImpl();
		
		GroupService groupService = new GroupServiceDefaultImpl(groupDAO, projectDAO, userDAO, userDetailDAO);
		ProjectService projectService = new ProjectServiceDefaultImpl(projectDAO, groupDAO, userDAO, userDetailDAO, projectUserLinkerDAO, transactionManager);
		TaskService taskService = new TaskServiceDefaultImpl(taskDAO, transactionManager);
		UserService userService = new UserServiceDefaultImpl(userDAO, userDetailDAO, transactionManager);
		
		sce.getServletContext().setAttribute("groupService", groupService);
		sce.getServletContext().setAttribute("projectService", projectService);
		sce.getServletContext().setAttribute("taskService", taskService);
		sce.getServletContext().setAttribute("userService", userService);
	}

}
