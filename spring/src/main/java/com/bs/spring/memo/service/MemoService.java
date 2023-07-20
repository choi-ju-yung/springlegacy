package com.bs.spring.memo.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.bs.spring.memo.model.dto.Memo;

public interface MemoService {
	
	List<Memo> selectMemoAll();
	int insertMemo(Memo m);
}
