package com.demo.interceptor;

import com.jfinal.aop.Before;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.demo.interceptor.AaaInter;

public class DemoInterceptor implements Interceptor {
    
	// Invocation 作为 Interceptor 接口 intercept 方法中的唯一参数，提供了很多便利的方法在拦截器中使用
	
	@Override
	public void intercept(Invocation inv) {
		System.out.println("Before method invoking");
		// inv.invoke()这一行代码是对目标方法的调用，在这一行代码的前后插入切面代码可以很方便地实现 AOP
		// 必须调用 inv.invoke() 方法， 才能将当前调用传递到后续的 Interceptor 与 Action.
		inv.invoke();
		System.out.println("After method invoking");
	}
	
//	// 配置一个Class级别的拦截器，她将拦截本类中的所有方法
//	@Before(AaaInter.class)
//	public class BlogController extends Controller {
//		// 配置多个Method级别的拦截器， 仅拦截本方法
//		@Before({BbbInter.class, CccInter.class})
//		public void index() {
//		}
//		// 未配置Method级别拦截器，但会被Class级别拦截器AaaInter所拦截
//		public void show() {
//		}
//	}
	
	/**
	 * 全局拦截器以及 Inject 拦截器（Inject拦截将在后面介绍），
	 * 全局拦截器分为控制层全局拦截器与业务层全局拦截器，前者拦截控制层所有 Action 方法，后者拦截业务层所有方法
	 */
	
	/**
	 * Clear
	 * 拦截器从上到下依次分为 Global、 Inject、 Class、 Method 四个层次， Clear 用于清除自身所处层次以上层的拦截器。
     * Clear 声明在 Method 层时将针对 Global、 Inject、 Class 进行清除。 Clear 声明在 Class 层时将针对 Global、 Inject 进行清除。 Clear 注解携带参数时清除目标层中指定的拦截器。
     * Clear 用法记忆技巧：
   * 		共有 Global、 Inject、 Class、 Method 四层拦截器
   * 		清除只针对 Clear 本身所处层的向上所有层，本层与下层不清除
   * 		不带参数时清除所有拦截器，带参时清除参数指定的拦截器
	 */

}
