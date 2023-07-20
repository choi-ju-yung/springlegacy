package com.bs.spring.demo.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.bs.spring.demo.model.dto.Demo;

public interface DemoDao {
	int insertDemo(SqlSessionTemplate session, Demo m);
	List<Demo> selectDemoAll(SqlSessionTemplate session);
}
