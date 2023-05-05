//시작
let index ={
	//init() 해당 function()들이 시작됨
	init: function() {
		$("#btn-save").on("click",()=>{											
			this.saveCheck();
		});
		$("#btn-update").on("click",()=>{					
			this.update();
		});
		$("#btn-findId").on("click",()=>{					
			this.findId();
		});
		$("#btn-findpwd").on("click", () => {			
			this.findpwd();
		});
		$("#btn-out").on("click", () => {	
			if (confirm("정말로 탈퇴하시겠습니까?") == true) {
				this.out();
			}else{
				alert("탈퇴가 취소되었습니다");
				return;
			};
					
			
		});
	},
	
	saveCheck: function() {
		var username = document.getElementById('username');
		var password = document.getElementById('password');
		var password2 = document.getElementById('password2');
		var username2 = document.getElementById('username2');
		var birth = document.getElementById('birth');
		var address = document.getElementById('address');
		var tel = document.getElementById('tel');
		var email = document.getElementById('email');
		var number = document.getElementById('number');
		var agree1 = document.getElementById('agree1');
		var agree2 = document.getElementById('agree2');
		var idcheck = document.getElementById('idcheck');

		if (username.value == "") {
			alert("아이디를 입력해주세요");			
			username.focus();
			return false;		
		};		

		if (idcheck.value == "") {
			alert("아이디 중복확인을 해주세요");
			id.focus();
			return false;
		};

		if (password.value == "") {
			alert("비밀번호를 입력해주세요");	
			password.focus();
			return false;		
		};

		var pwdCheck = /^(?=.*[0-9]).{8,25}$/;

		if (!pwdCheck.test(password.value)) {
			alert("비밀번호는 8~25자리를 사용합니다"); 
			password.focus();
			return false;
		};

		if (password2.value !== password.value) {
			alert("비밀번호가 일치하지 않습니다");
			password2.focus();
			return false;		
		};		

		if (username2.value == "") {
			alert("이름을 입력해주세요");
			username2.focus();
			return false;
		};

		if (birth.value == "") {
			alert("생년월일을 입력해주세요");
			birth.focus();
			return false;
		};

		var birthCheck = /^(?=.*[0-9]).{8}$/;
	
		if (!birthCheck.test(birth.value)) {
			alert("정확한 생년월일을 입력해주세요");
			birth.focus();
			return false;
		};

		if (address.value == "") {
			alert("주소를 입력해주세요");
			address.focus();
			return false;
		};

		if (tel.value == "") {
			alert("전화번호를 입력해주세요");
			tel.focus();
			return false;
		};

		var telCheck = /^(?=.*[0-9]).{10,11}$/;

		if (!telCheck.test(tel.value)) {
			alert("정확한 번호를 입력해주세요");
			tel.focus();
			return false;
		};

		if (email.value == "") {
			alert("이메일을 입력해주세요")
			email.focus();
			return false;
		};

		var emailCheck = /^(?=.*[a-z])[a-z0-9]{1,12}@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

		if (!emailCheck.test(email.value)) {
			alert("정확한 이메일을 입력해주세요");
			email.focus();
			return false;
		};

/*
		if (number.value == "") {
			document.getElementById('numbererror').innerHTML = "인증번호를 입력해주세요"
			number.focus();
			return false;
		} else {
			document.getElementById('numbererror').innerHTML = ""
		};

		if (number.value !== code) {
			document.getElementById('numbererror').innerHTML = "인증번호가 일치하지 않습니다"
			number.focus();
			return false;
		} else {
			document.getElementById('numbererror').innerHTML = ""
		};
*/

		if (!agree1.checked) {
			alert("약관동의를 체크하세요");
			agree1.focus();
			return false;
		};
		if (!agree2.checked) {
			alert("약관동의를 체크하세요");
			agree1.focus();
			return false;
		};

		this.save();

	},
	
	
	save : function() {
		let data = {
			username : $("#username").val(),
			password : $("#password").val(),
			username2 : $("#username2").val(),
			birth : $("#birth").val(),
			address : $("#address").val(),
			tel : $("#tel").val(),			
			email: $("#email").val()
		};
		
		$.ajax({
			type:"POST",
			url:"/auth/joinProc", 			
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){		
			//정상작동시 리턴된 ResponseDto<Integer>(HttpStatus.OK.value(), 1)를 받음	
			//ResponseDto의 state는 HttpStatus.OK.value()
			//ResponseDto의 data는 1
			console.log(resp.state);				
			alert("회원가입이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		let data={
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			username2 : $("#username2").val(),
			birth : $("#birth").val(),
			address : $("#address").val(),
			tel : $("#tel").val(),			
			email: $("#email").val()
		};
		
		$.ajax({						
			type:"PUT",
			url:"/user/update",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
			
		}).done(function(resp){
			alert("회원정보 수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
						
	},
	
	findId: function() {
		
		let data={			
			username2 : $("#username2").val(),			
			email: $("#email").val()
		};					
		
		$.ajax({						
			type:"POST",
			url:"/auth/id/search",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
			
		}).done(function(resp){	
			alert("ID : "+resp.data);			
			location.href="/auth/loginForm";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
				
	},
	
	findpwd: function() {

		let data = {
			username: $("#username").val(),
			email: $("#email").val()
		};
		
		console.log(data);

		$.ajax({
			type: "POST",
			url: "/auth/findpwd",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
			}).done(function(resp){
			alert("비밀번호가 재발급되었습니다");
			location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			});

	},
	
	out: function() {		
		var id = $("#id").val();

		$.ajax({
			type: "DELETE",
			url: "/user/out/" + id,
			dataType: "json"
		}).done(function(resp) {
			location.href = "/logout";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	
	
	
}
//init()의 function()들 종료
index.init();


//아이디중복검사
function idcheck() {
	var username = document.getElementById('username').value;
	document.getElementById('idcheck').value = username;

	$.ajax({
		type: "POST",
		url: "/auth/user/check", //auth
		data: username,
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(function(resp) {
		//해당이이디가 존재하지 않을 시 resp.data의 값은 0
		//해당아이디 존재시 resp.data의 값은 중복아이디의 id값
		if (resp.data !== 0) {
			alert("중복된 아이디입니다")
		} else {
			alert("사용할 수 있는 아이디입니다")
		}

	});	
	
}

//약관동의 일괄체크
function selectAll(selectAll) {
	const checkboxes
		= document.getElementsByName('agree');

	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}