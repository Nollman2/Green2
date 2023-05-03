<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	
		<div class="form-group">
			<label for="username2">Username</label><input type="text" class="form-control" placeholder="Enter Username"  id="username2" name="username2">
		</div>
		<div class="form-group">
			<label for="email">Email</label><input type="text" class="form-control" placeholder="Enter Email" id="email" name="email">
		</div>
		<button type="submit" id="btn-findId"class="btn btn-primary">아이디찾기</button>		
		<br><br>
		
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>