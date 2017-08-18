package com.demo.config;

import com.demo.controller.BlogController;
import com.demo.controller.IndexController;
import com.demo.model.Blog;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

// Created by LJF on 2017/08/18

public class DemoConfig extends JFinalConfig {
	
	/**
	 * 运行main可以启动项目
	 * @param args
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 8081, "/", 5);
	}
	
	/**
	 * 配置常量, 加载必要配置，如数据库配置文件
	 */
	@Override
	public void configConstant(Constants me) {
		// 加载必要的配置， 随后可用PropKit.get(...)取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}
	
	/**
	 * 配置路由， render
	 */
	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index");
		me.add("/blog", BlogController.class);
	}
	
	/**
	 * 配置模板引擎
	 */
	@Override
	public void configEngine(Engine me) {
		me.addSharedFunction("/common/_layout.html");
		me.addSharedFunction("/common/_paginate.html");
	}

	/**
	 * 配置插件，如数据库连接池等插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置c3p0数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件, activerecord还需将c3po插件的初始化
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.addMapping("blog", "id", Blog.class);
		me.add(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
	}

	@Override
	public void configHandler(Handlers me) {
	}
	
}
