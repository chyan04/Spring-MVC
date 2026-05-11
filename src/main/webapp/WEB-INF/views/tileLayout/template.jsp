<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>template</title>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.js" ></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js" ></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js" ></script>

</head>
<body>
<div class="container">
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	
	<div class="navbar navbar-default">
		<tiles:insertAttribute name="menu" />
		
	</div>
	
	<div class="container-fluid" style="min-height: 500px">
		<tiles:insertAttribute name="body" />		
	</div>
	
	<div class="container-fluid text-center" style="background-color: #555; color: white; padding: 15px;">
		<tiles:insertAttribute name="footer" />		
	</div>
	
</div>
</body>
</html>