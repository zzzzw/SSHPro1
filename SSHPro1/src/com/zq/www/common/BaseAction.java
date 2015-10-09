package com.zq.www.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;


//提取的公共Action
public class BaseAction extends ActionSupport{
	
	
	// 取得Request/Response/Session的简化函数 //
		
		/**
		 * 取得HttpRequest的简化方法.
		 */
		public static HttpServletRequest getRequest() {
			return ServletActionContext.getRequest();
		}

		/**
		 * 取得HttpResponse的简化方法.
		 */
		public static HttpServletResponse getResponse() {
			return ServletActionContext.getResponse();
		}
		
		/**
		 * 取得HttpSession的简化方法.
		 */
		public static HttpSession getSession() {
			return ServletActionContext.getRequest().getSession();
		}
		
		/**
		 * 取得Application的简化方法.
		 */
		public static ServletContext getApplication() {
			return ServletActionContext.getServletContext();
		}
		
		
		/**
		 * 发送普通的json
		 */
		public static void ajaxSendJson(Object obj) throws Exception{
			ajaxSendJson(obj,false);
		}
		
		/**
		 * 发送普通的json，带日期
		 */
		public static void ajaxSendJson(Object obj,boolean isDate) throws Exception{
			String result="";
			if(isDate)
				result=JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
			else
				result=JSON.toJSONString(obj);
			getResponse().getWriter().write(result);
		}
		
		/*
		 *发送success
		 * 
		 */
		public static void ajaxSendSuccess()throws Exception{
			getResponse().getWriter().write(JSON.toJSONString("success"));
		}
		
		
		

}
