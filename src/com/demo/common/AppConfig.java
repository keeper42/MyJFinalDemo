package com.demo.common;

import com.demo.interceptor.GlobalActionInterceptor;
import com.demo.interceptor.GlobalServiceInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;

public class AppConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		// 第一次使用use加载的配置将成为主配置，可以通过PropKit.get(...)直接取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode"));
	}

	@Override
	public void configRoute(Routes me) {
		
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * PropKit 工具类用来操作外部配置文件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 非第一次使用use加载的配置，需要通过每次使用use来指定配置文件名再来取值
		String redisHost = PropKit.use("redis_config.txt").get("host");
		int redisPort = PropKit.use("redis_config.txt").getInt("port");
		RedisPlugin rp = new RedisPlugin("myRedis", redisHost, redisPort);
		me.add(rp);
		
		// 非第一次使用 use加载的配置，也可以先得到一个Prop对象，再通过该对象来获取值
		Prop p = PropKit.use("db_config.txt");
		DruidPlugin dp = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password"));
		
	}
	

	/**
	 * 全局拦截器以及 Inject 拦截器（Inject拦截将在后面介绍），
	 * 全局拦截器分为控制层全局拦截器与业务层全局拦截器，前者拦截控制层所有 Action 方法，后者拦截业务层所有方法
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// 添加控制层全局拦截器
		me.addGlobalActionInterceptor(new GlobalActionInterceptor());
		
		// 添加业务层全局拦截器
		me.addGlobalServiceInterceptor(new GlobalServiceInterceptor());
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

}
