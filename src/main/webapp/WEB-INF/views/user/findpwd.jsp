<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	
		<div class="form-group">
			<label for="username">ID</label>
			<input type="text" class="form-control" placeholder="Enter ID"  id="username" name="username">
		</div>
		<div class="form-group">
			<label for="email">Email</label><input type="text" class="form-control" placeholder="Enter Email" id="email" name="email">
		</div>
		<button type="button" id="btn-findpwd" class="btn btn-primary">비밀번호재발급</button>		
		<br><br>	
	
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>