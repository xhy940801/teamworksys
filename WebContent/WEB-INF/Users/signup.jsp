<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Sign up</title>
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("teamworksys")%>
<%=URLHelper.css("users/signup")%>
<%
	int index = 0;
	String error = (String) request.getAttribute("error");
	error = error == null ? "" : error;
	if (!error.equals(""))
		index = 1;
%>
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
					<li><%=URLHelper.link("User/index", "首页")%></li>
				</ul>
				<form class="navbar-form navbar-right" role="form" method="post"
					action="<%=URLHelper.url("User", "signin")%>">
					<div class="form-group">
						<input type="text" placeholder="Email" class="form-control">
					</div>
					<div class="form-group">
						<input type="password" placeholder="Password" class="form-control">
					</div>
					<button type="submit" class="btn btn-success">Sign in</button>
				</form>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>
	<div class="container">
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
				<div id="signupblock" data-error-index="<%=index%>">
					<form class="form-horizontal" role="form"
						action="<%=URLHelper.url("User/signup")%>" method="post">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="inputEmail3"
									name="email" placeholder="Email" required>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword3"
									name="password" placeholder="Password" required>
							</div>
						</div>
						<div class="form-group">
							<label for="inputConfirmPassword3" class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control"
									id="inputConfirmPassword3" placeholder="Confirm password"
									required>
							</div>
						</div>
						<div class="form-group">
							<label for="inputNickname3" class="col-sm-2 control-label">昵称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputNickname3"
									name="nickname" placeholder="nickname" required>
							</div>
						</div>
						<div class="form-group">
							<label for="inputSex3" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="sex" id="sexRadio1" value="MALE">
									男
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="sexRadio2" value="FEMALE">
									女
								</label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary
								.">Sign
									up</button>
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
	<%=URLHelper.script("users/signup")%>
</body>
</html>