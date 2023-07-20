package com.bs.spring.demo.model.dto;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Demo {
	private Long devNo;
	private String devName;
	private int devAge;
	private String devGender;
	private String devEmail;
	private String[] devLang;
	private Date birthDay;  // sql Date로 import 하면 파싱됨   util Date는 파싱이안됨
}
