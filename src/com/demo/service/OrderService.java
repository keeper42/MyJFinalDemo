package com.demo.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

// 定义需要使用的AOP的业务层
public class OrderService {
    // 配置事务拦截器
	@Before(Tx.class) 
	public void payment(int orderId, int userId) {
		// service code here
	}
}
