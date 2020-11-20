<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<style type="text/css">
div{ border:solid 1px #000; font-weight:bold; font-size:30px; text-align:center;}
            #wrap{ width:1024px;  padding:10px;background-color:#ccbaba; overflow:hidden; margin:0 auto;}
            #header{background-color:#1ed741; height:80px; color:#fff;}
            #container{ padding:10px;background-color:#3d3939; overflow:hidden; margin:10px 0;}
            #sidebar{ float:left; width:320px;background-color:#f57373; height:400px;}
/*             #content{ float:right; width:650px; background-color:#ccbaba; height:400px;} */
            #content{ float:right; background-color:#ccbaba; width: 100%; height: 100%;}
            #footer{ background-color:#2f26e4; height:80px; color:#fff;}
ul {
	display: flex;
	align-content: flex-start;
	flex-direction: column;
	flex-wrap: wrap;
	overflow: auto;
	padding: 0;
}
</style>
<script type="text/javascript">

</script>
<body>
	<div id="wrap">
		<div id="header">
			<jsp:include page="layout/header.jsp"></jsp:include>
		</div>
	
		<div id="container">
			<div id="sidebar">
				<nav>
				<ul>
					<li><a href="?namePage=boardList.do">메뉴1</a></li>
					<li><a href="/board/boardList.do?namePage=login.do">메뉴2</a></li>
					<li><a href="/board/boardList.do?namePage=join.do">메뉴3</a></li>
				</ul>
				</nav>
			</div>
			<div id="content" style="height: 60%; width: 60%">
				<jsp:include page="/board/${namePage }"></jsp:include>
			</div>
		</div>

		<div id="footer">
			<jsp:include page="layout/footer.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>
