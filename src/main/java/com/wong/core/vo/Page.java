package com.wong.core.vo;

import java.util.List;

/**
 * @author huangzhibin E-mail:huangzhibin@touch-spring.com
 * @version 2014年12月18日 下午4:00:56
 *
 */
public class Page<T> {

	private int curPage;
	private int pageSize;
	private int pageCount;
	
	private int totalSize;
	
	private String href;
	
	private List<T> list;
	
	private Class<T> clazz;
	
	public Page(int curPage, int pageSize, Class<T> clazz){
		this.clazz = clazz;
		this.curPage = curPage;
		this.pageSize = pageSize;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}

	public int getCurPage() {
		return curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public String getHref() {
		return href;
	}

	public List<T> getList() {
		return list;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		pageCount = (int)Math.ceil((double)totalSize/pageSize);
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public int getFirstResult() {
		// TODO Auto-generated method stub
		return (curPage - 1) * pageSize;
	}
	
	public int getMaxResult() {
		// TODO Auto-generated method stub
		return pageSize;
	}
	
}
