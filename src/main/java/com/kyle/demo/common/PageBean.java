package com.kyle.demo.common;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageBean<T>  implements Serializable {

	private static final long serialVersionUID = 1L;

	 //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //总记录数
    private long total;
    //总页数
    private int pages;
    //结果集
    private List<T> data;

    public PageBean() {
    	
    }

    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.data = page;
            this.size = page.size();
        }
    }
    
}
