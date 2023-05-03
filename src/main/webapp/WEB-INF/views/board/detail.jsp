<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	 
	<c:if test="${board.users.id==principal.user.id }">	
		<button id="btn-delete" class="btn btn-danger">삭제</button>
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
	</c:if>	
	
	<div>
		<br/><br/>
		<div>
			글 번호 : <span id="id"><i>${board.id} </i></span> 
			작성자 : <span><i>${board.users.username} </i></span>
		</div>		
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div>
		<div>${board.content}</div>
	</div>
	
	
	<div class="card">
		<form>
			<input type="hidden" id="userId" value="${principal.user.id}">
			<input type="hidden" id="boardId" value="${board.id}">
			<div class="card-body"><textarea id="reply-content" class="form-control" rows="1" ></textarea></div>
			<div class="card-footer"><button type="button" id="btn-reply-save" class="btn btn-primary">등록</button></div>
		</form>
	</div>
	<br/>
	
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply-box" class="list-group">
			<c:forEach var="reply" items="${board.reply}">
				<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class="font-italic">${reply.users.username} &nbsp;</div>
						<button onclick="index.replyDelete(${board.id},${reply.id})" class="badge">삭제</button>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	
	<hr />
</div>
<br />
<script type="text/javascript" src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>

