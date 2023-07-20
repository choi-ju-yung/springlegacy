package com.bs.spring.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.bs.spring.memo.model.dto.Memo;

public interface MemoDao {
	
	List<Memo> selectMemoAll(SqlSession session);
	
	int insertMemo(SqlSession session, Memo m);
}
