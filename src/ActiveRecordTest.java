import com.demo.model.Blog;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;

public class ActiveRecordTest {
	
	public static void main(String[] args) {
		DruidPlugin dp = new DruidPlugin("localhost", "username", "password");
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.addMapping("blog", Blog.class);
		
		// 与web环境唯一的不同是要手动调用一次相关插件的start()方法
		dp.start();
		arp.start();
		
		// 通过上面简单的几行代码，即可立即开始使用
        new Blog().set("title", "title").set("content", "cxt text").save();
        ((Model<Blog>) Blog.dao).findById(123);		
	}
	
}
