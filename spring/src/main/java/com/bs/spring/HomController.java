package com.bs.spring;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.spring.beantest.Animal;
import com.bs.spring.beantest.Employee;
import com.bs.spring.beantest.FuntionalTest;
import com.bs.spring.include.TargetComponent;

@Controller // 스프링 빈으로 등록
public class HomController {
	
	// springbean으로 등록된 객체는 필드로 가져와 사용할 수 있음
	// 타입을 찾아서 집어넣음 
	// 문제점 : Animal 타입이 두개이면 NoUniqueBean 예외 발생
	// 해결방법 (1) : 동일한 타입이면, 필드명과 Id값을 비교해서 찾음
	// 해결방법 (2) : @Qualifier 어노테이션을 이용해서 특정 bean을 선택
	@Autowired  
	// 중복된 타입이 있는 경우 @Qualifier 어노테이션을 이용해서 특정 bean을 선택할 수 있음
	@Qualifier("dog")
	private Animal a;
	
	@Autowired
	@Qualifier("bbo")
	private Animal b;
	
	//springBean으로 등록되지않은 객체를 Autowired 하면?
	// 오류 발생! -> 등록되지은 bean은 Autowired 하지못함
	@Autowired(required = false)
	// required = false -> 빈에 있으면 넣고 빈에 없으면 넣지말라는 뜻 (오류발생 x)
	// 많이 사용하는 방식은 아님
	private Employee emp;
	
	@Autowired
	private Employee emp2;
	
	// java로 등록한 bean 가져오기
	@Autowired
	@Qualifier("ani")
	private Animal c;
	
	@Autowired
	@Qualifier("sol")
	private Employee sol;
	
	@Autowired
	List<Animal> animals;
	
	@Autowired
	private TargetComponent tc; 
	
	// @어노테이션으로 bean 등록
	@Autowired
	private FuntionalTest ft;
	
	// basepackage 외부에 있는 @Component
	@Autowired
	private Test test;
	
	
	@RequestMapping("/test")
	public String home() { // home() 메소드는 서블릿의 do get 역할을함
		System.out.println(a);
		System.out.println(b);
		System.out.println(emp);
		System.out.println(emp2);
		System.out.println(c);
		System.out.println(sol);
		animals.forEach(System.out::println);
		System.out.println(tc);
		System.out.println("functionalTest출력");
		System.out.println(ft);
		System.out.println(ft.getA());
		
		
		// spring에서 파일을 불러올 수 있는 Resource 객체를 제공
		Resource resource = new ClassPathResource("mydata.properties");
		
		try {
			Properties prop = PropertiesLoaderUtils.loadProperties(resource);
			System.out.println(prop);
			resource = new FileSystemResource("D:\\springwork\\spring\\src\\main\\resources\\test.txt");
			Files.lines(Paths.get(resource.getURI()),Charset.forName("UTF-8")).forEach(System.out::println);
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "index";
	}
}
