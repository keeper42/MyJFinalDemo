package com.demo.common;

import com.demo.model.Blog;
import com.demo.model.User;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class MultiDataResourceConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		
	}

	@Override
	public void configRoute(Routes me) {
		
	}

	@Override
	public void configEngine(Engine me) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// 1. mysql 数据源
		DruidPlugin dsMysql = new DruidPlugin("jdbc:mysql://localhost/db_name","userName", "password");
		me.add(dsMysql);
		
		// mysql ActiveRecordPlugin 实例， 并制定configName为mysql
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql", dsMysql);
		me.add(arpMysql);
		arpMysql.setCache(new EhCache());
		arpMysql.addMapping("user", User.class);
			
		// 2. oracle 数据源
		DruidPlugin dsOracle = new DruidPlugin("jdbc:mysql://localhost/db_name","userName", "password");
		me.add(dsOracle);
		// oracle ActiveRecrodPlugin 实例，并指定configName为 oracle
		ActiveRecordPlugin arpOracle = new ActiveRecordPlugin("oracle", dsOracle);
		me.add(arpOracle);
		arpOracle.setDialect(new OracleDialect());
		arpOracle.setTransactionLevel(8);
		arpOracle.addMapping("blog", Blog.class);
	}
	
	/**
	 * 只有在同一个 Model 希望对应到多个数据源的 table 时才需要使用 use 方法，如果同一个 Model 唯一对应一个数据源的一个 table，那么数据源的切换是自动的，无需使用 use方法。
     * 对于 Db + Record 的使用， 数据源的切换需要使用 Db.use(cnfigName)方法得到数据库操作对象，然后就可以进行数据库操作了
	 */
	public void multiDsModel() {
		// 默认使用arp.addMapping(...)时关联起来的数据源
		Blog blog = ((Model<Blog>) Blog.dao).findById(123);
		
		// 只需调用一次use方法即可切换到另一数据源去
		blog.use("backupDatabase").save();
		
	}
	
	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	@Override
	public void configHandler(Handlers me) {
		
	}
	
}
