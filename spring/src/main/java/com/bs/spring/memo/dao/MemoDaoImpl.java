package com.bs.spring.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bs.spring.memo.model.dto.Memo;


@Repository
public class MemoDaoImpl implements MemoDao {

	@Override
	public List<Memo> selectMemoAll(SqlSession session) {
		return session.selectList("memo.selectMemoAll");
	}
	
	@Override
	public int insertMemo(SqlSession session, Memo m) {
		return session.insert("memo.insertMemo",m);
	}
}
