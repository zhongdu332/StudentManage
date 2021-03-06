<%@ page language="java" import="java.util.*;" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>系统登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		<link href="css/style.css" rel='stylesheet' type='text/css' />
		<script src="scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="login-form-wrap">
			<h1>
				学生成绩管理系统
			</h1>
			<div class="login-form">
				<div class="head-info">
					<label class="lbl-1">
					</label>
					<label class="lbl-2">
					</label>
					<label class="lbl-3">
					</label>
				</div>
				<div class="clear">
				</div>
				<div class="avtar">
					<img src="images/login/avtar.png" />
				</div>
				<div style="color: white"></div>
				<form action="login" method="post">
					<input type="hidden" name="cmd" value="login" />
					<input type="text" id="username" name="uAccounts" placeholder="请输入用户名" autocomplete="off">
					<input type="password" id="password" name="uPassword" placeholder="请输入密码" autocomplete="off">
					<div class="signin">
						<input class="submit" id="login" type="submit" value="登陆" />
					</div>
				</form>
			</div>
		</div>
        <!-- 引入消息脚本页面 -->
        <jsp:include page="/views/message.jsp"></jsp:include>
	</body>
</html>