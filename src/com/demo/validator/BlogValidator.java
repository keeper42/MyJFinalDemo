package com.demo.validator;

import com.demo.model.Blog;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BlogValidator extends Validator {

	@Override
	protected void handleError(Controller c) {
		validateRequiredString("blog.title", "titleMsg", "请输入Blog标题!");
		validateRequiredString("blog.content", "contentMsg", "请输入Blog内容!");
	}

	@Override
	protected void validate(Controller c) {
		c.keepModel(Blog.class);
		
		String actionKey = getActionKey();
		if(actionKey.equals("/blog/save")) {
			c.render("add.html");
		}
		if(actionKey.equals("/blog/update")) {
			c.render("edit.html");
		}
	}
	
}
