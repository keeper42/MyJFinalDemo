package com.demo.router;

import com.demo.controller.AdminController;
import com.demo.controller.UserController;
import com.demo.interceptor.AdminInterceptor;
import com.demo.interceptor.AuthInterceptor;
import com.jfinal.config.Routes;

public class AdminRoutes extends Routes {
	
	/**
	 *  FrontRoutes 与 AdminRoutes 中 分 别 使 用 setBaseViewPath(…) 设 置 了 各 自
     *  Controller.render(view)时使用的 baseViewPath。
	 *	AdminRoutes 还通过 addInterceptor(new AdminInterceptor())添加了 Routes 级别的拦截
	 *	器，该拦截器将拦截 AdminRoutes 中添加的所有 Controller，相当于业务层的 inject 拦截器，
	 *	会在 class 拦截器之前被调用。这种用法可以避免在后台管理这样的模块中的所有 class 上使用
	 *	@Before(AdminInterceptor.class)，减少代码冗余。
	 */

	public void config() {
		// Routes 级别拦截器
		addInterceptor(new AuthInterceptor());
		
		setBaseViewPath("/view/amdin");
		addInterceptor(new AdminInterceptor());
		add("/admin", AdminController.class);
		add("/admin/user", UserController.class);
	}
	
}
