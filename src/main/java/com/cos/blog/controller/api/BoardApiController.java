package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	//글쓰기
	//@RequestBody Post방식으로 Body에담아서 Request한 Data를 Board타입의 board로 받음
	//@AuthenticationPrincipal 현재 사용자의 정보를 받아옴
	@PostMapping("/board/save")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//글삭제
	@DeleteMapping("/board/delete/{id}")
	public ResponseDto<Integer> deleteByid(@PathVariable int id) {
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//글수정하기
	@PutMapping("/board/update/{id}")
	public ResponseDto<Integer> deleteByid(@PathVariable int id,@RequestBody Board board ) {
		//@PathVariable 주소창에있는 id값을 받기위해 사용	//id값을 int id로 받음
		//@RequestBody put방식으로 Body에 담겨진 데이터를 받기위해사용	//Body의 data를 Board타입의 board로 받음
		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

}
