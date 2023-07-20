package com.bs.spring.common.aop;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerAspect {

	public void loggerBefore(JoinPoint jp) {
		log.debug("----- AOP loggerBefore -----");
		
		Signature sig = jp.getSignature();
		log.debug(sig.getDeclaringTypeName()+" "+sig.getName());
		
		Object[] arg = jp.getArgs(); // 메소드가 실행될 때 전달되는 매개변수의 인수값을 가져옴
		if(arg!=null) {
			for(Object o : arg) {
				log.debug("{}",o);
				if(o instanceof Map) {
					((Map)o).put("test", "째훈째훈");
				}
			}
		}
		log.debug("----------------------------");	
	}
	
	public void loggerAfter(JoinPoint jp) {
		log.debug("----- AOP loggerAfter -----");
		
		Signature sig = jp.getSignature();
		log.debug(sig.getDeclaringTypeName()+" "+sig.getName());
		
		log.debug("----------------------------");	
	}
	
	
	
}
