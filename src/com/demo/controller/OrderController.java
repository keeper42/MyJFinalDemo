package com.demo.controller;

import com.demo.service.OrderService;
import com.jfinal.core.Controller;

public class OrderController extends Controller {
    
	public void payment() {
		// 使用 enhance方法对业务层进行增强，使其具有AOP能力
		OrderService service = enhance(OrderService.class);
		
		// 调用payment方法将会触发拦截器
		service.payment(getParaToInt("orderId"), getParaToInt("userId"));
	}
	
}
