package com.bs.spring.beantest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal {
	private String name;
	private int age;
	private double height;
	
	public Animal(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
}
