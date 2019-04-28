<%@page import="database.dao.ArticleDao"%>
<%@page import="database.gas.ArticleGas"%>
<%@page import="java.util.ArrayList"%>
<%@page import="util.CookieUtil"%>
<%@page import="java.util.logging.Level"%>
<%@page import="log.LogUtil"%>
<%@page import="database.gas.UserGas"%>
<%@page import="java.sql.SQLException"%>
<%@page import="util.AttributeGetter"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="database.DatabaseManager" %>
<%@ page import="database.dao.UserCheckDao" %>
<%@ page import="database.dao.UserDao" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String token = null;
	boolean isMaster = false;
	DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
	CookieUtil cu = new CookieUtil(request);
	UserGas ug = cu.getUserByTokenCookie(dm);

	String ids = request.getParameter("id");
	if(ids == null){
		if(ug == null){
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}else{
			isMaster = true;
		}
	}else{
		long id = Long.parseLong(ids);
		if(ug == null || ug.getId() != id){
			ug = dm.getDao(UserDao.class).getUserGas(id);
		}else{
			isMaster = true;
		}
	}
	ArrayList<ArticleGas> ags = dm.getDao(ArticleDao.class).getArticlesByAutherId(ug.getId());

%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<%
	out.append(Long.valueOf(ug.getId()).toString()).append("<br>")
			.append(ug.getRegDate().toString()).append("<br>")
			.append(ug.getName()).append("<br>")
			.append(Integer.valueOf(ug.getPrivilege()).toString()).append("<br>")
			.append(ug.getEmail());
	%>
	<br>
	<%
		if (isMaster) {
			out.append("<button onclick=\"location.href = 'Write.jsp'\">Write</button>");
		}
	%>
	<button onclick="location.href = 'Article.jsp'">Article</button>
	<button onclick="location.href = 'Login.jsp'">Login</button>
	<HR>
	<b>Articles:</B>
	<br>
	<%
		for(ArticleGas ag : ags){
			out.append("<br>").append("<a href=\"Article.jsp?aid=")
				.append(Long.toString(ag.getId())).append("\">")
				.append(ag.getTitle()).append("</a>");
		}
	%>
</body>
</html>