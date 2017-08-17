	package com.demo.controller;

import java.sql.SQLException;
import java.util.List;

import com.demo.interceptor.AuthInterceptor;
import com.demo.model.Blog;
import com.demo.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;

@Before(AuthInterceptor.class)
public class UserController extends Controller {
	
	/**@ActionKey注解, 对注解不理解...
	 * 在使用了@ActionKey("/login")注解以后， 
	 * actionKey 由原来的"/user/login"变为了"/login"。
	 * 该注解还可以让 actionKey 中使用减号或数字等字符，如"/user/123-456"
	 */
//	@ActionKey("/login")
//	public void login() {
//		render("login.html");
//	}
	
	// AuthInterceptor 已被Clear清除掉，不会被其拦截
	@Clear
	public void login() {
		
	}
	
	// 此方法将被AuthInterceptor拦截
	public void show() {
		
	}	
	
	/** 
	 * User 中定义的 public static final User dao 对象是全局共享的， 
	 * 只能用于数据库查询，不能用于数据承载对象。 数据承载需要使用 new User().set(…)来实现。
	 */
	public void dao() {
		User user = getModel(User.class);
		
		// 创建name属性为James,age属性为25的User对象并添加到数据库
		user.set("name", "James").set("age",25).save();
		
		// 删除id值为25的User
		user.dao.deleteById(25);
		
		// 查询id值为25的User将其name属性改为James并更新到数据库
		user.dao.findById(25).set("name", "James").update();
		// 查询id值为25的user, 且仅仅取name与age两个字段的值
		User user2 = User.dao.findByIdLoadColumns(25, "name, age");
		// 获取user的name属性
		String userName = user2.getStr("name");
		// 获取user的age属性
		Integer userAge = user2.getInt("age");
		// 查询所有年龄大于18岁的user
		List<User> users = user.dao.find("select * from user where age>18");
		// 分页查询年龄大于18的user,当前页号为1,每页10个user
		Page<User> userPage = User.dao.paginate(1, 10, "select *", "from userwhere age > ?", 18);
	}
	
	/**
	 * 使用Db 与 Record 类时，无需对数据库表进行映射， Record 相当于一个通用的 Model。 
	 */
	public void record() {
		// 创建name属性为James,age属性为25的record对象并添加到数据库
		Record user = new Record().set("name", "James").set("age", 25);
		Db.save("user", user);
		
		// 删除id值为25的user表中的记录
		Db.deleteById("user", 25);
		// 查询id值为25的Record将其name属性改为James并更新到数据库
		user = Db.findById("user", 25).set("name", "James");
		Db.update("user", user);
		// 获取user的name属性
		String userName = user.getStr("name");
		// 获取user的age属性
		Integer userAge = user.getInt("age");
		
		// 查询所有年龄大于18岁的user
		List<Record> users = Db.find("select * from user where age > 18");
		
		// 分页查询年龄大于18的user,当前页号为1,每页10个user
		Page<Record> userPage = Db.paginate(1, 10, "select *", "from user whereage > ?", 18); // SqlPara
		
		// 两次数据库更新操作在一个事务中执行，如果执行过程中发生异常或者 run()方法返回 false，则自动回滚事务。
		boolean succeed = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int count = Db.update("update account set cash = cash - ? where id = ?", 100, 123);
				int count2 = Db.update("update account set cash = cash + ? where id = ?", 100, 345);
				return count == 1 && count2 == 1;
			}
		});
	}
	
	/**
	 * paginate 分页支持
	 */
	public void paginate() {
		 // Model 与 Db 中提供了四种 paginate 分页 API
		 // 第一种是： paginate(int pageNumber, intpageSize, String select, String sqlExceptSelect, Object... paras)，
		 // 其中的参数含义分别为： 当前页的页号、每页数据条数、 sql 语句的 select 部分、 sql 语句除了 select 以外的部分、查询参数。绝大多数情况下使用这个 API 即可。
		Db.paginate(1, 10, "select*", "from girl where age > ? and weigth < ?", 18, 50);
		
		// 第二种是： paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select, StringsqlExceptSelect, Object... paras)，
		// 相对于第一种仅仅多了一个 boolean isGroupBySql 参数
		// isGroupBy 参数只有在最外层 sql 具有 group by 子句时才能为true 值，嵌套 sql 中仅仅内层具有 group by 子句时仍然要使用 false
		Db.paginate(1, 10, true, "select*", "from girl where age > ? group by age");
		
		// 第三种是： paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql,Object... paras)，
		// 相对于第一种是将查询总行数与查询数据的两条 sql 独立出来，这样处理主要是应对具有复杂 order by 语句或者 select 中带有 distinct 的情况
	    String from = "from girl where age > ?";
	    String totalRowSql = "select count(*)" + from;
	    String findSql = "select * " + from + " order by age";
	    Db.paginateByFullSql(1, 10, totalRowSql, findSql, 18);
	    
	    // 第四种是： paginate(int pageNumber, int pageSize, SqlPara sqlPara)， 用于配合 sql 管理功能使用
	}
	
	
	/**
	 * 声明式事务， 声明式事务需要使用 ActiveRecordPlugin 提供的拦截器来实现
	 * 声明了一个 Tx 拦截器即为 action 添加了事务支持。除此之外 ActiveRecord
     * 还配备了 TxByActionKeys、 TxByActionKeyRegex、 TxByMethods、 TxByMethodRegex，分别
     * 支持 actionKeys、 actionKey 正则、 actionMethods、 actionMethod 正则声明式事务
	 */
	@Before(Tx.class)
	public void trans_demo() {
		
		// 获取转账金额
		Integer transAmount = getParaToInt("transAmount");
		
		// 获取转出账户id
		Integer fromAccountId = getParaToInt("fromAccountId");
		
		// 获取转入账户id
		Integer toAccountId = getParaToInt("toAccountId");
		
		// 转出操作
		Db.update("update account set cash = cash - ? where id = ?", transAmount, fromAccountId);
		
		// 转入操作
		Db.update("update account set cash = cash + ? where id = ?", transAmount, fromAccountId);
	}
	
    // Cache
//	public void list() {
//		List<Blog> blogList = ((Model<Blog>) Blog.dao).findByCache("cacheName", "key", "select * from blog");
//		setAttr("blogList", blogList).render("list.html");
//	}
	
	// 表关联操作 join
	
	// 复合主键: 当一个字段无法确定唯一性的时候，需要其他字段来一起形成唯一性。就是说用来组成唯一性的字段如果有多个就是联合主键
//	public void compositeKey() {
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//		// 多数据源的配置仅仅是如下第二个参数指定一次复合主键名称
//		arp.addMapping("user_role", "userId, roleId", UserRole.class);
//		//同时指定复合主键值即可查找记录
//		UserRole.dao.findById(123, 456);
//	}
	
	
	
}
