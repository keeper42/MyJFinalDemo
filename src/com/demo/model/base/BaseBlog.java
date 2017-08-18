package com.demo.model.base;

import java.lang.*;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

// IBean 标记型接口. 标记添加了 getter、setter 方法后的 Model
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBlog<M extends BaseBlog<M>> extends Model<M> implements IBean {
	
	public M setId(Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public Integer getId() {
		return getInt("id");
	}
	
	public M setTitle(String title) {
		set("title", title);
		return (M)this;
	}
	
	public String setTitle() {
		return getStr("title");
	}
	
	public M setContent(String content) {
		set("content", content);
		return (M)this;
	}
	
	public String getContent() {
		return getStr("content");
	}
	
}
