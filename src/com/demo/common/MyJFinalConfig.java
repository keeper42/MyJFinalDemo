package com.demo.common;

import com.demo.router.FrontRoutes;

import org.eclipse.jetty.server.Authentication.User;

import com.demo.handler.ResourceHandler;
import com.demo.interceptor.AuthInterceptor;
import com.demo.router.AdminRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class MyJFinalConfig extends JFinalConfig{
	
	public void configRoute(Routes me) {
		me.add(new FrontRoutes());
		me.add(new AdminRoutes());
	}

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		me.setDevMode(true);
	}

	@Override
	public void configEngine(Engine me) {
		// 向模板引擎中添加了三个定义了共享函数的模板文件
		me.addSharedFunction("/_view/common/__layout.html");
		me.addSharedFunction("_view/common/_paginate.html");
		me.addSharedFunction("/_view/_admin/common/__admin_layout.html");
	}

	@Override
	public void configPlugin(Plugins me) {
		// 以下代码配置了 Druid 数据库连接池插件与 ActiveRecord数据库访问插件。
		// 通过以下的配置， 可以在应用中使用 ActiveRecord 非常方便地操作数据库
		loadPropertyFile("your_app_config.txt");
		DruidPlugin dp = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);
		arp.addMapping("user", (Class<? extends Model<?>>) User.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 此方法用来配置 JFinal 的全局拦截器，全局拦截器将拦截所有 action 请求，
		// 除非使用@Clear 在 Controller 中清除
		me.add(new AuthInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// 配置了名为 ResourceHandler的处理器，Handler
		// 可以接管所有 web 请求，并对应用拥有完全的控制权
		me.add(new ResourceHandler());
	}
	
}
