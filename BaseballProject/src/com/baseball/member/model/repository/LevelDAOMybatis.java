package com.baseball.member.model.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LevelDAOMybatis implements LevelDAO{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectAll() {
		
		List list = sqlSessionTemplate.selectList("Level.selectAll");
		
		return list;
	}

}
