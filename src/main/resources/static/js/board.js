let index = {
	init : function() {
		$("#btn-save").on("click",()=>{					
			this.save();
		});
		$("#btn-delete").on("click",()=>{					
			this.delete();
		});
		$("#btn-update").on("click",()=>{						
			this.update();
		});
		$("#btn-reply-save").on("click",()=>{						
			this.replySave();
		});
	},
	
	save : function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),			
		};
		
		$.ajax({
			type:"POST",
			url:"/board/save", 			
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	
	delete : function() {		
		let id=$("#id").text();		/*#id는 spen에 있는 text*/
		
		$.ajax({			
			type:"DELETE",
			url:"/board/delete/"+id,						
			dataType:"json"
			
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});		
						
	},
	
		
	update : function() {
		let id=$("#id").val();		/*#id는 input에 있는 value*/ 
		let data={
			title: $("#title").val(),
			content: $("#content").val()			
		};
		
		$.ajax({			
			type:"PUT",
			url:"/board/update/"+id,	
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",		
			dataType:"json"
			
		}).done(function(resp){
			alert("수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
						
	},
	
	
	replySave : function() {
		
		let data = {			
			userId : $("#userId").val(),
			boardId : $("#boardId").val(),
			content: $("#reply-content").val()			
		};	
		
		$.ajax({
			type:"POST",
			url: `/board/${data.boardId}/reply0`, 			
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
	
	replyDelete : function(boardId,replyId) {
				
		$.ajax({
			type:"DELETE",
			url: `/board/${boardId}/reply/${replyId}`, 				
			dataType:"json"
		}).done(function(resp){
			alert("댓글삭제성공");
			location.href=`/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
	
	
	
}
index.init();