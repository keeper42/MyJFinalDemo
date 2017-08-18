package com.demo.service;

import com.demo.model.Blog;
import com.jfinal.plugin.activerecord.Page;

/**
 * BlogService
 * 所有 sql 与业务逻辑写在 Service 中，
 * 如果项目规模大并且复杂度高可以引入 Service 层，否则所有业务写在 Model 中就可以
 * @author LJF
 */
public class BlogService {
	
	// 所有dao对象也放在Service中
	private static final Blog dao = new Blog().dao();
	
	public Blog findById(int id) {
		return dao.findById(id);
	}
	
	public void deleteById(int id) {
		dao.deleteById(id);
	}
	
	public Page<Blog> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize,  "select *", "from blog order by id asc");
	}
	
}
