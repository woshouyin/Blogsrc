<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="res/simditor-2.3.23/styles/simditor.css">
		<script type="text/javascript" src="assets/scripts/jquery.min.js"></script>
		<script type="text/javascript" src="assets/scripts/module.js"></script>
		<script type="text/javascript" src="assets/scripts/hotkeys.js"></script>
		<script type="text/javascript" src="assets/scripts/uploader.js"></script>
		<script type="text/javascript" src="assets/scripts/simditor.js"></script>
		<title>Insert title here</title>
	
	</head>
	
	<body>
		<button onclick="location.href = 'User.jsp'">User</button>
		<br>
		<form action="ArticleWriterServlet" method="post" 
				onsubmit="document.getElementById('submit').disabled=true")>
			<%request.getSession().setAttribute("token", UUID.randomUUID().toString());%>
			<input name="token" type="hidden" value="${token}">
			<textarea name="title" placeholder="Title"></textarea>
			<textarea name="article" id="editor" placeholder="Balabala" autofocus></textarea>
			<br>
			<button type="submit" id="submit">提交</button>
		</form>
		<script>
			var editor = new Simditor({
			  textarea: $('#editor'),
			  defaultImage: 'assets/images/image.png',
			  upload: {
				  url: "UploadServlet",
				  params: null,
				  fileKey: 'image_upload_file',
				  connectionCount: 3,
				  leaveConfirm: 'Uploading is in progress, are you sure to leave this page?'
			  }
			});
		</script>
		
	</body>
</html>