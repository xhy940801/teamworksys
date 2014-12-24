package com.xzx.teamsys.util;

public class URLHelper
{
	static private String contextPath;
	static private String cssBasePath;
	static private String jsBasePath;
	static private String imgBasePath;
	
	static public void init(String contextPath, String cssBasePath, String jsBasePath, String imgBasePath)
	{
		URLHelper.contextPath = contextPath;
		URLHelper.cssBasePath = cssBasePath;
		URLHelper.jsBasePath = jsBasePath;
		URLHelper.imgBasePath = imgBasePath;
	}
	
	static public String getMethodName(String uri, String servletPath)
	{
		return uri.substring(contextPath.length() + servletPath.length() + 1);
	}
	
	static public String getViewPath(String servletPath, String name)
	{
		return "/WEB-INF" + servletPath + "s/" + name;
	}
	
	static public String css(String name)
	{
		String cssPath = cssBasePath + "/" + name + ".css";
		return "<link href=\"" + cssPath + "\" rel=\"stylesheet\">";
	}
	
	static public String script(String name)
	{
		String jsPath = jsBasePath + "/" + name + ".js";
		return "<script src=\"" + jsPath + "\" type=\"text/javascript\"></script>";
	}
	
	static public String url(String path)
	{
		return contextPath + "/" + path;
	}
	
	static public String url(String servletName, String method)
	{
		return contextPath + "/" + servletName + "/" + method;
	}
	
	static public String link(String path, String value)
	{
		return URLHelper.link(path, value, "");
	}
	
	static public String link(String path, String value, String other)
	{
		String linkPath = contextPath + "/" + path;
		return "<a href=\"" + linkPath + "\" " + other + ">" + value + "</a>";
	}
	
	static public String get404PageURL()
	{
		return "/Pages/404.html";
	}
}
