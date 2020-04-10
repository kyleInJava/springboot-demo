package com.kyle.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConvertUtil {
	
	//将xml数据转为map
	public static Map<String,Object> parseXml(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		ServletInputStream inputStream;
		try {
			inputStream = request.getInputStream();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			parseNode(root,map);
			
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
		
		return map;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private static void parseNode(Element e,Map<String,Object> map){
		List<Element> children = e.elements();
		if(children.size() > 0){
			Map<String,Object> m = new HashMap<>();
			map.put(e.getName(), m);
			for(Element el : children){
				parseNode(el,m);
			}
		} else {
			map.put(e.getName(), e.getText());
		}
	}

}
