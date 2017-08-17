package com.demo.controller;

import com.jfinal.core.Controller;

public class HelloController extends Controller {
	
	public void index() {
		renderText("Hello JFinal World!");
	}
	
	public String test() {
		renderHtml("<!DOCTYPE html><html lang='en'><body>Hello world!</body></html>");
		return null;
	}
	
	/**
	 *  getPara
	 *  第一个参数为 String 类型的将获取表单或者 url 中问号挂参的域值。
	 *  第一个参数为int 或无参数的将获取 urlPara 中的参数值。
	 *	getPara 使用例子: 
	 *	     方法调用                                                     返回值
	 *	getPara("title")     返回页面表单域名为“title" 参数值
	 *	getParaToInt("age")  页面表单域名为“age"的参数值并转为 int 型
	 *	getPara(0)           返回 url 请求中的 urlPara 参数的第一个值，如http://localhost/controllerKey/method/v0-v1-v2 这个请求将返回"v0"
	 *	getParaToInt(1)      返回 url 请求中的 urlPara 参数的第二个值并转换成int 型，如 http://localhost/controllerKey/method/2-5-9这个请求将返回 5
	 *	getParaToInt(2)      如 http://localhost/controllerKey/method/2-5-N8 这个请求将返回 -8。 注意：约定字母 N 与 n 可以表示负号，这对 urlParaSeparator 为 “-" 时非常有用。
	 *	getPara()            返回 url 请求中的 urlPara 参数的整体值，如http://localhost/controllerKey/method/v0-v1-v2 这个请求将返回"v0-v1-v2"
	 */
	
	/**
	 * getBean 与 getModel
	 * getBean 与 getModel 区别在于前者使用数表字段名而后者使用与 setter 方法一致的属性名进行数据注入。 建议优先使用 getBean 方法
	 */
	
	/**
	 * setAttr
	 * setAttr(String, Object)转调了 HttpServletRequest.setAttribute(String, Object)，该方法可以将各种数据传递给 View 并在 View 中显示出来
	 */
	
	/**
	 *  文件上传
	 *  Controller 提供了 getFile 系列方法支持文件上传。 特别注意： 如果客户端请求为 multipart
	 *	request（form 表单使用了 enctype="multipart/form-data"），那么必须先调用 getFile 系列方法才
	 *	能使 getPara 系列方法正常工作，因为 multipart request 需要通过 getFile 系列方法解析请求体中
	 *	的数据，包括参数。 同样的道理在 Interceptor、 Validator 中也需要先调用 getFile.
	 */
	
	/**
	 * 文件下载
	 * renderFile
	 */
	
	/**
	 * session
	 * setSessionAttr(key, value);
	 * getSession();
	 */
	
	/**
	 *  render
	 *  render 系列方法将渲染不同类型的视图并返回给客户端。
	 *  JFinal 目前支持的视图类型有：JFinal Template、 FreeMarker、 JSP、 Velocity、 JSON、 File、 Text、 Html 等等。
	 *  除了 JFinal 支持的视图型以外， 还可以通过继承 Render 抽象类来无限扩展视图类型。
	 *  render(String view)使用例子:
	 *  方法调用 描述
		render(”test.html”)              渲染名为 test.html 的视图，该视图的全路径为”/path/test.html”
		render(”/other_path/test.html”)  渲染名为 test.html 的视图，该视图的全路径为”/other_path/test.html”，即当参数以”/”开头时采用绝对路径。
		renderTemplate(”test.html”)      渲染名为 test.html 的视图，且视图类型为 JFinalTemplate。
		renderFreeMarker(”test.html”)    渲 染 名 为 test.html 的 视 图 ， 且 视 图 类 型 为FreeMarker。
		renderJsp(”test.html”)           渲染名为 test.html 的视图，且视图类型为 Jsp。
		renderVelocity(“test.html”)      渲染名为 test.html 的视图，且视图类型为 Velocity。
		renderJson()                     将所有通过 Controller.setAttr(String, Object)设置的变量转换成 json 数据并渲染。
		renderJson(“users”, userList)    以”users”为根，仅将 userList 中的数据转换成 jsonhttp://www.jfinal.com数据并渲染。
		renderJson(user)                 将 user 对象转换成 json 数据并渲染。
		renderJson(“{\”age\”:18}” )      直接渲染 json 字符串。
		renderJson(newString[]{“user”, “blog”})  仅将 setAttr(“user”, user)与 setAttr(“blog”, blog)设置的属性转换成 json 并渲染。使用 setAttr 设置的其它属性并不转换为 json。
		renderFile(“test.zip”);          渲染名为 test.zip 的文件，一般用于文件下载
		renderText(“Hello JFinal”)       渲染纯文本内容”Hello JFinal”。
		renderHtml(“Hello Html”)         渲染 Html 内容”Hello Html”。
		renderError (404 , “test.html”)  渲染名为 test.html 的文件， 且状态为 404。
		renderError (500 , “test.html”)  渲染名为 test.html 的文件，且状态为 500。
		renderNull()                     不渲染，即不向客户端返回数据。
		render(new XmlRender())          使用自定义的 XmlRender 来渲染。
	 */
	
}
