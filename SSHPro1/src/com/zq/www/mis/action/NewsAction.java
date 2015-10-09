package com.zq.www.mis.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.zq.www.common.BaseAction;
import com.zq.www.mis.dao.NewsDAO;
import com.zq.www.mis.entity.News;

//业务处理Action

//action是多实例的，对每一个请求都会new一个action,所以不必担心，参数会冲突
@SuppressWarnings("serial")
@Results( { @Result(name = "news-list", location = "/news/news-list.jsp"),
		@Result(name = "news-show", location = "/news/news-show.jsp"),
		@Result(name = "news-admin", location = "/news/news-admin.jsp"),
		@Result(name = "save-admin", location = "news!showAdminList.action" ,type="redirect"),
		@Result(name = "news-admin-input", location = "/news/news-admin-input.jsp")
		
		  })
public class NewsAction extends BaseAction {

	// ///////////////////////////////////////////////////////////////引入的DAO
	//spring自动注入
	@Autowired
	private NewsDAO dao;

	// ///////////////////////////////////////////////////////////////属性参数
	private List<News> xwlist;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private News entity;
	
	
	
	
	
	// ///////////////////////////////////////////////////////////////业务逻辑方法
 
	// ǰ̨前台展示全部
	public String showAll() throws Exception {

		xwlist = dao.listall();

		return "news-list";
	}

	// 前台展示内容
	public String showContent() throws Exception {
 
		entity = dao.get(entity.getId());
		
		//判断，若为null,则为第一次点击
		if(entity.getClicknumber()==null){
			entity.setClicknumber(0);
		}
		
		//阅读量+1
	    entity.setClicknumber(entity.getClicknumber()+1)  ;	
		
	    //更新阅读量
	    dao.update(entity);
		
		return "news-show";

	}
	
	
	
	//后台展示页
	public String showAdminList() throws Exception{		
		xwlist = dao.listall();
		return "news-admin";		
	}

    //后台保存新闻
	public String saveNews() throws Exception{
	     
		//add新增、update更新、save自动判断是新增还是更新
		//根据主键是否为Null,自动判断是新增还是修改
		dao.save(entity);
		return "save-admin";
	
	}
	
	//后台修改新闻
	public String getNews() throws Exception{
	    entity=dao.get(entity.getId());
	    return "news-admin-input";
	}
	
	//后台删除新闻
	public String deleteNews ()throws Exception
	{
	  dao.delete(entity.getId());
      return "save-admin" ;
	}
	
	//后台查询新闻
	public String searchNews() throws Exception {
		xwlist = dao.searchByTD(entity.getTitle(),entity.getPubdate());
		return "news-admin";
	}
	
	////////////////////////////////////////////////////////////////////实现get/set
	public List<News> getXwlist() {
		return xwlist;
	}

	public void setXwlist(List<News> xwlist) {
		this.xwlist = xwlist;
	}

	public News getEntity() {
		return entity;
	}

	public void setEntity(News entity) {
		this.entity = entity;
	}

}
