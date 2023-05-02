package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.blog.model.Board;
import com.cos.blog.model.Users;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	//글쓰기
	@Transactional
	public void 글쓰기(Board board,Users user) {
		board.setCount(0);
		board.setUsers(user);
		boardRepository.save(board);
	}
	
	//게시글 목록
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	//게시글 상세보기
	@Transactional
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
			.orElseThrow(()->{
				return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}
	
	//게시글 삭제하기
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	//게시글 수정하기
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		//받아온 Board의 id값을 DB에서 조회해 해당 게시글을 board로 생성		
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});
		//기존의 게시글로 생성된 board객체에 새로 입력된 data를 set 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	
	}

}
