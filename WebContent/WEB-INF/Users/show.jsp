<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<%@ page import="com.xzx.teamsys.entity.UserProjectInfo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("teamworksys")%>
<%=URLHelper.css("users/show")%>
<%
	List<UserProjectInfo> infos = (List<UserProjectInfo>) request.getAttribute("infos");
	List<UserProjectInfo> defaultInfos = (List<UserProjectInfo>) request.getAttribute("defaultInfos");
	if(infos == null)
		infos = new ArrayList<UserProjectInfo>();
	int userId = (Integer) request.getAttribute("userId");
%>
<title>用户信息</title>
</head>
<body>
	<div class="infobox">
		<% if(request.getAttribute("error") != null && !request.getAttribute("error").equals("")) { %>
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<strong>Warning!</strong> <%=request.getAttribute("error")%>
		</div>
		<% } %>
	</div>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Teamwork system</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><%=URLHelper.link("User/index", "首页")%></li>
				<% if(request.getAttribute("nickname") != null) { %>
				<li class="active"><%=URLHelper.link("User/show", "个人主页")%></li>
				<% } %>
			</ul>
			<% if(request.getAttribute("nickname") == null) { %>
			<form class="navbar-form navbar-right" role="form"  method="post" action="<%=URLHelper.url("User", "signin")%>">
				<div class="form-group">
					<input type="text" placeholder="Email" name="email" class="form-control">
				</div>
				<div class="form-group">
					<input type="password" placeholder="Password" name="password" class="form-control">
				</div>
				<button type="submit" class="btn btn-success">登录</button>
				<%=URLHelper.link("User/signup", "注册", "class=\"btn btn-default\"")%>
			</form>
			<% } else { %>
			<%=URLHelper.link("User/logout", "登出", "class=\"btn btn-success navbar-btn navbar-right\"")%>
			<p class="navbar-text navbar-right">欢迎你 <%=request.getAttribute("nickname")%></p>
			<% } %>
		</div>
	</nav>
	<div class="container">
		<div id="show-projects">
			<div class="row">
				<div class="col-md-8 col-md-offset-1">
					<div class="panel panel-info" role="tablist">
						<div class="panel-heading">拥有的项目</div>
						<div class="panel-body">
							<ul>
								<% for(UserProjectInfo info : infos) { if(info.getOwner() == userId) { %>
								<li><%=URLHelper.link("Project/show?id=" + info.getProjectId(), info.getProjectName())%>(<%=info.getGroupName()%>)</li>
								<% } }%>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-1">
					<div class="panel panel-info" role="tablist">
						<div class="panel-heading">参与的项目</div>
						<div class="panel-body">
							<ul>
								<% for(UserProjectInfo info : infos) { if(info.getOwner() != userId) { %>
								<li><%=URLHelper.link("Project/show?id=" + info.getProjectId(), info.getProjectName())%>(<%=info.getGroupName()%>)</li>
								<% } }%>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-1">
					<div class="panel panel-info" role="tablist">
						<div class="panel-heading">待处理的邀请</div>
						<div class="panel-body">
							<ul>
								<% for(UserProjectInfo info : defaultInfos) { %>
								<li>
									<%=info.getProjectName()%>
									<div class="pull-right">
										<%=URLHelper.link("User/accept?linkId=" + info.getLinkId(), "接受") %>
										<%=URLHelper.link("User/refuse?linkId=" + info.getLinkId(), "拒绝") %>
									</div>
								</li>
								<% } %>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-1">
					<p class="text-right"><%=URLHelper.link("Project/create", "新建项目")%></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>