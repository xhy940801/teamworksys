<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Teamwork system</title>
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("teamworksys")%>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
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
				<li class="active"><a href="#">首页</a></li>
				<li><%=URLHelper.link("User/show", "个人主页")%></li>
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
	</div>
	</nav>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>团队项目合作管理系统</h1>
			<p>
				这是一个团队项目合作管理系统，可以方便大家在团队项目中更方便的合作
			</p>
			<p>
				<%=URLHelper.link("User/index", "查看详情", "class=\"btn btn-primary btn-lg\" role=\"button\"") %>
			</p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-4">
				<h2>高效</h2>
				<p>
					本系统秉承高效便捷的原则。
				</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>高效</h2>
				<p>
					本系统秉承高效便捷的原则。
				</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>高效</h2>
				<p>
					本系统秉承高效便捷的原则。
				</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
		</div>

		<hr>

		<footer>
		<p>&copy; Copyright xzx</p>
		</footer>
	</div>
	<%=URLHelper.script("jquery-1.11.2.min")%>
	<%=URLHelper.script("bootstrap.min")%>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<%=URLHelper.script("ie10-viewport-bug-workaround")%>
</body>
</html>