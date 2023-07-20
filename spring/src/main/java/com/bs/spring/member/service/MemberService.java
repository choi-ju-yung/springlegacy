package com.bs.spring.member.service;

import java.util.List;
import java.util.Map;

import com.bs.spring.member.Member;

public interface MemberService {
	
	int insertMember(Member member);
	
	Member selectMemberById(Map param);
	
	List<Member> selectMemberAll();
}
