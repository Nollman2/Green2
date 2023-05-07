<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<head>
<!-- 다음주소API -->
<script	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<div id="kakaomap" style="display: none">
<p>카카오(다음) 주소찾기</p>
<div >Company Address</div>
  <input id="member_post"  type="text" placeholder="Zip Code" readonly onclick="findAddr()">
  <input id="member_addr" type="text" placeholder="Address" readonly> <br>
  <input type="text" placeholder="Detailed Address">
  </div>
</body>

<script>
function findAddr(){
	new daum.Postcode({
        oncomplete: function(data) {
        	
        	console.log(data);
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var jibunAddr = data.jibunAddress; // 지번 주소 변수
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('member_post').value = data.zonecode;
            if(roadAddr !== ''){
                document.getElementById("address").value = roadAddr;
            } 
            else if(jibunAddr !== ''){
                document.getElementById("address").value = jibunAddr;
            }
        }
    }).open();
}
</script>
<div class="container">
	
		<div class="form-group">
			<label for="username">ID</label> 
			<input type="text" class="form-control" placeholder="Enter ID" id="username">
			<br>
			<button onclick="idcheck()">중복확인</button>
			<input type="hidden" id="idcheck">
		</div>		
		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" class="form-control" placeholder="Enter Password" id="password">
		</div>
		<div class="form-group">
			<label for="password2">PasswordCheck</label> 
			<input type="password" class="form-control" placeholder="Enter Password" id="password2">
		</div>
		<div class="form-group">
			<label for="username2">Name</label> 
			<input type="text" class="form-control" placeholder="Enter Name" id="username2">
		</div>
		<div class="form-group">
			<label for="birth">Birth</label> 
			<input type="text" class="form-control" placeholder="Enter Birth" id="birth">
		</div>
		<div class="form-group">
			<label for="address">Address</label> 
			<input type="text" class="form-control" placeholder="Enter Address" id="address" onclick="findAddr()">
		</div>
		<div class="form-group">
			<label for="tel">Tel</label> 
			<input type="text" class="form-control" placeholder="Enter Tel" id="tel">
		</div>			
		<div class="form-group">
			<label for="email">Email</label> 
			<input type="email" class="form-control" placeholder="Enter Email" id="email"><br/>
			<button id="btn-joinnumber" onclick="joinNumber()">인증번호받기</button>
		</div>	
		<div class="form-group">
			<label for="number">인증번호</label> 
			<input type="text" class="form-control" placeholder="인증번호를 입력해주세요" id="number">
		</div>	
		
		
		<div class="signUpcheck">
			<input type="checkbox" name="agree" id="agreeAll" onclick="selectAll(this)" value="selectall">
			<spen id="signUptitle">&nbsp;약관 전체 동의하기</spen>
			<br>
			<div id="signUpcontent">
				<input type="checkbox" name="agree" id="agree1">&nbsp;이용약관 동의(필수)<br> 
				<input type="checkbox" name="agree" id="agree2">&nbsp;개인정보 취급 방침 동의(필수)<br> 
				<input type="checkbox" name="agree"	id="agree3">&nbsp;마케팅정보 수신 동의(선택)<br> <br>
			</div>
		</div>
	
	<!-- form형식이 아닌 javascrip를 통해 데이터를 전송 -->
	<button id="btn-save" class="btn btn-primary">회원가입</button>
</div>

<script src="/js/user.js"></script>
<br />
<%@ include file="../layout/footer.jsp"%>
