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
	
	
	
}
index.init();