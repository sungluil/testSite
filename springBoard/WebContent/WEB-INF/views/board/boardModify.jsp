<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardView</title>
</head>
<body>
<table align="center">
	<tr>
		<td>
			<table border ="1">
				<tr>
					<td width="120" align="center">
					Title
					</td>
					<td width="400">
					<input class="boardTitle" name="boardTitle" type="text" size="50" value="${board.boardTitle}"> 
					<input class="boardType" name="boardType" type="hidden" size="50" value="${board.boardType}"> 
					<input class="boardNum" name="boardNum" type="hidden" size="50" value="${board.boardNum}"> 
					</td>
				</tr>
				<tr>
					<td height="300" align="center">
					Comment
					</td>
					<td>
					<textarea class="boardComment" name="boardComment"  rows="20" cols="55">${board.boardComment}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center">
					Writer
					</td>
					<td>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right">
			<button id="modifybtn" class="btn btn-primary" type="button" >글작성</button>
			<button class="btn btn-primary" type="button" onclick="javaScript:history.back(-1)">뒤로</button>
		</td>
	</tr>
</table>	
<script type="text/javascript">

$j("#modifybtn").click(function() {
	
	var boardTitle = $j(".boardTitle").val();
	var boardComment = $j(".boardComment").val();
	var boardType = $j(".boardType").val();
	var boardNum = $j(".boardNum").val();
	//alert(boardTitle+","+boardComment+","+boardType+","+boardNum)
	
	$j.ajax({
		url:"/board/boardModifyAction.do",
		data:{boardTitle,boardComment,boardType,boardNum},
		type:"post",
		success:function(data) {
			alert("성공")
			location.href = "/board/boardList.do?pageNo=1";
		},
		error:function(req) {
			
		}
	})
	
	
})

</script>
</body>
</html>