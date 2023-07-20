package com.bs.spring.common.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.spring.demo.controller.DemoController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor{
	
	// alt + shift + s + v 로 추상메소드 선택해서 구현가능
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { // 매핑메소드가 실행되기전에 실행되는 로직을 작성
		
		// 반환형이 boolean으로 true 반환하면 매핑메소드가 실행, false반환하면 매핑메소드 실행을 하지 않음
		log.debug("----- 인터셉터 prehandle 실행 ----");
		log.debug(request.getRequestURI());
		Map params = request.getParameterMap(); // 파라미터 값들은 Map으로 만듬
		for(Object key : params.keySet()) { // 파라미터 키값들도 알 수 있음  
			System.out.println(key);
		}
		log.debug("-----------------------------");
			
		// handler -> 실행되는 controller 클래스, 실행되는 메소드확인
		HandlerMethod hm = (HandlerMethod)handler;
		log.debug("{}",hm.getBean()); 
		DemoController demo = (DemoController)hm.getBean();
		//demo.함수명  // 다른메소드로 전환가능
		
		log.debug("{}",hm.getMethod());
		Method m=hm.getMethod();
		
		return true;  // false일 경우 -> 그 페이지로 가지지 않음
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {  // 매핑메소드가 실행된 후에 실행되는 로직을 작성
		
		log.debug("------ 인터셉터 postHandle ------");
		log.debug("{}",modelAndView.getViewName()); // 화면 view 이름 출력
		
		Map modelData = modelAndView.getModel();
		log.debug("{}",modelData);  // 전달받은 데이터 출력
		log.debug("------------------------------");
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception{ // 응답처리가 끝난 후 실행되는 로직을 작성
		log.debug("----- 응답 후 인터셉터 실행 -----");
		log.debug("요청주소{} : ",request.getRequestURI());
		log.debug("에러메세지 {} ",ex!=null?ex.getMessage():"응답성공");
		log.debug("-------------------------");
	}
	
	
}
