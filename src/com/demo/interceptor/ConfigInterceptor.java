package com.demo.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.Interceptors;
import com.jfinal.plugin.activerecord.tx.*;

public class ConfigInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		
	}
	
	// 声明式事务
    // MySql 数据库表必须设置为 InnoDB 引擎时才支持事务， MyISAM 并不支持事务。
	public void configInterceptor(Interceptors me) {
		me.add(new TxByMethodRegex("(.*save.*|.*update.*)"));
		me.add(new TxByMethods("save", "update"));
		
		me.add(new TxByActionKeyRegex("/trans.*"));
		me.add(new TxByActionKeys("/tx/save", "/tx/update"));
		
	}
	
}
