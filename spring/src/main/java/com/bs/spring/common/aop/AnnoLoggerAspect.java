package com.bs.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component  // 클래스를 빈으로 등록
@Aspect   
public class AnnoLoggerAspect { // 어노테이션 방식으로 하는 방식
	
	
	// pointcut 설정
	@Pointcut("within(com.bs.spring.member..*)")
	public void loggerTest() {}
	
	// advisor 설정
	@Before("loggerTest()") // member 패키지 밑에 모든 함수들을 호출하기전에 발동
	public void loggerBefore(JoinPoint jp) {
		log.debug("======= anootation aop =======");
		Signature sig = jp.getSignature();
		log.debug(sig.getDeclaringTypeName()+" "+sig.getName());
		log.debug("==============================");
	}
	
	
	@Pointcut("execution(* com.bs.spring.memo..*(..))")
	public void memoLogger() {}
	
	
	@After("memoLogger()")
	public void afterLogger(JoinPoint jp) {
		log.debug("======= anootation aop after =======");
		Signature sig = jp.getSignature();
		log.debug(sig.getDeclaringTypeName()+" "+sig.getName());
		log.debug("=============aop after =================");
	}
	
	
	// Around -> 메소드 실행 전,후에 특정 로직을 실행할 때 사용
	// 실행하는 시간 체킹할때 많이 사용함
	// 반환형 : object, 매개변수타입 ProceedingJoinPoint
	@Around("execution(* com.bs.spring..*DaoImpl.*(..))")
	public Object daoLogger(ProceedingJoinPoint join) throws Throwable{
		// 전, 후 로직은 한번에 설정할 수 있다
		// 전, 후를 구분하는 구문은 ProceedingJoinPoint 클래스가 제공하는 proceed() 메소드를 이용
		// proceed() 메소드가 호출한 다음 라인은 후 처리, 그 전 라인은 전처리
		// proceed() 메소드는 Object를 반환한다
		
		// 메소드 실행시간 체크하기
		StopWatch stop = new StopWatch();
		stop.start();
		
		log.debug("================== around logger dao before =====================");
		log.debug("-------- 전처리 내용 구현 ---------");
		log.debug("==========================================================");
		Signature sig = join.getSignature();
		String classMethod = sig.getDeclaringType().getName()+sig.getClass();
		
		// 여기까지가 전처리 내용
		
		Object obj = join.proceed(); // proceed 메소드를 기준으로 전,후 나눔
		stop.stop();
		log.debug("================== around logger dao afetr =====================");
		log.debug("-------- 후처리 내용 구현 ---------");
		log.debug("실행시간 : "+stop.getTotalTimeMillis()+"ms");
		log.debug("==========================================================");
		
		return obj;  // 반환값은 따로 안쓰이며, 내부적으로 쓰임
	}
	
	
	// 에러가 발생했을때 발동
	@AfterThrowing(pointcut = "loggerTest()", throwing="e")
	public void afterThrowinglogger(JoinPoint jo, Throwable e) {
		log.debug("에러발생!!!");
		Signature sig = jo.getSignature();
		log.debug("{}",sig.getDeclaringTypeName()+" "+sig.getName());
		log.debug("{}",e.getMessage());
		StackTraceElement[] stacktrace = e.getStackTrace();
		for(StackTraceElement element : stacktrace) {
			log.debug("{}",element);
		}
		
	}
	
	
	
	
}
