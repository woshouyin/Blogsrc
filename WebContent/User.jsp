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

	PrintWriter writer = response.getWriter();
	String token = null;
	DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
	CookieUtil cu = new CookieUtil(request);
	UserGas ug = cu.getUserByTokenCookie(dm);
	if(ug == null){
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
		return;
	}
	
/*	//被移植到util.CookieUtil.getUserByTokenCookie(DatabaseManager dm)并在上方调用以替代该段
	Cookie[] cookies = request.getCookies();
	if(cookies == null){
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
		return;
	}
	
	for(Cookie cookie : cookies){
		if(cookie.getName().equals("tk")){
			token = cookie.getValue();
		}
	}
	
	if (token == null){
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
		return;
	}
	long id = -1;
	DatabaseManager dm = AttributeGetter.getDatabaseManager(request);
 	try (UserCheckDao ucd = dm.getDao(UserCheckDao.class)){
	 	id = ucd.checkByToken(token);
		if (id == -1){
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
 	} catch(SQLException e){
 		e.printStackTrace();
 	}
	
 	UserGas ug = null;
 	try(UserDao ud = dm.getDao(UserDao.class)){
 		ug = ud.getUserGas(id);
 		if(ug == null && id != -1){
 			LogUtil.warning("users表与user_check表未同步");
 		}
 	} catch(SQLException e){
 		e.printStackTrace();
 	}
*/


%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	writer.append(Long.valueOf(ug.getId()).toString()).append("<br>")
			.append(ug.getRegDate().toString()).append("<br>")
			.append(ug.getName()).append("<br>")
			.append(Integer.valueOf(ug.getPrivilege()).toString()).append("<br>")
			.append(ug.getEmail());
	%>
	<br>
	<button onclick="location.href = 'Write.jsp'">Write</button>
	<button onclick="location.href = 'Article.jsp'">Article</button>
</body>
</html>