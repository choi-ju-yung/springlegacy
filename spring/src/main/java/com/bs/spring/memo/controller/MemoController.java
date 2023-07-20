package com.bs.spring.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bs.spring.memo.model.dto.Memo;
import com.bs.spring.memo.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {
		
	// 필드에다 autowired 잘 안함
	// 생성자를 통해서 자동으로 의존성 주입
	
	private MemoService service;
	
	public MemoController(MemoService service) {
		this.service = service;
	}
	
	
	@RequestMapping("/selectMemoAll.do")
	public String selectMemoAll(Model m) {
		List<Memo> memos = service.selectMemoAll();
		m.addAttribute("memos",memos);
		return "memo/selectMemoAll";
	}
	
	@RequestMapping(value="/insertMemo.do",method = RequestMethod.POST)
	//@PostMapping("/insertMemo.do")
	public String insertMemo(Memo m) {
		int result=service.insertMemo(m);
		
		if(result==0) {
			return "common/msg";
		}
		return "redirect:/memo/selectMemoAll.do";
	}
	
}
