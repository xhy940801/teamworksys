package com.xzx.teamsys.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzx.teamsys.entity.CompletionStatus;
import com.xzx.teamsys.entity.ProjectInfo;
import com.xzx.teamsys.entity.Task;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.service.ProjectService;
import com.xzx.teamsys.service.TaskService;
import com.xzx.teamsys.util.URLHelper;

/**
 * Servlet implementation class ProjectServlet
 */
@WebServlet("/Project/*")
public class ProjectServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private ProjectService projectService;
	private TaskService taskService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet()
	{
		super();
	}
	
	/**
	 * @see HttpServlet#init()
	 */
	@Override
	public void init() throws ServletException
	{
		super.init();
		projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
		taskService = (TaskService) this.getServletContext().getAttribute("taskService");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String method = URLHelper.getMethodName(request.getRequestURI(), request.getServletPath());
		String result;
		switch (method)
		{
		case "create":
			result = this.createG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "show":
			result = this.showG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "addTask":
			result = this.addTaskG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "finishTask":
			result = this.finishTaskG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "invite":
			result = this.inviteG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		default:
			result = URLHelper.get404PageURL();
		}
		if(result != null)
			request.getRequestDispatcher(result).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String method = URLHelper.getMethodName(request.getRequestURI(), request.getServletPath());
		String result;
		switch (method)
		{
		case "create":
			result = this.createP(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "addTask":
			result = this.addTaskP(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "invite":
			result = this.inviteP(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		default:
			result = URLHelper.get404PageURL();
		}
		if (result != null)
			request.getRequestDispatcher(result).forward(request, response);
	}
	
	private String createG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		request.setAttribute("nickname", nickname);
		return "create.jsp";
	}
	
	private String inviteG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		request.setAttribute("nickname", nickname);
		return "invite.jsp";
	}
	
	private String inviteP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		request.setAttribute("nickname", nickname);
		int projectId = Integer.parseInt(request.getParameter("id"));
		String email = request.getParameter("email");
		if(projectService.inviteUser(email, projectId) < 0)
		{
			request.setAttribute("error", projectService.getLastError());
			return "invite.jsp";
		}
		response.sendRedirect(URLHelper.url("Project/show?id=" + projectId));
		return null;
	}
	
	private String createP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		int userId = (int) request.getSession().getAttribute("userId");
		int projectId = projectService.createProject(userId, name, remark);
		if(projectId < 0)
		{
			request.setAttribute("error", projectService.getLastError());
			return "create.jsp";
		}
		response.sendRedirect(URLHelper.url("Project/show?id=" + projectId));
		return null;
	}
	
	private String addTaskP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String projectId = request.getParameter("projectId");
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		String involves = request.getParameter("involves");
		String deadlineS = request.getParameter("deadline");
		String[] involveArr = involves.split(",");
		Integer[] userIds = new Integer[involveArr.length];
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YY");
			Date deadline = sdf.parse(deadlineS);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(deadline);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			deadline = calendar.getTime();
			for(int i = 0; i < involveArr.length; ++i)
				userIds[i] = Integer.valueOf(involveArr[i]);
			if(taskService.createTask(Integer.parseInt(projectId), userIds, name, remark, deadline) >= 0)
			{
				response.sendRedirect(URLHelper.url("Project/show?id=" + projectId));
				return null;
			}
		}
		catch (NumberFormatException e)
		{
			request.setAttribute("error", "所属项目不存在");
		}
		catch (ParseException e)
		{
			request.setAttribute("error", "日期格式错误");
		}
		return "addTask.jsp";
	}
	
	private String finishTaskG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int userId = (Integer) request.getSession().getAttribute("userId");
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		taskService.completeTask(taskId, userId, "");
		response.sendRedirect(URLHelper.url("Project/show?id=" + projectId));
		return null;
	}
	
	private String showG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Comparator<Task> taskComparator = new Comparator<Task>()
		{
			@Override
			public int compare(Task o1, Task o2)
			{
				return -(int) (o1.getDeadline().getTime() - o2.getDeadline().getTime());
			}
		};
		String nickname = (String) request.getSession().getAttribute("nickname");
		int userId = (Integer) request.getSession().getAttribute("userId");
		int projectId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("projectId", projectId);
		request.setAttribute("nickname", nickname);
		ProjectInfo projectInfo = projectService.getProjectInfo(projectId);
		if(projectInfo == null)
		{
			request.setAttribute("error", projectService.getLastError());
			return "show.jsp";
		}
		request.setAttribute("projectInfo", projectInfo);
		List<Task> unfinishedTasks = taskService.getUserProjectTasks(userId, projectId, CompletionStatus.UNFINISHED);
		if(unfinishedTasks == null)
		{
			request.setAttribute("error", taskService.getLastError());
			return "show.jsp";
		}
		unfinishedTasks.sort(taskComparator);
		request.setAttribute("unfinishedTasks", unfinishedTasks);
		List<Task> finishedTasks = taskService.getUserProjectTasks(userId, projectId, CompletionStatus.FINISHED);
		if(finishedTasks == null)
		{
			request.setAttribute("error", taskService.getLastError());
			return "show.jsp";
		}
		finishedTasks.sort(taskComparator);
		request.setAttribute("finishedTasks", finishedTasks);
		List<Task> overdueTasks = taskService.getUserProjectTasks(userId, projectId, CompletionStatus.OVERDUE);
		if(overdueTasks == null)
		{
			request.setAttribute("error", taskService.getLastError());
			return "show.jsp";
		}
		overdueTasks.sort(taskComparator);
		request.setAttribute("overdueTasks", overdueTasks);
		List<UserInfo> contributors = projectService.getProjectUserInfos(projectId);
		if(contributors == null)
		{
			request.setAttribute("error", projectService.getLastError());
			return "show.jsp";
		}
		Iterator<UserInfo> it = contributors.iterator();
		while(it.hasNext())
		{
			UserInfo userInfo = it.next();
			if(userInfo.getId() == projectInfo.getOwner())
			{
				it.remove();
				break;
			}
		}
		request.setAttribute("contributors", contributors);
		return "show.jsp";
	}
	
	private String addTaskG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		int projectId;
		try
		{
			projectId = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e)
		{
			request.setAttribute("error", "所属项目不存在");
			return "addTask.jsp";
		}
		List<UserInfo> userInfos = projectService.getProjectUserInfos(projectId);
		if(userInfos == null)
		{
			request.setAttribute("error", projectService.getLastError());
			return "addTask.jsp";
		}
		request.setAttribute("userInfos", userInfos);
		request.setAttribute("nickname", nickname);
		request.setAttribute("projectId", projectId);
		return "addTask.jsp";
	}

}
