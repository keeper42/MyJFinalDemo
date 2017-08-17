package com.demo.router;

import com.demo.controller.BlogController;
import com.demo.controller.IndexController;
import com.demo.interceptor.FrontInterceptor;
import com.jfinal.config.Routes;

public class FrontRoutes extends Routes {
	
	public void config() {
		setBaseViewPath("/view/front");
		
		// 这里有个bug
//		add("/", IndexController.class);
//		add("/blog", BlogController.class);
	}
	
}
	