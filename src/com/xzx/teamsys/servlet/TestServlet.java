package com.xzx.teamsys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xzx.teamsys.util.URLHelper;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet/*")
public class TestServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().println(request.getRequestURI());
		response.getWriter().println(request.getContextPath());
		response.getWriter().println(request.getServletPath());
		response.getWriter().println(URLHelper.getMethodName(request.getRequestURI(), request.getServletPath()));
	//	request.getRequestDispatcher(URLHelper.get404PageURL()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

}
