package com.zq.www.common;

import java.util.ArrayList;
import java.util.List;


//工具类
public class DataZh {
   
	//object转字符串
	public static List<String> ObjtoStr(List<Object> o)
	{
		List<String> x = new ArrayList<String>();
		for(Object oj: o){
			x.add(oj.toString());
		}
		return x;
	}
	
	//object[]转字符串[]
	public static List<String[]> ObjArrtoStrArr(List<Object[]> o)
	{
		List<String[]> x = new ArrayList<String[]>();
		for(Object[] oj:o){
			String[] temp = new String[oj.length];
			for(int i = 0; i < oj.length; i++){
				temp[i] = oj[i].toString();
			}
			x.add(temp);
		}
		return x;
	}
	
}
