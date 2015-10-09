package com.zq.www.mis.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zq.www.common.BaseAction;
import com.zq.www.mis.dao.NewsDAO;
import com.zq.www.mis.entity.News;

//业务处理Action，使用Ajax

@SuppressWarnings("serial")
public class NewsAjaxAction extends BaseAction {

	// ///////////////////////////////////////////////////////////////引入的DAO
	//自动注入
	@Autowired
	private NewsDAO dao;

	// ///////////////////////////////////////////////////////////////属性参数
	private List<News> xwlist;

	// 拿实体类当组合参数，实际上是它的属性帮我们传参了，常用
	private News entity;
	
	
	
	
	
	// ///////////////////////////////////////////////////////////////业务逻辑方法
 
	// ǰ̨展示全部
	public String showAll() throws Exception {
		
		xwlist = dao.listall();
         
		//序列化，如果有日期类型，取true，若无，则可不加
		this.ajaxSendJson(xwlist, true);	
		return null;
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
		
	    this.ajaxSendJson(entity, true);	
	    
		return null;

	}
	
	 //后台保存新闻
	public String saveNews() throws Exception{	
		//根据主键是否为Null,自动判断是新增还是修改
		dao.save(entity);		
		this.getResponse().getWriter().write(JSON.toJSONString("success"));
		return null;
	}
	
	
	//后台删除新闻
	public String deleteNews ()throws Exception
	{
	  dao.delete(entity.getId());
	  this.getResponse().getWriter().write(JSON.toJSONString("success"));
	  return null;
	}
	
	
	//后台修改新闻
	public String getNews() throws Exception{
	    entity=dao.get(entity.getId());
	    
	    String result=JSON.toJSONString(entity,SerializerFeature.WriteDateUseDateFormat);
		System.out.println(result);		
		//写到前台jsp
		this.getResponse().getWriter().write(result);
		
	    return null;
	}
	
	
	//后台查询新闻
	public String searchNews() throws Exception {
		xwlist = dao.searchByTD(entity.getTitle(),entity.getPubdate());
		
		//序列化，如果有日期类型，加SerializerFeature.WriteDateUseDateFormat，若无，则可不加
		String result=JSON.toJSONString(xwlist,SerializerFeature.WriteDateUseDateFormat);
		System.out.println(result);
		
		//写到前台jsp
		this.getResponse().getWriter().write(result);
		return null;
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
