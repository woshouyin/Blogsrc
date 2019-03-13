<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action = "LoginServlet" method = "post">
		<table align="center">
			<tr>
				<td>
					ID:
				</td>
				<td>
					<input type = "number" name = "id">
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
					<input type = "submit" value = "提交">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>