<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<head>
<style>
#searchbox {
display: flex;	
justify-content: space-around;
width : 330px;
margin : auto;
margin-bottom: 10px;
}
</style>
</head>
	<div class="container">
	<!-- Model로 받아오는값이 List면 items는 그대로 boards 사용 -->
	<!-- Model로 받아오는값이 Page면 items는 boards.content 사용 -->
	 <c:forEach var="findList" items="${findList.content}">
        <div class="card m-2">
        	<div class="card-body">
	            <h4 class="card=title">${findList.title}</h4>				
				<a href="/board/${findList.id}" class="btn btn-primary">상세보기</a>
			</div>    
        </div>
    </c:forEach>
	
	<!-- 검색기능 -->
	<form id="searchbox" style="" name="searchForm" method="GET" action="/auth/search">
	    <select name="searchtype">
	        <option value="title">제목</option>
	        <option value="writer">작성자</option>
	        <option value="content">내용</option>
	    </select>
	    <input type="text" name="keyword" id="keyword">
	    <input type="submit" id="search" name="submit" value="검색">
    </form>     
		
	
	<!-- 페이징기능 -->
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${findList.first}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${findList.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${findList.number-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${findList.last}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${findList.number+1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${findList.number+1}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>	
	
	</div>
	
	<br/>
	
<%@ include file="../layout/footer.jsp"%>


</body>
</html>


