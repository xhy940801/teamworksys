<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.xzx.teamsys.util.URLHelper"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<%=URLHelper.css("bootstrap.min")%>
<%=URLHelper.css("users/signin")%>
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<title>Sign in</title>
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
	<div class="container">
		<form class="form-signin" role="form" method="post" action="<%=URLHelper.url("User", "signin")%>">
			<h2 class="form-signin-heading">Please sign in</h2>
			<input type="email" class="form-control" placeholder="Email address" name="email" required autofocus />
			<input type="password" class="form-control" placeholder="Password" name="password" required />
			<!--
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me" />
					Remember me
				</label>
			</div>
			-->
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>
		<div id="signup"><%=URLHelper.link("User/signup", "Sign up")%></div>
	</div>
	<%=URLHelper.script("jquery-1.11.2.min")%>
	<%=URLHelper.script("bootstrap.min")%>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<%=URLHelper.script("ie10-viewport-bug-workaround")%>
</body>
</html>