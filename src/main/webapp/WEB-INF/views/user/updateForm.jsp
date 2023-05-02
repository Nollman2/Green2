<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}"/>
		<div class="form-group">
			<label for="username">ID</label> <input type="text" value="${principal.user.username}"
			class="form-control" placeholder="Enter ID" id="username" readonly>
		</div>
		<div class="form-group">
			<label for="pwd">Password</label> <input type="password" 
			class="form-control" placeholder="Enter Password" id="password">
		</div>	
		<div class="form-group">
			<label for="username2">Name</label> <input type="text" value="${principal.user.username2}"
			class="form-control" placeholder="Enter Name" id="username2">
		</div>
		<div class="form-group">
			<label for="birth">Birth</label> <input type="text" value="${principal.user.birth}"
			class="form-control" placeholder="Enter Birth" id="birth">
		</div>
			<div class="form-group">
			<label for="address">Address</label> <input type="text" value="${principal.user.address}"
			class="form-control" placeholder="Enter Address" id="address">
		</div>
			<div class="form-group">
			<label for="tel">Tel</label> <input type="text" value="${principal.user.tel}"
			class="form-control" placeholder="Enter Tel" id="tel">
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" value="${principal.user.email}"
			class="form-control" placeholder="Enter email" id="email">
		</div>
			
	</form>
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>
<script src="/js/user.js"></script>
<br />
<%@ include file="../layout/footer.jsp"%>