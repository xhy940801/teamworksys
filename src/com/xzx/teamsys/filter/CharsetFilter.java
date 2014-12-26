package com.xzx.teamsys.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.xzx.teamsys.util.ConfigureLoader;

/**
 * Servlet Filter implementation class EncodeFilter
 */
@WebFilter("/*")
public class CharsetFilter implements Filter
{
	private String charset;

	/**
	 * Default constructor.
	 */
	public CharsetFilter()
	{
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		request.setCharacterEncoding(charset);
		request.getParameter("");
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		charset = ConfigureLoader.getConfigure().getProperty("cm-charset", "utf-8");
	}

}
