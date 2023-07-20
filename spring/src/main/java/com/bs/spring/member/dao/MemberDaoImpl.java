package com.bs.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bs.spring.member.Member;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Override
	public int insertMember(SqlSession session, Member m) {
		return session.insert("member.insertMember",m); // name-> member  id-> insertMember인 쿼리문찾기
	}

	@Override
	public Member selectMemberById(SqlSession session, Map param) {
		return session.selectOne("member.selectMemberById",param);
	}
	
	public List<Member> selectMemberAll(SqlSession session){
		return session.selectList("member.selectMemberAll");
	}
}
