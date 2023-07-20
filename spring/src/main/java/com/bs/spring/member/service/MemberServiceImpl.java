package com.bs.spring.member.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.spring.member.Member;
import com.bs.spring.member.dao.MemberDao;

@Service // 어노테이션 설정안하면, NoSuchBeanException 발생
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao dao;
	
	@Autowired
	private SqlSessionTemplate session; 
	
	@Override
	public int insertMember(Member member) {
		return dao.insertMember(session, member);
	}
	
	@Override
	public Member selectMemberById(Map param) {
		return dao.selectMemberById(session,param);
	}
	
	
	@Override
	public List<Member> selectMemberAll(){
		return dao.selectMemberAll(session);
	}
	
}
