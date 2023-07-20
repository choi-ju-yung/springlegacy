package com.bs.spring.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bs.spring.beantest.Animal;
import com.bs.spring.beantest.Department;
import com.bs.spring.beantest.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

//클래스 방식으로 bean 등록해서 사용하기
//pojo 클래스를 configuration으로 사용할 수 있음 -> @Configuration 어노테이션 이용


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bs.spring",
		includeFilters = {@ComponentScan.Filter(
								type = FilterType.REGEX,
								pattern = {"com.bs.spring.include.*"})},// 어노테이션 표시가 없더라도 해당이되면 bean으로 등록함,
		excludeFilters = {} // excludeFilters -> 등록된거 제외시키는거
		) // 기본베이스 패키지는 com.bs.spring이라는 뜻

// @Import() // 다른 configuration 을 가져와 처리하는 것

public class BeanTestConfiguration {
	// springbeanconfiguration.xml과 동일한 기능
	// spring에서 사용할 bean을 자바코드로 등록할 수 있다
	// @Bean 어노테이션을 이용
	// 메소드선언을 통해 등록함
	@Bean
	@Order(1) // Order을 통해 bean의 우선순위를 설정할 수 있다
	public Animal ani() { // 메소드명이 id가되고 반환형은 Animal임
		return Animal.builder()
				.name("킥킥")
				.age(5)
				.height(80)
				.build();
	}
	
	@Bean
	// 등록된 bean에 특정 id값 부여하기
	@Qualifier("sol") // Qualifier 등록시 id값은 sol이됨
	public Employee getEmployee(@Qualifier("sal") Department d) { 
						// 메소드명이 id가되고 반환형은 Employee임
						// @Qualifier("sal") 로 지정
						// 매개변수로만 선언하면, 밑에있는 sal과 Department bean이 겹치기때문에
						// 의존성 주입을 해줌
		return Employee.builder()
				.name("최솔")
				.age(27)
				.address("경기도 안양시")
				.salary(200)
				.dept(d)
				.build();	
	}
	
	@Bean
	public Department sal() { 
		return Department.builder()
				.deptCode(2L)
				.deptTitle("영업부")
				.deptLocation("서울")
				.build();
	}

	@Bean
	public BasicDataSource getDateSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		source.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		source.setUsername("spring");
		source.setPassword("spring");
		return source;
	}
	
	/*
	 * @Bean public Gson gson() { return new Gson(); }
	 */
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper(); 
	}
	
	
}

