package com.xzx.teamsys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzx.teamsys.entity.ContributorStatus;
import com.xzx.teamsys.entity.Sex;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.entity.UserProjectInfo;
import com.xzx.teamsys.service.ProjectService;
import com.xzx.teamsys.service.UserService;
import com.xzx.teamsys.util.URLHelper;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/User/*")
public class UserServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private ProjectService projectService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet()
	{
		super();
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException
	{
		super.init();
		userService = (UserService) this.getServletContext().getAttribute("userService");
		projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
	}

	/**
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String method = URLHelper.getMethodName(request.getRequestURI(), request.getServletPath());
		String result;
		switch (method)
		{
		case "signin":
			result = this.signinG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "signup":
			result = this.signupG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "index":
			result = this.indexG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "logout":
			result = this.logoutG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "show":
			result = this.showG(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		default:
			result = URLHelper.get404PageURL();
		}
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
		case "signin":
			result = this.signinP(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		case "signup":
			result = this.signupP(request, response);
			result = result == null ? null : URLHelper.getViewPath(request.getServletPath(), result);
			break;
		default:
			result = URLHelper.get404PageURL();
		}
		if (result != null)
			request.getRequestDispatcher(result).forward(request, response);
	}
	
	private String indexG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		request.setAttribute("nickname", nickname);
		return "index.jsp";
	}

	private String signinG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		return "signin.jsp";
	}

	private String signupG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		request.setAttribute("nickname", nickname);
		return "signup.jsp";
	}
	
	private String logoutG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getSession().invalidate();
		return "index.jsp";
	}
	
	private String showG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nickname = (String) request.getSession().getAttribute("nickname");
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		request.setAttribute("nickname", nickname);
		request.setAttribute("userId", userId);
		List<UserProjectInfo> infos = projectService.getUserProjectInfos(userId, ContributorStatus.ACCEPT);
		if(infos == null)
			request.setAttribute("error", projectService.getLastError());
		request.setAttribute("infos", infos);
		return "show.jsp";
	}

	private String signinP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int userId = userService.checkPassword(email, password);
		if (userId < 0)
		{
			request.setAttribute("error", userService.getLastError());
			return "signin.jsp";
		}
		UserInfo info = userService.getUserInfo(userId);
		request.setAttribute("nickname", info.getNickname());
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("nickname", info.getNickname());
		response.sendRedirect(URLHelper.url("User/show"));
		return null;
	}
	
	private String signupP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		Sex sex = Sex.valueOf(request.getParameter("sex"));
		int userId = userService.register(email, password, nickname, sex);
		if (userId < 0)
		{
			request.setAttribute("error", userService.getLastError());
			return "signup.jsp";
		}
		request.getSession().setAttribute("userId", userId);
		response.sendRedirect(URLHelper.url("User/show"));
		return null;
	}

}
