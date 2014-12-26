<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<%@ page import="com.xzx.teamsys.entity.Task"%>
<%@ page import="com.xzx.teamsys.entity.ProjectInfo"%>
<%@ page import="com.xzx.teamsys.entity.UserInfo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("teamworksys")%>
<%=URLHelper.css("projects/show")%>
<%
	ProjectInfo projectInfo = (ProjectInfo) request.getAttribute("projectInfo");
	int projectId = (Integer) request.getAttribute("projectId");
	String projectName = projectInfo.getName();
	String projectRemark = projectInfo.getRemark();
	String projectNickname = projectInfo.getNickname();
	List<Task> unfinishedTasks = (List<Task>) request.getAttribute("unfinishedTasks");
	List<Task> finishedTasks = (List<Task>) request.getAttribute("finishedTasks");
	List<Task> overdueTasks = (List<Task>) request.getAttribute("overdueTasks");
	List<UserInfo> contributors = (List<UserInfo>) request.getAttribute("contributors");
	
	unfinishedTasks = unfinishedTasks == null ? new ArrayList<Task>() : unfinishedTasks;
	finishedTasks = finishedTasks == null ? new ArrayList<Task>() : finishedTasks;
	overdueTasks = overdueTasks == null ? new ArrayList<Task>() : overdueTasks;
%>
<title>项目详情</title>
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
				<li><%=URLHelper.link("User/show", "个人主页")%></li>
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
	<div class="container container-main">
		<div class="row">
			<div class="col-md-3">
				<div class="left-page">
					<div class="panel panel-info">
						<div class="panel-heading"><%=projectName%></div>
						<div class="panel-body">
							<h5>项目简介：</h5>
							<p class="text-info"><%=projectRemark%></p>
							<h5>项目创建者：</h5>
							<p class="text-info"><%=projectNickname%></p>
							<h5>项目参与者：</h5>
							<% for(UserInfo userInfo : contributors) { %>
							<p class="text-muted"><%=userInfo.getNickname()%></p>
							<% } %>
						</div>
					</div>
					<%=URLHelper.link("Project/invite?id=" + projectId, "邀请成员", "type=\"button\" class=\"btn btn-success\"") %>
				</div>
			</div>
			<div class="col-md-9">
				<div class="right-page">
					<% if(overdueTasks.size() > 0) { %>
					<div class="panel panel-warning">
						<div class="panel-heading">超期任务<span class="badge"><%=overdueTasks.size()%></span></div>
						<div class="panel-body">
							<div class="list-group">
								<% for(Task task : overdueTasks) { %>
								<div class = "list-group-item">
									<div class="task-head">
										<a href="#">
											<span class="task-name"><%=task.getName()%></span>
										</a>
										<%=URLHelper.link("Project/finishTask?taskId=" + task.getId() + "&projectId=" + projectId, "<span class=\"task-finish glyphicon glyphicon-ok\"></span>") %>
										<span class="task-deadline text-right"><%=task.getDeadline()%></span>
									</div>
									<div class="task-body"><p class="task-remark text-muted"><%=task.getRemark()%></p></div>
								</div>
								<% } %>
							</div>
						</div>
					</div>
					<% } %>
					<div class="panel panel-primary">
						<div class="panel-heading">未完成任务<span class="badge"><%=unfinishedTasks.size()%></span></div>
						<div class="panel-body">
							<div class="list-group">
								<% for(Task task : unfinishedTasks) { %>
								<div class = "list-group-item">
									<div class="task-head">
										<a href="#">
											<span class="task-name"><%=task.getName()%></span>
										</a>
										<%=URLHelper.link("Project/finishTask?taskId=" + task.getId() + "&projectId=" + projectId, "<span class=\"task-finish glyphicon glyphicon-ok\"></span>") %>
										<span class="task-deadline text-right"><%=task.getDeadline()%></span>
									</div>
									<div class="task-body"><p class="task-remark text-muted"><%=task.getRemark()%></p></div>
								</div>
								<% } %>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading">已完成任务<span class="badge"><%=finishedTasks.size()%></span></div>
						<div class="panel-body">
							<div class="list-group">
								<% for(Task task : finishedTasks) { %>
								<div class = "list-group-item">
									<a class="task-head" href="#">
										<span class="task-name"><%=task.getName()%></span>
										<span class="task-deadline"><%=task.getDeadline()%></span>
									</a>
									<div class="task-body"><p class="task-remark text-muted"><%=task.getRemark()%></p></div>
								</div>
								<% } %>
							</div>
						</div>
					</div>
					<div class="text-right"><%=URLHelper.link("Project/addTask?id=" + projectId, "添加任务", "type=\"button\" class=\"btn btn-default\"") %></div>
				</div>
			</div>
		</div>
	</div>
	<%=URLHelper.script("jquery-1.11.2.min")%>
	<%=URLHelper.script("bootstrap.min")%>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<%=URLHelper.script("ie10-viewport-bug-workaround")%>
	<%=URLHelper.script("showError")%>
	<%=URLHelper.script("projects/show")%>
</body>
</html>