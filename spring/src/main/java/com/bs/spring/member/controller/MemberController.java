package com.bs.spring.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bs.spring.member.Member;
import com.bs.spring.member.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member") // 이런식으로 선언하면, 밑에 나오는 requestMapping 부분의 공통적인 부분을 안써도된다
@SessionAttributes({"loginMember"})  
@Slf4j //롬복이있을때만 사용가능 
public class MemberController {	
	
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/enrollMember.do")
	public String enrollMember(@ModelAttribute("member") Member m) {
		return "member/enrollMemberView"; // viewResolver에 이문자열이 붙응면서 해당 주소로 이동
	}
	
	@RequestMapping(value="/insertMember.do", method=RequestMethod.POST) // 회원가입 컨트롤
	// 405 오류 -> 프런트에서 요청보낸곳의 보낸방식과 매핑에서 설정한 방식이 다르면 발생하는 오류
	public String insertMember(@Validated Member m, BindingResult isResult, Model model) {
		
		if(isResult.hasErrors()) { // 에러발생시
			return "member/enrollMemberView";
		}
		
		// 패스워드를 암호화해서 처리하자
		String oriPassword=m.getPassword();
		log.debug(oriPassword);

		String encodePassword = passwordEncoder.encode(oriPassword); // 암호화한 패스워드
		log.debug(encodePassword);
		m.setPassword(encodePassword);
		
		int result = service.insertMember(m);

		model.addAttribute("msg",result>0?"회원가입성공":"회원가입실패");
		model.addAttribute("loc",result>0?"/":"/member/enrollMember.do");
	
		return "common/msg";
	}
	
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST) // 로그인 컨트롤
	// 인수로, Member m 도 가능 (알아서 일치하는 값들만 세터로 넣어줌)
	// String userId, String password 도 가능 (파라미터값)
	public String loginMember(@RequestParam Map param, Model model) // HttpSession session
	{
		Member m = service.selectMemberById(param);
		
		// 암호화된값을 비교하기 위해서는 BCrptPasswordEncoder 가 제공하는 메소드를 이용해야한다.
		/* passwordEncoder.matches(원본비밀번호, 암호화된비밀번호 ) */
		if(m!=null && passwordEncoder.matches((String)param.get("password"), m.getPassword())) { // 암호화때문에 두번째 조건이존재
			// m.getPassword().equals(param.get("password"))
			// 로그인성공
			// httpSession으로 로그인처리
			// session.setAttribute("loginMember", m); // 세션에다 로그인한 member 객체 저장
			
			
			// model 의 생명주기는 request와 같음
			// Model 을 이용해서 로그인처리
			model.addAttribute("loginMember", m);
		}else {
			model.addAttribute("msg", "로그인 실패");
			model.addAttribute("loc","/");
			return "common/msg";
		}
		
		return "redirect:/";
	}
	
	
	//@RequestMapping("/logout.do") // 로그아웃 컨트롤  (세션을 이용해서 로그아웃)
	// public String logout(HttpSession session){
	//if(session!=null) {
	//	session.invalidate();
	//}
	// return "redirect:/";
	// }
	
	
	// SessionStatus 객체를 이용해서 삭제
	@RequestMapping("/logout.do")
	public String logout(SessionStatus status) {	
		
		/*
		 * if(1==1) throw new IllegalArgumentException("잘못된접근입니다."); // 로그아웃 했을 때 강제 에러발생
		 */
		if(!status.isComplete())status.setComplete(); // 세션을 만료시킴
		
		return "redirect:/";
	}
	
	
	@RequestMapping("/mypage.do")
	public String mypage(String userId, Model m) {
		m.addAttribute("member", service.selectMemberById(Map.of("userId",userId)));
		return "member/mypage";
	}
	
	
}
