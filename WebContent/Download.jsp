<%@page import="util.UnitConUtil"%>
<%@page import="util.AttributeGetter"%>
<%@page import="config.AMSConfig"%>
<%@page import="util.DownloadUtil"%>
<%@page import="util.UnitConUtil"%>
<%@page import="java.io.File"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action = "DownloadServlet" method="post">
		文件名:<input type="text" name="fileName"><br>
		下载地址:<input type="text" name="urlStr"><br>
		<button type="submit">提交</button>
	</form>
	<HR>
	<b>文件:</b>
	<%
		AMSConfig amsConfig = AttributeGetter.getAMSConfig(request);
		String path = amsConfig.get("amsHomePath") + "files/download/";
		File file = new File(path);
		File[] fs = file.listFiles();
		for (File f : fs){
			String name = f.getCanonicalFile().getName();
			String len = UnitConUtil.longToString(f.length());
			out.append("<br>").append("<a href=\"").append("files/download/").append(name).append("\">").append(name).append("</a>")
				.append(len).append("B");
		}
	%>
</body>
</html>