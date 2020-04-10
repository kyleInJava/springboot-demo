package com.kyle.demo.util;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import com.github.pagehelper.PageHelper;

//设置分页开始页和页大小
public class PageUtil {

	private static final Pattern pattern = Pattern.compile("[0-9]*");

	public static void startPage(Map<String, Object> paraMap) {
		
		Integer pageNum = 1;
		Integer pageSize = 15;
		Object pn = paraMap.get("pageNum");
		Object ps = paraMap.get("pageSize");
		
		if (Objects.nonNull(pn) && pattern.matcher(pn.toString()).matches()) {
			pageNum = Integer.valueOf(pn.toString());
		}
		
		if (Objects.nonNull(ps) && pattern.matcher(ps.toString()).matches()) {
			pageSize = Integer.valueOf(ps.toString());
		}

		paraMap.put("pageNum", pageNum);
		paraMap.put("pageSize", pageSize);
		
		PageHelper.startPage(pageNum,pageSize);
	}
	
	public static void startPage(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
	}
	
	
	



}
