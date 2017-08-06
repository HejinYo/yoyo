package cn.hejinyo.core.utils;

import cn.hejinyo.other.model.TableIp;

import java.util.List;


public class PageParam {
	private int currPage;// 当前页码
	private int totalPage;// 总页码
	private int rowCount;// 总记录数
	public static int pageSize = 5;// 页大小
	private List<TableIp> data;// 数据

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		int totalPage = rowCount / pageSize;
		if (rowCount % pageSize > 0) {
			totalPage += 1;
		}
		if(totalPage == 0){
			totalPage = 1;
		}
		setTotalPage(totalPage);
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<TableIp> getData() {
		return data;
	}

	public void setData(List<TableIp> data) {
		this.data = data;
	}

}
