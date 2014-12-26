<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.xzx.teamsys.entity.UserInfo"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Add task</title>
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("teamworksys")%>
<%
	int index = 0;
	String error = (String) request.getAttribute("error");
	error = error == null ? "" : error;
	
	int projectId = (Integer) request.getAttribute("projectId");
	List<UserInfo> userInfos = (List<UserInfo>) request.getAttribute("userInfos");
	userInfos = userInfos == null ? new ArrayList<UserInfo>() : userInfos;
%>
</head>
<body>
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
		<div id="error-msg" class="row" data-error="<%=error%>">
			<div class="alert alert-warning alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<strong>Warning!</strong> <span></span>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div id="addtaskblock" class="error-index" data-error-index="<%=index%>">
					<form class="form-horizontal" role="form"
						method="post">
						<input type="hidden" name="projectId" value="<%=projectId%>" />
						<input type="hidden" name="involves" value="" />
						<div class="form-group">
							<label for="inputName" class="col-sm-2 control-label">任务名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputName"
									name="name" placeholder="Task name" required />
							</div>
						</div>
						<div class="form-group">
							<label for="inputRemark" class="col-sm-2 control-label">任务详情</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputRemark"
									name="remark" placeholder="Remark" required />
							</div>
						</div>
						<div class="form-group">
							<label for="inputInvolves" class="col-sm-2 control-label">参与者</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputInvolves"
								placeholder="Involves" required disabled/>
							</div>
						</div>
						<div class="form-group">
							<label for="inputInvolves" class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
								<select multiple class="form-control">
									<% for(UserInfo userInfo : userInfos) { %>
									<option class="involve-opt" data-user-id="<%=userInfo.getId()%>" data-nickname="<%=userInfo.getNickname()%>"><%=userInfo.getNickname()%></option>
									<% } %>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputDeadline" class="col-sm-2 control-label">截止日期</label>
							<div class="col-sm-10">
								<input type="date" class="form-control" id="inputDeadline"
									name="deadline" placeholder="Deadline" required />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary">新建项目</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%=URLHelper.script("jquery-1.11.2.min")%>
	<%=URLHelper.script("bootstrap.min")%>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<%=URLHelper.script("ie10-viewport-bug-workaround")%>
	<%=URLHelper.script("showError")%>
	<%=URLHelper.script("projects/addTask")%>
</body>
</html>