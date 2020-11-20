<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="checkbox" onclick="open1();">일주일간 표시하지 않음.
</body>
<script type="text/javascript">

function setCookie(name,value,dt) {
	
	var date = new Date();
	date.setDate(date.getDate()+dt);
	//document.cookie = "Name=Value(값);Expires=날짜;Domain=도멘인;Path=경로;Secure";
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + date.toDateString() + ";"
}


function open1() {
	
	setCookie("내쿠키", true, 1);
	self.close();
	
}




</script>
</html>