package com.zq.www.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.zq.www.mis.dao.NewsDAO;

//测试dao
//1.打断点，调试
//2.写测试类
public class test2 {
	//加载配置文件
	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml","hibernate-spring.xml");
	
	
	//默认命名 NewsDAO->newsDAO，
    //也可自定义命名 @Repository(value="mydao")
	NewsDAO dao = ctx.getBean("newsDAO", NewsDAO.class);  
	
	
	@Test
	public void test() {
		System.out.println(dao.listall().size());
	}
}
