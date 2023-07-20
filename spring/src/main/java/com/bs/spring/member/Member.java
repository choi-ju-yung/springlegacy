package com.bs.spring.member;

import java.sql.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@NotEmpty(message = "아이디는 반드시 입력하세요!")
	@Size(min=4, message = "아이디는 4글자 이상 입력하세요") // 최소 4글자
	private String userId;
	
	@Pattern(regexp = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[~!@#$%^&*()])[a-zA-Z~!@#$%^&*()]{8,}"
			,message = "비밀번호는 영소문자, 대문자, 특수기호 ~!@#$%^&*()를 포함하고 8글자 이상 작성하세요")
	private String password;
	
	private String userName;
	
	private String gender;
	
	@Min(value=14, message = "14이상 입력하세요")@Max(value=150, message = "150살 이상은 없으요") // 최소 14살, 최대 150살
	private int age;
	
	@Email
	private String email;
	
	@NotEmpty
	private String phone;
	
	private String address;
	
	private String[] hobby;
	
	
	private Date enrollDate;
}
