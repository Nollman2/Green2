<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="title">Title</label> 
			<input type="text"	class="form-control" placeholder="Enter title" id="title">
		</div>
		<div class="form-group">
			<label for="content">Content:</label>
			<textarea class="form-control summernote" id="content"></textarea>
		</div>
	</form>
	<button id="btn-save" class="btn btn-primary">저장</button>

</div>

<br />

<script>
	$('.summernote').summernote({tabsize : 2, height : 500});
</script>

<%@ include file="../layout/footer.jsp"%>
<script src="/js/board.js"></script>

</body>
</html>


