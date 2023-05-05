<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<!-- 시큐리티로그인을 위해 action은 SecurityConfig의 loginProcessingUrl("/auth/loginProc")로 지정 -->
	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="username">username</label> <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox"> Remember me
			</label>
		</div> 
		
		<div id="findinfo">
			
			<div>
				<a href="/auth/findId">아이디찾기</a><br/>
				<a href="/auth/findpwd">비밀번호찾기</a><br/><br/>
			</div>
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=7b2e0649514d4ef6451afbb3084f61aa&redirect_uri=http://localhost:8002/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakaologin.png"/></a>
	</form>
	
</div>

<%@ include file="../layout/footer.jsp"%>