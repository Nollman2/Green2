package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
		
	@Autowired BoardService boardService;
	
	//게시글작성
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	//게시글상세보기
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) { 
		//@PathVariable GET방식으로 주소창에 id값이 있으므르 이를 읽기위해 사용
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
	}
	
	//게시글수정하기
	@GetMapping("/board/{id}/updateForm")
	public String update(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}	
	
	//게시글 검색
	@GetMapping("/auth/search")
	public String searchcontent(@RequestParam(value="keyword")String keyword,
								@RequestParam(value="searchtype")String searchtype,
								@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
								Model model) {		
	
	model.addAttribute("findList", boardService.searchpost(keyword,searchtype,pageable));
	return "board/boardsearch";
	}	

}
