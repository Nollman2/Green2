package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;

@Controller //파일을 전송함 //RestController는 데이터를 전송함			
public class MainController {
	
	@Autowired BoardService boardService;

	@GetMapping("/")
	public String index(Model model,@PageableDefault(size= 5, sort = "id",
			direction = Sort.Direction.DESC) Pageable pageable) {
		//id기준으로 3개씩 페이징해서 보여줌		
		
		model.addAttribute("boards",boardService.글목록(pageable));
		//boardService.글목록()의 리턴값을 boards라는 이름으로 index.jsp에 넣어줌
		//리턴받은 boards의 객체는 List<Board>타입이다 BoardService에서 확인 
		return "index";
	}
}
