<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		var isCommited = false;
		function onSubmit(){
			if(isCommited){
				return false;
			}else{
				isCommited = true;
				return true;
			}
			
		}
	</script>
	<form action = "LoginServlet" method = "post" onsubmit = "return onSubmit()">
		<table align="center">
			<tr>
				<td>
					NAME:
				</td>
				<td>
					<input type = "text" name = "name">
				</td>
			</tr>
			<tr>
				<td>
					PASSWORD:
				</td>
				<td>
					<input type = "password" name = "password"><br>
				</td>
			</tr>
			<tr>
				<td>
					<input id="submit" type = "submit" value = "登录">
				</td>
			</tr>
		</table>
	</form>
	<button onclick = "location.href='Register.jsp'">Register</button>
	<button onclick="location.href = 'Article.jsp'">Article</button>
</body>
</html>