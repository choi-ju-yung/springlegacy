package com.bs.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.bs.spring.board.model.dto.Attachment;
import com.bs.spring.board.model.dto.Board;

public interface BoardDao {
	
	List<Board> selectBoardList(SqlSession session,Map<String, Object> param);
	int insertBoard(SqlSession session, Board b);
	Board selectBoardDetail(SqlSession session, int boardNo);
	
	int selectBoardCount(SqlSession session); // 전체 글 개수
	
	int insertAttachment(SqlSession session, Attachment a);
	
}
