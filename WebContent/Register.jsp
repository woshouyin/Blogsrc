<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action = "RegisterServlet" method = "post" onsubmit = "document.getElementById('submit').disabled = true">
		<table align="center">
			<tr>
				<td>
					Name:
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
					E-mail:
				</td>
				<td>
					<input type = "text" name = "email"><br>
				</td>
			</tr>
			<tr>
				<td>
					<input id = "submit" type = "submit" value = "提交">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>