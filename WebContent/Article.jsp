<%@page import="java.util.UUID"%>
<%@page import="jdk.internal.agent.resources.agent"%>
<%@page import="config.AMSConfig"%>
<%@page import="util.AttributeGetter"%>
<%@page import="database.DatabaseManager"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="database.dao.ArticleDao"%>
<%@page import="database.gas.ArticleGas"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page errorPage="error404.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
</head>
<body>
	<%
		PrintWriter writer = response.getWriter();
		DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
		String base = AttributeGetter.getAMSConfig(request).getString("amsHomePath");
		String aids = request.getParameter("aid");
		ArticleGas ag = null;
		if(aids != null && !"".equals(aids)){
			long aid = Long.parseLong(aids);
			ag = dm.getDao(ArticleDao.class).getArticleGas(aid);
		}
	%>
	<script>
		function show(){
			frm = document.getElementById("frm");
			aidinp = document.getElementById("aid");
			aid = aidinp.value;
			location.href = "GetArticleServlet?aid=" + aid;
		}
	</script>
	<input type="number" id="aid">
	<button onclick="location.href = 'User.jsp'">用户</button>
	<button onclick="location.href = 'Article.jsp?aid=' + document.getElementById('aid').value">提交</button>
	<br>
		<%=ag == null ? "无此文章" : ag.getTitle() %>
	<br>
		<%=ag == null ? "" : "    作者:" + "<a href=\"User.jsp?id=" + ag.getAutherId() + "\">" + ag.getAutherName() + "</a>"%>
	<HR>
		<%=ag == null ? "" : ag.readContent(base) %>
	<br>
</body>
</html>