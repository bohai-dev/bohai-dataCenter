<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误页面</title>
<!-- jquery -->
<script type="text/javascript" src="resources/jquery/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<script type="text/javascript">
function alertError(){
	
	var s = '${exception}';
	alert(s);
	
}
</script>
</head>
<body>
<div class="container-fluid">
    <h1  style="margin-left:80px;margin-top:50px;">对不起，您没有权限访问该页面!</h1>
    <div style="margin-left:500px;margin-top:50px;">
    <a href="" onclick="alertError()"   class="btn btn-primary">详情</a>&nbsp;
    <a href="javascript:history.back()" class="btn btn-primary">返回</a>
    </div>
</div>   
</body>
</html>