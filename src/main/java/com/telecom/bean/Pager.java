package com.telecom.bean;

import java.util.List;
import java.util.Map;

/**
 * Bean类 - 分页
 */

public class Pager {

	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
	
	// 排序方式（递增、递减）
	public enum Order {
		asc, desc
	}

	private int pageNumber = 1;// 当前页码
	private int pageSize = 20;// 每页记录数
	private String timeBy;// 查找时间字段
	private String orderBy;// 排序字段
	private Order order;// 排序方式
	private String beginDate;// 开始时间
	private String endDate;// 结束时间
	private Map<String, String> searchMap;// 查找键值对
	private Map<String, Order> orderMap;// 排序方式
	private int totalCount;// 总记录数
	private List<?> result;// 返回结果
	
	// 获取总页数
	public int getPageCount() {
		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public Map<String, String> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, String> searchMap) {
		this.searchMap = searchMap;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		}
		this.pageSize = pageSize;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getTimeBy() {
		return timeBy;
	}

	public void setTimeBy(String timeBy) {
		this.timeBy = timeBy;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public Map<String, Order> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Order> orderMap) {
		this.orderMap = orderMap;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
}