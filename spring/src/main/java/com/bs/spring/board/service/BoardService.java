package com.bs.spring.board.service;

import java.util.List;
import java.util.Map;

import com.bs.spring.board.model.dto.Board;

public interface BoardService {
	List<Board> selectBoardList(Map<String, Object> param);
	int insertBoard(Board b);
	Board selectBoardDetail(int boardNo);
	
	int selectBoardCount();
}
