package com.bs.spring.ajax.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.spring.board.model.dto.Board;
import com.bs.spring.common.exception.AuthenticationException;
import com.bs.spring.member.Member;
import com.bs.spring.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;  // jackson 라이브러리에서 제공하는것!

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ajax") // 매핑값  
public class AjaxController {
	
	@Autowired
	private MemberService memberService;
	
	// ObjectMapper 로 객체를 반환하는 방법
	@GetMapping("/basicTest.do")
	public void basic(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		Board b = Board.builder().boardTitle("냉무").boardContent("냉무").build();
		
		ObjectMapper mapper = new ObjectMapper(); // jackson 라이브러리에서 제공하는것!
		
		res.setContentType("application/json;charset=utf-8"); // 객체를 보내야하므로 json 방식임
		res.getWriter().write(mapper.writeValueAsString(b));  //
		
//		res.setContentType("text/csv;charset=utf-8");   // 밑에 두개는 일반적인 String 보내는 방식
//		res.getWriter().write("test");
	}
	
	
	// 리턴값에 반환할 객체를 선언
	// @ResponseBody -> json으로 반환할 수 있게 처리
	@GetMapping("/converter")
	@ResponseBody
	public Board convertTest() {
		return Board.builder().boardTitle("spring이 좋다!").boardContent("하하하하").build();
	}
	
	
	@PostMapping("/idduplicate")
	@ResponseBody
	public Member idDuplicate(@RequestParam Map param) {
		
		log.info("{}",param);
		
		Member checkMember = memberService.selectMemberById(param);
		return checkMember;
	}
	
	
	@GetMapping("/basic2")
	public String basic2() {
		return "demo/demo";
	}
	
	@GetMapping("/allMember")
	@ResponseBody
	public List<Member> allMember(){
		if(1==1) throw new AuthenticationException("권한에러발생!"); // 강제로 에러발생
		List<Member> members = memberService.selectMemberAll();
		return members;	
	}

	
	@PostMapping("/insertData.do")
	@ResponseBody
	public Member insertData(@RequestBody Member m) { // @RequestBody로 json으로 넘긴 값들을 알아서 매칭 *단 키값이 일치해야함
		log.info("{}",m);
		return m;
	}
	
	
	// REST API, RESTFul -> session,Cookie 관리 안해! (stateless)
	// URL을 설정할 때 간편하게 서비스를 알아볼 수 있는 방식으로 구현을 하자
	// URL주소를 설정할 때, 행위에 대한 표현을 빼자 -> method를 보고 결정하자
	// GET : Data를 조회 서비스는 GET
	// POST : Data를 저장하는 서비스는 POST
	// PUT : Data를 수정하는 서비스
	// DELETE : Data를 삭제하는 서비스
	// URL을 설정할 때는 명사로 작성한다
	// 예) 회원을 관리하는 서비스
	// GET localhost:9090/spring/member -> 전체 회원 조회
	// GET localhost:9090/spring/member/{id}1||admin -> 회원 1명 조회
	
	// POST localhost:9090/spring/member -> 회원추가
	// PUT localhost:9090/spring/member -> 회원수정
	// DELETE localhost:9090/spring/member -> 회원삭제
	
	
}
