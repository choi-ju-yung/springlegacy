package com.bs.spring.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.bs.spring.demo.model.dto.Demo;
import com.bs.spring.demo.service.DemoService;

@Controller
public class DemoController {
	
	private Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private DemoService service;
	
	@RequestMapping("/demo/demo.do")
	public String demo() {
		return "demo/demo";
	}
	
	// 메소드 선언하기
	// 리턴값, 매개변수 알아보기
	// 1. 반환형 
	//  1) String : ViewResolver에 의해서 view화면을 출력해준다 * 기본적으로 많이 사용
	//  2) void : HttpServletResponse 객체로 직접 응답메시지를 작성할 때 사용 = (do get과 동일함) [파일업로드같은것 할때 사용]
	//  3) ModelAndView : 화면에 전달할 데이터와 view 내용을 저장하는 객체 (spring이 제공해줌)
	//  4) 클래스타입 : json으로 데이터를 반환할때, Restful하게 서버를 구성했을 때 많이 사용
	//   * ResponseEntity<클래스타입>
	
	// 2. 매개변수로 선언할 수 있는 타입
	//  1) HttpServletRequest : servlet처럼 사용가능 
	//  2) HttpServletResponse : servlet처럼 사용가능
	//  3) HttpSession : session값을 가져와서 대입해줌
	//  4) java.util.Locale : 서버의 로케일정보를 저장한 객체
	//  5) InputStream / Reader : 파일 읽어올때 사용하는 stream
	//  6) OutputStream.Writer : 파일 보낼때 사용하는 stream
	//  7) 기본자료형 변수 : 클라이언트가 보낸 parameter 데이터랑 선언한 변수 이름이 동일하면 자동으로 매핑해줌
	//    선언이름과 파라미터이름이 다를경우 @RequestParam 어노테이션을 이용해서 연결할 수 있음
	//  @RequestParam은 매핑, 기본값설정, 필수여부설정
	//  8) 클래스변수 : Command라고 함, parameter 데이터를 필드에 넣어서 객체를 전달
	//    * parameter 이름과 필드명이 같은 데이터를 대입해줌
	//  9) java.util.Map : @RequestParm 어노테이션이랑 같이 사용, parameter값을 map으로 저장
	//		키 : 파라미터의 이름   * 단 단일값에서만 제한이됨
	
	//  10) @RequestHeader(name값)와 기본자료형을 작성하면 header 정보를 받을 수 있음
	//  11) @CookieValue(name값)와 기본 자료형을 작성하면 Cookie에 저장된 값을 받을 수 있음
	//  12) Model : request와 비슷하게 데이터를 key/value 형식으로 저장할 수 있는 객체
	//  13) ModelAndView  : model과 view를 동시에 저장하는 객체

	// 메소드 어노테이션
	// @ResponseBody -> Rest방식으로 클래스를 json으로 전송할 때 
	// @RequestBody -> Json 방식으로 전송된 parameter를 클래스로 받을 때 사용
	// @GetMapping, @PostMapping, @DeleteMapping... 
	
	
	// 서블릿 방식으로 매핑메소드 이용하기
	@RequestMapping("demo/demo1.do")
	public void demo1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		logger.debug("request : {}",req);
		logger.debug("response : {}",res);
		
		String devName = req.getParameter("devName");
		int devAge = Integer.parseInt(req.getParameter("devAge"));
		String devGender = req.getParameter("devGender");
		String devEmail = req.getParameter("devEmail");
		String[] devLage = req.getParameterValues("devLang");
		
		logger.debug(devName+devAge+devGender+devEmail);
		

		for(String l : devLage) {
			logger.debug(l);
		}
		
		Demo d = Demo.builder()
					.devName(devName)
					.devAge(devAge).devEmail(devEmail)
					.devGender(devGender).devLang(devLage).build();
		req.setAttribute("demo", d);
		req.getRequestDispatcher("/WEB-INF/views/demo/demoResult.jsp").forward(req, res);
		
		
		/*
		 * res.setContentType("text/html;charset=utf-8"); PrintWriter out =
		 * res.getWriter(); out.print("<h2>"+devName+devAge+devGender+devEmail+"</h2>");
		 */
	}
	
	
	// viewResolver로 처리하기
	// 1:1로 매칭하여 데이터 받기
	// 매핑메소드의 매개변수에 파라미터로 전송되는 name과 동일한 이름의 변수를 선언
	// 매개변수의 타입은 사용할 타입으로 설정  * 변경이 가능해야함
	@RequestMapping("/demo/demo2.do")
	public String demo2(String devName,int devAge,String devGender,String devEmail,
			String[] devLang, Model model){ 
		// 매개변수중에서 사용하지 않는 String 값은 null값이 들어가서 괜찮지만, int나 double 값 처럼 값이 정해져있는것은 꼭 매개변수가 사용되어야한다
		
		System.out.println(devName+devAge+devGender+devEmail);
		/*
		 * for(String l : devLang) { System.out.println(l); }
		 */
		/* System.out.println(Arrays.toString(devLang)); */
		
		// 페이지에 생성한 데이터를 전송하려면.... request, session, servletcontetx를 사용했지만
		// Spring에서 데이터 전송하는 객체를 제공함 -> Model
		// Model에 데이터 저장하기 -> model.addAttribute("key",value); [String,Object]
		
		Demo d = Demo.builder().devName(devName).devAge(devAge).devGender(devGender)
				.devEmail(devEmail).devLang(devLang).build();
		
		model.addAttribute("demo", d); // 다른 페이지에서 사용할 데이터를 저장
		
		return "demo/demoResult";
	}
	
	
	// 파라미터 데이터를 받을 때, @RequestParam 어노테이션을 이용해서 
	// 옵션을 설정할 수 있다
	@RequestMapping("/demo/demo3.do")
	public String requestParamuse(
		@RequestParam(value="devName", defaultValue = "아무개") String name,  // name 값이 없어도, devName인걸로 인식 
		@RequestParam(value="devAge", defaultValue="10") int age, // defaultValue = 기본값 (값이없으면 10으로 들어감) 
		@RequestParam(value="devGender") String gender,
		@RequestParam(value="devEmail", required = false) String devEmail, // 필수값인지 아닌지 선택할때 -> required
		String[] devLang, Model model) {
		
		System.out.println(name+age+gender);
		System.out.println(devEmail);
		System.out.println(devLang);
		
		Demo d = Demo.builder()
				.devName(name)
				.devAge(age).devEmail(devEmail)
				.devGender(gender).devLang(devLang).build();
		
		model.addAttribute("demo", d);
		
		
		return "demo/demoResult";
	}
	
	
	// DTO/VO 객체로 직접 parameter값 받기
	// 매개변수로 전달되는 parameter 이름과 동일한 필드를 갖는 객체를 선언함
	// * 주의 할점은 클래스타입 Date를 전달받을 때는 java.sql.Date로 하자
	@RequestMapping("/demo/demo4.do")
	public String commandMapping(Demo demo, Model m) { // vo로 보낼때 알아서 값이 setter로 들어감
		System.out.println(demo);
		m.addAttribute("demo", demo);
		return "demo/demoResult";
	}
	
	
	// Map으로 parameter 데이터 받아오기
	// @RequestParam 어노테이션 설정 Map
	@RequestMapping("/demo/demo5.do")
	public String mapPapping(@RequestParam Map param, String[] devLang ,Model m) {
		System.out.println(param);  // Map으로 했을 때,배열값은 처리못함
		param.put("devLang", devLang); // 직접 put메소드로 값을 넣어줘야함
		m.addAttribute("demo", param);
		return "demo/demoResult";
	}
	
	// 추가데이터 받아오기
	// Cookie, Header, Session
	// Cookie : @CookieValue(value="key값") String data
	// Header : @RequestHeader(value="해더이름") String header
	// Session : @SessionAttribute(value="세션key값") String id;
	@RequestMapping("/demo/demo6.do")
	public String extraData(
		@CookieValue(value="testData",required = false, defaultValue = "rest-time") String data, 
		// required : 있어도되고 없어도되도록 설정
		@RequestHeader(value="User-agent") String userAgent,
		@SessionAttribute(value="sessionId") String sessionId,
		@RequestHeader(value="Referer") String referer
		){
		System.out.println("쿠키 : "+data);
		System.out.println("헤더 : "+userAgent);
		System.out.println("세션 : "+sessionId); 
		System.out.println("이전페이지 : "+referer);
		return "index";
	}
	
	// ModelAndView 객체를 이용해서 반환하기
	@RequestMapping("/demo/demo7.do")
	public ModelAndView modelAndViewReturn(Demo d, ModelAndView mv) {
		// ModelAndView -> view 설정과, Model(데이터저장) 설정을 같이 할 수 있는 객체
		// view : setViewName() 메소드를 이용해서 저장
		// data : addObject("key",value) 메소드 이용해서 저장
		mv.addObject("demo",d); // 데이터 저장
		mv.setViewName("demo/demoResult"); // 뷰 저장
		
		return mv;
	}
	
	// 자료형에 대해 반환하기 -> Data만 응답할 때 사용 -> jackson 라이브러리를 이용해서 처리
    // 메소드선언부 @ResponseBody 어노테이션 사용
	// Restfull 메소드를 구현했을 때 사용
	@RequestMapping("/demo/demo8.do")
	@ResponseBody
	public String dataReturn(){
		return "유병승 최주영 조장흠 최솔 조윤진";
	}
	
	
	// Request 요청 메소드(GET, POST)를 필터링하기
	//@RequestMapping(value="url",method=RequestMethod.GET || RequestMethod.POST)
	// @RequestMapping(value="/demo/demo9.do",method=RequestMethod.POST) // 겟방식으로 설정해놓았기때문에, form으로 Post형식으로 값을 넘기면 처리못함
	/* @PostMapping("/demo/demo9.do") */
	@GetMapping("/demo/demo9.do")
	public String methodCheck(Demo d, Model m) {
		m.addAttribute("demo", d);
		return "demo/demoResult";
	}
	
	@RequestMapping(value="/demo/insertDemo.do",method=RequestMethod.POST)
	public String insertDemo(Demo demo, Model m) {
		int result = service.insertDemo(demo);
		System.out.println(result);
		// 회원가입, 회원추가, 로그인, 로그아웃등은 sendRedirect로
		// prefix redirect:요청할 주소(매핑 주소)
		// jsp는 WEB-INF안에 있기때문에 직접호출할 수 없음
		// return "redirect:/demo/demo.do";
		
		m.addAttribute("msg",result>0?"저장성공":"저장실패");
		m.addAttribute("loc", "/demo/demo.do");
		
		return "common/msg";
		
		// dispatcher로 가는 방법
		/* return "demo/demo"; */
	}
	
	@RequestMapping("/demo/selectDemoAll.do")
	public String selectDemoAll(Model m) { 
		List<Demo> demos = service.selectDemoAll();
	
		m.addAttribute("demos", demos);
		
		demos.forEach(System.out::println);
		
		

		
		return "demo/demoList";
	}
	
	
	// 간편하게 사용할 수 있게 Mapping 어노테이션을 지원
	//@GetMapping
	//@PostMapping
	//@DeleteMapping
	//PutMapping
	
	// mapping 주소를 설정할 때 {}를 사용할 수 있음
	// /board/boardView?no=1
	// /board/1 method=GET   
	// /board/ method=GET 
	@GetMapping("/demo/{no}")
	public String searchDemo(@PathVariable(value ="no") int no) {
		// 데이터 조회 : get방식
		// 데이터 저장 : post방식
		System.out.println(no);
		return "demo/demoResult";
	}
	
	
	
}
