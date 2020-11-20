<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
<style type="text/css">
ul {
  padding: 0;
}
li {
  display: inline-block;
}
.xx-selected {
	background-color: #ebf0f9;
}
.notify_area .notice {
/*     line-height: 32px; */
    color: #00ba2e;
    font-size: 11px;
    display: inline-block;
    height: auto;
    letter-spacing: -1px;
    white-space: nowrap;
}
.notify_area {
	padding-right: 14px;
    z-index: 40;
}
</style>
</head>
<body>
<form action="/board/excelSelectDown.do" method="post" id="f">
<table style="margin-left: auto; margin-right: auto;">
	<tr>
		<td align="right">
			<div class="notify_area" style="display: inline; padding-right: 190px;"></div>
			total : ${totalCnt}
			<select name="size" id="size">
				<option value="10">10개씩</option>
				<option value="20">20개씩</option>
				<option value="30">30개씩</option>
				<option value="40">40개씩</option>
				<option value="50">50개씩</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<table id="boardTable" border = "1">
			<thead>
				<tr>
					<td width="40" align="center">
						<input type="checkbox" class="allchk" value="" name="allchk">
					</td>
					<td width="80" align="center">
						Type
					</td>
					<td width="40" align="center">
						No
					</td>
					<td width="300" align="center">
						Title
					</td>
				</tr>
			</thead>
			<tbody id="sizeTable">
				<c:forEach items="${boardList}" var="list">
					<tr id="xx">
						<td align="center">
							<input name="delchk" class="x" type="checkbox" value="${list.boardNum }" onclick="check(this)">
						</td>
						<td align="center">
							${list.comCodeVo.codeName}
						</td>
						<td align="center">
							${list.boardNum}
						</td>
						<td>
							<a href = "/board/${list.boardType}/${list.boardNum}/boardView.do?pageNo=${pageNo}">${list.boardTitle}</a>
						</td>
					</tr>	
				</c:forEach>
			</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right" style="padding: 5px;">
			<a href='<c:url value='/board/excelDown.do' />'><button id="excelDownBtn" class="btn btn-primary btn-sm" type="button">엑셀 다운로드</button></a>
			<button id="excelSelectDownBtn" class="btn btn-primary btn-sm" type="button">엑셀 선택 다운로드</button>
			<button id="delAllbtn" class="btn btn-primary btn-sm" type="button">선택삭제</button>
			<button class="btn btn-primary btn-sm" type="button" onclick="location=href='/board/boardWrite.do'">글쓰기</button>
			<button id="searchButton" type="button" class="btn btn-primary btn-sm">조회</button>
		</td>
	</tr>
	<tr>
		<td align="center">
			<ul>
				<li>
					<a href="boardList.do?pageNo=1">&lt;&lt;</a>
					<c:if test="${pageVo.pageNo >= 6 }">
						<a href="boardList.do?pageNo=${pageVo.prevPage }">&lt;</a>
					</c:if>
					<c:forEach var="i" begin="${pageVo.startPage}" end="${pageVo.endPage}">
			            <c:choose>
			                <c:when test="${i eq pageVo.pageNo}">${i}</c:when>
			                <c:otherwise>
			                <a href="boardList.do?pageNo=${i}">${i}</a>
			                </c:otherwise>
			            </c:choose>
			        </c:forEach>
			        <c:if test="${pageVo.pageNo >= 5 }">
			        <a href="boardList.do?pageNo=${pageVo.nextPage }">&gt;</a>
			        </c:if>
			        <a href="boardList.do?pageNo=${pageVo.totalPage }">&gt;&gt;</a>
		        </li>
			</ul>
		</td>
	</tr>
	<tr>
		<td>	
			<div class="custom-control custom-checkbox" id="checkboxArea">
				<input value="all" type="checkbox" name="board1" id="defaultInline1">전체
				<c:forEach var="list" items="${codeList}">
					<c:if test="${list.codeType eq 'menu' }"> 
						<input class="chk02" type="checkbox" name="board2"value="${list.codeId }">${list.codeName}
					</c:if>
				</c:forEach>
				<button id="searchChkButton" type="button" class="btn btn-primary btn-sm">검색</button>
				<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">Open modal</button>
			</div>
		</td>
	</tr>
	<tr>
		<td align="center">
		</td>
	</tr>
	<tr>
		<td>
			<div class="notify_area1">
				
			</div>
		</td>
	</tr>
</table>	
</form>
<div align="center">
<!-- <form action="/board/search2.do" method="post" id="searchForm"> -->
	<select name="search">
<!-- 		<option value="creator" selected="selected">&nbsp;작성자&nbsp;</option> -->
		<option value="board_title" selected="selected">&nbsp;제목&nbsp;</option>
		<option value="board_comment">&nbsp;내용&nbsp;</option>
	</select>
	<input type="text" name="keyword" id="keyword" style="margin-top: 10px; height: 25px; width: 300px;">
	<button id="search2" type="submit" class="btn btn-primary btn-sm">검색</button>
<!-- </form>		 -->
</div>


<!-- 모달창 -->
	<div class="container">
		<!-- The Modal -->
		<div class="modal" id="myModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">Modal Heading</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
					<form name="modalFrm" method="post">
	    			<input type="hidden" name="bizNm" id="bizNm" value="">
					<table class="table table-bordered">
						<colgroup>
							<col style="width: 30%;" />
							<col style="width: 70%;" />
						</colgroup>
						<tbody>
							<tr>
								<th>사업연도</th>
								<td><select class="form-control input-sm"
									id="MDSRCH_BIZ_YEAR" name="MDSRCH_BIZ_YEAR">
										<option value="">선택</option>
										<fpms:yearOption defaultYn="N" />
								</select></td>
							</tr>
							<tr>
								<th>세부사업명</th>
								<td><select class="form-control input-sm"
									id="MDSRCH_ANN_NO" name="MDSRCH_ANN_NO">
										<option value="">선택</option>
								</select></td>
							</tr>
							<tr>
								<th>활동처</th>
								<td><select class="form-control input-sm"
									id="MDSRCH_ACTCOMPNO" name="MDSRCH_ACTCOMPNO">
										<option value="">선택</option>
								</select></td>
							</tr>
						</tbody>
					</table>
					</form>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script id="entry-template" type="text/x-handlebars-template">
{{#boardList}}
<tr>
	<td align="center">
		<input name="delchk" class="x" type="checkbox" value="{{boardNum}}" onclick="check(this)">
	</td>
	<td align="center">
		{{comCodeVo.codeName}}
	</td>
	<td align="center">
		{{boardNum}}
	</td>
	<td>
		<a href = "/board/{{boardType}}/{{boardNum}}/boardView.do?pageNo={{pageNo}}">{{boardTitle}}</a>
	</td>
<a href = "/board/${list.boardType}/${list.boardNum}/boardView.do?pageNo=${pageNo}">${list.boardTitle}</a>
</tr>
{{/boardList}}	
</script>
<script type="text/javascript">

$j("#ExcelBtn").click(function() {
	location.href="/board/modalWindow.do";
});

	
$j("#searchButton").click(function() {
	//var a = document.getElementById('blockSize').options[document.getElementById('blockSize').selectedIndex].text;
	var size = document.getElementById('size').options[document.getElementById('size').selectedIndex].value;
	//alert(size);
	$j.ajax({
		url:"/board/boardSizeList.do",
		data:{"size":size},
		dataType: "json",
		type:"POST",
		success:function(json) {
			//alert(blockSize)
			//location.href="/board/boardList.do?pageNo=1";
			var source = $j("#entry-template").html();
			var template = Handlebars.compile(source);
			$j("#sizeTable").html(template(json))
		},
		error:function(req) {
			
		}
	})
});

//조회버튼 클릭시 체크박스 값
$j("#search2").click(function(){
	var board_title = $j("select[name=search]").val();
	var board_comment = $j("#keyword").val();
// 	var sendObject = ({
// 		board_title   : $j("select[name='search']").val()
// 	   ,board_comment : $j("#keyword").val()
// 	})
	//console.log(sendObject);
	$j.ajax({
		type:"POST",		
		url:"/board/boardSearch2.do",
// 		data: JSON.stringify(sendObject),
		data:{"search":board_title,"keyword":board_comment},
		dataType: "json",
		success:function(json) {
			var source = $j("#entry-template").html();
			var template = Handlebars.compile(source);
			$j("#sizeTable").html(template(json))
		},
		error:function(request,status,error) {
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
});

// function check(box) {
// 	if(box.checked == true) {
// 		document.querySelector("#xx").classList.add("xx","selected");
		
// 	} else {
// 		//document.querySelector(".xx-selected").className = "xx";
// 		document.querySelector(".xx",".selected").classList.remove("xx","selected");		
// 	}
// }

$j("#excelSelectDownBtn").click(function() {
	f.submit();
});
// $j("#excelSelectDownBtn").click(function() {
// 	var arrExcel = [];
// 	$j("input[type=checkbox]:checked").each(function() {
// 		arrExcel.push($j(this).val());
// 	})
// 	//var boardNum = arrVal.join(",");
// 	if(arrExcel == "") {
// 		alert("체크해주세요");
// 		return false;
// 	}
// 	$j.ajax({
// // 		enctype: 'multipart/form-data',
// 		url:"/board/excelSelectDown.do",
// 		data:{"excelNum":arrExcel},
// 		type:"post",
// 		async: false,
// 		traditional:true,
// 		success:function(data) {
// 			//alert("엑셀다운성공")
// 			//location.href("/board/excelSelectDown.do");
// 		},
// 		error:function(req) {
			
// 		}
// 	})
// })



$j("#delAllbtn").click(function() {
	var arr = [];
	$j("input[type=checkbox]:checked").each(function() {
		arr.push($j(this).val());
	})

	$j.ajax({
		url:"/board/boardDelete.do",
		data:{"boardNum":arr},
		type:"post",
		traditional:true,
		success:function(data) {
			alert("삭제 성공")
			location.replace("/board/boardList.do?pageNo=1");
		},
		error:function(req) {
			
		}
	})
})

$j(".allchk").change(function() {
	
	if($j(this).prop("checked")) {
		
		$j("input[name=delchk]").prop('checked', true);
		$j(".x").parent().parent().prop("className","xx-selected")
		
	} else {
		$j("input[name=delchk]").prop('checked', false);
		$j(".x").parent().parent().prop("className","")
	}
	//체크박스 갯수	
	var chk_leng = $j("input:checkbox[name=delchk]:checked").length;
	if(chk_leng > 0) {
		var html ="<span id='notice' class='notice'><span class='selection'>"+chk_leng+"개의 메일을 선택했습니다.</span></span>"
		$j(".notify_area").html(html);	    	
	} else {
		$j(".notify_area").html("");
	}
	

	
});
$j(".x").change(function() {
	
	var $this = $j(this);
	if($this.prop('checked')) {
		$this.parent().parent().prop("className","xx-selected")
	} else {
		$this.parent().parent().prop("className","xx")
	}
	//체크박스 갯수	
	var chk_leng = $j("input:checkbox[name=delchk]:checked").length;
	if(chk_leng > 0) {
		var html ="<span id='notice' class='notice'><span class='selection'>"+chk_leng+"개의 메일을 선택했습니다.</span></span>"
		$j(".notify_area").html(html);	    	
	} else {
		$j(".notify_area").html("");
	}
	
	var selchk = $j("input:checkbox[name=delchk]:checked").length
	var fulchk = $j("input:checkbox[name=delchk]").length
	console.log(selchk)
	console.log(fulchk)
	if(selchk==fulchk) {
		$j("input:checkbox[name=allchk]").prop("checked", true);	
	} else if (selchk<fulchk) {
		$j("input:checkbox[name=allchk]").prop("checked", false);	
	}

})
check();
function check() {
	//event.preventDefault(); // event 중단 함수
	//체크박스 4개체크시에 전체버튼체크
	$j("input:checkbox[name=board2]").click(function() {
		//alert("누름");
		if($j("input:checkbox[name=board2]:checked").length == 4) {
			$j("input:checkbox[name=board1]").prop("checked", true);
		} else {
			$j("input:checkbox[name=board1]").prop("checked", false);
		}
	});
	//전체버튼 체크시 전부체크 전부해제
	$j("input:checkbox[name=board1]").click(function() {
		if ($j(this).prop('checked')) {
			$j("input:checkbox[name=board2]").prop("checked", true);			
		} else {
			$j("input:checkbox[name=board2]").prop("checked", false);
		}
	});


		
		
	//조회버튼 클릭시 체크박스 값
	$j("#searchChkButton").click(function(){
		var arrVal = [];
		$j("input[name='board2']:checked").each(function(){
			arrVal.push($j(this).val());
		});
		console.log(arrVal);
		var boardType = arrVal.join(",");
		console.log(boardType);
		
		$j.ajax({
			type:"POST",
			url:"/board/boardSearch.do",
			data:{"boardType":boardType},
			dataType: "json",
			success:function(json) {
				//alert("성공");
				var source = $j("#entry-template").html();
				var template = Handlebars.compile(source);
				$j("#sizeTable").html(template(json))
			},
			error:function(request,status,error) {
				//alert("에러 = "+req.status);
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	});
	
}


//체크박스 갯수	
var chk_leng = $j("input:checkbox[name=delchk]:checked").length;
if(chk_leng > 0) {
	var html ="<span id='notice' class='notice'><span class='selection'>"+chk_leng+"개의 메일을 선택했습니다.</span></span>"
	$j(".notify_area").html(html);	    	
} else {
	$j(".notify_area").html("");
}


/*
function openPop() {
	var win = window.open("/board/popup.do", "PopupWin", 'width=400, height=600, left=2000, top=400, resizable = yes');
}
*/

//팝업창
openPop();
function openPop() {
	if(getCookie("내쿠키")!="true") {
		var win = window.open("/board/popup.do", "PopupWin", 'width=400, height=600, left=2000, top=400, resizable = yes');	
	}
}
//쿠키가져오기
function getCookie(cookieName) {
	
	//var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    //return value? value[2] : null;
	
    /*
    var nameOfCookie = name + "=";  
    var x = 0;  
    while ( x <= document.cookie.length )  
    {  
        var y = (x+nameOfCookie.length);  
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {  
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )  
                endOfCookie = document.cookie.length;  
            return unescape( document.cookie.substring( y, endOfCookie ) );  
        }  
        x = document.cookie.indexOf( " ", x ) + 1;  
        if ( x == 0 ) break;  
    }  
    return "";
    */
	cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
		start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}  




// $j(".x").change(function(){
	
// 	var $this = $j(this);
// 	var m = $this.parent().attr('class');
// 	if ($j(this).prop('checked')) {	
// 		$j(this).parent().parent().prop("className", 'xx-selected')
// 	} else {
// 		$j(this).parent().parent().prop("className", 'xx')
// 	}
// });
// //전부선택 or 전부해제
// $j(".allchk").change(function(){
// 	if ($j(this).prop('checked')) {
// 		$j("input:checkbox[name=delchk]").prop("checked", true);	
// 		$j(".xx").prop("className", 'xx-selected')
// 		$j(".xx-selected").css( 'background-color', '#ebf0f9' );
// 	} else {
// 		$j("input:checkbox[name=delchk]").prop("checked", false);
// 		$j(".xx-selected").prop("className", 'xx')
// 		$j(".xx").css( 'background-color', '' );
// 	}
// });

</script>
</html>