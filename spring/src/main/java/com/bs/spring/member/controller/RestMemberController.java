package com.bs.spring.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bs.spring.member.Member;
import com.bs.spring.member.service.MemberService;

@RestController // REST방식으로 데이터만 뿌려는 컨트롤러
@RequestMapping("/member")
// 위와같이 @RestController로 선언하면 이 컨트롤러 밑에 메소드들 앞에 @ResponseBody를 다 붙일필요없음
// 알아서 다 붙여짐
public class RestMemberController {
	
	private MemberService service;
	
	public RestMemberController(MemberService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity selectMemberAll(){
		List<Member> members= service.selectMemberAll();
		ResponseEntity<List<Member>> respones = ResponseEntity.ok(members);
				/*new ResponseEntity(members,HttpStatus.BAD_REQUEST);*/
				
		
		return respones;
	}
	
	@GetMapping("/{id}")  
	// 주소창에 http://localhost:8080/spring/member/admin 
	// 검색하면 해당 id가 admin인 데이터 조회
	public Member selectMemberById(@PathVariable("id") String id){
		return service.selectMemberById(Map.of("userId",id));
	}
	
	@PostMapping
	public int intsertMember(@RequestBody Member m) {
		return service.insertMember(m);
	}
	
	/*
	 * @PutMapping public int updateMember(@RequestBody Member m) { return
	 * service.updateMember(m); }
	 */
	
	/*
	 * @DeleteMapping("/{id}")   // 주소만 입력하면 구별할 수 없지만, 매핑방식으로 작동하게 됨
	 * public int deleteMember(@PathValiable("id") String id { 
	 * return service.deleteMember(m); }
	 */
	
	// 특정 게시글에 댓글들 가져오기
	//  /board/{no}/comment/{commentno}	
	// ResponseEntity 객체를 이용해서 
	
	
}
