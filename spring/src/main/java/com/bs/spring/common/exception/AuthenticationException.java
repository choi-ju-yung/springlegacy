package com.bs.spring.common.exception;

public class AuthenticationException extends RuntimeException{
	
	public AuthenticationException(String msg) {
		super(msg);
	}
}
