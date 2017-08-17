package com.demo.controller;

import com.demo.model.Blog;
import com.jfinal.core.Controller;

public class BlogController extends Controller {

	public void save() {
		// 页面的modelName正好是Blog类名的首字母小写
		Blog blog = getModel(Blog.class);
		
		// 如果表单域的名称为 "otherName.title"可加上一个参数来获取
		blog = getModel(Blog.class, "otherName");
		
		// 如果希望传参时避免使用 modelName 前缀 ，可以使用空串作为modelName 来实现： getModel(Blog.class, “”);
	}
	
}
