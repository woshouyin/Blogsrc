<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page errorPage="error404.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body>
	<script>
		function show(){
			frm = document.getElementById("frm");
			aidinp = document.getElementById("aid");
			aid = aidinp.value;
			frm.src = "GetArticleServlet?aid=" + aid;
		}
	</script>
	<input type="number" id="aid">
	<button onclick="location.href = 'User.jsp'">用户</button>
	<button onclick="show()">提交</button>
	<br>
	<iframe id="frm" src="" width=80% height=600></iframe>
</body>
</html>