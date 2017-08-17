package com.demo.common;

import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

import org.eclipse.jetty.server.Authentication.User;

import com.demo.controller.HelloController;
import com.demo.interceptor.FrontInterceptor;

public class DemoConfig extends JFinalConfig {
	
    /**
     * 此方法用来配置JFinal常量值,devMode是开发模式常量
     * 在开发模式下，JFinal会对每次请求输出报告，如URL、Controller、Method以及所携带的参数
     */
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}
	
	/**
	 * 此方法用来配置 JFinal 访问路由
	 * JFinal 路由规则如下表：
	 *    url组成                                                               访问目标
	 * controllerKey               YourController.index()
	 * controllerKey/method        YourController.method()
	 * controllerKey/method/v0-v1  YourController.method()， 所带 url 参数值为： v0-v1
     * controllerKey/v0-v1         YourController.index()， 所带 url 参数值为： v0-v1
	 */
	public void configRoute(Routes me) {
		// view 以  "/" 字符打头时表示绝对路径
//		me.setBaseViewPath("/view");
//		me.addInterceptor(new FrontInterceptor());
		me.add("/hello", HelloController.class);
	}
	
	// sql 管理模块使用的模板引擎并非在 configEngine(Engine me)配置，
	// 因此在配置 shared method、 directive 等扩展时需要使用 activeRecordPlugin.getEngine()，然后对该 Engine对象进行配置。
	public void configEngine(Engine me) {}
	
	/**
	 * ActiveRecord作为JFinal的Plugin而存在，所以需要使用JFinalConfig中配置ActiveRecordPlugin
	 * DruidPlugin 与 ActiveRecordPlugin， 前者是 druid 数据源插件，后者是 ActiveRecrod 支持插件。
	 * 另外，以上代码中 arp.addMapping("user", User.class)，表的主键名为默认为"id"，如果主键名称为 "user_id"则需要手动指定，如： arp.addMapping("user", "user_id", User.class)。
	 */
	public void configPlugin(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/db_name","userName", "password");
		me.add(dp);
		
		// ActiveReceord 中定义了 addMapping(String tableName, Class<?extends Model> modelClass>)方法，该方法建立了数据库表名到 Model 的映射关系。
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setDialect(new PostgreSqlDialect());
		me.add(arp);
		
		arp.addMapping("user", (Class<? extends Model<?>>) User.class);
//		arp.addMapping("article", "article_id", Article.class);
	}
	
	// Oracle
	public void configPlugin1(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/db_name","userName", "password");
		// 配置Oracle驱动
		dp.setDriverClass("oracle.jdbc.driver.OracleDriver");
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		me.add(arp);
		// 配置Oracle
		arp.setDialect(new OracleDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		arp.addMapping("user", "user_id", (Class<? extends Model<?>>) User.class);
	}
	
	public void sqlManage(Plugins me) {
		DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/db_name","userName", "password");
		me.add(dp);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setBaseSqlTemplatePath(PathKit.getRootClassPath());
		arp.addSqlTemplate("demo.sql");
		arp.addSqlTemplate("all.sql");
//		_MappingKit.mapping(arp);
		me.add(arp);

	}
	
    /** 模板文件中
     *  #sql 指令可以在模板文件中定义 sql 语句， 多数据源之下， 可为不同的 ActiveRecordPlugin 对象分别去配置 sql 模板， 有利于模块化管理
     *  #para 指令用于生成 sql 中的问号占位符以及占位符所对应的参数， #para 指令的另一种用法是传入除了 int 型常量以外的任意类型参数，
     *  #para 指令所在之处永远是生成一个问号占位符，并不是参数的值
	
	 *  select * from article where title like concat('%', ?, '%')
	 *  以上示例的 like 用法完全是 jdbc 决定的， JFinal 仅仅是生成了如下 sql 而已, 也就是仅仅将#para(title)替换生成为一个问号占位 ”?” 而已
	
	 *  #namespace指令为 sql 语句指定命名空间， 不同的命名空间可以让#sql 指令使用相同的key 值去定义 sql, 有利于模块化管理
	 *  
	 *  Sql 管理实现分页paginate功能，在使用#sql 定义 sql 时， 与普通查询完全一样，不需要使用额外的指令，在 java 代码中使用 getSqlPara 得到 SqlPara 对象
	 */
	
	public void configInterceptor(Interceptors me) {}

	public void configHandler(Handlers me) {}
	
	
}
