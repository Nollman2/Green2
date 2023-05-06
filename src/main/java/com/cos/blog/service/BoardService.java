package com.cos.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.Users;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
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
	
	//댓글쓰기(Dto사용)
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		Users user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> {
			return new IllegalArgumentException("댓글쓰기실패");
		});
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()-> {
			return new IllegalArgumentException("댓글쓰기실패");
		});
		
		//build()를 이용하는 방법
		Reply reply = Reply.builder()
				.users(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
		
		//Reply에 update매서드를 만들어 입력하는 방법
		Reply reply2 = new Reply();
		reply2.update(user, board, replySaveRequestDto.getContent());
				
		replyRepository.save(reply);		
	}
	
	//댓글쓰기(Dto미사용)
	@Transactional
	public void 댓글쓰기0(int boardId,Reply requestReply,Users user) {
				
		Board board = boardRepository.findById(boardId).orElseThrow(()-> {
			return new IllegalArgumentException("댓글쓰기실패");
		});
		
		requestReply.setUsers(user);
		requestReply.setBoard(board);
		
		replyRepository.save(requestReply);		
	}
	
	//댓글삭제
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	/*
	// 게시글 검색 기능
    @Transactional
    public Page<Board> searchPosts(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(keyword, pageable);

    }
    @Transactional
    public Page<Board> searchPosts1(String keyword, Pageable pageable) {
        return boardRepository.findByContentContaining(keyword, pageable);

    }
	*/
	
	// 게시글 검색
	@Transactional
	public Page<Board> searchpost(String keyword, String searchtype, Pageable pageable) {
		
		Page<Board> listboard = null;

		switch (searchtype) {
			case "title": {
				listboard = boardRepository.findByTitleContaining(keyword, pageable); //  리스트로 받음
				System.out.println("게시글 검색 title" +listboard);
				break;
			}
			case "content": {
				listboard = boardRepository.findByContentContaining(keyword, pageable);
				System.out.println("게시글 검색 content" +listboard);
				break;
			}			
			
		}

		return listboard;
	}
	
}
