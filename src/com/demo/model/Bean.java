package com.demo.model;

import com.jfinal.plugin.activerecord.Model;

public class Bean extends Model<Bean>{
	
	// Generator 与 JavaBean
	
	/**
	 * Model 与 Bean 合体后主要优势
	 * 充分利用海量的针对于 Bean 设计的第三方工具，例如 jackson、 freemarker
   * 快速响应数据库表变动，极速重构，提升开发效率，提升代码质量
   * 拥有 IDE 代码提示不用记忆数据表字段名，消除记忆负担，避免手写字段名出现手误
   * BaseModel 设计令 Model 中依然保持清爽，在表结构变化时极速重构关联代码
   * 自动化 table 至 Model 映射
   * 自动化主键、复合主键名称识别与映射
   * MappingKit 承载映射代码， JFinalConfig 保持干净清爽
   * 有利于分布式场景和无数据源时使用 Model
   * 新设计避免了以往自动扫描映射设计的若干缺点：引入新概念(如注解)增加学习成本、性能低、 jar 包扫描可靠性与安全性低
	 */
	
	/**
	 * Model 与 Bean 合体后注意事项
   * 合体后 JSP 模板输出 Bean 中的数据将依赖其 getter 方法，输出的变量名即为 getter 方法去掉”get”前缀字符后剩下的字符首字母变小写，
     * 如果希望 JSP 仍然使用之前的输出方式，可以在系统启动时调用一下 ModelRecordElResolver. setResolveBeanAsModel(true);
   * Controller 之中的 getModel()需要表单域名称对应于数据表字段名，而 getBean()则依赖于
     * setter 方法，表单域名对应于 setter 方法去掉”set”前缀字符后剩下的字符串字母变小写。
   * 许多类似于 jackson、 fastjson 的第三方工具依赖于 Bean 的 getter 方法进行操作，所以只有\合体后才可以使用 jackson、 fastjson
   * JFinalJson将 Model转换为 json数据时，json的 keyName是原始的数据表字段名，
     * 而 jackson、fastjson 这类依赖于 getter 方法转化成的 json 的 keyName 是数据表字段名转换而成的驼峰命名建议 mysql 数据表的字段名直接使用驼峰命名，
     * 这样可以令 json 的 keyName 完全一致，
     * 也可以使 JSP 在页面中取值时使用完全一致的属性名。注意： mysql 数据表的名称仍然使用下划线命名方式并使用小写字母，方便在 linux 与 windows 系统之间移植。
   * 总之，合体后的 Bean 在使用时要清楚使用的是其 BaseModel 中的 getter、 setter 方法还是其 Model 中的 get(String attrName)方法
	 */
	
}
